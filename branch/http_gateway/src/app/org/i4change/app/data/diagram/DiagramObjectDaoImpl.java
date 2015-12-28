package org.i4change.app.data.diagram;

import java.util.Date;
import java.util.List;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.i4change.app.data.basic.Mailmanagement;
import org.i4change.app.data.domain.daos.OrganisationDaoImpl;
import org.i4change.app.data.user.Usermanagement;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.hibernate.beans.diagram.Assignee;
import org.i4change.app.hibernate.beans.diagram.DataCarrierDiagramObject;
import org.i4change.app.hibernate.beans.diagram.Diagram;
import org.i4change.app.hibernate.beans.diagram.DiagramInstanceObject;
import org.i4change.app.hibernate.beans.diagram.DiagramObject;
import org.i4change.app.hibernate.beans.diagram.DiagramObjectOrganisation;
import org.i4change.app.hibernate.beans.diagram.Diagramrevision;
import org.i4change.app.hibernate.beans.domain.Organisation_Users;
import org.i4change.app.hibernate.utils.HibernateUtil;

public class DiagramObjectDaoImpl {
	 
	private static final Log log = LogFactory.getLog(DiagramObjectDaoImpl.class);	
	
	private AssigneeDaoImpl assigneeDaoImpl;
	private DataCarrierDiagramObjectDaoImpl dataCarrierDiagramObjectDaoImpl;
	private DiagramDaoImpl diagramDaoImpl;
	private DiagramObjectPropertyDaoImpl diagramObjectPropertyDaoImpl;
	
	public AssigneeDaoImpl getAssigneeDaoImpl() {
		return assigneeDaoImpl;
	}
	public void setAssigneeDaoImpl(AssigneeDaoImpl assigneeDaoImpl) {
		this.assigneeDaoImpl = assigneeDaoImpl;
	}
	public DataCarrierDiagramObjectDaoImpl getDataCarrierDiagramObjectDaoImpl() {
		return dataCarrierDiagramObjectDaoImpl;
	}
	public void setDataCarrierDiagramObjectDaoImpl(
			DataCarrierDiagramObjectDaoImpl dataCarrierDiagramObjectDaoImpl) {
		this.dataCarrierDiagramObjectDaoImpl = dataCarrierDiagramObjectDaoImpl;
	}
	public DiagramDaoImpl getDiagramDaoImpl() {
		return diagramDaoImpl;
	}
	public void setDiagramDaoImpl(DiagramDaoImpl diagramDaoImpl) {
		this.diagramDaoImpl = diagramDaoImpl;
	}
	public DiagramObjectPropertyDaoImpl getDiagramObjectPropertyDaoImpl() {
		return diagramObjectPropertyDaoImpl;
	}
	public void setDiagramObjectPropertyDaoImpl(
			DiagramObjectPropertyDaoImpl diagramObjectPropertyDaoImpl) {
		this.diagramObjectPropertyDaoImpl = diagramObjectPropertyDaoImpl;
	}


	private static DiagramObjectDaoImpl instance = null;

	public static synchronized DiagramObjectDaoImpl getInstance() {
		if (instance == null) {
			instance = new DiagramObjectDaoImpl();
		}
		return instance;
	}
	
