package de.robind.swt.protocol.tlv;

import org.jboss.netty.buffer.ChannelBuffer;

import de.robind.swt.protocol.SWTProtocolException;

/**
 * A TLV representing a {@link Boolean}.
 *
 * @author Robin Doer
 */
public class BoolTLV extends TLV {
  /**
   * The encapsulated value.
   */
  private boolean value;

  /**
   * Make std-c'tor not accessible from outside.
   */
  BoolTLV() {
  }

  /**
   * Creates a new {@link BoolTLV}-instance
   *
   * @param value The encapsulated value
   */
  public BoolTLV(boolean value) {
    this.value = value;
  }

  /**
   * Returns the boolean-value encapsulated by this TLV.
   *
   * @return The encapsulated boolean-value
   */
  public boolean getBool() {
    return (this.value);
  }

  /* (non-Javadoc)
   * @see de.robind.swt.protocol.tlv.TLV#readFromBuffer(org.jboss.netty.buffer.ChannelBuffer)
   */
  @Override
  protected void readFromBuffer(ChannelBuffer buffer)
      throws IndexOutOfBoundsException, SWTProtocolException {

    this.value = (buffer.readByte() == 1);
  }

  /* (non-Javadoc)
   * @see de.robind.swt.protocol.tlv.TLV#writeToBuffer(org.jboss.netty.buffer.ChannelBuffer)
   */
  @Override
  protected void writeToBuffer(ChannelBuffer buffer)
      throws SWTProtocolException {

    buffer.writeByte(this.value ? 1 : 0);
  }
}
