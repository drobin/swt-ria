package de.robind.swt.msg;

/**
 * A message for the {@link SWTProtocol#OP_NEW}-operation of type
 * {@link SWTProtocol#TYPE_RSP}.
 *
 * @author Robin Doer
 */
public class SWTNewResponse implements SWTOpNew, SWTResponse {
  private String excClass = null;
  private String excMessage = null;

  private SWTNewResponse() {
  }

  /**
   * Creates a failure response-message.
   *
   * @param excClassName The name of the thrown exception-class
   * @param excMessage The message if the thrown exception
   * @throws NullPointerException if <code>excClassName</code> or
   *         <code>excMessage</code> are <code>null</code>
   * @throws IllegalArgumentException if <code>excClassName</code> is empty
   */
  public SWTNewResponse(String excClassName, String excMessage)
      throws NullPointerException {

    if (excClassName == null) {
      throw new NullPointerException("excClassName cannot be null");
    }

    if (excMessage == null) {
      throw new NullPointerException("excMessage cannot be null");
    }

    if (excClassName.length() == 0) {
      throw new IllegalArgumentException("excClassName cannot be empty");
    }

    this.excClass = excClassName;
    this.excMessage = excMessage;
  }

  /**
   * Creates a successful response-message.
   * <p>
   * No exception is assigned, {@link #isSuccessful()} will return
   * <code>true</code>.
   *
   * @return A successful response-message
   */
  public static SWTNewResponse success() {
    return (new SWTNewResponse());
  }

  /**
   * Tests whether this is a successful response.
   * <p>
   * The object was successfully created.
   *
   * @return On success <code>true</code> is returned,
   *         <code>false</code> otherwise.
   */
  public boolean isSuccessful() {
    return (this.excClass == null);
  }

  /**
   * Returns the class-name of the thrown exception.
   *
   * @return Class-name of thrown exception. If this is a successful response
   *         <code>null</code> is returned.
   */
  public String getExceptionClass() {
    return (this.excClass);
  }

  /**
   * Returns the message of the thrown exception.
   *
   * @return Message of thrown exception. If this is a successful response
   *         <code>null</code> is returned.
   */
  public String getExceptionMessage() {
    return (this.excMessage);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    if (isSuccessful()) {
      return (getClass().getSimpleName() + "(success)");
    } else {
      return (getClass().getSimpleName() + "(" + getExceptionClass() + "," +
          getExceptionMessage() + ")");
    }
  }
}
