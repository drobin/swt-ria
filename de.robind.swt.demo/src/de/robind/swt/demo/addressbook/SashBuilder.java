package de.robind.swt.demo.addressbook;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Sash;

public class SashBuilder {
  public static void buildSash(final Composite parent, Control left, Control right) {
    final Sash sash = new Sash(parent, SWT.VERTICAL);

    FormData leftData = new FormData();
    leftData.left = new FormAttachment(0, 0);
    leftData.right = new FormAttachment(sash, 0);
    leftData.top = new FormAttachment(0, 0);
    leftData.bottom = new FormAttachment(100, 0);
    left.setLayoutData(leftData);

    FormData rightData = new FormData();
    rightData.left = new FormAttachment(sash, 0);
    rightData.right = new FormAttachment(100, 0);
    rightData.top = new FormAttachment(0, 0);
    rightData.bottom = new FormAttachment(100, 0);
    right.setLayoutData(rightData);

    final int limit = 20, percent = 50;
    final FormData sashData = new FormData();
    sashData.left = new FormAttachment(percent, 0);
    sashData.top = new FormAttachment(0, 0);
    sashData.bottom = new FormAttachment(100, 0);
    sash.setLayoutData(sashData);
    sash.addListener(SWT.Selection, new Listener() {
      public void handleEvent(Event e) {
        Rectangle sashRect = sash.getBounds();
        Rectangle shellRect = parent.getClientArea();
        int right = shellRect.width - sashRect.width - limit;
        e.x = Math.max(Math.min (e.x, right), limit);
        if (e.x != sashRect.x) {
          sashData.left = new FormAttachment(0, e.x);
          parent.layout();
        }
      }
    });

    FormLayout form = new FormLayout();
    parent.setLayout(form);

  }
}
