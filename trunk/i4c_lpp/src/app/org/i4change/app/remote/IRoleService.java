package org.i4change.app.remote;

import java.util.List;
import java.util.Map;

import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.dto.diagram.RoleDTO;

public interface IRoleService {

	public abstract RoleDTO getRoleById(String SID, Long rolesId);

	public abstract List<RoleDTO> getRolesBySelf(String SID,
			Long organisation_id);

	public abstract List<RoleDTO> getRolesByUserId(String SID, Long user_id,
			Long organisation_id);

	public abstract SearchResult getRolesByStartAndMax(String SID, int start,
			int max, String orderby, boolean asc, Long organisation_id);

	public abstract Long addRole(String SID, Long assignee_id,
			Long diagramObjectId, Long organisation_id);

	public abstract void updateRole(String SID, Long rolesId, Long assignee_id,
			Long diagramObjectId);

	public abstract void deleteRole(String SID, Long rolesId);

	public abstract Long saveOrUpdateRole(String SID, Map role);

	public abstract Long addRoleToUser(String SID, Map role);

}