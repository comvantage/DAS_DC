/*
 * Copyright 2014 SAP SE
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 	http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.comvantage.domainconfiguration.data;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;

//@Entity @IdClass(DomainRoleId.class)
@Entity
public class DomainRole {
	
	@Id
	private Long id;
	//@Id
	@Basic
	private Long client;
	@Basic
	private String name;
//	@ManyToMany
//	private Collection<DomainSPARULTemplate> templates;
	
	public DomainRole(){
//		templates = new ArrayList<DomainSPARULTemplate>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getClient() {
		return client;
	}

	public void setClient(Long client) {
		this.client = client;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public Collection<DomainSPARULTemplate> getTemplates() {
//		return templates;
//	}
//
//	public void setTemplates(Collection<DomainSPARULTemplate> templates) {
//		this.templates = templates;
//	}
	
//	public void addTemplate(DomainSPARULTemplate template) {
//		if (!getTemplates().contains(template)) {
//			getTemplates().add(template);
//		}
//		if (!template.getRoles().contains(this)) {
//			template.getRoles().add(this);
//		}
//	}
	
	public String toString() {
	    return "\n\nID:" + id + "\nName:" + name;
	}
}

//class DomainRoleId {
//	private int id;
//	private int client;
//}