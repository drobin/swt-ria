package de.robind.swt.protocol.tests.decoder;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.handler.codec.embedder.CodecEmbedderException;
import org.jboss.netty.handler.codec.embedder.DecoderEmbedder;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import de.robind.swt.msg.SWTMessage;
import de.robind.swt.msg.SWTMessageFactory;
import de.robind.swt.protocol.SWTMessageDecoder;
import de.robind.swt.protocol.SWTProtocol;

public class AbstractDecoderTest<T extends SWTMessage> {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  private byte operation;
  private byte type;
  private SWTMessageDecoder decoder = null;
  private DecoderEmbedder<T> embedder = null;

  @Before
  public void setup() {
    this.decoder = new SWTMessageDecoder(new SWTMessageFactory());
    this.embedder = new DecoderEmbedder<T>(this.decoder);
  }

  @After
  public void teardown() {
    this.embedder = null;
    this.decoder = null;
  }

  protected AbstractDecoderTest(byte operation, byte type) {
    this.operation = operation;
    this.type = type;
  }

  protected T decodeMessage(ChannelBuffer buffer) throws Throwable {
    try {
      assertThat(this.embedder.offer(buffer), is(true));
      return (this.embedder.poll());
    } catch (CodecEmbedderException e) {
      throw e.getCause();
    }
  }

  protected ChannelBuffer createBuffer(int payloadLength) {
    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();

    buffer.writeShort(SWTProtocol.MAGIC);
    buffer.writeByte(this.operation);
    buffer.writeByte(this.type);
    buffer.writeInt(payloadLength);

    return (buffer);
  }
}
