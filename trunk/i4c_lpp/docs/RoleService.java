package org.i4change.app.remote;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.AuthLevelmanagement;
import org.i4change.app.data.basic.Sessionmanagement;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.data.diagram.DiagramObjectDaoImpl;
import org.i4change.app.data.diagram.RoleDaoImpl;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.dto.diagram.DiagramObjectSearchDTO;
import org.i4change.app.dto.diagram.RoleDTO;
import org.i4change.app.dto.organization.OrganisationListDTO;
import org.i4change.app.dto.user.UserMinimalDTO;
import org.i4change.app.hibernate.beans.diagram.DiagramObject;
import org.i4change.app.hibernate.beans.diagram.Role;

public class RoleService {

	private static final Log log = LogFactory.getLog(RoleService.class);
	
	//Spring injected Bean
	private RoleDaoImpl roleDaoImpl;
	private Sessionmanagement sessionmanagement;
	private UserDaoImpl userDaoImpl;
	
	public RoleDaoImpl getRoleDaoImpl() {
		return roleDaoImpl;
	}
	public void setRoleDaoImpl(RoleDaoImpl roleDaoImpl) {
		this.roleDaoImpl = roleDaoImpl;
	}
	
	public Sessionmanagement getSessionmanagement() {
		return sessionmanagement;
	}
	public void setSessionmanagement(Sessionmanagement sessionmanagement) {
		this.sessionmanagement = sessionmanagement;
	}

