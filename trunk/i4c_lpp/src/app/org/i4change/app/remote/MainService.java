package org.i4change.app.remote;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.i4change.app.data.basic.Sessionmanagement;
import org.i4change.app.rss.LoadAtomRssFeed;
import org.i4change.app.hibernate.beans.adresses.Country;
import org.i4change.app.hibernate.beans.basic.Configuration;
import org.i4change.app.hibernate.beans.basic.Naviglobal;
import org.i4change.app.data.basic.Navimanagement;
import org.i4change.app.hibernate.beans.basic.Sessiondata;
import org.i4change.app.hibernate.beans.domain.Organisation_Users;
import org.i4change.app.hibernate.beans.export.PresentationTemplate;

import org.i4change.app.hibernate.beans.user.Users;
import org.i4change.app.hibernate.beans.user.Userdata;

import org.i4change.app.data.basic.*;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.data.user.daos.UserPropertyDaoImpl;
import org.i4change.app.data.user.CountryDaoImpl;
import org.i4change.app.data.user.Usermanagement;

import org.i4change.app.data.basic.AuthLevelmanagement;
import org.i4change.app.data.domain.daos.OrganisationDaoImpl;
import org.i4change.app.data.domain.daos.OrganisationUserDaoImpl;
import org.i4change.app.data.export.dao.PresentationTemplateDaoImpl;
import org.i4change.app.dto.basic.ConfigurationDTO;
import org.i4change.app.dto.user.UserAuthDTO;

import org.i4change.app.session.beans.RoomClient;
import org.springframework.beans.BeansException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 
 * @author swagner
 *
 */
public class MainService implements IMainService {
	
	private static final Log log = LogFactory.getLog(MainService.class);
	
	//Spring loaded beans
	private Usermanagement usermanagement;
	private PresentationTemplateDaoImpl presentationTemplateDaoImpl;
	private Sessionmanagement sessionmanagement;
	private UserDaoImpl userDaoImpl;
	private UserPropertyDaoImpl userPropertyDaoImpl;
	private CountryDaoImpl countryDaoImpl;
	private Navimanagement navimanagement;
	private OrganisationUserDaoImpl organisationUserDaoImpl;
	private Configurationmanagement configurationmanagement;
	private LoadAtomRssFeed loadAtomRssFeed;
	
	public Usermanagement getUsermanagement() {
		return usermanagement;
	}
	public void setUsermanagement(Usermanagement usermanagement) {
		this.usermanagement = usermanagement;
	}

	public PresentationTemplateDaoImpl getPresentationTemplateDaoImpl() {
		return presentationTemplateDaoImpl;
	}
	public void setPresentationTemplateDaoImpl(
			PresentationTemplateDaoImpl presentationTemplateDaoImpl) {
		this.presentationTemplateDaoImpl = presentationTemplateDaoImpl;
	}
	
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
	
	public UserPropertyDaoImpl getUserPropertyDaoImpl() {
		return userPropertyDaoImpl;
	}
	public void setUserPropertyDaoImpl(UserPropertyDaoImpl userPropertyDaoImpl) {
		this.userPropertyDaoImpl = userPropertyDaoImpl;
	}
	
	public CountryDaoImpl getCountryDaoImpl() {
		return countryDaoImpl;
	}
	public void setCountryDaoImpl(CountryDaoImpl countryDaoImpl) {
		this.countryDaoImpl = countryDaoImpl;
	}
	
	public Navimanagement getNavimanagement() {
		return navimanagement;
	}
	public void setNavimanagement(Navimanagement navimanagement) {
		this.navimanagement = navimanagement;
	}
	
	public OrganisationUserDaoImpl getOrganisationUserDaoImpl() {
		return organisationUserDaoImpl;
	}
	public void setOrganisationUserDaoImpl(
			OrganisationUserDaoImpl organisationUserDaoImpl) {
		this.organisationUserDaoImpl = organisationUserDaoImpl;
	}
	
	public Configurationmanagement getConfigurationmanagement() {
		return configurationmanagement;
	}
	public void setConfigurationmanagement(
			Configurationmanagement configurationmanagement) {
		this.configurationmanagement = configurationmanagement;
	}
	
