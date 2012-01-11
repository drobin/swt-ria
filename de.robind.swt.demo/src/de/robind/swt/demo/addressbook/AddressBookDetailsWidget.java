package de.robind.swt.demo.addressbook;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class AddressBookDetailsWidget extends Composite
  implements ISelectionChangedListener {

  private Label headlineLabel = null;
  private Label firstNameLabel = null;
  private Text firstNameText = null;
  private Label lastNameLabel = null;
  private Text lastNameText = null;

  public AddressBookDetailsWidget(Composite parent, int style) {
    super(parent, style);

    FormData data;

    FormLayout layout = new FormLayout();
    layout.spacing = 5;
    layout.marginTop = 5;
    layout.marginRight = 5;
    setLayout(layout);

    this.headlineLabel = new Label(this, SWT.CENTER);
    updateFont(this.headlineLabel, SWT.BOLD, 10);

    data = new FormData();
    data.left = new FormAttachment(0, 0);
    data.top = new FormAttachment(0, 0);
    data.right = new FormAttachment(100, 0);
    this.headlineLabel.setLayoutData(data);

    this.firstNameLabel = new Label(this, SWT.LEFT);
    this.firstNameLabel.setText("First name:");

    data = new FormData();
    data.left = new FormAttachment(0, 0);
    data.top = new FormAttachment(this.headlineLabel);
    this.firstNameLabel.setLayoutData(data);

    this.firstNameText = new Text(this, SWT.READ_ONLY);

    data = new FormData();
    data.left = new FormAttachment(firstNameLabel);
    data.top = new FormAttachment(this.headlineLabel);
    data.right = new FormAttachment(100, 0);
    this.firstNameText.setLayoutData(data);

    this.lastNameLabel = new Label(this, SWT.LEFT);
    this.lastNameLabel.setText("Last name:");

    data = new FormData();
    data.left = new FormAttachment(0, 0);
    data.top = new FormAttachment(firstNameLabel);
    this.lastNameLabel.setLayoutData(data);

    this.lastNameText = new Text(this, SWT.READ_ONLY);

    data = new FormData();
    data.left = new FormAttachment(lastNameLabel);
    data.top = new FormAttachment(firstNameText);
    data.right = new FormAttachment(100, 0);
    this.lastNameText.setLayoutData(data);
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
   */
  public void selectionChanged(SelectionChangedEvent e) {
    IStructuredSelection selection = (IStructuredSelection)e.getSelection();

    if (selection.isEmpty()) {
      this.headlineLabel.setText("");
      this.firstNameText.setText("");
      this.lastNameText.setText("");
    } else {
      Person person = (Person)selection.getFirstElement();
      this.headlineLabel.setText(person.getFirstName() + " " + person.getLastName());
      this.firstNameText.setText(person.getFirstName());
      this.lastNameText.setText(person.getLastName());
    }
  }

  private static void updateFont(Control control, int style, int heightAdd) {
    FontData[] fontData = control.getFont().getFontData();
    for (int i = 0; i < fontData.length; i++) {
      fontData[i].setStyle(style);
      fontData[i].setHeight(fontData[i].getHeight() + heightAdd);
    }

    // Dispose old fond, not used anymore
    control.getFont().dispose();

    // Also dispose newFont because we create it
    final Font newFont = new Font(control.getDisplay(), fontData);
    control.setFont(newFont);
    control.addDisposeListener(new DisposeListener() {
      public void widgetDisposed(DisposeEvent e) {
          newFont.dispose();
      }
    });
  }
}
