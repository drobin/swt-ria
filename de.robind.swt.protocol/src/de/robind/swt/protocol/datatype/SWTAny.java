package de.robind.swt.protocol.datatype;

import static de.robind.swt.protocol.datatype.DataTypeUtils.DT_BOOL;
import static de.robind.swt.protocol.datatype.DataTypeUtils.DT_BYTE;
import static de.robind.swt.protocol.datatype.DataTypeUtils.DT_INT;
import static de.robind.swt.protocol.datatype.DataTypeUtils.DT_MAP;
import static de.robind.swt.protocol.datatype.DataTypeUtils.DT_NULL;
import static de.robind.swt.protocol.datatype.DataTypeUtils.DT_STRING;
import static de.robind.swt.protocol.datatype.DataTypeUtils.DT_SWTOBJ;
import static de.robind.swt.protocol.datatype.DataTypeUtils.FLAG_ARRAY;

import java.util.Map;

import org.jboss.netty.buffer.ChannelBuffer;

import de.robind.swt.msg.SWTObjectId;
import de.robind.swt.protocol.SWTProtocolException;

/**
 * Utility to read an arbitrary datattype from a {@link ChannelBuffer}.
 *
 * @author Robin Doer
 */
public class SWTAny {
  /**
   * Reads (nearly) arbitrary data from the buffer.
   * <p>
   * A decoder must be implemented by this package.
   *
   * @param buffer the source buffer
   * @return the decoded value
   * @throws NullPointerException if <code>buffer</code> is <code>null</code>
   * @throws IndexOutOfBoundsException if not enough data are available in
   *         <code>buffer</code> to read the value
   * @throws SWTProtocolException if an decoding-error occured
   */
  public static Object readAny(ChannelBuffer buffer)
      throws NullPointerException, IndexOutOfBoundsException,
             SWTProtocolException {

    if (buffer == null) {
      throw new NullPointerException("buffer cannot be null");
    }

    if (buffer.readableBytes() < 2) {
      throw new IndexOutOfBoundsException();
    }

    byte type = buffer.getByte(buffer.readerIndex());
    byte flags = buffer.getByte(buffer.readerIndex() + 1);

    switch (type) {
      case DT_BOOL:   return (SWTBoolean.readBoolean(buffer));
      case DT_BYTE:   return (SWTByte.readByte(buffer));
      case DT_INT:    {
        if ((flags & FLAG_ARRAY) > 0) {
            return (SWTInteger.readIntegerArray(buffer));
        } else {
          return (SWTInteger.readInteger(buffer));
        }
      }
      case DT_MAP:    return (SWTMap.readMap(buffer));
      case DT_NULL:   {
        SWTNull.readNull(buffer);
        return (null);
      }
      case DT_STRING: {
        if ((flags & FLAG_ARRAY) > 0) {
          return (SWTString.readStringArray(buffer));
        } else {
          return (SWTString.readString(buffer));
        }
      }
      case DT_SWTOBJ: return (SWTObj.readObjId(buffer));
    }

    throw new SWTProtocolException(
        "Could not decode the type/flag-combination " + type + "/" + flags);
  }

  /**
   * Writes (nearly) arbitrary data back into the buffer.
   * <p>
   * An encoder must be implemented by this package.
   *
   * @param buffer the destination buffer
   * @param value the value to be encoded
   * @throws NullPointerException if <code>buffer</code> is <code>null</code>
   * @throws IndexOutOfBoundsException if not enough space is available in
   *         <code>buffer</code> to encode the whole <code>value</code>
   * @throws SWTProtocolException if an encoding-eror occured
   */
  public static void writeAny(ChannelBuffer buffer, Object value)
      throws NullPointerException, IndexOutOfBoundsException,
             SWTProtocolException {

    if (buffer == null) {
      throw new NullPointerException("buffer cannot be null");
    }

    if (value != null) {
      if (value.getClass().isArray()) {
        writeArray(buffer, value);
      } else {
        writeSimple(buffer, value);
      }
    } else {
      SWTNull.writeNull(buffer);
    }
  }

  @SuppressWarnings("unchecked")
  private static void writeSimple(ChannelBuffer buffer, Object value)
      throws SWTProtocolException {

    if (value instanceof Boolean) {
      SWTBoolean.writeBoolean(buffer, (Boolean)value);
    } else if (value instanceof Byte) {
      SWTByte.writeByte(buffer, (Byte)value);
    } else if (value instanceof Integer) {
      SWTInteger.writeInteger(buffer, (Integer)value);
    } else if (value instanceof Map) {
      SWTMap.writeMap(buffer, (Map<String, Object>)value);
    } else if (value instanceof String) {
      SWTString.writeString(buffer, (String)value);
    } else if (value instanceof SWTObjectId) {
      SWTObj.writeIObjId(buffer, (SWTObjectId)value);
    } else {
      throw new SWTProtocolException(
          "Could not write simple datatype of class " +
          value.getClass().getName());
    }
  }

  private static void writeArray(ChannelBuffer buffer, Object value)
      throws SWTProtocolException {

    if (value instanceof int[]) {
      SWTInteger.writeIntegerArray(buffer, (int[])value);
    } else if (value instanceof String[]) {
      SWTString.writeStringArray(buffer, (String[])value);
    } else {
      throw new SWTProtocolException(
          "Could not write an array datatype of class " +
          value.getClass().getComponentType().getName());
    }
  }
}
