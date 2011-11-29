package de.robind.swt.msg.tests;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.robind.swt.msg.SWTEvent;
import de.robind.swt.msg.SWTMessage;
import de.robind.swt.msg.SWTRequest;
import de.robind.swt.msg.SWTResponse;
import de.robind.swt.msg.SWTTrap;

public class SWTEventTest {
  @Test
  public void isSWTMessage() {
    SWTEvent msg = new SWTEvent();
    assertThat(msg, is(instanceOf(SWTMessage.class)));
  }

  @Test
  public void isSWTTrap() {
    SWTEvent msg = new SWTEvent();
    assertThat(msg, is(not(instanceOf(SWTRequest.class))));
    assertThat(msg, is(not(instanceOf(SWTResponse.class))));
    assertThat(msg, is(instanceOf(SWTTrap.class)));
  }
}
