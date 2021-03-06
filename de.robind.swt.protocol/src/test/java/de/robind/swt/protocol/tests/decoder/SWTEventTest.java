package de.robind.swt.protocol.tests.decoder;

import static de.robind.swt.protocol.datatype.SWTByte.writeByte;
import static de.robind.swt.protocol.datatype.SWTInteger.writeInteger;
import static de.robind.swt.protocol.datatype.SWTString.writeString;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.jboss.netty.buffer.ChannelBuffer;
import org.junit.Test;

import de.robind.swt.msg.SWTEvent;
import de.robind.swt.protocol.SWTProtocol;
import de.robind.swt.protocol.SWTProtocolException;

public class SWTEventTest extends AbstractDecoderTest<SWTEvent> {
  public SWTEventTest() {
    super(SWTProtocol.OP_CALL, SWTProtocol.TYPE_EVT);
  }

  @Test
  public void negativeNumberOfArguments() throws Throwable {
    ChannelBuffer buffer = createBuffer(5);
    writeInteger(buffer, 4711);
    writeByte(buffer, (byte)-1);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Number of attributes cannot be negative");

    decodeMessage(buffer);
  }

  @Test
  public void noArguments() throws Throwable {
    ChannelBuffer buffer = createBuffer(9);
    writeInteger(buffer, 4711);
    writeByte(buffer, (byte)0);

    SWTEvent msg = decodeMessage(buffer);
    assertThat(msg.getObjId(), is(4711));
    assertThat(msg.getAttributes().length, is(0));
  }

  @Test
  public void withArguments() throws Throwable {
    ChannelBuffer buffer = createBuffer(40);
    writeInteger(buffer, 4711);
    writeByte(buffer, (byte)2);
    writeString(buffer, "foo");
    writeInteger(buffer, 4711);
    writeString(buffer, "bar");
    writeString(buffer, "blubber");

    SWTEvent msg = decodeMessage(buffer);
    assertThat(msg.getObjId(), is(4711));
    assertThat(msg.getAttributes().length, is(2));
    assertThat((Integer)msg.getAttributeValue("foo"), is(4711));
    assertThat((String)msg.getAttributeValue("bar"), is(equalTo("blubber")));
  }

  @Test
  public void payloadNotEmptied() throws Throwable {
    ChannelBuffer buffer = createBuffer(23);
    writeInteger(buffer, 4711);
    writeByte(buffer, (byte)1);
    writeString(buffer, "foo");
    writeInteger(buffer, 4711);

    buffer.writeByte(0);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Data still in payload. Available: 23, consumed: 22");

    decodeMessage(buffer);
  }

  @Test
  public void payloadOverflow() throws Throwable {
    ChannelBuffer buffer = createBuffer(5);
    writeInteger(buffer, 4711);
    writeByte(buffer, (byte)1);
    writeString(buffer, "foo");
    writeInteger(buffer, 4711);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Payload-overflow. Available: 5, consumed: 22");

    decodeMessage(buffer);
  }
}
