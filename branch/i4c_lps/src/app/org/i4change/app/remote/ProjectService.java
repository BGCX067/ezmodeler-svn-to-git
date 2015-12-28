package org.i4change.app.remote;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.AuthLevelmanagement;
import org.i4change.app.data.basic.Sessionmanagement;
import org.i4change.app.data.domain.daos.OrganisationDaoImpl;
import org.i4change.app.data.project.daos.ProjectDaoImpl;
import org.i4change.app.data.project.daos.ProjectRelationDaoImpl;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.hibernate.beans.domain.Organisation;
import org.i4change.app.hibernate.beans.project.Project;

public class ProjectService {


	private static final Log log = LogFactory.getLog(ProjectService.class);	
	
	//Spring injected Beans
	private Application application;
	private ProjectDaoImpl projectDaoImpl;
	private ProjectRelationDaoImpl projectRelationDaoImpl;
	private OrganisationDaoImpl organisationDaoImpl;
	
	public Application getApplication() {
		return application;
	}
	public void setApplication(Application application) {
		this.application = application;
	}
	public ProjectDaoImpl getProjectDaoImpl() {
		return projectDaoImpl;
	}
	public void setProjectDaoImpl(ProjectDaoImpl projectDaoImpl) {
		this.projectDaoImpl = projectDaoImpl;
	}
	public ProjectRelationDaoImpl getProjectRelationDaoImpl() {
		return projectRelationDaoImpl;
	}
	public void setProjectRelationDaoImpl(
			ProjectRelationDaoImpl projectRelationDaoImpl) {
		this.projectRelationDaoImpl = projectRelationDaoImpl;
	}
	public OrganisationDaoImpl getOrganisationDaoImpl() {
		return organisationDaoImpl;
	}
	public void setOrganisationDaoImpl(OrganisationDaoImpl organisationDaoImpl) {
		this.organisationDaoImpl = organisationDaoImpl;
	}
	
	/**
	 * Adds a new Project to an Organization
	 * Each Project can only have one Owner
	 * 
	 * @param SID
	 * @param name
	 * @param description
	 * @param organisation_id
	 * @param wizzardType
	 * @param buildByWizzard
	 * @param wizzardStep
	 * @return
	 */
	public Long addProject(String SID, String name, String description, Long organisation_id,
			String wizzardType, Boolean buildByWizzard, Integer wizzardStep) {
		try {
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)){
	        	Organisation org = this.organisationDaoImpl.getOrganisationById(organisation_id);
	        	return this.projectDaoImpl.addProject(name, description, users_id, org, 
	        			wizzardType, buildByWizzard, wizzardStep);
	        }
		} catch (Exception e){
			log.error("addProject",e);
		}
		return -1L;
	}
	
	public Project getProjectById(String SID, Long projectId) {
		try {
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)){
	        	return this.projectDaoImpl.getProjectById(projectId);
	        }
		} catch (Exception e){
			log.error("getProjectById",e);
		}
		return null;
	}
	
	public Long updateProjectWizzard(String SID, Long projectId, 
			String wizzardType, Integer wizzardStep) {
		try {
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkAdminLevel(user_level)){
	        	Project project = this.projectDaoImpl.getProjectById(projectId);
	        	project.setWizzardType(wizzardType);
	        	project.setWizzardStep(wizzardStep);
	        	this.projectDaoImpl.updateProject(project);
	        	return 1L;
	        }
		} catch (Exception e){
			log.error("updateProjectWizzard",e);
		}
		return -1L;
	}
	
}
