package de.robind.swt.demo.addressbook;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class AddressBookDetailsWidget extends Composite {
  public AddressBookDetailsWidget(Composite parent, int style) {
    super(parent, style);

    FormLayout layout = new FormLayout();
    layout.spacing = 5;
    layout.marginTop = 5;
    layout.marginRight = 5;
    setLayout(layout);

    Label firstNameLabel = new Label(this, SWT.LEFT);
    firstNameLabel.setText("First name:");
    FormData firstNameLabelData = new FormData();
    firstNameLabel.setLayoutData(firstNameLabelData);

    Text firstNameText = new Text(this, SWT.READ_ONLY);
    firstNameText.setText("First name");
    FormData firstNameTextData = new FormData();
    firstNameText.setLayoutData(firstNameTextData);

    Label lastNameLabel = new Label(this, SWT.LEFT);
    lastNameLabel.setText("Last name:");
    FormData lastNameLabelData = new FormData();
    lastNameLabel.setLayoutData(lastNameLabelData);

    Text lastNameText = new Text(this, SWT.READ_ONLY);
    lastNameText.setText("Last name");
    FormData lastNameTextData = new FormData();
    lastNameText.setLayoutData(lastNameTextData);


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
}
