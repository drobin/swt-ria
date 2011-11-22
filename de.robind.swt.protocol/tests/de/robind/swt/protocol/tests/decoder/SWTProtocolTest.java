package de.robind.swt.protocol.tests.decoder;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.robind.swt.protocol.SWTProtocol;
import de.robind.swt.protocol.SWTProtocolException;

public class SWTProtocolTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void readStringInvalidLength() throws Exception {
    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
    buffer.writeShort(-1);
    buffer.writeBytes(new byte[] {'f', 'o', 'o'});

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("String cannot have negative length (-1)");

    SWTProtocol.readString(buffer);
  }

  @Test
  public void readStringIndexOutOfBounds() throws Exception {
    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
    buffer.writeShort(3);
    buffer.writeBytes(new byte[] {'f', 'o'});

    exception.expect(IndexOutOfBoundsException.class);

    SWTProtocol.readString(buffer);
  }

  @Test
  public void readStringEmpty() throws Exception {
    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
    buffer.writeShort(0);

    assertThat(SWTProtocol.readString(buffer), is(equalTo("")));
  }

  @Test
  public void readString() throws Exception {
    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
    buffer.writeShort(3);
    buffer.writeBytes(new byte[] {'f', 'o', 'o'});

    assertThat(SWTProtocol.readString(buffer), is(equalTo("foo")));
  }

  @Test
  public void writeStringNullBuffer() throws Exception {
    exception.expect(NullPointerException.class);
    exception.expectMessage("buffer cannot be null");

    SWTProtocol.writeString(null, "");
  }

  @Test
  public void writeStringNullValue() throws Exception {
    exception.expect(NullPointerException.class);
    exception.expectMessage("value cannot be null");

    SWTProtocol.writeString(ChannelBuffers.dynamicBuffer(), null);
  }

  @Test
  public void writeStringValueTooLong() throws Exception {
    String value = "x";
    while (value.length() < Short.MAX_VALUE) {
      value = value + value;
    }

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("value is too long: " + value.length());

    SWTProtocol.writeString(ChannelBuffers.dynamicBuffer(), value);
  }

  @Test
  public void writeStringIndexOutOfBoundsWhileWriteLength() throws Exception {
    ChannelBuffer buffer = ChannelBuffers.directBuffer(1);

    exception.expect(IndexOutOfBoundsException.class);

    SWTProtocol.writeString(buffer, "foo");
  }

  @Test
  public void writeStringIndexOutOfBoundsWhileWriteValue() throws Exception {
    ChannelBuffer buffer = ChannelBuffers.directBuffer(3);

    exception.expect(IndexOutOfBoundsException.class);

    SWTProtocol.writeString(buffer, "foo");
  }

  @Test
  public void writeStringEmpty() throws Exception {
    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();

    SWTProtocol.writeString(buffer, "");

    assertThat(buffer.readableBytes(), is(2));
    assertThat(buffer.readShort(), is((short)0));
  }

  @Test
  public void writeString() throws Exception {
    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();

    SWTProtocol.writeString(buffer, "foo");

    assertThat(buffer.readableBytes(), is(5));
    assertThat(buffer.readShort(), is((short)3));
    assertThat(buffer.readByte(), is((byte)'f'));
    assertThat(buffer.readByte(), is((byte)'o'));
    assertThat(buffer.readByte(), is((byte)'o'));
  }

  @Test
  public void readArgumentInvalidType() throws Exception {
    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
    buffer.writeByte(42);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Invalid argument-type: 42");

    SWTProtocol.readArgument(buffer);
  }

  @Test
  public void readArgumentString() throws Exception {
    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
    buffer.writeByte(SWTProtocol.ARG_STRING);
    buffer.writeShort(3);
    buffer.writeBytes(new byte[] {'f', 'o', 'o'});

    Object value = SWTProtocol.readArgument(buffer);
    assertThat(value, is(instanceOf(String.class)));
    assertThat((String)value, is(equalTo("foo")));
  }

  @Test
  public void readArgumentInt() throws Exception {
    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
    buffer.writeByte(SWTProtocol.ARG_INT);
    buffer.writeInt(4711);

    Object value = SWTProtocol.readArgument(buffer);
    assertThat(value, is(instanceOf(Integer.class)));
    assertThat((Integer)value, is(4711));
  }

  @Test
  public void readArgumentByte() throws Exception {
    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
    buffer.writeByte(SWTProtocol.ARG_BYTE);
    buffer.writeByte(42);

    Object value = SWTProtocol.readArgument(buffer);
    assertThat(value, is(instanceOf(Byte.class)));
    assertThat((Byte)value, is((byte)42));
  }

  @Test
  public void readArgumentBoolTrue() throws Exception {
    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
    buffer.writeByte(SWTProtocol.ARG_BOOL);
    buffer.writeByte(1);

    Object value = SWTProtocol.readArgument(buffer);
    assertThat(value, is(instanceOf(Boolean.class)));
    assertThat((Boolean)value, is(true));
  }

  @Test
  public void readArgumentBoolFalse() throws Exception {
    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
    buffer.writeByte(SWTProtocol.ARG_BOOL);
    buffer.writeByte(0);

    Object value = SWTProtocol.readArgument(buffer);
    assertThat(value, is(instanceOf(Boolean.class)));
    assertThat((Boolean)value, is(false));
  }
}
