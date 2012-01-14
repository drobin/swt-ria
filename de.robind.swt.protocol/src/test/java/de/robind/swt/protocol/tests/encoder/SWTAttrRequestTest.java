package de.robind.swt.protocol.tests.encoder;

import static de.robind.swt.protocol.datatype.SWTBoolean.readBoolean;
import static de.robind.swt.protocol.datatype.SWTInteger.readInteger;
import static de.robind.swt.protocol.datatype.SWTString.readString;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.jboss.netty.buffer.ChannelBuffer;
import org.junit.Test;

import de.robind.swt.msg.SWTAttrRequest;
import de.robind.swt.protocol.SWTProtocol;

public class SWTAttrRequestTest extends AbstractEncoderTest<SWTAttrRequest> {
  public SWTAttrRequestTest() {
    super(SWTProtocol.OP_ATTR, SWTProtocol.TYPE_REQ);
  }

  @Test
  public void request() throws Throwable {
    SWTAttrRequest msg = this.factory.createAttrRequest(4711, "xxx", true);

    ChannelBuffer buffer = encodeMessage(msg, 16);
    assertThat(readInteger(buffer), is(4711));
    assertThat(readString(buffer), is(equalTo("xxx")));
    assertThat(readBoolean(buffer), is(true));
  }
}
