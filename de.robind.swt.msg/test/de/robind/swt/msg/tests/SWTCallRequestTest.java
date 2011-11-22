package de.robind.swt.msg.tests;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.robind.swt.msg.SWTCallRequest;
import de.robind.swt.msg.SWTMessage;
import de.robind.swt.msg.SWTObjectId;
import de.robind.swt.msg.SWTOpCall;
import de.robind.swt.msg.SWTRequest;
import de.robind.swt.msg.SWTResponse;
import de.robind.swt.msg.SWTTrap;

public class SWTCallRequestTest {
  @Test
  public void isSWTMessage() {
    SWTCallRequest msg = new SWTCallRequest(SWTObjectId.undefined(), "xxx");
    assertThat(msg, is(instanceOf(SWTMessage.class)));
  }

  @Test
  public void isSWTRequest() {
    SWTCallRequest msg = new SWTCallRequest(SWTObjectId.undefined(), "xxx");
    assertThat(msg, is(instanceOf(SWTRequest.class)));
    assertThat(msg, is(not(instanceOf(SWTResponse.class))));
    assertThat(msg, is(not(instanceOf(SWTTrap.class))));
  }

  @Test
  public void isSWTOpCall() {
    SWTCallRequest msg = new SWTCallRequest(SWTObjectId.undefined(), "xxx");
    assertThat(msg, is(instanceOf(SWTOpCall.class)));
  }

  @Test(expected = NullPointerException.class)
  public void nullObjId() {
    new SWTCallRequest(null, "xxx");
  }

  @Test(expected = NullPointerException.class)
  public void nullMethod() {
    new SWTCallRequest(SWTObjectId.undefined(), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void emptyMethod() {
    new SWTCallRequest(SWTObjectId.undefined(), "");
  }

  @Test
  public void getDestinationObject() {
    SWTObjectId objId = new SWTObjectId(4711);
    SWTCallRequest msg = new SWTCallRequest(objId, "xxx");
    assertThat(msg.getDestinationObject(), is(sameInstance(objId)));
  }

  @Test
  public void getMethod() {
    SWTCallRequest msg = new SWTCallRequest(SWTObjectId.undefined(), "xxx");
    assertThat(msg.getMethod(), is(equalTo("xxx")));
  }

  @Test
  public void noArguments() {
    SWTCallRequest msg = new SWTCallRequest(SWTObjectId.undefined(), "xxx");
    assertThat(msg.getArguments().length, is(0));
  }

  @Test
  public void getArguments() {
    Object obj = new Object();

    SWTCallRequest msg = new SWTCallRequest(SWTObjectId.undefined(), "xxx",
        "foo", 4711, obj);
    assertThat(msg.getArguments().length, is(3));
    assertThat(msg.getArguments()[0], is(instanceOf(String.class)));
    assertThat((String)msg.getArguments()[0], is(equalTo("foo")));
    assertThat(msg.getArguments()[1], is(instanceOf(Integer.class)));
    assertThat((Integer)msg.getArguments()[1], is(4711));
    assertThat(msg.getArguments()[2], is(sameInstance(obj)));
  }
}
