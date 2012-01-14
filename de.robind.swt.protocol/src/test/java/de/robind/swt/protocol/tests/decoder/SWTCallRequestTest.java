package de.robind.swt.protocol.tests.decoder;

import static de.robind.swt.protocol.datatype.SWTBoolean.writeBoolean;
import static de.robind.swt.protocol.datatype.SWTByte.writeByte;
import static de.robind.swt.protocol.datatype.SWTInteger.writeInteger;
import static de.robind.swt.protocol.datatype.SWTString.writeString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.jboss.netty.buffer.ChannelBuffer;
import org.junit.Test;

import de.robind.swt.msg.SWTCallRequest;
import de.robind.swt.protocol.SWTProtocol;
import de.robind.swt.protocol.SWTProtocolException;

public class SWTCallRequestTest extends AbstractDecoderTest<SWTCallRequest> {
  public SWTCallRequestTest() {
    super(SWTProtocol.OP_CALL, SWTProtocol.TYPE_REQ);
  }

  @Test
  public void emptyMethod() throws Throwable {
    ChannelBuffer buffer = createBuffer(0);
    writeInteger(buffer, 4711);
    writeString(buffer, "");
    writeByte(buffer, (byte)0);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("method cannot be empty");

    decodeMessage(buffer);
  }

  @Test
  public void invalidNumArguments() throws Throwable {
    ChannelBuffer buffer = createBuffer(0);
    writeInteger(buffer, 4711);
    writeString(buffer, "foo");
    writeByte(buffer, (byte)-1);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Invalid number of arguments: -1");

    decodeMessage(buffer);
  }

  @Test
  public void noArguments() throws Throwable {
    ChannelBuffer buffer = createBuffer(16);
    writeInteger(buffer, 4711);
    writeString(buffer, "foo");
    writeByte(buffer, (byte)0);

    SWTCallRequest msg = decodeMessage(buffer);

    assertThat(msg, is(notNullValue()));
    assertThat(msg.getObjId(), is(4711));
    assertThat(msg.getMethod(), is(equalTo("foo")));
    assertThat(msg.getArguments().length, is(0));
  }

  @Test
  public void withArguments() throws Throwable {
    ChannelBuffer buffer = createBuffer(25);
    writeInteger(buffer, 4711);
    writeString(buffer, "foo");
    writeByte(buffer, (byte)2);
    writeInteger(buffer, 4711);
    writeBoolean(buffer, false);

    SWTCallRequest msg = decodeMessage(buffer);

    assertThat(msg, is(notNullValue()));
    assertThat(msg.getObjId(), is(4711));
    assertThat(msg.getMethod(), is(equalTo("foo")));
    assertThat(msg.getArguments().length, is(2));
    assertThat((Integer)msg.getArguments()[0], is(4711));
    assertThat((Boolean)msg.getArguments()[1], is(false));
  }

  @Test
  public void payloadNotEmptied() throws Throwable {
    ChannelBuffer buffer = createBuffer(17);
    writeInteger(buffer, 4711);
    writeString(buffer, "foo");
    writeByte(buffer, (byte)0);

    buffer.writeByte(0);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Data still in payload. Available: 17, consumed: 16");

    decodeMessage(buffer);
  }

  @Test
  public void payloadOverflow() throws Throwable {
    ChannelBuffer buffer = createBuffer(5);
    writeInteger(buffer, 4711);
    writeString(buffer, "foo");
    writeByte(buffer, (byte)0);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Payload-overflow. Available: 5, consumed: 16");

    decodeMessage(buffer);
  }
}
