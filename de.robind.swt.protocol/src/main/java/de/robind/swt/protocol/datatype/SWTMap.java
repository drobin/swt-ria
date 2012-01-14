package de.robind.swt.protocol.datatype;

import static de.robind.swt.protocol.datatype.DataTypeUtils.DT_MAP;
import static de.robind.swt.protocol.datatype.DataTypeUtils.FLAG_NONE;
import static de.robind.swt.protocol.datatype.DataTypeUtils.fetchAndCheckFlags;
import static de.robind.swt.protocol.datatype.DataTypeUtils.fetchAndCheckType;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.jboss.netty.buffer.ChannelBuffer;

import de.robind.swt.protocol.SWTProtocolException;

/**
 * Utilitiy-methods to encode/decode a {@link Map} from a
 * {@link ChannelBuffer}.
 *
 * @author Robin Doer
 */
public class SWTMap {
  /**
   * Reads a {@link Map} from the buffer.
   * <p>
   * This is a collection of mappings from a key to a (nearly) arbitrary value.
   * A decoder must be implemented by this package. See {@link SWTAny}.
   *
   * @param buffer The source buffer
   * @return The decoded map
   * @throws NullPointerException if <code>buffer</code> is <code>null</code>
   * @throws IndexOutOfBoundsException if not enough data are available in
   *         <code>buffer</code> to read the map
   * @throws SWTProtocolException if an decoding-error occured
   */
  public static Map<String, Object> readMap(ChannelBuffer buffer)
      throws NullPointerException, IndexOutOfBoundsException,
             SWTProtocolException {

    if (buffer == null) {
      throw new NullPointerException("buffer cannot be null");
    }

    fetchAndCheckType(buffer, DT_MAP);
    fetchAndCheckFlags(buffer, FLAG_NONE);

    byte numMappings = buffer.readByte();
    if (numMappings < 0) {
      throw new SWTProtocolException(
          "The number of mappings cannot be negative");
    }

    Map<String, Object> mappings = new HashMap<String, Object>();

    for (int i = 0; i < numMappings; i++) {
      // Read the key
      byte keyLength = buffer.readByte();

      if (keyLength < 1) {
        String text = (keyLength == 0) ?
            "The key cannot be empty" : "Length of key cannot be negative";
        throw new SWTProtocolException(text);
      }

      byte keyData[] = new byte[keyLength];
      buffer.readBytes(keyData);

      String key;

      try {
        key = new String(keyData, "US-ASCII");
      } catch (UnsupportedEncodingException e) {
        throw new SWTProtocolException(e);
      }

      // Read the value
      Object value = SWTAny.readAny(buffer);

      if (!mappings.containsKey(key)) {
        mappings.put(key, value);
      } else {
        throw new SWTProtocolException("Key " + key + " is not unique");
      }
    }

    return (mappings);
  }

  /**
   * Writes a map back into the buffer.
   *
   * @param buffer The destination buffer
   * @param map The map to be encoded
   * @throws NullPointerException if one of the arguments are <code>null</code>
   * @throws IndexOutOfBoundsException if not enough space is available in
   *         <code>buffer</code> to encode the <code>map</code>
   * @throws SWTProtocolException if an encoding-error occured
   */
  public static void writeMap(ChannelBuffer buffer, Map<String, Object> map)
      throws NullPointerException, IndexOutOfBoundsException,
             SWTProtocolException {

    if (buffer == null) {
      throw new NullPointerException("buffer cannot be null");
    }

    if (map == null) {
      throw new NullPointerException("map cannot be null");
    }

    if (map.size() > Byte.MAX_VALUE) {
      throw new SWTProtocolException(
          "Number of mappings cannot be greater than " + Byte.MAX_VALUE);
    }

    buffer.writeByte(DT_MAP);
    buffer.writeByte(FLAG_NONE);

    buffer.writeByte(map.size());

    for (String key: map.keySet()) {
      if (key.length() == 0) {
        throw new SWTProtocolException("Key cannot be empty");
      }

      if (key.length() > Byte.MAX_VALUE) {
        throw new SWTProtocolException(
            "Length of key cannot be greater than " + Byte.MAX_VALUE);
      }

      buffer.writeByte(key.length());

      try {
        buffer.writeBytes(key.getBytes("US-ASCII"));
      } catch (UnsupportedEncodingException e) {
        throw new SWTProtocolException(e);
      }

      SWTAny.writeAny(buffer, map.get(key));
    }
  }
}
