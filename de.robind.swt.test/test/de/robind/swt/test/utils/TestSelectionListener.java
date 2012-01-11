package de.robind.swt.test.utils;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

public class TestSelectionListener implements SelectionListener {
  public List<SelectionEvent> selectedEvents = new LinkedList<SelectionEvent>();
  public List<SelectionEvent> defaultSelectedEvents = new LinkedList<SelectionEvent>();

  public void widgetSelected(SelectionEvent e) {
    this.selectedEvents.add(e);
  }

  public void widgetDefaultSelected(SelectionEvent e) {
    this.defaultSelectedEvents.add(e);
  }

  public void clearEvents() {
    this.selectedEvents.clear();
    this.defaultSelectedEvents.clear();
  }
}
