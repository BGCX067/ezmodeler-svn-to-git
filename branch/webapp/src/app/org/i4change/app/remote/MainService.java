package org.i4change.app.remote;

import java.util.Date;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.red5.server.api.IConnection;
import org.red5.server.api.Red5;
import org.red5.server.api.service.IPendingServiceCall;
import org.red5.server.api.service.IPendingServiceCallback;
import org.red5.server.api.service.IServiceCapableConnection;
import org.i4change.app.hibernate.Configuration;
import org.i4change.app.data.basic.Navimanagement;
import org.i4change.app.hibernate.Sessiondata;

import org.i4change.app.hibernate.beans.user.Users;
import org.i4change.app.hibernate.beans.user.Userdata;

import org.i4change.app.data.basic.*;
import org.i4change.app.data.user.Usermanagement;
import org.i4change.app.data.user.Statemanagement;

import org.i4change.app.data.basic.AuthLevelmanagement;

import org.i4change.app.session.beans.RoomClient;

/**
 * 
 * @author swagner
 *
 */
public class MainService implements IPendingServiceCallback {
	
	private static final Log log = LogFactory.getLog(MainService.class);
	
	/**
	 * get Navigation
	 * @param SID
	 * @param language_id
	 * @return
	 */
	public List getNavi(String SID, long language_id){
		try {
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        //log.error("getNavi 1: "+users_id);
	        Long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);
	        //log.error("getNavi 2: "+user_level);
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
		long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);
    	if(user_level>2){
    		users = Usermanagement.getInstance().getUser(new Long(USER_ID));
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
    public Object loginUser(String SID, String Username, String Userpass){
    	try {
        	log.debug("loginUser 111: "+SID+" "+Username);
        	IConnection current = Red5.getConnectionLocal();
        	RoomClient currentClient = Application.getClientList().get(current.getClient().getId());
            Object obj = Usermanagement.getInstance().loginUser(SID,Username,Userpass, currentClient);
            
            if (currentClient.getUser_id()!=null && currentClient.getUser_id()>0) {
            	Users us = (Users) obj;
            	currentClient.setFirstname(us.getFirstname());
            	currentClient.setLastname(us.getLastname());
    			Iterator<IConnection> it = current.getScope().getConnections();
    			while (it.hasNext()) {
    				//log.error("hasNext == true");
    				IConnection cons = it.next();
    				//log.error("cons Host: "+cons);
    				if (cons instanceof IServiceCapableConnection) {
    					if (!cons.equals(current)){
    						//log.error("sending roomDisconnect to " + cons);
    						RoomClient rcl = Application.getClientList().get(cons.getClient().getId());
    						//Send to all connected users
							((IServiceCapableConnection) cons).invoke("roomConnect",new Object[] { currentClient }, this);
							//log.error("sending roomDisconnect to " + cons);
    					}
    				}
    			} 
            }

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
    	IConnection current = Red5.getConnectionLocal();
		RoomClient currentClient = Application.getClientList().get(current.getClient().getId());	
		currentClient.setUserObject(null, null, null, null);
    	return Usermanagement.getInstance().logout(SID,users_id);
    }
    
    /**
     * get a list of all states, needs no authentification to load
     * @return List of State-Objects or null
     */
    public List getStates(){
    	return Statemanagement.getInstance().getStates();
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
     * @return new users_id OR null if an exception, -1 if an error, -4 if mail already taken, -5 if username already taken, -3 if login or pass or mail is empty 
     */
    public Long registerUserByObject(Object regObjectObj){
    	try {
    		LinkedHashMap regObject = (LinkedHashMap) regObjectObj;
        	return Usermanagement.getInstance().registerUser(regObject.get("Username").toString(), regObject.get("Userpass").toString(), 
        			regObject.get("lastname").toString(), regObject.get("firstname").toString(), regObject.get("email").toString(), 
        			new Date(), regObject.get("street").toString(), regObject.get("additionalname").toString(), 
        			regObject.get("fax").toString(), regObject.get("zip").toString(), 
        			Long.valueOf(regObject.get("states_id").toString()).longValue(), regObject.get("town").toString(), 
        			Long.valueOf(regObject.get("language_id").toString()).longValue());
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
	public Long registerUser(String SID, String Username, String Userpass, String lastname, 
				String firstname, String email, Date age, String street, String additionalname, 
				String fax, String zip, long states_id, String town, long language_id){
    	return Usermanagement.getInstance().registerUser(Username, Userpass, lastname, firstname, email, 
    			age, street, additionalname, fax, zip, states_id, town, language_id);
	}	
	
	/**
	 * logs a user out and deletes his account
	 * @param SID
	 * @return
	 */
    public Long deleteUserIDSelf(String SID){
    	Long users_id = Sessionmanagement.getInstance().checkSession(SID);
    	Long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);
    	if(user_level>=1){
    		Usermanagement.getInstance().logout(SID,users_id);
    		return Usermanagement.getInstance().deleteUserID(users_id);
    	} else {
    		return new Long(-10);
    	}
    }
    


    public List<Userdata> getUserdata(String SID){
    	Long users_id = Sessionmanagement.getInstance().checkSession(SID);
    	Long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);
    	if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)){
    		return Usermanagement.getInstance().getUserdataDashBoard(users_id);
    	}
    	return null;
    }
    
	public LinkedHashMap<Integer,RoomClient> getUsersByDomain(String SID, String domain) {
    	Long users_id = Sessionmanagement.getInstance().checkSession(SID);
    	Long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);
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

	public void resultReceived(IPendingServiceCall arg0) {
		// TODO Auto-generated method stub
		log.debug("[resultReceived]"+arg0);
	}
    
}
