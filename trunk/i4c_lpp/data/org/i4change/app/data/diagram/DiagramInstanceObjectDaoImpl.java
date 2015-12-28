package org.i4change.app.data.diagram;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.i4change.app.hibernate.beans.diagram.Diagram;
import org.i4change.app.hibernate.beans.diagram.DiagramInstanceObject;
import org.i4change.app.hibernate.beans.diagram.DiagramObject;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class DiagramInstanceObjectDaoImpl extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(DiagramInstanceObjectDaoImpl.class);	

	
	public void updateDiagramInstanceObject(DiagramInstanceObject diagramInstanceObj) {
		try {

			getHibernateTemplate().update(diagramInstanceObj);

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

			Long diagramInstanceObjectId = (Long) getHibernateTemplate().save(diagramInstanceObject);

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

			Query query = getSession().createQuery(hql);
			query.setLong("diagramInstanceObjectId", diagramInstanceObjectId);
			DiagramInstanceObject diagramInstanceObject = (DiagramInstanceObject) query.uniqueResult();

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

			Query query = getSession().createQuery(hql);
			List<DiagramInstanceObject> diagramInstanceObject = query.list();

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

			Query query = getSession().createQuery(hql);
			query.setString("objInternalUID", objInternalUID);
			DiagramInstanceObject diagramInstanceObject = (DiagramInstanceObject) query.uniqueResult();

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

			Query query = getSession().createQuery(hql);
			query.setLong("diagramId", diagramId);
			query.setString("deleted","true");
			List<DiagramInstanceObject> diagramInstanceObject = query.list();

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

			Query query = getSession().createQuery(hql);
			query.setLong("diagramId", diagramId);
			query.setBoolean("pending", true);
			List<DiagramInstanceObject> diagramInstanceObject = query.list();

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

			Query query = getSession().createQuery(hql);
			query.setLong("diagramObjectId", diagramObjectId);
			List<DiagramInstanceObject> diagramInstanceObject = query.list();

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

			Query query = getSession().createQuery(hql);
			query.setLong("diagramObjectId", diagramObjectId);
			DiagramInstanceObject diagramInstanceObject = (DiagramInstanceObject) query.uniqueResult();

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

			Query query = getSession().createQuery(hql);
			query.setLong("organisation_id", organisation_id);
			query.setString("objectTypeName", objectTypeName);
			query.setString("deleted", "true");

			List<DiagramInstanceObject> resultList = query.list();

			log.debug("getDiagramInstanceObjectsByObjectType SIZE " + resultList.size());

			return resultList;
		} catch (HibernateException ex) {
			log.error("[getDiagramInstanceObjectsByObjectType]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramInstanceObjectsByObjectType]",ex2);
		}
		return null;
		
	}

	/**
	 * 
	 * @param diagramObjectId
	 * @param organisation_id
	 * @param orderBy
	 * @param orderAsc
	 * @return
	 */
	public List<DiagramInstanceObject> getLatestDiagramInstanceObjectsByObjectId(
			Long diagramObjectId, Long organisation_id, String orderBy, boolean orderAsc) {
		try {
			
			log.debug("diagramNo,organisation_id "+organisation_id);
			
			String orderItemValue = "ASC";
			if (!orderAsc) {
				orderItemValue = "DESC";
			}
			
			String hql = "SELECT dInst FROM Diagram c, DiagramSummary as ds, DiagramInstanceObject dInst " +
					"WHERE c.deleted!=:deleted " +
					"AND c.diagramId = dInst.diagram.diagramId " +
					"AND dInst.diagramObject.diagramObjectId = :diagramObjectId " +
					"AND c.organisation.organisation_id=:organisation_id " +
					"AND ds.diagram.diagramId = dInst.diagram.diagramId " +
					"ORDER BY "+orderBy+ " " + orderItemValue;

			Query query = getSession().createQuery(hql);
			query.setLong("organisation_id", organisation_id);
			query.setLong("diagramObjectId", diagramObjectId);
			query.setString("deleted", "true");

			List<DiagramInstanceObject> resultList = query.list();

			log.debug("getDiagramInstanceObjectsByObjectType SIZE " + resultList.size());

			return resultList;
		} catch (HibernateException ex) {
			log.error("[getDiagramInstanceObjectsByObjectType]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramInstanceObjectsByObjectType]",ex2);
		}
		return null;
		
	}
	
	public List<DiagramInstanceObject> getDiagramInstanceObjectsForIssues(int start, int max, String orderBy, 
			boolean orderAsc, Long organisation_id, String search) {
		try {
			
			String orderItemValue = "ASC";
			if (!orderAsc) {
				orderItemValue = "DESC";
			}
			
			String hql = "SELECT dio FROM IssueAssignee as c, DiagramSummary as ds, DiagramInstanceObject dio " +
					"WHERE c.diagramObject.objectTypeName=:issueflow " +
					"AND dio.diagramObject.diagramObjectId = c.diagramObject.diagramObjectId " +
					"AND ds.diagram.diagramId = dio.diagram.diagramId " +
					"AND lower(c.diagramObject.name) LIKE lower(:search) " +
					"AND c.diagramObject.organisation.organisation_id = :organisation_id " +
					"AND c.deleted!=:deleted AND c.diagramObject.deleted!=:deleted " +
					"ORDER BY "+orderBy+ " " + orderItemValue;

			Query query = getSession().createQuery(hql);
			query.setString("deleted", "true");
			query.setString("search", search);
			query.setString("issueflow", "issueflow");
			query.setLong("organisation_id", organisation_id);
			query.setMaxResults(max);
			query.setFirstResult(start);
			List<DiagramInstanceObject> diagramInstanceObjects = query.list();

			return diagramInstanceObjects;
		} catch (HibernateException ex) {
			log.error("[getDiagramInstanceObjectsForIssues]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramInstanceObjectsForIssues]",ex2);
		}
		return null;
	}


	public List<DiagramInstanceObject> getDiagramInstanceObjectsForIssuesByAssignee(
			int start, int max, String orderBy, boolean orderAsc,
			Long organization_id, String search, Long assigne_user_id) {
		try {
			
			String orderItemValue = "ASC";
			if (!orderAsc) {
				orderItemValue = "DESC";
			}
			
			String hql = "SELECT dio FROM IssueAssignee as c, DiagramSummary as ds, DiagramInstanceObject dio " +
					"WHERE c.diagramObject.objectTypeName=:issueflow " +
					"AND dio.diagramObject.diagramObjectId = c.diagramObject.diagramObjectId " +
					"AND ds.diagram.diagramId = dio.diagram.diagramId " +
					"AND lower(c.diagramObject.name) LIKE lower(:search) " +
					"AND c.diagramObject.organisation.organisation_id = :organisation_id " +
					"AND c.deleted!=:deleted AND c.diagramObject.deleted!=:deleted " +
					"AND c.assignee.user_id = :user_id " +
					"ORDER BY "+orderBy+ " " + orderItemValue;

			Query query = getSession().createQuery(hql);
			query.setString("deleted", "true");
			query.setString("search", search);
			query.setString("issueflow", "issueflow");
			query.setLong("organisation_id", organization_id);
			query.setLong("user_id", assigne_user_id);
			query.setMaxResults(max);
			query.setFirstResult(start);
			List<DiagramInstanceObject> diagramInstanceObjects = query.list();

			return diagramInstanceObjects;
		} catch (HibernateException ex) {
			log.error("[getDiagramInstanceObjectsForIssuesByAssignee]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramInstanceObjectsForIssuesByAssignee]",ex2);
		}
		return null;
	}
}
