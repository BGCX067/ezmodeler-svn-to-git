package org.i4change.app.http.javarpc;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.beans.ErrorResult;
import org.i4change.app.hibernate.beans.project.Project;
import org.i4change.app.remote.ProjectService;
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
			ProjectService projectService = (ProjectService) context.getBean("projectservice.service");
		
			return projectService.addProject(SID, name, description, organisation_id, wizzardType, buildByWizzard, wizzardStep);
			
		} catch (Exception err) {
			log.error ("[addProject]",err);
		}
		return null;
	}

	public Project getProjectById(String SID, Long projectId){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			ProjectService projectService = (ProjectService) context.getBean("projectservice.service");
		
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
			ProjectService projectService = (ProjectService) context.getBean("projectservice.service");
		
			return projectService.updateProjectWizzard(SID, projectId, wizzardType, wizzardStep);
			
		} catch (Exception err) {
			log.error ("[updateProjectWizzard]",err);
		}
		return null;
	}
	
}
