package de.freerider.datamodel;


// import java.util.*;

/**
 * Class for entity type Customer. Customer is an individual who acts as holder of a business relationship.
 */
public class Customer {

    // /**
    //  * Default constructor
    //  */
    // public Customer() {
    // }

    // /**
    //  * id attribute, {@code < 0} invalid, can be set only once.
    //  */
    // private long id;

    // /**
    //  * surname, never null, mapped to "" when empty.
    //  */
    // private String lastName;

    // /**
    //  * none-surname name parts, never null, mapped to "" when empty.
    //  */
    // private String firstName;

    // /**
    //  * contact information with multiple contact entries.
    //  */
    // private List<String> contacts;

    // /**
    //  * status information of a Customer.
    //  */
    // private enum Status status;

    // /**
    //  * Id getter.
    //  * @return customer id, may be invalid {@code < 0} if unassigned.
    //  */
    // public long getId() {
    //     // TODO implement here
    //     return 0;
    // }

    // /**
    //  * Id setter. Id can only be set once, id is immutable after being set.
    //  * @param id set id once if id is valid {@code >= 0} and id attribute is still unassigned {@code < 0}.
    //  * @return chainable self-reference.
    //  */
    // public Customer setId(long id) {
    //     // TODO implement here
    //     return null;
    // }

    // /**
    //  * Getter that returns single-String name from lastName and firstName attributes in format: {@code "lastName, firstName"} or {@code "lastName"} if {@code firstName} is empty.
    //  * @return single-String name.
    //  */
    // public String getName() {
    //     // TODO implement here
    //     return "";
    // }

    // /**
    //  * FirstName getter.
    //  * @return value of firstName attribute, never null, mapped to "" when empty.
    //  */
    // public String getFirstName() {
    //     // TODO implement here
    //     return "";
    // }

    // /**
    //  * LastName getter.
    //  * @return value of lastName attribute, never null, mapped to "" when empty.
    //  */
    // public String getLastName() {
    //     // TODO implement here
    //     return "";
    // }

    // /**
    //  * Setter that splits single-String name (e.g. "Eric Meyer") into first- and lastName parts and assigns parts to corresponding first- and lastName attributes.
    //  * @param name single-String name to split into first- and lastName parts.
    //  * @return chainable self-reference.
    //  */
    // public Customer setName(String name) {
    //     // TODO implement here
    //     return null;
    // }

    // /**
    //  * Name setter for first- and lastName attributes, which are only changed when arguments are not null or not empty "".
    //  * @param first assigned to firstName attribute when not null or empty "".
    //  * @param last assigned to lastName attribute when not null or empty "".
    //  * @return chainable self-reference.
    //  */
    // public Customer setName(String first, String last) {
    //     // TODO implement here
    //     return null;
    // }

    // /**
    //  * Return number of contacts.
    //  * @return number of contacts.
    //  */
    // public int contactsCount() {
    //     // TODO implement here
    //     return 0;
    // }

    // /**
    //  * Contacts getter (as {@code Iterable<String>}).
    //  * @return contacts as {@code Iterable<String>}.
    //  */
    // public Iterable<String> getContacts() {
    //     // TODO implement here
    //     return null;
    // }

    // /**
    //  * Add new contact. Only valid contacts (not null or "") are stored. Duplicate contacts are ignored.
    //  * @param contact contact to add, null, "" or duplicate contacts are ignored.
    //  * @return chainable self-reference.
    //  */
    // public Customer addContact(String contact) {
    //     // TODO implement here
    //     return null;
    // }

    // /**
    //  * Delete the i-th contact if {@code i >= 0} and {@code i < contacts.size()}, otherwise method has no effect.
    //  * @param i index of contact to delete.
    //  * @return
    //  */
    // public void deleteContact(int i) {
    //     // TODO implement here
    //     return null;
    // }

    // /**
    //  * Delete all contacts.
    //  * @return
    //  */
    // public void deleteAllContacts() {
    //     // TODO implement here
    //     return null;
    // }

    // /**
    //  * Status getter.
    //  * @return status of customer as defined in enum Status.
    //  */
    // public Customer.Status getStatus() {
    //     // TODO implement here
    //     return null;
    // }

    // /**
    //  * Status setter.
    //  * @param status customer status as defined in enum Status.
    //  * @return chainable self-reference.
    //  */
    // public Customer setStatus(Customer.Status status) {
    //     // TODO implement here
    //     return null;
    // }

    // /**
    //  * Definition of Customer Status states.
    //  */
    // public enum Customer.Status {
    //     New,
    //     InRegistration,
    //     Active,
    //     Suspended,
    //     Deleted
    // }

}