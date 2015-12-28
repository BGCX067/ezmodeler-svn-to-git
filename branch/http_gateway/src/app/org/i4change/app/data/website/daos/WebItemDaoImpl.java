package org.i4change.app.data.website.daos;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.i4change.app.hibernate.beans.basic.Configuration;
import org.i4change.app.hibernate.beans.user.Discount;
import org.i4change.app.hibernate.beans.website.WebItem;
import org.i4change.app.hibernate.beans.website.WebItemType;
import org.i4change.app.hibernate.utils.HibernateUtil;

public class WebItemDaoImpl {
	
	private static final Log log = LogFactory.getLog(WebItemDaoImpl.class);

	private static WebItemDaoImpl instance = null;
	
	//Spring injected Bean
	private WebItemRelationDaoImpl webItemRelationDaoImpl;

	private WebItemDaoImpl() {
	}

	public static synchronized WebItemDaoImpl getInstance() {
		if (instance == null) {
			instance = new WebItemDaoImpl();
		}
		return instance;
	}

	public WebItemRelationDaoImpl getWebItemRelationDaoImpl() {
		return webItemRelationDaoImpl;
	}
	public void setWebItemRelationDaoImpl(
			WebItemRelationDaoImpl webItemRelationDaoImpl) {
		this.webItemRelationDaoImpl = webItemRelationDaoImpl;
	}

	public WebItem getWebItemById(Long webItemId) {
		try {
			log.debug("getWebItemById webItemId: "+webItemId);
			
			String hql = "select c from WebItem as c " +
						"where c.webItemId = :webItemId " +
						"AND c.deleted!=:deleted";
	
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("webItemId", webItemId);
			query.setString("deleted", "true");
			WebItem webItem = (WebItem) query.uniqueResult();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			log.debug("getWebItemById webItem1: "+webItem);
			log.debug("getWebItemById webItem2: "+this.webItemRelationDaoImpl);
			log.debug("getWebItemById webItem3: "+this.webItemRelationDaoImpl.getWebItemsByChild(webItemId));
			
			//Set Parent Items of WebItem
			webItem.setParentItems(this.webItemRelationDaoImpl.getWebItemsByChild(webItemId));
			
			//Set Child Items of WebItem
			webItem.setChildItems(this.webItemRelationDaoImpl.getWebItemsByParent(webItemId));
			
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
			String videoURL, Boolean changeOnlyNeeded) {
		try {
			WebItem webItem = new WebItem();
			webItem.setWebItemName(webItemName);
			webItem.setWebItemImagepath(webItemImagepath);
			webItem.setWebItemType(WebItemTypeDaoImpl.getInstance().getWebItemTypeById(webItemTypeId));
			webItem.setWebItemText(webItemText);
			webItem.setPosition(position);
			webItem.setVideoURL(videoURL);
			webItem.setIsRoot(isRoot);
			webItem.setChangeOnlyNeeded(changeOnlyNeeded);
			webItem.setDeleted("false");
			webItem.setInserted(new Date());
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Long webItemId = (Long) session.save(webItem);
			tx.commit();
			HibernateUtil.closeSession(idf);
			
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
			String videoURL, Boolean changeOnlyNeeded) {
		try {
			WebItem webItem = this.getWebItemById(webItemId);
			webItem.setWebItemName(webItemName);
			webItem.setWebItemImagepath(webItemImagepath);
			webItem.setWebItemType(WebItemTypeDaoImpl.getInstance().getWebItemTypeById(webItemTypeId));
			webItem.setWebItemText(webItemText);
			webItem.setPosition(position);
			webItem.setVideoURL(videoURL);
			webItem.setIsRoot(isRoot);
			webItem.setChangeOnlyNeeded(changeOnlyNeeded);
			webItem.setUpdated(new Date());
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			session.update(webItem);
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			
		} catch (HibernateException ex) {
			log.error("[updateWebItem]" ,ex);
		} catch (Exception ex2) {
			log.error("[updateWebItem]" ,ex2);
		}
	}
	
	public List<WebItem> getWebItems(int start, int max, String orderby, boolean asc) {
		try {
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Criteria crit = session.createCriteria(WebItem.class);
			crit.add(Restrictions.eq("deleted", "false"));
			crit.setFirstResult(start);
			crit.setMaxResults(max);
			if (asc) crit.addOrder(Order.asc(orderby));
			else crit.addOrder(Order.desc(orderby));
			List<WebItem> ll = crit.list();
			tx.commit();
			HibernateUtil.closeSession(idf);		
			return ll;
		} catch (HibernateException ex) {
			log.error("[getWebItems]" ,ex);
		} catch (Exception ex2) {
			log.error("[getWebItems]" ,ex2);
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
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery("select count(c.webItemId) from WebItem c where c.deleted = 'false'"); 
			List ll = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
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
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			session.update(webItem);
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			
		} catch (HibernateException ex) {
			log.error("[deleteWebItemById]" ,ex);
		} catch (Exception ex2) {
			log.error("[deleteWebItemById]" ,ex2);
		}
	}

	public List<WebItem> getRootItems() {
		try {
			
			String hql = "select c from WebItem as c " +
						"where c.isRoot = :isRoot " +
						"AND c.deleted!=:deleted";
	
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setBoolean("isRoot", true);
			query.setString("deleted", "true");
			List<WebItem> webItems = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
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
	
	public List<WebItem> getWebItemsFrontendByParent(Long webItemId) {
		try {
			
			String hql = "select c from WebItem as c " +
						"where c.webItemId = :webItemId " +
						"AND c.deleted!=:deleted";
	
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("webItemId", webItemId);
			query.setString("deleted", "true");
			List<WebItem> webItems = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
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

}
