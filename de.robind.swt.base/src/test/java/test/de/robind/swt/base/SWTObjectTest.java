package test.de.robind.swt.base;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import static test.de.robind.swt.base.utils.SWTExceptionMatcher.swtCode;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import test.de.robind.swt.base.utils.TestClientTasks;
import test.de.robind.swt.base.utils.TestClientTasks.CallMethodInvocation;
import test.de.robind.swt.base.utils.TestClientTasks.CreateObjectInvocation;
import test.de.robind.swt.base.utils.TestClientTasks.RegisterEventInvocation;
import test.de.robind.swt.base.utils.TestClientTasks.UpdateAttributeInvocation;
import test.de.robind.swt.base.utils.TestUtils;
import de.robind.swt.base.ClientTasks;
import de.robind.swt.base.Key;
import de.robind.swt.base.SWTObject;

public class SWTObjectTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

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

    obj.setKey(key);
    assertThat(obj.getKey(), is(sameInstance(key)));

    obj.setKey(null);
    assertThat(obj.getKey(), is(nullValue()));
  }

  @Test
  public void createObject() throws Exception {
    SWTObject obj = new SWTObject() {};
    Key key = new Key() {};
    obj.setKey(key);

    obj.createObject(1, "foo");

    TestClientTasks clientTasks = (TestClientTasks)ClientTasks.getClientTasks();
    assertThat(clientTasks.invocationQueue.size(), is(1));
    assertThat(clientTasks.invocationQueue.peek(), is(instanceOf(CreateObjectInvocation.class)));

    CreateObjectInvocation invocation = (CreateObjectInvocation)clientTasks.invocationQueue.poll();

    assertThat(invocation.key, is(sameInstance(key)));
    assertThat(invocation.id, is(1));
    assertThat(invocation.objClass.getName(), equalTo(obj.getClass().getName()));
    assertThat(invocation.args.length, is(2));
    assertThat((Integer)invocation.args[0], is(1));
    assertThat((String)invocation.args[1], is(equalTo("foo")));
  }

  @Test
  public void createObjectScheduled() throws Exception {
    SWTObject obj = new SWTObject() {};
    Key key = new Key() {};

    obj.createObject(1, "foo");

    TestClientTasks clientTasks = (TestClientTasks)ClientTasks.getClientTasks();
    assertThat(clientTasks.invocationQueue.size(), is(0));

    obj.setKey(key);

    assertThat(clientTasks.invocationQueue.size(), is(1));
    assertThat(clientTasks.invocationQueue.peek(), is(instanceOf(CreateObjectInvocation.class)));

    CreateObjectInvocation invocation = (CreateObjectInvocation)clientTasks.invocationQueue.poll();

    assertThat(invocation.key, is(sameInstance(key)));
    assertThat(invocation.id, is(1));
    assertThat(invocation.objClass.getName(), equalTo(obj.getClass().getName()));
    assertThat(invocation.args.length, is(2));
    assertThat((Integer)invocation.args[0], is(1));
    assertThat((String)invocation.args[1], is(equalTo("foo")));
  }

  @Test
  public void createObjectAs() throws Exception {
    SWTObject obj = new SWTObject() {};
    Key key = new Key() {};
    obj.setKey(key);

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

    obj.setKey(key);

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
  public void callMethod() throws Exception {
    SWTObject obj = new SWTObject() {};
    Key key = new Key() {};
    obj.setKey(key);

    TestClientTasks clientTasks = (TestClientTasks)ClientTasks.getClientTasks();
    clientTasks.callMethodResult = 4711;

    Object result = obj.callMethod("foo", 1, "xxx");

    assertThat(clientTasks.invocationQueue.size(), is(1));
    assertThat(clientTasks.invocationQueue.peek(), is(instanceOf(CallMethodInvocation.class)));

    CallMethodInvocation invocation = (CallMethodInvocation)clientTasks.invocationQueue.poll();
    assertThat((Integer)result, is(4711));
    assertThat(invocation.key, is(sameInstance(key)));
    assertThat(invocation.id, is(1));
    assertThat(invocation.method, is(equalTo("foo")));
    assertThat(invocation.args.length, is(2));
    assertThat((Integer)invocation.args[0], is(1));
    assertThat((String)invocation.args[1], is("xxx"));
  }

  @Test
  public void callMethodScheduled() {
    SWTObject obj = new SWTObject() {};

    exception.expect(SWTException.class);
    exception.expect(swtCode(SWT.ERROR_FAILED_EXEC));

    obj.callMethod("foo", 1, "xxx");
  }

  @Test
  public void registerEvent() throws Exception {
    SWTObject obj = new SWTObject() {};
    Key key = new Key() {};
    obj.setKey(key);

    obj.registerEvent(4711, true);

    TestClientTasks clientTasks = (TestClientTasks)ClientTasks.getClientTasks();
    assertThat(clientTasks.invocationQueue.size(), is(1));
    assertThat(clientTasks.invocationQueue.peek(), is(instanceOf(RegisterEventInvocation.class)));

    RegisterEventInvocation invocation = (RegisterEventInvocation)clientTasks.invocationQueue.poll();

    assertThat(invocation.key, is(sameInstance(key)));
    assertThat(invocation.id, is(1));
    assertThat(invocation.eventType, is(4711));
    assertThat(invocation.enable, is(true));
  }

  @Test
  public void registerEventScheduled() throws Exception {
    SWTObject obj = new SWTObject() {};
    Key key = new Key() {};

    obj.registerEvent(4711, true);

    TestClientTasks clientTasks = (TestClientTasks)ClientTasks.getClientTasks();
    assertThat(clientTasks.invocationQueue.size(), is(0));

    obj.setKey(key);

    assertThat(clientTasks.invocationQueue.size(), is(1));
    assertThat(clientTasks.invocationQueue.peek(), is(instanceOf(RegisterEventInvocation.class)));

    RegisterEventInvocation invocation = (RegisterEventInvocation)clientTasks.invocationQueue.poll();

    assertThat(invocation.key, is(sameInstance(key)));
    assertThat(invocation.id, is(1));
    assertThat(invocation.eventType, is(4711));
    assertThat(invocation.enable, is(true));
  }

  @Test
  public void updateAttribute() throws Exception {
    SWTObject obj = new SWTObject() {};
    Key key = new Key() {};
    obj.setKey(key);

    obj.updateAttribute("foo", 4711);

    TestClientTasks clientTasks = (TestClientTasks)ClientTasks.getClientTasks();
    assertThat(clientTasks.invocationQueue.size(), is(1));
    assertThat(clientTasks.invocationQueue.peek(), is(instanceOf(UpdateAttributeInvocation.class)));

    UpdateAttributeInvocation invocation = (UpdateAttributeInvocation)clientTasks.invocationQueue.poll();

    assertThat(invocation.key, is(sameInstance(key)));
    assertThat(invocation.id, is(1));
    assertThat(invocation.attrName, is(equalTo("foo")));
    assertThat((Integer)invocation.attrValue, is(4711));
  }

  @Test
  public void updateAttributeScheduled() throws Exception {
    SWTObject obj = new SWTObject() {};
    Key key = new Key() {};

    obj.updateAttribute("foo", 4711);

    TestClientTasks clientTasks = (TestClientTasks)ClientTasks.getClientTasks();
    assertThat(clientTasks.invocationQueue.size(), is(0));

    obj.setKey(key);

    assertThat(clientTasks.invocationQueue.size(), is(1));
    assertThat(clientTasks.invocationQueue.peek(), is(instanceOf(UpdateAttributeInvocation.class)));

    UpdateAttributeInvocation invocation = (UpdateAttributeInvocation)clientTasks.invocationQueue.poll();

    assertThat(invocation.key, is(sameInstance(key)));
    assertThat(invocation.id, is(1));
    assertThat(invocation.attrName, is(equalTo("foo")));
    assertThat((Integer)invocation.attrValue, is(4711));
  }
}
