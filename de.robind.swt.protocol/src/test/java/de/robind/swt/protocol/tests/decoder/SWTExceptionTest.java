package de.robind.swt.protocol.tests.decoder;

import static de.robind.swt.protocol.datatype.SWTString.writeString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.jboss.netty.buffer.ChannelBuffer;
import org.junit.Test;

import de.robind.swt.msg.SWTException;
import de.robind.swt.protocol.SWTProtocol;
import de.robind.swt.protocol.SWTProtocolException;

public class SWTExceptionTest extends AbstractDecoderTest<SWTException> {
  public SWTExceptionTest() {
    super(SWTProtocol.OP_CALL, SWTProtocol.TYPE_EXC);
  }

  @Test
  public void invalidClassName() throws Throwable {
    ChannelBuffer buffer = createBuffer(0);
    writeString(buffer, "xxx");
    writeString(buffer, "foo");

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Failed to create exception");

    decodeMessage(buffer);
  }

  @Test
  public void regularMessage() throws Throwable {
    ChannelBuffer buffer = createBuffer(30);
    writeString(buffer, "java.lang.Exception");
    writeString(buffer, "foo");

    SWTException msg = decodeMessage(buffer);
    assertThat(msg, is(notNullValue()));
    assertThat(msg.getCause().getClass().getName(), is(equalTo("java.lang.Exception")));
    assertThat(msg.getCause().getMessage(), is(equalTo("foo")));
  }

  @Test
  public void emptyMessage() throws Throwable {
    ChannelBuffer buffer = createBuffer(27);
    writeString(buffer, "java.lang.Exception");
    writeString(buffer, "");

    SWTException msg = decodeMessage(buffer);
    assertThat(msg, is(notNullValue()));
    assertThat(msg.getCause().getClass().getName(), is(equalTo("java.lang.Exception")));
    assertThat(msg.getCause().getMessage(), is(equalTo("")));
  }

  @Test
  public void payloadNotEmptied() throws Throwable {
    ChannelBuffer buffer = createBuffer(31);
    writeString(buffer, "java.lang.Exception");
    writeString(buffer, "foo");

    buffer.writeByte(0);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Data still in payload. Available: 31, consumed: 30");

    decodeMessage(buffer);
  }

  @Test
  public void payloadOverflow() throws Throwable {
    ChannelBuffer buffer = createBuffer(5);
    writeString(buffer, "java.lang.Exception");
    writeString(buffer, "foo");

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Payload-overflow. Available: 5, consumed: 30");

    decodeMessage(buffer);
  }
}
