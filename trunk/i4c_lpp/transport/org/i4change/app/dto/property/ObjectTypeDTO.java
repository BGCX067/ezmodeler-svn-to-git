package org.i4change.app.dto.property;

import org.i4change.app.dto.organization.OrganisationListDTO;

public class ObjectTypeDTO {

	private long objectTypeId;
	private String name;
	private Long labelid;
	private Boolean isPublic;
	private String deleted;
	private OrganisationListDTO organisation;
	private String comment;
	private Boolean isSidebarItem;
	private String objectTypeName;
	
	public long getObjectTypeId() {
		return objectTypeId;
	}
	public void setObjectTypeId(long objectTypeId) {
		this.objectTypeId = objectTypeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getLabelid() {
		return labelid;
	}
	public void setLabelid(Long labelid) {
		this.labelid = labelid;
	}
	public Boolean getIsPublic() {
		return isPublic;
	}
	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
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
	public Boolean getIsSidebarItem() {
		return isSidebarItem;
	}
	public void setIsSidebarItem(Boolean isSidebarItem) {
		this.isSidebarItem = isSidebarItem;
	}
	public String getObjectTypeName() {
		return objectTypeName;
	}
	public void setObjectTypeName(String objectTypeName) {
		this.objectTypeName = objectTypeName;
	}
	
}
