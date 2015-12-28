package org.i4change.app.remote;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.AuthLevelmanagement;
import org.i4change.app.data.basic.Sessionmanagement;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.data.domain.daos.OrganisationDaoImpl;
import org.i4change.app.data.project.daos.ProjectDaoImpl;
import org.i4change.app.data.project.daos.ProjectRelationDaoImpl;
import org.i4change.app.data.project.daos.ProjectUserDaoImpl;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.dto.organization.OrganisationListDTO;
import org.i4change.app.dto.project.ProjectDTO;
import org.i4change.app.dto.user.UserOrgDTO;
import org.i4change.app.hibernate.beans.diagram.Diagram;
import org.i4change.app.hibernate.beans.domain.Organisation;
import org.i4change.app.hibernate.beans.project.Project;
import org.i4change.app.hibernate.beans.project.ProjectUser;

public class ProjectService implements IProjectService {


	private static final Log log = LogFactory.getLog(ProjectService.class);	
	
	//Spring injected Beans
	private IApplication application;
	private ProjectDaoImpl projectDaoImpl;
	private ProjectRelationDaoImpl projectRelationDaoImpl;
	private OrganisationDaoImpl organisationDaoImpl;
	private ProjectUserDaoImpl projectUserDaoImpl;
	private Sessionmanagement sessionmanagement;
	private UserDaoImpl userDaoImpl;
	