	public Long saveDiagramInstanceObject(Long diagramObjectId, String objectTypeName, 
			Long user_id, Long organisation_id, Diagram diagram, String name, Long startDiagramObjectId, 
			Long endDiagramObjectId, String graphObject, String objInternalUID, Boolean isPending, Long assigneeUserId,
			String diagramName, Long default_lang_id, String flowType, Map dataCarrierList, Map propertyList) {
		try {
			if (diagramObjectId == null || diagramObjectId == 0){
				diagramObjectId = this.addDiagramObject(user_id, name, objectTypeName, organisation_id, isPending, 
						assigneeUserId, diagramName, default_lang_id);
				
			} else {
				this.updateDiagramObject(diagramObjectId, user_id, name, isPending, assigneeUserId, 
						diagramName, default_lang_id);
			}
			
			//Update Diagrams which have this diagramObjectId as Parent
			List<Diagram> parentdiagram = this.diagramDaoImpl.getDiagramByParentId(diagramObjectId);
			
			log.debug("### Number of Parent Diagram Objects "+parentdiagram.size());
			
			for (Diagram dia : parentdiagram) {
				log.debug("### UPDATE ITEM of Parent Diagram Objects New Name "+name);
				dia.setName(name);
				dia.setUpdated(new Date());
				dia.setUpdatedby(UserDaoImpl.getInstance().getUserById(user_id));
				this.diagramDaoImpl.updateDiagramNameByObject(dia);
			}
			
			log.debug("Get DiagramObject "+diagramObjectId);
			
			DiagramObject diagramObject = this.getDiagramObjectById(diagramObjectId);
			
			log.debug("Save Data Carrier List");
			
			//####################
			//Data Carrier
			//Check for Items to Delete
			if (diagramObject.getDataCarrierDiagramObject() != null) {
				for (Iterator<DataCarrierDiagramObject> iterSet = diagramObject.getDataCarrierDiagramObject().iterator();iterSet.hasNext();) {
					DataCarrierDiagramObject remoteDCarrier = iterSet.next();
					
					boolean found = false;
					
					for (Iterator iter = dataCarrierList.keySet().iterator();iter.hasNext();) {
						Map dataCarrier = (Map) dataCarrierList.get(iter.next());
						Long carrierdiagramObjectId = Long.valueOf(dataCarrier.get("diagramObjectId").toString()).longValue();
						
						if (remoteDCarrier.getDataCarrierObjectdiagramObjectId().equals(carrierdiagramObjectId)) {
							found = true;
							break;
						}
					}
					
					if (!found) {
						//Remove Item
						this.dataCarrierDiagramObjectDaoImpl.removeDataCarrierDiagramObject(remoteDCarrier);
					}
					
				}
			} else {
				//I think we will never run into this Item as Hibernate will at least fill an empty Set, but not NULL
			}
			log.debug("Saveddd 12 Data Carrier List");
			
			if (dataCarrierList != null) {
				
				log.debug("dataCarrierList.size(): "+dataCarrierList.size());
				
				//Check for Items to add
				for (Iterator iter = dataCarrierList.keySet().iterator();iter.hasNext();) {
					Map dataCarrier = (Map) dataCarrierList.get(iter.next());
					Long carrierdiagramObjectId = Long.valueOf(dataCarrier.get("diagramObjectId").toString()).longValue();
					
					boolean found = false;
					
					for (Iterator<DataCarrierDiagramObject> iterSet = diagramObject.getDataCarrierDiagramObject().iterator();iterSet.hasNext();) {
						DataCarrierDiagramObject remoteDCarrier = iterSet.next();
						
						log.debug("remoteDCarrier: "+remoteDCarrier);
						log.debug("remoteDCarrier.getDataCarrierObjectdiagramObjectId() "+remoteDCarrier.getDataCarrierObjectdiagramObjectId());
						
						if (remoteDCarrier.getDataCarrierObjectdiagramObjectId().equals(carrierdiagramObjectId)) {
							found = true;
							break;
						}
					}
					
					if (!found) {
						//Add Item
						this.dataCarrierDiagramObjectDaoImpl.addDataCarrierDiagramObject(carrierdiagramObjectId, 
								diagramObjectId, user_id);
					}
					
				}
			}
			
			log.debug("Saveddd Data Carrier List");
			log.debug("Save Property List "+propertyList);
			log.debug(propertyList);
			
			//####################
			//Property Map
			//save property items
			if (propertyList != null) {
				for (Iterator iter = propertyList.keySet().iterator();iter.hasNext();) {
					Object key = iter.next();
					Map property = (Map) propertyList.get(key);
					Long propertyId = Long.valueOf(property.get("propertyId").toString()).longValue();
					String propertyVal = property.get("propertyVal").toString();
					log.debug("propertyId,propertyVal: "+propertyId+" "+propertyVal);
					
					this.diagramObjectPropertyDaoImpl.addDiagramObjectProperty(diagramObject.getDiagramObjectId(), 
								diagram.getDiagramId(), propertyId, propertyVal, user_id);
					
				}
			}
			log.debug("Saveddd Property List");
			
			return DiagramInstanceObjectDaoImpl.getInstance().addDiagramInstanceObject(diagram, user_id, name, diagramObject, 
						startDiagramObjectId, endDiagramObjectId, graphObject, objInternalUID, flowType);
			
		} catch (HibernateException ex) {
			log.error("[saveDiagramInstanceObject]",ex);
		} catch (Exception ex2) {
			log.error("[saveDiagramInstanceObject]",ex2);
		}
		return null;
	}
	
