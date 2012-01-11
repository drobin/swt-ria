package de.robind.swt.test;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class LayoutTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void flushCache() {
    class TestLayout extends Layout {
      @Override
      protected Point computeSize(Composite composite, int wHint, int hHint,
          boolean flushCache) {
        return (null);
      }

      @Override
      protected void layout(Composite composite, boolean flushCache) {
      }

      public void doFlushCache(Control control) {
        flushCache(control);
      }
    }

    TestLayout layout = new TestLayout();

    exception.expect(UnsupportedOperationException.class);
    exception.expectMessage("Layout.flushCache() not implemented");

    layout.doFlushCache(null);
  }
}
