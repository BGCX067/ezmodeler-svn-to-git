package org.i4change.app.http.javarpc;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.dto.basic.ConfigurationDTO;
import org.i4change.app.dto.user.UserAuthDTO;
import org.i4change.app.hibernate.beans.basic.Configuration;
import org.i4change.app.hibernate.beans.basic.Sessiondata;
import org.i4change.app.hibernate.beans.export.PresentationTemplate;
import org.i4change.app.hibernate.beans.user.Userdata;
import org.i4change.app.hibernate.beans.user.Users;
import org.i4change.app.remote.IMainService;
import org.i4change.app.session.beans.RoomClient;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class MainServiceRPC extends BaseAdapterRPC {
	
	private static final Log log = LogFactory.getLog(MainServiceRPC.class);

	public List getNavi(String SID, Long language_id, Long organization_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IMainService mainService = (IMainService) context.getBean("mainService.service");
		
			return mainService.getNavi(SID, language_id, organization_id);
			
		} catch (Exception err) {
			log.error ("[getNavi]",err);
		}
		return null;
	}
	
	public Users getUser(String SID,int USER_ID){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IMainService mainService = (IMainService) context.getBean("mainService.service");
		
			return mainService.getUser(SID, USER_ID);
			
		} catch (Exception err) {
			log.error ("[getNavi]",err);
		}
		return null;
	}
	
	public Sessiondata getsessiondata(HttpServletRequest request){
		try {
			
			log.debug("getsessiondata: "+request);
			log.warn("getsessiondata: "+request);
			log.error("getsessiondata: "+request);

			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IMainService mainService = (IMainService) context.getBean("mainService.service");
			
//			String className = context.getBean("txMainService.service").getClass().getName();
//			
//			log.debug("className "+className);
		
			return mainService.getsessiondata();
			
		} catch (Exception err) {
			log.error ("[getsessiondata]",err);
		}
		return null;
	}
	
	public UserAuthDTO setNewUserProperty(String SID, String propName, Object propValue) {
		try {

			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IMainService mainService = (IMainService) context.getBean("mainService.service");
		
			return mainService.setNewUserProperty(SID, propName, propValue);
			
		} catch (Exception err) {
			log.error ("[setNewUserProperty]",err);
		}
		return null;
	}
	
	public Object loginUser(String SID, String Username, String Userpass, int userlang){
		try {

			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IMainService mainService = (IMainService) context.getBean("mainService.service");
		
			return mainService.loginUser(SID, Username, Userpass, new Long(userlang));
			
		} catch (Exception err) {
			log.error ("[loginUser]",err);
		}
		return null;
	}
	

	public void markSessionAsLogedIn(String SID){
		try {

			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IMainService mainService = (IMainService) context.getBean("mainService.service");
		
			mainService.markSessionAsLogedIn(SID);
			
		} catch (Exception err) {
			log.error ("[markSessionAsLogedIn]",err);
		}
	}
	
	public Long logoutUser(String SID){
		try {

			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IMainService mainService = (IMainService) context.getBean("mainService.service");
		
			return mainService.logoutUser(SID);
			
		} catch (Exception err) {
			log.error ("[markSessionAsLogedIn]",err);
		}
		return null;
	}
	
	public List getStates(HttpServletRequest request){
		try {

			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IMainService mainService = (IMainService) context.getBean("mainService.service");
		
			return mainService.getStates();
			
		} catch (Exception err) {
			log.error ("[getStates]",err);
		}
		return null;
	}
	
	public ConfigurationDTO allowFrontendRegister(String SID){
		try {
 
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IMainService mainService = (IMainService) context.getBean("mainService.service");
		
			return mainService.allowFrontendRegister(SID);
			
		} catch (Exception err) {
			log.error ("[allowFrontendRegister]",err);
		}
		return null;
	}

	public Long registerUserByObjectAdvanced(Map regObjectObj){
		try {

			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IMainService mainService = (IMainService) context.getBean("mainService.service");
		
			return mainService.registerUserByObjectAdvanced(regObjectObj);
			
		} catch (Exception err) {
			log.error ("[registerUserByObjectAdvanced]",err);
		}
		return null;
	}
	
	public Long registerUserWebSite(Map regObject) {
		try {

			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IMainService mainService = (IMainService) context.getBean("mainService.service");
		
			return mainService.registerUserWebSite(regObject);
			
		} catch (Exception err) {
			log.error ("[registerUserWebSite]",err);
		}
		return null;
	}
	
	public Long deleteUserIDSelf(String SID){
		try {

			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IMainService mainService = (IMainService) context.getBean("mainService.service");
		
			return mainService.deleteUserIDSelf(SID);
			
		} catch (Exception err) {
			log.error ("[deleteUserIDSelf]",err);
		}
		return null;
	}
	
	public List<Userdata> getUserdata(String SID){
		try {

			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IMainService mainService = (IMainService) context.getBean("mainService.service");
		
			return mainService.getUserdata(SID);
			
		} catch (Exception err) {
			log.error ("[getUserdata]",err);
		}
		return null;
	}
	
//	public LinkedHashMap<Integer,RoomClient> getUsersByDomain(String SID, String domain){
//		try {
//
//			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
//			
//			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
//			MainService mainService = (MainService) context.getBean("mainService.service");
//		
//			return mainService.getUsersByDomain(SID, domain);
//			
//		} catch (Exception err) {
//			log.error ("[getUsersByDomain]",err);
//		}
//		return null;
//	}
	
	public LinkedHashMap<String,LinkedHashMap<String,LinkedHashMap<String,Object>>> getRssFeedByURL(String SID, 
			String urlEndPoint){
		try {

			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IMainService mainService = (IMainService) context.getBean("mainService.service");
		
			return mainService.getRssFeedByURL(SID, urlEndPoint);
			
		} catch (Exception err) {
			log.error ("[getRssFeedByURL]",err);
		}
		return null;
	}
	
	public LinkedHashMap<String,LinkedHashMap<String,LinkedHashMap<String,Object>>> getRssFeedByConf(String SID, 
			HttpServletRequest request){
		try {

			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IMainService mainService = (IMainService) context.getBean("mainService.service");
		
			return mainService.getRssFeedByConf(SID);
			
		} catch (Exception err) {
			log.error ("[getRssFeedByConf]",err);
		}
		return null;
	}
	
	public Long doTestMethod(Long myMethod) {
		try {
			
			return new Long(-1);
			
		} catch (Exception err) {
			log.error("[doTestMethod]",err);
		}
		return null;
	}
	
	public String passClientObject(Hashtable t) {
		try {
			log.debug("passClientObject "+t);
			return "got hashtable parameter: " + t;
		} catch (Exception err) {
			log.error("passClientObject: ",err);
		}
        return null;
    }
	 
	public List<PresentationTemplate> getPresentationTemplates(String SID, Long organisationId) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IMainService mainService = (IMainService) context.getBean("mainService.service");
			
			return mainService.getPresentationTemplates(SID, organisationId);
			
		} catch (Exception err) {
			log.error ("[getPresentationTemplates]",err);
		}
		return null;
	}

    public Object fail(){
    	Object fail = "my";
    	return (Map) fail;
    }
    
    public Sessiondata getStoredUserSessionBySID(String SID) {
    	try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IMainService mainService = (IMainService) context.getBean("mainService.service");
			
			return mainService.getStoredUserSessionBySID(SID);
			
		} catch (Exception err) {
			log.error ("[getStoredUserSessionBySID]",err);
		}
		return null;
    }
	
}
