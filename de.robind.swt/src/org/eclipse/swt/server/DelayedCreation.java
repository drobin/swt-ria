package org.eclipse.swt.server;


/**
 * Delayed object creation.
 * <p>
 * If an object implements this interface, the object cannot be created during
 * construction of the object. The object needs to be created later. Then,
 * {@link #createObject(Key)} is invoked.
 *
 * @author Robin Doer
 */
public interface DelayedCreation {
  /**
   * Asks the object to send a creation-request to the client.
   * <p>
   * There are object, which are not part of the SWT-object-tree
   * (e.g. layouts). But there also needs to transferred to the client.
   * That's why the creation is delayed and is executed later (if necessary).
   *
   * @param key The key of the application
   * @throws Throwable failed to create the layout at the client
   */
  void createObject(Key key) throws Throwable;
}
