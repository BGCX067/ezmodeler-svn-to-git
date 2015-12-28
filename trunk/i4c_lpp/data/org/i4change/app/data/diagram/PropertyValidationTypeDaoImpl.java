package org.i4change.app.data.diagram;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.i4change.app.hibernate.beans.diagram.PropertyValidationType;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class PropertyValidationTypeDaoImpl extends HibernateDaoSupport {
	
	private static final Log log = LogFactory.getLog(PropertyValidationTypeDaoImpl.class);	
	
	public void addDefaultPropertyValidationTypes() {
		try {
			this.addPropertyValidationType(1008L, "none", null);
			this.addPropertyValidationType(1009L, "email", null);
			this.addPropertyValidationType(1010L, "date", null);
			this.addPropertyValidationType(1011L, "float", null);
			//this.addPropertyValidationType(1012L, "phone");
			this.addPropertyValidationType(1013L, "number", null);
			this.addPropertyValidationType(1014L, "time", null);
			this.addPropertyValidationType(1315L, "none", 100);
			this.addPropertyValidationType(1354L, "list", null);
		} catch (Exception err) {
			log.error("[addDefaultPropertyValidationTypes]",err);
		}
	}
	
	public Long addPropertyValidationType(Long labelId, String validationValue, Integer height) {
		try {
			
			PropertyValidationType propertyValidationType = new PropertyValidationType();
			propertyValidationType.setLabelId(labelId);
			propertyValidationType.setValidationValue(validationValue);
			propertyValidationType.setInserted(new Date());
			propertyValidationType.setHeight(height);

			Long propertyValidationTypeId = (Long) getHibernateTemplate().save(propertyValidationType);

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

			Query query = getSession().createQuery(hql);

			List<PropertyValidationType> propertyValidationTypes = query.list();

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
