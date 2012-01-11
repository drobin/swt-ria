package de.robind.swt.test;

import static de.robind.swt.test.utils.ClientTaskMatcher.attrRequest;
import static de.robind.swt.test.utils.ClientTaskMatcher.createRequest;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.server.DisplayPool;
import org.eclipse.swt.server.Key;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import de.robind.swt.test.utils.TestClientTasks;

public class FormLayoutTest {
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
  public void ctor() {
    FormLayout layout = new FormLayout();

    assertThat(layout.marginLeft, is(0));
    assertThat(layout.marginRight, is(0));
    assertThat(layout.marginTop, is(0));
    assertThat(layout.marginBottom, is(0));
    assertThat(layout.marginWidth, is(0));
    assertThat(layout.marginHeight, is(0));
    assertThat(layout.spacing, is(0));

    assertThat(getClientTasks().getQueueSize(), is(0));

    layout.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), is(createRequest(layout, FormLayout.class)));
  }

  @Test
  public void updateMarginLeft() {
    FormLayout layout = new FormLayout();
    layout.marginLeft = 4711;

    assertThat(getClientTasks().getQueueSize(), is(0));

    layout.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), is(createRequest(layout, FormLayout.class)));
    assertThat(getClientTasks(), is(attrRequest(layout, "marginLeft", 4711)));
  }

  @Test
  public void updateMarginRight() {
    FormLayout layout = new FormLayout();
    layout.marginRight = 4711;

    assertThat(getClientTasks().getQueueSize(), is(0));

    layout.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), is(createRequest(layout, FormLayout.class)));
    assertThat(getClientTasks(), is(attrRequest(layout, "marginRight", 4711)));
  }

  @Test
  public void updateMarginTop() {
    FormLayout layout = new FormLayout();
    layout.marginTop = 4711;

    assertThat(getClientTasks().getQueueSize(), is(0));

    layout.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), is(createRequest(layout, FormLayout.class)));
    assertThat(getClientTasks(), is(attrRequest(layout, "marginTop", 4711)));
  }

  @Test
  public void updateMarginBottom() {
    FormLayout layout = new FormLayout();
    layout.marginBottom = 4711;

    assertThat(getClientTasks().getQueueSize(), is(0));

    layout.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), is(createRequest(layout, FormLayout.class)));
    assertThat(getClientTasks(), is(attrRequest(layout, "marginBottom", 4711)));
  }

  @Test
  public void updateMarginWidth() {
    FormLayout layout = new FormLayout();
    layout.marginWidth = 4711;

    assertThat(getClientTasks().getQueueSize(), is(0));

    layout.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), is(createRequest(layout, FormLayout.class)));
    assertThat(getClientTasks(), is(attrRequest(layout, "marginWidth", 4711)));
  }

  @Test
  public void updateMarginHeight() {
    FormLayout layout = new FormLayout();
    layout.marginHeight = 4711;

    assertThat(getClientTasks().getQueueSize(), is(0));

    layout.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), is(createRequest(layout, FormLayout.class)));
    assertThat(getClientTasks(), is(attrRequest(layout, "marginHeight", 4711)));
  }

  @Test
  public void updateSpacing() {
    FormLayout layout = new FormLayout();
    layout.spacing = 4711;

    assertThat(getClientTasks().getQueueSize(), is(0));

    layout.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), is(createRequest(layout, FormLayout.class)));
    assertThat(getClientTasks(), is(attrRequest(layout, "spacing", 4711)));
  }

  protected TestClientTasks getClientTasks() {
    DisplayPool pool = DisplayPool.getInstance();
    return ((TestClientTasks)pool.getClientTasks());
  }
}
