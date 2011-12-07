package de.robind.swt.msg.tests;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.robind.swt.msg.SWTEvent;
import de.robind.swt.msg.SWTMessage;

public class SWTEventTest extends AbstractMessageTest {
  @Test
  public void isSWTMessage() {
    SWTEvent msg = this.factory.createEvent();
    assertThat(msg, is(instanceOf(SWTMessage.class)));
  }
}
