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
    SWTEvent msg = this.factory.createEvent(0);
    assertThat(msg, is(instanceOf(SWTMessage.class)));
  }

  @Test
  public void getId() {
    SWTEvent msg = this.factory.createEvent(4711);
    assertThat(msg.getObjId(), is(4711));
  }
}
