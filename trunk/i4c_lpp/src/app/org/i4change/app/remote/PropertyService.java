package org.i4change.app.remote;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.AuthLevelmanagement;
import org.i4change.app.data.basic.Fieldmanagment;
import org.i4change.app.data.basic.Sessionmanagement;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.data.diagram.DiagramObjectPropertyDaoImpl;
import org.i4change.app.data.diagram.ObjectTypeDaoImpl;
import org.i4change.app.data.diagram.PropertyCategoryDaoImpl;
import org.i4change.app.data.diagram.PropertyDaoImpl;
import org.i4change.app.data.diagram.PropertyListItemDaoImpl;
import org.i4change.app.data.diagram.PropertyValidationTypeDaoImpl;
import org.i4change.app.data.domain.daos.OrganisationDaoImpl;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.dto.diagram.DiagramObjectPropertyDTO;
import org.i4change.app.dto.organization.OrganisationListDTO;
import org.i4change.app.dto.property.ObjectTypeDTO;
import org.i4change.app.dto.property.PropertyCatSidebar;
import org.i4change.app.dto.property.PropertyCategoryDTO;
import org.i4change.app.dto.property.PropertyCategoryListDTO;
import org.i4change.app.dto.property.PropertyDTO;
import org.i4change.app.dto.property.PropertyListDTO;
import org.i4change.app.dto.property.PropertyValidationTypeDTO;
import org.i4change.app.hibernate.beans.diagram.DiagramObjectProperty;
import org.i4change.app.hibernate.beans.diagram.ObjectType;
import org.i4change.app.hibernate.beans.diagram.Property;
import org.i4change.app.hibernate.beans.diagram.PropertyCategory;
import org.i4change.app.hibernate.beans.diagram.PropertyListItem;
import org.i4change.app.hibernate.beans.diagram.PropertyValidationType;
import org.i4change.app.hibernate.beans.lang.Fieldlanguagesvalues;

import sun.awt.SunHints.Value;

public class PropertyService implements IPropertyService {
	
	private static final Log log = LogFactory.getLog(PropertyService.class);	

	private IApplication application;
	private PropertyDaoImpl propertyDaoImpl;
	private PropertyCategoryDaoImpl propertyCategoryDaoImpl;
	private OrganisationDaoImpl organisationDaoImpl;
	private DiagramObjectPropertyDaoImpl diagramObjectPropertyDaoImpl;
	private PropertyValidationTypeDaoImpl propertyValidationTypeDaoImpl;
	private PropertyListItemDaoImpl propertyListItemDaoImpl;
	private ObjectTypeDaoImpl objectTypeDaoImpl;
	private Sessionmanagement sessionmanagement;
	private UserDaoImpl userDaoImpl;

	public IApplication getApplication() {
		return application;
	}
	public void setApplication(IApplication application) {
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
	
	public PropertyCategoryDaoImpl getPropertyCategoryDaoImpl() {
		return propertyCategoryDaoImpl;
	}
	public void setPropertyCategoryDaoImpl(
			PropertyCategoryDaoImpl propertyCategoryDaoImpl) {
		this.propertyCategoryDaoImpl = propertyCategoryDaoImpl;
	}
	
	public PropertyListItemDaoImpl getPropertyListItemDaoImpl() {
		return propertyListItemDaoImpl;
	}
	public void setPropertyListItemDaoImpl(
			PropertyListItemDaoImpl propertyListItemDaoImpl) {
		this.propertyListItemDaoImpl = propertyListItemDaoImpl;
	}
	
	public ObjectTypeDaoImpl getObjectTypeDaoImpl() {
		return objectTypeDaoImpl;
	}
	public void setObjectTypeDaoImpl(ObjectTypeDaoImpl objectTypeDaoImpl) {
		this.objectTypeDaoImpl = objectTypeDaoImpl;
	}
	
	public Sessionmanagement getSessionmanagement() {
		return sessionmanagement;
	}
	public void setSessionmanagement(Sessionmanagement sessionmanagement) {
		this.sessionmanagement = sessionmanagement;
	}
	
	public UserDaoImpl getUserDaoImpl() {
		return userDaoImpl;
	}
	public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IPropertyService#getPropertyById(java.lang.String, java.lang.Long)
	 */
	public PropertyDTO getPropertyById(String SID, Long propertyId) {
		try {
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	Property prop = this.propertyDaoImpl.getPropertyById(propertyId);
	        	
	        	return this.morphPropertyToPropertyDTO(prop);
	        }
	        
		} catch (Exception err) {
			log.error("[getPropertyById]",err);
		}
		return null; 
	}
	
