package de.robind.swt.protocol;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import de.robind.swt.msg.SWTMessage;

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

    return (new Object());
  }
}
