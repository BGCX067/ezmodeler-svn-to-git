package org.i4change.app.remote;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.AuthLevelmanagement;
import org.i4change.app.data.basic.Configurationmanagement;
import org.i4change.app.data.basic.Sessionmanagement;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.hibernate.beans.basic.Configuration;

/**
 * 
 * @author swagner
 *
 */
public class ConfigurationService {
	
	private static final Log log = LogFactory.getLog(ConfigurationService.class);

	/*
	 * Configuration Handlers
	 */    
    public SearchResult getAllConf(String SID, int start ,int max, String orderby, boolean asc){
        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);     	
        return Configurationmanagement.getInstance().getAllConf(user_level, start, max, orderby, asc);
    }
    public Configuration getConfByConfigurationId(String SID,long configuration_id){
        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);     	
        return Configurationmanagement.getInstance().getConfByConfigurationId(user_level,configuration_id);
    }
    public Long saveOrUpdateConfiguration(String SID,LinkedHashMap values){
        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);     	
        return Configurationmanagement.getInstance().saveOrUpdateConfiguration(user_level,values, users_id);
    }    
    public Long deleteConfiguration(String SID,LinkedHashMap values){
        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);     	
        return Configurationmanagement.getInstance().deleteConfByConfiguration(user_level, values, users_id);
    }
    
    public List<Configuration> getLicenseDefaultConfiguration(String SID) {
    	try {
    		Long users_id = Sessionmanagement.getInstance().checkSession(SID);
            Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
    		if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
    			List<Configuration> configList = new LinkedList<Configuration>();
        		
        		configList.add(Configurationmanagement.getInstance().getConfKey(3,"defaultUserPricing"));
        		configList.add(Configurationmanagement.getInstance().getConfKey(3,"discountNumberOfUsers1"));
        		configList.add(Configurationmanagement.getInstance().getConfKey(3,"discountAmount1"));
    			configList.add(Configurationmanagement.getInstance().getConfKey(3,"discountNumberOfUsers2"));
    			configList.add(Configurationmanagement.getInstance().getConfKey(3,"discountAmount2"));
    			configList.add(Configurationmanagement.getInstance().getConfKey(3,"maxNumberOfUser"));
    			configList.add(Configurationmanagement.getInstance().getConfKey(3,"baseUrlPaypal"));
        		
    			return configList;
    		} else {
    			log.warn("Not logged in");
    		}
    	} catch (Exception err) {
    		log.error("[getLicenseDefaultConfiguration]",err);
    	}
    	return null;
    }
	    
}
