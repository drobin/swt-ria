package org.eclipse.swt.graphics;

/**
 * Instances of this class represent places on the (x, y) coordinate plane.
 * <p>
 * The coordinate space for rectangles and points is considered to have
 * increasing values downward and to the right from its origin making this the
 * normal, computer graphics oriented notion of (x, y) coordinates rather than
 * the strict mathematical one.
 * <p>
 * The {@link #hashCode()} method in this class uses the values of the public
 * fields to compute the hash value. When storing instances of the class in
 * hashed collections, do not modify these fields after the object has been
 * inserted.
 * <p>
 * Application code does <i>not</i> need to explicitly release the resources
 * managed by each instance when those instances are no longer required, and
 * thus no dispose() method is provided.
 */
public class Point {
  /**
   * the x coordinate of the point
   */
  public int x;

  /**
   * the y coordinate of the point
   */
  public int y;

  /**
   * Constructs a new point with the given x and y coordinates.
   *
   * @param x the x coordinate of the new point
   * @param y the y coordinate of the new point
   */
  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;

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

    Point other = (Point)obj;
    if (x != other.x || y != other.y) {
      return (false);
    }

    return (true);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return getClass().getSimpleName() +
        " [x=" + this.x + ", y=" + this.y + "]";
  }
}
