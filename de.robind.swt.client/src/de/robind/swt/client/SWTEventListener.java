package de.robind.swt.client;

import java.nio.channels.Channel;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Widget;

import de.robind.swt.msg.SWTEvent;
import de.robind.swt.msg.SWTMessageFactory;

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
   * The widget where the event is triggered
   */
  private Widget widget = null;

  public SWTEventListener(Channel channel, SWTMessageFactory messageFactory, Widget widget) {
    this.channel = channel;
    this.messageFactory = messageFactory;
    this.widget = widget;
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
   */
  public void handleEvent(Event event) {
    System.out.println("Do something");
  }
}
