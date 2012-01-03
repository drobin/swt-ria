package de.robind.swt.protocol.datatype;

import static de.robind.swt.protocol.datatype.DataTypeUtils.DT_INT;
import static de.robind.swt.protocol.datatype.DataTypeUtils.FLAG_NONE;
import static de.robind.swt.protocol.datatype.DataTypeUtils.fetchAndCheckFlags;
import static de.robind.swt.protocol.datatype.DataTypeUtils.fetchAndCheckType;

import org.jboss.netty.buffer.ChannelBuffer;

import de.robind.swt.protocol.SWTProtocolException;

/**
 * Utilitiy-methods to encode/decode an {@link Integer} from a
 * {@link ChannelBuffer}.
 *
 * @author Robin Doer
 */
public class SWTInteger {
  /**
   * Reads an integer from the buffer.
   *
   * @param buffer The source buffer
   * @return The decoded integer-value
   * @throws NullPointerException if <code>buffer</code> is <code>null</code>
   * @throws IndexOutOfBoundsException if not enough data are available in
   *         <code>buffer</code> to read the integer
   * @throws SWTProtocolException if an decoding-error occured
   */
  public static int readInteger(ChannelBuffer buffer)
      throws NullPointerException, IndexOutOfBoundsException,
             SWTProtocolException {

    if (buffer == null) {
      throw new NullPointerException("buffer cannot be null");
    }

    fetchAndCheckType(buffer, DT_INT);
    fetchAndCheckFlags(buffer, FLAG_NONE);

    return (buffer.readInt());
  }

  /**
   * Writes an integer back into the buffer.
   *
   * @param buffer The destination buffer
   * @param integer The integer to be encoded
   * @throws NullPointerException of <code>buffer</code> is <code>null</code>
   * @throws IndexOutOfBoundsException if not enough space is available in
   *         <code>buffer</code> to encode the whole <code>integer</code>
   */
  public static void writeInteger(ChannelBuffer buffer, int integer)
      throws NullPointerException, IndexOutOfBoundsException {

    if (buffer == null) {
      throw new NullPointerException("buffer cannot be null");
    }

    buffer.writeByte(DT_INT);
    buffer.writeByte(FLAG_NONE);
    buffer.writeInt(integer);
  }
}
