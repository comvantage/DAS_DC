
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

@XmlRootElement(name = "getDomainSourcesByTypeResponse", namespace = "http://domainconfiguration.comvantage.eu/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getDomainSourcesByTypeResponse", namespace = "http://domainconfiguration.comvantage.eu/")

public class GetDomainSourcesByTypeResponse {

    @XmlElement(name = "return")
    private eu.comvantage.domainconfiguration.data.DomainSourceDetail[] _return;

    public eu.comvantage.domainconfiguration.data.DomainSourceDetail[] getReturn() {
        return this._return;
    }

    public void setReturn(eu.comvantage.domainconfiguration.data.DomainSourceDetail[] new_return)  {
        this._return = new_return;
    }

}

