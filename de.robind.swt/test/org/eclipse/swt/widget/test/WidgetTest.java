package org.eclipse.swt.widget.test;

import static org.eclipse.swt.test.SWTExceptionMatcher.swtCode;
import static org.eclipse.swt.test.SWTTestUtils.asyncExec;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.concurrent.Callable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.server.DisplayPool;
import org.eclipse.swt.server.Key;
import org.eclipse.swt.test.TestClientTasks;
import org.eclipse.swt.test.TestDisposeListener;
import org.eclipse.swt.test.TestEvent;
import org.eclipse.swt.test.TestListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
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
    DisplayPool.getInstance().pushKey(new Key() {});
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

  @Test
  public void addListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Widget widget = new Widget(this.shell, 0) {};
    widget.addListener(0, null);
  }

  @Test
  public void addListenerDisposed() {
    exception.expect(swtCode(SWT.ERROR_WIDGET_DISPOSED));

    Widget widget = new Widget(this.shell, 0) {};
    widget.dispose();
    widget.addListener(0, new  TestListener());
  }

  @Test
  public void addListenerInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    final Widget widget = new Widget(this.shell, 0) {};
    asyncExec(new Callable<Widget>() {
      public Widget call() throws Exception {
        widget.addListener(0, new TestListener());
        return (widget);
      }
    });
  }

  @Test
  public void removeListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Widget widget = new Widget(this.shell, 0) {};
    widget.removeListener(0, null);
  }

  @Test
  public void removeListenerDisposed() {
    exception.expect(swtCode(SWT.ERROR_WIDGET_DISPOSED));

    Widget widget = new Widget(this.shell, 0) {};
    widget.dispose();
    widget.removeListener(0, new TestListener());
  }

  @Test
  public void removeListenerInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    final Widget widget = new Widget(this.shell, 0) {};
    asyncExec(new Callable<Widget>() {
      public Widget call() throws Exception {
        widget.removeListener(0, new TestListener());
        return (widget);
      }
    });
  }

  @Test
  public void isListeningDisposed() {
    exception.expect(swtCode(SWT.ERROR_WIDGET_DISPOSED));

    Widget widget = new Widget(this.shell, 0) {};
    widget.dispose();
    widget.isListening(0);
  }

  @Test
  public void isListeningInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    final Widget widget = new Widget(this.shell, 0) {};
    asyncExec(new Callable<Widget>() {
      public Widget call() throws Exception {
        widget.isListening(0);
        return (widget);
      }
    });
  }

  @Test
  public void getListenersDisposed() {
    exception.expect(swtCode(SWT.ERROR_WIDGET_DISPOSED));

    Widget widget = new Widget(this.shell, 0) {};
    widget.dispose();
    widget.getListeners(0);
  }

  @Test
  public void getListenersInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    final Widget widget = new Widget(this.shell, 0) {};
    asyncExec(new Callable<Widget>() {
      public Widget call() throws Exception {
        widget.getListeners(0);
        return (widget);
      }
    });
  }

  @Test
  public void untypedListenerHandling() {
    TestListener listener = new TestListener();
    Widget widget = new Widget(this.shell, 0) {};
    Event event1 = new Event();
    Event event2 = new Event();

    Listener list[] = widget.getListeners(4711);
    assertThat(list.length, is(0));

    assertThat(widget.isListening(4711), is(false));
    widget.addListener(4711, listener);
    assertThat(widget.isListening(4711), is(true));

    list = widget.getListeners(4711);
    assertThat(list.length, is(1));
    assertThat((TestListener)list[0], is(sameInstance(listener)));

    widget.notifyListeners(4711, event1);
    widget.notifyListeners(4711, event2);

    assertThat(listener.handledEvents.size(), is(2));
    assertThat(listener.handledEvents.get(0), is(sameInstance(event1)));
    assertThat(listener.handledEvents.get(1), is(sameInstance(event2)));

    widget.removeListener(4711, listener);
    list = widget.getListeners(4711);
    assertThat(list.length, is(0));

    listener.handledEvents.clear();
    widget.notifyListeners(4711, event1);
    widget.notifyListeners(4711, event2);
    assertThat(listener.handledEvents.size(), is(0));
  }

  @Test
  public void addDisposeListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Widget widget = new Widget(this.shell, 0) {};
    widget.addDisposeListener(null);
  }

  @Test
  public void addDisposeListenerDisposed() {
    exception.expect(swtCode(SWT.ERROR_WIDGET_DISPOSED));

    Widget widget = new Widget(this.shell, 0) {};
    widget.dispose();
    widget.addDisposeListener(new TestDisposeListener());
  }

  @Test
  public void addDisposeListenerInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    final Widget widget = new Widget(this.shell, 0) {};
    asyncExec(new Callable<Widget>() {
      public Widget call() throws Exception {
        widget.addDisposeListener(new TestDisposeListener());
        return (widget);
      }
    });
  }

  @Test
  public void removeDisposeListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Widget widget = new Widget(this.shell, 0) {};
    widget.removeDisposeListener(null);
  }

  @Test
  public void removeDisposeListenerDisposed() {
    exception.expect(swtCode(SWT.ERROR_WIDGET_DISPOSED));

    Widget widget = new Widget(this.shell, 0) {};
    widget.dispose();
    widget.removeDisposeListener(new TestDisposeListener());
  }

  @Test
  public void removeDisposeListenerInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    final Widget widget = new Widget(this.shell, 0) {};
    asyncExec(new Callable<Widget>() {
      public Widget call() throws Exception {
        widget.removeDisposeListener(new TestDisposeListener());
        return (widget);
      }
    });
  }

  @Test
  public void disposeListenerHandling() {
    Widget widget = new Widget(this.shell, 0) {};
    TestDisposeListener listener = new TestDisposeListener();
    Object event2Data = new Object();
    TestEvent event1 = new TestEvent(SWT.Dispose, this.display, widget, 1, null);
    TestEvent event2 = new TestEvent(SWT.Dispose, this.display, widget, 2, event2Data);

    assertThat(widget.getListeners(SWT.Dispose).length, is(0));
    widget.addDisposeListener(listener);
    assertThat(widget.getListeners(SWT.Dispose).length, is(1));

    widget.notifyListeners(SWT.Dispose, event1);
    widget.notifyListeners(SWT.Dispose, event2);

    assertThat(listener.handledEvents.size(), is(2));
    assertThat(listener.handledEvents.get(0).display, is(sameInstance(this.display)));
    assertThat(listener.handledEvents.get(0).widget, is(sameInstance(widget)));
    assertThat(listener.handledEvents.get(0).time, is(1));
    assertThat(listener.handledEvents.get(0).data, is(nullValue()));
    assertThat(listener.handledEvents.get(1).display, is(sameInstance(this.display)));
    assertThat(listener.handledEvents.get(1).widget, is(sameInstance(widget)));
    assertThat(listener.handledEvents.get(1).time, is(2));
    assertThat(listener.handledEvents.get(1).data, is(sameInstance(event2Data)));

    listener.handledEvents.clear();
    widget.removeDisposeListener(listener);
    assertThat(widget.getListeners(SWT.Dispose).length, is(0));

    widget.notifyListeners(SWT.Dispose, event1);
    widget.notifyListeners(SWT.Dispose, event2);

    assertThat(listener.handledEvents.size(), is(0));
  }

  @Test
  public void getDataDisposed() {
    exception.expect(swtCode(SWT.ERROR_WIDGET_DISPOSED));

    Widget widget = new Widget(this.shell, 0) {};
    widget.dispose();
    widget.getData();
  }

  @Test
  public void getDataInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    final Widget widget = new Widget(this.shell, 0) {};
    asyncExec(new Callable<Widget>() {
      public Widget call() throws Exception {
        widget.getData();
        return (widget);
      }
    });
  }

  @Test
  public void setDataDisposed() {
    exception.expect(swtCode(SWT.ERROR_WIDGET_DISPOSED));

    Widget widget = new Widget(this.shell, 0) {};
    widget.dispose();
    widget.setData(null);
  }

  @Test
  public void setDataInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    final Widget widget = new Widget(this.shell, 0) {};
    asyncExec(new Callable<Widget>() {
      public Widget call() throws Exception {
        widget.setData(null);
        return (widget);
      }
    });
  }

  @Test
  public void dataHandling() {
    Object data = new Object();
    Widget widget = new Widget(this.shell, 0) {};

    assertThat(widget.getData(), is(nullValue()));
    widget.setData(data);
    assertThat(widget.getData(), is(sameInstance(data)));
  }

  @Test
  public void getDataWithKeyNullKey() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Widget widget = new Widget(this.shell, 0) {};
    widget.getData(null);
  }

  @Test
  public void getDataWithKeyDisposed() {
    exception.expect(swtCode(SWT.ERROR_WIDGET_DISPOSED));

    Widget widget = new Widget(this.shell, 0) {};
    widget.dispose();
    widget.getData("foo");
  }

  @Test
  public void getDataWithKeyInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    final Widget widget = new Widget(this.shell, 0) {};
    asyncExec(new Callable<Widget>() {
      public Widget call() throws Exception {
        widget.getData("foo");
        return (widget);
      }
    });
  }

  @Test
  public void setDataWithKeyNullKey() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Widget widget = new Widget(this.shell, 0) {};
    widget.setData(null, "foo");
  }

  @Test
  public void setDataWithKeyDisposed() {
    exception.expect(swtCode(SWT.ERROR_WIDGET_DISPOSED));

    Widget widget = new Widget(this.shell, 0) {};
    widget.dispose();
    widget.setData("foo", "bar");
  }

  @Test
  public void setDataWithKeyInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    final Widget widget = new Widget(this.shell, 0) {};
    asyncExec(new Callable<Widget>() {
      public Widget call() throws Exception {
        widget.setData("foo", "bar");
        return (widget);
      }
    });
  }

  @Test
  public void dataWithKeyHandling() {
    Object fooData = new Object();
    Object barData = new Object();
    Widget widget = new Widget(this.shell, 0) {};

    assertThat(widget.getData("foo"), is(nullValue()));
    assertThat(widget.getData("bar"), is(nullValue()));

    widget.setData("foo", fooData);
    widget.setData("bar", barData);

    assertThat(widget.getData("foo"), is(sameInstance(fooData)));
    assertThat(widget.getData("bar"), is(sameInstance(barData)));

    widget.setData("foo", null);
    widget.setData("bar", null);

    assertThat(widget.getData("foo"), is(nullValue()));
    assertThat(widget.getData("bar"), is(nullValue()));
  }
}
