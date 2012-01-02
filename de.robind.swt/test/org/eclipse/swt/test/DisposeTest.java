package org.eclipse.swt.test;

import static org.eclipse.swt.test.SWTExceptionMatcher.swtCode;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.server.DisplayPool;
import org.eclipse.swt.server.Key;
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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class DisposeTest {
  private Class<? extends Widget> testClass = null;
  private String method = null;
  private Class<?>[] parameterTypes = null;
  private Object[] arguments = null;

  private Display display = null;
  private Shell shell = null;
  private Widget widget = null;

  public DisposeTest(Class<? extends Widget> testClass,
      String method, Class<?> parameterTypes[], Object arguments[]) {

    this.testClass = testClass;
    this.method = method;
    this.parameterTypes = parameterTypes;
    this.arguments = arguments;
  }

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Parameters
  public static Collection<Object[]>testData() {
    Object[][] data = new Object[][] {
        { TestWidget.class, "getStyle", p(), a() },
        { TestWidget.class, "addListener", p(int.class, Listener.class), a(0, new TestListener()) },
        { TestWidget.class, "removeListener", p(int.class, Listener.class), a(0, new TestListener()) },
        { TestWidget.class, "isListening", p(int.class), a(0) },
        { TestWidget.class, "notifyListeners", p(int.class, Event.class), a(0, new Event()) },
        { TestWidget.class, "getListeners", p(int.class), a(0) },
        { TestWidget.class, "addDisposeListener", p(DisposeListener.class), a(new TestDisposeListener()) },
        { TestWidget.class, "removeDisposeListener", p(DisposeListener.class), a(new TestDisposeListener()) },
        { TestWidget.class, "getData", p(), a() },
        { TestWidget.class, "getData", p(String.class), a("foo") },
        { TestWidget.class, "setData", p(Object.class), a((Object)null) },
        { TestWidget.class, "setData", p(String.class, Object.class), a("foo", "bar") },
        { TestWidget.class, "getDisplay", p(), a() },
        { TestWidget.class, "reskin", p(int.class), a(0) },
    };
    return (Arrays.asList(data));
  }

  private static Class<?>[] p(Class<?>... parameterTypes) {
    return (parameterTypes);
  }

  private static Object[] a(Object... arguments) {
    return (arguments);
  }

  @BeforeClass
  public static void beforeClass() {
    System.setProperty("de.robind.swt.clienttasks", TestClientTasks.class.getName());
  }

  @AfterClass
  public static void afterClass() {
    System.clearProperty("de.robind.swt.clienttasks");
  }

  @Before
  public void before() throws Exception {
    DisplayPool.getInstance().offerKey(new Key() {});
    this.display = new Display();
    this.shell = new Shell(this.display);

    Constructor<? extends Widget> ctor =
        this.testClass.getConstructor(Widget.class, int.class);
    this.widget = ctor.newInstance(this.shell, 0);
  }

  @After
  public void after() {
    this.widget = null;
    this.shell = null;
    this.display = null;
  }

  @Test
  public void testDispose() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_WIDGET_DISPOSED));

    this.widget.dispose();

    Method method = testClass.getMethod(this.method, this.parameterTypes);
    try {
      method.invoke(this.widget, this.arguments);
    } catch (InvocationTargetException e) {
      throw e.getCause();
    }
  }

  private static class TestWidget extends Widget {
    public TestWidget(Widget parent, int style) throws SWTException {
      super(parent, style);
    }
  }
}
