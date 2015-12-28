package org.i4change.app.data.diagram;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.i4change.app.data.domain.daos.OrganisationDaoImpl;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.hibernate.beans.diagram.Role;
import org.i4change.app.hibernate.utils.HibernateUtil;

public class RoleDaoImpl {

	private static final Log log = LogFactory.getLog(RoleDaoImpl.class);	

	private static RoleDaoImpl instance = null;

	public static synchronized RoleDaoImpl getInstance() {
		if (instance == null) {
			instance = new RoleDaoImpl();
		}
		return instance;
	}
	

	public Role getRoleById(Long rolesId) {
		try {
			String hql = "SELECT c FROM Role as c " +
					"WHERE c.rolesId=:rolesId " +
					"AND c.deleted!=:deleted";

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			query.setLong("rolesId", rolesId);
			query.setString("deleted", "true");
			Role roles = (Role) query.uniqueResult();
			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("select getRoleById " + roles);

			return roles;
		} catch (HibernateException ex) {
			log.error("[getRoleById]",ex);
		} catch (Exception ex2) {
			log.error("[getRoleById]",ex2);
		}
		return null;
	}

	public List<Role> getRoles() {
		try {
			String hql = "FROM Role";

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			List<Role> roles = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("select getRoles " + roles);

			return roles;
		} catch (HibernateException ex) {
			log.error("[getRoles]",ex);
		} catch (Exception ex2) {
			log.error("[getRoles]",ex2);
		}
		return null;
	}	
	
	public List<Role> getRolesByUserId(Long user_id, Long organisation_id) {
		try {
			String hql = "SELECT c FROM Role as c " +
					"WHERE c.assignee.user_id=:user_id " +
					"AND c.organisation.organisation_id=:organisation_id " +
					"AND c.roleObject.deleted!=:deleted " +
					"AND c.deleted!=:deleted";

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			query.setLong("user_id", user_id);
			query.setLong("organisation_id",organisation_id);
			query.setString("deleted", "true");
			List<Role> roles = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("select getRolesByUserId " + roles);

			return roles;
		} catch (HibernateException ex) {
			log.error("[getRolesByUserId]",ex);
		} catch (Exception ex2) {
			log.error("[getRolesByUserId]",ex2);
		}
		return null;
	}
	
	public List<Role> getRolesByDiagramObjectId(Long diagramObjectId) {
		try {
			String hql = "SELECT c FROM Role as c " +
					"WHERE c.roleObject.diagramObjectId = :diagramObjectId  " +
					"AND c.roleObject.deleted!=:deleted " +
					"AND c.deleted!=:deleted";

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			query.setLong("diagramObjectId", diagramObjectId);
			query.setString("deleted", "true");
			List<Role> roles = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("select getRolesByUserId " + roles);

			return roles;
		} catch (HibernateException ex) {
			log.error("[getRolesByUserId]",ex);
		} catch (Exception ex2) {
			log.error("[getRolesByUserId]",ex2);
		}
		return null;
	}
	
	public List<Role> getRolesByUserOrgAndDiagramObjectId(Long user_id, Long organisation_id, Long diagramObjectId) {
		try {
			String hql = "SELECT c FROM Role as c " +
					"WHERE c.assignee.user_id=:user_id " +
					"AND c.organisation.organisation_id=:organisation_id " +
					"AND c.roleObject.diagramObjectId = :diagramObjectId  " +
					"AND c.roleObject.deleted!=:deleted " +
					"AND c.deleted!=:deleted";

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			query.setLong("user_id", user_id);
			query.setLong("organisation_id",organisation_id);
			query.setLong("diagramObjectId", diagramObjectId);
			query.setString("deleted", "true");
			List<Role> roles = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("select getRolesByUserId " + roles);

			return roles;
		} catch (HibernateException ex) {
			log.error("[getRolesByUserId]",ex);
		} catch (Exception ex2) {
			log.error("[getRolesByUserId]",ex2);
		}
		return null;
	}
	
