package de.robind.swt.client;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.Channels;

import de.robind.swt.msg.SWTEvent;
import de.robind.swt.msg.SWTMessageFactory;
import de.robind.swt.msg.SWTObjectId;

/**
 * SWT-listener.
 * <p>
 * Transforms {@link Event events} received over the listener into a
 * {@link SWTEvent} and sends the event to the server.
 *
 * @author Robin Doer
 */
public class SWTEventListener implements Listener {
  /**
   * The channel to the server
   */
  private Channel channel = null;

  /**
   * Factory used to create messages.
   */
  private SWTMessageFactory messageFactory = null;

  /**
   * The id of the source-object
   */
  private int objId;

  public SWTEventListener(int objId, Channel channel,
      SWTMessageFactory messageFactory) {

    this.objId = objId;
    this.channel = channel;
    this.messageFactory = messageFactory;
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
   */
  public void handleEvent(Event e) {
    Map<String, Object> attributes = new HashMap<String, Object>();
    attributes.put("swtObjectId", this.objId);
    attributes.put("type", e.type);
    attributes.put("widget", new SWTObjectId(this.objId));

    switch (e.type) {
      case SWT.Selection:
        attributes.put("item", e.item);
        attributes.put("x", e.x);
        attributes.put("y", e.y);
        attributes.put("width", e.width);
        attributes.put("height", e.height);
        attributes.put("detail", e.detail);
        attributes.put("stateMask", e.stateMask);
        attributes.put("text", e.text);
        attributes.put("doit", e.doit);
        break;
    }

    SWTEvent event = this.messageFactory.createEvent(this.objId, attributes);
    Channels.write(this.channel, event);
  }
}
