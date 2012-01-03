package de.robind.swt.test.utils;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;

public class TestMouseTrackListener implements MouseTrackListener {
  public List<MouseEvent> enterEvents = new LinkedList<MouseEvent>();
  public List<MouseEvent> exitEvents = new LinkedList<MouseEvent>();
  public List<MouseEvent> hoverEvents = new LinkedList<MouseEvent>();

  public void mouseEnter(MouseEvent e) {
    this.enterEvents.add(e);
  }

  public void mouseExit(MouseEvent e) {
    this.exitEvents.add(e);
  }

  public void mouseHover(MouseEvent e) {
    this.hoverEvents.add(e);
  }

  public void clearEvents() {
    this.enterEvents.clear();
    this.exitEvents.clear();
    this.hoverEvents.clear();
  }
}
