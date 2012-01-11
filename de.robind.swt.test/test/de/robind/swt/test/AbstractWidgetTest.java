package de.robind.swt.test;

import static de.robind.swt.test.utils.ClientTaskMatcher.createRequest;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.eclipse.swt.server.DisplayPool;
import org.eclipse.swt.server.Key;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class AbstractWidgetTest extends ClientTasksSupport {
  protected Display display = null;
  protected Shell shell = null;

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Before
  public void before() {
    DisplayPool.getInstance().offerKey(new Key() {});
    this.display = new Display();
    this.shell = new Shell(this.display);

    assertThat(getClientTasks(), is(createRequest(this.display, Display.class)));
    assertThat(getClientTasks(), is(createRequest(this.shell, Shell.class, this.display)));
  }

  @After
  public void after() {
    this.shell = null;
    this.display = null;
  }
}
