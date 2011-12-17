package org.eclipse.swt.events;

import org.eclipse.swt.widgets.Control;

/**
 * Classes which implement this interface provide methods that deal with the
 * events that are generated when the control needs to be painted.
 * <p>
 * After creating an instance of a class that implements this interface it can
 * be added to a control using the
 * {@link Control#addPaintListener(PaintListener)} method and removed using the
 * {@link Control#removePaintListener(PaintListener)} method. When a paint
 * event occurs, the {@link #paintControl(PaintEvent)} method will be invoked.
 */
public interface PaintListener extends TypedListener {
  /**
   * Sent when a paint event occurs for the control.
   *
   * @param e an event containing information about the paint
   */
  void paintControl(PaintEvent e);
}
