package de.robind.swt.demo.addressbook;

import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Shell;

public class AddressBookDemo {
  public static void main(String[] args) {
    Display display = new Display();

    final Shell shell = new Shell(display);
    shell.setText("Addressbook");

    ListViewer listViewer = new ListViewer(
        shell, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
    listViewer.setLabelProvider(new AddressBookLabelProvider());
    listViewer.setContentProvider(new AddressBookContentProvider());
    listViewer.setInput(new AddressBookModel());

    final Sash sash = new Sash(shell, SWT.VERTICAL);

    Label label = new Label(shell, SWT.LEFT);
    label.setText("Some content");

    FormLayout form = new FormLayout();
    shell.setLayout(form);

    FormData listViewerData = new FormData();
    listViewerData.left = new FormAttachment(0, 0);
    listViewerData.right = new FormAttachment(sash, 0);
    listViewerData.top = new FormAttachment(0, 0);
    listViewerData.bottom = new FormAttachment(100, 0);
    listViewer.getList().setLayoutData(listViewerData);

    final int limit = 20, percent = 50;
    final FormData sashData = new FormData();
    sashData.left = new FormAttachment(percent, 0);
    sashData.top = new FormAttachment(0, 0);
    sashData.bottom = new FormAttachment(100, 0);
    sash.setLayoutData(sashData);
    sash.addListener(SWT.Selection, new Listener() {
      public void handleEvent(Event e) {
        Rectangle sashRect = sash.getBounds();
        Rectangle shellRect = shell.getClientArea();
        int right = shellRect.width - sashRect.width - limit;
        e.x = Math.max(Math.min (e.x, right), limit);
        if (e.x != sashRect.x) {
          sashData.left = new FormAttachment(0, e.x);
          shell.layout();
        }
      }
    });

    FormData labelData = new FormData();
    labelData.left = new FormAttachment(sash, 0);
    labelData.right = new FormAttachment(100, 0);
    labelData.top = new FormAttachment(0, 0);
    labelData.bottom = new FormAttachment(100, 0);
    label.setLayoutData(labelData);

    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }

    display.dispose();
  }
}
