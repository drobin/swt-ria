package org.eclipse.swt.test;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;

public class TestMenuDetectListener implements MenuDetectListener {
  public List<MenuDetectEvent> events = new LinkedList<MenuDetectEvent>();

  public void menuDetected(MenuDetectEvent e) {
    this.events.add(e);
  }
}