	public Long addDiagramObject(Long user_id, String name, String objectTypeName, 
			Long organisation_id, Boolean isPending, Long assigneeUserId, String diagramName, 
			Long default_lang_id) {
		try {
			
			DiagramObject diagramObject = new DiagramObject();
			diagramObject.setDeleted("false");
			diagramObject.setInserted(new Date());
			diagramObject.setInsertedby(UserDaoImpl.getInstance().getUserById(user_id));
			diagramObject.setName(name);
			diagramObject.setObjectTypeName(objectTypeName);
			diagramObject.setOrganisation(OrganisationDaoImpl.getInstance().getOrganisationById(organisation_id));
			diagramObject.setPending(isPending);
			
			if (assigneeUserId != null) {
				//Add a mail to the Spool for the Assignee
				Mailmanagement.getInstance().addMailToSpoolAboutNewPendingObject(user_id, assigneeUserId, diagramName, default_lang_id, name);
				diagramObject.setAssignee(this.assigneeDaoImpl.addAssginee(user_id, assigneeUserId));
			} else {
				diagramObject.setAssignee(null);
			}

			return this.addDiagramObjectByObject(diagramObject);
		} catch (HibernateException ex) {
			log.error("[addDiagramObject]",ex);
		} catch (Exception ex2) {
			log.error("[addDiagramObject]",ex2);
		}
		return null;
	}

	public Long addDiagramObjectByObject(DiagramObject diagramObject) {
		try {
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Long diagramObjectId = (Long) session.save(diagramObject);

			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("addDiagramObject: " + diagramObjectId);

			return diagramObjectId;
		} catch (HibernateException ex) {
			log.error("[addDiagramObjectByObject]",ex);
		} catch (Exception ex2) {
			log.error("[addDiagramObjectByObject]",ex2);
		}
		return null;
	}
	
	public Long deleteDiagramObject(Long user_id, Long diagramObjectId) {
		try {
			DiagramObject diagramObject = this.getDiagramObjectById(diagramObjectId);
			if (diagramObject == null) {
				log.error("diagramObject already deleted! ");
				return null;
			}
			diagramObject.setUpdated(new Date());
			diagramObject.setUpdatedby(user_id);
			diagramObject.setDeleted("true");
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			session.update(diagramObject);

			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("addDiagramObject: " + diagramObjectId);

			return diagramObjectId;
		} catch (HibernateException ex) {
			log.error("[addDiagramObject]",ex);
		} catch (Exception ex2) {
			log.error("[addDiagramObject]",ex2);
		}
		return null;
	}

