package org.eclipse.swt.test;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Widget;

public class TestEvent extends Event {
  public TestEvent(int type, Display display, Widget widget, int time, Object data) {
    this.type = type;
    this.display = display;
    this.widget = widget;
    this.time = time;
    this.data = data;
  }
}
