package de.robind.swt.msg.tests;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.robind.swt.msg.SWTMessage;
import de.robind.swt.msg.SWTOpNew;

public class SWTOpNewTest {
  @Test
  public void isSWTMessage() {
    assertThat(new SWTOpNew() {}, is(instanceOf(SWTMessage.class)));
  }
}
