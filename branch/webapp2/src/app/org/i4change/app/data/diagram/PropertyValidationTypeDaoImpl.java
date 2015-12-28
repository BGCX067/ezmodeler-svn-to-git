package org.i4change.app.data.diagram;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.i4change.app.hibernate.beans.diagram.PropertyValidationType;
import org.i4change.app.hibernate.utils.HibernateUtil;

public class PropertyValidationTypeDaoImpl {
	
	private static final Log log = LogFactory.getLog(PropertyValidationTypeDaoImpl.class);	
	
	private static PropertyValidationTypeDaoImpl instance = null;

	public static synchronized PropertyValidationTypeDaoImpl getInstance() {
		if (instance == null) {
			instance = new PropertyValidationTypeDaoImpl();
		}
		return instance;
	}
	
	public void addDefaultPropertyValidationTypes() {
		try {
			this.addPropertyValidationType(1008L, "none");
			this.addPropertyValidationType(1009L, "email");
			this.addPropertyValidationType(1010L, "date");
			this.addPropertyValidationType(1011L, "float");
			//this.addPropertyValidationType(1012L, "phone");
			this.addPropertyValidationType(1013L, "number");
			this.addPropertyValidationType(1014L, "time");
		} catch (Exception err) {
			log.error("[addDefaultPropertyValidationTypes]",err);
		}
	}
	
	public Long addPropertyValidationType(Long labelId, String validationValue) {
		try {
			
			PropertyValidationType propertyValidationType = new PropertyValidationType();
			propertyValidationType.setLabelId(labelId);
			propertyValidationType.setValidationValue(validationValue);
			propertyValidationType.setInserted(new Date());

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Long propertyValidationTypeId = (Long) session.save(propertyValidationType);

			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("select propertyValidationTypeId " + propertyValidationTypeId);

			return propertyValidationTypeId;
			
		} catch (HibernateException ex) {
			log.error("[addPropertyValidationType]",ex);
		} catch (Exception ex2) {
			log.error("[addPropertyValidationType]",ex2);
		}
		return new Long(-1);
	}
	
	public List<PropertyValidationType> getPropertyValidationTypes() {
		try {
			String hql = "from PropertyValidationType";

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			
			Query query = session.createQuery(hql);

			List<PropertyValidationType> propertyValidationTypes = query.list();

			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("select propertyValidationTypes " + propertyValidationTypes);

			return propertyValidationTypes;
			
		} catch (HibernateException ex) {
			log.error("[getPropertyValidationTypes]",ex);
		} catch (Exception ex2) {
			log.error("[getPropertyValidationTypes]",ex2);
		}
		return null;
	}

}
