
package eu.comvantage.domainconfiguration.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.6.1
 * Thu May 15 18:19:19 CEST 2014
 * Generated source version: 2.6.1
 */

@XmlRootElement(name = "hasUseRightsForSPARULTemplate", namespace = "http://domainconfiguration.comvantage.eu/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "hasUseRightsForSPARULTemplate", namespace = "http://domainconfiguration.comvantage.eu/", propOrder = {"userRole", "templateId", "clientId"})

public class HasUseRightsForSPARULTemplate {

    @XmlElement(name = "userRole")
    private java.lang.String userRole;
    @XmlElement(name = "templateId")
    private java.lang.Long templateId;
    @XmlElement(name = "clientId")
    private java.lang.Long clientId;

    public java.lang.String getUserRole() {
        return this.userRole;
    }

    public void setUserRole(java.lang.String newUserRole)  {
        this.userRole = newUserRole;
    }

    public java.lang.Long getTemplateId() {
        return this.templateId;
    }

    public void setTemplateId(java.lang.Long newTemplateId)  {
        this.templateId = newTemplateId;
    }

    public java.lang.Long getClientId() {
        return this.clientId;
    }

    public void setClientId(java.lang.Long newClientId)  {
        this.clientId = newClientId;
    }

}
