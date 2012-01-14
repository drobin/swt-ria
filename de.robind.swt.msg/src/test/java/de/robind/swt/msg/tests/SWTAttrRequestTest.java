package de.robind.swt.msg.tests;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.robind.swt.msg.SWTAttrRequest;
import de.robind.swt.msg.SWTMessage;
import de.robind.swt.msg.SWTRequest;
import de.robind.swt.msg.SWTResponse;

public class SWTAttrRequestTest extends AbstractMessageTest {
  @Test
  public void isSWTMessage() {
    SWTAttrRequest msg = this.factory.createAttrRequest(1, "xxx", null);
    assertThat(msg, is(instanceOf(SWTMessage.class)));
  }

  @Test
  public void isSWTRequest() {
    SWTAttrRequest msg = this.factory.createAttrRequest(1, "xxx", null);
    assertThat(msg, is(instanceOf(SWTRequest.class)));
    assertThat(msg, is(not(instanceOf(SWTResponse.class))));
  }

  @Test
  public void nullAttribute() {
    exception.expect(NullPointerException.class);
    exception.expectMessage("attrName cannot be null");

    this.factory.createAttrRequest(1, null, null);
  }

  @Test
  public void emptyAttribute() {
    exception.expect(IllegalArgumentException.class);
    exception.expectMessage("attrName cannot be empty");

    this.factory.createAttrRequest(1, "", null);
  }

  @Test
  public void getObjId() {
    SWTAttrRequest msg = this.factory.createAttrRequest(4711, "xxx", null);
    assertThat(msg.getObjId(), is(4711));
  }

  @Test
  public void getAttrName() {
    SWTAttrRequest msg = this.factory.createAttrRequest(4711, "xxx", null);
    assertThat(msg.getAttrName(), is(equalTo("xxx")));
  }

  @Test
  public void getAttrValue() {
    Object value = new Object();
    SWTAttrRequest msg = this.factory.createAttrRequest(4711, "xxx", value);
    assertThat(msg.getAttrValue(), is(sameInstance(value)));
  }
}
