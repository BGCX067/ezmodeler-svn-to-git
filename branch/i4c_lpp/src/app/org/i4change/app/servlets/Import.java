package org.i4change.app.servlets;

import http.utils.multipartrequest.ServletMultipartRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.i4change.app.data.basic.Languagemanagement;
import org.i4change.app.data.basic.Sessionmanagement;
import org.i4change.app.data.domain.daos.OrganisationDaoImpl;
import org.i4change.app.data.user.Addressmanagement;
import org.i4change.app.data.user.EmailDaoImpl;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.xmlimport.LanguageImport;
import org.i4change.app.xmlimport.UserImport;
import org.i4change.app.hibernate.beans.user.*;
import org.i4change.app.utils.math.CalendarPatterns;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class Import extends HttpServlet {

	private static final Log log = LogFactory.getLog(Import.class);
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void service(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws ServletException,
			IOException {

		try {
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
			
			LanguageImport languageImport = (LanguageImport) context.getBean("i4change.LanguageImport");
			UserImport userImport = (UserImport) context.getBean("i4change.UserImport");
			Sessionmanagement sessionmanagement = (Sessionmanagement) context.getBean("i4change.Sessionmanagement");
			UserDaoImpl userDaoImpl = (UserDaoImpl) context.getBean("i4change.UserDaoImpl");
			
			String sid = httpServletRequest.getParameter("sid");
			if (sid == null) {
				sid = "default";
			}
			System.out.println("sid: " + sid);

			String moduleName = httpServletRequest.getParameter("moduleName");
			if (moduleName == null) {
				moduleName = "moduleName";
			}
			System.out.println("moduleName: " + sid);
			Long users_id = sessionmanagement.checkSession(sid);
			Long user_level = userDaoImpl.getUserLevelByID(users_id);

			System.out.println("users_id: " + users_id);
			System.out.println("user_level: " + user_level);

			// if (user_level!=null && user_level > 0) {
			if (true) {
				if (moduleName.equals("users")) {
					log.error("Import Users");
					String organisation = httpServletRequest.getParameter("secondid");
					if (organisation == null) {
						organisation = "0";
					}
					Long organisation_id = Long.valueOf(organisation).longValue();
					System.out.println("organisation_id: " + organisation_id);

					ServletMultipartRequest upload = new ServletMultipartRequest(httpServletRequest, 104857600); // max100 mb
					InputStream is = upload.getFileContents("Filedata");
					
					userImport.addUsersByDocument(is);

				} else if (moduleName.equals("language")) {
					log.error("Import Language");
					String language = httpServletRequest.getParameter("secondid");
					if (language == null) {
						language = "0";
					}
					Long language_id = Long.valueOf(language).longValue();
					System.out.println("language_id: " + language_id);

					ServletMultipartRequest upload = new ServletMultipartRequest(httpServletRequest, 104857600); // max100 mb
					InputStream is = upload.getFileContents("Filedata");
					
					languageImport.addLanguageByDocument(language_id, is);
					
				}
			} else {
				System.out.println("ERROR LangExport: not authorized FileDownload "+ (new Date()));
			}	
	
		} catch (Exception er) {
			log.error("ERROR ", er);
			System.out.println("Error exporting: " + er);
			er.printStackTrace();
		}
	}

}
