package de.robind.swt.protocol.tests.encoder;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.jboss.netty.buffer.ChannelBuffer;
import org.junit.Test;

import de.robind.swt.msg.SWTCallResponse;
import de.robind.swt.protocol.SWTProtocol;

public class SWTCallResponseTest extends AbstractEncoderTest<SWTCallResponse> {
  public SWTCallResponseTest() {
    super(SWTProtocol.OP_CALL, SWTProtocol.TYPE_RSP);
  }

  @Test
  public void voidResult() throws Throwable {
    SWTCallResponse msg = this.factory.createCallResponse(null);
    encodeMessage(msg, 0);
  }

  @Test
  public void withResult() throws Throwable {
    SWTCallResponse msg = this.factory.createCallResponse(4711);

    ChannelBuffer buffer = encodeMessage(msg, 5);
    assertThat((Integer)SWTProtocol.readArgument(buffer), is(4711));
  }
}
