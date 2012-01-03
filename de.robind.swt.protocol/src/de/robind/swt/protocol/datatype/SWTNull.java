package de.robind.swt.protocol.datatype;

import static de.robind.swt.protocol.datatype.DataTypeUtils.DT_NULL;
import static de.robind.swt.protocol.datatype.DataTypeUtils.FLAG_NONE;
import static de.robind.swt.protocol.datatype.DataTypeUtils.fetchAndCheckFlags;
import static de.robind.swt.protocol.datatype.DataTypeUtils.fetchAndCheckType;

import org.jboss.netty.buffer.ChannelBuffer;

import de.robind.swt.protocol.SWTProtocolException;

/**
 * Utilitiy-methods to encode/decode a <code>null</code> from a
 * {@link ChannelBuffer}.
 *
 * @author Robin Doer
 */
public class SWTNull {
  /**
   * Reads a <code>null</code> from the buffer.
   *
   * @param buffer The source buffer
   * @return The decoded byte-value
   * @throws NullPointerException if <code>buffer</code> is <code>null</code>
   * @throws IndexOutOfBoundsException if not enough data are available in
   *         <code>buffer</code> to read the <code>null</code>
   * @throws SWTProtocolException if an decoding-error occured
   */
  public static void readNull(ChannelBuffer buffer)
      throws NullPointerException, IndexOutOfBoundsException,
             SWTProtocolException {

    if (buffer == null) {
      throw new NullPointerException("buffer cannot be null");
    }

    fetchAndCheckType(buffer, DT_NULL);
    fetchAndCheckFlags(buffer, FLAG_NONE);
  }

  /**
   * Writes a <code>null</code> back into the buffer.
   *
   * @param buffer The destination buffer
   * @throws NullPointerException of <code>buffer</code> is <code>null</code>
   * @throws IndexOutOfBoundsException if not enough space is available in
   *         <code>buffer</code> to encode the <code>null</code>
   */
  public static void writeNull(ChannelBuffer buffer)
      throws NullPointerException, IndexOutOfBoundsException {

    if (buffer == null) {
      throw new NullPointerException("buffer cannot be null");
    }

    buffer.writeByte(DT_NULL);
    buffer.writeByte(FLAG_NONE);
  }
}
