package org.i4change.app.data.domain.daos;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.i4change.app.data.basic.AuthLevelmanagement;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.dto.organization.OrganisationListDTO;
import org.i4change.app.hibernate.beans.domain.Organisation;
import org.i4change.app.hibernate.beans.domain.Organisation_Users;
import org.i4change.app.hibernate.beans.user.Users;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 
 * @author swagner
 * 
 */
public class OrganisationDaoImpl extends HibernateDaoSupport {

	private static final Log log = LogFactory.getLog(OrganisationDaoImpl.class);

	// Spring loaded Beans
	private OrganisationsDiscountDaoImpl organisationsDiscountDaoImpl;
	private OrganisationUserDaoImpl organisationUserDaoImpl;
	private UserDaoImpl userDaoImpl;

	public UserDaoImpl getUserDaoImpl() {
		return userDaoImpl;
	}

	public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}

	public OrganisationsDiscountDaoImpl getOrganisationsDiscountDaoImpl() {
		return organisationsDiscountDaoImpl;
	}

	public void setOrganisationsDiscountDaoImpl(
			OrganisationsDiscountDaoImpl organisationsDiscountDaoImpl) {
		this.organisationsDiscountDaoImpl = organisationsDiscountDaoImpl;
	}

	public OrganisationUserDaoImpl getOrganisationUserDaoImpl() {
		return organisationUserDaoImpl;
	}

	public void setOrganisationUserDaoImpl(
			OrganisationUserDaoImpl organisationUserDaoImpl) {
		this.organisationUserDaoImpl = organisationUserDaoImpl;
	}

	/**
	 * adds a new organisation to the table organisation
	 * 
	 * @param titelname
	 * @param user_id
	 */
	public Long addOrganisation(String orgname, long user_id) {
		try {
			Organisation org = new Organisation();
			org.setName(orgname);
			org.setInsertedby(user_id);
			org.setDeleted("false");
			org.setStarttime(new Date());
			long id = (Long) getHibernateTemplate().save(org);
			return id;
		} catch (HibernateException ex) {
			log.error("[addOrganisation]", ex);
		} catch (Exception ex2) {
			log.error("[addOrganisation]", ex2);
		}
		return null;
	}

	public Boolean checkOrgForUniqueName(String orgname) {
		try {
			String hql = "select c from Organisation as c "
					+ "where lower(c.name) = lower(:name) "
					+ "AND deleted != :deleted";

			Query query = getSession().createQuery(hql);
			query.setString("name", orgname);
			query.setString("deleted", "true");
			List<Organisation> orgList = query.list();

			if (orgList.size() == 0) {
				return true;
			} else {
				return false;
			}

		} catch (HibernateException ex) {
			log.error("[checkOrgForUniqueName]", ex);
		} catch (Exception ex2) {
			log.error("[checkOrgForUniqueName]", ex2);
		}
		return null;
	}

	// Maybe re-use that code to send Reminder to Users
	// public void sendMailsToPendingOrgs(){
	// try {
	// List<Organisation> orgList =
	// this.getPendingOrganisationsForSendingMail();
	//
	// Date now = new Date();
	//
	// Long default_lang_id =
	// Long.valueOf(Configurationmanagement.getInstance().getConfKey(3,"default_lang_id").getConf_value()).longValue();
	//
	// LinkedList<LinkedHashMap<String,String>> sendedReminderOrgs = new
	// LinkedList<LinkedHashMap<String,String>>();
	//
	// for (Iterator<Organisation> iter = orgList.iterator();iter.hasNext();) {
	// Organisation org = iter.next();
	//
	// GregorianCalendar cal = new GregorianCalendar();
	//
	// if (org.getLastMailSend() == null) {
	// cal.setTime(org.getStarttime());
	// } else {
	// cal.setTime(org.getLastMailSend());
	// }
	// long dt = new
	// GregorianCalendar(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),0,0).getTimeInMillis();
	//
	// Long timeDifferenceInMilliSeconds = now.getTime() - dt;
	//
	// log.debug("timeDifferenceInMilliSeconds: "+timeDifferenceInMilliSeconds);
	// //daily send
	// //if (timeDifferenceInMilliSeconds >= 86400000) {
	//
	// if (timeDifferenceInMilliSeconds >= 86400000) {
	//
	// log.debug("Found Org to send reminder"+org.getOrganisation_id()+" Name: "+org.getName());
	// System.out.println("Found Org to send reminder"+org.getOrganisation_id()+" Name: "+org.getName());
	//
	// List<Users> modUsers =
	// OrganisationUserDaoImpl.getInstance().getModeratorsByOrganisationId(org.getOrganisation_id());
	//
	// for (Users mod : modUsers) {
	// log.debug("Send Mail to Moderator: "+mod.getUser_id()+" name: "+mod.getLogin());
	//
	// Adresses_Emails mail = null;
	// Iterator<Adresses_Emails> it = mod.getAdresses().getEmails().iterator();
	// if (it.hasNext()){
	// mail = it.next();
	// }
	//
	// String reminderUsers = mod.getLogin() + " (" + mod.getUser_id() + ") (" +
	// mail.getMail().getEmail() + ") ("+mod.getReceivePendingReminder()+")";
	//
	//
	// LinkedHashMap<String,String> sendedReminderOrg = new
	// LinkedHashMap<String,String>();
	// sendedReminderOrg.put("orgName", org.getName());
	// sendedReminderOrg.put("orgId", org.getOrganisation_id().toString());
	// sendedReminderOrg.put("orgCreationDate",
	// CalendarPatterns.getDateWithTimeByMiliSeconds(org.getStarttime()));
	// sendedReminderOrg.put("reminderUsers", reminderUsers);
	// sendedReminderOrgs.add(sendedReminderOrg);
	//
	// String receipent = mail.getMail().getEmail();
	//
	// log.debug("Send Mail to: "+receipent);
	//
	// if (mod.getReceivePendingReminder() == null ||
	// mod.getReceivePendingReminder()) {
	// Mailmanagement.getInstance().addMailToSpoolAboutPendingOrganization(mod.getUser_id(),
	// receipent,
	// org.getName(), org.getStarttime(), default_lang_id, mod.getUserHash());
	// }
	//
	// }
	//
	// }
	//
	// //Update Org to do not send twice
	// org.setLastMailSend(new Date());
	// OrganisationDaoImpl.getInstance().updateOrganisation(org);
	//
	// }
	//
	//
	// //send Report to Administrators:
	// List<Users> admins = UserDaoImpl.getInstance().getAdmins();
	//
	// for (Users admin : admins) {
	//
	// log.debug("Send Mail to Administrator: "+admin.getUser_id()+" name: "+admin.getLogin());
	//
	// Adresses_Emails mail = null;
	// Iterator<Adresses_Emails> it =
	// admin.getAdresses().getEmails().iterator();
	// if (it.hasNext()){
	// mail = it.next();
	// }
	//
	// Mailmanagement.getInstance().addMailToSpoolAboutReportOfPendingOrganizations(admin.getUser_id(),
	// mail.getMail().getEmail(), sendedReminderOrgs, default_lang_id);
	//
	// }
	//
	// } catch (Exception ex2) {
	// log.error("[sendMailsToPendingOrgs]" ,ex2);
	// }
	// }

	// public List<Organisation> getPendingOrganisationsForSendingMail() {
	// try {
	// String hql = "select c from Organisation as c " +
	// "where c.isPending = :isPending " +
	// "AND deleted != :deleted";
	//
	// Object idf = HibernateUtil.createSession();
	// Session session = HibernateUtil.getSession();
	// Transaction tx = session.beginTransaction();
	// Query query = session.createQuery(hql);
	// query.setBoolean("isPending", true);
	// query.setString("deleted", "true");
	// List<Organisation> orgList = query.list();
	// tx.commit();
	// HibernateUtil.closeSession(idf);
	//
	// return orgList;
	//
	// } catch (HibernateException ex) {
	// log.error("[getPendingOrganisationsForSendingMail]" ,ex);
	// } catch (Exception ex2) {
	// log.error("[getPendingOrganisationsForSendingMail]" ,ex2);
	// }
	// return null;
	// }

	/**
	 * adds a new organisation to the table organisation and orgPatternsMap
	 * 
	 * @param titelname
	 * @param user_id
	 */
	public Long addOrganisationWithMap(String orgname, long user_id,
			Vector orgPatternsMap) {
		try {

			XStream xStream = new XStream(new XppDriver());
			xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
			String xmlString = xStream.toXML(orgPatternsMap);

			Organisation org = new Organisation();
			org.setName(orgname);
			org.setInsertedby(user_id);
			org.setDeleted("false");
			org.setStarttime(new Date());
			org.setOrgPatterns(xmlString);
			org.setUuid(UUID.randomUUID().toString());

			long id = (Long) getHibernateTemplate().save(org);

			return id;
		} catch (HibernateException ex) {
			log.error("[addOrganisationWithMap]", ex);
		} catch (Exception ex2) {
			log.error("[addOrganisationWithMap]", ex2);
		}
		return null;
	}

	public Long addOrganisationByObject(Organisation org) {
		try {
			long id = (Long) getHibernateTemplate().save(org);
			return id;
		} catch (HibernateException ex) {
			log.error("[addOrganisation]", ex);
		} catch (Exception ex2) {
			log.error("[addOrganisation]", ex2);
		}
		return null;
	}

	/**
	 * 
	 * @param user_level
	 * @param start
	 * @param max
	 * @param orderby
	 * @return
	 */
	public SearchResult getOrganisations(int start, int max, String orderby,
			boolean asc) {
		try {
			SearchResult sresult = new SearchResult();
			sresult.setObjectName(Organisation.class.getName());
			sresult.setRecords(this.selectMaxFromOrganisations());

			List<Organisation> orgList = this.getOrganisationsList(start, max,
					orderby, asc);

			List<OrganisationListDTO> orgListDTO = new LinkedList<OrganisationListDTO>();
			for (int i = 0; i < orgList.size(); i++) {
				Organisation org = orgList.get(i);
				OrganisationListDTO organisationListDTO = new OrganisationListDTO();
				organisationListDTO.setName(org.getName());
				organisationListDTO
						.setOrganisation_id(org.getOrganisation_id());
				orgListDTO.add(organisationListDTO);
			}

			sresult.setResult(orgListDTO);

			return sresult;
		} catch (HibernateException ex) {
			log.error("[getOrganisations]", ex);
		} catch (Exception ex2) {
			log.error("[getOrganisations]", ex2);
		}
		return null;
	}

	/**
	 * @deprecated
	 * @param start
	 * @param max
	 * @param orderby
	 * @param asc
	 * @return
	 */
	@Deprecated
	public SearchResult getPendingOrganisations(int start, int max,
			String orderby, boolean asc) {
		try {
			SearchResult sresult = new SearchResult();
			sresult.setObjectName(Organisation.class.getName());
			// sresult.setRecords(this.selectMaxFromPendingOrganisations());
			// sresult.setResult(this.getPendingOrganisationsList(start, max,
			// orderby,asc));
			return sresult;
		} catch (HibernateException ex) {
			log.error("[getOrganisations]", ex);
		} catch (Exception ex2) {
			log.error("[getOrganisations]", ex2);
		}
		return null;
	}

	/**
	 * 
	 * @param user_level
	 * @return
	 */
	public List<Organisation> getOrganisationsList(int start, int max,
			String orderby, boolean asc) {
		try {
			Criteria crit = getSession().createCriteria(Organisation.class);
			crit.add(Restrictions.eq("deleted", "false"));
			crit.setFirstResult(start);
			crit.setMaxResults(max);
			if (asc)
				crit.addOrder(Order.asc(orderby));
			else
				crit.addOrder(Order.desc(orderby));
			List<Organisation> ll = crit.list();

			// XStream xStream = new XStream(new XppDriver());
			// xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
			//
			// for (Organisation org : ll) {
			// if (org.getOrgPatterns() != null && org.getOrgPatterns().length()
			// != 0) {
			// org.setOrgPatternsMap((Vector)xStream.fromXML(org.getOrgPatterns()));
			// }
			// }

			return ll;
		} catch (HibernateException ex) {
			log.error("[getOrganisations]", ex);
		} catch (Exception ex2) {
			log.error("[getOrganisations]", ex2);
		}
		return null;
	}

	// public List<Organisation> getPendingOrganisationsList(int start ,int max,
	// String orderby, boolean asc) {
	// try {
	// Object idf = HibernateUtil.createSession();
	// Session session = HibernateUtil.getSession();
	// Transaction tx = session.beginTransaction();
	// Criteria crit = session.createCriteria(Organisation.class);
	// crit.add(Restrictions.eq("deleted", "false"));
	// crit.add(Restrictions.eq("isPending", true));
	// crit.setFirstResult(start);
	// crit.setMaxResults(max);
	// if (asc) crit.addOrder(Order.asc(orderby));
	// else crit.addOrder(Order.desc(orderby));
	// List<Organisation> ll = crit.list();
	// tx.commit();
	// HibernateUtil.closeSession(idf);
	//
	// XStream xStream = new XStream(new XppDriver());
	// xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
	//
	// for (Organisation org : ll) {
	// if (org.getOrgPatterns() != null && org.getOrgPatterns().length() != 0) {
	// org.setOrgPatternsMap((Map)xStream.fromXML(org.getOrgPatterns()));
	// }
	// org.setUser(OrganisationUserDaoImpl.getInstance().getUsersByOrganisationId(org.getOrganisation_id()));
	// }
	//
	// return ll;
	// } catch (HibernateException ex) {
	// log.error("[getOrganisations]" ,ex);
	// } catch (Exception ex2) {
	// log.error("[getOrganisations]" ,ex2);
	// }
	// return null;
	// }

	public List<Organisation> getOrganisations() {
		try {
			// if (AuthLevelmanagement.checkAdminLevel(user_level)){
			// Criteria crit = getSession().createCriteria(Organisation.class);
			// List<Organisation> ll = crit.list();

			String hql = "select c from Organisation as c "
					+ "where c.deleted != :deleted " + "ORDER BY c.name ";

			Query query = getSession().createQuery(hql);
			query.setString("deleted", "true");

			List<Organisation> ll = query.list();

			XStream xStream = new XStream(new XppDriver());
			xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);

			for (Organisation org : ll) {
				if (org.getOrgPatterns() != null
						&& org.getOrgPatterns().length() != 0) {
					org.setOrgPatternsMap((Vector) xStream.fromXML(org
							.getOrgPatterns()));
				}
			}

			return ll;
			// } else {
			// log.error("[getOrganisations] no Permission");
			// }
		} catch (HibernateException ex) {
			log.error("[getOrganisations]", ex);
		} catch (Exception ex2) {
			log.error("[getOrganisations]", ex2);
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	private Long selectMaxFromOrganisations() {
		try {
			// get all users
			Query query = getSession()
					.createQuery(
							"select max(c.organisation_id) from Organisation c where c.deleted = 'false'");
			List ll = query.list();
			log.error(ll.get(0));
			return (Long) ll.get(0);
		} catch (HibernateException ex) {
			log.error("[selectMaxFromUsers] ", ex);
		} catch (Exception ex2) {
			log.error("[selectMaxFromUsers] ", ex2);
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	// private Long selectMaxFromPendingOrganisations(){
	// try {
	// String hql = "select max(c.organisation_id) " +
	// "from Organisation c where c.deleted = 'false'" +
	// "AND c.isPending = true";
	// //get all users
	// Object idf = HibernateUtil.createSession();
	// Session session = HibernateUtil.getSession();
	// Transaction tx = session.beginTransaction();
	// Query query = session.createQuery(hql);
	// List ll = query.list();
	// tx.commit();
	// HibernateUtil.closeSession(idf);
	// log.error((Long)ll.get(0));
	// return (Long)ll.get(0);
	// } catch (HibernateException ex) {
	// log.error("[selectMaxFromUsers] ",ex);
	// } catch (Exception ex2) {
	// log.error("[selectMaxFromUsers] ",ex2);
	// }
	// return null;
	// }

	/**
	 * updates an organisation if user_level is admin
	 * 
	 * @param user_level
	 * @param organisation_id
	 * @param orgname
	 * @param users_id
	 * @param users
	 * @return
	 */
	public Long updateOrganisation(long organisation_id, String orgname,
			long users_id, Vector orgPatternsMap) {
		try {

			XStream xStream = new XStream(new XppDriver());
			xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
			String xmlString = xStream.toXML(orgPatternsMap);

			Organisation org = this.getOrganisationById(organisation_id);
			org.setName(orgname);
			org.setUpdatedby(users_id);
			org.setUpdatetime(new Date());
			org.setOrgPatterns(xmlString);

			getHibernateTemplate().update(org);

			return org.getOrganisation_id();
		} catch (HibernateException hex) {
			log.error("updateOrganisation", hex);
		} catch (Exception err) {
			log.error("updateOrganisation", err);
		}
		return null;
	}

	public Long updateOrganisation(Organisation org) {
		try {

			getHibernateTemplate().update(org);

			return org.getOrganisation_id();
		} catch (HibernateException hex) {
			log.error("updateOrganisation", hex);
		} catch (Exception err) {
			log.error("updateOrganisation", err);
		}
		return null;
	}

	/**
	 * checks if a user is already stored
	 * 
	 * @param userIdToAdd
	 * @param usersStored
	 * @return
	 * @throws Exception
	 */
	private boolean checkUserAlreadyStored(Long userIdToAdd, List usersStored)
			throws Exception {
		for (Iterator it2 = usersStored.iterator(); it2.hasNext();) {
			Users us = (Users) it2.next();
			if (us.getUser_id().equals(userIdToAdd)) {
				log.error("userIdToAdd found: " + userIdToAdd);
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param user_id
	 * @param usersToStore
	 * @return
	 * @throws Exception
	 */
	private boolean checkUserShouldBeStored(Long user_id,
			LinkedHashMap usersToStore) throws Exception {
		for (Iterator it2 = usersToStore.keySet().iterator(); it2.hasNext();) {
			Integer key = (Integer) it2.next();
			Long usersIdToCheck = Long
					.valueOf(usersToStore.get(key).toString()).longValue();
			log.error("usersIdToCheck: " + usersIdToCheck);
			if (user_id.equals(usersIdToCheck)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * TODO
	 * 
	 * @param org
	 * @param users
	 * @return
	 */
	private Long updateOrganisationUsersByHashMap(Organisation org,
			LinkedHashMap users, long insertedby) {
		try {
			LinkedList<Long> usersToAdd = new LinkedList<Long>();
			LinkedList<Long> usersToDel = new LinkedList<Long>();

			List usersStored = this.organisationUserDaoImpl
					.getUsersByOrganisationId(org.getOrganisation_id());

			for (Iterator it = users.keySet().iterator(); it.hasNext();) {
				Integer key = (Integer) it.next();
				Long userIdToAdd = Long.valueOf(users.get(key).toString())
						.longValue();
				log.error("userIdToAdd: " + userIdToAdd);
				if (!this.checkUserAlreadyStored(userIdToAdd, usersStored))
					usersToAdd.add(userIdToAdd);
			}

			for (Iterator it = usersStored.iterator(); it.hasNext();) {
				Users us = (Users) it.next();
				Long userIdStored = us.getUser_id();
				log.error("userIdStored: " + userIdStored);
				if (!this.checkUserShouldBeStored(userIdStored, users))
					usersToDel.add(userIdStored);
			}

			log.error("usersToAdd.size " + usersToAdd.size());
			log.error("usersToDel.size " + usersToDel.size());

			for (Iterator<Long> it = usersToAdd.iterator(); it.hasNext();) {
				Long user_id = it.next();
				this.addUserToOrganisation(user_id, org.getOrganisation_id(),
						insertedby, "", false);
			}

			for (Iterator<Long> it = usersToDel.iterator(); it.hasNext();) {
				Long user_id = it.next();
				this.deleteUserFromOrganisation(new Long(3), user_id,
						org.getOrganisation_id());
			}

		} catch (Exception err) {
			log.error("updateOrganisationUsersByHashMap", err);
		}
		return null;
	}

	/**
	 * Gets an organisation by its id
	 * 
	 * @param organisation_id
	 * @return
	 */
	public Organisation getOrganisationById(long organisation_id) {
		try {
			String hql = "select c from Organisation as c "
					+ "where c.organisation_id = :organisation_id "
					+ "AND deleted != :deleted";

			Query query = getSession().createQuery(hql);
			query.setLong("organisation_id", organisation_id);
			query.setString("deleted", "true");
			Organisation org = (Organisation) query.uniqueResult();

			return org;
		} catch (HibernateException ex) {
			log.error("[getOrganisationById]", ex);
		} catch (Exception ex2) {
			log.error("[getOrganisationById]", ex2);
		}
		return null;
	}

	public Long deleteOrganisation(long user_level, long organisation_id,
			long updatedby) {
		try {
			if (AuthLevelmanagement.checkAdminLevel(user_level)) {
				return this.deleteOrganisation(organisation_id, updatedby);
			}
		} catch (HibernateException ex) {
			log.error("[deleteOrganisation]", ex);
		} catch (Exception ex2) {
			log.error("[deleteOrganisation]", ex2);
		}
		return null;
	}

	/**
	 * 
	 * 
	 * 
	 * @param organisation_id
	 * @param updatedby
	 * @return
	 */
	public Long deleteOrganisation(long organisation_id, long updatedby) {
		try {

			Organisation org = this.getOrganisationById(organisation_id);
			org.setDeleted("true");
			org.setUpdatedby(updatedby);

			getHibernateTemplate().update(org);

			return org.getOrganisation_id();

		} catch (HibernateException ex) {
			log.error("[deleteOrganisation]", ex);
		} catch (Exception ex2) {
			log.error("[deleteOrganisation]", ex2);
		}
		return null;
	}

	/**
	 * Adds a user to a given organisation-unit
	 * 
	 * @param user_id
	 * @param organisation_id
	 * @param insertedby
	 * @return
	 */
	public Long addUserToOrganisation(Long user_id, Long organisation_id,
			Long insertedby, String comment, Boolean isModerator) {
		try {
			if (this.getOrganisation_UserByUserAndOrganisation(user_id,
					organisation_id) == null) {

				// check for MaxUsers
				Organisation orga = this.getOrganisationById(organisation_id);
				// no need to do that
				// if (orga.getMaxUsers() != null) {
				//
				// Long currentUsers =
				// UserDaoImpl.getInstance().selectMaxFromUsersByOrganization(organisation_id);
				//
				// if (currentUsers >= orga.getMaxUsers()) {
				//
				// return new Long(-47);
				// }
				//
				// }

				Organisation org = this.getOrganisationById(organisation_id);
				// log.error("org: " + org.getName());
				Organisation_Users orgUser = new Organisation_Users();
				orgUser.setOrganisation(org);
				orgUser.setUser_id(user_id);
				orgUser.setDeleted("false");
				orgUser.setStarttime(new Date());
				orgUser.setComment(comment);
				orgUser.setIsModerator(isModerator);
				long id = (Long) getHibernateTemplate().save(orgUser);
				return id;

			} else {
				return new Long(-48);
			}
		} catch (HibernateException ex) {
			log.error("[addUserToOrganisation]", ex);
		} catch (Exception ex2) {
			log.error("[addUserToOrganisation]", ex2);
		}
		return null;
	}

	public Long addUserToOrganisationByModerator(Long user_id,
			Long organisation_id) {
		try {
			if (this.getOrganisation_UserByUserAndOrganisation(user_id,
					organisation_id) == null) {

				// check for MaxUsers
				Organisation orga = this.getOrganisationById(organisation_id);

				// no need to check for maxUser in a Organization
				// if (orga.getMaxUsers() != null) {
				//
				// Long currentUsers =
				// UserDaoImpl.getInstance().selectMaxFromUsersByOrganization(organisation_id);
				//
				// if (currentUsers >= orga.getMaxUsers()) {
				//
				// Long default_lang_id =
				// Long.valueOf(Configurationmanagement.getInstance().getConfKey(3,"default_lang_id").getConf_value()).longValue();
				//
				// //send Mail to Org.-Moderators and Sys-Administrators about
				// the failed auto-assign action
				// List<Users> orgModUsers =
				// OrganisationUserDaoImpl.getInstance().getModeratorsByOrganisationId(organisation_id);
				//
				// Users user = UserDaoImpl.getInstance().getUser(user_id);
				//
				// Adresses_Emails mail = null;
				// Iterator it = user.getAdresses().getEmails().iterator();
				// if (it.hasNext()){
				// mail = (Adresses_Emails) it.next();
				// }
				// String email = mail.getMail().getEmail();
				//
				// for (Users us : orgModUsers) {
				// Mailmanagement.getInstance().addMailToSpoolAboutFailedAutoAssign(user_id,
				// us.getUser_id(),
				// orga.getName(),
				// us.getFirstname()+" "+us.getLastname()+" ["+us.getLogin()+"]",
				// email, default_lang_id);
				// }
				//
				// List<Users> sysAdminUsers =
				// UserDaoImpl.getInstance().getAdmins();
				//
				// for (Users us : sysAdminUsers) {
				// Mailmanagement.getInstance().addMailToSpoolAboutFailedAutoAssignToSysAdmin(user_id,
				// us.getUser_id(),
				// orga.getName(),
				// us.getFirstname()+" "+us.getLastname()+" ["+us.getLogin()+"]",
				// email, default_lang_id);
				// }
				//
				// return new Long(-47);
				// }
				//
				// }

				Organisation org = this.getOrganisationById(organisation_id);
				log.error("org: " + org.getName());
				Organisation_Users orgUser = new Organisation_Users();
				orgUser.setOrganisation(org);
				orgUser.setUser_id(user_id);
				orgUser.setDeleted("false");
				orgUser.setStarttime(new Date());
				long id = (Long) getHibernateTemplate().save(orgUser);
				return id;
			} else {
				return new Long(-276);
			}
		} catch (HibernateException ex) {
			log.error("[addUserToOrganisationByImport]", ex);
		} catch (Exception ex2) {
			log.error("[addUserToOrganisationByImport]", ex2);
		}
		return null;
	}

	public Long addUserToOrganisationByImport(Long user_id, Long organisation_id) {
		try {
			if (this.getOrganisation_UserByUserAndOrganisation(user_id,
					organisation_id) == null) {

				Organisation org = this.getOrganisationById(organisation_id);
				log.error("org: " + org.getName());
				Organisation_Users orgUser = new Organisation_Users();
				orgUser.setOrganisation(org);
				orgUser.setUser_id(user_id);
				orgUser.setDeleted("false");
				orgUser.setStarttime(new Date());
				long id = (Long) getHibernateTemplate().save(orgUser);
				return id;
			} else {
				return new Long(-276);
			}
		} catch (HibernateException ex) {
			log.error("[addUserToOrganisationByImport]", ex);
		} catch (Exception ex2) {
			log.error("[addUserToOrganisationByImport]", ex2);
		}
		return null;
	}

	public Organisation_Users getOrganisation_UserByUserAndOrganisation(
			long user_id, long organisation_id) {
		try {

			log.error("getOrganisation_UserByUserAndOrganisation " + user_id
					+ "  " + organisation_id);

			Criteria crit = getSession().createCriteria(
					Organisation_Users.class);
			crit.add(Restrictions.eq("deleted", "false"));
			crit.add(Restrictions.eq("user_id", user_id));
			Criteria subCrit = crit.createCriteria("organisation");
			subCrit.add(Restrictions.eq("organisation_id", organisation_id));
			List ll = crit.list();
			log.error("getOrganisation_UserByUserAndOrganisation: " + ll.size());
			if (ll.size() > 0) {
				return (Organisation_Users) ll.get(0);
			}

		} catch (HibernateException ex) {
			log.error("[getOrganisation_UserByUserAndOrganisation]", ex);
		} catch (Exception ex2) {
			log.error("[getOrganisation_UserByUserAndOrganisation]", ex2);
		}
		return null;
	}

	public void updateOrganisationUser(Organisation_Users orgUser) {
		try {

			log.error("getOrganisation_UserByUserAndOrganisation " + orgUser);

			getHibernateTemplate().update(orgUser);

		} catch (HibernateException ex) {
			log.error("[updateOrganisationUser]", ex);
		} catch (Exception ex2) {
			log.error("[updateOrganisationUser]", ex2);
		}
	}

	public Long deleteUserFromOrganisation(Long user_level, long user_id,
			long organisation_id) {
		try {
			if (AuthLevelmanagement.checkAdminLevel(user_level)) {

				log.error("deleteUserFromOrganisation " + user_id + "  "
						+ organisation_id);

				Organisation_Users orgUser = this
						.getOrganisation_UserByUserAndOrganisation(user_id,
								organisation_id);
				log.error("org: " + orgUser.getOrganisation().getName());
				orgUser.setDeleted("true");
				orgUser.setUpdatetime(new Date());

				getHibernateTemplate().update(orgUser);
				return orgUser.getOrganisation_users_id();
			} else {
				log.error("[deleteUserFromOrganisation] authorization required");
			}
		} catch (HibernateException ex) {
			log.error("[deleteuserFromOrganisation]", ex);
		} catch (Exception ex2) {
			log.error("[deleteuserFromOrganisation]", ex2);
		}
		return null;
	}

	private boolean checkUserContainsOrganisation(long users_id,
			long organisation_id) {
		try {
			Users us = this.userDaoImpl.getUserById(users_id);
			for (Iterator it = us.getOrganisation_users().iterator(); it
					.hasNext();) {
				Organisation_Users orguser = (Organisation_Users) it.next();
				if (orguser.getOrganisation().getOrganisation_id() == organisation_id)
					return true;
			}
		} catch (HibernateException ex) {
			log.error("[checkUserContainsOrganisation]", ex);
		} catch (Exception ex2) {
			log.error("[checkUserContainsOrganisation]", ex2);
		}
		return false;
	}

	/**
	 * Filter all Organisations by user TODO: Add sorting
	 * 
	 * @param user_level
	 * @param users_id
	 * @param start
	 * @param max
	 * @param orderby
	 * @return
	 */
	public List<Organisation> getOrganisationsByUserId(long user_level,
			long user_id, int start, int max, String orderby, boolean asc) {
		try {
			if (AuthLevelmanagement.checkAdminLevel(user_level)) {
				Criteria crit = getSession().createCriteria(
						Organisation_Users.class);

				// TODO: I do not really remember that code, but it must be that
				// way
				// that you get only the IDs with this Projections Query
				ProjectionList projections = Projections.projectionList();
				projections.add(Projections
						.groupProperty("organisation.organisation_id"));
				crit.setProjection(projections);

				crit.add(Restrictions.eq("user_id", user_id));
				crit.setMaxResults(max);
				crit.setFirstResult(start);
				crit.add(Restrictions.ne("deleted", "true"));

				List userOrgIds = crit.list();

				LinkedList<Organisation> userOrg = new LinkedList<Organisation>();

				for (Iterator it = userOrgIds.iterator(); it.hasNext();) {
					Long org_id = (Long) it.next();
					userOrg.add(this.getOrganisationById(org_id));
				}

				return userOrg;
			}
		} catch (HibernateException ex) {
			log.error("[getOrganisationsByUserId]", ex);
		} catch (Exception ex2) {
			log.error("[getOrganisationsByUserId]", ex2);
		}
		return null;
	}

	public List<Organisation_Users> getOrganisationsByUserId(long user_id) {
		try {
			Criteria crit = getSession().createCriteria(
					Organisation_Users.class);
			crit.add(Restrictions.eq("user_id", user_id));
			crit.add(Restrictions.ne("deleted", "true"));
			List<Organisation_Users> userOrgIds = crit.list();
			return userOrgIds;
		} catch (HibernateException ex) {
			log.error("[getOrganisationsByUserId]", ex);
		} catch (Exception ex2) {
			log.error("[getOrganisationsByUserId]", ex2);
		}
		return null;
	}

	/**
	 * 
	 * @param user_level
	 * @param user_id
	 * @param start
	 * @param max
	 * @param orderby
	 * @param asc
	 * @return
	 */
	public List getRestOrganisationsByUserId(long user_level, long user_id,
			int start, int max, String orderby, boolean asc) {
		try {
			if (AuthLevelmanagement.checkAdminLevel(user_level)) {
				// get all organisations
				List allOrgs = this.getOrganisationsList(0, 1000000, orderby,
						asc);
				List orgUser = this.getOrganisationsByUserId(user_level,
						user_id, start, max, orderby, asc);

				List<Organisation> returnList = new LinkedList<Organisation>();
				boolean notInList = true;

				for (Iterator it = allOrgs.iterator(); it.hasNext();) {
					Organisation org = (Organisation) it.next();
					notInList = true;
					for (Iterator it2 = orgUser.iterator(); it2.hasNext();) {
						Organisation orgObj = (Organisation) it2.next();
						// log.error("orgObj ID: "+orgObj.getOrganisation_id());
						// log.error("orgUser ID: "+org.getOrganisation_id());
						if (orgObj.getOrganisation_id().equals(
								org.getOrganisation_id())) {
							notInList = false;
							// log.error("found notinList: "+notInList);
							break;
						}
					}
					// log.error("notinList: "+notInList);
					if (notInList)
						returnList.add(org);
				}

				return returnList;
			}
		} catch (HibernateException ex) {
			log.error("[getRestOrganisationsByUserId]", ex);
		} catch (Exception ex2) {
			log.error("[getRestOrganisationsByUserId]", ex2);
		}
		return null;
	}

	/**
	 * checks if the orgId is in the list of orgIds which have been send as
	 * organisations
	 * 
	 * @param orgId
	 * @param org
	 * @return
	 * @throws Exception
	 */
	private boolean checkOrgInList(Long orgId, Vector org) throws Exception {
		// log.error("checkOrgInList "+orgId);
		for (Iterator it = org.iterator(); it.hasNext();) {
			Object key = it.next();
			Long newOrgId = Long.valueOf(key.toString()).longValue();
			// log.error("[checkOrgInList 1]: newOrgId "+newOrgId);
			// log.error("[checkOrgInList 2]: org "+orgId);
			if (newOrgId.equals(orgId)) {
				// log.error("checkOrgInList 3 found");
				return true;
			}
		}
		return false;
	}

	/**
	 * checks if an orgId is already stored in the Users Organisations Object
	 * 
	 * @param orgId
	 * @param org
	 * @return
	 * @throws Exception
	 */
	private boolean checkOrgInStoredList(long orgId, List org) throws Exception {
		// log.error("checkOrgInStoredList "+orgId);
		for (Iterator it = org.iterator(); it.hasNext();) {
			Organisation_Users orgUsers = (Organisation_Users) it.next();
			// log.error("checkOrgInStoredList 2 "+orgUsers.getOrganisation().getOrganisation_id());
			if (orgUsers.getOrganisation().getOrganisation_id().equals(orgId)) {
				// log.error("checkOrgInStoredList 3 found");
				return true;
			}
		}
		return false;
	}

	/**
	 * updates users-organisations by a given params
	 * 
	 * @param us
	 * @param organisations
	 * @return
	 */
	public Long updateUserOrganisationsByUser(Users us, Vector organisations) {
		try {
			LinkedList<Long> orgIdsToAdd = new LinkedList<Long>();
			LinkedList<Long> orgIdsToDelete = new LinkedList<Long>();

			if (us.getOrganisation_users() == null) {
				us.setOrganisation_users(new LinkedList<Organisation_Users>());
			}

			for (Iterator it = organisations.iterator(); it.hasNext();) {
				Object key = it.next();
				Long orgIdToAdd = Long.valueOf(key.toString()).longValue();
				boolean isAlreadyStored = this.checkOrgInStoredList(orgIdToAdd,
						us.getOrganisation_users());
				if (!isAlreadyStored)
					orgIdsToAdd.add(orgIdToAdd);
			}

			for (Iterator it = us.getOrganisation_users().iterator(); it
					.hasNext();) {
				Organisation_Users orgUsers = (Organisation_Users) it.next();
				Long orgIdStored = orgUsers.getOrganisation()
						.getOrganisation_id();
				log.debug("updateUserOrganisationsByUser check1 : "
						+ orgIdStored);
				boolean shouldBeStored = this.checkOrgInList(orgIdStored,
						organisations);
				if (!shouldBeStored)
					orgIdsToDelete.add(orgIdStored);
			}

			log.debug("updateUserOrganisationsByUser size ADD: "
					+ orgIdsToAdd.size());
			for (Iterator it = orgIdsToAdd.iterator(); it.hasNext();) {
				Long orgToAdd = (Long) it.next();
				this.addUserToOrganisation(us.getUser_id(), orgToAdd,
						us.getUser_id(), "", false);
			}

			log.debug("updateUserOrganisationsByUser size DELETE: "
					+ orgIdsToDelete.size());
			for (Iterator it = orgIdsToDelete.iterator(); it.hasNext();) {
				Long orgToDel = (Long) it.next();
				this.deleteUserFromOrganisation(new Long(3), us.getUser_id(),
						orgToDel);
			}

		} catch (Exception err) {
			log.error("[updateUserOrganisationsByUser] ", err);
		}
		return null;
	}

	/**
	 * adds all send organisations to this User-Object (for newly
	 * registered/created Users)
	 * 
	 * @param us
	 * @param org
	 * @return
	 */
	public Long addUserOrganisationsByHashMap(long us, Vector org) {
		try {
			if (org != null) {
				for (Iterator it = org.iterator(); it.hasNext();) {
					Object key = it.next();
					Long newOrgId = Long.valueOf(key.toString()).longValue();
					this.addUserToOrganisation(us, newOrgId, new Long(1), "",
							false);
				}
			}
		} catch (Exception ex) {
			log.error("addUserOrganisationsByHashMap", ex);
		}
		return null;
	}

}
