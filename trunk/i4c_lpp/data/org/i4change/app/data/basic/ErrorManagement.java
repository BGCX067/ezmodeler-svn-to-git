package org.i4change.app.data.basic;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.i4change.app.hibernate.beans.basic.ErrorType;
import org.i4change.app.hibernate.beans.basic.ErrorValues;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class ErrorManagement extends HibernateDaoSupport {
	
	private static final Log log = LogFactory.getLog(ErrorManagement.class);

	public Long addErrorType(Long errortype_id, Long label_number) {
		try {
			ErrorType eType = new ErrorType();
			eType.setErrortype_id(errortype_id);
			eType.setStarttime(new Date());
			eType.setDeleted("false");
			eType.setLabel_number(label_number);
			Long newerrortype_id = (Long) getHibernateTemplate().save(eType);
			return newerrortype_id;
		} catch (HibernateException ex) {
			log.error("[addErrorType]: " + ex);
		} catch (Exception ex2) {
			log.error("[addErrorType]: " + ex2);
		}
		return null;
	}
	
	public List<ErrorType> getErrorTypes() {
		try {
			String hql = "select c from ErrorType as c " +
					"WHERE c.deleted != :deleted ";			
			Query query = getSession().createQuery(hql);
			query.setString("deleted", "true");
			List<ErrorType> ll = query.list();
			return ll;
		} catch (HibernateException ex) {
			log.error("[getErrorTypes]: " + ex);
		} catch (Exception ex2) {
			log.error("[getErrorTypes]: " + ex2);
		}
		return null;
	}
	
	public Long addErrorValues(Long errorvalues_id, Long errortype_id, Long label_number) {
		try {
			ErrorValues eValue = new ErrorValues();
			eValue.setErrorvalues_id(errorvalues_id);
			eValue.setErrortype_id(errortype_id);
			eValue.setDeleted("false");
			eValue.setStarttime(new Date());
			eValue.setLabel_number(label_number);
			Long newerrorvalues_id = (Long) getHibernateTemplate().save(eValue);
			return newerrorvalues_id;
		} catch (HibernateException ex) {
			log.error("[addErrorType]: " + ex);
		} catch (Exception ex2) {
			log.error("[addErrorType]: " + ex2);
		}
		return null;
	}	
	
	public Long getErrorValueById(Long errortype_id, Long label_number) {
		try {
			ErrorValues eValue = new ErrorValues();
			eValue.setErrortype_id(errortype_id);
			eValue.setStarttime(new Date());
			eValue.setLabel_number(label_number);
			Long newerrorvalues_id = (Long) getHibernateTemplate().save(eValue);
			return newerrorvalues_id;
		} catch (HibernateException ex) {
			log.error("[addErrorType]: " + ex);
		} catch (Exception ex2) {
			log.error("[addErrorType]: " + ex2);
		}
		return null;
	}	
	
	public Long updateErrorValues(Long errortype_id, Long label_number) {
		try {
			ErrorValues eValue = new ErrorValues();
			eValue.setErrortype_id(errortype_id);
			eValue.setStarttime(new Date());
			eValue.setLabel_number(label_number);
			Long newerrorvalues_id = (Long) getHibernateTemplate().save(eValue);
			return newerrorvalues_id;
		} catch (HibernateException ex) {
			log.error("[addErrorType]: " + ex);
		} catch (Exception ex2) {
			log.error("[addErrorType]: " + ex2);
		}
		return null;
	}	
	 
	public ErrorValues getErrorValuesById(Long errorvalues_id) {
		try {
			String hql = "select c from ErrorValues as c " +
					" where c.errorvalues_id = :errorvalues_id " +
					" AND c.deleted != :deleted";
			Query query = getSession().createQuery(hql);
			query.setLong("errorvalues_id", errorvalues_id);
			query.setString("deleted", "true");
			ErrorValues e = (ErrorValues) query.uniqueResult();
			return e;
		} catch (HibernateException ex) {
			log.error("[getErrorValuesById]",ex);
		} catch (Exception ex2) {
			log.error("[getErrorValuesById]",ex2);
		}
		return null;
	}		
}
