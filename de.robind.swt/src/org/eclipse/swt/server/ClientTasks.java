package org.eclipse.swt.server;

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
}
