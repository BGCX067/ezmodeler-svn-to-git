package org.i4change.app.data.website.daos;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.i4change.app.hibernate.beans.website.WebItem;
import org.i4change.app.hibernate.beans.website.WebItemRelation;
import org.i4change.app.hibernate.utils.HibernateUtil;

public class WebItemRelationDaoImpl {
	
	private static final Log log = LogFactory.getLog(WebItemRelationDaoImpl.class);

	private static WebItemRelationDaoImpl instance = null;
	
	//Spring injected Bean
	private WebItemDaoImpl webItemDaoImpl;

	private WebItemRelationDaoImpl() {
	}
	
	public WebItemDaoImpl getWebItemDaoImpl() {
		return webItemDaoImpl;
	}
	public void setWebItemDaoImpl(WebItemDaoImpl webItemDaoImpl) {
		this.webItemDaoImpl = webItemDaoImpl;
	}

	public static synchronized WebItemRelationDaoImpl getInstance() {
		if (instance == null) {
			instance = new WebItemRelationDaoImpl();
		}
		return instance;
	}

	public Long addWebItemRelation(Long parent, Long child) {
		try {
			
			WebItemRelation webItemRelation = new WebItemRelation();
			webItemRelation.setParent_webitem_id(parent);
			webItemRelation.setChild_webitem_id(child);
			webItemRelation.setDeleted("false");
			webItemRelation.setInserted(new Date());
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Long webItemRelationId = (Long) session.save(webItemRelation);
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return webItemRelationId;
			
		} catch (HibernateException ex) {
			log.error("[addWebItemRelation]" ,ex);
		} catch (Exception ex2) {
			log.error("[addWebItemRelation]" ,ex2);
		}
		return null;
	}
	
	public void deleteWebItemRelation(WebItemRelation webItemRelation) {
		try {
			
			webItemRelation.setDeleted("true");
			webItemRelation.setUpdated(new Date());
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			session.update(webItemRelation);
			tx.commit();
			HibernateUtil.closeSession(idf);
			
		} catch (HibernateException ex) {
			log.error("[deleteWebItemRelation]" ,ex);
		} catch (Exception ex2) {
			log.error("[deleteWebItemRelation]" ,ex2);
		}
	}

	public List<WebItemRelation> getWebItemsByChild(Long webItemId) {
		try {
			
			String hql = "select c from WebItemRelation as c " +
						"where c.child_webitem_id = :webItemId " +
						"AND c.deleted!=:deleted";
	
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("webItemId", webItemId);
			query.setString("deleted", "true");
			List<WebItemRelation> webItems = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return webItems;
		} catch (HibernateException ex) {
			log.error("[getWebItemsByChild]",ex);
		} catch (Exception ex2) {
			log.error("[getWebItemsByChild]",ex2);
		}
		return null;
	}

	public List<WebItemRelation> getWebItemsByParent(Long webItemId) {
		try {
			
			String hql = "select c from WebItemRelation as c " +
						"where c.parent_webitem_id = :webItemId " +
						"AND c.deleted!=:deleted";
	
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("webItemId", webItemId);
			query.setString("deleted", "true");
			List<WebItemRelation> webItems = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return webItems;
		} catch (HibernateException ex) {
			log.error("[getWebItemsByParent]",ex);
		} catch (Exception ex2) {
			log.error("[getWebItemsByParent]",ex2);
		}
		return null;
	}

