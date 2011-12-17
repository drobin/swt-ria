package org.eclipse.swt.events;

import org.eclipse.swt.widgets.Control;

/**
 * Classes which implement this interface provide methods that deal with the
 * events that are generated as the mouse pointer passes (or hovers) over
 * controls.
 * <p>
 * After creating an instance of a class that implements this interface it can
 * be added to a control using the
 * {@link Control#addMouseTrackListener(MouseTrackListener)} method and removed
 * using the {@link Control#removeMouseTrackListener(MouseTrackListener)}
 * method. When the mouse pointer passes into or out of the area of the screen
 * covered by a control or pauses while over a control, the appropriate method
 * will be invoked.
 */
public interface MouseTrackListener extends TypedListener {
  /**
   * Sent when the mouse pointer passes into the area of the screen covered by
   * a control.
   *
   * @param e an event containing information about the mouse enter
   */
  void mouseEnter(MouseEvent e);

  /**
   * Sent when the mouse pointer passes out of the area of the screen covered
   * by a control.
   *
   * @param e an event containing information about the mouse exit
   */
  void mouseExit(MouseEvent e);

  /**
   * Sent when the mouse pointer hovers (that is, stops moving for an
   * (operating system specified) period of time) over a control.
   *
   * @param e an event containing information about the hover
   */
  void mouseHover(MouseEvent e);
}
