package org.i4change.app.data.user.daos;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.hibernate.beans.user.Invoice;
import org.i4change.app.hibernate.utils.HibernateUtil;

public class InvoiceDaoImpl {
	
	private static final Log log = LogFactory.getLog(InvoiceDaoImpl.class);

	private static InvoiceDaoImpl instance = null;

	private InvoiceDaoImpl() {
	}

	public static synchronized InvoiceDaoImpl getInstance() {
		if (instance == null) {
			instance = new InvoiceDaoImpl();
		}
		return instance;
	}
	
	public Long addInvoice(Invoice invoice){
		try {

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Long invoiceId = (Long) session.save(invoice);
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return invoiceId;
			
		} catch (HibernateException ex) {
			log.error("[addInvoice]",ex);
		} catch (Exception ex2) {
			log.error("[addInvoice]",ex2);
		}
		return null;
	}

	public Invoice getInvoiceById(long invoiceId){
		try {
		
			String hql = "SELECT c FROM Invoice c " +
					"WHERE c.invoiceId = :invoiceId ";
			
			//get all users of this Organization
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Query query = session.createQuery(hql);
			Transaction tx = session.beginTransaction();
			query.setLong("invoiceId", invoiceId);
			Invoice invoice = (Invoice) query.uniqueResult();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return invoice;				
		} catch (HibernateException ex) {
			log.error("[getInvoiceById] ",ex);
		} catch (Exception ex2) {
			log.error("[getInvoiceById] ",ex2);
		}
		return null;
	}
	
	public SearchResult getTransactionsByUser(int start, int max, String orderby, boolean asc, Long user_id){
		try {
		
			String hql = "SELECT c FROM Invoice c " +
					"WHERE c.transactionPaypal.transActionPaypalId IS NOT NULL " +
					"AND c.transactionPaypal.users.user_id = :user_id " +
					"ORDER BY "+orderby;
			
			if (asc) {
				hql +=" ASC";
			} else {
				hql +=" DESC";
			}
			
			SearchResult sresult = new SearchResult();
			sresult.setObjectName(Invoice.class.getName());
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
			
			String hql = "SELECT COUNT(c.transactionPaypal.transactionId) FROM Invoice c " +
					"WHERE c.transactionPaypal.transActionPaypalId IS NOT NULL " +
					"AND c.transactionPaypal.users.user_id = :user_id ";
			
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
