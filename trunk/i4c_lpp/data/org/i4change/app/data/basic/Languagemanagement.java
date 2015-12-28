package org.i4change.app.data.basic;

import java.util.List;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import org.i4change.app.hibernate.beans.lang.FieldLanguage;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * 
 * @author sebastianwagner
 *
 */
public class Languagemanagement extends HibernateDaoSupport {

	private static final Log log = LogFactory.getLog(Languagemanagement.class);

	public Long addLanguage(String langName) {
		try {

			FieldLanguage fl = new FieldLanguage();
			fl.setStarttime(new Date());
			fl.setDeleted("false");
			fl.setName(langName);

			Long languages_id = (Long)getHibernateTemplate().save(fl);

			return languages_id;
		} catch (HibernateException ex) {
			log.error("[addLanguage]: ",ex);
		} catch (Exception ex2) {
			log.error("[addLanguage]: ",ex2);
		}
		return null;
	}

 
	public void emptyFieldLanguage() {
		try {
			
//			 TODO delete hql query doesn't work, must be repared
			getSession().createQuery("delete from FieldLanguage");
			
		} catch (HibernateException ex) {
			log.error("[getConfKey]: ",ex);
		} catch (Exception ex2) {
			log.error("[getConfKey]: ",ex2);
		}
	}
	
	public Long updateFieldLanguage(Long language_id, String langName, String deleted) {
		try {
			FieldLanguage fl = this.getFieldLanguageById(language_id);
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

	
	private void updateLanguage(FieldLanguage fl) throws Exception {
		getHibernateTemplate().update(fl);
	}	


	public FieldLanguage getFieldLanguageById(Long language_id) {
		try {
			String hql = "select c from FieldLanguage as c " +
					"WHERE c.deleted != :deleted " +
					"AND c.language_id = :language_id";
			Query query = getSession().createQuery(hql);
			query.setString("deleted", "true");
			query.setLong("language_id", language_id);
			FieldLanguage fl = (FieldLanguage) query.uniqueResult();
			return fl;
		} catch (HibernateException ex) {
			log.error("[getLanguageById]: ",ex);
		} catch (Exception ex2) {
			log.error("[getLanguageById]: ",ex2);
		}
		return null;
	}
	
	public List<FieldLanguage> getLanguages() {
		try {
			String hql = "select c from FieldLanguage as c " +
					"WHERE c.deleted != :deleted ";
			Query query = getSession().createQuery(hql);
			query.setString("deleted", "true");
			List<FieldLanguage> ll = query.list();
			return ll;
		} catch (HibernateException ex) {
			log.error("[getLanguages]: ",ex);
		} catch (Exception ex2) {
			log.error("[getLanguages]: ",ex2);
		}
		return null;
	}

}
