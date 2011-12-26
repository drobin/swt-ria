package de.robind.swt.protocol.tlv.tests;

import static junit.framework.Assert.fail;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.jboss.netty.buffer.ChannelBuffers.dynamicBuffer;
import static org.junit.Assert.assertThat;

import org.jboss.netty.buffer.ChannelBuffer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.robind.swt.protocol.SWTProtocolException;
import de.robind.swt.protocol.tlv.StringTLV;
import de.robind.swt.protocol.tlv.TLV;

public class StringTLVTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void ctorNullValue() {
    exception.expect(NullPointerException.class);
    exception.expectMessage("value cannot be null");

    new StringTLV(null);
  }

  @Test
  public void getString() {
    StringTLV tlv = new StringTLV("foo");
    assertThat(tlv.getString(), is(equalTo("foo")));
  }

  @Test
  public void readNegativeLength() throws SWTProtocolException {
    exception.expect(SWTProtocolException.class);
    exception.expectMessage("String-length cannot be negative");

    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(1);
    buffer.writeShort(2);
    buffer.writeShort(-1);

    TLV.read(buffer);
  }

  @Test
  public void readOverflow() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(1);
    buffer.writeShort(5);
    buffer.writeShort(4);
    buffer.writeBytes(new byte[] {'f', 'o', 'o'});

    try {
      TLV.read(buffer);
      fail("SWTProtocolException expected");
    } catch (SWTProtocolException e) {
      assertThat(e.getMessage(), is(equalTo("Overflow detected")));
      assertThat(buffer.readerIndex(), is(0));
      assertThat(buffer.writerIndex(), is(8));
    }
  }

  @Test
  public void readUnderflow() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(1);
    buffer.writeShort(6);
    buffer.writeShort(3);
    buffer.writeBytes(new byte[] {'f', 'o', 'o'});
    buffer.writeByte(0);

    try {
      TLV.read(buffer);
      fail("SWTProtocolException expected");
    } catch (SWTProtocolException e) {
      assertThat(e.getMessage(), is(equalTo("Underflow detected")));
      assertThat(buffer.readerIndex(), is(0));
      assertThat(buffer.writerIndex(), is(9));
    }
  }

  @Test
  public void readEmpty() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(1);
    buffer.writeShort(2);
    buffer.writeShort(0);

    TLV tlv = TLV.read(buffer);
    assertThat(tlv, is(instanceOf(StringTLV.class)));
    assertThat(((StringTLV)tlv).getString(), is(equalTo("")));
  }

  @Test
  public void readNonEmpty() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(1);
    buffer.writeShort(5);
    buffer.writeShort(3);
    buffer.writeBytes(new byte[] {'f', 'o', 'o'});

    TLV tlv = TLV.read(buffer);
    assertThat(tlv, is(instanceOf(StringTLV.class)));
    assertThat(((StringTLV)tlv).getString(), is(equalTo("foo")));
  }

  @Test
  public void writeStringTooLong() throws SWTProtocolException {
    String s = "x";
    while (s.length() < Short.MAX_VALUE) {
      s = s + s;
    }

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("The string cannot be longer than 32767");

    StringTLV tlv = new StringTLV(s);
    TLV.write(tlv, dynamicBuffer());
  }

  @Test
  public void writeEmpty() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    StringTLV tlv = new StringTLV("");

    TLV.write(tlv, buffer);

    assertThat(buffer.readByte(), is((byte)1));
    assertThat(buffer.readShort(), is((short)2));
    assertThat(buffer.readShort(), is((short)0));
  }

  @Test
  public void writeNonEmpty() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    StringTLV tlv = new StringTLV("foo");

    TLV.write(tlv, buffer);

    assertThat(buffer.readByte(), is((byte)1));
    assertThat(buffer.readShort(), is((short)5));
    assertThat(buffer.readShort(), is((short)3));
    assertThat(buffer.readByte(), is((byte)'f'));
    assertThat(buffer.readByte(), is((byte)'o'));
    assertThat(buffer.readByte(), is((byte)'o'));
  }
}
