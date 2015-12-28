package org.i4change.app.remote;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.AuthLevelmanagement;
import org.i4change.app.data.basic.Sessionmanagement;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.data.website.daos.WebItemDaoImpl;
import org.i4change.app.data.website.daos.WebItemRelationDaoImpl;
import org.i4change.app.data.website.daos.WebItemTypeDaoImpl;
import org.i4change.app.hibernate.beans.website.WebItem;
import org.i4change.app.hibernate.beans.website.WebItemType;

/**
 * 
 * @author swagner
 *
 */
public class WebsiteService {

	private static final Log log = LogFactory.getLog(WebsiteService.class);	
	
	//Spring Bean Injection
	private Application application;
	private WebItemDaoImpl webItemDaoImpl = null;
	private WebItemTypeDaoImpl webItemTypeDaoImpl = null;
	private WebItemRelationDaoImpl webItemRelationDaoImpl = null;
	
	public Application getApplication() {
		return application;
	}
	public void setApplication(Application application) {
		this.application = application;
	}
	public WebItemDaoImpl getWebItemDaoImpl() {
		return webItemDaoImpl;
	}
	public void setWebItemDaoImpl(WebItemDaoImpl webItemDaoImpl) {
		this.webItemDaoImpl = webItemDaoImpl;
	}
	public WebItemTypeDaoImpl getWebItemTypeDaoImpl() {
		return webItemTypeDaoImpl;
	}
	public void setWebItemTypeDaoImpl(WebItemTypeDaoImpl webItemTypeDaoImpl) {
		this.webItemTypeDaoImpl = webItemTypeDaoImpl;
	}
	public WebItemRelationDaoImpl getWebItemRelationDaoImpl() {
		return webItemRelationDaoImpl;
	}
	public void setWebItemRelationDaoImpl(
			WebItemRelationDaoImpl webItemRelationDaoImpl) {
		this.webItemRelationDaoImpl = webItemRelationDaoImpl;
	}
	
	public WebItem getWebItemById(String SID, long webItemId){
		try {
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
        	return this.webItemDaoImpl.getWebItemById(webItemId);
		} catch (Exception err) {
			log.error("[getWebItemById]",err);
		}
		return null;
	}
	
	public List<WebItemType> getWebItemTypes(String SID){
		try {
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
        	return this.webItemTypeDaoImpl.getWebItemTypes();
		} catch (Exception err) {
			log.error("[getWebItemTypes]",err);
		}
		return null;
	}
	
	public SearchResult getWebItemList(String SID, int start, int max, String orderby, boolean asc){
		try {
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkAdminLevel(user_level)){
	        	SearchResult sResult = new SearchResult();
	        	sResult.setObjectName(WebItem.class.getName());
	        	sResult.setRecords(this.webItemDaoImpl.getItemsSelect());
	        	sResult.setResult(this.webItemDaoImpl.getWebItems(start, max, orderby, asc));
	        	return sResult;
	        }
		} catch (Exception err) {
			log.error("[getWebItemTypes]",err);
		}
        return null;
    }
	
	public Long saveOrUpdateWebItem(String SID, Map objectMap) {
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
        	
	        Long webItemId = Long.valueOf(objectMap.get("webItemId").toString()).longValue();
	        
	        Map parentItems = (Map) objectMap.get("parentItems");
	        Map childItems = (Map) objectMap.get("childItems");
	        
	        if (webItemId == null || webItemId == 0 || webItemId.equals(0)) {
	        	webItemId = this.webItemDaoImpl.addWebItem(
	        			objectMap.get("webItemName").toString(), 
	        			objectMap.get("webItemImagepath").toString(), 
	        			objectMap.get("webItemText").toString(), 
	        			Long.valueOf(objectMap.get("webItemTypeId").toString()).longValue(), 
	        			Integer.valueOf(objectMap.get("position").toString()).intValue(), 
	        			Boolean.valueOf(objectMap.get("isRoot").toString()).booleanValue(),
	        			objectMap.get("videoURL").toString(),
	        			Boolean.valueOf(objectMap.get("changeOnlyNeeded").toString()).booleanValue());
	        } else {
	        	this.webItemDaoImpl.updateWebItem(webItemId, 
	        			objectMap.get("webItemName").toString(), 
	        			objectMap.get("webItemImagepath").toString(), 
	        			objectMap.get("webItemText").toString(), 
	        			Long.valueOf(objectMap.get("webItemTypeId").toString()).longValue(), 
	        			Integer.valueOf(objectMap.get("position").toString()).intValue(), 
	        			Boolean.valueOf(objectMap.get("isRoot").toString()).booleanValue(),
	        			objectMap.get("videoURL").toString(),
	        			Boolean.valueOf(objectMap.get("changeOnlyNeeded").toString()).booleanValue());
	        }
	        
	        log.debug("childItems"+childItems);
	        //Handle Parent Child Relation
	        this.webItemRelationDaoImpl.updateWebItemsByChild(webItemId,childItems);
	        
	        log.debug("childItems"+childItems);
	        //Handle Child Parent Relation
	        this.webItemRelationDaoImpl.updateWebItemsByParent(webItemId,parentItems);
	        
	        return webItemId;
		} catch (Exception err) {
			log.error("[saveOrUpdateWebItem]",err);
		}
		return new Long(-1);
	}
	
	public Long deleteWebItem(String SID, Long webItemId) {
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
        	
	        this.webItemDaoImpl.deleteWebItemById(webItemId);
	        
	        return webItemId;
		} catch (Exception err) {
			log.error("[saveOrUpdateWebItem]",err);
		}
		return new Long(-1);
	}
	
	public List<WebItem> getRooItems(String SID) {
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        
	        return this.webItemDaoImpl.getRootItems();
		} catch (Exception err) {
			log.error("[getRooItems]",err);
		}
		return null;
	}

	public List<WebItem> getWebItemsParentChilds(String SID, Long webItemId) {
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        
	        WebItem webItem = this.webItemDaoImpl.getWebItemById(webItemId);
	        
	        if (webItem.getIsRoot()) {
	        	return this.getRooItems(SID);
	        } else {
		        Long webItemParentId = webItem.getParentItems().get(0).getParent_webitem_id();
		        return this.webItemRelationDaoImpl.getWebItemsChilds(webItemParentId);
	        }
	        	
		} catch (Exception err) {
			log.error("[getRooItems]",err);
		}
		return null;
	}
	
	public List<WebItem> getWebItemsChilds(String SID, Long webItemId) {
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        
	        return this.webItemRelationDaoImpl.getWebItemsChilds(webItemId);
	        
		} catch (Exception err) {
			log.error("[getRooItems]",err);
		}
		return null;
	}

	public List<WebItem> getWebItemsFrontendByParent(String SID, Long webItemId) {
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        
	        return this.webItemDaoImpl.getWebItemsFrontendByParent(webItemId);
	        
		} catch (Exception err) {
			log.error("[getRooItems]",err);
		}
		return null;
	}
	
}
