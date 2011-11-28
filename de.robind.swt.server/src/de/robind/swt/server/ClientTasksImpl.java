package de.robind.swt.server;

import org.eclipse.swt.server.ClientTasks;
import org.eclipse.swt.server.Key;

import de.robind.swt.msg.SWTRequest;
import de.robind.swt.msg.SWTResponse;

/**
 * Implementation of the {@link ClientTasks}-interface.
 *
 * @author Robin Doer
 */
public class ClientTasksImpl implements ClientTasks {

  /* (non-Javadoc)
   * @see org.eclipse.swt.server.ClientTasks#sendRequest(org.eclipse.swt.server.Key, de.robind.swt.msg.SWTRequest)
   */
  public SWTResponse sendRequest(Key key, SWTRequest request) {
    throw new Error("Needs to be implemented");
  }
}
