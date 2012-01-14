package de.robind.swt.test;

import static de.robind.swt.test.utils.SWTExceptionMatcher.swtCode;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableTree;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class TableTreeTest extends AbstractWidgetTest {
  @Test
  public void deprecated() {
    exception.expect(swtCode(SWT.ERROR_NOT_IMPLEMENTED));
    new TableTree(this.shell, 4711);
  }
}
