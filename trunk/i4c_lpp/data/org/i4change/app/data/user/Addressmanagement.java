package org.i4change.app.data.user;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.i4change.app.hibernate.beans.adresses.Adresses;
import org.i4change.app.hibernate.beans.adresses.Country;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class Addressmanagement extends HibernateDaoSupport {

	private static final Log log = LogFactory.getLog(Addressmanagement.class);
	
	//Spring loaded beans
	private CountryDaoImpl countryDaoImpl;

	public CountryDaoImpl getCountryDaoImpl() {
		return countryDaoImpl;
	}
	public void setCountryDaoImpl(CountryDaoImpl countryDaoImpl) {
		this.countryDaoImpl = countryDaoImpl;
	}

	/**
	 * adds a new record to the adress table
	 * @param street
	 * @param zip
	 * @param town
	 * @param states_id
	 * @param additionalname
	 * @param comment
	 * @param fax
	 * @return id of generated Adress-Object or NULL
	 */
	public Long saveAddress(String street, String zip, String town,
			long states_id, String additionalname, String comment, String fax) {
		try {
			Country st = this.countryDaoImpl.getCountryById(states_id);

			Adresses adr = new Adresses();
			adr.setAdditionalname(additionalname);
			adr.setComment(comment);
			adr.setStarttime(new Date());
			adr.setFax(fax);
			adr.setStreet(street);
			adr.setTown(town);
			adr.setZip(zip);
			adr.setStates(st);

			Long id = (Long) getHibernateTemplate().save(adr);

			log.debug("added id " + id);

			return id;
		} catch (HibernateException ex) {
			log.error(ex);
		} catch (Exception ex2) {
			log.error(ex2);
		}
		return null;
	}
	
	public Long saveAddressByCompanyProfile(String street, String zip, String town,
			long states_id, String additionalname, String comment, String fax,
			String phone, String mobile) {
		try {
			Country st = this.countryDaoImpl.getCountryById(states_id);

			Adresses adr = new Adresses();
			adr.setAdditionalname(additionalname);
			adr.setComment(comment);
			adr.setStarttime(new Date());
			adr.setFax(fax);
			adr.setStreet(street);
			adr.setPhone(phone);
			adr.setMobile(mobile);
			adr.setTown(town);
			adr.setZip(zip);
			adr.setStates(st);

			Long id = (Long) getHibernateTemplate().save(adr);

			log.debug("added id " + id);

			return id;
		} catch (HibernateException ex) {
			log.error(ex);
		} catch (Exception ex2) {
			log.error(ex2);
		}
		return null;
	}
	
	public void saveAddressByObject(Adresses adr) {
		try {
			
			getHibernateTemplate().saveOrUpdate(adr);
		
		} catch (HibernateException ex) {
			log.error(ex);
		} catch (Exception ex2) {
			log.error(ex2);
		}
	}
	

	/**
	 * gets an adress by its id
	 * @param adresses_id
	 * @return Adress-Object or NULL
	 */
	public Adresses getAdressbyId(long adresses_id) {
		try {
			String hql = "select c from Adresses as c where c.adresses_id = :adresses_id";
			Query query = getSession().createQuery(hql);
			query.setLong("adresses_id", adresses_id);
			Adresses addr = (Adresses) query.uniqueResult();
			return addr;
		} catch (HibernateException ex) {
			log.error(ex);
		} catch (Exception ex2) {
			log.error(ex2);
		}
		return null;
	}
	
	/**
	 * updates an Adress-Record by its given Id
	 * @param adresses_id
	 * @param street
	 * @param zip
	 * @param town
	 * @param states_id
	 * @param additionalname
	 * @param comment
	 * @param fax
	 * @return the updated Adress-Object or null
	 */
	public Adresses updateAdress(long adresses_id, String street, String zip, String town,
			long states_id, String additionalname, String comment, String fax) {
		try {
			Country st = this.countryDaoImpl.getCountryById(states_id);
			
			Adresses adr = this.getAdressbyId(adresses_id);

			adr.setAdditionalname(additionalname);
			adr.setComment(comment);
			adr.setUpdatetime(new Date());
			adr.setFax(fax);
			adr.setStreet(street);
			adr.setTown(town);
			adr.setZip(zip);
			adr.setStates(st);

			getHibernateTemplate().update(adr);


			return adr;
		} catch (HibernateException ex) {
			log.error(ex);
		} catch (Exception ex2) {
			log.error(ex2);
		}
		return null;
	}
	
	/**
	 * 
	 * @param addr
	 * @return
	 */
	public Adresses updateAdress(Adresses addr) {
		try {
			
			log.debug("STATE: "+addr.getStates());
			if (addr.getStates() != null) {
				log.debug("UPDATE STATE WITH: "+addr.getStates().getName());
			}
			
			Adresses addrRemote = this.getAdressbyId(addr.getAdresses_id());
			addr.setEmails(addrRemote.getEmails());

			getHibernateTemplate().update(addr);
			//session.flush();

			return addr;
		} catch (HibernateException ex) {
			log.error("[updateAdress]",ex);
		} catch (Exception ex2) {
			log.error("[updateAdress]",ex2);
		}
		return null;
	}	

}
