package de.robind.swt.protocol;

import java.lang.reflect.Constructor;

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
        operation != SWTProtocol.OP_REG) {

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
      int objId = buffer.readInt();
      String objClassString = SWTProtocol.readString(buffer);
      Class<?> objClass;

      try {
        objClass = Class.forName(objClassString);
      } catch (ClassNotFoundException e) {
        throw new SWTProtocolException("Invalid objClass: " + objClassString);
      }

      byte numArgs = buffer.readByte();

      if (numArgs < 0) {
        throw new SWTProtocolException("Invalid number of arguments: " + numArgs);
      }

      Object args[] = new Object[numArgs];
      for (int i = 0; i < numArgs; i++) {
        args[i] = SWTProtocol.readArgument(buffer);
      }

      return (this.factory.createNewRequest(objId, objClass, args));
    } else if (operation == SWTProtocol.OP_CALL) {
      int objId = buffer.readInt();
      String method = SWTProtocol.readString(buffer);
      byte numArgs = buffer.readByte();

      if (method.length() == 0) {
        throw new SWTProtocolException("method cannot be empty");
      }

      if (numArgs < 0) {
        throw new SWTProtocolException(
            "Invalid number of arguments: " + numArgs);
      }

      Object args[] = new Object[numArgs];
      for (int i = 0; i < numArgs; i++) {
        args[i] = SWTProtocol.readArgument(buffer);
      }

      return (this.factory.createCallRequest(objId, method, args));
    } else if (operation == SWTProtocol.OP_REG) {
      int objId = buffer.readInt();
      int eventType = buffer.readInt();
      boolean enable = SWTProtocol.readBoolean(buffer);

      return (this.factory.createRegRequest(objId, eventType, enable));
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
        Object result = SWTProtocol.readArgument(buffer);
        return (this.factory.createCallResponse(result));
      } else {
        return (this.factory.createCallResponse(null));
      }
    } else if (operation == SWTProtocol.OP_REG) {
      return (this.factory.createRegResponse());
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

    String className = SWTProtocol.readString(buffer);
    String message = SWTProtocol.readString(buffer);

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

    int objId = buffer.readInt();
    return (this.factory.createEvent(objId));
  }
}
