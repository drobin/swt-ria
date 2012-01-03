package org.eclipse.swt.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Widget;

/**
 * The class needs to be located inside the org.eclipse.swt-hierarchie to
 * prevent {@link SWT#ERROR_INVALID_SUBCLASS}-exception.
 */
public class TestWidget extends Widget {
  public TestWidget(Widget parent, int style) throws SWTException {
    super(parent, style);
  }
}
