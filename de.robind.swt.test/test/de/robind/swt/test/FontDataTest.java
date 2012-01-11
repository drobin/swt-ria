package de.robind.swt.test;

import static de.robind.swt.test.utils.ClientTaskMatcher.callRequest;
import static de.robind.swt.test.utils.ClientTaskMatcher.createRequest;
import static de.robind.swt.test.utils.SWTExceptionMatcher.swtCode;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.server.Key;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FontDataTest extends ClientTasksSupport {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void stdCtor() {
    FontData data = new FontData();

    assertThat(data.getName(), is(nullValue()));
    assertThat(data.getHeight(), is(0));
    assertThat(data.getStyle(), is(0));

    assertThat(getClientTasks().getQueueSize(), is(0));

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), is(createRequest(data, FontData.class)));
  }

  @Test
  public void ctorStringNullString() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    new FontData(null);
  }

  @Test
  public void ctorStringInvalidString() {
    exception.expect(swtCode(SWT.ERROR_INVALID_ARGUMENT));

    new FontData("xxx");
  }

  @Test
  public void ctorStringIntInt() {
    FontData data = new FontData("foo", 1, 2);

    assertThat(data.getName(), is(equalTo("foo")));
    assertThat(data.getHeight(), is(1));
    assertThat(data.getStyle(), is(2));

    assertThat(getClientTasks().getQueueSize(), is(0));

    data.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), is(createRequest(data, FontData.class, "foo", 1, 2)));
  }

  @Test
  public void setNameNullName() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    FontData data = new FontData();
    data.setName(null);
  }

  @Test
  public void setName() {
    FontData data = new FontData();
    data.setKey(new Key() {});

    data.setName("foo");
    assertThat(data.getName(), is(equalTo("foo")));

    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), is(createRequest(data, FontData.class)));
    assertThat(getClientTasks(), is(callRequest(data, "setName", "foo")));
  }

  @Test
  public void setHeightNegativeHeight() {
    exception.expect(swtCode(SWT.ERROR_INVALID_ARGUMENT));

    FontData data = new FontData();
    data.setHeight(-1);
  }

  @Test
  public void setHeight() {
    FontData data = new FontData();
    data.setKey(new Key() {});

    data.setHeight(4711);
    assertThat(data.getHeight(), is(4711));

    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), is(createRequest(data, FontData.class)));
    assertThat(getClientTasks(), is(callRequest(data, "setHeight", 4711)));
  }

  @Test
  public void setStyle() {
    FontData data = new FontData();
    data.setKey(new Key() {});

    data.setStyle(SWT.NORMAL | 0x80);
    assertThat(data.getStyle(), is(SWT.NORMAL));

    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), is(createRequest(data, FontData.class)));
    assertThat(getClientTasks(), is(callRequest(data, "setStyle", SWT.NORMAL | 0x80)));

    data.setStyle(SWT.BOLD | 0x80);
    assertThat(data.getStyle(), is(SWT.BOLD));

    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), is(callRequest(data, "setStyle", SWT.BOLD | 0x80)));

    data.setStyle(SWT.ITALIC | 0x80);
    assertThat(data.getStyle(), is(SWT.ITALIC));

    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), is(callRequest(data, "setStyle", SWT.ITALIC | 0x80)));
  }
}
