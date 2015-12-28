package org.i4change.app.data.user.daos;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.i4change.app.data.user.EmailDaoImpl;
import org.i4change.app.hibernate.beans.adresses.Adresses_Emails;
import org.i4change.app.hibernate.utils.HibernateUtil;

public class AddressesEmailsDaoImpl {
	
	private static final Log log = LogFactory.getLog(AddressesEmailsDaoImpl.class);

	private static AddressesEmailsDaoImpl instance = null;

	private AddressesEmailsDaoImpl() {
	}

	public static synchronized AddressesEmailsDaoImpl getInstance() {
		if (instance == null) {
			instance = new AddressesEmailsDaoImpl();
		}
		return instance;
	}
	
	public Long addAdressesEmails(Long adresses_id, Long mail_id) {
		try {
			Adresses_Emails addEmails = new Adresses_Emails();
			addEmails.setAdresses_id(adresses_id);
			addEmails.setDeleted("false");
			addEmails.setMail(EmailDaoImpl.getInstance().getEmailById(mail_id));
			addEmails.setStarttime(new Date());
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Long addEmailsId = (Long) session.save(addEmails);
			tx.commit();
			HibernateUtil.closeSession(idf);
			return addEmailsId;
		} catch (HibernateException ex) {
			log.error("[addAdressesEmails]" ,ex);
		} catch (Exception ex2) {
			log.error("[addAdressesEmails]" ,ex2);
		}
		return null;
	}

}
