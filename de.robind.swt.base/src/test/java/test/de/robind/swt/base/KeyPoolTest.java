package test.de.robind.swt.base;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.robind.swt.base.KeyPool;

public class KeyPoolTest {
  @Test
  public void getInstance() {
    KeyPool pool = KeyPool.getInstance();

    assertThat(pool, is(notNullValue()));
    assertThat(KeyPool.getInstance(), is(sameInstance(pool)));
  }
}
