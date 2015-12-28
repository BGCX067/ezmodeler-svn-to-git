package org.i4change.app.dto.project;

import java.util.Date;

import org.i4change.app.dto.organization.OrganisationListDTO;
import org.i4change.app.dto.user.UserOrgDTO;

public class ProjectDTO {

	private long projectId;
	private String name;
	private OrganisationListDTO organisation;
	private UserOrgDTO owner;
	private Date inserted;
	
	public long getProjectId() {
		return projectId;
	}
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public OrganisationListDTO getOrganisation() {
		return organisation;
	}
	public void setOrganisation(OrganisationListDTO organisation) {
		this.organisation = organisation;
	}
	public UserOrgDTO getOwner() {
		return owner;
	}
	public void setOwner(UserOrgDTO owner) {
		this.owner = owner;
	}
	public Date getInserted() {
		return inserted;
	}
	public void setInserted(Date inserted) {
		this.inserted = inserted;
	}
	
}
