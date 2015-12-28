package org.i4change.help;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.help.HelpTopicServiceDaoImpl;
import org.i4change.diagram.AddDefaultDiagramTypes;

import junit.framework.TestCase;

public class TestErrorMessage extends TestCase {
	
	private static final Log log = LogFactory.getLog(TestErrorMessage.class);	
	
	public void testaddNewHelpTopic() {
		try {
			
			HelpTopicServiceDaoImpl.getInstance().addHelpText(new Long(1), "fName", false, 5, "topicText", "helpText", new Long(1));
			
		} catch (Exception err) {
			log.error("[testaddNewHelpTopic]",err);
		}
	}

}
