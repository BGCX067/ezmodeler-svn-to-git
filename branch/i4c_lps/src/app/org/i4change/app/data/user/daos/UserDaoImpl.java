package org.i4change.app.data.user.daos;

import java.util.Date;
import java.util.Iterator;
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
import org.i4change.app.data.basic.Configurationmanagement;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.data.domain.daos.OrganisationUserDaoImpl;
import org.i4change.app.data.user.Addressmanagement;
import org.i4change.app.hibernate.beans.domain.Organisation;
import org.i4change.app.hibernate.beans.user.Userdata;
import org.i4change.app.hibernate.beans.user.Userlevel;
import org.i4change.app.hibernate.beans.user.Users;
import org.i4change.app.hibernate.utils.HibernateUtil;
import org.i4change.app.utils.crypt.ManageCryptStyle;

public class UserDaoImpl {
	
	private static final Log log = LogFactory.getLog(UserDaoImpl.class);
	
	//Spring managed Beans
	private OrganisationUserDaoImpl organisationUserDaoImpl;
	
	public OrganisationUserDaoImpl getOrganisationUserDaoImpl() {
		return organisationUserDaoImpl;
	}
	public void setOrganisationUserDaoImpl(
			OrganisationUserDaoImpl organisationUserDaoImpl) {
		this.organisationUserDaoImpl = organisationUserDaoImpl;
	}

	private static UserDaoImpl instance = null;

	private UserDaoImpl() {
	}

	public static synchronized UserDaoImpl getInstance() {
		if (instance == null) {
			instance = new UserDaoImpl();
		}
		return instance;
	}
	

	/**
	 * query for a list of users
	 * @param users_id
	 * @param user_level
	 * @param start
	 * @param max
	 * @param orderby
	 * @return
	 */
	public SearchResult getUsersList(int start, int max, String orderby, boolean asc){
		try {
			
			SearchResult sresult = new SearchResult();
			sresult.setObjectName(Users.class.getName());
			sresult.setRecords(this.selectMaxFromUsers());
			//get all users
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Criteria crit = session.createCriteria(Users.class);
			crit.add(Restrictions.eq("deleted", "false"));
			if (asc) crit.addOrder(Order.asc(orderby));
			else crit.addOrder(Order.desc(orderby));
			crit.setMaxResults(max);
			crit.setFirstResult(start);
			sresult.setResult(crit.list());
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return sresult;				
		} catch (HibernateException ex) {
			log.error("[getUsersList] ",ex);
		} catch (Exception ex2) {
			log.error("[getUsersList] ",ex2);
		}
		return null;
	}
	
	public SearchResult getUsersListByOrganization(int start, int max, String orderby, boolean asc, Long organization_id){
		try {
		
			String hql = "SELECT c FROM Users c, Organisation_Users ou " +
					"WHERE c.deleted!=:deleted " +
					"AND ou.user_id = c.user_id " +
					"AND ou.organisation.organisation_id = :organisation_id " +
					"AND ou.deleted!=:deleted " +
					"GROUP BY c.user_id " +
					"ORDER BY "+orderby;
			if (asc) {
				hql +=" ASC";
			} else {
				hql +=" DESC";
			}
			
			SearchResult sresult = new SearchResult();
			sresult.setObjectName(Users.class.getName());
			sresult.setRecords(this.selectMaxFromUsersByOrganization(organization_id));
			
			//get all users of this Organization
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Query query = session.createQuery(hql);
			Transaction tx = session.beginTransaction();
			query.setString("deleted", "true");
			query.setLong("organisation_id", organization_id);
			query.setMaxResults(max);
			query.setFirstResult(start);
			sresult.setResult(query.list());
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return sresult;				
		} catch (HibernateException ex) {
			log.error("[getUsersListByOrganization] ",ex);
		} catch (Exception ex2) {
			log.error("[getUsersListByOrganization] ",ex2);
		}
		return null;
	}
	
	public List<Users> getAllUsers(){
		try {
			
			//get all users
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Criteria crit = session.createCriteria(Users.class);
			crit.add(Restrictions.eq("deleted", "false"));

			List<Users> ll = crit.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return ll;				

		} catch (HibernateException ex) {
			log.error("[getAllUsers] ",ex);
		} catch (Exception ex2) {
			log.error("[getAllUsers] ",ex2);
		}
		return null;
	}	
	