	public IApplication getApplication() {
		return application;
	}
	public void setApplication(IApplication application) {
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
	public ProjectUserDaoImpl getProjectUserDaoImpl() {
		return projectUserDaoImpl;
	}
	public void setProjectUserDaoImpl(ProjectUserDaoImpl projectUserDaoImpl) {
		this.projectUserDaoImpl = projectUserDaoImpl;
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
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IProjectService#addProject(java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.lang.String, java.lang.Boolean, java.lang.Integer)
	 */
	public Long addProject(String SID, String name, String description, Long organisation_id,
			String wizzardType, Boolean buildByWizzard, Integer wizzardStep) {
		try {
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)){
	        	Organisation org = this.organisationDaoImpl.getOrganisationById(organisation_id);
	        	return this.projectDaoImpl.addProject(name, description, users_id, org, 
	        			wizzardType, buildByWizzard, wizzardStep);
	        }
		} catch (Exception e){
			log.error("addProject",e);
		}
		return -1L;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IProjectService#getProjectById(java.lang.String, java.lang.Long)
	 */
	public ProjectDTO getProjectById(String SID, Long projectId) {
		try {
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)){
	        	Project project = this.projectDaoImpl.getProjectById(projectId);
	        	
	        	return this.morphProjectToDTO(project);
	        	
	        }
		} catch (Exception e){
			log.error("getProjectById",e);
		}
		return null;
	}
	
	private ProjectDTO morphProjectToDTO(Project project) throws Exception {
		
		ProjectDTO projectDTO = new ProjectDTO();
    	projectDTO.setProjectId(project.getProjectId());
    	projectDTO.setInserted(project.getInserted());
    	projectDTO.setName(project.getName());
    	
    	if (project.getOrganisation() != null) {
    		projectDTO.setOrganisation(new OrganisationListDTO());
    		projectDTO.getOrganisation().setName(project.getOrganisation().getName());
    		projectDTO.getOrganisation().setOrganisation_id(project.getOrganisation().getOrganisation_id());
    	}
    	
    	if (project.getOwner() != null) {
    		projectDTO.setOwner(new UserOrgDTO());
    		projectDTO.getOwner().setFirstname(project.getOwner().getFirstname());
    		projectDTO.getOwner().setLastname(project.getOwner().getLastname());
    		projectDTO.getOwner().setLogin(project.getOwner().getLogin());
    		projectDTO.getOwner().setUser_id(project.getOwner().getUser_id());
    	}
    	
    	return projectDTO;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IProjectService#updateProjectWizzard(java.lang.String, java.lang.Long, java.lang.String, java.lang.Integer)
	 */
	public Long updateProjectWizzard(String SID, Long projectId, 
			String wizzardType, Integer wizzardStep) {
		try {
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkAdminLevel(user_level)){
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
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IProjectService#getProjectsBySearch(java.lang.String, int, int, java.lang.String, boolean, java.lang.Long, java.lang.String)
	 */
	public SearchResult getProjectsBySearch(String SID, int start, int max,
			String orderBy, boolean asc, Long organization_id, String search) {
		try {
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)){
	        	
	        	if (search.equals(null) || search.length() == 0) {
	        		search = "%";
	        	} else {
	        		search = "%"+search+"%";
	        	}
	        	
	        	List<Project> listResult = this.projectDaoImpl.getProjectsByNoMaxAndOrder(organization_id, orderBy, 
	        															asc, start, max, search);
			
	        	List<ProjectDTO> projectDTOListResult = new LinkedList<ProjectDTO>();
	        	for (int i=0;i<listResult.size();i++) {
	        		projectDTOListResult.add(this.morphProjectToDTO(listResult.get(i)));
	        	}
	        	
				SearchResult sresult = new SearchResult();
				sresult.setObjectName(ProjectDTO.class.getName());
				//sresult.setRecords(this.diagramDaoImpl.getMaxDiagram(organization_id, search));
				sresult.setRecords(this.projectDaoImpl.getMaxProjects(organization_id, search));
				sresult.setResult(projectDTOListResult);
				
				return sresult;
	        }
	        return null;
		} catch (Exception e){
			log.error("updateProjectWizzard",e);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IProjectService#addProjectToUser(java.lang.String, long)
	 */
	public Long addProjectToUser(String SID, long projectId) {
		try {
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)){
	        	
	        	Project project = this.projectDaoImpl.getProjectById(projectId);
	        	
	        	if (project.getOwner().getUser_id().equals(users_id)) {
	        		return new Long(-52);
	        	}
	        	return this.projectUserDaoImpl.addProjectToUser(users_id,projectId);
	        	
	        }
	        return null;
		} catch (Exception e){
			log.error("addProjectToUser",e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IProjectService#getProjectsByUser(java.lang.String, java.lang.Long)
	 */
	public List<ProjectDTO> getProjectsByUser(String SID, Long organisation_id) {
		try {
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)){
	        	
	        	List<Project> projects = this.projectDaoImpl.getProjectsByUser(users_id, organisation_id);
	        	
	        	List<ProjectUser> projectUsers = this.projectUserDaoImpl.getProjectUsersByUser(users_id);
	        	
	        	List<ProjectDTO> returnList = new LinkedList<ProjectDTO>();
	        	
	        	if (projects != null) {
		        	for (int i=0;i<projects.size();i++) {
		        		returnList.add(
			        		this.morphProjectToDTO(projects.get(i))
		        				);
		        	}
	        	}
	        	
	        	if (projectUsers != null) {
	        		
	        		for (int i=0;i<projectUsers.size();i++) {
	        			
	        			ProjectUser projectUser = projectUsers.get(i);
	        			
	        			boolean found = false;
	        			
	        			for (Project Project : projects) {
	        				if (Project.getProjectId() == projectUser.getProjectId()) {
	        					found = true;
	        					break;
	        				}
	        			}
	        			
	        			if (!found) {
	        				returnList.add(
	    			        		this.morphProjectToDTO(
	    			        					this.projectDaoImpl.getProjectById(projectUser.getProjectId())
	    			        				)
	    		        				);
	        			}
	        			
	        		}
	        		
	        	}
	        	
	        	return returnList;
	        	
	        }
	        return null;
		} catch (Exception e){
			log.error("getProjectsByUser",e);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IProjectService#deleteProjectFromUser(java.lang.String, long)
	 */
	public Long deleteProjectFromUser(String SID, long projectId){
		try {
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)){
	        	
	        	return this.projectUserDaoImpl.deleteProjectFromUser(users_id, projectId);
	        	
	        }
	        return null;
		} catch (Exception e){
			log.error("deleteProjectFromUser",e);
		}
		return null;
	}
	
}
