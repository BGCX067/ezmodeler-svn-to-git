package org.i4change.app.data.basic;

import java.util.List;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.i4change.app.hibernate.utils.HibernateUtil;

import org.i4change.app.hibernate.Fieldlanguage;

/**
 * 
 * @author sebastianwagner
 *
 */
public class Languagemanagement {

	private static final Log log = LogFactory.getLog(Languagemanagement.class);

	private Languagemanagement() {
	}

	private static Languagemanagement instance = null;

	public static synchronized Languagemanagement getInstance() {
		if (instance == null) {
			instance = new Languagemanagement();
		}
		return instance;
	}
	

	public Long addLanguage(String langName) {
		try {
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Fieldlanguage fl = new Fieldlanguage();
			fl.setStarttime(new Date());
			fl.setDeleted("false");
			fl.setName(langName);

			Long languages_id = (Long)session.save(fl);

			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return languages_id;
		} catch (HibernateException ex) {
			log.error("[addLanguage]: ",ex);
		} catch (Exception ex2) {
			log.error("[addLanguage]: ",ex2);
		}
		return null;
	}

 
	public void emptyFieldlanguage() {
		try {
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			
//			 TODO delete hql query doesn't work, must be repared
			session.createQuery("delete from Fieldlanguage");
			tx.commit();
			HibernateUtil.closeSession(idf);
		} catch (HibernateException ex) {
			log.error("[getConfKey]: ",ex);
		} catch (Exception ex2) {
			log.error("[getConfKey]: ",ex2);
		}
	}
	
	public Long updateFieldlanguage(Long language_id, String langName, String deleted) {
		try {
			Fieldlanguage fl = this.getFieldlanguageById(language_id);
			fl.setUpdatetime(new Date());
			if (langName.length()>0) fl.setName(langName);
			fl.setDeleted(deleted);
			this.updateLanguage(fl);
			return language_id;
		} catch (HibernateException ex) {
			log.error("[updateLanguage]: ",ex);
		} catch (Exception ex2) {
			log.error("[updateLanguage]: ",ex2);
		}
		return new Long(-1);
	}

	
	private void updateLanguage(Fieldlanguage fl) throws Exception {
		Object idf = HibernateUtil.createSession();
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		session.update(fl);
		tx.commit();
		HibernateUtil.closeSession(idf);
	}	


	public Fieldlanguage getFieldlanguageById(Long language_id) {
		try {
			String hql = "select c from Fieldlanguage as c " +
					"WHERE c.deleted != :deleted " +
					"AND c.language_id = :language_id";
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setString("deleted", "true");
			query.setLong("language_id", language_id);
			Fieldlanguage fl = (Fieldlanguage) query.uniqueResult();
			tx.commit();
			HibernateUtil.closeSession(idf);
			return fl;
		} catch (HibernateException ex) {
			log.error("[getLanguageById]: ",ex);
		} catch (Exception ex2) {
			log.error("[getLanguageById]: ",ex2);
		}
		return null;
	}
	
	public List<Fieldlanguage> getLanguages() {
		try {
			String hql = "select c from Fieldlanguage as c " +
					"WHERE c.deleted != :deleted ";
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setString("deleted", "true");
			List<Fieldlanguage> ll = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			return ll;
		} catch (HibernateException ex) {
			log.error("[getLanguages]: ",ex);
		} catch (Exception ex2) {
			log.error("[getLanguages]: ",ex2);
		}
		return null;
	}

}
