
package eu.comvantage.domainconfiguration.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.6.1
 * Thu May 15 18:19:18 CEST 2014
 * Generated source version: 2.6.1
 */

@XmlRootElement(name = "getVersionResponse", namespace = "http://domainconfiguration.comvantage.eu/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getVersionResponse", namespace = "http://domainconfiguration.comvantage.eu/")

public class GetVersionResponse {

    @XmlElement(name = "return")
    private java.lang.String _return;

    public java.lang.String getReturn() {
        return this._return;
    }

    public void setReturn(java.lang.String new_return)  {
        this._return = new_return;
    }

}
