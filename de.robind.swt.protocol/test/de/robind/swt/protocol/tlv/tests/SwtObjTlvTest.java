package de.robind.swt.protocol.tlv.tests;

import static junit.framework.Assert.fail;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.jboss.netty.buffer.ChannelBuffers.dynamicBuffer;
import static org.junit.Assert.assertThat;

import org.jboss.netty.buffer.ChannelBuffer;
import org.junit.Test;

import de.robind.swt.msg.SWTObjectId;
import de.robind.swt.protocol.SWTProtocolException;
import de.robind.swt.protocol.tlv.SwtObjTLV;
import de.robind.swt.protocol.tlv.TLV;

public class SwtObjTlvTest {
  @Test
  public void getSWTOject() {
    SwtObjTLV tlv = new SwtObjTLV(new SWTObjectId(4711));
    assertThat(tlv.getObjId().getId(), is(4711));
  }

  @Test
  public void readOverflow() {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(5);
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
    buffer.writeByte(5);
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
  public void readValidObjId() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(5);
    buffer.writeShort(4);
    buffer.writeInt(4711);

    TLV tlv = TLV.read(buffer);
    assertThat(tlv, is(instanceOf(SwtObjTLV.class)));
    assertThat(((SwtObjTLV)tlv).getObjId().isValid(), is(true));
    assertThat(((SwtObjTLV)tlv).getObjId().getId(), is(4711));
  }

  @Test
  public void readInvalidObjId() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(5);
    buffer.writeShort(4);
    buffer.writeInt(-1);

    TLV tlv = TLV.read(buffer);
    assertThat(tlv, is(instanceOf(SwtObjTLV.class)));
    assertThat(((SwtObjTLV)tlv).getObjId().isValid(), is(false));
  }

  @Test
  public void writeValidObjId() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    SwtObjTLV tlv = new SwtObjTLV(new SWTObjectId(4711));

    TLV.write(tlv, buffer);

    assertThat(buffer.readByte(), is((byte)5));
    assertThat(buffer.readShort(), is((short)4));
    assertThat(buffer.readInt(), is(4711));
  }

  @Test
  public void writeInvalidObjId() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    SwtObjTLV tlv = new SwtObjTLV(SWTObjectId.undefined());

    TLV.write(tlv, buffer);

    assertThat(buffer.readByte(), is((byte)5));
    assertThat(buffer.readShort(), is((short)4));
    assertThat(buffer.readInt(), is(-1));
  }
}
