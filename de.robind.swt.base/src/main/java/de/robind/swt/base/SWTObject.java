package de.robind.swt.base;


/**
 * The base-class for all SWT-objects.
 * <p>
 * Each object is identified by an {@link #getId() id}. The identifier is
 * unique over all applications.
 *
 * @author Robin Doer
 */
public class SWTObject {
  /**
   * Next free identifier used by {@link #generateId()}.
   */
  private static int nextId = 0;

  /**
   * The id of the object.
   */
  private int id;

  /**
   * Creates a new {@link SWTObject}-instance and assigns an unique
   * {@link #getId() id} to the instance.
   */
  protected SWTObject() {
    this.id = generateId();
  }

  /**
   * Returns the (unique) id of the object.
   *
   * @return The id of the object
   */
  public int getId() {
    return (this.id);
  }

  /**
   * Generates a unique identifier.
   *
   * @return A unique identifier
   */
  private int generateId() {
    return (++nextId);
  }
}
