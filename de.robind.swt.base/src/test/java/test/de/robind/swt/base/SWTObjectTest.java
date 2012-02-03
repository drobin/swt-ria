package test.de.robind.swt.base;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

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
}
