package org.i4change.organization;

import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.domain.daos.OrganisationDaoImpl;
import org.i4change.app.data.domain.daos.OrganisationUserDaoImpl;
import org.i4change.app.hibernate.beans.user.Users;

public class TestGetUserByOrganisation extends TestCase {
	
	private static final Log log = LogFactory.getLog(TestGetUserByOrganisation.class);	
	
	public void testGetOrgList() {
		try {
			
			List<Users> users = OrganisationUserDaoImpl.getInstance().getUsersByOrganisationId(new Long(1));
			
			for (Users us : users) {
				
				log.debug("us: "+us.getLogin());
				
			}
			
		} catch (Exception err) {
			log.error("[testGetOrgList]",err);
		}
	}
	
}
