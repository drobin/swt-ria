package de.robind.swt.protocol.tests.decoder;

import static de.robind.swt.protocol.datatype.SWTInteger.writeInteger;
import static de.robind.swt.protocol.datatype.SWTString.writeString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.jboss.netty.buffer.ChannelBuffer;
import org.junit.Test;

import de.robind.swt.msg.SWTAttrRequest;
import de.robind.swt.protocol.SWTProtocol;
import de.robind.swt.protocol.SWTProtocolException;

public class SWTAttrRequestTest extends AbstractDecoderTest<SWTAttrRequest>{
  public SWTAttrRequestTest() {
    super(SWTProtocol.OP_ATTR, SWTProtocol.TYPE_REQ);
  }

  @Test
  public void invalidObjId() throws Throwable {
    ChannelBuffer buffer = createBuffer(1);
    writeString(buffer, "xxx");

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Unexpected type read from buffer. Expected 2, but read 1");

    decodeMessage(buffer);
  }

  @Test
  public void invalidAttrName() throws Throwable {
    ChannelBuffer buffer = createBuffer(1);
    writeInteger(buffer, 4711);
    writeInteger(buffer, 5);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Unexpected type read from buffer. Expected 1, but read 2");

    decodeMessage(buffer);
  }

  @Test
  public void emptyAttrName() throws Throwable {
    ChannelBuffer buffer = createBuffer(1);
    writeInteger(buffer, 4711);
    writeString(buffer, "");

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Name of attribute cannot be empty");

    decodeMessage(buffer);
  }

  @Test
  public void success() throws Throwable {
    ChannelBuffer buffer = createBuffer(24);
    writeInteger(buffer, 4711);
    writeString(buffer, "xxx");
    writeString(buffer, "blubber");

    SWTAttrRequest msg = decodeMessage(buffer);
    assertThat(msg, is(notNullValue()));
    assertThat(msg.getObjId(), is(4711));
    assertThat(msg.getAttrName(), is(equalTo("xxx")));
    assertThat((String)msg.getAttrValue(), is(equalTo("blubber")));
  }

  @Test
  public void payloadNotEmptied() throws Throwable {
    ChannelBuffer buffer = createBuffer(25);
    writeInteger(buffer, 4711);
    writeString(buffer, "xxx");
    writeString(buffer, "blubber");

    buffer.writeByte(0);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Data still in payload. Available: 25, consumed: 24");

    decodeMessage(buffer);
  }

  @Test
  public void payloadOverflow() throws Throwable {
    ChannelBuffer buffer = createBuffer(5);
    writeInteger(buffer, 4711);
    writeString(buffer, "xxx");
    writeString(buffer, "blubber");

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Payload-overflow. Available: 5, consumed: 24");

    decodeMessage(buffer);
  }
}
