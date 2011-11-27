package org.eclipse.swt.server;

import de.robind.swt.msg.SWTEvent;
import de.robind.swt.msg.SWTRequest;
import de.robind.swt.msg.SWTResponse;

public interface ClientTasks {
  /**
   * Sends the given request-message to the client and returns the related
   * answer.
   *
   * @param key The key of the client-connection
   * @param request The request to be send
   * @return The related answer
   */
  SWTResponse sendRequest(Key key, SWTRequest request);

  /**
   * Receives an event from the client.
   * <p>
   * The method blocks until a new event arrived.
   *
   * @param key The key to the client-connection
   * @return The new event received from the client
   */
  SWTEvent receiveEvent(Key key);
}
