/**
 * 
 * Copyright 2008 The Australian National University (ANU)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package au.edu.apsr.mtk.ch;

import java.util.Stack;

import javax.xml.parsers.DocumentBuilderFactory;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.apache.log4j.Logger;

import au.edu.apsr.mtk.base.Constants;

/**
 * A METS SAX handler for use where it is known FContent is not used.
 * 
 * This class takes any METS document and builds a 
 * DOM. Unlike the default METS content handler this handler does not
 * expect emdedded file content and simply builds a DOM without
 * alteration from the incoming events. This content handler should only
 * be used when it is certain no binData is present.
 * 
 * Ensure both http://xml.org/sax/features/namespaces and 
 * http://xml.org/sax/features/namespace-prefixes are set to true in the
 * SAXParserFactory object
 *
 * @author Scott Yeadon
 */
public class ExternalContentMETSHandler extends DefaultMETSHandler
{
    private static Logger log = Logger.getLogger(ExternalContentMETSHandler.class);

    /** the DOM document */
    private Document doc = null;
    
    /** Element stack to assist in building the DOM */
    private Stack<Element> elements = new Stack<Element>();    

    /** Locator (for future use) */
    private Locator locator;
        

    /**
     * Set the  locator
     * 
     * @param locator 
     *        The Locator object used to track the parsing location
     *        
     */ 
    public void setDocumentLocator(Locator locator)
    {
        this.locator = locator;
    }
        
        
    /**
     * Create an empty DOM document when the startDocument event is received
     * 
     * @exception SAXException
     *         
     */ 
    public void startDocument() throws SAXException
    {
        try
        {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        }
        catch (Exception e)
        {
            throw new SAXException(e);
        }
    }

    
    /**
     * Processing for the startElement event.
     * 
     * Create a DOM element and push it on the stack.
     * 
     * @param uri
     *      The element namespace
     * @param localName
     *      The unqualified element name
     * @param qName
     *      The qualified element name
     * @param attributes
     *      Attributes associated with the element
     * 
     * @exception SAXException
     *         
     */ 
    public void startElement(String uri,
                            String localName,
                            String qName,
                            Attributes attributes) throws SAXException
    {
        Element e = null;
        String prefix=null;
        
        if (uri.length()>0)
        {
            e = doc.createElementNS(uri,qName);
        }
        else
        {
            e = doc.createElement(qName);
        }
        
        for (int i=0; i<attributes.getLength(); i++)
        {
            e.setAttribute(attributes.getQName(i),attributes.getValue(i));
        }
        
        elements.push(e);
    }

    
    /**
     * Processing for characters.
     * 
     * All element's characters are simply echoed to
     * the DOM.
     * 
     * @param chars
     *      An array of characters
     * @param start
     *      The start position of the first in the array
     * @param length
     *      The length of the character data being passed
     * 
     * @exception SAXException
     *         
     */ 
    public void characters(char[] chars,
                           int start,
                           int length) throws SAXException
    {
        String s = new String(chars, start, length);
        if (!s.matches("\\s+"))
        {
            Element e = elements.peek();

            if (e.getTextContent().length()==0)
            {
                e.setTextContent(s);
            }
            else
            {
                e.setTextContent(e.getTextContent() + s);
            }
        }
    }
        
        
    /**
     * Processing for skipped entities.
     * 
     * To avoid loss of skipped entities just recreate them and pass
     * to the characters() method.
     * 
     * @param name
     *      The entity name
     * 
     * @exception SAXException
     *         
     */
    public void skippedEntity(String name) throws SAXException
    {
        String s = "&" + name + ";";
        char[] text = s.toCharArray();
        this.characters(text, 0, text.length);      
    }
        
        
    /**
     * Processing for the endElement event.
     * 
     * Popping the DOM element from the stack and inserting it into 
     * the DOM document.
     * 
     * @param uri
     *      The element namespace
     * @param localName
     *      The unqualified element name
     * @param qName
     *      The qualified element name
     * 
     * @exception SAXException
     *         
     */ 
    public void endElement(String uri,
                            String localName,
                            String qName) throws SAXException
    {
        Element e = elements.pop();
        if (!elements.empty())
        {
            elements.peek().appendChild(e);
        }
        else
        {
            doc.appendChild(e);
        }
    }

        
    /**
     * Print parser location. This may be used in future for debugging
     * purposes
     *  
     * @param s
     *      Parser message text
     *
     * @return String
     *      Parser message with line/column location information
     * 
     */
    private String printLocation(String s)
    { 
        int line = locator.getLineNumber();
        int column = locator.getColumnNumber();
        return s + " at line " + line + "; column " + column;
    }


    /**
     * Get the DOM document
     *  
     *  @return Document
     *      The DOM document. May be null if called before parsing and empty
     *      if parsing exception caught.
     */
    public Document getDocument()
    {
        return this.doc;
    }
}