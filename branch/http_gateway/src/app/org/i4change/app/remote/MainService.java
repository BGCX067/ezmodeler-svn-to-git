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
import org.i4change.app.hibernate.beans.basic.Configuration;
import org.i4change.app.data.basic.Navimanagement;
import org.i4change.app.hibernate.beans.basic.Sessiondata;
import org.i4change.app.hibernate.beans.domain.Organisation_Users;

import org.i4change.app.hibernate.beans.user.Users;
import org.i4change.app.hibernate.beans.user.Userdata;

import org.i4change.app.data.basic.*;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.data.user.CountryDaoImpl;
import org.i4change.app.data.user.Usermanagement;

import org.i4change.app.data.basic.AuthLevelmanagement;
import org.i4change.app.data.domain.daos.OrganisationDaoImpl;
import org.i4change.app.data.domain.daos.OrganisationUserDaoImpl;

import org.i4change.app.session.beans.RoomClient;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 
 * @author swagner
 *
 */
public class MainService {
	
	private static final Log log = LogFactory.getLog(MainService.class);
	
	/**
	 * get Navigation
	 * @param SID
	 * @param language_id
	 * @return
	 */
	public List getNavi(String SID, Long language_id, Long organization_id){
		try {
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        //log.error("getNavi 1: "+users_id);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        //log.error("getNavi 2: "+user_level);
	        
	        //check if this is a Org-Admin
	        log.debug("user_level: "+user_level);
	        if (user_level.equals(1)){
	        	Organisation_Users orgUser = OrganisationUserDaoImpl.getInstance().checkUserInOrganisationId(organization_id, users_id);
	        	log.debug("orgUser: "+orgUser);
	        	log.debug("orgUser.getIsModerator: "+orgUser.getIsModerator());
	        	if (orgUser.getIsModerator()) {
	        		user_level = new Long(2);
	        	}
	        }
	        
			return Navimanagement.getInstance().getMainMenu(user_level,users_id, language_id);
		} catch (Exception err){
			log.error("[getNavi] ", err);
		}
		return null;
	}
  
	/**
	 * gets a user by its SID
	 * @param SID
	 * @param USER_ID
	 * @return
	 */
	public Users getUser(String SID,int USER_ID){
		Users users = new Users();
		Long users_id = Sessionmanagement.getInstance().checkSession(SID);
		long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
    	if(user_level>2){
    		users = UserDaoImpl.getInstance().getUserById(new Long(USER_ID));
    	} else {
    		users.setFirstname("No rights to do this");
    	}
		return users;
	}
	
	/**
	 * This Method is jsut for testing
	 * you can find the corresponding
	 * CLietn Function in
	 * xmlcrm/auth/checkLoginData.lzx
	 * @param myObject2
	 * @return
	 */
	public int testObject(Object myObject2){
		try {
			LinkedHashMap myObject = (LinkedHashMap) myObject2;
			log.debug("testObject "+myObject.size());
			log.debug("testObject "+myObject.get(1));
			log.debug("testObject "+myObject.get("stringObj"));
			return myObject.size();
		} catch (Exception e){
			log.error("ex: ", e);
		}
		return -1;
	}
	
