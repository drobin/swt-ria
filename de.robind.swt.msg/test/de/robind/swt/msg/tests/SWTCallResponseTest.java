package de.robind.swt.msg.tests;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.robind.swt.msg.SWTCallResponse;
import de.robind.swt.msg.SWTMessage;
import de.robind.swt.msg.SWTRequest;
import de.robind.swt.msg.SWTResponse;

public class SWTCallResponseTest extends AbstractMessageTest {
  @Test
  public void isSWTMessage() {
    SWTCallResponse msg = this.factory.createCallResponse(0);
    assertThat(msg, is(instanceOf(SWTMessage.class)));
  }

  @Test
  public void isSWTResponse() {
    SWTCallResponse msg = this.factory.createCallResponse(0);
    assertThat(msg, is(instanceOf(SWTResponse.class)));
    assertThat(msg, is(not(instanceOf(SWTRequest.class))));
  }

  @Test
  public void nullResult() {
    SWTCallResponse msg = this.factory.createCallResponse(null);
    assertThat(msg.getResult(), is(nullValue()));
  }

  @Test
  public void nonNullResult() {
    Object result = 4711;
    SWTCallResponse msg = this.factory.createCallResponse(result);
    assertThat(msg.getResult(), is(sameInstance(result)));
  }
}
