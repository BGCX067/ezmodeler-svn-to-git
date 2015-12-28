package org.i4change.app.data.diagram;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.i4change.app.data.basic.Fieldmanagment;
import org.i4change.app.data.domain.daos.OrganisationDaoImpl;
import org.i4change.app.hibernate.beans.diagram.ObjectType;
import org.i4change.app.hibernate.beans.lang.Fieldlanguagesvalues;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class ObjectTypeDaoImpl extends HibernateDaoSupport {

	private static final Log log = LogFactory.getLog(ObjectTypeDaoImpl.class);	
	
	//Spring loaded beans
	private OrganisationDaoImpl organisationDaoImpl;
	private Fieldmanagment fieldmanagment;

	public Fieldmanagment getFieldmanagment() {
		return fieldmanagment;
	}
	public void setFieldmanagment(Fieldmanagment fieldmanagment) {
		this.fieldmanagment = fieldmanagment;
	}
	
	public OrganisationDaoImpl getOrganisationDaoImpl() {
		return organisationDaoImpl;
	}
	public void setOrganisationDaoImpl(OrganisationDaoImpl organisationDaoImpl) {
		this.organisationDaoImpl = organisationDaoImpl;
	}
	
	public ObjectType getObjectTypeById(Long objectTypeId) {
		try {
			String hql = "SELECT c FROM ObjectType as c " +
					"WHERE c.objectTypeId=:objectTypeId " +
					"AND c.deleted!=:deleted";


			Query query = getSession().createQuery(hql);
			query.setLong("objectTypeId", objectTypeId);
			query.setString("deleted", "true");
			ObjectType objectType = (ObjectType) query.uniqueResult();

			//log.debug("select issueAssignee " + issueAssignee);

			return objectType;
		} catch (HibernateException ex) {
			log.error("[getObjectTypeById]",ex);
		} catch (Exception ex2) {
			log.error("[getObjectTypeById]",ex2);
		}
		return null;
	}
	
	public List<ObjectType> getDiagramObjectTypes() {
		try {
			String hql = "SELECT c FROM ObjectType as c " +
					"WHERE c.deleted!=:deleted";
	
			Query query = getSession().createQuery(hql);
			query.setString("deleted", "true");
			List<ObjectType> property = query.list();
	
			//log.debug("select issueAssignee " + issueAssignee);
	
			return property;
		} catch (HibernateException ex) {
			log.error("[getDiagramObjectTypes]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramObjectTypes]",ex2);
		}
		return null;
	}

	public List<ObjectType> getDiagramObjectTypesByOrg(Long organisation_id) {
		try {
			String hql = "SELECT c FROM ObjectType as c " +
					"WHERE c.organisation.organisation_id = :organisation_id " +
					"AND c.deleted!=:deleted";
	
			Query query = getSession().createQuery(hql);
			query.setLong("organisation_id", organisation_id);
			query.setString("deleted", "true");
			List<ObjectType> property = query.list();
	
			//log.debug("select issueAssignee " + issueAssignee);
	
			return property;
		} catch (HibernateException ex) {
			log.error("[getDiagramObjectTypes]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramObjectTypes]",ex2);
		}
		return null;
	}
	
	public List<ObjectType> getDiagramObjectTypesPublicAndOrg(Long organisation_id) {
		try {
			String hql = "SELECT c FROM ObjectType as c " +
					"WHERE (c.organisation.organisation_id = :organisation_id OR c.isPublic = :isPublic) " +
					"AND c.deleted!=:deleted";
	
			Query query = getSession().createQuery(hql);
			query.setLong("organisation_id", organisation_id);
			query.setBoolean("isPublic", true);
			query.setString("deleted", "true");
			List<ObjectType> property = query.list();
	
			//log.debug("select issueAssignee " + issueAssignee);
	
			return property;
		} catch (HibernateException ex) {
			log.error("[getDiagramObjectTypes]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramObjectTypes]",ex2);
		}
		return null;
	}
	
	public List<ObjectType> getObjectTypesByType(String objectTypeName, Long organisation_id, 
				Boolean isSidebarItem) {
		try {
			String hql = "SELECT c FROM ObjectType as c " +
					"WHERE ( c.organisation.organisation_id = :organisation_id OR c.isPublic = :isPublic) " +
					"AND c.isSidebarItem = :isSidebarItem " +
					"AND ( c.objectTypeName LIKE '' OR c.objectTypeName IS NULL OR c.objectTypeName LIKE :objectTypeName ) " +
					"AND c.deleted!=:deleted";

			Query query = getSession().createQuery(hql);
			query.setLong("organisation_id", organisation_id);
			query.setString("objectTypeName", objectTypeName);
			query.setBoolean("isPublic", true);
			query.setBoolean("isSidebarItem", isSidebarItem);
			query.setString("deleted", "true");
			List<ObjectType> property = query.list();

			//log.debug("select issueAssignee " + issueAssignee);

			return property;
		} catch (HibernateException ex) {
			log.error("[getObjectTypesByType]",ex);
		} catch (Exception ex2) {
			log.error("[getObjectTypesByType]",ex2);
		}
		return null;
	}
	
	public void addDefaultObjectTypes() {
		try {
			
			//canvas.getLabelName(1343),"");
            //this.addItem(canvas.getLabelName(999),"activity");
            //this.addItem(canvas.getLabelName(1006),"processtree");
            //this.addItem(canvas.getLabelName(1005),"processgroup");
            //this.addItem(canvas.getLabelName(1007),"unitFixed");
            //this.addItem(canvas.getLabelName(1002),"departementFixed");
            //this.addItem(canvas.getLabelName(1000),"companyFixed");
            //this.addItem(canvas.getLabelName(1001),"connector");
            //this.addItem(canvas.getLabelName(1003),"flow");
            //this.addItem(canvas.getLabelName(1004),"issueflow");
            //this.addItem(canvas.getLabelName(1314),"datacarrier"
            		
            this.addObjectType("", true, null, "No organization", false, "", 1343L);
            this.addObjectType("activity", true, null, "Activity", false, "", 999L);
            this.addObjectType("processtree", true, null, "Process", false, "", 1006L);
            this.addObjectType("processgroup", true, null, "Processgroup", false, "", 1005L);
            this.addObjectType("unitFixed", true, null, "Role", false, "", 1007L);
            this.addObjectType("departementFixed", true, null, "Department", false, "", 1002L);
            this.addObjectType("companyFixed", true, null, "Company", false, "", 1000L);
            this.addObjectType("connector", true, null, "Connector", false, "", 1001L);
            this.addObjectType("flow", true, null, "In- or Outputflow", false, "", 1003L);
            this.addObjectType("issueflow", true, null, "Idea", false, "", 1004L);
            
            this.addObjectType("datacarrier", true, null, "Data Carrier", true, "", 1314L);
            this.addObjectType("system", true, null, "System", true, "", 1364L);
            this.addObjectType("people", true, null, "People", true, "", 1365L);
            
            this.addObjectType("diagram", true, null, "Diagram", false, "", 1366L);
            
		} catch (Exception err) {
			log.error("[addDefaultObjectTypes]",err);
		}
	}
	
	public Long addObjectType(String name, Boolean isPublic, Long organisation_id, 
			String comment, Boolean isSidebarItem, String objectTypeName, Long labelid) {
		try {
			
			ObjectType oType = new ObjectType();
			
			oType.setName(name);
			oType.setComment(comment);
			oType.setIsPublic(isPublic);
			oType.setIsSidebarItem(isSidebarItem);
			oType.setLabelid(labelid);
			oType.setObjectTypeName(objectTypeName);
			
			if (organisation_id != null) {
				oType.setOrganisation(this.organisationDaoImpl.getOrganisationById(organisation_id));
			}
			oType.setDeleted("false");
			oType.setInserted(new Date());

			Long objectTypeId = (Long) getHibernateTemplate().save(oType);
			
			return objectTypeId;
		} catch (HibernateException ex) {
			log.error("[addObjectType]",ex);
		} catch (Exception ex2) {
			log.error("[addObjectType]",ex2);
		}
		return null;
	}
	
	public Long saveOrUpdateObjectTypeByValue(Long objectTypeId, String oName, Boolean isPublic, Long organisation_id, 
			String comment, Boolean isSidebarItem, String objectTypeName, 
			String valueName, Long languages_id, Long insertedBy) {
		try {
			
			ObjectType oType = null;
			if (objectTypeId == null || objectTypeId == 0 || objectTypeId.equals(0)) {
				oType = new ObjectType();
				
				Long maxLabelId = this.fieldmanagment.selectMaxLabelNumberHelp();
				
				if(maxLabelId == null || maxLabelId < 2000) {
					maxLabelId = new Long(2000);
				} else {
					maxLabelId++;
				}
				log.debug("maxLabelId: "+maxLabelId);
				
				this.fieldmanagment.addFieldByLabelNumber("ObjectType_Label_"+maxLabelId,maxLabelId);
				this.fieldmanagment.addFieldValueByLabeldNumberAndLanguage(
						maxLabelId, languages_id, valueName);
				
				oType.setLabelid(maxLabelId);
				oType.setInserted(new Date());
				oType.setInsertedBy(insertedBy);
				
			} else {
				oType = this.getObjectTypeById(objectTypeId);
				if (oType == null) {
					oType = new ObjectType();
					
					Long maxLabelId = this.fieldmanagment.selectMaxLabelNumberHelp();
					
					if(maxLabelId == null || maxLabelId < 2000) {
						maxLabelId = new Long(2000);
					} else {
						maxLabelId++;
					}
					log.debug("maxLabelId: "+maxLabelId);
					
					this.fieldmanagment.addFieldByLabelNumber("ObjectType_Label_"+maxLabelId,maxLabelId);
					this.fieldmanagment.addFieldValueByLabeldNumberAndLanguage(
							maxLabelId, languages_id, valueName);
					
					oType.setLabelid(maxLabelId);
					oType.setInserted(new Date());
					oType.setInsertedBy(insertedBy);
					
				} else {
					
					Fieldlanguagesvalues fieldValueLabel = this.fieldmanagment.
					getFieldlanguagesvaluesByLabelAndLang(oType.getLabelid(), languages_id);
					fieldValueLabel.setValue(valueName);
					this.fieldmanagment.updateFieldValueByFieldAndLanguage(fieldValueLabel);
					
					oType.setUpdated(new Date());
					oType.setUpdatedBy(insertedBy);
					
				}
			}
			
			
			oType.setName(oName);
			oType.setComment(comment);
			oType.setIsPublic(isPublic);
			oType.setIsSidebarItem(isSidebarItem);
			oType.setObjectTypeName(objectTypeName);
			
			
			if (organisation_id != null) {
				oType.setOrganisation(this.organisationDaoImpl.getOrganisationById(organisation_id));
			}
			oType.setDeleted("false");
			oType.setInserted(new Date());

			if (objectTypeId == null || objectTypeId == 0 || objectTypeId.equals(0)) {
				objectTypeId = (Long) getHibernateTemplate().save(oType);
			} else {
				getHibernateTemplate().update(oType);
			}
			
			return objectTypeId;
		} catch (HibernateException ex) {
			log.error("[saveOrUpdateObjectTypeByValue]",ex);
		} catch (Exception ex2) {
			log.error("[saveOrUpdateObjectTypeByValue]",ex2);
		}
		return null;
	}
	
	public void updateObjectType(Long objectTypeId, String name, Boolean isPublic, Long organisation_id, 
			String comment, Boolean isSidebarItem, String objectTypeName, Long labelid) {
		try {
			
			ObjectType oType = this.getObjectTypeById(objectTypeId);
			if (oType == null) {
				this.addObjectType(name, isPublic, organisation_id, comment, isSidebarItem, objectTypeName, labelid);
			}
			
			oType.setName(name);
			oType.setComment(comment);
			oType.setIsPublic(isPublic);
			oType.setIsSidebarItem(isSidebarItem);
			oType.setObjectTypeName(objectTypeName);
			oType.setLabelid(labelid);
			
			if (organisation_id != null) {
				oType.setOrganisation(this.organisationDaoImpl.getOrganisationById(organisation_id));
			} else {
				oType.setOrganisation(null);
			}
			
			oType.setDeleted("false");
			oType.setInserted(new Date());

			getHibernateTemplate().update(oType);
			
		} catch (HibernateException ex) {
			log.error("[updateObjectType]",ex);
		} catch (Exception ex2) {
			log.error("[updateObjectType]",ex2);
		}
	}

}
