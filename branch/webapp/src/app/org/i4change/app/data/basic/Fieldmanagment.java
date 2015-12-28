package org.i4change.app.data.basic;

import java.util.Date;
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
import org.i4change.app.hibernate.Fieldlanguage;
import org.i4change.app.hibernate.Fieldlanguagesvalues;
import org.i4change.app.hibernate.Fieldvalues;
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
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery("select f from Fieldlanguagesvalues f WHERE f.language_id = :language_id AND f.label_number = :label_number");
			query.setLong("label_number", label_number);
			query.setLong("language_id", language_id);
			Fieldlanguagesvalues flv = (Fieldlanguagesvalues) query.uniqueResult();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return flv;
		} catch (HibernateException ex) {
			log.error("[getFieldByIdAndLanguage]: " + ex);
		} catch (Exception ex2) {
			log.error("[getFieldByIdAndLanguage]: " + ex2);
		}
		return null;
	}
	
	public Long deleteFieldlanguagesvaluesById(Long Fieldlanguagesvalues_id) {
		try {
			Fieldlanguagesvalues flv = this.getFieldlanguagesvaluesById(Fieldlanguagesvalues_id);
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
			log.error("[getConfKey]: " + ex);
		} catch (Exception ex2) {
			log.error("[getConfKey]: " + ex2);
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

			Fieldlanguagesvalues flv = new Fieldlanguagesvalues();
			flv.setStarttime(new Date());
			flv.setValue(fieldvalue);
			flv.setLanguage_id(language_id);
			flv.setLabel_number(label_number);
			flv.setDeleted("false");

			Long FieldlanguagesvaluesId = (Long) session.save(flv);

			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return FieldlanguagesvaluesId;
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
	 * @param Fieldlanguagesvalues_id
	 * @param value
	 * @return
	 */
	public Long updateLabel(Long label_number, String name, Long Fieldlanguagesvalues_id, String value) {
		try {
			Fieldvalues fv = this.getFieldvaluesByLabelNumber(label_number);
			if (fv==null) {
				return new Long(-24);
			} else {
				fv.setName(name);
				fv.setUpdatetime(new Date());
				this.updateField(fv);
			}
			Fieldlanguagesvalues flv = this.getFieldlanguagesvaluesById(Fieldlanguagesvalues_id);
			if (flv==null) {
				return new Long(-25);
			} else {
				flv.setUpdatetime(new Date());
				flv.setValue(value);
				this.updateFieldlanguagesLabel(flv);
			}
			return label_number;
		} catch (HibernateException ex) {
			log.error("[updateFieldlanguagesLabel]: ",ex);
		} catch (Exception ex2) {
			log.error("[updateFieldlanguagesLabel]: ",ex2);
		}
		return new Long(-1);
	}
	
	/**
	 * 
	 * @param fieldvalues_id
	 * @param label_number
	 * @param name
	 * @param Fieldlanguagesvalues_id
	 * @param value
	 * @return
	 */
	public Long updateLabel(Long fieldvalues_id, Long label_number, 
			String name, Long Fieldlanguagesvalues_id, String value) {
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
			Fieldlanguagesvalues flv = this.getFieldlanguagesvaluesById(Fieldlanguagesvalues_id);
			if (flv==null) {
				return new Long(-25);
			} else {
				flv.setUpdatetime(new Date());
				flv.setValue(value);
				this.updateFieldlanguagesLabel(flv);
			}
			return label_number;
		} catch (HibernateException ex) {
			log.error("[updateFieldlanguagesLabel]: ",ex);
		} catch (Exception ex2) {
			log.error("[updateFieldlanguagesLabel]: ",ex2);
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
			log.error("[updateFieldlanguagesLabel]: ",ex);
		} catch (Exception ex2) {
			log.error("[updateFieldlanguagesLabel]: ",ex2);
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
			log.error("[updateFieldlanguagesLabel]: ",ex);
		} catch (Exception ex2) {
			log.error("[updateFieldlanguagesLabel]: ",ex2);
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

	private Long selectMaxFromFieldsValues() throws Exception{
		Object idf = HibernateUtil.createSession();
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("select max(c.fieldvalues_id) from Fieldvalues c where c.deleted = 'false'"); 
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
	
	private List<Fieldvalues> getFieldsValues() throws Exception {
		Object idf = HibernateUtil.createSession();
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		Criteria crit = session.createCriteria(Fieldvalues.class);
		crit.add(Restrictions.eq("deleted", "false"));
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
	
	private Fieldlanguagesvalues getFieldlanguagesvaluesById(Long Fieldlanguagesvalues_id) throws Exception {
		String hql = "select f from Fieldlanguagesvalues f WHERE f.Fieldlanguagesvalues_id = :Fieldlanguagesvalues_id ";
		Object idf = HibernateUtil.createSession();
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery(hql);
		query.setLong("Fieldlanguagesvalues_id", Fieldlanguagesvalues_id);
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
	
	private void updateFieldlanguagesLabel(Fieldlanguagesvalues flv) throws Exception {
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			session.update(flv);
			tx.commit();
			HibernateUtil.closeSession(idf);
	}	

}
