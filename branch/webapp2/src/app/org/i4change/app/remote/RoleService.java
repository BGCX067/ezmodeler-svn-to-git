package org.i4change.app.remote;

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
import org.i4change.app.hibernate.beans.diagram.DiagramObject;
import org.i4change.app.hibernate.beans.diagram.Role;

public class RoleService {

	private static final Log log = LogFactory.getLog(RoleService.class);
	
	private static RoleService instance = null;

	public static synchronized RoleService getInstance() {
		if (instance == null) {
			instance = new RoleService();
		}
		return instance;
	}
	
	public Role getRoleById(String SID, Long rolesId) {
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	return RoleDaoImpl.getInstance().getRoleById(rolesId);
	        }
		} catch (Exception err) {
			log.error("[updateDiagram]",err);
		}
        return null;
	}
	
	public List<Role> getRolesBySelf(String SID, Long organisation_id) {
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	return RoleDaoImpl.getInstance().getRolesByUserId(users_id, organisation_id);
	        }
		} catch (Exception err) {
			log.error("[updateDiagram]",err);
		}
        return null;
	}
	
	
	public List<Role> getRolesByUserId(String SID, Long user_id, Long organisation_id) {
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	return RoleDaoImpl.getInstance().getRolesByUserId(user_id, organisation_id);
	        }
		} catch (Exception err) {
			log.error("[updateDiagram]",err);
		}
        return null;
	}
	
	public SearchResult getRolesByStartAndMax(String SID, int start, int max, String orderby, boolean asc, Long organisation_id) {
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	List<Role> listResult = RoleDaoImpl.getInstance().getRolesByStartAndMax(start, max, orderby, asc, organisation_id);
	        	
	        	SearchResult sresult = new SearchResult();
				sresult.setObjectName(DiagramObject.class.getName());
				sresult.setRecords(RoleDaoImpl.getInstance().selectMaxFromRoleObject(organisation_id));
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
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	return RoleDaoImpl.getInstance().addRole(assignee_id, diagramObjectId, users_id, organisation_id);
	        }
		} catch (Exception err) {
			log.error("[updateDiagram]",err);
		}
        return null;
	}
	
	public void updateRole(String SID, Long rolesId, Long assignee_id, Long diagramObjectId) {
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	RoleDaoImpl.getInstance().updateRole(rolesId, assignee_id, diagramObjectId, users_id);
	        }
		} catch (Exception err) {
			log.error("[updateDiagram]",err);
		}
	}
	
	public void deleteRole(String SID, Long rolesId) {
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	RoleDaoImpl.getInstance().deleteRole(rolesId, users_id);
	        }
		} catch (Exception err) {
			log.error("[updateDiagram]",err);
		}
	}
	
	public Long saveOrUpdateRole(String SID, Object role) {
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
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
	
	public Long addRoleToUser(String SID, Object role) {
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	
	        	Map roleObject = (Map) role;
	        	Long organisation_id = Long.valueOf(roleObject.get("organisation_id").toString()).longValue();
	        	Long assignee_id = Long.valueOf(roleObject.get("user_id").toString()).longValue();
	        	Long diagramObjectId = Long.valueOf(roleObject.get("diagramObjectId").toString()).longValue();
	        	if (diagramObjectId == 0) diagramObjectId = null;
	        	
	        	log.debug("organisation_id: "+organisation_id);
	        	log.debug("assignee_id: "+assignee_id);
	        	log.debug("diagramObjectId: "+diagramObjectId);

	        	if (RoleDaoImpl.getInstance().getRolesByUserOrgAndDiagramObjectId(users_id, organisation_id, diagramObjectId).size() != 0) {
	        		return new Long(-50);
	        	}
	        	
	        	return RoleDaoImpl.getInstance().addRole(assignee_id, diagramObjectId, users_id, organisation_id);
	        	
	        }
		} catch (Exception err) {
			log.error("[saveOrUpdateRole]",err);
		}
		return new Long(-1);
	}
	
}
