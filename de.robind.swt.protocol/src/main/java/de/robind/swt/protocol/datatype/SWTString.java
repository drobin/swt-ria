package de.robind.swt.protocol.datatype;

import static de.robind.swt.protocol.datatype.DataTypeUtils.DT_STRING;
import static de.robind.swt.protocol.datatype.DataTypeUtils.FLAG_ARRAY;
import static de.robind.swt.protocol.datatype.DataTypeUtils.FLAG_NONE;
import static de.robind.swt.protocol.datatype.DataTypeUtils.fetchAndCheckFlags;
import static de.robind.swt.protocol.datatype.DataTypeUtils.fetchAndCheckType;

import java.io.UnsupportedEncodingException;

import org.jboss.netty.buffer.ChannelBuffer;

import de.robind.swt.protocol.SWTProtocolException;

/**
 * Utilitiy-methods to encode/decode a string from a {@link ChannelBuffer}.
 *
 * @author Robin Doer
 */
public class SWTString {
  /**
   * Reads a string from the buffer.
   *
   * @param buffer The source buffer
   * @return the decoded message
   * @throws NullPointerException if <code>buffer</code> is <code>null</code>
   * @throws IndexOutOfBoundsException if not enough data are available in
   *         <code>buffer</code> to read the string
   * @throws SWTProtocolException if an decoding-error occured
   */
  public static String readString(ChannelBuffer buffer)
      throws NullPointerException, IndexOutOfBoundsException,
             SWTProtocolException {

    if (buffer == null) {
      throw new NullPointerException("buffer cannot be null");
    }

    fetchAndCheckType(buffer, DT_STRING);
    fetchAndCheckFlags(buffer, FLAG_NONE);

    short length = buffer.readShort();
    if (length < 0) {
      throw new SWTProtocolException("Length of string cannot be negative");
    }

    byte byteData[] = new byte[length];
    buffer.readBytes(byteData);

    try {
      return (new String(byteData, "US-ASCII"));
    } catch (UnsupportedEncodingException e) {
      throw new SWTProtocolException(e);
    }
  }

  /**
   * Reads a string-array from the buffer.
   *
   * @param buffer The source buffer
   * @return the decoded array
   * @throws NullPointerException if <code>buffer</code> is <code>null</code>
   * @throws IndexOutOfBoundsException if not enough data are available in
   *         <code>buffer</code> to read the array
   * @throws SWTProtocolException if an decoding-error occured
   */
  public static String[] readStringArray(ChannelBuffer buffer)
      throws NullPointerException, IndexOutOfBoundsException,
             SWTProtocolException {

    if (buffer == null) {
      throw new NullPointerException("buffer cannot be null");
    }

    fetchAndCheckType(buffer, DT_STRING);
    fetchAndCheckFlags(buffer, FLAG_ARRAY);

    short length = buffer.readShort();
    if (length < 0) {
      throw new SWTProtocolException("Length of array cannot be negative");
    }

    String result[] = new String[length];
    for (int i = 0; i < length; i++) {
      short stringLength = buffer.readShort();
      if (stringLength < 0) {
        throw new SWTProtocolException("Length of string cannot be negative");
      }

      try {
        byte stringData[] = new byte[stringLength];
        buffer.readBytes(stringData);

        result[i] = new String(stringData, "US-ASCII");
      } catch (UnsupportedEncodingException e) {
        throw new SWTProtocolException(e);
      }
    }

    return (result);
  }

  /**
   * Writes a string back into the buffer.
   *
   * @param buffer The destination buffer
   * @param string The string to be encoded
   * @throws NullPointerException if one of the arguments are <code>null</code>
   * @throws IndexOutOfBoundsException if not enough space is available in
   *         <code>buffer</code> to encode the <code>string</code>
   * @throws SWTProtocolException if an encoding-error occured
   */
  public static void writeString(ChannelBuffer buffer, String string)
      throws NullPointerException, IndexOutOfBoundsException,
             SWTProtocolException {

    if (buffer == null) {
      throw new NullPointerException("buffer cannot be null");
    }

    if (string == null) {
      throw new NullPointerException("string cannot be null");
    }

    if (string.length() > Short.MAX_VALUE) {
      throw new SWTProtocolException(
          "The length of the string cannot be greater than " + Short.MAX_VALUE);
    }

    try {
      buffer.writeByte(DT_STRING);
      buffer.writeByte(FLAG_NONE);
      buffer.writeShort(string.length());
      buffer.writeBytes(string.getBytes("US-ASCII"));
    } catch (UnsupportedEncodingException e) {
      throw new SWTProtocolException(e);
    }
  }

  /**
   * Writes a string-array back into the buffer.
   *
   * @param buffer The destination buffer
   * @param array The array to be encoded
   * @throws NullPointerException if one of the arguments are <code>null</code>
   *         or one one the array-elements are <code>null</code>
   * @throws IndexOutOfBoundsException if not enough space is available in
   *         <code>buffer</code> to encode the array
   * @throws SWTProtocolException if an encoding-error occured
   */
  public static void writeStringArray(ChannelBuffer buffer, String array[])
      throws NullPointerException, IndexOutOfBoundsException,
             SWTProtocolException {

    if (buffer == null) {
      throw new NullPointerException("buffer cannot be null");
    }

    if (array == null) {
      throw new NullPointerException("array cannot be null");
    }

    for (String elem: array) {
      if (elem == null) {
        throw new NullPointerException("Array-element cannot be null");
      }

      if (elem.length() > Short.MAX_VALUE) {
        throw new SWTProtocolException(
            "The length of the string cannot be greater than " + Short.MAX_VALUE);
      }
    }

    if (array.length > Short.MAX_VALUE) {
      throw new SWTProtocolException(
          "Length of array cannot be greater than " + Short.MAX_VALUE);
    }

    buffer.writeByte(DT_STRING);
    buffer.writeByte(FLAG_ARRAY);
    buffer.writeShort(array.length);

    for (String elem: array) {
      try {
        buffer.writeShort(elem.length());
        buffer.writeBytes(elem.getBytes("US-ASCII"));
      } catch (UnsupportedEncodingException e) {
        throw new SWTProtocolException(e);
      }
    }
  }
}
