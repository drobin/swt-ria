package de.robind.swt.protocol.tests.decoder;

import static de.robind.swt.protocol.decoder.CauseMatcher.causeClass;
import static de.robind.swt.protocol.decoder.CauseMatcher.causeMsg;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.handler.codec.embedder.DecoderEmbedder;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.robind.swt.msg.SWTCallResponse;
import de.robind.swt.protocol.SWTDecoderException;
import de.robind.swt.protocol.SWTMessageDecoder;
import de.robind.swt.protocol.SWTProtocol;

public class SWTCallResponseTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  private SWTMessageDecoder decoder = null;
  DecoderEmbedder<SWTCallResponse> embedder = null;

  @Before
  public void setup() {
    this.decoder = new SWTMessageDecoder();
    this.embedder = new DecoderEmbedder<SWTCallResponse>(this.decoder);
  }

  @After
  public void teardown() {
    this.embedder = null;
    this.decoder = null;
  }

  @Test
  public void voidResult() {
    ChannelBuffer buffer = createBuffer(0);

    this.embedder.offer(buffer);
    SWTCallResponse msg = this.embedder.poll();

    assertThat(msg, is(notNullValue()));
    assertThat(msg.isVoid(), is(true));
  }

  @Test
  public void nonVoidResult() throws Exception {
    ChannelBuffer buffer = createBuffer(5);
    SWTProtocol.writeArgument(buffer, 4711);

    this.embedder.offer(buffer);
    SWTCallResponse msg = this.embedder.poll();

    assertThat(msg, is(notNullValue()));
    assertThat(msg.isVoid(), is(false));
    assertThat((Integer)msg.getResult(), is(4711));
  }

  @Test
  public void payloadNotEmptied() throws Exception {
    ChannelBuffer buffer = createBuffer(6);
    SWTProtocol.writeArgument(buffer, 4711);

    buffer.writeByte(0);

    exception.expect(causeClass(SWTDecoderException.class));
    exception.expect(causeMsg("Data still in payload. Available: 6, consumed: 5"));

    this.embedder.offer(buffer);
    this.embedder.poll();
  }

  @Test
  public void payloadOverflow() throws Exception {
    ChannelBuffer buffer = createBuffer(3);
    SWTProtocol.writeArgument(buffer, 4711);

    exception.expect(causeClass(SWTDecoderException.class));
    exception.expect(causeMsg("Payload-overflow. Available: 3, consumed: 5"));

    this.embedder.offer(buffer);
    this.embedder.poll();
  }

  private ChannelBuffer createBuffer(int payloadLength) {
    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();

    buffer.writeShort(SWTProtocol.MAGIC);
    buffer.writeByte(SWTProtocol.OP_CALL);
    buffer.writeByte(SWTProtocol.TYPE_RSP);
    buffer.writeInt(payloadLength);

    return (buffer);
  }
}
