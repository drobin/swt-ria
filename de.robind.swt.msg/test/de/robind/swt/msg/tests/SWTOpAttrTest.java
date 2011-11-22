package de.robind.swt.msg.tests;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.robind.swt.msg.SWTMessage;
import de.robind.swt.msg.SWTOpAttr;

public class SWTOpAttrTest {
  @Test
  public void isSWTMessage() {
    assertThat(new SWTOpAttr() {}, is(instanceOf(SWTMessage.class)));
  }
}
