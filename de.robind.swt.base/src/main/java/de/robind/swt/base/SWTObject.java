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
   * The key assigned to the object.
   */
  private Key key = null;

  /**
   * Creates a new {@link SWTObject}-instance and assigns an unique
   * {@link #getId() id} to the instance.
   */
  protected SWTObject() {
    this.id = generateId();
    SWTObjectPool.getInstance().registerObject(this);
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
   * Returns the key, which is assigned (if any) to the instance.
   *
   * @return The assigned key
   */
  public Key getKey() {
    return (this.key);
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
