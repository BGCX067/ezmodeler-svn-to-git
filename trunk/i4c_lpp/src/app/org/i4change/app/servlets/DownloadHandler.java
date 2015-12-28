package org.i4change.app.servlets;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.servlets.services.IDownloadHandlerService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class DownloadHandler extends HttpServlet {

	private static final Log log = LogFactory.getLog(DownloadHandler.class);
	
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
			
			IDownloadHandlerService downloadHandlerService = (IDownloadHandlerService) context.getBean("downloadHandlerService.service");
			
			downloadHandlerService.service(httpServletRequest, httpServletResponse, getServletContext());

		} catch (Exception er) {
			log.error("Error downloading: " + er);
			er.printStackTrace();
		}
		
	}
	
}
