package de.robind.swt.protocol.datatype;

import static de.robind.swt.protocol.datatype.DataTypeUtils.DT_SWTOBJ;
import static de.robind.swt.protocol.datatype.DataTypeUtils.FLAG_NONE;
import static de.robind.swt.protocol.datatype.DataTypeUtils.fetchAndCheckFlags;
import static de.robind.swt.protocol.datatype.DataTypeUtils.fetchAndCheckType;

import org.jboss.netty.buffer.ChannelBuffer;

import de.robind.swt.msg.SWTObjectId;
import de.robind.swt.protocol.SWTProtocolException;

/**
 * Utilitiy-methods to encode/decode an {@link SWTObjectId} from a
 * {@link ChannelBuffer}.
 *
 * @author Robin Doer
 */
public class SWTObj {
  /**
   * Reads an {@link SWTObjectId} from the buffer.
   *
   * @param buffer The source buffer
   * @return The decoded {@link SWTObjectId}-value
   * @throws NullPointerException if <code>buffer</code> is <code>null</code>
   * @throws IndexOutOfBoundsException if not enough data are available in
   *         <code>buffer</code> to read the {@link SWTObjectId}
   * @throws SWTProtocolException if an decoding-error occured
   */
  public static SWTObjectId readObjId(ChannelBuffer buffer)
      throws NullPointerException, IndexOutOfBoundsException,
             SWTProtocolException {

    if (buffer == null) {
      throw new NullPointerException("buffer cannot be null");
    }

    fetchAndCheckType(buffer, DT_SWTOBJ);
    fetchAndCheckFlags(buffer, FLAG_NONE);

    int value = buffer.readInt();
    return ((value >= 0) ? new SWTObjectId(value) : SWTObjectId.undefined());
  }

  /**
   * Writes a {@link SWTObjectId} back into the buffer.
   *
   * @param buffer The destination buffer
   * @param objId The {@link SWTObjectId} to be encoded
   * @throws NullPointerException if one of the arguments are <code>null</code>
   * @throws IndexOutOfBoundsException if not enough space is available in
   *         <code>buffer</code> to encode the whole {@link SWTObjectId}
   */
  public static void writeIObjId(ChannelBuffer buffer, SWTObjectId objId)
      throws NullPointerException, IndexOutOfBoundsException {

    if (buffer == null) {
      throw new NullPointerException("buffer cannot be null");
    }

    if (objId == null) {
      throw new NullPointerException("objId cannot be null");
    }

    buffer.writeByte(DT_SWTOBJ);
    buffer.writeByte(FLAG_NONE);
    buffer.writeInt(objId.isValid() ? objId.getId() : -1);
  }
}
