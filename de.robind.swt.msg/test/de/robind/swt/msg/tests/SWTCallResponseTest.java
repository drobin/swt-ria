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

public class SWTCallResponseTest {
  @Test
  public void isSWTMessage() {
    SWTCallResponse msg = new SWTCallResponse(0);
    assertThat(msg, is(instanceOf(SWTMessage.class)));
  }

  @Test
  public void isSWTResponse() {
    SWTCallResponse msg = new SWTCallResponse(0);
    assertThat(msg, is(instanceOf(SWTResponse.class)));
    assertThat(msg, is(not(instanceOf(SWTRequest.class))));
  }

  @Test(expected = NullPointerException.class)
  public void nullResult() {
    new SWTCallResponse(null);
  }

  @Test
  public void voidResult() {
    SWTCallResponse msg = SWTCallResponse.voidResult();
    assertThat(msg.isVoid(), is(true));
    assertThat(msg.isException(), is(false));
    assertThat(msg.getResult(), is(nullValue()));
  }

  @Test
  public void exceptionResult() {
    Object exc = new NullPointerException();
    SWTCallResponse msg = new SWTCallResponse(exc);
    assertThat(msg.isVoid(), is(false));
    assertThat(msg.isException(), is(true));
    assertThat(msg.getResult(), is(sameInstance(exc)));
  }

  @Test
  public void regularResult() {
    Object result = 4711;
    SWTCallResponse msg = new SWTCallResponse(result);
    assertThat(msg.isVoid(), is(false));
    assertThat(msg.isException(), is(false));
    assertThat(msg.getResult(), is(sameInstance(result)));
  }
}
