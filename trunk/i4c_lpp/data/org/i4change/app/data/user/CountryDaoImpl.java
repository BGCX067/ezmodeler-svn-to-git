package org.i4change.app.data.user;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.i4change.app.hibernate.beans.adresses.Country;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * 
 * @author swagner
 * 
 */
public class CountryDaoImpl extends HibernateDaoSupport {

	private static final Log log = LogFactory.getLog(CountryDaoImpl.class);

	/**
	 * adds a new State to the states table
	 * 
	 * @param statename
	 * @return the id of the new state or null if an error occurred
	 */
	public Long addCountry(String statename, String paypal) {
		try {
			// Object idf = HibernateUtil.createSession();
			// Session session = HibernateUtil.getSession();
			// Transaction tx = session.beginTransaction();

			Country st = new Country();
			st.setName(statename);
			st.setPaypal(paypal);
			st.setStarttime(new Date());
			st.setDeleted("false");

			Long id = (Long) getHibernateTemplate().save(st);

			// tx.commit();
			// HibernateUtil.closeSession(idf);

			log.debug("added id " + id);

			return id;
		} catch (HibernateException ex) {
			log.error(ex);
		} catch (Exception ex2) {
			log.error(ex2);
		}
		return null;
	}

	/**
	 * selects a state by its id
	 * 
	 * @param state_id
	 * @return the state-object or null
	 */
	public Country getCountryById(long country_id) {
		try {
			// Object idf = HibernateUtil.createSession();
			// Session session = HibernateUtil.getSession();
			// Transaction tx = session.beginTransaction();
			Query query = getSession()
					.createQuery(
							"select c from Country as c where c.country_id = :country_id AND deleted != :deleted");
			query.setLong("country_id", country_id);
			query.setString("deleted", "true");
			List ll = query.list();
			// tx.commit();
			// HibernateUtil.closeSession(idf);
			if (ll.size() > 0) {
				return (Country) ll.get(0);
			}
		} catch (HibernateException ex) {
			log.error(ex);
		} catch (Exception ex2) {
			log.error(ex2);
		}
		return null;
	}

	/**
	 * 
	 * selects a state by its id
	 * 
	 * @param paypal
	 * @return the country-object or null
	 */
	public Country getCountryByPaypalCode(String paypal) {
		try {
			// Object idf = HibernateUtil.createSession();
			// Session session = HibernateUtil.getSession();
			// Transaction tx = session.beginTransaction();
			Query query = getSession()
					.createQuery(
							"select c from Country as c where c.paypal LIKE :paypal AND deleted != :deleted");
			query.setString("paypal", paypal);
			query.setString("deleted", "true");
			List ll = query.list();
			// tx.commit();
			// HibernateUtil.closeSession(idf);
			if (ll.size() > 0) {
				return (Country) ll.get(0);
			}
		} catch (HibernateException ex) {
			log.error(ex);
		} catch (Exception ex2) {
			log.error(ex2);
		}
		return null;
	}

	/**
	 * Get all state-Object
	 * 
	 * @return List of State Objects or null
	 */
	public List<Country> getCountry() {
		try {
			// Object idf = HibernateUtil.createSession();
			// Session session = HibernateUtil.getSession();
			// Transaction tx = session.beginTransaction();
			Query query = getSession()
					.createQuery(
							"select c from Country as c where deleted != :deleted AND paypal != :paypal");
			query.setString("deleted", "true");
			query.setString("paypal", "");
			List<Country> ll = query.list();
			// tx.commit();
			// HibernateUtil.closeSession(idf);
			return ll;
		} catch (HibernateException ex) {
			log.error(ex);
		} catch (Exception ex2) {
			log.error(ex2);
		}
		return null;
	}

}
