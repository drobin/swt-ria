package de.robind.swt.test.utils;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;

public class TestControlListener implements ControlListener {
  public List<ControlEvent> movedEvents = new LinkedList<ControlEvent>();
  public List<ControlEvent> resizedEvents = new LinkedList<ControlEvent>();

  public void controlMoved(ControlEvent e) {
    this.movedEvents.add(e);
  }

  public void controlResized(ControlEvent e) {
    this.resizedEvents.add(e);
  }

  public void clearEvents() {
    this.movedEvents.clear();
    this.resizedEvents.clear();
  }
}
