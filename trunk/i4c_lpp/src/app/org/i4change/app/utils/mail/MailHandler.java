package org.i4change.app.utils.mail;

import javax.mail.*;
import javax.mail.internet.*;

import java.io.File;
import java.util.*;

import javax.activation.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.Configurationmanagement;

/**
 * 
 * @author swagner
 * 
 */
public class MailHandler {

	private static final Log log = LogFactory.getLog(MailHandler.class);

	//Spring loaded beans
	private Configurationmanagement configurationmanagement;

	public Configurationmanagement getConfigurationmanagement() {
		return configurationmanagement;
	}
	public void setConfigurationmanagement(
			Configurationmanagement configurationmanagement) {
		this.configurationmanagement = configurationmanagement;
	}

	/**
	 * send mail to address
	 * 
	 * @param toEmail
	 * @param subj
	 * @param message
	 * @return
	 */
	public String sendMail(String toEmail, String subj, String message, String filePath) {
		try {

			// String smtpServer="smtp.xmlcrm.org";
			String smtpServer = this.configurationmanagement.getConfKey(3, "smtp_server").getConf_value();
			String smtpPort = this.configurationmanagement.getConfKey(3, "smtp_port").getConf_value();
			String to = toEmail;
			// String from = "openmeetings@xmlcrm.org";
			String from = this.configurationmanagement.getConfKey(3,"system_email_addr").getConf_value();
			String subject = subj;
			String body = message;
			
			String emailUsername = this.configurationmanagement.getConfKey(3, "email_username").getConf_value();
			String emailUserpass = this.configurationmanagement.getConfKey(3, "email_userpass").getConf_value();

			//return send(smtpServer, smtpPort, to, from, subject, body);
			return this.send(smtpServer, smtpPort, to, from, subject, body, emailUsername, emailUserpass, filePath);
		} catch (Exception ex) {
			log.error("[sendMail] " ,ex);
			return "Error: " + ex;
		}
	}

//	/**
//	 * Sending a mail with given values
//	 * 
//	 * @param smtpServer
//	 * @param to
//	 * @param from
//	 * @param subject
//	 * @param body
//	 * @return
//	 */
//	public static String send(String smtpServer, String smtpPort, String to, String from,
//			String subject, String body) {
//		try {
//
//			log.debug("Message sending in progress");
//
//			Properties props = System.getProperties();
//
//			// -- Attaching to default Session, or we could start a new one --
//			//smtpPort 25 or 587
//			props.put("mail.smtp.host", smtpServer);
//			props.put("mail.smtp.port", smtpPort);
//			props.put("mail.smtp.auth", "true");
//
//			Session session = Session.getDefaultInstance(props,
//					new SmtpAuthenticator());
//
//			// -- Create a new message --
//			Message msg = new MimeMessage(session);
//
//			// -- Set the FROM and TO fields --
//			msg.setFrom(new InternetAddress(from));
//			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(
//					to, false));
//
//			// -- We could include CC recipients too --
//			// if (cc != null)
//			// msg.setRecipients(Message.RecipientType.CC
//			// ,InternetAddress.parse(cc, false));
//
//			// -- Set the subject and body text --
//			msg.setSubject(subject);
//			msg.setDataHandler(new DataHandler(new ByteArrayDataSource(body
//					.toString(), "text/html")));
//			// msg.setContent(body, "text/html");
//
//			// -- Set some other header information --
//			msg.setHeader("X-Mailer", "XML-Mail");
//			msg.setSentDate(new Date());
//
//			// -- Send the message --
//			Transport.send(msg);
//
//			return "success";
//		} catch (Exception ex) {
//			log.error("[mail send] " ,ex);
//			return "Error" + ex;
//		}
//	}
	
