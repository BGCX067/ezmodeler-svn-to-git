package org.i4change.help;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.beans.ErrorResult;
import org.i4change.app.data.help.HelpTopicServiceDaoImpl;
import org.i4change.app.remote.ErrorService;
import org.i4change.diagram.AddDefaultDiagramTypes;

public class TestHelpCenterFunctions extends TestCase {
	
	private static final Log log = LogFactory.getLog(TestHelpCenterFunctions.class);	
	
	public void testError() {
		try {
			
			ErrorService eService = new ErrorService();
			
			ErrorResult eResult = eService.getErrorByCode("", new Long(-18), new Long(1));
			
			log.debug("eResult: "+eResult);
			
		} catch (Exception err) {
			log.error("[testError]",err);
		}
	}

}
