package de.robind.swt.protocol;

import static de.robind.swt.protocol.datatype.SWTAny.readAny;
import static de.robind.swt.protocol.datatype.SWTBoolean.readBoolean;
import static de.robind.swt.protocol.datatype.SWTByte.readByte;
import static de.robind.swt.protocol.datatype.SWTInteger.readInteger;
import static de.robind.swt.protocol.datatype.SWTString.readString;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import de.robind.swt.msg.SWTEvent;
import de.robind.swt.msg.SWTException;
import de.robind.swt.msg.SWTMessage;
import de.robind.swt.msg.SWTMessageFactory;
import de.robind.swt.msg.SWTRequest;
import de.robind.swt.msg.SWTResponse;

/**
 * Decoder used to decode a byte-stream received from the {@link Channel}
 * into the correct {@link SWTMessage}.
 *
 * @author Robin Doer
 */
public class SWTMessageDecoder extends FrameDecoder {
  /**
   * Factory used to create messages.
   */
  private SWTMessageFactory factory = null;

  /**
   * Creates a new {@link SWTMessageDecoder}.
   *
   * @param factory The factory is used to create {@link SWTMessage}-instances
   * @throws NullPointerException if <code>factory</code> is <code>null</code>
   */
  public SWTMessageDecoder(SWTMessageFactory factory)
      throws NullPointerException {

    if (factory == null) {
      throw new NullPointerException("factory cannot be null");
    }

    this.factory = factory;
  }

  /* (non-Javadoc)
   * @see org.jboss.netty.handler.codec.frame.FrameDecoder#decode(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.Channel, org.jboss.netty.buffer.ChannelBuffer)
   */
  @Override
  protected Object decode(ChannelHandlerContext ctx, Channel channel,
      ChannelBuffer buffer) throws Exception {

    // Read the header of the message
    if (buffer.readableBytes() < 8) {
      // Not enough data available to read the complete header
      return (null);
    }

    int readerIndexBefore = buffer.readerIndex();
    buffer.markReaderIndex();

    // Read the header
    short magic = buffer.readShort();
    byte operation = buffer.readByte();
    byte type = buffer.readByte();
    int payloadLength = buffer.readInt();

    if (magic !=SWTProtocol.MAGIC) {
      buffer.resetReaderIndex();
      throw new SWTProtocolException("Invalid magic number: " + magic);
    }

    if (operation != SWTProtocol.OP_NEW && operation != SWTProtocol.OP_CALL &&
        operation != SWTProtocol.OP_REG && operation != SWTProtocol.OP_ATTR) {

      buffer.resetReaderIndex();
      throw new SWTProtocolException("Invalid operation: " + operation);
    }

    if (type != SWTProtocol.TYPE_REQ && type != SWTProtocol.TYPE_RSP &&
        type != SWTProtocol.TYPE_EXC && type != SWTProtocol.TYPE_EVT) {

      buffer.resetReaderIndex();
      throw new SWTProtocolException("Invalid message-type: " + type);
    }

    if (payloadLength < 0) {
      buffer.resetReaderIndex();
      throw new SWTProtocolException(
          "Invalid payload-length: " + payloadLength);
    }

    // Wait until the whole payload is available
    if (buffer.readableBytes() < payloadLength) {
      buffer.resetReaderIndex();
      return (null);
    }

    SWTMessage message = null;

    switch (type) {
      case SWTProtocol.TYPE_REQ:
       message = decodeRequestMessage(operation, buffer);
       break;
      case SWTProtocol.TYPE_RSP:
       message = decodeResponseMessage(operation, buffer, payloadLength);
       break;
      case SWTProtocol.TYPE_EXC:
        message = decodeExceptionMessage(buffer);
        break;
      case SWTProtocol.TYPE_EVT:
        message = decodeEventMessage(buffer);
        break;
    }

    if (message == null) {
      throw new Error("Should never be reached");
    }

    int readerIndexAfter = buffer.readerIndex();

    if ((readerIndexAfter - readerIndexBefore) < payloadLength + 8) {
      // The payload was not read completely
      throw new SWTProtocolException(
          "Data still in payload. Available: " + payloadLength +
          ", consumed: " + (buffer.readerIndex() - 8));
    }

    if ((readerIndexAfter - readerIndexBefore) > payloadLength + 8) {
      // payload-overflow. More data read then available
      throw new SWTProtocolException(
          "Payload-overflow. Available: " + payloadLength +
          ", consumed: " + (buffer.readerIndex() - 8));
    }

    return (message);
  }

