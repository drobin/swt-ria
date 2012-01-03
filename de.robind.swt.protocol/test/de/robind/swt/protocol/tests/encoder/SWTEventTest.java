package de.robind.swt.protocol.tests.encoder;

import static de.robind.swt.protocol.datatype.SWTByte.readByte;
import static de.robind.swt.protocol.datatype.SWTInteger.readInteger;
import static de.robind.swt.protocol.datatype.SWTString.readString;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.jboss.netty.buffer.ChannelBuffer;
import org.junit.Test;

import de.robind.swt.msg.SWTEvent;
import de.robind.swt.protocol.SWTProtocol;
import de.robind.swt.protocol.SWTProtocolException;

public class SWTEventTest extends AbstractEncoderTest<SWTEvent> {
  public SWTEventTest() {
    super(SWTProtocol.OP_CALL, SWTProtocol.TYPE_EVT);
  }

  @Test
  public void tooManyAttributes() throws Throwable {
    Map<String, Object> attributes = new HashMap<String, Object>();
    for (int i = 0; i < Byte.MAX_VALUE + 1; i++) {
      attributes.put(Integer.toBinaryString(i), i);
    }

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Number of attributes cannot be greater than 127");

    SWTEvent msg = this.factory.createEvent(0, attributes);
    encodeMessage(msg, 0);
  }

  @Test
  public void noAttributes() throws Throwable {
    SWTEvent msg = this.factory.createEvent(4711, new HashMap<String, Object>());
    ChannelBuffer buffer = encodeMessage(msg, 9);

    assertThat(readInteger(buffer), is(4711));
    assertThat(readByte(buffer), is((byte)0));
  }

  @Test
  public void withAttributes() throws Throwable {
    Map<String, Object> attributes = new HashMap<String, Object>();
    attributes.put("foo", 4711);
    attributes.put("bar", "xxx");

    SWTEvent msg = this.factory.createEvent(4711, attributes);
    ChannelBuffer buffer = encodeMessage(msg, 36);

    assertThat(readInteger(buffer), is(4711));
    assertThat(readByte(buffer), is((byte)2));
    assertThat(readString(buffer), is(equalTo("foo")));
    assertThat(readInteger(buffer), is(4711));
    assertThat(readString(buffer), is(equalTo("bar")));
    assertThat(readString(buffer), is(equalTo("xxx")));
  }
}
