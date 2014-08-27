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

package eu.comvantage.domainconfiguration;

import java.util.List;
import javax.jws.WebParam;
import javax.jws.WebService;

import eu.comvantage.domainconfiguration.data.DomainRole;
import eu.comvantage.domainconfiguration.data.DomainSPARULTemplate;
import eu.comvantage.domainconfiguration.data.DomainSourceDetail;
import eu.comvantage.domainconfiguration.data.DomainSystemParameter;
import eu.comvantage.domainconfiguration.data.DomainViewAction;

@WebService(
		targetNamespace = "http://domainconfiguration.comvantage.eu/",
		portName = "DomainConfigurationServiceImplPort", 
		serviceName = "DomainConfigurationServiceImplService")
public class DomainConfigurationServiceImpl {

	//***TEST METHODS***//
	
	public String wstestSingle(@WebParam(name = "testparam") String text) {
		System.out.println("Test (single) method called!");
		return "Successfully received input = " + text;
	}
	
	public String[] wstestComplex() {
		System.out.println("Test (complex) method called!");
		String[] test = {"001","002","003"};
		return test;
	}
	
	public DomainSourceDetail[] wstestSuperComplex() {
		System.out.println("Test (complex) method called!");
		DomainSourceDetail s1 = new DomainSourceDetail("Virtuoso", "http://ddrsl001x4.drss.sap.corp:8890/sparql", "sparql");
		DomainSourceDetail s2 = new DomainSourceDetail("Any", "http://endpoint", "other");
		DomainSourceDetail[] out = {s1,s2};
		return out;
	}
	
	public String getVersion() {
		System.out.println("getVersion called!");
		return "1.0";
	}
	
	//***WEB METHODS***//
	// get all methods
	public DomainSourceDetail[] getAllDomainSources() {
		System.out.println("getAllDomainSources called!");
		return toDomainSourceDetailDTO(DomainConfigurationManager.getInstance().getAllDomainSources());
	}
	
	public DomainSystemParameter[] getAllDomainSystemParameters() {
		System.out.println("getAllDomainSystemParameters called!");
		return toDomainSystemParameterDTO(DomainConfigurationManager.getInstance().getAllDomainSystemParameters());
	}
	
	public DomainSPARULTemplate[] getAllDomainSPARULTemplates() {
		System.out.println("getAllDomainSPARULTemplates called!");
		return toDomainSPARULTemplateDTO(DomainConfigurationManager.getInstance().getAllDomainSPARULTemplates());
	}
	
	public DomainRole[] getAllDomainRoles() {
		System.out.println("getAllDomainRoles called!");
		return toDomainRoleDTO(DomainConfigurationManager.getInstance().getAllDomainRoles());
	}
	
	public DomainViewAction[] getAllDomainViewActions() {
		System.out.println("getAllDomainViewActions called!");
		return toDomainViewActionDTO(DomainConfigurationManager.getInstance().getAllDomainViewActions());
	}
	
	// get specific methods
	public DomainSourceDetail[] getDomainSourcesByType(@WebParam(name = "type") String type) {
		System.out.println("getDomainSourceByType called!");
		
		return toDomainSourceDetailDTO(DomainConfigurationManager.getInstance().getDomainSourcesByType(type));
	}
	
	public DomainSourceDetail[] getDomainSourcesByContent(@WebParam(name = "contents") String[] contents) {
		System.out.println("getDomainSourceByContent called!");
		
		return toDomainSourceDetailDTO(DomainConfigurationManager.getInstance().getDomainSourcesByContent(contents));
	}
	
	public DomainSourceDetail getDomainSourcesByName(@WebParam(name = "name") String name) {
		System.out.println("getDomainSourcesByName called!");
		
		return DomainConfigurationManager.getInstance().getDomainSourceByName(name);
	}
	
	public DomainSourceDetail getDomainSourcesById(@WebParam(name = "id") Long id) {
		System.out.println("getDomainSourcesById called!");
		
		return DomainConfigurationManager.getInstance().getDomainSourceById(id);
	}
	
	public DomainSystemParameter[] getDomainSystemParametersByKey(@WebParam(name = "key") String key) {
		System.out.println("getDomainSystemParametersByKey called!");
		return toDomainSystemParameterDTO(DomainConfigurationManager.getInstance().getDomainSystemParameterByKey(key));
	}
	
	public DomainSPARULTemplate getDomainSPARULTemplateById(@WebParam(name = "templateId") Long templateId, @WebParam(name = "clientId") Long clientId) {
		System.out.println("getDomainSPARULTemplateById called!");
		return DomainConfigurationManager.getInstance().getDomainSPARULTemplateById(templateId,clientId);
	}
	
	//verify state actions
	public boolean hasUseRightsForSPARULTemplate(@WebParam(name = "userRole") String userRole, @WebParam(name = "templateId") Long templateId, @WebParam(name = "clientId") Long clientId) {
		System.out.println("hasUseRightsForSPARULTemplate called!");
		return DomainConfigurationManager.getInstance().hasUseRightsForSPARULTemplate(userRole, templateId, clientId);
	}
	
	// add methods
	public void addDomainSource(@WebParam(name = "source") DomainSourceDetail d) {
		DomainConfigurationManager.getInstance().addDomainSource(d);
	}
	
	public void addDomainSystemParameter(@WebParam(name = "param") DomainSystemParameter p) {
		DomainConfigurationManager.getInstance().addDomainSystemParameter(p);
	}
	
	// delete methods
	public void deleteDomainSource(@WebParam(name = "source") DomainSourceDetail d) {
		DomainConfigurationManager.getInstance().deleteDomainSource(d);
	}
	
	public void deleteDomainSystemParameter(@WebParam(name = "param") DomainSystemParameter p) {
		DomainConfigurationManager.getInstance().deleteDomainSystemParameter(p);
	}
	
	// clear methods
	public void clearDomainConfiguration() {
		DomainConfigurationManager.getInstance().clearDomainConfiguration();
	}
	
	// DTO generation helper methods
	private DomainSourceDetail[] toDomainSourceDetailDTO(List<DomainSourceDetail> in){
		DomainSourceDetail[] out = new DomainSourceDetail[in.size()];
		for(int i=0;i<in.size();i++){
			out[i] = in.get(i);
		}
		return out;
	}
	
	private DomainSystemParameter[] toDomainSystemParameterDTO(List<DomainSystemParameter> in){
		DomainSystemParameter[] out = new DomainSystemParameter[in.size()];
		for(int i=0;i<in.size();i++){
			out[i] = in.get(i);
		}
		return out;
	}
	
	private DomainSPARULTemplate[] toDomainSPARULTemplateDTO(List<DomainSPARULTemplate> in){
		DomainSPARULTemplate[] out = new DomainSPARULTemplate[in.size()];
		for(int i=0;i<in.size();i++){
			out[i] = in.get(i);
		}
		return out;
	}
	
	private DomainRole[] toDomainRoleDTO(List<DomainRole> in){
		DomainRole[] out = new DomainRole[in.size()];
		for(int i=0;i<in.size();i++){
			out[i] = in.get(i);
		}
		return out;
	}
	
	private DomainViewAction[] toDomainViewActionDTO(List<DomainViewAction> in){
		DomainViewAction[] out = new DomainViewAction[in.size()];
		for(int i=0;i<in.size();i++){
			out[i] = in.get(i);
		}
		return out;
	}
}