package de.robind.swt.test;

import static de.robind.swt.test.utils.ClientTaskMatcher.createRequest;
import static de.robind.swt.test.utils.SWTExceptionMatcher.swtCode;
import static de.robind.swt.test.utils.SWTTestUtils.asyncExec;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.Callable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.junit.Test;

public class TreeTest extends AbstractWidgetTest {
  @Test
  public void ctorNullParent() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));
    new Tree(null, 0);
  }

  @Test
  public void ctorParentDisposed() {
    exception.expect(swtCode(SWT.ERROR_INVALID_ARGUMENT));

    this.shell.dispose();
    new Tree(this.shell, 0);
  }

  @Test
  public void ctorInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    asyncExec(new Callable<Tree>() {
      public Tree call() throws Exception {
        return (new Tree(shell, 0));
      }
    });
  }

  @Test
  public void ctorInvalidSubclass() {
    exception.expect(swtCode(SWT.ERROR_INVALID_SUBCLASS));
    new Tree(this.shell, 0) {};
  }

  @Test
  public void ctorRequest() {
    Tree tree = new Tree(this.shell, 4711);
    assertThat(getClientTasks(), is(createRequest(tree, Tree.class, this.shell, 4711)));
  }

  @Test
  public void ctorStyle() {
    Tree tree = new Tree(this.shell, 4711);
    assertThat(tree.getStyle(), is(4711));
  }

}
