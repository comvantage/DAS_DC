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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.eclipse.persistence.tools.schemaframework.SchemaManager;

import eu.comvantage.domainconfiguration.data.DomainRole;
import eu.comvantage.domainconfiguration.data.DomainSPARULTemplate;
import eu.comvantage.domainconfiguration.data.DomainSourceDetail;
import eu.comvantage.domainconfiguration.data.DomainSystemParameter;
import eu.comvantage.domainconfiguration.data.DomainViewAction;
import eu.comvantage.domainconfiguration.utils.SystemParameterManager;

public final class DomainConfigurationManager {

	private static DomainConfigurationManager instance;
	private static EntityManagerFactory emf;
	private static EntityManager em;
	
	private DomainConfigurationManager() {
	}
	
	public synchronized static DomainConfigurationManager getInstance() {
		if(instance == null) {
			instance = new DomainConfigurationManager();
			instance.initPersistence();
		}
		return instance;
	}
	
	//add actions
	public void addDomainSource(DomainSourceDetail source) {
		em.getTransaction().begin();
		em.persist(source);
		em.getTransaction().commit();
	}
	
	public void addDomainSystemParameter(DomainSystemParameter param) {
		em.getTransaction().begin();
		em.persist(param);
		em.getTransaction().commit();
	}
	
	public void addRole(DomainRole role) {
		em.getTransaction().begin();
		em.persist(role);
		em.getTransaction().commit();
	}
	
	public void addSPARULTemplate(DomainSPARULTemplate template) {
		em.getTransaction().begin();
		em.persist(template);
		em.getTransaction().commit();
	}
	
	public void addViewaction(DomainViewAction viewaction) {
		em.getTransaction().begin();
		em.persist(viewaction);
		em.getTransaction().commit();
	}
	
	//delete actions
	public void deleteDomainSource(DomainSourceDetail source) {
		em.getTransaction().begin();
		em.remove(source);
		em.getTransaction().commit();
	}
	
	public void deleteDomainSystemParameter(DomainSystemParameter param) {
		em.getTransaction().begin();
		em.remove(param);
		em.getTransaction().commit();
	}
	
	public void deleteSPARULTemplate(DomainSPARULTemplate template) {
		em.getTransaction().begin();
		em.remove(template);
		em.getTransaction().commit();
	}
	
	public void deleteRole(DomainRole role) {
		em.getTransaction().begin();
		em.remove(role);
		em.getTransaction().commit();
	}
	
	public void deleteViewAction(DomainViewAction viewaction) {
		em.getTransaction().begin();
		em.remove(viewaction);
		em.getTransaction().commit();
	}
	
	//clear actions
	public void clearDomainConfiguration(){
		em.getTransaction().begin();
		//TODO, do something
		em.getTransaction().commit();
	}
	
	public void clearSPARQLTemplates() {	
		//loop all templates
		for(DomainSPARULTemplate dst:getAllDomainSPARULTemplates()){
			deleteSPARULTemplate(dst);
		}
		
		//loop all actions
		for(DomainViewAction dva:getAllDomainViewActions()){
			deleteViewAction(dva);
		}	
		//loop all roles
		for(DomainRole dr:getAllDomainRoles()){
			deleteRole(dr);
		}
	}
	
	public void clearSPARQLTemplatesByClientId(Long clientId) {	
		//loop all templates
		for(DomainSPARULTemplate dst:getDomainSPARULTemplateByClient(clientId)){
			deleteSPARULTemplate(dst);
		}
		
		//loop all actions
		for(DomainViewAction dva:getDomainViewActionsByClient(clientId)){
			deleteViewAction(dva);
		}	
		//loop all roles
		for(DomainRole dr:getDomainRolesByClient(clientId)){
			deleteRole(dr);
		}
	}
	
	//get all actions
	public List<DomainSourceDetail> getAllDomainSources() {		
		List<DomainSourceDetail> result = em.createQuery("select p from DomainSourceDetail p").getResultList();
		return result;
	}
	
	public List<DomainSystemParameter> getAllDomainSystemParameters() {		
		List<DomainSystemParameter> result = em.createQuery("select p from DomainSystemParameter p").getResultList();
		return result;
	}
	
	public List<DomainSPARULTemplate> getAllDomainSPARULTemplates() {		
		List<DomainSPARULTemplate> result = em.createQuery("select p from DomainSPARULTemplate p").getResultList();
		return result;
	}
	
	public List<DomainRole> getAllDomainRoles() {		
		List<DomainRole> result = em.createQuery("select p from DomainRole p").getResultList();
		return result;
	}
	
	public List<DomainViewAction> getAllDomainViewActions() {		
		List<DomainViewAction> result = em.createQuery("select p from DomainViewAction p").getResultList();
		return result;
	}
	
