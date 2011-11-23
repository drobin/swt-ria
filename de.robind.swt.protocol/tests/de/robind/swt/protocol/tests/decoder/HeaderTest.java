package de.robind.swt.protocol.tests.decoder;

import static de.robind.swt.protocol.tests.CauseMatcher.causeClass;
import static de.robind.swt.protocol.tests.CauseMatcher.causeMsg;
import static org.hamcrest.CoreMatchers.nullValue;
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

import de.robind.swt.protocol.SWTDecoderException;
import de.robind.swt.protocol.SWTMessageDecoder;
import de.robind.swt.protocol.SWTProtocol;

public class HeaderTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  private SWTMessageDecoder decoder = null;
  DecoderEmbedder<Object> embedder = null;

  @Before
  public void setup() {
    this.decoder = new SWTMessageDecoder();
    this.embedder = new DecoderEmbedder<Object>(this.decoder);
  }

  @After
  public void teardown() {
    this.embedder = null;
    this.decoder = null;
  }

  @Test
  public void incompleteHeader() {
    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
    buffer.writeShort(SWTProtocol.MAGIC);

    this.embedder.offer(buffer);
    assertThat(this.embedder.poll(), is(nullValue()));
  }

  @Test
  public void incompletePayload() {
    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
    buffer.writeShort(SWTProtocol.MAGIC);
    buffer.writeByte(SWTProtocol.OP_NEW);
    buffer.writeByte(SWTProtocol.TYPE_REQ);
    buffer.writeInt(42);

    this.embedder.offer(buffer);
    assertThat(this.embedder.poll(), is(nullValue()));
  }

  @Test
  public void invalidMagic() {
    exception.expect(causeClass(SWTDecoderException.class));
    exception.expect(causeMsg("Invalid magic number: 4711"));

    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
    buffer.writeShort(4711);
    buffer.writeByte(SWTProtocol.OP_NEW);
    buffer.writeByte(SWTProtocol.TYPE_REQ);
    buffer.writeInt(0);

    this.embedder.offer(buffer);
    this.embedder.poll();
  }

  @Test
  public void invalidOperation() {
    exception.expect(causeClass(SWTDecoderException.class));
    exception.expect(causeMsg("Invalid operation: 42"));

    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
    buffer.writeShort(SWTProtocol.MAGIC);
    buffer.writeByte(42);
    buffer.writeByte(SWTProtocol.TYPE_REQ);
    buffer.writeInt(0);

    this.embedder.offer(buffer);
    this.embedder.poll();
  }

  @Test
  public void invalidType() {
    exception.expect(causeClass(SWTDecoderException.class));
    exception.expect(causeMsg("Invalid message-type: 42"));

    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
    buffer.writeShort(SWTProtocol.MAGIC);
    buffer.writeByte(SWTProtocol.OP_NEW);
    buffer.writeByte(42);
    buffer.writeInt(0);

    this.embedder.offer(buffer);
    this.embedder.poll();
  }

  @Test
  public void invalidPayloadLength() {
    exception.expect(causeClass(SWTDecoderException.class));
    exception.expect(causeMsg("Invalid payload-length: -1"));

    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
    buffer.writeShort(SWTProtocol.MAGIC);
    buffer.writeByte(SWTProtocol.OP_NEW);
    buffer.writeByte(SWTProtocol.TYPE_REQ);
    buffer.writeInt(-1);

    this.embedder.offer(buffer);
    this.embedder.poll();
  }
}
