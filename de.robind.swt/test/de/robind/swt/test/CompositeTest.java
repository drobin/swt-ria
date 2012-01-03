package de.robind.swt.test;

import static de.robind.swt.test.utils.SWTExceptionMatcher.swtCode;
import static de.robind.swt.test.utils.SWTTestUtils.asyncExec;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.Callable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.server.DisplayPool;
import org.eclipse.swt.server.Key;
import org.eclipse.swt.widgets.Composite;
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

import de.robind.swt.test.utils.TestClientTasks;

public class CompositeTest {
  private Display display = null;
  private Shell shell = null;

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
    DisplayPool.getInstance().offerKey(new Key() {});
    this.display = new Display();
    this.shell = new Shell(this.display);
  }

  @After
  public void after() {
    this.shell = null;
    this.display = null;
  }

  @Test
  public void ctorNullParent() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    new Composite(null, 0);
  }

  @Test
  public void ctorParentDisposed() {
    exception.expect(swtCode(SWT.ERROR_INVALID_ARGUMENT));

    this.shell.dispose();

    new Composite(this.shell, 0);
  }

  @Test
  public void ctorInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    asyncExec(new Callable<Widget>() {
      public Composite call() throws Exception {
        return (new Composite(shell, 0));
      }
    });
  }

  @Test
  public void ctorStyle() {
    Composite composite = new Composite(this.shell, 4711);
    assertThat(composite.getStyle(), is(4711));
  }
}
