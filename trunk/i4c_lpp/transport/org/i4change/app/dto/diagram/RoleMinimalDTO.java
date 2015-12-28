package org.i4change.app.dto.diagram;

import org.i4change.app.dto.user.UserMinimalDTO;

public class RoleMinimalDTO {

	private Long rolesId;
	private UserMinimalDTO assignee;
	
	public Long getRolesId() {
		return rolesId;
	}
	public void setRolesId(Long rolesId) {
		this.rolesId = rolesId;
	}
	public UserMinimalDTO getAssignee() {
		return assignee;
	}
	public void setAssignee(UserMinimalDTO assignee) {
		this.assignee = assignee;
	}
	
}
