package de.robind.swt.msg;

/**
 * Requests the update of an attribute.
 * <p>
 * The attribute is located at the object with the given {@link #getObjId() id},
 * has a {@link #getAttrName() name}. The new value can be fetched by calling
 * {@link #getAttrValue()}.
 *
 * @author Robin Doer
 */
public interface SWTAttrRequest extends SWTRequest {
  /**
   * Returns the id of the object, where the attribute should be updated.
   *
   * @return The id of the destination-object
   */
  int getObjId();

  /**
   * Returns the name of the attribute, which should be updated.
   *
   * @return The name of the attribute to be updated
   */
  String getAttrName();

  /**
   * Returns the new value of the attribute.
   *
   * @return The new value
   */
  Object getAttrValue();
}
