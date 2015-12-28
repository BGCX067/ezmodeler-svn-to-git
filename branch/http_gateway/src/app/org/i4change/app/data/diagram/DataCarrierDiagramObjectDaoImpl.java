package org.i4change.app.data.diagram;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.i4change.app.hibernate.beans.diagram.DataCarrierDiagramObject;
import org.i4change.app.hibernate.beans.diagram.Diagramrevision;
import org.i4change.app.hibernate.utils.HibernateUtil;

public class DataCarrierDiagramObjectDaoImpl {

	private static final Log log = LogFactory.getLog(DataCarrierDiagramObjectDaoImpl.class);	

	private static DataCarrierDiagramObjectDaoImpl instance = null;

	public static synchronized DataCarrierDiagramObjectDaoImpl getInstance() {
		if (instance == null) {
			instance = new DataCarrierDiagramObjectDaoImpl();
		}
		return instance;
	}
	
	public Long addDataCarrierDiagramObject(Long dataCarrierObjectdiagramObjectId, Long diagramObjectId, Long user_id) {
		try {
			DataCarrierDiagramObject dCarrier = new DataCarrierDiagramObject();
			dCarrier.setDataCarrierObjectdiagramObjectId(dataCarrierObjectdiagramObjectId);
			dCarrier.setDiagramObjectId(diagramObjectId);
			dCarrier.setInserted(new Date());
			dCarrier.setInsertedBy(user_id);
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Long dataCarrierDiagramObjectId = (Long) session.save(dCarrier);

			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("dataCarrierDiagramObjectId: " + dataCarrierDiagramObjectId);

			return dataCarrierDiagramObjectId;
			
		} catch (HibernateException ex) {
			log.error("[addDataCarrierDiagramObject]",ex);
		} catch (Exception ex2) {
			log.error("[addDataCarrierDiagramObject]",ex2);
		}
		return null;
	}
	
	public Long addDataCarrierDiagramObjectByObject(DataCarrierDiagramObject dCarrier) {
		try {
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Long dataCarrierDiagramObjectId = (Long) session.save(dCarrier);

			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("dataCarrierDiagramObjectId: " + dataCarrierDiagramObjectId);

			return dataCarrierDiagramObjectId;
			
		} catch (HibernateException ex) {
			log.error("[addDataCarrierDiagramObject]",ex);
		} catch (Exception ex2) {
			log.error("[addDataCarrierDiagramObject]",ex2);
		}
		return null;
	}
	
	public DataCarrierDiagramObject getDataCarrierDiagramObjectById(Long dataCarrierDiagramObjectId) {
		try {
			String hql = "SELECT c FROM DataCarrierDiagramObject as c " +
						"WHERE c.dataCarrierDiagramObjectId=:dataCarrierDiagramObjectId ";

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
		
			Query query = session.createQuery(hql);
			query.setLong("dataCarrierDiagramObjectId", dataCarrierDiagramObjectId);
			DataCarrierDiagramObject dCarrier = (DataCarrierDiagramObject) query.uniqueResult();
		
			tx.commit();
			HibernateUtil.closeSession(idf);
		
			log.debug("select dCarrier " + dCarrier);
		
			return dCarrier;
			
		} catch (HibernateException ex) {
			log.error("[getDataCarrierDiagramObjectById]",ex);
		} catch (Exception ex2) {
			log.error("[getDataCarrierDiagramObjectById]",ex2);
		}
		return null;
	}
	
	public void removeDataCarrierDiagramObject(DataCarrierDiagramObject carrierObject) {
		try {
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			session.delete(carrierObject);

			tx.commit();
			HibernateUtil.closeSession(idf);
			
		} catch (HibernateException ex) {
			log.error("[removeDataCarrierDiagramObject]",ex);
		} catch (Exception ex2) {
			log.error("[removeDataCarrierDiagramObject]",ex2);
		}
	}
	
}
