package org.i4change.organization;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.domain.daos.OrganisationDaoImpl;
import org.i4change.app.data.domain.daos.OrganisationUserDaoImpl;
import org.i4change.app.hibernate.beans.domain.Organisation_Users;

public class TestGetOrgUser extends TestCase {
	
	private static final Log log = LogFactory.getLog(TestGetOrgUser.class);	
	
	public void testGetOrgList() {
		try {
			
			long organization_id = 23;
			long users_id = 9;
			
			Organisation_Users orgUser = OrganisationUserDaoImpl.getInstance().checkUserInOrganisationId(organization_id, users_id);
			
			log.debug("orgUser: "+orgUser);
			
			log.debug("orgUser: "+orgUser.getIsModerator());
			
		} catch (Exception err) {
			log.error("[testGetOrgList]",err);
		}
	}

}
