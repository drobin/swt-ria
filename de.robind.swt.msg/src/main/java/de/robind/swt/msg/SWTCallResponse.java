package de.robind.swt.msg;

/**
 * A message for the {@link SWTProtocol#OP_CALL}-operation of type
 * {@link SWTProtocol#TYPE_RSP}.
 *
 * @author Robin Doer
 */
public interface SWTCallResponse extends SWTResponse {
  /**
   * Returns the result of the method-invocation.
   * <p>
   * If <code>null</code> is returned, the method has a <code>void</code>
   * return-value. If the result is an instance of {@link Throwable}, then
   * the method has thrown an exception. Any other result depends on the
   * method.
   *
   * @return The result of the method-invocation.
   */
  Object getResult();
}
