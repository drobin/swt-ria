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

import de.robind.swt.msg.SWTNewRequest;
import de.robind.swt.protocol.SWTDecoderException;
import de.robind.swt.protocol.SWTMessageDecoder;
import de.robind.swt.protocol.SWTProtocol;

public class SWTNewRequestTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  private SWTMessageDecoder decoder = null;
  DecoderEmbedder<SWTNewRequest> embedder = null;

  @Before
  public void setup() {
    this.decoder = new SWTMessageDecoder();
    this.embedder = new DecoderEmbedder<SWTNewRequest>(this.decoder);
  }

  @After
  public void teardown() {
    this.embedder = null;
    this.decoder = null;
  }

  @Test
  public void emptyObjClass() throws Exception {
    ChannelBuffer buffer = createBuffer(7);
    buffer.writeInt(4711);
    SWTProtocol.writeString(buffer, "");
    buffer.writeByte(0);

    exception.expect(causeClass(SWTDecoderException.class));
    exception.expect(causeMsg("Invalid objClass: "));

    this.embedder.offer(buffer);
    this.embedder.poll();
  }

  @Test
  public void unknownObjClass() throws Exception {
    ChannelBuffer buffer = createBuffer(10);
    buffer.writeInt(4711);
    SWTProtocol.writeString(buffer, "foo");
    buffer.writeByte(0);

    exception.expect(causeClass(SWTDecoderException.class));
    exception.expect(causeMsg("Invalid objClass: foo"));

    this.embedder.offer(buffer);
    this.embedder.poll();
  }

  @Test
  public void invalidNumArguments() throws Exception {
    ChannelBuffer buffer = createBuffer(23);
    buffer.writeInt(4711);
    SWTProtocol.writeString(buffer, "java.lang.Object");
    buffer.writeByte(-1);

    exception.expect(causeClass(SWTDecoderException.class));
    exception.expect(causeMsg("Invalid number of arguments: -1"));

    this.embedder.offer(buffer);
    this.embedder.poll();
  }

  @Test
  public void noArguments() throws Exception {
    ChannelBuffer buffer = createBuffer(23);
    buffer.writeInt(4711);
    SWTProtocol.writeString(buffer, "java.lang.Object");
    buffer.writeByte(0);

    buffer.writeByte(0);

    this.embedder.offer(buffer);
    SWTNewRequest msg = this.embedder.poll();

    assertThat(msg, is(notNullValue()));
    assertThat(msg.getId(), is(4711));
    assertThat(msg.getObjClass().getName(), is(equalTo("java.lang.Object")));
    assertThat(msg.getArguments().length, is(0));
  }

  @Test
  public void withArguments() throws Exception {
    ChannelBuffer buffer = createBuffer(30);
    buffer.writeInt(4711);
    SWTProtocol.writeString(buffer, "java.lang.Object");
    buffer.writeByte(2);
    SWTProtocol.writeArgument(buffer, 4711);
    SWTProtocol.writeArgument(buffer, true);

    this.embedder.offer(buffer);
    SWTNewRequest msg = this.embedder.poll();

    assertThat(msg, is(notNullValue()));
    assertThat(msg.getId(), is(4711));
    assertThat(msg.getObjClass().getName(), is(equalTo("java.lang.Object")));
    assertThat(msg.getArguments().length, is(2));
    assertThat((Integer)msg.getArguments()[0], is(4711));
    assertThat((Boolean)msg.getArguments()[1], is(true));
  }

  @Test
  public void payloadNotEmptied() throws Exception {
    ChannelBuffer buffer = createBuffer(5);
    buffer.writeInt(4711);
    SWTProtocol.writeString(buffer, "java.lang.Object");
    buffer.writeByte(0);

    exception.expect(causeClass(SWTDecoderException.class));
    exception.expect(causeMsg("Payload-overflow. Available: 5, consumed: 23"));

    this.embedder.offer(buffer);
    this.embedder.poll();
  }

  @Test
  public void payloadOverflow() throws Exception {
    ChannelBuffer buffer = createBuffer(24);
    buffer.writeInt(4711);
    SWTProtocol.writeString(buffer, "java.lang.Object");
    buffer.writeByte(0);

    buffer.writeByte(0);

    exception.expect(causeClass(SWTDecoderException.class));
    exception.expect(causeMsg("Data still in payload. Available: 24, consumed: 23"));

    this.embedder.offer(buffer);
    this.embedder.poll();
  }

  private ChannelBuffer createBuffer(int payloadLength) {
    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();

    buffer.writeShort(SWTProtocol.MAGIC);
    buffer.writeByte(SWTProtocol.OP_NEW);
    buffer.writeByte(SWTProtocol.TYPE_REQ);
    buffer.writeInt(payloadLength);

    return (buffer);
  }
}
