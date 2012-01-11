package de.robind.swt.test.utils;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.events.HelpEvent;
import org.eclipse.swt.events.HelpListener;

public class TestHelpListener implements HelpListener {
  public List<HelpEvent> events = new LinkedList<HelpEvent>();

  public void helpRequested(HelpEvent e) {
    this.events.add(e);
  }
}
