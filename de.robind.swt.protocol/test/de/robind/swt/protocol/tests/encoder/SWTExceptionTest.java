package de.robind.swt.protocol.tests.encoder;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.jboss.netty.buffer.ChannelBuffer;
import org.junit.Test;

import de.robind.swt.msg.SWTException;
import de.robind.swt.protocol.SWTProtocol;

public class SWTExceptionTest extends AbstractEncoderTest<SWTException> {
  public SWTExceptionTest() {
    super(SWTProtocol.OP_CALL, SWTProtocol.TYPE_EXC);
  }

  @Test
  public void regularException() throws Throwable {
    IndexOutOfBoundsException exc = new IndexOutOfBoundsException("foo");
    SWTException msg = this.factory.createException(exc);
    ChannelBuffer buffer = encodeMessage(msg, 42);

    assertThat(SWTProtocol.readString(buffer), is(equalTo("java.lang.IndexOutOfBoundsException")));
    assertThat(SWTProtocol.readString(buffer), is(equalTo("foo")));
  }

  @Test
  public void nullMessage() throws Throwable {
    IndexOutOfBoundsException exc = new IndexOutOfBoundsException();
    SWTException msg = this.factory.createException(exc);
    ChannelBuffer buffer = encodeMessage(msg, 39);

    assertThat(exc.getMessage(), is(nullValue()));
    assertThat(SWTProtocol.readString(buffer), is(equalTo("java.lang.IndexOutOfBoundsException")));
    assertThat(SWTProtocol.readString(buffer), is(equalTo("")));
  }

  @Test
  public void emptyMessage() throws Throwable {
    IndexOutOfBoundsException exc = new IndexOutOfBoundsException("");
    SWTException msg = this.factory.createException(exc);
    ChannelBuffer buffer = encodeMessage(msg, 39);

    assertThat(exc.getMessage(), is(equalTo("")));
    assertThat(SWTProtocol.readString(buffer), is(equalTo("java.lang.IndexOutOfBoundsException")));
    assertThat(SWTProtocol.readString(buffer), is(equalTo("")));
  }
}
