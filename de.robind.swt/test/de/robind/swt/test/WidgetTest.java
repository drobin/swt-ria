package de.robind.swt.test;

import static de.robind.swt.test.utils.ClientTaskMatcher.createRequest;
import static de.robind.swt.test.utils.SWTExceptionMatcher.swtCode;
import static de.robind.swt.test.utils.SWTTestUtils.asyncExec;
import static de.robind.swt.test.utils.TypedEventMatcher.event;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;

import java.util.concurrent.Callable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.test.TestWidget;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.junit.Test;

import de.robind.swt.test.utils.TestDisposeListener;
import de.robind.swt.test.utils.TestEvent;
import de.robind.swt.test.utils.TestListener;


public class WidgetTest extends AbstractWidgetTest {
  @Test
  public void ctorNullParent() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    new TestWidget(null, 0);
  }

  @Test
  public void ctorParentDisposed() {
    exception.expect(swtCode(SWT.ERROR_INVALID_ARGUMENT));

    Shell shell = new Shell(this.display);
    shell.dispose();

    new TestWidget(shell, 0);
  }

  @Test
  public void ctorInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    asyncExec(new Callable<Widget>() {
      public Widget call() throws Exception {
        return (new TestWidget(shell, 0));
      }
    });
  }

  @Test
  public void ctorInvalidSubclass() {
    exception.expect(swtCode(SWT.ERROR_INVALID_SUBCLASS));
    new Widget(this.shell, 0) {};
  }

  @Test
  public void ctorRequest() {
    Widget widget = new TestWidget(this.shell, 4711);
    assertThat(getClientTasks(), is(createRequest(this.display, Display.class)));
    assertThat(getClientTasks(), is(createRequest(this.shell, Shell.class, this.display)));
    assertThat(getClientTasks(), is(createRequest(widget, TestWidget.class, this.shell, 4711)));
  }

  @Test
  public void ctorStyle() {
    Widget widget = new TestWidget(this.shell, 4711);
    assertThat(widget.getStyle(), is(4711));
  }

  @Test
  public void addListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Widget widget = new TestWidget(this.shell, 0);
    widget.addListener(0, null);
  }

  @Test
  public void removeListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Widget widget = new TestWidget(this.shell, 0);
    widget.removeListener(0, null);
  }

  @Test
  public void notifyListenersNullEvent() {
    TestListener listener = new TestListener();
    Widget widget = new TestWidget(this.shell, 0);

    widget.addListener(4711, listener);
    widget.notifyListeners(4711, null);

    assertThat(listener.handledEvents.size(), is(1));
    assertThat(listener.handledEvents.get(0).type, is(4711));
    assertThat(listener.handledEvents.get(0).display, is(sameInstance(this.display)));
    assertThat(listener.handledEvents.get(0).widget, is(sameInstance(widget)));
    assertThat(listener.handledEvents.get(0).time, is(0));
  }

  @Test
  public void notifyListenersWithEvent() {
    TestListener listener = new TestListener();

    Object testData = new Object();
    TestEvent event = new TestEvent(testData);
    event.type = 4711;

    Widget widget = new TestWidget(this.shell, 0);

    widget.addListener(4711, listener);
    widget.notifyListeners(4711, event);

    assertThat(listener.handledEvents.size(), is(1));
    assertThat(listener.handledEvents.get(0).type, is(4711));
    assertThat(listener.handledEvents.get(0).display, is(sameInstance(this.display)));
    assertThat(listener.handledEvents.get(0).widget, is(sameInstance(widget)));
    assertThat(listener.handledEvents.get(0).time, is(0));
  }

  @Test
  public void notifyListenersDifferentType() {
    TestListener listener = new TestListener();

    Object testData = new Object();
    TestEvent event = new TestEvent(testData);
    event.type = 42;

    Widget widget = new TestWidget(this.shell, 0);

    widget.addListener(4711, listener);
    widget.notifyListeners(4711, event);

    assertThat(listener.handledEvents.size(), is(1));
    assertThat(listener.handledEvents.get(0).type, is(4711));
    assertThat(listener.handledEvents.get(0).display, is(sameInstance(this.display)));
    assertThat(listener.handledEvents.get(0).widget, is(sameInstance(widget)));
    assertThat(listener.handledEvents.get(0).time, is(0));
  }

  @Test
  public void untypedListenerHandling() {
    TestListener listener = new TestListener();
    Widget widget = new TestWidget(this.shell, 0);
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

    Widget widget = new TestWidget(this.shell, 0);
    widget.addDisposeListener(null);
  }

  @Test
  public void removeDisposeListenerNullListener() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Widget widget = new TestWidget(this.shell, 0);
    widget.removeDisposeListener(null);
  }

  @Test
  public void disposeListenerHandling() {
    Widget widget = new TestWidget(this.shell, 0);
    TestDisposeListener listener = new TestDisposeListener();
    Object event1Data = new Object();
    Object event2Data = new Object();
    TestEvent event1 = new TestEvent(event1Data);
    TestEvent event2 = new TestEvent(event2Data);

    assertThat(widget.getListeners(SWT.Dispose).length, is(0));
    widget.addDisposeListener(listener);
    assertThat(widget.getListeners(SWT.Dispose).length, is(1));

    widget.notifyListeners(SWT.Dispose, event1);
    widget.notifyListeners(SWT.Dispose, event2);

    assertThat(listener.handledEvents.size(), is(2));
    assertThat(listener.handledEvents.get(0), is(event(this.display, widget, event1Data)));
    assertThat(listener.handledEvents.get(1), is(event(this.display, widget, event2Data)));

    listener.handledEvents.clear();
    widget.removeDisposeListener(listener);
    assertThat(widget.getListeners(SWT.Dispose).length, is(0));

    widget.notifyListeners(SWT.Dispose, event1);
    widget.notifyListeners(SWT.Dispose, event2);

    assertThat(listener.handledEvents.size(), is(0));
  }

  @Test
  public void dataHandling() {
    Object data = new Object();
    Widget widget = new TestWidget(this.shell, 0);

    assertThat(widget.getData(), is(nullValue()));
    widget.setData(data);
    assertThat(widget.getData(), is(sameInstance(data)));
  }

  @Test
  public void getDataWithKeyNullKey() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Widget widget = new TestWidget(this.shell, 0);
    widget.getData(null);
  }

  @Test
  public void setDataWithKeyNullKey() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    Widget widget = new TestWidget(this.shell, 0);
    widget.setData(null, "foo");
  }

  @Test
  public void dataWithKeyHandling() {
    Object fooData = new Object();
    Object barData = new Object();
    Widget widget = new TestWidget(this.shell, 0);

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

  @Test
  public void getParent() {
    Widget widget = new TestWidget(this.shell, 0);
    assertThat(this.shell.getParent(), is(nullValue()));
    assertThat(widget.getParent(), is(sameInstance((Widget)this.shell)));

    Widget other = new TestWidget(widget, 0);
    assertThat(other.getParent(), is(sameInstance(widget)));
    assertThat(widget.getParent(), is(sameInstance((Widget)this.shell)));
    assertThat(this.shell.getParent(), is(nullValue()));
  }
}
