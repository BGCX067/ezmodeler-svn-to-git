package org.i4change.organization;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.domain.daos.OrganisationDaoImpl;
import org.i4change.app.hibernate.beans.domain.Organisation;

public class TestExpireDateForOrganization extends TestCase {
	
	private static final Log log = LogFactory.getLog(TestExpireDateForOrganization.class);	
	
	public void testOrgExpire() {
		try {
			
			Long returnValue = null;
			Long organization_id = new Long(1);
			
			Organisation org = OrganisationDaoImpl.getInstance().getOrganisationById(organization_id);
//			
//			if (org.getExpireDate() != null) {
//				
//				Date now = new Date();
//				
//				if (org.getExpireDate().getTime() < now.getTime()) {
//					returnValue = new Long(-44);
//				}
//				
//			}
//			
//				
//			if (org.getMaxWorkDays() != null && org.getUsedWorkDays() != null && org.getUsedWorkDays() >= org.getMaxWorkDays()){
//				returnValue = new Long(-45);
//			} else {
//				//check if today is already a new WorkDay
//				if (org.getLastWorkDay() == null || org.getUsedWorkDays() == null) {
//					org.setUsedWorkDays(new Long(1));
//					//int year, int month, int dayOfMonth, int hourOfDay,int minute
//					GregorianCalendar cal = new GregorianCalendar();
//					long dt = new GregorianCalendar(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),0,0).getTimeInMillis();
//					Date todayMidnight = new Date(dt);
//					org.setLastWorkDay(todayMidnight);
//					OrganisationDaoImpl.getInstance().updateOrganisation(org);
//				} else {
//					Date now = new Date();
//					Long timeDifferenceInMilliSeconds = now.getTime() - org.getLastWorkDay().getTime();
//					//log.debug("timeDifferenceInMilliSeconds: "+timeDifferenceInMilliSeconds);
//					//log.debug("must########################: "+86400000);
//					if (timeDifferenceInMilliSeconds >= 86400000) {
//						//new Workdays
//						org.setUsedWorkDays(org.getUsedWorkDays()+1);
//						//int year, int month, int dayOfMonth, int hourOfDay,int minute
//						GregorianCalendar cal = new GregorianCalendar();
//						long dt = new GregorianCalendar(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),0,0).getTimeInMillis();
//						Date todayMidnight = new Date(dt);
//						org.setLastWorkDay(todayMidnight);
//						OrganisationDaoImpl.getInstance().updateOrganisation(org);
//					}
//				}
//				
//				
//			}
//			
			log.debug("return: "+returnValue);
			
		} catch (Exception err) {
			log.error("[testOrgExpire]",err);
		}
	}

}
