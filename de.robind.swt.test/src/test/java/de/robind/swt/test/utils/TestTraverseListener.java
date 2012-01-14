package de.robind.swt.test.utils;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;

public class TestTraverseListener implements TraverseListener {
  public List<TraverseEvent> events = new LinkedList<TraverseEvent>();

  public void keyTraversed(TraverseEvent e) {
    this.events.add(e);
  }
}
