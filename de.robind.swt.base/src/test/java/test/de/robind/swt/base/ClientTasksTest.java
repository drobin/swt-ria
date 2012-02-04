package test.de.robind.swt.base;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import static test.de.robind.swt.base.utils.SWTExceptionMatcher.swtCode;

import java.lang.reflect.Field;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import test.de.robind.swt.base.utils.TestClientTasks;
import de.robind.swt.base.ClientTasks;

public class ClientTasksTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Before
  public void before() throws Exception {
    // You need this "hack" to be able to predict the ClientTasks-instance.
    // So, the ClientTasks-instance "instance" is resetted before are test-run.
    Field instanceField = ClientTasks.class.getDeclaredField("instance");
    instanceField.setAccessible(true);
    instanceField.set(null, null);
  }

  @After
  public void after() {
    System.getProperties().remove("de.robind.swt.clienttasks");
    assertThat(System.getProperty("de.robind.swt.clienttasks"), is(nullValue()));
  }

  @Test
  public void getClientTasks() {
    System.setProperty("de.robind.swt.clienttasks", TestClientTasks.class.getName());

    ClientTasks clientTasks = ClientTasks.getClientTasks();
    assertThat(ClientTasks.getClientTasks(), is(instanceOf(TestClientTasks.class)));
    assertThat(ClientTasks.getClientTasks(), is(sameInstance(clientTasks)));
  }

  @Test
  public void getClientTasksFailure() {
    System.setProperty("de.robind.swt.clienttasks", "xxx");

    exception.expect(SWTException.class);
    exception.expect(swtCode(SWT.ERROR_FAILED_EXEC));

    ClientTasks.getClientTasks();
  }
}
