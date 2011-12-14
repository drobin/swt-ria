package de.robind.swt.demo.addressbook;

import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class AddressBookContentProvider
  implements IContentProvider, IStructuredContentProvider {

  private Person personList[] = null;

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
   */
  public Object[] getElements(Object inputElement) {
    if (this.personList == null) {
      AddressBookModel model = (AddressBookModel)inputElement;
      this.personList = new Person[model.getNumPersons()];

      for (int i = 0; i < this.personList.length; i++) {
        this.personList[i] = model.getPerson(i);
      }
    }

    return (this.personList);
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.IContentProvider#dispose()
   */
  public void dispose() {
    this.personList = null;

  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
   */
  public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
  }
}
