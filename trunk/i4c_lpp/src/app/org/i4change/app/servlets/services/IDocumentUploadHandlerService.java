package org.i4change.app.servlets.services;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IDocumentUploadHandlerService {

	/* (non-Javadoc)
	 * @see org.i4change.app.servlets.services.IUploadHandlerService#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.ServletContext)
	 */
	public abstract void service(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			ServletContext servletContext) throws ServletException, IOException;

}