package de.robind.swt.msg.tests;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.robind.swt.msg.SWTCallRequest;
import de.robind.swt.msg.SWTMessage;
import de.robind.swt.msg.SWTMessageFactory;
import de.robind.swt.msg.SWTObjectId;
import de.robind.swt.msg.SWTRequest;
import de.robind.swt.msg.SWTResponse;
import de.robind.swt.msg.SWTTrap;

public class SWTCallRequestTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  private SWTMessageFactory factory = null;

  @Before
  public void setup() {
    this.factory = new SWTMessageFactory();
  }

  @After
  public void teardown() {
    this.factory = null;
  }

  @Test
  public void isSWTMessage() {
    SWTCallRequest msg = this.factory.createCallRequest(new SWTObjectId(1), "xxx");
    assertThat(msg, is(instanceOf(SWTMessage.class)));
  }

  @Test
  public void isSWTRequest() {
    SWTCallRequest msg = this.factory.createCallRequest(new SWTObjectId(1), "xxx");
    assertThat(msg, is(instanceOf(SWTRequest.class)));
    assertThat(msg, is(not(instanceOf(SWTResponse.class))));
    assertThat(msg, is(not(instanceOf(SWTTrap.class))));
  }

  @Test
  public void nullObjId() {
    exception.expect(NullPointerException.class);
    exception.expectMessage("destObj cannot be null");

    this.factory.createCallRequest(null, "xxx");
  }

  @Test
  public void undefinedObjId() {
    exception.expect(IllegalArgumentException.class);
    exception.expectMessage("destObj cannot be invalid");

    this.factory.createCallRequest(SWTObjectId.undefined(), "xxx");
  }

  @Test
  public void nullMethod() {
    exception.expect(NullPointerException.class);
    exception.expectMessage("method cannot be null");

    this.factory.createCallRequest(new SWTObjectId(1), null);
  }

  @Test
  public void emptyMethod() {
    exception.expect(IllegalArgumentException.class);
    exception.expectMessage("method cannot be an empty string");

    this.factory.createCallRequest(new SWTObjectId(1), "");
  }

  @Test
  public void getDestinationObject() {
    SWTObjectId objId = new SWTObjectId(4711);
    SWTCallRequest msg = this.factory.createCallRequest(objId, "xxx");
    assertThat(msg.getDestinationObject(), is(sameInstance(objId)));
  }

  @Test
  public void getMethod() {
    SWTCallRequest msg =
        this.factory.createCallRequest(new SWTObjectId(1), "xxx");
    assertThat(msg.getMethod(), is(equalTo("xxx")));
  }

  @Test
  public void noArguments() {
    SWTCallRequest msg =
        this.factory.createCallRequest(new SWTObjectId(1), "xxx");
    assertThat(msg.getArguments().length, is(0));
  }

  @Test
  public void getArguments() {
    Object obj = new Object();
    SWTCallRequest msg = this.factory.createCallRequest(
        new SWTObjectId(1), "xxx", "foo", 4711, obj);

    assertThat(msg.getArguments().length, is(3));
    assertThat(msg.getArguments()[0], is(instanceOf(String.class)));
    assertThat((String)msg.getArguments()[0], is(equalTo("foo")));
    assertThat(msg.getArguments()[1], is(instanceOf(Integer.class)));
    assertThat((Integer)msg.getArguments()[1], is(4711));
    assertThat(msg.getArguments()[2], is(sameInstance(obj)));
  }
}
