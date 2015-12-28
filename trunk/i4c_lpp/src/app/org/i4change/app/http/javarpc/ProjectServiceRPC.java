package org.i4change.app.http.javarpc;

import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.dto.project.ProjectDTO;
import org.i4change.app.hibernate.beans.project.Project;
import org.i4change.app.remote.IProjectService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author sebastianwagner
 *
 */
public class ProjectServiceRPC extends BaseAdapterRPC {
	
	private static final Log log = LogFactory.getLog(ProjectServiceRPC.class);
	
	public Long addProject(String SID, String name, String description, Long organisation_id,
			String wizzardType, Boolean buildByWizzard, Integer wizzardStep){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IProjectService projectService = (IProjectService) context.getBean("projectservice.service");
		
			return projectService.addProject(SID, name, description, organisation_id, wizzardType, buildByWizzard, wizzardStep);
			
		} catch (Exception err) {
			log.error ("[addProject]",err);
		}
		return null;
	}
	
	public SearchResult getProjectsBySearch(String SID, int start, int max, String orderBy, boolean asc, 
			Long organization_id, String search){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IProjectService projectService = (IProjectService) context.getBean("projectservice.service");
		
			return projectService.getProjectsBySearch(SID, start, max, orderBy, asc, organization_id, search);
			
			
		} catch (Exception err) {
			log.error ("[getDiagramSearch]",err);
		}
		return null;
	}

	public ProjectDTO getProjectById(String SID, Long projectId){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IProjectService projectService = (IProjectService) context.getBean("projectservice.service");
		
			return projectService.getProjectById(SID, projectId);
			
		} catch (Exception err) {
			log.error ("[getProjectById]",err);
		}
		return null;
	}

	public Long updateProjectWizzard(String SID, Long projectId, 
			String wizzardType, Integer wizzardStep){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IProjectService projectService = (IProjectService) context.getBean("projectservice.service");
		
			return projectService.updateProjectWizzard(SID, projectId, wizzardType, wizzardStep);
			
		} catch (Exception err) {
			log.error ("[updateProjectWizzard]",err);
		}
		return null;
	}
	
	public Long addProjectToUser(String SID, long projectId) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IProjectService projectService = (IProjectService) context.getBean("projectservice.service");
		
			return projectService.addProjectToUser(SID, projectId);
			
		} catch (Exception err) {
			log.error ("[getProjectsByUser]",err);
		}
		return null;
	}
	
	public List<ProjectDTO> getProjectsByUser(String SID, Long organisation_id) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IProjectService projectService = (IProjectService) context.getBean("projectservice.service");
		
			return projectService.getProjectsByUser(SID, organisation_id);
			
		} catch (Exception err) {
			log.error ("[getProjectsByUser]",err);
		}
		return null;
	}
	
	public Long deleteProjectFromUser(String SID, long projectId){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IProjectService projectService = (IProjectService) context.getBean("projectservice.service");
		
			return projectService.deleteProjectFromUser(SID, projectId);
			
		} catch (Exception err) {
			log.error ("[getProjectsByUser]",err);
		}
		return null;
	}
	
}
