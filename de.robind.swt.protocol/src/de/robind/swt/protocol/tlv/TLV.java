package de.robind.swt.protocol.tlv;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import de.robind.swt.protocol.SWTProtocolException;

/**
 * A TLV (<u>T</u>ype-<u>L</u>ength-<u>V</u>alue) data-structure.
 * <p>
 * This is the abstract base-class. Concrete implementations needs to implement
 * {@link #readFromBuffer(ChannelBuffer)} and
 * {@link #writeToBuffer(ChannelBuffer)} to be able to encode/decode data into/
 * from the TLV.
 * <p>
 * {@link #read(ChannelBuffer)} is used to decode byte-data into a concrete
 * {@link TLV}-structure. The {@link #write(TLV, ChannelBuffer)}-method is used
 * to encode the TLV back into a byte-stream.
 *
 * @author Robin Doer
 */
public abstract class TLV {
  /**
   * A TLV representing a {@link String}.
   * @see StringTLV
   */
  static final byte TYPE_STRING = 1;

  /**
   * A TLV representing an {@link Integer}.
   * @see IntTLV
   */
  static final byte TYPE_INT = 2;

  /**
   * A TLV representing an {@link Byte}.
   * @see ByteTLV
   */
  static final byte TYPE_BYTE = 3;

  /**
   * Reads a {@link TLV}-structure from the given {@link ChannelBuffer}.
   * <p>
   * If not enough data are avaiable to read a complete TLV-structure,
   * <code>null</code> is returned.
   *
   * @param buffer The source-buffer. Contains byte-data, which are transformed
   *               into the {@link TLV}.
   * @return The extracted TLV. If not enough data are available to read a
   *         complete TLV, <code>null</code> is returned.
   * @throws SWTProtocolException if decoding of the TLV failed
   * @throws NullPointerException if buffer id <code>null</code>
   */
  public static TLV read(ChannelBuffer buffer) throws SWTProtocolException {
    if (buffer == null) {
      throw new NullPointerException("buffer cannot be null");
    }

    buffer.markReaderIndex();

    if (buffer.readableBytes() < 3) {
      // Not enough data available to read complete header
      return (null);
    }

    // Read type and length of TLV
    byte type = buffer.readByte();
    short length = buffer.readShort();

    if (length < 0) {
      buffer.resetReaderIndex();
      throw new SWTProtocolException("TLV-length cannot be negative");
    }

    if (buffer.readableBytes() < length) {
      // Not enough data available to read the value of the TLV-structure
      buffer.resetReaderIndex();
      return (null);
    }

    TLV tlv = type2tlv(type);

    try {
      ChannelBuffer valueBuffer = buffer.slice(3, length);
      tlv.readFromBuffer(valueBuffer);

      if (valueBuffer.readable()) {
        buffer.resetReaderIndex();
        throw new SWTProtocolException("Underflow detected");
      }
    } catch (IndexOutOfBoundsException e) {
      buffer.resetReaderIndex();
      throw new SWTProtocolException("Overflow detected", e);
    }

    return (tlv);
  }

  /**
   * Writes a {@link TLV}-structure into the given {@link ChannelBuffer}.
   *
   * @param tlv The source TLV-structure
   * @param buffer The destination buffer
   * @throws IndexOutOfBoundsException if the buffer is not big enough to store
   *         all the data
   * @throws NullPointerException if one of the arguments are <code>null</code>
   * @throws SWTProtocolException if encoding failed
   */
  public static void write(TLV tlv, ChannelBuffer buffer)
      throws IndexOutOfBoundsException, NullPointerException,
             SWTProtocolException {

    if (tlv == null) {
      throw new NullPointerException("tlv cannot be null");
    }

    if (buffer == null) {
      throw new NullPointerException("buffer cannot be null");
    }

    // Read the value from the tlv
    ChannelBuffer valueBuffer = ChannelBuffers.dynamicBuffer();
    tlv.writeToBuffer(valueBuffer);

    // Create the complete data-structure
    buffer.ensureWritableBytes(3 + valueBuffer.readableBytes());
    buffer.writeByte(tlv2type(tlv));
    buffer.writeShort(valueBuffer.readableBytes());
    buffer.writeBytes(valueBuffer);
  }

  /**
   * Decodes the value of the TLV-structure into the concrete
   * {@link TLV}-implementation.
   *
   * @param buffer The source-buffer contains the value of the TLV. This needs
   *               to be decoded.
   * @throws SWTProtocolException if decoding failed
   */
  protected abstract void readFromBuffer(ChannelBuffer buffer)
      throws IndexOutOfBoundsException, SWTProtocolException;

  /**
   * Encodes the concrete {@link TLV}-structure into the given
   * {@link ChannelBuffer}.
   *
   * @param buffer The destination-buffer where the byte-data are stored
   * @throws SWTProtocolException if encoding failed
   */
  protected abstract void writeToBuffer(ChannelBuffer buffer)
      throws SWTProtocolException;

  /**
   * Converts the given tlv into the related type.
   * <p>
   * This is one of the <code>TYPE_*</code>-constants.
   *
   * @param tlv The source tlv
   * @return The related <code>TYPE_*</code>-constant
   * @throws SWTProtocolException if no constant is defined for the tlv.
   */
  private static byte tlv2type(TLV tlv) throws SWTProtocolException {
    if (tlv instanceof StringTLV) {
      return (TYPE_STRING);
    } else if (tlv instanceof IntTLV) {
      return (TYPE_INT);
    } else if (tlv instanceof ByteTLV) {
      return (TYPE_BYTE);
    } else {
      throw new SWTProtocolException(
          "Unsupported TLV of class " + tlv.getClass().getName());
    }
  }

  /**
   * Creates a concrete {@link TLV} from the given type.
   *
   * @param type The TLV-type. This is one of the <code>TYPE_*</code>-constants.
   * @return The related TLV-object.
   * @throws SWTProtocolException if no mapping exists between type and object.
   */
  private static TLV type2tlv(byte type) throws SWTProtocolException {
    switch (type) {
      case TYPE_STRING: return (new StringTLV());
      case TYPE_INT   : return (new IntTLV());
      case TYPE_BYTE  : return (new ByteTLV());
      default: throw new SWTProtocolException("Unsupported TLV-type: " + type);
    }
  }
}
