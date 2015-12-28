package org.i4change.app.data.basic;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.i4change.app.hibernate.beans.basic.ObjectIdentifier;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class ObjectIdentifierDaoImpl extends HibernateDaoSupport {

	private static final Log log = LogFactory.getLog(ObjectIdentifierDaoImpl.class);

	public Long getCurrentObjectIdentifier(long organization_id) {
		try {
			
			// Criteria crit = session.createCriteria();
			Query query = getSession().createQuery("select c from ObjectIdentifier as c " +
					"where c.organization_id = :organization_id");
			query.setLong("organization_id", organization_id);
			ObjectIdentifier obj = (ObjectIdentifier) query.uniqueResult();

			log.debug("obj "+obj);
			
			if (obj == null) {
				Long objectidentifier_id = this.addCurrentObjectIdentifier(organization_id);
				obj = this.getCurrentObjectIdentifierById(objectidentifier_id);
			}
			
			long returnVal = obj.getCurrentid();
			
			this.updateCurrentObjectIdentifier(obj);
			
			return returnVal;
		} catch (HibernateException ex) {
			log.error(ex);
		} catch (Exception ex2) {
			log.error(ex2);
		}
		return null;
	}
	

	public List<ObjectIdentifier> getCurrentObjectIdentifiers() {
		try {
			
			Query query = getSession().createQuery("FROM ObjectIdentifier");
			List<ObjectIdentifier> obj = query.list();

			log.debug("obj "+obj);
			
			return obj;
		} catch (HibernateException ex) {
			log.error("[getCurrentObjectIdentifiers]",ex);
		} catch (Exception ex2) {
			log.error("[getCurrentObjectIdentifiers]",ex2);
		}
		return null;
	}
	
	public Long addNewObjectIdentifier(ObjectIdentifier objIdent) {
		try {
			// Criteria crit = session.createCriteria();
			long objIdent_id = (Long) getHibernateTemplate().save(objIdent);
			
			log.debug("objIdent_id "+objIdent_id);
			return objIdent_id;
		} catch (HibernateException ex) {
			log.error(ex);
		} catch (Exception ex2) {
			log.error(ex2);
		}
		return null;
	}

	private Long addCurrentObjectIdentifier(long organization_id) {
		try {
			ObjectIdentifier objIdent = new ObjectIdentifier();
			objIdent.setOrganization_id(organization_id);
			objIdent.setUpdated(new Date());
			objIdent.setCurrentid(new Long(0));
			// Criteria crit = session.createCriteria();
			long objIdent_id = (Long) getHibernateTemplate().save(objIdent);
			
			log.debug("objIdent_id "+objIdent_id);
			return objIdent_id;
		} catch (HibernateException ex) {
			log.error(ex);
		} catch (Exception ex2) {
			log.error(ex2);
		}
		return null;
	}
	
	public void updateCurrentObjectIdentifier(ObjectIdentifier objIdent) {
		try {
			objIdent.setUpdated(new Date());
			objIdent.setCurrentid(objIdent.getCurrentid()+1);
			// Criteria crit = session.createCriteria();
			getHibernateTemplate().update(objIdent);
//			session.flush();
//			session.update(objIdent);
//			session.clear();
//			session.refresh(objIdent);
		} catch (HibernateException ex) {
			log.error(ex);
		} catch (Exception ex2) {
			log.error(ex2);
		}
	}
	
	public ObjectIdentifier getCurrentObjectIdentifierById(long objectidentifier_id) {
		try {
			
			// Criteria crit = session.createCriteria();
			Query query = getSession().createQuery("select c from ObjectIdentifier as c " +
					"where c.objectidentifier_id = :objectidentifier_id");
			query.setLong("objectidentifier_id", objectidentifier_id);
			ObjectIdentifier obj = (ObjectIdentifier) query.uniqueResult();
			
			log.debug("obj "+obj);
			
			return obj;
		} catch (HibernateException ex) {
			log.error(ex);
		} catch (Exception ex2) {
			log.error(ex2);
		}
		return null;
	}

	
}
