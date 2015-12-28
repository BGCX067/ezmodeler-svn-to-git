package org.i4change.app.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.servlets.services.IUploadHandlerService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class UploadHandler extends HttpServlet {

	private static final Log log = LogFactory.getLog(UploadHandler.class);
	

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void service(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws ServletException,
			IOException {
		try {

				
			// Get Spring context
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
				
			IUploadHandlerService uploadHandlerService = (IUploadHandlerService) context.getBean("uploadHandlerService.service");
			
			uploadHandlerService.service(httpServletRequest, httpServletResponse, getServletContext());
			
		} catch (Exception e) {
			System.out.println("ee " + e);
			e.printStackTrace();
			throw new ServletException(e);
		}
		
		
		//Do Return something
		return;

	}
	


}
