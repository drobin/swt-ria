package de.robind.swt.protocol.tests.encoder;

import org.junit.Test;

import de.robind.swt.msg.SWTAttrResponse;
import de.robind.swt.protocol.SWTProtocol;

public class SWTAttrResponseTest extends AbstractEncoderTest<SWTAttrResponse> {
  public SWTAttrResponseTest() {
    super(SWTProtocol.OP_ATTR, SWTProtocol.TYPE_RSP);
  }

  @Test
  public void success() throws Throwable {
    SWTAttrResponse msg = this.factory.createAttrResponse();
    encodeMessage(msg, 0);
  }
}
