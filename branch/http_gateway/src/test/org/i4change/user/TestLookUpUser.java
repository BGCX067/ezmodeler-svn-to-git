package org.i4change.user;

import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.hibernate.beans.user.Users;

public class TestLookUpUser extends TestCase {
	
	private static final Log log = LogFactory.getLog(TestLookUpUser.class);	
	
	public void testaddNewHelpTopic() {
		try {
			
			String searchstring = "hans";
			int max = 100;
			int start = 0;
			String orderby = "c.user_id";
			boolean asc = true;
			
			List<Users> users = UserDaoImpl.getInstance().lookUpUser(searchstring, max, start, orderby, asc);
			
			for (Users us : users) {
				log.debug("TestLookUpUser user: "+us.getLogin()+" "+us.getUser_id()+" "+us.getFirstname()+" "+us.getLastname());
			}
			
		} catch (Exception err) {
			log.error("[testaddNewHelpTopic]",err);
		}
	}

}
