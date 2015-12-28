package org.i4change.paypal;

import java.util.Date;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.i4change.paypal.payment.TransactionDaoImpl;

public class TestTransactions extends TestCase {
	
	private static final Log log = LogFactory.getLog(TestTransactions.class);	
		
		public void testCheckDuplicateEMails() {
			try {
				
				//TransactionDaoImpl.getInstance().addTransaction("asd", "1.02", new Date(), "COMPARE",1);
				

			} catch (Exception err) {
				log.error("[testCheckDuplicateEMails]",err);
			}
		}


}
