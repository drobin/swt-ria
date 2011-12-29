package org.eclipse.swt.widget.test;

import static org.eclipse.swt.test.SWTExceptionMatcher.swtCode;
import static org.eclipse.swt.test.SWTTestUtils.asyncExec;
import static org.junit.Assert.fail;

import java.util.concurrent.Callable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.server.DisplayPool;
import org.eclipse.swt.server.Key;
import org.eclipse.swt.test.TestClientTasks;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class WidgetTest {
  private Display display = null;

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @BeforeClass
  public static void beforeClass() {
    System.setProperty("de.robind.swt.clienttasks", TestClientTasks.class.getName());
  }

  @AfterClass
  public static void afterClass() {
    System.clearProperty("de.robind.swt.clienttasks");
  }

  @Before
  public void before() {
    DisplayPool.getInstance().pushKey(new Key() {});
    this.display = new Display();
  }

  @After
  public void after() {
    this.display = null;
  }

  @Test
  public void ctorNullParent() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    new Widget(null, 0) {};
  }

  @Test
  public void ctorParentDisposed() {
    exception.expect(swtCode(SWT.ERROR_INVALID_ARGUMENT));

    Shell shell = new Shell(this.display);
    shell.dispose();

    new Widget(shell, 0) {};
  }

  @Test
  public void ctorInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    final Shell shell = new Shell(this.display);

    asyncExec(new Callable<Widget>() {
      public Widget call() throws Exception {
        return (new Widget(shell, 0) {});
      }
    });
  }

  @Test
  public void ctorInvalidSubclass() {
    fail("Not implemented");
  }

  @Test
  public void ctorStyle() {
    fail("TODO Merge style with parent");
  }
}
