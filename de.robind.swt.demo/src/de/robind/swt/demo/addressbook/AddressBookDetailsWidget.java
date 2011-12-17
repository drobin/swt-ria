package de.robind.swt.demo.addressbook;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class AddressBookDetailsWidget extends Composite
  implements ISelectionChangedListener {

  private Label firstNameLabel = null;
  private Text firstNameText = null;
  private Label lastNameLabel = null;
  private Text lastNameText = null;

  public AddressBookDetailsWidget(Composite parent, int style) {
    super(parent, style);

    FormLayout layout = new FormLayout();
    layout.spacing = 5;
    layout.marginTop = 5;
    layout.marginRight = 5;
    setLayout(layout);

    this.firstNameLabel = new Label(this, SWT.LEFT);
    this.firstNameLabel.setText("First name:");
    FormData firstNameLabelData = new FormData();
    this.firstNameLabel.setLayoutData(firstNameLabelData);

    this.firstNameText = new Text(this, SWT.READ_ONLY);
    FormData firstNameTextData = new FormData();
    this.firstNameText.setLayoutData(firstNameTextData);

    this.lastNameLabel = new Label(this, SWT.LEFT);
    this.lastNameLabel.setText("Last name:");
    FormData lastNameLabelData = new FormData();
    this.lastNameLabel.setLayoutData(lastNameLabelData);

    this.lastNameText = new Text(this, SWT.READ_ONLY);
    FormData lastNameTextData = new FormData();
    this.lastNameText.setLayoutData(lastNameTextData);


    firstNameLabelData.left = new FormAttachment(0, 0);
    firstNameLabelData.top = new FormAttachment(0, 0);

    firstNameTextData.left = new FormAttachment(firstNameLabel);
    firstNameTextData.top = new FormAttachment(0, 0);
    firstNameTextData.right = new FormAttachment(100, 0);

    lastNameLabelData.left = new FormAttachment(0, 0);
    lastNameLabelData.top = new FormAttachment(firstNameLabel);

    lastNameTextData.left = new FormAttachment(lastNameLabel);
    lastNameTextData.top = new FormAttachment(firstNameText);
    lastNameTextData.right = new FormAttachment(100, 0);
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
   */
  public void selectionChanged(SelectionChangedEvent e) {
    IStructuredSelection selection = (IStructuredSelection)e.getSelection();

    if (selection.isEmpty()) {
      this.firstNameText.setText("");
      this.lastNameText.setText("");
    } else {
      Person person = (Person)selection.getFirstElement();
      this.firstNameText.setText(person.getFirstName());
      this.lastNameText.setText(person.getLastName());
    }

  }
}