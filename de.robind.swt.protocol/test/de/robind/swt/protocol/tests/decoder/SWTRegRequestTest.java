package de.robind.swt.protocol.tests.decoder;

import static de.robind.swt.protocol.datatype.SWTBoolean.writeBoolean;
import static de.robind.swt.protocol.datatype.SWTInteger.writeInteger;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.jboss.netty.buffer.ChannelBuffer;
import org.junit.Test;

import de.robind.swt.msg.SWTRegRequest;
import de.robind.swt.protocol.SWTProtocol;
import de.robind.swt.protocol.SWTProtocolException;

public class SWTRegRequestTest extends AbstractDecoderTest<SWTRegRequest> {
  public SWTRegRequestTest() {
    super(SWTProtocol.OP_REG, SWTProtocol.TYPE_REQ);
  }

  @Test
  public void success() throws Throwable {
    ChannelBuffer buffer = createBuffer(15);
    writeInteger(buffer, 4711);
    writeInteger(buffer, 555);
    writeBoolean(buffer, true);

    SWTRegRequest msg = decodeMessage(buffer);

    assertThat(msg, is(notNullValue()));
    assertThat(msg.getObjId(), is(4711));
    assertThat(msg.getEventType(), is(555));
    assertThat(msg.enable(), is(true));
  }

  @Test
  public void payloadNotEmptied() throws Throwable {
    ChannelBuffer buffer = createBuffer(16);
    writeInteger(buffer, 4711);
    writeInteger(buffer, 555);
    writeBoolean(buffer, true);

    buffer.writeByte(0);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Data still in payload. Available: 16, consumed: 15");

    decodeMessage(buffer);
  }

  @Test
  public void payloadOverflow() throws Throwable {
    ChannelBuffer buffer = createBuffer(5);
    writeInteger(buffer, 4711);
    writeInteger(buffer, 555);
    writeBoolean(buffer, true);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Payload-overflow. Available: 5, consumed: 15");

    decodeMessage(buffer);
  }
}
