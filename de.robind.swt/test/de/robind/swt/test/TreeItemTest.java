package de.robind.swt.test;

import static de.robind.swt.test.utils.ClientTaskMatcher.createRequest;
import static de.robind.swt.test.utils.SWTExceptionMatcher.swtCode;
import static de.robind.swt.test.utils.SWTTestUtils.asyncExec;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.Callable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TreeItemTest extends AbstractWidgetTest {
  private Tree tree = null;

  @Before
  public void before2() {
    this.tree = new Tree(this.shell, SWT.DEFAULT);
  }

  @After
  public void after2() {
    this.tree = null;
  }

  @Test
  public void ctorNullParent() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));
    new TreeItem(null, 0);
  }

  @Test
  public void ctorParentDisposed() {
    exception.expect(swtCode(SWT.ERROR_INVALID_ARGUMENT));

    this.tree.dispose();
    new TreeItem(this.tree, 0);
  }

  @Test
  public void ctorInvalidThread() throws Throwable {
    exception.expect(swtCode(SWT.ERROR_THREAD_INVALID_ACCESS));

    asyncExec(new Callable<TreeItem>() {
      public TreeItem call() throws Exception {
        return (new TreeItem(tree, 0));
      }
    });
  }

  @Test
  public void ctorInvalidSubclass() {
    exception.expect(swtCode(SWT.ERROR_INVALID_SUBCLASS));
    new TreeItem(this.tree, 0) {};
  }

  @Test
  public void ctorRequest() {
    TreeItem treeItem = new TreeItem(this.tree, 4711);
    assertThat(getClientTasks(), is(createRequest(this.display, Display.class)));
    assertThat(getClientTasks(), is(createRequest(this.shell, Shell.class, this.display)));
    assertThat(getClientTasks(), is(createRequest(this.tree, Tree.class, this.shell, SWT.DEFAULT)));
    assertThat(getClientTasks(), is(createRequest(treeItem, TreeItem.class, this.tree, 4711)));
  }

  @Test
  public void ctorStyle() {
    TreeItem treeItem = new TreeItem(this.tree, 4711);
    assertThat(treeItem.getStyle(), is(4711));
  }

}
