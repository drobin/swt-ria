package de.robind.swt.protocol.tests.encoder;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.handler.codec.embedder.EncoderEmbedder;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.robind.swt.msg.SWTCallResponse;
import de.robind.swt.protocol.SWTMessageEncoder;
import de.robind.swt.protocol.SWTProtocol;

public class SWTCallResponseTest {
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
  public void voidResult() {
    SWTCallResponse msg = SWTCallResponse.voidResult();
    encodeMessage(msg, 0);
  }

  @Test
  public void withResult() throws Exception {
    SWTCallResponse msg = new SWTCallResponse(4711);

    ChannelBuffer buffer = encodeMessage(msg, 5);
    assertThat((Integer)SWTProtocol.readArgument(buffer), is(4711));
  }

  private ChannelBuffer encodeMessage(SWTCallResponse msg, int payloadLength) {
    this.embedder.offer(msg);
    ChannelBuffer buffer = this.embedder.poll();

    assertThat(buffer.readShort(), is(SWTProtocol.MAGIC));
    assertThat(buffer.readByte(), is(SWTProtocol.OP_CALL));
    assertThat(buffer.readByte(), is(SWTProtocol.TYPE_RSP));
    assertThat(buffer.readInt(), is(payloadLength));

    return (buffer);
  }
}
