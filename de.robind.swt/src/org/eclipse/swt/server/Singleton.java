package org.eclipse.swt.server;

import de.robind.swt.msg.SWTMessageFactory;

public class Singleton {
  private static SWTMessageFactory messageFactory = null;

  public static SWTMessageFactory getMessageFactory() {
    if (Singleton.messageFactory == null) {
      Singleton.messageFactory = new SWTMessageFactory();
    }

    return (Singleton.messageFactory);
  }
}
