package de.robind.swt.msg;

/**
 * Message requests the invocation of a mathod.
 * <p>
 * The object is identified by the {@link #getObjId() id}. The
 * {@link #getMethod()}-method is invoked with the given
 * {@link #getArguments() arguments}.
 *
 * @author Robin Doer
 */
public interface SWTCallRequest extends SWTRequest {
  /**
   * Returns the id of the object, where the {@link #getMethod() method} should
   * be invoked.
   *
   * @return Object-id of destination object
   */
  int getObjId();

  /**
   * Returns the method-name which should be invoked on the
   * {@link #getDestinationObject() object}.
   *
   * @return The destination method-name
   */
  String getMethod();

  /**
   * Returns the arguments which should be passed to the
   * {@link #getMethod() destination method}.
   *
   * @return Arguments for the method
   */
  Object[] getArguments();
}
