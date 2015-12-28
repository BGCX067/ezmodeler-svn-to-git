package org.i4change.app.http;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import javax.xml.parsers.*;

public class ParseXMLBySAX extends DefaultHandler {
	
	private static final Log log = LogFactory.getLog(ParseXMLBySAX.class);
	
    /**
     * Verschachtelungstiefe der Tags, wird verwendet, um das XML-Dokument
     * formatiert auszugeben. 
     */
    private int level = 0;

    /**
     * Gibt <code>level</code> Tabs auf der Konsole aus.
     */
    public void indent()
    {
        // Mit Tabs einrücken
        for (int i=0;i<level;i++)
            log.debug("indent");
    }

    /**
     * Wird am Anfang des Dokuments aufgerufen, definiert im Interface ContentHandler.
     */
    public void startDocument() throws SAXException
    {
    	log.debug("Start of Document");
    }

    /**
     * Wird bei jedem öffnenden Tag aufgerufen, definiert im Interface ContentHandler.
     * Bei leeren Tags wie zum Beispiel &lt;img /&gt; wird startElement und
     * endElement direkt hintereinander aufgerufen. Mit J2SE 1.4.2 scheint nur
     * qName gefüllt zu sein.
     *
     * @param namespaceURI URI des Namespaces für dieses Element, kann auch ein leerer String sein.
     * @param localName Lokaler Name des Elements, kann auch ein leerer String sein.
     * @param qName Qualifizierter Name (mit Namespace-Prefix) des Elements.
     * @param atts Liste der Attribute.
     */
    public void startElement(String namespaceURI, String localName,
            String qName, Attributes atts) throws SAXException
    {

    	log.debug("<" + qName);

        // Test-Code um zu sehen, was in namespaceURI und localName steht
        // System.out.print(" " + namespaceURI);
        // System.out.print(" " + localName);

        // Attribute ausgeben
        for( int i = 0; i < atts.getLength(); i++ )
        	log.debug(" " + atts.getQName(i) + "=\"" + atts.getValue(i) + "\"");

        log.debug(">");

        level++;
    }

    /**
     * Wird bei jedem schließenden Tag aufgerufen, definiert im Interface ContentHandler.
     *
     * @param namespaceURI URI des Namespaces für dieses Element, kann auch ein leerer String sein.
     * @param localName Lokaler Name des Elements.
     * @param qName Qualifizierter Name des Elements.
     */
    public void endElement(String namespaceURI, String localName, String qName)
    {
        level--;

        indent();

        log.debug("</" + qName + ">");
    }

    /**
     * Wird immer aufgerufen, wenn Zeichen im Dokument auftauchen.
     *
     * @param ch Character Array
     * @param start Startindex der Zeichen in ch
     * @param length Länge der Zeichenkette
     */
    public void characters(char ch[], int start, int length)
    {
        String s = new String(ch,start,length).trim();
        if (s.length() > 0) {
            indent();
            log.debug(s);
        }
    }

    /**
     * Wird aufgerufen, wenn Leerraum (" ", "\t", "\n", "\r") im Dokument
     * auftaucht, der für die Struktur des Dokuments nicht von Bedeutung ist. 
     *
     * @param ch Character Array
     * @param start Startindex der Zeichen in ch
     * @param length Länge der Zeichenkette
     */
    public void ignorableWhitespace(char[] ch, int start, int length)
    {
    }
    
    
}
