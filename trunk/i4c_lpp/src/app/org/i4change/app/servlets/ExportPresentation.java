package org.i4change.app.servlets;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.presenter.PresenterService;
import org.i4change.app.presenter.helper.DiagramExportBean;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @deprecated
 * @author swagner
 *
 */
public class ExportPresentation extends HttpServlet {

	private static final Log log = LogFactory.getLog(ExportPresentation.class);

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
			
			log.debug("ExportPresentation");
			
			// Get Spring context
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
			PresenterService presenterService = (PresenterService) context.getBean("i4change.PresenterService");
			
			File f = presenterService.generatePresentation(new LinkedList<DiagramExportBean>(), 1L);
			
			
			
		} catch (Exception er) {
			log.error("ERROR ", er);
			System.out.println("Error exporting: " + er);
			er.printStackTrace();
		}
		
	}
	
}
