package de.robind.swt.test.utils;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;

public class TestMouseMoveListener implements MouseMoveListener {
  public List<MouseEvent> events = new LinkedList<MouseEvent>();

  public void mouseMove(MouseEvent e) {
    this.events.add(e);
  }
}
