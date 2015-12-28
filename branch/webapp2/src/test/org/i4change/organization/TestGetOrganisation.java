package org.i4change.organization;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.domain.daos.OrganisationDaoImpl;

public class TestGetOrganisation extends TestCase {
	
	private static final Log log = LogFactory.getLog(TestGetOrganisation.class);	
	
	public void testGetOrgList() {
		try {
			
			int start = 0;
			int max = 100;
			String orderby = "organisation_id";
			boolean asc = true;
//			
//			OrganisationDaoImpl.getInstance().getOrganisationsList(start,max,orderby,asc);
//			
		} catch (Exception err) {
			log.error("[testGetOrgList]",err);
		}
	}
	
}
