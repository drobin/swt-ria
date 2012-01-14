package org.eclipse.swt.events;

/**
 * Classes which implement this interface provide methods that deal with the
 * events that are generated when selection occurs in a control.
 * <p>
 * After creating an instance of a class that implements this interface it can
 * be added to a control using the <code>addSelectionListener</code> method and
 * removed using the <code>removeSelectionListener</code> method. When
 * selection occurs in a control the appropriate method will be invoked.
 */
public interface SelectionListener extends TypedListener {
  /**
   * Sent when selection occurs in the control.
   * <p>
   * For example, selection occurs in a List when the user selects an item or
   * items with the keyboard or mouse. On some platforms, the event occurs when
   * a mouse button or key is pressed. On others, it happens when the mouse or
   * key is released. The exact key or mouse gesture that causes this event is
   * platform specific.
   *
   * @param e an event containing information about the selection
   */
  void widgetSelected(SelectionEvent e);

  /**
   * Sent when default selection occurs in the control.
   * <p>
   * For example, on some platforms default selection occurs in a List when the
   * user double-clicks an item or types return in a Text. On some platforms,
   * the event occurs when a mouse button or key is pressed. On others, it
   * happens when the mouse or key is released. The exact key or mouse gesture
   * that causes this event is platform specific.
   *
   * @param e an event containing information about the default selection
   */
  void widgetDefaultSelected(SelectionEvent e);
}