	public List<Users> getAllBackupUsers(){
		try {
			
			//get all users
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Criteria crit = session.createCriteria(Users.class);

			List<Users> ll = crit.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return ll;				

		} catch (HibernateException ex) {
			log.error("[getAllUsers] ",ex);
		} catch (Exception ex2) {
			log.error("[getAllUsers] ",ex2);
		}
		return null;
	}	
	
	/**
	 * returns the maximum
	 * @return
	 */
	public Long selectMaxFromUsers(){
		try {
			//get all users
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery("select count(c.user_id) from Users c where c.deleted = 'false'"); 
			List ll = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			log.info((Long)ll.get(0));
			return (Long)ll.get(0);				
		} catch (HibernateException ex) {
			log.error("[selectMaxFromUsers] ",ex);
		} catch (Exception ex2) {
			log.error("[selectMaxFromUsers] ",ex2);
		}
		return null;
	}
	
	public Long selectMaxFromUsersByOrganization(long organization_id){
		try {
			String hql = "SELECT COUNT(c.user_id) FROM Users c, Organisation_Users ou " +
							"WHERE c.deleted!=:deleted " +
							"AND ou.user_id = c.user_id " +
							"AND ou.organisation.organisation_id = :organisation_id " +
							"AND ou.deleted!=:deleted " +
							"GROUP BY c.user_id";
			//get all users
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setString("deleted", "true");
			query.setLong("organisation_id", organization_id);
			List ll = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
//			for (Iterator it = ll.iterator();it.hasNext();) {
//				log.debug("it: "+it.next());
//			}
			
			//TODO: For some reason I was un-able to fix, this does not return the number but each row only one time?!
			
			return Long.valueOf(""+ll.size()).longValue();				
		} catch (HibernateException ex) {
			log.error("[selectMaxFromUsersByOrganization] ",ex);
		} catch (Exception ex2) {
			log.error("[selectMaxFromUsersByOrganization] ",ex2);
		}
		return null;
	}
	

	/**
	 * 
	 * @param user_id
	 * @return
	 */
	public Users getUserById(Long user_id) {
		if (user_id > 0) {
			try {
				
				log.debug("getUserById: "+user_id);
				
				String hql = "select c from Users as c " +
						"where c.user_id = :user_id";
				
				Object idf = HibernateUtil.createSession();
				Session session = HibernateUtil.getSession();
				Transaction tx = session.beginTransaction();
				Query query = session.createQuery(hql);
				query.setLong("user_id", user_id);
				Users user = (Users) query.uniqueResult();
				session.refresh(user);
				tx.commit();
				HibernateUtil.closeSession(idf);
				
				user.setDiscounts(DiscountDaoImpl.getInstance().getDiscountsByUser(user.getUser_id()));
				
				if (this.organisationUserDaoImpl == null) {
					//Backport cause Spring Injection is not available for all Beans
					this.organisationUserDaoImpl = OrganisationUserDaoImpl.getInstance();
				}
				user.setOrganisation_users(this.organisationUserDaoImpl.getOrganisationsByUsersId(user.getUser_id()));
				
				return user;
				// TODO: Add Usergroups to user
				// users.setUsergroups(ResHandler.getGroupmanagement().getUserGroups(user_id));
			} catch (HibernateException ex) {
				log.error("[getUserById]",ex);
			} catch (Exception ex2) {
				log.error("[getUserById]",ex2);
			}
		} else {
			log.error("[getUser] "+"Error: No USER_ID given");
		}
		return null;
	}
	
	public Users getUserByUserHash(String userHash) {
		try {
			String hql = "select c from Users as c " +
					"where c.userHash = :userHash";
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setString("userHash", userHash);
			Users users = (Users) query.uniqueResult();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return users;
		} catch (HibernateException ex) {
			log.error(ex);
		} catch (Exception ex2) {
			log.error(ex2);
		}
		return null;
	}
	
