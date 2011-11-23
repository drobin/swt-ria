package de.robind.swt.protocol.tests.decoder;

import static de.robind.swt.protocol.decoder.CauseMatcher.causeClass;
import static de.robind.swt.protocol.decoder.CauseMatcher.causeMsg;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.handler.codec.embedder.DecoderEmbedder;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.robind.swt.msg.SWTNewResponse;
import de.robind.swt.protocol.SWTDecoderException;
import de.robind.swt.protocol.SWTMessageDecoder;
import de.robind.swt.protocol.SWTProtocol;

public class SWTNewResponseTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  private SWTMessageDecoder decoder = null;
  DecoderEmbedder<SWTNewResponse> embedder = null;

  @Before
  public void setup() {
    this.decoder = new SWTMessageDecoder();
    this.embedder = new DecoderEmbedder<SWTNewResponse>(this.decoder);
  }

  @After
  public void teardown() {
    this.embedder = null;
    this.decoder = null;
  }

  @Test
  public void emptyClassName() throws Exception {
    ChannelBuffer buffer = createBuffer(4);
    SWTProtocol.writeString(buffer, "");
    SWTProtocol.writeString(buffer, "");

    exception.expect(causeClass(SWTDecoderException.class));
    exception.expect(causeMsg("Class-name cannot be empty"));

    this.embedder.offer(buffer);
    this.embedder.poll();
  }

  @Test
  public void success() {
    ChannelBuffer buffer = createBuffer(0);

    this.embedder.offer(buffer);
    SWTNewResponse msg = this.embedder.poll();

    assertThat(msg, is(notNullValue()));
    assertThat(msg.isSuccessful(), is(true));
  }

  @Test
  public void withErrorIncluded() throws Exception {
    ChannelBuffer buffer = createBuffer(7);
    SWTProtocol.writeString(buffer, "foo");
    SWTProtocol.writeString(buffer, "");

    this.embedder.offer(buffer);
    SWTNewResponse msg = this.embedder.poll();

    assertThat(msg, is(notNullValue()));
    assertThat(msg.isSuccessful(), is(false));
    assertThat(msg.getExceptionClass(), is(equalTo("foo")));
    assertThat(msg.getExceptionMessage(), is(equalTo("")));
  }

  @Test
  public void withErrorAndMessageIncluded() throws Exception {
    ChannelBuffer buffer = createBuffer(10);
    SWTProtocol.writeString(buffer, "foo");
    SWTProtocol.writeString(buffer, "bar");

    this.embedder.offer(buffer);
    SWTNewResponse msg = this.embedder.poll();

    assertThat(msg, is(notNullValue()));
    assertThat(msg.isSuccessful(), is(false));
    assertThat(msg.getExceptionClass(), is(equalTo("foo")));
    assertThat(msg.getExceptionMessage(), is(equalTo("bar")));
  }

  @Test
  public void payloadNotEmptied() throws Exception {
    ChannelBuffer buffer = createBuffer(8);
    SWTProtocol.writeString(buffer, "foo");
    SWTProtocol.writeString(buffer, "");

    buffer.writeByte(0);

    exception.expect(causeClass(SWTDecoderException.class));
    exception.expect(causeMsg("Data still in payload. Available: 8, consumed: 7"));

    this.embedder.offer(buffer);
    this.embedder.poll();
  }

  @Test
  public void payloadOverflow() throws Exception {
    ChannelBuffer buffer = createBuffer(5);
    SWTProtocol.writeString(buffer, "foo");
    SWTProtocol.writeString(buffer, "");

    exception.expect(causeClass(SWTDecoderException.class));
    exception.expect(causeMsg("Payload-overflow. Available: 5, consumed: 7"));

    this.embedder.offer(buffer);
    this.embedder.poll();
  }

  private ChannelBuffer createBuffer(int payloadLength) {
    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();

    buffer.writeShort(SWTProtocol.MAGIC);
    buffer.writeByte(SWTProtocol.OP_NEW);
    buffer.writeByte(SWTProtocol.TYPE_RSP);
    buffer.writeInt(payloadLength);

    return (buffer);
  }
}
