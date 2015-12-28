package org.i4change.app.servlets;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.remote.Application;

public class StartUpServlet extends HttpServlet {
	
	private static final Log log = LogFactory.getLog(StartUpServlet.class);

	/**
	 * 
	 */
	
	public StartUpServlet(){
	}
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		String path = config.getServletContext().getRealPath("/");

		log.debug("StartUpServlet5 "+path);
		
		Application.webAppPath = path;
		
		Application.batchFileFir = Application.webAppPath + "jod" + File.separatorChar;
		Application.mergeFileFir = Application.webAppPath + "joo" + File.separatorChar;
		Application.tempFileFir = Application.webAppPath + "temp" + File.separatorChar;
		
	}

}
