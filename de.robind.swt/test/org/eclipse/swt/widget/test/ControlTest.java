package org.eclipse.swt.widget.test;

import static org.eclipse.swt.test.SWTExceptionMatcher.swtCode;
import static org.eclipse.swt.test.SWTTestUtils.asyncExec;
import static org.eclipse.swt.test.TypedEventMatcher.event;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.concurrent.Callable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.server.DisplayPool;
import org.eclipse.swt.server.Key;
import org.eclipse.swt.test.TestClientTasks;
import org.eclipse.swt.test.TestControlListener;
import org.eclipse.swt.test.TestEvent;
import org.eclipse.swt.widgets.Control;
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

public class ControlTest {
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
    new Control(null, 0) {};
  }

  @Test
  public void ctorParentDisposed() {
    exception.expect(swtCode(SWT.ERROR_INVALID_ARGUMENT));

    this.shell.dispose();
    new Control(this.shell, 0) {};
  }

  @Test
  public void ctorInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    asyncExec(new Callable<Widget>() {
      public Widget call() throws Exception {
        return (new Control(shell, 0) {});
      }
    });
  }

  @Test
  public void ctorInvalidSubclass() {
    fail("Not implemented");
  }

  @Test
  public void ctorStyle() {
    Control control = new Control(this.shell, 4711) {};
    assertThat(control.getStyle(), is(4711));
  }

  @Test
  public void addControlListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Control control = new Control(this.shell, 0) {};
    control.addControlListener(null);
  }

  @Test
  public void addControlListenerDisposed() {
    exception.expect(swtCode(SWT.ERROR_WIDGET_DISPOSED));

    Control control = new Control(this.shell, 0) {};
    control.dispose();
    control.addControlListener(new TestControlListener());
  }

  @Test
  public void addControlListenerInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    final Control control = new Control(this.shell, 0) {};
    asyncExec(new Callable<Control>() {
      public Control call() throws Exception {
        control.addControlListener(new TestControlListener());
        return (control);
      }
    });
  }

  @Test
  public void removeControlListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Control control = new Control(this.shell, 0) {};
    control.addControlListener(null);
  }

  @Test
  public void removeControlListenerDisposed() {
    exception.expect(swtCode(SWT.ERROR_WIDGET_DISPOSED));

    Control control = new Control(this.shell, 0) {};
    control.dispose();
    control.addControlListener(new TestControlListener());
  }

  @Test
  public void removeControlListenerInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    final Control control = new Control(this.shell, 0) {};
    asyncExec(new Callable<Control>() {
      public Control call() throws Exception {
        control.addControlListener(new TestControlListener());
        return (control);
      }
    });
  }

  @Test
  public void controlListenerHandling() {
    Control control = new Control(this.shell, 0) {};
    TestControlListener listener = new TestControlListener();
    TestEvent event1 = new TestEvent(1);
    TestEvent event2 = new TestEvent(2);
    TestEvent event3 = new TestEvent(3);
    TestEvent event4 = new TestEvent(4);

    assertThat(control.getListeners(SWT.Move).length, is(0));
    assertThat(control.getListeners(SWT.Resize).length, is(0));
    control.addControlListener(listener);
    assertThat(control.getListeners(SWT.Move).length, is(1));
    assertThat(control.getListeners(SWT.Resize).length, is(1));

    control.notifyListeners(SWT.Move, event1);
    control.notifyListeners(SWT.Resize, event2);
    control.notifyListeners(SWT.Move, event3);
    control.notifyListeners(SWT.Resize, event4);

    assertThat(listener.movedEvents.size(), is(2));
    assertThat(listener.movedEvents.get(0), is(event(display, control, 1)));
    assertThat(listener.movedEvents.get(1), is(event(display, control, 3)));
    assertThat(listener.resizedEvents.get(0), is(event(display, control, 2)));
    assertThat(listener.resizedEvents.get(1), is(event(display, control, 4)));

    listener.clearEvents();
    control.removeControlListener(listener);
    assertThat(control.getListeners(SWT.Move).length, is(0));
    assertThat(control.getListeners(SWT.Resize).length, is(0));

    control.notifyListeners(SWT.Move, event1);
    control.notifyListeners(SWT.Resize, event2);
    control.notifyListeners(SWT.Move, event3);
    control.notifyListeners(SWT.Resize, event4);

    assertThat(listener.movedEvents.size(), is(0));
    assertThat(listener.resizedEvents.size(), is(0));
  }
}
