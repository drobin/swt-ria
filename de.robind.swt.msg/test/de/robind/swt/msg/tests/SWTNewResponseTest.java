package de.robind.swt.msg.tests;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.robind.swt.msg.SWTMessage;
import de.robind.swt.msg.SWTNewResponse;
import de.robind.swt.msg.SWTRequest;
import de.robind.swt.msg.SWTResponse;

public class SWTNewResponseTest extends AbstractMessageTest {
  @Test
  public void isSWTMessage() {
    SWTNewResponse msg = this.factory.createNewResponse();
    assertThat(msg, is(instanceOf(SWTMessage.class)));
  }

  @Test
  public void isSWTResponse() {
    SWTNewResponse msg = this.factory.createNewResponse();
    assertThat(msg, is(instanceOf(SWTResponse.class)));
    assertThat(msg, is(not(instanceOf(SWTRequest.class))));
  }
}
