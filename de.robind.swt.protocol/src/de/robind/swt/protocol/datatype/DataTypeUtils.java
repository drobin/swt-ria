package de.robind.swt.protocol.datatype;

import org.jboss.netty.buffer.ChannelBuffer;

import de.robind.swt.protocol.SWTProtocolException;

/**
 * Internal utilities for the datatype encoding/decoding.
 *
 * @author Robin Doer
 */
class DataTypeUtils {
  /**
   * A string-type.
   */
  static final byte DT_STRING = 1;

  /**
   * An integer-type.
   */
  static final byte DT_INT = 2;

  /**
   * A byte-type.
   */
  static final byte DT_BYTE = 3;

  /**
   * A boolean-type.
   */
  static final byte DT_BOOL = 4;

  /**
   * A null-type.
   */
  static final byte DT_NULL = 5;

  /**
   * No flags are set.
   */
  static final byte FLAG_NONE = 0x0;

  /**
   * Flags shows that an array is encoded
   */
  static final byte FLAG_ARRAY = 0x1;

  /**
   * Reads the type-flag from the <code>buffer</code> and checks it against the
   * <code>expected</code> type.
   *
   * @param buffer the source buffer
   * @param expected the expected type-flag
   * @throws SWTProtocolException if the type read from the buffer does not
   *         match with the expected typed
   */
  static void fetchAndCheckType(ChannelBuffer buffer, byte expected)
      throws SWTProtocolException {

    byte type = buffer.readByte();

    if (type != expected) {
      throw new SWTProtocolException(
          "Unexpected type read from buffer. Expected " + expected +
          ", but read " + type);
    }
  }

  /**
   * Reads the flags from the <code>buffer</code> and checks it against the
   * <code>expected</code> flags.
   *
   * @param buffer the source buffer
   * @param expected the expected flags-value
   * @throws SWTProtocolException if the flags read from the buffer does not
   *         match with the expected flags
   */
  static void fetchAndCheckFlags(ChannelBuffer buffer, byte expected)
      throws SWTProtocolException {

    byte flags = buffer.readByte();

    if (flags != expected) {
      throw new SWTProtocolException(
          "Unexpected flags read from buffer. Expected " + expected +
          ", but read " + flags);
    }
  }
}
