package edu.hawaii.contactservice.server.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.util.Collection;
import org.junit.Test;
import edu.hawaii.contactservice.common.contact.Contact;

/**
 * Simple unit testing of the Contacts collection class. 
 * @author Philip Johnson
 */
public class TestContacts {
  /**
   * Check to make sure the Contacts class can be manipulated correctly.
   */
  @Test
  public void testContactRepository() {
    // Get the singleton instance of the repository.
    Contacts contacts = Contacts.getInstance();

    // Add a contact to the repository.
    String uniqueID = "pmj";
    Contact contact = new Contact("Philip", "Johnson", "Professor", uniqueID);
    contacts.addContact(contact);

    // Get the contact out of the repository and make sure it's the same one.
    assertEquals("Checking retrieval", contact, contacts.getContact(uniqueID));

    // Check to see that retrieving an unknown contact returns null.
    assertNull("Checking unknown retrieval", contacts.getContact("foo"));
    
    // Get all of the contacts, see that there's only one.
    Collection<Contact> snapshot = contacts.getContacts();
    assertEquals("Checking snapshot", 1, snapshot.size());
    
    // Delete the contact, make sure it's gone.
    contacts.deleteContact(uniqueID);
    assertNull("Checking deleted contact", contacts.getContact(uniqueID));
  }
}
