package edu.hawaii.contactservice.common.contact;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Provides a simple record of information about a person, as well as an XML representation.
 * @author Philip Johnson
 */
public class Contact {
  /** The contact's first name. */
  private String firstName;
  /** The contact's last name. */
  private String lastName;
  /** All info about the contact. */
  private String info;
  /** The contact's unique ID. */
  private String uniqueID;
  /** The unique ID element name. */
  private static final String uniqueIDElementName = "unique-ID";
  /** The first name element name. */
  private static final String firstNameElementName = "first-name";
  /** The last name element name. */
  private static final String lastNameElementName = "last-name";
  /** The info element name. */
  private static final String infoElementName = "info";
  
  /**
   * Creates a Contact instance given its field values as strings. 
   * @param firstName The first name.
   * @param lastName The last name.
   * @param info The info.
   * @param uniqueID The uniqueID. 
   */
  public Contact (String firstName, String lastName, String info, String uniqueID) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.info = info;
    this.uniqueID = uniqueID;
  }
  
  /**
   * Creates a Contact instance given its representation in XML.
   * @param doc The XML document. 
   */
  public Contact(Document doc) {
    this.firstName = getElementTextContent(doc, firstNameElementName);
    this.lastName = getElementTextContent(doc, lastNameElementName);
    this.info = getElementTextContent(doc, infoElementName);
    this.uniqueID = getElementTextContent(doc, uniqueIDElementName);
  }
  
  /**
   * Returns the unique ID associated with this contact.
   * @return The unique ID.
   */
  public String getUniqueID() {
    return this.uniqueID;
  }
  
  /**
   * Return this contact as a formatted string.
   * @return The contact as a string. 
   */
  @Override
  public String toString() {
    return String.format("[ID %s: Name: %s %s Info: %s]", this.uniqueID, this.firstName, 
        this.lastName, this.info);
  }
  
  /**
   * Returns true if the other object is a Contact and if its uniqueID matches this one's.
   * @param other The other object.
   * @return True if the two objects are contacts and have matching unique IDs.
   */
  @Override 
  public boolean equals(Object other) {
    return 
    (other instanceof edu.hawaii.contactservice.common.contact.Contact) &&
    (this.uniqueID.equals(((Contact) other).uniqueID));
  }
  
  /**
   * Returns the hashCode of the uniqueID field, which is the only field used to determine
   * equality among Contact objects. 
   * @return The hashcode.
   */
  @Override 
  public int hashCode() {
    return this.uniqueID.hashCode();
  }
  
  
  /**
   * Returns this contact as an XML Document instance. 
   * For example:
   * <pre>
   * {@code
   * <contact>
   *   <first-name>Philip</first-name>
   *   <last-name>Johnson</last-name>
   *   <info>Professor</info>
   *   <unique-ID>pmj</unique-ID>
   * </contact>
   * }
   * </pre>
   * @return This contact as XML.
   * @throws Exception If problems occur creating the XML.
   */
  public Document toXml() throws Exception {
    // Create the Document instance representing this XML.
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.newDocument();
    // Create and attach the root element <contact>.
    Element rootElement = doc.createElement("contact");
    doc.appendChild(rootElement);
    // Now create and attach the fields for this contact.
    attachElement(doc, rootElement, uniqueIDElementName, this.uniqueID);
    attachElement(doc, rootElement, firstNameElementName, this.firstName);
    attachElement(doc, rootElement, lastNameElementName, this.lastName);
    attachElement(doc, rootElement, infoElementName, this.info);
    return doc;
  }

  /**
   * Helper function that creates a child element and attaches it to the passed parent element.
   * @param doc The document for creating elements. 
   * @param parent The parent element. 
   * @param childName The name of the child element.
   * @param childValue The text value for the child element. 
   */
  private void attachElement(Document doc, Element parent, String childName, String childValue) {
    Element childElement = doc.createElement(childName);
    childElement.setTextContent(childValue);
    parent.appendChild(childElement);
  }
  
  /**
   * Helper method that returns the text content of an interior element of this XML document. 
   * @param doc The XML document. 
   * @param elementName The element name whose text content is to be retrieved.
   * @return The text content
   */
  private String getElementTextContent(Document doc, String elementName) {
    Element element = (Element) doc.getElementsByTagName(elementName).item(0);
    return element.getTextContent();
  }
}
