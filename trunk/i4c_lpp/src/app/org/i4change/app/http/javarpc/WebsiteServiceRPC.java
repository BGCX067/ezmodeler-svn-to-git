package org.i4change.app.http.javarpc;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.dto.website.WebItemDTO;
import org.i4change.app.dto.website.WebItemListDTO;
import org.i4change.app.hibernate.beans.website.WebItem;
import org.i4change.app.hibernate.beans.website.WebItemType;
import org.i4change.app.remote.IWebsiteService;
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
	
	/**
	 * @param SID
	 * @param webItemId
	 * @return
	 */
	public WebItemDTO getWebItemById(String SID, long webItemId){
		try {
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IWebsiteService websiteService = (IWebsiteService) context.getBean("websiteservice.service");
			
			return websiteService.getWebItemById(SID, webItemId);
		} catch (Exception err) {
			log.error("[getWebItemById]",err);
		}
		return null;
	}
	
	public List<WebItemType> getWebItemTypes(String SID){
		try {
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IWebsiteService websiteService = (IWebsiteService) context.getBean("websiteservice.service");
			
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
			IWebsiteService websiteService = (IWebsiteService) context.getBean("websiteservice.service");
			
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
			IWebsiteService websiteService = (IWebsiteService) context.getBean("websiteservice.service");
			
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
			IWebsiteService websiteService = (IWebsiteService) context.getBean("websiteservice.service");
			
			return websiteService.deleteWebItem(SID, webItemId);
		} catch (Exception err) {
			log.error("[deleteWebItem]",err);
		} 
		return null;
	}
	
	public WebItemDTO getFrontendItems(String SID) {
		try {
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IWebsiteService websiteService = (IWebsiteService) context.getBean("websiteservice.service");
			
			return websiteService.getFrontendItems(SID);
		} catch (Exception err) {
			log.error("[getFrontendItems]",err);
		}
		return null;
	}
	
	public WebItemDTO getWebItemsParentChilds(String SID, Long webItemId) {
		try {
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IWebsiteService websiteService = (IWebsiteService) context.getBean("websiteservice.service");
			
			return websiteService.getWebItemsParentChilds(SID, webItemId);
		} catch (Exception err) {
			log.error("[getWebItemsParentChilds]",err);
		}
		return null;
	}
	
	public WebItemDTO getWebItemsChilds(String SID, Long webItemId) {
		try {
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IWebsiteService websiteService = (IWebsiteService) context.getBean("websiteservice.service");
			
			return websiteService.getWebItemsChilds(SID, webItemId);
		} catch (Exception err) {
			log.error("[getWebItemsChilds]",err);
		}
		return null;
	}
	
	/**
	 * @deprecated never used
	 * @param SID
	 * @param webItemId
	 * @return
	 */
	public List<WebItem> getWebItemsFrontendByParent(String SID, Long webItemId) {
		try {
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IWebsiteService websiteService = (IWebsiteService) context.getBean("websiteservice.service");
			
			return websiteService.getWebItemsFrontendByParent(SID, webItemId);
		} catch (Exception err) {
			log.error("[getWebItemsFrontendByParent]",err);
		}
		return null;
	}
	
	public List<WebItemListDTO> getHelpRootItems(String SID) {
		try {
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IWebsiteService websiteService = (IWebsiteService) context.getBean("websiteservice.service");
			
			return websiteService.getHelpRootItems(SID);
			
		} catch (Exception err) {
			log.error("[getHelpRootItems]",err);
		}
		return null;
	}
	
	public List<WebItemListDTO> getTreeHelpItemsByParent(String SID, Long webItemId) {
		try {
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IWebsiteService websiteService = (IWebsiteService) context.getBean("websiteservice.service");
			
			return websiteService.getTreeHelpItemsByParent(SID, webItemId);
			
		} catch (Exception err) {
			log.error("[getHelpRootItems]",err);
		}
		return null;
	}
	
	public List<WebItemListDTO> getSearchHelpItems(String SID, String search) {
		try {
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IWebsiteService websiteService = (IWebsiteService) context.getBean("websiteservice.service");
			
			return websiteService.getSearchHelpItems(SID, search);
			
		} catch (Exception err) {
			log.error("[getHelpRootItems]",err);
		}
		return null;
	}
	
	public WebItemDTO getHelpMasterRoot(String SID) {
		try {
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IWebsiteService websiteService = (IWebsiteService) context.getBean("websiteservice.service");
			
			return websiteService.getHelpMasterRoot(SID);
			
		} catch (Exception err) {
			log.error("[getHelpMasterRoot]",err);
		}
		return null;
	}
	
	public SearchResult getWebItemSearchList(String SID, int start, int max, 
			String orderby, boolean asc, Integer searchOnlyHelpItems, String search){
		try {
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IWebsiteService websiteService = (IWebsiteService) context.getBean("websiteservice.service");
			
			return websiteService.getWebItemSearchList(SID, start, max, orderby, asc, searchOnlyHelpItems, search);
			
		} catch (Exception err) {
			log.error("[getWebItemSearchList]",err);
		}
		return null;
	}
	
}
