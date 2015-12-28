package org.i4change.app.data.basic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AuthLevelmanagement {
	
	private static final Log log = LogFactory.getLog(AuthLevelmanagement.class);

	private AuthLevelmanagement() {}
	
	public static boolean checkUserLevel(Long user_level) {
		if (user_level > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checkModLevel(Long user_level) {
		if (user_level > 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checkAdminLevel(Long user_level) {
		if (user_level > 2) {
			return true;
		} else {
			return false;
		}
	}

}
