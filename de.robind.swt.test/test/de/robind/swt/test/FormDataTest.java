package de.robind.swt.test;

import static de.robind.swt.test.utils.ClientTaskMatcher.attrRequest;
import static de.robind.swt.test.utils.ClientTaskMatcher.createRequest;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.server.DisplayPool;
import org.eclipse.swt.server.Key;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import de.robind.swt.test.utils.TestClientTasks;

public class FormDataTest {
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
    FormData data = new FormData();

    assertThat(data.left, is(nullValue()));
    assertThat(data.right, is(nullValue()));
    assertThat(data.top, is(nullValue()));
    assertThat(data.bottom, is(nullValue()));
    assertThat(data.width, is(SWT.DEFAULT));
    assertThat(data.height, is(SWT.DEFAULT));

    assertThat(getClientTasks().getQueueSize(), is(0));

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), is(createRequest(data, FormData.class)));
  }

  @Test
  public void ctorIntInt() {
    FormData data = new FormData(1, 2);

    assertThat(data.left, is(nullValue()));
    assertThat(data.right, is(nullValue()));
    assertThat(data.top, is(nullValue()));
    assertThat(data.bottom, is(nullValue()));
    assertThat(data.width, is(1));
    assertThat(data.height, is(2));

    assertThat(getClientTasks().getQueueSize(), is(0));

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), is(createRequest(data, FormData.class, 1, 2)));
  }

  @Test
  public void updateLeftBeforeKeyAssigned() {
    FormData data = new FormData();
    data.left = new FormAttachment();

    assertThat(getClientTasks().getQueueSize(), is(0));

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(3));
    assertThat(getClientTasks(), is(createRequest(data.left, FormAttachment.class)));
    assertThat(getClientTasks(), is(createRequest(data, FormData.class)));
    assertThat(getClientTasks(), is(attrRequest(data, "left", data.left)));
  }

  @Test
  public void updateLeftAfterKeyAssigned() {
    FormData data = new FormData();

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), is(createRequest(data, FormData.class)));

    data.left = new FormAttachment();

    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), is(createRequest(data.left, FormAttachment.class)));
    assertThat(getClientTasks(), is(attrRequest(data, "left", data.left)));
  }

  @Test
  public void updateRightBeforeKeyAssigned() {
    FormData data = new FormData();
    data.right = new FormAttachment();

    assertThat(getClientTasks().getQueueSize(), is(0));

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(3));
    assertThat(getClientTasks(), is(createRequest(data.right, FormAttachment.class)));
    assertThat(getClientTasks(), is(createRequest(data, FormData.class)));
    assertThat(getClientTasks(), is(attrRequest(data, "right", data.right)));
  }

  @Test
  public void updateRightAfterKeyAssigned() {
    FormData data = new FormData();

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), is(createRequest(data, FormData.class)));

    data.right = new FormAttachment();

    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), is(createRequest(data.right, FormAttachment.class)));
    assertThat(getClientTasks(), is(attrRequest(data, "right", data.right)));
  }

  @Test
  public void updateTopBeforeKeyAssigned() {
    FormData data = new FormData();
    data.top = new FormAttachment();

    assertThat(getClientTasks().getQueueSize(), is(0));

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(3));
    assertThat(getClientTasks(), is(createRequest(data.top, FormAttachment.class)));
    assertThat(getClientTasks(), is(createRequest(data, FormData.class)));
    assertThat(getClientTasks(), is(attrRequest(data, "top", data.top)));
  }

  @Test
  public void updateTopAfterKeyAssigned() {
    FormData data = new FormData();

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), is(createRequest(data, FormData.class)));

    data.top = new FormAttachment();

    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), is(createRequest(data.top, FormAttachment.class)));
    assertThat(getClientTasks(), is(attrRequest(data, "top", data.top)));
  }

  @Test
  public void updateBottomBeforeKeyAssigned() {
    FormData data = new FormData();
    data.bottom = new FormAttachment();

    assertThat(getClientTasks().getQueueSize(), is(0));

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(3));
    assertThat(getClientTasks(), is(createRequest(data.bottom, FormAttachment.class)));
    assertThat(getClientTasks(), is(createRequest(data, FormData.class)));
    assertThat(getClientTasks(), is(attrRequest(data, "bottom", data.bottom)));
  }

  @Test
  public void updateBottomAfterKeyAssigned() {
    FormData data = new FormData();

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), is(createRequest(data, FormData.class)));

    data.bottom = new FormAttachment();

    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), is(createRequest(data.bottom, FormAttachment.class)));
    assertThat(getClientTasks(), is(attrRequest(data, "bottom", data.bottom)));
  }

  @Test
  public void updateWidth() {
    FormData data = new FormData();
    data.width = 4711;

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), is(createRequest(data, FormData.class)));
    assertThat(getClientTasks(), is(attrRequest(data, "width", 4711)));
  }

  @Test
  public void updateHeight() {
    FormData data = new FormData();
    data.height = 4711;

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), is(createRequest(data, FormData.class)));
    assertThat(getClientTasks(), is(attrRequest(data, "height", 4711)));
  }

  protected TestClientTasks getClientTasks() {
    DisplayPool pool = DisplayPool.getInstance();
    return ((TestClientTasks)pool.getClientTasks());
  }
}
