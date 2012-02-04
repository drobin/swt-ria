package de.robind.swt.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import de.robind.swt.base.ClientTasks;
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
    return ((TestClientTasks)ClientTasks.getClientTasks());
  }
}
