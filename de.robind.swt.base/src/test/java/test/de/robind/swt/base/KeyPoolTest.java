package test.de.robind.swt.base;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.robind.swt.base.Key;
import de.robind.swt.base.KeyPool;

public class KeyPoolTest {
  @Test
  public void getInstance() {
    KeyPool pool = KeyPool.getInstance();

    assertThat(pool, is(notNullValue()));
    assertThat(KeyPool.getInstance(), is(sameInstance(pool)));
  }

  @Test
  public void offerTakeKey() {
    KeyPool pool = KeyPool.getInstance();
    Key key1 = new Key() {};
    Key key2 = new Key() {};
    Key key3 = new Key() {};

    assertThat(pool.takeKey(), is(nullValue()));

    pool.offerKey(key1);
    pool.offerKey(key2);
    pool.offerKey(key3);

    assertThat(pool.takeKey(), is(sameInstance(key1)));
    assertThat(pool.takeKey(), is(sameInstance(key2)));
    assertThat(pool.takeKey(), is(sameInstance(key3)));
    assertThat(pool.takeKey(), is(nullValue()));
  }
}
