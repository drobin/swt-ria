package de.robind.swt.test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.eclipse.swt.graphics.Point;
import org.junit.Test;

public class PointTest {
  @Test
  public void ctor() {
    Point p = new Point(1, 2);
    assertThat(p.x, is(1));
    assertThat(p.y, is(2));
  }

  @Test
  public void equals() {
    Point p = new Point(1, 2);

    assertThat(p.equals(p), is(true));
    assertThat(p.equals(null), is(false));
    assertThat(p.equals(new Integer(4711)), is(false));
    assertThat(p.equals(new Point(1, 3)), is(false));
    assertThat(p.equals(new Point(3, 2)), is(false));
    assertThat(p.equals(new Point(1, 2)), is(true));
  }
}
