package de.robind.swt.protocol.tests.encoder;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.jboss.netty.buffer.ChannelBuffer;
import org.junit.Test;

import de.robind.swt.msg.SWTCallRequest;
import de.robind.swt.msg.SWTObjectId;
import de.robind.swt.protocol.SWTProtocol;

public class SWTCallRequestTest extends AbstractEncoderTest<SWTCallRequest> {
  public SWTCallRequestTest() {
    super(SWTProtocol.OP_CALL, SWTProtocol.TYPE_REQ);
  }

  @Test
  public void noArguments() throws Exception {
    SWTCallRequest msg =
        this.factory.createCallRequest(new SWTObjectId(4711), "foo");

    ChannelBuffer buffer = encodeMessage(msg, 10);
    assertThat(buffer.readInt(), is(4711));
    assertThat(SWTProtocol.readString(buffer), is(equalTo("foo")));
    assertThat(buffer.readByte(), is((byte)0));
  }

  @Test
  public void withArguments() throws Exception {
    SWTCallRequest msg =
        this.factory.createCallRequest(new SWTObjectId(4711), "foo", 1, true);

    ChannelBuffer buffer = encodeMessage(msg, 17);
    assertThat(buffer.readInt(), is(4711));
    assertThat(SWTProtocol.readString(buffer), is(equalTo("foo")));
    assertThat(buffer.readByte(), is((byte)2));
    assertThat((Integer)SWTProtocol.readArgument(buffer), is(1));
    assertThat((Boolean)SWTProtocol.readArgument(buffer), is(true));
  }
}
