package de.robind.swt.msg.tests;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.robind.swt.msg.SWTMessage;
import de.robind.swt.msg.SWTRegRequest;
import de.robind.swt.msg.SWTRequest;
import de.robind.swt.msg.SWTResponse;

public class SWTRegRequestTest extends AbstractMessageTest {
  @Test
  public void isSWTMessage() {
    SWTRegRequest msg = this.factory.createRegRequest(1, 0, true);
    assertThat(msg, is(instanceOf(SWTMessage.class)));
  }

  @Test
  public void isSWTRequest() {
    SWTRegRequest msg =
        this.factory.createRegRequest(1, 0, true);
    assertThat(msg, is(instanceOf(SWTRequest.class)));
    assertThat(msg, is(not(instanceOf(SWTResponse.class))));
  }

  @Test
  public void getObjId() {
    SWTRegRequest msg = this.factory.createRegRequest(4711, 0, true);

    assertThat(msg.getObjId(), is(4711));
  }

  @Test
  public void getEventType() {
    SWTRegRequest msg =
        this.factory.createRegRequest(1, 4711, true);
    assertThat(msg.getEventType(), is(4711));
  }

  @Test
  public void enable() {
    SWTRegRequest msg;

    msg = this.factory.createRegRequest(1, 4711, true);
    assertThat(msg.enable(), is(true));

    msg = this.factory.createRegRequest(1, 4711, false);
    assertThat(msg.enable(), is(false));
  }
}
