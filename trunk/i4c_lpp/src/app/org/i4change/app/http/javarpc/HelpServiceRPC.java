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
import org.i4change.app.hibernate.beans.help.HelpTopic;
import org.i4change.app.remote.ConfigurationService;
import org.i4change.app.remote.DownloadService;
import org.i4change.app.remote.IHelpService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author sebastianwagner
 *
 */
public class HelpServiceRPC extends BaseAdapterRPC {
	
	private static final Log log = LogFactory.getLog(HelpServiceRPC.class);
	
	public Long addHelpText(String SID, Long helpId, String helpName, boolean isAgentHelp, int priority, 
			String topicText, String helpText, Long languages_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IHelpService helpservice = (IHelpService) context.getBean("helpservice.service");
		
			return helpservice.addHelpText(SID, helpId, helpName, isAgentHelp, priority, topicText, helpText, languages_id);
			
		} catch (Exception err) {
			log.error ("[addHelpText]",err);
		}
		return null;
	}
	
	public List<HelpTopic> getHelpTopicByHelpIdRange(String SID, Map helpIdList){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IHelpService helpservice = (IHelpService) context.getBean("helpservice.service");
		
			return helpservice.getHelpTopicByHelpIdRange(SID, helpIdList);
			
		} catch (Exception err) {
			log.error ("[getHelpTopicByHelpIdRange]",err);
		}
		return null;
	}

	public Long editHelpText(String SID, Long helpId, String helpName, boolean isAgentHelp, int priority, 
			String topicText, String helpText, Long languages_id, Map helpItem){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IHelpService helpservice = (IHelpService) context.getBean("helpservice.service");
		
			return helpservice.editHelpText(SID, helpId, helpName, isAgentHelp, priority, 
					topicText, helpText, languages_id, helpItem);
			
		} catch (Exception err) {
			log.error ("[editHelpText]",err);
		}
		return null;
	}	
	
	public Long deleteHelpTopic(String SID, Long helptopicId){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IHelpService helpservice = (IHelpService) context.getBean("helpservice.service");
		
			return helpservice.deleteHelpTopic(SID, helptopicId);
			
		} catch (Exception err) {
			log.error ("[deleteHelpTopic]",err);
		}
		return null;
	}

	public List<HelpTopic> searchHelp(String SID, String helpStr, Long languages_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IHelpService helpservice = (IHelpService) context.getBean("helpservice.service");
		
			return helpservice.searchHelp(SID, helpStr, languages_id);
			
		} catch (Exception err) {
			log.error ("[searchHelp]",err);
		}
		return null;
	}	
	
}
