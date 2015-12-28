package org.i4change.app.data.project.daos;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.i4change.app.hibernate.beans.diagram.DiagramObject;
import org.i4change.app.hibernate.beans.project.ProjectRelation;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class ProjectRelationDaoImpl extends HibernateDaoSupport {
	
	private static final Log log = LogFactory.getLog(ProjectRelationDaoImpl.class);

	//Spring loaded Bean
	private ProjectDaoImpl projectDaoImpl;
	
	public ProjectDaoImpl getProjectDaoImpl() {
		return projectDaoImpl;
	}
	public void setProjectDaoImpl(ProjectDaoImpl projectDaoImpl) {
		this.projectDaoImpl = projectDaoImpl;
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
			projectRelation.setProject(this.projectDaoImpl.getProjectById(projectId));
			projectRelation.setDiagramNo(diagramNo);
			projectRelation.setDiagramObject(diagramObject);
			
			Long projectRelationId = (Long) getHibernateTemplate().save(projectRelation);
			
			return projectRelationId;
		} catch (HibernateException ex) {
			log.error("[addProjectRelation]",ex);
		} catch (Exception ex2) {
			log.error("[addProjectRelation]",ex2);
		}
		return null;
	}
	
	public void updateProjectRelation(ProjectRelation projectRelation){
		try {
		
			getHibernateTemplate().update(projectRelation);
			
		} catch (HibernateException ex) {
			log.error("[updateProjectRelation]",ex);
		} catch (Exception ex2) {
			log.error("[updateProjectRelation]",ex2);
		}
	}
	
	public void deleteProjectRelation(Long diagramNo){
		try {
			
			ProjectRelation projectRelation = this.getProjectRelationByDiagramNo(diagramNo);
			if (projectRelation != null) {
			
				getSession().delete(projectRelation);
			
			}
		} catch (HibernateException ex) {
			log.error("[deleteProjectRelation]",ex);
		} catch (Exception ex2) {
			log.error("[deleteProjectRelation]",ex2);
		}
	}

	/**
	 * get all Diagrams of a Project
	 * @param projectId
	 * @return
	 */
	public List<ProjectRelation> getProjectRelationsByProjectById(Long projectId) {
		try {
			
			String hql = "select c from ProjectRelation as c " +
						"where c.project.projectId = :projectId ";
	
			Query query = getSession().createQuery(hql);
			query.setLong("projectId", projectId);
			List<ProjectRelation> projectRels = query.list();
			
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
						"where c.project.projectId = :projectId " +
						"AND c.diagramNo = :diagramNo ";
	
			Query query = getSession().createQuery(hql);
			query.setLong("projectId", projectId);
			query.setLong("diagramNo", diagramNo);
			ProjectRelation projectRel = (ProjectRelation) query.uniqueResult();
			
			return projectRel;
		} catch (HibernateException ex) {
			log.error("[getProjectRelationByIdAndDiagramNo]",ex);
		} catch (Exception ex2) {
			log.error("[getProjectRelationByIdAndDiagramNo]",ex2);
		}
		return null;
	}

	public ProjectRelation getProjectRelationByDiagramNo(Long diagramNo) {
		try {
			
			String hql = "select c from ProjectRelation as c " +
						"where c.diagramNo = :diagramNo ";
	
			Query query = getSession().createQuery(hql);
			query.setLong("diagramNo", diagramNo);
			ProjectRelation projectRel = (ProjectRelation) query.uniqueResult();
			
			return projectRel;
		} catch (HibernateException ex) {
			log.error("[getProjectRelationByDiagramNo]",ex);
		} catch (Exception ex2) {
			log.error("[getProjectRelationByDiagramNo]",ex2);
		}
		return null;
	}

}
