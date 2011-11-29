package de.robind.swt.msg.tests;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.robind.swt.msg.SWTMessage;
import de.robind.swt.msg.SWTNewResponse;
import de.robind.swt.msg.SWTRequest;
import de.robind.swt.msg.SWTResponse;
import de.robind.swt.msg.SWTTrap;

public class SWTNewResponseTest {
  @Test
  public void isSWTMessage() {
    SWTNewResponse msg = SWTNewResponse.success();
    assertThat(msg, is(instanceOf(SWTMessage.class)));
  }

  @Test
  public void isSWTResponse() {
    SWTNewResponse msg = SWTNewResponse.success();
    assertThat(msg, is(instanceOf(SWTResponse.class)));
    assertThat(msg, is(not(instanceOf(SWTRequest.class))));
    assertThat(msg, is(not(instanceOf(SWTTrap.class))));
  }

  @Test(expected = NullPointerException.class)
  public void nullExcClassName() {
    new SWTNewResponse(null, "blubber");
  }

  @Test(expected = NullPointerException.class)
  public void nullExcMessage() {
    new SWTNewResponse("blubber", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void emptyExcClassName() {
    new SWTNewResponse("", "blubber");
  }

  @Test
  public void success() {
    SWTNewResponse msg = SWTNewResponse.success();
    assertThat(msg.isSuccessful(), is(true));
    assertThat(msg.getExceptionClass(), is(nullValue()));
    assertThat(msg.getExceptionMessage(), is(nullValue()));
  }

  @Test
  public void unsuccessful() {
    SWTNewResponse msg = new SWTNewResponse("123", "blubber");
    assertThat(msg.isSuccessful(), is(false));
    assertThat(msg.getExceptionClass(), is(equalTo("123")));
    assertThat(msg.getExceptionMessage(), is(equalTo("blubber")));
  }
}