	public Long updateDiagramObject(Long diagramObjectId, Long user_id, String name, 
			Boolean isPending, Long assigneeUserId, String diagramName, 
			Long default_lang_id) {
		try {
			DiagramObject diagramObject = this.getDiagramObjectById(diagramObjectId);
			if (diagramObject == null) {
				log.error("diagramObject already deleted! ");
				return null;
			}
			diagramObject.setUpdated(new Date());
			diagramObject.setUpdatedby(user_id);
			diagramObject.setName(name);
			
			//If this Object was Pending and is now Cleared then Send an EMail to the initial Creator that his Object
			//was created
			if (diagramObject.getPending() && !isPending) {
				Assignee assignee = diagramObject.getAssignee();
				//Cannot be NULL!
				if (assignee != null) {
					//Add a mail to the Spool for the Assignee
					Mailmanagement.getInstance().addMailToSpoolAboutApprovedPendingObject(user_id, assignee.getInsertedby(), 
							diagramName, default_lang_id, name);
				}
			}
			
			diagramObject.setPending(isPending);
			if (assigneeUserId != null) {
				Assignee assignee = diagramObject.getAssignee();
				if (assignee == null) {
					//Add a mail to the Spool for the Assignee
					Mailmanagement.getInstance().addMailToSpoolAboutNewPendingObject(user_id, assigneeUserId, diagramName, default_lang_id, name);
					diagramObject.setAssignee(this.assigneeDaoImpl.addAssginee(user_id, assigneeUserId));
				} else {
					//Only do that on new Assignment
					if (!assignee.getAssignee().getUser_id().equals(assigneeUserId)){
						//Add a mail to the Spool for the Assignee
						Mailmanagement.getInstance().addMailToSpoolAboutChangedPendingObject(user_id, assigneeUserId, diagramName, default_lang_id, name);
						this.assigneeDaoImpl.updateAssginee(user_id, assignee.getAssigneeId(), assigneeUserId);
						diagramObject.setAssignee(this.assigneeDaoImpl.getAssginee(assignee.getAssigneeId()));
					} 
				}
			} else {
				diagramObject.setAssignee(null);
			}

			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			session.update(diagramObject);

			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("addDiagramObject: " + diagramObjectId);

			return diagramObjectId;
		} catch (HibernateException ex) {
			log.error("[addDiagramObject]",ex);
		} catch (Exception ex2) {
			log.error("[addDiagramObject]",ex2);
		}
		return null;
	}

	/**
	 * 
	 * @param diagramObject
	 * @return
	 */
	public Long updateDiagramObjectByObject(DiagramObject diagramObject){
		try {
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			session.update(diagramObject);

			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return diagramObject.getDiagramObjectId();
			
		} catch (HibernateException ex) {
			log.error("[updateDiagramObjectByObject]",ex);
		} catch (Exception ex2) {
			log.error("[updateDiagramObjectByObject]",ex2);
		}
		return null;
	}
	
	public DiagramObject getDiagramObjectById(Long diagramObjectId) {
		try {
			String hql = "SELECT c FROM DiagramObject as c " +
					"WHERE c.diagramObjectId=:diagramObjectId " +
					"AND c.deleted!=:deleted";

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			query.setLong("diagramObjectId", diagramObjectId);
			query.setString("deleted", "true");
			DiagramObject diagramObject = (DiagramObject) query.uniqueResult();

			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("select diagramObject " + diagramObject);

			return diagramObject;
		} catch (HibernateException ex) {
			log.error("[getDiagramObjectById]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramObjectById]",ex2);
		}
		return null;
	}
	
	public List<DiagramObject> getDiagramObjects() {
		try {
			String hql = "FROM DiagramObject";

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			List<DiagramObject> diagramObject = query.list();

			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("select diagramObject list " + diagramObject);

			return diagramObject;
		} catch (HibernateException ex) {
			log.error("[getDiagramObjects]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramObjects]",ex2);
		}
		return null;
	}
	
	public List<DiagramObject> getDiagramObjectsIssues(Long organization_id) {
		try {
			String hql = "SELECT c FROM DiagramObject as c " +
					"WHERE c.organisation.organisation_id=:organization_id " +
					"AND c.objectTypeName=:issueflow " +
					"AND c.deleted!=:deleted";

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			query.setLong("organization_id", organization_id);
			query.setString("issueflow", "issueflow");
			query.setString("deleted", "true");
			List<DiagramObject> diagramObjectList = query.list();

			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("select diagramObjectList " + diagramObjectList);

			return diagramObjectList;
		} catch (HibernateException ex) {
			log.error("[getDiagramObjectsHierarchical]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramObjectsHierarchical]",ex2);
		}
		return null;
	}	

	public List<DiagramObject> getDiagramObjectsIssuesAssignedToMe(Long organization_id, Long user_id) {
		try {
			String hql = "SELECT c FROM DiagramObject c, IssueAssignee a " +
					"WHERE c.organisation.organisation_id=:organization_id " +
					"AND c.diagramObjectId=a.diagramObject.diagramObjectId " +
					"AND a.assignee.user_id=:user_id " +
					"AND c.objectTypeName=:issueflow " +
					"AND c.deleted!=:deleted";

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			query.setLong("organization_id", organization_id);
			query.setLong("user_id",user_id);
			query.setString("issueflow", "issueflow");
			query.setString("deleted", "true");
			List<DiagramObject> diagramObjectList = query.list();

			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("select diagramObjectList " + diagramObjectList);

			return diagramObjectList;
		} catch (HibernateException ex) {
			log.error("[getDiagramObjectsHierarchical]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramObjectsHierarchical]",ex2);
		}
		return null;
	}	

