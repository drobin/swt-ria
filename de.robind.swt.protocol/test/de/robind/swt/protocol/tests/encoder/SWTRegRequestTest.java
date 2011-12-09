package de.robind.swt.protocol.tests.encoder;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.jboss.netty.buffer.ChannelBuffer;
import org.junit.Test;

import de.robind.swt.msg.SWTRegRequest;
import de.robind.swt.protocol.SWTProtocol;

public class SWTRegRequestTest extends AbstractEncoderTest<SWTRegRequest>{
  public SWTRegRequestTest() {
    super(SWTProtocol.OP_REG, SWTProtocol.TYPE_REQ);
  }

  @Test
  public void request() throws Throwable {
    SWTRegRequest msg = this.factory.createRegRequest(1, 4711, true);

    ChannelBuffer buffer = encodeMessage(msg, 9);
    assertThat(buffer.readInt(), is(1));
    assertThat(buffer.readInt(), is(4711));
    assertThat(SWTProtocol.readBoolean(buffer), is(true));
  }

}
