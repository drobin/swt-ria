package de.robind.swt.protocol.tests.decoder;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.jboss.netty.buffer.ChannelBuffer;
import org.junit.Test;

import de.robind.swt.msg.SWTRegResponse;
import de.robind.swt.protocol.SWTProtocol;
import de.robind.swt.protocol.SWTProtocolException;

public class SWTRegResponseTest extends AbstractDecoderTest<SWTRegResponse> {
  public SWTRegResponseTest() {
    super(SWTProtocol.OP_REG, SWTProtocol.TYPE_RSP);
  }

  @Test
  public void emptyClassName() throws Throwable {
    ChannelBuffer buffer = createBuffer(4);
    SWTProtocol.writeString(buffer, "");
    SWTProtocol.writeString(buffer, "");

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Class-name cannot be empty");

    decodeMessage(buffer);
  }

  @Test
  public void success() throws Throwable {
    ChannelBuffer buffer = createBuffer(0);

    SWTRegResponse msg = decodeMessage(buffer);

    assertThat(msg, is(notNullValue()));
    assertThat(msg.isSuccessful(), is(true));
  }

  @Test
  public void withErrorIncluded() throws Throwable {
    ChannelBuffer buffer = createBuffer(7);
    SWTProtocol.writeString(buffer, "foo");
    SWTProtocol.writeString(buffer, "");

    SWTRegResponse msg = decodeMessage(buffer);

    assertThat(msg, is(notNullValue()));
    assertThat(msg.isSuccessful(), is(false));
    assertThat(msg.getExceptionClass(), is(equalTo("foo")));
    assertThat(msg.getExceptionMessage(), is(equalTo("")));
  }

  @Test
  public void withErrorAndMessageIncluded() throws Throwable {
    ChannelBuffer buffer = createBuffer(10);
    SWTProtocol.writeString(buffer, "foo");
    SWTProtocol.writeString(buffer, "bar");

    SWTRegResponse msg = decodeMessage(buffer);

    assertThat(msg, is(notNullValue()));
    assertThat(msg.isSuccessful(), is(false));
    assertThat(msg.getExceptionClass(), is(equalTo("foo")));
    assertThat(msg.getExceptionMessage(), is(equalTo("bar")));
  }

  @Test
  public void payloadNotEmptied() throws Throwable {
    ChannelBuffer buffer = createBuffer(8);
    SWTProtocol.writeString(buffer, "foo");
    SWTProtocol.writeString(buffer, "");

    buffer.writeByte(0);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Data still in payload. Available: 8, consumed: 7");

    decodeMessage(buffer);
  }

  @Test
  public void payloadOverflow() throws Throwable {
    ChannelBuffer buffer = createBuffer(5);
    SWTProtocol.writeString(buffer, "foo");
    SWTProtocol.writeString(buffer, "");

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Payload-overflow. Available: 5, consumed: 7");

    decodeMessage(buffer);
  }
}