	public UserDaoImpl getUserDaoImpl() {
		return userDaoImpl;
	}
	public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}

	public RoleDTO getRoleById(String SID, Long rolesId) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	return this.morphRoleToRoleDTO(this.roleDaoImpl.getRoleById(rolesId));
	        }
		} catch (Exception err) {
			log.error("[getRoleById]",err);
		}
        return null;
	}
	
	public List<RoleDTO> getRolesBySelf(String SID, Long organisation_id) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	List<Role> listResultRoles = this.roleDaoImpl.getRolesByUserId(users_id, organisation_id);

	        	List<RoleDTO> listResult = new LinkedList<RoleDTO>();
	        	
	        	for (int i=0;i<listResultRoles.size();i++) {
	        		listResult.add(this.morphRoleToRoleDTO(listResultRoles.get(i)));
	        	}
	        	
	        	return listResult;
	        }
		} catch (Exception err) {
			log.error("[getRolesBySelf]",err);
		}
        return null;
	}
	
	
	public List<RoleDTO> getRolesByUserId(String SID, Long user_id, Long organisation_id) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	List<Role> listResultRoles = this.roleDaoImpl.getRolesByUserId(user_id, organisation_id);
	        	
	        	List<RoleDTO> listResult = new LinkedList<RoleDTO>();
	        	
	        	for (int i=0;i<listResultRoles.size();i++) {
	        		listResult.add(this.morphRoleToRoleDTO(listResultRoles.get(i)));
	        	}
	        	
	        	return listResult;
	        }
		} catch (Exception err) {
			log.error("[getRolesByUserId]",err);
		}
        return null;
	}
	
	public SearchResult getRolesByStartAndMax(String SID, int start, int max, String orderby, boolean asc, Long organisation_id) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	List<Role> listResultRoles = this.roleDaoImpl.getRolesByStartAndMax(start, max, orderby, asc, organisation_id);
	        	
	        	List<RoleDTO> listResult = new LinkedList<RoleDTO>();
	        	
	        	for (int i=0;i<listResultRoles.size();i++) {
	        		listResult.add(this.morphRoleToRoleDTO(listResultRoles.get(i)));
	        	}
	        		
	        	SearchResult sresult = new SearchResult();
				sresult.setObjectName(DiagramObject.class.getName());
				sresult.setRecords(this.roleDaoImpl.selectMaxFromRoleObject(organisation_id));
				sresult.setResult(listResult);
				
				return sresult;
	        }
		} catch (Exception err) {
			log.error("[updateDiagram]",err);
		}
        return null;
	}
	
	public Long addRole(String SID, Long assignee_id, Long diagramObjectId, Long organisation_id) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	return this.roleDaoImpl.addRole(assignee_id, diagramObjectId, users_id, organisation_id);
	        }
		} catch (Exception err) {
			log.error("[updateDiagram]",err);
		}
        return null;
	}
	
	public void updateRole(String SID, Long rolesId, Long assignee_id, Long diagramObjectId) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	this.roleDaoImpl.updateRole(rolesId, assignee_id, diagramObjectId, users_id);
	        }
		} catch (Exception err) {
			log.error("[updateDiagram]",err);
		}
	}
	
	public void deleteRole(String SID, Long rolesId) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	this.roleDaoImpl.deleteRole(rolesId, users_id);
	        }
		} catch (Exception err) {
			log.error("[updateDiagram]",err);
		}
	}
	
	public Long saveOrUpdateRole(String SID, Map role) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	Map roleObject = (Map) role;
	        	Long rolesId = Long.valueOf(roleObject.get("rolesId").toString()).longValue();
	        	Long organisation_id = Long.valueOf(roleObject.get("organisation_id").toString()).longValue();
	        	Long assignee_id = Long.valueOf(roleObject.get("user_id").toString()).longValue();
	        	Long diagramObjectId = Long.valueOf(roleObject.get("diagramObjectId").toString()).longValue();
	        	if (diagramObjectId == 0) diagramObjectId = null;
	        	
	        	log.debug("rolesId: "+rolesId);
	        	log.debug("organisation_id: "+organisation_id);
	        	log.debug("assignee_id: "+assignee_id);
	        	log.debug("diagramObjectId: "+diagramObjectId);
	        	
	        	if (rolesId != 0) {
	        		this.updateRole(SID, rolesId, assignee_id, diagramObjectId);
	        		return rolesId;
	        	} else {
	        		return this.addRole(SID, assignee_id, diagramObjectId, organisation_id);
	        	}
	        }
		} catch (Exception err) {
			log.error("[saveOrUpdateRole]",err);
		}
		return new Long(-1);
	}
	
	public Long addRoleToUser(String SID, Map role) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	Map roleObject = (Map) role;
	        	Long organisation_id = Long.valueOf(roleObject.get("organisation_id").toString()).longValue();
	        	Long assignee_id = Long.valueOf(roleObject.get("user_id").toString()).longValue();
	        	Long diagramObjectId = Long.valueOf(roleObject.get("diagramObjectId").toString()).longValue();
	        	if (diagramObjectId == 0) diagramObjectId = null;
	        	
	        	log.debug("organisation_id: "+organisation_id);
	        	log.debug("assignee_id: "+assignee_id);
	        	log.debug("diagramObjectId: "+diagramObjectId);

	        	if (this.roleDaoImpl.getRolesByUserOrgAndDiagramObjectId(users_id, organisation_id, diagramObjectId).size() != 0) {
	        		return new Long(-50);
	        	}
	        	
	        	return this.roleDaoImpl.addRole(assignee_id, diagramObjectId, users_id, organisation_id);
	        	
	        }
		} catch (Exception err) {
			log.error("[saveOrUpdateRole]",err);
		}
		return new Long(-1);
	}
	
	private RoleDTO morphRoleToRoleDTO(Role role) throws Exception {
		
		RoleDTO roleDTO = new RoleDTO();
		
		roleDTO.setRolesId(role.getRolesId());
		
		if (role.getAssignee() != null) {
			roleDTO.setAssignee(new UserMinimalDTO());
			roleDTO.getAssignee().setFirstname(role.getAssignee().getFirstname());
			roleDTO.getAssignee().setLastname(role.getAssignee().getLastname());
			roleDTO.getAssignee().setLogin(role.getAssignee().getLogin());
			roleDTO.getAssignee().setUser_id(role.getAssignee().getUser_id());
		}
		
		if (role.getOrganisation() != null) {
			roleDTO.setOrganisation(new OrganisationListDTO());
			roleDTO.getOrganisation().setName(role.getOrganisation().getName());
			roleDTO.getOrganisation().setOrganisation_id(role.getOrganisation().getOrganisation_id());
		}
		
		if (role.getRoleObject() != null) {
			roleDTO.setRoleObject(new DiagramObjectSearchDTO());
			roleDTO.getRoleObject().setDiagramObjectId(role.getRoleObject().getDiagramObjectId());
			roleDTO.getRoleObject().setName(role.getRoleObject().getName());
			roleDTO.getRoleObject().setObjectTypeName(role.getRoleObject().getObjectTypeName());
			roleDTO.getRoleObject().setPending(role.getRoleObject().getPending());
		}
		
		return roleDTO;
		
	}
	
}
