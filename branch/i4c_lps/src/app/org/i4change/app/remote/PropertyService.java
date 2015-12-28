package org.i4change.app.remote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.AuthLevelmanagement;
import org.i4change.app.data.basic.Sessionmanagement;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.data.diagram.DiagramObjectPropertyDaoImpl;
import org.i4change.app.data.diagram.PropertyDaoImpl;
import org.i4change.app.data.diagram.PropertyValidationTypeDaoImpl;
import org.i4change.app.data.domain.daos.OrganisationDaoImpl;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.hibernate.beans.diagram.DiagramObjectProperty;
import org.i4change.app.hibernate.beans.diagram.Property;
import org.i4change.app.hibernate.beans.diagram.PropertyValidationType;

public class PropertyService {
	
	private static final Log log = LogFactory.getLog(PropertyService.class);	

	private Application application;
	private PropertyDaoImpl propertyDaoImpl;
	private OrganisationDaoImpl organisationDaoImpl;
	private DiagramObjectPropertyDaoImpl diagramObjectPropertyDaoImpl;
	private PropertyValidationTypeDaoImpl propertyValidationTypeDaoImpl;

	public Application getApplication() {
		return application;
	}
	public void setApplication(Application application) {
		this.application = application;
	}
	
	public PropertyDaoImpl getPropertyDaoImpl() {
		return propertyDaoImpl;
	}
	public void setPropertyDaoImpl(PropertyDaoImpl propertyDaoImpl) {
		this.propertyDaoImpl = propertyDaoImpl;
	}
	
	public OrganisationDaoImpl getOrganisationDaoImpl() {
		return organisationDaoImpl;
	}
	public void setOrganisationDaoImpl(OrganisationDaoImpl organisationDaoImpl) {
		this.organisationDaoImpl = organisationDaoImpl;
	}
	
	public DiagramObjectPropertyDaoImpl getDiagramObjectPropertyDaoImpl() {
		return diagramObjectPropertyDaoImpl;
	}
	public void setDiagramObjectPropertyDaoImpl(
			DiagramObjectPropertyDaoImpl diagramObjectPropertyDaoImpl) {
		this.diagramObjectPropertyDaoImpl = diagramObjectPropertyDaoImpl;
	}
	
	public PropertyValidationTypeDaoImpl getPropertyValidationTypeDaoImpl() {
		return propertyValidationTypeDaoImpl;
	}
	public void setPropertyValidationTypeDaoImpl(
			PropertyValidationTypeDaoImpl propertyValidationTypeDaoImpl) {
		this.propertyValidationTypeDaoImpl = propertyValidationTypeDaoImpl;
	}
	
