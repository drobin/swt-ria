package de.robind.swt.msg.tests;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.robind.swt.msg.SWTMessage;
import de.robind.swt.msg.SWTObjectId;
import de.robind.swt.msg.SWTRegRequest;
import de.robind.swt.msg.SWTRequest;
import de.robind.swt.msg.SWTResponse;
import de.robind.swt.msg.SWTTrap;

public class SWTRegRequestTest {
  @Test
  public void isSWTMessage() {
    SWTRegRequest msg = new SWTRegRequest(SWTObjectId.undefined(), 0, true);
    assertThat(msg, is(instanceOf(SWTMessage.class)));
  }

  @Test
  public void isSWTRequest() {
    SWTRegRequest msg = new SWTRegRequest(SWTObjectId.undefined(), 0, true);
    assertThat(msg, is(instanceOf(SWTRequest.class)));
    assertThat(msg, is(not(instanceOf(SWTResponse.class))));
    assertThat(msg, is(not(instanceOf(SWTTrap.class))));
  }

  @Test(expected = NullPointerException.class)
  public void nullObjId() {
    new SWTRegRequest(null, 0, true);
  }

  @Test
  public void getDestinationObject() {
    SWTObjectId objId = new SWTObjectId(4711);
    SWTRegRequest msg = new SWTRegRequest(objId, 0, true);

    assertThat(msg.getDestinationObject(), is(sameInstance(objId)));
  }

  @Test
  public void getEventType() {
    SWTRegRequest msg = new SWTRegRequest(SWTObjectId.undefined(), 4711, true);
    assertThat(msg.getEventType(), is(4711));
  }

  @Test
  public void enable() {
    SWTRegRequest msg;

    msg = new SWTRegRequest(SWTObjectId.undefined(), 4711, true);
    assertThat(msg.enable(), is(true));

    msg = new SWTRegRequest(SWTObjectId.undefined(), 4711, false);
    assertThat(msg.enable(), is(false));
  }
}
