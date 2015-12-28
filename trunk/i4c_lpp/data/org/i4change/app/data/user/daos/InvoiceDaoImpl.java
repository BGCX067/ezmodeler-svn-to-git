package org.i4change.app.data.user.daos;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.dt.payment.InvoiceDTO;
import org.i4change.app.dt.payment.TransactionPaypalDTO;
import org.i4change.app.dto.user.OrganisationDTO;
import org.i4change.app.dto.user.UserMinimalDTO;
import org.i4change.app.hibernate.beans.user.Invoice;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class InvoiceDaoImpl extends HibernateDaoSupport {
	
	private static final Log log = LogFactory.getLog(InvoiceDaoImpl.class);

	public Long addInvoice(Invoice invoice){
		try {
			
			if (invoice.getInvoiceId() != null && invoice.getInvoiceId() > 0) {
				log.warn("invoice UPDATE instead of INSERT "+invoice.getInvoiceId());
				this.updateInvoice(invoice);
				return invoice.getInvoiceId();
			}

			Long invoiceId = (Long) getHibernateTemplate().save(invoice);
			
			return invoiceId;
			
		} catch (HibernateException ex) {
			log.error("[addInvoice]",ex);
		} catch (Exception ex2) {
			log.error("[addInvoice]",ex2);
		}
		return null;
	}
	
	public void updateInvoice(Invoice invoice){
		try {

			getHibernateTemplate().update(invoice);
			
		} catch (HibernateException ex) {
			log.error("[updateInvoice]",ex);
		} catch (Exception ex2) {
			log.error("[updateInvoice]",ex2);
		}
	}

	public Invoice getInvoiceById(long invoiceId){
		try {
		
			String hql = "SELECT c FROM Invoice c " +
					"WHERE c.invoiceId = :invoiceId ";
			
			//get all users of this Organization
			Query query = getSession().createQuery(hql);
			query.setLong("invoiceId", invoiceId);
			Invoice invoice = (Invoice) query.uniqueResult();
			
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
			Query query = getSession().createQuery(hql);
			query.setLong("user_id", user_id);
			query.setMaxResults(max);
			query.setFirstResult(start);
			List<Invoice> invoiceList = query.list();
			
			LinkedList<InvoiceDTO> invoiceListDTO = new LinkedList();
			
			for (int i=0;i<invoiceList.size();i++) {
				invoiceListDTO.add(this.morphInvoiceToDTO(invoiceList.get(i)));
			}
			
			sresult.setResult(invoiceListDTO);
			
			return sresult;				
		} catch (HibernateException ex) {
			log.error("[getUsersListByOrganization] ",ex);
		} catch (Exception ex2) {
			log.error("[getUsersListByOrganization] ",ex2);
		}
		return null;
	}

	private InvoiceDTO morphInvoiceToDTO(Invoice invoice) {
		try {
			InvoiceDTO invoiceDTO = new InvoiceDTO();
			
			invoiceDTO.setDescription(invoice.getDescription());
			invoiceDTO.setInserted(invoice.getInserted());
			invoiceDTO.setInsertedFormated(invoice.getInsertedFormated());
			invoiceDTO.setInvoiceFileName(invoice.getInvoiceFileName());
			invoiceDTO.setInvoiceId(invoice.getInvoiceId());
			invoiceDTO.setInvoiceNumber(invoice.getInvoiceNumber());
			invoiceDTO.setInvoicePdfName(invoice.getInvoicePdfName());
			invoiceDTO.setInvoiceString(invoice.getInvoiceString());
			invoiceDTO.setMergedXmlData_FileName(invoice.getMergedXmlData_FileName());
			
			invoiceDTO.setTransactionPaypal(new TransactionPaypalDTO());
			
			if (invoice.getTransactionPaypal() != null) {
				
				if (invoice.getTransactionPaypal().getUsers() != null) {
					invoiceDTO.getTransactionPaypal().setUsers(new UserMinimalDTO());
					invoiceDTO.getTransactionPaypal().getUsers().setFirstname(invoice.getTransactionPaypal().getUsers().getFirstname());
					invoiceDTO.getTransactionPaypal().getUsers().setLastname(invoice.getTransactionPaypal().getUsers().getLastname());
					invoiceDTO.getTransactionPaypal().getUsers().setLogin(invoice.getTransactionPaypal().getUsers().getLogin());
					invoiceDTO.getTransactionPaypal().getUsers().setUser_id(invoice.getTransactionPaypal().getUsers().getUser_id());
				}
				
				if (invoice.getTransactionPaypal().getOrganisation() != null) {
					invoiceDTO.getTransactionPaypal().setOrganisation(new OrganisationDTO());
					invoiceDTO.getTransactionPaypal().getOrganisation().setName(invoice.getTransactionPaypal().getOrganisation().getName());
					invoiceDTO.getTransactionPaypal().getOrganisation().setOrganisation_id(invoice.getTransactionPaypal().getOrganisation().getOrganisation_id());
				}
				
				invoiceDTO.getTransactionPaypal().setAddress_1(invoice.getTransactionPaypal().getAddress_1());
				invoiceDTO.getTransactionPaypal().setAddress_2(invoice.getTransactionPaypal().getAddress_2());
				invoiceDTO.getTransactionPaypal().setAmount(invoice.getTransactionPaypal().getAmount());
				invoiceDTO.getTransactionPaypal().setAmountStartTransaction(invoice.getTransactionPaypal().getAmountStartTransaction());
				invoiceDTO.getTransactionPaypal().setCity(invoice.getTransactionPaypal().getCity());
				invoiceDTO.getTransactionPaypal().setCountryAsName(invoice.getTransactionPaypal().getCountryAsName());
				invoiceDTO.getTransactionPaypal().setCountryCode(invoice.getTransactionPaypal().getCountryCode());
				invoiceDTO.getTransactionPaypal().setFirstName(invoice.getTransactionPaypal().getFirstName());
				invoiceDTO.getTransactionPaypal().setFirstNameBilling(invoice.getTransactionPaypal().getFirstNameBilling());
				invoiceDTO.getTransactionPaypal().setInserted(invoice.getTransactionPaypal().getInserted());
				invoiceDTO.getTransactionPaypal().setIsControlled(invoice.getTransactionPaypal().getIsControlled());
				invoiceDTO.getTransactionPaypal().setLastName(invoice.getTransactionPaypal().getLastName());
				invoiceDTO.getTransactionPaypal().setLastNameBilling(invoice.getTransactionPaypal().getLastNameBilling());
				invoiceDTO.getTransactionPaypal().setNumberOfLicenses(invoice.getTransactionPaypal().getNumberOfLicenses());
				invoiceDTO.getTransactionPaypal().setPaidDate(invoice.getTransactionPaypal().getPaidDate());
				invoiceDTO.getTransactionPaypal().setStartTransaction(invoice.getTransactionPaypal().getStartTransaction());
				invoiceDTO.getTransactionPaypal().setState(invoice.getTransactionPaypal().getState());
				invoiceDTO.getTransactionPaypal().setStatus(invoice.getTransactionPaypal().getStatus());
				invoiceDTO.getTransactionPaypal().setTransactionId(invoiceDTO.getTransactionPaypal().getTransactionId());
				invoiceDTO.getTransactionPaypal().setTransActionPaypalId(invoice.getTransactionPaypal().getTransActionPaypalId());
				invoiceDTO.getTransactionPaypal().setUpdated(invoice.getTransactionPaypal().getUpdated());
				invoiceDTO.getTransactionPaypal().setZip(invoiceDTO.getTransactionPaypal().getZip());
				
			}
			
			return invoiceDTO;
			
		} catch (Exception err) {
			log.error("[morphInvoiceToDTO]",err);
		}
		return null;
	}

	public Long selectMaxTransactionsByUser(long user_id){
		try {
			
			String hql = "SELECT COUNT(c.transactionPaypal.transactionId) FROM Invoice c " +
					"WHERE c.transactionPaypal.transActionPaypalId IS NOT NULL " +
					"AND c.transactionPaypal.users.user_id = :user_id ";
			
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

}
