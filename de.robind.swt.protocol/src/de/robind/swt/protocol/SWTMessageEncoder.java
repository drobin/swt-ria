package de.robind.swt.protocol;

import static de.robind.swt.protocol.datatype.SWTAny.writeAny;
import static de.robind.swt.protocol.datatype.SWTBoolean.writeBoolean;
import static de.robind.swt.protocol.datatype.SWTByte.writeByte;
import static de.robind.swt.protocol.datatype.SWTInteger.writeInteger;
import static de.robind.swt.protocol.datatype.SWTString.writeString;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import de.robind.swt.msg.SWTCallRequest;
import de.robind.swt.msg.SWTCallResponse;
import de.robind.swt.msg.SWTEvent;
import de.robind.swt.msg.SWTException;
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
    } else if (e.getMessage() instanceof SWTException) {
      operation = encodeExceptionMessage((SWTException)e.getMessage(), buffer);
      type = SWTProtocol.TYPE_EXC;
    } else if (e.getMessage() instanceof SWTResponse) {
      operation = encodeResponseMessage((SWTResponse)e.getMessage(), buffer);
      type = SWTProtocol.TYPE_RSP;
    } else if (e.getMessage() instanceof SWTEvent) {
      operation = encodeEventMessage((SWTEvent)e.getMessage(), buffer);
      type = SWTProtocol.TYPE_EVT;
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

      writeInteger(buffer, request.getObjId());
      writeString(buffer, request.getMethod());
      writeByte(buffer, (byte)request.getArguments().length); // TODO Check for overflow

      for (Object arg: request.getArguments()) {
        writeAny(buffer, arg);
      }

      return (SWTProtocol.OP_CALL);
    } else if (msg instanceof SWTNewRequest) {
      SWTNewRequest request = (SWTNewRequest)msg;

      writeInteger(buffer, request.getObjId());
      writeString(buffer, request.getObjClass().getName());
      writeByte(buffer, (byte)request.getArguments().length); // TODO Check or overflow

      for (Object arg: request.getArguments()) {
        writeAny(buffer, arg);
      }

      return (SWTProtocol.OP_NEW);
    } else if (msg instanceof SWTRegRequest) {
      SWTRegRequest request = (SWTRegRequest)msg;

      writeInteger(buffer, request.getObjId());
      writeInteger(buffer, request.getEventType());
      writeBoolean(buffer, request.enable());

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
        writeAny(buffer, response.getResult());
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

  /**
   * Encodes an {@link SWTException exception-message} into the given buffer.
   *
   * @param msg The message to be encoded
   * @param buffer The destination buffer
   * @return The operation (one of the SWTProtocol.OP_*-values).
   * @throws SWTProtocolException if encoding failed
   */
  private byte encodeExceptionMessage(SWTException msg, ChannelBuffer buffer)
      throws SWTProtocolException {

    String message = msg.getCause().getMessage();
    if (message == null) {
      message = "";
    }

    writeString(buffer, msg.getCause().getClass().getName());
    writeString(buffer, message);

    return (SWTProtocol.OP_CALL); // Ignored by the exception-message-type
  }

  /**
   * Encodes an {@link SWTEvent event-message} into the given buffer.
   *
   * @param msg The message to be encoded
   * @param buffer The destination buffer
   * @return The operation (one of the SWTProtocol.OP_*-values).
   * @throws SWTProtocolException if encoding failed
   */
  private byte encodeEventMessage(SWTEvent msg, ChannelBuffer buffer)
      throws SWTProtocolException {

    if (msg.getAttributes().length > Byte.MAX_VALUE) {
      throw new SWTProtocolException(
          "Number of attributes cannot be greater than " + Byte.MAX_VALUE);
    }

    writeInteger(buffer, msg.getObjId());
    writeByte(buffer, (byte)msg.getAttributes().length); // TODO Check for overflow

    for (String attribute: msg.getAttributes()) {
      writeString(buffer, attribute);
      writeAny(buffer, msg.getAttributeValue(attribute));
    }

    return (SWTProtocol.OP_CALL); // Ignored by the event-message-type
  }
}
