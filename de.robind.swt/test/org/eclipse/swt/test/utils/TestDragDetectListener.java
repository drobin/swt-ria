package org.eclipse.swt.test.utils;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.DragDetectListener;

public class TestDragDetectListener implements DragDetectListener {
  public List<DragDetectEvent> events = new LinkedList<DragDetectEvent>();

  public void dragDetected(DragDetectEvent e) {
    this.events.add(e);
  }
}
