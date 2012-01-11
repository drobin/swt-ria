package de.robind.swt.test;

import static de.robind.swt.test.utils.ClientTaskMatcher.callRequest;
import static de.robind.swt.test.utils.ClientTaskMatcher.createRequest;
import static de.robind.swt.test.utils.SWTExceptionMatcher.swtCode;
import static de.robind.swt.test.utils.SWTTestUtils.asyncExec;
import static de.robind.swt.test.utils.TypedEventMatcher.event;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.concurrent.Callable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTObject;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.test.TestControl;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.junit.Test;

import de.robind.swt.test.utils.TestControlListener;
import de.robind.swt.test.utils.TestDragDetectListener;
import de.robind.swt.test.utils.TestEvent;
import de.robind.swt.test.utils.TestFocusListener;
import de.robind.swt.test.utils.TestHelpListener;
import de.robind.swt.test.utils.TestKeyListener;
import de.robind.swt.test.utils.TestMenuDetectListener;
import de.robind.swt.test.utils.TestMouseListener;
import de.robind.swt.test.utils.TestMouseMoveListener;
import de.robind.swt.test.utils.TestMouseTrackListener;
import de.robind.swt.test.utils.TestMouseWheelListener;
import de.robind.swt.test.utils.TestPaintListener;
import de.robind.swt.test.utils.TestTraverseListener;


