package org.i4change.organization;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.domain.daos.OrganisationDaoImpl;

public class TestSendEmailsToPendingOrganizations extends TestCase {
	
	private static final Log log = LogFactory.getLog(TestSendEmailsToPendingOrganizations.class);	
		
		public void testSendEmailsToPendingOrganizations() {
			try {
				
				//OrganisationDaoImpl.getInstance().sendMailsToPendingOrgs();
				
				//log.debug("isValid: "+isValid);

			} catch (Exception err) {
				log.error("[testCheckDuplicateEMails]",err);
			}
		}

}
