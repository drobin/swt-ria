package de.robind.swt.protocol.tests.decoder;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.jboss.netty.buffer.ChannelBuffer;
import org.junit.Test;

import de.robind.swt.msg.SWTCallResponse;
import de.robind.swt.protocol.SWTProtocol;
import de.robind.swt.protocol.SWTProtocolException;

public class SWTCallResponseTest extends AbstractDecoderTest<SWTCallResponse> {
  public SWTCallResponseTest() {
    super(SWTProtocol.OP_CALL, SWTProtocol.TYPE_RSP);
  }

  @Test
  public void voidResult() throws Throwable {
    ChannelBuffer buffer = createBuffer(0);

    SWTCallResponse msg = decodeMessage(buffer);

    assertThat(msg, is(notNullValue()));
    assertThat(msg.isVoid(), is(true));
  }

  @Test
  public void nonVoidResult() throws Throwable {
    ChannelBuffer buffer = createBuffer(5);
    SWTProtocol.writeArgument(buffer, 4711);

    SWTCallResponse msg = decodeMessage(buffer);

    assertThat(msg, is(notNullValue()));
    assertThat(msg.isVoid(), is(false));
    assertThat((Integer)msg.getResult(), is(4711));
  }

  @Test
  public void payloadNotEmptied() throws Throwable {
    ChannelBuffer buffer = createBuffer(6);
    SWTProtocol.writeArgument(buffer, 4711);

    buffer.writeByte(0);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Data still in payload. Available: 6, consumed: 5");

    decodeMessage(buffer);
  }

  @Test
  public void payloadOverflow() throws Throwable {
    ChannelBuffer buffer = createBuffer(3);
    SWTProtocol.writeArgument(buffer, 4711);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Payload-overflow. Available: 3, consumed: 5");

    decodeMessage(buffer);
  }
}
