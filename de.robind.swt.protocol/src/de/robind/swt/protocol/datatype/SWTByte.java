package de.robind.swt.protocol.datatype;

import static de.robind.swt.protocol.datatype.DataTypeUtils.DT_BYTE;
import static de.robind.swt.protocol.datatype.DataTypeUtils.FLAG_NONE;
import static de.robind.swt.protocol.datatype.DataTypeUtils.fetchAndCheckFlags;
import static de.robind.swt.protocol.datatype.DataTypeUtils.fetchAndCheckType;

import org.jboss.netty.buffer.ChannelBuffer;

import de.robind.swt.protocol.SWTProtocolException;

/**
 * Utilitiy-methods to encode/decode an {@link Byte} from a
 * {@link ChannelBuffer}.
 *
 * @author Robin Doer
 */
public class SWTByte {
  /**
   * Reads a byte from the buffer.
   *
   * @param buffer The source buffer
   * @return The decoded byte-value
   * @throws NullPointerException if <code>buffer</code> is <code>null</code>
   * @throws IndexOutOfBoundsException if not enough data are available in
   *         <code>buffer</code> to read the integer
   * @throws SWTProtocolException if an decoding-error occured
   */
  public static byte readByte(ChannelBuffer buffer)
      throws NullPointerException, IndexOutOfBoundsException,
             SWTProtocolException {

    if (buffer == null) {
      throw new NullPointerException("buffer cannot be null");
    }

    fetchAndCheckType(buffer, DT_BYTE);
    fetchAndCheckFlags(buffer, FLAG_NONE);

    return (buffer.readByte());
  }

  /**
   * Writes a byte back into the buffer.
   *
   * @param buffer The destination buffer
   * @param value The byte to be encoded
   * @throws NullPointerException of <code>buffer</code> is <code>null</code>
   * @throws IndexOutOfBoundsException if not enough space is available in
   *         <code>buffer</code> to encode the whole <code>value</code>
   */
  public static void writeByte(ChannelBuffer buffer, byte value)
      throws NullPointerException, IndexOutOfBoundsException {

    if (buffer == null) {
      throw new NullPointerException("buffer cannot be null");
    }

    buffer.writeByte(DT_BYTE);
    buffer.writeByte(FLAG_NONE);
    buffer.writeByte(value);
  }
}
