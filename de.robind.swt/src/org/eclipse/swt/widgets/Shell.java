package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;

import de.robind.swt.msg.SWTCallRequest;
import de.robind.swt.msg.SWTMessageFactory;
import de.robind.swt.msg.SWTNewRequest;
import de.robind.swt.msg.SWTObjectId;

/**
 * TODO Needs to be implemented!!
 */
public class Shell extends Decorations {
  /**
   * Constructs a new instance of this class. This is equivalent to calling
   * <code>Shell((Display)null)</code>.
   *
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the parent
   *    </li>
   *    <li>{@link SWT#ERROR_INVALID_SUBCLASS} -
   *      if this class is not an allowed subclass
   *    </li>
   *  </ul>
   */
  public Shell() throws SWTException {
    this((Display)null);
  }

  /**
   * Constructs a new instance of this class given only the display to create
   * it on. It is created with style {@link SWT#SHELL_TRIM}.
   * <p>
   * Note: Currently, <code>null</code> can be passed in for the display
   * argument. This has the effect of creating the shell on the currently
   * active display if there is one. If there is no current display, the shell
   * is created on a "default" display. <b>Passing in <code>null</code> as the
   * display argument is not considered to be good coding style, and may not be
   * supported in a future release of SWT.</b>
   *
   * @param display the display to create the shell on
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the parent
   *    </li>
   *    <li>{@link SWT#ERROR_INVALID_SUBCLASS} -
   *      if this class is not an allowed subclass
   *    </li>
   *  </ul>
   */
  public Shell(Display display) throws SWTException {
    this (display, SWT.SHELL_TRIM);
  }

  /**
   * Constructs a new instance of this class given the display to create it on
   * and a style value describing its behavior and appearance.
   * <p>
   * The style value is either one of the style constants defined in class SWT
   * which is applicable to instances of this class, or must be built by
   * bitwise OR'ing together (that is, using the int "|" operator) two or more
   * of those {@link SWT} style constants. The class description lists the style
   * constants that are applicable to the class. Style bits are also inherited
   * from superclasses.
   * <p>
   * Note: Currently, <code>null</code> can be passed in for the display
   * argument. This has the effect of creating the shell on the currently
   * active display if there is one. If there is no current display, the
   * shell is created on a "default" display. <b>Passing in <code>null</code>
   * as the display argument is not considered to be good coding style, and may
   * not be supported in a future release of SWT.</b>
   *
   * @param display the display to create the shell on
   * @param style the style of control to construct
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the parent
   *    </li>
   *    <li>{@link SWT#ERROR_INVALID_SUBCLASS} -
   *      if this class is not an allowed subclass
   *    </li>
   *  </ul>
   */
  public Shell(Display display, int style) throws SWTException {
    super(null, style);
    setDisplay(display);

    // TODO Check for SWTException
  }

  /**
   * Constructs a new instance of this class given only the style value
   * describing its behavior and appearance. This is equivalent to calling
   * <code>Shell((Display)null, style)</code>.
   * <p>
   * The style value is either one of the style constants defined in class
   * {@link SWT} which is applicable to instances of this class, or must be
   * built by bitwise OR'ing together (that is, using the int "|" operator)
   * two or more of those SWT style constants. The class description lists the
   * style constants that are applicable to the class. Style bits are also
   * inherited from superclasses.
   *
   * @param style the style of control to construct
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the parent
   *    </li>
   *    <li>{@link SWT#ERROR_INVALID_SUBCLASS} -
   *      if this class is not an allowed subclass
   *    </li>
   *  </ul>
   */
  public Shell(int style) throws SWTException {
    this ((Display)null, style);
  }

  /**
   * Constructs a new instance of this class given only its parent. It is
   * created with style {@link SWT#DIALOG_TRIM}.
   * <p>
   * Note: Currently, <code>null</code> can be passed in for the parent. This
   * has the effect of creating the shell on the currently active display if
   * there is one. If there is no current display, the shell is created on a
   * "default" display. <b>Passing in <code>null</code> as the parent is not
   * considered to be good coding style, and may not be supported in a future
   * release of SWT.</b>
   *
   * @param parent a shell which will be the parent of the new instance
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_INVALID_ARGUMENT} -
   *      if the parent is disposed
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the parent
   *    </li>
   *    <li>{@link SWT#ERROR_INVALID_SUBCLASS} -
   *      if this class is not an allowed subclass
   *    </li>
   *  </ul>
   */
  public Shell(Shell parent) throws SWTException {
    this(parent, SWT.DIALOG_TRIM);
  }

  /**
   * Constructs a new instance of this class given its parent and a style value
   * describing its behavior and appearance.
   * <p>
   * The style value is either one of the style constants defined in class
   * {@link SWT} which is applicable to instances of this class, or must be
   * built by bitwise OR'ing together (that is, using the int "|" operator) two
   * or more of those SWT style constants. The class description lists the
   * style constants that are applicable to the class. Style bits are also
   * inherited from superclasses.
   * <p>
   * Note: Currently, <code>null</code> can be passed in for the parent. This
   * has the effect of creating the shell on the currently active display
   * if there is one. If there is no current display, the shell is created on
   * a "default" display. <b>Passing in <code>null</code> as the parent is not
   * considered to be good coding style, and may not be supported in a future
   * release of SWT.</b>
   *
   * @param parent a shell which will be the parent of the new instance
   * @param style the style of control to construct
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_INVALID_ARGUMENT} -
   *      if the parent is disposed
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the parent
   *    </li>
   *    <li>{@link SWT#ERROR_INVALID_SUBCLASS} -
   *      if this class is not an allowed subclass
   *    </li>
   *  </ul>
   */
  public Shell(Shell parent, int style) throws SWTException {
    super(parent, style);

    setDisplay(parent.getDisplay());
  }

  /**
   * Moves the receiver to the top of the drawing order for the display on
   * which it was created (so that all other shells on that display, which are
   * not the receiver's children will be drawn behind it), marks it visible,
   * sets the focus and asks the window manager to make the shell active.
   *
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_WIDGET_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *  </ul>
   *
   * TODO Needs to be implemented!!
   */
  public void open() throws SWTException {
    // TODO Evaluate the response
    Display display = getDisplay();
    SWTMessageFactory factory = display.getMessageFactory();
    SWTCallRequest request = factory.createCallRequest(getId(), "open");
    display.sendMessage(request);
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.widgets.Widget#createNewRequest()
   */
  @Override
  protected SWTNewRequest createNewRequest() {
    // TODO Pass parent Display to ctor
    SWTObjectId displayId =
        getDisplay() != null ? getDisplay().getId() : SWTObjectId.undefined();
    return (new SWTNewRequest(getId().getId(), Shell.class, displayId));
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.widgets.Widget#canHaveParent()
   */
  @Override
  boolean canHaveParent() {
    return (false);
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.widgets.Widget#canDispose()
   */
  @Override
  boolean canDispose() {
    return (false);
  }
}
