package de.robind.swt.demo;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ButtonDemo {
  public static void main(String[] args) {
    Display display = new Display();

    Shell shell = new Shell(display);
    shell.setText("Button Demo");

    Button button = new Button (shell, SWT.CENTER);
    button.setText("Click me");

    FillLayout layout = new FillLayout();
    shell.setLayout(layout);

    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }

    display.dispose ();
  }
}
