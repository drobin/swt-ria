package de.robind.swt.protocol.tests;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.robind.swt.msg.SWTObjectId;
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
  public void readBooleanNullBuffer() {
    exception.expect(NullPointerException.class);
    exception.expectMessage("buffer cannot be null");

    SWTProtocol.readBoolean(null);
  }

  @Test
  public void readBooleanIndexOutOfBounds() {
    ChannelBuffer buffer = ChannelBuffers.directBuffer(1);
    buffer.writeByte(42);
    buffer.readByte();

    exception.expect(IndexOutOfBoundsException.class);

    SWTProtocol.readBoolean(buffer);
  }

  @Test
  public void readBooleanTrue() {
    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
    buffer.writeByte(1);

    assertThat(SWTProtocol.readBoolean(buffer), is(true));
  }

  @Test
  public void readBooleanFalse() {
    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
    buffer.writeByte(0);

    assertThat(SWTProtocol.readBoolean(buffer), is(false));
  }

  @Test
  public void writeBooleanNullBuffer() {
    exception.expect(NullPointerException.class);
    exception.expectMessage("buffer cannot be null");

    SWTProtocol.writeBoolean(null, true);
  }

  @Test
  public void writeBooleanIndexOutOfBounds() {
    ChannelBuffer buffer = ChannelBuffers.directBuffer(1);
    buffer.writerIndex(1);

    exception.expect(IndexOutOfBoundsException.class);

    SWTProtocol.writeBoolean(buffer, true);
  }

  @Test
  public void writeBooleanTrue() {
    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();

    SWTProtocol.writeBoolean(buffer, true);

    assertThat(buffer.readableBytes(), is(1));
    assertThat(buffer.readByte(), is((byte)1));
  }

  @Test
  public void writeBooleanFalse() {
    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();

    SWTProtocol.writeBoolean(buffer, false);

    assertThat(buffer.readableBytes(), is(1));
    assertThat(buffer.readByte(), is((byte)0));
  }

  @Test
  public void readSwtObjectIdNullBuffer() throws Exception {
    exception.expect(NullPointerException.class);
    exception.expectMessage("buffer cannot be null");

    SWTProtocol.readSwtObjectId(null);
  }

  @Test
  public void readSwtObjectIdIndexOutOfBounds() throws Exception {
    ChannelBuffer buffer = ChannelBuffers.directBuffer(1);
    buffer.writeByte(42);
    buffer.readByte();

    exception.expect(IndexOutOfBoundsException.class);

    SWTProtocol.readSwtObjectId(buffer);
  }

  @Test
  public void readSwtObjectIdValid() {
    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
    buffer.writeInt(4711);

    SWTObjectId objId = SWTProtocol.readSwtObjectId(buffer);
    assertThat(objId.isValid(), is(true));
    assertThat(objId.getId(), is(4711));
  }

  @Test
  public void readSwtObjectIdInvalid() {
    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
    buffer.writeInt(-1);

    SWTObjectId objId = SWTProtocol.readSwtObjectId(buffer);
    assertThat(objId.isValid(), is(false));
  }

  @Test
  public void writeSwtObjectNullBuffer() {
    exception.expect(NullPointerException.class);
    exception.expectMessage("buffer cannot be null");

    SWTProtocol.writeSwtObjectId(null, SWTObjectId.undefined());
  }

  @Test
  public void writeSwtObjectNullValue() {
    exception.expect(NullPointerException.class);
    exception.expectMessage("value cannot be null");

    SWTProtocol.writeSwtObjectId(ChannelBuffers.dynamicBuffer(), null);
  }

  @Test
  public void writeSwtObjectIdIndexOutOfBounds() {
    ChannelBuffer buffer = ChannelBuffers.directBuffer(3);

    exception.expect(IndexOutOfBoundsException.class);

    SWTProtocol.writeSwtObjectId(buffer, SWTObjectId.undefined());
  }

  @Test
  public void writeSwtObjectIdValid() {
    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
    SWTProtocol.writeSwtObjectId(buffer, new SWTObjectId(4711));

    assertThat(buffer.readInt(), is(4711));
  }

  @Test
  public void writeSwtObjectIdInvalid() {
    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
    SWTProtocol.writeSwtObjectId(buffer, SWTObjectId.undefined());

    assertThat(buffer.readInt(), is(-1));
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

  @Test
  public void readArgumentSwtObjectIdValid() throws Exception {
    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
    buffer.writeByte(SWTProtocol.ARG_SWTOBJ);
    buffer.writeInt(4711);

    Object value = SWTProtocol.readArgument(buffer);
    assertThat(value, is(instanceOf(SWTObjectId.class)));
    assertThat(((SWTObjectId)value).isValid(), is(true));
    assertThat(((SWTObjectId)value).getId(), is(4711));
  }

  @Test
  public void readArgumentSwtObjectIdInvalid() throws Exception {
    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
    buffer.writeByte(SWTProtocol.ARG_SWTOBJ);
    buffer.writeInt(-1);

    Object value = SWTProtocol.readArgument(buffer);
    assertThat(value, is(instanceOf(SWTObjectId.class)));
    assertThat(((SWTObjectId)value).isValid(), is(false));
  }

  @Test
  public void writeArgumentInvalidArgument() throws Exception {
    class Foo {
    }

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Invalid argument of type de.robind.swt.protocol.tests.SWTProtocolTest$1Foo");

    SWTProtocol.writeArgument(ChannelBuffers.dynamicBuffer(), new Foo());
  }

  @Test
  public void writeArgumentStringIndexOutOfBoundsWhileWriteType() throws Exception {
    ChannelBuffer buffer = ChannelBuffers.directBuffer(1);
    buffer.writerIndex(1);

    exception.expect(IndexOutOfBoundsException.class);

    SWTProtocol.writeArgument(buffer, "foo");
  }

  @Test
  public void writeArgumentStringOutOfBoundsWhileWriteString() throws Exception {
    ChannelBuffer buffer = ChannelBuffers.directBuffer(3);

    exception.expect(IndexOutOfBoundsException.class);

    SWTProtocol.writeArgument(buffer, "foo");
  }

  @Test
  public void writeArgumentString() throws Exception {
    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();

    SWTProtocol.writeArgument(buffer, "foo");
    assertThat(buffer.readableBytes(), is(6));
    assertThat(buffer.readByte(), is(SWTProtocol.ARG_STRING));
    assertThat(buffer.readShort(), is((short)3));
    assertThat(buffer.readByte(), is((byte)'f'));
    assertThat(buffer.readByte(), is((byte)'o'));
    assertThat(buffer.readByte(), is((byte)'o'));
  }

  @Test
  public void writeArgumentIntIndexOutOfBoundsWhileWriteType() throws Exception {
    ChannelBuffer buffer = ChannelBuffers.directBuffer(1);
    buffer.writerIndex(1);

    exception.expect(IndexOutOfBoundsException.class);

    SWTProtocol.writeArgument(buffer, 4711);
  }

  @Test
  public void writeArgumentIntOutOfBoundsWhileWriteString() throws Exception {
    ChannelBuffer buffer = ChannelBuffers.directBuffer(3);

    exception.expect(IndexOutOfBoundsException.class);

    SWTProtocol.writeArgument(buffer, 4711);
  }

  @Test
  public void writeArgumentInt() throws Exception {
    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();

    SWTProtocol.writeArgument(buffer, 4711);
    assertThat(buffer.readableBytes(), is(5));
    assertThat(buffer.readByte(), is(SWTProtocol.ARG_INT));
    assertThat(buffer.readInt(), is(4711));
  }

  @Test
  public void writeArgumentByteIndexOutOfBoundsWhileWriteType() throws Exception {
    ChannelBuffer buffer = ChannelBuffers.directBuffer(1);
    buffer.writerIndex(1);

    exception.expect(IndexOutOfBoundsException.class);

    SWTProtocol.writeArgument(buffer, (byte)42);
  }

  @Test
  public void writeArgumentByteOutOfBoundsWhileWriteString() throws Exception {
    ChannelBuffer buffer = ChannelBuffers.directBuffer(1);

    exception.expect(IndexOutOfBoundsException.class);

    SWTProtocol.writeArgument(buffer, (byte)42);
  }

  @Test
  public void writeArgumentByte() throws Exception {
    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();

    SWTProtocol.writeArgument(buffer, (byte)42);
    assertThat(buffer.readableBytes(), is(2));
    assertThat(buffer.readByte(), is(SWTProtocol.ARG_BYTE));
    assertThat(buffer.readByte(), is((byte)42));
  }

  @Test
  public void writeArgumentBoolIndexOutOfBoundsWhileWriteType() throws Exception {
    ChannelBuffer buffer = ChannelBuffers.directBuffer(1);
    buffer.writerIndex(1);

    exception.expect(IndexOutOfBoundsException.class);

    SWTProtocol.writeArgument(buffer, true);
  }

  @Test
  public void writeArgumentBoolOutOfBoundsWhileWriteString() throws Exception {
    ChannelBuffer buffer = ChannelBuffers.directBuffer(1);

    exception.expect(IndexOutOfBoundsException.class);

    SWTProtocol.writeArgument(buffer, true);
  }

  @Test
  public void writeArgumentBoolTrue() throws Exception {
    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();

    SWTProtocol.writeArgument(buffer, true);
    assertThat(buffer.readableBytes(), is(2));
    assertThat(buffer.readByte(), is(SWTProtocol.ARG_BOOL));
    assertThat(buffer.readByte(), is((byte)1));
  }

  @Test
  public void writeArgumentBoolFalse() throws Exception {
    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();

    SWTProtocol.writeArgument(buffer, false);
    assertThat(buffer.readableBytes(), is(2));
    assertThat(buffer.readByte(), is(SWTProtocol.ARG_BOOL));
    assertThat(buffer.readByte(), is((byte)0));
  }

  @Test
  public void writeArgumentSwtObjectIdIndexOutOfBounds() throws Exception {
    ChannelBuffer buffer = ChannelBuffers.directBuffer(3);

    exception.expect(IndexOutOfBoundsException.class);

    SWTProtocol.writeArgument(buffer, new SWTObjectId(4711));
  }

  @Test
  public void writeArgumentSwtObjectIdValid() throws Exception {
    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();

    SWTProtocol.writeArgument(buffer, new SWTObjectId(4711));

    assertThat(buffer.readByte(), is(SWTProtocol.ARG_SWTOBJ));
    assertThat(buffer.readInt(), is(4711));
  }

  @Test
  public void writeArgumentSwtObjectIdInvalid() throws Exception {
    ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();

    SWTProtocol.writeArgument(buffer, SWTObjectId.undefined());

    assertThat(buffer.readByte(), is(SWTProtocol.ARG_SWTOBJ));
    assertThat(buffer.readInt(), is(-1));
  }
}
