package org.eclipse.swt.server;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.eclipse.swt.widgets.Display;


public class DisplayPool {
  /**
   * The singleton instance of the {@link DisplayPool}.
   */
  private static DisplayPool singleton = null;

  /**
   * Map of registered displays.
   * The key of the map is the external {@link Key} assigned with
   * {@link #pushKey(Key)}.
   */
  private Map<Key, Display> displayMap = new HashMap<Key, Display>();

  /**
   * Queue holds all keys, which are used by {@link #addDisplay(Display)} to
   * assign a key to the display.
   */
  private BlockingQueue<Key> keyQueue = new LinkedBlockingQueue<Key>();

  /**
   * Tasks to communicate with the clients.
   */
  private ClientTasks clientTasks = null;

  /**
   * Returns the singleton instance of the {@link DisplayPool}.
   *
   * @return The singleton instance of the {@link DisplayPool}.
   */
  public static DisplayPool getInstance() {
    if (singleton == null) {
      singleton = new DisplayPool();
    }

    return (singleton);
  }

  private DisplayPool() {
    try {
      this.clientTasks = createClientTasks();
    } catch (Exception e) {
      // TODO What you should do with the error?
      e.printStackTrace();
    }
  }

  /**
   * Assigns a new key to the {@link DisplayPool}.
   * <p>
   * When a new {@link Display} is {@link #addDisplay(Display) added} to the
   * {@link DisplayPool}, it receives the key. So it can be identified
   * later.
   *
   * @param key the new key
   */
  public void pushKey(Key key) {
    this.keyQueue.offer(key);
  }

  /**
   * Appends a {@link Display} to the {@link DisplayPool}.
   * <p>
   * The method fetches a {@link Key key} from the internal
   * {@link #pushKey(Key) key-list} and assigns it to the display. So later,
   * the display can be identified by the key.
   *
   * @param display The display to be appended
   * @throws NullPointerException if no key is available. You need to call
   *         {@link #pushKey(Key)} before!
   */
  public void addDisplay(Display display) throws NullPointerException {
    Key key = this.keyQueue.poll();

    if (key == null) {
      throw new NullPointerException("No key available");
    }

    display.setKey(key);
    this.displayMap.put(key, display);
  }

  /**
   * Retuns an instance of the {@link ClientTasks}.
   * <p>
   * The object can be used to communicate with the connected client.
   *
   * @return The {@link ClientTasks}-instance
   */
  public ClientTasks getClientTasks() {
    return (this.clientTasks);
  }

  private static ClientTasks createClientTasks() throws Exception {
    Class<? extends ClientTasks> c =
        Class.forName("de.robind.swt.ria.server.ClientTasksImpl")
          .asSubclass(ClientTasks.class);
    return (c.newInstance());
  }
}
