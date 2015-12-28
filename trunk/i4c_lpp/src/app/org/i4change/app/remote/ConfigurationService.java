package org.i4change.app.remote;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
public class ConfigurationService implements IConfigurationService {
	
	private static final Log log = LogFactory.getLog(ConfigurationService.class);
	
	private Sessionmanagement sessionmanagement;
	private UserDaoImpl userDaoImpl;
	private Configurationmanagement configurationmanagement;
	
	public Sessionmanagement getSessionmanagement() {
		return sessionmanagement;
	}
	public void setSessionmanagement(Sessionmanagement sessionmanagement) {
		this.sessionmanagement = sessionmanagement;
	}
	public UserDaoImpl getUserDaoImpl() {
		return userDaoImpl;
	}
	public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}
	public Configurationmanagement getConfigurationmanagement() {
		return configurationmanagement;
	}
	public void setConfigurationmanagement(
			Configurationmanagement configurationmanagement) {
		this.configurationmanagement = configurationmanagement;
	}
	
	/*
	 * Configuration Handlers
	 */    
    /* (non-Javadoc)
	 * @see org.i4change.app.remote.IConfigurationService#getAllConf(java.lang.String, int, int, java.lang.String, boolean)
	 */
    public SearchResult getAllConf(String SID, int start ,int max, String orderby, boolean asc){
        Long users_id = this.sessionmanagement.checkSession(SID);
        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);     	
        return this.configurationmanagement.getAllConf(user_level, start, max, orderby, asc);
    }
    /* (non-Javadoc)
	 * @see org.i4change.app.remote.IConfigurationService#getConfByConfigurationId(java.lang.String, long)
	 */
    public Configuration getConfByConfigurationId(String SID,long configuration_id){
        Long users_id = this.sessionmanagement.checkSession(SID);
        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);     	
        return this.configurationmanagement.getConfByConfigurationId(user_level,configuration_id);
    }
    /* (non-Javadoc)
	 * @see org.i4change.app.remote.IConfigurationService#saveOrUpdateConfiguration(java.lang.String, java.util.Map)
	 */
    public Long saveOrUpdateConfiguration(String SID,Map values){
        Long users_id = this.sessionmanagement.checkSession(SID);
        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);     	
        return this.configurationmanagement.saveOrUpdateConfiguration(user_level,values, users_id);
    }    
    /* (non-Javadoc)
	 * @see org.i4change.app.remote.IConfigurationService#deleteConfiguration(java.lang.String, java.util.Map)
	 */
    public Long deleteConfiguration(String SID,Map values){
        Long users_id = this.sessionmanagement.checkSession(SID);
        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);     	
        return this.configurationmanagement.deleteConfByConfiguration(user_level, values, users_id);
    }
    
    /* (non-Javadoc)
	 * @see org.i4change.app.remote.IConfigurationService#getLicenseDefaultConfiguration(java.lang.String)
	 */
    public List<Configuration> getLicenseDefaultConfiguration(String SID) {
    	try {
    		Long users_id = this.sessionmanagement.checkSession(SID);
            Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
    		if (AuthLevelmanagement.checkUserLevel(user_level)) {
    			List<Configuration> configList = new LinkedList<Configuration>();
        		
        		configList.add(this.configurationmanagement.getConfKey(3,"defaultUserPricing"));
        		configList.add(this.configurationmanagement.getConfKey(3,"discountNumberOfUsers1"));
        		configList.add(this.configurationmanagement.getConfKey(3,"discountAmount1"));
    			configList.add(this.configurationmanagement.getConfKey(3,"discountNumberOfUsers2"));
    			configList.add(this.configurationmanagement.getConfKey(3,"discountAmount2"));
    			configList.add(this.configurationmanagement.getConfKey(3,"maxNumberOfUser"));
    			configList.add(this.configurationmanagement.getConfKey(3,"baseUrlPaypal"));
        		
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
