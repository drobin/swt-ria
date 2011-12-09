package de.robind.swt.protocol.tests.encoder;

import org.junit.Test;

import de.robind.swt.msg.SWTRegResponse;
import de.robind.swt.protocol.SWTProtocol;

public class SWTRegResponseTest extends AbstractEncoderTest<SWTRegResponse> {
  public SWTRegResponseTest() {
    super(SWTProtocol.OP_REG, SWTProtocol.TYPE_RSP);
  }

  @Test
  public void success() throws Throwable {
    SWTRegResponse msg = this.factory.createRegResponse();
    encodeMessage(msg, 0);
  }
}
