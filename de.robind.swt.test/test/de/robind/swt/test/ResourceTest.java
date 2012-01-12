package de.robind.swt.test;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;

import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Resource;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ResourceTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void dispose() {
    Resource res = new TestResource(null);
    assertThat(res.isDisposed(), is(false));

    res.dispose();
    assertThat(res.isDisposed(), is(true));
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
