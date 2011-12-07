package de.robind.swt.protocol.tests.decoder;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.jboss.netty.buffer.ChannelBuffer;
import org.junit.Test;

import de.robind.swt.msg.SWTEvent;
import de.robind.swt.protocol.SWTProtocol;
import de.robind.swt.protocol.SWTProtocolException;

public class SWTEventTest extends AbstractDecoderTest<SWTEvent> {
  public SWTEventTest() {
    super(SWTProtocol.OP_CALL, SWTProtocol.TYPE_EVT);
  }

  @Test
  public void regularMessage() throws Throwable {
    ChannelBuffer buffer = createBuffer(4);
    buffer.writeInt(4711);

    SWTEvent msg = decodeMessage(buffer);
    assertThat(msg, is(notNullValue()));
    assertThat(msg.getObjId(), is(4711));
  }

  @Test
  public void payloadNotEmptied() throws Throwable {
    ChannelBuffer buffer = createBuffer(5);
    buffer.writeInt(4711);

    buffer.writeByte(0);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Data still in payload. Available: 5, consumed: 4");

    decodeMessage(buffer);
  }

  @Test
  public void payloadOverflow() throws Throwable {
    ChannelBuffer buffer = createBuffer(3);
    buffer.writeInt(4711);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Payload-overflow. Available: 3, consumed: 4");

    decodeMessage(buffer);
  }
}
