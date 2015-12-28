package org.i4change.app.http.javarpc;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.hibernate.beans.diagram.DiagramObjectProperty;
import org.i4change.app.hibernate.beans.diagram.Property;
import org.i4change.app.hibernate.beans.diagram.PropertyValidationType;
import org.i4change.app.remote.PropertyService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author sebastianwagner
 *
 */
public class PropertyServiceRPC extends BaseAdapterRPC {

	private static final Log log = LogFactory.getLog(PropertyServiceRPC.class);
	
	public Property getPropertyById(String SID, Long propertyId){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			PropertyService propertyService = (PropertyService) context.getBean("propertyservice.service");
		
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
			PropertyService propertyService = (PropertyService) context.getBean("propertyservice.service");
		
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
			PropertyService propertyService = (PropertyService) context.getBean("propertyservice.service");
		
			return propertyService.deleteProperty(SID, propertyId);
			
		} catch (Exception err) {
			log.error ("[deleteProperty]",err);
		}
		return null;
	}	

	public SearchResult getProperties(String SID, int start ,int max, String orderby, boolean asc){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			PropertyService propertyService = (PropertyService) context.getBean("propertyservice.service");
		
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
			PropertyService propertyService = (PropertyService) context.getBean("propertyservice.service");
		
			return propertyService.getPropertiesByOrganization(SID, organization_id, start, max, orderby, asc);
			
		} catch (Exception err) {
			log.error ("[getPropertiesByOrganization]",err);
		}
		return null;
	}	

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

	public List<PropertyValidationType> getPropertyValidationTypes(String SID){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			PropertyService propertyService = (PropertyService) context.getBean("propertyservice.service");
		
			return propertyService.getPropertyValidationTypes(SID);
			
		} catch (Exception err) {
			log.error ("[getPropertyValidationTypes]",err);
		}
		return null;
	}	
	
	
}
