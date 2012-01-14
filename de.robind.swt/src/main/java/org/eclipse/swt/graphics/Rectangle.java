package org.eclipse.swt.graphics;

import org.eclipse.swt.widgets.Widget;

/**
 * Instances of this class represent rectangular areas in an (x, y) coordinate
 * system. The top left corner of the rectangle is specified by its x and y
 * values, and the extent of the rectangle is specified by its width and
 * height.
 * <p>
 * The coordinate space for rectangles and points is considered to have
 * increasing values downward and to the right from its origin making this
 * the normal, computer graphics oriented notion of (x, y) coordinates rather
 * than the strict mathematical one.
 * <p>
 * The {@link #hashCode()} method in this class uses the values of the public
 * fields to compute the hash value. When storing instances of the class in
 * hashed collections, do not modify these fields after the object has been
 * inserted.
 * <p>
 * Application code does not need to explicitly release the resources managed
 * by each instance when those instances are no longer required, and thus no
 * {@link Widget#dispose()} method is provided.
 */
public class Rectangle {
  /**
   * the x coordinate of the rectangle
   */
  public int x;

  /**
   * the y coordinate of the rectangle
   */
  public int y;

  /**
   * the width of the rectangle
   */
  public int width;

  /**
   * the height of the rectangle
   */
  public int height;

  /**
   * Construct a new instance of this class given the x, y, width and height
   * values.
   *
   * @param x the x coordinate of the origin of the rectangle
   * @param y the y coordinate of the origin of the rectangle
   * @param width the width of the rectangle
   * @param height the height of the rectangle
   */
  public Rectangle(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;

    result = prime * result + height;
    result = prime * result + width;
    result = prime * result + x;
    result = prime * result + y;

    return (result);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return (true);
    }

    if (obj == null) {
      return (false);
    }

    if (getClass() != obj.getClass()) {
      return (false);
    }

    Rectangle other = (Rectangle)obj;

    if (height != other.height) {
      return (false);
    }

    if (width != other.width) {
      return (false);
    }

    if (x != other.x) {
      return (false);
    }

    if (y != other.y) {
      return (false);
    }

    return (true);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "Rectangle [x=" + this.x + ", y=" + this.y +
        ", width=" + this.width + ", height=" + this.height + "]";
  }
}
