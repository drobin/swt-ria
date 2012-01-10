package de.robind.swt.test;

import static de.robind.swt.test.utils.ClientTaskMatcher.attrRequest;
import static de.robind.swt.test.utils.ClientTaskMatcher.callRequest;
import static de.robind.swt.test.utils.ClientTaskMatcher.createRequest;
import static de.robind.swt.test.utils.ClientTaskMatcher.registerRequest;
import static de.robind.swt.test.utils.SWTExceptionMatcher.swtCode;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Field;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTObject;
import org.eclipse.swt.server.DisplayPool;
import org.eclipse.swt.server.Key;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.robind.swt.test.utils.TestClientTasks;

public class SWTObjectTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @BeforeClass
  public static void beforeClass() {
    System.setProperty("de.robind.swt.clienttasks", TestClientTasks.class.getName());
  }

  @AfterClass
  public static void afterClass() {
    System.clearProperty("de.robind.swt.clienttasks");
  }

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

  @After
  public void after() {
    getClientTasks().clearState();
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
    SWTObject obj = new SWTObject(key1) {};

    assertThat(obj.getKey(), is(sameInstance(key1)));

    obj.setKey(null);
    assertThat(obj.getKey(), is(nullValue()));

    obj.setKey(key2);
    assertThat(obj.getKey(), is(sameInstance(key2)));
  }

  @Test
  public void createObjectWithKey() {
    Key key = new Key() {};
    SWTObject obj = new SWTObject(key) {};
    obj.createObject(1, 2, 3);

    assertThat(getClientTasks(), is(createRequest(obj, obj.getClass(), 1, 2, 3)));
  }

  @Test
  public void createObjectDelayed() {
    Key key = new Key() {};
    SWTObject obj = new SWTObject() {};

    obj.createObject(1, 2, 3);
    assertThat(getClientTasks().getQueueSize(), is(0));

    obj.setKey(key);
    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), is(createRequest(obj, obj.getClass(), 1, 2, 3)));
  }

  @Test
  public void createObjectAsWithKey() {
    Key key = new Key() {};
    SWTObject obj = new SWTObject(key) {};
    obj.createObjectAs(Integer.class, 1, 2, 3);

    assertThat(getClientTasks(), is(createRequest(obj, Integer.class, 1, 2, 3)));
  }

  @Test
  public void createObjectAsDelayed() {
    Key key = new Key() {};
    SWTObject obj = new SWTObject() {};

    obj.createObjectAs(Integer.class, 1, 2, 3);
    assertThat(getClientTasks().getQueueSize(), is(0));

    obj.setKey(key);
    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), is(createRequest(obj, Integer.class, 1, 2, 3)));
  }

  @Test
  public void callMethodWithKey() {
    getClientTasks().setCallMethodResult("xxx");
    Key key = new Key() {};
    SWTObject obj = new SWTObject(key) {};

    Object result = obj.callMethod("foo", 1, 2, 3);
    assertThat((String)result, is(equalTo("xxx")));
    assertThat(getClientTasks(), is(callRequest(obj, "foo", 1, 2, 3)));
  }

  @Test
  public void callMethodDelayed() {
    SWTObject obj = new SWTObject() {};

    exception.expect(swtCode(SWT.ERROR_FAILED_EXEC));

    obj.callMethod("foo", 1, 2, 3);
  }

  @Test
  public void registerEventWithKey() {
    Key key = new Key() {};
    SWTObject obj = new SWTObject(key) {};

    obj.registerEvent(4711, true);
    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), is(registerRequest(obj, 4711, true)));
  }

  @Test
  public void registerEventDelayed() {
    Key key = new Key() {};
    SWTObject obj = new SWTObject() {};

    obj.registerEvent(4711, true);
    assertThat(getClientTasks().getQueueSize(), is(0));

    obj.setKey(key);
    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), is(registerRequest(obj, 4711, true)));
  }

  @Test
  public void updateAttributeNoSuchField() {
    Key key = new Key() {};
    SWTObject obj = new SWTObject(key) {};

    exception.expect(swtCode(SWT.ERROR_UNSPECIFIED));

    obj.updateAttribute("foo");
  }

  @Test
  public void updateAttributeWithKey() {
    class TestObject extends SWTObject {
      TestObject(Key key) {
        super(key);
      }

      public int foo;
    }

    Key key = new Key() {};
    TestObject obj = new TestObject(key);

    obj.foo = 4711;
    obj.updateAttribute("foo");

    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), is(attrRequest(obj, "foo", 4711)));
  }

  @Test
  public void updateAttributeDelayed() {
    class TestObject extends SWTObject {
      public int foo;
    }

    Key key = new Key() {};
    TestObject obj = new TestObject();

    obj.foo = 4711;
    obj.updateAttribute("foo");
    assertThat(getClientTasks().getQueueSize(), is(0));

    obj.setKey(key);
    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), is(attrRequest(obj, "foo", 4711)));
  }

  protected TestClientTasks getClientTasks() {
    DisplayPool pool = DisplayPool.getInstance();
    return ((TestClientTasks)pool.getClientTasks());
  }


}