	private PropertyDTO morphPropertyToPropertyDTO(Property prop) {
		try {
			
			PropertyDTO propertyDTO = new PropertyDTO();
			
			if (prop == null) {
				return null;
			}
			
			propertyDTO.setComment(prop.getComment());
			propertyDTO.setIsPublic(prop.getIsPublic());
			propertyDTO.setLabelid(prop.getLabelid());
			propertyDTO.setObjectTypeName(prop.getObjectTypeName());
			
			if (prop.getOrganisation() != null) {
				propertyDTO.setOrganisation(new OrganisationListDTO());
				propertyDTO.getOrganisation().setName(prop.getOrganisation().getName());
				propertyDTO.getOrganisation().setOrganisation_id(prop.getOrganisation().getOrganisation_id());
			}
			
			if (prop.getPropertyCategory() != null) {
				propertyDTO.setPropertyCategory(new PropertyCategoryListDTO());
				propertyDTO.getPropertyCategory().setCategoryName(prop.getPropertyCategory().getCategoryName());
				propertyDTO.getPropertyCategory().setPropertyCategoryId(prop.getPropertyCategory().getPropertyCategoryId());
			}
			
			propertyDTO.setPropertyId(prop.getPropertyId());
			
			if (prop.getPropertyValidationType() != null) {
				propertyDTO.setPropertyValidationType(new PropertyValidationTypeDTO());
				propertyDTO.getPropertyValidationType().setHeight(prop.getPropertyValidationType().getHeight());
				propertyDTO.getPropertyValidationType().setInserted(prop.getPropertyValidationType().getInserted());
				propertyDTO.getPropertyValidationType().setInsertedBy(prop.getPropertyValidationType().getInsertedBy());
				propertyDTO.getPropertyValidationType().setLabelId(prop.getPropertyValidationType().getLabelId());
				propertyDTO.getPropertyValidationType().setPropertyValidationTypeId(
						prop.getPropertyValidationType().getPropertyValidationTypeId());
				propertyDTO.getPropertyValidationType().setValidationValue(prop.getPropertyValidationType().getValidationValue());
			}
			
			propertyDTO.setPropertyValidationTypeId(prop.getPropertyValidationTypeId());
			
			propertyDTO.setToolTip(prop.getToolTip());
			
			return propertyDTO;
			
		} catch (Exception err) {
			log.error("[morphPropertyToPropertyDTO]",err);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IPropertyService#saveOrUpdateProperty(java.lang.String, java.util.Map)
	 */
	public Long saveOrUpdateProperty(String SID, Map values) {
		try {
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	log.debug(values);
	        	
	        	Long propertyId = Long.valueOf(values.get("propertyId").toString()).longValue();
		        String propertyName = values.get("propertyName").toString();
		        String tooltip = values.get("tooltip").toString();
		        Long languages_id = Long.valueOf(values.get("languages_id").toString()).longValue();
		        String comment = values.get("comment").toString();
		        Boolean isPublic = Boolean.valueOf(values.get("isPublic").toString()).booleanValue();
		        Long organisation_id = Long.valueOf(values.get("organisation_id").toString()).longValue();
		        if (organisation_id == 0) {
		        	organisation_id = null;
		        }
		        
		        String objectTypeName = values.get("objectTypeName").toString();
		        Long propertyValidationTypeId = Long.valueOf(values.get("propertyValidationTypeId").toString()).longValue();
		        Long propertyCategoryId = Long.valueOf(values.get("propertyCategoryId").toString()).longValue();
		        
		        log.debug("propertyId: "+propertyId);
		        log.debug("propertyCategoryId: "+propertyCategoryId);
		        
		        Vector propertyListValues = (Vector) values.get("propertyListValues");
		        log.debug("propertyListValues: "+propertyListValues);
		        log.debug("propertyListValues: "+propertyListValues.getClass().getName());
		        
		        if (propertyId == null || propertyId == 0 || propertyId.equals(0)) {
		        	return this.propertyDaoImpl.addProperty(propertyName, tooltip, languages_id, comment, isPublic, 
		        			organisation_id, objectTypeName, users_id, propertyValidationTypeId, 
		        			propertyCategoryId, propertyListValues);
		        } else {
		        	return this.propertyDaoImpl.updateProperty(propertyId, propertyName, tooltip, languages_id, comment, isPublic, 
		        			organisation_id, objectTypeName, users_id, propertyValidationTypeId, 
		        			propertyCategoryId, propertyListValues);
		        }
	        }
	        
		} catch (Exception err) {
			log.error("[saveOrUpdateproperty]",err);
		}
		return new Long(-1); 
	}
	
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IPropertyService#deleteProperty(java.lang.String, java.lang.Long)
	 */
	public Long deleteProperty(String SID, Long propertyId) {
		try {
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	return this.propertyDaoImpl.deleteProperty(propertyId, users_id);
	        	
	        }
			
		} catch (Exception err) {
			log.error("[updateProperty]",err);
		}
		return new Long(-1);		
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IPropertyService#getProperties(java.lang.String, int, int, java.lang.String, boolean)
	 */
	public SearchResult getProperties(String SID, int start ,int max, String orderby, boolean asc) {
		try {
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.checkAdminLevel(user_level)) {
	        	
	        	return this.propertyDaoImpl.getProperties(start, max, orderby, asc);
	        	
	        }
			
		} catch (Exception err) {
			log.error("[getProperties]",err);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IPropertyService#getPropertyCategories(java.lang.String, int, int, java.lang.String, boolean, java.lang.String)
	 */
	public SearchResult getPropertyCategories(String SID, int start ,int max, String orderby, boolean asc, String search) {
		try {
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.checkAdminLevel(user_level)) {
	        	
	        	return this.propertyCategoryDaoImpl.getPropertyCategories(start, max, orderby, asc, search);
	        
	        }
			
		} catch (Exception err) {
			log.error("[getProperties]",err);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IPropertyService#getPropertiesByOrganization(java.lang.String, java.lang.Long, int, int, java.lang.String, boolean)
	 */
	public SearchResult getPropertiesByOrganization(String SID, Long organization_id,
			int start ,int max, String orderby, boolean asc) {
		try {
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	return this.propertyDaoImpl.getPropertiesByOrganization(organization_id, start, max, orderby, asc);
	        	
	        }
			
		} catch (Exception err) {
			log.error("[getPropertiesByOrganization]",err);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IPropertyService#getPropertiesListAllByOrganiation(java.lang.String, java.lang.Long)
	 */
	public List<PropertyListDTO> getPropertiesListAllByOrganiation(String SID, Long organization_id) {
		try {
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	List<Property> propList = this.propertyDaoImpl.getPropertiesListAllByOrganiation(organization_id);
	        	
	        	List<PropertyListDTO> propListDTO = new LinkedList<PropertyListDTO>();
	        	
	        	PropertyListDTO propertyListDTO = new PropertyListDTO();
	        	propertyListDTO.setLabelid(1426L);
	        	propertyListDTO.setObjectTypeName("all");
	        	propertyListDTO.setPropertyId(0);
	        	
	        	propListDTO.add(propertyListDTO);
	        	
				for (int i=0;i<propList.size();i++) {
					propListDTO.add(this.propertyDaoImpl.morphPropertyToPropertyListDTO(propList.get(i)));
				}
	        	
	        	return propListDTO;
	        	
	        }
			
		} catch (Exception err) {
			log.error("[getPropertiesByOrganization]",err);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IPropertyService#getPropertyCategoriesByOrganization(java.lang.String, java.lang.Long, int, int, java.lang.String, boolean, java.lang.String)
	 */
	public SearchResult getPropertyCategoriesByOrganization(String SID, Long organization_id,
			int start ,int max, String orderby, boolean asc, String search) {
		try {
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	return this.propertyCategoryDaoImpl.getPropertiesByOrganization(organization_id, start, max, orderby, asc, search);
	        	
	        }
			
		} catch (Exception err) {
			log.error("[getPropertiesByOrganization]",err);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IPropertyService#getPropertyCategoryById(java.lang.String, java.lang.Long)
	 */
	
	
	public PropertyCategoryDTO getPropertyCategoryById(String SID, Long propertyCategoryId) {
		try {
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	PropertyCategory propertyCategory =  this.propertyCategoryDaoImpl.getPropertyCategoryById(propertyCategoryId);
	        	
	        	return this.morphPropertyCategoryToPropertyCategoryDTO(propertyCategory);
	        }
	        
		} catch (Exception err) {
			log.error("[getPropertyCategoryById]",err);
		}
		return null; 
	}
	
	private PropertyCategoryDTO morphPropertyCategoryToPropertyCategoryDTO(PropertyCategory propertyCategory) {
		try {
			
			PropertyCategoryDTO propertyCategoryDTO = new PropertyCategoryDTO();
			
			propertyCategoryDTO.setCategoryName(propertyCategory.getCategoryName());
			propertyCategoryDTO.setComment(propertyCategory.getComment());
			propertyCategoryDTO.setIsPublic(propertyCategory.getIsPublic());
			propertyCategoryDTO.setObjectTypeName(propertyCategory.getObjectTypeName());
			if (propertyCategory.getOrganisation() != null) {
				propertyCategoryDTO.setOrganisation(new OrganisationListDTO());
				propertyCategoryDTO.getOrganisation().setName(propertyCategory.getOrganisation().getName());
				propertyCategoryDTO.getOrganisation().setOrganisation_id(propertyCategory.getOrganisation().getOrganisation_id());
			}
			
			propertyCategoryDTO.setPropertyCategoryId(propertyCategory.getPropertyCategoryId());
			
			return propertyCategoryDTO;
			
		} catch (Exception err) {
			log.error("[morphPropertyCategoryToPropertyCategoryDTO]",err);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IPropertyService#saveOrUpdatePropertyCategory(java.lang.String, java.util.Map)
	 */
	public Long saveOrUpdatePropertyCategory(String SID, Map values) {
		try {
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	log.debug(values);
	        	
	        	Long propertyCategoryId = Long.valueOf(values.get("propertyCategoryId").toString()).longValue();
		        String categoryName = values.get("categoryName").toString();
		        String comment = values.get("comment").toString();
		        String objectTypeName = values.get("objectTypeName").toString();
		        
		        Boolean isPublic = Boolean.valueOf(values.get("isPublic").toString()).booleanValue();
		        
		        Long organisation_id = null;
		        if (values.get("organisation_id").toString() != null && values.get("organisation_id").toString().length() != 0) {
		        	organisation_id = Long.valueOf(values.get("organisation_id").toString()).longValue();
		        }
		        if (organisation_id != null && organisation_id == 0) {
		        	organisation_id = null;
		        }
		        log.debug("propertyCategoryId: "+propertyCategoryId);
		        
		        if (propertyCategoryId == null || propertyCategoryId == 0 || propertyCategoryId.equals(0)) {
		        	return this.propertyCategoryDaoImpl.addPropertyCategory(categoryName, 
		        			users_id, objectTypeName, isPublic, organisation_id, comment);
		        } else {
		        	return this.propertyCategoryDaoImpl.updatePropertyCategory(propertyCategoryId, 
	        				categoryName, users_id, objectTypeName, isPublic, organisation_id, 
	        				comment);
		        }
	        }
	        
		} catch (Exception err) {
			log.error("[saveOrUpdateproperty]",err);
		}
		return new Long(-1); 
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IPropertyService#deletePropertyCategory(java.lang.String, java.lang.Long)
	 */
	public Long deletePropertyCategory(String SID, Long propertyCategoryId) {
		try {
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	return this.propertyCategoryDaoImpl.deletePropertyCategory(propertyCategoryId);
	        	
	        }
			
		} catch (Exception err) {
			log.error("[updateProperty]",err);
		}
		return new Long(-1);		
	}	
	
	
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IPropertyService#getPropertiesByOrganization(java.lang.String, java.lang.Long)
	 */
	public Map<String,List<Property>> getPropertiesByOrganization(String SID, Long organisation_id) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
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
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IPropertyService#getDiagramObjectPropertyByObject(java.lang.String, java.lang.Long)
	 */
	public List<DiagramObjectProperty> getDiagramObjectPropertyByObject(String SID, Long diagramObjectId) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	return this.diagramObjectPropertyDaoImpl.getDiagramObjectPropertyByObject(diagramObjectId);
	        	
	        }
			
		} catch (Exception err) {
			log.error("[getDiagramObjectPropertyByObject]",err);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IPropertyService#getDiagramObjectPropertyByObject(java.lang.String, java.lang.Long, java.lang.Long, java.lang.String)
	 */
	public List<DiagramObjectPropertyDTO> getDiagramObjectPropertyByObject(String SID,
			Long organisation_id, Long diagramObjectId, String objectTypeAppName) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	String objectTypeName = "";
	        	
	        	if (objectTypeAppName.equals("unit")) {
	        		objectTypeName = "unitFixed";
                } else if (objectTypeAppName.equals("departement")) {
                	objectTypeName = "departementFixed";
                } else if (objectTypeAppName.equals("company")) {
                	objectTypeName = "companyFixed";
                } else if (objectTypeAppName.equals("process")) {
                	objectTypeName = "processtree";
                } else {
                	objectTypeName = objectTypeAppName;
                }
	        	
	        	List<Property> propertyList = this.propertyDaoImpl.getPropertiesByType(objectTypeName, organisation_id);
	        	
	        	List<DiagramObjectProperty> propertyValues = this.diagramObjectPropertyDaoImpl.getDiagramObjectPropertyByDiagramObjectId(diagramObjectId);
	        	
	        	LinkedList<DiagramObjectPropertyDTO> returnProperties = new LinkedList<DiagramObjectPropertyDTO>();
	        	
	        	for (Property property : propertyList) {
	        		DiagramObjectPropertyDTO diagramObjectPropertyDTO = new DiagramObjectPropertyDTO();
	        		
	        		diagramObjectPropertyDTO.setLabelid(property.getLabelid());
	        		diagramObjectPropertyDTO.setPropertyId(property.getPropertyId());
	        		diagramObjectPropertyDTO.setToolTip(property.getToolTip());
	        		diagramObjectPropertyDTO.setValidationValue(property.getPropertyValidationType().getValidationValue());
	        		diagramObjectPropertyDTO.setTextBoxHeight(property.getPropertyValidationType().getHeight());
	        		
	        		for (DiagramObjectProperty diagramObjectProperty : propertyValues) {
	        			if (diagramObjectProperty.getPropertyId().equals(property.getPropertyId())) {
	        				
	        				diagramObjectPropertyDTO.setUpdated(diagramObjectProperty.getUpdated());
	    	        		diagramObjectPropertyDTO.setUpdatedBy(diagramObjectProperty.getUpdatedBy());
	    	        		
	        				diagramObjectPropertyDTO.setDiagramObjectPropertyId(diagramObjectProperty.getDiagramObjectPropertyId());
	        				diagramObjectPropertyDTO.setValue(diagramObjectProperty.getValue());
	        				
	        				break;
	        			}
	        		
	        		}
	        		
	        		returnProperties.add(diagramObjectPropertyDTO);
	        	}
	        	
	        	return returnProperties;
	        }
			
		} catch (Exception err) {
			log.error("[getDiagramObjectPropertyByObject]",err);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IPropertyService#getPropertyValidationTypes(java.lang.String)
	 */
	public List<PropertyValidationType> getPropertyValidationTypes(String SID) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        
	        //TODO: Check if its really need to be logged in to load the validation type, client loads it before he logs in
	        //if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	return this.propertyValidationTypeDaoImpl.getPropertyValidationTypes();
	        	
	        //}
			
		} catch (Exception err) {
			log.error("[getPropertyValidationTypes]",err);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IPropertyService#getDiagramPropertyByObjectAndCat(java.lang.String, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Long)
	 */
	public PropertyCatSidebar getDiagramPropertyByObjectAndCat(
			String SID, Long organisation_id, Long diagramNo,
			String objectTypeAppName, Long categoryId, Long languagesId) {
		try {

			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	//Some of the Objects have two different names but actually its the same object
	        	String objectTypeName = "";
	        	
	        	if (objectTypeAppName.equals("unit")) {
	        		objectTypeName = "unitFixed";
                } else if (objectTypeAppName.equals("departement")) {
                	objectTypeName = "departementFixed";
                } else if (objectTypeAppName.equals("company")) {
                	objectTypeName = "companyFixed";
                } else if (objectTypeAppName.equals("process")) {
                	objectTypeName = "processtree";
                } else {
                	objectTypeName = objectTypeAppName;
                }
	        	 
	        	
	        	PropertyCatSidebar propertyCatSidebar = new PropertyCatSidebar();
				
	        	//Get the Categories
				List<PropertyCategoryListDTO> propertyCategoryList = this.propertyCategoryDaoImpl.getPropertyCategoriesByStrictOrganizationList(organisation_id,objectTypeName);
				
				log.debug("propertyCatSidebar "+propertyCategoryList.size());
				
				propertyCatSidebar.setPropertyCategory(propertyCategoryList);
				
	        	//Get the Properties
	        	List<Property> propertyList = null;
	        	
	        	if (categoryId == null || categoryId == 0){
					
	        		propertyList = this.propertyDaoImpl.getPropertiesByStrictTypeAndNoCat(objectTypeName, organisation_id);
					
				} else {
					
					propertyList = this.propertyDaoImpl.getPropertiesByStrictTypeAndCat(objectTypeName, organisation_id, categoryId);
				
				}

	        	//Get the Values
	        	List<DiagramObjectProperty> propertyValues = this.diagramObjectPropertyDaoImpl.getDiagramObjectPropertyByDiagramNo(diagramNo);
	        	
	        	//Morph the Properties together with the Values into a DTO-List
	        	LinkedList<DiagramObjectPropertyDTO> returnProperties = new LinkedList<DiagramObjectPropertyDTO>();
	        	
	        	for (Property property : propertyList) {
	        		DiagramObjectPropertyDTO diagramObjectPropertyDTO = new DiagramObjectPropertyDTO();
	        		
	        		diagramObjectPropertyDTO.setLabelid(property.getLabelid());
	        		diagramObjectPropertyDTO.setPropertyId(property.getPropertyId());
	        		diagramObjectPropertyDTO.setToolTip(property.getToolTip());
	        		diagramObjectPropertyDTO.setValidationValue(property.getPropertyValidationType().getValidationValue());
	        		diagramObjectPropertyDTO.setTextBoxHeight(property.getPropertyValidationType().getHeight());
	        		
	        		diagramObjectPropertyDTO.setValue("");
	        		
	        		for (DiagramObjectProperty diagramObjectProperty : propertyValues) {
	        			if (diagramObjectProperty.getPropertyId().equals(property.getPropertyId())) {
	        				
	        				diagramObjectPropertyDTO.setUpdated(diagramObjectProperty.getUpdated());
	    	        		diagramObjectPropertyDTO.setUpdatedBy(diagramObjectProperty.getUpdatedBy());
	    	        		
	        				diagramObjectPropertyDTO.setDiagramObjectPropertyId(diagramObjectProperty.getDiagramObjectPropertyId());
	        				diagramObjectPropertyDTO.setValue(diagramObjectProperty.getValue());
	        				
	        				break;
	        			}
	        		
	        		}
	        		
	        		returnProperties.add(diagramObjectPropertyDTO);
	        	}
	        	
	        	propertyCatSidebar.setProperties(returnProperties);
	        	
	        	
	        	return propertyCatSidebar;
	        }
			
		} catch (Exception err) {
			log.error("[getDiagramObjectPropertyByObjectAndCat]",err);
		}
		return null;
	}
	
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IPropertyService#getDiagramObjectPropertyByObjectAndCat(java.lang.String, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Long)
	 */
	public PropertyCatSidebar getDiagramObjectPropertyByObjectAndCat(
			String SID, Long organisation_id, Long diagramObjectId,
			String objectTypeAppName, Long categoryId, Long languagesId) {
		try {
			
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	//Some of the Objects have two different names but actually its the same object
	        	String objectTypeName = "";
	        	
	        	if (objectTypeAppName.equals("unit")) {
	        		objectTypeName = "unitFixed";
                } else if (objectTypeAppName.equals("departement")) {
                	objectTypeName = "departementFixed";
                } else if (objectTypeAppName.equals("company")) {
                	objectTypeName = "companyFixed";
                } else if (objectTypeAppName.equals("process")) {
                	objectTypeName = "processtree";
                } else {
                	objectTypeName = objectTypeAppName;
                }
	        	
	        	
	        	PropertyCatSidebar propertyCatSidebar = new PropertyCatSidebar();
				
	        	//Get the Categories
				List<PropertyCategoryListDTO> propertyCategoryList = this.propertyCategoryDaoImpl.getPropertyCategoriesByOrganizationList(organisation_id,objectTypeName);
				
				propertyCatSidebar.setPropertyCategory(propertyCategoryList);
				
	        	//Get the Properties
	        	List<Property> propertyList = null;
	        	
	        	if (categoryId == null || categoryId == 0){
					
	        		propertyList = this.propertyDaoImpl.getPropertiesByTypeAndNoCat(objectTypeName, organisation_id);
					
				} else {
					
					propertyList = this.propertyDaoImpl.getPropertiesByTypeAndCat(objectTypeName, organisation_id, categoryId);
				
				}

	        	//Get the Values
	        	List<DiagramObjectProperty> propertyValues = this.diagramObjectPropertyDaoImpl.getDiagramObjectPropertyByDiagramObjectId(diagramObjectId);
	        	
	        	//Morph the Properties together with the Values into a DTO-List
	        	LinkedList<DiagramObjectPropertyDTO> returnProperties = new LinkedList<DiagramObjectPropertyDTO>();
	        	
	        	for (Property property : propertyList) {
	        		DiagramObjectPropertyDTO diagramObjectPropertyDTO = new DiagramObjectPropertyDTO();
	        		
	        		diagramObjectPropertyDTO.setLabelid(property.getLabelid());
	        		diagramObjectPropertyDTO.setPropertyId(property.getPropertyId());
	        		diagramObjectPropertyDTO.setToolTip(property.getToolTip());
	        		diagramObjectPropertyDTO.setValidationValue(property.getPropertyValidationType().getValidationValue());
	        		diagramObjectPropertyDTO.setTextBoxHeight(property.getPropertyValidationType().getHeight());
	        		
	        		diagramObjectPropertyDTO.setValue("");
	        		
	        		for (DiagramObjectProperty diagramObjectProperty : propertyValues) {
	        			if (diagramObjectProperty.getPropertyId().equals(property.getPropertyId())) {
	        				
	        				diagramObjectPropertyDTO.setUpdated(diagramObjectProperty.getUpdated());
	    	        		diagramObjectPropertyDTO.setUpdatedBy(diagramObjectProperty.getUpdatedBy());
	    	        		
	        				diagramObjectPropertyDTO.setDiagramObjectPropertyId(diagramObjectProperty.getDiagramObjectPropertyId());
	        				diagramObjectPropertyDTO.setValue(diagramObjectProperty.getValue());
	        				
	        				break;
	        			}
	        		
	        		}
	        		
	        		returnProperties.add(diagramObjectPropertyDTO);
	        	}
	        	
	        	propertyCatSidebar.setProperties(returnProperties);
	        	
	        	
	        	return propertyCatSidebar;
	        }
			
		} catch (Exception err) {
			log.error("[getDiagramObjectPropertyByObjectAndCat]",err);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IPropertyService#getPropertyListItemsByPropertyId(java.lang.String, java.lang.Long)
	 */
	public List<PropertyListItem> getPropertyListItemsByPropertyId(String SID, Long propertyId) {
		try {
			
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	log.debug("getPropertyListItemsByPropertyId: "+propertyId);
	        	
	        	return this.propertyListItemDaoImpl.getPropertyListItemsByPropertyId(propertyId);
	        	
	        }
			
		} catch (Exception err) {
			log.error("[getPropertyListItemsByPropertyId]",err);
		}
		return null;
	}
	
	/*
	 * ########################################################
	 * 
	 * Handle Object-Types
	 * 
	 */

	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IPropertyService#getDiagramObjectTypes(java.lang.String)
	 */
	public List<ObjectTypeDTO> getDiagramObjectTypes(String SID) {
		try {
			
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	//log.debug("getPropertyListItemsByPropertyId: "+propertyId);
	        	
	        	List<ObjectType> objectTypes = this.objectTypeDaoImpl.getDiagramObjectTypes();
	        	
	        	List<ObjectTypeDTO> objectTypesDTO = new LinkedList<ObjectTypeDTO>();
	        	
	        	for (int i=0;i<objectTypes.size();i++) {
	        		objectTypesDTO.add(this.morphObjectTypeToObjectTypeDTO(objectTypes.get(i)));
	        	}
	        	
	        	return objectTypesDTO;
	        }
			
		} catch (Exception err) {
			log.error("[getDiagramObjectTypes]",err);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IPropertyService#getDiagramObjectTypesByOrg(java.lang.String, java.lang.Long)
	 */
	public List<ObjectTypeDTO> getDiagramObjectTypesByOrg(String SID, Long organisation_id) {
		try {
			
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	//log.debug("getPropertyListItemsByPropertyId: "+propertyId);
	        	
	        	List<ObjectType> objectTypes = this.objectTypeDaoImpl.getDiagramObjectTypesByOrg(organisation_id);
	        	
	        	List<ObjectTypeDTO> objectTypesDTO = new LinkedList<ObjectTypeDTO>();
	        	
	        	for (int i=0;i<objectTypes.size();i++) {
	        		objectTypesDTO.add(this.morphObjectTypeToObjectTypeDTO(objectTypes.get(i)));
	        	}
	        	
	        	return objectTypesDTO;
	        }
			
		} catch (Exception err) {
			log.error("[getDiagramObjectTypes]",err);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IPropertyService#getDiagramObjectTypesPublicAndOrg(java.lang.String, java.lang.Long)
	 */
	public List<ObjectTypeDTO> getDiagramObjectTypesPublicAndOrg(String SID, Long organisation_id)  {
		try {
			
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	//log.debug("getPropertyListItemsByPropertyId: "+propertyId);
	        	
	        	List<ObjectType> objectTypes = this.objectTypeDaoImpl.getDiagramObjectTypesPublicAndOrg(organisation_id);
	        	
	        	List<ObjectTypeDTO> objectTypesDTO = new LinkedList<ObjectTypeDTO>();
	        	
	        	for (int i=0;i<objectTypes.size();i++) {
	        		objectTypesDTO.add(this.morphObjectTypeToObjectTypeDTO(objectTypes.get(i)));
	        	}
	        	
	        	return objectTypesDTO;
	        }
			
		} catch (Exception err) {
			log.error("[getDiagramObjectTypes]",err);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IPropertyService#getObjectTypesByType(java.lang.String, java.lang.String, java.lang.Long, java.lang.Boolean)
	 */
	public List<ObjectTypeDTO> getObjectTypesByType(String SID, String objectTypeName, 
			Long organisation_id, Boolean isSidebarItem) {
		try {
			
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	//log.debug("getPropertyListItemsByPropertyId: "+propertyId);
	        	
	        	List<ObjectType> objectTypes = this.objectTypeDaoImpl.getObjectTypesByType(objectTypeName, 
	        			organisation_id, isSidebarItem);
	        	
	        	List<ObjectTypeDTO> objectTypesDTO = new LinkedList<ObjectTypeDTO>();
	        	
	        	for (int i=0;i<objectTypes.size();i++) {
	        		objectTypesDTO.add(this.morphObjectTypeToObjectTypeDTO(objectTypes.get(i)));
	        	}
	        	
	        	return objectTypesDTO;
	        }
			
		} catch (Exception err) {
			log.error("[getDiagramObjectTypes]",err);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IPropertyService#getObjectTypeById(java.lang.String, java.lang.Long)
	 */
	public ObjectTypeDTO getObjectTypeById(String SID, Long objectTypeId) {
		try {
			
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	return this.morphObjectTypeToObjectTypeDTO(this.objectTypeDaoImpl.getObjectTypeById(objectTypeId));
	        }
			
			
		} catch (Exception err) {
			log.error("[getObjectTypeById]",err);
		}
		return null;
	}
	
	private ObjectTypeDTO morphObjectTypeToObjectTypeDTO(ObjectType objectType) {
		try {
			
			if (objectType == null) {
				return null;
			}
			
			ObjectTypeDTO objectTypeDTO = new ObjectTypeDTO();
			
			objectTypeDTO.setComment(objectType.getComment());
			objectTypeDTO.setDeleted(objectType.getDeleted());
			objectTypeDTO.setIsPublic(objectType.getIsPublic());
			objectTypeDTO.setIsSidebarItem(objectType.getIsSidebarItem());
			objectTypeDTO.setLabelid(objectType.getLabelid());
			objectTypeDTO.setName(objectType.getName());
			objectTypeDTO.setObjectTypeId(objectType.getObjectTypeId());
			objectTypeDTO.setObjectTypeName(objectType.getObjectTypeName());
			
			if (objectType.getOrganisation() != null) {
				objectTypeDTO.setOrganisation(new OrganisationListDTO());
				objectTypeDTO.getOrganisation().setName(objectType.getOrganisation().getName());
				objectTypeDTO.getOrganisation().setOrganisation_id(objectType.getOrganisation().getOrganisation_id());
			}
			
			return objectTypeDTO;
		} catch (Exception err) {
			log.error("[morphObjectTypeToObjectTypeDTO]",err);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IPropertyService#saveOrUpdateObjectType(java.lang.String, java.util.Map)
	 */
	public Long saveOrUpdateObjectType(String SID, Map values) {
		try {
			
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	log.debug(values);
	        	
	        	Long objectTypeId = Long.valueOf(values.get("objectTypeId").toString()).longValue();
		        String oName = StringUtils.deleteWhitespace(values.get("oName").toString());
		        Long languages_id = Long.valueOf(values.get("languages_id").toString()).longValue();
		        String comment = values.get("comment").toString();
		        Boolean isPublic = Boolean.valueOf(values.get("isPublic").toString()).booleanValue();
		        Boolean isSidebarItem = Boolean.valueOf(values.get("isSidebarItem").toString()).booleanValue();
		        Long organisation_id = Long.valueOf(values.get("organisation_id").toString()).longValue();
		        if (organisation_id == 0) {
		        	organisation_id = null;
		        }
		        
		        String objectTypeName = values.get("objectTypeName").toString();
		        String labelName = values.get("labelName").toString();
		        
	        	return this.objectTypeDaoImpl.saveOrUpdateObjectTypeByValue(objectTypeId, 
	        			oName, isPublic, organisation_id, comment, 
	        			isSidebarItem, objectTypeName, labelName, languages_id, users_id);
		        	
	        }
			
			
		} catch (Exception err) {
			log.error("[saveOrUpdateObjectType]",err);
		}
		return null;
	}
	
}
