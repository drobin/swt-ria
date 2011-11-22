package de.robind.swt.msg;


/**
 * The request-message for the {@link SWTOpAttr}-message.
 *
 * @author Robin Doer
 */
public class SWTAttrRequest implements SWTOpAttr, SWTRequest {
  private SWTObjectId objId = null;
  private String name = null;
  private Object value = null;

  /**
   * Creates a new {@link SWTAttrRequest}-instance
   *
   * @param objId The object-if of the object to update
   * @param name Name of attribute to set
   * @param value The new value, which can be <code>null</code>
   * @throws NullPointerException if <code>objId</code> or <code>name</code>
   *         are <code>null</code>.
   * @throws IllegalArgumentException if <code>name</code> is empty
   */
  public SWTAttrRequest(SWTObjectId objId, String name, Object value) throws NullPointerException {
    if (objId == null) {
      throw new NullPointerException("objId cannot be null");
    }

    if (name == null) {
      throw new NullPointerException("name cannot be null");
    }

    if (name.length() == 0) {
      throw new IllegalArgumentException("name cannot be empty");
    }

    this.objId = objId;
    this.name = name;
    this.value = value;
  }

  /**
   * Returns the object, where the operation should by applied.
   *
   * @return The id of the destination object
   */
  public SWTObjectId getDestinationObject() {
    return (this.objId);
  }

  /**
   * Returns the name of the attribute to be changed.
   *
   * @return Name of attribute
   */
  public String getName() {
    return (this.name);
  }

  /**
   * Returns the new value of the {@link #getName() attribute}.
   *
   * @return The new value, which can be <code>null</code>.
   */
  public Object getValue() {
    return (this.value);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return (getClass().getSimpleName() + "(" + getDestinationObject() + ", " +
        getName() + "," + getValue() + ")");
  }
}
