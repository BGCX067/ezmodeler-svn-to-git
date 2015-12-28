package org.i4change.app.http.javarpc;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.hibernate.beans.website.WebItem;
import org.i4change.app.hibernate.beans.website.WebItemType;
import org.i4change.app.remote.WebsiteService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class WebsiteServiceRPC extends BaseAdapterRPC {
	
	private static final Log log = LogFactory.getLog(WebsiteServiceRPC.class);

	public WebsiteServiceRPC() {
		try {
			
		} catch (Exception err) {
			log.error("[WebsiteServiceRPC]",err);
		}
	}
	
	public WebItem getWebItemById(String SID, long webItemId){
		try {
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			WebsiteService websiteService = (WebsiteService) context.getBean("websiteservice.service");
			
			return websiteService.getWebItemById(SID, webItemId);
		} catch (Exception err) {
			log.error("[getFieldvalueById]",err);
		}
		return null;
	}
	
	public List<WebItemType> getWebItemTypes(String SID){
		try {
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			WebsiteService websiteService = (WebsiteService) context.getBean("websiteservice.service");
			
			return websiteService.getWebItemTypes(SID);
		} catch (Exception err) {
			log.error("[getWebItemTypes]",err);
		}
		return null;
	}
	
	public SearchResult getWebItemList(String SID, int start, int max, String orderby, boolean asc){
		try {
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			WebsiteService websiteService = (WebsiteService) context.getBean("websiteservice.service");
			
			return websiteService.getWebItemList(SID, start, max, orderby, asc);
		} catch (Exception err) {
			log.error("[getWebItemList]",err);
		}
		return null;
	}
	
	public Long saveOrUpdateWebItem(String SID, Map objectMap) {
		try {
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			WebsiteService websiteService = (WebsiteService) context.getBean("websiteservice.service");
			
			return websiteService.saveOrUpdateWebItem(SID, objectMap);
		} catch (Exception err) {
			log.error("[saveOrUpdateWebItem]",err);
		}
		return null;
	}
	
	public Long deleteWebItem(String SID, Long webItemId) {
		try {
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			WebsiteService websiteService = (WebsiteService) context.getBean("websiteservice.service");
			
			return websiteService.deleteWebItem(SID, webItemId);
		} catch (Exception err) {
			log.error("[deleteWebItem]",err);
		}
		return null;
	}
	
	public List<WebItem> getRooItems(String SID) {
		try {
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			WebsiteService websiteService = (WebsiteService) context.getBean("websiteservice.service");
			
			return websiteService.getRooItems(SID);
		} catch (Exception err) {
			log.error("[getRooItems]",err);
		}
		return null;
	}
	
	public List<WebItem> getWebItemsParentChilds(String SID, Long webItemId) {
		try {
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			WebsiteService websiteService = (WebsiteService) context.getBean("websiteservice.service");
			
			return websiteService.getWebItemsParentChilds(SID, webItemId);
		} catch (Exception err) {
			log.error("[getWebItemsParentChilds]",err);
		}
		return null;
	}
	
	public List<WebItem> getWebItemsChilds(String SID, Long webItemId) {
		try {
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			WebsiteService websiteService = (WebsiteService) context.getBean("websiteservice.service");
			
			return websiteService.getWebItemsChilds(SID, webItemId);
		} catch (Exception err) {
			log.error("[getWebItemsChilds]",err);
		}
		return null;
	}
	
	public List<WebItem> getWebItemsFrontendByParent(String SID, Long webItemId) {
		try {
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			WebsiteService websiteService = (WebsiteService) context.getBean("websiteservice.service");
			
			return websiteService.getWebItemsFrontendByParent(SID, webItemId);
		} catch (Exception err) {
			log.error("[getWebItemsFrontendByParent]",err);
		}
		return null;
	}
	
}
