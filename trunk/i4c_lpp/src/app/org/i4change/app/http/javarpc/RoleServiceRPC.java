package org.i4change.app.http.javarpc;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.dto.diagram.RoleDTO;
import org.i4change.app.remote.IRoleService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author sebastianwagner
 *
 */
public class RoleServiceRPC extends BaseAdapterRPC {

	private static final Log log = LogFactory.getLog(RoleServiceRPC.class);
	
	public RoleDTO getRoleById(String SID, Long rolesId){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IRoleService roleService = (IRoleService) context.getBean("roleservice.service");
		
			return roleService.getRoleById(SID, rolesId);
			
		} catch (Exception err) {
			log.error ("[getRoleById]",err);
		}
		return null;
	}

	public List<RoleDTO> getRolesBySelf(String SID, Long organisation_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IRoleService roleService = (IRoleService) context.getBean("roleservice.service");
		
			return roleService.getRolesBySelf(SID, organisation_id);
			
		} catch (Exception err) {
			log.error ("[getRolesBySelf]",err);
		}
		return null;
	}

	public List<RoleDTO> getRolesByUserId(String SID, Long user_id, Long organisation_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IRoleService roleService = (IRoleService) context.getBean("roleservice.service");
		
			return roleService.getRolesByUserId(SID, user_id, organisation_id);
			
		} catch (Exception err) {
			log.error ("[getRolesByUserId]",err);
		}
		return null;
	}	

	public SearchResult getRolesByStartAndMax(String SID, int start, int max, String orderby, boolean asc, 
			Long organisation_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IRoleService roleService = (IRoleService) context.getBean("roleservice.service");
		
			return roleService.getRolesByStartAndMax(SID, start, max, orderby, asc, organisation_id);
			
		} catch (Exception err) {
			log.error ("[getRolesByStartAndMax]",err);
		}
		return null;
	}	

	public Long addRole(String SID, Long assignee_id, Long diagramObjectId, Long organisation_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IRoleService roleService = (IRoleService) context.getBean("roleservice.service");
		
			return roleService.addRole(SID, assignee_id, diagramObjectId, organisation_id);
			
		} catch (Exception err) {
			log.error ("[addRole]",err);
		}
		return null;
	}	

	public void updateRole(String SID, Long rolesId, Long assignee_id, Long diagramObjectId){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IRoleService roleService = (IRoleService) context.getBean("roleservice.service");
		
			roleService.updateRole(SID, rolesId, assignee_id, diagramObjectId);
			
		} catch (Exception err) {
			log.error ("[updateRole]",err);
		}
	}	

	public void deleteRole(String SID, Long rolesId){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IRoleService roleService = (IRoleService) context.getBean("roleservice.service");
		
			roleService.deleteRole(SID, rolesId);
			
		} catch (Exception err) {
			log.error ("[deleteRole]",err);
		}
	}	

	public Long saveOrUpdateRole(String SID, Map role){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IRoleService roleService = (IRoleService) context.getBean("roleservice.service");
		
			return roleService.saveOrUpdateRole(SID, role);
			
		} catch (Exception err) {
			log.error ("[saveOrUpdateRole]",err);
		}
		return null;
	}	

	public Long addRoleToUser(String SID, Map role){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IRoleService roleService = (IRoleService) context.getBean("roleservice.service");
		
			return roleService.addRoleToUser(SID, role);
			
		} catch (Exception err) {
			log.error ("[addRoleToUser]",err);
		}
		return null;
	}
	
}
