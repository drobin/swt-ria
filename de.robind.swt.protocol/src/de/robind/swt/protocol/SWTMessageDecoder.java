package de.robind.swt.protocol;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import de.robind.swt.msg.SWTMessage;
import de.robind.swt.msg.SWTNewRequest;
import de.robind.swt.msg.SWTRequest;
import de.robind.swt.msg.SWTResponse;

/**
 * Decoder used to decode a byte-stream received from the {@link Channel}
 * into the correct {@link SWTMessage}.
 *
 * @author Robin Doer
 */
public class SWTMessageDecoder extends FrameDecoder {
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

    buffer.markReaderIndex();

    // Read the header
    short magic = buffer.readShort();
    byte operation = buffer.readByte();
    byte type = buffer.readByte();
    int payloadLength = buffer.readInt();

    if (magic !=SWTProtocol.MAGIC) {
      buffer.resetReaderIndex();
      throw new SWTDecoderException("Invalid magic number: " + magic);
    }

    if (operation != SWTProtocol.OP_NEW) {
      buffer.resetReaderIndex();
      throw new SWTDecoderException("Invalid operation: " + operation);
    }

    if (type != SWTProtocol.TYPE_REQ) {
      buffer.resetReaderIndex();
      throw new SWTDecoderException("Invalid message-type: " + type);
    }

    if (payloadLength < 0) {
      buffer.resetReaderIndex();
      throw new SWTDecoderException("Invalid payload-length: " + payloadLength);
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
       message = decodeResponseMessage(operation, buffer);
       break;
    }

    if (message == null) {
      throw new Error("Should never be reached");
    }

    if (buffer.readerIndex() < payloadLength + 8) {
      // The payload was not read completely
      throw new SWTDecoderException(
          "Data still in payload. Available: " + payloadLength +
          ", consumed: " + (buffer.readerIndex() - 8));
    }

    if (buffer.readerIndex() > payloadLength + 8) {
      // payload-overflow. More data read then available
      throw new SWTDecoderException(
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
        throw new SWTDecoderException("Invalid objClass: " + objClassString);
      }

      byte numArgs = buffer.readByte();

      if (numArgs < 0) {
        throw new SWTDecoderException("Invalid number of arguments: " + numArgs);
      }

      Object args[] = new Object[numArgs];
      for (int i = 0; i < numArgs; i++) {
        args[i] = SWTProtocol.readArgument(buffer);
      }

      return (new SWTNewRequest(objId, objClass, args));
    } else {
      return (null);
    }
  }

  /**
   * Decodes a {@link SWTResponse response-message}
   *
   * @param operation The operation
   * @param buffer The buffer with source-data
   * @return The decoded response-message
   * @throws SWTDecoderException if decoding has failed
   */
  private SWTResponse decodeResponseMessage(
      byte operation, ChannelBuffer buffer) throws SWTDecoderException {

    return (null);
  }
}
