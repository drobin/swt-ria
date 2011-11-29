package org.eclipse.swt.widgets;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.events.TypedListener;
import org.eclipse.swt.server.Singleton;

import de.robind.swt.msg.SWTMessageFactory;
import de.robind.swt.msg.SWTRegRequest;

class ListenerTable {
  /**
   * The containing widget.
   */
  private Widget widget = null;

  /**
   * Map stores listener per event-type.
   */
  private Map<Integer, List<Listener>> listenerMap =
      new HashMap<Integer, List<Listener>>();

  public ListenerTable(Widget widget) {
    this.widget = widget;
  }

  /**
   * Appends a listener to the table.
   *
   * @param eventType The event-type of the listener
   * @param listener The listener to register
   */
  void addListener(int eventType, Listener listener) {
    List<Listener> list = getListenerList(eventType);

    if (list.isEmpty()) {
      // TODO Evaluat answer
      SWTMessageFactory factory = Singleton.getMessageFactory();
      SWTRegRequest request = factory.createRegRequest(
          this.widget.getId(), eventType, true);
      this.widget.getDisplay().sendMessage(request);
    }

    list.add(listener);
  }

  /**
   * Removes a listener from the table.
   *
   * @param eventType The event-type of the listener
   * @param listener The listener to remove from the table
   */
  void removeListener(int eventType, Listener listener) {
    List<Listener> list = getListenerList(eventType);

    list.remove(listener);

    if (list.isEmpty()) {
      // TODO Evaluate answer
      SWTMessageFactory factory = Singleton.getMessageFactory();
      SWTRegRequest request = factory.createRegRequest(
          this.widget.getId(), eventType, false);
      this.widget.getDisplay().sendMessage(request);
    }
  }

  /**
   * Removes a {@link TypedListenerProxy} from the table.
   * <p>
   * The proxy with an association to the given listener is removed.
   *
   * @param eventType The event-type of the listener
   * @param listener The associated listener to remove
   */
  void removeListener(int eventType, TypedListener listener) {
    for (Listener l: getListenerList(eventType)) {
      if (l instanceof TypedListenerProxy) {
        if (((TypedListenerProxy)l).getListener() == listener) {
          removeListener(eventType, listener);
          return;
        }
      }
    }
  }

  /**
   * Tests whether you have at least one listener registered for the given
   * event-type.
   *
   * @param eventType The requested event-type
   * @return If you have at least one listener for the event-type
   *         <code>true</code> is returned, <code>false</code> otherwise.
   */
  boolean haveListener(int eventType) {
    if (this.listenerMap.containsKey(eventType)) {
      return (!getListenerList(eventType).isEmpty());
    } else {
      return (false);
    }
  }

  /**
   * Returns the list with all listener of the given event-type from
   * {@link #listenerMap}.
   * <p>
   * If the list does not exists, it is created.
   *
   * @param eventType The requested event-type
   * @return The related listener-list
   */
  private List<Listener> getListenerList(int eventType) {
    List<Listener> list = this.listenerMap.get(eventType);

    if (list == null) {
      list = new LinkedList<Listener>();
      this.listenerMap.put(eventType, list);
    }

    return (list);
  }
}
