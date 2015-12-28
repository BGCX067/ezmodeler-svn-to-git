package org.i4change.app.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.view.servlet.VelocityViewServlet;
import org.i4change.app.servlets.services.IUnregisterService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class Unregister extends VelocityViewServlet {

	/**
	 * 
	 */
	private static final Log log = LogFactory.getLog(Unregister.class);

	@Override
	public Template handleRequest(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Context ctx) throws ServletException,
			IOException {
		
		try {
			// Get Spring context
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
			
			IUnregisterService unregisterService = (IUnregisterService) context.getBean("unregisterService.service");
			
			unregisterService.handleRequest(httpServletRequest, httpServletResponse, ctx);
			
			return getVelocityEngine().getTemplate("activation_template.vm");
			
		} catch (Exception err) {
			log.error("[Unregister]",err);
		}
		return null;
	}

}
