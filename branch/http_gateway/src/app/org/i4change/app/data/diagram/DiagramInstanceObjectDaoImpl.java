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
import org.i4change.app.hibernate.beans.diagram.DiagramInstanceObject;
import org.i4change.app.hibernate.beans.diagram.DiagramObject;
import org.i4change.app.hibernate.utils.HibernateUtil;

public class DiagramInstanceObjectDaoImpl {
	private static final Log log = LogFactory.getLog(DiagramInstanceObjectDaoImpl.class);	

	private static DiagramInstanceObjectDaoImpl instance = null;

	public static synchronized DiagramInstanceObjectDaoImpl getInstance() {
		if (instance == null) {
			instance = new DiagramInstanceObjectDaoImpl();
		}
		return instance;
	}
	
	
	public void updateDiagramInstanceObject(DiagramInstanceObject diagramInstanceObj) {
		try {

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			session.update(diagramInstanceObj);

			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("updateDiagramInstanceObject: " + diagramInstanceObj);

		} catch (HibernateException ex) {
			log.error("[updateDiagramInstanceObject]",ex);
		} catch (Exception ex2) {
			log.error("[updateDiagramInstanceObject]",ex2);
		}
	}

	
	public Long addDiagramInstanceObject(Diagram diagram, Long user_id, String name, DiagramObject diagramObject, 
			Long startDiagramObjectId, Long endDiagramObjectId, String graphObject, String objInternalUID, String flowType) {
		try {
			DiagramInstanceObject diagramInstanceObject = new DiagramInstanceObject();
			diagramInstanceObject.setInserted(new Date());
			diagramInstanceObject.setInsertedby(user_id);
			diagramInstanceObject.setDiagramObject(diagramObject);
			diagramInstanceObject.setStartDiagramObjectId(startDiagramObjectId);
			diagramInstanceObject.setEndDiagramObjectId(endDiagramObjectId);
			diagramInstanceObject.setGraphObject(graphObject);
			diagramInstanceObject.setDiagram(diagram);
			diagramInstanceObject.setObjInternalUID(objInternalUID);
			diagramInstanceObject.setFlowType(flowType);

			return this.addDiagramInstanceObjectByObject(diagramInstanceObject);
		} catch (HibernateException ex) {
			log.error("[addDiagramInstanceObject]",ex);
		} catch (Exception ex2) {
			log.error("[addDiagramInstanceObject]",ex2);
		}
		return null;
	}
	
	public Long addDiagramInstanceObjectByObject(DiagramInstanceObject diagramInstanceObject) {
		try {

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Long diagramInstanceObjectId = (Long) session.save(diagramInstanceObject);

			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("addDiagramInstanceObject: " + diagramInstanceObjectId);

			return diagramInstanceObjectId;
		} catch (HibernateException ex) {
			log.error("[addDiagramInstanceObjectByObject]",ex);
		} catch (Exception ex2) {
			log.error("[addDiagramInstanceObjectByObject]",ex2);
		}
		return null;
	}
	

	public DiagramInstanceObject getDiagramInstanceObjectById(Long diagramInstanceObjectId) {
		try {
			
			log.debug("diagramInstanceObjectId: "+diagramInstanceObjectId);
			
			String hql = "SELECT c FROM DiagramInstanceObject as c " +
					"WHERE c.diagramInstanceObjectId=:diagramInstanceObjectId";

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			query.setLong("diagramInstanceObjectId", diagramInstanceObjectId);
			DiagramInstanceObject diagramInstanceObject = (DiagramInstanceObject) query.uniqueResult();

			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("select diagramInstanceObject " + diagramInstanceObject);

			return diagramInstanceObject;
		} catch (HibernateException ex) {
			log.error("[getDiagramInstanceObjectById]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramInstanceObjectById]",ex2);
		}
		return null;
	}
	
	public List<DiagramInstanceObject> getDiagramInstanceObjects() {
		try {
			
			String hql = "FROM DiagramInstanceObject";

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			List<DiagramInstanceObject> diagramInstanceObject = query.list();

			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("select diagramInstanceObject " + diagramInstanceObject);

			return diagramInstanceObject;
		} catch (HibernateException ex) {
			log.error("[getDiagramInstanceObjects]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramInstanceObjects]",ex2);
		}
		return null;
	}
	

	public DiagramInstanceObject getDiagramInstanceObjectByOID(String objInternalUID) {
		try {
			
			log.debug("objInternalUID: "+objInternalUID);
			
			String hql = "SELECT c FROM DiagramInstanceObject as c " +
					"WHERE c.objInternalUID=:objInternalUID";

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			query.setString("objInternalUID", objInternalUID);
			DiagramInstanceObject diagramInstanceObject = (DiagramInstanceObject) query.uniqueResult();

			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("select diagramInstanceObject " + diagramInstanceObject);

			return diagramInstanceObject;
		} catch (HibernateException ex) {
			log.error("[getDiagramInstanceObjectByOID]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramInstanceObjectByOID]",ex2);
		}
		return null;
	}
	
