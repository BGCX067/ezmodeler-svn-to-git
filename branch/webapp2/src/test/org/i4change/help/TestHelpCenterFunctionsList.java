package org.i4change.help;

import java.util.LinkedHashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.help.HelpTopicServiceDaoImpl;
import org.i4change.diagram.AddDefaultDiagramTypes;

public class TestHelpCenterFunctionsList extends TestCase {
	
	private static final Log log = LogFactory.getLog(AddDefaultDiagramTypes.class);	
	
	public void testaddNewHelpTopic() {
		try {
			
			Map helpIds = new LinkedHashMap();
			helpIds.put(0, 1);
			helpIds.put(1, 2);
			helpIds.put(2, 3);
			
			HelpTopicServiceDaoImpl.getInstance().getHelpTopics(helpIds);
			
		} catch (Exception err) {
			log.error("[testaddNewHelpTopic]",err);
		}
	}

}
