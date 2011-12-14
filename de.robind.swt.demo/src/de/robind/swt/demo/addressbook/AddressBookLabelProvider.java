package de.robind.swt.demo.addressbook;

import org.eclipse.jface.viewers.LabelProvider;

public class AddressBookLabelProvider extends LabelProvider {
  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
   */
  @Override
  public String getText(Object element) {
    Person person = (Person)element;
    return (person.getFirstName() + " " + person.getLastName());
  }
}
