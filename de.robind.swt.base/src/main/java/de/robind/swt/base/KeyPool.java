package de.robind.swt.base;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * The {@link KeyPool} maintains a list of {@link Key keys}.
 * <p>
 * You can push a key into the pool by calling {@link #offerKey(Key)}. Take
 * an key from the pool by calling {@link #takeKey()}.
 * <p>
 * It's designed as a singleton, use {@link #getInstance()} to receive the only
 * instance.
 *
 * @author Robin Doer
 */
public class KeyPool {
  /**
   * The singleton instance of the {@link KeyPool}.
   */
  private static KeyPool singleton = null;

  /**
   * Queue holds all keys maintained by this pool.
   */
  private BlockingQueue<Key> keyQueue = new LinkedBlockingQueue<Key>();

  /**
   * Returns the singleton instance of the {@link KeyPool}.
   *
   * @return The singleton instance of the {@link KeyPool}.
   */
  public static KeyPool getInstance() {
    if (singleton == null) {
      singleton = new KeyPool();
    }

    return (singleton);
  }

  /**
   * Assigns a new key to the {@link KeyPool}.
   *
   * @param key the key to assign
   */
  public void offerKey(Key key) {
    this.keyQueue.offer(key);
  }

  /**
   * Removes a key from the pool.
   * <p>
   * If no key is currently assigned, <code>null</code> is returned.
   *
   * @return A key assigned to the pool
   */
  public Key takeKey() {
    return (this.keyQueue.poll());
  }
}
