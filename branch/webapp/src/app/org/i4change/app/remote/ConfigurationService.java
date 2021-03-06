package org.i4change.app.remote;

import java.util.LinkedHashMap;

import org.i4change.app.data.basic.Configurationmanagement;
import org.i4change.app.data.basic.Sessionmanagement;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.data.user.Usermanagement;
import org.i4change.app.hibernate.Configuration;

/**
 * 
 * @author swagner
 *
 */
public class ConfigurationService {
	
	/*
	 * Configuration Handlers
	 */    
    public SearchResult getAllConf(String SID, int start ,int max, String orderby, boolean asc){
        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
        Long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);     	
        return Configurationmanagement.getInstance().getAllConf(user_level, start, max, orderby, asc);
    }
    public Configuration getConfByConfigurationId(String SID,long configuration_id){
        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
        Long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);     	
        return Configurationmanagement.getInstance().getConfByConfigurationId(user_level,configuration_id);
    }
    public Long saveOrUpdateConfiguration(String SID,LinkedHashMap values){
        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
        Long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);     	
        return Configurationmanagement.getInstance().saveOrUpdateConfiguration(user_level,values, users_id);
    }    
    public Long deleteConfiguration(String SID,LinkedHashMap values){
        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
        Long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);     	
        return Configurationmanagement.getInstance().deleteConfByConfiguration(user_level, values, users_id);
    }
	    
}
