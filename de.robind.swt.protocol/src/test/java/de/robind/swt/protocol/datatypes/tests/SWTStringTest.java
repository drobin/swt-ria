package de.robind.swt.protocol.datatypes.tests;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.jboss.netty.buffer.ChannelBuffers.dynamicBuffer;
import static org.junit.Assert.assertThat;

import org.jboss.netty.buffer.ChannelBuffer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.robind.swt.protocol.SWTProtocolException;
import de.robind.swt.protocol.datatype.SWTString;

public class SWTStringTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void readStringNullBuffer() throws SWTProtocolException {
    exception.expect(NullPointerException.class);
    exception.expectMessage("buffer cannot be null");

    SWTString.readString(null);
  }

  @Test
  public void readStringUnexpectedType() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(0);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Unexpected type read from buffer. Expected 1, but read 0");

    SWTString.readString(buffer);
  }

  @Test
  public void readStringUnexpectedFlags() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(1);
    buffer.writeByte(1);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Unexpected flags read from buffer. Expected 0, but read 1");

    SWTString.readString(buffer);
  }

  @Test
  public void readStringNegativeLength() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(1);
    buffer.writeByte(0);
    buffer.writeShort(-1);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Length of string cannot be negative");

    SWTString.readString(buffer);
  }

  @Test
  public void readStringEmpty() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(1);
    buffer.writeByte(0);
    buffer.writeShort(0);

    assertThat(SWTString.readString(buffer), is(equalTo("")));
  }

  @Test
  public void readStringNonEmpty() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(1);
    buffer.writeByte(0);
    buffer.writeShort(3);
    buffer.writeBytes(new byte[] {'f', 'o', 'o'});

    assertThat(SWTString.readString(buffer), is(equalTo("foo")));
  }

  @Test
  public void readStringArrayNullBuffer() throws SWTProtocolException {
      exception.expect(NullPointerException.class);
      exception.expectMessage("buffer cannot be null");

      SWTString.readStringArray(null);
  }

  @Test
  public void readStringArrayUnexpectedType() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(0);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Unexpected type read from buffer. Expected 1, but read 0");

    SWTString.readStringArray(buffer);
  }

  @Test
  public void readStringArrayUnexpectedFlags() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(1);
    buffer.writeByte(0);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Unexpected flags read from buffer. Expected 1, but read 0");

    SWTString.readStringArray(buffer);
  }

  @Test
  public void readStringArrayNegativeArrayLength() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(1);
    buffer.writeByte(1);
    buffer.writeShort(-1);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Length of array cannot be negative");

    SWTString.readStringArray(buffer);
  }

  @Test
  public void readStringArrayEmpty() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(1);
    buffer.writeByte(1);
    buffer.writeShort(0);

    assertThat(SWTString.readStringArray(buffer).length, is(0));
  }

  @Test
  public void readStringArrayNegativeStringLength() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(1);
    buffer.writeByte(1);
    buffer.writeShort(1);
    buffer.writeShort(-1);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Length of string cannot be negative");

    SWTString.readStringArray(buffer);
  }

  @Test
  public void readStringArrayNonEmpty() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(1);
    buffer.writeByte(1);
    buffer.writeShort(2);
    buffer.writeShort(3);
    buffer.writeBytes(new byte[] {'f', 'o', 'o'});
    buffer.writeShort(3);
    buffer.writeBytes(new byte[] {'b', 'a', 'r'});

    String result[] = SWTString.readStringArray(buffer);
    assertThat(result.length, is(2));
    assertThat(result[0], is(equalTo("foo")));
    assertThat(result[1], is(equalTo("bar")));
  }

  @Test
  public void writeStringNullBuffer() throws SWTProtocolException {
    exception.expect(NullPointerException.class);
    exception.expectMessage("buffer cannot be null");

    SWTString.writeString(null, "");
  }

  @Test
  public void writeStringNullString() throws SWTProtocolException {
    exception.expect(NullPointerException.class);
    exception.expectMessage("string cannot be null");

    SWTString.writeString(dynamicBuffer(), null);
  }

  @Test
  public void writeStringTooLarge() throws SWTProtocolException {
    String string = "x";
    while (string.length() <= Short.MAX_VALUE) {
      string = string + string;
    }

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("The length of the string cannot be greater than 32767");

    SWTString.writeString(dynamicBuffer(), string);
  }

  @Test
  public void writeStringEmpty() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    SWTString.writeString(buffer, "");

    assertThat(buffer.readByte(), is((byte)1));
    assertThat(buffer.readByte(), is((byte)0));
    assertThat(buffer.readShort(), is((short)0));
  }

  @Test
  public void writeStringNonEmpty() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    SWTString.writeString(buffer, "foo");

    assertThat(buffer.readByte(), is((byte)1));
    assertThat(buffer.readByte(), is((byte)0));
    assertThat(buffer.readShort(), is((short)3));
    assertThat(buffer.readByte(), is((byte)'f'));
    assertThat(buffer.readByte(), is((byte)'o'));
    assertThat(buffer.readByte(), is((byte)'o'));
  }

  @Test
  public void writeStringArrayNullBuffer() throws SWTProtocolException {
    exception.expect(NullPointerException.class);
    exception.expectMessage("buffer cannot be null");

    SWTString.writeStringArray(null, new String[] {});
  }

  @Test
  public void writeStringArrayNullArray() throws SWTProtocolException {
    exception.expect(NullPointerException.class);
    exception.expectMessage("array cannot be null");

    SWTString.writeStringArray(dynamicBuffer(), null);
  }

  @Test
  public void writeStringArrayNullArrayElement() throws SWTProtocolException {
    exception.expect(NullPointerException.class);
    exception.expectMessage("Array-element cannot be null");

    SWTString.writeStringArray(dynamicBuffer(), new String[] { null });
  }

  @Test
  public void writeStringArrayStringTooLong() throws SWTProtocolException {
    String s = "x";
    while (s.length() <= Short.MAX_VALUE) {
      s = s + s;
    }

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("The length of the string cannot be greater than 32767");

    SWTString.writeStringArray(dynamicBuffer(), new String[] { "foo", s });
  }

  @Test
  public void writeStringArrayArrayTooLong() throws SWTProtocolException {
    String array[] = new String[Short.MAX_VALUE + 1];
    for (int i = 0; i < array.length; i++) {
      array[i] = "x";
    }

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Length of array cannot be greater than 32767");

    SWTString.writeStringArray(dynamicBuffer(), array);
  }

  @Test
  public void writeStringArrayEmpty() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    SWTString.writeStringArray(buffer, new String[] {});

    assertThat(buffer.readByte(), is((byte)1));
    assertThat(buffer.readByte(), is((byte)1));
    assertThat(buffer.readShort(), is((short)0));
  }

  @Test
  public void writeStringArrayNonEmpty() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    SWTString.writeStringArray(buffer, new String[] {"foo", "bar"});

    assertThat(buffer.readByte(), is((byte)1));
    assertThat(buffer.readByte(), is((byte)1));
    assertThat(buffer.readShort(), is((short)2));
    assertThat(buffer.readShort(), is((short)3));
    assertThat(buffer.readByte(), is((byte)'f'));
    assertThat(buffer.readByte(), is((byte)'o'));
    assertThat(buffer.readByte(), is((byte)'o'));
    assertThat(buffer.readShort(), is((short)3));
    assertThat(buffer.readByte(), is((byte)'b'));
    assertThat(buffer.readByte(), is((byte)'a'));
    assertThat(buffer.readByte(), is((byte)'r'));
  }
}
