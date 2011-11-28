package de.robind.swt.server;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

/**
 * Channel-handler used to handle server business logic.
 *
 * @author Robin Doer
 */
public class SWTServerHandler extends SimpleChannelHandler {
  /**
   * Logger of the class.
   */
  private static final Logger logger = Logger.getLogger(SWTServerHandler.class);

  /* (non-Javadoc)
   * @see org.jboss.netty.channel.SimpleChannelHandler#channelConnected(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent)
   */
  @Override
  public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
      throws Exception {

    logger.debug("Connected: " + ctx.getChannel());

    SWTApplication application = new SWTApplication(ctx.getChannel());
    application.start();

    ctx.setAttachment(application);
  }

  /* (non-Javadoc)
   * @see org.jboss.netty.channel.SimpleChannelHandler#channelClosed(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent)
   */
  @Override
  public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
      throws Exception {

    logger.debug("Closed: " + ctx.getChannel());

    SWTApplication application = (SWTApplication)ctx.getAttachment();
    application.join();
  }

  /* (non-Javadoc)
   * @see org.jboss.netty.channel.SimpleChannelHandler#exceptionCaught(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ExceptionEvent)
   */
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
    logger.error("Exception caught, closing channel", e.getCause());
    e.getChannel().close();
  }
}
