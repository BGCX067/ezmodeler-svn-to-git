package org.i4change.app.data.basic;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.hibernate.beans.lang.FieldLanguage;
import org.i4change.app.hibernate.beans.lang.Fieldlanguagesvalues;
import org.i4change.app.hibernate.beans.lang.Fieldvalues;
import org.i4change.app.hibernate.utils.HibernateUtil;

/**
 * 
 * @author sebastianwagner
 *
 */
public class Fieldmanagment {

	private static final Log log = LogFactory.getLog(Fieldmanagment.class);

	private static Fieldmanagment instance = null;

	private Fieldmanagment() {
	}

	public static synchronized Fieldmanagment getInstance() {
		if (instance == null) {
			instance = new Fieldmanagment();
		}
		return instance;
	}
	
	public Fieldlanguagesvalues getFieldByLabelNumberAndLanguage(Long label_number, Long language_id) {
		try {
			//log.debug("getFieldByLabelNumberAndLanguage: "+label_number+" lang: "+language_id);
			
			String hql = "select f from Fieldlanguagesvalues f " +
					"WHERE f.language_id = :language_id " +
					"AND f.label_number = :label_number";
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("label_number", label_number);
			query.setLong("language_id", language_id);
			
			Fieldlanguagesvalues flv = (Fieldlanguagesvalues) query.uniqueResult();
			
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			//log.debug("flv: "+flv);
			
			return flv;
		} catch (HibernateException ex) {
			log.error("[getFieldByIdAndLanguage]: " + ex);
		} catch (Exception ex2) {
			log.error("[getFieldByIdAndLanguage]: " + ex2);
		}
		return null;
	}
	
	public Long deleteFieldlanguagesvaluesById(Long fieldlanguagesvalues_id) {
		try {
			Fieldlanguagesvalues flv = this.getFieldlanguagesvaluesById(fieldlanguagesvalues_id);
			if (flv == null) {
				return new Long(-27);
			}
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			session.delete(flv);
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return new Long(-28);
		} catch (HibernateException ex) {
			log.error("[getFieldByIdAndLanguage]: " + ex);
		} catch (Exception ex2) {
			log.error("[getFieldByIdAndLanguage]: " + ex2);
		}
		return new Long(-1);
	}	

