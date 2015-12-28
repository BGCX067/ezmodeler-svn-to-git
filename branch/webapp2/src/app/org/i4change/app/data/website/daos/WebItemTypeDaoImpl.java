package org.i4change.app.data.website.daos;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.i4change.app.hibernate.beans.website.WebItemType;
import org.i4change.app.hibernate.utils.HibernateUtil;

public class WebItemTypeDaoImpl {
	
	private static final Log log = LogFactory.getLog(WebItemTypeDaoImpl.class);

	private static WebItemTypeDaoImpl instance = null;

	private WebItemTypeDaoImpl() {
	}

	public static synchronized WebItemTypeDaoImpl getInstance() {
		if (instance == null) {
			instance = new WebItemTypeDaoImpl();
		}
		return instance;
	}
	

	public WebItemType getWebItemTypeById(Long webItemTypeId) {
		try {
			
			String hql = "select c from WebItemType as c " +
						"where c.webItemTypeId = :webItemTypeId " +
						"AND c.deleted!=:deleted";
	
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("webItemTypeId", webItemTypeId);
			query.setString("deleted", "true");
			WebItemType webItemType = (WebItemType) query.uniqueResult();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return webItemType;
		} catch (HibernateException ex) {
			log.error("[getWebItemTypeById]",ex);
		} catch (Exception ex2) {
			log.error("[getWebItemTypeById]",ex2);
		}
		return null;
	}
	
	public Long addWebItemType(String typeName, Long label_number) {
		try {
			WebItemType webItemType = new WebItemType();
			webItemType.setTypeName(typeName);
			webItemType.setDeleted("false");
			webItemType.setLabel_number(label_number);
			webItemType.setInserted(new Date());
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Long webItemTypeId = (Long) session.save(webItemType);
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return webItemTypeId;
			
		} catch (HibernateException ex) {
			log.error("[addWebItemType]" ,ex);
		} catch (Exception ex2) {
			log.error("[addWebItemType]" ,ex2);
		}
		return null;
	}

	public List<WebItemType> getWebItemTypes() {
		try {
			
			String hql = "select c from WebItemType as c " +
						"where c.deleted!=:deleted";
	
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setString("deleted", "true");
			List<WebItemType> webItemTypes = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return webItemTypes;
		} catch (HibernateException ex) {
			log.error("[getWebItemTypes]",ex);
		} catch (Exception ex2) {
			log.error("[getWebItemTypes]",ex2);
		}
		return null;
	}

}
