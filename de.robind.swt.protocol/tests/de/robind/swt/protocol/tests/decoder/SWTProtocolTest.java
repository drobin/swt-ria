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