	public List<Fieldlanguagesvalues> getAllFieldsByLanguage(Long language_id) {
		try {
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery("select f from Fieldlanguagesvalues f WHERE f.language_id = :language_id ");
			query.setLong("language_id", language_id);
			List<Fieldlanguagesvalues> returnList = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);

			return returnList;
		} catch (HibernateException ex) {
			log.error("[getConfKey]: " + ex);
		} catch (Exception ex2) {
			log.error("[getConfKey]: " + ex2);
		}
		return null;
	}


	public List<Fieldlanguagesvalues> getAllFieldsByLanguage(Long language_id, int start, int max) {
		try {
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			
			String sql = "select f from Fieldlanguagesvalues f WHERE f.language_id = :language_id " +
					"AND f.label_number >= :start AND f.label_number <  :max";
			
//			log.debug("getAllFieldsByLanguage sql: "+sql);
//			log.debug("getAllFieldsByLanguage language_id: "+language_id);
//			log.debug("getAllFieldsByLanguage start: "+start);
//			log.debug("getAllFieldsByLanguage max: "+max);

			Query query = session.createQuery(sql);
			query.setLong("language_id", language_id);
			query.setLong("start", start);
			query.setLong("max", start+max);
			
			List<Fieldlanguagesvalues> returnList = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
//			
//			for (Iterator<Fieldlanguagesvalues> iter = returnList.iterator();iter.hasNext();){
//				Fieldlanguagesvalues flang = iter.next();
//				log.debug("IDs: "+flang.getFieldlanguagesvalues_id()+" "+flang.getFieldvalues_id());
//				
//			}

			return returnList;
		} catch (HibernateException ex) {
			log.error("[getConfKey]: " , ex);
		} catch (Exception ex2) {
			log.error("[getConfKey]: " , ex2);
		}
		return null;
	}
	
	public List<Fieldlanguagesvalues> getAllFieldsByLanguageLabeled(Long language_id, int start, int max) {
		try {
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			
			String sql = "select f from Fieldlanguagesvalues f WHERE f.language_id = :language_id " +
					"AND f.label_number >= :start AND f.label_number <  :max";
//			log.debug("getAllFieldsByLanguage sql: "+sql);
//			log.debug("getAllFieldsByLanguage language_id: "+language_id);
//			log.debug("getAllFieldsByLanguage start: "+start);
//			log.debug("getAllFieldsByLanguage max: "+max);

			Query query = session.createQuery(sql);
			query.setLong("language_id", language_id);
			query.setLong("start", start);
			query.setLong("max", start+max);
			
			List<Fieldlanguagesvalues> returnList = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			List<Fieldlanguagesvalues> returnListNew = new LinkedList<Fieldlanguagesvalues>();
//			
			for (Iterator<Fieldlanguagesvalues> iter = returnList.iterator();iter.hasNext();){
				Fieldlanguagesvalues flang = iter.next();
				Fieldlanguagesvalues flangNew = new Fieldlanguagesvalues();
				flangNew = flang;
				flangNew.setValue(flang.getValue()+" ["+flang.getLabel_number()+"]");
				returnListNew.add(flangNew);
			}

			return returnListNew;
		} catch (HibernateException ex) {
			log.error("[getAllFieldsByLanguageLabeled]: " + ex);
		} catch (Exception ex2) {
			log.error("[getAllFieldsByLanguageLabeled]: " + ex2);
		}
		return null;
	}
	
	
	public Long addFieldValueByLabeldNumberAndLanguage(Long label_number,
			Long language_id, String fieldvalue) {
		try {
			
			log.debug("addFieldValueByLabeldNumberAndLanguage: "+label_number+" "+language_id+" "+fieldvalue);
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			//Reduce Date Loaded by setting some values to NULL
			Fieldlanguagesvalues flv = new Fieldlanguagesvalues();
			//flv.setStarttime(new Date());
			flv.setValue(fieldvalue);
			flv.setLanguage_id(language_id);
			flv.setLabel_number(label_number);
			//flv.setDeleted();

			Long fieldlanguagesvaluesId = (Long) session.save(flv);

			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return fieldlanguagesvaluesId;
		} catch (HibernateException ex) {
			log.error("[getConfKey]: ",ex);
		} catch (Exception ex2) {
			log.error("[getConfKey]: ",ex2);
		}
		return null;
	}
	
	public void updateFieldValueByFieldAndLanguage(Fieldlanguagesvalues flv) {
		try {
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			session.update(flv);

			tx.commit();
			HibernateUtil.closeSession(idf);

		} catch (HibernateException ex) {
			log.error("[updateFieldValueByFieldAndLanguage]: ",ex);
		} catch (Exception ex2) {
			log.error("[updateFieldValueByFieldAndLanguage]: ",ex2);
		}

	}	
	
	public Long addFieldByLabelNumber(String fieldName, Long label_number) {
		try {
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Fieldvalues fl = new Fieldvalues();
			fl.setLabel_number(label_number);
			fl.setStarttime(new Date());
			fl.setName(fieldName);
			fl.setDeleted("false");

			Long fieldId = (Long)session.save(fl);

			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return fieldId;
		} catch (HibernateException ex) {
			log.error("[getConfKey]: ",ex);
		} catch (Exception ex2) {
			log.error("[getConfKey]: ",ex2);
		}
		return null;
	}	
	
	public List<Fieldvalues> getMixedFieldValuesList(Long language_id) throws Exception {
		List<Fieldvalues> ll = this.getFieldsValues();
		for (Iterator<Fieldvalues> iter = ll.iterator(); iter.hasNext();){
			Fieldvalues fv = iter.next();
			fv.setFieldlanguagesvalue(this.getFieldByLabelNumberAndLanguage(fv.getLabel_number(), language_id));
		}
		return ll;
	}	
	
	public Fieldvalues getFieldvaluesByFieldid(Long fieldvalues_id, Long language_id) {
		try {
			//log.error("Long fieldvalues_id, Long language_id "+fieldvalues_id+" || "+language_id);
			Fieldvalues fv = this.getFieldvaluesById(fieldvalues_id);
			fv.setFieldlanguagesvalue(this.getFieldByLabelNumberAndLanguage(fv.getLabel_number(), language_id));
			return fv;
		} catch (Exception e) {
			log.error("[getFieldvaluesById] ",e);
		}
		return null;
	}
	
	/**
	 * update given Field and its Label by IDs
	 * @param fieldvalues_id
	 * @param name
	 * @param fieldlanguagesvalues_id
	 * @param value
	 * @return
	 */
	public Long updateLabel(Long label_number, String name, Long fieldlanguagesvalues_id, String value) {
		try {
			Fieldvalues fv = this.getFieldvaluesByLabelNumber(label_number);
			if (fv==null) {
				return new Long(-24);
			} else {
				fv.setName(name);
				fv.setUpdatetime(new Date());
				this.updateField(fv);
			}
			Fieldlanguagesvalues flv = this.getFieldlanguagesvaluesById(fieldlanguagesvalues_id);
			if (flv==null) {
				return new Long(-25);
			} else {
				flv.setUpdatetime(new Date());
				flv.setValue(value);
				this.updateFieldLanguagesLabel(flv);
			}
			return label_number;
		} catch (HibernateException ex) {
			log.error("[updateFieldLanguagesLabel]: ",ex);
		} catch (Exception ex2) {
			log.error("[updateFieldLanguagesLabel]: ",ex2);
		}
		return new Long(-1);
	}
	
	/**
	 * 
	 * @param fieldvalues_id
	 * @param label_number
	 * @param name
	 * @param fieldlanguagesvalues_id
	 * @param value
	 * @return
	 */
	public Long updateLabel(Long fieldvalues_id, Long label_number, 
			String name, Long fieldlanguagesvalues_id, String value) {
		try {
			Fieldvalues fv = this.getFieldvaluesById(fieldvalues_id);
			if (fv==null) {
				return new Long(-24);
			} else {
				fv.setName(name);
				fv.setUpdatetime(new Date());
				fv.setLabel_number(label_number);
				this.updateField(fv);
			}
			Fieldlanguagesvalues flv = this.getFieldlanguagesvaluesById(fieldlanguagesvalues_id);
			if (flv==null) {
				return new Long(-25);
			} else {
				flv.setUpdatetime(new Date());
				flv.setValue(value);
				this.updateFieldLanguagesLabel(flv);
			}
			return fieldvalues_id;
		} catch (HibernateException ex) {
			log.error("[updateFieldLanguagesLabel]: ",ex);
		} catch (Exception ex2) {
			log.error("[updateFieldLanguagesLabel]: ",ex2);
		}
		return new Long(-1);
	}	
	
	public Long addAndUpdateLabel(Long fieldvalues_id, Long label_number, String name, String value, Long language_id) {
		try {
			Fieldvalues fv = this.getFieldvaluesById(fieldvalues_id);
			if (fv==null) {
				return new Long(-24);
			} else {
				fv.setName(name);
				fv.setLabel_number(label_number);
				fv.setUpdatetime(new Date());
				this.updateField(fv);
			}
			this.addFieldValueByLabeldNumberAndLanguage(label_number, language_id, value);
			return label_number;
		} catch (HibernateException ex) {
			log.error("[updateFieldLanguagesLabel]: ",ex);
		} catch (Exception ex2) {
			log.error("[updateFieldLanguagesLabel]: ",ex2);
		}
		return new Long(-1);
	}	
	
	
	public Long addFieldAndLabel(String name, Long label_number, String value, Long language_id) {
		try {
			Long fieldvalues_id = this.addFieldByLabelNumber(name, label_number);
			if (fieldvalues_id>0) {
				this.addFieldValueByLabeldNumberAndLanguage(label_number, language_id, value);
				return fieldvalues_id;
			} else {
				return new Long(-1);
			}
		} catch (HibernateException ex) {
			log.error("[updateFieldLanguagesLabel]: ",ex);
		} catch (Exception ex2) {
			log.error("[updateFieldLanguagesLabel]: ",ex2);
		}
		return new Long(-1);
	}	
	
	public SearchResult getFieldsByLanguage(int start ,int max, String orderby, boolean asc, Long language_id){
		try {
			SearchResult sresult = new SearchResult();
			sresult.setObjectName(Fieldlanguagesvalues.class.getName());
			sresult.setRecords(this.selectMaxFromFieldsValues());
			sresult.setResult(this.getMixedFieldValuesList(start, max, orderby, asc, language_id));
			return sresult;
		} catch (HibernateException ex) {
			log.error("[getFieldsByLanguage]: ",ex);
		} catch (Exception ex2) {
			log.error("[getFieldsByLanguage]: ",ex2);
		}
		return null;
	}
	
	public SearchResult getFieldsByLanguageBySearch(int start ,int max, String orderby, boolean asc, Long language_id, String search){
		try {
			List<Fieldvalues> listResult = Fieldmanagment.getInstance().getFieldsValuesBySearch(start, max, 
					orderby, asc, search, language_id);
			for (Iterator<Fieldvalues> iter = listResult.iterator(); iter.hasNext();){
				Fieldvalues fv = iter.next();
				fv.setFieldlanguagesvalue(Fieldmanagment.getInstance().getFieldByLabelNumberAndLanguage(fv.getLabel_number(), language_id));
			}
	
			SearchResult sresult = new SearchResult();
			sresult.setObjectName(Fieldvalues.class.getName());
			sresult.setRecords(Fieldmanagment.getInstance().
						getFieldsValuesBySearchMaxResults(orderby, asc, search, language_id));
			sresult.setResult(listResult);
			
			return sresult;
		} catch (HibernateException ex) {
			log.error("[getFieldsByLanguageBySearch]: ",ex);
		} catch (Exception ex2) {
			log.error("[getFieldsByLanguageBySearch]: ",ex2);
		}
		return null;
	}
	
	public Long selectMaxLabelNumberHelp() throws Exception{
		
		String hql = "select max(c.label_number) from Fieldvalues c " +
				"where c.deleted = 'false' " +
				"AND c.label_number > 1999 " +
				"AND c.label_number < 3000";
		
		Object idf = HibernateUtil.createSession();
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery(hql); 
		List ll = query.list();
		tx.commit();
		HibernateUtil.closeSession(idf);
		//log.error((Long)ll.get(0));
		return (Long)ll.get(0);				
	}

	private Long selectMaxFromFieldsValues() throws Exception{
		Object idf = HibernateUtil.createSession();
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("select count(c.fieldvalues_id) from Fieldvalues c where c.deleted = 'false'"); 
		List ll = query.list();
		tx.commit();
		HibernateUtil.closeSession(idf);
		//log.error((Long)ll.get(0));
		return (Long)ll.get(0);				
	}	
	
	private List<Fieldvalues> getMixedFieldValuesList(int start ,int max, String orderby, boolean asc, Long language_id) throws Exception {
		List<Fieldvalues> ll = this.getFieldsValues(start, max, orderby, asc);
		for (Iterator<Fieldvalues> iter = ll.iterator(); iter.hasNext();){
			Fieldvalues fv = iter.next();
			fv.setFieldlanguagesvalue(this.getFieldByLabelNumberAndLanguage(fv.getLabel_number(), language_id));
		}
		return ll;
	}
	
	private List<Fieldvalues> getMixedFieldValuesListBySearch(int start ,int max, 
			String orderby, boolean asc, Long language_id, String search) throws Exception {
		List<Fieldvalues> ll = this.getFieldsValuesBySearch(start, max, orderby, asc, search, language_id);
		for (Iterator<Fieldvalues> iter = ll.iterator(); iter.hasNext();){
			Fieldvalues fv = iter.next();
			fv.setFieldlanguagesvalue(this.getFieldByLabelNumberAndLanguage(fv.getLabel_number(), language_id));
		}
		return ll;
	}
	
	private List<Fieldvalues> getFieldsValues() throws Exception {
		Object idf = HibernateUtil.createSession();
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		Criteria crit = session.createCriteria(Fieldvalues.class);
		crit.add(Restrictions.eq("deleted", "false"));
		crit.addOrder(Order.asc("label_number"));
		List<Fieldvalues> ll = crit.list();
		tx.commit();
		HibernateUtil.closeSession(idf);
		return ll;
	}
	
	private List<Fieldvalues> getFieldsValues(int start ,int max, String orderby, boolean asc) throws Exception {
		Object idf = HibernateUtil.createSession();
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		Criteria crit = session.createCriteria(Fieldvalues.class);
		crit.add(Restrictions.eq("deleted", "false"));
		crit.setFirstResult(start);
		crit.setMaxResults(max);
		if (asc) crit.addOrder(Order.asc(orderby));
		else crit.addOrder(Order.desc(orderby));
		List<Fieldvalues> ll = crit.list();
		tx.commit();
		HibernateUtil.closeSession(idf);
		return ll;
	}
	
	public List<Fieldvalues> getFieldsValuesBySearch(int start ,int max, String orderby, 
			boolean asc, String search, Long language_id) throws Exception {
		
		String hql = "SELECT c FROM Fieldvalues c, Fieldlanguagesvalues flv " +
				"WHERE c.deleted != :deleted " +
				"AND flv.deleted != :deleted " +
				"AND c.label_number = flv.label_number " +
				"AND flv.language_id = :language_id " +
				"AND " +
				"(" +
				"c.label_number LIKE :search " +
				"OR lower(flv.value) LIKE lower(:searchStr) " +
				"OR lower(c.name) LIKE lower(:searchStr)" +
				") " +
				"ORDER BY "+orderby;
		
		if (asc) {
			hql += " ASC";
		} else {
			hql += " DESC";
		}
		
		Object idf = HibernateUtil.createSession();
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery(hql);
		query.setString("deleted", "true");
		query.setLong("language_id", language_id);
		query.setString("search", search);
		query.setString("searchStr", "%"+search+"%");
		query.setFirstResult(start);
		query.setMaxResults(max);
		
		List<Fieldvalues> ll = query.list();
		
		tx.commit();
		HibernateUtil.closeSession(idf);
		return ll;
	}
	
	public Long getFieldsValuesBySearchMaxResults(String orderby, 
			boolean asc, String search, Long language_id) throws Exception {
		
		String hql = "SELECT count(c.fieldvalues_id) FROM Fieldvalues c, Fieldlanguagesvalues flv " +
				"WHERE c.deleted != :deleted " +
				"AND flv.deleted != :deleted " +
				"AND c.label_number = flv.label_number " +
				"AND flv.language_id = :language_id " +
				"AND " +
				"(" +
				"c.label_number LIKE :search " +
				"OR lower(flv.value) LIKE lower(:searchStr) " +
				"OR lower(c.name) LIKE lower(:searchStr)" +
				") " +
				"ORDER BY "+orderby;
		
		if (asc) {
			hql += " ASC";
		} else {
			hql += " DESC";
		}
		
		Object idf = HibernateUtil.createSession();
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery(hql);
		query.setString("deleted", "true");
		query.setLong("language_id", language_id);
		query.setString("search", search);
		query.setString("searchStr", "%"+search+"%");
		
		List ll = query.list();
		
		tx.commit();
		HibernateUtil.closeSession(idf);
		
		return (Long)ll.get(0);	
	}
	
	public Fieldvalues getFieldvaluesById(Long fieldvalues_id) throws Exception {
		String hql = "select f from Fieldvalues f WHERE f.fieldvalues_id = :fieldvalues_id ";
		Object idf = HibernateUtil.createSession();
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery(hql);
		query.setLong("fieldvalues_id", fieldvalues_id);
		Fieldvalues fv = (Fieldvalues) query.uniqueResult();
		tx.commit();
		HibernateUtil.closeSession(idf);
		return fv;
	}
	
	public Fieldvalues getFieldvaluesByLabelNumber(Long label_number) throws Exception {
		String hql = "select f from Fieldvalues f WHERE f.label_number = :label_number ";
		Object idf = HibernateUtil.createSession();
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery(hql);
		query.setLong("label_number", label_number);
		Fieldvalues fv = (Fieldvalues) query.uniqueResult();
		tx.commit();
		HibernateUtil.closeSession(idf);
		return fv;
	}
	
	public Fieldlanguagesvalues getFieldlanguagesvaluesByLabelAndLang(Long label_number, Long language_id) throws Exception {
		String hql = "select f from Fieldlanguagesvalues f " +
				"WHERE f.label_number = :label_number " +
				"AND f.language_id = :language_id";
		Object idf = HibernateUtil.createSession();
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery(hql);
		query.setLong("label_number", label_number);
		query.setLong("language_id", language_id);
		Fieldlanguagesvalues flv = (Fieldlanguagesvalues) query.uniqueResult();
		tx.commit();
		HibernateUtil.closeSession(idf);
		return flv;
	}
	
	private Fieldlanguagesvalues getFieldlanguagesvaluesById(Long fieldlanguagesvalues_id) throws Exception {
		String hql = "select f from Fieldlanguagesvalues f WHERE f.fieldlanguagesvalues_id = :fieldlanguagesvalues_id ";
		Object idf = HibernateUtil.createSession();
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery(hql);
		query.setLong("fieldlanguagesvalues_id", fieldlanguagesvalues_id);
		Fieldlanguagesvalues flv = (Fieldlanguagesvalues) query.uniqueResult();
		tx.commit();
		HibernateUtil.closeSession(idf);
		return flv;
	}
	
	private void updateField(Fieldvalues fv) throws Exception {
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			session.update(fv);
			tx.commit();
			HibernateUtil.closeSession(idf);
	}
	
	private void updateFieldLanguagesLabel(Fieldlanguagesvalues flv) throws Exception {
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			session.update(flv);
			tx.commit();
			HibernateUtil.closeSession(idf);
	}	

}
