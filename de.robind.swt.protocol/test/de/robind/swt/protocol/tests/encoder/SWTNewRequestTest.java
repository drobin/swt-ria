package de.robind.swt.protocol.tests.encoder;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.jboss.netty.buffer.ChannelBuffer;
import org.junit.Test;

import de.robind.swt.msg.SWTNewRequest;
import de.robind.swt.protocol.SWTProtocol;

public class SWTNewRequestTest extends AbstractEncoderTest<SWTNewRequest>{
  public SWTNewRequestTest() {
    super(SWTProtocol.OP_NEW, SWTProtocol.TYPE_REQ);
  }

  @Test
  public void noArguments() throws Throwable {
    SWTNewRequest msg = this.factory.createNewRequest(4711, Integer.class);

    ChannelBuffer buffer = encodeMessage(msg, 24);
    assertThat(buffer.readInt(), is(4711));
    assertThat(SWTProtocol.readString(buffer), is(IsEqual.equalTo("java.lang.Integer")));
    assertThat(buffer.readByte(), is((byte)0));
  }

  @Test
  public void withArguments() throws Throwable {
    SWTNewRequest msg = this.factory.createNewRequest(4711, Integer.class, 42, true);

    ChannelBuffer buffer = encodeMessage(msg, 31);
    assertThat(buffer.readInt(), is(4711));
    assertThat(SWTProtocol.readString(buffer), is(IsEqual.equalTo("java.lang.Integer")));
    assertThat(buffer.readByte(), is((byte)2));
    assertThat((Integer)SWTProtocol.readArgument(buffer), is(42));
    assertThat((Boolean)SWTProtocol.readArgument(buffer), is(true));
  }
}
