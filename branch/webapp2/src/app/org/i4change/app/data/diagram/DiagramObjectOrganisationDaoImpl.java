package org.i4change.app.data.diagram;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.i4change.app.hibernate.beans.diagram.Diagram;
import org.i4change.app.hibernate.beans.diagram.DiagramObjectOrganisation;
import org.i4change.app.hibernate.utils.HibernateUtil;

public class DiagramObjectOrganisationDaoImpl {
	
	private static final Log log = LogFactory.getLog(DiagramObjectOrganisationDaoImpl.class);	

	private static DiagramObjectOrganisationDaoImpl instance = null;

	public static synchronized DiagramObjectOrganisationDaoImpl getInstance() {
		if (instance == null) {
			instance = new DiagramObjectOrganisationDaoImpl();
		}
		return instance;
	}
	
	public Long addDiagramObjectOrganisation(Long diagramInstanceObjectId, Long diagramObjectId, Long insertedby) {
		try {
			
			DiagramObjectOrganisation diagramObjectOrganisation = new DiagramObjectOrganisation();
			diagramObjectOrganisation.setDiagramInstanceObject(DiagramInstanceObjectDaoImpl.getInstance().getDiagramInstanceObjectById(diagramInstanceObjectId));
			diagramObjectOrganisation.setDiagramObject(DiagramObjectDaoImpl.getInstance().getDiagramObjectById(diagramObjectId));
			diagramObjectOrganisation.setInserted(new Date());
			diagramObjectOrganisation.setInsertedby(insertedby);
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
		
			Long diagramobjectorganisationId = (Long) session.save(diagramObjectOrganisation);
		
			tx.commit();
			HibernateUtil.closeSession(idf);
		
			log.debug("select diagramobjectorganisationId " + diagramobjectorganisationId);
		
			return diagramobjectorganisationId;
		
		} catch (HibernateException ex) {
			log.error("[addDiagramObjectOrganisation]",ex);
		} catch (Exception ex2) {
			log.error("[addDiagramObjectOrganisation]",ex2);
		}
		return null;
	}
	
//	public List<DiagramObjectOrganisation> getDiagramObjectOrganisationByDiagramInstanceObject(Long diagramInstanceObjectId) {
//		try {
//			
//			String hql = "SELECT c FROM DiagramObjectOrganisation as c " +
//					"WHERE c.diagramInstanceObjectId=:diagramInstanceObjectId";
//
//			Object idf = HibernateUtil.createSession();
//			Session session = HibernateUtil.getSession();
//			Transaction tx = session.beginTransaction();
//
//			Query query = session.createQuery(hql);
//			query.setLong("diagramInstanceObjectId", diagramInstanceObjectId);
//			List<DiagramObjectOrganisation> listResult = query.list();
//
//			tx.commit();
//			HibernateUtil.closeSession(idf);
//
//			log.debug("select listResult " + listResult);
//
//			return listResult;
//			
//		} catch (HibernateException ex) {
//			log.error("[getDiagramObjectOrganisationByDiagramInstanceObject]",ex);
//		} catch (Exception ex2) {
//			log.error("[getDiagramObjectOrganisationByDiagramInstanceObject]",ex2);
//		}
//		return null;
//	}
	
	public List<DiagramObjectOrganisation> getDiagramObjectOrganisationByDiagramObject(Long diagramObjectId) {
		try {
			
			String hql = "SELECT c FROM DiagramObjectOrganisation as c " +
					"WHERE c.diagramObject.diagramObjectId=:diagramObjectId";

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			query.setLong("diagramObjectId", diagramObjectId);
			List<DiagramObjectOrganisation> listResult = query.list();

			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("select listResult " + listResult);

			return listResult;
			
		} catch (HibernateException ex) {
			log.error("[getDiagramObjectOrganisationByDiagramObject]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramObjectOrganisationByDiagramObject]",ex2);
		}
		return null;
	}
	
	public List<DiagramObjectOrganisation> getOrganisationsObjectsByDiagramObject(
			Long diagramObjectId, Long organisation_id, String orderBy, boolean orderAsc) {
		try {
			
			log.debug("diagramNo,organisation_id "+organisation_id);
			
			String orderItemValue = "ASC";
			if (!orderAsc) {
				orderItemValue = "DESC";
			}
			
			String hql = "SELECT dOrg FROM Diagram c, DiagramObjectOrganisation dOrg " +
					"WHERE c.deleted!=:deleted " +
					"AND c.diagramId = dOrg.diagramInstanceObject.diagram.diagramId " +
					"AND dOrg.diagramInstanceObject.diagramObject.diagramObjectId = :diagramObjectId " +
					"AND c.organisation.organisation_id=:organisation_id " +
					"AND c.diagramId=(SELECT MAX(diagramId) FROM Diagram " +
					"WHERE diagramNo=c.diagramNo) " +
					"ORDER BY "+orderBy+ " " + orderItemValue;

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			query.setLong("organisation_id", organisation_id);
			query.setLong("diagramObjectId", diagramObjectId);
			query.setString("deleted", "true");

			List<DiagramObjectOrganisation> resultList = query.list();

			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("getDiagramObjectOrganisationByDiagramInstanceObject SIZE " + resultList.size());

			return resultList;
		} catch (HibernateException ex) {
			log.error("[getDiagramObjectOrganisationByDiagramInstanceObject]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramObjectOrganisationByDiagramInstanceObject]",ex2);
		}
		return null;
		
	}
	
	public List<DiagramObjectOrganisation> getObjectsByOrganisationObject(
			Long diagramObjectId, Long organisation_id, String orderBy, boolean orderAsc) {
		try {
			
			log.debug("diagramNo,organisation_id "+organisation_id);
			
			String orderItemValue = "ASC";
			if (!orderAsc) {
				orderItemValue = "DESC";
			}
			
			String hql = "SELECT dOrg FROM Diagram c, DiagramObjectOrganisation dOrg " +
					"WHERE c.deleted!=:deleted " +
					"AND c.diagramId = dOrg.diagramInstanceObject.diagram.diagramId " +
					"AND dOrg.diagramObject.diagramObjectId = :diagramObjectId " +
					"AND c.organisation.organisation_id=:organisation_id " +
					"AND c.diagramId=(SELECT MAX(diagramId) FROM Diagram " +
					"WHERE diagramNo=c.diagramNo) " +
					"ORDER BY "+orderBy+ " " + orderItemValue;

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			query.setLong("organisation_id", organisation_id);
			query.setLong("diagramObjectId", diagramObjectId);
			query.setString("deleted", "true");

			List<DiagramObjectOrganisation> resultList = query.list();

			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("getDiagramObjectOrganisationByDiagramInstanceObject SIZE " + resultList.size());

			return resultList;
		} catch (HibernateException ex) {
			log.error("[getDiagramObjectOrganisationByDiagramInstanceObject]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramObjectOrganisationByDiagramInstanceObject]",ex2);
		}
		return null;
		
	}

}
