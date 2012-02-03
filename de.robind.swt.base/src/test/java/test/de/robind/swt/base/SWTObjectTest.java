package test.de.robind.swt.base;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.robind.swt.base.SWTObject;

public class SWTObjectTest {
  @Test
  public void getId() {
    assertThat(new SWTObject() {}.getId(), is(1));
    assertThat(new SWTObject() {}.getId(), is(2));
    assertThat(new SWTObject() {}.getId(), is(3));
  }
}
