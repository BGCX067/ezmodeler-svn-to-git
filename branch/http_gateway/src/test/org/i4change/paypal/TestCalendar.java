package org.i4change.paypal;

import java.util.Date;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.i4change.paypal.payment.TransactionDaoImpl;
import org.i4change.app.utils.math.CalendarPatterns;

public class TestCalendar extends TestCase {
	
	private static final Log log = LogFactory.getLog(TestCalendar.class);	
		
		public void testCheckDuplicateEMails() {
			try {
				
				String pattern = CalendarPatterns.getYear(new Date());
				
				log.debug("pattern: "+pattern);
				

			} catch (Exception err) {
				log.error("[testCheckDuplicateEMails]",err);
			}
		}


}
