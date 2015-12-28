package org.i4change.app.data.user;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import org.i4change.app.data.basic.Configurationmanagement;
import org.i4change.app.data.basic.Fieldmanagment;
import org.i4change.app.hibernate.beans.adresses.Emails;
import org.i4change.app.utils.mail.MailHandler;
import org.i4change.app.hibernate.beans.adresses.Adresses_Emails;
import org.i4change.app.hibernate.beans.lang.Fieldlanguagesvalues;
import org.i4change.app.hibernate.beans.user.Users;
import org.i4change.app.templates.RegisterUserTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class EmailDaoImpl extends HibernateDaoSupport {

	private static final Log log = LogFactory.getLog(EmailDaoImpl.class);

	//Spring loaded beans
	private RegisterUserTemplate registerUserTemplate;
	private Configurationmanagement configurationmanagement;
	private MailHandler mailHandler;
	private Fieldmanagment fieldmanagment;

	public Fieldmanagment getFieldmanagment() {
		return fieldmanagment;
	}
	public void setFieldmanagment(Fieldmanagment fieldmanagment) {
		this.fieldmanagment = fieldmanagment;
	}

	public Configurationmanagement getConfigurationmanagement() {
		return configurationmanagement;
	}
	public void setConfigurationmanagement(
			Configurationmanagement configurationmanagement) {
		this.configurationmanagement = configurationmanagement;
	}
	
	public RegisterUserTemplate getRegisterUserTemplate() {
		return registerUserTemplate;
	}
	public void setRegisterUserTemplate(RegisterUserTemplate registerUserTemplate) {
		this.registerUserTemplate = registerUserTemplate;
	}
	
	public MailHandler getMailHandler() {
		return mailHandler;
	}
	public void setMailHandler(MailHandler mailHandler) {
		this.mailHandler = mailHandler;
	}
	
	private boolean checkUserLevel(int user_level) {
		if (user_level > 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Get a Mail-Object by its Mail-Id
	 * @param mail_id
	 * @return
	 */
	public Emails getEmailById(long mail_id) {
		try {
			String hql = "select c from Emails as c " +
						"where c.mail_id = :mail_id " +
						"AND deleted != :deleted";
			
			Query query = getSession().createQuery(hql);
			query.setLong("mail_id", mail_id);
			query.setString("deleted", "true");
			List ll = query.list();
			if (ll.size() > 0) {
				return (Emails) ll.get(0);
			}
		} catch (HibernateException ex) {
			log.error("[getEmailById]" ,ex);
		} catch (Exception ex2) {
			log.error("[getEmailById]" ,ex2);
		}
		return null;
	}

	public List getemails(Long USER_ID) {
		try {
			Query query = getSession()
					.createQuery("select c from emails as c where c.USER_ID = :USER_ID");
			query.setLong("USER_ID", USER_ID.longValue());
			List lt = query.list();
			return lt;
		} catch (HibernateException ex) {
			log.error(ex);
		} catch (Exception ex2) {
			log.error(ex2);
		}
		return null;
	}
	
	public Adresses_Emails getAdresses_EmailsByMail(String email) {
		try {
			String hql = "select c from Adresses_Emails as c " +
					" where c.mail.email = :email ";
			Query query = getSession().createQuery(hql);
			query.setString("email", email);
			Adresses_Emails e = (Adresses_Emails) query.uniqueResult();
			return e;
		} catch (HibernateException ex) {
			log.error(ex);
		} catch (Exception ex2) {
			log.error(ex2);
		}
		return null;
	}	

	public List getemailsCon(int CONTACT_ID) {
		try {
			Query query = getSession()
					.createQuery("select c from emails as c where c.CONTACT_ID = :CONTACT_ID");
			query.setInteger("CONTACT_ID", CONTACT_ID);
			List lt = query.list();
			return lt;
		} catch (HibernateException ex) {
			log.error(ex);
		} catch (Exception ex2) {
			log.error(ex2);
		}
		return null;
	}

	/**
	 * Adds a mail with its connection to the adress table
	 * @param EMail
	 * @param adresses_id
	 * @param Username
	 * @param Userpass
	 * @param comment
	 * @param sendWelcomeMail
	 * @return the new mail_id or -1
	 */
	public Long registerEmail(String EMail, long adresses_id, String comment) {
		Long mail_id = this.registerEmail(EMail, comment);
		if (mail_id != null) {
			try {			
				
				Adresses_Emails addr_emails = new Adresses_Emails();
				addr_emails.setAdresses_id(adresses_id);
				addr_emails.setMail(this.getEmailById(mail_id));
				addr_emails.setStarttime(new Date());
				addr_emails.setDeleted("false");

				long addr_emails_id = (Long) getHibernateTemplate().save(addr_emails);
				log.error("registerEmail addr_emails: " + addr_emails_id);
				

				return mail_id;
			} catch (HibernateException ex) {
				log.error("Error: " ,ex);
			} catch (Exception ex2) {
				log.error("Error: " ,ex2);
			}
		}
		return null;
	}

	/**
	 * adds a mail-address to the mail table
	 * @param EMail
	 * @param Username
	 * @param Userpass
	 * @param comment
	 * @return
	 */
	public Long registerEmail(String EMail,	String comment) {
		try {
			Emails emails = new Emails();
			emails.setEmail(EMail);
			emails.setStarttime(new Date());
			emails.setComment(comment);
			emails.setDeleted("false");

			long email_id = (Long) getHibernateTemplate().save(emails);
			log.error("registerEmail id: " + email_id);

			return email_id;

		} catch (HibernateException ex) {
			log.error("Error: " ,ex);
		} catch (Exception ex2) {
			log.error("Error: " ,ex2);
		}

		return null;

	}

	/**
	 * sends a mail adress to the user with his account data
	 * @param Username
	 * @param Userpass
	 * @param EMail
	 * @return
	 * @throws Exception
	 */
	public String sendMail(String Username, String Userpass, String EMail, String activation_key) {
		String succ = "valid email";
		
		
		Integer sendEmailAtRegister = Integer.valueOf(this.configurationmanagement.
				getConfKey(3,"sendEmailAtRegister").getConf_value()).intValue();
		
		if (sendEmailAtRegister==1){
			
			Long default_lang_id = Long.valueOf(this.configurationmanagement.
	        		getConfKey(3,"default_lang_id").getConf_value()).longValue();
			
			String template = this.registerUserTemplate.getRegisterUserTemplate(Username,Userpass,EMail,default_lang_id, activation_key);
			Fieldlanguagesvalues label = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(512), default_lang_id);
			
			succ = this.mailHandler.sendMail(EMail, label.getValue(), template, null);
			return succ;
		} else {
			return "success";
		}
	}

	public String addEmailCon(String EMail, int CONTACT_ID) {
		String succ = "invalid email";
		//		Emails emails = new Emails();
		//		long time = CalenderI.getTimeStampMili();
		//		emails.setEmail(EMail);
		//		emails.setCONTACT_ID(CONTACT_ID);
		//		emails.setStartdate(time);
		//		emails.setUpdatedate(time);
		//        try {   
		//            Object idf = HibernateUtil.creategetSession()(); 			getSession() getSession() = HibernateUtil.getgetSession()();
		//            Transaction tx = getSession().beginTransaction();
		//            getHibernateTemplate().save(emails);
		//            getSession().flush();   
		//            session.clear();
		//            session.refresh(emails);
		//            tx.commit();
		//            HibernateUtil.closeSession(idf);   
		//            succ = "valid email";
		//        } catch( HibernateException ex ) {
		//        	succ = "Error: "+ex;
		//        } catch ( Exception ex2 ){
		//        	succ = "Error: "+ex2;
		//        }		
		return succ;
	}

	/**
	 * delete a Email-Object by a given Id
	 * @param mail_id
	 */
	public void deleteEMailByID(long mail_id) {
		try {
			Emails mail = this.getEmailById(mail_id);
			getSession().delete(mail);			
		} catch (HibernateException ex) {
			log.error(ex);
		} catch (Exception ex2) {
			log.error(ex2);
		}
	}

	//TODO: Change code sothat it doesn't usw HQL
	public String deleteEMailByUserID(int USER_ID) {
		String result = "Fehler im Bestellvorgang";
		try {
			String hqlDelete = "delete emails where USER_ID = :USER_ID";
			int deletedEntities = getSession().createQuery(hqlDelete).setInteger(
					"USER_ID", USER_ID).executeUpdate();
			//getSession().flush(); 

			result = "Erfolgreich" + deletedEntities;
		} catch (HibernateException ex) {
			log.error(ex);
		} catch (Exception ex2) {
			log.error(ex2);
		}
		return result;
	}

	/**
	 * Checks if a mail is already taken by someone else
	 * @param email
	 * @return
	 */
	public boolean checkUserEMail(String email) {
		try {
			String hql = "select c from Emails c, Adresses_Emails addrEm, Adresses addr, Users us " +
					"where c.email = :email " +
					"AND c.deleted != :deleted " +
					"AND addrEm.mail.mail_id = c.mail_id " +
					"AND addr.adresses_id = addrEm.adresses_id " +
					"AND us.adresses.adresses_id = addr.adresses_id " +
					"AND us.deleted != :deleted";
			if (email.length()==0) return true;
			log.error("checkUserMail: " + email);
			Query query = getSession().createQuery(hql);
			query.setString("email", email);
			query.setString("deleted", "true");
			int count = query.list().size();
			log.error("size: " + count);
			
			if (count > 0) {
				return false;
			}			
		} catch (HibernateException ex) {
			log.error("Error: " ,ex);
		} catch (Exception ex2) {
			log.error("Error: " ,ex2);
		}
		return true;
	}
	
	public List<Users> getForCheckUserEMail(String email) {
		try {
			String hql = "select us from Emails c, Adresses_Emails addrEm, Adresses addr, Users us " +
					"where c.email = :email " +
					"AND c.deleted != :deleted " +
					"AND addrEm.mail.mail_id = c.mail_id " +
					"AND addr.adresses_id = addrEm.adresses_id " +
					"AND us.adresses.adresses_id = addr.adresses_id " +
					"AND us.deleted != :deleted";
			
			log.error("getForCheckUserEMail: " + email);
			Query query = getSession().createQuery(hql);
			query.setString("email", email);
			query.setString("deleted", "true");
			List<Users> userList = query.list();
			log.error("size: " + userList.size());
			
			return userList;
		} catch (HibernateException ex) {
			log.error("getForCheckUserEMail: " ,ex);
		} catch (Exception ex2) {
			log.error("getForCheckUserEMail: " ,ex2);
		}
		return null;
	}

	/**
	 * update a Email-Object by a given id
	 * @param mail_id
	 * @param user_id
	 * @param email
	 * @return
	 */
	public Emails updateUserEmail(long mail_id, Long user_id, String email) {
		try {
			Emails mail = this.getEmailById(mail_id);
			mail.setEmail(email);
			mail.setUpdatetime(new Date());
            getHibernateTemplate().update(mail);
            return mail;
		} catch (HibernateException ex) {
			log.error("[updateUserEmail] "+ex);
		} catch (Exception ex2) {
			log.error("[updateUserEmail] "+ex2);
		}
		return null;
	}

	public String updateContactEmail(int MAIL_ID, int Contact_ID, String email) {
		String res = "Fehler beim Update";
		try {
			//            Object idf = HibernateUtil.creategetSession()(); 			Session session = HibernateUtil.getSession();
			//            Transaction tx = session.beginTransaction();        
			//            String hqlUpdate = "update emails set email= :email, CONTACT_ID = :CONTACT_ID, updatedate = :updatedate where MAIL_ID= :MAIL_ID";
			//            int updatedEntities = session.createQuery( hqlUpdate )
			//                                .setString("email",email)
			//                                .setInteger( "CONTACT_ID", Contact_ID )
			//                                .setLong( "updatedate", CalenderI.getTimeStampMili() )
			//                                .setInteger( "MAIL_ID", MAIL_ID )
			//                                .executeUpdate();
			//            res = "Success"+updatedEntities;
			//            tx.commit();
			//            HibernateUtil.closeSession(idf);
		} catch (HibernateException ex) {
			log.error(ex);
		} catch (Exception ex2) {
			log.error(ex2);
		}
		return res;
	}

}
