package org.i4change.app.data.basic;

import java.util.Iterator;
import java.util.List;
import java.util.Date;
import java.util.Calendar;

import org.i4change.app.hibernate.beans.basic.Sessiondata;
import org.i4change.app.utils.crypt.ManageCryptStyle;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


/**
 * 
 * @author swagner
 * This Class handles all session management
 * 
 *
 */

public class Sessionmanagement extends HibernateDaoSupport {
    
	private static final Log log = LogFactory.getLog(Sessionmanagement.class);
	   
	//Spring managed beans
	private ManageCryptStyle manageCryptStyle;

	public ManageCryptStyle getManageCryptStyle() {
		return manageCryptStyle;
	}
	public void setManageCryptStyle(ManageCryptStyle manageCryptStyle) {
		this.manageCryptStyle = manageCryptStyle;
	}

	/**
	 * creates a new session-object in the database
	 * @return
	 */
	public Sessiondata startsession() {
		//log.error("startsession User: || ");
		long thistime = new Date().getTime();
		Sessiondata sessiondata = new Sessiondata();
		sessiondata.setSession_id(this.manageCryptStyle.getInstanceOfCrypt().createPassPhrase(String.valueOf(thistime).toString()));
		sessiondata.setRefresh_time(new Date());
		sessiondata.setStarttermin_time(new Date());
		sessiondata.setUser_id(null);
		
		log.error("####################");
		log.error("####################");
		log.error("####################");
		log.error("####################");
		log.error("####################");
		log.error("####################");
		
		try {
			
//			Object idf = HibernateUtil.createSession();
//			Session session = HibernateUtil.getSession();
//			Transaction tx = session.beginTransaction();
//			session.flush();
//			session.save(sessiondata);
//			session.flush();
//			session.refresh(sessiondata);
//			tx.commit();
//			HibernateUtil.closeSession(idf);
			
			
//			log.error("Save Session +++ ");
//			Transaction tx = getSession().beginTransaction();

			getHibernateTemplate().save(sessiondata);
			
			//Einmal die Session flushen zum starten der Anwendung
			getSession().flush();

			//tx.commit();
			
		} catch (HibernateException ex) {
			log.error("[startsession]: " ,ex);
		} catch (Exception ex2) {
			log.error("[startsession]: " ,ex2);
		}

		return sessiondata;
	}

	/**
	 * check if a given sessionID is loged in
	 * @param SID
	 * @return
	 */
	public Long checkSession(String SID) {
		try {
			//log.debug("checkSession User: || "+SID);
//			Object idf = HibernateUtil.createSession();
//			Transaction tx = session.beginTransaction();
//			
//			session.flush();
			Criteria crit = getSession().createCriteria(Sessiondata.class);
			crit.add(Restrictions.eq("session_id", SID));

			int count = crit.list().size();
			Sessiondata sessiondata = null;
			for (Iterator it2 = crit.list().iterator(); it2.hasNext();) {
				sessiondata = (Sessiondata) it2.next();
			}
			
			if (sessiondata == null) {
				log.error("No Session found: "+SID);
			}
			
//			if (sessiondata!=null) {
//				session.refresh(sessiondata);
//			} else {
//				log.debug ("No Session found: "+SID);
//			}
//			session.flush();
//			tx.commit();
//			HibernateUtil.closeSession(idf);
			
			//if (sessiondata!=null) log.debug("checkSession USER_ID: "+sessiondata.getUser_id());
				
			if (sessiondata!=null) updatesession(SID);
			
			if (sessiondata==null || count == 0 || sessiondata.equals(null) ||
					sessiondata.getUser_id()==null || sessiondata.getUser_id().equals(null) || sessiondata.getUser_id().equals(new Long(0)) ) {
				return new Long(0);
			} else {
				return sessiondata.getUser_id();
			}			
		} catch (HibernateException ex) {
			log.error("[checkSession]: " ,ex);
		} catch (Exception ex2) {
			log.error("[checkSession]: " ,ex2);
		}
		return null;
	}

	/**
	 * update the session of a user with a new user id
	 * this is needed to see if the session is loggedin
	 * @param SID
	 * @param USER_ID
	 */
	public void updateUser(String SID, long USER_ID) {
		try {
			//log.debug("updateUser User: "+USER_ID+" || "+SID);
			
//			Object idf = HibernateUtil.createSession();
//			Session session = getSession();
//			Transaction tx = session.beginTransaction();
//			session.flush();
			Criteria crit = getSession().createCriteria(Sessiondata.class);
			crit.add(Restrictions.eq("session_id", SID));

			List fullList = crit.list();
			if (fullList.size() == 0){
				log.error("Could not find session to update: "+SID);
				return;
			} else {
				//log.error("Found session to update: "+SID);
			}
//			tx.commit();
//			HibernateUtil.closeSession(idf);
			
			Sessiondata sd = (Sessiondata) fullList.get(0);
			//log.debug("Found session to update: "+sd.getSession_id()+ " userId: "+USER_ID);
			
//			idf = HibernateUtil.createSession();
//			session = HibernateUtil.getSession();
//			tx = session.beginTransaction();
//			sd.setRefresh_time(new Date());
//			session.refresh(sd);
			
			sd.setUser_id(USER_ID);
//			session.flush();
			
			getHibernateTemplate().update(sd);
			
//			session.flush();
//			tx.commit();
//			HibernateUtil.closeSession(idf);
			
			//log.debug("session updated User: "+USER_ID);

		} catch (HibernateException ex) {
			log.error("[updateUser]: " ,ex);
		} catch (Exception ex2) {
			log.error("[updateUser]: " ,ex2);
		}
	}