	/**
	 * Sending a mail with given values.<br>
	 * If the parameter "emailUsername" and "emailUserpass" is exist, use SMTP Authentication.
	 * 
	 * @param smtpServer
	 * @param to
	 * @param from
	 * @param subject
	 * @param body
	 * @param emailUsername
	 * @param emailUserpass
	 * @return
	 */
	public String send(String smtpServer, String smtpPort, String to, String from,
			String subject, String body, String emailUsername, String emailUserpass, String filePath) {
		try {

			
			// add handlers for main MIME types
			MailcapCommandMap mc = (MailcapCommandMap)CommandMap.getDefaultCommandMap();
			mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
			mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
			mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
			mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
			mc.addMailcap("multipart/mixed;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
			mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
			CommandMap.setDefaultCommandMap(mc);
			
			log.debug("Message sending in progress");
//			smtpServer
//			smtpPort

			Properties props = System.getProperties();

			// -- Attaching to default Session, or we could start a new one --
			//smtpPort 25 or 587
			props.put("mail.smtp.host", smtpServer);
			props.put("mail.smtp.port", smtpPort);
			
			//String username = "openmeetings@xmlcrm.org";
			String username = this.configurationmanagement.getConfKey(3, "email_username").getConf_value();
	        //String password = "tony123";
			String password = this.configurationmanagement.getConfKey(3, "email_userpass").getConf_value();
		     

			Session session = null;
			if (emailUsername != null && emailUsername.length() > 0
					&& emailUserpass != null && emailUserpass.length() > 0) {
				//use SMTP Authentication
				props.put("mail.smtp.auth", "true");
				session = Session.getInstance(props,
						new SmtpAuthenticator(username,password));
			}else{
				//not use SMTP Authentication
				session = Session.getDefaultInstance(props, null);
			}


			// -- Create a new message --
			Message msg = new MimeMessage(session);

			// -- Set the FROM and TO fields --
			msg.setFrom(new InternetAddress(from));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(
					to, false));
			

			// -- We could include CC recipients too --
			// if (cc != null)
			// msg.setRecipients(Message.RecipientType.CC
			// ,InternetAddress.parse(cc, false));

			// -- Set the subject and body text --
			msg.setSubject(subject);
			
			//filePath
		    if (filePath == null || filePath.length() == 0) {
				msg.setDataHandler(new DataHandler(new ByteArrayDataSource(body
						.toString(), "text/html")));
				// msg.setContent(body, "text/html");
		    } else {
		    	
	    		MimeBodyPart mbp1 = new MimeBodyPart();
	    		mbp1.setDataHandler(new DataHandler(new ByteArrayDataSource(body
						.toString(), "text/html")));
	    		
	    		File file = new File(filePath);
	    		MimeBodyPart mbp2 = new MimeBodyPart();
				mbp2.setDataHandler(new DataHandler(new FileDataSource(file)));
				mbp2.setFileName(file.getName());
				mbp2.setDisposition(MimeBodyPart.ATTACHMENT);
				
	    		MimeMultipart mp = new MimeMultipart();
	    		mp.addBodyPart(mbp1);
	    		mp.addBodyPart(mbp2);
	    		msg.setContent(mp);
		    	
		    	
//	    		// Attach the specified file.
//	    		// We need a multipart message to hold the attachment.
//	    		MimeBodyPart mbp1 = new MimeBodyPart();
//	    		mbp1.setText(body);
//	    		MimeBodyPart mbp2 = new MimeBodyPart();
//				mbp2.attachFile(filePath);
//	    		MimeMultipart mp = new MimeMultipart();
//	    		mp.addBodyPart(mbp1);
//	    		mp.addBodyPart(mbp2);
//	    		msg.setContent(mp);
		    	    
//		    	
//		    	MimeMultipart mimeMultipart = new MimeMultipart();
//		    	  
//	  			MimeBodyPart text = new MimeBodyPart();
//	  			text.setText(body);
	  			
//	 			File file = new File(filePath);
//	  			MimeBodyPart attachement = new MimeBodyPart();
//	  		    attachement.setDataHandler(new DataHandler(new FileDataSource(file)));
//	  			attachement.setFileName(file.getName());
//	  			attachement.setDisposition(MimeBodyPart.ATTACHMENT);
	  			
//	  			mimeMultipart.addBodyPart(text);
	  			
//	  			mimeMultipart.addBodyPart(attachement);
	  
//	  			msg.setContent(mimeMultipart);
		    	

//		    	// create the message part 
//			    MimeBodyPart messageBodyPart = 
//			      new MimeBodyPart();
//	
//			    //fill message
//			    messageBodyPart.setText(body);
//	
//			    Multipart multipart = new MimeMultipart();
//			    multipart.addBodyPart(messageBodyPart);
//	
//			    File f = new File(filePath);
//			    
//			    // Part two is attachment
//			    messageBodyPart = new MimeBodyPart();
//			    DataSource source = 
//			      new FileDataSource(f);
//			    messageBodyPart.setDataHandler(
//			      new DataHandler(source));
//			    messageBodyPart.setFileName(f.getName());
//			    multipart.addBodyPart(messageBodyPart);
//	
//			    // Put parts in message
//			    msg.setContent(multipart);
			    
		    }

		    // -- Set some other header information --
			msg.setHeader("X-Mailer", "XML-Mail");
			msg.setSentDate(new Date());
			
			// -- Send the message --
			Transport.send(msg);

			return "success";
		} catch (Exception ex) {
			log.error("[mail send] " ,ex);
			return "Error" + ex;
		}
	}
	
}