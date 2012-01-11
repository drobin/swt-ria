package de.robind.swt.test;

import static de.robind.swt.test.utils.SWTExceptionMatcher.swtCode;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

import org.eclipse.swt.SWT;
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
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.server.DisplayPool;
import org.eclipse.swt.server.Key;
import org.eclipse.swt.test.TestControl;
import org.eclipse.swt.test.TestScrollable;
import org.eclipse.swt.test.TestWidget;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import de.robind.swt.test.utils.TestControlListener;
import de.robind.swt.test.utils.TestDisposeListener;
import de.robind.swt.test.utils.TestDragDetectListener;
import de.robind.swt.test.utils.TestFocusListener;
import de.robind.swt.test.utils.TestHelpListener;
import de.robind.swt.test.utils.TestKeyListener;
import de.robind.swt.test.utils.TestListener;
import de.robind.swt.test.utils.TestMenuDetectListener;
import de.robind.swt.test.utils.TestMouseListener;
import de.robind.swt.test.utils.TestMouseMoveListener;
import de.robind.swt.test.utils.TestMouseTrackListener;
import de.robind.swt.test.utils.TestMouseWheelListener;
import de.robind.swt.test.utils.TestPaintListener;
import de.robind.swt.test.utils.TestSelectionListener;
import de.robind.swt.test.utils.TestTraverseListener;


@RunWith(value = Parameterized.class)
public class DisposeTest extends ClientTasksSupport {
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
        { Button.class, "addSelectionListener", p(SelectionListener.class), a(new TestSelectionListener()) },
        { Button.class, "getText", p(), a() },
        { Button.class, "removeSelectionListener", p(SelectionListener.class), a(new TestSelectionListener()) },
        { Button.class, "setText", p(String.class), a("") },

        { Composite.class, "layout", p(), a() },
        { Composite.class, "layout", p(boolean.class), a(true) },
        { Composite.class, "layout", p(boolean.class, boolean.class), a(true, true) },
        { Composite.class, "layout", p(Control[].class), a((Object)(new Control[] {})) },
        { Composite.class, "layout", p(Control[].class, int.class), a((Object)(new Control[] {}), 1) },

        { List.class, "getSelectionIndices", p(), a() },
        { List.class, "getTopIndex", p(), a() },
        { List.class, "deselectAll", p(), a() },
        { List.class, "removeAll", p(), a() },
        { List.class, "setItems", p(String[].class), a((Object)(new String[] {})) },
        { List.class, "setTopIndex", p(int.class), a(0) },

        { Sash.class, "addSelectionListener", p(SelectionListener.class), a(new TestSelectionListener()) },
        { Sash.class, "removeSelectionListener", p(SelectionListener.class), a(new TestSelectionListener()) },

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
        { TestControl.class, "getBounds", p(), a() },
        { TestControl.class, "getLayoutData", p(), a() },
        { TestControl.class, "getParent", p(), a() },
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
        { TestControl.class, "setBounds", p(int.class, int.class, int.class, int.class), a(0, 0, 0, 0) },
        { TestControl.class, "setLayoutData", p(Object.class), a((Object)null) },
        { TestControl.class, "setRedraw", p(boolean.class), a(true) },

        { TestScrollable.class, "getClientArea", p(), a() },

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

  @Before
  public void before() throws Exception {
    DisplayPool.getInstance().offerKey(new Key() {});
    this.display = new Display();
    this.shell = new Shell(this.display);

    Constructor<? extends Widget> ctor;
    if (Control.class.isAssignableFrom(this.testClass)) {
      ctor = this.testClass.getConstructor(Composite.class, int.class);
    } else {
      // Special handling for Widgets. They has another constructor
      ctor = this.testClass.getConstructor(Widget.class, int.class);
    }

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
}
