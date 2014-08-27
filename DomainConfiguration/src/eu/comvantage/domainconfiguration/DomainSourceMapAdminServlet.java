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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import eu.comvantage.domainconfiguration.data.DomainSourceDetail;

public class DomainSourceMapAdminServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.write("<html><head></head><body>");

		String uri = request.getRequestURI();
		ServletContext context = getServletContext();

		//handling add request
		if (uri.endsWith("/DomainConfiguration/DomainSourceMapAdminServlet/Add")) {
			doAdd(request, out);
		}
		//handling delete request
		if (uri.endsWith("/DomainConfiguration/DomainSourceMapAdminServlet/Delete")) {
			doDelete(request, out);
		}

		List<DomainSourceDetail> resultList = DomainConfigurationManager.getInstance().getAllDomainSources();

		out.write("<h2>Domain Access Server</h2></br><h3>Domain Configuration Admin Interface</h3></br><h4>Domain Source Map View</h4>");
		out.write("<table border=\"1\"><tr><th colspan=\"6\">"
				+ (resultList.isEmpty() ? "" : resultList.size() + " ")
				+ "Entries in the Database</th></tr>");

		if (resultList.isEmpty()) {
			out.write("<tr><td colspan=\"7\">Database is empty</td></tr>");
		} else {
			//TODO: Extend table header
			out.write("<tr><th>Id</th><th>Source Name</th><th>Type</th><th>Query Endpoint URL</th><th>Update Endpoint URL</th><th>Content</th><th></th></tr>");
		}

		//Source table
        //TODO: Extend table
		for (DomainSourceDetail d : resultList) {
            //Render source id, name, endpoint
			out.write("<tr><td>" 
            		+ d.getId() 
            		+ "</td><td>"
                    + (d.getName().length() > 0 ? d.getName() : "&nbsp")
                    + "</td><td>"
                    + (d.getType().length() > 0 ? d.getType() : "&nbsp")
                    + "</td><td>"
                    + (d.getQueryEndpointURL().length() > 0 ? d.getQueryEndpointURL() : "&nbsp")
                    + "</td><td>"
					+ (d.getUpdateEndpointURL().length() > 0 ? d.getUpdateEndpointURL() : "&nbsp")
					+ "</td><td>");
            //Render source content
            String content = "";
            if(d.getContent() != null){
                for (int i=0;i<d.getContent().size();i++){
                	content += d.getContent().get(i) + ", ";
                }
                out.write(content.toString());
            }
            out.write("</td><td>");
            //Render delete button
            out.write("<form action=\"Delete\">"
                	+ "<input type=\"hidden\" name=\"Id\" value=\"" + String.valueOf(d.getId()) + "\">"
                	+ "<input type=\"submit\" value=\"Delete Source\">"
                	+ "</form>"
                	+ "</td></tr>");
        }
		out.write("</table>");

		//Add source form
		//TODO: extend "add new" form
		out.write("</br>New Source:</br>"
				+ "<form action=\"Add\">"
				+ "<input type=\"text\" name=\"Name\"> << Source Name<br/>"
				+ "<select style=\"width: 141px\" name=\"Type\"><option value=\"sparql\">sparql</option><option value=\"dhm\">dhm</option></select> << Type</br>"	
				+ "<input type=\"text\" name=\"QueryEndpointURL\"> << Query Endpoint URL<br/>"
				+ "<input type=\"text\" name=\"UpdateEndpointURL\"> << Update Endpoint URL (optional)<br/>"
				+ "<input type=\"text\" name=\"Username\"> << Username (optional, system defaults are used if not set)<br/>"
				+ "<input type=\"text\" name=\"Password\"> << Password (optional, system defaults are used if not set)<br/>"
				+ "<input type=\"text\" name=\"Content\"> << Content (optional, use comma separated values!)<br/>"
				+ "<input type=\"submit\" value=\"Add Source\"><br/>"
				+ "</form><br/>");
		
		out.write("</body></html>");
	}

	protected void doAdd(HttpServletRequest request, PrintWriter out) throws ServletException, IOException {
		DomainSourceDetail source = new DomainSourceDetail(request.getParameter("Name"),request.getParameter("QueryEndpointURL"),request.getParameter("Type"));
		
		//add further paramters
		
		//add update endpoint URL
		source.setUpdateEndpointURL(request.getParameter("UpdateEndpointURL"));
		
		//add content
		ArrayList<String> content = new ArrayList<String>();
		if (request.getParameter("Content").length()>0) {
			String[] ctemp = (request.getParameter("Content")).split(",");
			for (int i=0; i<ctemp.length; i++){
				content.add(ctemp[i]);
			}
			source.setContent(content);
		}
		
		//add credentials
		source.setUsername(request.getParameter("Username"));
		source.setPassword(request.getParameter("Password"));
		
		DomainConfigurationManager.getInstance().addDomainSource(source);
	}
	
	protected void doDelete(HttpServletRequest request, PrintWriter out) throws ServletException, IOException {
		DomainSourceDetail source = DomainConfigurationManager.getInstance().getDomainSourceById(Long.parseLong(request.getParameter("Id")));
		DomainConfigurationManager.getInstance().deleteDomainSource(source);
	}

	@Override
	public void init() throws ServletException {
		//do something
	}
}