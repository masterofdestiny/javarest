package edu.hawaii.contactservice.server.db;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import edu.hawaii.contactservice.common.contact.Contact;

/**
 * Holds an in-memory representation of all Contacts present in this service. 
 * There is no persistent storage, so the Contacts disappear when this service ceases execution.
 * This collection needs to be thread-safe. 
 * @author Philip Johnson
 */
public class Contacts {
  /** Holds a map from unique IDs to the corresponding Contact instance. */
  private Map<String, Contact> uniqueID2Contact = new ConcurrentHashMap<String, Contact>();
  
  /** Create the singleton instance of this Contacts at class definition time. */
  private static Contacts theInstance = new Contacts();
  
  /** Ensure no clients can create an instance of this class. */
  private Contacts() {
    // do nothing. 
  }
  
  /** 
   * Return the single instance of this Contacts.
   * @return The Contacts instance. 
   */
  public static Contacts getInstance() {
    return theInstance;
  }
  
  /**
   * Add a new contact to our repository.
   * Note that if a contact with the same unique ID is already present, it is overridden. 
   * @param contact The contact to be added.
   */
  public synchronized void addContact(Contact contact) {
    uniqueID2Contact.put(contact.getUniqueID(), contact);
  }
  
  /**
   * Returns the Contact instance associated with uniqueID if one is present, or null if not 
   * found.
   * @param uniqueID The uniqueID to search for. 
   * @return The Contact instance or null.
   */
  public synchronized Contact getContact(String uniqueID) {
    return uniqueID2Contact.get(uniqueID);
  }
  
  /**
   * Ensures the Contact represented by uniqueID is no longer in the database. 
   * @param uniqueID The uniqueID to remove, if present. 
   */
  public synchronized void deleteContact(String uniqueID) {
    uniqueID2Contact.remove(uniqueID);
  }


  /**
   * Return a snapshot of the collection at the point in time that this was called.
   * @return A collection of Contact instances.  
   */
  public synchronized Collection<Contact> getContacts () {
    return uniqueID2Contact.values();
  }
}
