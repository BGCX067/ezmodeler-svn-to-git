package org.i4change.app.data.domain.daos;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
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
import org.i4change.app.data.basic.AuthLevelmanagement;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.dto.user.OrganisationDTO;
import org.i4change.app.dto.user.OrganisationUserDTO;
import org.i4change.app.hibernate.beans.diagram.PropertyListItem;
import org.i4change.app.hibernate.beans.domain.Organisation_Users;
import org.i4change.app.hibernate.beans.user.Users;
//import org.i4change.app.hibernate.utils.HibernateUtil;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class OrganisationUserDaoImpl extends HibernateDaoSupport {

	private static final Log log = LogFactory.getLog(OrganisationUserDaoImpl.class);

	//Spring loaded beans
	private UserDaoImpl userDaoImpl;
	
	public UserDaoImpl getUserDaoImpl() {
		return userDaoImpl;
	}
	public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}
	
	public SearchResult getUsersByOrganisationId(long user_level, long organisation_id, int start, int max, String orderby, boolean asc){
		try {
			if (AuthLevelmanagement.checkAdminLevel(user_level)){ 
				SearchResult sResult = new SearchResult();
				sResult.setObjectName(Organisation_Users.class.getName());
				sResult.setRecords(this.selectMaxUsersByOrganisationId(organisation_id));
				sResult.setResult(this.getUsersByOrganisationId(organisation_id, start, max, orderby, asc));
				return sResult;
			} else {
				log.error("[getUsersByOrganisationId] authorization denied");
			}
		} catch (HibernateException ex) {
			log.error("[getUsersByOrganisationId]",ex);
		} catch (Exception ex2) {
			log.error("[getUsersByOrganisationId]",ex2);
		}
		return null;
	}
	
	private Long selectMaxUsersByOrganisationId(long organisation_id) {
		try {
//			Object idf = HibernateUtil.createSession();
//			Session session = HibernateUtil.getSession();
//			Transaction tx = session.beginTransaction();
			
			String hql = "select count(c.organisation_users_id) from Organisation_Users c " +
							"where c.deleted = 'false' " +
							"AND c.organisation.organisation_id = :organisation_id";
	
			Query query = getSession().createQuery(hql);
			query.setLong("organisation_id", organisation_id);
			
			List ll = query.list();
//			tx.commit();
//			HibernateUtil.closeSession(idf);
			log.error(ll.size());
			return (Long)ll.get(0);	
			
		} catch (HibernateException ex) {
			log.error("[getUsersByOrganisationId]",ex);
		} catch (Exception ex2) {
			log.error("[getUsersByOrganisationId]",ex2);
		}
		return null;
	}
	
	/**
	 * get a list of all users of an organisation
	 * @param user_level
	 * @param organisation_id
	 * @param start
	 * @param max
	 * @param orderby
	 * @param asc
	 * @return
	 */
	public List<Users> getUsersByOrganisationId(long organisation_id, int start, int max, String orderby, boolean asc){
		try {
				//get all users
//				Object idf = HibernateUtil.createSession();
//				Session session = HibernateUtil.getSession();
//				Transaction tx = session.beginTransaction();
				Criteria crit = getSession().createCriteria(Organisation_Users.class);
				Criteria subcrit = crit.createCriteria("organisation");
				subcrit.add(Restrictions.eq("organisation_id", organisation_id));
				crit.add(Restrictions.ne("deleted", "true"));
				crit.setMaxResults(max);
				crit.setFirstResult(start);
				List userOrg = crit.list();
//				tx.commit();
//				HibernateUtil.closeSession(idf);
				List<Users> userL = new LinkedList<Users>();
				for (Iterator it = userOrg.iterator();it.hasNext();){
					Organisation_Users us = (Organisation_Users) it.next();
					userL.add(this.userDaoImpl.getUserById(us.getUser_id()));
				}
				//Collections.sort(userL,new UsersFirstnameComperator());
				return userL;
		} catch (HibernateException ex) {
			log.error("[getUsersByOrganisationId]",ex);
		} catch (Exception ex2) {
			log.error("[getUsersByOrganisationId]",ex2);
		}
		return null;
	}
	
	public List<Organisation_Users> getOrganisationsByUsersId(long user_id){
		try {
			
			//get all users
//			Object idf = HibernateUtil.createSession();
//			Session session = HibernateUtil.getSession();
//			Transaction tx = session.beginTransaction();
//			Criteria crit = session.createCriteria(Organisation_Users.class);
//			crit.add(Restrictions.eq("user_id", users_id));
//			crit.add(Restrictions.ne("deleted", "true"));
//			
//			crit.addOrder(Order.asc("organisation.name"));
//			List<Organisation_Users> userOrg = crit.list();
//			tx.commit();
//			HibernateUtil.closeSession(idf);
			
			String hql = "SELECT c FROM Organisation_Users as c " +
							"WHERE c.user_id=:user_id " +
							"AND c.deleted!=:deleted " +
							"ORDER BY organisation.name ASC";
			
//			Object idf = HibernateUtil.createSession();
//			Session session = HibernateUtil.getSession();
//			Transaction tx = session.beginTransaction();
		
			Query query = getSession().createQuery(hql);
			query.setLong("user_id", user_id);
			query.setString("deleted", "true");
			List<Organisation_Users> userOrg = query.list();
		
//			tx.commit();
//			HibernateUtil.closeSession(idf);
			
			//Collections.sort(userL,new UsersFirstnameComperator());
			
			return userOrg;
		} catch (HibernateException ex) {
			log.error("[getUsersByOrganisationId]",ex);
		} catch (Exception ex2) {
			log.error("[getUsersByOrganisationId]",ex2);
		}
		return null;
	}
	
	public void updateUsersByOrganisationUser(Organisation_Users ou){
		try {
			
//			Object idf = HibernateUtil.createSession();
//			Session session = HibernateUtil.getSession();
//			Transaction tx = session.beginTransaction();
			getHibernateTemplate().update(ou);
//			tx.commit();
//			HibernateUtil.closeSession(idf);
			
		} catch (HibernateException ex) {
			log.error("[updateUsersByOrganisationId]",ex);
		} catch (Exception ex2) {
			log.error("[updateUsersByOrganisationId]",ex2);
		}
		
	}
	
	public List<Users> getUsersByOrganisationId(long organisation_id){
		try {
			
			String hql = "SELECT c FROM Users c, Organisation_Users ou " +
					"WHERE ((c.user_id = ou.user_id AND ou.deleted!=:deleted " +
					"AND ou.organisation.organisation_id=:organisation_id) " +
					"OR c.level_id > 2) " +
					"AND c.doNotShowInOrgSelection IS NULL " +
					"AND c.deleted!=:deleted " +
					"GROUP BY c.user_id ORDER BY c.level_id ASC";
			
//			Object idf = HibernateUtil.createSession();
//			Session session = HibernateUtil.getSession();
//			Transaction tx = session.beginTransaction();
			Query query = getSession().createQuery(hql);
			query.setLong("organisation_id", organisation_id);
			query.setString("deleted", "true");
			//query.setBoolean("doNotShowInOrgSelection", true);
			List<Users> users = query.list();
//			tx.commit();
//			HibernateUtil.closeSession(idf);
			
			//This is needed to show the Moderators in the list of possible Assignee's
			for (Users user : users) {
				user.setOrgUser(this.getModeratorByOrganisationAndUserId(organisation_id, user.getUser_id()));
			}
			
			return users;
			
		} catch (HibernateException ex) {
			log.error("[getUsersByOrganisationId]",ex);
		} catch (Exception ex2) {
			log.error("[getUsersByOrganisationId]",ex2);
		}
		
		return null;
		
	}
