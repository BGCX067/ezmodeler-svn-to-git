package org.i4change.app.data.user.daos;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.i4change.app.data.user.EmailDaoImpl;
import org.i4change.app.hibernate.beans.adresses.Adresses_Emails;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class AddressesEmailsDaoImpl extends HibernateDaoSupport {
	
	private static final Log log = LogFactory.getLog(AddressesEmailsDaoImpl.class);

	//Spring loaded beans
	private EmailDaoImpl emailDaoImpl;
	
	public EmailDaoImpl getEmailDaoImpl() {
		return emailDaoImpl;
	}
	public void setEmailDaoImpl(EmailDaoImpl emailDaoImpl) {
		this.emailDaoImpl = emailDaoImpl;
	}

	public Long addAdressesEmails(Long adresses_id, Long mail_id) {
		try {
			Adresses_Emails addEmails = new Adresses_Emails();
			addEmails.setAdresses_id(adresses_id);
			addEmails.setDeleted("false");
			addEmails.setMail(this.emailDaoImpl.getEmailById(mail_id));
			addEmails.setStarttime(new Date());
			
			Long addEmailsId = (Long) getHibernateTemplate().save(addEmails);
			
			return addEmailsId;
		} catch (HibernateException ex) {
			log.error("[addAdressesEmails]" ,ex);
		} catch (Exception ex2) {
			log.error("[addAdressesEmails]" ,ex2);
		}
		return null;
	}

}
