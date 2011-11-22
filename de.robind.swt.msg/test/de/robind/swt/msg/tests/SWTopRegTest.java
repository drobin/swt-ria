package de.robind.swt.msg.tests;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.robind.swt.msg.SWTMessage;
import de.robind.swt.msg.SWTOpReg;

public class SWTopRegTest {
  @Test
  public void isSWTMessage() {
    assertThat(new SWTOpReg() {}, is(instanceOf(SWTMessage.class)));
  }
}
