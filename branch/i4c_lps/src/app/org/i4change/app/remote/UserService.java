package org.i4change.app.remote;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.AuthLevelmanagement;
import org.i4change.app.data.basic.Sessionmanagement;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.data.diagram.DiagramInstanceObjectDaoImpl;
import org.i4change.app.data.domain.daos.OrganisationDaoImpl;
import org.i4change.app.data.domain.daos.OrganisationUserDaoImpl;
import org.i4change.app.data.user.daos.InvoiceDaoImpl;
import org.i4change.app.data.user.daos.TransactionPaypalDaoImpl;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.data.user.daos.UserSidebarPropertyDaoImpl;
import org.i4change.app.data.user.Usermanagement;
import org.i4change.app.data.user.Salutationmanagement;
import org.i4change.app.hibernate.beans.domain.Organisation;
import org.i4change.app.hibernate.beans.domain.Organisation_Users;
import org.i4change.app.hibernate.beans.user.UserSidebarProperty;
import org.i4change.app.hibernate.beans.user.Users;
import org.i4change.app.utils.math.CalendarPatterns;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 
 * @author swagner
 *
 */
public class UserService {
	
	private static final Log log = LogFactory.getLog(UserService.class);	
	
	//Spring Bean Injection
	private Application application;
	private Usermanagement usermanagement;
	private UserDaoImpl userDaoImpl;
	
	public Application getApplication() {
		return application;
	}
	public void setApplication(Application application) {
		this.application = application;
	}
	
	public Usermanagement getUsermanagement() {
		return usermanagement;
	}
	public void setUsermanagement(Usermanagement usermanagement) {
		this.usermanagement = usermanagement;
	}
	
