package org.i4change.app.http.javarpc;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.dto.diagram.DiagramObjectPropertyDTO;
import org.i4change.app.dto.property.ObjectTypeDTO;
import org.i4change.app.dto.property.PropertyCatSidebar;
import org.i4change.app.dto.property.PropertyCategoryDTO;
import org.i4change.app.dto.property.PropertyDTO;
import org.i4change.app.dto.property.PropertyListDTO;
import org.i4change.app.hibernate.beans.diagram.DiagramObjectProperty;
import org.i4change.app.hibernate.beans.diagram.Property;
import org.i4change.app.hibernate.beans.diagram.PropertyCategory;
import org.i4change.app.hibernate.beans.diagram.PropertyListItem;
import org.i4change.app.hibernate.beans.diagram.PropertyValidationType;
import org.i4change.app.http.beans.Comperators;
import org.i4change.app.remote.IPropertyService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author sebastianwagner
 *
 */
public class PropertyServiceRPC extends BaseAdapterRPC {

	private static final Log log = LogFactory.getLog(PropertyServiceRPC.class);
	
	public PropertyDTO getPropertyById(String SID, Long propertyId){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IPropertyService propertyService = (IPropertyService) context.getBean("propertyservice.service");
		
			return propertyService.getPropertyById(SID, propertyId);
			
		} catch (Exception err) {
			log.error ("[getPropertyById]",err);
		}
		return null;
	}

	public Long saveOrUpdateProperty(String SID, Map values){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IPropertyService propertyService = (IPropertyService) context.getBean("propertyservice.service");
		
			return propertyService.saveOrUpdateProperty(SID, values);
			
		} catch (Exception err) {
			log.error ("[saveOrUpdateProperty]",err);
		}
		return null;
	}

	public Long deleteProperty(String SID, Long propertyId){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IPropertyService propertyService = (IPropertyService) context.getBean("propertyservice.service");
		
			return propertyService.deleteProperty(SID, propertyId);
			
		} catch (Exception err) {
			log.error ("[deleteProperty]",err);
		}
		return null;
	}	
	
	public Long deletePropertyCategory(String SID, Long propertyCategoryId) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IPropertyService propertyService = (IPropertyService) context.getBean("propertyservice.service");
		
			return propertyService.deletePropertyCategory(SID, propertyCategoryId);
			
		} catch (Exception err) {
			log.error ("[deletePropertyCategory]",err);
		}
		return null;
	}
	
	public Long saveOrUpdatePropertyCategory(String SID, Map values) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IPropertyService propertyService = (IPropertyService) context.getBean("propertyservice.service");
		
			return propertyService.saveOrUpdatePropertyCategory(SID, values);
			
		} catch (Exception err) {
			log.error ("[saveOrUpdatePropertyCategory]",err);
		}
		return null;
	}
	
	public SearchResult getPropertyCategories(String SID, int start ,int max, String orderby, 
								boolean asc, String search) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IPropertyService propertyService = (IPropertyService) context.getBean("propertyservice.service");
		
			return propertyService.getPropertyCategories(SID, start, max, orderby, asc, search);
			
		} catch (Exception err) {
			log.error ("[getPropertyCategories]",err);
		}
		return null;
	}
	
	public SearchResult getPropertyCategoriesByOrganization(String SID, Long organization_id,
			int start ,int max, String orderby, boolean asc, String search) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IPropertyService propertyService = (IPropertyService) context.getBean("propertyservice.service");
		
			return propertyService.getPropertyCategoriesByOrganization(SID, 
					organization_id, start, max, orderby, asc, search);
			
		} catch (Exception err) {
			log.error ("[getPropertyCategoriesByOrganization]",err);
		}
		return null;
	}
	
	public PropertyCategoryDTO getPropertyCategoryById(String SID, Long propertyCategoryId) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IPropertyService propertyService = (IPropertyService) context.getBean("propertyservice.service");
		
			return propertyService.getPropertyCategoryById(SID, propertyCategoryId);
			
		} catch (Exception err) {
			log.error ("[getPropertyCategoryById]",err);
		}
		return null;
	}

	public SearchResult getProperties(String SID, int start ,int max, String orderby, boolean asc){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IPropertyService propertyService = (IPropertyService) context.getBean("propertyservice.service");
		
			return propertyService.getProperties(SID, start, max, orderby, asc);
			
		} catch (Exception err) {
			log.error ("[getProperties]",err);
		}
		return null;
	}	

	public SearchResult getPropertiesByOrganization(String SID, Long organization_id,
			int start ,int max, String orderby, boolean asc){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IPropertyService propertyService = (IPropertyService) context.getBean("propertyservice.service");
		
			return propertyService.getPropertiesByOrganization(SID, organization_id, start, max, orderby, asc);
			
		} catch (Exception err) {
			log.error ("[getPropertiesByOrganization]",err);
		}
		return null;
	}	
	
	public List<PropertyListDTO> getPropertiesListAllByOrganiation(String SID, Long organization_id) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IPropertyService propertyService = (IPropertyService) context.getBean("propertyservice.service");
		
			return propertyService.getPropertiesListAllByOrganiation(SID, organization_id);
			
		} catch (Exception err) {
			log.error ("[getPropertiesListAllByOrganiation]",err);
		}
		return null;
	}
	
	public List<Comperators> getComperators() {
		try {
			
			List<Comperators> rList = new LinkedList<Comperators>();
			
			Comperators c = new Comperators();
			c.setText("Like");
			c.setValue("like");
			c.setLabelid(1430L);
			rList.add(c);
			
			Comperators c1 = new Comperators();
			c1.setText("&lt;");
			c1.setValue("smaller");
			c1.setLabelid(1433L);
			rList.add(c1);
			
			Comperators c2 = new Comperators();
			c2.setText("&lt;=");
			c2.setValue("smallerEqual");
			c2.setLabelid(1434L);
			rList.add(c2);

			Comperators c3 = new Comperators();
			c3.setText("&gt;");
			c3.setValue("bigger");
			c3.setLabelid(1431L);
			rList.add(c3);
			
			Comperators c4 = new Comperators();
			c4.setText("&gt;=");
			c4.setValue("biggerEqual");
			c4.setLabelid(1432L);
			rList.add(c4);
			
			Comperators c5 = new Comperators();
			c5.setText("=");
			c5.setValue("=");
			c5.setLabelid(1438L);
			rList.add(c5);
			
			Comperators c6 = new Comperators();
			c6.setText("!=");
			c6.setValue("!=");
			c6.setLabelid(1435L);
			rList.add(c6);
			
			Comperators c7 = new Comperators();
			c7.setText("Not Like");
			c7.setValue("NotLike");
			c7.setLabelid(1436L);
			rList.add(c7);
			
			return rList;
			
		} catch (Exception err) {
			log.error ("[getComperators]",err);
		}
		return null;
	}

	/*
	public List<Property> getPropertiesByObjectType(String SID, Long organisation_id, String objectTypeName){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			PropertyService propertyService = (PropertyService) context.getBean("propertyservice.service");
		
			return propertyService.getPropertiesByObjectType(SID, organisation_id, objectTypeName);
			
		} catch (Exception err) {
			log.error ("[getPropertiesByObjectType]",err);
		}
		return null;
	}	
	*/	

	/*
	public Map<String,List<Property>> getPropertiesByOrganization(String SID, Long organisation_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			PropertyService propertyService = (PropertyService) context.getBean("propertyservice.service");
		
			return propertyService.getPropertiesByOrganization(SID, organisation_id);
			
		} catch (Exception err) {
			log.error ("[getPropertiesByOrganization]",err);
		}
		return null;
	}	
	*/

	/*
	public List<DiagramObjectProperty> getDiagramObjectPropertyByObject(String SID, Long diagramObjectId){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			PropertyService propertyService = (PropertyService) context.getBean("propertyservice.service");
		
			return propertyService.getDiagramObjectPropertyByObject(SID, diagramObjectId);
			
		} catch (Exception err) {
			log.error ("[getDiagramObjectPropertyByObject]",err);
		}
		return null;
	}		
	*/
	
	public List<PropertyValidationType> getPropertyValidationTypes(String SID){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IPropertyService propertyService = (IPropertyService) context.getBean("propertyservice.service");
		
			return propertyService.getPropertyValidationTypes(SID);
			
		} catch (Exception err) {
			log.error ("[getPropertyValidationTypes]",err);
		}
		return null;
	}	
	
	/*
	public List<DiagramObjectPropertyDTO> getDiagramObjectPropertyByObject(String SID,
			Long organisation_id, Long diagramObjectId, String objectTypeAppName){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			PropertyService propertyService = (PropertyService) context.getBean("propertyservice.service");
		
			return propertyService.getDiagramObjectPropertyByObject(SID, 
						organisation_id, diagramObjectId, objectTypeAppName);
			
		} catch (Exception err) {
			log.error ("[getPropertyValidationTypes]",err);
		}
		return null;
	}
	*/	
	
	public PropertyCatSidebar getDiagramObjectPropertyByObjectAndCat(String SID,
			Long organisation_id, Long diagramObjectId, String objectTypeAppName, 
			Long categoryId, Long languagesId){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IPropertyService propertyService = (IPropertyService) context.getBean("propertyservice.service");
		
			return propertyService.getDiagramObjectPropertyByObjectAndCat(SID, 
						organisation_id, diagramObjectId, objectTypeAppName, 
						categoryId, languagesId);
			
		} catch (Exception err) {
			log.error ("[getPropertyValidationTypes]",err);
		}
		return null;
	}
	
	public PropertyCatSidebar getDiagramPropertyByObjectAndCat(
			String SID, Long organisation_id, Long diagramNo,
			String objectTypeAppName, Long categoryId, Long languagesId) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IPropertyService propertyService = (IPropertyService) context.getBean("propertyservice.service");
		
			return propertyService.getDiagramPropertyByObjectAndCat(SID, 
							organisation_id, diagramNo, objectTypeAppName, 
							categoryId, languagesId);
			
		} catch (Exception err) {
			log.error ("[getDiagramPropertyByObjectAndCat]",err);
		}
		return null;
	}
	
	public List<PropertyListItem> getPropertyListItemsByPropertyId(String SID, Long propertyId) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IPropertyService propertyService = (IPropertyService) context.getBean("propertyservice.service");
		
			return propertyService.getPropertyListItemsByPropertyId(SID, propertyId);
			
		} catch (Exception err) {
			log.error ("[getPropertyListItemsByPropertyId]",err);
		}
		return null;
	}
	
	/*
	 * ########################################################
	 * 
	 * Handle Object-Types
	 * 
	 */
	
	public List<ObjectTypeDTO> getDiagramObjectTypes(String SID) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IPropertyService propertyService = (IPropertyService) context.getBean("propertyservice.service");
		
			return propertyService.getDiagramObjectTypes(SID);
			
		} catch (Exception err) {
			log.error ("[getDiagramObjectTypes]",err);
		}
		return null;
	}
	
	public List<ObjectTypeDTO> getDiagramObjectTypesByOrg(String SID, Long organisation_id) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IPropertyService propertyService = (IPropertyService) context.getBean("propertyservice.service");
		
			return propertyService.getDiagramObjectTypesByOrg(SID, organisation_id);
			
		} catch (Exception err) {
			log.error ("[getDiagramObjectTypesByOrg]",err);
		}
		return null;
	}
	
	public List<ObjectTypeDTO> getDiagramObjectTypesPublicAndOrg(String SID, Long organisation_id)  {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IPropertyService propertyService = (IPropertyService) context.getBean("propertyservice.service");
		
			return propertyService.getDiagramObjectTypesPublicAndOrg(SID, organisation_id);
			
		} catch (Exception err) {
			log.error ("[getDiagramObjectTypesPublicAndOrg]",err);
		}
		return null;
	}
	
	public List<ObjectTypeDTO> getObjectTypesByType(String SID, String objectTypeName, 
			Long organisation_id, Boolean isSidebarItem) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IPropertyService propertyService = (IPropertyService) context.getBean("propertyservice.service");
		
			return propertyService.getObjectTypesByType(SID, objectTypeName, organisation_id, isSidebarItem);
			
		} catch (Exception err) {
			log.error ("[getObjectTypesByType]",err);
		}
		return null;
	}
	
	public ObjectTypeDTO getObjectTypeById(String SID, Long objectTypeId) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IPropertyService propertyService = (IPropertyService) context.getBean("propertyservice.service");
		
			return propertyService.getObjectTypeById(SID, objectTypeId);
			
		} catch (Exception err) {
			log.error ("[getObjectTypesByType]",err);
		}
		return null;
	}
	
	public Long saveOrUpdateObjectType(String SID, Map values){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IPropertyService propertyService = (IPropertyService) context.getBean("propertyservice.service");
		
			return propertyService.saveOrUpdateObjectType(SID, values);
			
		} catch (Exception err) {
			log.error ("[saveOrUpdateObjectType]",err);
		}
		return null;
	}
	
}
