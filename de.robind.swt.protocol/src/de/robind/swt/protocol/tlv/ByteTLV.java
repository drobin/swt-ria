package de.robind.swt.protocol.tlv;

import org.jboss.netty.buffer.ChannelBuffer;

import de.robind.swt.protocol.SWTProtocolException;

/**
 * A TLV representing a {@link Byte}.
 *
 * @author Robin Doer
 */
public class ByteTLV extends TLV {
  /**
   * The encapsulated value.
   */
  private byte value;

  /**
   * Make std-c'tor not accessible from outside.
   */
  ByteTLV() {
  }

  /**
   * Creates a new {@link ByteTLV}-instance
   *
   * @param value The encapsulated value
   */
  public ByteTLV(byte value) {
    this.value = value;
  }

  /**
   * Returns the byte-value encapsulated by this TLV.
   *
   * @return The encapsulated byte-value
   */
  public byte getByte() {
    return (this.value);
  }

  /* (non-Javadoc)
   * @see de.robind.swt.protocol.tlv.TLV#readFromBuffer(org.jboss.netty.buffer.ChannelBuffer)
   */
  @Override
  protected void readFromBuffer(ChannelBuffer buffer)
      throws IndexOutOfBoundsException, SWTProtocolException {

    this.value = buffer.readByte();
  }

  /* (non-Javadoc)
   * @see de.robind.swt.protocol.tlv.TLV#writeToBuffer(org.jboss.netty.buffer.ChannelBuffer)
   */
  @Override
  protected void writeToBuffer(ChannelBuffer buffer)
      throws SWTProtocolException {

    buffer.writeByte(this.value);
  }
}
