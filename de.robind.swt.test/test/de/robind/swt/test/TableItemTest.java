package de.robind.swt.test;

import static de.robind.swt.test.utils.ClientTaskMatcher.createRequest;
import static de.robind.swt.test.utils.SWTExceptionMatcher.swtCode;
import static de.robind.swt.test.utils.SWTTestUtils.asyncExec;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.Callable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TableItemTest extends AbstractWidgetTest {
  private Table table = null;

  @Before
  public void before2() {
    this.table = new Table(this.shell, SWT.DEFAULT);
  }

  @After
  public void after2() {
    this.table = null;
  }

  @Test
  public void ctorNullParent() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));
    new TableItem(null, 0);
  }

  @Test
  public void ctorParentDisposed() {
    exception.expect(swtCode(SWT.ERROR_INVALID_ARGUMENT));

    this.table.dispose();
    new TableItem(this.table, 0);
  }

  @Test
  public void ctorInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    asyncExec(new Callable<TableItem>() {
      public TableItem call() throws Exception {
        return (new TableItem(table, 0));
      }
    });
  }

  @Test
  public void ctorInvalidSubclass() {
    exception.expect(swtCode(SWT.ERROR_INVALID_SUBCLASS));
    new TableItem(this.table, 0) {};
  }

  @Test
  public void ctorRequest() {
    TableItem tableItem = new TableItem(this.table, 4711);
    assertThat(getClientTasks(), is(createRequest(this.display, Display.class)));
    assertThat(getClientTasks(), is(createRequest(this.shell, Shell.class, this.display)));
    assertThat(getClientTasks(), is(createRequest(this.table, Table.class, this.shell, SWT.DEFAULT)));
    assertThat(getClientTasks(), is(createRequest(tableItem, TableItem.class, this.table, 4711)));
  }

  @Test
  public void ctorStyle() {
    TableItem tableItem = new TableItem(this.table, 4711);
    assertThat(tableItem.getStyle(), is(4711));
  }
}
