package de.robind.swt.msg.tests;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.robind.swt.msg.SWTException;
import de.robind.swt.msg.SWTMessage;
import de.robind.swt.msg.SWTRequest;
import de.robind.swt.msg.SWTResponse;

public class SWTExceptionTest extends AbstractMessageTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void isSWTMessage() {
    SWTException msg = this.factory.createException(new Exception());
    assertThat(msg, is(instanceOf(SWTMessage.class)));
  }

  @Test
  public void isSWTResponse() {
    SWTException msg = this.factory.createException(new Exception());
    assertThat(msg, is(instanceOf(SWTResponse.class)));
    assertThat(msg, is(not(instanceOf(SWTRequest.class))));
  }

  @Test
  public void nullCause() {
    exception.expect(NullPointerException.class);
    exception.expectMessage("cause cannot be null");

    this.factory.createException(null);
  }

  @Test
  public void withException() {
    Throwable cause = new IndexOutOfBoundsException();
    SWTException msg = this.factory.createException(cause);
    assertThat(msg.getCause(), is(cause));
  }
}
