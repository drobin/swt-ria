package org.eclipse.swt.graphics;

import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;

/**
 * Instances of this class manage operating system resources that define how
 * text looks when it is displayed. Fonts may be constructed by providing a
 * device and either name, size and style information or a {@link FontData}
 * object which encapsulates this data.
 * <p>
 * Application code must explicitly invoke the Font.dispose() method to
 * release the operating system resources managed by each instance when those
 * instances are no longer required.
 */
public class Font extends Resource {
  /**
   * FontData assigned to the font
   */
  private FontData fontData[];

  /**
   * Constructs a new font given a device and font data which describes the
   * desired font's appearance.
   * <p>
   * You must dispose the font when it is no longer required.
   *
   * @param device the device to create the font on
   * @param fd the FontData that describes the desired font (must not be
   *           <code>null</code>)
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_NULL_ARGUMENT} -
   *     if device is null and there is no current device
   *    </li>
   *    <li>{@link SWT#ERROR_NULL_ARGUMENT} -
   *     if the fd argument is null
   *    </li>
   *    <li>{@link SWT#ERROR_NO_HANDLES} -
   *      if a font could not be created from the given font data
   *    </li>
   *  </ul>
   */
  public Font(Device device, FontData fd) throws SWTException {
    this(device, new FontData[] {fd});
  }

  /**
   * Constructs a new font given a device and an array of font data which
   * describes the desired font's appearance.
   * <p>
   * You must dispose the font when it is no longer required.
   *
   * @param device the device to create the font on
   * @param fds the array of {@link FontData} that describes the desired font
   *            (must not be <code>null</code>)
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_NULL_ARGUMENT} -
   *     if device is null and there is no current device
   *    </li>
   *    <li>{@link SWT#ERROR_NULL_ARGUMENT} -
   *     if the fds argument is null
   *    </li>
   *    <li>{@link SWT#ERROR_INVALID_ARGUMENT} -
   *     if the length of fds is zero
   *    </li>
   *    <li>{@link SWT#ERROR_NULL_ARGUMENT} -
   *     if any fd in the array is null
   *    </li>
   *    <li>{@link SWT#ERROR_NO_HANDLES} -
   *      if a font could not be created from the given font data
   *    </li>
   *  </ul>
   */
  public Font(Device device, FontData[] fds) throws SWTException {
    if (device == null) {
      throw new SWTException(SWT.ERROR_NULL_ARGUMENT);
    }

    if (fds == null) {
      throw new SWTException(SWT.ERROR_NULL_ARGUMENT);
    }

    if (fds.length == 0) {
      throw new SWTException(SWT.ERROR_INVALID_ARGUMENT);
    }

    for (FontData fd: fds) {
      if (fd == null) {
        throw new SWTException(SWT.ERROR_NULL_ARGUMENT);
      }
    }

    this.device = device;
    this.fontData = fds;
  }

  /**
   * Constructs a new font given a device, a font name, the height of the
   * desired font in points, and a font style.
   * <p>
   * You must dispose the font when it is no longer required.
   *
   * @param device the device to create the font on
   * @param name the name of the font (must not be <code>null</code>)
   * @param height the font height in points
   * @param style a bit or combination of {@link SWT#NORMAL}, {@link SWT#BOLD},
   *              {@link SWT#ITALIC}
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_NULL_ARGUMENT} -
   *     if device is null and there is no current device
   *    </li>
   *    <li>{@link SWT#ERROR_NULL_ARGUMENT} -
   *     if the name argument is null
   *    </li>
   *    <li>{@link SWT#ERROR_INVALID_ARGUMENT} -
   *     if the height is negative
   *    </li>
   *    <li>{@link SWT#ERROR_NO_HANDLES} -
   *      if a font could not be created from the given font data
   *    </li>
   *  </ul>
   */
  public Font(Device device, String name, int height, int style)
      throws SWTException {

    this(device, new FontData[] { new FontData(name, height, style) });
  }

  /**
   * Returns an array of {@link FontData}s representing the receiver. On
   * <i>Windows</i>, only one {@link FontData} will be returned per font. On
   * <i>X</i> however, a {@link Font} object may be composed of multiple X
   * fonts. To support this case, we return an array of font data objects.
   *
   * @return an array of font data objects describing the receiver
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_GRAPHIC_DISPOSED} -
   *     if the receiver has been disposed
   *    </li>
   *  </ul>
   */
  public FontData[] getFontData() throws SWTException {
    if (isDisposed()) {
      throw new SWTException(SWT.ERROR_GRAPHIC_DISPOSED);
    }

    return (this.fontData);
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.graphics.Resource#isDisposed()
   */
  @Override
  public boolean isDisposed() {
    return (this.disposed);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;

    result = prime * result + Arrays.hashCode(fontData);

    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return (false);
    }

    if (getClass() != obj.getClass()) {
      return (false);
    }

    Font other = (Font)obj;

    if (this.getDevice() != other.getDevice()) {
      return (false);
    }

    if ((this.fontData == null) && (other.fontData != null)) {
      return (false);
    }

    if (!Arrays.equals(this.fontData, other.fontData)) {
      return (false);
    }

    return (true);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return getClass().getSimpleName() + " [fontData=" +
        Arrays.toString(this.fontData) + "]";
  }
}
