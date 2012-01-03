package de.robind.swt.protocol.datatype;

import static de.robind.swt.protocol.datatype.DataTypeUtils.DT_BOOL;
import static de.robind.swt.protocol.datatype.DataTypeUtils.FLAG_NONE;
import static de.robind.swt.protocol.datatype.DataTypeUtils.fetchAndCheckFlags;
import static de.robind.swt.protocol.datatype.DataTypeUtils.fetchAndCheckType;

import org.jboss.netty.buffer.ChannelBuffer;

import de.robind.swt.protocol.SWTProtocolException;
/**
 * /**
 * Utilitiy-methods to encode/decode a {@link Boolean} from a
 * {@link ChannelBuffer}.
 *
 * @author Robin Doer
 */
public class SWTBoolean {
  /**
   * Reads a boolean from the buffer.
   *
   * @param buffer The source buffer
   * @return The decoded boolean-value
   * @throws NullPointerException if <code>buffer</code> is <code>null</code>
   * @throws IndexOutOfBoundsException if not enough data are available in
   *         <code>buffer</code> to read the boolean
   * @throws SWTProtocolException if an decoding-error occured
   */
  public static boolean readBoolean(ChannelBuffer buffer)
      throws NullPointerException, IndexOutOfBoundsException,
             SWTProtocolException {

    if (buffer == null) {
      throw new NullPointerException("buffer cannot be null");
    }

    fetchAndCheckType(buffer, DT_BOOL);
    fetchAndCheckFlags(buffer, FLAG_NONE);

    byte byteValue = buffer.readByte();
    return (byteValue != 0);
  }

  /**
   * Writes a boolean back into the buffer.
   *
   * @param buffer The destination buffer
   * @param value The boolean to be encoded
   * @throws NullPointerException of <code>buffer</code> is <code>null</code>
   * @throws IndexOutOfBoundsException if not enough space is available in
   *         <code>buffer</code> to encode the whole <code>value</code>
   */
  public static void writeBoolean(ChannelBuffer buffer, boolean value)
      throws NullPointerException, IndexOutOfBoundsException {

    if (buffer == null) {
      throw new NullPointerException("buffer cannot be null");
    }

    buffer.writeByte(DT_BOOL);
    buffer.writeByte(FLAG_NONE);
    buffer.writeByte(value ? 1 : 0);
  }
}
