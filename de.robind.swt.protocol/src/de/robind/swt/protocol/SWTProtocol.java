package de.robind.swt.protocol;

import java.io.UnsupportedEncodingException;

import org.jboss.netty.buffer.ChannelBuffer;

import de.robind.swt.msg.SWTObjectId;

/**
 * Constants and values used by the protocol.
 *
 * @author Robin Doer
 *
 */
public class SWTProtocol {
  /**
   * The magic number describing the protocol.
   */
  public static final short MAGIC = 0x7601;

  /**
   * Operation: Create a new object
   */
  public static final byte OP_NEW = 0x01;

  /**
   * Operation: Call a remote method
   */
  public static final byte OP_CALL = 2;

  /**
   * Operation: register an event-handler
   */
  public static final byte OP_REG = 3;

  /**
   * A request-message.
   */
  public static final byte TYPE_REQ = 0x00;

  /**
   * A response-message
   */
  public static final byte TYPE_RSP = 0x01;

  /**
   * An exception-message.
   */
  public static final byte TYPE_EXC = 0x02;

  /**
   * An event-message
   */
  public static final byte TYPE_EVT = 0x03;

  /**
   * A string-argument
   */
  public static final byte ARG_STRING = 1;

  /**
   * An integer-argument.
   */
  public static final byte ARG_INT = 2;

  /**
   * A byte-argument.
   */
  public static final byte ARG_BYTE = 3;

  /**
   * A boolean-argument.
   */
  public static final byte ARG_BOOL = 4;

  /**
   * The argument is a {@link SWTObjectId}.
   */
  public static final byte ARG_SWTOBJ = 5;

  /**
   * Reads a string from the given buffer.
   * <p>
   * The string starts with a two-byte length, followed by the byte-data with
   * the previous specified length.
   *
   * @param buffer The source buffer
   * @return The decoded string
   * @throws SWTProtocolException if decoding has failed
   * @throws IndexOutOfBoundsException if not enough data are available in
   *         <code>buffer</code>.
   */
  public static String readString(ChannelBuffer buffer)
      throws SWTProtocolException, IndexOutOfBoundsException {

    short length = buffer.readShort();
    if (length < 0) {
      throw new SWTProtocolException(
          "String cannot have negative length (" + length + ")");
    }

    byte value[] = new byte[length];
    buffer.readBytes(value);

    try {
      return (new String(value, "US-ASCII"));
    } catch (UnsupportedEncodingException e) {
      throw new SWTProtocolException(e);
    }
  }

  /**
   * Encodes the given string into the given buffer.
   *
   * @param buffer The destination buffer
   * @param value The value to be encoded
   * @throws SWTProtocolException if encoding failed
   * @throws IndexOutOfBoundsException if the buffer is not big enough
   * @throws NullPointerException if one of the arguments are <code>null</code>
   */
  public static void writeString(ChannelBuffer buffer, String value)
      throws SWTProtocolException, IndexOutOfBoundsException,
             NullPointerException {

    if (buffer == null) {
      throw new NullPointerException("buffer cannot be null");
    }

    if (value == null) {
      throw new NullPointerException("value cannot be null");
    }

    if (value.length() > Short.MAX_VALUE) {
      throw new SWTProtocolException("value is too long: " + value.length());
    }

    buffer.ensureWritableBytes(2);
    buffer.writeShort(value.length());

    try {
      byte bytes[] = value.getBytes("US-ASCII");
      buffer.ensureWritableBytes(bytes.length);
      buffer.writeBytes(bytes);
    } catch (UnsupportedEncodingException e) {
      throw new SWTProtocolException(e);
    }
  }

  /**
   * Reads a boolean value from the given channel.
   *
   * @param buffer The source buffer
   * @return The decoded boolean value
   * @throws IndexOutOfBoundsException if not enough data are available in
   *         <code>buffer</code>
   * @throws NullPointerException if <code>buffer</code> is <code>null</code>
   */
  public static boolean readBoolean(ChannelBuffer buffer)
      throws IndexOutOfBoundsException, NullPointerException {

    if (buffer == null) {
      throw new NullPointerException("buffer cannot be null");
    }

    return (buffer.readByte() == 1);
  }

  /**
   * Writes a boolean value into the given buffer.
   *
   * @param buffer The destination buffer
   * @param value The value to be encoded
   * @throws IndexOutOfBoundsException if the buffer if not big enough
   * @throws NullPointerException if <code>buffer</code> is <code>null</code>
   */
  public static void writeBoolean(ChannelBuffer buffer, boolean value)
      throws IndexOutOfBoundsException, NullPointerException {

    if (buffer == null) {
      throw new NullPointerException("buffer cannot be null");
    }

    buffer.ensureWritableBytes(1);
    buffer.writeByte(value ? 1 : 0);
  }

