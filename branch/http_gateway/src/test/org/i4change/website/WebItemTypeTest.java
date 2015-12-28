package org.i4change.website;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.website.daos.WebItemTypeDaoImpl;
import org.i4change.user.TestLookUpUser;

public class WebItemTypeTest extends TestCase {
	
	private static final Log log = LogFactory.getLog(TestLookUpUser.class);	
	
	public void testaddNewHelpTopic() {
		try {
			
			WebItemTypeDaoImpl.getInstance().addWebItemType("Text", 1208L);
			WebItemTypeDaoImpl.getInstance().addWebItemType("Image", 1209L);
			WebItemTypeDaoImpl.getInstance().addWebItemType("Video", 1210L);
			
		} catch (Exception err) {
			log.error("[testaddNewHelpTopic]",err);
		}
	}
	
}
