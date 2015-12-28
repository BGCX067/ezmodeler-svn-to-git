package org.i4change.app.data.domain.daos;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.i4change.app.hibernate.beans.user.Discount;
import org.i4change.app.hibernate.utils.HibernateUtil;

public class OrganisationsDiscountDaoImpl {

	private static final Log log = LogFactory.getLog(OrganisationsDiscountDaoImpl.class);

	private static OrganisationsDiscountDaoImpl instance = null;

	public static synchronized OrganisationsDiscountDaoImpl getInstance() {
		if (instance == null) {
			instance = new OrganisationsDiscountDaoImpl();
		}
		return instance;
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
			orgDiscount.setOrganisation(OrganisationDaoImpl.getInstance().getOrganisationById(organisation_id));
			orgDiscount.setInserted(new Date());
			orgDiscount.setDeleted("false");
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			
			long id = (Long) session.save(orgDiscount);
			
			tx.commit();
			HibernateUtil.closeSession(idf);
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

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("organisation_id", organisation_id);
			query.setString("deleted", "true");
			List<Discount> orgList = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);

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
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			
			session.update(orgDiscount);
			
			tx.commit();
			HibernateUtil.closeSession(idf);
			
		} catch (HibernateException ex) {
			log.error("[deleteOrganisationsDiscount]" ,ex);
		} catch (Exception ex2) {
			log.error("[deleteOrganisationsDiscount]" ,ex2);
		}
	}

}
