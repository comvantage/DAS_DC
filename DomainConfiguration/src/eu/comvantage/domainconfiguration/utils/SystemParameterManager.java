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

package eu.comvantage.domainconfiguration.utils;

import java.util.List;

import eu.comvantage.domainconfiguration.DomainConfigurationManager;
import eu.comvantage.domainconfiguration.DomainConfigurationServiceImpl;
import eu.comvantage.domainconfiguration.data.DomainSystemParameter;

public final class SystemParameterManager {
	
	static public Long getDefaultClientIdOffset(){
		List<DomainSystemParameter> r1 = DomainConfigurationManager.getInstance().getDomainSystemParameterByKey("default_client_id_offset");
				
		if(r1.isEmpty()) {
			System.out.println("SystemParameterManager: Default client id offset not specified, fixed value used");
			return new Long(1000);
		}
		else {
			System.out.println("SystemParameterManager: Default client id offset set to "+r1.get(0).getValue());
			return Long.valueOf(r1.get(0).getValue());
		}
	}
	
	static public Long createIdWithOffsetByClientId(Long id, Long clientId) {
		return getDefaultClientIdOffset()*clientId + id;
	}
}