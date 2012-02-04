package test.de.robind.swt.base;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.de.robind.swt.base.utils.TestClientTasks;
import test.de.robind.swt.base.utils.TestClientTasks.CreateObjectInvocation;
import test.de.robind.swt.base.utils.TestUtils;
import de.robind.swt.base.ClientTasks;
import de.robind.swt.base.Key;
import de.robind.swt.base.SWTObject;

public class SWTObjectTest {
  @Before
  public void before() throws Exception {
    TestUtils.resetNextId();
    TestUtils.resetInstance();

    System.setProperty("de.robind.swt.clienttasks", TestClientTasks.class.getName());
  }

  @After
  public void after() {
    System.getProperties().remove("de.robind.swt.clienttasks");
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
    SWTObject obj = new SWTObject() {};
    Key key = new Key() {};

    assertThat(obj.getKey(), is(nullValue()));

    setKey(obj, key);
    assertThat(obj.getKey(), is(sameInstance(key)));

    setKey(obj , null);
    assertThat(obj.getKey(), is(nullValue()));
  }

  @Test
  public void createObjectAs() throws Exception {
    SWTObject obj = new SWTObject() {};
    Key key = new Key() {};
    setKey(obj, key);

    obj.createObjectAs(Integer.class, 1, "foo");

    TestClientTasks clientTasks = (TestClientTasks)ClientTasks.getClientTasks();
    assertThat(clientTasks.invocationQueue.size(), is(1));
    assertThat(clientTasks.invocationQueue.peek(), is(instanceOf(CreateObjectInvocation.class)));

    CreateObjectInvocation invocation = (CreateObjectInvocation)clientTasks.invocationQueue.poll();

    assertThat(invocation.key, is(sameInstance(key)));
    assertThat(invocation.id, is(1));
    assertThat(invocation.objClass.getName(), equalTo(Integer.class.getName()));
    assertThat(invocation.args.length, is(2));
    assertThat((Integer)invocation.args[0], is(1));
    assertThat((String)invocation.args[1], is(equalTo("foo")));
  }

  @Test
  public void createObjectAsScheduled() throws Exception {
    SWTObject obj = new SWTObject() {};
    Key key = new Key() {};

    obj.createObjectAs(Integer.class, 1, "foo");

    TestClientTasks clientTasks = (TestClientTasks)ClientTasks.getClientTasks();
    assertThat(clientTasks.invocationQueue.size(), is(0));

    setKey(obj, key);

    assertThat(clientTasks.invocationQueue.size(), is(1));
    assertThat(clientTasks.invocationQueue.peek(), is(instanceOf(CreateObjectInvocation.class)));

    CreateObjectInvocation invocation = (CreateObjectInvocation)clientTasks.invocationQueue.poll();

    assertThat(invocation.key, is(sameInstance(key)));
    assertThat(invocation.id, is(1));
    assertThat(invocation.objClass.getName(), equalTo(Integer.class.getName()));
    assertThat(invocation.args.length, is(2));
    assertThat((Integer)invocation.args[0], is(1));
    assertThat((String)invocation.args[1], is(equalTo("foo")));
  }

  private void setKey(SWTObject obj, Key key)
      throws NoSuchMethodException, InvocationTargetException,
             IllegalAccessException {

    Method setKeyMethod = SWTObject.class.getDeclaredMethod("setKey", Key.class);
    assertThat(setKeyMethod.getModifiers(), is(0)); // Test for default-access
    setKeyMethod.setAccessible(true);
    setKeyMethod.invoke(obj, key);
  }
}
