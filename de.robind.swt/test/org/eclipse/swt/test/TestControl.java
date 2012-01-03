package org.eclipse.swt.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * The class needs to be located inside the org.eclipse.swt-hierarchie to
 * prevent {@link SWT#ERROR_INVALID_SUBCLASS}-exception.
 */
public class TestControl extends Control {
  public TestControl(Composite parent, int style) throws SWTException {
    super(parent, style);
  }
}
