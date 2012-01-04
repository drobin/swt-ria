package de.robind.swt.test;

import static de.robind.swt.test.utils.SWTExceptionMatcher.swtCode;
import static de.robind.swt.test.utils.SWTTestUtils.asyncExec;
import static de.robind.swt.test.utils.TypedEventMatcher.event;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;

import java.util.concurrent.Callable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.server.DisplayPool;
import org.eclipse.swt.server.Key;
import org.eclipse.swt.widgets.Button;
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
import de.robind.swt.test.utils.TestEvent;
import de.robind.swt.test.utils.TestSelectionListener;

public class ButtonTest {private Display display = null;
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
    new Button(null, 0);
  }

  @Test
  public void ctorParentDisposed() {
    exception.expect(swtCode(SWT.ERROR_INVALID_ARGUMENT));

    this.shell.dispose();
    new Button(this.shell, 0);
  }

  @Test
  public void ctorInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    asyncExec(new Callable<Button>() {
      public Button call() throws Exception {
        return (new Button(shell, 0));
      }
    });
  }

  @Test
  public void ctorInvalidSubclass() {
    exception.expect(swtCode(SWT.ERROR_INVALID_SUBCLASS));
    new Button(this.shell, 0) {};
  }

  @Test
  public void ctorStyle() {
    Button button = new Button(this.shell, 4711);
    assertThat(button.getStyle(), is(4711));
  }

  @Test
  public void addSelectionListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Button button = new Button(this.shell, 0);
    button.addSelectionListener(null);
  }

  @Test
  public void removeSelectionListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Button button = new Button(this.shell, 0);
    button.removeSelectionListener(null);
  }

  @Test
  public void selectionListenerHandling() {
    Button button = new Button(this.shell, 0);
    TestSelectionListener listener = new TestSelectionListener();
    TestEvent event1 = new TestEvent(1);
    TestEvent event2 = new TestEvent(2);
    TestEvent event3 = new TestEvent(3);
    TestEvent event4 = new TestEvent(4);

    event1.item = button;
    event1.detail = 1;
    event1.x = 2;
    event1.y = 3;
    event1.width = 4;
    event1.height = 5;
    event1.stateMask = 6;
    event1.text = "a";
    event1.doit = true;

    event2.item = shell;
    event2.detail = 7;
    event2.x = 8;
    event2.y = 9;
    event2.width = 10;
    event2.height = 11;
    event2.stateMask = 12;
    event2.text = "b";
    event2.doit = false;

    event3.item = button;
    event3.detail = 13;
    event3.x = 14;
    event3.y = 15;
    event3.width = 16;
    event3.height = 17;
    event3.stateMask = 18;
    event3.text = "c";
    event3.doit = true;

    event4.item = shell;
    event4.detail = 19;
    event4.x = 20;
    event4.y = 21;
    event4.width = 22;
    event4.height = 23;
    event4.stateMask = 24;
    event4.text = "d";
    event4.doit = false;

    assertThat(button.getListeners(SWT.Selection).length, is(0));
    assertThat(button.getListeners(SWT.DefaultSelection).length, is(0));
    button.addSelectionListener(listener);
    assertThat(button.getListeners(SWT.Selection).length, is(1));
    assertThat(button.getListeners(SWT.DefaultSelection).length, is(1));

    button.notifyListeners(SWT.Selection, event1);
    button.notifyListeners(SWT.DefaultSelection, event2);
    button.notifyListeners(SWT.Selection, event3);
    button.notifyListeners(SWT.DefaultSelection, event4);

    assertThat(listener.selectedEvents.size(), is(2));
    assertThat(listener.selectedEvents.get(0), is(event(display, button, 1)));
    assertThat(listener.selectedEvents.get(0).item, is(sameInstance((Widget)button)));
    assertThat(listener.selectedEvents.get(0).detail, is(1));
    assertThat(listener.selectedEvents.get(0).x, is(2));
    assertThat(listener.selectedEvents.get(0).y, is(3));
    assertThat(listener.selectedEvents.get(0).width, is(4));
    assertThat(listener.selectedEvents.get(0).height, is(5));
    assertThat(listener.selectedEvents.get(0).stateMask, is(6));
    assertThat(listener.selectedEvents.get(0).text, is(equalTo("a")));
    assertThat(listener.selectedEvents.get(0).doit, is(true));
    assertThat(listener.selectedEvents.get(1), is(event(display, button, 3)));
    assertThat(listener.selectedEvents.get(1).item, is(sameInstance((Widget)button)));
    assertThat(listener.selectedEvents.get(1).detail, is(13));
    assertThat(listener.selectedEvents.get(1).x, is(14));
    assertThat(listener.selectedEvents.get(1).y, is(15));
    assertThat(listener.selectedEvents.get(1).width, is(16));
    assertThat(listener.selectedEvents.get(1).height, is(17));
    assertThat(listener.selectedEvents.get(1).stateMask, is(18));
    assertThat(listener.selectedEvents.get(1).text, is(equalTo("c")));
    assertThat(listener.selectedEvents.get(1).doit, is(true));
    assertThat(listener.defaultSelectedEvents.size(), is(2));
    assertThat(listener.defaultSelectedEvents.get(0), is(event(display, button, 2)));
    assertThat(listener.defaultSelectedEvents.get(0).item, is(sameInstance((Widget)shell)));
    assertThat(listener.defaultSelectedEvents.get(0).detail, is(7));
    assertThat(listener.defaultSelectedEvents.get(0).x, is(8));
    assertThat(listener.defaultSelectedEvents.get(0).y, is(9));
    assertThat(listener.defaultSelectedEvents.get(0).width, is(10));
    assertThat(listener.defaultSelectedEvents.get(0).height, is(11));
    assertThat(listener.defaultSelectedEvents.get(0).stateMask, is(12));
    assertThat(listener.defaultSelectedEvents.get(0).text, is(equalTo("b")));
    assertThat(listener.defaultSelectedEvents.get(0).doit, is(false));
    assertThat(listener.defaultSelectedEvents.get(1), is(event(display, button, 4)));
    assertThat(listener.defaultSelectedEvents.get(1).item, is(sameInstance((Widget)shell)));
    assertThat(listener.defaultSelectedEvents.get(1).detail, is(19));
    assertThat(listener.defaultSelectedEvents.get(1).x, is(20));
    assertThat(listener.defaultSelectedEvents.get(1).y, is(21));
    assertThat(listener.defaultSelectedEvents.get(1).width, is(22));
    assertThat(listener.defaultSelectedEvents.get(1).height, is(23));
    assertThat(listener.defaultSelectedEvents.get(1).stateMask, is(24));
    assertThat(listener.defaultSelectedEvents.get(1).text, is(equalTo("d")));
    assertThat(listener.defaultSelectedEvents.get(1).doit, is(false));

    listener.clearEvents();
    button.removeSelectionListener(listener);
    assertThat(button.getListeners(SWT.Selection).length, is(0));
    assertThat(button.getListeners(SWT.DefaultSelection).length, is(0));

    button.notifyListeners(SWT.Selection, event1);
    button.notifyListeners(SWT.DefaultSelection, event2);
    button.notifyListeners(SWT.Selection, event3);
    button.notifyListeners(SWT.DefaultSelection, event4);

    assertThat(listener.selectedEvents.size(), is(0));
    assertThat(listener.defaultSelectedEvents.size(), is(0));
  }
}
