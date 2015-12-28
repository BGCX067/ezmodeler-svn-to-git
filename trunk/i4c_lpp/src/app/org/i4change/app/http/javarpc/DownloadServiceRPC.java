package org.i4change.app.http.javarpc;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.beans.ErrorResult;
import org.i4change.app.remote.ErrorService;
import org.i4change.app.remote.IDownloadservice;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author sebastianwagner
 *
 */
public class DownloadServiceRPC extends BaseAdapterRPC {
	
	private static final Log log = LogFactory.getLog(DownloadServiceRPC.class);
	
	public String generateDownLoadLinkInvoiceSelf(String SID, Long invoiceId){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDownloadservice downloadservice = (IDownloadservice) context.getBean("downloadservice.service");
		
			return downloadservice.generateDownLoadLinkInvoiceSelf(SID, invoiceId);
			
		} catch (Exception err) {
			log.error ("[generateDownLoadLinkInvoiceSelf]",err);
		}
		return null;
	}
	
}
