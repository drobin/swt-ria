package de.robind.swt.test.utils;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;

public class TestFocusListener implements FocusListener {
  public List<FocusEvent> focusGainedEvents = new LinkedList<FocusEvent>();
  public List<FocusEvent> focusLostEvents = new LinkedList<FocusEvent>();

  public void focusGained(FocusEvent e) {
    this.focusGainedEvents.add(e);
  }

  public void focusLost(FocusEvent e) {
    this.focusLostEvents.add(e);
  }

  public void clearEvents() {
    this.focusGainedEvents.clear();
    this.focusLostEvents.clear();
  }
}
