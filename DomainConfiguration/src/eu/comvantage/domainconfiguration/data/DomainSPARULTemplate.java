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

//@Entity @IdClass(DomainSPARULTemplateId.class)
@Entity
public class DomainSPARULTemplate {
	
	@Id
	private Long id;
//	@Id
	@Basic
	private Long client;
	@Lob
	private String statement;
	@Basic
	private String name;
	@Basic
	private String source;
	@Basic
	private boolean enabled;
	@ManyToMany
	private Collection<DomainViewAction> viewactions;
	@ManyToMany
	private Collection<DomainRole> roles;
	
	public DomainSPARULTemplate(){
		viewactions = new ArrayList<DomainViewAction>();
		roles = new ArrayList<DomainRole>();
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

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Collection<DomainViewAction> getViewactions() {
		return viewactions;
	}

	public void setViewactions(Collection<DomainViewAction> viewactions) {
		this.viewactions = viewactions;
	}
	
	public void addViewaction(DomainViewAction viewaction) {
		if (!getViewactions().contains(viewaction)) {
			getViewactions().add(viewaction);
		}
//		if (!viewaction.getTemplates().contains(this)) {
//			viewaction.getTemplates().add(this);
//		}
	}
	
	public Collection<DomainRole> getRoles() {
		return roles;
	}

	public void setRoles(Collection<DomainRole> roles) {
		this.roles = roles;
	}

	public void addRole(DomainRole role) {
		if (!getRoles().contains(role)) {
			getRoles().add(role);
		}
//		if (!role.getTemplates().contains(this)) {
//			role.getTemplates().add(this);
//		}
	}
	
	public String toString() {
	    return "\n\nID:" + id + "\nStatement:" + statement + "\nEnabled:" + enabled;
	}
}

//class DomainSPARULTemplateId {
//	private int id;
//	private int client;
//}