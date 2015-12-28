package org.i4change.app.remote;

import java.util.List;

import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.dto.project.ProjectDTO;

public interface IProjectService {

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
	public abstract Long addProject(String SID, String name,
			String description, Long organisation_id, String wizzardType,
			Boolean buildByWizzard, Integer wizzardStep);

	public abstract ProjectDTO getProjectById(String SID, Long projectId);

	public abstract Long updateProjectWizzard(String SID, Long projectId,
			String wizzardType, Integer wizzardStep);

	public abstract SearchResult getProjectsBySearch(String SID, int start,
			int max, String orderBy, boolean asc, Long organization_id,
			String search);

	public abstract Long addProjectToUser(String SID, long projectId);

	public abstract List<ProjectDTO> getProjectsByUser(String SID,
			Long organisation_id);

	public abstract Long deleteProjectFromUser(String SID, long projectId);

}