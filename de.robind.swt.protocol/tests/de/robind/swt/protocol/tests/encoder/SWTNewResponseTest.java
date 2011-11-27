package de.robind.swt.protocol.tests.encoder;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.jboss.netty.buffer.ChannelBuffer;
import org.junit.Test;

import de.robind.swt.msg.SWTNewResponse;
import de.robind.swt.protocol.SWTProtocol;

public class SWTNewResponseTest extends AbstractEncoderTest<SWTNewResponse>{
  public SWTNewResponseTest() {
    super(SWTProtocol.OP_NEW, SWTProtocol.TYPE_RSP);
  }

  @Test
  public void success() {
    SWTNewResponse msg = SWTNewResponse.success();
    encodeMessage(msg, 0);
  }

  @Test
  public void withException() throws Exception {
    SWTNewResponse msg = new SWTNewResponse("java.lang.Exception", "foo");

    ChannelBuffer buffer = encodeMessage(msg, 26);
    assertThat(SWTProtocol.readString(buffer), is(equalTo("java.lang.Exception")));
    assertThat(SWTProtocol.readString(buffer), is(equalTo("foo")));
  }
}
