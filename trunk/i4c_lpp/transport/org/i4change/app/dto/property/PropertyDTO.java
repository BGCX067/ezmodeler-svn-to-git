package org.i4change.app.dto.property;

import java.util.Date;

import org.i4change.app.dto.organization.OrganisationListDTO;

public class PropertyDTO {
	
	private long propertyId;
	//same as help-labelid, they start with 2000
	private long labelid;
	private long toolTip;
	private Boolean isPublic;
	private OrganisationListDTO organisation;
	private String objectTypeName;
	private String comment;
	private PropertyValidationTypeDTO propertyValidationType;
	private Long propertyValidationTypeId;
	private PropertyCategoryListDTO propertyCategory;
	public long getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(long propertyId) {
		this.propertyId = propertyId;
	}
	public long getLabelid() {
		return labelid;
	}
	public void setLabelid(long labelid) {
		this.labelid = labelid;
	}
	public long getToolTip() {
		return toolTip;
	}
	public void setToolTip(long toolTip) {
		this.toolTip = toolTip;
	}
	public Boolean getIsPublic() {
		return isPublic;
	}
	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}
	public OrganisationListDTO getOrganisation() {
		return organisation;
	}
	public void setOrganisation(OrganisationListDTO organisation) {
		this.organisation = organisation;
	}
	public String getObjectTypeName() {
		return objectTypeName;
	}
	public void setObjectTypeName(String objectTypeName) {
		this.objectTypeName = objectTypeName;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public PropertyValidationTypeDTO getPropertyValidationType() {
		return propertyValidationType;
	}
	public void setPropertyValidationType(
			PropertyValidationTypeDTO propertyValidationType) {
		this.propertyValidationType = propertyValidationType;
	}
	public Long getPropertyValidationTypeId() {
		return propertyValidationTypeId;
	}
	public void setPropertyValidationTypeId(Long propertyValidationTypeId) {
		this.propertyValidationTypeId = propertyValidationTypeId;
	}
	public PropertyCategoryListDTO getPropertyCategory() {
		return propertyCategory;
	}
	public void setPropertyCategory(PropertyCategoryListDTO propertyCategory) {
		this.propertyCategory = propertyCategory;
	}
	
}
