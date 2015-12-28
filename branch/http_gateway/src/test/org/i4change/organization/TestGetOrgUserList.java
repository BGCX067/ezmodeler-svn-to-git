package org.i4change.organization;

import java.util.List;
import java.util.Iterator;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.domain.daos.OrganisationUserDaoImpl;
import org.i4change.app.hibernate.beans.user.Users;

public class TestGetOrgUserList extends TestCase {
	
	private static final Log log = LogFactory.getLog(TestGetOrgUserList.class);	
	
	public void testGetOrgList() {
		try {
			
			long organization_id = 3;
			
			List<Users> users = OrganisationUserDaoImpl.getInstance().getUsersByOrganisationId(organization_id);
			
			log.debug("Number of Users: "+users.size());
			
			for (Iterator<Users> iter = users.iterator();iter.hasNext();) {
				Users user = iter.next();
				log.debug("orgUser: "+user.getLogin());
				log.debug("orgUser: "+user.getOrgUser());
			}
			
		} catch (Exception err) {
			log.error("[testGetOrgList]",err);
		}
	}

}
