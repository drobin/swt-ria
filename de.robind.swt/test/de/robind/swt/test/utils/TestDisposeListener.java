package de.robind.swt.test.utils;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;


public class TestDisposeListener implements DisposeListener {
  public List<DisposeEvent> handledEvents = new LinkedList<DisposeEvent>();

  public void widgetDisposed(DisposeEvent e) {
    this.handledEvents.add(e);
  }
}
