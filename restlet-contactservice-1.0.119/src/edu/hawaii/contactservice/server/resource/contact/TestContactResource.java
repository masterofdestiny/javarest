package edu.hawaii.contactservice.server.resource.contact;

import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import edu.hawaii.contactservice.common.contact.Contact;
import edu.hawaii.contactservice.server.ContactServer;

/**
 * Tests the operations supported for the ContactService Contact resource.
 * @author Philip Johnson
 */
public class TestContactResource {

  /** The port used for testing. */
  private static int testPort = 8112;

  /**
   * Start up a test server before testing any of the operations on this resource.
   * @throws Exception If problems occur starting up the server. 
   */
  @BeforeClass
  public static void startServer () throws Exception {
    ContactServer.runServer(testPort);
  }
  
  /**
   * Tests that we cannot retrieve an unknown Contact ID without throwing an exception.
   * @throws Exception If no exception, Houston we have a problem.
   */
  @Test(expected = ResourceException.class)
  public void testUnknownContact() throws Exception {
    String testUrl = String.format("http://localhost:%s/contactserver/contact/foo", testPort);
    ClientResource client = new ClientResource(testUrl);
    client.get();
  }
  
  /**
   * Test the cycle of putting a new Contact on the server, retrieving it, then deleting it.
   * @throws Exception If problems occur.
   */
  @Test
  public void testAddContact() throws Exception {
    // Construct the URL to test.
    String uniqueID = "DS";
    String testUrl = String.format("http://localhost:%s/contactserver/contact/%s", testPort,
        uniqueID);
    ClientResource client = new ClientResource(testUrl);
    
    // Construct the payload: an XML representation of a Contact.
    Contact contact = new Contact("Dan", "Suthers", "Professor", uniqueID);
    DomRepresentation representation = new DomRepresentation();
    representation.setDocument(contact.toXml());
    
    // Now put the Contact to the server. 
    client.put(representation);
    
    // Let's now try to retrieve the Contact instance we just put on the server. 
    DomRepresentation representation2 = new DomRepresentation(client.get());
    Contact contact2 = new Contact(representation2.getDocument());
    assertEquals("Checking retrieved contact's ID", uniqueID, contact2.getUniqueID());
    
    // Now let's get rid of the sucker.
    client.delete();
    
    // Make sure it's really gone.
    try {
      client.get();
      throw new Exception("Eek! We got a deleted Contact!");
    }
    catch (Exception e) { //NOPMD
      // It's all G. 
    }
  }
}
