package org.eclipse.swt.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.SWTObject;
import org.eclipse.swt.widgets.Display;

/**
 * This class is the abstract superclass of all device objects, such as the
 * {@link Display} device and the Printer device. Devices can have a graphics
 * context (GC) created for them, and they can be drawn on by sending messages
 * to the associated GC.
 */
public abstract class Device extends SWTObject implements Drawable {
  /**
   * Disposed state.
   */
  private boolean disposed = false;

  /**
   * Constructs a new instance of this class.
   * <p>
   * You must dispose the device when it is no longer required.
   */
  public Device() {
    this(null);
  }

  /**
   * Constructs a new instance of this class.
   * <p>
   * You must dispose the device when it is no longer required.
   *
   * @param data the DeviceData which describes the receiver
   */
  public Device(DeviceData data) {
    synchronized (Device.class) {
      //check and create pool
      create(data);
      init();
    }
  }

  /**
   * Returns <code>true</code> if the device has been disposed, and
   * <code>false</code> otherwise.
   * <p>
   * This method gets the dispose state for the device. When a device has been
   * disposed, it is an error to invoke any other method using the device.
   *
   * @return <code>true</code> when the device is disposed and
   *         <code>false</code> otherwise
   * @throws SWTException
   */
  public boolean isDisposed() {
    synchronized (Device.class) {
      return (this.disposed);
    }
  }

  /**
   * Disposes of the operating system resources associated with the receiver.
   * <p>
   * After this method has been invoked, the receiver will answer
   * <code>true</code> when sent the message {@link #isDisposed()}.
   */
  public void dispose() {
    synchronized (Device.class) {
      if (isDisposed()) {
        return;
      }

      checkDevice();
      release();
      destroy();
      this.disposed = true;
    }
  }

  /**
   * Throws an {@link SWTException} if the receiver can not be accessed by the
   * caller. This may include both checks on the state of the receiver and more
   * generally on the entire execution context. This method <i>should</i> be
   * called by device implementors to enforce the standard SWT invariants.
   * <p>
   * Currently, it is an error to invoke any method (other than
   * {@link #isDisposed()} and {@link #dispose()}) on a device that has had its
   * {@link #dispose()} method called.
   *
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_WIDGET_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *  </ul>
   */
  protected void checkDevice() throws SWTException {
    if (this.disposed) {
      throw new SWTException(SWT.ERROR_DEVICE_DISPOSED);
    }
  }

  /**
   * Initializes any internal resources needed by the device.
   * <p>
   * This method is called after {@link #create(DeviceData)}.
   * <p>
   * If subclasses reimplement this method, they must call the super
   * implementation.
   */
  protected void init() {
  }

  /**
   * Releases any internal resources back to the operating system and clears
   * all fields except the device handle.
   * <p>
   * When a device is destroyed, resources that were acquired on behalf of the
   * programmer need to be returned to the operating system. For example, if
   * the device allocated a font to be used as the system font, this font would
   * be freed in {@link #release()}. Also, to assist the garbage collector and
   * minimize the amount of memory that is not reclaimed when the programmer
   * keeps a reference to a disposed device, all fields except the handle are
   * zero'd. The handle is needed by {@link #destroy()}.
   * <p>
   * This method is called before {@link #destroy()}.
   * <p>
   * If subclasses reimplement this method, they must call the super
   * implementation.
   */
  protected void release() {
  }

  /**
   * Creates the device in the operating system. If the device does not have a
   * handle, this method may do nothing depending on the device.
   * <p>
   * This method is called before {@link #init()}.
   * <p>
   * Subclasses are supposed to reimplement this method and not call the super
   * implementation.
   *
   * @param data the DeviceData which describes the receiver
   */
  protected void create(DeviceData data) {
  }

  /**
   * Destroys the device in the operating system and releases the device's
   * handle. If the device does not have a handle, this method may do nothing
   * depending on the device.
   * <p>
   * This method is called after {@link #release()}.
   * <p>
   * Subclasses are supposed to reimplement this method and not call the super
   * implementation.
   */
  protected void destroy() {
  }
}
