package de.robind.swt.protocol.tests.encoder;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.handler.codec.embedder.EncoderEmbedder;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.robind.swt.msg.SWTCallRequest;
import de.robind.swt.msg.SWTObjectId;
import de.robind.swt.protocol.SWTMessageEncoder;
import de.robind.swt.protocol.SWTProtocol;

public class SWTCallRequestTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  private SWTMessageEncoder encoder = null;
  EncoderEmbedder<ChannelBuffer> embedder = null;

  @Before
  public void setup() {
    this.encoder = new SWTMessageEncoder();
    this.embedder = new EncoderEmbedder<ChannelBuffer>(this.encoder);
  }

  @After
  public void teardown() {
    this.embedder = null;
    this.encoder = null;
  }

  @Test
  public void noArguments() throws Exception {
    SWTCallRequest msg = new SWTCallRequest(new SWTObjectId(4711), "foo");

    ChannelBuffer buffer = encodeMessage(msg, 10);
    assertThat(buffer.readInt(), is(4711));
    assertThat(SWTProtocol.readString(buffer), is(equalTo("foo")));
    assertThat(buffer.readByte(), is((byte)0));
  }

  @Test
  public void withArguments() throws Exception {
    SWTCallRequest msg = new SWTCallRequest(new SWTObjectId(4711), "foo", 1, true);

    ChannelBuffer buffer = encodeMessage(msg, 17);
    assertThat(buffer.readInt(), is(4711));
    assertThat(SWTProtocol.readString(buffer), is(equalTo("foo")));
    assertThat(buffer.readByte(), is((byte)2));
    assertThat((Integer)SWTProtocol.readArgument(buffer), is(1));
    assertThat((Boolean)SWTProtocol.readArgument(buffer), is(true));
  }

  private ChannelBuffer encodeMessage(SWTCallRequest msg, int payloadLength) {
    this.embedder.offer(msg);
    ChannelBuffer buffer = this.embedder.poll();

    assertThat(buffer.readShort(), is(SWTProtocol.MAGIC));
    assertThat(buffer.readByte(), is(SWTProtocol.OP_CALL));
    assertThat(buffer.readByte(), is(SWTProtocol.TYPE_REQ));
    assertThat(buffer.readInt(), is(payloadLength));

    return (buffer);
  }
}
