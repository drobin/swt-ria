package de.robind.swt.demo.addressbook;

import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class AddressBookDemo {
  public static void main(String[] args) {
    Display display = new Display();

    Shell shell = new Shell(display);
    shell.setText("Addressbook");

    ListViewer listViewer = new ListViewer(
        shell, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
    listViewer.setLabelProvider(new AddressBookLabelProvider());
    listViewer.setContentProvider(new AddressBookContentProvider());
    listViewer.setInput(new AddressBookModel());

    AddressBookDetailsWidget detailsWidget =
        new AddressBookDetailsWidget(shell, SWT.NO_SCROLL);
    listViewer.addSelectionChangedListener(detailsWidget);

    SashBuilder.buildSash(shell, listViewer.getList(), detailsWidget);

    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }

    display.dispose();
  }
}
