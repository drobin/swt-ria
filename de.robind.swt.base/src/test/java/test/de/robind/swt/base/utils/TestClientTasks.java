package test.de.robind.swt.base.utils;

import de.robind.swt.base.ClientTasks;
import de.robind.swt.base.Key;
import de.robind.swt.base.SWTBaseException;

public class TestClientTasks extends ClientTasks {
  /* (non-Javadoc)
   * @see de.robind.swt.base.ClientTasks#createObject(de.robind.swt.base.Key, int, java.lang.Class, java.lang.Object[])
   */
  @Override
  public void createObject(Key key, int id, Class<?> objClass, Object... args)
      throws SWTBaseException {

    throw new UnsupportedOperationException("Not implemented");
  }

  /* (non-Javadoc)
   * @see de.robind.swt.base.ClientTasks#callMethod(de.robind.swt.base.Key, int, java.lang.String, java.lang.Object[])
   */
  @Override
  public Object callMethod(Key key, int id, String method, Object... args)
      throws SWTBaseException {

    throw new UnsupportedOperationException("Not implemented");
  }

  /* (non-Javadoc)
   * @see de.robind.swt.base.ClientTasks#registerEvent(de.robind.swt.base.Key, int, int, boolean)
   */
  @Override
  public void registerEvent(Key key, int id, int eventType, boolean enable)
      throws SWTBaseException {

    throw new UnsupportedOperationException("Not implemented");
  }

  /* (non-Javadoc)
   * @see de.robind.swt.base.ClientTasks#updateAttribute(de.robind.swt.base.Key, int, java.lang.String, java.lang.Object)
   */
  @Override
  public void updateAttribute(Key key, int id, String attrName, Object attrValue)
      throws SWTBaseException {

    throw new UnsupportedOperationException("Not implemented");
  }
}
