package org.i4change.app.data.project.daos;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.hibernate.beans.domain.Organisation;
import org.i4change.app.hibernate.beans.project.Project;
import org.i4change.app.hibernate.utils.HibernateUtil;

public class ProjectDaoImpl {
	
	private static final Log log = LogFactory.getLog(ProjectDaoImpl.class);

	private static ProjectDaoImpl instance = null;

	private ProjectDaoImpl() {
	}

	public static synchronized ProjectDaoImpl getInstance() {
		if (instance == null) {
			instance = new ProjectDaoImpl();
		}
		return instance;
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
			project.setOwner(UserDaoImpl.getInstance().getUserById(userId));
			project.setInserted(new Date());
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Long projectId = (Long) session.save(project);
			tx.commit();
			HibernateUtil.closeSession(idf);
			
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
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			session.update(project);
			tx.commit();
			HibernateUtil.closeSession(idf);
			
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
	
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("projectId", projectId);
			query.setString("deleted", "true");
			Project project = (Project) query.uniqueResult();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return project;
		} catch (HibernateException ex) {
			log.error("[getProjectById]",ex);
		} catch (Exception ex2) {
			log.error("[getProjectById]",ex2);
		}
		return null;
	}

}
