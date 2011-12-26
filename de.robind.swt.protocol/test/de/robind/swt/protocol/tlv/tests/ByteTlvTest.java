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
import de.robind.swt.protocol.tlv.ByteTLV;
import de.robind.swt.protocol.tlv.TLV;

public class ByteTlvTest {
  @Test
  public void getByte() {
    ByteTLV tlv = new ByteTLV((byte)5);
    assertThat(tlv.getByte(), is((byte)5));
  }

  @Test
  public void readOverflow() {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(3);
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
    buffer.writeByte(3);
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
  public void readByte() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(3);
    buffer.writeShort(1);
    buffer.writeByte(5);

    TLV tlv = TLV.read(buffer);
    assertThat(tlv, is(instanceOf(ByteTLV.class)));
    assertThat(((ByteTLV)tlv).getByte(), is((byte)5));
  }

  @Test
  public void writeByte() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    ByteTLV tlv = new ByteTLV((byte)5);

    TLV.write(tlv, buffer);

    assertThat(buffer.readByte(), is((byte)3));
    assertThat(buffer.readShort(), is((short)1));
    assertThat(buffer.readByte(), is((byte)5));
  }
}
