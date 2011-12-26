package de.robind.swt.protocol.tlv;

import org.jboss.netty.buffer.ChannelBuffer;

import de.robind.swt.protocol.SWTProtocolException;

/**
 * A TLV representing an {@link Integer}.
 *
 * @author Robin Doer
 */
public class IntTLV extends TLV {
  /**
   * The encapsulated value.
   */
  private int value;

  /**
   * Make std-c'tor not accessible from outside.
   */
  IntTLV() {
  }

  /**
   * Creates a new {@link IntTLV}-instance.
   *
   * @param value The encapsulated value
   */
  public IntTLV(int value) {
    this.value = value;
  }

  /**
   * Returns the int-value encapsulated by this TLV.
   *
   * @return The encapsulated int-value
   */
  public int getInt() {
    return (this.value);
  }

  /* (non-Javadoc)
   * @see de.robind.swt.protocol.tlv.TLV#readFromBuffer(org.jboss.netty.buffer.ChannelBuffer)
   */
  @Override
  protected void readFromBuffer(ChannelBuffer buffer)
      throws IndexOutOfBoundsException, SWTProtocolException {

    this.value = buffer.readInt();
  }

  /* (non-Javadoc)
   * @see de.robind.swt.protocol.tlv.TLV#writeToBuffer(org.jboss.netty.buffer.ChannelBuffer)
   */
  @Override
  protected void writeToBuffer(ChannelBuffer buffer)
      throws SWTProtocolException {

    buffer.writeInt(this.value);
  }
}
