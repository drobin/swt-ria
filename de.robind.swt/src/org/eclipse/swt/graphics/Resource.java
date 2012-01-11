package org.eclipse.swt.graphics;

import org.eclipse.swt.SWTObject;

/**
 * This class is the abstract superclass of all graphics resource objects.
 * Resources created by the application must be disposed.
 * <p>
 * IMPORTANT: This class is intended to be subclassed only within the SWT
 * implementation. However, it has not been marked final to allow those
 * outside of the SWT development team to implement patched versions of the
 * class in order to get around specific limitations in advance of when those
 * limitations can be addressed by the team. Any class built using subclassing
 * to access the internals of this class will likely fail to compile or run
 * between releases and may be strongly platform specific. Subclassing should
 * not be attempted without an intimate and detailed understanding of the
 * workings of the hierarchy. No support is provided for user-written classes
 * which are implemented as subclasses of this class.
 */
public abstract class Resource extends SWTObject {
  /**
   * The devise where the resource was created.
   */
  protected Device device = null;

  /**
   * Dispose-state of the resource
   */
  protected boolean disposed = false;

  /**
   * Returns the {@link Device} where this resource was created.
   *
   * @return the device of the receiver
   */
  public Device getDevice() {
    return (this.device);
  }

  /**
   * Disposes of the operating system resources associated with this resource.
   * Applications must dispose of all resources which they allocate. This
   * method does nothing if the resource is already disposed.
   */
  public void dispose() {
    if (!isDisposed()) {
      this.disposed = true;
      callMethod("dispose");
    }
  }

  /**
   * Returns <code>true</code> if the resource has been disposed, and
   * <code>false</code> otherwise.
   * <p>
   * This method gets the dispose state for the resource. When a resource has
   * been disposed, it is an error to invoke any other method (except
   * {@link #dispose()}) using the resource.
   *
   * @return <code>true</code> when the resource is disposed and
   *         <code>false</code> otherwise
   */
  public abstract boolean isDisposed();
}
