package org.i4change.app.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.servlets.services.IDownloadHashHandlerService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class DownloadHashHandler extends HttpServlet {

	private static final Log log = LogFactory.getLog(DownloadHashHandler.class);
	
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
			
			IDownloadHashHandlerService downloadHashHandlerService = (IDownloadHashHandlerService) context.getBean("downloadHashHandlerService.service");

			downloadHashHandlerService.service(httpServletRequest, httpServletResponse);
			
		} catch (Exception er) {
			log.error("Error downloading: " , er);
		}
	}
	

}
