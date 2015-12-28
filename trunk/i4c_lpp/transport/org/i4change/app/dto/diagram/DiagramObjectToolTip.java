package org.i4change.app.dto.diagram;

import java.util.List;

import org.i4change.app.dto.user.UserMinimalDTO;

public class DiagramObjectToolTip {
	
	private long diagramObjectId;
	private List<RoleMinimalDTO> roles;
	private UserMinimalDTO insertedby;
	
	public long getDiagramObjectId() {
		return diagramObjectId;
	}
	public void setDiagramObjectId(long diagramObjectId) {
		this.diagramObjectId = diagramObjectId;
	}
	public List<RoleMinimalDTO> getRoles() {
		return roles;
	}
	public void setRoles(List<RoleMinimalDTO> roles) {
		this.roles = roles;
	}
	public UserMinimalDTO getInsertedby() {
		return insertedby;
	}
	public void setInsertedby(UserMinimalDTO insertedby) {
		this.insertedby = insertedby;
	}

}
