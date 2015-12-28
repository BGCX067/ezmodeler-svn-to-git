package org.i4change.app.dto.diagram;

import org.i4change.app.dto.organization.OrganisationListDTO;
import org.i4change.app.dto.user.UserMinimalDTO;

public class RoleDTO {

	private Long rolesId;
	private DiagramObjectSearchDTO roleObject;
	private OrganisationListDTO organisation;
	private UserMinimalDTO assignee;
	
	public Long getRolesId() {
		return rolesId;
	}
	public void setRolesId(Long rolesId) {
		this.rolesId = rolesId;
	}
	public DiagramObjectSearchDTO getRoleObject() {
		return roleObject;
	}
	public void setRoleObject(DiagramObjectSearchDTO roleObject) {
		this.roleObject = roleObject;
	}
	public OrganisationListDTO getOrganisation() {
		return organisation;
	}
	public void setOrganisation(OrganisationListDTO organisation) {
		this.organisation = organisation;
	}
	public UserMinimalDTO getAssignee() {
		return assignee;
	}
	public void setAssignee(UserMinimalDTO assignee) {
		this.assignee = assignee;
	}
	
}
