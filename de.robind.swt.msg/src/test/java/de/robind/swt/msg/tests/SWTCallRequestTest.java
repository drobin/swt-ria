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
import de.robind.swt.msg.SWTRequest;
import de.robind.swt.msg.SWTResponse;

public class SWTCallRequestTest extends AbstractMessageTest {
  @Test
  public void isSWTMessage() {
    SWTCallRequest msg = this.factory.createCallRequest(1, "xxx");
    assertThat(msg, is(instanceOf(SWTMessage.class)));
  }

  @Test
  public void isSWTRequest() {
    SWTCallRequest msg = this.factory.createCallRequest(1, "xxx");
    assertThat(msg, is(instanceOf(SWTRequest.class)));
    assertThat(msg, is(not(instanceOf(SWTResponse.class))));
  }

  @Test
  public void nullMethod() {
    exception.expect(NullPointerException.class);
    exception.expectMessage("method cannot be null");

    this.factory.createCallRequest(1, null);
  }

  @Test
  public void emptyMethod() {
    exception.expect(IllegalArgumentException.class);
    exception.expectMessage("method cannot be an empty string");

    this.factory.createCallRequest(1, "");
  }

  @Test
  public void getObjId() {
    SWTCallRequest msg = this.factory.createCallRequest(4711, "xxx");
    assertThat(msg.getObjId(), is(4711));
  }

  @Test
  public void getMethod() {
    SWTCallRequest msg =
        this.factory.createCallRequest(1, "xxx");
    assertThat(msg.getMethod(), is(equalTo("xxx")));
  }

  @Test
  public void noArguments() {
    SWTCallRequest msg = this.factory.createCallRequest(1, "xxx");
    assertThat(msg.getArguments().length, is(0));
  }

  @Test
  public void getArguments() {
    Object obj = new Object();
    SWTCallRequest msg = this.factory.createCallRequest(
        1, "xxx", "foo", 4711, obj);

    assertThat(msg.getArguments().length, is(3));
    assertThat(msg.getArguments()[0], is(instanceOf(String.class)));
    assertThat((String)msg.getArguments()[0], is(equalTo("foo")));
    assertThat(msg.getArguments()[1], is(instanceOf(Integer.class)));
    assertThat((Integer)msg.getArguments()[1], is(4711));
    assertThat(msg.getArguments()[2], is(sameInstance(obj)));
  }
}
