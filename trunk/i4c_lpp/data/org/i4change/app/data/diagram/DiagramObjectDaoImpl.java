package org.i4change.app.data.diagram;

import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.i4change.app.data.basic.Mailmanagement;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.data.domain.daos.OrganisationDaoImpl;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.hibernate.beans.diagram.Assignee;
import org.i4change.app.hibernate.beans.diagram.DataCarrierDiagramObject;
import org.i4change.app.hibernate.beans.diagram.Diagram;
import org.i4change.app.hibernate.beans.diagram.DiagramObject;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class DiagramObjectDaoImpl extends HibernateDaoSupport {
	 
	private static final Log log = LogFactory.getLog(DiagramObjectDaoImpl.class);	
	
	private AssigneeDaoImpl assigneeDaoImpl;
	private DataCarrierDiagramObjectDaoImpl dataCarrierDiagramObjectDaoImpl;
	private DataCarrierDiagramObjectHistoryDaoImpl dataCarrierDiagramObjectHistoryDaoImpl;
	private DiagramDaoImpl diagramDaoImpl;
	private DiagramObjectPropertyDaoImpl diagramObjectPropertyDaoImpl;
	private DiagramObjectPropertyHistoryDaoImpl diagramObjectPropertyHistoryDaoImpl;
	private Mailmanagement mailmanagement;
	private DiagramInstanceObjectDaoImpl diagramInstanceObjectDaoImpl;
	private OrganisationDaoImpl organisationDaoImpl;
	private UserDaoImpl userDaoImpl;
	
	public UserDaoImpl getUserDaoImpl() {
		return userDaoImpl;
	}
	public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}
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

	public DiagramObjectPropertyHistoryDaoImpl getDiagramObjectPropertyHistoryDaoImpl() {
		return diagramObjectPropertyHistoryDaoImpl;
	}
	public void setDiagramObjectPropertyHistoryDaoImpl(
			DiagramObjectPropertyHistoryDaoImpl diagramObjectPropertyHistoryDaoImpl) {
		this.diagramObjectPropertyHistoryDaoImpl = diagramObjectPropertyHistoryDaoImpl;
	}

	public DataCarrierDiagramObjectHistoryDaoImpl getDataCarrierDiagramObjectHistoryDaoImpl() {
		return dataCarrierDiagramObjectHistoryDaoImpl;
	}
	public void setDataCarrierDiagramObjectHistoryDaoImpl(
			DataCarrierDiagramObjectHistoryDaoImpl dataCarrierDiagramObjectHistoryDaoImpl) {
		this.dataCarrierDiagramObjectHistoryDaoImpl = dataCarrierDiagramObjectHistoryDaoImpl;
	}

	public DiagramInstanceObjectDaoImpl getDiagramInstanceObjectDaoImpl() {
		return diagramInstanceObjectDaoImpl;
	}
	public void setDiagramInstanceObjectDaoImpl(
			DiagramInstanceObjectDaoImpl diagramInstanceObjectDaoImpl) {
		this.diagramInstanceObjectDaoImpl = diagramInstanceObjectDaoImpl;
	}

	public Mailmanagement getMailmanagement() {
		return mailmanagement;
	}
	public void setMailmanagement(Mailmanagement mailmanagement) {
		this.mailmanagement = mailmanagement;
	}
	
	public OrganisationDaoImpl getOrganisationDaoImpl() {
		return organisationDaoImpl;
	}
	public void setOrganisationDaoImpl(OrganisationDaoImpl organisationDaoImpl) {
		this.organisationDaoImpl = organisationDaoImpl;
	}
	
	public Long saveDiagramInstanceObject(Long diagramObjectId, String objectTypeName, 
			Long user_id, Long organisation_id, Diagram diagram, String name, Long startDiagramObjectId, 
			Long endDiagramObjectId, String graphObject, String objInternalUID, Boolean isPending, Long assigneeUserId,
			String diagramName, Long default_lang_id, String flowType, Vector dataCarrierList, Vector propertyList) {
		try {
			
			log.debug("saveDiagramInstanceObject ############# ");
			log.debug("diagramObjectId"+diagramObjectId);
			log.debug("diagramObjectId"+diagram.getDiagramId());
			log.debug("saveDiagramInstanceObject ############# ");
			
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
				//log.debug("### UPDATE ITEM of Parent Diagram Objects New Name "+name);
				dia.setName(name);
				dia.setUpdated(new Date());
				dia.setUpdatedby(this.userDaoImpl.getUserById(user_id));
				this.diagramDaoImpl.updateDiagramNameByObject(dia);
			}
			
			log.debug("Get DiagramObject "+diagramObjectId);
			
			DiagramObject diagramObject = this.getDiagramObjectById(diagramObjectId);
			
			log.debug("Save Data Carrier List");
			
			//####################
			//Data Carrier
			//Check for Items to Delete but only if the data Carrier is NOT null
			if (dataCarrierList != null) {
				
				//log.debug("D-Carrier List is NOT NULL ANYTHING HAPPENS "+dataCarrierList.size());
			
				if (diagramObject.getDataCarrierDiagramObject() != null) {
					for (Iterator<DataCarrierDiagramObject> iterSet = diagramObject.getDataCarrierDiagramObject().iterator();iterSet.hasNext();) {
						DataCarrierDiagramObject remoteDCarrier = iterSet.next();
						
						boolean found = false;
						
						for (Iterator iter = dataCarrierList.iterator();iter.hasNext();) {
							Map dataCarrier = (Map) iter.next();
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
				
				//log.debug("Saveddd 12 Data Carrier List");
				
				if (dataCarrierList != null) {
					
					//log.debug("dataCarrierList.size(): "+dataCarrierList.size());
					
					//Check for Items to add
					for (Iterator iter = dataCarrierList.iterator();iter.hasNext();) {
						Map dataCarrier = (Map) iter.next();
						Long carrierdiagramObjectId = Long.valueOf(dataCarrier.get("diagramObjectId").toString()).longValue();
						
						//Always add them => the addOrUpdate Function does also take care of existing dataCarriers
						this.dataCarrierDiagramObjectDaoImpl.addOrUpdateDataCarrierDiagramObject(carrierdiagramObjectId, 
								diagramObjectId, user_id);
						
						this.dataCarrierDiagramObjectHistoryDaoImpl.addDataCarrierDiagramObjectHistory(carrierdiagramObjectId, 
								diagramObjectId, user_id, diagram.getDiagramId());
					}
				}
				
				//log.debug("Saveddd Data Carrier List");
				
			} else {
				//log.debug("D-Carrier List is NULL NOTHING HAPPENS ");
			}

			
			log.debug("Save Property List "+propertyList);
			log.debug(propertyList);
			
			//####################
			//Property Map
			//save property items
			if (propertyList != null) {
				for (Iterator iter = propertyList.iterator();iter.hasNext();) {
					Map property = (Map) iter.next();
					Long propertyId = Long.valueOf(property.get("propertyId").toString()).longValue();
					String propertyVal = property.get("propertyVal").toString();
					log.debug("propertyId,propertyVal: "+propertyId+" "+propertyVal);
					
					this.diagramObjectPropertyDaoImpl.addOrUpdateDiagramObjectProperty(diagramObject.getDiagramObjectId(), 
								propertyId, propertyVal, user_id);
					
				}
			}
			log.debug("Saved Property List");
			
			//Save Property Status to History Table
			this.diagramObjectPropertyHistoryDaoImpl.addDiagramObjectProperties(diagramObject.getDiagramObjectId(),
					diagram.getDiagramId());
			
			if (diagram == null || graphObject == null || graphObject.length() == 0) {
				return -54L;
			}
			return this.diagramInstanceObjectDaoImpl.addDiagramInstanceObject(diagram, user_id, name, diagramObject, 
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
			diagramObject.setInsertedby(this.userDaoImpl.getUserById(user_id));
			diagramObject.setName(name);
			diagramObject.setObjectTypeName(objectTypeName);
			diagramObject.setOrganisation(this.organisationDaoImpl.getOrganisationById(organisation_id));
			diagramObject.setPending(isPending);
			diagramObject.setUuid(UUID.randomUUID().toString());
			
			String objectKey = UUID.randomUUID().toString();
			diagramObject.setObjectKey(objectKey);
			
			if (assigneeUserId != null) {
				//Add a mail to the Spool for the Assignee
				this.mailmanagement.addMailToSpoolAboutNewPendingObject(user_id, assigneeUserId, diagramName, default_lang_id, name);
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
	
	public Long addDiagramObjectNonVisualAndProperties(Long user_id, String name, String objectTypeName, 
			Long organisation_id, Boolean isPending, Long assigneeUserId, String diagramName, 
			Long default_lang_id, String comment, Vector propertyList) {
		try {
			
			DiagramObject diagramObject = new DiagramObject();
			diagramObject.setDeleted("false");
			diagramObject.setInserted(new Date());
			diagramObject.setInsertedby(this.userDaoImpl.getUserById(user_id));
			diagramObject.setName(name);
			diagramObject.setObjectTypeName(objectTypeName);
			diagramObject.setOrganisation(this.organisationDaoImpl.getOrganisationById(organisation_id));
			diagramObject.setPending(isPending);
			diagramObject.setUuid(UUID.randomUUID().toString());
			diagramObject.setComment(comment);
			
			String objectKey = UUID.randomUUID().toString();
			diagramObject.setObjectKey(objectKey);
			
			if (assigneeUserId != null) {
				//Add a mail to the Spool for the Assignee
				this.mailmanagement.addMailToSpoolAboutNewPendingObject(user_id, assigneeUserId, diagramName, default_lang_id, name);
				diagramObject.setAssignee(this.assigneeDaoImpl.addAssginee(user_id, assigneeUserId));
			} else {
				diagramObject.setAssignee(null);
			}
			
			Long diagramObjectId = this.addDiagramObjectByObject(diagramObject);
			
			log.debug("propertyList: "+propertyList);
			
			if (propertyList != null) {
				log.debug("propertyList: "+propertyList.size());
				
				for (Iterator iter = propertyList.iterator();iter.hasNext();) {
					Map property = (Map) iter.next();
					Long propertyId = Long.valueOf(property.get("propertyId").toString()).longValue();
					String propertyVal = property.get("propertyVal").toString();
					log.debug("propertyId,propertyVal: "+propertyId+" "+propertyVal);
					this.diagramObjectPropertyDaoImpl.addOrUpdateDiagramObjectProperty(diagramObjectId, 
								propertyId, propertyVal, user_id);
				}
			}
			
			return diagramObjectId;
		} catch (HibernateException ex) {
			log.error("[addDiagramObjectNonVisualAndProperties]",ex);
		} catch (Exception ex2) {
			log.error("[addDiagramObjectNonVisualAndProperties]",ex2);
		}
		return null;
	}

	public Long updateDiagramObjectNonVisualAndProperties(Long diagramObjectId, Long user_id, String name, String objectTypeName, 
			Long organisation_id, Boolean isPending, Long assigneeUserId, String diagramName, 
			Long default_lang_id, String comment, Vector propertyList) {
		try {
			
			DiagramObject diagramObject = this.getDiagramObjectById(diagramObjectId);
			
			if (diagramObject == null) {
				return -1L;
			}
			diagramObject.setDeleted("false");
			diagramObject.setInserted(new Date());
			diagramObject.setInsertedby(this.userDaoImpl.getUserById(user_id));
			diagramObject.setName(name);
			diagramObject.setObjectTypeName(objectTypeName);
			diagramObject.setOrganisation(this.organisationDaoImpl.getOrganisationById(organisation_id));
			diagramObject.setPending(isPending);
			diagramObject.setUuid(UUID.randomUUID().toString());
			diagramObject.setComment(comment);
			
			String objectKey = UUID.randomUUID().toString();
			diagramObject.setObjectKey(objectKey);
			
			if (assigneeUserId != null) {
				//Add a mail to the Spool for the Assignee
				this.mailmanagement.addMailToSpoolAboutNewPendingObject(user_id, assigneeUserId, diagramName, default_lang_id, name);
				diagramObject.setAssignee(this.assigneeDaoImpl.addAssginee(user_id, assigneeUserId));
			} else {
				diagramObject.setAssignee(null);
			}
			
			this.updateDiagramObjectByObject(diagramObject);
			
			log.debug("propertyList: "+propertyList);
			
			if (propertyList != null) {
				log.debug("propertyList: "+propertyList.size());
				
				for (Iterator iter = propertyList.iterator();iter.hasNext();) {
					Map property = (Map) iter.next();
					Long propertyId = Long.valueOf(property.get("propertyId").toString()).longValue();
					String propertyVal = property.get("propertyVal").toString();
					log.debug("propertyId,propertyVal: "+propertyId+" "+propertyVal);
					this.diagramObjectPropertyDaoImpl.addOrUpdateDiagramObjectProperty(diagramObjectId, 
								propertyId, propertyVal, user_id);
				}
			}
			
			return diagramObjectId;
		} catch (HibernateException ex) {
			log.error("[addDiagramObjectNonVisualAndProperties]",ex);
		} catch (Exception ex2) {
			log.error("[addDiagramObjectNonVisualAndProperties]",ex2);
		}
		return null;
	}

	public Long addDiagramObjectByObject(DiagramObject diagramObject) {
		try {
			
			Long diagramObjectId = (Long) getHibernateTemplate().save(diagramObject);

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
			diagramObject.setUpdatedby(this.userDaoImpl.getUserById(user_id));
			diagramObject.setDeleted("true");
			
			getHibernateTemplate().update(diagramObject);

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
			diagramObject.setUpdatedby(this.userDaoImpl.getUserById(user_id));
			diagramObject.setName(name);
			
			//Fill up UUID if not available
			if (diagramObject.getObjectKey().equals(null)) {
				diagramObject.setObjectKey(UUID.randomUUID().toString());
			}
			
			//If this Object was Pending and is now Cleared then Send an EMail to the initial Creator that his Object
			//was created
			if (diagramObject.getPending() && !isPending) {
				Assignee assignee = diagramObject.getAssignee();
				//Cannot be NULL!
				if (assignee != null) {
					//Add a mail to the Spool for the Assignee
					this.mailmanagement.addMailToSpoolAboutApprovedPendingObject(user_id, assignee.getInsertedby(), 
							diagramName, default_lang_id, name);
				}
			}
			
			diagramObject.setPending(isPending);
			if (assigneeUserId != null) {
				Assignee assignee = diagramObject.getAssignee();
				if (assignee == null) {
					//Add a mail to the Spool for the Assignee
					this.mailmanagement.addMailToSpoolAboutNewPendingObject(user_id, assigneeUserId, diagramName, default_lang_id, name);
					diagramObject.setAssignee(this.assigneeDaoImpl.addAssginee(user_id, assigneeUserId));
				} else {
					//Only do that on new Assignment
					if (!assignee.getAssignee().getUser_id().equals(assigneeUserId)){
						//Add a mail to the Spool for the Assignee
						this.mailmanagement.addMailToSpoolAboutChangedPendingObject(user_id, assigneeUserId, diagramName, default_lang_id, name);
						this.assigneeDaoImpl.updateAssginee(user_id, assignee.getAssigneeId(), assigneeUserId);
						diagramObject.setAssignee(this.assigneeDaoImpl.getAssginee(assignee.getAssigneeId()));
					} 
				}
			} else {
				diagramObject.setAssignee(null);
			}

			getHibernateTemplate().update(diagramObject);

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
			
			getHibernateTemplate().update(diagramObject);

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

			Query query = getSession().createQuery(hql);
			query.setLong("diagramObjectId", diagramObjectId);
			query.setString("deleted", "true");
			DiagramObject diagramObject = (DiagramObject) query.uniqueResult();

			log.debug("select diagramObject " + diagramObject);

			return diagramObject;
		} catch (HibernateException ex) {
			log.error("[getDiagramObjectById]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramObjectById]",ex2);
		}
		return null;
	}
	
	public List<Map> getDiagramObjectsNameById(List<DataCarrierDiagramObject> dCarriers) {
		try {
			String diagramObjectIds = "";
			
			log.debug("getDiagramObjectsNameById "+dCarriers.size());
			
			for (int i = 0;i<dCarriers.size();i++) {
				
				DataCarrierDiagramObject dataCarrierDiagramObject = dCarriers.get(i);
				
				diagramObjectIds += "c.diagramObjectId = "+dataCarrierDiagramObject.getDataCarrierObjectdiagramObjectId()+" ";
				
				log.debug("dataCarrierDiagramObject.getDataCarrierDiagramObjectId() "+dataCarrierDiagramObject.getDataCarrierObjectdiagramObjectId());
				log.debug(" -11- "+diagramObjectIds);
				
				if (i < dCarriers.size()-1) {
					diagramObjectIds += "OR ";
				}
				
			}
			
			log.debug("gdiagramObjectIds "+diagramObjectIds);
			
			String hql = "SELECT new map (c.diagramObjectId as diagramObjectId, c.name as name, c.objectTypeName as objectTypeName) " +
					"FROM DiagramObject as c " +
					"WHERE ("+diagramObjectIds+") " +
					"AND c.deleted!=:deleted";

			Query query = getSession().createQuery(hql);
			//query.setString("diagramObjectIds", diagramObjectIds);
			query.setString("deleted", "true");
			List<Map> diagramList = query.list();

			log.debug("select diagramObject " + diagramList);

			return diagramList;
		} catch (HibernateException ex) {
			log.error("[getDiagramObjectNameById]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramObjectNameById]",ex2);
		}
		return null;
	}
	
	public List<DiagramObject> getDiagramObjects() {
		try {
			String hql = "FROM DiagramObject";

			Query query = getSession().createQuery(hql);
			List<DiagramObject> diagramObject = query.list();

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

			Query query = getSession().createQuery(hql);
			query.setLong("organization_id", organization_id);
			query.setString("issueflow", "issueflow");
			query.setString("deleted", "true");
			List<DiagramObject> diagramObjectList = query.list();

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

			Query query = getSession().createQuery(hql);
			query.setLong("organization_id", organization_id);
			query.setLong("user_id",user_id);
			query.setString("issueflow", "issueflow");
			query.setString("deleted", "true");
			List<DiagramObject> diagramObjectList = query.list();

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

			Query query = getSession().createQuery(hql);
			query.setLong("organization_id", organization_id);
			query.setLong("user_id", user_id);
			query.setString("deleted", "true");
			List<DiagramObject> diagramObjectList = query.list();

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
			
			Query query = getSession().createQuery(hql);
			query.setLong("organisation_id", organization_id);
			query.setString("name", objectName);
			query.setString("deleted", "true");
			query.setString("objectTypeName", typeOfObject);
			query.setString("objectTypeNameFixed", typeOfObject+"Fixed");
			
			//crit.add(Restrictions.ne("pending", true));
			List<DiagramObject> diagramObjectList = query.list();

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
			Vector objectTypeNames, String orderBy, boolean asc, String search, Boolean notPending){
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
				for (Iterator<Integer> iter = objectTypeNames.iterator();iter.hasNext();) {
					Object key = iter.next();
					log.debug("key: "+key);
					String objectTypeName = key.toString();
					
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
			
			Query query = getSession().createQuery(hql);
			query.setString("search", search);
			if (notPending != null) {
				query.setBoolean("pending", notPending);
			}
			query.setLong("organization_id", organization_id);
			query.setString("deleted", "true");
			query.setFirstResult(start);
			query.setMaxResults(max);
			
			List<DiagramObject> diagramList = query.list();
			
			log.debug("diagramList: "+diagramList.size());
			
			return diagramList;				
		} catch (HibernateException ex) {
			log.error("[selectDiagramObjects] ",ex);
		} catch (Exception ex2) {
			log.error("[selectDiagramObjects] ",ex2);
		}
		return null;
	}
	
	public List<Map> selectSmallDiagramObjects(Long organization_id, int start, int  max, 
			Vector objectTypeNames, String orderBy, boolean asc, String search, Boolean notPending){
		try {
			
			String hql = "SELECT new map (c.diagramObjectId as diagramObjectId,c.name as name," +
						"c.pending as pending,c.objectTypeName as objectTypeName," +
						"c.inserted as inserted,c.objectTypeName as objectTypeName) " +
						"FROM DiagramObject c " +
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
				for (Iterator<Integer> iter = objectTypeNames.iterator();iter.hasNext();) {
					Object key = iter.next();
					log.debug("key: "+key);
					String objectTypeName = key.toString();
					
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
			
			Query query = getSession().createQuery(hql);
			query.setString("search", search);
			if (notPending != null) {
				query.setBoolean("pending", notPending);
			}
			query.setLong("organization_id", organization_id);
			query.setString("deleted", "true");
			query.setFirstResult(start);
			query.setMaxResults(max);
			
			List<Map> diagramList = query.list();
			
			log.debug("diagramList: "+diagramList.size());
			
			return diagramList;				
		} catch (HibernateException ex) {
			log.error("[selectDiagramObjects] ",ex);
		} catch (Exception ex2) {
			log.error("[selectDiagramObjects] ",ex2);
		}
		return null;
	}
	
	public SearchResult selectSearchDiagramObjectsQueryBuilder(Long organization_id, int start, int  max, 
			Vector objectTypeNames, String orderBy, boolean asc, Vector search, Vector properties, 
			Integer itemStatus){
		try {
			
			String sqlCMD = "";
			
			if (itemStatus == 1) {
				
				sqlCMD = this.getLatestObjectSQLBuilder(organization_id, start, max, objectTypeNames, orderBy, asc, search, properties);
			
			} else if (itemStatus == 2) {
				
				sqlCMD = this.getOrphanSQLBuilder(organization_id, start, max, objectTypeNames, orderBy, asc, search, properties);
				
			} else if (itemStatus == 0) {
				
			    sqlCMD = this.getObjectSQLBuilder(organization_id, start, max, objectTypeNames, orderBy, asc, search, properties);
				
			}
			
			return this.selectSearchDiagramObjects(sqlCMD);
			
		} catch (HibernateException ex) {
			log.error("[selectSearchDiagramObjectsQueryBuilder] ",ex);
		} catch (Exception ex2) {
			log.error("[selectSearchDiagramObjectsQueryBuilder] ",ex2);
		}
		return null;
	}
	
	private String getObjectSQLBuilder(Long organization_id, int start,
			int max, Vector objectTypeNames, String orderBy, boolean asc,
			Vector search, Vector properties) throws Exception {
		
		String sqlCMD = "";
		
			sqlCMD += "SELECT SQL_CALC_FOUND_ROWS c.diagramobject_id, c.name, c.objecttypename, c.inserted, c.updated " +
						"FROM diagramobject c ";
			
			
		if (properties == null || properties.size() == 0) {
			
			sqlCMD += "WHERE c.organisation_id="+organization_id+" " +
							//"AND lower(c.name) LIKE lower('"+search+"') " +
							"AND c.deleted NOT LIKE 'true' ";
			if (search.size() > 0) {
				
				sqlCMD += " AND (";
				for (int i=0;i<search.size();i++) {
					Hashtable ht = (Hashtable) search.get(i);
					
					String searchVal = ht.get("search").toString();
					if (searchVal.length() == 0) {
						searchVal = "%";
					} else {
						searchVal = "%" + searchVal +"%";
					}
					
					if (i==0) {
						sqlCMD += "lower(c.name) LIKE lower('"+searchVal+"') ";
					} else {
						sqlCMD += ht.get("comperator")+" lower(c.name) LIKE lower('"+searchVal+"') ";
					}
				}
				sqlCMD += ") ";
			}
			
		} else {
			
			sqlCMD += "LEFT OUTER JOIN diagramobject_property d ON (d.diagramobject_id = c.diagramobject_id) " +
						"WHERE c.organisation_id="+organization_id+" " +
						//"AND lower(c.name) LIKE lower('"+search+"') " +
						"AND c.deleted NOT LIKE 'true' ";
			
			if (search.size() > 0) {
				
				sqlCMD += " AND (";
				for (int i=0;i<search.size();i++) {
					Hashtable ht = (Hashtable) search.get(i);
					
					String searchVal = ht.get("search").toString();
					if (searchVal.length() == 0) {
						searchVal = "%";
					} else {
						searchVal = "%" + searchVal +"%";
					}
					
					if (i==0) {
						sqlCMD += "lower(c.name) LIKE lower('"+searchVal+"') ";
					} else {
						sqlCMD += ht.get("comperator")+" lower(c.name) LIKE lower('"+searchVal+"') ";
					}
				}
				sqlCMD += ") ";
			}
			
			if (properties.size()>1) {
				sqlCMD += "AND ( ";
			} 
	
			for (int i=0;i<properties.size();i++) {
				
				Hashtable item = (Hashtable) properties.get(i);
				
				log.debug("Hashtable "+item);
				
				if (i == 0) {
				
					if (properties.size()==1) {
						sqlCMD += " AND ";
					} 
					
					sqlCMD += " ( ";
					
					String searchValue = item.get("value").toString();
					if (searchValue == null || searchValue.length() == 0) {
						searchValue = "%";
		        	} else {
		        		searchValue = "%" + searchValue + "%";
		        	}
					
					if (item.get("comperator").equals("like")) {
						sqlCMD += "lower(d.value) LIKE lower('"+searchValue+"') ";
					} else if (item.get("comperator").equals("NotLike")) {
						sqlCMD += "lower(d.value) NOT LIKE lower('"+searchValue+"') ";
					} else if (item.get("comperator").equals("smaller")){
						sqlCMD += "d.value < "+item.get("value")+" ";
					} else if (item.get("comperator").equals("bigger")){
						sqlCMD += "d.value > "+item.get("value")+" ";
					} else if (item.get("comperator").equals("smallerEqual")){
						sqlCMD += "d.value <= "+item.get("value")+" ";
					} else if (item.get("comperator").equals("biggerEqual")){
						sqlCMD += "d.value >= "+item.get("value")+" ";
					} else {
						sqlCMD += "d.value "+item.get("comperator")+" "+item.get("value")+" ";
					}
					
					if (Integer.parseInt(item.get("property_id").toString()) != 0) {
						sqlCMD += "AND d.property_id = "+item.get("property_id")+" ";
					}
					sqlCMD += " ) ";
					
				} else {
					
					sqlCMD += " " + item.get("andOrComperator") + " ( ";
					
					String searchValue = item.get("value").toString();
					if (searchValue == null || searchValue.length() == 0) {
						searchValue = "%";
		        	} else {
		        		searchValue = "%" + searchValue + "%";
		        	}
					
					if (item.get("comperator").equals("like")) {
						sqlCMD += "lower(d.value) LIKE lower('"+searchValue+"') ";
					} else if (item.get("comperator").equals("NotLike")) {
						sqlCMD += "lower(d.value) NOT LIKE lower('"+searchValue+"') ";
					} else if (item.get("comperator").equals("smaller")){
						sqlCMD += "d.value < "+item.get("value")+" ";
					} else if (item.get("comperator").equals("bigger")){
						sqlCMD += "d.value > "+item.get("value")+" ";
					} else if (item.get("comperator").equals("smallerEqual")){
						sqlCMD += "d.value <= "+item.get("value")+" ";
					} else if (item.get("comperator").equals("biggerEqual")){
						sqlCMD += "d.value >= "+item.get("value")+" ";
					} else {
						sqlCMD += "d.value "+item.get("comperator")+" "+item.get("value")+" ";
					}
					
					if (Integer.parseInt(item.get("property_id").toString()) != 0) {
						sqlCMD += "AND d.property_id = "+item.get("property_id")+" ";
					}
					sqlCMD += " ) ";
					
				}
				
			}
			
			if (properties.size()>1) {
				sqlCMD += " ) ";
			}
			
		}
		
		//Not for orphan Search!!
		if (objectTypeNames != null) {
			if (objectTypeNames.size() > 0) {
				sqlCMD += "AND ( ";
			}
			int k = 0;
			for (Iterator<Integer> iter = objectTypeNames.iterator();iter.hasNext();) {
				Object key = iter.next();
				log.debug("key: "+key);
				String objectTypeName = key.toString();
				
				if (k != 0){
					sqlCMD += " OR ";
				}
				k++;
				sqlCMD += "c.objecttypename LIKE '"+objectTypeName+"' ";
			}
			if (objectTypeNames.size() > 0) {
				sqlCMD += " ) ";
			}
		}
		
		//Do we really have to Group By at this stage or can we use the GROUP BY on the hole UNION statement?
		sqlCMD += " GROUP BY c.diagramobject_id ";
		
		return sqlCMD;
		
	}
	private String getLatestObjectSQLBuilder(Long organization_id, int start, int  max, 
			Vector objectTypeNames, String orderBy, boolean asc, Vector search, Vector properties) throws Exception {
		//Vector properties = new Vector();
		String sqlCMD = "( ";
		
			sqlCMD += "SELECT SQL_CALC_FOUND_ROWS c.diagramobject_id, c.name, c.objecttypename, c.inserted, c.updated " +
						"FROM diagramobject c ";
			
			sqlCMD += "INNER JOIN diagraminstanceobject dioObject ON (dioObject.diagramobject_id = c.diagramobject_id) " +
					  "INNER JOIN diagramsummary diaSummery ON (diaSummery.diagram_id = dioObject.diagram_id) ";
			
		if (properties == null || properties.size() == 0) {
			
							//"LEFT OUTER JOIN diagramobject_property d ON (d.diagramobject_id = c.diagramobject_id) " +
			sqlCMD += "WHERE c.organisation_id="+organization_id+" " +
							//"AND lower(c.name) LIKE lower('"+search+"') " +
							"AND c.deleted NOT LIKE 'true' " +
							"AND diaSummery.deleted NOT LIKE 'true' ";
			
			if (search.size() > 0) {
			
				sqlCMD += " AND (";
				for (int i=0;i<search.size();i++) {
					Hashtable ht = (Hashtable) search.get(i);
					
					String searchVal = ht.get("search").toString();
					if (searchVal.length() == 0) {
						searchVal = "%";
					} else {
						searchVal = "%" + searchVal +"%";
					}
					
					if (i==0) {
						sqlCMD += "lower(c.name) LIKE lower('"+searchVal+"') ";
					} else {
						sqlCMD += ht.get("comperator")+" lower(c.name) LIKE lower('"+searchVal+"') ";
					}
				}
				sqlCMD += ") ";
			}
							//"AND lower(d.value) LIKE lower('"+search+"') ";
			
		} else {
			
			sqlCMD += "LEFT OUTER JOIN diagramobject_property d ON (d.diagramobject_id = c.diagramobject_id) " +
						"WHERE c.organisation_id="+organization_id+" " +
						//"AND lower(c.name) LIKE lower('"+search+"') " +
						"AND c.deleted NOT LIKE 'true' " +
						"AND diaSummery.deleted NOT LIKE 'true' ";
			
			if (search.size() > 0) {
				
				sqlCMD += " AND (";
				for (int i=0;i<search.size();i++) {
					Hashtable ht = (Hashtable) search.get(i);
					
					String searchVal = ht.get("search").toString();
					if (searchVal.length() == 0) {
						searchVal = "%";
					} else {
						searchVal = "%" + searchVal +"%";
					}
					
					if (i==0) {
						sqlCMD += "lower(c.name) LIKE lower('"+searchVal+"') ";
					} else {
						sqlCMD += ht.get("comperator")+" lower(c.name) LIKE lower('"+searchVal+"') ";
					}
				}
				sqlCMD += ") ";
			}
						//"AND lower(d.value) LIKE lower('"+search+"') ";
			
			if (properties.size()>1) {
				sqlCMD += "AND ( ";
			} 

			for (int i=0;i<properties.size();i++) {
				
				Hashtable item = (Hashtable) properties.get(i);
				
				log.debug("Hashtable "+item);
				
				if (i == 0) {
				
					if (properties.size()==1) {
						sqlCMD += " AND ";
					} 
					
					sqlCMD += " ( ";
					
					String searchValue = item.get("value").toString();
					if (searchValue == null || searchValue.length() == 0) {
						searchValue = "%";
		        	} else {
		        		searchValue = "%" + searchValue + "%";
		        	}
					
					if (item.get("comperator").equals("like")) {
						sqlCMD += "lower(d.value) LIKE lower('"+searchValue+"') ";
					} else if (item.get("comperator").equals("NotLike")) {
						sqlCMD += "lower(d.value) NOT LIKE lower('"+searchValue+"') ";
					} else if (item.get("comperator").equals("smaller")){
						sqlCMD += "d.value < "+item.get("value")+" ";
					} else if (item.get("comperator").equals("bigger")){
						sqlCMD += "d.value > "+item.get("value")+" ";
					} else if (item.get("comperator").equals("smallerEqual")){
						sqlCMD += "d.value <= "+item.get("value")+" ";
					} else if (item.get("comperator").equals("biggerEqual")){
						sqlCMD += "d.value >= "+item.get("value")+" ";
					} else {
						sqlCMD += "d.value "+item.get("comperator")+" "+item.get("value")+" ";
					}
					
					if (Integer.parseInt(item.get("property_id").toString()) != 0) {
						sqlCMD += "AND d.property_id = "+item.get("property_id")+" ";
					}
					sqlCMD += " ) ";
					
				} else {
					
					sqlCMD += " " + item.get("andOrComperator") + " ( ";
					
					String searchValue = item.get("value").toString();
					if (searchValue == null || searchValue.length() == 0) {
						searchValue = "%";
		        	} else {
		        		searchValue = "%" + searchValue + "%";
		        	}
					
					if (item.get("comperator").equals("like")) {
						sqlCMD += "lower(d.value) LIKE lower('"+searchValue+"') ";
					} else if (item.get("comperator").equals("NotLike")) {
						sqlCMD += "lower(d.value) NOT LIKE lower('"+searchValue+"') ";
					} else if (item.get("comperator").equals("smaller")){
						sqlCMD += "d.value < "+item.get("value")+" ";
					} else if (item.get("comperator").equals("bigger")){
						sqlCMD += "d.value > "+item.get("value")+" ";
					} else if (item.get("comperator").equals("smallerEqual")){
						sqlCMD += "d.value <= "+item.get("value")+" ";
					} else if (item.get("comperator").equals("biggerEqual")){
						sqlCMD += "d.value >= "+item.get("value")+" ";
					} else {
						sqlCMD += "d.value "+item.get("comperator")+" "+item.get("value")+" ";
					}
					
					if (Integer.parseInt(item.get("property_id").toString()) != 0) {
						sqlCMD += "AND d.property_id = "+item.get("property_id")+" ";
					}
					sqlCMD += " ) ";
					
				}
				
			}
			
			if (properties.size()>1) {
				sqlCMD += " ) ";
			}
			
		}
		
		//Not for orphan Search!!
		if (objectTypeNames != null) {
			if (objectTypeNames.size() > 0) {
				sqlCMD += "AND ( ";
			}
			int k = 0;
			for (Iterator<Integer> iter = objectTypeNames.iterator();iter.hasNext();) {
				Object key = iter.next();
				log.debug("key: "+key);
				String objectTypeName = key.toString();
				
				if (k != 0){
					sqlCMD += " OR ";
				}
				k++;
				sqlCMD += "c.objecttypename LIKE '"+objectTypeName+"' ";
			}
			if (objectTypeNames.size() > 0) {
				sqlCMD += " ) ";
			}
		}
		
		//Do we really have to Group By at this stage or can we use the GROUP BY on the hole UNION statement?
		sqlCMD += " GROUP BY c.diagramobject_id ";
		
		/*
		 * Add the Union to query also those Objects that have a connection to a Diagram through the
		 * Non-Visual Shape Objects (Dynamic Object Types)
		 * 
		 */
		sqlCMD += ") UNION ( ";
		
			sqlCMD += "SELECT c.diagramobject_id, c.name, c.objecttypename, c.inserted, c.updated " +
						"FROM diagramobject c ";
			
			sqlCMD += "INNER JOIN datacarrier_diagramobject dCarrier ON (c.diagramobject_id = dCarrier.datacarrier_object_diagramobject_id) " +
					  "INNER JOIN diagraminstanceobject dioObject ON (dioObject.diagramobject_id = dCarrier.diagramobject_id) " +
					  "INNER JOIN diagramsummary diaSummery ON (diaSummery.diagram_id = dioObject.diagram_id) ";
			
			/*
			 * Add the where statements to the query
			 */
			if (properties == null || properties.size() == 0) {
				
				sqlCMD += "WHERE c.organisation_id="+organization_id+" " +
								//"AND lower(c.name) LIKE lower('"+search+"') " +
								"AND c.deleted NOT LIKE 'true' " +
								"AND diaSummery.deleted NOT LIKE 'true' ";
				if (search.size() > 0) {
					
					sqlCMD += " AND (";
					for (int i=0;i<search.size();i++) {
						Hashtable ht = (Hashtable) search.get(i);
						if (i==0) {
							sqlCMD += "lower(c.name) LIKE lower('"+ht.get("search")+"') ";
						} else {
							sqlCMD += ht.get("comperator")+" lower(c.name) LIKE lower('"+ht.get("search")+"') ";
						}
					}
					sqlCMD += ") ";
				}
				
			} else {
				
				sqlCMD += "LEFT OUTER JOIN diagramobject_property d ON (d.diagramobject_id = c.diagramobject_id) " +
							"WHERE c.organisation_id="+organization_id+" " +
							//"AND lower(c.name) LIKE lower('"+search+"') " +
							"AND c.deleted NOT LIKE 'true' " +
							"AND diaSummery.deleted NOT LIKE 'true' ";
				
							//"AND lower(d.value) LIKE lower('"+search+"') ";
				if (search.size() > 0) {
					
					sqlCMD += " AND (";
					for (int i=0;i<search.size();i++) {
						Hashtable ht = (Hashtable) search.get(i);
						
						String searchVal = ht.get("search").toString();
						if (searchVal.length() == 0) {
							searchVal = "%";
						} else {
							searchVal = "%" + searchVal +"%";
						}
						
						if (i==0) {
							sqlCMD += "lower(c.name) LIKE lower('"+searchVal+"') ";
						} else {
							sqlCMD += ht.get("comperator")+" lower(c.name) LIKE lower('"+searchVal+"') ";
						}
					}
					sqlCMD += ") ";
				}
				
				if (properties.size()>1) {
					sqlCMD += "AND ( ";
				} 
			
				for (int i=0;i<properties.size();i++) {
					
					Hashtable item = (Hashtable) properties.get(i);
					
					log.debug("Hashtable "+item);
					
					if (i == 0) {
					
						if (properties.size()==1) {
							sqlCMD += " AND ";
						} 
						
						sqlCMD += " ( ";
						
						String searchValue = item.get("value").toString();
						if (searchValue == null || searchValue.length() == 0) {
							searchValue = "%";
			        	} else {
			        		searchValue = "%" + searchValue + "%";
			        	}
						
						if (item.get("comperator").equals("like")) {
							sqlCMD += "lower(d.value) LIKE lower('"+searchValue+"') ";
						} else if (item.get("comperator").equals("NotLike")) {
							sqlCMD += "lower(d.value) NOT LIKE lower('"+searchValue+"') ";
						} else if (item.get("comperator").equals("smaller")){
							sqlCMD += "d.value < "+item.get("value")+" ";
						} else if (item.get("comperator").equals("bigger")){
							sqlCMD += "d.value > "+item.get("value")+" ";
						} else if (item.get("comperator").equals("smallerEqual")){
							sqlCMD += "d.value <= "+item.get("value")+" ";
						} else if (item.get("comperator").equals("biggerEqual")){
							sqlCMD += "d.value >= "+item.get("value")+" ";
						} else {
							sqlCMD += "d.value "+item.get("comperator")+" "+item.get("value")+" ";
						}
						
						if (Integer.parseInt(item.get("property_id").toString()) != 0) {
							sqlCMD += "AND d.property_id = "+item.get("property_id")+" ";
						}
						sqlCMD += " ) ";
						
					} else {
						
						sqlCMD += " " + item.get("andOrComperator") + " ( ";
						
						String searchValue = item.get("value").toString();
						if (searchValue == null || searchValue.length() == 0) {
							searchValue = "%";
			        	} else {
			        		searchValue = "%" + searchValue + "%";
			        	}
						
						if (item.get("comperator").equals("like")) {
							sqlCMD += "lower(d.value) LIKE lower('"+searchValue+"') ";
						} else if (item.get("comperator").equals("NotLike")) {
							sqlCMD += "lower(d.value) NOT LIKE lower('"+searchValue+"') ";
						} else if (item.get("comperator").equals("smaller")){
							sqlCMD += "d.value < "+item.get("value")+" ";
						} else if (item.get("comperator").equals("bigger")){
							sqlCMD += "d.value > "+item.get("value")+" ";
						} else if (item.get("comperator").equals("smallerEqual")){
							sqlCMD += "d.value <= "+item.get("value")+" ";
						} else if (item.get("comperator").equals("biggerEqual")){
							sqlCMD += "d.value >= "+item.get("value")+" ";
						} else {
							sqlCMD += "d.value "+item.get("comperator")+" "+item.get("value")+" ";
						}
						
						if (Integer.parseInt(item.get("property_id").toString()) != 0) {
							sqlCMD += "AND d.property_id = "+item.get("property_id")+" ";
						}
						sqlCMD += " ) ";
						
					}
					
				}
				
				if (properties.size()>1) {
					sqlCMD += " ) ";
				}
				
			}
			
			if (objectTypeNames != null) {
				if (objectTypeNames.size() > 0) {
					sqlCMD += "AND ( ";
				}
				int k = 0;
				for (Iterator<Integer> iter = objectTypeNames.iterator();iter.hasNext();) {
					Object key = iter.next();
					log.debug("key: "+key);
					String objectTypeName = key.toString();
					
					if (k != 0){
						sqlCMD += " OR ";
					}
					k++;
					sqlCMD += "c.objecttypename LIKE '"+objectTypeName+"' ";
				}
				if (objectTypeNames.size() > 0) {
					sqlCMD += " ) ";
				}
			}
			
			//Do we really have to Group By at this stage or can we use the GROUP BY on the hole UNION statement?
			sqlCMD += " GROUP BY c.diagramobject_id ";
			
		sqlCMD += " ) ";
		
		String orderByAsc = "ASC";
		if (!asc) {
			orderByAsc = "DESC";
		}
		sqlCMD += " ORDER BY " + orderBy + " " + orderByAsc;
		
		sqlCMD += " LIMIT "+start+", "+max;
		
		return sqlCMD;
	}
	
	private String getOrphanSQLBuilder(Long organization_id, int start, int  max, 
			Vector objectTypeNames, String orderBy, boolean asc, Vector search, Vector properties) throws Exception {
		//Vector properties = new Vector();
		
		
		String sqlGetLatestShapeCMD = "SELECT c.diagramobject_id " +
						"FROM diagramobject c ";
			
		sqlGetLatestShapeCMD += "INNER JOIN diagraminstanceobject dioObject ON (dioObject.diagramobject_id = c.diagramobject_id) " +
					  "INNER JOIN diagramsummary diaSummery ON (diaSummery.diagram_id = dioObject.diagram_id) " +
					  "WHERE diaSummery.deleted NOT LIKE 'true'";
			
		
		//Do we really have to Group By at this stage or can we use the GROUP BY on the hole UNION statement?
		sqlGetLatestShapeCMD += " GROUP BY c.diagramobject_id ";
		
		/*
		 * Add the Union to query also those Objects that have a connection to a Diagram through the
		 * Non-Visual Shape Objects (Dynamic Object Types)
		 * 
		 */
		
		String sqlGetDynamicObjectsCMD = "SELECT c.diagramobject_id " +
											"FROM diagramobject c ";
			
		sqlGetDynamicObjectsCMD += "INNER JOIN datacarrier_diagramobject dCarrier ON (c.diagramobject_id = dCarrier.datacarrier_object_diagramobject_id) " +
								  "INNER JOIN diagraminstanceobject dioObject ON (dioObject.diagramobject_id = dCarrier.diagramobject_id) " +
								  "INNER JOIN diagramsummary diaSummery ON (diaSummery.diagram_id = dioObject.diagram_id) " +
								  "WHERE diaSummery.deleted NOT LIKE 'true'";
		
		
		//Do we really have to Group By at this stage or can we use the GROUP BY on the hole UNION statement?
		sqlGetDynamicObjectsCMD += " GROUP BY c.diagramobject_id ";
			
		
		/*
		 * In case its a query to get all the orphans we need to inverse some of the statements
		 * 
		 */
		String sqlCMD = "SELECT SQL_CALC_FOUND_ROWS c.diagramobject_id, c.name, c.objecttypename, c.inserted, c.updated " +
							"FROM diagramobject c ";
		
		if (properties == null || properties.size() == 0) {
			sqlCMD +=	"WHERE c.diagramobject_id " +
							"NOT IN (" +
							sqlGetLatestShapeCMD +
							" ) " +
							"AND c.diagramobject_id " +
							"NOT IN (" +
							sqlGetDynamicObjectsCMD +
							" ) " +
							"AND c.organisation_id="+organization_id+" " +
							//"AND lower(c.name) LIKE lower('"+search+"') " +
							"AND c.deleted NOT LIKE 'true' ";
			
			if (search.size() > 0) {
				
				sqlCMD += " AND (";
				for (int i=0;i<search.size();i++) {
					Hashtable ht = (Hashtable) search.get(i);
					
					String searchVal = ht.get("search").toString();
					if (searchVal.length() == 0) {
						searchVal = "%";
					} else {
						searchVal = "%" + searchVal +"%";
					}
					
					if (i==0) {
						sqlCMD += "lower(c.name) LIKE lower('"+searchVal+"') ";
					} else {
						sqlCMD += ht.get("comperator")+" lower(c.name) LIKE lower('"+searchVal+"') ";
					}
				}
				sqlCMD += ") ";
			}
		} else {
			
			sqlCMD += "LEFT OUTER JOIN diagramobject_property d ON (d.diagramobject_id = c.diagramobject_id) " +
						"WHERE c.diagramobject_id " +
						"NOT IN (" +
						sqlGetLatestShapeCMD +
						" ) " +
						"AND c.diagramobject_id " +
						"NOT IN (" +
						sqlGetDynamicObjectsCMD +
						" ) " +
						"AND c.organisation_id="+organization_id+" " +
						//"AND lower(c.name) LIKE lower('"+search+"') " +
						"AND c.deleted NOT LIKE 'true' ";
			
			if (search.size() > 0) {
				
				sqlCMD += " AND (";
				for (int i=0;i<search.size();i++) {
					Hashtable ht = (Hashtable) search.get(i);
					
					String searchVal = ht.get("search").toString();
					if (searchVal.length() == 0) {
						searchVal = "%";
					} else {
						searchVal = "%" + searchVal +"%";
					}
					
					if (i==0) {
						sqlCMD += "lower(c.name) LIKE lower('"+searchVal+"') ";
					} else {
						sqlCMD += ht.get("comperator")+" lower(c.name) LIKE lower('"+searchVal+"') ";
					}
				}
				sqlCMD += ") ";
			}
			
			if (properties.size()>1) {
				sqlCMD += "AND ( ";
			} 
		
			for (int i=0;i<properties.size();i++) {
				
				Hashtable item = (Hashtable) properties.get(i);
				
				log.debug("Hashtable "+item);
				
				if (i == 0) {
				
					if (properties.size()==1) {
						sqlCMD += " AND ";
					} 
					
					sqlCMD += " ( ";
					
					String searchValue = item.get("value").toString();
					if (searchValue == null || searchValue.length() == 0) {
						searchValue = "%";
		        	} else {
		        		searchValue = "%" + searchValue + "%";
		        	}
					
					if (item.get("comperator").equals("like")) {
						sqlCMD += "lower(d.value) LIKE lower('"+searchValue+"') ";
					} else if (item.get("comperator").equals("NotLike")) {
						sqlCMD += "lower(d.value) NOT LIKE lower('"+searchValue+"') ";
					} else if (item.get("comperator").equals("smaller")){
						sqlCMD += "d.value < "+item.get("value")+" ";
					} else if (item.get("comperator").equals("bigger")){
						sqlCMD += "d.value > "+item.get("value")+" ";
					} else if (item.get("comperator").equals("smallerEqual")){
						sqlCMD += "d.value <= "+item.get("value")+" ";
					} else if (item.get("comperator").equals("biggerEqual")){
						sqlCMD += "d.value >= "+item.get("value")+" ";
					} else {
						sqlCMD += "d.value "+item.get("comperator")+" "+item.get("value")+" ";
					}
					
					if (Integer.parseInt(item.get("property_id").toString()) != 0) {
						sqlCMD += "AND d.property_id = "+item.get("property_id")+" ";
					}
					sqlCMD += " ) ";
					
				} else {
					
					sqlCMD += " " + item.get("andOrComperator") + " ( ";
					
					String searchValue = item.get("value").toString();
					if (searchValue == null || searchValue.length() == 0) {
						searchValue = "%";
		        	} else {
		        		searchValue = "%" + searchValue + "%";
		        	}
					
					if (item.get("comperator").equals("like")) {
						sqlCMD += "lower(d.value) LIKE lower('"+searchValue+"') ";
					} else if (item.get("comperator").equals("NotLike")) {
						sqlCMD += "lower(d.value) NOT LIKE lower('"+searchValue+"') ";
					} else if (item.get("comperator").equals("smaller")){
						sqlCMD += "d.value < "+item.get("value")+" ";
					} else if (item.get("comperator").equals("bigger")){
						sqlCMD += "d.value > "+item.get("value")+" ";
					} else if (item.get("comperator").equals("smallerEqual")){
						sqlCMD += "d.value <= "+item.get("value")+" ";
					} else if (item.get("comperator").equals("biggerEqual")){
						sqlCMD += "d.value >= "+item.get("value")+" ";
					} else {
						sqlCMD += "d.value "+item.get("comperator")+" "+item.get("value")+" ";
					}
					
					if (Integer.parseInt(item.get("property_id").toString()) != 0) {
						sqlCMD += "AND d.property_id = "+item.get("property_id")+" ";
					}
					sqlCMD += " ) ";
					
				}
				
			}
			
			if (properties.size()>1) {
				sqlCMD += " ) ";
			}
			
		}
		

		if (objectTypeNames != null) {
			if (objectTypeNames.size() > 0) {
				sqlCMD += "AND ( ";
			}
			int k = 0;
			for (Iterator<Integer> iter = objectTypeNames.iterator();iter.hasNext();) {
				Object key = iter.next();
				log.debug("key: "+key);
				String objectTypeName = key.toString();
				
				if (k != 0){
					sqlCMD += " OR ";
				}
				k++;
				sqlCMD += "c.objecttypename LIKE '"+objectTypeName+"' ";
			}
			if (objectTypeNames.size() > 0) {
				sqlCMD += " ) ";
			}
		}
		
		String orderByAsc = "ASC";
		if (!asc) {
			orderByAsc = "DESC";
		}
		sqlCMD += " ORDER BY " + orderBy + " " + orderByAsc;
		
		sqlCMD += " LIMIT "+start+", "+max;
		
		return sqlCMD;
	}
	
	public SearchResult selectSearchDiagramObjects(String sqlCMD){
		try {
			
			log.debug("sqlCMD: "+sqlCMD);
			
			SQLQuery query = getSession().createSQLQuery(sqlCMD);
			
			query.addScalar("diagramobject_id",Hibernate.LONG);
			query.addScalar("name",Hibernate.STRING);
			query.addScalar("objecttypename",Hibernate.STRING);
			
			query.addScalar("inserted",Hibernate.TIMESTAMP);
			query.addScalar("updated",Hibernate.TIMESTAMP);
			
			List<Object[]> diagramObjectList = query.list();
			
			//String[] items = query.getReturnAliases();
			
			String numberSqlCMD = "SELECT FOUND_ROWS()";
			
			SQLQuery queryNumberFounds = getSession().createSQLQuery(numberSqlCMD);
			
			List numberOfItems = queryNumberFounds.list();
			
			
			log.debug("numberOfItems: "+Long.parseLong(numberOfItems.get(0).toString()));
			
			log.debug("diagramList: "+diagramObjectList.size());
			
			SearchResult sResult = new SearchResult();
			
//			log.debug("items "+items.length);
//			for (int i=0;i<items.length;i++) {
//				log.debug(items[i]);
//			}
			
			List<Map<Integer,String>> returnList = new LinkedList<Map<Integer,String>>();
			for (Object[] item : diagramObjectList) {
				log.debug(item);
				Map<Integer,String> record = new HashMap<Integer,String>();
				//record.put(0, "hans");
				for (int i=0;i<item.length;i++) {
					Object itemObj = item[i];
					
					log.debug(itemObj);
					if (itemObj != null) {
						record.put(i, itemObj.toString());
					} else {
						record.put(i, null);
					}
					
				}
				returnList.add(record);
			}
			
			sResult.setObjectName("Custom Query");
			
			//org.hibernate.pretty.Formatter format = new Formatter(sqlCMD);


			//org.hibernate.loader.custom.sql.SQLQueryParser
			
			sResult.setSqlQuery(sqlCMD);
			sResult.setRecords(Long.parseLong(numberOfItems.get(0).toString()));
			sResult.setResult(returnList);
			
			return sResult;				
		} catch (HibernateException ex) {
			log.error("[selectSearchDiagramObjects] ",ex);
		} catch (Exception ex2) {
			log.error("[selectSearchDiagramObjects] ",ex2);
		}
		return null;
	}

	public List<Map> _selectSearchDiagramObjects(Long organization_id, int start, int  max, 
			Vector objectTypeNames, String orderBy, boolean asc, String search){
		try {
			
			String hql = "SELECT new map (c.diagramObjectId as diagramObjectId,c.name as name," +
						"c.pending as pending, c.objectTypeName as objectTypeName," +
						"c.inserted as inserted,c.objectTypeName as objectTypeName) " +
						"FROM DiagramObject c " +
						"WHERE c.organisation.organisation_id="+organization_id+" " +
						"AND lower(c.name) LIKE lower('"+search+"') " +
						"AND c.deleted!='true' ";
			
			//Add ObjectTypeNames
			//"AND c.objectTypeName=:departementFixed OR c.objectTypeName=:unitFixed OR c.objectTypeName=:companyFixed " +
			
			if (objectTypeNames != null) {
				if (objectTypeNames.size() > 0) {
					hql += "AND ( ";
				}
				int k = 0;
				for (Iterator<Integer> iter = objectTypeNames.iterator();iter.hasNext();) {
					Object key = iter.next();
					log.debug("key: "+key);
					String objectTypeName = key.toString();
					
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
			
			Query query = getSession().createQuery(hql);
			
			//query.setString("search", search);
			//query.setLong("organization_id", organization_id);
			
			query.setFirstResult(start);
			query.setMaxResults(max);
			
			List<Map> diagramList = query.list();
			
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
			
			Query query = getSession().createQuery(hql);
			query.setLong("diagramObjectId", diagramObjectId);
			query.setLong("organization_id", organization_id);
			query.setString("deleted", "true");
			
			List<Diagram> diagramList = query.list();
			
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
	public List<Map> selectParentDiagramObjects(Long organization_id, int start, int  max, 
			Vector objectTypeNames, String orderBy, boolean asc, String search){
		try {
			
//			String hql = "SELECT new map (c.diagramObjectId as diagramObjectId,c.name as name," +
//						"c.pending as pending,c.objectTypeName as objectTypeName,c.inserted as inserted) " +
//						"FROM DiagramObject c " +
//						"WHERE c.organisation.organisation_id=:organization_id " +
//						"AND (SELECT d from Diagram d " +
//						"WHERE d.deleted!=:deleted " +
//						"AND d.parentDiagramObject.diagramObjectId = c.diagramObjectId " +
//						"AND d.organisation.organisation_id=:organization_id " +
//						"AND d.diagramId=(SELECT MAX(diagramId) FROM Diagram " +
//						"WHERE diagramNo=d.diagramNo)" +
//						") IS NULL " +
//						"AND lower(c.name) LIKE lower(:search) " +
//						"AND c.deleted!=:deleted ";
			
			String hql = "SELECT new map (c.diagramObjectId as diagramObjectId,c.name as name," +
						"c.pending as pending,c.objectTypeName as objectTypeName,c.inserted as inserted) " +
						"FROM DiagramObject c " +
						"WHERE c.organisation.organisation_id=:organization_id " +
						"AND lower(c.name) LIKE lower(:search) " +
						"AND c.deleted!=:deleted ";
			
			//Add ObjectTypeNames
			//"AND c.objectTypeName=:departementFixed OR c.objectTypeName=:unitFixed OR c.objectTypeName=:companyFixed " +
			
			if (objectTypeNames != null) {
				if (objectTypeNames.size() > 0) {
					hql += "AND ( ";
				}
				int k = 0;
				for (Iterator<String> iter = objectTypeNames.iterator();iter.hasNext();) {
					String objectTypeName = iter.next();
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
			
			Query query = getSession().createQuery(hql);
			query.setString("search", search);
			query.setLong("organization_id", organization_id);
			query.setString("deleted", "true");
			query.setFirstResult(start);
			query.setMaxResults(max);
			
			List<Map> diagramList = query.list();
			
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
			Vector objectTypeNames, String search, Boolean notPending){
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
				for (Iterator<Integer> iter = objectTypeNames.iterator();iter.hasNext();) {
					Object key = iter.next();
					log.debug("key: "+key);
					String objectTypeName = key.toString();
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
			
			Query query = getSession().createQuery(hql);
			query.setString("search", search);
			if (notPending != null) {
				query.setBoolean("pending", notPending);
			}
			query.setLong("organization_id", organization_id);
			query.setString("deleted", "true");
			
			List ll = query.list();
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
			Vector objectTypeNames, String search){
		try {
			
//			String hql = "SELECT COUNT(c) FROM DiagramObject c " +
//							"WHERE c.organisation.organisation_id=:organization_id " +
//							"AND (SELECT d from Diagram d " +
//							"WHERE d.deleted!=:deleted " +
//							"AND d.parentDiagramObject.diagramObjectId = c.diagramObjectId " +
//							"AND d.organisation.organisation_id=:organization_id " +
//							"AND d.diagramId=(SELECT MAX(diagramId) FROM Diagram " +
//							"WHERE diagramNo=d.diagramNo)" +
//							") IS NULL " +
//							"AND lower(c.name) LIKE lower(:search) " +
//							"AND c.deleted!=:deleted ";
			
			String hql = "SELECT COUNT(c) FROM DiagramObject c " +
							"WHERE c.organisation.organisation_id=:organization_id " +
							"AND lower(c.name) LIKE lower(:search) " +
							"AND c.deleted!=:deleted ";
							
			//Add ObjectTypeNames
			//"AND c.objectTypeName=:departementFixed OR c.objectTypeName=:unitFixed OR c.objectTypeName=:companyFixed " +
			
			log.debug("objectTypeNames "+objectTypeNames);
			
			if (objectTypeNames != null) {
				if (objectTypeNames.size() > 0) {
					hql += "AND ( ";
				}
				int k = 0;
				for (Iterator<String> iter = objectTypeNames.iterator();iter.hasNext();) {
					String objectTypeName = iter.next();
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
			
			Query query = getSession().createQuery(hql);
			query.setString("search", search);
			query.setLong("organization_id", organization_id);
			query.setString("deleted", "true");
			
			List ll = query.list();
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