	/**
	 * update the session every time a user makes a request
	 * @param SID
	 */
	private void updatesession(String SID) {
		try {
//			log.debug("****** updatesession: "+SID);
//			Object idf = HibernateUtil.createSession();
//			Session session = getSession();
//			Transaction tx = session.beginTransaction();
			Criteria crit = getSession().createCriteria(Sessiondata.class);
			crit.add(Restrictions.eq("session_id", SID));
			Sessiondata sessionDat = (Sessiondata) crit.uniqueResult();
//			tx.commit();
//			HibernateUtil.closeSession(idf);			
			if (sessionDat == null) {
				log.error("Found NO session to updateSession: ");
			} else {
//				log.debug("Found session to updateSession: ");
				//log.debug("Found session to updateSession sd "+sd.getUser_id()+" "+sd.getSession_id());
				sessionDat.setRefresh_time(new Date());
				
//				Object idf2 = HibernateUtil.createSession();
//				Session session2 = HibernateUtil.getSession();
//				Transaction tx2 = session2.beginTransaction();				
				getHibernateTemplate().update(sessionDat);
//				session2.flush();
//				tx2.commit();
//				HibernateUtil.closeSession(idf2);	
			}
			
		} catch (HibernateException ex) {
			log.error("[updatesession]: " ,ex);
		} catch (Exception ex2) {
			log.error("[updatesession]: " ,ex2);
		}
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 */
	private List<Sessiondata> getSessionToDelete(Date date){
		try {
			
			String hql = "SELECT c FROM Sessiondata c " +
						"WHERE refresh_time < :date " +
						"AND permanent is null";
			
			log.debug("****** sessionToDelete: "+date);
//			Object idf = HibernateUtil.createSession();
//			Session session = HibernateUtil.getSession();
//			Transaction tx = session.beginTransaction();
//			Criteria crit = session.createCriteria(Sessiondata.class);
//			crit.add(Restrictions.lt("refresh_time", date));
//			//crit.add(Restrictions.ne("permanent", true));
			Query query = getSession().createQuery(hql);
			query.setTimestamp("date", date);
			List<Sessiondata> fullList = query.list();
//			tx.commit();
//			HibernateUtil.closeSession(idf);
			return fullList;
		} catch (HibernateException ex) {
			log.error("[getSessionToDelete]: " ,ex);
		} catch (Exception ex2) {
			log.error("[getSessionToDelete]: " ,ex2);
		}
		return null;
	}
	
	/**
	 * 
	 *
	 */
	public void clearSessionTable(){
		try {
			//log.debug("****** clearSessionTable: "+ new Date());
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTimeInMillis(rightNow.getTimeInMillis()-1800000);
		    List<Sessiondata> l = this.getSessionToDelete(rightNow.getTime());
		    log.debug("clearSessionTable: "+l.size());
		    for (Iterator<Sessiondata> it = l.iterator();it.hasNext();){
//				Object idf = HibernateUtil.createSession();
//				Session session = HibernateUtil.getSession();
//				Transaction tx = session.beginTransaction();
				Sessiondata sData = it.next();
		    	getSession().delete(sData);
//				tx.commit();
//				HibernateUtil.closeSession(idf);
		    }
		    
		    //Workaround for now to keep the DB-Connection alive and 
		    //to clear the Session scheduled based
		    //ObjectIdentifierDaoImpl.getInstance().getCurrentObjectIdentifier(1L);
			
		    
		} catch (HibernateException ex) {
			log.error("clearSessionTable",ex);
		} catch (Exception err) {
			log.error("clearSessionTable",err);
		}
	}

	public void updateSessionWithPermanent(String SID, Long language_id,
			Long organization_id) {
		try {
			//log.debug("checkSession User: || "+SID);
//			Object idf = HibernateUtil.createSession();
//			Session session = HibernateUtil.getSession();
//			Transaction tx = session.beginTransaction();
//			
//			session.flush();
			Criteria crit = getSession().createCriteria(Sessiondata.class);
			crit.add(Restrictions.eq("session_id", SID));

			Sessiondata sessiondata = (Sessiondata) crit.uniqueResult();
			
			sessiondata.setLanguage_id(language_id);
			sessiondata.setOrganization_id(organization_id);
			sessiondata.setPermanent(true);
//			
//			tx.commit();
//			HibernateUtil.closeSession(idf);
			
					
		} catch (HibernateException ex) {
			log.error("[checkSession]: " ,ex);
		} catch (Exception ex2) {
			log.error("[checkSession]: " ,ex2);
		}
	}

	public Sessiondata getSessionByHash(String SID) {
		try {
			//log.debug("checkSession User: || "+SID);
//			Object idf = HibernateUtil.createSession();
//			Session session = HibernateUtil.getSession();
//			Transaction tx = session.beginTransaction();
			
//			session.flush();
			Criteria crit = getSession().createCriteria(Sessiondata.class);
			crit.add(Restrictions.eq("session_id", SID));

			Sessiondata sessiondata = (Sessiondata) crit.uniqueResult();
			
//			tx.commit();
//			HibernateUtil.closeSession(idf);
			
			return sessiondata;		
		} catch (HibernateException ex) {
			log.error("[getSessionByHash]: " ,ex);
		} catch (Exception ex2) {
			log.error("[getSessionByHash]: " ,ex2);
		}
		return null;
	}
}
