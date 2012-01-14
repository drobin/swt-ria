package org.eclipse.swt.events;

import org.eclipse.swt.widgets.Control;

/**
 * Classes which implement this interface provide methods that deal with the
 * events that are generated when a drag gesture is detected.
 * <p>
 * After creating an instance of a class that implements this interface it can
 * be added to a control using the
 * {@link Control#addDragDetectListener(DragDetectListener)} method and removed
 * using the {@link Control#removeDragDetectListener(DragDetectListener)}
 * method. When the drag is detected, the
 * {@link #dragDetected(DragDetectEvent)} method will be invoked.
 */
public interface DragDetectListener extends TypedListener {
  /**
   * Sent when a drag gesture is detected.
   *
   * @param e an event containing information about the drag
   */
  void dragDetected(DragDetectEvent e);
}
