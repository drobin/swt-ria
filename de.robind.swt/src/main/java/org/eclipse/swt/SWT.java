package org.eclipse.swt;

import de.robind.swt.base.SWTBaseException;

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
   * A constant indicating that a given operation should be performed on all
   * widgets (value is 1<<0).
   */
  public static final int ALL = 1 << 0;

  /**
   * Style constant for arrow button behavior (value is 1<<2).
   */
  public static final int ARROW = 1 << 2;

  /**
   * Style constant for vertical alignment or orientation behavior
   * (value is 1).
   */
  public static final int BEGINNING = 1;

  /**
   * The font style constant indicating a bold weight font (value is 1<<0).
   */
  public static final int BOLD = 1 << 0;

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
   * A constant indicating that widgets have changed. (value is 1<<1).
   */
  public static final int CHANGED = 1 << 1;

  /**
   * Style constant for check box behavior (value is 1<<5).
   */
  public static final int CHECK = 1 << 5;

  /**
   * Style constant for close box trim (value is 1<<6, since we do not
   * distinguish between CLOSE style and MENU style).
   */
  public static final int CLOSE = 1 << 16;

  /**
   * Keyboard and/or mouse event mask indicating that the CTRL key was pushed
   * on the keyboard when the event was generated (value is 1<<18).
   */
  public static final int CTRL = 1 << 18;

  /**
   * Indicates that a default should be used (value is -1).
   */
  public static final int DEFAULT = -1;

  /**
   * A constant indicating that a given operation should be deferred.
   * (value is 1<<2).
   */
  public static final int DEFER = 1 << 2;

  /**
   * Style constant for align down behavior (value is 1<<10), since align
   * DOWN and align BOTTOM are considered the same).
   */
  public static final int DOWN = 1 << 10;

  /**
   * Indicates that a user-interface component is being dragged, for example
   * dragging the thumb of a scroll bar (value is 1).
   */
  public static final int DRAG = 1;

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
   * The dispose event type (value is 12).
   */
  public static final int Dispose = 12;

  /**
   * Style constant to indicate double buffering (value is 1<<29).
   */
  public static final int DOUBLE_BUFFERED = 1 << 29;

  /**
   * The drag detect event type (value is 29).
   */
  public static final int DragDetect = 29;

  /**
   * Style constant to allow embedding (value is 1<<24).
   */
  public static final int EMBEDDED = 1 << 24;

  /**
   * Keyboard event constant representing the END key (value is (1<<24)+8).
   */
  public static final int END = (1 << 24) + 8;

  /**
   * SWT error constant indicating that a value which can not be zero was
   * found to be (value is 7).
   */
  public static final int ERROR_CANNOT_BE_ZERO = 7;

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
   * SWT error constant indicating that an attempt was made to invoke an SWT
   * operation using a graphics object which had already been disposed (value
   * is 44).
   */
  public static final int ERROR_GRAPHIC_DISPOSED = 44;

  /**
   * SWT error constant indicating that an invalid argument was passed in
   * (value is 5).
   */
  public static final int ERROR_INVALID_ARGUMENT = 5;

  /**
   * SWT error constant indicating that a provided widget can not be used as a
   * parent in the current operation (value is 32).
   */
  public static final int ERROR_INVALID_PARENT = 32;

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
   * SWT error constant indicating that a particular feature has not been
   * implemented on this platform (value is 20).
   */
  public static final int ERROR_NOT_IMPLEMENTED = 20;

  /**
   * SWT error constant indicating that no more handles for an operating system
   * resource are available (value is 2).
   */
  public static final int ERROR_NO_HANDLES = 2;

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
   * Style constant for flat appearance. (value is 1<<23).
   * <p>
   * Note that this is a <i>HINT</i>.
   */
  public static final int FLAT = 1 << 23;

  /**
   * The focus in event type (value is 15).
   */
  public static final int FocusIn = 15;

  /**
   * The focus out event type (value is 16).
   */
  public static final int FocusOut = 16;

  /**
   * Style constant for full row selection behavior and selection constant
   * indicating that a full line should be drawn. (value is 1<<16).
   * <p>
   * Note that for some widgets this is a <i>HINT</i>.
   */
  public static final int FULL_SELECTION = 1 << 16;

  /**
   * The help event type (value is 28).
   */
  public static final int Help = 28;

  /**
   * Style constant for selection hiding behavior when the widget loses focus
   * (value is 1<<15).
   * <p>
   * Note that this is a <i>HINT</i>.
   */
  public static final int HIDE_SELECTION = 1 << 15;

  /**
   * Style constant for horizontal scrollbar behavior (value is 1<<8).
   */
  public static final int H_SCROLL = 1 << 8;

  /**
   * Style constant for horizontal alignment or orientation behavior
   * (value is 1<<8).
   */
  public static final int HORIZONTAL = 1 << 8;

  /**
   * The font style constant indicating an italic font (value is 1<<1).
   */
  public static final int ITALIC = 1 << 1;

  /**
   * Keyboard event constant representing the keypad location. (value is 1<<1).
   */
  public static final int KEYPAD = 1 << 1;

  /**
   * The key down event type (value is 1).
   */
  public static final int KeyDown = 1;

  /**
   * The key up event type (value is 2).
   */
  public static final int KeyUp = 2;

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
   * Style constant for left to right orientation (value is 1<<25).
   * <p>
   * When orientation is not explicitly specified, orientation is inherited.
   * This means that children will be assigned the orientation of their parent.
   * To override this behavior and force an orientation for a child, explicitl
   * set the orientation of the child when that child is created.
   * <p>
   * Note that this is a <i>HINT</i>.
   */
  public static final int LEFT_TO_RIGHT = 1 << 25;

  /**
   * Style constant for maximize box trim (value is 1<<10).
   */
  public static final int MAX = 1 << 10;

  /**
   * The menu detect event type (value is 35).
   */
  public static final int MenuDetect = 35;

  /**
   * Style constant for minimize box trim (value is 1<<7).
   */
  public static final int MIN = 1 << 7;

  /**
   * The mouse double click event type (value is 8).
   */
  public static final int MouseDoubleClick = 8;

  /**
   * The mouse down event type (value is 3).
   */
  public static final int MouseDown = 3;

  /**
   * The mouse enter event type (value is 6).
   */
  public static final int MouseEnter = 6;

  /**
   * The mouse exit event type (value is 7).
   */
  public static final int MouseExit = 7;

  /**
   * The mouse hover event type (value is 32).
   */
  public static final int MouseHover = 32;

  /**
   * The mouse move event type (value is 5).
   */
  public static final int MouseMove = 5;

  /**
   * The mouse up event type (value is 4).
   */
  public static final int MouseUp = 4;

  /**
   * The mouse vertical wheel event type (value is 37).
   */
  public static final int MouseVerticalWheel = 37;

  /**
   * The mouse wheel event type (value is 37). This is a synonym for
   * {@link SWT#MouseVerticalWheel} (value is 37). Newer applications should
   * use @link SWT#MouseVerticalWheel} instead of {@link SWT#MouseWheel} to
   * make code more understandable.
   */
  public static final int MouseWheel = 37;

  /**
   * The move event type (value is 10).
   */
  public static final int Move = 10;

  /**
   * Style constant for multi-selection behavior in lists and multiple line
   * support on text fields (value is 1<<1).
   */
  public static final int MULTI = 1 << 1;

  /**
   * The font style constant indicating a normal weight, non-italic font
   * (value is 0). This constant is also used with ProgressBar to indicate a
   * normal state.
   */
  public static final int NORMAL = 0;

  /**
   * Style constant for no background behavior (value is 1<<18).
   * <p>
   * By default, before a widget paints, the client area is filled with the
   * current background. When this style is specified, the background is not
   * filled, and the application is responsible for filling every pixel of the
   * client area. This style might be used as an alternative to
   * "double-buffering" in order to reduce flicker. This style does not mean
   * "transparent" - widgets that are obscured will not draw through.
   */
  public static final int NO_BACKGROUND = 1 << 18;

  /**
   * Style constant for no focus from the mouse behavior (value is 1<<19).
   * <p>
   * Normally, when the user clicks on a control, focus is assigned to that
   * control, providing the control has no children. Some controls, such as
   * tool bars and sashes, don't normally take focus when the mouse is clicked
   * or accept focus when assigned from within the program. This style allows
   * Composites to implement "no focus" mouse behavior.
   * <p>
   * Note that this is a <i>HINT</i>.
   */
  public static final int NO_FOCUS = 1 << 19;

  /**
   * Style constant for no paint event merging behavior (value is 1<<21).
   * <p>
   * Note that this is a <i>HINT</i>.
   */
  public static final int NO_MERGE_PAINTS = 1 << 21;

  /**
   * Style constant for preventing child radio group behavior (value is 1<<22).
   */
  public static final int NO_RADIO_GROUP = 1 << 22;

  /**
   * Style constant for no redraw on resize behavior (value is 1<<20).
   * <p>
   * This style stops the entire client area from being invalidated when the
   * size of the Canvas changes. Specifically, when the size of the Canvas gets
   * smaller, the SWT.Paint event is not sent. When it gets bigger, an
   * SWT.Paint event is sent with a GC clipped to only the new areas to be
   * painted. Without this style, the entire client area will be repainted.
   * <p>
   * Note that this is a <i>HINT</i>.
   */
  public static final int NO_REDRAW_RESIZE = 1 << 20;

  /**
   * Style constant for no scrollbar behavior (value is 1<<4).
   * <p>
   * When neither {@link #H_SCROLL} or {@link #V_SCROLL} are specified,
   * controls are free to create the default scroll bars for the control.
   * Using NO_SCROLL overrides the default and forces the control to have no
   * scroll bars.
   */
  public static final int NO_SCROLL = 1 << 4;

  /**
   * A constant known to be zero (0), typically used in operations which take
   * bit flags to indicate that "no bits are set".
   */
  public static final int NONE = 0;

  /**
   * The paint event type (value is 9).
   */
  public static final int Paint = 9;

  /**
   * Style constant for push button behavior (value is 1<<3).
   */
  public static final int PUSH = 1 << 3;

  /**
   * Style constant for radio button behavior (value is 1<<4).
   */
  public static final int RADIO = 1 << 4;

  /**
   * Style constant for read-only behavior (value is 1<<3).
   */
  public static final int READ_ONLY = 1 << 3;

  /**
   * The resize event type (value is 11).
   */
  public static final int Resize = 11;

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
   * Style constant for right to left orientation (value is 1<<26).
   * <p>
   * When orientation is not explicitly specified, orientation is inherited.
   * This means that children will be assigned the orientation of their parent.
   * To override this behavior and force an orientation for a child, explicitly
   * set the orientation of the child when that child is created.
   * <p>
   * Note that this is a <i>HINT</i>.
   */
  public static final int RIGHT_TO_LEFT = 1 << 26;

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
   * Style constant for single selection behavior in lists and single line
   * support on text fields (value is 1<<2).
   */
  public static final int SINGLE = 1 << 2;

  /**
   * Key value for setting and getting the skin class of a widget.
   */
  public static final String SKIN_CLASS = "org.eclipse.swt.skin.class";

  /**
   * Key value for setting and getting the skin id of a widget.
   */
  public static final String SKIN_ID = "org.eclipse.swt.skin.id";

  /**
   * Style constant for smooth appearance. (value is 1<<16).
   * <p>
   * Note that this is a <i>HINT</i>.
   */
  public static final int SMOOTH = 1 << 16;

  /**
   * Style constant for toggle button behavior (value is 1<<1).
   */
  public static final int TOGGLE =  1 << 1;

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
   * The traverse event type (value is 31).
   */
  public static final int Traverse = 31;

  /**
   * Traversal event detail field value indicating that the key which
   * designates that focus should be given to the previous tab item was
   * pressed; typically, this is either the RIGHT-ARROW or DOWN-ARROW keys
   * (value is 1<<6).
   */
  public static final int TRAVERSE_ARROW_NEXT = 1 << 6;

  /**
   * Traversal event detail field value indicating that the key which
   * designates that focus should be given to the previous tab item was
   * pressed; typically, this is either the LEFT-ARROW or UP-ARROW keys
   * (value is 1<<5).
   */
  public static final int TRAVERSE_ARROW_PREVIOUS = 1 << 5;

  /**
   * Traversal event detail field value indicating that the key which
   * designates that a dialog should be cancelled was pressed; typically, this
   * is the ESC key (value is 1<<1).
   */
  public static final int TRAVERSE_ESCAPE = 1 << 1;

  /**
   * Traversal event detail field value indicating that a mnemonic key sequence
   * was pressed (value is 1<<7).
   */
  public static final int TRAVERSE_MNEMONIC = 1 << 7;

  /**
   * Traversal event detail field value indicating that no traversal action
   * should be taken (value is 0).
   */
  public static final int TRAVERSE_NONE = 0;

  /**
   * Traversal event detail field value indicating that the key which
   * designates that the next page of a multi-page window should be shown was
   * pressed; typically, this is the CTRL-PAGEDOWN key sequence
   * (value is 1<<9).
   */
  public static final int TRAVERSE_PAGE_NEXT = 1 << 9;

  /**
   * Traversal event detail field value indicating that the key which
   * designates that the previous page of a multi-page window should be shown
   * was pressed; typically, this is the CTRL-PAGEUP key sequence
   * (value is 1<<8).
   */
  public static final int TRAVERSE_PAGE_PREVIOUS = 1 << 8;

  /**
   * Traversal event detail field value indicating that the key which activates
   * the default button in a dialog was pressed; typically, this is the ENTER
   * key (value is 1<<2).
   */
  public static final int TRAVERSE_RETURN = 1 << 2;

  /**
   * Traversal event detail field value indicating that the key which
   * designates that focus should be given to the next tab group was pressed;
   * typically, this is the TAB key (value is 1<<4).
   */
  public static final int TRAVERSE_TAB_NEXT = 1 << 4;

  /**
   * Traversal event detail field value indicating that the key which
   * designates that focus should be given to the previous tab group was
   * pressed; typically, this is the SHIFT-TAB key sequence (value is 1<<3).
   */
  public static final int TRAVERSE_TAB_PREVIOUS = 1 << 3;

  /**
   * Style constant for align up behavior (value is 1<<7, since align UP and
   * align TOP are considered the same).
   */
  public static final int UP = TOP;

  /**
   * Style constant for vertical alignment or orientation behavior
   * (value is 1<<9).
   */
  public static final int VERTICAL = 1 << 9;

  /**
   * Style constant to allow virtual data (value is 1<<28).
   */
  public static final int VIRTUAL = 1 << 28;

  /**
   * Style constant for vertical scrollbar behavior (value is 1<<9).
   */
  public static final int V_SCROLL = 1 << 9;

  /**
   * Style constant for automatic line wrap behavior (value is 1<<6).
   */
  public static final int WRAP = 1 << 6;

  /**
   * Creates a string-representation of the given error-code.
   *
   * @param code the requested error-code
   * @return The relatated string
   * @throws SWTError
   */
  static String errorToString(int code) throws SWTError {
    switch (code) {
      case SWT.ERROR_CANNOT_BE_ZERO: return ("CANNOT_BE_ZERO");
      case SWT.ERROR_DEVICE_DISPOSED: return ("DEVICE_DISPOSED");
      case SWT.ERROR_GRAPHIC_DISPOSED: return ("GRAPHIC_DISPOSED");
      case SWT.ERROR_FAILED_EXEC: return ("FAILED_EXEC");
      case SWT.ERROR_INVALID_ARGUMENT: return ("INVALID_ARGUMENT");
      case SWT.ERROR_INVALID_MESSAGE: return ("INVALID_MESSAGE");
      case SWT.ERROR_INVALID_PARENT: return ("INVALID_PARENT");
      case SWT.ERROR_INVALID_SUBCLASS: return ("INVALID_SUBCLASS");
      case SWT.ERROR_NO_HANDLES: return ("NO_HANDLES");
      case SWT.ERROR_NOT_IMPLEMENTED: return ("NOT_IMPLEMENTED");
      case SWT.ERROR_NULL_ARGUMENT: return ("NULL_ARGUMENT");
      case SWT.ERROR_THREAD_INVALID_ACCESS: return ("THREAD_INVALID_ACCESS");
      case SWT.ERROR_UNSPECIFIED: return ("UNSPECIFIED");
      case SWT.ERROR_WIDGET_DISPOSED: return ("WIDGET_DISPOSED");
    }

    throw new SWTError(SWT.ERROR_INVALID_ARGUMENT,
        "Unknown error-code ( " + code + ")");
  }

  /**
   * Throws a new {@link SWTException} by wrapping the given
   * {@link SWTBaseException} into the {@link SWTException}.
   *
   * @param cause The cause of the exception
   * @throws SWTError Failed to create the exception
   */
  public static SWTException exception(SWTBaseException cause)
      throws SWTError {

    if (cause == null) {
      throw new SWTError(SWT.ERROR_NULL_ARGUMENT);
    }

    // Map Reason to SWT-error-code
    int code;
    switch (cause.getReason()) {
      case ClientTasks: code = SWT.ERROR_FAILED_EXEC; break;
      case AppServer:   code = SWT.ERROR_FAILED_EXEC; break;
      case FailedExec:  code = SWT.ERROR_FAILED_EXEC; break;
      case Unknown:     code = SWT.ERROR_UNSPECIFIED; break;
      default: throw new SWTError(SWT.ERROR_INVALID_ARGUMENT,
          "Cannot map " + cause.getReason() + " into a SWT-errorcode");
    }

    SWTException e = new SWTException(code);
    e.throwable = cause;

    return (e);
  }
}
