package edu.hawaii.contactservice.client;

import org.restlet.ext.xml.DomRepresentation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import edu.hawaii.contactservice.common.contact.Contact;

/**
 * A simple example of a "client" class for the DateServer service. 
 * @author Philip Johnson
 */
public class ContactClient {
  /**
   * Connect to a running ContactService and perform the requested operation. 
   * @param args The first arg is the host (such as "http://localhost:8112/").
   * The second arg is the operation, either "put", "get", or "delete". 
   * The third arg is the unique ID of the contact.
   * If the operation is a "put", then there must be three more arguments for 
   * first name, last name, and info. 
   * @throws Exception If problems occur.
   */
  public static void main(String[] args) throws Exception {
    // Make sure we were at least passed the minimum number of arguments. 
    if (args.length < 3) {
      System.out.println("Usage: <host> <operation> <uniqueID> {optional args}");
      System.out.println("Example host: http://localhost:8112");
      return;
    }
    // (1) Figure out the URL from the args and make the ClientResource.
    String host = args[0];
    String contextRoot = "/contactserver/";
    String resource = "contact/";
    String uniqueID = args[2];
    String url = host + contextRoot + resource + uniqueID;
    ClientResource client = new ClientResource(url);

    // (2) Now do the right thing depending on the operation.
    String operation = args[1];
    try {
      if ("get".equals(operation)) {
        DomRepresentation representation = new DomRepresentation(client.get());
        Contact contact = new Contact(representation.getDocument());
        System.out.println(contact.toString());
      }
      else if ("delete".equals(operation)) {
        client.delete();
      }
      else if ("put".equals(operation)) {
        Contact contact = new Contact(args[3], args[4], args[5], uniqueID);
        DomRepresentation representation = new DomRepresentation();
        representation.setDocument(contact.toXml());
        client.put(representation);
      }
    }
    catch (ResourceException e) {
      // If the operation didn't succeed, indicate why here.
      System.out.println("Error: " + e.getStatus());
    }
  }
}

