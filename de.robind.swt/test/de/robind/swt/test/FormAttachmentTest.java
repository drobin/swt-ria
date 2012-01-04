package de.robind.swt.test;

import static de.robind.swt.test.utils.SWTErrorMatcher.errorCode;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.server.DisplayPool;
import org.eclipse.swt.server.Key;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.robind.swt.test.utils.TestClientTasks;

public class FormAttachmentTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @BeforeClass
  public static void beforeClass() {
    System.setProperty("de.robind.swt.clienttasks", TestClientTasks.class.getName());
  }

  @AfterClass
  public static void afterClass() {
    System.clearProperty("de.robind.swt.clienttasks");
  }

  @Test
  public void stdCtor() {
    FormAttachment attachment = new FormAttachment();
    assertThat(attachment.alignment, is(0));
    assertThat(attachment.control, is(nullValue()));
    assertThat(attachment.denominator, is(100));
    assertThat(attachment.numerator, is(0));
    assertThat(attachment.offset, is(0));
  }

  @Test
  public void ctorControl() {
    DisplayPool.getInstance().offerKey(new Key() {});
    Display display = new Display();
    Shell shell = new Shell(display);

    FormAttachment attachment = new FormAttachment(shell);
    assertThat(attachment.alignment, is(SWT.DEFAULT));
    assertThat(attachment.control, is(sameInstance((Control)shell)));
    assertThat(attachment.denominator, is(100));
    assertThat(attachment.numerator, is(0));
    assertThat(attachment.offset, is(0));
  }

  @Test
  public void ctorControlInt() {
    DisplayPool.getInstance().offerKey(new Key() {});
    Display display = new Display();
    Shell shell = new Shell(display);

    FormAttachment attachment = new FormAttachment(shell, 55);
    assertThat(attachment.alignment, is(SWT.DEFAULT));
    assertThat(attachment.control, is(sameInstance((Control)shell)));
    assertThat(attachment.denominator, is(100));
    assertThat(attachment.numerator, is(0));
    assertThat(attachment.offset, is(55));
  }

  @Test
  public void ctorControlIntInt() {
    DisplayPool.getInstance().offerKey(new Key() {});
    Display display = new Display();
    Shell shell = new Shell(display);

    FormAttachment attachment = new FormAttachment(shell, 55, SWT.TOP);
    assertThat(attachment.alignment, is(SWT.TOP));
    assertThat(attachment.control, is(sameInstance((Control)shell)));
    assertThat(attachment.denominator, is(100));
    assertThat(attachment.numerator, is(0));
    assertThat(attachment.offset, is(55));
  }

  @Test
  public void ctorInt() {
    FormAttachment attachment = new FormAttachment(55);

    assertThat(attachment.alignment, is(0));
    assertThat(attachment.control, is(nullValue()));
    assertThat(attachment.denominator, is(100));
    assertThat(attachment.numerator, is(55));
    assertThat(attachment.offset, is(0));
  }

  @Test
  public void ctorIntInt() {
    FormAttachment attachment = new FormAttachment(55, 66);

    assertThat(attachment.alignment, is(0));
    assertThat(attachment.control, is(nullValue()));
    assertThat(attachment.denominator, is(100));
    assertThat(attachment.numerator, is(55));
    assertThat(attachment.offset, is(66));
  }

  @Test
  public void ctorIntIntInt() {
    FormAttachment attachment = new FormAttachment(55, 66, 77);

    assertThat(attachment.alignment, is(0));
    assertThat(attachment.control, is(nullValue()));
    assertThat(attachment.denominator, is(66));
    assertThat(attachment.numerator, is(55));
    assertThat(attachment.offset, is(77));
  }

  @Test
  public void ctorIntIntIntUnsupportedDenominator() {
    exception.expect(errorCode(SWT.ERROR_CANNOT_BE_ZERO));
    new FormAttachment(55, 0, 77);
  }
}
