package org.eclipse.swt.widgets;

import org.eclipse.swt.graphics.Point;

/**
 * An adapter around a {@link Layout}, which implements all the abstract
 * methods of the {@link Layout}-class.
 * <p>
 * <b>NOTE:</b> This class not not part of the offical SWT-API!
 *
 * @author Robin Doer
 */
public class LayoutAdapter extends Layout {
  /* (non-Javadoc)
   * @see org.eclipse.swt.widgets.Layout#computeSize(org.eclipse.swt.widgets.Composite, int, int, boolean)
   */
  @Override
  protected Point computeSize(Composite composite, int wHint, int hHint,
      boolean flushCache) {

    throw new UnsupportedOperationException(
        "Layout.computeSize() is not implemented");
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.widgets.Layout#layout(org.eclipse.swt.widgets.Composite, boolean)
   */
  @Override
  protected void layout(Composite composite, boolean flushCache) {
    throw new UnsupportedOperationException(
        "Layout.layout() is not implemented");
  }
}