  /**
   * Decodes a {@link SWTRequest request-message}.
   *
   * @param operation The operation
   * @param buffer The buffer with source data
   * @return The decoded request-message
   * @throws SWTProtocolException if decoding has failed
   */
  private SWTRequest decodeRequestMessage(
      byte operation, ChannelBuffer buffer) throws SWTProtocolException {

    if (operation == SWTProtocol.OP_NEW) {
      int objId = readInteger(buffer);
      String objClassString = readString(buffer);
      Class<?> objClass;

      try {
        objClass = Class.forName(objClassString);
      } catch (ClassNotFoundException e) {
        throw new SWTProtocolException("Invalid objClass: " + objClassString);
      }

      byte numArgs = readByte(buffer);

      if (numArgs < 0) {
        throw new SWTProtocolException("Invalid number of arguments: " + numArgs);
      }

      Object args[] = new Object[numArgs];
      for (int i = 0; i < numArgs; i++) {
        args[i] = readAny(buffer);
      }

      return (this.factory.createNewRequest(objId, objClass, args));
    } else if (operation == SWTProtocol.OP_CALL) {
      int objId = readInteger(buffer);
      String method = readString(buffer);
      byte numArgs = readByte(buffer);

      if (method.length() == 0) {
        throw new SWTProtocolException("method cannot be empty");
      }

      if (numArgs < 0) {
        throw new SWTProtocolException(
            "Invalid number of arguments: " + numArgs);
      }

      Object args[] = new Object[numArgs];
      for (int i = 0; i < numArgs; i++) {
        args[i] = readAny(buffer);
      }

      return (this.factory.createCallRequest(objId, method, args));
    } else if (operation == SWTProtocol.OP_REG) {
      int objId = readInteger(buffer);
      int eventType = readInteger(buffer);
      boolean enable = readBoolean(buffer);

      return (this.factory.createRegRequest(objId, eventType, enable));
    } else if (operation == SWTProtocol.OP_ATTR) {
      int objId = readInteger(buffer);
      String attrName = readString(buffer);

      if (attrName.length() == 0) {
        throw new SWTProtocolException("Name of attribute cannot be empty");
      }

      Object attrValue = readAny(buffer);

      return (this.factory.createAttrRequest(objId, attrName, attrValue));
    } else {
      return (null);
    }
  }

  /**
   * Decodes a {@link SWTResponse response-message}
   *
   * @param operation The operation
   * @param buffer The buffer with source-data
   * @param payloadLength Length of payload
   * @return The decoded response-message
   * @throws SWTDecoderException if decoding has failed
   */
  private SWTResponse decodeResponseMessage(
      byte operation, ChannelBuffer buffer, int payloadLength)
          throws SWTProtocolException {

    if (operation == SWTProtocol.OP_NEW) {
      return (this.factory.createNewResponse());
    } else if (operation == SWTProtocol.OP_CALL) {
      if (payloadLength > 0) {
        Object result = readAny(buffer);
        return (this.factory.createCallResponse(result));
      } else {
        return (this.factory.createCallResponse(null));
      }
    } else if (operation == SWTProtocol.OP_REG) {
      return (this.factory.createRegResponse());
    } else if (operation == SWTProtocol.OP_ATTR) {
      return (this.factory.createAttrResponse());
    } else {
      return (null);
    }
  }

  /**
   * Decodes a {@link SWTException}-message.
   *
   * @param buffer The buffer with source-data
   * @return the decoded exception-message
   * @throws SWTProtocolException if decoding has failed
   */
  private SWTException decodeExceptionMessage(ChannelBuffer buffer)
      throws SWTProtocolException {

    String className = readString(buffer);
    String message = readString(buffer);

    try {
      Class<? extends Throwable> excClass =
          Class.forName(className).asSubclass(Throwable.class);
      Constructor<? extends Throwable> ctor =
          excClass.getConstructor(String.class);
      Throwable exc = ctor.newInstance(message);

      return (this.factory.createException(exc));
    } catch (Exception e) {
      throw new SWTProtocolException("Failed to create exception", e);
    }
  }

  /**
   * Decodes a {@link SWTEvent}-message.
   *
   * @param buffer The buffer with source-data
   * @return the decoded event-message
   * @throws SWTProtocolException if decoding has failed
   */
  private SWTEvent decodeEventMessage(ChannelBuffer buffer)
      throws SWTProtocolException {

    Map<String, Object> attributes = new HashMap<String, Object>();

    int objId = readInteger(buffer);
    byte numAttributes = readByte(buffer);

    if (numAttributes < 0) {
      throw new SWTProtocolException("Number of attributes cannot be negative");
    }

    for (int i = 0; i < numAttributes; i++) {
      String key = readString(buffer);
      Object value = readAny(buffer);

      attributes.put(key, value);
    }

    return (this.factory.createEvent(objId, attributes));
  }
}