public class ControlTest extends AbstractWidgetTest {
  @Test
  public void ctorNullParent() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));
    new TestControl(null, 0);
  }

  @Test
  public void ctorParentDisposed() {
    exception.expect(swtCode(SWT.ERROR_INVALID_ARGUMENT));

    this.shell.dispose();
    new TestControl(this.shell, 0);
  }

  @Test
  public void ctorInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    asyncExec(new Callable<Widget>() {
      public Widget call() throws Exception {
        return (new TestControl(shell, 0));
      }
    });
  }

  @Test
  public void ctorInvalidSubclass() {
    exception.expect(swtCode(SWT.ERROR_INVALID_SUBCLASS));
    new Control(this.shell, 0) {};
  }

  @Test
  public void ctorRequest() {
    Control control = new TestControl(this.shell, 4711);
    assertThat(getClientTasks(), is(createRequest(this.display, Display.class)));
    assertThat(getClientTasks(), is(createRequest(this.shell, Shell.class, this.display)));
    assertThat(getClientTasks(), is(createRequest(control, TestControl.class, this.shell, 4711)));
  }

  @Test
  public void ctorStyle() {
    Control control = new TestControl(this.shell, 4711);
    assertThat(control.getStyle(), is(4711));
  }

  @Test
  public void addControlListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Control control = new TestControl(this.shell, 0);
    control.addControlListener(null);
  }

  @Test
  public void removeControlListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Control control = new TestControl(this.shell, 0);
    control.removeControlListener(null);
  }

  @Test
  public void controlListenerHandling() {
    Control control = new TestControl(this.shell, 0);
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

    Control control = new TestControl(this.shell, 0);
    control.addDragDetectListener(null);
  }

  @Test
  public void removeDragDetectListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Control control = new TestControl(this.shell, 0);
    control.removeDragDetectListener(null);
  }

  @Test
  public void dragDetectListenerHandling() {
    Control control = new TestControl(this.shell, 0);
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

    Control control = new TestControl(this.shell, 0);
    control.addFocusListener(null);
  }

  @Test
  public void removeFocusListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Control control = new TestControl(this.shell, 0);
    control.removeFocusListener(null);
  }

  @Test
  public void focusListenerHandling() {
    Control control = new TestControl(this.shell, 0);
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

    Control control = new TestControl(this.shell, 0);
    control.addHelpListener(null);
  }

  @Test
  public void removeHelpListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Control control = new TestControl(this.shell, 0);
    control.removeHelpListener(null);
  }

  @Test
  public void helpListenerHandling() {
    Control control = new TestControl(this.shell, 0);
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

    Control control = new TestControl(this.shell, 0);
    control.addKeyListener(null);
  }

  @Test
  public void removeKeyListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Control control = new TestControl(this.shell, 0);
    control.removeKeyListener(null);
  }

  @Test
  public void keyListenerHandling() {
    Control control = new TestControl(this.shell, 0);
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

    Control control = new TestControl(this.shell, 0);
    control.addMenuDetectListener(null);
  }

  @Test
  public void removeMenuDetectListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Control control = new TestControl(this.shell, 0);
    control.removeMenuDetectListener(null);
  }

  @Test
  public void menuDetectListenerHandling() {
    Control control = new TestControl(this.shell, 0);
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

    Control control = new TestControl(this.shell, 0);
    control.addMouseListener(null);
  }

  @Test
  public void removeMouseListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Control control = new TestControl(this.shell, 0);
    control.removeMouseListener(null);
  }

  @Test
  public void mouseListenerHandling() {
    Control control = new TestControl(this.shell, 0);
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

    Control control = new TestControl(this.shell, 0);
    control.addMouseMoveListener(null);
  }


  @Test
  public void removeMouseMoveListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Control control = new TestControl(this.shell, 0);
    control.removeMouseMoveListener(null);
  }

  @Test
  public void mouseMoveListenerHandling() {
    Control control = new TestControl(this.shell, 0);
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

    Control control = new TestControl(this.shell, 0);
    control.addMouseTrackListener(null);
  }

  @Test
  public void removeMouseTrackListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Control control = new TestControl(this.shell, 0);
    control.removeMouseTrackListener(null);
  }

  @Test
  public void mouseTrackListenerHandling() {
    Control control = new TestControl(this.shell, 0);
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

  @Test
  public void addMouseWheelListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Control control = new TestControl(this.shell, 0);
    control.addMouseWheelListener(null);
  }

  @Test
  public void removeMouseWheelListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Control control = new TestControl(this.shell, 0);
    control.removeMouseWheelListener(null);
  }

  @Test
  public void mouseWheelListenerHandling() {
    Control control = new TestControl(this.shell, 0);
    TestMouseWheelListener listener = new TestMouseWheelListener();
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

    assertThat(control.getListeners(SWT.MouseWheel).length, is(0));
    control.addMouseWheelListener(listener);
    assertThat(control.getListeners(SWT.MouseWheel).length, is(1));

    control.notifyListeners(SWT.MouseWheel, event1);
    control.notifyListeners(SWT.MouseWheel, event2);

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
    control.removeMouseWheelListener(listener);
    assertThat(control.getListeners(SWT.MouseWheel).length, is(0));

    control.notifyListeners(SWT.MouseWheel, event1);
    control.notifyListeners(SWT.MouseWheel, event2);

    assertThat(listener.events.size(), is(0));
  }

  @Test
  public void addPaintListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Control control = new TestControl(this.shell, 0);
    control.addPaintListener(null);
  }

  @Test
  public void removePaintListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Control control = new TestControl(this.shell, 0);
    control.removePaintListener(null);
  }

  @Test
  public void paintListenerHandling() {
    Control control = new TestControl(this.shell, 0);
    TestPaintListener listener = new TestPaintListener();
    TestEvent event1 = new TestEvent(1);
    TestEvent event2 = new TestEvent(2);

    event1.count = 1;
    event1.gc = new GC();
    event1.height = 2;
    event1.width = 3;
    event1.x = 4;
    event1.y = 5;

    event2.count = 6;
    event2.gc = new GC();
    event2.height = 7;
    event2.width = 8;
    event2.x = 9;
    event2.y = 10;

    assertThat(control.getListeners(SWT.Paint).length, is(0));
    control.addPaintListener(listener);
    assertThat(control.getListeners(SWT.Paint).length, is(1));

    control.notifyListeners(SWT.Paint, event1);
    control.notifyListeners(SWT.Paint, event2);

    assertThat(listener.events.size(), is(2));
    assertThat(listener.events.get(0), is(event(display, control, 1)));
    assertThat(listener.events.get(0).count, is(1));
    assertThat(listener.events.get(0).gc, is(notNullValue()));
    assertThat(listener.events.get(0).height, is(2));
    assertThat(listener.events.get(0).width, is(3));
    assertThat(listener.events.get(0).x, is(4));
    assertThat(listener.events.get(0).y, is(5));
    assertThat(listener.events.get(1), is(event(display, control, 2)));
    assertThat(listener.events.get(1).count, is(6));
    assertThat(listener.events.get(1).gc, is(notNullValue()));
    assertThat(listener.events.get(1).height, is(7));
    assertThat(listener.events.get(1).width, is(8));
    assertThat(listener.events.get(1).x, is(9));
    assertThat(listener.events.get(1).y, is(10));

    listener.events.clear();
    control.removePaintListener(listener);
    assertThat(control.getListeners(SWT.Paint).length, is(0));

    control.notifyListeners(SWT.Paint, event1);
    control.notifyListeners(SWT.Paint, event2);

    assertThat(listener.events.size(), is(0));
  }

  @Test
  public void addTraverseListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Control control = new TestControl(this.shell, 0);
    control.addTraverseListener(null);
  }

  @Test
  public void removeTraverseListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Control control = new TestControl(this.shell, 0);
    control.removeTraverseListener(null);
  }

  @Test
  public void traverseListenerHandling() {
    Control control = new TestControl(this.shell, 0);
    TestTraverseListener listener = new TestTraverseListener();
    TestEvent event1 = new TestEvent(1);
    TestEvent event2 = new TestEvent(2);

    event1.detail = 1;

    event2.detail = 2;

    assertThat(control.getListeners(SWT.Traverse).length, is(0));
    control.addTraverseListener(listener);
    assertThat(control.getListeners(SWT.Traverse).length, is(1));

    control.notifyListeners(SWT.Traverse, event1);
    control.notifyListeners(SWT.Traverse, event2);

    assertThat(listener.events.size(), is(2));
    assertThat(listener.events.get(0), is(event(display, control, 1)));
    assertThat(listener.events.get(0).detail, is(1));
    assertThat(listener.events.get(1), is(event(display, control, 2)));
    assertThat(listener.events.get(1).detail, is(2));

    listener.events.clear();
    control.removeTraverseListener(listener);
    assertThat(control.getListeners(SWT.Traverse).length, is(0));

    control.notifyListeners(SWT.Traverse, event1);
    control.notifyListeners(SWT.Traverse, event2);

    assertThat(listener.events.size(), is(0));
  }

  @Test
  public void setLayoutDataInvalidLayoutData() {
    exception.expect(swtCode(SWT.ERROR_INVALID_ARGUMENT));

    Control control = new TestControl(this.shell, 0);
    control.setLayoutData(1);
  }

  @Test
  public void getsetLayoutDataSuccess() {
    Object data = new SWTObject() {};
    Control control = new TestControl(this.shell, 0);

    assertThat(control.getLayoutData(), is(nullValue()));
    control.setLayoutData(data);
    assertThat(control.getLayoutData(), is(sameInstance(data)));

    // TODO Check what send to the client
  }

  @Test
  public void getBounds() {
    HashMap<String, Object> bounds = new HashMap<String, Object>();
    bounds.put("x", 1);
    bounds.put("y", 2);
    bounds.put("width", 3);
    bounds.put("height", 4);

    getClientTasks().setCallMethodResult(bounds);

    Control control = new TestControl(this.shell, 0);
    Rectangle rect = control.getBounds();

    assertThat(rect.x, is(1));
    assertThat(rect.y, is(2));
    assertThat(rect.width, is(3));
    assertThat(rect.height, is(4));

    assertThat(getClientTasks(), is(callRequest(control, "getBounds")));
  }

  @Test
  public void setBounds() {
    Control control = new TestControl(this.shell, 0);
    control.setBounds(1, 2, 3, 4);

    assertThat(getClientTasks(), is(callRequest(control, "setBounds", 1, 2, 3, 4)));
  }
}
