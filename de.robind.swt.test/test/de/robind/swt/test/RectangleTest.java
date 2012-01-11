package de.robind.swt.test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.eclipse.swt.graphics.Rectangle;
import org.junit.Test;

public class RectangleTest {
  @Test
  public void ctor() {
    Rectangle r = new Rectangle(1, 2, 3, 4);

    assertThat(r.x, is(1));
    assertThat(r.y, is(2));
    assertThat(r.width, is(3));
    assertThat(r.height, is(4));
  }
}
