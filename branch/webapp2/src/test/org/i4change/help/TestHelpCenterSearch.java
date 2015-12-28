package org.i4change.help;

import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.help.HelpTopicServiceDaoImpl;
import org.i4change.app.hibernate.beans.help.HelpTopic;
import org.i4change.diagram.AddDefaultDiagramTypes;

public class TestHelpCenterSearch extends TestCase {
	
	private static final Log log = LogFactory.getLog(AddDefaultDiagramTypes.class);	
	
	public void testaddNewHelpTopic() {
		try {
			
			String str = "%prio%";
			
			List<HelpTopic> helpList = HelpTopicServiceDaoImpl.getInstance().getHelpTopicsByStr(str, new Long(1));
			
			for (HelpTopic helpItem : helpList) {
				log.debug("helpItem: "+helpItem.getHelptopicId());
			}
			
		} catch (Exception err) {
			log.error("[testaddNewHelpTopic]",err);
		}
	}

}
