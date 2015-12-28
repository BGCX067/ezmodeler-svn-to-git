package org.i4change.app.dto.organization;

import java.util.List;
import java.util.Vector;

import org.i4change.app.dto.user.UserOrgDTO;

public class OrganisationDetailedDTO {

	private Long organisation_id;
	private String name;
	private Vector orgPatternsMap;
	private List<UserOrgDTO> user;
	
	public Long getOrganisation_id() {
		return organisation_id;
	}
	public void setOrganisation_id(Long organisation_id) {
		this.organisation_id = organisation_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Vector getOrgPatternsMap() {
		return orgPatternsMap;
	}
	public void setOrgPatternsMap(Vector orgPatternsMap) {
		this.orgPatternsMap = orgPatternsMap;
	}
	public List<UserOrgDTO> getUser() {
		return user;
	}
	public void setUser(List<UserOrgDTO> user) {
		this.user = user;
	}
	
}
