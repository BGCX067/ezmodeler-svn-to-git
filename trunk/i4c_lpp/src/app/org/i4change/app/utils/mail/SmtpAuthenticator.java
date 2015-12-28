package org.i4change.app.utils.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import org.i4change.app.data.basic.Configurationmanagement;

/**
 * 
 * @author swagner
 *
 */
public class SmtpAuthenticator extends Authenticator{

	private String username;
	private String userpass;
	
	public SmtpAuthenticator(String username, String password) {
		this.username = username;
		this.userpass = userpass;
	}
	
	@Override
	public PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(this.username,this.userpass);
	}

	
}
