package de.robind.swt.msg.tests;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.robind.swt.msg.SWTMessage;
import de.robind.swt.msg.SWTOpReg;
import de.robind.swt.msg.SWTRegResponse;
import de.robind.swt.msg.SWTRequest;
import de.robind.swt.msg.SWTResponse;
import de.robind.swt.msg.SWTTrap;

public class SWTRegResponseTest {
  @Test
  public void isSWTMessage() {
    SWTRegResponse msg = SWTRegResponse.success();
    assertThat(msg, is(instanceOf(SWTMessage.class)));
  }

  @Test
  public void isSWTResponse() {
    SWTRegResponse msg = SWTRegResponse.success();
    assertThat(msg, is(instanceOf(SWTResponse.class)));
    assertThat(msg, is(not(instanceOf(SWTRequest.class))));
    assertThat(msg, is(not(instanceOf(SWTTrap.class))));
  }

  @Test
  public void isSWTOpReg() {
    SWTRegResponse msg = SWTRegResponse.success();
    assertThat(msg, is(instanceOf(SWTOpReg.class)));
  }

  @Test(expected = NullPointerException.class)
  public void nullExcClassName() {
    new SWTRegResponse(null, "blubber");
  }

  @Test(expected = NullPointerException.class)
  public void nullExcMessage() {
    new SWTRegResponse("blubber", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void emptyExcClassName() {
    new SWTRegResponse("", "blubber");
  }

  @Test
  public void success() {
    SWTRegResponse msg = SWTRegResponse.success();
    assertThat(msg.isSuccessful(), is(true));
    assertThat(msg.getExceptionClass(), is(nullValue()));
    assertThat(msg.getExceptionMessage(), is(nullValue()));
  }

  @Test
  public void unsuccessful() {
    SWTRegResponse msg = new SWTRegResponse("123", "blubber");
    assertThat(msg.isSuccessful(), is(false));
    assertThat(msg.getExceptionClass(), is(equalTo("123")));
    assertThat(msg.getExceptionMessage(), is(equalTo("blubber")));
  }
}
