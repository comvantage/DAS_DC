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
import javax.persistence.*;

@Entity
@Table(name = "DomainSourceDetail")
public class DomainSourceDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Basic
	private String name;
	@Basic
	private String type;
	@Basic
	private String queryEndpointURL;
	@Basic
	private String updateEndpointURL;
	@Basic
	private String username;
	@Basic
	private String password;
	@Basic
	private ArrayList<String> content;
	
	public DomainSourceDetail(){
	}
	
	public DomainSourceDetail(String name, String queryEndpoint, String type){
		this.name = name;
		this.queryEndpointURL = queryEndpoint;
		this.type = type;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQueryEndpointURL() {
		return queryEndpointURL;
	}

	public void setQueryEndpointURL(String queryEndpointURL) {
		this.queryEndpointURL = queryEndpointURL;
	}

	public String getUpdateEndpointURL() {
		return updateEndpointURL;
	}

	public void setUpdateEndpointURL(String updateEndpointURL) {
		this.updateEndpointURL = updateEndpointURL;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<String> getContent() {
		return content;
	}

	public void setContent(ArrayList<String> content) {
		this.content = content;
	}
}