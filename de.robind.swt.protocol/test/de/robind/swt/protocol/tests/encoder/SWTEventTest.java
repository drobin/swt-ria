package de.robind.swt.protocol.tests.encoder;

import static de.robind.swt.protocol.tests.CauseMatcher.causeClass;
import static de.robind.swt.protocol.tests.CauseMatcher.causeMsg;
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
  public void tooManyAttributes() {
    Map<String, Object> attributes = new HashMap<String, Object>();
    for (int i = 0; i < Byte.MAX_VALUE + 1; i++) {
      attributes.put(Integer.toBinaryString(i), i);
    }

    exception.expect(causeClass(SWTProtocolException.class));
    exception.expect(causeMsg("Number of attributes cannot be greater than 127"));

    SWTEvent msg = this.factory.createEvent(attributes);
    encodeMessage(msg, 0);
  }

  @Test
  public void noAttributes() throws Exception {
    SWTEvent msg = this.factory.createEvent(new HashMap<String, Object>());
    ChannelBuffer buffer = encodeMessage(msg, 1);

    assertThat(buffer.readByte(), is((byte)0));
  }

  @Test
  public void withAttributes() throws Exception {
    Map<String, Object> attributes = new HashMap<String, Object>();
    attributes.put("foo", 4711);
    attributes.put("bar", "xxx");

    SWTEvent msg = this.factory.createEvent(attributes);
    ChannelBuffer buffer = encodeMessage(msg, 22);

    assertThat(buffer.readByte(), is((byte)2));
    assertThat(SWTProtocol.readString(buffer), is(equalTo("foo")));
    assertThat((Integer)SWTProtocol.readArgument(buffer), is(4711));
    assertThat(SWTProtocol.readString(buffer), is(equalTo("bar")));
    assertThat((String)SWTProtocol.readArgument(buffer), is(equalTo("xxx")));
  }
}
