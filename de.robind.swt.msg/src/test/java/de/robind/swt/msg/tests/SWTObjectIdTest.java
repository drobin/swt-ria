package de.robind.swt.msg.tests;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.robind.swt.msg.SWTObjectId;

public class SWTObjectIdTest {
  @Test
  public void valid() {
    SWTObjectId objId = new SWTObjectId(4711);
    assertThat(objId.isValid(), is(true));
    assertThat(objId.getId(), is(4711));
  }

  @Test
  public void invalid() {
    SWTObjectId objId = SWTObjectId.undefined();
    Throwable expectedException = null;

    assertThat(objId.isValid(), is(false));

    try {
      objId.getId();
    } catch (UnsupportedOperationException e) {
      expectedException = e;
    }

    assertThat(expectedException, is(notNullValue()));
    assertThat(expectedException, is(instanceOf(UnsupportedOperationException.class)));
  }
}
