package org.eclipse.swt.test;

import static org.eclipse.swt.test.SWTExceptionMatcher.swtCode;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.server.DisplayPool;
import org.eclipse.swt.server.Key;
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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class DisposeTest {
  private Class<? extends Widget> testClass = null;
  private String method = null;
  private Object[] arguments = null;

  private Display display = null;
  private Shell shell = null;
  private Widget widget = null;

  public DisposeTest(Class<? extends Widget> testClass, String method, Object arguments[]) {
    this.testClass = testClass;
    this.method = method;
    this.arguments = arguments;
  }

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Parameters
  public static Collection<Object[]>testData() {
    Object[][] data = new Object[][] {
        { TestWidget.class, "getStyle", new Object[] {} },
    };
    return (Arrays.asList(data));
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

    Class<?> parameterTypes[] = new Class<?>[this.arguments.length];
    for (int i = 0; i < this.arguments.length; i++) {
      parameterTypes[i] = this.arguments[i].getClass();
    }

    Method method = testClass.getMethod(this.method, parameterTypes);

    this.widget.dispose();
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
