package de.robind.swt.demo;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ButtonDemo {
  public static void main(String[] args) {
    Display display = new Display();

    Shell shell = new Shell(display);
    shell.setText("Button Demo");

    GridLayout layout = new GridLayout(2, false);
    shell.setLayout(layout);

    Button button = new Button (shell, SWT.PUSH);
    button.setText("Click me");
    button.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

    Label label = new Label(shell, SWT.LEAD);
    label.setText("# of clicks:");
    label.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));

    final Text text = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
    text.setText("0");
    text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

    button.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        int numClicked = Integer.valueOf(text.getText());
        text.setText(Integer.toString(numClicked + 1));
      }
    });

    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }

    display.dispose ();
  }
}
