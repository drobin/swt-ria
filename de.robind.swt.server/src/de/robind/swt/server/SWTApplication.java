package de.robind.swt.server;

import java.lang.reflect.Method;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
import org.eclipse.swt.server.DisplayPool;
import org.eclipse.swt.server.Key;
import org.jboss.netty.channel.Channel;

import de.robind.swt.msg.SWTResponse;

/**
 * An application running on the application-server
 *
 * @author Robin Doer
 */
public class SWTApplication extends Thread {
  /**
   * Logger of the class
   */
  private static final Logger logger = Logger.getLogger(SWTApplication.class);

  /**
   * The channel used to send/receive messages.
   */
  private Channel channel = null;

  /**
   * The key of the application
   */
  private Key key = new SWTApplicationKey(this);

  /**
   * Queue holds responses received from the client.
   */
  private BlockingQueue<SWTResponse> responseQueue =
      new LinkedBlockingQueue<SWTResponse>();

  /**
   * Creates a new {@link SWTApplication}.
   *
   * @param channel The related channel
   */
  public SWTApplication(Channel channel) {
    this.channel = channel;
  }

  /**
   * Returns the {@link Channel} assigned to the application.
   *
   * @return The related channel
   */
  public Channel getChannel() {
    return (this.channel);
  }

  /**
   * Returns the queue, which holds responses received from the connected
   * client.
   *
   * @return Queue with responses send by the client
   */
  public BlockingQueue<SWTResponse> getResponseQueue() {
    return (this.responseQueue);
  }

  /* (non-Javadoc)
   * @see java.lang.Thread#run()
   */
  @Override
  public void run() {
    try {
      DisplayPool.getInstance().pushKey(this.key);

      Class<?> appClass = Class.forName("de.robind.swt.demo.ButtonDemo");
      Method mainMethod = appClass.getMethod("main", String[].class);
      mainMethod.invoke(null, new Object[] { new String[] {} });
    } catch (Exception e) {
      logger.error("Failed to run the application", e);
    }

    this.channel.close();
  }
}
