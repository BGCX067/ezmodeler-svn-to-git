package org.i4change.app.data.diagram;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.i4change.app.data.basic.Mailmanagement;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.hibernate.beans.diagram.IssueAssignee;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class IssueAssigneeDaoImpl extends HibernateDaoSupport {

	private static final Log log = LogFactory.getLog(IssueAssigneeDaoImpl.class);	

	//Spring loaded beans
	private Mailmanagement mailmanagement;
	private DiagramObjectDaoImpl diagramObjectDaoImpl;
	private UserDaoImpl userDaoImpl;
	
	public UserDaoImpl getUserDaoImpl() {
		return userDaoImpl;
	}
	public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}
	public Mailmanagement getMailmanagement() {
		return mailmanagement;
	}
	public void setMailmanagement(Mailmanagement mailmanagement) {
		this.mailmanagement = mailmanagement;
	}

	public DiagramObjectDaoImpl getDiagramObjectDaoImpl() {
		return diagramObjectDaoImpl;
	}
	public void setDiagramObjectDaoImpl(DiagramObjectDaoImpl diagramObjectDaoImpl) {
		this.diagramObjectDaoImpl = diagramObjectDaoImpl;
	}

	public Long addIssueAssignee(Long diagramObjectId, Long assigneeUser_id, Long insertedBy, String diagramName, Long default_lang_id) {
		try {
			
			
			IssueAssignee issueAssignee = new IssueAssignee();
			issueAssignee.setDiagramObject(this.diagramObjectDaoImpl.getDiagramObjectById(diagramObjectId));
			issueAssignee.setInserted(new Date());
			issueAssignee.setAssignee(this.userDaoImpl.getUserById(assigneeUser_id));
			issueAssignee.setInsertedby(this.userDaoImpl.getUserById(insertedBy));
			issueAssignee.setDeleted("false");

			Long issueAssigneeId = (Long) getHibernateTemplate().save(issueAssignee);

			log.debug("added id " + issueAssigneeId);

			//Add a mail to the Spool for the Assignee
			this.mailmanagement.addMailToSpoolAboutNewIssue(insertedBy, assigneeUser_id, diagramName, 
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

			Query query = getSession().createQuery(hql);
			List<IssueAssignee> issueAssignee = query.list();

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

			Query query = getSession().createQuery(hql);
			query.setLong("diagramObjectId", diagramObjectId);
			query.setString("deleted", "true");
			IssueAssignee issueAssignee = (IssueAssignee) query.uniqueResult();

			//log.debug("select issueAssignee " + issueAssignee);

			return issueAssignee;
		} catch (HibernateException ex) {
			log.error("[getIssueAssignee]",ex);
		} catch (Exception ex2) {
			log.error("[getIssueAssignee]",ex2);
		}
		return null;
	}
	
	public List<IssueAssignee> getIssuesBySearch(int start, int max, String orderBy, 
			boolean orderAsc, Long organisation_id, String search) {
		try {
			
			String orderItemValue = "ASC";
			if (!orderAsc) {
				orderItemValue = "DESC";
			}
			
			String hql = "SELECT c FROM IssueAssignee as c, DiagramSummary as ds, DiagramInstanceObject dio " +
					"WHERE c.diagramObject.objectTypeName=:issueflow " +
					"AND dio.diagramObject.diagramObjectId = c.diagramObject.diagramObjectId " +
					"AND ds.diagram.diagramId = dio.diagram.diagramId " +
					"AND lower(c.diagramObject.name) LIKE lower(:search) " +
					"AND c.diagramObject.organisation.organisation_id = :organisation_id " +
					"AND c.deleted!=:deleted AND c.diagramObject.deleted!=:deleted " +
					"ORDER BY "+orderBy+ " " + orderItemValue;

			Query query = getSession().createQuery(hql);
			query.setString("deleted", "true");
			query.setString("search", search);
			query.setString("issueflow", "issueflow");
			query.setLong("organisation_id", organisation_id);
			query.setMaxResults(max);
			query.setFirstResult(start);
			List<IssueAssignee> issueAssignees = query.list();

			return issueAssignees;
		} catch (HibernateException ex) {
			log.error("[getIssuesBySearch]",ex);
		} catch (Exception ex2) {
			log.error("[getIssuesBySearch]",ex2);
		}
		return null;
	}
	
	public List<IssueAssignee> getIssuesBySearchByAssginee(int start, int max, String orderBy, 
			boolean orderAsc, Long organisation_id, String search, Long assigne_user_id) {
		try {
			
			String orderItemValue = "ASC";
			if (!orderAsc) {
				orderItemValue = "DESC";
			}
			
			String hql = "SELECT c FROM IssueAssignee as c, DiagramSummary as ds, DiagramInstanceObject dio " +
					"WHERE c.diagramObject.objectTypeName=:issueflow " +
					"AND dio.diagramObject.diagramObjectId = c.diagramObject.diagramObjectId " +
					"AND ds.diagram.diagramId = dio.diagram.diagramId " +
					"AND lower(c.diagramObject.name) LIKE lower(:search) " +
					"AND c.diagramObject.organisation.organisation_id = :organisation_id " +
					"AND c.deleted!=:deleted AND c.diagramObject.deleted!=:deleted " +
					"AND c.assignee.user_id = :user_id " +
					"ORDER BY "+orderBy+ " " + orderItemValue;

			Query query = getSession().createQuery(hql);
			query.setString("deleted", "true");
			query.setString("search", search);
			query.setString("issueflow", "issueflow");
			query.setLong("organisation_id", organisation_id);
			query.setLong("user_id", assigne_user_id);
			query.setMaxResults(max);
			query.setFirstResult(start);
			List<IssueAssignee> issueAssignees = query.list();

			return issueAssignees;
		} catch (HibernateException ex) {
			log.error("[getIssuesBySearchByAssginee]",ex);
		} catch (Exception ex2) {
			log.error("[getIssuesBySearchByAssginee]",ex2);
		}
		return null;
	}
	
	public Long getIssuesBySearchMax(int start, int max, String orderBy, 
			boolean orderAsc, Long organisation_id, String search) {
		try {
			
			String hql = "SELECT COUNT(c) FROM IssueAssignee as c, DiagramSummary as ds, DiagramInstanceObject dio " +
						"WHERE c.diagramObject.objectTypeName=:issueflow " +
						"AND dio.diagramObject.diagramObjectId = c.diagramObject.diagramObjectId " +
						"AND ds.diagram.diagramId = dio.diagram.diagramId " +
						"AND lower(c.diagramObject.name) LIKE lower(:search) " +
						"AND c.diagramObject.organisation.organisation_id = :organisation_id " +
						"AND c.deleted!=:deleted AND c.diagramObject.deleted!=:deleted";

			Query query = getSession().createQuery(hql);
			query.setString("deleted", "true");
			query.setString("search", search);
			query.setString("issueflow", "issueflow");
			query.setLong("organisation_id", organisation_id);
			List ll = query.list();

			return (Long)ll.get(0);
		} catch (HibernateException ex) {
			log.error("[getIssuesBySearchMax]",ex);
		} catch (Exception ex2) {
			log.error("[getIssuesBySearchMax]",ex2);
		}
		return null;
	}
	
	public void updateIssueAssignee(IssueAssignee issueAssignee, Long assigneeUser_id, Long insertedBy, 
			String diagramName, Long default_lang_id) {
		try {
			issueAssignee.setUpdated(new Date());
			issueAssignee.setAssignee(this.userDaoImpl.getUserById(assigneeUser_id));
			issueAssignee.setUpdatedBy(this.userDaoImpl.getUserById(insertedBy));

			getHibernateTemplate().update(issueAssignee);

			//Add a mail to the Spool for the Assignee
			this.mailmanagement.addMailToSpoolAboutUpdatedIssue(insertedBy, assigneeUser_id, diagramName, 
					default_lang_id, issueAssignee.getDiagramObject().getName());

		} catch (HibernateException ex) {
			log.error("[updateIssueAssignee]",ex);
		} catch (Exception ex2) {
			log.error("[updateIssueAssignee]",ex2);
		}
	}

	public Long getIssuesBySearchMaxByAssignee(int start, int max,
			String orderBy, boolean asc, Long organization_id, String search,
			Long assigne_user_id) {
		try {
			
			String hql = "SELECT COUNT(c) FROM IssueAssignee as c, DiagramSummary as ds, DiagramInstanceObject dio " +
						"WHERE c.diagramObject.objectTypeName=:issueflow " +
						"AND dio.diagramObject.diagramObjectId = c.diagramObject.diagramObjectId " +
						"AND ds.diagram.diagramId = dio.diagram.diagramId " +
						"AND lower(c.diagramObject.name) LIKE lower(:search) " +
						"AND c.diagramObject.organisation.organisation_id = :organisation_id " +
						"AND c.deleted!=:deleted AND c.diagramObject.deleted!=:deleted " +
						"AND c.assignee.user_id = :user_id ";

			Query query = getSession().createQuery(hql);
			query.setString("deleted", "true");
			query.setString("search", search);
			query.setString("issueflow", "issueflow");
			query.setLong("organisation_id", organization_id);
			query.setLong("user_id", assigne_user_id);
			List ll = query.list();

			return (Long)ll.get(0);
		} catch (HibernateException ex) {
			log.error("[getIssuesBySearchMax]",ex);
		} catch (Exception ex2) {
			log.error("[getIssuesBySearchMax]",ex2);
		}
		return null;
	}
	
}
