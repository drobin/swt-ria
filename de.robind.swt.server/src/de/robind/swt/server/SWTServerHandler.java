package de.robind.swt.server;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import de.robind.swt.msg.SWTMessage;
import de.robind.swt.msg.SWTResponse;

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
   * @see org.jboss.netty.channel.SimpleChannelHandler#writeRequested(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.MessageEvent)
   */
  @Override
  public void writeRequested(ChannelHandlerContext ctx, MessageEvent e)
      throws Exception {

    // Message send to the client
    SWTMessage message = (SWTMessage)e.getMessage();
    logger.debug("Send:" + message);

    // Simply forward downstream without modifying the message
    ctx.sendDownstream(e);
  }

  /* (non-Javadoc)
   * @see org.jboss.netty.channel.SimpleChannelHandler#messageReceived(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.MessageEvent)
   */
  @Override
  public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
      throws Exception {

    SWTApplication app = (SWTApplication)ctx.getAttachment();

    if (e.getMessage() instanceof SWTResponse) {
      logger.debug("Response received: " + e.getMessage());
      app.getResponseQueue().put((SWTResponse)e.getMessage());
    } else {
      throw new Exception("Unexpected message of type " +
          e.getMessage().getClass().getName() + " received");
    }

    // Simple forward message upstream without modifying the message
    ctx.sendUpstream(e);
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
