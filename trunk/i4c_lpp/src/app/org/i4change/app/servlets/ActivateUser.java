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
import org.i4change.app.servlets.services.IActivateUserService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ActivateUser extends VelocityViewServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8892729047921796170L;
	private static final Log log = LogFactory.getLog(HelpExport.class);

	@Override
	public Template handleRequest(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Context ctx) throws ServletException,
			IOException {
		
		try {
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
			
			IActivateUserService activateUserService = (IActivateUserService) context.getBean("activateUserService.service");
			
			activateUserService.handleRequest(httpServletRequest, httpServletResponse, ctx);
			
			return getVelocityEngine().getTemplate("activation_template.vm");
			
		} catch (Exception err) {
			log.error("[ActivateUser]",err);
			err.printStackTrace();
		}
		return null;
	}

}
