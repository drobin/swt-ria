package de.robind.swt.protocol.tlv;

import java.io.UnsupportedEncodingException;

import org.jboss.netty.buffer.ChannelBuffer;

import de.robind.swt.protocol.SWTProtocolException;

/**
 * A {@link TLV} representing a {@link String}.
 *
 * @author Robin Doer
 */
public class StringTLV extends TLV {
  /**
   * The encapsulated value.
   */
  private String str = null;

  /**
   * Make std-c'tor not accessible from outside.
   */
  StringTLV() {
  }

  /**
   * Creates a new {@link StringTLV}.
   *
   * @param value The encapsulated value
   * @throws NullPointerException if the value is <code>null</code>
   */
  public StringTLV(String value) throws NullPointerException {
    if (value == null) {
      throw new NullPointerException("value cannot be null");
    }

    this.str = value;
  }

  public String getString() {
    return (this.str);
  }

  /* (non-Javadoc)
   * @see de.robind.swt.protocol.tlv.TLV#readFromBuffer(org.jboss.netty.buffer.ChannelBuffer)
   */
  @Override
  protected void readFromBuffer(ChannelBuffer buffer) throws SWTProtocolException {
    short length = buffer.readShort();

    if (length < 0) {
      throw new SWTProtocolException("String-length cannot be negative");
    }

    byte data[] = new byte[length];
    buffer.readBytes(data);

    try {
      this.str = new String(data, "US-ASCII");
    } catch (UnsupportedEncodingException e) {
      throw new SWTProtocolException(e);
    }
  }

  /* (non-Javadoc)
   * @see de.robind.swt.protocol.tlv.TLV#writeToBuffer(org.jboss.netty.buffer.ChannelBuffer)
   */
  @Override
  protected void writeToBuffer(ChannelBuffer buffer) throws SWTProtocolException {
    if (this.str.length() > Short.MAX_VALUE) {
      throw new SWTProtocolException(
          "The string cannot be longer than " + Short.MAX_VALUE);
    }

    try {
      buffer.writeShort(this.str.length());
      buffer.writeBytes(this.str.getBytes("US-ASCII"));
    } catch (UnsupportedEncodingException e) {
      throw new SWTProtocolException(e);
    }
  }
}
