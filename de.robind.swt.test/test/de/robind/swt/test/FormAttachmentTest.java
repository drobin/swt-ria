package de.robind.swt.test;

import static de.robind.swt.test.utils.ClientTaskMatcher.attrRequest;
import static de.robind.swt.test.utils.ClientTaskMatcher.createRequest;
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
import org.junit.After;
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

  @After
  public void after() {
    getClientTasks().clearState();
  }

  @Test
  public void stdCtor() {
    FormAttachment attachment = new FormAttachment();
    assertThat(attachment.alignment, is(0));
    assertThat(attachment.control, is(nullValue()));
    assertThat(attachment.denominator, is(100));
    assertThat(attachment.numerator, is(0));
    assertThat(attachment.offset, is(0));

    assertThat(getClientTasks().getQueueSize(), is(0));

    attachment.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), is(createRequest(attachment, FormAttachment.class)));
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

    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), is(createRequest(display, Display.class)));
    assertThat(getClientTasks(), is(createRequest(shell, Shell.class, display)));

    attachment.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), is(createRequest(attachment, FormAttachment.class, shell, 0, SWT.DEFAULT)));
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

    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), is(createRequest(display, Display.class)));
    assertThat(getClientTasks(), is(createRequest(shell, Shell.class, display)));

    attachment.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), is(createRequest(attachment, FormAttachment.class, shell, 55, SWT.DEFAULT)));
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

    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), is(createRequest(display, Display.class)));
    assertThat(getClientTasks(), is(createRequest(shell, Shell.class, display)));

    attachment.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), is(createRequest(attachment, FormAttachment.class, shell, 55, SWT.TOP)));
  }

  @Test
  public void ctorInt() {
    FormAttachment attachment = new FormAttachment(55);

    assertThat(attachment.alignment, is(0));
    assertThat(attachment.control, is(nullValue()));
    assertThat(attachment.denominator, is(100));
    assertThat(attachment.numerator, is(55));
    assertThat(attachment.offset, is(0));

    assertThat(getClientTasks().getQueueSize(), is(0));

    attachment.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), is(createRequest(attachment, FormAttachment.class, 55, 100, 0)));
  }

  @Test
  public void ctorIntInt() {
    FormAttachment attachment = new FormAttachment(55, 66);

    assertThat(attachment.alignment, is(0));
    assertThat(attachment.control, is(nullValue()));
    assertThat(attachment.denominator, is(100));
    assertThat(attachment.numerator, is(55));
    assertThat(attachment.offset, is(66));

    assertThat(getClientTasks().getQueueSize(), is(0));

    attachment.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), is(createRequest(attachment, FormAttachment.class, 55, 100, 66)));
  }

  @Test
  public void ctorIntIntInt() {
    FormAttachment attachment = new FormAttachment(55, 66, 77);

    assertThat(attachment.alignment, is(0));
    assertThat(attachment.control, is(nullValue()));
    assertThat(attachment.denominator, is(66));
    assertThat(attachment.numerator, is(55));
    assertThat(attachment.offset, is(77));

    assertThat(getClientTasks().getQueueSize(), is(0));

    attachment.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(1));
    assertThat(getClientTasks(), is(createRequest(attachment, FormAttachment.class, 55, 66, 77)));
  }

  @Test
  public void ctorIntIntIntUnsupportedDenominator() {
    exception.expect(errorCode(SWT.ERROR_CANNOT_BE_ZERO));
    new FormAttachment(55, 0, 77);
  }

  @Test
  public void updateAlignment() {
    FormAttachment attachment = new FormAttachment();
    attachment.alignment = 4711;

    assertThat(getClientTasks().getQueueSize(), is(0));

    attachment.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), is(createRequest(attachment, FormAttachment.class)));
    assertThat(getClientTasks(), is(attrRequest(attachment, "alignment", 4711)));
  }

  @Test
  public void updateControl() {
    DisplayPool.getInstance().offerKey(new Key() {});
    Display display = new Display();
    Shell shell = new Shell(display);

    FormAttachment attachment = new FormAttachment();
    attachment.control = shell;

    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), is(createRequest(display, Display.class)));
    assertThat(getClientTasks(), is(createRequest(shell, Shell.class, display)));

    attachment.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), is(createRequest(attachment, FormAttachment.class)));
    assertThat(getClientTasks(), is(attrRequest(attachment, "control", shell)));
  }

  @Test
  public void updateDenominator() {
    FormAttachment attachment = new FormAttachment();
    attachment.denominator = 4711;

    assertThat(getClientTasks().getQueueSize(), is(0));

    attachment.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), is(createRequest(attachment, FormAttachment.class)));
    assertThat(getClientTasks(), is(attrRequest(attachment, "denominator", 4711)));
  }

  @Test
  public void updateNumerator() {
    FormAttachment attachment = new FormAttachment();
    attachment.numerator = 4711;

    assertThat(getClientTasks().getQueueSize(), is(0));

    attachment.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), is(createRequest(attachment, FormAttachment.class)));
    assertThat(getClientTasks(), is(attrRequest(attachment, "numerator", 4711)));
  }

  @Test
  public void updateOffset() {
    FormAttachment attachment = new FormAttachment();
    attachment.offset = 4711;

    assertThat(getClientTasks().getQueueSize(), is(0));

    attachment.setKey(new Key() {});
    assertThat(getClientTasks().getQueueSize(), is(2));
    assertThat(getClientTasks(), is(createRequest(attachment, FormAttachment.class)));
    assertThat(getClientTasks(), is(attrRequest(attachment, "offset", 4711)));
  }

  protected TestClientTasks getClientTasks() {
    DisplayPool pool = DisplayPool.getInstance();
    return ((TestClientTasks)pool.getClientTasks());
  }
}
