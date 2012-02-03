package de.robind.swt.base;

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
}
