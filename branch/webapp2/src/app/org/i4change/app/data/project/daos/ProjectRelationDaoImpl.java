package org.i4change.app.data.project.daos;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.i4change.app.hibernate.beans.diagram.Diagram;
import org.i4change.app.hibernate.beans.diagram.DiagramObject;
import org.i4change.app.hibernate.beans.project.Project;
import org.i4change.app.hibernate.beans.project.ProjectRelation;
import org.i4change.app.hibernate.utils.HibernateUtil;

public class ProjectRelationDaoImpl  {
	
	private static final Log log = LogFactory.getLog(ProjectRelationDaoImpl.class);

	private static ProjectRelationDaoImpl instance = null;

	private ProjectRelationDaoImpl() {
	}

	public static synchronized ProjectRelationDaoImpl getInstance() {
		if (instance == null) {
			instance = new ProjectRelationDaoImpl();
		}
		return instance;
	}
	
	/**
	 * Adds a new Relation to a Project
	 * @param projectId
	 * @param diagramNo
	 * @param diagramObject
	 * @return
	 */
	public Long addProjectRelation(Long projectId, Long diagramNo, DiagramObject diagramObject){
		try {
			
			ProjectRelation projectRelation = new ProjectRelation();
			projectRelation.setProjectId(projectId);
			projectRelation.setDiagramNo(diagramNo);
			projectRelation.setDiagramObject(diagramObject);
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Long projectRelationId = (Long) session.save(projectRelation);
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return projectRelationId;
		} catch (HibernateException ex) {
			log.error("[addProjectRelation]",ex);
		} catch (Exception ex2) {
			log.error("[addProjectRelation]",ex2);
		}
		return null;
	}

	/**
	 * get all Diagrams of a Project
	 * @param projectId
	 * @return
	 */
	public List<ProjectRelation> getProjectRelationsByProjectById(Long projectId) {
		try {
			
			String hql = "select c from ProjectRelation as c " +
						"where c.projectId = :projectId ";
	
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("projectId", projectId);
			List<ProjectRelation> projectRels = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return projectRels;
		} catch (HibernateException ex) {
			log.error("[getProjectRelationsByProjectById]",ex);
		} catch (Exception ex2) {
			log.error("[getProjectRelationsByProjectById]",ex2);
		}
		return null;
	}
	
	/**
	 * check if the Diagram is already on Project Level
	 * @param projectId
	 * @param diagramNo
	 * @return
	 */
	public ProjectRelation getProjectRelationByIdAndDiagramNo(Long projectId, Long diagramNo) {
		try {
			
			String hql = "select c from ProjectRelation as c " +
						"where c.projectId = :projectId " +
						"AND c.diagramNo = :diagramNo ";
	
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("projectId", projectId);
			query.setLong("diagramNo", diagramNo);
			ProjectRelation projectRel = (ProjectRelation) query.uniqueResult();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return projectRel;
		} catch (HibernateException ex) {
			log.error("[getProjectRelationByIdAndDiagramNo]",ex);
		} catch (Exception ex2) {
			log.error("[getProjectRelationByIdAndDiagramNo]",ex2);
		}
		return null;
	}

}