	public Users setNewUserProperty(String SID, String propName, Object propValue) {
		try {
			Long user_id = Sessionmanagement.getInstance().checkSession(SID);
			Long user_level = UserDaoImpl.getInstance().getUserLevelByID(user_id);
			if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)){
				
				Users us = UserDaoImpl.getInstance().getUserById(user_id);
				
				XStream xStream = new XStream(new XppDriver());
				xStream.setMode(XStream.NO_REFERENCES);
				
				if (us.getUserProperties() != null && us.getUserProperties().length() != 0) {
					us.setUserPropsAsObject((Map) xStream.fromXML(us.getUserProperties()));
				} else {
					us.setUserPropsAsObject(new HashMap());
				}
				
				us.getUserPropsAsObject().put(propName, propValue);
				
				if (us.getUserPropsAsObject() != null) {
					XStream xStream_2 = new XStream(new XppDriver());
					xStream_2.setMode(XStream.NO_REFERENCES);
	    			us.setUserProperties(xStream.toXML(us.getUserPropsAsObject()));	
				} else {
					us.setUserProperties(null);
				}
    			UserDaoImpl.getInstance().updateUser(us);
				
    			return us;
			}
		} catch (Exception err) {
			log.error("[setNewUserProperty]",err);
		}
		return null;
	}

	 
	/**
	 * load this session id before doing anything else
	 * @return a unique session identifier
	 */
    public Sessiondata getsessiondata(){
        return Sessionmanagement.getInstance().startsession();
    }   
       
    /**
     * auth function, use the SID you get by getsessiondata
     * @param SID
     * @param Username
     * @param Userpass
     * @return a valid user account or an empty user with an error message and level -1
     */ 
    public Object loginUser(String SID, String Username, String Userpass, Long userlang){
    	try {
        	log.debug("loginUser 111: "+SID+" "+Username);
        	//IConnection current = Red5.getConnectionLocal();
        	//RoomClient currentClient = Application.getClientList().get(current.getClient().getId());
        	RoomClient currentClient = new RoomClient();
            Object obj = Usermanagement.getInstance().loginUser(SID,Username,Userpass, currentClient, userlang);
            
//            if (currentClient.getUser_id()!=null && currentClient.getUser_id()>0) {
//            	Users us = (Users) obj;
//            	currentClient.setFirstname(us.getFirstname());
//            	currentClient.setLastname(us.getLastname());
//    			Iterator<IConnection> it = current.getScope().getConnections();
//    			while (it.hasNext()) {
//    				//log.error("hasNext == true");
//    				IConnection cons = it.next();
//    				//log.error("cons Host: "+cons);
//    				if (cons instanceof IServiceCapableConnection) {
//    					if (!cons.equals(current)){
//    						//log.error("sending roomDisconnect to " + cons);
//    						RoomClient rcl = Application.getClientList().get(cons.getClient().getId());
//    						//Send to all connected users
//							((IServiceCapableConnection) cons).invoke("roomConnect",new Object[] { currentClient }, this);
//							//log.error("sending roomDisconnect to " + cons);
//    					}
//    				}
//    			} 
//            }

			return obj;
    	} catch (Exception err) {
    		log.error("loginUser",err);
    	}
    	return null;
    } 
    
    /**
     * this function logs a user into if he enteres the app directly into a room
     * @param SID
     */
    public void markSessionAsLogedIn(String SID){
    	Sessionmanagement.getInstance().updateUser(SID, -1);
    }
    
    /**
     * clear this session id
     * @param SID
     * @return string value if completed
     */
    public Long logoutUser(String SID){
    	Long users_id = Sessionmanagement.getInstance().checkSession(SID);
    	//IConnection current = Red5.getConnectionLocal();
		//RoomClient currentClient = Application.getClientList().get(current.getClient().getId());	
    	RoomClient currentClient = new RoomClient();
		currentClient.setUserObject(null, null, null, null);
    	return Usermanagement.getInstance().logout(SID,users_id);
    }
    
    /**
     * get a list of all states, needs no authentification to load
     * @return List of State-Objects or null
     */
    public List getStates(){
    	return CountryDaoImpl.getInstance().getCountry();
    }

    /**
     * Load if the users can register itself by using the form without logging in, 
     * needs no authentification to load
     * @param SID
     * @return
     */
    public Configuration allowFrontendRegister(String SID){
    	return Configurationmanagement.getInstance().getConfKey(3, "allow_frontend_register");
    }
    
    /**
     * Add a user register by an Object
     * see [registerUser] for the index of the Object
     * To allow the registering the config_key *allow_frontend_register* has to be the value 1
     * otherwise the user will get an error code
     * @param regObject
     * @deprecated
     * @return new users_id OR null if an exception, -1 if an error, -4 if mail already taken, -5 if username already taken, -3 if login or pass or mail is empty 
     */
    public Long registerUserByObject(Object regObjectObj){
    	try {
    		LinkedHashMap regObject = (LinkedHashMap) regObjectObj;
        	return Usermanagement.getInstance().registerUserNew(regObject.get("Username").toString(), regObject.get("Userpass").toString(), 
        			regObject.get("lastname").toString(), regObject.get("firstname").toString(), regObject.get("email").toString(), 
        			new Date(), regObject.get("street").toString(), regObject.get("additionalname").toString(), 
        			regObject.get("fax").toString(), regObject.get("zip").toString(), 
        			Long.valueOf(regObject.get("states_id").toString()).longValue(), regObject.get("town").toString(), 
        			Long.valueOf(regObject.get("language_id").toString()).longValue(),
        			regObject.get("domain").toString(),
        			Integer.valueOf(regObject.get("port").toString()).intValue(),
        			regObject.get("webapp").toString());
    	} catch (Exception ex) {
    		log.error("registerUserByObject",ex);
    	}
    	return new Long(-1);
    }
    
    /**
     * Add a user register by an Object
     * see [registerUser] for the index of the Object
     * To allow the registering the config_key *allow_frontend_register* has to be the value 1
     * otherwise the user will get an error code
     * @param regObject
     * @return new users_id OR null if an exception, -1 if an error, -4 if mail already taken, -5 if username already taken, -3 if login or pass or mail is empty 
     */
    public Long registerUserByObjectAdvanced(Map regObjectObj){
    	try {
    		Map regObject = (Map) regObjectObj.get("personalData");
        	return Usermanagement.getInstance().registerUserNewWithPersonalDetails(
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
//    	return UserDaoImpl.getInstance().registerUser(Username, Userpass, lastname, firstname, email, 
//    			age, street, additionalname, fax, zip, states_id, town, language_id);
//	}	
	
	/**
	 * logs a user out and deletes his account
	 * @param SID
	 * @return
	 */
    public Long deleteUserIDSelf(String SID){
    	Long users_id = Sessionmanagement.getInstance().checkSession(SID);
    	long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
    	if(user_level>=1){
    		Usermanagement.getInstance().logout(SID,users_id);
    		return UserDaoImpl.getInstance().deleteUserID(users_id);
    	} else {
    		return new Long(-10);
    	}
    }
    


    public List<Userdata> getUserdata(String SID){
    	Long users_id = Sessionmanagement.getInstance().checkSession(SID);
    	Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
    	if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)){
    		return UserDaoImpl.getInstance().getUserdataDashBoard(users_id);
    	}
    	return null;
    }
    
	public LinkedHashMap<Integer,RoomClient> getUsersByDomain(String SID, String domain) {
    	Long users_id = Sessionmanagement.getInstance().checkSession(SID);
    	Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
    	if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)){
    		LinkedHashMap<Integer,RoomClient> lMap = new LinkedHashMap<Integer,RoomClient>();
    		Integer counter = 0;
    		for (Iterator<String> it = Application.getClientList().keySet().iterator();it.hasNext();) {
    			RoomClient rc = Application.getClientList().get(it.next());
    			if (rc.getDomain().equals(domain)) {
    				lMap.put(counter, rc);
    				counter++;
    			}
    		}
    		return lMap;
    	} else {
    		return null;
    	}
	}
    
    /**
     * 
     * @param SID
     * @param urlEndPoint
     * @return
     */
    public LinkedHashMap<String,LinkedHashMap<String,LinkedHashMap<String,Object>>> getRssFeedByURL(String SID, String urlEndPoint) {
    	Long users_id = Sessionmanagement.getInstance().checkSession(SID);
    	Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
    	if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)){
    		return LoadAtomRssFeed.getInstance().parseRssFeed(urlEndPoint);
    	} else {
    		return null;
    	}
    }
    
    public LinkedHashMap<String,LinkedHashMap<String,LinkedHashMap<String,Object>>> getRssFeedByConf(String SID) {
    	Long users_id = Sessionmanagement.getInstance().checkSession(SID);
    	Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
    	if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)){
    		return LoadAtomRssFeed.getInstance().getRssFeedSingleStored(user_level);
    	} else {
    		return null;
    	}
    }
    
}
