package de.robind.swt.msg;

/**
 * Class identifies the id of an SWT-object.
 * <p>
 * You nees to distinguish a <i>normal</i> integer with the (integer) id of
 * an object. That's why you put a class around the object-id.
 *
 * @author Robin Doer
 */
public class SWTObjectId {
  /**
   * The id of an object.
   */
  private Integer id = null;

  /**
   * Creates an {@link SWTObjectId}-instance
   * @param id The id of the object
   */
  public SWTObjectId(int id) {
    this.id = id;
  }

  private SWTObjectId() {
    this.id = null;
  }

  /**
   * Creates an undefined {@link SWTObjectId}.
   * @return An undefined object-id
   */
  public static SWTObjectId undefined() {
    return (new SWTObjectId());
  }

  /**
   * Tests if this is a valid object-id.
   * <p>
   * It is invalid, if is was created by {@link #undefined()}.
   *
   * @return <code>true</code> is valid, <code>false</code> otherwise.
   */
  public boolean isValid() {
    return (this.id != null);
  }

  /**
   * Returns the if of the object.
   *
   * @return If of object
   * @throws UnsupportedOperationException if this object-id is
   *         {@link #isValid() invalid}.
   */
  public int getId() throws UnsupportedOperationException {
    if (!isValid()) {
      throw new UnsupportedOperationException(
          "Calling getId() on an invalid instance");
    }

    return (this.id);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    if (isValid()) {
      return (getClass().getSimpleName() + "(id=" + this.id + ")");
    } else {
      return (getClass().getSimpleName() + "(id=undefined)");
    }
  }
}
