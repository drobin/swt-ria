package de.robind.swt.test.utils;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;

public class TestKeyListener implements KeyListener {
  public List<KeyEvent> keyPressedEvents = new LinkedList<KeyEvent>();
  public List<KeyEvent> keyReleasedEvents = new LinkedList<KeyEvent>();

  public void keyPressed(KeyEvent e) {
    this.keyPressedEvents.add(e);
  }

  public void keyReleased(KeyEvent e) {
    this.keyReleasedEvents.add(e);
  }

  public void clearEvents() {
    this.keyPressedEvents.clear();
    this.keyReleasedEvents.clear();
  }
}
