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
import de.robind.swt.protocol.tlv.IntTLV;
import de.robind.swt.protocol.tlv.TLV;

public class IntTlvTest {
  @Test
  public void getInt() {
    IntTLV tlv = new IntTLV(4711);
    assertThat(tlv.getInt(), is(4711));
  }

  @Test
  public void readOverflow() {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(2);
    buffer.writeShort(2);
    buffer.writeShort(5);

    try {
      TLV.read(buffer);
      fail("SWTProtocolException expected");
    } catch (SWTProtocolException e) {
      assertThat(e.getMessage(), is(equalTo("Overflow detected")));
      assertThat(buffer.readerIndex(), is(0));
      assertThat(buffer.writerIndex(), is(5));
    }
  }

  @Test
  public void readUnderflow() {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(2);
    buffer.writeShort(5);
    buffer.writeInt(4711);
    buffer.writeByte(0);

    try {
      TLV.read(buffer);
      fail("SWTProtocolException expected");
    } catch (SWTProtocolException e) {
      assertThat(e.getMessage(), is(equalTo("Underflow detected")));
      assertThat(buffer.readerIndex(), is(0));
      assertThat(buffer.writerIndex(), is(8));
    }
  }

  @Test
  public void readInt() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(2);
    buffer.writeShort(4);
    buffer.writeInt(4711);

    TLV tlv = TLV.read(buffer);
    assertThat(tlv, is(instanceOf(IntTLV.class)));
    assertThat(((IntTLV)tlv).getInt(), is(4711));
  }

  @Test
  public void writeInt() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    IntTLV tlv = new IntTLV(4711);

    TLV.write(tlv, buffer);

    assertThat(buffer.readByte(), is((byte)2));
    assertThat(buffer.readShort(), is((short)4));
    assertThat(buffer.readInt(), is(4711));
  }
}
