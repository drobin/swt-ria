package de.robind.swt.client;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.SimpleChannelHandler;


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
   * @see org.jboss.netty.channel.SimpleChannelHandler#exceptionCaught(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ExceptionEvent)
   */
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
    logger.error("exceptionCaught, closing channel", e.getCause());
    ctx.getChannel().close();
  }
}
