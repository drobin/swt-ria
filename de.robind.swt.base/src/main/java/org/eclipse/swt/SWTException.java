package org.eclipse.swt;


/**
 * This runtime exception is thrown whenever a recoverable error occurs
 * internally in SWT. The message text and error code provide a further
 * description of the problem. The exception has a throwable field which holds
 * the underlying exception that caused the problem (if this information is
 * available (i.e. it may be null)).
 * <p>
 * SWTExceptions are thrown when something fails internally, but SWT is left in
 * a known stable state (eg. a widget call was made from a non-u/i thread, or
 * there is failure while reading an Image because the source file was
 * corrupt).
 */
public class SWTException extends RuntimeException {
  private static final long serialVersionUID = 4856216507632136584L;

  /**
   * The SWT error code, one of SWT.ERROR_*.
   */
  public int code;

  /**
   * The underlying throwable that caused the problem, or <code>null</code> if
   * this information is not available.
   */
  public Throwable throwable;

  /**
   * Constructs a new instance of this class with its stack trace filled in.
   * The error code is set to an unspecified value.
   */
  public SWTException() {
    this(SWT.ERROR_UNSPECIFIED);
  }

  /**
   * Constructs a new instance of this class with its stack trace and error
   * code filled in.
   *
   * @param code the SWT error code
   */
  public SWTException(int code) {
    // TODO Maybe you want to call "this (code, SWT.findErrorText(code))"
    this(code, "");
  }

  /**
   * Constructs a new instance of this class with its stack trace and message
   * filled in. The error code is set to an unspecified value. Specifying
   * <code>null</code> as the message is equivalent to specifying an empty
   * string.
   *
   * @param message the detail message for the exception
   */
  public SWTException(String message) {
    this (SWT.ERROR_UNSPECIFIED, message);
  }

  /**
   * Constructs a new instance of this class with its stack trace, error code
   * and message filled in. Specifying <code>null</code> as the message is
   * equivalent to specifying an empty string.
   *
   * @param code the SWT error code
   * @param message the detail message for the exception
   */
  public SWTException(int code, String message) {
    super(message);
    this.code = code;
  }

  /**
   * Returns the underlying throwable that caused the problem, or
   * <code>null</code> if this information is not available.
   * <p>
   * NOTE: This method overrides {@link Throwable#getCause()} that was added to
   * JDK1.4. It is necessary to override this method in order for inherited
   * {@link #printStackTrace()} methods to work.
   *
   * @return the underlying throwable
   */
  @Override
  public Throwable getCause() {
    return (this.throwable);
  }

  /* (non-Javadoc)
   * @see java.lang.Throwable#toString()
   */
  @Override
  public String toString() {
    String message = getMessage();

    if (message != null && message.length() > 0) {
      return getClass().getSimpleName() +
          "[code=" + SWT.errorToString(this.code) + "]: " + message;
    } else {
      return getClass().getSimpleName() +
          "[code=" + SWT.errorToString(this.code) + "]";
    }
  }
}
