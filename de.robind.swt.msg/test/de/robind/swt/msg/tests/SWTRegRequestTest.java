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

public class SWTRegRequestTest extends AbstractMessageTest {
  @Test
  public void isSWTMessage() {
    SWTRegRequest msg =
        this.factory.createRegRequest(SWTObjectId.undefined(), 0, true);
    assertThat(msg, is(instanceOf(SWTMessage.class)));
  }

  @Test
  public void isSWTRequest() {
    SWTRegRequest msg =
        this.factory.createRegRequest(SWTObjectId.undefined(), 0, true);
    assertThat(msg, is(instanceOf(SWTRequest.class)));
    assertThat(msg, is(not(instanceOf(SWTResponse.class))));
    assertThat(msg, is(not(instanceOf(SWTTrap.class))));
  }

  @Test
  public void nullObjId() {
    exception.expect(NullPointerException.class);
    exception.expectMessage("objId cannot be null");

    this.factory.createRegRequest(null, 0, true);
  }

  @Test
  public void getDestinationObject() {
    SWTObjectId objId = new SWTObjectId(4711);
    SWTRegRequest msg = this.factory.createRegRequest(objId, 0, true);

    assertThat(msg.getDestinationObject(), is(sameInstance(objId)));
  }

  @Test
  public void getEventType() {
    SWTRegRequest msg =
        this.factory.createRegRequest(SWTObjectId.undefined(), 4711, true);
    assertThat(msg.getEventType(), is(4711));
  }

  @Test
  public void enable() {
    SWTRegRequest msg;

    msg = this.factory.createRegRequest(SWTObjectId.undefined(), 4711, true);
    assertThat(msg.enable(), is(true));

    msg = this.factory.createRegRequest(SWTObjectId.undefined(), 4711, false);
    assertThat(msg.enable(), is(false));
  }
}
