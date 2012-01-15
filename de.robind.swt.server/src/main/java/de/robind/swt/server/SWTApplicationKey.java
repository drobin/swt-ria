package de.robind.swt.server;

import org.eclipse.swt.server.Key;

/**
 * A {@link Key} for a {@link SWTApplication}.
 *
 * @author Robin Doer
 */
public class SWTApplicationKey implements Key {
  private static int keyCounter = 0;
  private int id = 0;
  private SWTApplication application = null;

  /**
   * Creates a new {@link SWTApplicationKey}-instance.
   *
   * @param application The application to be assigned to the key
   */
  public SWTApplicationKey(SWTApplication application) {
    this.id = keyCounter;
    this.application = application;

    keyCounter++;
  }

  /**
   * Returns the application assigned to the key.
   *
   * @return The application of the key
   */
  public SWTApplication getApplication() {
    return (this.application);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;

    result = prime * result + id;

    return (result);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return (true);
    }

    if (obj == null) {
      return (false);
    }

    if (getClass() != obj.getClass()) {
      return (false);
    }

    SWTApplicationKey other = (SWTApplicationKey)obj;
    if (this.id != other.id) {
      return (false);
    }

    return (true);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return getClass().getSimpleName() + " [id=" + this.id + "]";
  }
}
