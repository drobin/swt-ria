package de.robind.swt.protocol.tests.decoder;

import static de.robind.swt.protocol.tests.CauseMatcher.causeClass;
import static de.robind.swt.protocol.tests.CauseMatcher.causeMsg;
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

import de.robind.swt.msg.SWTRegRequest;
import de.robind.swt.protocol.SWTDecoderException;
import de.robind.swt.protocol.SWTMessageDecoder;
import de.robind.swt.protocol.SWTProtocol;

public class SWTRegRequestTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  private SWTMessageDecoder decoder = null;
  DecoderEmbedder<SWTRegRequest> embedder = null;

  @Before
  public void setup() {
    this.decoder = new SWTMessageDecoder();
    this.embedder = new DecoderEmbedder<SWTRegRequest>(this.decoder);
  }

  @After
  public void teardown() {
    this.embedder = null;
    this.decoder = null;
  }

  @Test
  public void success() {
    ChannelBuffer buffer = createBuffer(9);
    buffer.writeInt(4711);
    buffer.writeInt(555);
    SWTProtocol.writeBoolean(buffer, true);

    this.embedder.offer(buffer);
    SWTRegRequest msg = this.embedder.poll();

    assertThat(msg, is(notNullValue()));
    assertThat(msg.getDestinationObject().getId(), is(4711));
    assertThat(msg.getEventType(), is(555));
    assertThat(msg.enable(), is(true));
  }

  @Test
  public void payloadNotEmptied() throws Exception {
    ChannelBuffer buffer = createBuffer(10);
    buffer.writeInt(4711);
    buffer.writeInt(555);
    SWTProtocol.writeBoolean(buffer, true);

    buffer.writeByte(0);

    exception.expect(causeClass(SWTDecoderException.class));
    exception.expect(causeMsg("Data still in payload. Available: 10, consumed: 9"));

    this.embedder.offer(buffer);
    this.embedder.poll();
  }

  @Test
  public void payloadOverflow() throws Exception {
    ChannelBuffer buffer = createBuffer(5);
    buffer.writeInt(4711);
    buffer.writeInt(555);
    SWTProtocol.writeBoolean(buffer, true);

    exception.expect(causeClass(SWTDecoderException.class));
    exception.expect(causeMsg("Payload-overflow. Available: 5, consumed: 9"));

    this.embedder.offer(buffer);
    this.embedder.poll();
  }

  private ChannelBuffer createBuffer(int payloadLength) {
    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();

    buffer.writeShort(SWTProtocol.MAGIC);
    buffer.writeByte(SWTProtocol.OP_REG);
    buffer.writeByte(SWTProtocol.TYPE_REQ);
    buffer.writeInt(payloadLength);

    return (buffer);
  }
}