	public UserDaoImpl getUserDaoImpl() {
		return userDaoImpl;
	}
	public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}
	
	/**
	 * get your own user-object
	 * @param SID
	 * @param user_id
	 * @return
	 */
	public Users getUserSelf(String SID){
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        
	        Users users = UserDaoImpl.getInstance().getUserById(users_id);
	        if (users.getUserProperties() != null && users.getUserProperties().length() != 0) {
				XStream xStream = new XStream(new XppDriver());
				xStream.setMode(XStream.NO_REFERENCES);
				
				users.setUserPropsAsObject((Map) xStream.fromXML(users.getUserProperties()));
			}
	        if (users.getXmlStringRegObjectObj() != null && users.getXmlStringRegObjectObj().length() != 0) {
	        	XStream xStream = new XStream(new XppDriver());
				xStream.setMode(XStream.NO_REFERENCES);
				
				users.setRegObjectObj((Map) xStream.fromXML(users.getXmlStringRegObjectObj()));
	        }
	        return users;
		} catch (Exception err) {
			log.error("getUserSelf ",err);
		}
        return null;
	}
	
	public Long resetUserPwd(String SID, String email, String login, String applink){
		Sessionmanagement.getInstance().checkSession(SID);
		return Usermanagement.getInstance().resetUser(email, login, applink);
	}
	
	public Object getUserByHash(String SID, String hash) {
		Sessionmanagement.getInstance().checkSession(SID);
		return UserDaoImpl.getInstance().getUserByHash(hash);
	}
	
	public Object resetPassByHash(String SID, String hash, String pass) {
		Sessionmanagement.getInstance().checkSession(SID);
		return UserDaoImpl.getInstance().resetPassByHash(hash,pass);
	}
	
	/**
	 * get user by id, admin only
	 * @param SID
	 * @param user_id
	 * @return
	 */
	public Users getUserById(String SID, long user_id){
		try {
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        //TODO: Fix that only Org-Moderators and Admins are allowed to invoke this RPC-Call
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	return UserDaoImpl.getInstance().getUserById(user_id);
	        }
	        
		} catch (Exception err) {
			log.error("[getUserById]",err);
		}
		return null;
	}
	
	/**
	 * refreshes the current SID
	 * @param SID
	 * @return
	 */
	public String refreshSession(String SID){
		try {
	        Sessionmanagement.getInstance().checkSession(SID);
	        return "ok";
		} catch (Exception err) {
			log.error("[refreshSession]",err);
		}
		return "error";
	}
	
	/**
	 * get all availible Salutations
	 * @param SID
	 * @return
	 */
	public List getUserSalutations(String SID, long language_id){
        return Salutationmanagement.getInstance().getUserSalutations(language_id);
	}
	
	/**
	 * 
	 * @param SID
	 * @param searchcriteria login,lastname,firstname,user_id
	 * @param searchstring
	 * @param max
	 * @param start
	 * @param orderby login,lastname,firstname,user_id
	 * @param asc
	 * @return
	 */
    public List searchUser(String SID, String searchcriteria ,String searchstring, int max, int start, String orderby, boolean asc){   	
        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);    	
    	return UserDaoImpl.getInstance().searchUser(user_level,searchcriteria,searchstring,max,start,orderby,asc);
    } 
    
    public SearchResult lookUpUser(String SID, String searchstring, int max, int start, String orderby, boolean asc){   	
        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);  
        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
        	SearchResult sResult = new SearchResult();
        	sResult.setObjectName(Users.class.getName());
        	sResult.setResult(UserDaoImpl.getInstance().lookUpUser(searchstring,max,start,orderby,asc));
        	sResult.setRecords(UserDaoImpl.getInstance().lookUpUserMax(searchstring, max, start, asc));
        	return sResult;
        }
        return null;
    }   
    
    /**
     * get a list of all users of an organisation
     * @param SID
     * @param organisation_id
     * @param start
     * @param max
     * @param orderby
     * @param asc
     * @return
     */
    public List getOrgUserList(String SID, long organisation_id, int start, int max, String orderby, boolean asc){
        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
        return OrganisationUserDaoImpl.getInstance().getUsersByOrganisationId(organisation_id, start, max, orderby, asc);
    }
    
    public List getUserListByModForm(String SID){
        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
        return Usermanagement.getInstance().getUserByMod(user_level, users_id);
    }
    
    /**
     * gat a list of all organisations of an user
     * @param SID
     * @param client_user
     * @param start
     * @param max
     * @param orderby
     * @param asc
     * @return
     */
    public List getOrganisationListByUser(String SID, long client_user, int start, int max, String orderby, boolean asc){
        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
        long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
        return OrganisationDaoImpl.getInstance().getOrganisationsByUserId(user_level, client_user, start, max, orderby, asc);
    }    
    
    /**
     * gets a list of all not assigned organisations of a user
     * @param SID
     * @param client_user
     * @param start
     * @param max
     * @param orderby
     * @param asc
     * @return
     */
    public List getRestOrganisationListByUser(String SID, long client_user, int start, int max, String orderby, boolean asc){
        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
        return OrganisationDaoImpl.getInstance().getRestOrganisationsByUserId(user_level, client_user, start, max, orderby, asc);
    }
    
    
    /**
     * gets a hole user-list(admin-role only)
     * @param SID
     * @param start
     * @param max
     * @param orderby
     * @return
     */
    public SearchResult getUserList(String SID, int start, int max, String orderby, boolean asc){
        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
        if (AuthLevelmanagement.getInstance().checkAdminLevel(user_level)){
        	return UserDaoImpl.getInstance().getUsersList(start, max, orderby, asc);
        }
        return null;
    }
    
    
    public SearchResult getUserListByOrganization(String SID, int start, int max, String orderby, boolean asc, long organization_id){
        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
        //TODO: Fix that only Org.-Admins are allowed to process this RPC-Call
        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)){
        	return UserDaoImpl.getInstance().getUsersListByOrganization(start, max, orderby, asc, organization_id);
        }
        return null;
    }
    
    
    /**
     * returns a List of Users of this Organization
     * @param SID
     * @param organization_id
     * @return
     */
    public List<Users> getUserByOrganization(String SID, Long organization_id) {
    	try {
    		Long users_id = Sessionmanagement.getInstance().checkSession(SID);
            Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
    		if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
    			return OrganisationUserDaoImpl.getInstance().getUsersByOrganisationId(organization_id);
    		}
    	} catch (Exception err) {
    		log.error("[getUserByOrganization]",err);
    	}
    	return null;
    }
    
    /**
     * updates the user profile, every user can update his own profile
     * @param SID
     * @param argObject
     * @return user_id or NULL or negativ value (error_id)
     */
    public Long updateUserSelfSmall(String SID, Map values ){
    	try {
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if(user_level!=null && user_level>=1){
	        	return Usermanagement.getInstance().saveOrUpdateUser(new Long(3),values, users_id);
	        } else {
	            return new Long(-2);
	        }
    	} catch (Exception err){
    		log.error("[updateUserSelfSmall] "+err);
    		return new Long(-1);
    	}
    }    
    
    public Long updateUserProfile(String SID, Map values) {
    	try {
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if(user_level!=null && user_level>=1){
	        	return this.usermanagement.updateUserProfile(values);
	        } else {
	            return new Long(-2);
	        }
    	} catch (Exception err){
    		log.error("[updateUserSelfSmall] "+err);
    		return new Long(-1);
    	}
    }
    
    /**
     * 
     * This function is triggered in the Administration Interface
     * 
     * @param SID
     * @param regObjectObj
     * @return
     */
    public Long saveOrUpdateUser(String SID, Map argObjectMap){
    	try {
    		Long user_idClient = null;
    		if (argObjectMap.get("user_idClient")!=null){
    			user_idClient = Long.valueOf(argObjectMap.get("user_idClient").toString()).longValue();
    		}
    		//log.error("saveOrUpdateUser1: ");
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        //log.error("saveOrUpdateUser2: ");
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);	
	        
//	        log.error("saveOrUpdateUser1: "+argObjectMap.get("organisations"));
//	        log.error("saveOrUpdateUser2: "+argObjectMap.get("organisations").getClass());
//	        log.error("saveOrUpdateUser3: "+argObjectMap.get("user_idClient"));
	        //TODO: there is a BUG here: value send is Time as GMT but here it gets CEST which is wrong	  
	        //but maybe a OS-related-issue
	        //log.error("saveOrUpdateUser4: "+argObjectMap.get("userage"));
	        //log.error("saveOrUpdateUser5: "+argObjectMap.get("userage").getClass());
	        
	        Vector organisations = (Vector) argObjectMap.get("organisations");
	        Date age = null;
	        if (argObjectMap.get("userage") instanceof Date){
	        	age = (Date) argObjectMap.get("userage");
	        	log.error("saveOrUpdateUser7: "+age.getTimezoneOffset());
	        }	        
	        
	        log.debug("argObjectMap: "+argObjectMap);
	        
	        Date expireDate = null;
			if (argObjectMap.get("expireDate") != null && !argObjectMap.get("expireDate").equals("null")) {
				expireDate = CalendarPatterns.parseDate(argObjectMap.get("expireDate").toString());
			}
			
			Long maxWorkDays = null;
			if (argObjectMap.get("maxWorkDays") != null && !argObjectMap.get("maxWorkDays").equals("null")) {
				maxWorkDays = Long.valueOf(argObjectMap.get("maxWorkDays").toString()).longValue();
			}
			
			Float pricePerUser = null;
			if (argObjectMap.get("pricePerUser") != null && !argObjectMap.get("pricePerUser").equals("null")) {
				pricePerUser = Float.valueOf(argObjectMap.get("pricePerUser").toString()).floatValue();
			}

			Vector discounts = null;
			if (argObjectMap.get("discounts") != null && !argObjectMap.get("discounts").equals("null")) {
				discounts = (Vector) argObjectMap.get("discounts");
			}
	        //log.debug("saveOrUpdateUser6: "+age);
			
			log.debug(user_level);
			log.debug(user_idClient);
			log.debug(Long.valueOf(argObjectMap.get("level_id").toString()).longValue()); 
			log.debug(argObjectMap.get("login").toString());
			log.debug(argObjectMap.get("password").toString()); 
			log.debug(argObjectMap.get("lastname").toString()); 
			log.debug(argObjectMap.get("firstname").toString());
			log.debug(age);
			log.debug(argObjectMap.get("street").toString()); 
			log.debug(argObjectMap.get("additionalname").toString()); 
			log.debug(argObjectMap.get("zip").toString());
			log.debug(Long.valueOf(argObjectMap.get("states_id").toString())
					.longValue());
			log.debug(argObjectMap.get("town").toString());
			log.debug(Integer.valueOf(argObjectMap.get("availible").toString())
					.intValue());
			log.debug(argObjectMap.get("telefon").toString());
			log.debug(argObjectMap.get("fax").toString());
			log.debug(argObjectMap.get("mobil").toString());
			log.debug(argObjectMap.get("email").toString());
			log.debug(argObjectMap.get("comment").toString());
			log.debug(Integer.valueOf(argObjectMap.get("status").toString())
					.intValue());
			log.debug(organisations);
			log.debug(Integer.valueOf(argObjectMap.get("title_id").toString())
					.intValue());
			log.debug(Boolean.valueOf(argObjectMap.get("isPending").toString())
					.booleanValue());
			log.debug(expireDate);
			log.debug(maxWorkDays);
			log.debug(Boolean.valueOf(
					argObjectMap.get("useDefaultDiscounts").toString())
					.booleanValue());
			log.debug(Boolean.valueOf(
					argObjectMap.get("unlimitedLicenses").toString())
					.booleanValue());
			log.debug(Long.valueOf(
					argObjectMap.get("licenseUserPayed").toString())
					.longValue());
			log
					.debug(Long.valueOf(
							argObjectMap.get("licenseUserUsed").toString())
							.longValue());
			log.debug(pricePerUser);
			log.debug(discounts);
	       
    		if (user_idClient==null || user_idClient==0){
	        	return Usermanagement.getInstance().addUserByAdministrationPanel(user_level, 
	        			Long.valueOf(argObjectMap.get("level_id").toString()).longValue(), 
	        			Integer.valueOf(argObjectMap.get("availible").toString()).intValue(),
	        			Integer.valueOf(argObjectMap.get("status").toString()).intValue(),
	        			argObjectMap.get("login").toString(), argObjectMap.get("password").toString(), 
	        			argObjectMap.get("lastname").toString(), argObjectMap.get("firstname").toString(), 
	        			argObjectMap.get("email").toString(), age, 
	        			argObjectMap.get("street").toString(), argObjectMap.get("additionalname").toString(), 
	        			argObjectMap.get("fax").toString(), argObjectMap.get("zip").toString(), 
	        			Long.valueOf(argObjectMap.get("states_id").toString()).longValue(), 
	        			argObjectMap.get("town").toString(), 
	        			0,
	        			organisations, 
	        			Boolean.valueOf(argObjectMap.get("isPending").toString()).booleanValue(),
	        			expireDate, maxWorkDays,
	        			Boolean.valueOf(argObjectMap.get("useDefaultDiscounts").toString()).booleanValue(),
	        			Boolean.valueOf(argObjectMap.get("unlimitedLicenses").toString()).booleanValue(),
	        			Long.valueOf(argObjectMap.get("licenseUserPayed").toString()).longValue(),
	        			Long.valueOf(argObjectMap.get("licenseUserUsed").toString()).longValue(),
	        			pricePerUser, discounts); 	
    		} else {
		        return Usermanagement.getInstance().updateByAdministrationPanel(user_level,user_idClient, 
		        		Long.valueOf(argObjectMap.get("level_id").toString()).longValue(), argObjectMap.get("login").toString(), 
		        		argObjectMap.get("password").toString(), argObjectMap.get("lastname").toString(), 
		        		argObjectMap.get("firstname").toString(), age, 
		        		argObjectMap.get("street").toString(), argObjectMap.get("additionalname").toString(), 
		        		argObjectMap.get("zip").toString(), Long.valueOf(argObjectMap.get("states_id").toString()).longValue(), 
		        		argObjectMap.get("town").toString(), Integer.valueOf(argObjectMap.get("availible").toString()).intValue(),
		        		argObjectMap.get("telefon").toString(),argObjectMap.get("fax").toString(),
		        		argObjectMap.get("mobil").toString(),
		        		argObjectMap.get("email").toString(),argObjectMap.get("comment").toString(),
		        		Integer.valueOf(argObjectMap.get("status").toString()).intValue(),
		        		organisations,
		        		Integer.valueOf(argObjectMap.get("title_id").toString()).intValue(), 
		        		Boolean.valueOf(argObjectMap.get("isPending").toString()).booleanValue(),
	        			expireDate,maxWorkDays,
	        			Boolean.valueOf(argObjectMap.get("useDefaultDiscounts").toString()).booleanValue(),
	        			Boolean.valueOf(argObjectMap.get("unlimitedLicenses").toString()).booleanValue(),
	        			Long.valueOf(argObjectMap.get("licenseUserPayed").toString()).longValue(),
	        			Long.valueOf(argObjectMap.get("licenseUserUsed").toString()).longValue(),
	        			pricePerUser, discounts); 
    		}
    		
    	} catch (Exception ex) {
    		log.error("[saveOrUpdateUser]: ",ex);
    	}
    	return null;
    }   
    
    /**
     * 
     * This function is triggered from the Moderation-User-Administration
     * 
     * @param SID
     * @param regObjectObj
     * @return
     */
    public Long saveOrUpdateUserOnly(String SID, Object regObjectObj){
    	try {
    		LinkedHashMap argObjectMap = (LinkedHashMap) regObjectObj;
    		Long user_idClient = null;
    		if (argObjectMap.get("user_idClient")!=null){
    			user_idClient = Long.valueOf(argObjectMap.get("user_idClient").toString()).longValue();
    		}
    		//log.error("saveOrUpdateUser1: ");
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        //log.error("saveOrUpdateUser2: ");
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);	
	        
//	        log.error("saveOrUpdateUser1: "+argObjectMap.get("organisations"));
//	        log.error("saveOrUpdateUser2: "+argObjectMap.get("organisations").getClass());
//	        log.error("saveOrUpdateUser3: "+argObjectMap.get("user_idClient"));
	        //TODO: there is a BUG here: value send is Time as GMT but here it gets CEST which is wrong	  
	        //but maybe a OS-related-issue
	        //log.error("saveOrUpdateUser4: "+argObjectMap.get("userage"));
	        //log.error("saveOrUpdateUser5: "+argObjectMap.get("userage").getClass());
	        
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	Date age = null;
		        if (argObjectMap.get("userage") instanceof Date){
		        	age = (Date) argObjectMap.get("userage");
		        	log.error("saveOrUpdateUserOnly TimeZoneOffset: "+age.getTimezoneOffset());
		        }	

	    		if (user_idClient==null || user_idClient==0){
		        	return Usermanagement.getInstance().addUserOrgModerator(users_id, 
		        			Integer.valueOf(argObjectMap.get("availible").toString()).intValue(),Integer.valueOf(argObjectMap.get("status").toString()).intValue(),
		        			argObjectMap.get("login").toString(), argObjectMap.get("password").toString(), 
		        			argObjectMap.get("lastname").toString(), argObjectMap.get("firstname").toString(), 
		        			argObjectMap.get("email").toString(), age, 
		        			argObjectMap.get("street").toString(), argObjectMap.get("additionalname").toString(), 
		        			argObjectMap.get("fax").toString(), argObjectMap.get("zip").toString(), 
		        			Long.valueOf(argObjectMap.get("states_id").toString()).longValue(), argObjectMap.get("town").toString(), 
		        			0,
		        			Long.valueOf(argObjectMap.get("organisation_id").toString()).longValue(),
		        			Boolean.valueOf(argObjectMap.get("isModerator").toString()).booleanValue(),
		        			Boolean.valueOf(argObjectMap.get("sendMail").toString()).booleanValue()); 	
	    		} else {
			        return Usermanagement.getInstance().updateUserByOrgModerator(users_id, user_idClient,
			        		argObjectMap.get("login").toString(), 
			        		argObjectMap.get("password").toString(), argObjectMap.get("lastname").toString(), 
			        		argObjectMap.get("firstname").toString(), age, 
			        		argObjectMap.get("street").toString(), argObjectMap.get("additionalname").toString(), 
			        		argObjectMap.get("zip").toString(), Long.valueOf(argObjectMap.get("states_id").toString()).longValue(), 
			        		argObjectMap.get("town").toString(), Integer.valueOf(argObjectMap.get("availible").toString()).intValue(),
			        		argObjectMap.get("telefon").toString(),argObjectMap.get("fax").toString(),
			        		argObjectMap.get("mobil").toString(),
			        		argObjectMap.get("email").toString(),argObjectMap.get("comment").toString(),
			        		Integer.valueOf(argObjectMap.get("status").toString()).intValue(),
			        		Boolean.valueOf(argObjectMap.get("isModerator").toString()).booleanValue(),
			        		Integer.valueOf(argObjectMap.get("title_id").toString()).intValue(),
			        		Long.valueOf(argObjectMap.get("organisation_id").toString()).longValue(),
		        			Boolean.valueOf(argObjectMap.get("sendMail").toString()).booleanValue()); 
	    		}
	        }
	        		
	                

	        //log.error("saveOrUpdateUser6: "+age);
	       
    	} catch (Exception ex) {
    		log.error("[saveOrUpdateUser]: ",ex);
    	}
    	return null;
    } 
    
    
    
    /**
     * deletes a user
     * @param SID
     * @param user_idClient
     * @return
     */
    public Long deleteUserAdmin(String SID, int user_idClient){
    	Long users_id = Sessionmanagement.getInstance().checkSession(SID);
    	Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
    	if(AuthLevelmanagement.getInstance().checkAdminLevel(user_level)){
    		if (users_id!=user_idClient){
    		return UserDaoImpl.getInstance().deleteUserID(user_idClient);
    		} else {
    			return new Long(-10);
    		}
    	} else {
    		return new Long(-11);
    	}
    } 
    
    public Long deleteUserFromOrganization(String SID, long user_idClient, long organization_id){
    	Long users_id = Sessionmanagement.getInstance().checkSession(SID);
    	Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
    	if(AuthLevelmanagement.getInstance().checkUserLevel(user_level)){
    		
    		//UserDaoImpl.getInstance().
    		Organisation_Users orgUser = OrganisationUserDaoImpl.getInstance().checkUserInOrganisationId(organization_id, user_idClient);
    		if (orgUser!= null) {
    			orgUser.setDeleted("true");
    			orgUser.setUpdatetime(new Date());
    			OrganisationUserDaoImpl.getInstance().updateUsersByOrganisationUser(orgUser);
    			
    			return new Long(-49);
    		} else {
    			return new Long(-1);
    		}
    		
    	} else {
    		return new Long(-1);
    	}
    } 
    
    /**
     * updates the individual Diagram Sidebar settings
     * @param SID
     * @param userSidebarPropertyId
     * @param diagramNo
     * @param propMap
     * @return
     */
    public Long saveOrUpdateUserSidebarPropertyByDiagram(String SID, Long userSidebarPropertyId, Long diagramNo, Map propMap) {
    	try {
			Long user_id = Sessionmanagement.getInstance().checkSession(SID);
			Long user_level = UserDaoImpl.getInstance().getUserLevelByID(user_id);

			if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
				
				if (userSidebarPropertyId == null || userSidebarPropertyId == 0) {
					return UserSidebarPropertyDaoImpl.getInstance().addUserSidebarProperty(diagramNo, user_id, propMap);
				} else {
					return UserSidebarPropertyDaoImpl.getInstance().updateUserSidebarProperty(userSidebarPropertyId, propMap);
				}
				
			}			
			
		} catch (Exception err) {
			log.error("saveOrUpdateUserSidebarPropertyByDiagram: ",err);
		}
		return null;
    }
    
    /**
     * get the individual Diagram Sidebar settings
     * @param SID
     * @param diagramNo
     * @return
     */
    public UserSidebarProperty getUserSidebarPropertyByDiagram(String SID, Long diagramNo) {
    	try {
			Long user_id = Sessionmanagement.getInstance().checkSession(SID);
			Long user_level = UserDaoImpl.getInstance().getUserLevelByID(user_id);

			if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
				
				return UserSidebarPropertyDaoImpl.getInstance().getUserSidebarPropertyByDiagram(diagramNo, user_id);
				
			}			
			
		} catch (Exception err) {
			log.error("getUserSidebarPropertyByDiagram: ",err);
		}
		return null;
    }
    
    /**
     * 
     * @param SID
     * @return
     */
    public Long checkUserStatus(String SID) {
    	try {
    		Long users_id = Sessionmanagement.getInstance().checkSession(SID);
            Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
    		if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
    			
    			Users user = this.userDaoImpl.getUserById(users_id);
    			
    			//Check if the User-Account has expired
    			if (user.getExpireDate() != null) {
				
					Date now = new Date();
					
					if (user.getExpireDate().getTime() < now.getTime()) {
						return new Long(-44);
					}
					
				}
				
    			//check if the user has reached his maxFreeWorkdays => only do that in case the user is pending
    			if (user.getIsPending()) {
					if (user.getMaxWorkDays() != null && user.getUsedWorkDays() != null && user.getUsedWorkDays() >= user.getMaxWorkDays()){
						return new Long(-45);
					} else {
						//check if today is already a new WorkDay
						if (user.getLastWorkDay() == null || user.getUsedWorkDays() == null) {
							user.setUsedWorkDays(new Long(1));
							//int year, int month, int dayOfMonth, int hourOfDay,int minute
							GregorianCalendar cal = new GregorianCalendar();
							long dt = new GregorianCalendar(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),0,0).getTimeInMillis();
							Date todayMidnight = new Date(dt);
							user.setLastWorkDay(todayMidnight);
							this.userDaoImpl.updateUser(user);
						} else {
							Date now = new Date();
							Long timeDifferenceInMilliSeconds = now.getTime() - user.getLastWorkDay().getTime();
							//log.debug("timeDifferenceInMilliSeconds: "+timeDifferenceInMilliSeconds);
							//log.debug("must########################: "+86400000);
							if (timeDifferenceInMilliSeconds >= 86400000) {
								//new Workdays
								user.setUsedWorkDays(user.getUsedWorkDays()+1);
								//int year, int month, int dayOfMonth, int hourOfDay,int minute
								GregorianCalendar cal = new GregorianCalendar();
								long dt = new GregorianCalendar(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),0,0).getTimeInMillis();
								Date todayMidnight = new Date(dt);
								user.setLastWorkDay(todayMidnight);
								this.userDaoImpl.updateUser(user);
							}
						}
					}
    			}
    			
    			return new Long(2);
    		}
    		
    	} catch (Exception err) {
    		log.error("[checkUserStatus]",err);
    	}
    	return new Long(-1);
    }
    
    public Long activateUserByMod(String SID, Long user_id) {
    	try {
    		
    		Long users_id = Sessionmanagement.getInstance().checkSession(SID);
            Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
    		if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
    			
    			Users userMod = this.userDaoImpl.getUserById(users_id);
    			
    			if (userMod.getUnlimitedLicenses() == null || !userMod.getUnlimitedLicenses()){
    				
    				long licensePayed = 0L;
    				if (userMod.getLicenseUserPayed() != null) {
    					licensePayed = userMod.getLicenseUserPayed();
    				}
    				
    				long licenseUsed = 0L;
    				if (userMod.getLicenseUserUsed() != null) {
    					licenseUsed = userMod.getLicenseUserUsed();
    				}
    				
    				if ((licensePayed - licenseUsed) <= 0) {
    					return new Long(-51);
    				}
    			}
    			
    			long licenseUsedToPay = 0L;
				if (userMod.getLicenseUserUsed() != null) {
					licenseUsedToPay = userMod.getLicenseUserUsed();
				}
				licenseUsedToPay++;
				userMod.setLicenseUserUsed(licenseUsedToPay);
				this.userDaoImpl.updateUser(userMod);
    			
    			Users user = this.userDaoImpl.getUserById(user_id);
    			
    			if (user.getIsPending()) {
					
					Integer expireDateMonthsMax = 12;
		        	Long timeToExpire = new Long(expireDateMonthsMax) * 31 * 86400000;
		        	Date currentDate = new Date();
		        	Date expireDate = new Date(currentDate.getTime()+timeToExpire);
					
		        	user.setExpireDate(expireDate);
		        	user.setIsPending(false);
		        	
					UserDaoImpl.getInstance().updateUser(user);
				
				} else {
					
					Date currentDate = new Date();
					
					//If this user has endless email do not change that at all
					if (user.getExpireDate() == null) {
						log.debug("User with endless Expire Date did buy a License");
						
					} else if (user.getExpireDate().getTime() < currentDate.getTime()) {
						//If this users expire-Date is in the past we need to use the current Date to renew his license
						
						Integer expireDateMonthsMax = 12;
			        	Long timeToExpire = new Long(expireDateMonthsMax) * 31 * 86400000;
			        	Date expireDate = new Date(currentDate.getTime()+timeToExpire);
						
			        	user.setExpireDate(expireDate);
			        	
						UserDaoImpl.getInstance().updateUser(user);
						
					} else {
						//If this user is still valid, add the 12 months to his account
						
						Integer expireDateMonthsMax = 12;
			        	Long timeToExpire = new Long(expireDateMonthsMax) * 31 * 86400000;
			        	Date expireDate = new Date(user.getExpireDate().getTime()+timeToExpire);
						
			        	user.setExpireDate(expireDate);
			        	
						UserDaoImpl.getInstance().updateUser(user);
						
					}
					
				}
    			
    			return new Long(1);
    		}
    		
    	} catch (Exception err) {
    		log.error("[activateUserByMod]",err);
    	}
    	return new Long(-1);
    }
    
    public Long addTransaction(String SID, String amount, Long numberOfLicenses) {
    	try {
    		Long users_id = Sessionmanagement.getInstance().checkSession(SID);
            Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
    		if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
    			return TransactionPaypalDaoImpl.getInstance().addTransactionByUser(users_id, amount, numberOfLicenses);
    		}
    	} catch (Exception err) {
    		log.error("[addTransaction]",err);
    	}
    	return new Long(-1);
    }
    
    public SearchResult getTransactions(String SID, int start ,int max, String orderby, boolean asc){
         try {
        	Long users_id = Sessionmanagement.getInstance().checkSession(SID);
            Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id); 
     		if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
     			return InvoiceDaoImpl.getInstance().getTransactionsByUser(start, max, orderby, asc, users_id);
     		}
     	} catch (Exception err) {
     		log.error("[addTransaction]",err);
     	}
     	return null;
    }
    
}
