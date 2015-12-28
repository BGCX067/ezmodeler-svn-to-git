package org.i4change.app.dto.user;

public class OrganisationUserDTO {
	
	private Long organisation_users_id;
	//private OrganisationDTO organisationDTO;
	private Boolean isModerator;
	private OrganisationDTO organisation;
	
	public Long getOrganisation_users_id() {
		return organisation_users_id;
	}
	public void setOrganisation_users_id(Long organisation_users_id) {
		this.organisation_users_id = organisation_users_id;
	}
//	public OrganisationDTO getOrganisationDTO() {
//		return organisationDTO;
//	}
//	public void setOrganisationDTO(OrganisationDTO organisationDTO) {
//		this.organisationDTO = organisationDTO;
//	}
	public Boolean getIsModerator() {
		return isModerator;
	}
	public void setIsModerator(Boolean isModerator) {
		this.isModerator = isModerator;
	}
	public OrganisationDTO getOrganisation() {
		return organisation;
	}
	public void setOrganisation(OrganisationDTO organisation) {
		this.organisation = organisation;
	}

}
