package de.robind.swt.protocol.tests.encoder;

import static de.robind.swt.protocol.datatype.SWTBoolean.readBoolean;
import static de.robind.swt.protocol.datatype.SWTInteger.readInteger;
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

    ChannelBuffer buffer = encodeMessage(msg, 15);
    assertThat(readInteger(buffer), is(1));
    assertThat(readInteger(buffer), is(4711));
    assertThat(readBoolean(buffer), is(true));
  }
}
