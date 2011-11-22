package de.robind.swt.msg.tests;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.robind.swt.msg.SWTAttrRequest;
import de.robind.swt.msg.SWTMessage;
import de.robind.swt.msg.SWTObjectId;
import de.robind.swt.msg.SWTOpAttr;
import de.robind.swt.msg.SWTRequest;
import de.robind.swt.msg.SWTResponse;
import de.robind.swt.msg.SWTTrap;

public class SWTAttrRequestTest {
  @Test
  public void isSWTMessage() {
    SWTAttrRequest msg = new SWTAttrRequest(SWTObjectId.undefined(), "xxx", null);
    assertThat(msg, is(instanceOf(SWTMessage.class)));
  }

  @Test
  public void isSWTRequest() {
    SWTAttrRequest msg = new SWTAttrRequest(SWTObjectId.undefined(), "xxx", null);
    assertThat(msg, is(instanceOf(SWTRequest.class)));
    assertThat(msg, is(not(instanceOf(SWTResponse.class))));
    assertThat(msg, is(not(instanceOf(SWTTrap.class))));
  }

  @Test
  public void isSWTOpAttr() {
    SWTAttrRequest msg = new SWTAttrRequest(SWTObjectId.undefined(), "xxx", null);
    assertThat(msg, is(instanceOf(SWTOpAttr.class)));
  }

  @Test(expected = NullPointerException.class)
  public void nullObjId() {
    new SWTAttrRequest(null, "xxx", null);
  }

  @Test(expected = NullPointerException.class)
  public void nullName() {
    new SWTAttrRequest(SWTObjectId.undefined(), null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void emptyName() {
    new SWTAttrRequest(SWTObjectId.undefined(), "", null);
  }

  @Test
  public void getDestinationObject() {
    SWTObjectId objId = new SWTObjectId(4711);
    SWTAttrRequest msg = new SWTAttrRequest(objId, "xxx", null);
    assertThat(msg.getDestinationObject(), is(sameInstance(objId)));
  }

  @Test
  public void getName() {
    SWTAttrRequest msg = new SWTAttrRequest(SWTObjectId.undefined(), "xxx", null);
    assertThat(msg.getName(), is(equalTo("xxx")));
  }

  @Test
  public void getNullValue() {
    SWTAttrRequest msg = new SWTAttrRequest(SWTObjectId.undefined(), "xxx", null);
    assertThat(msg.getValue(), is(nullValue()));
  }

  @Test
  public void getValue() {
    Object obj = new Object();
    SWTAttrRequest msg = new SWTAttrRequest(SWTObjectId.undefined(), "xxx", obj);
    assertThat(msg.getValue(), is(sameInstance(obj)));
  }
}
