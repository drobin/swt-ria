package de.robind.swt.msg;

/**
 * A message for the {@link SWTProtocol#OP_CALL}-operation of type
 * {@link SWTProtocol#TYPE_RSP}.
 *
 * @author Robin Doer
 */
public class SWTCallResponse implements SWTResponse {
  private Object result = null;

  private SWTCallResponse() {
  }

  /**
   * Returns a new {@link SWTCallResponse}-instance
   *
   * @param result The result of the method-invocation
   * @throws NullPointerException if <code>result</code> is null
   */
  public SWTCallResponse(Object result) {
    if (result == null) {
      throw new NullPointerException("result cannot be null");
    }

    this.result = result;
  }

  /**
   * Returns a <code>void</code>-result.
   * <p>
   * The response-instance has no return-value and {@link #isVoid()} will
   * return <code>true</code>.
   *
   * @return A <code>void</code>-result
   */
  public static SWTCallResponse voidResult() {
    return (new SWTCallResponse());
  }

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
  public Object getResult() {
    return (this.result);
  }

  /**
   * Tests if the result is a void-operation.
   *
   * @return <code>true</code> if the method has no return-value.
   */
  public boolean isVoid() {
    return (this.result == null);
  }

  /**
   * Tests if the result contains an exception.
   * <p>
   * Then, the method has no return-value, it was aborted with an exception.
   *
   * @return <code>true</code> if the method-invocation was aborted with an
   *         exception.
   */
  public boolean isException() {
    return (this.result instanceof Throwable);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    if (isVoid()) {
      return (getClass().getSimpleName() + "(void)");
    } else {
      return (getClass().getSimpleName() + "(" + getResult() + ")");
    }
  }
}
