package de.robind.swt.base;


/**
 * The {@link SWTObjectPool} maintains instances of {@link SWTObject}.
 * <p>
 * It's designed as a singleton, use {@link #getInstance()} to receive the only
 * instance.
 *
 * @author Robin Doer
 */
public class SWTObjectPool {
  /**
   * The singleton instance of the {@link SWTObjectPool}.
   */
  private static SWTObjectPool singleton = null;

  /**
   * Returns the singleton instance of the {@link SWTObjectPool}.
   *
   * @return The singleton instance of the {@link SWTObjectPool}.
   */
  public static SWTObjectPool getInstance() {
    if (singleton == null) {
      singleton = new SWTObjectPool();
    }

    return (singleton);
  }

  private SWTObjectPool() {
  }
}
