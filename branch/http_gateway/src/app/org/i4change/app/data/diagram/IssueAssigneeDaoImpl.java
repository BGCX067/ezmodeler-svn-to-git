package org.i4change.app.data.diagram;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.i4change.app.data.basic.Mailmanagement;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.hibernate.beans.diagram.DiagramObject;
import org.i4change.app.hibernate.beans.diagram.IssueAssignee;
import org.i4change.app.hibernate.utils.HibernateUtil;

public class IssueAssigneeDaoImpl {

	private static final Log log = LogFactory.getLog(IssueAssigneeDaoImpl.class);	

	private static IssueAssigneeDaoImpl instance = null;

	public static synchronized IssueAssigneeDaoImpl getInstance() {
		if (instance == null) {
			instance = new IssueAssigneeDaoImpl();
		}
		return instance;
	}
	
	public Long addIssueAssignee(Long diagramObjectId, Long assigneeUser_id, Long insertedBy, String diagramName, Long default_lang_id) {
		try {
			
			
			IssueAssignee issueAssignee = new IssueAssignee();
			issueAssignee.setDiagramObject(DiagramObjectDaoImpl.getInstance().getDiagramObjectById(diagramObjectId));
			issueAssignee.setInserted(new Date());
			issueAssignee.setAssignee(UserDaoImpl.getInstance().getUserById(assigneeUser_id));
			issueAssignee.setInsertedby(UserDaoImpl.getInstance().getUserById(insertedBy));
			issueAssignee.setDeleted("false");

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Long issueAssigneeId = (Long) session.save(issueAssignee);

			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("added id " + issueAssigneeId);

			//Add a mail to the Spool for the Assignee
			Mailmanagement.getInstance().addMailToSpoolAboutNewIssue(insertedBy, assigneeUser_id, diagramName, 
					default_lang_id, issueAssignee.getDiagramObject().getName());
			
			return issueAssigneeId;
		} catch (HibernateException ex) {
			log.error("[addDiagramType]",ex);
		} catch (Exception ex2) {
			log.error("[addDiagramType]",ex2);
		}
		return null;
	}
	
	/**
	 * return the issueassigneeId NOT the USER_ID of the Assignee
	 * @param diagramObjectId
	 * @param assigneeUser_id
	 * @param insertedBy
	 * @param diagramName
	 * @param default_lang_id
	 * @return
	 */
	public Long saveOrUpdateIssueAssignee(Long diagramObjectId, Long assigneeUser_id, Long insertedBy, String diagramName, Long default_lang_id) {
		try {
			IssueAssignee issueAssignee = this.getIssueAssignee(diagramObjectId);
			Long issueAssigneeId = null;
			
			if (issueAssignee == null) {
				issueAssigneeId = this.addIssueAssignee(diagramObjectId, assigneeUser_id, insertedBy, diagramName, default_lang_id);
			} else {
				issueAssigneeId = issueAssignee.getIssueassigneeId();
				
				//Only do that if the Assignee has changed
				if (!issueAssignee.getAssignee().getUser_id().equals(assigneeUser_id)) {
					this.updateIssueAssignee(issueAssignee, assigneeUser_id, insertedBy, diagramName, default_lang_id);
				}
			}
			
			return issueAssigneeId;
		} catch (HibernateException ex) {
			log.error("[getDiagramObjectById]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramObjectById]",ex2);
		}
		return null;
	}
	
	public List<IssueAssignee> getIssueAssignees() {
		try {
			String hql = "FROM IssueAssignee";

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			List<IssueAssignee> issueAssignee = query.list();

			tx.commit();
			HibernateUtil.closeSession(idf);

			//log.debug("select issueAssignee " + issueAssignee);

			return issueAssignee;
		} catch (HibernateException ex) {
			log.error("[getIssueAssignees]",ex);
		} catch (Exception ex2) {
			log.error("[getIssueAssignees]",ex2);
		}
		return null;
	}
	
	public IssueAssignee getIssueAssignee(Long diagramObjectId) {
		try {
			String hql = "SELECT c FROM IssueAssignee as c " +
					"WHERE c.diagramObject.diagramObjectId=:diagramObjectId " +
					"AND c.deleted!=:deleted";

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			query.setLong("diagramObjectId", diagramObjectId);
			query.setString("deleted", "true");
			IssueAssignee issueAssignee = (IssueAssignee) query.uniqueResult();

			tx.commit();
			HibernateUtil.closeSession(idf);

			//log.debug("select issueAssignee " + issueAssignee);

			return issueAssignee;
		} catch (HibernateException ex) {
			log.error("[getIssueAssignee]",ex);
		} catch (Exception ex2) {
			log.error("[getIssueAssignee]",ex2);
		}
		return null;
	}
	
	public void updateIssueAssignee(IssueAssignee issueAssignee, Long assigneeUser_id, Long insertedBy, 
			String diagramName, Long default_lang_id) {
		try {
			issueAssignee.setUpdated(new Date());
			issueAssignee.setAssignee(UserDaoImpl.getInstance().getUserById(assigneeUser_id));
			issueAssignee.setUpdatedBy(UserDaoImpl.getInstance().getUserById(insertedBy));

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			session.update(issueAssignee);

			tx.commit();
			HibernateUtil.closeSession(idf);
			
			//Add a mail to the Spool for the Assignee
			Mailmanagement.getInstance().addMailToSpoolAboutUpdatedIssue(insertedBy, assigneeUser_id, diagramName, 
					default_lang_id, issueAssignee.getDiagramObject().getName());

		} catch (HibernateException ex) {
			log.error("[updateIssueAssignee]",ex);
		} catch (Exception ex2) {
			log.error("[updateIssueAssignee]",ex2);
		}
	}
	
}
