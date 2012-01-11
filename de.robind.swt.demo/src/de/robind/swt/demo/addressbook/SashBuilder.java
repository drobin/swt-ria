package de.robind.swt.demo.addressbook;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Sash;

public class SashBuilder {
  public static void buildSash(Composite parent, Control left, Control right) {
    parent.setLayout(new FormLayout());

    // Create the sash first, so the other controls
    // can be attached to it.
    final Sash sash = new Sash(parent, SWT.VERTICAL);
    FormData data = new FormData();
    data.top = new FormAttachment(0, 0); // Attach to top
    data.bottom = new FormAttachment(100, 0); // Attach to bottom
    data.left = new FormAttachment(50, 0); // Attach halfway across
    sash.setLayoutData(data);

    sash.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent event) {
        // We reattach to the left edge, and we use the x value of the event to
        // determine the offset from the left
        ((FormData)sash.getLayoutData()).left = new FormAttachment(0, event.x);

        // Until the parent window does a layout, the sash will not be redrawn in
        // its new location.
        sash.getParent().layout();
      }
    });

    // Create the first text box and attach its right edge
    // to the sash
    data = new FormData();
    data.top = new FormAttachment(0, 0);
    data.bottom = new FormAttachment(100, 0);
    data.left = new FormAttachment(0, 0);
    data.right = new FormAttachment(sash, 0);
    left.setLayoutData(data);

    // Create the second text box and attach its left edge
    // to the sash
    data = new FormData();
    data.top = new FormAttachment(0, 0);
    data.bottom = new FormAttachment(100, 0);
    data.left = new FormAttachment(sash, 0);
    data.right = new FormAttachment(100, 0);
    right.setLayoutData(data);
  }
}