	public List<DiagramInstanceObject> getDiagramInstanceObjectListByDiagram(Long diagramId) {
		try {
			String hql = "SELECT c FROM DiagramInstanceObject as c " +
					"WHERE c.diagram.diagramId=:diagramId " +
					"AND c.diagramObject.deleted != :deleted";

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			query.setLong("diagramId", diagramId);
			query.setString("deleted","true");
			List<DiagramInstanceObject> diagramInstanceObject = query.list();

			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("select diagramInstanceObject " + diagramInstanceObject);

			return diagramInstanceObject;
		} catch (HibernateException ex) {
			log.error("[getDiagramObjectById]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramObjectById]",ex2);
		}
		return null;
	}	
	
	
	public List<DiagramInstanceObject> getDiagramInstanceObjectPendingListByDiagram(Long diagramId) {
		try {
			
			//There is no need for a Group By Clause as on Pending Role(object) cannot be twice on the same Diagram 
			// (-Version ... but it can be several Times on the same DiagramNo)
			
			String hql = "SELECT c FROM DiagramInstanceObject c " +
					"WHERE c.diagram.diagramId=:diagramId " +
					"AND c.diagramObject.pending=:pending ";

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			query.setLong("diagramId", diagramId);
			query.setBoolean("pending", true);
			List<DiagramInstanceObject> diagramInstanceObject = query.list();

			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("select diagramInstanceObject " + diagramInstanceObject);

			return diagramInstanceObject;
		} catch (HibernateException ex) {
			log.error("[getDiagramObjectById]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramObjectById]",ex2);
		}
		return null;
	}
	
	public List<DiagramInstanceObject> getDiagramInstanceObjectListByDiagramObjectId(Long diagramObjectId) {
		try {
			String hql = "SELECT c FROM DiagramInstanceObject as c " +
					"WHERE c.diagramObject.diagramObjectId=:diagramObjectId " +
					"GROUP BY diagram.diagramNo";

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			query.setLong("diagramObjectId", diagramObjectId);
			List<DiagramInstanceObject> diagramInstanceObject = query.list();

			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("select diagramInstanceObject " + diagramInstanceObject);

			return diagramInstanceObject;
		} catch (HibernateException ex) {
			log.error("[getDiagramInstanceObjectListByDiagramObjectId]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramInstanceObjectListByDiagramObjectId]",ex2);
		}
		return null;
	}	
	
	public DiagramInstanceObject getLatestDiagramInstanceObjectListByDiagramObjectId(Long diagramObjectId) {
		try {
			String hql = "SELECT c FROM DiagramInstanceObject as c " +
					"WHERE c.diagramObject.diagramObjectId=:diagramObjectId " +
					"AND c.diagram.diagramId = (SELECT MAX(c.diagram.diagramId) FROM DiagramInstanceObject as c WHERE " +
					"c.diagramObject.diagramObjectId=:diagramObjectId)";

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			query.setLong("diagramObjectId", diagramObjectId);
			DiagramInstanceObject diagramInstanceObject = (DiagramInstanceObject) query.uniqueResult();

			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("select diagramInstanceObject " + diagramInstanceObject);

			return diagramInstanceObject;
		} catch (HibernateException ex) {
			log.error("[getDiagramInstanceObjectListByDiagramObjectId]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramInstanceObjectListByDiagramObjectId]",ex2);
		}
		return null;
	}	
	

	public List<DiagramInstanceObject> getDiagramInstanceObjectsByObjectType(
			String objectTypeName, Long organisation_id, String orderBy, boolean orderAsc) {
		try {
			
			log.debug("diagramNo,organisation_id "+organisation_id);
			
			String orderItemValue = "ASC";
			if (!orderAsc) {
				orderItemValue = "DESC";
			}
			
			String hql = "SELECT dInst FROM Diagram c, DiagramInstanceObject dInst " +
					"WHERE c.deleted!=:deleted " +
					"AND c.diagramId = dInst.diagram.diagramId " +
					"AND dInst.diagramObject.objectTypeName = :objectTypeName " +
					"AND c.organisation.organisation_id=:organisation_id " +
					"AND c.diagramId=(SELECT MAX(diagramId) FROM Diagram " +
					"WHERE diagramNo=c.diagramNo) " +
					"ORDER BY "+orderBy+ " " + orderItemValue;

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			query.setLong("organisation_id", organisation_id);
			query.setString("objectTypeName", objectTypeName);
			query.setString("deleted", "true");

			List<DiagramInstanceObject> resultList = query.list();

			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("getDiagramInstanceObjectsByObjectType SIZE " + resultList.size());

			return resultList;
		} catch (HibernateException ex) {
			log.error("[getDiagramInstanceObjectsByObjectType]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramInstanceObjectsByObjectType]",ex2);
		}
		return null;
		
	}

	public List<DiagramInstanceObject> getDiagramInstanceObjectsByObjectId(
			Long diagramObjectId, Long organisation_id, String orderBy, boolean orderAsc) {
		try {
			
			log.debug("diagramNo,organisation_id "+organisation_id);
			
			String orderItemValue = "ASC";
			if (!orderAsc) {
				orderItemValue = "DESC";
			}
			
			String hql = "SELECT dInst FROM Diagram c, DiagramInstanceObject dInst " +
					"WHERE c.deleted!=:deleted " +
					"AND c.diagramId = dInst.diagram.diagramId " +
					"AND dInst.diagramObject.diagramObjectId = :diagramObjectId " +
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

			List<DiagramInstanceObject> resultList = query.list();

			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("getDiagramInstanceObjectsByObjectType SIZE " + resultList.size());

			return resultList;
		} catch (HibernateException ex) {
			log.error("[getDiagramInstanceObjectsByObjectType]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramInstanceObjectsByObjectType]",ex2);
		}
		return null;
		
	}
}
