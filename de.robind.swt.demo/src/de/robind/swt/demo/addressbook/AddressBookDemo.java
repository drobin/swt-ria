package de.robind.swt.demo.addressbook;

import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class AddressBookDemo {
  public static void main(String[] args) {
    Display display = new Display();

    Shell shell = new Shell(display);
    shell.setText("Addressbook");
    shell.setLayout(new FillLayout());

    ListViewer listViewer = new ListViewer(shell, SWT.SINGLE);
    listViewer.setLabelProvider(new AddressBookLabelProvider());
    listViewer.setContentProvider(new AddressBookContentProvider());
    listViewer.setInput(new AddressBookModel());

    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }

    display.dispose();
  }
}
