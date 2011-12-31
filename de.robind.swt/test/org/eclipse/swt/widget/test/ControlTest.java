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
import org.eclipse.swt.test.TestHelpListener;
import org.eclipse.swt.test.TestKeyListener;
import org.eclipse.swt.test.TestMenuDetectListener;
import org.eclipse.swt.test.TestMouseListener;
import org.eclipse.swt.test.TestMouseMoveListener;
import org.eclipse.swt.test.TestMouseTrackListener;
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

  @Test
  public void addHelpListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Control control = new Control(this.shell, 0) {};
    control.addHelpListener(null);
  }

  @Test
  public void addHelpListenerDisposed() {
    exception.expect(swtCode(SWT.ERROR_WIDGET_DISPOSED));

    Control control = new Control(this.shell, 0) {};
    control.dispose();
    control.addHelpListener(new TestHelpListener());
  }

  @Test
  public void addHelpListenerInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    final Control control = new Control(this.shell, 0) {};
    asyncExec(new Callable<Control>() {
      public Control call() throws Exception {
        control.addHelpListener(new TestHelpListener());
        return (control);
      }
    });
  }

  @Test
  public void removeHelpListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Control control = new Control(this.shell, 0) {};
    control.removeHelpListener(null);
  }

  @Test
  public void removeHelpListenerDisposed() {
    exception.expect(swtCode(SWT.ERROR_WIDGET_DISPOSED));

    Control control = new Control(this.shell, 0) {};
    control.dispose();
    control.removeHelpListener(new TestHelpListener());
  }

  @Test
  public void removeHelpListenerInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    final Control control = new Control(this.shell, 0) {};
    asyncExec(new Callable<Control>() {
      public Control call() throws Exception {
        control.removeHelpListener(new TestHelpListener());
        return (control);
      }
    });
  }

  @Test
  public void helpListenerHandling() {
    Control control = new Control(this.shell, 0) {};
    TestHelpListener listener = new TestHelpListener();
    TestEvent event1 = new TestEvent(1);
    TestEvent event2 = new TestEvent(2);

    assertThat(control.getListeners(SWT.Help).length, is(0));
    control.addHelpListener(listener);
    assertThat(control.getListeners(SWT.Help).length, is(1));

    control.notifyListeners(SWT.Help, event1);
    control.notifyListeners(SWT.Help, event2);

    assertThat(listener.events.size(), is(2));
    assertThat(listener.events.get(0), is(event(display, control, 1)));
    assertThat(listener.events.get(1), is(event(display, control, 2)));

    listener.events.clear();
    control.removeHelpListener(listener);
    assertThat(control.getListeners(SWT.Help).length, is(0));

    control.notifyListeners(SWT.Help, event1);
    control.notifyListeners(SWT.Help, event2);

    assertThat(listener.events.size(), is(0));
  }

  @Test
  public void addKeyListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Control control = new Control(this.shell, 0) {};
    control.addKeyListener(null);
  }

  @Test
  public void addKeyListenerDisposed() {
    exception.expect(swtCode(SWT.ERROR_WIDGET_DISPOSED));

    Control control = new Control(this.shell, 0) {};
    control.dispose();
    control.addKeyListener(new TestKeyListener());
  }

  @Test
  public void addKeyListenerInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    final Control control = new Control(this.shell, 0) {};
    asyncExec(new Callable<Control>() {
      public Control call() throws Exception {
        control.addKeyListener(new TestKeyListener());
        return (control);
      }
    });
  }

  @Test
  public void removeKeyListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Control control = new Control(this.shell, 0) {};
    control.removeKeyListener(null);
  }

  @Test
  public void removeKeyListenerDisposed() {
    exception.expect(swtCode(SWT.ERROR_WIDGET_DISPOSED));

    Control control = new Control(this.shell, 0) {};
    control.dispose();
    control.removeKeyListener(new TestKeyListener());
  }

  @Test
  public void removeKeyListenerInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    final Control control = new Control(this.shell, 0) {};
    asyncExec(new Callable<Control>() {
      public Control call() throws Exception {
        control.removeKeyListener(new TestKeyListener());
        return (control);
      }
    });
  }

  @Test
  public void keyListenerHandling() {
    Control control = new Control(this.shell, 0) {};
    TestKeyListener listener = new TestKeyListener();
    TestEvent event1 = new TestEvent(1);
    TestEvent event2 = new TestEvent(2);
    TestEvent event3 = new TestEvent(3);
    TestEvent event4 = new TestEvent(4);

    event1.character = 'a';
    event1.doit = true;
    event1.keyCode = 1;
    event1.keyLocation = 2;

    event2.character = 'b';
    event2.doit = false;
    event2.keyCode = 3;
    event2.keyLocation = 4;

    event3.character = 'c';
    event3.doit = true;
    event3.keyCode = 5;
    event3.keyLocation = 6;

    event4.character = 'd';
    event4.doit = false;
    event4.keyCode = 7;
    event4.keyLocation = 8;

    assertThat(control.getListeners(SWT.KeyUp).length, is(0));
    assertThat(control.getListeners(SWT.KeyDown).length, is(0));
    control.addKeyListener(listener);
    assertThat(control.getListeners(SWT.KeyUp).length, is(1));
    assertThat(control.getListeners(SWT.KeyDown).length, is(1));

    control.notifyListeners(SWT.KeyDown, event1);
    control.notifyListeners(SWT.KeyUp, event2);
    control.notifyListeners(SWT.KeyDown, event3);
    control.notifyListeners(SWT.KeyUp, event4);

    assertThat(listener.keyPressedEvents.size(), is(2));
    assertThat(listener.keyPressedEvents.get(0), is(event(display, control, 1)));
    assertThat(listener.keyPressedEvents.get(0).character, is('a'));
    assertThat(listener.keyPressedEvents.get(0).doit, is(true));
    assertThat(listener.keyPressedEvents.get(0).keyCode, is(1));
    assertThat(listener.keyPressedEvents.get(0).keyLocation, is(2));
    assertThat(listener.keyPressedEvents.get(1), is(event(display, control, 3)));
    assertThat(listener.keyPressedEvents.get(1).character, is('c'));
    assertThat(listener.keyPressedEvents.get(1).doit, is(true));
    assertThat(listener.keyPressedEvents.get(1).keyCode, is(5));
    assertThat(listener.keyPressedEvents.get(1).keyLocation, is(6));
    assertThat(listener.keyReleasedEvents.size(), is(2));
    assertThat(listener.keyReleasedEvents.get(0), is(event(display, control, 2)));
    assertThat(listener.keyReleasedEvents.get(0).character, is('b'));
    assertThat(listener.keyReleasedEvents.get(0).doit, is(false));
    assertThat(listener.keyReleasedEvents.get(0).keyCode, is(3));
    assertThat(listener.keyReleasedEvents.get(0).keyLocation, is(4));
    assertThat(listener.keyReleasedEvents.get(1), is(event(display, control, 4)));
    assertThat(listener.keyReleasedEvents.get(1).character, is('d'));
    assertThat(listener.keyReleasedEvents.get(1).doit, is(false));
    assertThat(listener.keyReleasedEvents.get(1).keyCode, is(7));
    assertThat(listener.keyReleasedEvents.get(1).keyLocation, is(8));

    listener.clearEvents();
    control.removeKeyListener(listener);
    assertThat(control.getListeners(SWT.KeyUp).length, is(0));
    assertThat(control.getListeners(SWT.KeyDown).length, is(0));

    control.notifyListeners(SWT.KeyUp, event1);
    control.notifyListeners(SWT.KeyDown, event2);
    control.notifyListeners(SWT.KeyUp, event3);
    control.notifyListeners(SWT.KeyDown, event4);

    assertThat(listener.keyPressedEvents.size(), is(0));
    assertThat(listener.keyReleasedEvents.size(), is(0));
  }

  @Test
  public void addMenuDetectListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Control control = new Control(this.shell, 0) {};
    control.addMenuDetectListener(null);
  }

  @Test
  public void addMenuDetectListenerDisposed() {
    exception.expect(swtCode(SWT.ERROR_WIDGET_DISPOSED));

    Control control = new Control(this.shell, 0) {};
    control.dispose();
    control.addMenuDetectListener(new TestMenuDetectListener());
  }

  @Test
  public void addMenuDetectListenerInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    final Control control = new Control(this.shell, 0) {};
    asyncExec(new Callable<Control>() {
      public Control call() throws Exception {
        control.addMenuDetectListener(new TestMenuDetectListener());
        return (control);
      }
    });
  }

  @Test
  public void removeMenuDetectListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Control control = new Control(this.shell, 0) {};
    control.removeMenuDetectListener(null);
  }

  @Test
  public void removeMenuDetectListenerDisposed() {
    exception.expect(swtCode(SWT.ERROR_WIDGET_DISPOSED));

    Control control = new Control(this.shell, 0) {};
    control.dispose();
    control.removeMenuDetectListener(new TestMenuDetectListener());
  }

  @Test
  public void removeMenuDetectListenerInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    final Control control = new Control(this.shell, 0) {};
    asyncExec(new Callable<Control>() {
      public Control call() throws Exception {
        control.removeMenuDetectListener(new TestMenuDetectListener());
        return (control);
      }
    });
  }

  @Test
  public void menuDetectListenerHandling() {
    Control control = new Control(this.shell, 0) {};
    TestMenuDetectListener listener = new TestMenuDetectListener();
    TestEvent event1 = new TestEvent(1);
    TestEvent event2 = new TestEvent(2);

    event1.doit = true;
    event1.x = 1;
    event1.y = 2;

    event2.doit = false;
    event2.x = 3;
    event2.y = 4;

    assertThat(control.getListeners(SWT.MenuDetect).length, is(0));
    control.addMenuDetectListener(listener);
    assertThat(control.getListeners(SWT.MenuDetect).length, is(1));

    control.notifyListeners(SWT.MenuDetect, event1);
    control.notifyListeners(SWT.MenuDetect, event2);

    assertThat(listener.events.size(), is(2));
    assertThat(listener.events.get(0), is(event(display, control, 1)));
    assertThat(listener.events.get(0).doit, is(true));
    assertThat(listener.events.get(0).x, is(1));
    assertThat(listener.events.get(0).y, is(2));
    assertThat(listener.events.get(1), is(event(display, control, 2)));
    assertThat(listener.events.get(1).doit, is(false));
    assertThat(listener.events.get(1).x, is(3));
    assertThat(listener.events.get(1).y, is(4));

    listener.events.clear();
    control.removeMenuDetectListener(listener);
    assertThat(control.getListeners(SWT.MenuDetect).length, is(0));

    control.notifyListeners(SWT.MenuDetect, event1);
    control.notifyListeners(SWT.MenuDetect, event2);

    assertThat(listener.events.size(), is(0));
  }

  @Test
  public void addMouseListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Control control = new Control(this.shell, 0) {};
    control.addMouseListener(null);
  }

  @Test
  public void addMouseListenerDisposed() {
    exception.expect(swtCode(SWT.ERROR_WIDGET_DISPOSED));

    Control control = new Control(this.shell, 0) {};
    control.dispose();
    control.addMouseListener(new TestMouseListener());
  }

  @Test
  public void addMouseListenerInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    final Control control = new Control(this.shell, 0) {};
    asyncExec(new Callable<Control>() {
      public Control call() throws Exception {
        control.addMouseListener(new TestMouseListener());
        return (control);
      }
    });
  }

  @Test
  public void removeMouseListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Control control = new Control(this.shell, 0) {};
    control.removeMouseListener(null);
  }

  @Test
  public void removeMouseListenerDisposed() {
    exception.expect(swtCode(SWT.ERROR_WIDGET_DISPOSED));

    Control control = new Control(this.shell, 0) {};
    control.dispose();
    control.removeMouseListener(new TestMouseListener());
  }

  @Test
  public void removeMouseListenerInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    final Control control = new Control(this.shell, 0) {};
    asyncExec(new Callable<Control>() {
      public Control call() throws Exception {
        control.removeMouseListener(new TestMouseListener());
        return (control);
      }
    });
  }

  @Test
  public void mouseListenerHandling() {
    Control control = new Control(this.shell, 0) {};
    TestMouseListener listener = new TestMouseListener();
    TestEvent event1 = new TestEvent(1);
    TestEvent event2 = new TestEvent(2);
    TestEvent event3 = new TestEvent(3);
    TestEvent event4 = new TestEvent(4);
    TestEvent event5 = new TestEvent(5);
    TestEvent event6 = new TestEvent(6);

    event1.button = 1;
    event1.stateMask = 2;
    event1.x = 3;
    event1.y = 4;

    event2.button = 5;
    event2.stateMask = 6;
    event2.x = 7;
    event2.y = 8;

    event3.button = 9;
    event3.stateMask = 10;
    event3.x = 11;
    event3.y = 12;

    event4.button = 13;
    event4.stateMask = 14;
    event4.x = 15;
    event4.y = 16;

    event5.button = 17;
    event5.stateMask = 18;
    event5.x = 19;
    event5.y = 20;

    event6.button = 21;
    event6.stateMask = 22;
    event6.x = 23;
    event6.y = 24;

    assertThat(control.getListeners(SWT.MouseUp).length, is(0));
    assertThat(control.getListeners(SWT.MouseDown).length, is(0));
    assertThat(control.getListeners(SWT.MouseDoubleClick).length, is(0));
    control.addMouseListener(listener);
    assertThat(control.getListeners(SWT.MouseUp).length, is(1));
    assertThat(control.getListeners(SWT.MouseDown).length, is(1));
    assertThat(control.getListeners(SWT.MouseDoubleClick).length, is(1));

    control.notifyListeners(SWT.MouseUp, event1);
    control.notifyListeners(SWT.MouseDown, event2);
    control.notifyListeners(SWT.MouseDoubleClick, event3);
    control.notifyListeners(SWT.MouseUp, event4);
    control.notifyListeners(SWT.MouseDown, event5);
    control.notifyListeners(SWT.MouseDoubleClick, event6);

    assertThat(listener.upEvents.size(), is(2));
    assertThat(listener.downEvents.size(), is(2));
    assertThat(listener.doubleClickEvents.size(), is(2));
    assertThat(listener.upEvents.get(0), is(event(display, control, 1)));
    assertThat(listener.upEvents.get(0).button, is(1));
    assertThat(listener.upEvents.get(0).stateMask, is(2));
    assertThat(listener.upEvents.get(0).x, is(3));
    assertThat(listener.upEvents.get(0).y, is(4));
    assertThat(listener.upEvents.get(1), is(event(display, control, 4)));
    assertThat(listener.upEvents.get(1).button, is(13));
    assertThat(listener.upEvents.get(1).stateMask, is(14));
    assertThat(listener.upEvents.get(1).x, is(15));
    assertThat(listener.upEvents.get(1).y, is(16));
    assertThat(listener.downEvents.get(0), is(event(display, control, 2)));
    assertThat(listener.downEvents.get(0).button, is(5));
    assertThat(listener.downEvents.get(0).stateMask, is(6));
    assertThat(listener.downEvents.get(0).x, is(7));
    assertThat(listener.downEvents.get(0).y, is(8));
    assertThat(listener.downEvents.get(1), is(event(display, control, 5)));
    assertThat(listener.downEvents.get(1).button, is(17));
    assertThat(listener.downEvents.get(1).stateMask, is(18));
    assertThat(listener.downEvents.get(1).x, is(19));
    assertThat(listener.downEvents.get(1).y, is(20));
    assertThat(listener.doubleClickEvents.get(0), is(event(display, control, 3)));
    assertThat(listener.doubleClickEvents.get(0).button, is(9));
    assertThat(listener.doubleClickEvents.get(0).stateMask, is(10));
    assertThat(listener.doubleClickEvents.get(0).x, is(11));
    assertThat(listener.doubleClickEvents.get(0).y, is(12));
    assertThat(listener.doubleClickEvents.get(1), is(event(display, control, 6)));
    assertThat(listener.doubleClickEvents.get(1).button, is(21));
    assertThat(listener.doubleClickEvents.get(1).stateMask, is(22));
    assertThat(listener.doubleClickEvents.get(1).x, is(23));
    assertThat(listener.doubleClickEvents.get(1).y, is(24));

    listener.clearEvents();
    control.removeMouseListener(listener);
    assertThat(control.getListeners(SWT.MouseUp).length, is(0));
    assertThat(control.getListeners(SWT.MouseDown).length, is(0));
    assertThat(control.getListeners(SWT.MouseDoubleClick).length, is(0));

    control.notifyListeners(SWT.MouseUp, event1);
    control.notifyListeners(SWT.MouseDown, event2);
    control.notifyListeners(SWT.MouseDoubleClick, event3);
    control.notifyListeners(SWT.MouseUp, event4);
    control.notifyListeners(SWT.MouseDown, event5);
    control.notifyListeners(SWT.MouseDoubleClick, event6);

    assertThat(listener.upEvents.size(), is(0));
    assertThat(listener.downEvents.size(), is(0));
    assertThat(listener.doubleClickEvents.size(), is(0));
  }

  @Test
  public void addMouseMoveListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Control control = new Control(this.shell, 0) {};
    control.addMouseMoveListener(null);
  }

  @Test
  public void addMouseMoveListenerDisposed() {
    exception.expect(swtCode(SWT.ERROR_WIDGET_DISPOSED));

    Control control = new Control(this.shell, 0) {};
    control.dispose();
    control.addMouseMoveListener(new TestMouseMoveListener());
  }

  @Test
  public void addMouseMoveListenerInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    final Control control = new Control(this.shell, 0) {};
    asyncExec(new Callable<Control>() {
      public Control call() throws Exception {
        control.addMouseMoveListener(new TestMouseMoveListener());
        return (control);
      }
    });
  }

  @Test
  public void removeMouseMoveListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Control control = new Control(this.shell, 0) {};
    control.removeMouseMoveListener(null);
  }

  @Test
  public void removeMouseMoveListenerDisposed() {
    exception.expect(swtCode(SWT.ERROR_WIDGET_DISPOSED));

    Control control = new Control(this.shell, 0) {};
    control.dispose();
    control.removeMouseMoveListener(new TestMouseMoveListener());
  }

  @Test
  public void removeMouseMoveListenerInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    final Control control = new Control(this.shell, 0) {};
    asyncExec(new Callable<Control>() {
      public Control call() throws Exception {
        control.removeMouseMoveListener(new TestMouseMoveListener());
        return (control);
      }
    });
  }

  @Test
  public void mouseMoveListenerHandling() {
    Control control = new Control(this.shell, 0) {};
    TestMouseMoveListener listener = new TestMouseMoveListener();
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

    assertThat(control.getListeners(SWT.MouseMove).length, is(0));
    control.addMouseMoveListener(listener);
    assertThat(control.getListeners(SWT.MouseMove).length, is(1));

    control.notifyListeners(SWT.MouseMove, event1);
    control.notifyListeners(SWT.MouseMove, event2);

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
    control.removeMouseMoveListener(listener);
    assertThat(control.getListeners(SWT.MouseMove).length, is(0));

    control.notifyListeners(SWT.MouseMove, event1);
    control.notifyListeners(SWT.MouseMove, event2);

    assertThat(listener.events.size(), is(0));
  }

  @Test
  public void addMouseTrackListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Control control = new Control(this.shell, 0) {};
    control.addMouseTrackListener(null);
  }

  @Test
  public void addMouseTrackListenerDisposed() {
    exception.expect(swtCode(SWT.ERROR_WIDGET_DISPOSED));

    Control control = new Control(this.shell, 0) {};
    control.dispose();
    control.addMouseTrackListener(new TestMouseTrackListener());
  }

  @Test
  public void addMouseTrackListenerInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    final Control control = new Control(this.shell, 0) {};
    asyncExec(new Callable<Control>() {
      public Control call() throws Exception {
        control.addMouseTrackListener(new TestMouseTrackListener());
        return (control);
      }
    });
  }

  @Test
  public void removeMouseTrackListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Control control = new Control(this.shell, 0) {};
    control.removeMouseTrackListener(null);
  }

  @Test
  public void removeMouseTrackListenerDisposed() {
    exception.expect(swtCode(SWT.ERROR_WIDGET_DISPOSED));

    Control control = new Control(this.shell, 0) {};
    control.dispose();
    control.removeMouseTrackListener(new TestMouseTrackListener());
  }

  @Test
  public void removeMouseTrackListenerInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    final Control control = new Control(this.shell, 0) {};
    asyncExec(new Callable<Control>() {
      public Control call() throws Exception {
        control.removeMouseTrackListener(new TestMouseTrackListener());
        return (control);
      }
    });
  }

  @Test
  public void mouseTrackListenerHandling() {
    Control control = new Control(this.shell, 0) {};
    TestMouseTrackListener listener = new TestMouseTrackListener();
    TestEvent event1 = new TestEvent(1);
    TestEvent event2 = new TestEvent(2);
    TestEvent event3 = new TestEvent(3);
    TestEvent event4 = new TestEvent(4);
    TestEvent event5 = new TestEvent(5);
    TestEvent event6 = new TestEvent(6);

    event1.button = 1;
    event1.stateMask = 2;
    event1.x = 3;
    event1.y = 4;

    event2.button = 5;
    event2.stateMask = 6;
    event2.x = 7;
    event2.y = 8;

    event3.button = 9;
    event3.stateMask = 10;
    event3.x = 11;
    event3.y = 12;

    event4.button = 13;
    event4.stateMask = 14;
    event4.x = 15;
    event4.y = 16;

    event5.button = 17;
    event5.stateMask = 18;
    event5.x = 19;
    event5.y = 20;

    event6.button = 21;
    event6.stateMask = 22;
    event6.x = 23;
    event6.y = 24;

    assertThat(control.getListeners(SWT.MouseEnter).length, is(0));
    assertThat(control.getListeners(SWT.MouseExit).length, is(0));
    assertThat(control.getListeners(SWT.MouseHover).length, is(0));
    control.addMouseTrackListener(listener);
    assertThat(control.getListeners(SWT.MouseEnter).length, is(1));
    assertThat(control.getListeners(SWT.MouseExit).length, is(1));
    assertThat(control.getListeners(SWT.MouseHover).length, is(1));

    control.notifyListeners(SWT.MouseEnter, event1);
    control.notifyListeners(SWT.MouseExit, event2);
    control.notifyListeners(SWT.MouseHover, event3);
    control.notifyListeners(SWT.MouseEnter, event4);
    control.notifyListeners(SWT.MouseExit, event5);
    control.notifyListeners(SWT.MouseHover, event6);

    assertThat(listener.enterEvents.size(), is(2));
    assertThat(listener.exitEvents.size(), is(2));
    assertThat(listener.hoverEvents.size(), is(2));
    assertThat(listener.enterEvents.get(0), is(event(display, control, 1)));
    assertThat(listener.enterEvents.get(0).button, is(1));
    assertThat(listener.enterEvents.get(0).stateMask, is(2));
    assertThat(listener.enterEvents.get(0).x, is(3));
    assertThat(listener.enterEvents.get(0).y, is(4));
    assertThat(listener.enterEvents.get(1), is(event(display, control, 4)));
    assertThat(listener.enterEvents.get(1).button, is(13));
    assertThat(listener.enterEvents.get(1).stateMask, is(14));
    assertThat(listener.enterEvents.get(1).x, is(15));
    assertThat(listener.enterEvents.get(1).y, is(16));
    assertThat(listener.exitEvents.get(0), is(event(display, control, 2)));
    assertThat(listener.exitEvents.get(0).button, is(5));
    assertThat(listener.exitEvents.get(0).stateMask, is(6));
    assertThat(listener.exitEvents.get(0).x, is(7));
    assertThat(listener.exitEvents.get(0).y, is(8));
    assertThat(listener.exitEvents.get(1), is(event(display, control, 5)));
    assertThat(listener.exitEvents.get(1).button, is(17));
    assertThat(listener.exitEvents.get(1).stateMask, is(18));
    assertThat(listener.exitEvents.get(1).x, is(19));
    assertThat(listener.exitEvents.get(1).y, is(20));
    assertThat(listener.hoverEvents.get(0), is(event(display, control, 3)));
    assertThat(listener.hoverEvents.get(0).button, is(9));
    assertThat(listener.hoverEvents.get(0).stateMask, is(10));
    assertThat(listener.hoverEvents.get(0).x, is(11));
    assertThat(listener.hoverEvents.get(0).y, is(12));
    assertThat(listener.hoverEvents.get(1), is(event(display, control, 6)));
    assertThat(listener.hoverEvents.get(1).button, is(21));
    assertThat(listener.hoverEvents.get(1).stateMask, is(22));
    assertThat(listener.hoverEvents.get(1).x, is(23));
    assertThat(listener.hoverEvents.get(1).y, is(24));

    listener.clearEvents();
    control.removeMouseTrackListener(listener);
    assertThat(control.getListeners(SWT.MouseEnter).length, is(0));
    assertThat(control.getListeners(SWT.MouseExit).length, is(0));
    assertThat(control.getListeners(SWT.MouseHover).length, is(0));

    control.notifyListeners(SWT.MouseEnter, event1);
    control.notifyListeners(SWT.MouseExit, event2);
    control.notifyListeners(SWT.MouseHover, event3);
    control.notifyListeners(SWT.MouseEnter, event4);
    control.notifyListeners(SWT.MouseExit, event5);
    control.notifyListeners(SWT.MouseHover, event6);

    assertThat(listener.enterEvents.size(), is(0));
    assertThat(listener.exitEvents.size(), is(0));
    assertThat(listener.hoverEvents.size(), is(0));
  }
}
