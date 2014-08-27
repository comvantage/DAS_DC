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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.persistence.platform.xml.SAXDocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import eu.comvantage.domainconfiguration.data.DomainRole;
import eu.comvantage.domainconfiguration.data.DomainSPARULTemplate;
import eu.comvantage.domainconfiguration.data.DomainSourceDetail;
import eu.comvantage.domainconfiguration.data.DomainSystemParameter;
import eu.comvantage.domainconfiguration.data.DomainViewAction;
import eu.comvantage.domainconfiguration.utils.SystemParameterManager;

@MultipartConfig
public class DomainSPARULTemplatesAdminServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		String uri = request.getRequestURI();
		ServletContext context = getServletContext();
		
		PrintWriter out = response.getWriter();
		out.write("<html><head></head><body>");
		out.write("<h2>Upload new SPARUL template specification</h2>");
		out.write("<h4>Please upload a valid XML file</h4>");
		out.write("<form action=\"Upload\" method=\"post\" enctype=\"multipart/form-data\">");
		out.write("<input type=\"file\" name=\"file\" size=\"200\"> << XML input file<br/>");
		out.write("<input type=\"submit\" value=\"Upload\"><br/>");
		out.write("</form>");
		out.write("</body>");
		
		//handling add request
		if (uri.endsWith("/DomainConfiguration/DomainSPARULTemplatesAdminServlet/Upload")) {
			
			//read file from form
			Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
			String filename = getFilename(filePart);
			InputStream filecontent = filePart.getInputStream();
			Document doc = getFileContentAsXML(filecontent);

			//parse and add new configuration
			parse(doc);
		}
	}
		
	private void parse(Document doc) {
		//http://www.tutorialspoint.com/java/xml/javax_xml_parsers_documentbuilder_inputstream.htm	
		
		Long clientId = Long.parseLong(doc.getElementsByTagName("client").item(0).getAttributes().getNamedItem("id").getNodeValue());
		
		//drop current configuration of selected client
		drop(clientId);
		
		Map<Long, DomainViewAction> actions = new HashMap<Long, DomainViewAction>();
		Map<Long, DomainRole> roles = new HashMap<Long, DomainRole>();
		
		NodeList nodes;

		nodes = doc.getElementsByTagName("action");
		for (int i = 0; i < nodes.getLength(); i++) {
			DomainViewAction viewaction = new DomainViewAction();
			viewaction.setId(SystemParameterManager.createIdWithOffsetByClientId(Long.parseLong(nodes.item(i).getAttributes().getNamedItem("id").getNodeValue()),clientId));
			viewaction.setClient(clientId);
			viewaction.setName(nodes.item(i).getAttributes().getNamedItem("name").getNodeValue());
			
			List<Node> nodes1 = getXmlChildren(nodes.item(i), "statement");
			viewaction.setStatement(getXmlContent(nodes1.get(0)));
			
			DomainConfigurationManager.getInstance().addViewaction(viewaction);
			actions.put(viewaction.getId(), viewaction);
		}
		
		nodes = doc.getElementsByTagName("role");
		for (int i = 0; i < nodes.getLength(); i++) {
			DomainRole role = new DomainRole();
			role.setId(SystemParameterManager.createIdWithOffsetByClientId(Long.parseLong(nodes.item(i).getAttributes().getNamedItem("id").getNodeValue()),clientId));
			role.setClient(clientId);
			role.setName(nodes.item(i).getAttributes().getNamedItem("name").getNodeValue());
			DomainConfigurationManager.getInstance().addRole(role);
			roles.put(role.getId(), role);
		}
		
		nodes = doc.getElementsByTagName("template");
		for (int i = 0; i < nodes.getLength(); i++) {
			DomainSPARULTemplate template = new DomainSPARULTemplate();
			template.setId(SystemParameterManager.createIdWithOffsetByClientId(Long.parseLong(nodes.item(i).getAttributes().getNamedItem("id").getNodeValue()),clientId));
			template.setClient(clientId);
			template.setName(nodes.item(i).getAttributes().getNamedItem("name").getNodeValue());
			template.setSource(nodes.item(i).getAttributes().getNamedItem("source").getNodeValue());
			
			List<Node> nodes1 = getXmlChildren(nodes.item(i), "statement");
			template.setStatement(getXmlContent(nodes1.get(0)));
			
			if(nodes.item(i).getAttributes().getNamedItem("enabled").getNodeValue().equalsIgnoreCase("true")) {
				template.setEnabled(true);
			} else {
				template.setEnabled(false);
			}
			
			List<Node> nodes2 = getXmlChildren(nodes.item(i), "assignedActions");
			List<Node> nodes3 = getXmlChildren(nodes2.get(0), "value");
	        for(Node n:nodes3) {
	        	template.addViewaction(actions.get(SystemParameterManager.createIdWithOffsetByClientId(Long.parseLong(getXmlContent(n)),clientId)));
	        }
			
	        List<Node> nodes4 = getXmlChildren(nodes.item(i), "assignedRoles");
	        List<Node> nodes5 = getXmlChildren(nodes4.get(0), "value");
	        for(Node n:nodes5) {
	        	template.addRole(roles.get(SystemParameterManager.createIdWithOffsetByClientId(Long.parseLong(getXmlContent(n)),clientId)));
	        }
	        
			DomainConfigurationManager.getInstance().addSPARULTemplate(template); 
		}
	}
	
	private void drop(Long clientId) {
		DomainConfigurationManager.getInstance().clearSPARQLTemplatesByClientId(clientId);
	}
	
	private static Document getFileContentAsXML(InputStream is) throws IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	      try {
	         DocumentBuilder builder = factory.newDocumentBuilder();
	         return builder.parse(is);
	      } catch (Exception ex) {
	         ex.printStackTrace();
	         return null;
	      }
	}
	
	private static String getFileContentAsString(InputStream inputStream) throws IOException {
	    String body = null;
	    StringBuilder stringBuilder = new StringBuilder();
	    BufferedReader bufferedReader = null;

	    try {
	        if (inputStream != null) {
	            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
	            char[] charBuffer = new char[128];
	            int bytesRead = -1;
	            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
	                stringBuilder.append(charBuffer, 0, bytesRead);
	            }
	        } else {
	            stringBuilder.append("");
	        }
	    } catch (IOException ex) {
	        throw ex;
	    } finally {
	        if (bufferedReader != null) {
	            try {
	                bufferedReader.close();
	            } catch (IOException ex) {
	                throw ex;
	            }
	        }
	    }

	    body = stringBuilder.toString();
	    return body;
	}
	
	private List<Node> getXmlChildren(Node node, String match) {		
		List<Node> results = new ArrayList<Node>();
		NodeList childs = node.getChildNodes();
        for (int a = 0; a < childs.getLength(); a++) {
      	  if(childs.item(a).getNodeName().equals(match)) {
      		  results.add(childs.item(a));
      	  }
        }
        return results;
	}
	
	private String getXmlContent(Node node) {		
		String result = node.getFirstChild().getNodeValue();
        return result;
	}
	
	private static String getFilename(Part part) {
	    for (String cd : part.getHeader("content-disposition").split(";")) {
	        if (cd.trim().startsWith("filename")) {
	            String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
	            return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
	        }
	    }
	    return null;
	}

	@Override
	public void init() throws ServletException {
		//do something
	}
}