	public Property getPropertyById(String SID, Long propertyId) {
		try {
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	return this.propertyDaoImpl.getPropertyById(propertyId);
	        }
	        
		} catch (Exception err) {
			log.error("[getPropertyById]",err);
		}
		return null; 
	}
	
	public Long saveOrUpdateProperty(String SID, Map values) {
		try {
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	
	        	log.debug(values);
	        	
	        	Long propertyId = Long.valueOf(values.get("propertyId").toString()).longValue();
		        String propertyName = values.get("propertyName").toString();
		        String tooltip = values.get("tooltip").toString();
		        Long languages_id = Long.valueOf(values.get("languages_id").toString()).longValue();
		        String comment = values.get("comment").toString();
		        Boolean isPublic = Boolean.valueOf(values.get("isPublic").toString()).booleanValue();
		        Long organisation_id = Long.valueOf(values.get("organisation_id").toString()).longValue();
		        String objectTypeName = values.get("objectTypeName").toString();
		        Long propertyValidationTypeId = Long.valueOf(values.get("propertyValidationTypeId").toString()).longValue();
		        
		        log.debug("propertyId: "+propertyId);
		        
		        if (propertyId == null || propertyId == 0 || propertyId.equals(0)) {
		        	return this.propertyDaoImpl.addProperty(propertyName, tooltip, languages_id, comment, isPublic, 
		        			organisation_id, objectTypeName, users_id, propertyValidationTypeId);
		        } else {
		        	return this.propertyDaoImpl.updateProperty(propertyId, propertyName, tooltip, languages_id, comment, isPublic, 
		        			organisation_id, objectTypeName, users_id, propertyValidationTypeId);
		        }
	        }
	        
		} catch (Exception err) {
			log.error("[saveOrUpdateproperty]",err);
		}
		return new Long(-1); 
	}
	
	
	public Long deleteProperty(String SID, Long propertyId) {
		try {
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	
	        	return this.propertyDaoImpl.deleteProperty(propertyId, users_id);
	        	
	        }
			
		} catch (Exception err) {
			log.error("[updateProperty]",err);
		}
		return new Long(-1);		
	}
	
	public SearchResult getProperties(String SID, int start ,int max, String orderby, boolean asc) {
		try {
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.getInstance().checkAdminLevel(user_level)) {
	        	
	        	return this.propertyDaoImpl.getProperties(start, max, orderby, asc);
	        	
	        }
			
		} catch (Exception err) {
			log.error("[getProperties]",err);
		}
		return null;
	}
	
	public SearchResult getPropertiesByOrganization(String SID, Long organization_id,
			int start ,int max, String orderby, boolean asc) {
		try {
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	
	        	return this.propertyDaoImpl.getPropertiesByOrganization(organization_id, start, max, orderby, asc);
	        	
	        }
			
		} catch (Exception err) {
			log.error("[getPropertiesByOrganization]",err);
		}
		return null;
	}
	
	public List<Property> getPropertiesByObjectType(String SID, Long organisation_id, String objectTypeName) {
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	
	        	return this.propertyDaoImpl.getPropertiesByType(objectTypeName, organisation_id);
	        	
	        }
			
		} catch (Exception err) {
			log.error("[getPropertiesByObjectType]",err);
		}
		return null;
	}
	
	public Map<String,List<Property>> getPropertiesByOrganization(String SID, Long organisation_id) {
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	
	        	HashMap<String,List<Property>> myProperties = new HashMap<String,List<Property>>();
	        	myProperties.put("activity",this.propertyDaoImpl.getPropertiesByType("activity", organisation_id));
	        	myProperties.put("processtree",this.propertyDaoImpl.getPropertiesByType("processtree", organisation_id));
	        	myProperties.put("processgroup",this.propertyDaoImpl.getPropertiesByType("processgroup", organisation_id));
	        	myProperties.put("unitFixed",this.propertyDaoImpl.getPropertiesByType("unitFixed", organisation_id));
	        	myProperties.put("departementFixed",this.propertyDaoImpl.getPropertiesByType("departementFixed", organisation_id));
	        	myProperties.put("companyFixed",this.propertyDaoImpl.getPropertiesByType("companyFixed", organisation_id));
	        	myProperties.put("connector",this.propertyDaoImpl.getPropertiesByType("connector", organisation_id));
	        	myProperties.put("flow",this.propertyDaoImpl.getPropertiesByType("flow", organisation_id));
	        	myProperties.put("issueflow",this.propertyDaoImpl.getPropertiesByType("issueflow", organisation_id));
	        	
	        	return myProperties;
	        	
	        }
			
		} catch (Exception err) {
			log.error("[getPropertiesByObjectType]",err);
		}
		return null;
	}
	
	/**
	 * 
	 * @param SID
	 * @param diagramObjectId
	 * @return
	 */
	public List<DiagramObjectProperty> getDiagramObjectPropertyByObject(String SID, Long diagramObjectId) {
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	
	        	return this.diagramObjectPropertyDaoImpl.getDiagramObjectPropertyByObject(diagramObjectId);
	        	
	        }
			
		} catch (Exception err) {
			log.error("[getDiagramObjectPropertyByObject]",err);
		}
		return null;
	}
	
	/**
	 * 
	 * @param SID
	 * @return
	 */
	public List<PropertyValidationType> getPropertyValidationTypes(String SID) {
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        
	        //TODO: Check if its really need to be logged in to load the validation type, client loads it before he logs in
	        //if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	
	        	return this.propertyValidationTypeDaoImpl.getPropertyValidationTypes();
	        	
	        //}
			
		} catch (Exception err) {
			log.error("[getPropertyValidationTypes]",err);
		}
		return null;
	}
}
