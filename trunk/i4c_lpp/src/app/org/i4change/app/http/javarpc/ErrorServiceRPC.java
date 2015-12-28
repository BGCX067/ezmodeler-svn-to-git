package org.i4change.app.http.javarpc;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.beans.ErrorResult;
import org.i4change.app.remote.IErrorservice;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author sebastianwagner
 *
 */
public class ErrorServiceRPC extends BaseAdapterRPC {
	
	private static final Log log = LogFactory.getLog(ErrorServiceRPC.class);
	
	public ErrorResult getErrorByCode(String SID, int errorid, int language_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IErrorservice errorService = (IErrorservice) context.getBean("errorservice.service");
		
			return errorService.getErrorByCode(SID, new Long(errorid), new Long(language_id));
			
		} catch (Exception err) {
			log.error ("[getErrorByCode]",err);
		}
		return null;
	}
	
}
