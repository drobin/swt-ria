package de.robind.swt.test;

import static de.robind.swt.test.utils.SWTExceptionMatcher.swtCode;
import static de.robind.swt.test.utils.SWTTestUtils.asyncExec;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.Callable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.junit.Test;

public class ListTest extends AbstractWidgetTest {
  @Test
  public void ctorNullParent() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    new List(null, 0);
  }

  @Test
  public void ctorParentDisposed() {
    exception.expect(swtCode(SWT.ERROR_INVALID_ARGUMENT));

    Shell shell = new Shell(this.display);
    shell.dispose();

    new List(shell, 0);
  }

  @Test
  public void ctorInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    asyncExec(new Callable<Widget>() {
      public List call() throws Exception {
        return (new List(shell, 0));
      }
    });
  }

  @Test
  public void ctorInvalidSubclass() {
    exception.expect(swtCode(SWT.ERROR_INVALID_SUBCLASS));
    new List(this.shell, 0 ) {};
  }

  @Test
  public void ctorStyle() {
    List list = new List(this.shell, 4711);
    assertThat(list.getStyle(), is(4711));
  }

  @Test
  public void getSelectionIndices() {
    getClientTasks().setCallMethodResult(new int[] {});

    List list = new List(this.shell, 4711);
    assertThat(list.getSelectionIndices().length, is(0));
  }

  @Test
  public void getTopIndex() {
    getClientTasks().setCallMethodResult(0);

    List list = new List(this.shell, 4711);
    assertThat(list.getTopIndex(), is(0));
  }

  @Test
  public void deselectAll() {
    List list = new List(this.shell, 4711);
    list.deselectAll();
  }

  @Test
  public void removeAll() {
    List list = new List(this.shell, 4711);
    list.removeAll();
  }

  @Test
  public void setItemsNullItems() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    List list = new List(this.shell, 4711);
    list.setItems(null);
  }

  @Test
  public void setItemsNullElement() {
    exception.expect(swtCode(SWT.ERROR_INVALID_ARGUMENT));

    List list = new List(this.shell, 4711);
    list.setItems(new String[] { null });
  }

  @Test
  public void setItems() {
    List list = new List(this.shell, 4711);
    list.setItems(new String[] { "foo", "bar" });
  }

  @Test
  public void setTopIndex() {
    List list = new List(this.shell, 4711);
    list.setTopIndex(0);
  }
}
