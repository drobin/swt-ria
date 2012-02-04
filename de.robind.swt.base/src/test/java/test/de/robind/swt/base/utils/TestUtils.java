package test.de.robind.swt.base.utils;

import java.lang.reflect.Field;

import de.robind.swt.base.ClientTasks;
import de.robind.swt.base.SWTObject;

public class TestUtils {
  public static void resetNextId()
      throws NoSuchFieldException, IllegalAccessException {

    // You need this "hack" to be able to predict the object id.
    // So, the id-counter "nextId" is resetted before every test runs.
    Field nextIdField = SWTObject.class.getDeclaredField("nextId");
    nextIdField.setAccessible(true);
    nextIdField.set(null, 0);
  }

  public static void resetInstance()
      throws NoSuchFieldException, IllegalAccessException {

    // You need this "hack" to be able to predict the ClientTasks-instance.
    // So, the ClientTasks-instance "instance" is resetted before are test-run.
    Field instanceField = ClientTasks.class.getDeclaredField("instance");
    instanceField.setAccessible(true);
    instanceField.set(null, null);
  }
}
