package de.robind.swt.test;

import static de.robind.swt.test.utils.ClientTaskMatcher.attrRequest;
import static de.robind.swt.test.utils.ClientTaskMatcher.createRequest;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.server.Key;
import org.junit.Test;

public class GridLayoutTest extends ClientTasksSupport {

  @Test
  public void stdCtor() {
    GridLayout layout = new GridLayout();

    assertThat(layout.horizontalSpacing, is(5));
    assertThat(layout.makeColumnsEqualWidth, is(false));
    assertThat(layout.marginBottom, is(0));
    assertThat(layout.marginHeight, is(5));
    assertThat(layout.marginLeft, is(0));
    assertThat(layout.marginRight, is(0));
    assertThat(layout.marginTop, is(0));
    assertThat(layout.marginWidth, is(5));
    assertThat(layout.numColumns, is(1));
    assertThat(layout.verticalSpacing, is(5));

    assertThat(getClientTasks().getQueueSize(), is(0));

    layout.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), createRequest(layout, GridLayout.class));
  }

  @Test
  public void ctorIntBool() {
    GridLayout layout = new GridLayout(2, true);

    assertThat(layout.horizontalSpacing, is(5));
    assertThat(layout.makeColumnsEqualWidth, is(true));
    assertThat(layout.marginBottom, is(0));
    assertThat(layout.marginHeight, is(5));
    assertThat(layout.marginLeft, is(0));
    assertThat(layout.marginRight, is(0));
    assertThat(layout.marginTop, is(0));
    assertThat(layout.marginWidth, is(5));
    assertThat(layout.numColumns, is(2));
    assertThat(layout.verticalSpacing, is(5));

    assertThat(getClientTasks().getQueueSize(), is(0));

    layout.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), createRequest(layout, GridLayout.class, 2, true));
  }

  @Test
  public void updateHorizontalSpacing() {
    GridLayout layout = new GridLayout();
    layout.horizontalSpacing = 4711;

    assertThat(getClientTasks().getQueueSize(), is(0));

    layout.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), createRequest(layout, GridLayout.class));
    assertThat(getClientTasks(), attrRequest(layout, "horizontalSpacing", 4711));
  }

  @Test
  public void updateMakeColumnsEqualWidth() {
    GridLayout layout = new GridLayout();
    layout.makeColumnsEqualWidth = true;

    assertThat(getClientTasks().getQueueSize(), is(0));

    layout.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), createRequest(layout, GridLayout.class));
    assertThat(getClientTasks(), attrRequest(layout, "makeColumnsEqualWidth", true));
  }

  @Test
  public void updateMarginBottom() {
    GridLayout layout = new GridLayout();
    layout.marginBottom = 4711;

    assertThat(getClientTasks().getQueueSize(), is(0));

    layout.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), createRequest(layout, GridLayout.class));
    assertThat(getClientTasks(), attrRequest(layout, "marginBottom", 4711));
  }

  @Test
  public void updateMarginHeight() {
    GridLayout layout = new GridLayout();
    layout.marginHeight = 4711;

    assertThat(getClientTasks().getQueueSize(), is(0));

    layout.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), createRequest(layout, GridLayout.class));
    assertThat(getClientTasks(), attrRequest(layout, "marginHeight", 4711));
  }

  @Test
  public void updateMarginLeft() {
    GridLayout layout = new GridLayout();
    layout.marginLeft = 4711;

    assertThat(getClientTasks().getQueueSize(), is(0));

    layout.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), createRequest(layout, GridLayout.class));
    assertThat(getClientTasks(), attrRequest(layout, "marginLeft", 4711));
  }

  @Test
  public void updateMarginRight() {
    GridLayout layout = new GridLayout();
    layout.marginRight = 4711;

    assertThat(getClientTasks().getQueueSize(), is(0));

    layout.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), createRequest(layout, GridLayout.class));
    assertThat(getClientTasks(), attrRequest(layout, "marginRight", 4711));
  }

  @Test
  public void updateMarginTop() {
    GridLayout layout = new GridLayout();
    layout.marginTop = 4711;

    assertThat(getClientTasks().getQueueSize(), is(0));

    layout.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), createRequest(layout, GridLayout.class));
    assertThat(getClientTasks(), attrRequest(layout, "marginTop", 4711));
  }

  @Test
  public void updateMarginWidth() {
    GridLayout layout = new GridLayout();
    layout.marginWidth = 4711;

    assertThat(getClientTasks().getQueueSize(), is(0));

    layout.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), createRequest(layout, GridLayout.class));
    assertThat(getClientTasks(), attrRequest(layout, "marginWidth", 4711));
  }

  @Test
  public void updateNumColumns() {
    GridLayout layout = new GridLayout();
    layout.numColumns = 4711;

    assertThat(getClientTasks().getQueueSize(), is(0));

    layout.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), createRequest(layout, GridLayout.class));
    assertThat(getClientTasks(), attrRequest(layout, "numColumns", 4711));
  }

  @Test
  public void updateVerticalSpacing() {
    GridLayout layout = new GridLayout();
    layout.verticalSpacing = 4711;

    assertThat(getClientTasks().getQueueSize(), is(0));

    layout.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), createRequest(layout, GridLayout.class));
    assertThat(getClientTasks(), attrRequest(layout, "verticalSpacing", 4711));
  }
}
