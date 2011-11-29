package de.robind.swt.protocol.tests.decoder;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.jboss.netty.buffer.ChannelBuffer;
import org.junit.Test;

import de.robind.swt.msg.SWTNewRequest;
import de.robind.swt.protocol.SWTProtocol;
import de.robind.swt.protocol.SWTProtocolException;

public class SWTNewRequestTest extends AbstractDecoderTest<SWTNewRequest> {
  public SWTNewRequestTest() {
    super(SWTProtocol.OP_NEW, SWTProtocol.TYPE_REQ);
  }

  @Test
  public void emptyObjClass() throws Throwable {
    ChannelBuffer buffer = createBuffer(7);
    buffer.writeInt(4711);
    SWTProtocol.writeString(buffer, "");
    buffer.writeByte(0);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Invalid objClass: ");

    decodeMessage(buffer);
  }

  @Test
  public void unknownObjClass() throws Throwable {
    ChannelBuffer buffer = createBuffer(10);
    buffer.writeInt(4711);
    SWTProtocol.writeString(buffer, "foo");
    buffer.writeByte(0);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Invalid objClass: foo");

    decodeMessage(buffer);
  }

  @Test
  public void invalidNumArguments() throws Throwable {
    ChannelBuffer buffer = createBuffer(23);
    buffer.writeInt(4711);
    SWTProtocol.writeString(buffer, "java.lang.Object");
    buffer.writeByte(-1);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Invalid number of arguments: -1");

    decodeMessage(buffer);
  }

  @Test
  public void noArguments() throws Throwable {
    ChannelBuffer buffer = createBuffer(23);
    buffer.writeInt(4711);
    SWTProtocol.writeString(buffer, "java.lang.Object");
    buffer.writeByte(0);

    buffer.writeByte(0);

    SWTNewRequest msg = decodeMessage(buffer);

    assertThat(msg, is(notNullValue()));
    assertThat(msg.getId(), is(4711));
    assertThat(msg.getObjClass().getName(), is(equalTo("java.lang.Object")));
    assertThat(msg.getArguments().length, is(0));
  }

  @Test
  public void withArguments() throws Throwable {
    ChannelBuffer buffer = createBuffer(30);
    buffer.writeInt(4711);
    SWTProtocol.writeString(buffer, "java.lang.Object");
    buffer.writeByte(2);
    SWTProtocol.writeArgument(buffer, 4711);
    SWTProtocol.writeArgument(buffer, true);

    SWTNewRequest msg = decodeMessage(buffer);

    assertThat(msg, is(notNullValue()));
    assertThat(msg.getId(), is(4711));
    assertThat(msg.getObjClass().getName(), is(equalTo("java.lang.Object")));
    assertThat(msg.getArguments().length, is(2));
    assertThat((Integer)msg.getArguments()[0], is(4711));
    assertThat((Boolean)msg.getArguments()[1], is(true));
  }

  @Test
  public void payloadNotEmptied() throws Throwable {
    ChannelBuffer buffer = createBuffer(5);
    buffer.writeInt(4711);
    SWTProtocol.writeString(buffer, "java.lang.Object");
    buffer.writeByte(0);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Payload-overflow. Available: 5, consumed: 23");

    decodeMessage(buffer);
  }

  @Test
  public void payloadOverflow() throws Throwable {
    ChannelBuffer buffer = createBuffer(24);
    buffer.writeInt(4711);
    SWTProtocol.writeString(buffer, "java.lang.Object");
    buffer.writeByte(0);

    buffer.writeByte(0);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Data still in payload. Available: 24, consumed: 23");

    decodeMessage(buffer);
  }
}
