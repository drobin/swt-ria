package de.robind.swt.test;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.LayoutAdapter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class LayoutTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void computeSize() {
    TestLayout layout = new TestLayout();

    exception.expect(UnsupportedOperationException.class);
    exception.expectMessage("Layout.computeSize() is not implemented");

    layout.doComputeSize(null, 0, 0, true);
  }

  @Test
  public void flushCache() {
    TestLayout layout = new TestLayout();

    exception.expect(UnsupportedOperationException.class);
    exception.expectMessage("Layout.flushCache() not implemented");

    layout.doFlushCache(null);
  }

  @Test
  public void layout() {
    TestLayout layout = new TestLayout();

    exception.expect(UnsupportedOperationException.class);
    exception.expectMessage("Layout.layout() is not implemented");

    layout.doLayout(null, true);
  }

  private static class TestLayout extends LayoutAdapter {
    public Point doComputeSize(Composite composite, int wHint, int hHint,
        boolean flushCache) {

      return (computeSize(composite, wHint, hHint, flushCache));
    }

    public void doFlushCache(Control control) {
      flushCache(control);
    }

    public void doLayout(Composite composite, boolean flushCache) {
      layout(composite, flushCache);
    }
  }
}
