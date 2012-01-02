package org.eclipse.swt.test.utils;

import org.eclipse.swt.widgets.Event;

public class TestEvent extends Event {
  public TestEvent(Object data) {
    this.data = data;
  }
}
