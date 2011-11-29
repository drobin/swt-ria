package de.robind.swt.protocol.tests.encoder;

import org.junit.Test;

import de.robind.swt.msg.SWTNewResponse;
import de.robind.swt.protocol.SWTProtocol;

public class SWTNewResponseTest extends AbstractEncoderTest<SWTNewResponse>{
  public SWTNewResponseTest() {
    super(SWTProtocol.OP_NEW, SWTProtocol.TYPE_RSP);
  }

  @Test
  public void success() {
    SWTNewResponse msg = this.factory.createNewResponse();
    encodeMessage(msg, 0);
  }
}
