package org.i4change.app.data.diagram;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.i4change.app.hibernate.beans.diagram.PropertyListItem;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class PropertyListItemDaoImpl extends HibernateDaoSupport {
	
	private static final Log log = LogFactory.getLog(PropertyListItemDaoImpl.class);	
	
	public PropertyListItem getPropertyListItemById(long propertyListItemId) {
		try {
			
			String hql = "SELECT c FROM PropertyListItem as c " +
						"WHERE c.propertyListItemId=:propertyListItemId " +
						"AND c.deleted!=:deleted";

			Query query = getSession().createQuery(hql);
			query.setLong("propertyListItemId", propertyListItemId);
			query.setString("deleted", "true");
			PropertyListItem propertyListItem = (PropertyListItem) query.uniqueResult();
		
			//log.debug("select issueAssignee " + issueAssignee);
		
			return propertyListItem;
			
		} catch (HibernateException ex) {
			log.error("[getPropertyListItemById]",ex);
		} catch (Exception err) {
			log.error("[getPropertyListItemById]",err);
		}
		return null;
	}
	
	public List<PropertyListItem> getPropertyListItemsByPropertyId(Long propertyId) {
		try {
			log.debug("getPropertyListItemsByPropertyId"+propertyId);
			
			String hql = "SELECT c FROM PropertyListItem as c " +
						"WHERE c.propertyId = :propertyId " +
						"AND c.deleted != :deleted";

			Query query = getSession().createQuery(hql);
			query.setLong("propertyId", propertyId);
			query.setString("deleted", "true");
			List<PropertyListItem> propertyListItems = query.list();
		
			log.debug("select getPropertyListItemsByPropertyId " + propertyListItems.size());
		
			return propertyListItems;
			
		} catch (HibernateException ex) {
			log.error("[getPropertyListItemsByPropertyId]",ex);
		} catch (Exception err) {
			log.error("[getPropertyListItemsByPropertyId]",err);
		}
		return null;
	}
	
	public void addPropertyListItems(long propertyId, Vector<Hashtable> propertyItemList){
		try {
			
			for (Iterator<Hashtable> iter = propertyItemList.iterator();iter.hasNext();) {
				Hashtable propertyPair = iter.next();
				
				String key = propertyPair.get("key").toString();
				String value = propertyPair.get("value").toString();
				
				if (propertyPair.containsKey("propertyListItemId")) {
					Long propertyListItemId = Long.valueOf(propertyPair.get("propertyListItemId").toString()).longValue();
					if (propertyListItemId != null && propertyListItemId != 0) {
						this.updatePropertyListItem(propertyListItemId, propertyId, key, value);
					} else {
						this.addPropertyListItem(propertyId, key, value);
					}
				} else {
					this.addPropertyListItem(propertyId, key, value);
				}
				
			}
			
		} catch (Exception err) {
			log.error("[addPropertyListItems]",err);
		}
	}
	
	public void updatePropertyListItems(Long propertyId, Vector propertyItemList) {
		try {
			
			List<PropertyListItem> propertyList = this.getPropertyListItemsByPropertyId(propertyId);
			
			for (PropertyListItem propertyListItem : propertyList) {
				boolean found = false;
				
				Long storedPropertyListItemId = propertyListItem.getPropertyListItemId();
				
				for (Iterator<Hashtable> iter = propertyItemList.iterator();iter.hasNext();) {
					Hashtable propertyPair = iter.next();
					
					if (propertyPair.containsKey("propertyListItemId")) {
						Long propertyListItemId = Long.valueOf(propertyPair.get("propertyListItemId").toString()).longValue();
						
						if (propertyListItemId.equals(storedPropertyListItemId)){
							found = true;
							break;
						}
					}
				}
				
				if (!found) {
					this.deletePropertyListItem(storedPropertyListItemId);
				}
			}
			
			this.addPropertyListItems(propertyId, propertyItemList);
			
		} catch (Exception err) {
			log.error("[updatePropertyListItems]",err);
		}
	}
	
	public Long addPropertyListItem(long propertyId, String key, String value) {
		try {
			
			PropertyListItem propertyListItem = new PropertyListItem();
			propertyListItem.setDeleted("false");
			propertyListItem.setInserted(new Date());
			propertyListItem.setKey(key);
			propertyListItem.setValue(value);
			propertyListItem.setPropertyId(propertyId);
			
			Long propertyListItemId = (Long) getHibernateTemplate().save(propertyListItem);
		
			//log.debug("select issueAssignee " + issueAssignee);
		
			return propertyListItemId;
			
		} catch (HibernateException ex) {
			log.error("[addPropertyListItem]",ex);
		} catch (Exception err) {
			log.error("[addPropertyListItem]",err);
		}
		return null;
	}
	
	public void updatePropertyListItem(long propertyListItemId, long propertyId, String key, String value) {
		try {
			
			PropertyListItem propertyListItem = this.getPropertyListItemById(propertyListItemId);
			if (propertyListItem == null) {
				this.addPropertyListItem(propertyId, key, value);
				return;
			}
			propertyListItem.setDeleted("false");
			propertyListItem.setUpdated(new Date());
			propertyListItem.setKey(key);
			propertyListItem.setValue(value);
			propertyListItem.setPropertyId(propertyId);
			
			getHibernateTemplate().update(propertyListItem);
		
			//log.debug("select issueAssignee " + issueAssignee);
		
		} catch (HibernateException ex) {
			log.error("[updatePropertyListItem]",ex);
		} catch (Exception err) {
			log.error("[updatePropertyListItem]",err);
		}
	}
	
	public void deletePropertyListItem(long propertyListItemId) {
		try {
			
			PropertyListItem propertyListItem = this.getPropertyListItemById(propertyListItemId);
			if (propertyListItem == null) {
				return;
			}
			propertyListItem.setDeleted("true");
			propertyListItem.setUpdated(new Date());
		
			getHibernateTemplate().update(propertyListItem);
		
			//log.debug("select issueAssignee " + issueAssignee);
		
		} catch (HibernateException ex) {
			log.error("[updatePropertyListItem]",ex);
		} catch (Exception err) {
			log.error("[updatePropertyListItem]",err);
		}
	}

}
