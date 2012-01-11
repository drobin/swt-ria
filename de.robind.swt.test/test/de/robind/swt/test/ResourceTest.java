package de.robind.swt.test;

import static de.robind.swt.test.utils.ClientTaskMatcher.callRequest;
import static de.robind.swt.test.utils.SWTExceptionMatcher.swtCode;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.server.Key;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ResourceTest extends ClientTasksSupport {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void disposeNoKey() {
    Resource res = new TestResource(null);
    assertThat(res.isDisposed(), is(false));

    exception.expect(swtCode(SWT.ERROR_FAILED_EXEC));

    res.dispose();
  }

  @Test
  public void disposeWithKey() {
    Resource res = new TestResource(null);
    res.setKey(new Key() {});

    assertThat(res.isDisposed(), is(false));

    res.dispose();
    assertThat(res.isDisposed(), is(true));

    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), is(callRequest(res, "dispose")));
  }

  @Test
  public void getDevice() {
    Resource res = new TestResource(null);
    assertThat(res.getDevice(), is(nullValue()));

    Device dev = new Device() {};
    res = new TestResource(dev);
    assertThat(res.getDevice(), is(sameInstance(dev)));
  }

  private static class TestResource extends Resource {
    public TestResource(Device parent) {
      this.device = parent;
    }

    @Override
    public boolean isDisposed() {
      return (this.disposed);
    }
  }
}
