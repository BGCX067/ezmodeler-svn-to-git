package org.i4change.app.data.user.daos;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.i4change.app.hibernate.beans.user.UserSidebarProperty;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

//import com.thoughtworks.xstream.XStream;
//import com.thoughtworks.xstream.io.xml.XppDriver;

public class UserSidebarPropertyDaoImpl extends HibernateDaoSupport {

	private static final Log log = LogFactory.getLog(UserSidebarPropertyDaoImpl.class);

	//Spring loaded Beans
	private UserDaoImpl userDaoImpl;
	
	public UserDaoImpl getUserDaoImpl() {
		return userDaoImpl;
	}
	public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}
	
	public UserSidebarProperty getUserSidebarPropertyById(Long userSidebarPropertyId) {
		try {
			
			String hql = "select c from UserSidebarProperty as c " +
			"where c.userSidebarPropertyId = :userSidebarPropertyId";
	
			Query query = getSession().createQuery(hql);
			query.setLong("userSidebarPropertyId", userSidebarPropertyId);
			UserSidebarProperty sideBarProperties = (UserSidebarProperty) query.uniqueResult();
			
			return sideBarProperties;
		} catch (HibernateException ex) {
			log.error("[getUserSidebarPropertyById]",ex);
		} catch (Exception ex2) {
			log.error("[getUserSidebarPropertyById]",ex2);
		}
		return null;
	}

	public List<UserSidebarProperty> getUserSidebarPropertyList() {
		try {
			
			String hql = "from UserSidebarProperty";
	
			Query query = getSession().createQuery(hql);
			List<UserSidebarProperty> sideBarProperties = query.list();
			
			return sideBarProperties;
		} catch (HibernateException ex) {
			log.error("[getUserSidebarPropertyList]",ex);
		} catch (Exception ex2) {
			log.error("[getUserSidebarPropertyList]",ex2);
		}
		return null;
	}
	
	public Long addUserSidebarProperty(Long diagramNo, Long user_id, Map propMap) {
		try {
			
			UserSidebarProperty sideBarProperties = new UserSidebarProperty();
			
			sideBarProperties.setCurrentOpenitem(propMap.get("currentOpenitem").toString());
			sideBarProperties.setCurrentMaxWidth(Double.valueOf(propMap.get("currentMaxWidth").toString()).doubleValue());
			sideBarProperties.setDiagramWidth(Double.valueOf(propMap.get("diagramWidth").toString()).doubleValue());
			sideBarProperties.setDiagramY(Double.valueOf(propMap.get("diagramY").toString()).doubleValue());
			sideBarProperties.setIsOpen(Boolean.valueOf(propMap.get("isOpen").toString()).booleanValue());
			sideBarProperties.setDiagramX(Double.valueOf(propMap.get("diagramX").toString()).doubleValue());
			sideBarProperties.setCurrentZoom(Integer.valueOf(propMap.get("currentZoom").toString()).intValue());
			sideBarProperties.setDiagramHeight(Double.valueOf(propMap.get("diagramHeight").toString()).doubleValue());
			sideBarProperties.setSaveItemStatus(Boolean.valueOf(propMap.get("saveItemStatus").toString()).booleanValue());
			sideBarProperties.setShowPreNextDiagramsOnFlows(Boolean.valueOf(propMap.get("showPreNextDiagramsOnFlows").toString()).booleanValue());
			
			sideBarProperties.setInserted(new Date());
			sideBarProperties.setUsers(this.userDaoImpl.getUserById(user_id));
			sideBarProperties.setDiagramNo(diagramNo);
			
			Long sideBarPropertiesId = (Long) getHibernateTemplate().save(sideBarProperties);
			
			return sideBarPropertiesId;
		} catch (HibernateException ex) {
			log.error("[addUserSidebarProperty]",ex);
		} catch (Exception ex2) {
			log.error("[addUserSidebarProperty]",ex2);
		}
		return null;
	}
	

	public Long addUserSidebarPropertyByObject(UserSidebarProperty sideBarProperties) {
		try {
			
			Long sideBarPropertiesId = (Long) getHibernateTemplate().save(sideBarProperties);
			
			return sideBarPropertiesId;
		} catch (HibernateException ex) {
			log.error("[addUserSidebarProperty]",ex);
		} catch (Exception ex2) {
			log.error("[addUserSidebarProperty]",ex2);
		}
		return null;
	}
	
	public Long updateUserSidebarProperty(Long userSidebarPropertyId, Map propMap) {
		try {
			UserSidebarProperty sideBarProperties = this.getUserSidebarPropertyById(userSidebarPropertyId);
			
			sideBarProperties.setCurrentOpenitem(propMap.get("currentOpenitem").toString());
			sideBarProperties.setCurrentMaxWidth(Double.valueOf(propMap.get("currentMaxWidth").toString()).doubleValue());
			sideBarProperties.setDiagramWidth(Double.valueOf(propMap.get("diagramWidth").toString()).doubleValue());
			sideBarProperties.setDiagramY(Double.valueOf(propMap.get("diagramY").toString()).doubleValue());
			sideBarProperties.setIsOpen(Boolean.valueOf(propMap.get("isOpen").toString()).booleanValue());
			sideBarProperties.setDiagramX(Double.valueOf(propMap.get("diagramX").toString()).doubleValue());
			sideBarProperties.setCurrentZoom(Integer.valueOf(propMap.get("currentZoom").toString()).intValue());
			sideBarProperties.setDiagramHeight(Double.valueOf(propMap.get("diagramHeight").toString()).doubleValue());
			sideBarProperties.setSaveItemStatus(Boolean.valueOf(propMap.get("saveItemStatus").toString()).booleanValue());
			sideBarProperties.setShowPreNextDiagramsOnFlows(Boolean.valueOf(propMap.get("showPreNextDiagramsOnFlows").toString()).booleanValue());
			
			sideBarProperties.setUpdated(new Date());

			getHibernateTemplate().update(sideBarProperties);
			
			return userSidebarPropertyId;
		} catch (HibernateException ex) {
			log.error("[updateUserSidebarProperty]",ex);
		} catch (Exception ex2) {
			log.error("[updateUserSidebarProperty]",ex2);
		}
		return null;
	}
	
	public UserSidebarProperty getUserSidebarPropertyByDiagram(Long diagramNo, Long user_id) {
		try {
			String hql = "select c from UserSidebarProperty as c " +
					"where c.diagramNo = :diagramNo " +
					"AND c.users.user_id = :user_id";
			
			Query query = getSession().createQuery(hql);
			query.setLong("diagramNo", diagramNo);
			query.setLong("user_id", user_id);
			UserSidebarProperty sideBarProperties = (UserSidebarProperty) query.uniqueResult();
			
			return sideBarProperties;
		} catch (HibernateException ex) {
			log.error("[getUserSidebarPropertyByDiagram]",ex);
		} catch (Exception ex2) {
			log.error("[getUserSidebarPropertyByDiagram]",ex2);
		}
		return null;
	}
	
}
