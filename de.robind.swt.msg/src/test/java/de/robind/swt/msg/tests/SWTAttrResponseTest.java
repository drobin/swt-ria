package de.robind.swt.msg.tests;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.robind.swt.msg.SWTAttrResponse;
import de.robind.swt.msg.SWTMessage;
import de.robind.swt.msg.SWTRequest;
import de.robind.swt.msg.SWTResponse;

public class SWTAttrResponseTest extends AbstractMessageTest {
  @Test
  public void isSWTMessage() {
    SWTAttrResponse msg = this.factory.createAttrResponse();
    assertThat(msg, is(instanceOf(SWTMessage.class)));
  }

  @Test
  public void isSWTResponse() {
    SWTAttrResponse msg = this.factory.createAttrResponse();
    assertThat(msg, is(instanceOf(SWTResponse.class)));
    assertThat(msg, is(not(instanceOf(SWTRequest.class))));
  }
}
