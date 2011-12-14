package de.robind.swt.demo.addressbook;

public class AddressBookModel {
  private Person personList[] = {
      createPerson("First1", "Last1"),
      createPerson("First2", "Last2"),
      createPerson("First3", "Last3")
  };

  public int getNumPersons() {
    return (this.personList.length);
  }

  public Person getPerson(int index) {
    if (index < 0 || index >= this.personList.length) {
      throw new IndexOutOfBoundsException("Index out of bounds: " + index);
    }

    return (this.personList[index]);
  }

  private static Person createPerson(String firstName, String lastName) {
    Person p = new Person();

    p.setFirstName(firstName);
    p.setLastName(lastName);

    return (p);
  }
}
