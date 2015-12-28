package org.i4change.app.data.website.daos;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.i4change.app.hibernate.beans.website.WebItem;
import org.i4change.app.hibernate.beans.website.WebItemRelation;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class WebItemRelationDaoImpl extends HibernateDaoSupport {
	
	private static final Log log = LogFactory.getLog(WebItemRelationDaoImpl.class);

//	private static WebItemRelationDaoImpl instance = null;
	
	//Spring injected Bean
	private WebItemDaoImpl webItemDaoImpl;

//	private WebItemRelationDaoImpl() {
//	}
	
	public WebItemDaoImpl getWebItemDaoImpl() {
		return webItemDaoImpl;
	}
	public void setWebItemDaoImpl(WebItemDaoImpl webItemDaoImpl) {
		this.webItemDaoImpl = webItemDaoImpl;
	}

//	public static synchronized WebItemRelationDaoImpl getInstance() {
//		if (instance == null) {
//			instance = new WebItemRelationDaoImpl();
//		}
//		return instance;
//	}

	public Long addWebItemRelation(Long parent, Long child) {
		try {
			
			WebItemRelation webItemRelation = new WebItemRelation();
			webItemRelation.setParent_webitem_id(parent);
			webItemRelation.setChild_webitem_id(child);
			webItemRelation.setDeleted("false");
			webItemRelation.setInserted(new Date());
			
			Long webItemRelationId = (Long) getHibernateTemplate().save(webItemRelation);
			
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
			
			getHibernateTemplate().update(webItemRelation);
			
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
						"AND c.deleted!=:deleted " +
						"AND c.parentItem.deleted!=:deleted ";
	
			Query query = getSession().createQuery(hql);
			query.setLong("webItemId", webItemId);
			query.setString("deleted", "true");
			List<WebItemRelation> webItems = query.list();
			
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
						"AND c.deleted!=:deleted " +
						"AND c.childItem.deleted!=:deleted " +
						"ORDER BY c.childItem.orderInt";
	
			Query query = getSession().createQuery(hql);
			query.setLong("webItemId", webItemId);
			query.setString("deleted", "true");
			List<WebItemRelation> webItems = query.list();
			
			return webItems;
		} catch (HibernateException ex) {
			log.error("[getWebItemsByParent]",ex);
		} catch (Exception ex2) {
			log.error("[getWebItemsByParent]",ex2);
		}
		return null;
	}
	
	public Long countWebItemsByParent(Long webItemId) {
		try {
			
			String hql = "select count(c.webItemRelationId) from WebItemRelation as c " +
						"where c.parent_webitem_id = :webItemId " +
						"AND c.deleted!=:deleted " +
						"AND c.childItem.deleted!=:deleted ";
	
			Query query = getSession().createQuery(hql);
			query.setLong("webItemId", webItemId);
			query.setString("deleted", "true");
			Long number = Long.valueOf(query.list().get(0).toString()).longValue();
			
			return number;
		} catch (HibernateException ex) {
			log.error("[countWebItemsByParent]",ex);
		} catch (Exception ex2) {
			log.error("[countWebItemsByParent]",ex2);
		}
		return null;
	}

	public void updateWebItemsByChild(Long webItemId, Vector childItems) {
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
					for (Iterator iter = childItems.iterator();iter.hasNext();) {
						Long webItemChildId = Long.valueOf(iter.next().toString()).longValue();
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
				for (Iterator iter = childItems.iterator();iter.hasNext();) {
					Long webItemChildId = Long.valueOf(iter.next().toString()).longValue();
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

	public void updateWebItemsByParent(Long webItemId, Vector parentItems) {
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
					for (Iterator iter = parentItems.iterator();iter.hasNext();) {
						Long webItemParentId = Long.valueOf(iter.next().toString()).longValue();
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
				for (Iterator iter = parentItems.iterator();iter.hasNext();) {
					Long webItemParentId = Long.valueOf(iter.next().toString()).longValue();
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

	public List<WebItem> getPathToRootNode(WebItem webItem) {
		try {
			
			List<WebItem> webItemRootList = new LinkedList<WebItem>();
			
			List<WebItemRelation> webItems = this.getWebItemsByChild(webItem.getWebItemId());
			
			log.debug("getPathToRootNode Child: "+webItem.getWebItemId());
			log.debug("getPathToRootNode Parents: "+webItems.size());
			
			for (WebItemRelation webItemRelation : webItems) {
				
				webItemRelation.getParentItem().setChildTreeItems(new LinkedList<WebItem>());
				
				webItemRelation.getParentItem().getChildTreeItems().add(webItem);
				
				if (webItemRelation.getParentItem().getIsRoot()) {
					
					
					webItemRootList.add(webItemRelation.getParentItem());
					
					
				} else {
					
					List<WebItem> parentPaths = this.getPathToRootNode(webItemRelation.getParentItem());
					
					for (WebItem parentPath : parentPaths) {
						
						webItemRootList.add(parentPath);
						
					}
					
				}
				
			}
			
			return webItemRootList;
		
		} catch (Exception err) {
			log.error("[getPathToRootNode]",err);
		}
		return null;
	}

}