	/**
	 * suche eines Bentzers
	 * @param user_level
	 * @param searchstring
	 * @param max
	 * @param start
	 * @return
	 */
	public List searchUser(long user_level, String searchcriteria, String searchstring, int max, int start, String orderby, boolean asc) {
		if (AuthLevelmanagement.getInstance().checkAdminLevel(user_level)) {
			try {
				Object idf = HibernateUtil.createSession();
				Session session = HibernateUtil.getSession();
				Transaction tx = session.beginTransaction();
				Criteria crit = session.createCriteria(Users.class);
				crit.add(Restrictions.ilike(searchcriteria, "%" + searchstring + "%"));
				if (asc) crit.addOrder(Order.asc(orderby));
				else crit.addOrder(Order.desc(orderby));
				crit.add(Restrictions.ne("deleted", "true"));
				crit.setMaxResults(max);
				crit.setFirstResult(start);
				List contactsZ = crit.list();
				tx.commit();
				HibernateUtil.closeSession(idf);
				return contactsZ;
			} catch (HibernateException ex) {
				log.error(ex);
			} catch (Exception ex2) {
				log.error(ex2);
			}
		}
		return null;
	}
	
	public List<Users> lookUpUser(String searchstring, int max, int start, String orderby, boolean asc) {
		try {
			//
			String hql = "SELECT c from Users c, Adresses_Emails adr_e " +
					"WHERE c.deleted != :deleted " +
					"AND " +
					"(" +
						"(" +
							"lower(c.login) LIKE lower(:search) " +
							"OR lower(c.firstname) LIKE lower(:search) " +
							"OR lower(c.lastname) LIKE lower(:search)" +
						") " +
						"OR " +
						"(" +
							"c.adresses.adresses_id = adr_e.adresses_id " +
							"AND lower(adr_e.mail.email) LIKE lower(:search)" +
						") " +
					") " +
					"GROUP BY c.user_id " +
					"ORDER BY "+ orderby +" ";
			
			if (asc) {
				hql += "ASC";
			} else {
				hql += "DESC";
			}
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setString("deleted", "true");
			query.setString("search", "%"+searchstring+"%");
			query.setMaxResults(max);
			query.setFirstResult(start);
			List<Users> contactsZ = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			return contactsZ;
		} catch (HibernateException ex) {
			log.error("lookUpUser",ex);
		} catch (Exception ex2) {
			log.error("lookUpUser",ex2);
		}
		return null;
	}
	
	public Long lookUpUserMax(String searchstring, int max, int start, boolean asc) {
		try {
			//
			String hql = "SELECT COUNT(c) from Users c, Adresses_Emails adr_e " +
					"WHERE c.deleted != :deleted " +
					"AND " +
					"(" +
						"(" +
							"lower(c.login) LIKE lower(:search) " +
							"OR lower(c.firstname) LIKE lower(:search) " +
							"OR lower(c.lastname) LIKE lower(:search)" +
						") " +
						"OR " +
						"(" +
							"c.adresses.adresses_id = adr_e.adresses_id " +
							"AND lower(adr_e.mail.email) LIKE lower(:search)" +
						") " +
					") " +
					"GROUP BY c.user_id ";
			
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setString("deleted", "true");
			query.setString("search", "%"+searchstring+"%");
			query.setMaxResults(max);
			query.setFirstResult(start);
			List ll = query.list();
			
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			log.info((Long)ll.get(0));
			return (Long)ll.get(0);	
			
		} catch (HibernateException ex) {
			log.error("lookUpUserMax",ex);
		} catch (Exception ex2) {
			log.error("lookUpUserMax",ex2);
		}
		return null;
	}

	public List getUserdataDashBoard(Long user_id) {
		if (user_id.longValue() > 0) {
			try {
				Object idf = HibernateUtil.createSession();
				Session session = HibernateUtil.getSession();
				Transaction tx = session.beginTransaction();
				Query query = session.createQuery("select c from Userdata as c where c.user_id = :user_id AND deleted != :deleted");
				query.setLong("user_id", user_id.longValue());
				query.setString("deleted", "true");
				List ll = query.list();
				tx.commit();
				HibernateUtil.closeSession(idf);
				return ll;
			} catch (HibernateException ex) {
				log.error(ex);
			} catch (Exception ex2) {
				log.error(ex2);
			}
		}
		return null;
	}

