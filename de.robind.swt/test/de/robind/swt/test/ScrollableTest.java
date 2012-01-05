package de.robind.swt.test;

import static de.robind.swt.test.utils.SWTExceptionMatcher.swtCode;
import static de.robind.swt.test.utils.SWTTestUtils.asyncExec;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.test.TestScrollable;
import org.eclipse.swt.widgets.Scrollable;
import org.junit.Test;

public class ScrollableTest extends AbstractWidgetTest {
  @Test
  public void ctorNullParent() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));
    new TestScrollable(null, 0);
  }

  @Test
  public void ctorParentDisposed() {
    exception.expect(swtCode(SWT.ERROR_INVALID_ARGUMENT));

    this.shell.dispose();
    new TestScrollable(this.shell, 0);
  }

  @Test
  public void ctorInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    asyncExec(new Callable<Scrollable>() {
      public Scrollable call() throws Exception {
        return (new TestScrollable(shell, 0));
      }
    });
  }

  @Test
  public void ctorInvalidSubclass() {
    exception.expect(swtCode(SWT.ERROR_INVALID_SUBCLASS));
    new TestScrollable(this.shell, 0) {};
  }

  @Test
  public void ctorStyle() {
    TestScrollable scrollable = new TestScrollable(this.shell, 4711);
    assertThat(scrollable.getStyle(), is(4711));
  }

  @Test
  public void getClientArea() {
    Map<String, Object> clientArea = new HashMap<String, Object>();
    clientArea.put("x", 1);
    clientArea.put("y", 2);
    clientArea.put("width", 3);
    clientArea.put("height", 4);

    getClientTasks().setCallMethodResult(clientArea);

    Scrollable scrollable = new TestScrollable(this.shell, 0);
    Rectangle rect = scrollable.getClientArea();

    assertThat(rect.x, is(1));
    assertThat(rect.y, is(2));
    assertThat(rect.width, is(3));
    assertThat(rect.height, is(4));

  }
}
