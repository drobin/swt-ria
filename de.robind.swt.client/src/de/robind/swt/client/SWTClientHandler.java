package de.robind.swt.client;

import org.jboss.netty.channel.SimpleChannelHandler;

/**
 * This channel-handler handles incoming and outgoing messages for the client.
 *
 * @author Robin Doer
 */
public class SWTClientHandler extends SimpleChannelHandler {
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
}
