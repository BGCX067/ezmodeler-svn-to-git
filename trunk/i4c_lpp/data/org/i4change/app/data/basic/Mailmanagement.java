package org.i4change.app.data.basic;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.mail.MailItemServiceDaoImpl;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.hibernate.beans.adresses.Adresses_Emails;
import org.i4change.app.hibernate.beans.lang.Fieldlanguagesvalues;
import org.i4change.app.hibernate.beans.mail.MailItem;
import org.i4change.app.hibernate.beans.user.Users;
import org.i4change.app.templates.PaymentReceivedTemplate;
import org.i4change.app.utils.mail.MailHandler;
import org.i4change.app.utils.math.CalendarPatterns;

public class Mailmanagement {
	
	//Spring loaded beans
	private MailItemServiceDaoImpl mailItemServiceDaoImpl;
	private Fieldmanagment fieldmanagment;
	private UserDaoImpl userDaoImpl;
	private Configurationmanagement configurationmanagement;
	private MailHandler mailHandler;

	public Configurationmanagement getConfigurationmanagement() {
		return configurationmanagement;
	}
	public void setConfigurationmanagement(
			Configurationmanagement configurationmanagement) {
		this.configurationmanagement = configurationmanagement;
	}
	
	public UserDaoImpl getUserDaoImpl() {
		return userDaoImpl;
	}
	public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}
	public Fieldmanagment getFieldmanagment() {
		return fieldmanagment;
	}
	public void setFieldmanagment(Fieldmanagment fieldmanagment) {
		this.fieldmanagment = fieldmanagment;
	}
	
	public MailHandler getMailHandler() {
		return mailHandler;
	}
	public void setMailHandler(MailHandler mailHandler) {
		this.mailHandler = mailHandler;
	}

	private static final Log log = LogFactory.getLog(Mailmanagement.class);	

	public MailItemServiceDaoImpl getMailItemServiceDaoImpl() {
		return mailItemServiceDaoImpl;
	}
	public void setMailItemServiceDaoImpl(
			MailItemServiceDaoImpl mailItemServiceDaoImpl) {
		this.mailItemServiceDaoImpl = mailItemServiceDaoImpl;
	}



	public void sendMails(){
		try {
			
			List<MailItem> listMailItems = this.mailItemServiceDaoImpl.getMailItems();
			
			for (MailItem mailItem : listMailItems) {
				//First set it to send status, cause otherwise an Exception in the MailHandler could due to an endless loop
				this.mailItemServiceDaoImpl.updateMailItem(mailItem.getMailItemId(), "TRIED TO SEND");
				
				String succ = this.mailHandler.sendMail(mailItem.getReceipent(), mailItem.getSubject(), mailItem.getContent(), mailItem.getFilePath());
				
				//log.debug ("SUCC: "+succ);
				this.mailItemServiceDaoImpl.updateMailItem(mailItem.getMailItemId(), succ);
				
			}
			//Mailhandler.sendMail(String toEmail, String subj, String message);
		} catch (Exception err) {
			log.error("[sendMails]",err);
		}
	}
	
	public void addMailToSpoolAboutNewPendingObject(Long user_id, Long assigneeUserId, 
			String diagramName, Long default_lang_id, String ObjectName) {
		try {
			
			Users creator = this.userDaoImpl.getUserById(user_id);
			
			Users user = this.userDaoImpl.getUserById(assigneeUserId);
			
			Adresses_Emails mail = null;
			Iterator it = user.getAdresses().getEmails().iterator();
			if (it.hasNext()){
				mail = (Adresses_Emails) it.next();
			}				
			String receipent = mail.getMail().getEmail();
			//TODO: check before sending the mail if the mail-adress is valid would be nice
			
			Fieldlanguagesvalues labelid734 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(734), default_lang_id);
			Fieldlanguagesvalues labelid735 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(735), default_lang_id);
			Fieldlanguagesvalues labelid736 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(736), default_lang_id);
			Fieldlanguagesvalues labelid737 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(737), default_lang_id);
			Fieldlanguagesvalues labelid742 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(742), default_lang_id);

			String subject = labelid734.getValue()+" ["+diagramName+"]"+ " " + "["+ObjectName+"]";
			
			String content = labelid735.getValue();
			content += labelid736.getValue() + diagramName;
			content += labelid742.getValue() + ObjectName;
			content += labelid737.getValue() + creator.getLogin();
			
			//TODO: This is duplicated/ignored
			String from = this.configurationmanagement.getConfKey(3,"system_email_addr").getConf_value();
			
			this.mailItemServiceDaoImpl.addMailItem(user_id, from, receipent, subject, content, null);
			
		} catch (Exception err) {
			log.error("[addMailToSpoolAboutNewPendingObject]",err);
		}
	}
	
	public void addMailToSpoolAboutChangedPendingObject(Long user_id, Long assigneeUserId, 
			String diagramName, Long default_lang_id, String ObjectName) {
		try {
			
			Users creator = this.userDaoImpl.getUserById(user_id);
			
			Users user = this.userDaoImpl.getUserById(assigneeUserId);
			
			Adresses_Emails mail = null;
			Iterator it = user.getAdresses().getEmails().iterator();
			if (it.hasNext()){
				mail = (Adresses_Emails) it.next();
			}				
			String receipent = mail.getMail().getEmail();
			//TODO: check before sending the mail if the mail-adress is valid would be nice
			
			Fieldlanguagesvalues labelid739 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(739), default_lang_id);
			Fieldlanguagesvalues labelid738 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(738), default_lang_id);
			Fieldlanguagesvalues labelid736 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(736), default_lang_id);
			Fieldlanguagesvalues labelid737 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(737), default_lang_id);
			Fieldlanguagesvalues labelid742 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(742), default_lang_id);

			String subject = labelid739.getValue()+" ["+diagramName+"]"+ " " + "["+ObjectName+"]";
			
			String content = labelid738.getValue();
			content += labelid736.getValue() + diagramName;
			content += labelid742.getValue() + ObjectName;
			content += labelid737.getValue() + creator.getLogin();
			
			//TODO: This is duplicated/ignored
			String from = this.configurationmanagement.getConfKey(3,"system_email_addr").getConf_value();
			
			this.mailItemServiceDaoImpl.addMailItem(user_id, from, receipent, subject, content, null);
			
		} catch (Exception err) {
			log.error("[addMailToSpoolAboutChangedPendingObject]",err);
		}
	}

	public void addMailToSpoolAboutApprovedPendingObject(Long user_id, Long creatoruserId, 
			String diagramName, Long default_lang_id, String ObjectName) {
		try {
			
			Users creator = this.userDaoImpl.getUserById(creatoruserId);
			
			Users user = this.userDaoImpl.getUserById(creatoruserId);
			
			Adresses_Emails mail = null;
			Iterator it = user.getAdresses().getEmails().iterator();
			if (it.hasNext()){
				mail = (Adresses_Emails) it.next();
			}				
			String receipent = mail.getMail().getEmail();
			//TODO: check before sending the mail if the mail-address is valid would be nice
			
			Fieldlanguagesvalues labelid740 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(740), default_lang_id);
			Fieldlanguagesvalues labelid741 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(741), default_lang_id);
			Fieldlanguagesvalues labelid736 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(736), default_lang_id);
			Fieldlanguagesvalues labelid737 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(737), default_lang_id);
			Fieldlanguagesvalues labelid742 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(742), default_lang_id);

			String subject = labelid740.getValue()+" ["+diagramName+"]"+ " " + "["+ObjectName+"]";
			
			String content = labelid741.getValue();
			content += labelid736.getValue() + diagramName;
			content += labelid742.getValue() + ObjectName;
			content += labelid737.getValue() + creator.getLogin();
			
			//TODO: This is duplicated/ignored
			String from = this.configurationmanagement.getConfKey(3,"system_email_addr").getConf_value();
			
			this.mailItemServiceDaoImpl.addMailItem(user_id, from, receipent, subject, content, null);
			
		} catch (Exception err) {
			log.error("[addMailToSpoolAboutApprovedPendingObject]",err);
		}
	}

	public void addMailToSpoolAboutNewIssue(Long user_id, Long creatoruserId, 
			String diagramName, Long default_lang_id, String ObjectName) {
		try {
			
			Users creator = this.userDaoImpl.getUserById(creatoruserId);
			
			Users user = this.userDaoImpl.getUserById(creatoruserId);
			
			Adresses_Emails mail = null;
			Iterator it = user.getAdresses().getEmails().iterator();
			if (it.hasNext()){
				mail = (Adresses_Emails) it.next();
			}				
			String receipent = mail.getMail().getEmail();
			//TODO: check before sending the mail if the mail-adress is valid would be nice
			
			Fieldlanguagesvalues labelid743 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(743), default_lang_id);
			Fieldlanguagesvalues labelid744 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(744), default_lang_id);
			Fieldlanguagesvalues labelid736 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(736), default_lang_id);
			Fieldlanguagesvalues labelid737 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(737), default_lang_id);
			Fieldlanguagesvalues labelid742 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(742), default_lang_id);

			String subject = labelid743.getValue()+" ["+diagramName+"]"+ " " + "["+ObjectName+"]";
			
			String content = labelid744.getValue();
			content += labelid736.getValue() + diagramName;
			content += labelid742.getValue() + ObjectName;
			content += labelid737.getValue() + creator.getLogin();
			
			//TODO: This is duplicated/ignored
			String from = this.configurationmanagement.getConfKey(3,"system_email_addr").getConf_value();
			
			this.mailItemServiceDaoImpl.addMailItem(user_id, from, receipent, subject, content, null);
			
		} catch (Exception err) {
			log.error("[addMailToSpoolAboutApprovedPendingObject]",err);
		}
	}
	
	public void addMailToSpoolAboutPaymentReceived(Users user, String filePath) {
		try {
			
			Long default_lang_id = Long.valueOf(this.configurationmanagement.getConfKey(3,"default_lang_id").getConf_value()).longValue();
			
			if (user.getLanguage_id() != null) {
				default_lang_id = user.getLanguage_id();
			}
			
			Adresses_Emails mail = null;
			Iterator it = user.getAdresses().getEmails().iterator();
			if (it.hasNext()){
				mail = (Adresses_Emails) it.next();
			}				
			String receipent = mail.getMail().getEmail();
			//TODO: check before sending the mail if the mail-adress is valid would be nice
			
			Fieldlanguagesvalues labelid1136 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(1136), default_lang_id);
			Fieldlanguagesvalues labelid1137 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(1137), default_lang_id);
			Fieldlanguagesvalues labelid1138 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(1138), default_lang_id);
			
			String subject = labelid1138.getValue();
			
			String content = PaymentReceivedTemplate.getInstance().getResetPasswordTemplate(labelid1136.getValue(), labelid1137.getValue());
			
			//TODO: This is duplicated/ignored
			String from = this.configurationmanagement.getConfKey(3,"system_email_addr").getConf_value();
			
			this.mailItemServiceDaoImpl.addMailItem(user.getUser_id(), from, receipent, subject, content, filePath);
			
		} catch (Exception err) {
			log.error("[addMailToSpoolAboutApprovedPendingObject]",err);
		}
	}

	public void addMailToSpoolAboutUpdatedIssue(Long user_id, Long creatoruserId, 
			String diagramName, Long default_lang_id, String ObjectName) {
		try {
			
			Users creator = this.userDaoImpl.getUserById(creatoruserId);
			
			Users user = this.userDaoImpl.getUserById(creatoruserId);
			
			Adresses_Emails mail = null;
			Iterator it = user.getAdresses().getEmails().iterator();
			if (it.hasNext()){
				mail = (Adresses_Emails) it.next();
			}				
			String receipent = mail.getMail().getEmail();
			//TODO: check before sending the mail if the mail-adress is valid would be nice
			
			Fieldlanguagesvalues labelid745 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(745), default_lang_id);
			Fieldlanguagesvalues labelid746 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(746), default_lang_id);
			Fieldlanguagesvalues labelid736 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(736), default_lang_id);
			Fieldlanguagesvalues labelid737 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(737), default_lang_id);
			Fieldlanguagesvalues labelid742 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(742), default_lang_id);
			
			String subject = labelid745.getValue()+" ["+diagramName+"]"+ " " + "["+ObjectName+"]";
			
			String content = labelid746.getValue();
			content += labelid736.getValue() + diagramName;
			content += labelid742.getValue() + ObjectName;
			content += labelid737.getValue() + creator.getLogin();
			
			//TODO: This is duplicated/ignored
			String from = this.configurationmanagement.getConfKey(3,"system_email_addr").getConf_value();
			
			this.mailItemServiceDaoImpl.addMailItem(user_id, from, receipent, subject, content, null);
			
		} catch (Exception err) {
			log.error("[addMailToSpoolAboutApprovedPendingObject]",err);
		}
	}

	public void addMailToSpoolAboutRemovedPendingObject(Long user_id, Long creatoruserId, 
			String diagramName, Long default_lang_id, String ObjectName) {
		try {
			
			Users creator = this.userDaoImpl.getUserById(creatoruserId);
			
			Users user = this.userDaoImpl.getUserById(creatoruserId);
			
			Adresses_Emails mail = null;
			Iterator it = user.getAdresses().getEmails().iterator();
			if (it.hasNext()){
				mail = (Adresses_Emails) it.next();
			}				
			String receipent = mail.getMail().getEmail();
			//TODO: check before sending the mail if the mail-address is valid would be nice
			
			Fieldlanguagesvalues labelid748 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(748), default_lang_id);
			Fieldlanguagesvalues labelid749 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(749), default_lang_id);
			Fieldlanguagesvalues labelid736 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(736), default_lang_id);
			Fieldlanguagesvalues labelid737 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(737), default_lang_id);
			Fieldlanguagesvalues labelid742 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(742), default_lang_id);
			
			String subject = labelid748.getValue()+" ["+diagramName+"]"+ " " + "["+ObjectName+"]";
			
			String content = labelid749.getValue();
			content += labelid736.getValue() + diagramName;
			content += labelid742.getValue() + ObjectName;
			content += labelid737.getValue() + creator.getLogin();
			
			//TODO: This is duplicated/ignored
			String from = this.configurationmanagement.getConfKey(3,"system_email_addr").getConf_value();
			
			this.mailItemServiceDaoImpl.addMailItem(user_id, from, receipent, subject, content, null);
			
		} catch (Exception err) {
			log.error("[addMailToSpoolAboutApprovedPendingObject]",err);
		}
	}

	public void addMailToSpoolAboutPendingOrganization(Long user_id, String receipent, String organizationName, 
				Date inserted,	Long default_lang_id, String userHash) {
		try {
			
			String system_link_buy_license = this.configurationmanagement.getConfKey(3,"system_link_buy_license").getConf_value();
			String system_link_unregister_from_list = this.configurationmanagement.getConfKey(3,"system_link_unregister_from_list").getConf_value();
			
			//TODO: check before sending the mail if the mail-address is valid would be nice
			
			Fieldlanguagesvalues labelid929 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(929), default_lang_id);
			Fieldlanguagesvalues labelid930 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(930), default_lang_id);
			Fieldlanguagesvalues labelid931 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(931), default_lang_id);
			Fieldlanguagesvalues labelid932 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(932), default_lang_id);
			Fieldlanguagesvalues labelid933 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(933), default_lang_id);
			Fieldlanguagesvalues labelid934 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(934), default_lang_id);
			Fieldlanguagesvalues labelid935 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(935), default_lang_id);
			Fieldlanguagesvalues labelid936 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(936), default_lang_id);
						
			String subject = labelid929.getValue()+" ["+organizationName+"]";
			
			String content = labelid930.getValue();
			content += labelid931.getValue();
			content += labelid932.getValue() + organizationName;
			content += labelid933.getValue() + CalendarPatterns.getDateWithTimeByMiliSeconds(inserted);
			content += labelid934.getValue();
			//content += "<a href='"+system_link_buy_license+"' target='_BLANK'>"+labelid935.getValue()+"</a><br/><br/>";
			content +="<a href='"+system_link_unregister_from_list+"?uid="+userHash+"' target='_BLANK'>"+labelid936.getValue()+"</a><br/>";
			
			String from = this.configurationmanagement.getConfKey(3,"system_email_addr").getConf_value();
			
			this.mailItemServiceDaoImpl.addMailItem(user_id, from, receipent, subject, content, null);
			
		} catch (Exception err) {
			log.error("[addMailToSpoolAboutApprovedPendingObject]",err);
		}
	}
	
	public void addMailToSpoolAboutReportOfPendingOrganizations(Long user_id, String receipent, 
			LinkedList<LinkedHashMap<String,String>> sendedReminderOrgs, Long default_lang_id) {
		try {
			
			log.debug("addMailToSpoolAboutReportOfPendingOrganizations "+sendedReminderOrgs);
			
			//TODO: check before sending the mail if the mail-address is valid would be nice
			
			Fieldlanguagesvalues labelid937 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(937), default_lang_id);
			Fieldlanguagesvalues labelid938 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(938), default_lang_id);
			Fieldlanguagesvalues labelid939 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(939), default_lang_id);
			Fieldlanguagesvalues labelid940 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(940), default_lang_id);
			Fieldlanguagesvalues labelid941 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(941), default_lang_id);
						
			String subject = labelid937.getValue();
			
			String content = labelid938.getValue();
			content += "<table border='1'>" +
					"<tr>" +
					"<th>"+labelid939.getValue()+"</th>"+
					"<th>"+labelid940.getValue()+"</th>"+
					"<th>"+labelid941.getValue()+"</th>"+
					"</tr>" +
					"";
					
			for (LinkedHashMap<String,String> sendedReminderOrg : sendedReminderOrgs) {
				content += 	"<tr>" +
							"<td>"+sendedReminderOrg.get("orgName")+"("+sendedReminderOrg.get("orgId")+")"+"</td>"+
							"<td>"+sendedReminderOrg.get("orgCreationDate")+"</td>"+
							"<td>"+sendedReminderOrg.get("reminderUsers")+"</td>"+
							"</tr>";
			}
			content += "</table>";
			
			
			String from = this.configurationmanagement.getConfKey(3,"system_email_addr").getConf_value();
			
			this.mailItemServiceDaoImpl.addMailItem(user_id, from, receipent, subject, content, null);
			
		} catch (Exception err) {
			log.error("[addMailToSpoolAboutApprovedPendingObject]",err);
		}
	}

	
	public void addMailToSpoolAboutNewUser(Long user_id, Long inviteduser_id, String organisationName, 
			String userName, String pass, Long default_lang_id) {
		try {
			
			Users user = this.userDaoImpl.getUserById(inviteduser_id);
			
			Adresses_Emails mail = null;
			Iterator it = user.getAdresses().getEmails().iterator();
			if (it.hasNext()){
				mail = (Adresses_Emails) it.next();
			}				
			String receipent = mail.getMail().getEmail();
			//TODO: check before sending the mail if the mail-address is valid would be nice
			
			Fieldlanguagesvalues labelid960 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(960), default_lang_id);
			Fieldlanguagesvalues labelid961 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(961), default_lang_id);
			Fieldlanguagesvalues labelid962 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(962), default_lang_id);
			Fieldlanguagesvalues labelid963 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(963), default_lang_id);
			Fieldlanguagesvalues labelid964 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(964), default_lang_id);
			
			String subject = labelid960.getValue()+" ["+organisationName+"]";
			
			String content = labelid961.getValue();
			content += " "+ labelid962.getValue() + userName;
			content += " "+ labelid963.getValue() + pass;
			content += " "+ labelid964.getValue() + organisationName;
			
			//TODO: This is duplicated/ignored
			String from = this.configurationmanagement.getConfKey(3,"system_email_addr").getConf_value();
			
			this.mailItemServiceDaoImpl.addMailItem(user_id, from, receipent, subject, content, null);
			
		} catch (Exception err) {
			log.error("[addMailToSpoolAboutApprovedPendingObject]",err);
		}
	}
	
	public void addMailToSpoolAboutFailedAutoAssign(Long user_id, Long orgModeratorUserId, String organisationName, 
			String userName, String email, Long default_lang_id) {
		try {
			
			Users user = this.userDaoImpl.getUserById(orgModeratorUserId);
			
			Adresses_Emails mail = null;
			Iterator it = user.getAdresses().getEmails().iterator();
			if (it.hasNext()){
				mail = (Adresses_Emails) it.next();
			}				
			String receipent = mail.getMail().getEmail();
			//TODO: check before sending the mail if the mail-address is valid would be nice
			
			Fieldlanguagesvalues labelid976 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(976), default_lang_id);
			Fieldlanguagesvalues labelid977 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(977), default_lang_id);
			Fieldlanguagesvalues labelid978 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(978), default_lang_id);
			Fieldlanguagesvalues labelid979 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(979), default_lang_id);
			
			String subject = labelid976.getValue()+" ["+organisationName+"]";
			
			String content = labelid977.getValue();
			content += " "+ labelid978.getValue() + userName;
			content += " "+ labelid979.getValue() + email;
			
			//TODO: This is duplicated/ignored
			String from = this.configurationmanagement.getConfKey(3,"system_email_addr").getConf_value();
			
			this.mailItemServiceDaoImpl.addMailItem(user_id, from, receipent, subject, content, null);
			
		} catch (Exception err) {
			log.error("[addMailToSpoolAboutFailedAutoAssign]",err);
		}
	}
	
	public void addMailToSpoolAboutFailedAutoAssignToSysAdmin(Long user_id, Long orgModeratorUserId, String organisationName, 
			String userName, String email, Long default_lang_id) {
		try {
			
			Users user = this.userDaoImpl.getUserById(orgModeratorUserId);
			
			Adresses_Emails mail = null;
			Iterator it = user.getAdresses().getEmails().iterator();
			if (it.hasNext()){
				mail = (Adresses_Emails) it.next();
			}				
			String receipent = mail.getMail().getEmail();
			//TODO: check before sending the mail if the mail-address is valid would be nice
			
			Fieldlanguagesvalues labelid976 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(976), default_lang_id);
			Fieldlanguagesvalues labelid980 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(980), default_lang_id);
			Fieldlanguagesvalues labelid978 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(978), default_lang_id);
			Fieldlanguagesvalues labelid979 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(979), default_lang_id);
			
			String subject = labelid976.getValue()+" ["+organisationName+"]";
			
			String content = labelid980.getValue();
			content += " "+ labelid978.getValue() + userName;
			content += " "+ labelid979.getValue() + email;
			
			//TODO: This is duplicated/ignored
			String from = this.configurationmanagement.getConfKey(3,"system_email_addr").getConf_value();
			
			this.mailItemServiceDaoImpl.addMailItem(user_id, from, receipent, subject, content, null);
			
		} catch (Exception err) {
			log.error("[addMailToSpoolAboutFailedAutoAssign]",err);
		}
	}
	
}