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

import javax.persistence.*;

@Entity
@Table(name = "DomainSystemParameter")
public class DomainSystemParameter {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Basic
	private String pkey;
	@Basic
	private String pvalue;
	
	public DomainSystemParameter(){
	}
	
	public DomainSystemParameter(String key, String value){
		this.pkey = key;
		this.pvalue = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKey() {
		return pkey;
	}

	public void setKey(String pkey) {
		this.pkey = pkey;
	}

	public String getValue() {
		return pvalue;
	}

	public void setValue(String pvalue) {
		this.pvalue = pvalue;
	}
}