	public List<Role> getRolesByStartAndMax(int start, int max, String orderby, boolean asc, Long organisation_id){
		try {

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			
			Criteria crit = session.createCriteria(Role.class);
			
			crit.createCriteria("organisation")
				.add(Restrictions.eq("organisation_id", organisation_id));
			
			crit.add(Restrictions.ne("deleted", "true"));
			crit.setMaxResults(max);
			crit.setFirstResult(start);
			
			//You can order by Attributes of the Main-Class, to orderby Sub-Criteria you gonna have to
			//add a Restriction for them first
			if (asc) crit.addOrder(Order.asc(orderby));
			else crit.addOrder(Order.desc(orderby));
			
			List<Role> roles = crit.list();
			
			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("getRolesByStartAndMax " + roles);

			return roles;
		} catch (HibernateException ex) {
			log.error("[getRolesByStartAndMax]",ex);
		} catch (Exception ex2) {
			log.error("[getRolesByStartAndMax]",ex2);
		}
		return null;
	}

	public Long addRole(Long assignee_id, Long diagramObjectId, Long insertedBy, Long organisation_id) {
		try {
			
			Role role = new Role();
			role.setInserted(new Date());
			role.setInsertedBy(insertedBy);
			role.setAssignee(UserDaoImpl.getInstance().getUserById(assignee_id));
			role.setOrganisation(OrganisationDaoImpl.getInstance().getOrganisationById(organisation_id));
			role.setDeleted("false");
			role.setRoleObject(DiagramObjectDaoImpl.getInstance().getDiagramObjectById(diagramObjectId));
			
			return this.addRoleByObject(role);
			
		} catch (HibernateException ex) {
			log.error("[addRole]",ex);
		} catch (Exception ex2) {
			log.error("[addRole]",ex2);
		}
		return null;
	}

	public Long addRoleByObject(Role role) {
		try {
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			
			Long rolesId = (Long) session.save(role);
			
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return rolesId;
			
		} catch (HibernateException ex) {
			log.error("[addRoleByObject]",ex);
		} catch (Exception ex2) {
			log.error("[addRoleByObject]",ex2);
		}
		return null;
	}
	
	public void updateRole(Long rolesId, Long assignee_id, Long diagramObjectId, Long updatedBy) {
		try {
			
			Role role = this.getRoleById(rolesId);
			role.setUpdated(new Date());
			role.setUpdatedBy(updatedBy);
			role.setAssignee(UserDaoImpl.getInstance().getUserById(assignee_id));
			role.setDeleted("false");
			role.setRoleObject(DiagramObjectDaoImpl.getInstance().getDiagramObjectById(diagramObjectId));
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			
			session.update(role);
			
			tx.commit();
			HibernateUtil.closeSession(idf);
			
		} catch (HibernateException ex) {
			log.error("[updateRole]",ex);
		} catch (Exception ex2) {
			log.error("[updateRole]",ex2);
		}
	}
	
	public void deleteRole(Long rolesId, Long updatedBy) {
		try {
			
			Role role = this.getRoleById(rolesId);
			role.setUpdated(new Date());
			role.setUpdatedBy(updatedBy);
			role.setDeleted("true");
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			
			session.update(role);
			
			tx.commit();
			HibernateUtil.closeSession(idf);
			
		} catch (HibernateException ex) {
			log.error("[deleteRole]",ex);
		} catch (Exception ex2) {
			log.error("[deleteRole]",ex2);
		}
	}


	public Long selectMaxFromRoleObject(Long organisation_id) {
		try {
			String hql = "SELECT COUNT(*) as number FROM Role as c " +
						"WHERE c.organisation.organisation_id=:organisation_id " +
						"AND c.deleted!=:deleted";
			
			//get all users
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			query.setLong("organisation_id", organisation_id);
			query.setString("deleted", "true");
			
			List ll = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			log.info((Long)ll.get(0));
			
			return (Long) ll.get(0);				
		} catch (HibernateException ex) {
			log.error("[selectMaxFromRoleObject] "+ex);
		} catch (Exception ex2) {
			log.error("[selectMaxFromRoleObject] "+ex2);
		}
		return null;
	}
	
}
