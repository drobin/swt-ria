package de.robind.swt.protocol.tests.encoder;

import static de.robind.swt.protocol.datatype.SWTBoolean.readBoolean;
import static de.robind.swt.protocol.datatype.SWTByte.readByte;
import static de.robind.swt.protocol.datatype.SWTInteger.readInteger;
import static de.robind.swt.protocol.datatype.SWTString.readString;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.jboss.netty.buffer.ChannelBuffer;
import org.junit.Test;

import de.robind.swt.msg.SWTCallRequest;
import de.robind.swt.protocol.SWTProtocol;

public class SWTCallRequestTest extends AbstractEncoderTest<SWTCallRequest> {
  public SWTCallRequestTest() {
    super(SWTProtocol.OP_CALL, SWTProtocol.TYPE_REQ);
  }

  @Test
  public void noArguments() throws Throwable {
    SWTCallRequest msg = this.factory.createCallRequest(4711, "foo");

    ChannelBuffer buffer = encodeMessage(msg, 16);
    assertThat(readInteger(buffer), is(4711));
    assertThat(readString(buffer), is(equalTo("foo")));
    assertThat(readByte(buffer), is((byte)0));
  }

  @Test
  public void withArguments() throws Throwable {
    SWTCallRequest msg =
        this.factory.createCallRequest(4711, "foo", 1, true);

    ChannelBuffer buffer = encodeMessage(msg, 25);
    assertThat(readInteger(buffer), is(4711));
    assertThat(readString(buffer), is(equalTo("foo")));
    assertThat(readByte(buffer), is((byte)2));
    assertThat(readInteger(buffer), is(1));
    assertThat(readBoolean(buffer), is(true));
  }
}
