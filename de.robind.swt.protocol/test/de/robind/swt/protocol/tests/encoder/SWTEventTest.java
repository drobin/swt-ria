package de.robind.swt.protocol.tests.encoder;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.jboss.netty.buffer.ChannelBuffer;
import org.junit.Test;

import de.robind.swt.msg.SWTEvent;
import de.robind.swt.protocol.SWTProtocol;

public class SWTEventTest extends AbstractEncoderTest<SWTEvent> {
  public SWTEventTest() {
    super(SWTProtocol.OP_CALL, SWTProtocol.TYPE_EVT);
  }

  @Test
  public void regularEvent() throws Exception {
    Map<String, Object> attributes = new HashMap<String, Object>();
    attributes.put("widget", 4711);
    SWTEvent msg = this.factory.createEvent(attributes);
    ChannelBuffer buffer = encodeMessage(msg, 4);

    assertThat(buffer.readInt(), is(4711));
  }
}
