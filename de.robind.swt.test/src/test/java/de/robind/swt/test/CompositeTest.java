package de.robind.swt.test;

import static de.robind.swt.test.utils.ClientTaskMatcher.callRequest;
import static de.robind.swt.test.utils.ClientTaskMatcher.createRequest;
import static de.robind.swt.test.utils.SWTExceptionMatcher.swtCode;
import static de.robind.swt.test.utils.SWTTestUtils.asyncExec;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.Callable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;
import org.junit.Test;

public class CompositeTest extends AbstractWidgetTest {
  @Test
  public void ctorNullParent() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    new Composite(null, 0);
  }

  @Test
  public void ctorParentDisposed() {
    exception.expect(swtCode(SWT.ERROR_INVALID_ARGUMENT));

    this.shell.dispose();

    new Composite(this.shell, 0);
  }

  @Test
  public void ctorInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    asyncExec(new Callable<Widget>() {
      public Composite call() throws Exception {
        return (new Composite(shell, 0));
      }
    });
  }

  @Test
  public void ctorRequest() {
    Composite composite = new Composite(this.shell, 4711);
    assertThat(getClientTasks(), is(createRequest(composite, Composite.class, this.shell, 4711)));
  }

  @Test
  public void ctorStyle() {
    Composite composite = new Composite(this.shell, 4711);
    assertThat(composite.getStyle(), is(4711));
  }

  @Test
  public void layout() {
    Composite composite = new Composite(this.shell, 4711);
    composite.layout();
    assertThat(getClientTasks(), is(createRequest(composite, Composite.class, this.shell, 4711)));
    assertThat(getClientTasks(), is(callRequest(composite, "layout", true, false)));
  }

  @Test
  public void layoutBoolean() {
    Composite composite = new Composite(this.shell, 4711);
    composite.layout(true);
    assertThat(getClientTasks(), is(createRequest(composite, Composite.class, this.shell, 4711)));
    assertThat(getClientTasks(), is(callRequest(composite, "layout", true, false)));
  }

  @Test
  public void layoutBooleanBoolean() {
    Composite composite = new Composite(this.shell, 4711);
    composite.layout(true, true);
    assertThat(getClientTasks(), is(createRequest(composite, Composite.class, this.shell, 4711)));
    assertThat(getClientTasks(), is(callRequest(composite, "layout", true, true)));
  }

  @Test
  public void layoutArrayNullArray() {
    exception.expect(swtCode(SWT.ERROR_INVALID_ARGUMENT));

    Composite composite = new Composite(this.shell, 4711);
    composite.layout(null);
  }

  @Test
  public void layoutArrayNullArrayElement() {
    exception.expect(swtCode(SWT.ERROR_INVALID_ARGUMENT));

    Composite composite = new Composite(this.shell, 4711);
    composite.layout(new Control[] {null});
  }

  @Test
  public void layoutArrayDisposedArrayElement() {
    Composite composite = new Composite(this.shell, 4711);

    Control changed[] = new Control[1];
    changed[0] = new Composite(composite, 0);
    changed[0].dispose();

    exception.expect(swtCode(SWT.ERROR_INVALID_ARGUMENT));

    composite.layout(changed);
  }

  @Test
  public void layoutArrayArrayElementNotInTree() {
    Composite composite = new Composite(this.shell, 4711);
    Composite child1 = new Composite(composite, 0);
    new Composite(composite, 0);
    new Composite(child1, 0);
    new Composite(child1, 0);
    new Composite(child1, 0);

    Control changed[] = new Control[1];
    changed[0] = new Composite(this.shell, 0);

    exception.expect(swtCode(SWT.ERROR_INVALID_PARENT));

    composite.layout(changed);
  }

  @Test
  public void layoutArray() {
    Composite composite = new Composite(this.shell, 4711);
    Control changed[] = new Control[] {
        new Composite(composite, 0)
    };

    composite.layout(changed);
    assertThat(getClientTasks(), is(createRequest(composite, Composite.class, this.shell, 4711)));
    assertThat(getClientTasks(), is(createRequest(changed[0], Composite.class, composite, 0)));
    assertThat(getClientTasks(), is(callRequest(composite, "layout", changed, SWT.NONE)));
  }

  @Test
  public void layoutArrayIntNullArray() {
    exception.expect(swtCode(SWT.ERROR_INVALID_ARGUMENT));

    Composite composite = new Composite(this.shell, 4711);
    composite.layout(null, SWT.NONE);
  }

  @Test
  public void layoutArrayIntNullArrayElement() {
    exception.expect(swtCode(SWT.ERROR_INVALID_ARGUMENT));

    Composite composite = new Composite(this.shell, 4711);
    composite.layout(new Control[] {null}, SWT.NONE);
  }

  @Test
  public void layoutArrayIntDisposedArrayElement() {
    Composite composite = new Composite(this.shell, 4711);

    Control changed[] = new Control[1];
    changed[0] = new Composite(composite, 0);
    changed[0].dispose();

    exception.expect(swtCode(SWT.ERROR_INVALID_ARGUMENT));

    composite.layout(changed, SWT.NONE);
  }

  @Test
  public void layoutArrayIntArrayElementNotInTree() {
    Composite composite = new Composite(this.shell, 4711);
    Composite child1 = new Composite(composite, 0);
    new Composite(composite, 0);
    new Composite(child1, 0);
    new Composite(child1, 0);
    new Composite(child1, 0);

    Control changed[] = new Control[1];
    changed[0] = new Composite(this.shell, 0);

    exception.expect(swtCode(SWT.ERROR_INVALID_PARENT));

    composite.layout(changed, SWT.NONE);
  }

  @Test
  public void layoutArrayInt() {
    Composite composite = new Composite(this.shell, 4711);
    Control changed[] = new Control[] {
        new Composite(composite, 0)
    };

    composite.layout(changed, SWT.NONE);
    assertThat(getClientTasks(), is(createRequest(composite, Composite.class, this.shell, 4711)));
    assertThat(getClientTasks(), is(createRequest(changed[0], Composite.class, composite, 0)));
    assertThat(getClientTasks(), is(callRequest(composite, "layout", changed, SWT.NONE)));
  }
}
