package de.robind.swt.test;

import static de.robind.swt.test.utils.ClientTaskMatcher.attrRequest;
import static de.robind.swt.test.utils.ClientTaskMatcher.createRequest;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.server.DisplayPool;
import org.eclipse.swt.server.Key;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import de.robind.swt.test.utils.TestClientTasks;


public class FillLayoutTest {
  @BeforeClass
  public static void beforeClass() {
    System.setProperty("de.robind.swt.clienttasks", TestClientTasks.class.getName());
  }

  @AfterClass
  public static void afterClass() {
    System.clearProperty("de.robind.swt.clienttasks");
  }

  @After
  public void after() {
    getClientTasks().clearState();
  }

  @Test
  public void stdCtor() {
    FillLayout layout = new FillLayout();
    assertThat(layout.type, is(SWT.HORIZONTAL));
    assertThat(layout.marginWidth, is(0));
    assertThat(layout.marginHeight, is(0));
    assertThat(layout.spacing, is(0));
  }

  @Test
  public void ctorWithType() {
    FillLayout layout = new FillLayout(SWT.VERTICAL);
    assertThat(layout.type, is(SWT.VERTICAL));
    assertThat(layout.marginWidth, is(0));
    assertThat(layout.marginHeight, is(0));
    assertThat(layout.spacing, is(0));
  }

  @Test
  public void updateType() {
    FillLayout layout = new FillLayout();
    layout.type = SWT.VERTICAL;

    assertThat(getClientTasks().getQueueSize(), is(0));

    layout.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), is(createRequest(layout, FillLayout.class, SWT.HORIZONTAL)));
    assertThat(getClientTasks(), is(attrRequest(layout, "type", SWT.VERTICAL)));
  }

  @Test
  public void updateMarginWidth() {
    FillLayout layout = new FillLayout();
    layout.marginWidth = 4711;

    assertThat(getClientTasks().getQueueSize(), is(0));

    layout.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), is(createRequest(layout, FillLayout.class, SWT.HORIZONTAL)));
    assertThat(getClientTasks(), is(attrRequest(layout, "marginWidth", 4711)));
  }

  @Test
  public void updateMarginHeight() {
    FillLayout layout = new FillLayout();
    layout.marginHeight = 4711;

    assertThat(getClientTasks().getQueueSize(), is(0));

    layout.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), is(createRequest(layout, FillLayout.class, SWT.HORIZONTAL)));
    assertThat(getClientTasks(), is(attrRequest(layout, "marginHeight", 4711)));
  }

  @Test
  public void updateSpacing() {
    FillLayout layout = new FillLayout();
    layout.spacing = 4711;

    assertThat(getClientTasks().getQueueSize(), is(0));

    layout.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), is(createRequest(layout, FillLayout.class, SWT.HORIZONTAL)));
    assertThat(getClientTasks(), is(attrRequest(layout, "spacing", 4711)));
  }

  protected TestClientTasks getClientTasks() {
    DisplayPool pool = DisplayPool.getInstance();
    return ((TestClientTasks)pool.getClientTasks());
  }
}
