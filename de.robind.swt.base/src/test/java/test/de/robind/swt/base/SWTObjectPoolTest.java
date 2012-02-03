package test.de.robind.swt.base;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

import de.robind.swt.base.SWTObject;
import de.robind.swt.base.SWTObjectPool;

public class SWTObjectPoolTest {
  @Before
  public void before() throws Exception {
    // You need this "hack" to be able to predict the object id.
    // So, the id-counter "nextId" is resetted before every test runs.
    Field nextIdField = SWTObject.class.getDeclaredField("nextId");
    nextIdField.setAccessible(true);
    nextIdField.set(null, 0);
  }

  @Test
  public void getInstance() {
    SWTObjectPool pool = SWTObjectPool.getInstance();

    assertThat(pool, is(notNullValue()));
    assertThat(SWTObjectPool.getInstance(), is(sameInstance(pool)));
  }

  @Test
  public void findObjectById() {
    SWTObject obj1 = new SWTObject() {};
    SWTObject obj2 = new SWTObject() {};
    SWTObject obj3 = new SWTObject() {};

    SWTObjectPool pool = SWTObjectPool.getInstance();
    assertThat(pool.findObjectById(0), is(nullValue()));
    assertThat(pool.findObjectById(1), is(sameInstance(obj1)));
    assertThat(pool.findObjectById(2), is(sameInstance(obj2)));
    assertThat(pool.findObjectById(3), is(sameInstance(obj3)));
    assertThat(pool.findObjectById(4), is(nullValue()));
  }
}
