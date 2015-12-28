package org.i4change.diagram;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.domain.daos.OrganisationDaoImpl;
import org.i4change.app.hibernate.beans.domain.Organisation;
import org.i4change.app.utils.math.CalendarPatterns;

import junit.framework.TestCase;

public class SaveorUpdateOrganization extends TestCase {
	
private static final Log log = LogFactory.getLog(GetDiagramObjectList.class);	
	
	public void testUpdateOrganization(){
		try {
			
			Organisation org = OrganisationDaoImpl.getInstance().getOrganisationById(new Long(3));
			
			Long users_id = new Long(1);
			Long organisation_id = org.getOrganisation_id();
			
			HashMap argObjectMap = new HashMap();
			argObjectMap.put("orgname", org.getName());
			argObjectMap.put("orgPatternMap", null);
			argObjectMap.put("isPending", "true");
			argObjectMap.put("expireDate", null);
			argObjectMap.put("maxWorkDays", null);
			argObjectMap.put("maxUsers", null);
			
			Date expireDate = null;
			if (argObjectMap.get("expireDate") != null) {
				expireDate = CalendarPatterns.parseDate(argObjectMap.get("expireDate").toString());
			}
			Long maxWorkDays = null;
			if (argObjectMap.get("maxWorkDays") != null) {
				maxWorkDays = Long.valueOf(argObjectMap.get("maxWorkDays").toString()).longValue();
			}
			Long maxUsers = null;
			if (argObjectMap.get("maxUsers") != null) {
				maxUsers = Long.valueOf(argObjectMap.get("maxUsers").toString()).longValue();
			}

			Long remoteOrganisation_id = OrganisationDaoImpl.getInstance().updateOrganisation(organisation_id, 
					argObjectMap.get("orgname").toString(), 
        			users_id, (Map) argObjectMap.get("orgPatternMap")
        			);

        	
        	log.debug("remoteOrganisation_id: "+remoteOrganisation_id);

		} catch (Exception err) {
			log.error("[getListOfDiagramsByDiagramObjectId]",err);
		}
	}

}
