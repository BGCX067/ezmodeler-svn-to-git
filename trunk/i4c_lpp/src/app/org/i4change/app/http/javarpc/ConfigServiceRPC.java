package org.i4change.app.http.javarpc;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.hibernate.beans.basic.Configuration;
import org.i4change.app.remote.IConfigurationService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author sebastianwagner
 *
 */
public class ConfigServiceRPC extends BaseAdapterRPC {
	
	private static final Log log = LogFactory.getLog(ConfigServiceRPC.class);
	
	public SearchResult getAllConf(String SID, int start ,int max, String orderby, boolean asc){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IConfigurationService configService = (IConfigurationService) context.getBean("configservice.service");
		
			return configService.getAllConf(SID, start, max, orderby, asc);
			
		} catch (Exception err) {
			log.error ("[getAllConf]",err);
		}
		return null;
	}
	
	public Configuration getConfByConfigurationId(String SID,long configuration_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IConfigurationService configService = (IConfigurationService) context.getBean("configservice.service");
		
			return configService.getConfByConfigurationId(SID, configuration_id);
			
		} catch (Exception err) {
			log.error ("[getConfByConfigurationId]",err);
		}
		return null;
	}

	public Long saveOrUpdateConfiguration(String SID,Map values){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IConfigurationService configService = (IConfigurationService) context.getBean("configservice.service");
		
			return configService.saveOrUpdateConfiguration(SID, values);
			
		} catch (Exception err) {
			log.error ("[getConfByConfigurationId]",err);
		}
		return null;
	}
	
	public Long deleteConfiguration(String SID,Map values){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IConfigurationService configService = (IConfigurationService) context.getBean("configservice.service");
		
			return configService.deleteConfiguration(SID, values);
			
		} catch (Exception err) {
			log.error ("[deleteConfiguration]",err);
		}
		return null;
	}

	public List<Configuration> getLicenseDefaultConfiguration(String SID){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IConfigurationService configService = (IConfigurationService) context.getBean("configservice.service");
		
			return configService.getLicenseDefaultConfiguration(SID);
			
		} catch (Exception err) {
			log.error ("[getLicenseDefaultConfiguration]",err);
		}
		return null;
	}
	
}
