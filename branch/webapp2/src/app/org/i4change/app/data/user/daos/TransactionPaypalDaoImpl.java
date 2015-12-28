package org.i4change.app.data.user.daos;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.hibernate.beans.user.Discount;
import org.i4change.app.hibernate.beans.user.TransactionPaypal;
import org.i4change.app.hibernate.beans.user.Users;
import org.i4change.app.hibernate.utils.HibernateUtil;

public class TransactionPaypalDaoImpl {
	
	private static final Log log = LogFactory.getLog(TransactionPaypalDaoImpl.class);

	private static TransactionPaypalDaoImpl instance = null;

	private TransactionPaypalDaoImpl() {
	}

	public static synchronized TransactionPaypalDaoImpl getInstance() {
		if (instance == null) {
			instance = new TransactionPaypalDaoImpl();
		}
		return instance;
	}
	
	public Long addTransactionByUser(Long userId, String amount, Long numberOfLicenses){
		try {
			Users user = UserDaoImpl.getInstance().getUserById(userId);
			
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
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Long transactionPaypalId = (Long) session.save(transactionPaypal);
			tx.commit();
			HibernateUtil.closeSession(idf);
			
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
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			session.update(transactionPaypal);
			tx.commit();
			HibernateUtil.closeSession(idf);
			
		} catch (HibernateException ex) {
			log.error("[updateTransaction]",ex);
		} catch (Exception ex2) {
			log.error("[updateTransaction]",ex2);
		}
	}

	public TransactionPaypal getTransactionPayedById(Long transactionId) {
		try {
			
			String hql = "select c from TransactionPaypal as c " +
						"where c.transactionId = :transactionId ";
	
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("transactionId", transactionId);
			TransactionPaypal transaction = (TransactionPaypal) query.uniqueResult();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
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
	
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setString("status", status);
			List<TransactionPaypal> transactionPaypals = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
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
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Query query = session.createQuery(hql);
			Transaction tx = session.beginTransaction();
			query.setLong("user_id", user_id);
			query.setMaxResults(max);
			query.setFirstResult(start);
			sresult.setResult(query.list());
			tx.commit();
			HibernateUtil.closeSession(idf);
			
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
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("user_id", user_id);
			List ll = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			
			log.info((Long)ll.get(0));
			return (Long)ll.get(0);	
			
		} catch (HibernateException ex) {
			log.error("[selectMaxTransactionsByUser] ",ex);
		} catch (Exception ex2) {
			log.error("[selectMaxTransactionsByUser] ",ex2);
		}
		return null;
	}
	
}
