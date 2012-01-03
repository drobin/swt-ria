package de.robind.swt.protocol.datatype;

import static de.robind.swt.protocol.datatype.DataTypeUtils.DT_INT;
import static de.robind.swt.protocol.datatype.DataTypeUtils.FLAG_ARRAY;
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
   * Reads an integer-array from the buffer.
   *
   * @param buffer the source buffer
   * @return the decoded integer-array
   * @throws NullPointerException if <code>buffer</code> is <code>null</code>
   * @throws IndexOutOfBoundsException if not enough data are available in
   *         <code>buffer</code> to read the integer-array
   * @throws SWTProtocolException if an decoding-error occured
   */
  public static int[] readIntegerArray(ChannelBuffer buffer)
      throws NullPointerException, IndexOutOfBoundsException,
             SWTProtocolException {

    if (buffer == null) {
      throw new NullPointerException("buffer cannot be null");
    }

    fetchAndCheckType(buffer, DT_INT);
    fetchAndCheckFlags(buffer, FLAG_ARRAY);

    short length = buffer.readShort();
    if (length < 0) {
      throw new SWTProtocolException("Length of array cannot be negative");
    }

    int result[] = new int[length];
    for (int i = 0; i < length; i++) {
      result[i] = buffer.readInt();
    }

    return (result);
  }

  /**
   * Writes an integer back into the buffer.
   *
   * @param buffer The destination buffer
   * @param integer The integer to be encoded
   * @throws NullPointerException if <code>buffer</code> is <code>null</code>
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

  /**
   * Writes an integer-array back into the buffer.
   *
   * @param buffer the destination buffer
   * @param array the integer-array to be encoded
   * @throws NullPointerException if <code>buffer</code> is <code>null</code>
   * @throws IndexOutOfBoundsException if not enough space is available in
   *         <code>buffer</code> to encode the whole array
   * @throws SWTProtocolException if an encoding-error occured
   */
  public static void writeIntegerArray(ChannelBuffer buffer, int array[])
      throws NullPointerException, IndexOutOfBoundsException,
             SWTProtocolException {

    if (buffer == null) {
      throw new NullPointerException("buffer cannot be null");
    }

    if (array.length > Short.MAX_VALUE) {
      throw new SWTProtocolException(
          "Length of array cannot be greater than " + Short.MAX_VALUE);
    }

    buffer.writeByte(DT_INT);
    buffer.writeByte(FLAG_ARRAY);
    buffer.writeShort(array.length);

    for (int value: array) {
      buffer.writeInt(value);
    }
  }
}
