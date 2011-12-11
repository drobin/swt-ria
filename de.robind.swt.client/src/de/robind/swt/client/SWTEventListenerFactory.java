package de.robind.swt.client;

import org.jboss.netty.channel.Channel;

import de.robind.swt.msg.SWTMessageFactory;

/**
 * Factory used to create {@link SWTEventListener}-instances.
 *
 * @author Robin Doer
 */
public class SWTEventListenerFactory {
  /**
   * The channel to the server
   */
  private Channel channel = null;

  /**
   * Mapping from object-id to {@link SWTObject}.
   */
  private SWTObjectMap objMap = null;

  /**
   * Factory used to create messages.
   */
  private SWTMessageFactory messageFactory = null;

  public SWTEventListenerFactory(Channel channel, SWTObjectMap objMap,
      SWTMessageFactory messageFactory) {

    this.channel = channel;
    this.objMap = objMap;
    this.messageFactory = messageFactory;
  }

  /**
   * Creates a new {@link SWTEventListener}-instance.
   *
   * @param objId The object-id of the widget maintained by the listener
   * @return The new listener
   */
  public SWTEventListener createListener(int objId) {
    return (new SWTEventListener(objId, this.channel, this.objMap,
        this.messageFactory));
  }
}
