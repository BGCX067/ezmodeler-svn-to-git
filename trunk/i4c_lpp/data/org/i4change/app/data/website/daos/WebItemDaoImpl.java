package org.i4change.app.data.website.daos;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.i4change.app.hibernate.beans.website.WebItem;
import org.i4change.app.hibernate.beans.website.WebItemRelation;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class WebItemDaoImpl extends HibernateDaoSupport {
	 
	private static final Log log = LogFactory.getLog(WebItemDaoImpl.class);

//	private static WebItemDaoImpl instance = null;
	
	//Spring injected Bean
	private WebItemRelationDaoImpl webItemRelationDaoImpl;
	private WebItemTypeDaoImpl webItemTypeDaoImpl;

//	private WebItemDaoImpl() {
//	}

//	public static synchronized WebItemDaoImpl getInstance() {
//		if (instance == null) {
//			instance = new WebItemDaoImpl();
//		}
//		return instance;
//	}

	public WebItemRelationDaoImpl getWebItemRelationDaoImpl() {
		return webItemRelationDaoImpl;
	}
	public void setWebItemRelationDaoImpl(
			WebItemRelationDaoImpl webItemRelationDaoImpl) {
		this.webItemRelationDaoImpl = webItemRelationDaoImpl;
	}

	public WebItemTypeDaoImpl getWebItemTypeDaoImpl() {
		return webItemTypeDaoImpl;
	}
	public void setWebItemTypeDaoImpl(WebItemTypeDaoImpl webItemTypeDaoImpl) {
		this.webItemTypeDaoImpl = webItemTypeDaoImpl;
	}
	
	public WebItem getWebItemById(Long webItemId) {
		try {
			//log.debug("getWebItemById webItemId: "+webItemId);
			
			String hql = "select c from WebItem as c " +
						"where c.webItemId = :webItemId " +
						"AND c.deleted!=:deleted";
	
			Query query = getSession().createQuery(hql);
			query.setLong("webItemId", webItemId);
			query.setString("deleted", "true");
			WebItem webItem = (WebItem) query.uniqueResult();
			
			if (webItem == null) {
				log.error("[getWebItemById] webItem for webItemId is NULL "+webItemId);
				return null;
			}
			
			//log.debug("getWebItemById webItem1: "+webItem);
			//log.debug("getWebItemById webItem2: "+this.webItemRelationDaoImpl);
			//log.debug("getWebItemById webItem3: "+this.webItemRelationDaoImpl.getWebItemsByChild(webItemId));
			
			//Set Parent Items of WebItem
			List<WebItemRelation> webItemParentRelations = this.webItemRelationDaoImpl.getWebItemsByChild(webItemId);
			webItem.setParentItems(webItemParentRelations);
			
			//Set Child Items of WebItem
			List<WebItemRelation> webItemChildRelations = this.webItemRelationDaoImpl.getWebItemsByParent(webItemId);
			webItem.setChildItems(webItemChildRelations);
			
			return webItem;
		} catch (HibernateException ex) {
			log.error("[getWebItemById]",ex);
		} catch (Exception ex2) {
			log.error("[getWebItemById]",ex2);
		}
		return null;
	}
	
	public Long addWebItem(String webItemName, String webItemImagepath, 
			String webItemText, Long webItemTypeId, Integer position, boolean isRoot, 
			String videoURL, String userDefiniedType, Boolean changeOnlyNeeded,
			Boolean isMasterRoot, String webItemLayout, Boolean isHelpItem, 
			Integer orderInt) {
		try {
			WebItem webItem = new WebItem();
			webItem.setWebItemName(webItemName);
			webItem.setWebItemImagepath(webItemImagepath);
			webItem.setWebItemType(this.webItemTypeDaoImpl.getWebItemTypeById(webItemTypeId));
			webItem.setWebItemText(webItemText);
			webItem.setPosition(position);
			webItem.setVideoURL(videoURL);
			webItem.setIsRoot(isRoot);
			webItem.setChangeOnlyNeeded(changeOnlyNeeded);
			webItem.setDeleted("false");
			webItem.setUserDefiniedType(userDefiniedType);
			webItem.setInserted(new Date());
			webItem.setIsMasterRoot(isMasterRoot);
			webItem.setWebItemLayout(webItemLayout);
			webItem.setIsHelpItem(isHelpItem);
			webItem.setOrderInt(orderInt);
			
			Long webItemId = (Long) getHibernateTemplate().save(webItem);
			
			//I Don't know why but if you do a select right after the insert the Object is NOT there
			
			return webItemId;
			
		} catch (HibernateException ex) {
			log.error("[addWebItem]" ,ex);
		} catch (Exception ex2) {
			log.error("[addWebItem]" ,ex2);
		}
		return null;
	}

	public void updateWebItem(Long webItemId, String webItemName, String webItemImagepath, 
			String webItemText, Long webItemTypeId, Integer position, boolean isRoot, 
			String videoURL, String userDefiniedType, Boolean changeOnlyNeeded,
			Boolean isMasterRoot, String webItemLayout, Boolean isHelpItem, 
			Integer orderInt) {
		try {
			WebItem webItem = this.getWebItemById(webItemId);
			webItem.setWebItemName(webItemName);
			webItem.setWebItemImagepath(webItemImagepath);
			webItem.setWebItemType(this.webItemTypeDaoImpl.getWebItemTypeById(webItemTypeId));
			webItem.setWebItemText(webItemText);
			webItem.setPosition(position);
			webItem.setVideoURL(videoURL);
			webItem.setUserDefiniedType(userDefiniedType);
			webItem.setIsRoot(isRoot);
			webItem.setChangeOnlyNeeded(changeOnlyNeeded);
			webItem.setUpdated(new Date());
			webItem.setIsMasterRoot(isMasterRoot);
			webItem.setWebItemLayout(webItemLayout);
			webItem.setIsHelpItem(isHelpItem);
			webItem.setOrderInt(orderInt);
			
			getHibernateTemplate().update(webItem);
			
			
		} catch (HibernateException ex) {
			log.error("[updateWebItem]" ,ex);
		} catch (Exception ex2) {
			log.error("[updateWebItem]" ,ex2);
		}
	}
	
	

	public List<WebItem> getWebItemsBySearch(int start, int max, 
			String orderby, boolean asc, Integer searchOnlyHelpItems, String search) {
		try {
			
			String searchStr = "%";
			if (search != null && search.length() > 0) {
				searchStr = "%" + search + "%";
			}
			
			String hql = "select c from WebItem as c " +
							"where " +
							"(lower(c.webItemName) LIKE lower(:search) OR lower(c.webItemText) LIKE lower(:search)) " +
							"AND c.deleted!=:deleted ";
			
			if (searchOnlyHelpItems == 1) {
				hql += "AND c.isHelpItem = true ";
			} else if (searchOnlyHelpItems == 2){
				hql += "AND (c.isHelpItem IS NULL OR c.isHelpItem = false)";
			}
			
			hql += " ORDER BY " + orderby+ " ";
			
			if (asc) {
				hql += "ASC";
			} else {
				hql += "DESC";
			}
			
			Query query = getSession().createQuery(hql);
		
			query.setString("deleted", "true");
			query.setString("search", searchStr);
			
			query.setFirstResult(start);
			query.setMaxResults(max);
			
			List<WebItem> ll = query.list();
			return ll;
		} catch (HibernateException ex) {
			log.error("[getWebItems]" ,ex);
		} catch (Exception ex2) {
			log.error("[getWebItems]" ,ex2);
		}
		return null;
	}
	
	public List<WebItem> getWebItems(int start, int max, String orderby, boolean asc) {
		try {
			Criteria crit = getSession().createCriteria(WebItem.class);
			crit.add(Restrictions.eq("deleted", "false"));
			crit.setFirstResult(start);
			crit.setMaxResults(max);
			if (asc) crit.addOrder(Order.asc(orderby));
			else crit.addOrder(Order.desc(orderby));
			List<WebItem> ll = crit.list();
			return ll;
		} catch (HibernateException ex) {
			log.error("[getWebItems]" ,ex);
		} catch (Exception ex2) {
			log.error("[getWebItems]" ,ex2);
		}
		return null;
	}
	
	
	public Long getItemsSelectBySearch(String search, Integer searchOnlyHelpItems){
		try {
			
			String searchStr = "%";
			if (search != null && search.length() > 0) {
				searchStr = "%" + search + "%";
			}
			 
			String hql = "select COUNT(c.webItemId) from WebItem as c " +
							"where " +
							"(lower(c.webItemName) LIKE lower(:search) OR lower(c.webItemText) LIKE lower(:search)) " +
							"AND c.deleted!=:deleted ";
			
			if (searchOnlyHelpItems == 1) {
				hql += "AND c.isHelpItem = true ";
			} else if (searchOnlyHelpItems == 2){
				hql += "AND (c.isHelpItem IS NULL OR c.isHelpItem = false)";
			}
			
			//get all users
			Query query = getSession().createQuery(hql); 
			query.setString("search", searchStr);
			query.setString("deleted", "true");
			List ll = query.list();
			return (Long)ll.get(0);				
		} catch (HibernateException ex) {
			log.error("[getItemsSelectBySearch] ",ex);
		} catch (Exception ex2) {
			log.error("[getItemsSelectBySearch] ",ex2);
		}
		return null;
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	public Long getItemsSelect(){
		try {
			//get all users
			Query query = getSession().createQuery("select count(c.webItemId) from WebItem c where c.deleted = 'false'"); 
			List ll = query.list();
			return (Long)ll.get(0);				
		} catch (HibernateException ex) {
			log.error("[getItemsSelect] ",ex);
		} catch (Exception ex2) {
			log.error("[getItemsSelect] ",ex2);
		}
		return null;
	}		


	public void deleteWebItemById(Long webItemId) {
		try {
			WebItem webItem = this.getWebItemById(webItemId);
			webItem.setDeleted("true");
			webItem.setUpdated(new Date());
			
			getHibernateTemplate().update(webItem);
			
		} catch (HibernateException ex) {
			log.error("[deleteWebItemById]" ,ex);
		} catch (Exception ex2) {
			log.error("[deleteWebItemById]" ,ex2);
		}
	}
	
	public List<WebItem> getSearchHelpItems(String search) {
		try {
			
			String hql = "select c from WebItem as c " +
						"where " +
						"(lower(c.webItemName) LIKE lower(:search) OR lower(c.webItemText) LIKE lower(:search)) " +
						"AND c.deleted!=:deleted " +
						"AND c.isHelpItem = true";
	
			Query query = getSession().createQuery(hql);
			query.setString("search", "%"+search+"%");
			query.setString("deleted", "true");
			List<WebItem> webItems = query.list();
			
			return webItems;
		} catch (HibernateException ex) {
			log.error("[getSearchHelpItems]",ex);
		} catch (Exception ex2) {
			log.error("[getSearchHelpItems]",ex2);
		}
		return null;
	}

	public List<WebItem> getRootItems() {
		try {
			
			String hql = "select c from WebItem as c " +
						"where c.isRoot = :isRoot " +
						"AND c.deleted!=:deleted " +
						"AND (c.isHelpItem IS NULL or c.isHelpItem = false) ";
	
			Query query = getSession().createQuery(hql);
			query.setBoolean("isRoot", true);
			query.setString("deleted", "true");
			List<WebItem> webItems = query.list();
			
			for (WebItem webItem : webItems) {
				//Set Parent Items of WebItem
				webItem.setParentItems(this.webItemRelationDaoImpl.getWebItemsByChild(webItem.getWebItemId()));
				
				//Set Child Items of WebItem
				webItem.setChildItems(this.webItemRelationDaoImpl.getWebItemsByParent(webItem.getWebItemId()));
			}
			
			return webItems;
		} catch (HibernateException ex) {
			log.error("[getRootItems]",ex);
		} catch (Exception ex2) {
			log.error("[getRootItems]",ex2);
		}
		return null;
	}
	
	public List<WebItem> getHelpRootItems() {
		try {
			
			String hql = "select c from WebItem as c " +
						"where c.isRoot = :isRoot " +
						"AND c.deleted!=:deleted " +
						"AND c.isHelpItem = true  " +
						"ORDER BY c.orderInt";
	
			Query query = getSession().createQuery(hql);
			query.setBoolean("isRoot", true);
			query.setString("deleted", "true");
			List<WebItem> webItems = query.list();
			
			return webItems;
		} catch (HibernateException ex) {
			log.error("[getHelpRootItems]",ex);
		} catch (Exception ex2) {
			log.error("[getHelpRootItems]",ex2);
		}
		return null;
	}
	
	public WebItem geMastertRootItem() {
		try {
			
			String hql = "select c from WebItem as c " +
						"where c.isMasterRoot = :isMasterRoot " +
						"AND c.deleted!=:deleted " +
						"AND (c.isHelpItem IS NULL or c.isHelpItem = false) ";
	
			Query query = getSession().createQuery(hql);
			query.setBoolean("isMasterRoot", true);
			query.setString("deleted", "true");
			List<WebItem> webItems = query.list();
			
			log.debug("geMastertRootItem "+webItems.size());
			
			if (webItems.size() > 0) {
				return webItems.get(0);
			}
		} catch (HibernateException ex) {
			log.error("[geMastertRootItem]",ex);
		} catch (Exception ex2) {
			log.error("[geMastertRootItem]",ex2);
		}
		return null;
	}
	
	public List<WebItem> getWebItemsFrontendByParent(Long webItemId) {
		try {
			
			String hql = "select c from WebItem as c " +
						"where c.webItemId = :webItemId " +
						"AND c.deleted!=:deleted " +
						"AND (c.isHelpItem IS NULL or c.isHelpItem = false) ";
	
			Query query = getSession().createQuery(hql);
			query.setLong("webItemId", webItemId);
			query.setString("deleted", "true");
			List<WebItem> webItems = query.list();
			
			for (WebItem webItem : webItems) {
				//Set Parent Items of WebItem
				webItem.setParentItems(this.webItemRelationDaoImpl.getWebItemsByChild(webItem.getWebItemId()));
				
				//Set Child Items of WebItem
				webItem.setChildItems(this.webItemRelationDaoImpl.getWebItemsByParent(webItem.getWebItemId()));
			}
			
			return webItems;
		} catch (HibernateException ex) {
			log.error("[getWebItemsFrontendByParent]",ex);
		} catch (Exception ex2) {
			log.error("[getWebItemsFrontendByParent]",ex2);
		}
		return null;
	}

	public WebItem getHelpMasterRoot() {
		try {
			//log.debug("getHelpMasterRoot");
			
			String hql = "select c from WebItem as c " +
						"where c.isMasterRoot = true " +
						"AND c.isHelpItem = true " +
						"AND c.deleted!=:deleted";
	
			Query query = getSession().createQuery(hql);
			query.setString("deleted", "true");
			List<WebItem> webItems = query.list();
			
			if (webItems.size() > 0) {
				return webItems.get(0);
			}
		} catch (HibernateException ex) {
			log.error("[getHelpMasterRoot]",ex);
		} catch (Exception ex2) {
			log.error("[getHelpMasterRoot]",ex2);
		}
		return null;
	}

}
