package test.de.robind.swt.base;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

import de.robind.swt.base.Key;
import de.robind.swt.base.SWTObject;

public class SWTObjectTest {
  @Before
  public void before() throws Exception {
    // You need this "hack" to be able to predict the object id.
    // So, the id-counter "nextId" is resetted before every test runs.
    Field nextIdField = SWTObject.class.getDeclaredField("nextId");
    nextIdField.setAccessible(true);
    nextIdField.set(null, 0);
  }

  @Test
  public void getId() {
    assertThat(new SWTObject() {}.getId(), is(1));
    assertThat(new SWTObject() {}.getId(), is(2));
    assertThat(new SWTObject() {}.getId(), is(3));
  }

  @Test
  public void getKey() {
    assertThat(new SWTObject() {}.getKey(), is(nullValue()));
  }

  @Test
  public void setKey() throws Exception {
    Method setKeyMethod = SWTObject.class.getDeclaredMethod("setKey", Key.class);
    assertThat(setKeyMethod.getModifiers(), is(0)); // Test for default-access
    setKeyMethod.setAccessible(true);

    SWTObject obj = new SWTObject() {};
    Key key = new Key() {};

    assertThat(obj.getKey(), is(nullValue()));

    setKeyMethod.invoke(obj, key);
    assertThat(obj.getKey(), is(sameInstance(key)));

    setKeyMethod.invoke(obj, (Key)null);
    assertThat(obj.getKey(), is(nullValue()));
  }
}