	public void updateWebItemsByChild(Long webItemId, Map childItems) {
		try {
			
			//Get all Items where this one is already the Parent == Get all childs
			List<WebItemRelation> webItemRelations = this.getWebItemsByParent(webItemId);
			
			//These are id's of WebItems that should become child
			LinkedList<Long> itemIdsToAdd = new LinkedList<Long>();
			//These are webItemRelations!
			LinkedList<WebItemRelation> itemIdsToDelete = new LinkedList<WebItemRelation>();
			
			//First delete the Ones that are not on the List anymore
			for (WebItemRelation webItemRelation : webItemRelations) {
				
				boolean found = false;
				if (childItems != null) {
					for (Iterator iter = childItems.keySet().iterator();iter.hasNext();) {
						Long webItemChildId = Long.valueOf(childItems.get(iter.next()).toString()).longValue();
						log.debug("webItemChildId TO ADD 1: "+webItemChildId);
						
						if (webItemChildId.equals(webItemRelation.getChild_webitem_id())) {
							found = true;
							break;
						}
					}
				}
				
				if (!found) {
					log.debug("was NOT Found add to Delete List: "+webItemRelation);
					itemIdsToDelete.add(webItemRelation);
				}
			}
			
			
			//Second iterate through the Items that are from the Client and see which ones are missing 
			if (childItems != null) {
				for (Iterator iter = childItems.keySet().iterator();iter.hasNext();) {
					Long webItemChildId = Long.valueOf(childItems.get(iter.next()).toString()).longValue();
					log.debug("webItemChildId TO ADD 2: "+webItemChildId);
					
					boolean found = false;
					for (WebItemRelation webItemRelation : webItemRelations) {
						if (webItemChildId.equals(webItemRelation.getChild_webitem_id())) {
							found = true;
							break;
						}
					}
					
					if (!found) {
						log.debug("was NOT Found add to ADD List: "+webItemChildId);
						itemIdsToAdd.add(webItemChildId);
					}
				}
			}
			
			
			//Remove the Removals
			for (WebItemRelation webItemRelation : itemIdsToDelete) {
				this.deleteWebItemRelation(webItemRelation);
			}
			
			//Add the new Ones
			for (Long webItemChildId : itemIdsToAdd) {
				this.addWebItemRelation(webItemId, webItemChildId);
			}
		
		} catch (HibernateException ex) {
			log.error("[updateWebItemsByChild]",ex);
		} catch (Exception ex2) {
			log.error("[updateWebItemsByChild]",ex2);
		}
	}

	public void updateWebItemsByParent(Long webItemId, Map parentItems) {
		try {
			
			//Get all Items where this one is already the Child == Get all Parent
			List<WebItemRelation> webItemRelations = this.getWebItemsByChild(webItemId);
			
			//These are id's of WebItems that should become child
			LinkedList<Long> itemIdsToAdd = new LinkedList<Long>();
			//These are webItemRelations!
			LinkedList<WebItemRelation> itemIdsToDelete = new LinkedList<WebItemRelation>();
			
			//First delete the Ones that are not on the List anymore
			for (WebItemRelation webItemRelation : webItemRelations) {
				
				boolean found = false;
				if (parentItems != null) {
					for (Iterator iter = parentItems.keySet().iterator();iter.hasNext();) {
						Long webItemParentId = Long.valueOf(parentItems.get(iter.next()).toString()).longValue();
						log.debug("webItemChildId TO ADD 1: "+webItemParentId);
						
						if (webItemParentId.equals(webItemRelation.getParent_webitem_id())) {
							found = true;
							break;
						}
					}
				}
				
				if (!found) {
					log.debug("was NOT Found add to Delete List: "+webItemRelation);
					itemIdsToDelete.add(webItemRelation);
				}
			}
			
			
			//Second iterate through the Items that are from the Client and see which ones are missing 
			if (parentItems != null) {
				for (Iterator iter = parentItems.keySet().iterator();iter.hasNext();) {
					Long webItemParentId = Long.valueOf(parentItems.get(iter.next()).toString()).longValue();
					log.debug("webItemParentId TO ADD 2: "+webItemParentId);
					
					boolean found = false;
					for (WebItemRelation webItemRelation : webItemRelations) {
						if (webItemParentId.equals(webItemRelation.getParent_webitem_id())) {
							found = true;
							break;
						}
					}
					
					if (!found) {
						log.debug("was NOT Found add to ADD List: "+webItemParentId);
						itemIdsToAdd.add(webItemParentId);
					}
				}
			}
			
			
			//Remove the Removals
			for (WebItemRelation webItemRelation : itemIdsToDelete) {
				this.deleteWebItemRelation(webItemRelation);
			}
			
			//Add the new Ones
			for (Long webItemParentId : itemIdsToAdd) {
				this.addWebItemRelation(webItemParentId, webItemId);
			}
		
		} catch (HibernateException ex) {
			log.error("[updateWebItemsByChild]",ex);
		} catch (Exception ex2) {
			log.error("[updateWebItemsByChild]",ex2);
		}
		
	}
	
	public List<WebItem> getWebItemsChilds(Long webItemId) {
		try {
			
			//Get all Childs
			List<WebItemRelation> webItemRelations = this.getWebItemsByParent(webItemId);
			
			LinkedList<WebItem> webItems = new LinkedList<WebItem>();
			
			for (WebItemRelation webItemRelation : webItemRelations) {
				
				log.debug("webItemRelation.getChild_webitem_id(): "+webItemRelation.getChild_webitem_id());
				webItems.add(this.webItemDaoImpl.getWebItemById(webItemRelation.getChild_webitem_id()));
			}
			
			return webItems;
			
		} catch (Exception ex) {
			log.error("[getWebItemsChilds]",ex);
		}
		return null;
	}

}
