package org.eclipse.swt.events;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;

/**
 * Instances of this class are sent as a result of widget traversal actions.
 * <p>
 * The traversal event allows fine control over keyboard traversal in a
 * control both to implement traversal and override the default traversal
 * behavior defined by the system. This is achieved using two fields,
 * {@link #detail} and {@link KeyEvent#doit}.
 * <p>
 * When a control is traversed, a traverse event is sent. The detail describes
 * the type of traversal and the {@link KeyEvent#doit} field indicates the
 * default behavior of the system. For example, when a right arrow key is
 * pressed in a text control, the detail field is
 * {@link SWT#TRAVERSE_ARROW_NEXT} and the {@link KeyEvent#doit} field is
 * <code>false</code>, indicating that the system will not traverse to the next
 * tab item and the arrow key will be delivered to the text control. If the
 * same key is pressed in a radio button, the {@link KeyEvent#doit} field will
 * be <code>true</code>, indicating that traversal is to proceed to the next
 * tab item, possibly another radio button in the group and that the arrow key
 * is not to be delivered to the radio button.
 * <p>
 * How can the traversal event be used to implement traversal? When a tab key
 * is pressed in a canvas, the {@link #detail} field will be
 * {@link SWT#TRAVERSE_TAB_NEXT} and the {@link KeyEvent#doit} field will be
 * <code>false</code>. The default behavior of the system is to provide no
 * traversal for canvas controls. This means that by default in a canvas, a
 * key listener will see every key that the user types, including traversal
 * keys. To understand why this is so, it is important to understand that only
 * the widget implementor can decide which traversal is appropriate for the
 * widget. Returning to the {@link SWT#TRAVERSE_TAB_NEXT} example, a text
 * widget implemented by a canvas would typically want to use the tab key to
 * insert a tab character into the widget. A list widget implementation, on the
 * other hand, would like the system default traversal behavior. Using only the
 * {@link KeyEvent#doit} flag, both implementations are possible. The text
 * widget implementor sets {@link KeyEvent#doit} to <code>false</code>,
 * ensuring that the system will not traverse and that the tab key will be
 * delivered to key listeners. The list widget implementor sets
 * {@link KeyEvent#doit} to <code>true</code>, indicating that the system
 * should perform tab traversal and that the key should not be delivered to the
 * list widget.
 * <p>
 * How can the traversal event be used to override system traversal? When the
 * return key is pressed in a single line text control, the detail field is
 * {@link SWT#TRAVERSE_NONE} and the {@link KeyEvent#doit} field is
 * <code>true</code>. This means that the return key will be processed by the
 * default button, not the text widget. If the text widget has a default
 * selection listener, it will not run because the return key will be processed
 * by the default button. Imagine that the text control is being used as an
 * in-place editor and return is used to dispose the widget. Setting
 * {@link KeyEvent#doit} to <code>false</code> will stop the system from
 * activating the default button but the key will be delivered to the text
 * control, running the key and selection listeners for the text. How can
 * {@link SWT#TRAVERSE_RETURN} be implemented so that the default button will
 * not be activated and the text widget will not see the return key? This is
 * achieved by setting {@link KeyEvent#doit} to <code>true</code>, and the
 * detail to {@link SWT#TRAVERSE_NONE}.
 * <p>
 * Note: A widget implementor will typically implement traversal using only the
 * {@link KeyEvent#doit} flag to either enable or disable system traversal.
 */
public class TraverseEvent extends KeyEvent {
  private static final long serialVersionUID = -2915385154042522303L;

  /**
   * The traversal type.
   *
   * <ul>
   *  <li>{@link SWT#TRAVERSE_NONE}</li>
   *  <li>{@link SWT#TRAVERSE_ESCAPE}</li>
   *  <li>{@link SWT#TRAVERSE_RETURN}</li>
   *  <li>{@link SWT#TRAVERSE_TAB_NEXT}</li>
   *  <li>{@link SWT#TRAVERSE_TAB_PREVIOUS}</li>
   *  <li>{@link SWT#TRAVERSE_ARROW_NEXT}</li>
   *  <li>{@link SWT#TRAVERSE_ARROW_PREVIOUS}</li>
   *  <li>{@link SWT#TRAVERSE_MNEMONIC}</li>
   *  <li>{@link SWT#TRAVERSE_PAGE_NEXT}</li>
   *  <li>{@link SWT#TRAVERSE_PAGE_PREVIOUS}</li>
   * </ul>
   *
   * Setting this field will change the type of traversal. For example, setting
   * the detail to {@link SWT#TRAVERSE_NONE} causes no traversal action to be
   * taken. When used in conjunction with the {@link KeyEvent#doit} field, the
   * traversal detail field can be useful when overriding the default traversal
   * mechanism for a control. For example, setting the {@link KeyEvent#doit}
   * field to <code>false</code> will cancel the operation and allow the
   * traversal key stroke to be delivered to the control. Setting the
   * {@link KeyEvent#doit} field to <code>true</code> indicates that the
   * traversal described by the {@link #detail} field is to be performed.
   */
  public int detail;

  /**
   * Constructs a new instance of this class based on the information in the
   * given untyped event.
   *
   * @param e
   */
  public TraverseEvent(Event e) {
    super(e);

    this.detail = e.detail;
  }
}
