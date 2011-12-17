package org.eclipse.swt.events;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;

/**
 * Instances of this class are sent as a result of keys being pressed and
 * released on the keyboard.
 * <p>
 * When a key listener is added to a control, the control will take part in
 * widget traversal. By default, all traversal keys (such as the tab key and
 * so on) are delivered to the control. In order for a control to take part in
 * traversal, it should listen for traversal events. Otherwise, the user can
 * traverse into a control but not out. Note that native controls such as
 * table and tree implement key traversal in the operating system. It is not
 * necessary to add traversal listeners for these controls, unless you want to
 * override the default traversal.
 */
public class KeyEvent extends TypedEvent {
  private static final long serialVersionUID = 3998554930283084373L;

  /**
   * the character represented by the key that was typed. This is the final
   * character that results after all modifiers have been applied. For example,
   * when the user types Ctrl+A, the character value is 0x01. It is important
   * that applications do not attempt to modify the character value based on a
   * stateMask (such as {@link SWT#CTRL}) or the resulting character will not
   * be correct.
   */
  public char character;

  /**
   * A flag indicating whether the operation should be allowed. Setting this
   * field to <code>false</code> will cancel the operation.
   */
  public boolean doit;

  /**
   * the key code of the key that was typed, as defined by the key code
   * constants in class SWT. When the character field of the event is
   * ambiguous, this field contains the unicode value of the original
   * character. For example, typing Ctrl+M or Return both result in the
   * character '\r' but the keyCode field will also contain '\r' when Return
   * was typed.
   */
  public int keyCode;

  /**
   * depending on the event, the location of key specified by the keyCode or
   * character. The possible values for this field are {@link SWT#LEFT},
   * {@link SWT#RIGHT}, {@link SWT#KEYPAD}, or {@link SWT#NONE} representing
   * the main keyboard area.
   */
  public int keyLocation;

  /**
   * the state of the keyboard modifier keys and mouse masks at the time the
   * event was generated.
   */
  public int stateMask;

  public KeyEvent(Event e) {
    super(e);

    this.character = e.character;
    this.doit = e.doit;
    this.keyCode = e.keyCode;
    this.keyLocation = e.keyLocation;
    this.stateMask = e.stateMask;
  }
}
