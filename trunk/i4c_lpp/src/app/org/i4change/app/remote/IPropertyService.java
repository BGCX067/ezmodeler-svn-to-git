package org.i4change.app.remote;

import java.util.List;
import java.util.Map;

import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.dto.diagram.DiagramObjectPropertyDTO;
import org.i4change.app.dto.property.ObjectTypeDTO;
import org.i4change.app.dto.property.PropertyCatSidebar;
import org.i4change.app.dto.property.PropertyCategoryDTO;
import org.i4change.app.dto.property.PropertyDTO;
import org.i4change.app.dto.property.PropertyListDTO;
import org.i4change.app.hibernate.beans.diagram.DiagramObjectProperty;
import org.i4change.app.hibernate.beans.diagram.Property;
import org.i4change.app.hibernate.beans.diagram.PropertyListItem;
import org.i4change.app.hibernate.beans.diagram.PropertyValidationType;

public interface IPropertyService {

	public abstract PropertyDTO getPropertyById(String SID, Long propertyId);

	public abstract Long saveOrUpdateProperty(String SID, Map values);

	public abstract Long deleteProperty(String SID, Long propertyId);

	public abstract SearchResult getProperties(String SID, int start, int max,
			String orderby, boolean asc);

	public abstract SearchResult getPropertyCategories(String SID, int start,
			int max, String orderby, boolean asc, String search);

	public abstract SearchResult getPropertiesByOrganization(String SID,
			Long organization_id, int start, int max, String orderby,
			boolean asc);

	public abstract List<PropertyListDTO> getPropertiesListAllByOrganiation(
			String SID, Long organization_id);

	public abstract SearchResult getPropertyCategoriesByOrganization(
			String SID, Long organization_id, int start, int max,
			String orderby, boolean asc, String search);

	/**
	public List<Property> getPropertiesByObjectType(String SID, Long organisation_id, String objectTypeName) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	return this.propertyDaoImpl.getPropertiesByType(objectTypeName, organisation_id);
	        	
	        }
			
		} catch (Exception err) {
			log.error("[getPropertiesByObjectType]",err);
		}
		return null;
	}
	 **/

	public abstract PropertyCategoryDTO getPropertyCategoryById(String SID,
			Long propertyCategoryId);

	public abstract Long saveOrUpdatePropertyCategory(String SID, Map values);

	public abstract Long deletePropertyCategory(String SID,
			Long propertyCategoryId);

	/**
	 * @deprecated Is not used anymore
	 * 
	 * @param SID
	 * @param organisation_id
	 * @return
	 */
	public abstract Map<String, List<Property>> getPropertiesByOrganization(
			String SID, Long organisation_id);

	/**
	 * @deprecated We do not need to load the Properties anymore when we laod any
	 * existing Object to the Stage
	 * 
	 * @param SID
	 * @param diagramObjectId
	 * @return
	 */
	public abstract List<DiagramObjectProperty> getDiagramObjectPropertyByObject(
			String SID, Long diagramObjectId);

	public abstract List<DiagramObjectPropertyDTO> getDiagramObjectPropertyByObject(
			String SID, Long organisation_id, Long diagramObjectId,
			String objectTypeAppName);

	/**
	 * 
	 * @param SID
	 * @return
	 */
	public abstract List<PropertyValidationType> getPropertyValidationTypes(
			String SID);

	public abstract PropertyCatSidebar getDiagramPropertyByObjectAndCat(
			String SID, Long organisation_id, Long diagramNo,
			String objectTypeAppName, Long categoryId, Long languagesId);

	public abstract PropertyCatSidebar getDiagramObjectPropertyByObjectAndCat(
			String SID, Long organisation_id, Long diagramObjectId,
			String objectTypeAppName, Long categoryId, Long languagesId);

	public abstract List<PropertyListItem> getPropertyListItemsByPropertyId(
			String SID, Long propertyId);

	public abstract List<ObjectTypeDTO> getDiagramObjectTypes(String SID);

	public abstract List<ObjectTypeDTO> getDiagramObjectTypesByOrg(String SID,
			Long organisation_id);

	public abstract List<ObjectTypeDTO> getDiagramObjectTypesPublicAndOrg(
			String SID, Long organisation_id);

	public abstract List<ObjectTypeDTO> getObjectTypesByType(String SID,
			String objectTypeName, Long organisation_id, Boolean isSidebarItem);

	public abstract ObjectTypeDTO getObjectTypeById(String SID,
			Long objectTypeId);

	public abstract Long saveOrUpdateObjectType(String SID, Map values);

}