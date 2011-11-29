package de.robind.swt.protocol.tests.decoder;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.robind.swt.protocol.SWTMessageDecoder;

public class GeneralTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void nullFactory() {
    exception.expect(NullPointerException.class);
    exception.expectMessage("factory cannot be null");

    new SWTMessageDecoder(null);
  }
}
