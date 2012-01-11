package de.robind.swt.test;

import static de.robind.swt.test.utils.ClientTaskMatcher.attrRequest;
import static de.robind.swt.test.utils.ClientTaskMatcher.createRequest;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.server.DisplayPool;
import org.eclipse.swt.server.Key;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import de.robind.swt.test.utils.TestClientTasks;

public class GridDataTest {
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
    GridData data = new GridData();

    assertThat(data.exclude, is(false));
    assertThat(data.grabExcessHorizontalSpace, is(false));
    assertThat(data.grabExcessVerticalSpace, is(false));
    assertThat(data.heightHint, is(SWT.DEFAULT));
    assertThat(data.horizontalAlignment, is(GridData.BEGINNING));
    assertThat(data.horizontalIndent, is(0));
    assertThat(data.horizontalSpan, is(1));
    assertThat(data.minimumHeight, is(0));
    assertThat(data.minimumWidth, is(0));
    assertThat(data.verticalAlignment, is(GridData.CENTER));
    assertThat(data.verticalIndent, is(0));
    assertThat(data.verticalSpan, is(1));
    assertThat(data.widthHint, is(SWT.DEFAULT));

    assertThat(getClientTasks().getQueueSize(), is(0));

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), createRequest(data, GridData.class));
  }

  @Test
  public void ctorIntSetVerticalAlignmentToBeginning() {
    GridData data = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);

    assertThat(data.exclude, is(false));
    assertThat(data.grabExcessHorizontalSpace, is(false));
    assertThat(data.grabExcessVerticalSpace, is(false));
    assertThat(data.heightHint, is(SWT.DEFAULT));
    assertThat(data.horizontalAlignment, is(GridData.BEGINNING));
    assertThat(data.horizontalIndent, is(0));
    assertThat(data.horizontalSpan, is(1));
    assertThat(data.minimumHeight, is(0));
    assertThat(data.minimumWidth, is(0));
    assertThat(data.verticalAlignment, is(GridData.BEGINNING));
    assertThat(data.verticalIndent, is(0));
    assertThat(data.verticalSpan, is(1));
    assertThat(data.widthHint, is(SWT.DEFAULT));

    assertThat(getClientTasks().getQueueSize(), is(0));

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), createRequest(data, GridData.class, GridData.VERTICAL_ALIGN_BEGINNING));
  }

  @Test
  public void ctorIntSetVerticalAlignmentToCenter() {
    GridData data = new GridData(GridData.VERTICAL_ALIGN_CENTER);

    assertThat(data.exclude, is(false));
    assertThat(data.grabExcessHorizontalSpace, is(false));
    assertThat(data.grabExcessVerticalSpace, is(false));
    assertThat(data.heightHint, is(SWT.DEFAULT));
    assertThat(data.horizontalAlignment, is(GridData.BEGINNING));
    assertThat(data.horizontalIndent, is(0));
    assertThat(data.horizontalSpan, is(1));
    assertThat(data.minimumHeight, is(0));
    assertThat(data.minimumWidth, is(0));
    assertThat(data.verticalAlignment, is(GridData.CENTER));
    assertThat(data.verticalIndent, is(0));
    assertThat(data.verticalSpan, is(1));
    assertThat(data.widthHint, is(SWT.DEFAULT));

    assertThat(getClientTasks().getQueueSize(), is(0));

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), createRequest(data, GridData.class, GridData.VERTICAL_ALIGN_CENTER));
  }

  @Test
  public void ctorIntSetVerticalAlignmentToFill() {
    GridData data = new GridData(GridData.VERTICAL_ALIGN_FILL);

    assertThat(data.exclude, is(false));
    assertThat(data.grabExcessHorizontalSpace, is(false));
    assertThat(data.grabExcessVerticalSpace, is(false));
    assertThat(data.heightHint, is(SWT.DEFAULT));
    assertThat(data.horizontalAlignment, is(GridData.BEGINNING));
    assertThat(data.horizontalIndent, is(0));
    assertThat(data.horizontalSpan, is(1));
    assertThat(data.minimumHeight, is(0));
    assertThat(data.minimumWidth, is(0));
    assertThat(data.verticalAlignment, is(GridData.FILL));
    assertThat(data.verticalIndent, is(0));
    assertThat(data.verticalSpan, is(1));
    assertThat(data.widthHint, is(SWT.DEFAULT));

    assertThat(getClientTasks().getQueueSize(), is(0));

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), createRequest(data, GridData.class, GridData.VERTICAL_ALIGN_FILL));
  }

  @Test
  public void ctorIntSetVerticalAlignmentToEnd() {
    GridData data = new GridData(GridData.VERTICAL_ALIGN_END);

    assertThat(data.exclude, is(false));
    assertThat(data.grabExcessHorizontalSpace, is(false));
    assertThat(data.grabExcessVerticalSpace, is(false));
    assertThat(data.heightHint, is(SWT.DEFAULT));
    assertThat(data.horizontalAlignment, is(GridData.BEGINNING));
    assertThat(data.horizontalIndent, is(0));
    assertThat(data.horizontalSpan, is(1));
    assertThat(data.minimumHeight, is(0));
    assertThat(data.minimumWidth, is(0));
    assertThat(data.verticalAlignment, is(GridData.END));
    assertThat(data.verticalIndent, is(0));
    assertThat(data.verticalSpan, is(1));
    assertThat(data.widthHint, is(SWT.DEFAULT));

    assertThat(getClientTasks().getQueueSize(), is(0));

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), createRequest(data, GridData.class, GridData.VERTICAL_ALIGN_END));
  }

  @Test
  public void ctorIntSetHorizontalAlignmentToBeginning() {
    GridData data = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);

    assertThat(data.exclude, is(false));
    assertThat(data.grabExcessHorizontalSpace, is(false));
    assertThat(data.grabExcessVerticalSpace, is(false));
    assertThat(data.heightHint, is(SWT.DEFAULT));
    assertThat(data.horizontalAlignment, is(GridData.BEGINNING));
    assertThat(data.horizontalIndent, is(0));
    assertThat(data.horizontalSpan, is(1));
    assertThat(data.minimumHeight, is(0));
    assertThat(data.minimumWidth, is(0));
    assertThat(data.verticalAlignment, is(GridData.CENTER));
    assertThat(data.verticalIndent, is(0));
    assertThat(data.verticalSpan, is(1));
    assertThat(data.widthHint, is(SWT.DEFAULT));

    assertThat(getClientTasks().getQueueSize(), is(0));

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), createRequest(data, GridData.class, GridData.HORIZONTAL_ALIGN_BEGINNING));
  }

  @Test
  public void ctorIntSetHorizontalAlignmentToCenter() {
    GridData data = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);

    assertThat(data.exclude, is(false));
    assertThat(data.grabExcessHorizontalSpace, is(false));
    assertThat(data.grabExcessVerticalSpace, is(false));
    assertThat(data.heightHint, is(SWT.DEFAULT));
    assertThat(data.horizontalAlignment, is(GridData.CENTER));
    assertThat(data.horizontalIndent, is(0));
    assertThat(data.horizontalSpan, is(1));
    assertThat(data.minimumHeight, is(0));
    assertThat(data.minimumWidth, is(0));
    assertThat(data.verticalAlignment, is(GridData.CENTER));
    assertThat(data.verticalIndent, is(0));
    assertThat(data.verticalSpan, is(1));
    assertThat(data.widthHint, is(SWT.DEFAULT));

    assertThat(getClientTasks().getQueueSize(), is(0));

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), createRequest(data, GridData.class, GridData.HORIZONTAL_ALIGN_CENTER));
  }

  @Test
  public void ctorIntSetHorizontalAlignmentToFill() {
    GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);

    assertThat(data.exclude, is(false));
    assertThat(data.grabExcessHorizontalSpace, is(false));
    assertThat(data.grabExcessVerticalSpace, is(false));
    assertThat(data.heightHint, is(SWT.DEFAULT));
    assertThat(data.horizontalAlignment, is(GridData.FILL));
    assertThat(data.horizontalIndent, is(0));
    assertThat(data.horizontalSpan, is(1));
    assertThat(data.minimumHeight, is(0));
    assertThat(data.minimumWidth, is(0));
    assertThat(data.verticalAlignment, is(GridData.CENTER));
    assertThat(data.verticalIndent, is(0));
    assertThat(data.verticalSpan, is(1));
    assertThat(data.widthHint, is(SWT.DEFAULT));

    assertThat(getClientTasks().getQueueSize(), is(0));

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), createRequest(data, GridData.class, GridData.HORIZONTAL_ALIGN_FILL));
  }

  @Test
  public void ctorIntSetHorizontalAlignmentToEnd() {
    GridData data = new GridData(GridData.HORIZONTAL_ALIGN_END);

    assertThat(data.exclude, is(false));
    assertThat(data.grabExcessHorizontalSpace, is(false));
    assertThat(data.grabExcessVerticalSpace, is(false));
    assertThat(data.heightHint, is(SWT.DEFAULT));
    assertThat(data.horizontalAlignment, is(GridData.END));
    assertThat(data.horizontalIndent, is(0));
    assertThat(data.horizontalSpan, is(1));
    assertThat(data.minimumHeight, is(0));
    assertThat(data.minimumWidth, is(0));
    assertThat(data.verticalAlignment, is(GridData.CENTER));
    assertThat(data.verticalIndent, is(0));
    assertThat(data.verticalSpan, is(1));
    assertThat(data.widthHint, is(SWT.DEFAULT));

    assertThat(getClientTasks().getQueueSize(), is(0));

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), createRequest(data, GridData.class, GridData.HORIZONTAL_ALIGN_END));
  }

  @Test
  public void ctorIntSetGrabExcessHorizontalSpace() {
    GridData data = new GridData(GridData.GRAB_HORIZONTAL);

    assertThat(data.exclude, is(false));
    assertThat(data.grabExcessHorizontalSpace, is(true));
    assertThat(data.grabExcessVerticalSpace, is(false));
    assertThat(data.heightHint, is(SWT.DEFAULT));
    assertThat(data.horizontalAlignment, is(GridData.BEGINNING));
    assertThat(data.horizontalIndent, is(0));
    assertThat(data.horizontalSpan, is(1));
    assertThat(data.minimumHeight, is(0));
    assertThat(data.minimumWidth, is(0));
    assertThat(data.verticalAlignment, is(GridData.CENTER));
    assertThat(data.verticalIndent, is(0));
    assertThat(data.verticalSpan, is(1));
    assertThat(data.widthHint, is(SWT.DEFAULT));

    assertThat(getClientTasks().getQueueSize(), is(0));

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), createRequest(data, GridData.class, GridData.GRAB_HORIZONTAL));
  }

  @Test
  public void ctorIntSetGrabExcessVerticalSpace() {
    GridData data = new GridData(GridData.GRAB_VERTICAL);

    assertThat(data.exclude, is(false));
    assertThat(data.grabExcessHorizontalSpace, is(false));
    assertThat(data.grabExcessVerticalSpace, is(true));
    assertThat(data.heightHint, is(SWT.DEFAULT));
    assertThat(data.horizontalAlignment, is(GridData.BEGINNING));
    assertThat(data.horizontalIndent, is(0));
    assertThat(data.horizontalSpan, is(1));
    assertThat(data.minimumHeight, is(0));
    assertThat(data.minimumWidth, is(0));
    assertThat(data.verticalAlignment, is(GridData.CENTER));
    assertThat(data.verticalIndent, is(0));
    assertThat(data.verticalSpan, is(1));
    assertThat(data.widthHint, is(SWT.DEFAULT));

    assertThat(getClientTasks().getQueueSize(), is(0));

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), createRequest(data, GridData.class, GridData.GRAB_VERTICAL));
  }

  @Test
  public void ctorIntInt() {
    GridData data = new GridData(1, 2);

    assertThat(data.exclude, is(false));
    assertThat(data.grabExcessHorizontalSpace, is(false));
    assertThat(data.grabExcessVerticalSpace, is(false));
    assertThat(data.heightHint, is(2));
    assertThat(data.horizontalAlignment, is(GridData.BEGINNING));
    assertThat(data.horizontalIndent, is(0));
    assertThat(data.horizontalSpan, is(1));
    assertThat(data.minimumHeight, is(0));
    assertThat(data.minimumWidth, is(0));
    assertThat(data.verticalAlignment, is(GridData.CENTER));
    assertThat(data.verticalIndent, is(0));
    assertThat(data.verticalSpan, is(1));
    assertThat(data.widthHint, is(1));

    assertThat(getClientTasks().getQueueSize(), is(0));

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), createRequest(data, GridData.class, 1, 2));
  }

  @Test
  public void ctorIntIntBoolBool() {
    GridData data = new GridData(1, 2, true, true);

    assertThat(data.exclude, is(false));
    assertThat(data.grabExcessHorizontalSpace, is(true));
    assertThat(data.grabExcessVerticalSpace, is(true));
    assertThat(data.heightHint, is(SWT.DEFAULT));
    assertThat(data.horizontalAlignment, is(1));
    assertThat(data.horizontalIndent, is(0));
    assertThat(data.horizontalSpan, is(1));
    assertThat(data.minimumHeight, is(0));
    assertThat(data.minimumWidth, is(0));
    assertThat(data.verticalAlignment, is(2));
    assertThat(data.verticalIndent, is(0));
    assertThat(data.verticalSpan, is(1));
    assertThat(data.widthHint, is(SWT.DEFAULT));

    assertThat(getClientTasks().getQueueSize(), is(0));

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), createRequest(data, GridData.class, 1, 2, true, true, 1, 1));
  }

  @Test
  public void ctorIntIntBoolBoolIntInt() {
    GridData data = new GridData(1, 2, true, true, 3, 4);

    assertThat(data.exclude, is(false));
    assertThat(data.grabExcessHorizontalSpace, is(true));
    assertThat(data.grabExcessVerticalSpace, is(true));
    assertThat(data.heightHint, is(SWT.DEFAULT));
    assertThat(data.horizontalAlignment, is(1));
    assertThat(data.horizontalIndent, is(0));
    assertThat(data.horizontalSpan, is(3));
    assertThat(data.minimumHeight, is(0));
    assertThat(data.minimumWidth, is(0));
    assertThat(data.verticalAlignment, is(2));
    assertThat(data.verticalIndent, is(0));
    assertThat(data.verticalSpan, is(4));
    assertThat(data.widthHint, is(SWT.DEFAULT));

    assertThat(getClientTasks().getQueueSize(), is(0));

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), createRequest(data, GridData.class, 1, 2, true, true, 3, 4));
  }

  @Test
  public void updateExclude() {
    GridData data = new GridData();
    data.exclude = true;

    assertThat(getClientTasks().getQueueSize(), is(0));

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), createRequest(data, GridData.class));
    assertThat(getClientTasks(), attrRequest(data, "exclude", true));
  }

  @Test
  public void updateGrabExcessHorizontalSpace() {
    GridData data = new GridData();
    data.grabExcessHorizontalSpace = true;

    assertThat(getClientTasks().getQueueSize(), is(0));

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), createRequest(data, GridData.class));
    assertThat(getClientTasks(), attrRequest(data, "grabExcessHorizontalSpace", true));
  }

  @Test
  public void updateGrabExcessVerticalSpace() {
    GridData data = new GridData();
    data.grabExcessVerticalSpace = true;

    assertThat(getClientTasks().getQueueSize(), is(0));

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), createRequest(data, GridData.class));
    assertThat(getClientTasks(), attrRequest(data, "grabExcessVerticalSpace", true));
  }

  @Test
  public void updateHeightHint() {
    GridData data = new GridData();
    data.heightHint = 4711;

    assertThat(getClientTasks().getQueueSize(), is(0));

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), createRequest(data, GridData.class));
    assertThat(getClientTasks(), attrRequest(data, "heightHint", 4711));
  }

  @Test
  public void updateHorizontalAlignment() {
    GridData data = new GridData();
    data.horizontalAlignment = 4711;

    assertThat(getClientTasks().getQueueSize(), is(0));

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), createRequest(data, GridData.class));
    assertThat(getClientTasks(), attrRequest(data, "horizontalAlignment", 4711));
  }

  @Test
  public void updateHorizontalIndent() {
    GridData data = new GridData();
    data.horizontalIndent = 4711;

    assertThat(getClientTasks().getQueueSize(), is(0));

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), createRequest(data, GridData.class));
    assertThat(getClientTasks(), attrRequest(data, "horizontalIndent", 4711));
  }

  @Test
  public void updateHorizontalSpan() {
    GridData data = new GridData();
    data.horizontalSpan = 4711;

    assertThat(getClientTasks().getQueueSize(), is(0));

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), createRequest(data, GridData.class));
    assertThat(getClientTasks(), attrRequest(data, "horizontalSpan", 4711));
  }

  @Test
  public void updateMinimumHeight() {
    GridData data = new GridData();
    data.minimumHeight = 4711;

    assertThat(getClientTasks().getQueueSize(), is(0));

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), createRequest(data, GridData.class));
    assertThat(getClientTasks(), attrRequest(data, "minimumHeight", 4711));
  }

  @Test
  public void updateMinimumWidth() {
    GridData data = new GridData();
    data.minimumWidth = 4711;

    assertThat(getClientTasks().getQueueSize(), is(0));

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), createRequest(data, GridData.class));
    assertThat(getClientTasks(), attrRequest(data, "minimumWidth", 4711));
  }

  @Test
  public void updateVerticalAlignment() {
    GridData data = new GridData();
    data.verticalAlignment = 4711;

    assertThat(getClientTasks().getQueueSize(), is(0));

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), createRequest(data, GridData.class));
    assertThat(getClientTasks(), attrRequest(data, "verticalAlignment", 4711));
  }

  @Test
  public void updateVerticalIndent() {
    GridData data = new GridData();
    data.verticalIndent = 4711;

    assertThat(getClientTasks().getQueueSize(), is(0));

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), createRequest(data, GridData.class));
    assertThat(getClientTasks(), attrRequest(data, "verticalIndent", 4711));
  }

  @Test
  public void updateVerticalSpan() {
    GridData data = new GridData();
    data.verticalSpan = 4711;

    assertThat(getClientTasks().getQueueSize(), is(0));

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), createRequest(data, GridData.class));
    assertThat(getClientTasks(), attrRequest(data, "verticalSpan", 4711));
  }

  @Test
  public void updateWidthHint() {
    GridData data = new GridData();
    data.widthHint = 4711;

    assertThat(getClientTasks().getQueueSize(), is(0));

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), createRequest(data, GridData.class));
    assertThat(getClientTasks(), attrRequest(data, "widthHint", 4711));
  }

  protected TestClientTasks getClientTasks() {
    DisplayPool pool = DisplayPool.getInstance();
    return ((TestClientTasks)pool.getClientTasks());
  }
}
