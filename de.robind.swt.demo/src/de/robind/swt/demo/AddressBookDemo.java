package de.robind.swt.demo;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class AddressBookDemo {
  public static void main(String[] args) {
    Display display = new Display();

    Shell shell = new Shell(display);
    shell.setText("Addressbook");

    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }

    display.dispose();
  }
}