	//get specific actions
	public List<DomainSourceDetail> getDomainSourcesByType(String type) {
		Query q = em.createQuery("select p from DomainSourceDetail p where p.type = :type");
		q.setParameter("type", type);
		List<DomainSourceDetail> result = q.getResultList();
		return result;
	}
	
	public List<DomainSourceDetail> getDomainSourcesByContent(String[] uris) {
		//TODO: add filtering logic
		List<DomainSourceDetail> result = em.createQuery("select p from DomainSourceDetail p").getResultList();
		return result;
	}
	
	public DomainSourceDetail getDomainSourceById(Long id) {
		Query q = em.createQuery("select p from DomainSourceDetail p where p.id = :pid");
		q.setParameter("pid", id);
		List<DomainSourceDetail> result = q.getResultList();
		if(result.isEmpty()) return null;
		else return result.get(0);
	}
	
	public DomainSourceDetail getDomainSourceByName(String name) {
		Query q = em.createQuery("select p from DomainSourceDetail p where p.name = :pname");
		q.setParameter("pname", name);
		List<DomainSourceDetail> result = q.getResultList();
		if(result.isEmpty()) return null;
		else return result.get(0);
	}
	
//	public DomainSourceDetail getDomainSourceById(int id) {
//		List<DomainSourceDetail> list = em.createQuery("select p from DomainSourceDetail p").getResultList();
//		for(int i=0;i<list.size();i++) {
//			if(list.get(i).getId() == id)
//				return list.get(i);
//		}
//		return null;
//	}
	
	public List<DomainSystemParameter> getDomainSystemParameterByKey(String pkey) {		
		Query q = em.createQuery("select p from DomainSystemParameter p where p.pkey = :pkey");
		q.setParameter("pkey", pkey);
		List<DomainSystemParameter> result = q.getResultList();
		return result;
	}
	
	public DomainSystemParameter getDomainSystemParameterById(int id) {		
		Query q = em.createQuery("select p from DomainSystemParameter p where p.id = :pid");
		q.setParameter("pid", id);
		List<DomainSystemParameter> result = q.getResultList();
		if(result.isEmpty()) return null;
		else return result.get(0);
	}
	
//	public DomainSystemParameter getDomainSystemParameterById(int id) {
//		List<DomainSystemParameter> list = em.createQuery("select p from DomainSystemParameter p").getResultList();
//		for(int i=0;i<list.size();i++) {
//			if(list.get(i).getId() == id)
//				return list.get(i);
//		}
//		return null;
//	}
	
	public DomainSPARULTemplate getDomainSPARULTemplateById(Long templateId, Long clientId) {
		Query q = em.createQuery("select p from DomainSPARULTemplate p where p.id = :pid and p.client = :pclient");
		q.setParameter("pid", SystemParameterManager.createIdWithOffsetByClientId(templateId,clientId));
		q.setParameter("pclient", clientId);
		List<DomainSPARULTemplate> result = q.getResultList();
		if(result.isEmpty()) return null;
		else return result.get(0);
	}
	
	public List<DomainSPARULTemplate> getDomainSPARULTemplateByClient(Long client) {		
		Query q = em.createQuery("select p from DomainSPARULTemplate p where p.client = :pclient");
		q.setParameter("pclient", client);
		List<DomainSPARULTemplate> result = q.getResultList();
		return result;
	}
	
	public List<DomainRole> getDomainRolesByClient(Long client) {		
		Query q = em.createQuery("select p from DomainRole p where p.client = :pclient");
		q.setParameter("pclient", client);
		List<DomainRole> result = q.getResultList();
		return result;
	}
	
	public List<DomainViewAction> getDomainViewActionsByClient(Long client) {		
		Query q = em.createQuery("select p from DomainViewAction p where p.client = :pclient");
		q.setParameter("pclient", client);
		List<DomainViewAction> result = q.getResultList();
		return result;
	}
	
	//verify state actions
	public boolean hasUseRightsForSPARULTemplate(String role, Long templateId, Long clientId) {
		DomainSPARULTemplate result = getDomainSPARULTemplateById(templateId, clientId);
		if(result==null) return false;
		
		for(DomainRole r : result.getRoles()){
			if(r.getName().equalsIgnoreCase(role)) return true;
		}
		return false;
	}
	
	private void initPersistence() {
//		try {
//			InitialContext ctx = new InitialContext();
//			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DefaultDB");
//		} catch (NamingException e) {
//			e.printStackTrace();
//		}

		Map properties = new HashMap();
//		properties.put(PersistenceUnitProperties.NON_JTA_DATASOURCE, ds);
//		emf = Persistence.createEntityManagerFactory("DomainConfiguration", properties);
		emf = Persistence.createEntityManagerFactory("DomainConfiguration");
		em = emf.createEntityManager();
	}
}