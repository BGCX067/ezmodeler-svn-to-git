package org.i4change.app.data.user.daos;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.hibernate.beans.user.TransactionPaypal;
import org.i4change.app.hibernate.beans.user.Users;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class TransactionPaypalDaoImpl extends HibernateDaoSupport {
	
	private static final Log log = LogFactory.getLog(TransactionPaypalDaoImpl.class);

	//Spring loaded Beans
	private UserDaoImpl userDaoImpl;
	
	public UserDaoImpl getUserDaoImpl() {
		return userDaoImpl;
	}
	public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}
	
	public Long addTransactionByUser(Long userId, String amount, Long numberOfLicenses){
		try {
			
			log.debug("addTransactionByUser amount: "+amount);
			
			Users user = this.userDaoImpl.getUserById(userId);
			
			TransactionPaypal transactionPaypal = new TransactionPaypal();
			transactionPaypal.setUsers(user);
			transactionPaypal.setAmountStartTransaction(amount);
			transactionPaypal.setStartTransaction(new Date());
			transactionPaypal.setNumberOfLicenses(numberOfLicenses);
			
			//Billing Information
			transactionPaypal.setAddress_1(user.getAdresses().getStreet()+" "+user.getAdresses().getAdditionalname());
			transactionPaypal.setAddress_2("");
			//transactionPaypal.setState(state)
			transactionPaypal.setCountryCode(user.getAdresses().getStates().getPaypal());
			transactionPaypal.setCity(user.getAdresses().getTown());
			transactionPaypal.setZip(user.getAdresses().getZip());
			
			transactionPaypal.setFirstName(user.getFirstname());
			transactionPaypal.setLastName(user.getLastname());
			
			Long transactionPaypalId = (Long) getHibernateTemplate().save(transactionPaypal);
			
			return transactionPaypalId;
		} catch (HibernateException ex) {
			log.error("[addTransactionByUser]",ex);
		} catch (Exception ex2) {
			log.error("[addTransactionByUser]",ex2);
		}
		return null;
	}
	
	public void updateTransaction(TransactionPaypal transactionPaypal){
		try {
			
			getHibernateTemplate().update(transactionPaypal);
			
		} catch (HibernateException ex) {
			log.error("[updateTransaction]",ex);
		} catch (Exception ex2) {
			log.error("[updateTransaction]",ex2);
		}
	}
	
	public void updateTransactionStatus(String transactionPaypalId, String status){
		try {
			
			TransactionPaypal transactionPaypal = this.getTransactionByPaypalId(transactionPaypalId);
			
			if (transactionPaypal != null) {
				transactionPaypal.setStatus(status);
				
				this.updateTransaction(transactionPaypal);
			} else {
				//log.debug("Could not Find transactionPaypalId "+transactionPaypalId);
			}
		} catch (HibernateException ex) {
			log.error("[updateTransactionStatus]",ex);
		} catch (Exception ex2) {
			log.error("[updateTransactionStatus]",ex2);
		}
	}
	
	public TransactionPaypal getTransactionByPaypalId(String transActionPaypalId) {
		try {
			
			String hql = "select c from TransactionPaypal as c " +
						"where c.transActionPaypalId LIKE :transActionPaypalId ";
	
			Query query = getSession().createQuery(hql);
			query.setString("transActionPaypalId", transActionPaypalId);
			TransactionPaypal transaction = (TransactionPaypal) query.uniqueResult();
			
			return transaction;
		} catch (HibernateException ex) {
			log.error("[getTransactionByPaypalId]",ex);
		} catch (Exception ex2) {
			log.error("[getTransactionByPaypalId]",ex2);
		}
		return null;
	}

	public TransactionPaypal getTransactionPayedById(Long transactionId) {
		try {
			
			String hql = "select c from TransactionPaypal as c " +
						"where c.transactionId = :transactionId ";
	
			Query query = getSession().createQuery(hql);
			query.setLong("transactionId", transactionId);
			TransactionPaypal transaction = (TransactionPaypal) query.uniqueResult();
			
			return transaction;
		} catch (HibernateException ex) {
			log.error("[getDiscountById]",ex);
		} catch (Exception ex2) {
			log.error("[getDiscountById]",ex2);
		}
		return null;
	}

	public List<TransactionPaypal> getTransactionPayedByStatus(String status) {
		try {
			
			String hql = "select c from TransactionPaypal as c " +
						"where c.transActionPaypalId IS NOT NULL " +
						"AND c.isControlled IS NULL " +
						"AND c.status LIKE :status";
	
			Query query = getSession().createQuery(hql);
			query.setString("status", status);
			List<TransactionPaypal> transactionPaypals = query.list();
			
			return transactionPaypals;
		} catch (HibernateException ex) {
			log.error("[getDiscountById]",ex);
		} catch (Exception ex2) {
			log.error("[getDiscountById]",ex2);
		}
		return null;
	}
	
	public SearchResult getTransactionsByUser(int start, int max, String orderby, boolean asc, Long user_id){
		try {
		
			String hql = "SELECT c FROM TransactionPaypal c " +
					"WHERE c.transActionPaypalId IS NOT NULL " +
					"AND c.users.user_id = :user_id " +
					"ORDER BY "+orderby;
			
			if (asc) {
				hql +=" ASC";
			} else {
				hql +=" DESC";
			}
			
			SearchResult sresult = new SearchResult();
			sresult.setObjectName(TransactionPaypal.class.getName());
			sresult.setRecords(this.selectMaxTransactionsByUser(user_id));
			
			//get all users of this Organization
			Query query = getSession().createQuery(hql);
			query.setLong("user_id", user_id);
			query.setMaxResults(max);
			query.setFirstResult(start);
			sresult.setResult(query.list());
			
			return sresult;				
		} catch (HibernateException ex) {
			log.error("[getUsersListByOrganization] ",ex);
		} catch (Exception ex2) {
			log.error("[getUsersListByOrganization] ",ex2);
		}
		return null;
	}

	public Long selectMaxTransactionsByUser(long user_id){
		try {
			
			String hql = "SELECT COUNT(c.transactionId) FROM TransactionPaypal c " +
					"WHERE c.transActionPaypalId IS NOT NULL " +
					"AND c.users.user_id = :user_id ";
			
			Query query = getSession().createQuery(hql);
			query.setLong("user_id", user_id);
			List ll = query.list();
			
			log.info((Long)ll.get(0));
			return (Long)ll.get(0);	
			
		} catch (HibernateException ex) {
			log.error("[selectMaxTransactionsByUser] ",ex);
		} catch (Exception ex2) {
			log.error("[selectMaxTransactionsByUser] ",ex2);
		}
		return null;
	}

	/**
	 * @param id
	 * @return
	 */
	public TransactionPaypal getTransactionTransactionById(Long transactionId) {
		try {
			
			String hql = "select c from TransactionPaypal as c " +
						"where c.transactionId = :transactionId";
	
			Query query = getSession().createQuery(hql);
			query.setLong("transactionId", transactionId);
			TransactionPaypal transactionPaypal = (TransactionPaypal) query.uniqueResult();
			
			return transactionPaypal;
		} catch (HibernateException ex) {
			log.error("[getTransactionTransactionById]",ex);
		} catch (Exception ex2) {
			log.error("[getTransactionTransactionById]",ex2);
		}
		return null;
	}
	
	public void addAndUpdateTransaction(Long transaction_id, String transactionpaypalid, String status, 
			String amount, Date transferDate, String firstName, String lastName,
			String address1, String address2, String city, String state, String zip, String countryCode,
			String firstNameBilling, String lastNameBilling, Long invoiceId) {
		try {
			
			TransactionPaypal tPaypal = this.getTransactionPayedById(transaction_id);
			
			tPaypal.setTransActionPaypalId(transactionpaypalid);
			tPaypal.setStatus(status);
			tPaypal.setAmount(amount);
			tPaypal.setPaidDate(new Date());
			tPaypal.setInserted(new Date());
			tPaypal.setFirstName(firstName);
			tPaypal.setLastName(lastName);
			tPaypal.setAddress_1(address1);
			tPaypal.setAddress_2(address2);
			tPaypal.setCity(city);
			tPaypal.setState(state);
			tPaypal.setZip(zip);
			tPaypal.setCountryCode(countryCode);
			tPaypal.setFirstNameBilling(firstNameBilling);
			tPaypal.setLastNameBilling(lastNameBilling);
			tPaypal.setInvoiceId(invoiceId);
			
			this.updateTransaction(tPaypal);
			
		} catch (HibernateException ex) {
			log.error("[addAndUpdateTransaction]",ex);
		} catch (Exception ex2) {
			log.error("[addAndUpdateTransaction]",ex2);
		}
		
	}
	
}
