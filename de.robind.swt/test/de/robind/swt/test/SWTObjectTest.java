package de.robind.swt.test;

import static de.robind.swt.test.utils.SWTExceptionMatcher.swtCode;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Field;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTObject;
import org.eclipse.swt.server.Key;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SWTObjectTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @SuppressWarnings("unchecked")
  @Before
  public void before() throws Exception {
    Field f;

    f = SWTObject.class.getDeclaredField("nextId");
    f.setAccessible(true);
    f.set(null, 0);

    f = SWTObject.class.getDeclaredField("objMap");
    f.setAccessible(true);
    ((Map<Integer, SWTObject>)f.get(null)).clear();
  }

  @Test
  public void getId() {
    SWTObject obj1 = new SWTObject() {};
    SWTObject obj2 = new SWTObject() {};
    SWTObject obj3 = new SWTObject() {};

    assertThat(obj1.getId(), is(1));
    assertThat(obj2.getId(), is(2));
    assertThat(obj3.getId(), is(3));
  }

  @Test
  public void findObjectById() {
    SWTObject obj1 = new SWTObject() {};
    SWTObject obj2 = new SWTObject() {};
    SWTObject obj3 = new SWTObject() {};

    assertThat(SWTObject.findObjectById(0), is(nullValue()));
    assertThat(SWTObject.findObjectById(1), is(sameInstance(obj1)));
    assertThat(SWTObject.findObjectById(2), is(sameInstance(obj2)));
    assertThat(SWTObject.findObjectById(3), is(sameInstance(obj3)));
    assertThat(SWTObject.findObjectById(4), is(nullValue()));
  }

  @Test
  public void keyCtorNullArgument() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));
    new SWTObject(null) {};
  }

  @Test
  public void getKey() {
    Key key = new Key() {};
    SWTObject obj = new SWTObject(key) {};
    assertThat(obj.getKey(), is(sameInstance(key)));
  }

  @Test
  public void setKey() {
    Key key1 = new Key() {};
    Key key2 = new Key() {};
    class TestSWTObject extends SWTObject {
      public TestSWTObject(Key key) {
        super(key);
      }

      public void setKeyTest(Key key) {
        setKey(key);
      }
    };

    TestSWTObject obj = new TestSWTObject(key1);

    assertThat(obj.getKey(), is(sameInstance(key1)));

    obj.setKeyTest(null);
    assertThat(obj.getKey(), is(nullValue()));

    obj.setKeyTest(key2);
    assertThat(obj.getKey(), is(sameInstance(key2)));
  }
}
