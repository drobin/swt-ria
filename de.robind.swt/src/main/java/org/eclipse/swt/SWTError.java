package org.eclipse.swt;

import de.robind.swt.base.SWTBaseException;


/**
 * This error is thrown whenever an unrecoverable error occurs internally in
 * SWT. The message text and error code provide a further description of the
 * problem. The exception has a throwable field which holds the underlying
 * throwable that caused the problem (if this information is available (i.e.
 * it may be null)).
 * <p>
 * SWTErrors are thrown when something fails internally which either leaves
 * SWT in an unknown state (eg. the o/s call to remove an item from a list
 * returns an error code) or when SWT is left in a known-to-be-unrecoverable
 * state (eg. it runs out of callback resources). SWTErrors should not occur
 * in typical programs, although "high reliability" applications should still
 * catch them.
 * This class also provides support methods used by SWT to match error codes to
 * the appropriate exception class (SWTError, SWTException, or
 * IllegalArgumentException) and to provide human readable strings for SWT
 * error codes.
 */
public class SWTError extends Error {
  private static final long serialVersionUID = -9160311124292866538L;

  /**
   * The SWT error code, one of SWT.ERROR_*.
   */
  public int code;

  /**
   * The underlying throwable that caused the problem,
   * or <code>null</code> if this information is not available.
   */
  public Throwable throwable;

  /**
   * Constructs a new instance of this class with its
   * stack trace filled in. The error code is set to an
   * unspecified value.
   */
  public SWTError() {
    this(SWT.ERROR_UNSPECIFIED);
  }

  /**
   * Constructs a new instance of this class with its
   * stack trace and message filled in. The error code is
   * set to an unspecified value.  Specifying <code>null</code>
   * as the message is equivalent to specifying an empty string.
   *
   * @param message the detail message for the exception
   */
  public SWTError(String message) {
    this(SWT.ERROR_UNSPECIFIED, message);
  }

  /**
   * Constructs a new instance of this class with its
   * stack trace and error code filled in.
   *
   * @param code the SWT error code
   */
  public SWTError(int code) {
    // TODO Maybe you want to call "this (code, SWT.findErrorText(code))"
    this (code, "");
  }

  /**
   * Constructs a new instance of this class with its
   * stack trace, error code and message filled in.
   * Specifying <code>null</code> as the message is
   * equivalent to specifying an empty string.
   *
   * @param code the SWT error code
   * @param message the detail message for the exception
   */
  public SWTError(int code, String message) {
    super(message);
    this.code = code;
  }

  /**
   * Constructs a new instance which wraps the given {@link SWTBaseException}.
   *
   * @param cause The cause of the exception
   */
  public SWTError(SWTBaseException cause) {
    super();

    if (cause == null) {
      throw new SWTError(SWT.ERROR_NULL_ARGUMENT);
    }

    this.throwable = cause;

    // Map Reason to SWT-error-code
    switch (cause.getReason()) {
      case ClientTasks: this.code = SWT.ERROR_FAILED_EXEC; break;
      case AppServer:   this.code = SWT.ERROR_FAILED_EXEC; break;
      case FailedExec:  this.code = SWT.ERROR_FAILED_EXEC; break;
      case Unknown:     this.code = SWT.ERROR_UNSPECIFIED; break;
      default: throw new SWTError(SWT.ERROR_INVALID_ARGUMENT,
          "Cannot map " + cause.getReason() + " into a SWT-errorcode");
    }
  }

  /**
   * Returns the underlying throwable that caused the problem,
   * or null if this information is not available.
   * <p>
   * NOTE: This method overrides Throwable.getCause() that was
   * added to JDK1.4. It is necessary to override this method
   * in order for inherited printStackTrace() methods to work.
   *
   * @return the underlying throwable
   */
  @Override
  public Throwable getCause() {
    return (this.throwable);
  }

  /**
   * Returns the string describing this SWTError object.
   * <p>
   * It is combined with the message string of the Throwable
   * which caused this SWTError (if this information is available).
   *
   * @return the error message string of this SWTError object
   */
  @Override
  public String getMessage() {
    if (this.throwable == null) {
      return super.getMessage ();
    }

    return (super.getMessage () + " (" + throwable.toString () + ")");
  }
}
