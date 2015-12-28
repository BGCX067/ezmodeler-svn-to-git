package org.i4change.app.servlets.services;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IDownloadHandlerService {

	public abstract void service(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			ServletContext servletContext) throws ServletException, IOException;

}