	public int getUserdataNoByKey(Long USER_ID, String DATA_KEY) {
		int userdata = 0;
		if (USER_ID.longValue() > 0) {
			try {
				Object idf = HibernateUtil.createSession();
				Session session = HibernateUtil.getSession();
				Transaction tx = session.beginTransaction();
				Query query = session.createQuery("select c from Userdata as c where c.user_id = :user_id AND c.data_key = :data_key AND deleted != :deleted");
				query.setLong("user_id", USER_ID.longValue());
				query.setString("data_key", DATA_KEY);
				query.setString("deleted", "true");
				userdata = query.list().size();
				tx.commit();
				HibernateUtil.closeSession(idf);
			} catch (HibernateException ex) {
				log.error(ex);
			} catch (Exception ex2) {
				log.error(ex2);
			}
		} else {
			System.out.println("Error: No USER_ID given");
		}
		return userdata;
	}

	public Userdata getUserdataByKey(Long user_id, String DATA_KEY) {
		Userdata userdata = new Userdata();
		if (user_id.longValue() > 0) {
			try {
				Object idf = HibernateUtil.createSession();
				Session session = HibernateUtil.getSession();
				Transaction tx = session.beginTransaction();
				Query query = session.createQuery("select c from Userdata as c where c.user_id = :user_id AND c.data_key = :data_key AND deleted != :deleted");
				query.setLong("user_id", user_id.longValue());
				query.setString("data_key", DATA_KEY);
				query.setString("deleted", "true");
				for (Iterator it2 = query.iterate(); it2.hasNext();) {
					userdata = (Userdata) it2.next();
				}
				tx.commit();
				HibernateUtil.closeSession(idf);
			} catch (HibernateException ex) {
				log.error(ex);
			} catch (Exception ex2) {
				log.error(ex2);
			}
		} else {
			userdata.setComment("Error: No USER_ID given");
		}
		return userdata;
	}
	
	/**
	 * @author swagner This Methdo adds a User to the User-Table
	 * @param level_id
	 *            The User Level, 1=User, 2=GroupAdmin/Moderator,
	 *            3=SystemAdmin/Admin
	 * @param availible
	 *            The user is activated
	 * @param status
	 *            The user is not blocked by System admins
	 * @param firstname
	 * @param login
	 *            Username for login
	 * @param lastname
	 * @param language_id
	 * @param Userpass
	 *            is MD5-crypted
	 * @param adress_id
	 * @return user_id or error null
	 */
	public Long addUser(long level_id, int availible, int status,
			String firstname, String login, String lastname, long language_id,
			String userpass, long adress_id, Date age, Boolean isPending, 
			Date expireDate, Long maxWorkDays, 
			Boolean useDefaultDiscounts, Boolean unlimitedLicenses, 
			Long licenseUserPayed, Long licenseUserUsed, Float pricePerUser) {
		try {
			Users users = new Users();
			users.setFirstname(firstname);
			users.setLogin(login);
			users.setLastname(lastname);
			users.setAge(age);
			users.setAdresses(Addressmanagement.getInstance().getAdressbyId(adress_id));
			users.setAvailible(availible);
			users.setLastlogin(new Date());
			users.setLasttrans(new Long(0));
			users.setLevel_id(level_id);
			users.setStatus(status);
			users.setTitle_id(new Integer(1));
			users.setStarttime(new Date());
			users.setIsPending(isPending);
			users.setExpireDate(expireDate);
			users.setMaxWorkDays(maxWorkDays);
			users.setUseDefaultDiscounts(useDefaultDiscounts);
			users.setUnlimitedLicenses(unlimitedLicenses);

			users.setLicenseUserPayed(licenseUserPayed);
			users.setLicenseUserUsed(licenseUserUsed);
			users.setPricePerUser(pricePerUser);
			
			// this is needed cause the language is not a needed data at
			// registering
			if (language_id != 0) {
				users.setLanguage_id(new Long(language_id));
			} else {
				users.setLanguage_id(null);
			}
			users.setPassword(ManageCryptStyle.getInstance().getInstanceOfCrypt().createPassPhrase(userpass));
			users.setRegdate(new Date());
			users.setDeleted("false");
			String cryptHash = " "+ new Date() + "random";
			users.setUserHash(ManageCryptStyle.getInstance().getInstanceOfCrypt().createPassPhrase(cryptHash));

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			long user_id = (Long) session.save(users);
			tx.commit();
			HibernateUtil.closeSession(idf);

			return user_id;

		} catch (HibernateException ex) {
			log.error("[registerUser]" ,ex);
		} catch (Exception ex2) {
			log.error("[registerUser]" ,ex2);
		}
		return null;
	}
	
