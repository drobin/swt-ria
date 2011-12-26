package de.robind.swt.protocol.tlv;

import org.jboss.netty.buffer.ChannelBuffer;

import de.robind.swt.msg.SWTObjectId;
import de.robind.swt.protocol.SWTProtocolException;

/**
 * A TLV representing an {@link Integer}.
 *
 * @author Robin Doer
 */
public class SwtObjTLV extends TLV {
  /**
   * The encapsulated value.
   */
  private SWTObjectId value;

  /**
   * Make std-c'tor not accessible from outside.
   */
  SwtObjTLV() {
  }

  /**
   * Creates a new {@link SwtObjTLV}-instance.
   *
   * @param value The encapsulated value
   */
  public SwtObjTLV(SWTObjectId value) {
    this.value = value;
  }

  /**
   * Returns the id-value encapsulated by this TLV.
   *
   * @return The encapsulated id-value
   */
  public SWTObjectId getObjId() {
    return (this.value);
  }

  /* (non-Javadoc)
   * @see de.robind.swt.protocol.tlv.TLV#readFromBuffer(org.jboss.netty.buffer.ChannelBuffer)
   */
  @Override
  protected void readFromBuffer(ChannelBuffer buffer)
      throws IndexOutOfBoundsException, SWTProtocolException {

    int id = buffer.readInt();
    if (id == -1) {
      this.value = SWTObjectId.undefined();
    } else {
      this.value = new SWTObjectId(id);
    }
  }

  /* (non-Javadoc)
   * @see de.robind.swt.protocol.tlv.TLV#writeToBuffer(org.jboss.netty.buffer.ChannelBuffer)
   */
  @Override
  protected void writeToBuffer(ChannelBuffer buffer)
      throws SWTProtocolException {

    if (this.value.isValid()) {
      buffer.writeInt(this.value.getId());
    } else {
      buffer.writeInt(-1);
    }
  }
}
