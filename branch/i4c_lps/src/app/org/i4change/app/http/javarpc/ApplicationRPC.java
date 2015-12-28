package org.i4change.app.http.javarpc;

import java.util.Date;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.remote.Application;
import org.i4change.app.utils.crypt.ManageCryptStyle;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ApplicationRPC extends BaseAdapterRPC {
	
	private static final Log log = LogFactory.getLog(ApplicationRPC.class);

	public ApplicationRPC() {
		try {
			
			log.debug("ApplicationRPC NEW USER CONNECTED ");
			log.error("ApplicationRPC NEW USER CONNECTED ");

//			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
//			
//			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
//			Application application = (Application) context.getBean("web.handler");
			
			
		} catch (Exception err) {
			log.error("[ApplicationRPC]",err);
		}
	}
	
	public String getPublicSID() {

		long thistime = new Date().getTime();
		String hash = ManageCryptStyle.getInstance().getInstanceOfCrypt().createPassPhrase(String.valueOf(thistime).toString());
		
		
		return hash;
	}
	
	public void overwritePublicSID(String newPublicSID) {
		
	}
	
	public Long generateExportJob(String SID, Object printObjectList, String diagramName, Long diagramId, Long diagramType) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			Application application = (Application) context.getBean("web.handler");
		
			return application.generateExportJob(SID, printObjectList, diagramName, diagramId, diagramType);
			
		} catch (Exception err) {
			log.error ("[generateExportJob]",err);
		}
		return null;
	}

	public Long getNewExportJobId(String SID){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			Application application = (Application) context.getBean("web.handler");
		
			return application.getNewExportJobId(SID);
			
		} catch (Exception err) {
			log.error ("[getNewExportJobId]",err);
		}
		return null;
	}

	public Long getObjectIdentifier(long organization_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			Application application = (Application) context.getBean("web.handler");
		
			return application.getObjectIdentifier(organization_id);
			
		} catch (Exception err) {
			log.error ("[getObjectIdentifier]",err);
		}
		return null;
	}
	
	
}
