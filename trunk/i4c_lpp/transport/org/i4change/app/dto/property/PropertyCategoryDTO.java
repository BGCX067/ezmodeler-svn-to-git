package org.i4change.app.dto.property;

import org.i4change.app.dto.organization.OrganisationListDTO;

public class PropertyCategoryDTO {

	private long propertyCategoryId;
	private String categoryName;
	private Boolean isPublic;
	private OrganisationListDTO organisation;
	private String comment;
	private String objectTypeName;
	public long getPropertyCategoryId() {
		return propertyCategoryId;
	}
	public void setPropertyCategoryId(long propertyCategoryId) {
		this.propertyCategoryId = propertyCategoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getObjectTypeName() {
		return objectTypeName;
	}
	public void setObjectTypeName(String objectTypeName) {
		this.objectTypeName = objectTypeName;
	}
	
}
