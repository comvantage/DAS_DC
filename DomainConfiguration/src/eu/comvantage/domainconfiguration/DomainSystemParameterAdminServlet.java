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
import eu.comvantage.domainconfiguration.data.DomainSystemParameter;

public class DomainSystemParameterAdminServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.write("<html><head></head><body>");

		String uri = request.getRequestURI();
		ServletContext context = getServletContext();

		//handling add request
		if (uri.endsWith("/DomainConfiguration/DomainSystemParameterAdminServlet/Add")) {
			doAdd(request, out);
		}
		//handling delete request
		if (uri.endsWith("/DomainConfiguration/DomainSystemParameterAdminServlet/Delete")) {
			doDelete(request, out);
		}

		List<DomainSystemParameter> resultList = DomainConfigurationManager.getInstance().getAllDomainSystemParameters();

		out.write("<h2>Domain Access Server</h2></br><h3>Domain Configuration Admin Interface</h3></br><h4>Domain System Parameters View</h4>");
		out.write("<table border=\"1\"><tr><th colspan=\"3\">"
				+ (resultList.isEmpty() ? "" : resultList.size() + " ")
				+ "Entries in the Database</th></tr>");

		if (resultList.isEmpty()) {
			out.write("<tr><td colspan=\"4\">Database is empty</td></tr>");
		} else {
			//TODO: Extend table header
			out.write("<tr><th>Id</th><th>Key</th><th>Value</th><th></th></tr>");
		}

		//Source table
        //TODO: Extend table
		for (DomainSystemParameter d : resultList) {
            //Render source id, name, endpoint
			out.write("<tr><td>" 
            		+ d.getId() 
            		+ "</td><td>"
                    + (d.getKey().length() > 0 ? d.getKey() : "&nbsp")
                    + "</td><td>"
                    + (d.getValue().length() > 0 ? d.getValue() : "&nbsp")
                    + "</td><td>"
                    + "<form action=\"Delete\">"
                	+ "<input type=\"hidden\" name=\"Id\" value=\"" + String.valueOf(d.getId()) + "\">"
                	+ "<input type=\"submit\" value=\"Delete Param\">"
                	+ "</form>"
                	+ "</td></tr>");
        }
		out.write("</table>");

		//Add source form
		//TODO: extend "add new" form
		out.write("</br>New System Parameter:</br>"
				+ "<form action=\"Add\">"
				+ "<input type=\"text\" name=\"Key\"> << Parameter Key<br/>"
				+ "<input type=\"text\" name=\"Value\"> << Parameter Value<br/>"
				+ "<input type=\"submit\" value=\"Add Parameter\"><br/>"
				+ "</form><br/>");
		
		out.write("</body></html>");
	}

	protected void doAdd(HttpServletRequest request, PrintWriter out) throws ServletException, IOException {
		DomainSystemParameter param = new DomainSystemParameter(request.getParameter("Key"),request.getParameter("Value"));
		
		DomainConfigurationManager.getInstance().addDomainSystemParameter(param);
	}
	
	protected void doDelete(HttpServletRequest request, PrintWriter out) throws ServletException, IOException {
		DomainSystemParameter param = DomainConfigurationManager.getInstance().getDomainSystemParameterById(Integer.parseInt(request.getParameter("Id")));
		DomainConfigurationManager.getInstance().deleteDomainSystemParameter(param);
	}

	@Override
	public void init() throws ServletException {
		//do something
	}
}