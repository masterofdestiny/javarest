package edu.hawaii.contactservice.common.contact;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.w3c.dom.Document;

/**
 * Tests the Contact class to see that XML representation works correctly.
 * @author Philip Johnson
 */
public class TestContact {
  
  /**
   * Check to make sure we can create Contacts, convert them to XML, and convert an XML
   * representation of a Contact into a Contact instance. 
   * @throws Exception If problems occur. 
   */
  @Test
  public void testContact() throws Exception {
    // Create a simple Contact instance. 
    Contact contact1 = new Contact("First", "Last", "Info", "ID");
    // Get its XML representation.
    Document contactXML = contact1.toXml();
    // Make a new contact from the XML representation of the first one. 
    Contact contact2 = new Contact(contactXML);
    // See that the two contacts are equal.
    assertEquals("Checking contact1 = contact2", contact1.toString(), contact2.toString());
  }
}
