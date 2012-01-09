package de.robind.swt.protocol.tests.decoder;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.jboss.netty.buffer.ChannelBuffer;
import org.junit.Test;

import de.robind.swt.msg.SWTAttrResponse;
import de.robind.swt.protocol.SWTProtocol;
import de.robind.swt.protocol.SWTProtocolException;

public class SWTAttrResponseTest extends AbstractDecoderTest<SWTAttrResponse>{
  public SWTAttrResponseTest() {
    super(SWTProtocol.OP_ATTR, SWTProtocol.TYPE_RSP);
  }

  @Test
  public void success() throws Throwable {
    ChannelBuffer buffer = createBuffer(0);

    SWTAttrResponse msg = decodeMessage(buffer);

    assertThat(msg, is(notNullValue()));
  }

  @Test
  public void payloadNotEmptied() throws Throwable {
    ChannelBuffer buffer = createBuffer(1);

    buffer.writeByte(0);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Data still in payload. Available: 1, consumed: 0");

    decodeMessage(buffer);
  }
}
