package de.robind.swt.test;

import org.eclipse.swt.server.DisplayPool;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import de.robind.swt.test.utils.TestClientTasks;

public class ClientTasksSupport {
  @BeforeClass
  public static void setupClientTasks() {
    System.setProperty("de.robind.swt.clienttasks", TestClientTasks.class.getName());
  }

  @AfterClass
  public static void teardownClientTasks() {
    System.clearProperty("de.robind.swt.clienttasks");
  }

  @After
  public void clearClientTasksState() {
    getClientTasks().clearState();
  }

  protected TestClientTasks getClientTasks() {
    DisplayPool pool = DisplayPool.getInstance();
    return ((TestClientTasks)pool.getClientTasks());
  }
}
