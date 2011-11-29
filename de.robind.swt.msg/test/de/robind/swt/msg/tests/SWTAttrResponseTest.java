package de.robind.swt.msg.tests;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.robind.swt.msg.SWTAttrResponse;
import de.robind.swt.msg.SWTMessage;
import de.robind.swt.msg.SWTRequest;
import de.robind.swt.msg.SWTResponse;
import de.robind.swt.msg.SWTTrap;

public class SWTAttrResponseTest {
  @Test
  public void isSWTMessage() {
    SWTAttrResponse msg = SWTAttrResponse.success();
    assertThat(msg, is(instanceOf(SWTMessage.class)));
  }

  @Test
  public void isSWTResponse() {
    SWTAttrResponse msg = SWTAttrResponse.success();
    assertThat(msg, is(instanceOf(SWTResponse.class)));
    assertThat(msg, is(not(instanceOf(SWTRequest.class))));
    assertThat(msg, is(not(instanceOf(SWTTrap.class))));
  }

  @Test(expected = NullPointerException.class)
  public void nullExcClassName() {
    new SWTAttrResponse(null, "blubber");
  }

  @Test(expected = NullPointerException.class)
  public void nullExcMessage() {
    new SWTAttrResponse("blubber", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void emptyExcClassName() {
    new SWTAttrResponse("", "blubber");
  }

  @Test
  public void success() {
    SWTAttrResponse msg = SWTAttrResponse.success();
    assertThat(msg.isSuccessful(), is(true));
    assertThat(msg.getExceptionClass(), is(nullValue()));
    assertThat(msg.getExceptionMessage(), is(nullValue()));
  }

  @Test
  public void unsuccessful() {
    SWTAttrResponse msg = new SWTAttrResponse("123", "blubber");
    assertThat(msg.isSuccessful(), is(false));
    assertThat(msg.getExceptionClass(), is(equalTo("123")));
    assertThat(msg.getExceptionMessage(), is(equalTo("blubber")));
  }
}
