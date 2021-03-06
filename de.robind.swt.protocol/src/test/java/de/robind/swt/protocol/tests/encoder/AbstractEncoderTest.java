package de.robind.swt.protocol.tests.encoder;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.handler.codec.embedder.CodecEmbedderException;
import org.jboss.netty.handler.codec.embedder.EncoderEmbedder;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import de.robind.swt.msg.SWTMessage;
import de.robind.swt.msg.SWTMessageFactory;
import de.robind.swt.protocol.SWTMessageEncoder;
import de.robind.swt.protocol.SWTProtocol;

public class AbstractEncoderTest<T extends SWTMessage> {
  private byte operation;
  private byte type;

  @Rule
  public ExpectedException exception = ExpectedException.none();

  protected SWTMessageFactory factory = null;
  private SWTMessageEncoder encoder = null;
  private EncoderEmbedder<ChannelBuffer> embedder = null;

  @Before
  public void setup() {
    this.factory = new SWTMessageFactory();
    this.encoder = new SWTMessageEncoder();
    this.embedder = new EncoderEmbedder<ChannelBuffer>(this.encoder);
  }

  @After
  public void teardown() {
    this.factory = null;
    this.embedder = null;
    this.encoder = null;
  }

  protected AbstractEncoderTest(byte operation, byte type) {
    this.operation = operation;
    this.type = type;
  }

  protected ChannelBuffer encodeMessage(T msg, int payloadLength)
      throws Throwable {

    try {
      this.embedder.offer(msg);
      ChannelBuffer buffer = this.embedder.poll();

      assertThat(buffer.readShort(), is(SWTProtocol.MAGIC));
      assertThat(buffer.readByte(), is(this.operation));
      assertThat(buffer.readByte(), is(this.type));
      assertThat(buffer.readInt(), is(payloadLength));

      return (buffer);
    } catch (CodecEmbedderException e) {
      throw e.getCause();
    }
  }
}
