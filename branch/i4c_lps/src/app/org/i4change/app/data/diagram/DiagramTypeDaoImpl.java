package org.i4change.app.data.diagram;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.i4change.app.hibernate.beans.diagram.DiagramType;
import org.i4change.app.hibernate.beans.diagram.Diagramrevision;
import org.i4change.app.hibernate.utils.HibernateUtil;

public class DiagramTypeDaoImpl {
	
	private static final Log log = LogFactory.getLog(DiagramTypeDaoImpl.class);		
	
	private static DiagramTypeDaoImpl instance = null;

	public static synchronized DiagramTypeDaoImpl getInstance() {
		if (instance == null) {
			instance = new DiagramTypeDaoImpl();
		}
		return instance;
	}	
	
	public Long addDiagramType(Long fieldId, String internalName) {
		try {
			DiagramType diagramType = new DiagramType();
			diagramType.setFieldId(fieldId);
			diagramType.setInserted(new Date());
			diagramType.setInternalName(internalName);

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Long diagramTypeId = (Long) session.save(diagramType);

			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("added id " + diagramTypeId);

			return diagramTypeId;
		} catch (HibernateException ex) {
			log.error("[addDiagramType]",ex);
		} catch (Exception ex2) {
			log.error("[addDiagramType]",ex2);
		}
		return null;
	}
	
	public Long addDiagramTypeByObject(DiagramType diagramType) {
		try {

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Long diagramTypeId = (Long) session.save(diagramType);

			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("added id " + diagramTypeId);

			return diagramTypeId;
		} catch (HibernateException ex) {
			log.error("[addDiagramType]",ex);
		} catch (Exception ex2) {
			log.error("[addDiagramType]",ex2);
		}
		return null;
	}
	
	public DiagramType getDiagramTypeById(Long diagramTypeId) {
		try {
			String hql = "SELECT c FROM DiagramType as c " +
					"WHERE c.diagramTypeId=:diagramTypeId";

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			query.setLong("diagramTypeId", diagramTypeId);
			DiagramType diagramType = (DiagramType) query.uniqueResult();

			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("select diagramType " + diagramType);

			return diagramType;
		} catch (HibernateException ex) {
			log.error("[getDiagramrevisionById]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramrevisionById]",ex2);
		}
		return null;
	}
	
}