//	
//	public List<Users> getModeratorsByOrganisationId(long organisation_id){
//		try {
//			
//			String hql = "SELECT c FROM Users c, Organisation_Users ou " +
//					"WHERE ((c.user_id = ou.user_id AND ou.deleted!=:deleted " +
//					"AND ou.organisation.organisation_id=:organisation_id AND ou.isModerator = :isModerator) " +
//					"OR c.level_id > 2) " +
//					"AND c.deleted!=:deleted " +
//					"GROUP BY c.user_id";
//			
//			Object idf = HibernateUtil.createSession();
//			Session session = HibernateUtil.getSession();
//			Transaction tx = session.beginTransaction();
//			Query query = session.createQuery(hql);
//			query.setLong("organisation_id", organisation_id);
//			query.setBoolean("isModerator", true);
//			query.setString("deleted", "true");
//			List<Users> users = query.list();
//			tx.commit();
//			HibernateUtil.closeSession(idf);
//			
//			return users;
//			
//		} catch (HibernateException ex) {
//			log.error("[getUsersByOrganisationId]",ex);
//		} catch (Exception ex2) {
//			log.error("[getUsersByOrganisationId]",ex2);
//		}
//		
//		return null;
//		
//	}
	
	public Organisation_Users getModeratorByOrganisationAndUserId(long organisation_id, long user_id){
		try {
			
			String hql = "SELECT ou FROM Organisation_Users ou " +
					"WHERE ou.user_id = :user_id " +
					"AND ou.deleted!=:deleted " +
					"AND ou.organisation.organisation_id=:organisation_id " +
					"AND ou.isModerator=:isModerator " +
					"GROUP BY ou.organisation.organisation_id";
			
//			Object idf = HibernateUtil.createSession();
//			Session session = HibernateUtil.getSession();
//			Transaction tx = session.beginTransaction();
			Query query = getSession().createQuery(hql);
			query.setLong("organisation_id", organisation_id);
			query.setBoolean("isModerator", true);
			query.setLong("user_id", user_id);
			query.setString("deleted", "true");
			Organisation_Users orgUser = (Organisation_Users) query.uniqueResult();
//			tx.commit();
//			HibernateUtil.closeSession(idf);
			
			return orgUser;
			
		} catch (HibernateException ex) {
			log.error("[getModeratorByOrganisationAndUserId]",ex);
		} catch (Exception ex2) {
			log.error("[getModeratorByOrganisationAndUserId]",ex2);
		}
		
		return null;
		
	}
	

	public List<Users> getModeratorsByOrganisationId(long organisation_id){
		try {
			
			String hql = "SELECT c FROM Users c, Organisation_Users ou " +
					"WHERE c.user_id = ou.user_id " +
					"AND ou.deleted!=:deleted " +
					"AND ou.organisation.organisation_id=:organisation_id " +
					"AND ou.isModerator=:isModerator " +
					"AND c.deleted!=:deleted " +
					"GROUP BY c.user_id";
			
//			Object idf = HibernateUtil.createSession();
//			Session session = HibernateUtil.getSession();
//			Transaction tx = session.beginTransaction();
			Query query = getSession().createQuery(hql);
			query.setLong("organisation_id", organisation_id);
			query.setBoolean("isModerator", true);
			query.setString("deleted", "true");
			List<Users> users = query.list();
//			tx.commit();
//			HibernateUtil.closeSession(idf);
			
			return users;
			
		} catch (HibernateException ex) {
			log.error("[getModeratorsByOrganisationId]",ex);
		} catch (Exception ex2) {
			log.error("[getModeratorsByOrganisationId]",ex2);
		}
		
		return null;
		
	}
	
	
	public Organisation_Users checkUserInOrganisationId(long organisation_id, long user_id){
		try {
			
			String hql = "SELECT ou FROM Users c, Organisation_Users ou " +
					"WHERE (c.user_id = ou.user_id AND ou.deleted!=:deleted " +
					"AND ou.organisation.organisation_id=:organisation_id) " +
					"AND c.user_id = :user_id " +
					"AND c.deleted!=:deleted " +
					"AND ou.deleted!=:deleted " +
					"GROUP BY c.user_id";
			
//			Object idf = HibernateUtil.createSession();
//			Session session = HibernateUtil.getSession();
//			Transaction tx = session.beginTransaction();
			Query query = getSession().createQuery(hql);
			query.setLong("organisation_id", organisation_id);
			query.setLong("user_id", user_id);
			query.setString("deleted", "true");
			List<Organisation_Users> orgUsers = query.list();
//			tx.commit();
//			HibernateUtil.closeSession(idf);
			
			Organisation_Users orgUser = orgUsers.get(0);
			
			return orgUser;
			
		} catch (HibernateException ex) {
			log.error("[checkUserInOrganisationId]",ex);
		} catch (Exception ex2) {
			log.error("[checkUserInOrganisationId]",ex2);
		}
		
		return null;
		
	}
	
	/**
	 * 
	 * @param organisation_id
	 * @return
	 */
	public List<Users> _getUsersByOrganisationId(long organisation_id){
		try {
	
			//get all users
//			Object idf = HibernateUtil.createSession();
//			Session session = HibernateUtil.getSession();
//			Transaction tx = session.beginTransaction();
			Criteria crit = getSession().createCriteria(Organisation_Users.class);
			Criteria subcrit = crit.createCriteria("organisation");
			subcrit.add(Restrictions.eq("organisation_id", organisation_id));
			crit.add(Restrictions.ne("deleted", "true"));
			List<Organisation_Users> userOrg = crit.list();
//			tx.commit();
//			HibernateUtil.closeSession(idf);
			List<Users> userL = new LinkedList<Users>();
			for (Iterator<Organisation_Users> it = userOrg.iterator();it.hasNext();){
				Organisation_Users us = it.next();
				userL.add(this.userDaoImpl.getUserById(us.getUser_id()));
			}
			Collections.sort(userL,new UsersLoginComperator());
			return userL;
			
		} catch (HibernateException ex) {
			log.error("[getUsersByOrganisationId]",ex);
		} catch (Exception ex2) {
			log.error("[getUsersByOrganisationId]",ex2);
		}
		return null;
	}	
	
	class UsersLoginComperator implements Comparator<Users> {
		public int compare(Users o1, Users o2) {
			if(o1!=null || o2!=null)
			return 0;
			
			return o1.getLogin().compareTo(o2.getLogin()) ;
		}
	}

	public List<OrganisationUserDTO> getOrganisationsDTOByUsersId(Long user_id) throws Exception {
		List<Organisation_Users> orgUsers = this.getOrganisationsByUsersId(user_id);
		
		List<OrganisationUserDTO> orgUsersDTO = new LinkedList<OrganisationUserDTO>();
		
		for (int i=0;i<orgUsers.size();i++) {
			Organisation_Users orgUser = orgUsers.get(i);
			OrganisationUserDTO orgUserDTO = new OrganisationUserDTO();
			
			orgUserDTO.setOrganisation_users_id(orgUser.getOrganisation_users_id());
			
			orgUserDTO.setOrganisation(new OrganisationDTO());
			orgUserDTO.getOrganisation().setOrganisation_id(orgUser.getOrganisation().getOrganisation_id());
			orgUserDTO.getOrganisation().setName(orgUser.getOrganisation().getName());
			orgUserDTO.setIsModerator(orgUser.getIsModerator());
			
			orgUsersDTO.add(orgUserDTO);
		}
		
		return orgUsersDTO;
	}
		
}
