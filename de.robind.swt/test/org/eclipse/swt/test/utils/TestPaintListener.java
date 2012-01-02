package org.eclipse.swt.test.utils;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;

public class TestPaintListener implements PaintListener {
  public List<PaintEvent> events = new LinkedList<PaintEvent>();

  public void paintControl(PaintEvent e) {
    this.events.add(e);
  }
}