	public Long addUserNonActivated(long level_id, int availible, int status,
			String firstname, String login, String lastname, long language_id,
			String userpass, long adress_id, Date age, String hash, String xmlStringRegObjectObj) {
		try {
			
        	Integer expireDateMonthsMax = Integer.valueOf(Configurationmanagement.getInstance().getConfKey(3, "expireDateMonthsMax").getConf_value()).intValue();
        	Long timeToExpire = new Long(expireDateMonthsMax) * 30 * 86400000;
        	Date currentDate = new Date();
        	Date expireDate = new Date(currentDate.getTime()+timeToExpire);
        	
        	Long maxWorkDays = Long.valueOf(Configurationmanagement.getInstance().getConfKey(3, "maxWorkDays").getConf_value()).longValue();
			
			Users users = new Users();
			
			users.setExpireDate(expireDate);
			users.setMaxWorkDays(maxWorkDays);
			users.setIsPending(true);
			
			users.setFirstname(firstname);
			users.setLogin(login);
			users.setLastname(lastname);
			users.setAge(age);
			users.setAdresses(Addressmanagement.getInstance().getAdressbyId(adress_id));
			users.setAvailible(availible);
			users.setLastlogin(new Date());
			users.setLasttrans(new Long(0));
			users.setLevel_id(level_id);
			users.setStatus(status);
			users.setTitle_id(new Integer(1));
			users.setStarttime(new Date());
			users.setActivatehash(hash);
			users.setXmlStringRegObjectObj(xmlStringRegObjectObj);
			
			users.setUnlimitedLicenses(false);
			users.setUseDefaultDiscounts(true);
			
			String cryptHash = " "+ new Date() + "random";
			users.setUserHash(ManageCryptStyle.getInstance().getInstanceOfCrypt().createPassPhrase(cryptHash));
			// this is needed cause the language is not a needed data at
			// registering
			if (language_id != 0) {
				users.setLanguage_id(new Long(language_id));
			} else {
				users.setLanguage_id(null);
			}
			users.setPassword(ManageCryptStyle.getInstance().getInstanceOfCrypt().createPassPhrase(userpass));
			users.setRegdate(new Date());
			users.setDeleted("false");

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			long user_id = (Long) session.save(users);
			tx.commit();
			HibernateUtil.closeSession(idf);

			return user_id;

		} catch (HibernateException ex) {
			log.error("[registerUser]" ,ex);
		} catch (Exception ex2) {
			log.error("[registerUser]" ,ex2);
		}
		return new Long(-1);
	}
	
	public Long updateUserByProfile(Long user_id, 
			String firstname, String login, String lastname, Integer title_id,
			String userpass, long adress_id, Date age, String xmlStringRegObjectObj,
			Long company_adress_id) {
		try {
			
			Users users = this.getUserById(user_id);
			if (users == null) {
				return new Long(-1);
			}
			users.setFirstname(firstname);
			users.setLogin(login);
			users.setTitle_id(title_id);
			users.setLastname(lastname);
			users.setAge(age);
			users.setPassword(userpass);
			users.setAdresses(Addressmanagement.getInstance().getAdressbyId(adress_id));
			if (company_adress_id != null) {
				users.setCompanyAddress(Addressmanagement.getInstance().getAdressbyId(company_adress_id));
			} else {
				users.setCompanyAddress(null);
			}
			users.setTitle_id(new Integer(1));
			users.setUpdatetime(new Date());
			users.setXmlStringRegObjectObj(xmlStringRegObjectObj);
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			session.update(users);
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return user_id;
			
		} catch (HibernateException ex) {
			log.error("[updateUserByProfile]" ,ex);
		} catch (Exception ex2) {
			log.error("[updateUserByProfile]" ,ex2);
		}
		return new Long(-1);
	}

