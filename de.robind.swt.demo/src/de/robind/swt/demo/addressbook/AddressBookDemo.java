package de.robind.swt.demo.addressbook;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;

public class AddressBookDemo {
//  public static void main(String[] args) {
//    Display display = new Display();
//
//    Shell shell = new Shell(display);
//    shell.setText("Addressbook");
//
//    ListViewer listViewer = new ListViewer(
//        shell, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
//    listViewer.setLabelProvider(new AddressBookLabelProvider());
//    listViewer.setContentProvider(new AddressBookContentProvider());
//    listViewer.setInput(new AddressBookModel());
//
//    AddressBookDetailsWidget detailsWidget =
//        new AddressBookDetailsWidget(shell, SWT.NO_SCROLL);
//    listViewer.addSelectionChangedListener(detailsWidget);
//
//    SashBuilder.buildSash(shell, listViewer.getList(), detailsWidget);
//
//    shell.open();
//    while (!shell.isDisposed()) {
//      if (!display.readAndDispatch()) {
//        display.sleep();
//      }
//    }
//
//    display.dispose();
//  }

  public static void main(String[] args) {
    try {
      Display display = new Display();
      Shell shell = new Shell(display);
      new WidgetImpl(shell, SWT.DEFAULT);
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }

  static class WidgetImpl extends Widget {
    public WidgetImpl(Widget parent, int style) {
      super(parent, style);
    }
  }
}
