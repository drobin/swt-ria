package org.eclipse.swt.test;

import static org.eclipse.swt.test.utils.SWTExceptionMatcher.swtCode;
import static org.eclipse.swt.test.utils.SWTTestUtils.asyncExec;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.Callable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.server.DisplayPool;
import org.eclipse.swt.server.Key;
import org.eclipse.swt.test.utils.TestClientTasks;
import org.eclipse.swt.test.utils.TestControlListener;
import org.eclipse.swt.test.utils.TestDisposeListener;
import org.eclipse.swt.test.utils.TestDragDetectListener;
import org.eclipse.swt.test.utils.TestFocusListener;
import org.eclipse.swt.test.utils.TestHelpListener;
import org.eclipse.swt.test.utils.TestKeyListener;
import org.eclipse.swt.test.utils.TestListener;
import org.eclipse.swt.test.utils.TestMenuDetectListener;
import org.eclipse.swt.test.utils.TestMouseListener;
import org.eclipse.swt.test.utils.TestMouseMoveListener;
import org.eclipse.swt.test.utils.TestMouseTrackListener;
import org.eclipse.swt.test.utils.TestMouseWheelListener;
import org.eclipse.swt.test.utils.TestPaintListener;
import org.eclipse.swt.test.utils.TestTraverseListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
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


@RunWith(Parameterized.class)
public class InvalidAccessTest {
  private Class<? extends Widget> testClass = null;
  private String method = null;
  private Class<?>[] parameterTypes = null;
  private Object[] arguments = null;

  private Display display = null;
  private Shell shell = null;
  private Widget widget = null;

  public InvalidAccessTest(Class<? extends Widget> testClass,
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
        { List.class, "getSelectionIndices", p(), a() },
        { List.class, "getTopIndex", p(), a() },
        { List.class, "deselectAll", p(), a() },
        { List.class, "removeAll", p(), a() },
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
        { TestWidget.class, "reskin", p(int.class), a(0) },
        { TestControl.class, "addControlListener", p(ControlListener.class), a(new TestControlListener()) },
        { TestControl.class, "addDragDetectListener", p(DragDetectListener.class), a(new TestDragDetectListener()) },
        { TestControl.class, "addFocusListener", p(FocusListener.class), a(new TestFocusListener()) },
        { TestControl.class, "addHelpListener", p(HelpListener.class), a(new TestHelpListener()) },
        { TestControl.class, "addKeyListener", p(KeyListener.class), a(new TestKeyListener()) },
        { TestControl.class, "addMenuDetectListener", p(MenuDetectListener.class), a(new TestMenuDetectListener()) },
        { TestControl.class, "addMouseListener", p(MouseListener.class), a(new TestMouseListener()) },
        { TestControl.class, "addMouseMoveListener", p(MouseMoveListener.class), a(new TestMouseMoveListener()) },
        { TestControl.class, "addMouseTrackListener", p(MouseTrackListener.class), a(new TestMouseTrackListener()) },
        { TestControl.class, "addMouseWheelListener", p(MouseWheelListener.class), a(new TestMouseWheelListener()) },
        { TestControl.class, "addPaintListener", p(PaintListener.class), a(new TestPaintListener()) },
        { TestControl.class, "addTraverseListener", p(TraverseListener.class), a(new TestTraverseListener()) },
        { TestControl.class, "getLayoutData", p(), a() },
        { TestControl.class, "removeControlListener", p(ControlListener.class), a(new TestControlListener()) },
        { TestControl.class, "removeDragDetectListener", p(DragDetectListener.class), a(new TestDragDetectListener()) },
        { TestControl.class, "removeFocusListener", p(FocusListener.class), a(new TestFocusListener()) },
        { TestControl.class, "removeHelpListener", p(HelpListener.class), a(new TestHelpListener()) },
        { TestControl.class, "removeKeyListener", p(KeyListener.class), a(new TestKeyListener()) },
        { TestControl.class, "removeMenuDetectListener", p(MenuDetectListener.class), a(new TestMenuDetectListener()) },
        { TestControl.class, "removeMouseListener", p(MouseListener.class), a(new TestMouseListener()) },
        { TestControl.class, "removeMouseMoveListener", p(MouseMoveListener.class), a(new TestMouseMoveListener()) },
        { TestControl.class, "removeMouseTrackListener", p(MouseTrackListener.class), a(new TestMouseTrackListener()) },
        { TestControl.class, "removeMouseWheelListener", p(MouseWheelListener.class), a(new TestMouseWheelListener()) },
        { TestControl.class, "removePaintListener", p(PaintListener.class), a(new TestPaintListener()) },
        { TestControl.class, "removeTraverseListener", p(TraverseListener.class), a(new TestTraverseListener()) },
        { TestControl.class, "setLayoutData", p(Object.class), a((Object)null) },
        { TestControl.class, "setRedraw", p(boolean.class), a(true) },
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
        this.testClass.getConstructor(Composite.class, int.class);
    this.widget = ctor.newInstance(this.shell, 0);
  }

  @After
  public void after() {
    this.widget = null;
    this.shell = null;
    this.display = null;
  }

  @Test
  public void invalidAccess() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    asyncExec(new Callable<Widget>() {
      public Widget call() throws Exception {
        Method m = testClass.getMethod(method, parameterTypes);

        try {
          m.invoke(widget, arguments);
        } catch (InvocationTargetException e) {
          if (e.getCause() instanceof Exception) {
            throw (Exception)e.getCause();
          } else {
            throw new Exception(e.getCause());
          }
        }
        return (widget);
      }
    });

  }

  private static class TestWidget extends Widget {
    public TestWidget(Composite parent, int style) throws SWTException {
      super((Widget)parent, style);
    }
  }

  private static class TestControl extends Control {
    public TestControl(Composite parent, int style) throws SWTException {
      super(parent, style);
    }
  }
}
