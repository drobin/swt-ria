package org.eclipse.swt.events;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Event;

/**
 * Instances of this class are sent as a result of visible areas of controls
 * requiring re-painting.
 */
public class PaintEvent extends TypedEvent {
  private static final long serialVersionUID = -3187338062441835219L;

  /**
   * the number of following paint events which are pending which may always be
   * zero on some platforms
   */
  public int count;

  /**
   * the graphics context to use when painting that is configured to use the
   * colors, font and damaged region of the control. It is valid only during
   * the paint and must not be disposed
   */
  public GC gc;

  /**
   * the height of the bounding rectangle of the region that requires painting
   */
  public int height;

  /**
   * the width of the bounding rectangle of the region that requires painting
   */
  public int width;

  /**
   * the x offset of the bounding rectangle of the region that requires
   * painting
   */
  public int x;

  /**
   * the y offset of the bounding rectangle of the region that requires
   * painting
   */
  public int y;

  /**
   * Constructs a new instance of this class based on the information in the
   * given untyped event.
   *
   * @param e the untyped event containing the information
   */
  public PaintEvent(Event e) {
    super(e);

    this.count = e.count;
    this.gc = e.gc;
    this.height = e.height;
    this.width = e.width;
    this.x = e.x;
    this.y = e.y;
  }
}