	public List<DiagramObject> getDiagramObjectsPendingObjectsAssignedToMe(Long organization_id, Long user_id) {
		try {
			String hql = "SELECT c FROM DiagramObject as c " +
					"WHERE c.organisation.organisation_id=:organization_id " +
					"AND c.assignee.assignee.user_id=:user_id " +
					"AND c.deleted!=:deleted";

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			query.setLong("organization_id", organization_id);
			query.setLong("user_id", user_id);
			query.setString("deleted", "true");
			List<DiagramObject> diagramObjectList = query.list();

			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("select diagramObjectList " + diagramObjectList);

			return diagramObjectList;
		} catch (HibernateException ex) {
			log.error("[getDiagramObjectsHierarchical]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramObjectsHierarchical]",ex2);
		}
		return null;
	}	
	
	public List<DiagramObject> checkDiagramObjectsForUniqueName(Long organization_id, String objectName, 
			String typeOfObject) {
		try {
			
			String hql = "SELECT c FROM DiagramObject c " +
					"WHERE c.organisation.organisation_id = :organisation_id " +
					"AND c.deleted != :deleted " +
					"AND lower(c.name) LIKE lower(:name) " +
					"AND (c.objectTypeName LIKE :objectTypeName OR c.objectTypeName LIKE :objectTypeNameFixed) ";
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("organisation_id", organization_id);
			query.setString("name", objectName);
			query.setString("deleted", "true");
			query.setString("objectTypeName", typeOfObject);
			query.setString("objectTypeNameFixed", typeOfObject+"Fixed");
			
			//crit.add(Restrictions.ne("pending", true));
			List<DiagramObject> diagramObjectList = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("select checkDiagramObjectsForUniqueName " + diagramObjectList.size());
			
			return diagramObjectList;

		} catch (HibernateException ex) {
			log.error("[checkDiagramObjectsForUniqueName]",ex);
		} catch (Exception ex2) {
			log.error("[checkDiagramObjectsForUniqueName]",ex2);
		}
		return null;
	}
	
	
	/*
	 * ###########################################
	 * General Search Method to get DiagramObjects
	 * but only the NON-Pending ones
	 * ###########################################
	 */
	public List<DiagramObject> selectDiagramObjects(Long organization_id, int start, int  max, 
			Map<Integer,String> objectTypeNames, String orderBy, boolean asc, String search, Boolean notPending){
		try {
			
			String hql = "SELECT c FROM DiagramObject c " +
						"WHERE c.organisation.organisation_id=:organization_id " +
						"AND lower(c.name) LIKE lower(:search) " +
						"AND c.deleted!=:deleted ";
			
			if (notPending != null) {
				hql += "AND c.pending!=:pending ";
			}
			
			//Add ObjectTypeNames
			//"AND c.objectTypeName=:departementFixed OR c.objectTypeName=:unitFixed OR c.objectTypeName=:companyFixed " +
			
			if (objectTypeNames != null) {
				if (objectTypeNames.size() > 0) {
					hql += "AND ( ";
				}
				int k = 0;
				for (Iterator<Integer> iter = objectTypeNames.keySet().iterator();iter.hasNext();) {
					String objectTypeName = objectTypeNames.get(iter.next());
					if (k != 0){
						hql += " OR ";
					}
					k++;
					hql += "c.objectTypeName LIKE '"+objectTypeName+"' ";
				}
				if (objectTypeNames.size() > 0) {
					hql += " ) ";
				}
			}
			
			String orderByAsc = "ASC";
			if (!asc) {
				orderByAsc = "DESC";
			}
			hql += " ORDER BY " + orderBy + " " + orderByAsc;
			
			log.debug("hql: "+hql);
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			query.setString("search", search);
			if (notPending != null) {
				query.setBoolean("pending", notPending);
			}
			query.setLong("organization_id", organization_id);
			query.setString("deleted", "true");
			query.setFirstResult(start);
			query.setMaxResults(max);
			
			List<DiagramObject> diagramList = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			log.debug("diagramList: "+diagramList.size());
			
			return diagramList;				
		} catch (HibernateException ex) {
			log.error("[selectDiagramObjects] ",ex);
		} catch (Exception ex2) {
			log.error("[selectDiagramObjects] ",ex2);
		}
		return null;
	}
	
