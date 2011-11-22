package de.robind.swt.protocol;

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
}
