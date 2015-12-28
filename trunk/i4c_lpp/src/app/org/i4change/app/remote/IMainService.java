package org.i4change.app.remote;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.i4change.app.dto.basic.ConfigurationDTO;
import org.i4change.app.dto.user.UserAuthDTO;
import org.i4change.app.hibernate.beans.adresses.Country;
import org.i4change.app.hibernate.beans.basic.Naviglobal;
import org.i4change.app.hibernate.beans.basic.Sessiondata;
import org.i4change.app.hibernate.beans.export.PresentationTemplate;
import org.i4change.app.hibernate.beans.user.Userdata;
import org.i4change.app.hibernate.beans.user.Users;

public interface IMainService {

	/**
	 * get Navigation
	 * @param SID
	 * @param language_id
	 * @deprecated
	 * @return
	 */
	public abstract List<Naviglobal> getNavi(String SID, Long language_id,
			Long organization_id);

	/**
	 * gets a user by its SID
	 * @param SID
	 * @param USER_ID
	 * @deprecated
	 * @return
	 */
	public abstract Users getUser(String SID, int USER_ID);

	public abstract UserAuthDTO setNewUserProperty(String SID, String propName,
			Object propValue);

	/**
	 * load this session id before doing anything else
	 * @return a unique session identifier
	 */
	public abstract Sessiondata getsessiondata();

	/**
	 * auth function, use the SID you get by getsessiondata
	 * @param SID
	 * @param Username
	 * @param Userpass
	 * @return a valid user account or an empty user with an error message and level -1
	 */
	public abstract Object loginUser(String SID, String Username,
			String Userpass, Long userlang);

	/**
	 * this function logs a user into if he enteres the app directly into a room
	 * @param SID
	 */
	public abstract void markSessionAsLogedIn(String SID);

	/**
	 * clear this session id
	 * @param SID
	 * @return string value if completed
	 */
	public abstract Long logoutUser(String SID);

	/**
	 * get a list of all states, needs no authentification to load
	 * @return List of State-Objects or null
	 */
	public abstract List<Country> getStates();

	/**
	 * Load if the users can register itself by using the form without logging in, 
	 * needs no authentification to load
	 * @param SID
	 * @return
	 */
	public abstract ConfigurationDTO allowFrontendRegister(String SID);

	/**
	 * Add a user register by an Object
	 * see [registerUser] for the index of the Object
	 * To allow the registering the config_key *allow_frontend_register* has to be the value 1
	 * otherwise the user will get an error code
	 * @param regObject
	 * @return new users_id OR null if an exception, -1 if an error, -4 if mail already taken, -5 if username already taken, -3 if login or pass or mail is empty 
	 */
	public abstract Long registerUserByObjectAdvanced(Map regObjectObj);

	public abstract Long registerUserWebSite(Map regObject);

	/**
	 * logs a user out and deletes his account
	 * @param SID
	 * @return
	 */
	public abstract Long deleteUserIDSelf(String SID);

	/**
	 * @deprecated
	 * @param SID
	 * @return
	 */
	public abstract List<Userdata> getUserdata(String SID);

	public abstract List<PresentationTemplate> getPresentationTemplates(
			String SID, Long organisationId);

	/**
	 * 
	 * @param SID
	 * @param urlEndPoint
	 * @return
	 */
	public abstract LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Object>>> getRssFeedByURL(
			String SID, String urlEndPoint);

	public abstract LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Object>>> getRssFeedByConf(
			String SID);

	public abstract Sessiondata getStoredUserSessionBySID(String sid);

}