	public List<Diagram> testSelectParentDiagramObjects(Long organization_id, Long diagramObjectId){
		try {
			
			String hql = "SELECT d from Diagram d " +
						"WHERE d.deleted!=:deleted " +
						"AND d.parentDiagramObject.diagramObjectId = :diagramObjectId " +
						"AND d.organisation.organisation_id=:organization_id " +
						"AND d.diagramId=(SELECT MAX(diagramId) FROM Diagram " +
						"WHERE diagramNo=d.diagramNo)";
			
			//Add ObjectTypeNames
			//"AND c.objectTypeName=:departementFixed OR c.objectTypeName=:unitFixed OR c.objectTypeName=:companyFixed " +

			log.debug("hql: "+hql);
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			query.setLong("diagramObjectId", diagramObjectId);
			query.setLong("organization_id", organization_id);
			query.setString("deleted", "true");
			
			List<Diagram> diagramList = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			log.debug("Number of Diagram: "+diagramList.size());
			
			return diagramList;				
		} catch (HibernateException ex) {
			log.error("[testSelectParentDiagramObjects] ",ex);
		} catch (Exception ex2) {
			log.error("[testSelectParentDiagramObjects] ",ex2);
		}
		return null;
	}
	
	/**
	 * 
	 * @param organization_id
	 * @param start
	 * @param max
	 * @param objectTypeNames
	 * @param orderBy
	 * @param asc
	 * @param search
	 * @return
	 */
	public List<DiagramObject> selectParentDiagramObjects(Long organization_id, int start, int  max, 
			Map<Integer,String> objectTypeNames, String orderBy, boolean asc, String search){
		try {
			
			String hql = "SELECT c FROM DiagramObject c " +
						"WHERE c.organisation.organisation_id=:organization_id " +
						"AND (SELECT d from Diagram d " +
						"WHERE d.deleted!=:deleted " +
						"AND d.parentDiagramObject.diagramObjectId = c.diagramObjectId " +
						"AND d.organisation.organisation_id=:organization_id " +
						"AND d.diagramId=(SELECT MAX(diagramId) FROM Diagram " +
						"WHERE diagramNo=d.diagramNo)" +
						") IS NULL " +
						"AND lower(c.name) LIKE lower(:search) " +
						"AND c.deleted!=:deleted ";
			
			//Add ObjectTypeNames
			//"AND c.objectTypeName=:departementFixed OR c.objectTypeName=:unitFixed OR c.objectTypeName=:companyFixed " +
			
			if (objectTypeNames != null) {
				if (objectTypeNames.size() > 0) {
					hql += "AND ( ";
				}
				int k = 0;
				for (Iterator<Integer> iter = objectTypeNames.keySet().iterator();iter.hasNext();) {
					String objectTypeName = objectTypeNames.get(iter.next());
					if (k != 0){
						hql += " OR ";
					}
					k++;
					hql += "c.objectTypeName LIKE '"+objectTypeName+"' ";
				}
				if (objectTypeNames.size() > 0) {
					hql += " ) ";
				}
			}
			
			String orderByAsc = "ASC";
			if (!asc) {
				orderByAsc = "DESC";
			}
			hql += " ORDER BY " + orderBy + " " + orderByAsc;
			
			log.debug("hql: "+hql);
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			query.setString("search", search);
			query.setLong("organization_id", organization_id);
			query.setString("deleted", "true");
			query.setFirstResult(start);
			query.setMaxResults(max);
			
			List<DiagramObject> diagramList = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			log.debug("diagramList: "+diagramList.size());
			
			return diagramList;				
		} catch (HibernateException ex) {
			log.error("[selectDiagramObjects] ",ex);
		} catch (Exception ex2) {
			log.error("[selectDiagramObjects] ",ex2);
		}
		return null;
	}
	
