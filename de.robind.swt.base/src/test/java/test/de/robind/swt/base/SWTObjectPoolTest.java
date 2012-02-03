package test.de.robind.swt.base;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.robind.swt.base.SWTObjectPool;

public class SWTObjectPoolTest {
  @Test
  public void getInstance() {
    SWTObjectPool pool = SWTObjectPool.getInstance();

    assertThat(pool, is(notNullValue()));
    assertThat(SWTObjectPool.getInstance(), is(sameInstance(pool)));
  }
}
