package de.robind.swt.msg.tests;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.robind.swt.msg.SWTMessage;
import de.robind.swt.msg.SWTTrap;

public class SWTTrapTest {
  @Test
  public void isSWTMessage() {
    assertThat(new SWTTrap() {}, is(instanceOf(SWTMessage.class)));
  }
}
