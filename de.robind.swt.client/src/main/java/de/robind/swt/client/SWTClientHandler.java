package de.robind.swt.client;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import de.robind.swt.msg.SWTMessage;
import de.robind.swt.msg.SWTRequest;


/**
 * This channel-handler handles incoming and outgoing messages for the client.
 *
 * @author Robin Doer
 */
public class SWTClientHandler extends SimpleChannelHandler {
  /**
   * Logger of the class.
   */
  private static final Logger logger = Logger.getLogger(SWTClientHandler.class);

  /**
   * Environment of the client.
   */
  private SWTClientEnvironment env = null;

  /**
   * Creates a new {@link SWTClientHandler}.
   *
   * @param env The environment of the client
   */
  public SWTClientHandler(SWTClientEnvironment env) {
    this.env = env;
  }

  /* (non-Javadoc)
   * @see org.jboss.netty.channel.SimpleChannelHandler#messageReceived(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.MessageEvent)
   */
  @Override
  public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
      throws Exception {

    // The request-message from server
    if (e.getMessage() instanceof SWTRequest) {
      // You cannot evaluate the request here. It needs to be performed in the
      // thread, where the SWT-event-loop runs. So, put it into the
      // request-queue. Now the SWT-event-loop can evaluate the message.
      SWTRequest request = (SWTRequest)e.getMessage();
      logger.debug("Request:" + request);

      env.requestQueue.offer(request);

      // If the loop "sleeps", wake it up. We have something to do!
      env.display.wake();
    } else {
      throw new Exception(
          "Upsupported message-type: " + e.getMessage().getClass());
    }

    // Simple forward message upstream without modifying the message
    ctx.sendUpstream(e);
  }

  /* (non-Javadoc)
   * @see org.jboss.netty.channel.SimpleChannelHandler#writeRequested(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.MessageEvent)
   */
  @Override
  public void writeRequested(ChannelHandlerContext ctx, MessageEvent e)
      throws Exception {

    // The answer-message to be send back to server
    SWTMessage message = (SWTMessage)e.getMessage();
    logger.debug("Response:" + message);

    // Simply forward downstream without modifying the message
    ctx.sendDownstream(e);
  }

  /* (non-Javadoc)
   * @see org.jboss.netty.channel.SimpleChannelHandler#exceptionCaught(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ExceptionEvent)
   */
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
    logger.error("exceptionCaught, closing channel", e.getCause());
    ctx.getChannel().close();
  }
}
