package org.i4change.app.data.diagram;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.i4change.app.data.domain.daos.OrganisationDaoImpl;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.hibernate.beans.diagram.Assignee;
import org.i4change.app.hibernate.beans.diagram.DiagramObject;
import org.i4change.app.hibernate.utils.HibernateUtil;

public class AssigneeDaoImpl {

	private static final Log log = LogFactory.getLog(AssigneeDaoImpl.class);	

	private static AssigneeDaoImpl instance = null;

	public static synchronized AssigneeDaoImpl getInstance() {
		if (instance == null) {
			instance = new AssigneeDaoImpl();
		}
		return instance;
	}
	
//	public Assignee getAssginee(Long assigneeId) {
//		try {
//			
//			String hql = "SELECT c FROM Assignee as c " +
//					"WHERE c.assigneeId=:assigneeId";
//
//			Object idf = HibernateUtil.createSession();
//			Session session = HibernateUtil.getSession();
//			Transaction tx = session.beginTransaction();
//
//			Query query = session.createQuery(hql);
//			query.setLong("assigneeId", assigneeId);
//			Assignee assignee = (Assignee) query.uniqueResult();
//
//			tx.commit();
//			HibernateUtil.closeSession(idf);
//
//			log.debug("select assignee " + assignee);
//
//			return assignee;
//			
//		} catch (HibernateException ex) {
//			log.error("[getAssginee]",ex);
//		} catch (Exception ex2) {
//			log.error("[getAssginee]",ex2);
//		}
//		return null;
//	}
	
	public Assignee getAssginee(Long assigneeId) {
		try {
			
			String hql = "SELECT c FROM Assignee as c " +
					"WHERE c.assigneeId=:assigneeId";

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			query.setLong("assigneeId", assigneeId);
			Assignee assignee = (Assignee) query.uniqueResult();

			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("select assignee " + assignee);

			return assignee;
			
		} catch (HibernateException ex) {
			log.error("[getAssginee]",ex);
		} catch (Exception ex2) {
			log.error("[getAssginee]",ex2);
		}
		return null;
	}
	
	public Assignee addAssginee(Long user_id, Long assigneeUserId) {
		try {
			
			Assignee assignee = new Assignee();
			assignee.setInserted(new Date());
			assignee.setInsertedby(user_id);
			assignee.setAssignee(UserDaoImpl.getInstance().getUserById(assigneeUserId));

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Long assigneeId = (Long) session.save(assignee);

			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("addAssgineeObject: " + assigneeId);

			return this.getAssginee(assigneeId);
		} catch (HibernateException ex) {
			log.error("[addAssgineeObject]",ex);
		} catch (Exception ex2) {
			log.error("[addAssgineeObject]",ex2);
		}
		return null;
	}
	
	public Assignee addAssgineeByObject(Assignee assignee) {
		try {

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Long assigneeId = (Long) session.save(assignee);

			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("addAssgineeByObject: " + assigneeId);

			return this.getAssginee(assigneeId);
		} catch (HibernateException ex) {
			log.error("[addAssgineeByObject]",ex);
		} catch (Exception ex2) {
			log.error("[addAssgineeByObject]",ex2);
		}
		return null;
	}
	
	public void updateAssginee(Long user_id, Long assigneeId, Long assigneeUserId) {
		try {
			
			Assignee assignee = this.getAssginee(assigneeId);
			assignee.setUpdated(new Date());
			assignee.setUpdatedBy(user_id);
			assignee.setAssignee(UserDaoImpl.getInstance().getUserById(assigneeUserId));

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			session.update(assignee);

			tx.commit();
			HibernateUtil.closeSession(idf);

		} catch (HibernateException ex) {
			log.error("[updateAssginee]",ex);
		} catch (Exception ex2) {
			log.error("[updateAssginee]",ex2);
		}
	}
	
}
