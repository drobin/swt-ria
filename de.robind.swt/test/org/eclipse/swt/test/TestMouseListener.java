package org.eclipse.swt.test;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

public class TestMouseListener implements MouseListener {
  public List<MouseEvent> doubleClickEvents = new LinkedList<MouseEvent>();
  public List<MouseEvent> downEvents = new LinkedList<MouseEvent>();
  public List<MouseEvent> upEvents = new LinkedList<MouseEvent>();

  public void mouseDoubleClick(MouseEvent e) {
    this.doubleClickEvents.add(e);
  }

  public void mouseDown(MouseEvent e) {
    this.downEvents.add(e);
  }

  public void mouseUp(MouseEvent e) {
    this.upEvents.add(e);
  }

  public void clearEvents() {
    this.doubleClickEvents.clear();
    this.downEvents.clear();
    this.upEvents.clear();
  }
}
