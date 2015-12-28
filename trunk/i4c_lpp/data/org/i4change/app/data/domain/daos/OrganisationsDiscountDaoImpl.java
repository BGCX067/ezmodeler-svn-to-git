package org.i4change.app.data.domain.daos;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.i4change.app.hibernate.beans.user.Discount;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class OrganisationsDiscountDaoImpl extends HibernateDaoSupport {

	private static final Log log = LogFactory.getLog(OrganisationsDiscountDaoImpl.class);

	//Spring loaded Beans
	private OrganisationDaoImpl organisationDaoImpl = null;
	
	public OrganisationDaoImpl getOrganisationDaoImpl() {
		return organisationDaoImpl;
	}
	public void setOrganisationDaoImpl(OrganisationDaoImpl organisationDaoImpl) {
		this.organisationDaoImpl = organisationDaoImpl;
	}

	/**
	 * @deprecated
	 * @param organisation_id
	 * @param numberOfUsers
	 * @param discount
	 * @return
	 */
	public Long addOrganisationsDiscount(long organisation_id, int numberOfUsers, double discount) {
		try {
			Discount orgDiscount = new Discount();
			orgDiscount.setDiscount(discount);
			orgDiscount.setNumberOfUsers(numberOfUsers);
			orgDiscount.setOrganisation(this.organisationDaoImpl.getOrganisationById(organisation_id));
			orgDiscount.setInserted(new Date());
			orgDiscount.setDeleted("false");
			
			long id = (Long) getHibernateTemplate().save(orgDiscount);
			
			return id;
		} catch (HibernateException ex) {
			log.error("[addOrganisationsDiscount]" ,ex);
		} catch (Exception ex2) {
			log.error("[addOrganisationsDiscount]" ,ex2);
		}
		return null;
	}
	
	public List<Discount> getOrganisationsDiscountsByOrg(Long organisation_id) {
		try {
			String hql = "select c from OrganisationsDiscount as c "
					+ "where c.organisation.organisation_id = :organisation_id "
					+ "AND deleted != :deleted";

			Query query = getSession().createQuery(hql);
			query.setLong("organisation_id", organisation_id);
			query.setString("deleted", "true");
			List<Discount> orgList = query.list();

			return orgList;

		} catch (HibernateException ex) {
			log.error("[getOrganisationsDiscountByOrg]", ex);
		} catch (Exception ex2) {
			log.error("[getOrganisationsDiscountByOrg]", ex2);
		}
		return null;
	}
	
	public void deleteOrganisationsDiscount (Discount orgDiscount) {
		try {
			
			orgDiscount.setDeleted("true");
			
			getHibernateTemplate().update(orgDiscount);
			
		} catch (HibernateException ex) {
			log.error("[deleteOrganisationsDiscount]" ,ex);
		} catch (Exception ex2) {
			log.error("[deleteOrganisationsDiscount]" ,ex2);
		}
	}

}