	public LoadAtomRssFeed getLoadAtomRssFeed() {
		return loadAtomRssFeed;
	}
	public void setLoadAtomRssFeed(LoadAtomRssFeed loadAtomRssFeed) {
		this.loadAtomRssFeed = loadAtomRssFeed;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IMainService#getNavi(java.lang.String, java.lang.Long, java.lang.Long)
	 */
	public List<Naviglobal> getNavi(String SID, Long language_id, Long organization_id){
		try {
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        //log.error("getNavi 1: "+users_id);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        //log.error("getNavi 2: "+user_level);
	        
	        //check if this is a Org-Admin
	        log.debug("user_level: "+user_level);
	        if (user_level.equals(1)){
	        	Organisation_Users orgUser = this.organisationUserDaoImpl.checkUserInOrganisationId(organization_id, users_id);
	        	log.debug("orgUser: "+orgUser);
	        	log.debug("orgUser.getIsModerator: "+orgUser.getIsModerator());
	        	if (orgUser.getIsModerator()) {
	        		user_level = new Long(2);
	        	}
	        }
	        
			return this.navimanagement.getMainMenu(user_level,users_id, language_id);
		} catch (Exception err){
			log.error("[getNavi] ", err);
		}
		return null;
	}
  
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IMainService#getUser(java.lang.String, int)
	 */
	public Users getUser(String SID,int USER_ID){
		Users users = new Users();
		Long users_id = this.sessionmanagement.checkSession(SID);
		long user_level = this.userDaoImpl.getUserLevelByID(users_id);
    	if(user_level>2){
    		users = this.userDaoImpl.getUserById(new Long(USER_ID));
    	} else {
    		users.setFirstname("No rights to do this");
    	}
		return users;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IMainService#setNewUserProperty(java.lang.String, java.lang.String, java.lang.Object)
	 */
	public UserAuthDTO setNewUserProperty(String SID, String propName, Object propValue) {
		try {
			Long user_id = this.sessionmanagement.checkSession(SID);
			Long user_level = this.userDaoImpl.getUserLevelByID(user_id);
			if (AuthLevelmanagement.checkUserLevel(user_level)){
				
				//Users us = this.userDaoImpl.getUserById(user_id);
				UserAuthDTO userAuth = new UserAuthDTO();
				
				XStream xStream = new XStream(new XppDriver());
				xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
				String xmlString = xStream.toXML(propValue);	
				
				this.userPropertyDaoImpl.addOrUpdateUserProperty(propName, user_id, xmlString);
				//us.setUserPropsAsObject(this.userPropertyDaoImpl.getUserSidebarPropertyByUser(us.getUser_id()));
				
				userAuth.setUserPropsAsObject(this.userPropertyDaoImpl.getUserSidebarPropertyDTOByUser(user_id));
				
    			return userAuth;
			}
		} catch (Exception err) {
			log.error("[setNewUserProperty]",err);
		}
		return null;
	}

	 
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IMainService#getsessiondata()
	 */
    public Sessiondata getsessiondata(){
    	log.debug("getsessiondata");
    	log.debug("getsessiondata"+this);
    	log.debug("getsessiondata"+this.sessionmanagement);
        return this.sessionmanagement.startsession();
    }   
       
    /* (non-Javadoc)
	 * @see org.i4change.app.remote.IMainService#loginUser(java.lang.String, java.lang.String, java.lang.String, java.lang.Long)
	 */ 
    public Object loginUser(String SID, String Username, String Userpass, Long userlang){
    	try {
        	log.debug("loginUser 111: "+SID+" "+Username);
        	//IConnection current = Red5.getConnectionLocal();
        	//RoomClient currentClient = Application.getClientList().get(current.getClient().getId());
        	RoomClient currentClient = new RoomClient();
            Object obj = this.usermanagement.loginUser(SID,Username,Userpass, currentClient, userlang);
            
			return obj;
    	} catch (Exception err) {
    		log.error("loginUser",err);
    	}
    	return null;
    } 
    
    /* (non-Javadoc)
	 * @see org.i4change.app.remote.IMainService#markSessionAsLogedIn(java.lang.String)
	 */
    public void markSessionAsLogedIn(String SID){
    	this.sessionmanagement.updateUser(SID, -1);
    }
    
    /* (non-Javadoc)
	 * @see org.i4change.app.remote.IMainService#logoutUser(java.lang.String)
	 */
    public Long logoutUser(String SID){
    	Long users_id = this.sessionmanagement.checkSession(SID);
    	//IConnection current = Red5.getConnectionLocal();
		//RoomClient currentClient = Application.getClientList().get(current.getClient().getId());	
    	RoomClient currentClient = new RoomClient();
		currentClient.setUserObject(null, null, null, null);
    	return this.usermanagement.logout(SID,users_id);
    }
    
    /* (non-Javadoc)
	 * @see org.i4change.app.remote.IMainService#getStates()
	 */
    public List<Country> getStates(){
    	return this.countryDaoImpl.getCountry();
    }

    /* (non-Javadoc)
	 * @see org.i4change.app.remote.IMainService#allowFrontendRegister(java.lang.String)
	 */
    public ConfigurationDTO allowFrontendRegister(String SID){
    	return this.configurationmanagement.getConfKeyDTO(3, "allow_frontend_register");
    }
    
//    /**
//     * Add a user register by an Object
//     * see [registerUser] for the index of the Object
//     * To allow the registering the config_key *allow_frontend_register* has to be the value 1
//     * otherwise the user will get an error code
//     * @param regObject
//     * @deprecated
//     * @return new users_id OR null if an exception, -1 if an error, -4 if mail already taken, -5 if username already taken, -3 if login or pass or mail is empty 
//     */
//    public Long registerUserByObject(Object regObjectObj){
//    	try {
//    		LinkedHashMap regObject = (LinkedHashMap) regObjectObj;
//        	return Usermanagement.getInstance().registerUserNew(regObject.get("Username").toString(), regObject.get("Userpass").toString(), 
//        			regObject.get("lastname").toString(), regObject.get("firstname").toString(), regObject.get("email").toString(), 
//        			new Date(), regObject.get("street").toString(), regObject.get("additionalname").toString(), 
//        			regObject.get("fax").toString(), regObject.get("zip").toString(), 
//        			Long.valueOf(regObject.get("states_id").toString()).longValue(), regObject.get("town").toString(), 
//        			Long.valueOf(regObject.get("language_id").toString()).longValue(),
//        			regObject.get("domain").toString(),
//        			Integer.valueOf(regObject.get("port").toString()).intValue(),
//        			regObject.get("webapp").toString());
//    	} catch (Exception ex) {
//    		log.error("registerUserByObject",ex);
//    	}
//    	return new Long(-1);
//    }
    
    /* (non-Javadoc)
	 * @see org.i4change.app.remote.IMainService#registerUserByObjectAdvanced(java.util.Map)
	 */
    public Long registerUserByObjectAdvanced(Map regObjectObj){
    	try {
    		Map regObject = (Map) regObjectObj.get("personalData");
        	return this.usermanagement.registerUserNewWithPersonalDetails(
        			regObject.get("Username").toString(), regObject.get("Userpass").toString(), 
        			regObject.get("lastname").toString(), regObject.get("firstname").toString(), regObject.get("email").toString(), 
        			null, regObject.get("street").toString(), regObject.get("additionalname").toString(), 
        			regObject.get("fax").toString(), regObject.get("zip").toString(), 
        			Long.valueOf(regObject.get("states_id").toString()).longValue(), regObject.get("town").toString(), 
        			Long.valueOf(regObject.get("language_id").toString()).longValue(),
        			regObject.get("domain").toString(),
        			Integer.valueOf(regObject.get("port").toString()).intValue(),
        			regObject.get("webapp").toString(),regObjectObj);
    	} catch (Exception ex) {
    		log.error("registerUserByObject",ex);
    	}
    	return new Long(-1);
    }
    
    /* (non-Javadoc)
	 * @see org.i4change.app.remote.IMainService#registerUserWebSite(java.util.Map)
	 */
    public Long registerUserWebSite(Map regObject) {
		try {
			
			return this.usermanagement.registerUserNewWithPersonalDetails(
					regObject.get("Username").toString(), regObject.get("Userpass").toString(), 
        			regObject.get("lastname").toString(), regObject.get("firstname").toString(), 
        			regObject.get("email").toString(), 
        			null, "", "", //age,street,additionalname
        			"", "", //fax,zip
        			Long.valueOf(regObject.get("states_id").toString()).longValue(), "", //country, town
        			0, //language_id
        			regObject.get("domain").toString(),
        			Integer.valueOf(regObject.get("port").toString()).intValue(),
        			regObject.get("webapp").toString(),null);
			
			
		} catch (Exception e) {
			log.error("[registerUserMinimal]",e);
		}
		return null;
	}
    
    
    
    /**
     * Register a new User
     * To allow the registering the config_key *allow_frontend_register* has to be the value 1
     * otherwise the user will get an error code
     * @deprecated use registerUserByObject instead
     * @param SID
     * @param Username
     * @param Userpass
     * @param lastname
     * @param firstname
     * @param email
     * @param age
     * @param street
     * @param additionalname
     * @param fax
     * @param zip
     * @param states_id
     * @param town
     * @param language_id
     * @return new users_id OR null if an exception, -1 if an error, -4 if mail already taken, -5 if username already taken, -3 if login or pass or mail is empty 
     */
//	public Long registerUser(String SID, String Username, String Userpass, String lastname, 
//				String firstname, String email, Date age, String street, String additionalname, 
//				String fax, String zip, long states_id, String town, long language_id){
//    	return this.userDaoImpl.registerUser(Username, Userpass, lastname, firstname, email, 
//    			age, street, additionalname, fax, zip, states_id, town, language_id);
//	}	
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IMainService#deleteUserIDSelf(java.lang.String)
	 */
    public Long deleteUserIDSelf(String SID){
    	Long users_id = this.sessionmanagement.checkSession(SID);
    	long user_level = this.userDaoImpl.getUserLevelByID(users_id);
    	if(user_level>=1){
    		this.usermanagement.logout(SID,users_id);
    		return this.userDaoImpl.deleteUserID(users_id);
    	} else {
    		return new Long(-10);
    	}
    }
    

    /* (non-Javadoc)
	 * @see org.i4change.app.remote.IMainService#getUserdata(java.lang.String)
	 */
    public List<Userdata> getUserdata(String SID){
    	Long users_id = this.sessionmanagement.checkSession(SID);
    	Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
    	if (AuthLevelmanagement.checkUserLevel(user_level)){
    		return this.userDaoImpl.getUserdataDashBoard(users_id);
    	}
    	return null;
    }
    
//	public LinkedHashMap<Integer,RoomClient> getUsersByDomain(String SID, String domain) {
//    	Long users_id = this.sessionmanagement.checkSession(SID);
//    	Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
//    	if (AuthLevelmanagement.checkUserLevel(user_level)){
//    		LinkedHashMap<Integer,RoomClient> lMap = new LinkedHashMap<Integer,RoomClient>();
//    		Integer counter = 0;
//    		for (Iterator<String> it = Application.getClientList().keySet().iterator();it.hasNext();) {
//    			RoomClient rc = Application.getClientList().get(it.next());
//    			if (rc.getDomain().equals(domain)) {
//    				lMap.put(counter, rc);
//    				counter++;
//    			}
//    		}
//    		return lMap;
//    	} else {
//    		return null;
//    	}
//	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IMainService#getPresentationTemplates(java.lang.String, java.lang.Long)
	 */
	public List<PresentationTemplate> getPresentationTemplates(String SID, Long organisationId) {
		Long users_id = this.sessionmanagement.checkSession(SID);
    	Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
    	if (AuthLevelmanagement.checkUserLevel(user_level)){
    		return this.presentationTemplateDaoImpl.getPresentationTemplates(organisationId);
    	}
    	return null;
	}
    
    /* (non-Javadoc)
	 * @see org.i4change.app.remote.IMainService#getRssFeedByURL(java.lang.String, java.lang.String)
	 */
    public LinkedHashMap<String,LinkedHashMap<String,LinkedHashMap<String,Object>>> getRssFeedByURL(String SID, String urlEndPoint) {
    	Long users_id = this.sessionmanagement.checkSession(SID);
    	Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
    	if (AuthLevelmanagement.checkUserLevel(user_level)){
    		return this.loadAtomRssFeed.parseRssFeed(urlEndPoint);
    	} else {
    		return null;
    	}
    }
    
    /* (non-Javadoc)
	 * @see org.i4change.app.remote.IMainService#getRssFeedByConf(java.lang.String)
	 */
    public LinkedHashMap<String,LinkedHashMap<String,LinkedHashMap<String,Object>>> getRssFeedByConf(String SID) {
    	Long users_id = this.sessionmanagement.checkSession(SID);
    	Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
    	if (AuthLevelmanagement.checkUserLevel(user_level)){
    		return this.loadAtomRssFeed.getRssFeedSingleStored(user_level);
    	} else {
    		return null;
    	}
    }
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IMainService#getStoredUserSessionBySID(java.lang.String)
	 */
	public Sessiondata getStoredUserSessionBySID(String sid) {
		return this.sessionmanagement.getSessionByHash(sid);
	}
	
}
