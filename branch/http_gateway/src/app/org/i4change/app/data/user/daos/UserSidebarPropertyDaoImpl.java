package org.i4change.app.data.user.daos;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.i4change.app.data.diagram.DiagramDaoImpl;
import org.i4change.app.hibernate.beans.user.UserSidebarProperty;
import org.i4change.app.hibernate.beans.user.Users;
import org.i4change.app.hibernate.utils.HibernateUtil;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class UserSidebarPropertyDaoImpl {

	private static final Log log = LogFactory.getLog(UserSidebarPropertyDaoImpl.class);

	private static UserSidebarPropertyDaoImpl instance = null;

	private UserSidebarPropertyDaoImpl() {
	}

	public static synchronized UserSidebarPropertyDaoImpl getInstance() {
		if (instance == null) {
			instance = new UserSidebarPropertyDaoImpl();
		}
		return instance;
	}
	
	public UserSidebarProperty getUserSidebarPropertyById(Long userSidebarPropertyId) {
		try {
			
			String hql = "select c from UserSidebarProperty as c " +
			"where c.userSidebarPropertyId = :userSidebarPropertyId";
	
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("userSidebarPropertyId", userSidebarPropertyId);
			UserSidebarProperty sideBarProperties = (UserSidebarProperty) query.uniqueResult();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return sideBarProperties;
			// TODO: Add Usergroups to user
			// users.setUsergroups(ResHandler.getGroupmanagement().getUserGroups(user_id));
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
	
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			List<UserSidebarProperty> sideBarProperties = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return sideBarProperties;
			// TODO: Add Usergroups to user
			// users.setUsergroups(ResHandler.getGroupmanagement().getUserGroups(user_id));
		} catch (HibernateException ex) {
			log.error("[getUserSidebarPropertyList]",ex);
		} catch (Exception ex2) {
			log.error("[getUserSidebarPropertyList]",ex2);
		}
		return null;
	}
	
	public Long addUserSidebarProperty(Long diagramNo, Long user_id, Map propMap) {
		try {
			
			XStream xStream = new XStream(new XppDriver());
			xStream.setMode(XStream.NO_REFERENCES);
			
			UserSidebarProperty sideBarProperties = new UserSidebarProperty();
			sideBarProperties.setPropKey(xStream.toXML(propMap));
			sideBarProperties.setInserted(new Date());
			sideBarProperties.setUsers(UserDaoImpl.getInstance().getUserById(user_id));
			sideBarProperties.setDiagramNo(diagramNo);
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Long sideBarPropertiesId = (Long) session.save(sideBarProperties);
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return sideBarPropertiesId;
			// TODO: Add Usergroups to user
			// users.setUsergroups(ResHandler.getGroupmanagement().getUserGroups(user_id));
		} catch (HibernateException ex) {
			log.error("[addUserSidebarProperty]",ex);
		} catch (Exception ex2) {
			log.error("[addUserSidebarProperty]",ex2);
		}
		return null;
	}
	

	public Long addUserSidebarPropertyByObject(UserSidebarProperty sideBarProperties) {
		try {
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Long sideBarPropertiesId = (Long) session.save(sideBarProperties);
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return sideBarPropertiesId;
			// TODO: Add Usergroups to user
			// users.setUsergroups(ResHandler.getGroupmanagement().getUserGroups(user_id));
		} catch (HibernateException ex) {
			log.error("[addUserSidebarProperty]",ex);
		} catch (Exception ex2) {
			log.error("[addUserSidebarProperty]",ex2);
		}
		return null;
	}
	
	public Long updateUserSidebarProperty(Long userSidebarPropertyId, Map propMap) {
		try {
			
			XStream xStream = new XStream(new XppDriver());
			xStream.setMode(XStream.NO_REFERENCES);
			
			UserSidebarProperty sideBarProperties = this.getUserSidebarPropertyById(userSidebarPropertyId);
			sideBarProperties.setPropKey(xStream.toXML(propMap));
			sideBarProperties.setUpdated(new Date());
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			session.update(sideBarProperties);
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return userSidebarPropertyId;
			// TODO: Add Usergroups to user
			// users.setUsergroups(ResHandler.getGroupmanagement().getUserGroups(user_id));
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
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("diagramNo", diagramNo);
			query.setLong("user_id", user_id);
			UserSidebarProperty sideBarProperties = (UserSidebarProperty) query.uniqueResult();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			XStream xStream = new XStream(new XppDriver());
			xStream.setMode(XStream.NO_REFERENCES);
			
			sideBarProperties.setPropMap((Map) xStream.fromXML(sideBarProperties.getPropKey()));
			
			return sideBarProperties;
			// TODO: Add Usergroups to user
			// users.setUsergroups(ResHandler.getGroupmanagement().getUserGroups(user_id));
		} catch (HibernateException ex) {
			log.error("[getUserSidebarPropertyByDiagram]",ex);
		} catch (Exception ex2) {
			log.error("[getUserSidebarPropertyByDiagram]",ex2);
		}
		return null;
	}
	
}