	public Long addUser(Users usr) {
		try {
			String cryptHash = " "+ new Date() + "random";
			usr.setUserHash(ManageCryptStyle.getInstance().getInstanceOfCrypt().createPassPhrase(cryptHash));
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Long user_id = (Long) session.save(usr);
			tx.commit();
			HibernateUtil.closeSession(idf);
			return user_id;
		} catch (HibernateException ex) {
			log.error("[registerUser]" ,ex);
		} catch (Exception ex2) {
			log.error("[registerUser]" ,ex2);
		}
		return null;
	}
	
	/**
	 * check for duplicates
	 * @param DataValue
	 * @return
	 */
	public boolean checkUserLogin(String DataValue) {
		try {
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery("select c from Users as c where lower(c.login) = lower(:DataValue) AND deleted != :deleted");
			query.setString("DataValue", DataValue);
			query.setString("deleted", "true");
			int count = query.list().size();

			tx.commit();
			HibernateUtil.closeSession(idf);
			if (count != 0) {
				return false;
			}			
		} catch (HibernateException ex) {
			log.error("[checkUserData]" ,ex);
		} catch (Exception ex2) {
			log.error("[checkUserData]" ,ex2);
		}
		return true;
	}

	public void addUserLevel(String description, int myStatus) {
		try {
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Userlevel uslevel = new Userlevel();
			uslevel.setStarttime(new Date());
			uslevel.setDescription(description);
			uslevel.setStatuscode(new Integer(myStatus));
			uslevel.setDeleted("false");
			session.save(uslevel);
			tx.commit();
			HibernateUtil.closeSession(idf);
		} catch (HibernateException ex) {
			log.error("[addUserLevel]" ,ex);
		} catch (Exception ex2) {
			log.error("[addUserLevel]" ,ex2);
		}
	}
	

	public Users getUserByName(String login) {
		try {
			String hql = "SELECT u FROM Users as u " +
					" where u.login = :login" +
					" AND deleted != :deleted";
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setString("login", login);
			query.setString("deleted", "true");
			Users us = (Users) query.uniqueResult();
			tx.commit();
			HibernateUtil.closeSession(idf);
			return us;			
		} catch (Exception e) {
			log.error("[getUserByName]",e);
		}
		return null;
	}
	
	public List<Users> getUsersByLogin(String login) {
		try {
			String hql = "SELECT u FROM Users as u " +
					" where u.login = :login" +
					" AND deleted != :deleted";
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setString("login", login);
			query.setString("deleted", "true");
			List<Users> us = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			return us;			
		} catch (Exception e) {
			log.error("[getUsersByLogin]",e);
		}
		return null;
	}
	
	public List<Users> getAdmins() {
		try {
			String hql = "SELECT u FROM Users as u " +
					" where u.level_id > 2" +
					" AND deleted != :deleted";
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setString("deleted", "true");
			List<Users> us = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			return us;			
		} catch (Exception e) {
			log.error("[getUserByAdressesId]",e);
		}
		return null;
	}
	
	public Users getUserByAdressesId(Long adresses_id) {
		try {
			String hql = "SELECT u FROM Users as u " +
					" where u.adresses.adresses_id = :adresses_id" +
					" AND deleted != :deleted";
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("adresses_id", adresses_id);
			query.setString("deleted", "true");
			Users us = (Users) query.uniqueResult();
			tx.commit();
			HibernateUtil.closeSession(idf);
			return us;			
		} catch (Exception e) {
			log.error("[getUserByAdressesId]",e);
		}
		return null;
	}
	
	public Object getUserByHash (String hash) {
		try {
			if (hash.length()==0) return new Long(-5);
			String hql = "SELECT u FROM Users as u " +
					" where u.resethash = :resethash" +
					" AND deleted != :deleted";
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setString("resethash", hash);
			query.setString("deleted", "true");
			Users us = (Users) query.uniqueResult();
			tx.commit();
			HibernateUtil.closeSession(idf);
			if (us!=null) {
				return us;		
			} else {
				return new Long(-5);
			}
		} catch (Exception e) {
			log.error("[getUserByHash]",e);
		}
		return new Long(-1);
	}
	

	public Users getUserByActivationHash (String activatehash) {
		try {
			String hql = "SELECT u FROM Users as u " +
					" where u.activatehash = :activatehash" +
					" AND deleted != :deleted";
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setString("activatehash", activatehash);
			query.setString("deleted", "true");
			Users us = (Users) query.uniqueResult();
			tx.commit();
			HibernateUtil.closeSession(idf);
			return us;
		} catch (Exception e) {
			log.error("[getUserByActivationHash]",e);
		}
		return null;
	}
	
