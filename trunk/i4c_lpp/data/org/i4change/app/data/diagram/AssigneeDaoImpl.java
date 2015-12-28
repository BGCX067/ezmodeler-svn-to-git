package org.i4change.app.data.diagram;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.i4change.app.data.basic.Fieldmanagment;
import org.i4change.app.data.mail.MailItemServiceDaoImpl;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.hibernate.beans.diagram.Assignee;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class AssigneeDaoImpl extends HibernateDaoSupport {

	private static final Log log = LogFactory.getLog(AssigneeDaoImpl.class);	
	
	//Spring loaded beans
	private UserDaoImpl userDaoImpl;
	
	public UserDaoImpl getUserDaoImpl() {
		return userDaoImpl;
	}
	public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
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

			Query query = getSession().createQuery(hql);
			query.setLong("assigneeId", assigneeId);
			Assignee assignee = (Assignee) query.uniqueResult();

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
			assignee.setAssignee(this.userDaoImpl.getUserById(assigneeUserId));

			Long assigneeId = (Long) getHibernateTemplate().save(assignee);

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

			Long assigneeId = (Long) getHibernateTemplate().save(assignee);

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
			assignee.setAssignee(this.userDaoImpl.getUserById(assigneeUserId));

			getHibernateTemplate().update(assignee);

		} catch (HibernateException ex) {
			log.error("[updateAssginee]",ex);
		} catch (Exception ex2) {
			log.error("[updateAssginee]",ex2);
		}
	}
	
}
