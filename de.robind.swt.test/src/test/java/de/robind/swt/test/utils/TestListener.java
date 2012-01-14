package de.robind.swt.test.utils;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class TestListener implements Listener {
  public List<Event> handledEvents = new LinkedList<Event>();

  public void handleEvent(Event event) {
    this.handledEvents.add(event);
  }
}