	public Object resetPassByHash (String hash, String pass) {
		try {
			Object u = this.getUserByHash(hash);
			if (u instanceof Users) {
				Users us = (Users) u;
				us.setPassword(ManageCryptStyle.getInstance().getInstanceOfCrypt().createPassPhrase(pass));
				us.setResethash("");
				this.updateUser(us);
				return new Long(-8);
			} else {
				return u;
			}
		} catch (Exception e) {
			log.error("[resetPassByHash]",e);
		}
		return new Long(-1);
	}
	
	public void updateUser(Users user) {
		if (user.getUser_id() > 0) {
			try {
				Object idf = HibernateUtil.createSession();
				Session session = HibernateUtil.getSession();
				Transaction tx = session.beginTransaction();
				session.update(user);
				tx.commit();
				HibernateUtil.closeSession(idf);
			} catch (HibernateException ex) {
				log.error("[updateUser] ",ex);
			} catch (Exception ex2) {
				log.error("[updateUser] ",ex2);
			}
		} else {
			log.error("[updateUser] "+"Error: No USER_ID given");
		}
	}

	public String updateUserdata(int DATA_ID, long USER_ID, String DATA_KEY,
			String DATA, String Comment) {
		String res = "Fehler beim Update";
		try {
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			String hqlUpdate = "update userdata set DATA_KEY= :DATA_KEY, USER_ID = :USER_ID, DATA = :DATA, updatetime = :updatetime, comment = :Comment where DATA_ID= :DATA_ID";
			int updatedEntities = session.createQuery(hqlUpdate).setString(
					"DATA_KEY", DATA_KEY).setLong("USER_ID", USER_ID)
					.setString("DATA", DATA).setLong("updatetime",new Long(-1))
					.setString("Comment", Comment).setInteger("DATA_ID",
							DATA_ID).executeUpdate();
			res = "Success" + updatedEntities;
			tx.commit();
			HibernateUtil.closeSession(idf);
		} catch (HibernateException ex) {
			log.error(ex);
		} catch (Exception ex2) {
			log.error(ex2);
		}
		return res;
	}

	public String updateUserdataByKey(Long USER_ID, String DATA_KEY,
			String DATA, String Comment) {
		String res = "Fehler beim Update";
		try {
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			String hqlUpdate = "update Userdata set data = :data, updatetime = :updatetime, "
					+ "comment = :comment where user_id= :user_id AND data_key = :data_key";
			int updatedEntities = session.createQuery(hqlUpdate).setString(
					"data", DATA).setLong("updatetime",
					new Long(-1)).setString(
					"comment", Comment).setLong("user_id", USER_ID.longValue())
					.setString("data_key", DATA_KEY).executeUpdate();
			res = "Success" + updatedEntities;
			tx.commit();
			HibernateUtil.closeSession(idf);
		} catch (HibernateException ex) {
			log.error(ex);
		} catch (Exception ex2) {
			log.error(ex2);
		}
		return res;
	}

	public String deleteUserdata(int DATA_ID) {
		String res = "Fehler beim deleteUserdata";
		try {
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			String hqlUpdate = "delete userdata where DATA_ID= :DATA_ID";
			int updatedEntities = session.createQuery(hqlUpdate).setInteger("DATA_ID", DATA_ID).executeUpdate();
			res = "Success" + updatedEntities;
			tx.commit();
			HibernateUtil.closeSession(idf);
		} catch (HibernateException ex) {
			log.error(ex);
		} catch (Exception ex2) {
			log.error(ex2);
		}
		return res;
	}

	public String deleteUserdataByUserAndKey(int users_id, String DATA_KEY) {
		String res = "Fehler beim deleteUserdataByUserAndKey";
		try {
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			String hqlUpdate = "delete userdata where users_id= :users_id AND DATA_KEY = :DATA_KEY";
			int updatedEntities = session.createQuery(hqlUpdate).setInteger(
					"users_id", users_id).setString("DATA_KEY", DATA_KEY)
					.executeUpdate();
			res = "Success" + updatedEntities;
			tx.commit();
			HibernateUtil.closeSession(idf);
		} catch (HibernateException ex) {
			log.error(ex);
		} catch (Exception ex2) {
			log.error(ex2);
		}
		return res;
	}

