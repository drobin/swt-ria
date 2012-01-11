package de.robind.swt.test;

import static de.robind.swt.test.utils.ClientTaskMatcher.callRequest;
import static de.robind.swt.test.utils.ClientTaskMatcher.createRequest;
import static de.robind.swt.test.utils.SWTExceptionMatcher.swtCode;
import static de.robind.swt.test.utils.SWTTestUtils.asyncExec;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.Callable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
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
  public void ctorRequest() {
    List list = new List(this.shell, 4711);
    assertThat(getClientTasks(), is(createRequest(this.display, Display.class)));
    assertThat(getClientTasks(), is(createRequest(this.shell, Shell.class, this.display)));
    assertThat(getClientTasks(), is(createRequest(list, List.class, this.shell, 4711)));
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
    assertThat(getClientTasks(), is(callRequest(list, "getSelectionIndices")));
  }

  @Test
  public void getTopIndex() {
    getClientTasks().setCallMethodResult(0);

    List list = new List(this.shell, 4711);
    assertThat(list.getTopIndex(), is(0));
    assertThat(getClientTasks(), is(callRequest(list, "getTopIndex")));
  }

  @Test
  public void deselectAll() {
    List list = new List(this.shell, 4711);
    list.deselectAll();
    assertThat(getClientTasks(), is(callRequest(list, "deselectAll")));
  }

  @Test
  public void removeAll() {
    List list = new List(this.shell, 4711);
    list.removeAll();
    assertThat(getClientTasks(), is(callRequest(list, "removeAll")));
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
    String[] items = new String[] { "foo", "bar" };
    List list = new List(this.shell, 4711);
    list.setItems(items);
    assertThat(getClientTasks(), is(callRequest(list, "setItems", (Object)items)));
  }

  @Test
  public void setTopIndex() {
    List list = new List(this.shell, 4711);
    list.setTopIndex(0);
    assertThat(getClientTasks(), is(callRequest(list, "setTopIndex", 0)));
  }
}
