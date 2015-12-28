package org.i4change.app.data.project.daos;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.hibernate.beans.domain.Organisation;
import org.i4change.app.hibernate.beans.project.Project;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class ProjectDaoImpl extends HibernateDaoSupport {
	
	private static final Log log = LogFactory.getLog(ProjectDaoImpl.class);

	//Spring loaded Beans
	private UserDaoImpl userDaoImpl;
	
	public UserDaoImpl getUserDaoImpl() {
		return userDaoImpl;
	}
	public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}
	
	
	public Long addProject(String name, String description, Long userId, Organisation org, 
			String wizzardType, Boolean buildByWizzard, Integer wizzardStep ){
		try {
			
			Project project = new Project();
			project.setName(name);
			project.setDeleted("false");
			project.setDescription(description);
			project.setOrganisation(org);
			project.setWizzardType(wizzardType);
			project.setBuildByWizzard(buildByWizzard);
			project.setWizzardStep(wizzardStep);
			project.setOwner(this.userDaoImpl.getUserById(userId));
			project.setInserted(new Date());
			
			Long projectId = (Long) getHibernateTemplate().save(project);
			
			return projectId;
		} catch (HibernateException ex) {
			log.error("[addProject]",ex);
		} catch (Exception ex2) {
			log.error("[addProject]",ex2);
		}
		return null;
	}
	
	public void deleteProject(Project project){
		try {
			
			project.setUpdated(new Date());
			project.setDeleted("true");
			
			this.updateProject(project);
			
		} catch (Exception ex2) {
			log.error("[updateProject]",ex2);
		}
	}
	
	public void updateProject(Project project){
		try {
			
			project.setUpdated(new Date());
			
			getHibernateTemplate().update(project);
			
		} catch (HibernateException ex) {
			log.error("[updateProject]",ex);
		} catch (Exception ex2) {
			log.error("[updateProject]",ex2);
		}
	}

	public Project getProjectById(Long projectId) {
		try {
			
			String hql = "select c from Project as c " +
						"where c.projectId = :projectId " +
						"AND c.deleted!=:deleted ";
	
			Query query = getSession().createQuery(hql);
			query.setLong("projectId", projectId);
			query.setString("deleted", "true");
			Project project = (Project) query.uniqueResult();
			
			return project;
		} catch (HibernateException ex) {
			log.error("[getProjectById]",ex);
		} catch (Exception ex2) {
			log.error("[getProjectById]",ex2);
		}
		return null;
	}

	public List<Project> getProjectsByNoMaxAndOrder(Long organisation_id,
			String orderBy, boolean orderAsc, int start, int max, String search) {
		try {
			
			log.debug("diagramNo,organisation_id "+organisation_id);
			
			String orderItemValue = "ASC";
			if (!orderAsc) {
				orderItemValue = "DESC";
			}
			
			String hql = "SELECT c FROM Project as c " +
					"WHERE c.deleted!=:deleted " +
					"AND lower(c.name) LIKE lower(:search) " +
					"AND c.organisation.organisation_id=:organisation_id " +
					"ORDER BY "+orderBy+ " " + orderItemValue;

			Query query = getSession().createQuery(hql);
			query.setLong("organisation_id", organisation_id);
			query.setString("deleted", "true");
			query.setString("search", search);
			query.setMaxResults(max);
			query.setFirstResult(start);
			List<Project> projects = query.list();

			log.debug("getProjectsByNoMaxAndOrder diagram " + projects.size());
			
			return projects;
		} catch (HibernateException ex) {
			log.error("[getProjectsByNoMaxAndOrder]",ex);
		} catch (Exception ex2) {
			log.error("[getProjectsByNoMaxAndOrder]",ex2);
		}
		return null;
	}

	public Long getMaxProjects(Long organisation_id, String search) {
		try {
			
			log.debug("diagramNo,organisation_id "+organisation_id);
			
			String hql = "SELECT COUNT(*) as number FROM Project as c " +
						"WHERE c.deleted!=:deleted " +
						"AND lower(c.name) LIKE lower(:search) " +
						"AND c.organisation.organisation_id=:organisation_id";

			Query query = getSession().createQuery(hql);
			query.setLong("organisation_id", organisation_id);
			query.setString("deleted", "true");
			query.setString("search", search);
			
			List ll = query.list();
			log.info((Long)ll.get(0));
			
			return (Long)ll.get(0);	
		} catch (HibernateException ex) {
			log.error("[getMaxProjects]",ex);
		} catch (Exception ex2) {
			log.error("[getMaxProjects]",ex2);
		}
		return null;
	}

	public List<Project> getProjectsByUser(Long userId, Long organisation_id) {
		try {
			
			//, ProjectUser as c
			
			String hql = "select p from Project as p " +
						"WHERE p.owner.user_id = :userId " +
						"AND p.organisation.organisation_id = :organisation_id " +
						"AND p.deleted!=:deleted";
	
			Query query = getSession().createQuery(hql);
			query.setLong("userId", userId);
			query.setLong("organisation_id", organisation_id);
			query.setString("deleted", "true");
			List<Project> projectUsers = query.list();
			
			log.debug("getProjectsByUser userId: "+userId);
			log.debug("getProjectsByUser size: "+projectUsers.size());
			
			return projectUsers;
		} catch (HibernateException ex) {
			log.error("[getProjectsByUser]",ex);
		} catch (Exception ex2) {
			log.error("[getProjectsByUser]",ex2);
		}
		return null;
	}

}