	public String addUserdata(long USER_ID, String DATA_KEY, String DATA,
			String Comment) {
		String ret = "Fehler beim speichern der Userdata";
		Userdata userdata = new Userdata();
		userdata.setData_key(DATA_KEY);
		userdata.setData(DATA);
		userdata.setStarttime(new Date());
		userdata.setUpdatetime(null);
		userdata.setComment(Comment);
		userdata.setUser_id(new Long(USER_ID));
		userdata.setDeleted("false");
		try {
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			session.save(userdata);
			session.flush();
			session.clear();
			session.refresh(userdata);
			tx.commit();
			HibernateUtil.closeSession(idf);
			ret = "success";
		} catch (HibernateException ex) {
			log.error(ex);
		} catch (Exception ex2) {
			log.error(ex2);
		}
		return ret;
	}

	public Long deleteUserID(long USER_ID) {
		try {
			if (USER_ID != 0) {
				Users us = this.getUserById(USER_ID);
				us.setDeleted("true");
				us.setUpdatetime(new Date());
				// result +=
				// Groupmanagement.getInstance().deleteUserFromAllGroups(new
				// Long(USER_ID));

				Object idf = HibernateUtil.createSession();
				Session session = HibernateUtil.getSession();
				Transaction tx = session.beginTransaction();
				session.update(us);
				tx.commit();
				HibernateUtil.closeSession(idf);
				return us.getUser_id();
				// result +=
				// ResHandler.getBestellmanagement().deleteWarenkorbByUserID(USER_ID);
				// result +=
				// ResHandler.getEmailmanagement().deleteEMailByUserID(USER_ID);
				// result +=
				// ResHandler.getContactmanagement().deleteContactUsergroups(USER_ID);
				// result +=
				// ResHandler.getContactmanagement().deleteUserContact(USER_ID);

			}
		} catch (HibernateException ex) {
			log.error("[deleteUserID]" ,ex);
		} catch (Exception ex2) {
			log.error("[deleteUserID]" ,ex2);
		}
		return null;
	}

	public Userlevel getUserLevel(Long level_id) {
		Userlevel userlevel = new Userlevel();
		try {
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery("select c from Userlevel as c where c.level_id = :level_id AND deleted != :deleted");
			query.setLong("level_id", level_id.longValue());
			query.setString("deleted", "true");
			for (Iterator it2 = query.iterate(); it2.hasNext();) {
				userlevel = (Userlevel) it2.next();
			}
			tx.commit();
			HibernateUtil.closeSession(idf);
		} catch (HibernateException ex) {
			log.error("[getUserLevel]" ,ex);
		} catch (Exception ex2) {
			log.error("[getUserLevel]" ,ex2);
		}
		return userlevel;
	}

	/**
	 * get user-role
	 * 1 - user
	 * 2 - moderator
	 * 3 - admin
	 * @param user_id
	 * @return
	 */
	public Long getUserLevelByID(Long user_id) {
		
		try {
			if (user_id==null) return new Long(0);
			//For direct access of linked users
			if (user_id==-1){
				return new Long(1);
			}
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			
			Query query = session.createQuery("select c from Users as c where c.user_id = :user_id AND deleted <> 'true'");
			query.setLong("user_id", user_id);
			Users us = (Users) query.uniqueResult();
			
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			if (us!=null){
				return us.getLevel_id();
			}
		} catch (HibernateException ex) {
			log.error("[getUserLevelByID]" ,ex);
		} catch (Exception ex2) {
			log.error("[getUserLevelByID]" ,ex2);
		}
		return null;
	}
	
	public List<Users> getPendingUsersForSendingMail() {
		try {
			String hql = "select c from Users as c " +
						"where c.isPending = :isPending " +
						"AND deleted != :deleted";
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setBoolean("isPending", true);
			query.setString("deleted", "true");
			List<Users> orgList = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return orgList;
			
		} catch (HibernateException ex) {
			log.error("[getPendingOrganisationsForSendingMail]" ,ex);
		} catch (Exception ex2) {
			log.error("[getPendingOrganisationsForSendingMail]" ,ex2);
		}
		return null;
	}
}
