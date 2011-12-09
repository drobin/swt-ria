package org.eclipse.swt.server;

import org.eclipse.swt.widgets.Event;

public interface ClientTasks {
  /**
   * Asks the connected client to create an object.
   *
   * @param key The key of the client-connection
   * @param id The id of the object to be created
   * @param objClass The class of the object to be created
   * @param args Arguments which should be passed to the constructor
   * @throws Exception failed to create the client-object
   */
  void createObject(Key key, int id, Class<?> objClass, Object... args)
      throws Throwable;

  /**
   * Asks the connected client to invoke a method.
   *
   * @param key The key of the client-connection
   * @param id The id of the destination object
   * @param method Name of method to be invoked
   * @param args Arguments passed to the method
   * @return Result returned by the client-method
   * @throws Exception failed to call client-method
   */
  Object callMethod(Key key, int id, String method, Object... args)
      throws Throwable;

  /**
   * Asks the client to enable event-handling for an object.
   *
   * @param key The key of the client-connection
   * @param id The id of the destination object
   * @param eventType the event-type, where the event-handling should be
   *                  enabed/disabled.
   * @param enable if set to <code>true</code>, the event-handling should be
   *               enabled. If set to <code>false</code>, it should be
   *               disabled.
   * @throws Throwable failed to enable/disable event-handling
   */
  void registerEvent(Key key, int id, int eventType, boolean enable)
      throws Throwable;

  /**
   * Receives an event from the client.
   * <p>
   * The method blocks the calling thread until an event arrives.
   *
   * @return The event received from the client
   * @throws InterruptedException if the current thread was interrupted
   * @throws Exception failed to receive the event
   */
  Event waitForEvent(Key key) throws Exception;
}
