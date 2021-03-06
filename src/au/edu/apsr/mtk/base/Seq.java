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
package au.edu.apsr.mtk.base;

import java.util.List;

import org.w3c.dom.Node;

/**
 * Class representing the METS seq element
 * 
 * @author Scott Yeadon
 *
 */
public class Seq extends METSElement
{
    private Par par = null;
    private Area area = null;
    
    
    /**
     * Construct a METS seq
     * 
     * @param n
     *        A w3c Node, typically an Element
     * 
     * @exception METSException
     */ 
    public Seq(Node n) throws METSException
    {
        super(n, Constants.ELEMENT_SEQ);
    }
    
    
    /**
     * Return an empty Par.
     * 
     * The returned object has no properties or content and is not part
     * of the METS document, it is essentially a constructor of a METS element
     * which is owned by the METS document. The returned object needs to be
     * "filled out" (e.g. with id, additional sub-elements, etc) before being
     * added to the METS document.
     * 
     * @exception METSException
     *
     */
    public Par newPar() throws METSException
    {
        return new Par(this.newElement(Constants.ELEMENT_PAR));
    }

    
    /**
     * Return an empty Area.
     * 
     * The returned object has no properties or content and is not part
     * of the METS document, it is essentially a constructor of a METS element
     * which is owned by the METS document. The returned object needs to be
     * "filled out" (e.g. with id, additional sub-elements, etc) before being
     * added to the METS document.
     * 
     * @exception METSException
     *
     */
    public Area newArea() throws METSException
    {
        return new Area(this.newElement(Constants.ELEMENT_AREA));
    }
    
    
    /**
     * Obtain the ID
     * 
     * @return String 
     *      The ID attribute value or empty string if attribute
     *      is empty or not present
     */  
    public String getID()
    {
        return super.getAttributeValue(Constants.ATTRIBUTE_ID);
    }
    

    /**
     * Set the ID
     * 
     * @param id 
     *      The ID attribute value
     */
    public void setID(String id)
    {
        super.setAttributeValue(Constants.ATTRIBUTE_ID, id);
    }    

    
    /**
     * Remove the ID attribute
     */
    public void removeID()
    {
        super.removeAttribute(Constants.ATTRIBUTE_ID);
    }
    
    
    /**
     * Obtain a par object if available
     * 
     * @return Par 
     *      a Par object or <code>null</code> if no par
     *      elements exist
     */  
    public Par getPar() throws METSException
    {
        if (par == null)
        {
            List<Node> l = super.getChildElements(Constants.ELEMENT_PAR);

            if (l.size() == 1)
            {
                par = new Par(l.get(0));
            }
        }
        
        return par;
    }
    
    
    /**
     * Set the par object
     * 
     * @param newPar 
     *      a Par object
     */  
    public void setPar(Par newPar)
    {
        par = newPar;
        this.getElement().appendChild(par.getElement());
    }
    
    
    /**
     * Obtain an area object if available
     * 
     * @return Area 
     *      an Area object or <code>null</code> if no area
     *      elements exist
     */  
    public Area getArea() throws METSException
    {
        if (area == null)
        {
            List<Node> l = super.getChildElements(Constants.ELEMENT_AREA);

            if (l.size() == 1)
            {
                area = new Area(l.get(0));
            }
        }
        
        return area;
    }
    
    
    /**
     * Set the area object
     * 
     * @param newArea 
     *      an Area object
     */  
    public void setArea(Area newArea)
    {
        area = newArea;
        this.getElement().appendChild(area.getElement());
    }
}