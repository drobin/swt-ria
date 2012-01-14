package org.eclipse.swt.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Scrollable;

/**
 * The class needs to be located inside the org.eclipse.swt-hierarchie to
 * prevent {@link SWT#ERROR_INVALID_SUBCLASS}-exception.
 */
public class TestScrollable extends Scrollable {
  public TestScrollable(Composite parent, int style) throws SWTException {
    super(parent, style);
  }
}
