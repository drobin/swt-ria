package de.robind.swt.test.utils;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;

public class TestMouseWheelListener implements MouseWheelListener {
  public List<MouseEvent> events = new LinkedList<MouseEvent>();

  public void mouseScrolled(MouseEvent e) {
    this.events.add(e);
  }
}
