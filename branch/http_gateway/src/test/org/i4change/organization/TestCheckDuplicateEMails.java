package org.i4change.organization;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.user.EmailDaoImpl;

import junit.framework.TestCase;

public class TestCheckDuplicateEMails extends TestCase {
	
private static final Log log = LogFactory.getLog(TestCheckDuplicateEMails.class);	
	
	public void testCheckDuplicateEMails() {
		try {
			
			boolean isValid = EmailDaoImpl.getInstance().checkUserEMail("seba.wagner@gmail.com");
			
			log.debug("isValid: "+isValid);

		} catch (Exception err) {
			log.error("[testCheckDuplicateEMails]",err);
		}
	}

}