  /**
   * Reads a {@link SWTObjectId} from the given channel.
   *
   * @param The source buffer
   * @return The decoded value
   * @throws IndexOutOfBoundsException if not enough data are available in
   *         <code>buffer</code>
   * @throws NullPointerException if <code>buffer</code> is <code>null</code>
   */
  public static SWTObjectId readSwtObjectId(ChannelBuffer buffer)
      throws IndexOutOfBoundsException, NullPointerException {

    if (buffer == null) {
      throw new NullPointerException("buffer cannot be null");
    }

    int objId = buffer.readInt();
    return (objId != -1 ? new SWTObjectId(objId) : SWTObjectId.undefined());
  }

  /**
   * Writes a {@link SWTObjectId} into the given buffer.
   *
   * @param buffer The destination buffer
   * @param value The value to be encoded
   * @throws IndexOutOfBoundsException if the buffer if not big enough
   * @throws NullPointerException if <code>buffer</code> or <code>value</code>
   *         are <code>null</code>
   */
  public static void writeSwtObjectId(ChannelBuffer buffer, SWTObjectId value)
      throws IndexOutOfBoundsException, NullPointerException {

    if (buffer == null) {
      throw new NullPointerException("buffer cannot be null");
    }

    if (value == null) {
      throw new NullPointerException("value cannot be null");
    }

    buffer.ensureWritableBytes(4);
    if (value.isValid()) {
      buffer.writeInt(value.getId());
    } else {
      buffer.writeInt(-1);
    }
  }

  /**
   * Reads an argument from the given channel.
   * <p>
   * The special case here is, that you don't know what kind of argument you
   * are reading. That's why the argument starts with one of the
   * <code>ARG_</code>-values, which defines the type of the argument.
   * Dedepending on the value the concrete argument is read and returned from
   * the channel and returned.
   *
   * @param buffer The source buffer
   * @return The argument
   * @throws SWTProtocolException if decoding has failed
   * @throws IndexOutOfBoundsException if not enough data are available
   */
  public static Object readArgument(ChannelBuffer buffer)
      throws SWTProtocolException, IndexOutOfBoundsException {

    byte type = buffer.readByte();
    switch (type) {
      case ARG_STRING: return (readString(buffer));
      case ARG_INT:    return (buffer.readInt());
      case ARG_BYTE:   return (buffer.readByte());
      case ARG_BOOL:   return (readBoolean(buffer));
      case ARG_SWTOBJ: return (readSwtObjectId(buffer));
      default: throw new SWTProtocolException("Invalid argument-type: " + type);
    }
  }

  /**
   * Writes an argument into the given stream.
   *
   * @param buffer The destination buffer.
   * @param value The argument to be encoded
   * @throws SWTProtocolException if encoding has failed
   * @throws IndexOutOfBoundsException if the bufer is not big enough
   * @throws NullPointerException if one of the arguments are <code>null</code>
   */
  public static void writeArgument(ChannelBuffer buffer, Object value)
      throws SWTProtocolException, IndexOutOfBoundsException,
             NullPointerException {

    if (buffer == null) {
      throw new NullPointerException("buffer cannot be null");
    }

    if (value == null) {
      throw new NullPointerException("value cannot be null");
    }

    if (value instanceof String) {
      buffer.ensureWritableBytes(1);
      buffer.writeByte(ARG_STRING);
      writeString(buffer, (String)value);
    } else if (value instanceof Integer) {
      buffer.ensureWritableBytes(5);
      buffer.writeByte(ARG_INT);
      buffer.writeInt((Integer)value);
    } else if (value instanceof Byte) {
      buffer.ensureWritableBytes(2);
      buffer.writeByte(ARG_BYTE);
      buffer.writeByte((Byte)value);
    } else if (value instanceof Boolean) {
      buffer.ensureWritableBytes(2);
      buffer.writeByte(ARG_BOOL);
      buffer.writeByte((Boolean)value ? 1 : 0);
    } else if (value instanceof SWTObjectId) {
      buffer.ensureWritableBytes(5);
      buffer.writeByte(ARG_SWTOBJ);
      writeSwtObjectId(buffer, (SWTObjectId)value);
    } else {
      throw new SWTProtocolException(
          "Invalid argument of type " + value.getClass().getName());
    }
  }
}
