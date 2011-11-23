package de.robind.swt.protocol;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import de.robind.swt.msg.SWTMessage;

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
  }
}
