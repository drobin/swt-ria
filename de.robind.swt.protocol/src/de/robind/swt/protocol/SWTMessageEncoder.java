package de.robind.swt.protocol;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import de.robind.swt.msg.SWTCallRequest;
import de.robind.swt.msg.SWTCallResponse;
import de.robind.swt.msg.SWTMessage;
import de.robind.swt.msg.SWTNewRequest;
import de.robind.swt.msg.SWTNewResponse;
import de.robind.swt.msg.SWTRegRequest;
import de.robind.swt.msg.SWTRegResponse;
import de.robind.swt.msg.SWTRequest;
import de.robind.swt.msg.SWTResponse;

/**
 * Encoder used to transform a {@link SWTMessage} back into a byte-stream,
 * which can be send over the network.
 *
 * @author Robin Doer
 */
public class SWTMessageEncoder extends SimpleChannelHandler {
  /* (non-Javadoc)
   * @see org.jboss.netty.channel.SimpleChannelHandler#writeRequested(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.MessageEvent)
   */
  @Override
  public void writeRequested(ChannelHandlerContext ctx, MessageEvent e)
      throws Exception {

    if (!(e.getMessage() instanceof SWTMessage)) {
      throw new SWTProtocolException(
          "Invalid message-class: " + e.getMessage().getClass().getName());
    }

    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer(8);

    // Skip the header, encode the payload first into the buffer
    buffer.writerIndex(8);
    byte operation, type;

    if (e.getMessage() instanceof SWTRequest) {
      operation = encodeRequestMessage((SWTRequest)e.getMessage(), buffer);
      type = SWTProtocol.TYPE_REQ;
    } else if (e.getMessage() instanceof SWTResponse) {
      operation = encodeResponseMessage((SWTResponse)e.getMessage(), buffer);
      type = SWTProtocol.TYPE_RSP;
    } else {
      throw new Error("Should never be reached");
    }

    // Size of payload: Number of bytes written into the buffer
    int payloadLength = buffer.writerIndex() - 8;

    // Step back to the beginning of the buffer, write the header
    buffer.writerIndex(0);
    buffer.writeShort(SWTProtocol.MAGIC);
    buffer.writeByte(operation);
    buffer.writeByte(type);
    buffer.writeInt(payloadLength);

    // Advance again to the end of the buffer
    buffer.writerIndex(payloadLength + 8);

    Channels.write(ctx, e.getFuture(), buffer);
  }

  /**
   * Encodes a {@link SWTRequest request-message} into the given buffer.
   *
   * @param msg The message to be encoded
   * @param buffer The destination buffer
   * @return The operation (one of the SWTProtocol.OP_*-values).
   * @throws SWTProtocolException if encoding failed
   */
  private byte encodeRequestMessage(SWTRequest msg, ChannelBuffer buffer)
      throws SWTProtocolException {

    if (msg instanceof SWTCallRequest) {
      SWTCallRequest request = (SWTCallRequest)msg;

      buffer.writeInt(request.getObjId());
      SWTProtocol.writeString(buffer, request.getMethod());
      buffer.writeByte(request.getArguments().length);

      for (Object arg: request.getArguments()) {
        SWTProtocol.writeArgument(buffer, arg);
      }

      return (SWTProtocol.OP_CALL);
    } else if (msg instanceof SWTNewRequest) {
      SWTNewRequest request = (SWTNewRequest)msg;

      buffer.writeInt(request.getObjId());
      SWTProtocol.writeString(buffer, request.getObjClass().getName());
      buffer.writeByte(request.getArguments().length);

      for (Object arg: request.getArguments()) {
        SWTProtocol.writeArgument(buffer, arg);
      }

      return (SWTProtocol.OP_NEW);
    } else if (msg instanceof SWTRegRequest) {
      SWTRegRequest request = (SWTRegRequest)msg;

      buffer.writeInt(request.getObjId());
      buffer.writeInt(request.getEventType());
      SWTProtocol.writeBoolean(buffer, request.enable());

      return (SWTProtocol.OP_REG);
    }

    throw new SWTProtocolException(
        "Request not supported: " + msg.getClass().getName());
  }

  /**
   * Encodes a {@link SWTResponse response-message} into the given buffer.
   *
   * @param msg The message to be encoded
   * @param buffer The destination buffer
   * @return The operation (one of the SWTProtocol.OP_*-values).
   * @throws SWTProtocolException if encoding failed
   */
  private byte encodeResponseMessage(SWTResponse msg, ChannelBuffer buffer)
      throws SWTProtocolException {

    if (msg instanceof SWTCallResponse) {
      SWTCallResponse response = (SWTCallResponse)msg;

      if (response.getResult() != null) {
        SWTProtocol.writeArgument(buffer, response.getResult());
      }

      return (SWTProtocol.OP_CALL);
    } else if (msg instanceof SWTNewResponse) {
      return (SWTProtocol.OP_NEW);
    } else if (msg instanceof SWTRegResponse) {
      return (SWTProtocol.OP_REG);
    }

    throw new SWTProtocolException(
        "Response not supported: " + msg.getClass().getName());
  }
}