	public Long selectMaxDiagramObjects(Long organization_id, 
			Map<Integer,String> objectTypeNames, String search, Boolean notPending){
		try {
			
			String hql = "SELECT COUNT(*) as number FROM DiagramObject c " +
						"WHERE c.organisation.organisation_id=:organization_id " +
						"AND lower(c.name) LIKE lower(:search) " +
						"AND c.deleted!=:deleted ";
						
			if (notPending != null) {
				hql += "AND c.pending!=:pending ";
			}
			
			//Add ObjectTypeNames
			//"AND c.objectTypeName=:departementFixed OR c.objectTypeName=:unitFixed OR c.objectTypeName=:companyFixed " +
			
			if (objectTypeNames != null) {
				if (objectTypeNames.size() > 0) {
					hql += "AND ( ";
				}
				int k = 0;
				for (Iterator<Integer> iter = objectTypeNames.keySet().iterator();iter.hasNext();) {
					String objectTypeName = objectTypeNames.get(iter.next());
					if (k != 0){
						hql += " OR ";
					}
					k++;
					hql += "c.objectTypeName LIKE '"+objectTypeName+"' ";
				}
				if (objectTypeNames.size() > 0) {
					hql += " ) ";
				}
			}
			
			log.debug("hql: "+hql);
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			query.setString("search", search);
			if (notPending != null) {
				query.setBoolean("pending", notPending);
			}
			query.setLong("organization_id", organization_id);
			query.setString("deleted", "true");
			
			List ll = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			log.info((Long)ll.get(0));
			
			return (Long)ll.get(0);	
			
		} catch (HibernateException ex) {
			log.error("[selectMaxDiagramObjects] ",ex);
		} catch (Exception ex2) {
			log.error("[selectMaxDiagramObjects] ",ex2);
		}
		return null;
	}
	

	public Long selectParentMaxDiagramObjects(Long organization_id, 
			Map<Integer,String> objectTypeNames, String search){
		try {
			
			

			String hql = "SELECT COUNT(c) FROM DiagramObject c " +
							"WHERE c.organisation.organisation_id=:organization_id " +
							"AND (SELECT d from Diagram d " +
							"WHERE d.deleted!=:deleted " +
							"AND d.parentDiagramObject.diagramObjectId = c.diagramObjectId " +
							"AND d.organisation.organisation_id=:organization_id " +
							"AND d.diagramId=(SELECT MAX(diagramId) FROM Diagram " +
							"WHERE diagramNo=d.diagramNo)" +
							") IS NULL " +
							"AND lower(c.name) LIKE lower(:search) " +
							"AND c.deleted!=:deleted ";
							
			//Add ObjectTypeNames
			//"AND c.objectTypeName=:departementFixed OR c.objectTypeName=:unitFixed OR c.objectTypeName=:companyFixed " +
			
			if (objectTypeNames != null) {
				if (objectTypeNames.size() > 0) {
					hql += "AND ( ";
				}
				int k = 0;
				for (Iterator<Integer> iter = objectTypeNames.keySet().iterator();iter.hasNext();) {
					String objectTypeName = objectTypeNames.get(iter.next());
					if (k != 0){
						hql += " OR ";
					}
					k++;
					hql += "c.objectTypeName LIKE '"+objectTypeName+"' ";
				}
				if (objectTypeNames.size() > 0) {
					hql += " ) ";
				}
			}
			
			log.debug("hql: "+hql);
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			query.setString("search", search);
			query.setLong("organization_id", organization_id);
			query.setString("deleted", "true");
			
			List ll = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			log.info((Long)ll.get(0));
			
			return (Long)ll.get(0);	
			
		} catch (HibernateException ex) {
			log.error("[selectParentMaxDiagramObjects] "+ex);
		} catch (Exception ex2) {
			log.error("[selectParentMaxDiagramObjects] "+ex2);
		}
		return null;
	}
	
}
