package org.i4change.app.dto.property;

import java.util.List;

import org.i4change.app.dto.diagram.DiagramObjectPropertyDTO;

public class PropertyCatSidebar {
	
	private List<PropertyCategoryListDTO> propertyCategory;
	private List<DiagramObjectPropertyDTO> properties;
	
	public List<PropertyCategoryListDTO> getPropertyCategory() {
		return propertyCategory;
	}
	public void setPropertyCategory(List<PropertyCategoryListDTO> propertyCategory) {
		this.propertyCategory = propertyCategory;
	}
	public List<DiagramObjectPropertyDTO> getProperties() {
		return properties;
	}
	public void setProperties(List<DiagramObjectPropertyDTO> properties) {
		this.properties = properties;
	}
	
}
