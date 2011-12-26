package de.robind.swt.protocol.tlv.tests;

import static junit.framework.Assert.fail;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.jboss.netty.buffer.ChannelBuffers.dynamicBuffer;
import static org.junit.Assert.assertThat;

import org.jboss.netty.buffer.ChannelBuffer;
import org.junit.Test;

import de.robind.swt.protocol.SWTProtocolException;
import de.robind.swt.protocol.tlv.BoolTLV;
import de.robind.swt.protocol.tlv.TLV;

public class BoolTlvTest {
  @Test
  public void getBool() {
    BoolTLV tlv = new BoolTLV(true);
    assertThat(tlv.getBool(), is(true));

    tlv = new BoolTLV(false);
    assertThat(tlv.getBool(), is(false));
  }

  @Test
  public void readOverflow() {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(4);
    buffer.writeShort(0);
    buffer.writeByte(1);

    try {
      TLV.read(buffer);
      fail("SWTProtocolException expected");
    } catch (SWTProtocolException e) {
      assertThat(e.getMessage(), is(equalTo("Overflow detected")));
      assertThat(buffer.readerIndex(), is(0));
      assertThat(buffer.writerIndex(), is(4));
    }
  }

  @Test
  public void readUnderflow() {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(4);
    buffer.writeShort(2);
    buffer.writeByte(0);
    buffer.writeByte(1);

    try {
      TLV.read(buffer);
      fail("SWTProtocolException expected");
    } catch (SWTProtocolException e) {
      assertThat(e.getMessage(), is(equalTo("Underflow detected")));
      assertThat(buffer.readerIndex(), is(0));
      assertThat(buffer.writerIndex(), is(5));
    }
  }

  @Test
  public void readBoolTrue() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(4);
    buffer.writeShort(1);
    buffer.writeByte(1);

    TLV tlv = TLV.read(buffer);
    assertThat(tlv, is(instanceOf(BoolTLV.class)));
    assertThat(((BoolTLV)tlv).getBool(), is(true));
  }

  @Test
  public void readBoolFalse() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(4);
    buffer.writeShort(1);
    buffer.writeByte(0);

    TLV tlv = TLV.read(buffer);
    assertThat(tlv, is(instanceOf(BoolTLV.class)));
    assertThat(((BoolTLV)tlv).getBool(), is(false));
  }

  @Test
  public void writeByteTrue() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    BoolTLV tlv = new BoolTLV(true);

    TLV.write(tlv, buffer);

    assertThat(buffer.readByte(), is((byte)4));
    assertThat(buffer.readShort(), is((short)1));
    assertThat(buffer.readByte(), is((byte)1));
  }

  @Test
  public void writeByteFalse() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    BoolTLV tlv = new BoolTLV(false);

    TLV.write(tlv, buffer);

    assertThat(buffer.readByte(), is((byte)4));
    assertThat(buffer.readShort(), is((short)1));
    assertThat(buffer.readByte(), is((byte)0));
  }
}
