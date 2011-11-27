package org.eclipse.swt;

/**
 * This class provides access to a small number of SWT system-wide methods,
 * and in addition defines the public constants provided by SWT.
 * <p>
 * By defining constants like UP and DOWN in a single class, SWT can share
 * common names and concepts at the same time minimizing the number of classes,
 * names and constants for the application programmer.
 * <p>
 * Note that some of the constants provided by this class represent optional,
 * appearance related aspects of widgets which are available either only on
 * some window systems, or for a differing set of widgets on each window
 * system. These constants are marked as HINTs. The set of widgets which
 * support a particular HINT may change from release to release, although we
 * typically will not withdraw support for a HINT once it is made available.
 */
public class SWT {
  /**
   * Style constant for vertical alignment or orientation behavior
   * (value is 1).
   */
  public static final int BEGINNING = 1;

  /**
   * Style constant for bordered behavior (value is 1<<11).
   * <p>
   * Note that this is a <i>HINT.</i>
   */
  public static final int BORDER = 1 << 11;

  /**
   * Style constant for align bottom behavior (value is 1<<10, since align
   * DOWN and align BOTTOM are considered the same).
   */
  public static final int BOTTOM = 1 << 10;

  /**
   * Style constant for align center behavior (value is 1<<24).
   */
  public static final int CENTER = 1 << 24;

  /**
   * Style constant for close box trim (value is 1<<6, since we do not
   * distinguish between CLOSE style and MENU style).
   */
  public static final int CLOSE = 1 << 16;

  /**
   * Indicates that a default should be used (value is -1).
   */
  public static final int DEFAULT = -1;

  /**
   * Style constant for align down behavior (value is 1<<10), since align
   * DOWN and align BOTTOM are considered the same).
   */
  public static final int DOWN = 1 << 10;

  /**
   * Style constant for vertical alignment or orientation behavior
   * (value is 4).
   */
  public static final int FILL = 4;

  /**
   * Style constant for title area trim (value is 1<<5).
   */
  public static final int TITLE = 1 << 5;

  /**
   * The default selection event type (value is 14).
   */
  public static final int DefaultSelection = 14;

  /**
   * Trim style convenience constant for the most common dialog shell
   * appearance (value is CLOSE|TITLE|BORDER).
   */
  public static final int DIALOG_TRIM = CLOSE|TITLE|BORDER;

  /**
   * Keyboard event constant representing the END key (value is (1<<24)+8).
   */
  public static final int END = (1 << 24) + 8;

  /**
   * SWT error constant indicating that an attempt was made to invoke an SWT
   * operation using a device which had already been disposed (value is 45).
   */
  public static final int ERROR_DEVICE_DISPOSED = 45;

  /**
   * SWT error constant indicating that an exception happened when executing a
   * runnable (value is 46).
   */
  public static final int ERROR_FAILED_EXEC = 46;

  /**
   * SWT error constant indicating that an invalid argument was passed in
   * (value is 5).
   */
  public static final int ERROR_INVALID_ARGUMENT = 5;

  /**
   * SWT error constant indicating that an attempt was made to subclass an SWT
   * widget class without implementing the checkSubclass() method
   * (value is 43). For additional information see the comment in
   * Widget.checkSubclass().
   */
  public static final int ERROR_INVALID_SUBCLASS = 43;

  /**
   * SWT errpr constant indicating that a message was generated/received, which
   * does not fit the protocol-standard.
   */
  public static final int ERROR_INVALID_MESSAGE = 99;

  /**
   * SWT error constant indicating that a null argument was passed in
   * (value is 4).
   */
  public static final int ERROR_NULL_ARGUMENT = 4;

  /**
   * SWT error constant indicating that an attempt was made to invoke an SWT
   * operation which can only be executed by the user-interface thread from
   * some other thread (value is 22).
   */
  public static final int ERROR_THREAD_INVALID_ACCESS = 22;

  /**
   * SWT error constant indicating that no error number was specified
   * (value is 1).
   */
  public static final int ERROR_UNSPECIFIED = 1;

  /**
   * SWT error constant indicating that an attempt was made to invoke an SWT
   * operation using a widget which had already been disposed (value is 24).
   */
  public static final int ERROR_WIDGET_DISPOSED = 24;

  /**
   * Style constant for horizontal alignment or orientation behavior
   * (value is 1<<8).
   */
  public static final int HORIZONTAL = 1 << 8;

  /**
   * Keyboard event constant representing the keypad location. (value is 1<<1).
   */
  public static final int KEYPAD = 1 << 1;

  /**
   * Style constant for leading alignment (value is 1<<14).
   */
  public static final int LEAD = 1 << 14;

  /**
   * Style constant for align left behavior (value is 1<<14). This is a synonym
   * for {@link #LEAD} (value is 1<<14). Newer applications should use
   * {@link #LEAD} instead of LEFT to make code more understandable on
   * right-to-left platforms.
   */
  public static final int LEFT = 1 << 14;

  /**
   * Style constant for maximize box trim (value is 1<<10).
   */
  public static final int MAX = 1 << 10;

  /**
   * Style constant for minimize box trim (value is 1<<7).
   */
  public static final int MIN = 1 << 7;

  /**
   * A constant known to be zero (0), typically used in operations which take
   * bit flags to indicate that "no bits are set".
   */
  public static final int NONE = 0;

  /**
   * Style constant for push button behavior (value is 1<<3).
   */
  public static final int PUSH = 1 << 3;

  /**
   * Style constant for read-only behavior (value is 1<<3).
   */
  public static final int READ_ONLY = 1 << 3;

  /**
   * Style constant for resize box trim (value is 1<<4).
   */
  public static final int RESIZE = 1 << 4;

  /**
   * Style constant for align right behavior (value is 1<<17). This is a
   * synonym for {@link #TRAIL} (value is 1<<17). Newer applications should use
   * {@link #TRAIL} instead of RIGHT to make code more understandable on
   * right-to-left platforms.
   * <p>
   * This constant can also be used to representing the right keyboard locatio
   *  during a key event.
   */
  public static final int RIGHT = 1 << 17;

  /**
   * The selection event type (value is 13).
   */
  public static final int Selection = 13;

  /**
   * Style constant for line separator behavior (value is 1<<1).
   */
  public static final int SEPARATOR = 1 << 1;

  /**
   * Style constant for shadow in behavior (value is 1<<2).
   * Note that this is a <i>HINT</i>.
   */
  public static final int SHADOW_IN = 1 << 2;

  /**
   * Style constant for shadow out behavior (value is 1<<3).
   * Note that this is a <i>HINT</i>.
   */
  public static final int SHADOW_OUT = 1 << 3;

  /**
   * Style constant for no shadow behavior (value is 1<<5).
   * Note that this is a <i>HINT</i>.
   */
  public static final int SHADOW_NONE = 1 << 5;

  /**
   * Trim style convenience constant for the most common top level shell
   * appearance (value is CLOSE|TITLE|MIN|MAX|RESIZE).
   */
  public static final int SHELL_TRIM = CLOSE|TITLE|MIN|MAX|RESIZE;

  /**
   * Style constant for align top behavior (value is 1<<7), since align UP and
   * align TOP are considered the same).
   */
  public static final int TOP = 1 << 7;

  /**
   * Style constant for trailing alignment (value is 1<<17).
   */
  public static final int TRAIL = 1 << 17;

  /**
   * Style constant for vertical alignment or orientation behavior
   * (value is 1<<9).
   */
  public static final int VERTICAL = 1 << 9;

  /**
   * Style constant for automatic line wrap behavior (value is 1<<6).
   */
  public static final int WRAP = 1 << 6;
}