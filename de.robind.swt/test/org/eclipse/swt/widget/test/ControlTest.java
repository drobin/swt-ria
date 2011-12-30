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
import org.eclipse.swt.test.TestDragDetectListener;
import org.eclipse.swt.test.TestEvent;
import org.eclipse.swt.test.TestFocusListener;
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
    control.removeControlListener(null);
  }

  @Test
  public void removeControlListenerDisposed() {
    exception.expect(swtCode(SWT.ERROR_WIDGET_DISPOSED));

    Control control = new Control(this.shell, 0) {};
    control.dispose();
    control.removeControlListener(new TestControlListener());
  }

  @Test
  public void removeControlListenerInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    final Control control = new Control(this.shell, 0) {};
    asyncExec(new Callable<Control>() {
      public Control call() throws Exception {
        control.removeControlListener(new TestControlListener());
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

  @Test
  public void addDragDetectListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Control control = new Control(this.shell, 0) {};
    control.addDragDetectListener(null);
  }

  @Test
  public void addDragDetectListenerDisposed() {
    exception.expect(swtCode(SWT.ERROR_WIDGET_DISPOSED));

    Control control = new Control(this.shell, 0) {};
    control.dispose();
    control.addDragDetectListener(new TestDragDetectListener());
  }

  @Test
  public void addDragDetectListenerInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    final Control control = new Control(this.shell, 0) {};
    asyncExec(new Callable<Control>() {
      public Control call() throws Exception {
        control.addDragDetectListener(new TestDragDetectListener());
        return (control);
      }
    });
  }

  @Test
  public void removeDragDetectListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Control control = new Control(this.shell, 0) {};
    control.removeDragDetectListener(null);
  }

  @Test
  public void removeDragDetectListenerDisposed() {
    exception.expect(swtCode(SWT.ERROR_WIDGET_DISPOSED));

    Control control = new Control(this.shell, 0) {};
    control.dispose();
    control.removeDragDetectListener(new TestDragDetectListener());
  }

  @Test
  public void removeDragDetectListenerInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    final Control control = new Control(this.shell, 0) {};
    asyncExec(new Callable<Control>() {
      public Control call() throws Exception {
        control.removeDragDetectListener(new TestDragDetectListener());
        return (control);
      }
    });
  }

  @Test
  public void dragDetectListenerHandling() {
    Control control = new Control(this.shell, 0) {};
    TestDragDetectListener listener = new TestDragDetectListener();
    TestEvent event1 = new TestEvent(1);
    TestEvent event2 = new TestEvent(2);

    event1.button = 1;
    event1.stateMask = 2;
    event1.x = 3;
    event1.y = 4;

    event2.button = 5;
    event2.stateMask = 6;
    event2.x = 7;
    event2.y = 8;

    assertThat(control.getListeners(SWT.DragDetect).length, is(0));
    control.addDragDetectListener(listener);
    assertThat(control.getListeners(SWT.DragDetect).length, is(1));

    control.notifyListeners(SWT.DragDetect, event1);
    control.notifyListeners(SWT.DragDetect, event2);

    assertThat(listener.events.size(), is(2));
    assertThat(listener.events.get(0), is(event(display, control, 1)));
    assertThat(listener.events.get(0).button, is(1));
    assertThat(listener.events.get(0).stateMask, is(2));
    assertThat(listener.events.get(0).x, is(3));
    assertThat(listener.events.get(0).y, is(4));
    assertThat(listener.events.get(1), is(event(display, control, 2)));
    assertThat(listener.events.get(1).button, is(5));
    assertThat(listener.events.get(1).stateMask, is(6));
    assertThat(listener.events.get(1).x, is(7));
    assertThat(listener.events.get(1).y, is(8));

    listener.events.clear();
    control.removeDragDetectListener(listener);
    assertThat(control.getListeners(SWT.DragDetect).length, is(0));

    control.notifyListeners(SWT.DragDetect, event1);
    control.notifyListeners(SWT.DragDetect, event2);

    assertThat(listener.events.size(), is(0));
  }

  @Test
  public void addFocusListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Control control = new Control(this.shell, 0) {};
    control.addFocusListener(null);
  }

  @Test
  public void addFocusListenerDisposed() {
    exception.expect(swtCode(SWT.ERROR_WIDGET_DISPOSED));

    Control control = new Control(this.shell, 0) {};
    control.dispose();
    control.addFocusListener(new TestFocusListener());
  }

  @Test
  public void addFocusListenerInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    final Control control = new Control(this.shell, 0) {};
    asyncExec(new Callable<Control>() {
      public Control call() throws Exception {
        control.addFocusListener(new TestFocusListener());
        return (control);
      }
    });
  }

  @Test
  public void removeFocusListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Control control = new Control(this.shell, 0) {};
    control.removeFocusListener(null);
  }

  @Test
  public void removeFocusListenerDisposed() {
    exception.expect(swtCode(SWT.ERROR_WIDGET_DISPOSED));

    Control control = new Control(this.shell, 0) {};
    control.dispose();
    control.removeFocusListener(new TestFocusListener());
  }

  @Test
  public void removeFocusListenerInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    final Control control = new Control(this.shell, 0) {};
    asyncExec(new Callable<Control>() {
      public Control call() throws Exception {
        control.removeFocusListener(new TestFocusListener());
        return (control);
      }
    });
  }

  @Test
  public void focusListenerHandling() {
    Control control = new Control(this.shell, 0) {};
    TestFocusListener listener = new TestFocusListener();
    TestEvent event1 = new TestEvent(1);
    TestEvent event2 = new TestEvent(2);
    TestEvent event3 = new TestEvent(3);
    TestEvent event4 = new TestEvent(4);

    assertThat(control.getListeners(SWT.FocusIn).length, is(0));
    assertThat(control.getListeners(SWT.FocusOut).length, is(0));
    control.addFocusListener(listener);
    assertThat(control.getListeners(SWT.FocusIn).length, is(1));
    assertThat(control.getListeners(SWT.FocusOut).length, is(1));

    control.notifyListeners(SWT.FocusIn, event1);
    control.notifyListeners(SWT.FocusOut, event2);
    control.notifyListeners(SWT.FocusIn, event3);
    control.notifyListeners(SWT.FocusOut, event4);

    assertThat(listener.focusGainedEvents.size(), is(2));
    assertThat(listener.focusGainedEvents.get(0), is(event(display, control, 1)));
    assertThat(listener.focusGainedEvents.get(1), is(event(display, control, 3)));
    assertThat(listener.focusLostEvents.get(0), is(event(display, control, 2)));
    assertThat(listener.focusLostEvents.get(1), is(event(display, control, 4)));

    listener.clearEvents();
    control.removeFocusListener(listener);
    assertThat(control.getListeners(SWT.FocusIn).length, is(0));
    assertThat(control.getListeners(SWT.FocusOut).length, is(0));

    control.notifyListeners(SWT.FocusIn, event1);
    control.notifyListeners(SWT.FocusOut, event2);
    control.notifyListeners(SWT.FocusIn, event3);
    control.notifyListeners(SWT.FocusOut, event4);

    assertThat(listener.focusGainedEvents.size(), is(0));
    assertThat(listener.focusLostEvents.size(), is(0));
  }
}
