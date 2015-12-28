package org.i4change.app.dto.property;

import org.i4change.app.dto.organization.OrganisationListDTO;

public class PropertyListDTO {
	
	private long propertyId;
	//same as help-labelid, they start with 2000
	private long labelid;
	private OrganisationListDTO organisation;
	private String objectTypeName;
	
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
	

}
