package org.i4change.app.data.project.daos;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.i4change.app.hibernate.beans.project.ProjectUser;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class ProjectUserDaoImpl extends HibernateDaoSupport {
	
	private static final Log log = LogFactory.getLog(ProjectUserDaoImpl.class);

	public ProjectUser getProjectUserByProjectAndUser(long projectId, long userId) {
		try {
			
			String hql = "select c from ProjectUser as c " +
						"where c.projectId = :projectId " +
						"AND c.userId = :userId " +
						"AND c.deleted!=:deleted ";
	
			Query query = getSession().createQuery(hql);
			query.setLong("projectId", projectId);
			query.setLong("userId", userId);
			query.setString("deleted", "true");
			ProjectUser projectUser = (ProjectUser) query.uniqueResult();
			
			return projectUser;
		} catch (HibernateException ex) {
			log.error("[getProjectUserByProjectAndUser]",ex);
		} catch (Exception ex2) {
			log.error("[getProjectUserByProjectAndUser]",ex2);
		}
		return null;
	}

	public Long addProjectToUser(Long userId, long projectId) {
		try {
			
			ProjectUser projectUser = this.getProjectUserByProjectAndUser(projectId, userId);
			
			if (projectUser != null) {
				return new Long(-53);
			}
			
			projectUser = new ProjectUser();
			projectUser.setDeleted("false");
			projectUser.setProjectId(projectId);
			projectUser.setUserId(userId);
			projectUser.setInserted(new Date());
			
			Long projectUserId = (Long) getHibernateTemplate().save(projectUser);
			
			return projectUserId;
		} catch (HibernateException ex) {
			log.error("[addProjectToUser]",ex);
		} catch (Exception ex2) {
			log.error("[addProjectToUser]",ex2);
		}
		return null;
	}
	
	public Long deleteProjectFromUser(Long userId, long projectId){
		try {
			
			ProjectUser projectUser = this.getProjectUserByProjectAndUser(projectId, userId);
			
			if (projectUser == null) {
				return new Long(-2);
			}
			projectUser.setDeleted("true");
			
			getHibernateTemplate().update(projectUser);
			
			return projectUser.getProjectUserId();
			
		} catch (Exception ex2) {
			log.error("[deleteProjectFromUser]",ex2);
		}
		return new Long(-1);
	}

	public List<ProjectUser> getProjectUsersByUser(Long users_id) {
		try {
			String hql = "select p from ProjectUser as p " +
						"WHERE p.userId = :userId " +
						"AND p.deleted!=:deleted";
	
			Query query = getSession().createQuery(hql);
			query.setLong("userId", users_id);
			query.setString("deleted", "true");
			List<ProjectUser> projectUsers = query.list();
			
			return projectUsers;
		} catch (HibernateException ex) {
			log.error("[getProjectsByUser]",ex);
		} catch (Exception ex2) {
			log.error("[getProjectsByUser]",ex2);
		}
		return null;
	}

}
