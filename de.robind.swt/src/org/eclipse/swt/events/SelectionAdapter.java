package org.eclipse.swt.events;

/**
 * This adapter class provides default implementations for the methods
 * described by the {@link SelectionListener} interface.
 * <p>
 * Classes that wish to deal with {@link SelectionEvent}s can extend this
 * class and override only the methods which they are interested in.
 */
public class SelectionAdapter implements SelectionListener {
  /* (non-Javadoc)
   * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
   */
  public void widgetSelected(SelectionEvent e) {
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
   */
  public void widgetDefaultSelected(SelectionEvent e) {
  }
}
