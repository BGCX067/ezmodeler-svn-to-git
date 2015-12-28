package org.i4change.app.http.javarpc;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.remote.IReportService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author sebastianwagner
 *
 */
public class ReportServiceRPC extends BaseAdapterRPC {
	
	private static final Log log = LogFactory.getLog(ReportServiceRPC.class);
	
	public Long startGenerateOverview(String SID, Long projectId, Long organisation_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IReportService reportservice = (IReportService) context.getBean("reportservice.service");
		
			return reportservice.startGenerateOverview(SID, projectId, organisation_id);
			
		} catch (Exception err) {
			log.error ("[startGenerateOverview]",err);
		}
		return null;
	}
	
}
