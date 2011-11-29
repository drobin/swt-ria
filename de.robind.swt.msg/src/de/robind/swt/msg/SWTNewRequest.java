package de.robind.swt.msg;


/**
 * A message requests the creation of an object.
 * <p>
 * The new object is created from the given {@link #getObjClass()} and has
 * the given {@link #getId() id}. The following
 * {@link #getArguments() arguments} are passed to the constructor of the
 * class.
 *
 * @author Robin Doer
 */
public interface SWTNewRequest extends SWTRequest {
  /**
   * Returns the id of the object.
   *
   * @return The object-id
   */
  int getId();

  /**
   * Returns the class to be instanciated.
   *
   * @return The class to be instanciated
   */
  Class<?> getObjClass();

  /**
   * Returns the arguments which are passed to the constructor.
   *
   * @return Arguments passed to the constructor
   */
  Object[] getArguments();
}
