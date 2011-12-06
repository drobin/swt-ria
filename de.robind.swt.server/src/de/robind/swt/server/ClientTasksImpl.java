package de.robind.swt.server;

import org.eclipse.swt.server.ClientTasks;
import org.eclipse.swt.server.Key;
import org.jboss.netty.channel.Channels;

import de.robind.swt.msg.SWTCallRequest;
import de.robind.swt.msg.SWTCallResponse;
import de.robind.swt.msg.SWTException;
import de.robind.swt.msg.SWTNewRequest;
import de.robind.swt.msg.SWTNewResponse;
import de.robind.swt.msg.SWTResponse;

/**
 * Implementation of the {@link ClientTasks}-interface.
 *
 * @author Robin Doer
 */
public class ClientTasksImpl implements ClientTasks {
  /* (non-Javadoc)
   * @see org.eclipse.swt.server.ClientTasks#createObject(org.eclipse.swt.server.Key, int, java.lang.Class, java.lang.Object[])
   */
  public void createObject(Key key, int id, Class<?> objClass, Object... args)
      throws Throwable {

    SWTApplicationKey appKey = (SWTApplicationKey)key;
    SWTApplication app = appKey.getApplication();
    SWTNewRequest request =
        app.getMessageFactory().createNewRequest(id, objClass, args);

    Channels.write(app.getChannel(), request);
    SWTResponse response = app.getResponseQueue().take();

    if (!(response instanceof SWTNewResponse)) {
      throw new Exception("Illegal response of type " +
          response.getClass().getName() + " received");
    }

    if (response instanceof SWTException) {
      throw ((SWTException)response).getCause();
    }
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.server.ClientTasks#callMethod(org.eclipse.swt.server.Key, int, java.lang.String, java.lang.Object[])
   */
  public Object callMethod(Key key, int id, String method, Object... args)
      throws Throwable {

    SWTApplicationKey appKey = (SWTApplicationKey)key;
    SWTApplication app = appKey.getApplication();
    SWTCallRequest request =
        app.getMessageFactory().createCallRequest(id, method, args);

    Channels.write(app.getChannel(), request);
    SWTResponse response = app.getResponseQueue().take();

    if (!(response instanceof SWTCallResponse)) {
      throw new Exception("Illegal response of type " +
          response.getClass().getName() + " received");
    }

    if (response instanceof SWTException) {
      throw ((SWTException)response).getCause();
    }

    return (((SWTCallResponse)response).getResult());
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.server.ClientTasks#registerEvent(org.eclipse.swt.server.Key, int, int, boolean)
   */
  public void registerEvent(Key key, int id, int eventType, boolean enable)
      throws Throwable {

    throw new Error("Needs to be implemented");
  }
}
