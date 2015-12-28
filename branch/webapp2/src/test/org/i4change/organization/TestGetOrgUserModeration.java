package org.i4change.organization;

import java.util.Iterator;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.hibernate.beans.user.Users;

public class TestGetOrgUserModeration extends TestCase {
	
	private static final Log log = LogFactory.getLog(TestGetOrgUserModeration.class);	
	
	public void testGetOrgList() {
		try {
			
			long organization_id = 4;
			int start = 0;
			int max = 100;
			String orderby = "c.user_id";
			boolean asc = true;
			
			SearchResult sResult = UserDaoImpl.getInstance().getUsersListByOrganization(start, max, orderby, asc, organization_id);
			
			log.debug("Number: "+sResult.getResult().size());
			log.debug("Max: "+sResult.getRecords());
			
			for (Iterator iter = sResult.getResult().iterator();iter.hasNext();) {
				Users us = (Users) iter.next();
				log.debug("User User: "+us.getLogin());
				
			}
		} catch (Exception err) {
			log.error("[testGetOrgList]",err);
		}
	}

}
