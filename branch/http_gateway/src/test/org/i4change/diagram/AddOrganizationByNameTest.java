package org.i4change.diagram;

import java.util.Date;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.Configurationmanagement;
import org.i4change.app.data.domain.daos.OrganisationDaoImpl;

import junit.framework.TestCase;

public class AddOrganizationByNameTest extends TestCase {
	
private static final Log log = LogFactory.getLog(TestDiagramObjects.class);	
	
	public void testgetDiagramObjectList(){
		try {
			
			HashMap orgPatternsMap = null;
			String orgName = "hansieeeeewqe";
			Long users_id = new Long(1);
			Long user_level = new Long(3);

			//check for duplicates
        	if (OrganisationDaoImpl.getInstance().checkOrgForUniqueName(orgName)){
        		
        		//Not needed
//	        	Integer expireDateMonthsMax = Integer.valueOf(Configurationmanagement.getInstance().getConfKey(3, "expireDateMonthsMax").getConf_value()).intValue();
//	        	log.debug("expireDateMonthsMax: "+expireDateMonthsMax);
//	        	Long timeToExpire = new Long(expireDateMonthsMax) * 30 * 86400000;
//	        	Date currentDate = new Date();
//	        	Date expireDate = new Date(currentDate.getTime()+timeToExpire);
//	        	
//	        	Long maxWorkDays = Long.valueOf(Configurationmanagement.getInstance().getConfKey(3, "maxWorkDays").getConf_value()).longValue();
//	        	Long maxUsers = Long.valueOf(Configurationmanagement.getInstance().getConfKey(3, "maxUsers").getConf_value()).longValue();

	        	Long organisation_id = OrganisationDaoImpl.getInstance().addOrganisationWithMap(orgName, users_id, 
	        								orgPatternsMap);
	        	
	        	//organisation_id
	        	OrganisationDaoImpl.getInstance().addUserToOrganisation(users_id, organisation_id, users_id, "", true);
	        	
	        	log.debug("organisation_id: "+organisation_id);
	        	
        	} else {
        		log.debug("Error 41 ");
        	}
			

		} catch (Exception err) {
			log.error("testgetDiagramObjectList: ",err);
		}
		
	}

}
