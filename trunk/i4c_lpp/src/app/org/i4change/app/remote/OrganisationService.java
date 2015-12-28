package org.i4change.app.remote;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.AuthLevelmanagement;
import org.i4change.app.data.basic.Configurationmanagement;
import org.i4change.app.data.basic.Sessionmanagement;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.data.domain.daos.OrganisationDaoImpl;
import org.i4change.app.data.domain.daos.OrganisationUserDaoImpl;
import org.i4change.app.data.domain.daos.OrganisationsDiscountDaoImpl;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.dto.organization.OrganisationDetailedDTO;
import org.i4change.app.dto.user.OrganisationDTO;
import org.i4change.app.dto.user.OrganisationUserDTO;
import org.i4change.app.dto.user.UserOrgDTO;
import org.i4change.app.hibernate.beans.domain.Organisation;
import org.i4change.app.hibernate.beans.domain.Organisation_Users;
import org.i4change.app.hibernate.beans.user.Users;
import org.i4change.app.session.beans.RoomClient;
import org.i4change.app.utils.math.CalendarPatterns;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 
 * @author swagner
 *
 */
public class OrganisationService implements IOrganisationService {
	
	//Spring loaded Bean
	private IApplication application;
	private OrganisationDaoImpl organisationDaoImpl;
	private Sessionmanagement sessionmanagement;
	private UserDaoImpl userDaoImpl;
	private Configurationmanagement configurationmanagement;
	private OrganisationUserDaoImpl organisationUserDaoImpl;
	
	public IApplication getApplication() {
		return application;
	}
	public void setApplication(IApplication application) {
		this.application = application;
	}
	public OrganisationDaoImpl getOrganisationDaoImpl() {
		return organisationDaoImpl;
	}
	public void setOrganisationDaoImpl(OrganisationDaoImpl organisationDaoImpl) {
		this.organisationDaoImpl = organisationDaoImpl;
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

	public Configurationmanagement getConfigurationmanagement() {
		return configurationmanagement;
	}
	public void setConfigurationmanagement(
			Configurationmanagement configurationmanagement) {
		this.configurationmanagement = configurationmanagement;
	}

	public OrganisationUserDaoImpl getOrganisationUserDaoImpl() {
		return organisationUserDaoImpl;
	}
	public void setOrganisationUserDaoImpl(
			OrganisationUserDaoImpl organisationUserDaoImpl) {
		this.organisationUserDaoImpl = organisationUserDaoImpl;
	}


	private static final Log log = LogFactory.getLog(OrganisationService.class);
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IOrganisationService#getOrganisations(java.lang.String, int, int, java.lang.String, boolean)
	 */
	public SearchResult getOrganisations(String SID, int start ,int max, String orderby, boolean asc){
		try {
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkAdminLevel(user_level)){
	        	return this.organisationDaoImpl.getOrganisations(start,max,orderby,asc);
	        }
		} catch (Exception e){
			log.error("getOrganisations",e);
		}
		return null;
	}
	
//	public SearchResult getPendingOrganisations(String SID, int start ,int max, String orderby, boolean asc){
//		try {
//	        Long users_id = this.sessionmanagement.checkSession(SID);
//	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
//	        if (AuthLevelmanagement.checkAdminLevel(user_level)){
//	        	return this.organisationDaoImpl.getPendingOrganisations(start,max,orderby,asc);
//	        }
//		} catch (Exception e){
//			log.error("getOrganisations",e);
//		}
//		return null;
//	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IOrganisationService#getOrganisationsByUser(java.lang.String)
	 */
	public List<OrganisationUserDTO> getOrganisationsByUser(String SID){
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)){
	        	//return this.organisationDaoImpl.getOrganisationsByUserId(users_id);
	        	List<Organisation_Users> orgUsers = this.organisationDaoImpl.getOrganisationsByUserId(users_id);
	        	
	        	List<OrganisationUserDTO> orgUsersDTO = new LinkedList<OrganisationUserDTO>();
	    		
	    		for (int i=0;i<orgUsers.size();i++) {
	    			Organisation_Users orgUser = orgUsers.get(i);
	    			OrganisationUserDTO orgUserDTO = new OrganisationUserDTO();
	    			
	    			orgUserDTO.setOrganisation_users_id(orgUser.getOrganisation_users_id());
	    			
	    			orgUserDTO.setOrganisation(new OrganisationDTO());
	    			orgUserDTO.getOrganisation().setOrganisation_id(orgUser.getOrganisation().getOrganisation_id());
	    			orgUserDTO.getOrganisation().setName(orgUser.getOrganisation().getName());
	    			orgUserDTO.setIsModerator(orgUser.getIsModerator());
	    			
	    			orgUsersDTO.add(orgUserDTO);
	    		}
	    		
	    		return orgUsersDTO;
	        }
		} catch (Exception err) {
			log.error("[getOrganisationsByUser]",err);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IOrganisationService#getAllOrganisations(java.lang.String)
	 */
	public List<Organisation> getAllOrganisations(String SID){
		try {
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkAdminLevel(user_level)){
	        	return this.organisationDaoImpl.getOrganisations();
	        }
		} catch (Exception e){
			log.error("getAllOrganisations",e);
		}
		return null;
	}	
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IOrganisationService#addPendingOrganization(java.lang.String, java.lang.String, java.util.Vector)
	 */
	public Long addPendingOrganization(String SID, String orgName, Vector orgPatternsMap){
		try {
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)){
	        	
	        	//check for duplicates
	        	if (this.organisationDaoImpl.checkOrgForUniqueName(orgName)){
	        		
	        		if (orgName.length() < 3) {
	        			return new Long(-42);
	        		}
	        		
	        		Users us = this.userDaoImpl.getUserById(users_id);
	        		
	        		//There is no maximum for creation of Pending orgs any more
//	        		if (us.getMaxPendingOrganizations() != null && us.getMaxPendingOrganizations() >= us.getCreatedOrganizations()) {
//	        			return new Long(-43);
//	        		}
	        		
	        		//shift that to the pending user
//		        	Integer expireDateMonthsMax = Integer.valueOf(Configurationmanagement.getInstance().getConfKey(3, "expireDateMonthsMax").getConf_value()).intValue();
//		        	Long timeToExpire = new Long(expireDateMonthsMax) * 30 * 86400000;
//		        	Date currentDate = new Date();
//		        	Date expireDate = new Date(currentDate.getTime()+timeToExpire);
		        	
		        	//Long maxWorkDays = Long.valueOf(Configurationmanagement.getInstance().getConfKey(3, "maxWorkDays").getConf_value()).longValue();
		        	//Long maxUsers = Long.valueOf(Configurationmanagement.getInstance().getConfKey(3, "maxUsers").getConf_value()).longValue();
	
		        	Float defaultOrganizationPricing = Float.valueOf(this.configurationmanagement.getConfKey(3, "defaultOrganizationPricing").getConf_value()).floatValue();
		        	
		        	Long organisation_id = this.organisationDaoImpl.addOrganisationWithMap(orgName, users_id, 
		        								orgPatternsMap);
		        	
		        	//organisation_id
		        	this.organisationDaoImpl.addUserToOrganisation(users_id, organisation_id, users_id, "", true);
		        	
//		        	if (us.getMaxPendingOrganizations() == null) {
//		        		us.setMaxPendingOrganizations(new Long(1));
//		        		
//		        	}
		        	if (us.getCreatedOrganizations() == null) {
		        		us.setCreatedOrganizations(new Long(1));
		        	} else {
		        		us.setCreatedOrganizations(us.getCreatedOrganizations()+1);
		        	}

		        	this.userDaoImpl.updateUser(us);
		        	
		        	return organisation_id;
		        	
	        	} else {
	        		return new Long(-41);
	        	}
	        }
		} catch (Exception e){
			log.error("[addPendingOrganization]",e);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IOrganisationService#getOrganisationById(java.lang.String, long)
	 */
	public Organisation getOrganisationById(String SID, long organisation_id){
        Long users_id = this.sessionmanagement.checkSession(SID);
        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
        if (AuthLevelmanagement.checkUserLevel(user_level)){
        	return this.organisationDaoImpl.getOrganisationById(organisation_id);
        }
        return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IOrganisationService#getOrganisationDTOById(java.lang.String, long)
	 */
	public OrganisationDTO getOrganisationDTOById(String SID, long organisation_id){
        Long users_id = this.sessionmanagement.checkSession(SID);
        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
        if (AuthLevelmanagement.checkUserLevel(user_level)){
        	Organisation org = this.organisationDaoImpl.getOrganisationById(organisation_id);
        	OrganisationDTO orgDTO = new OrganisationDTO();
        	orgDTO.setOrganisation_id(org.getOrganisation_id());
        	orgDTO.setName(org.getName());
        	return orgDTO;
        }
        return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IOrganisationService#getOrganisationDetailedById(java.lang.String, long)
	 */
	public OrganisationDetailedDTO getOrganisationDetailedById(String SID, long organisation_id){
        Long users_id = this.sessionmanagement.checkSession(SID);
        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
        if (AuthLevelmanagement.checkUserLevel(user_level)){
        	Organisation org = this.organisationDaoImpl.getOrganisationById(organisation_id);
        	OrganisationDetailedDTO orgDTO = new OrganisationDetailedDTO();
        	orgDTO.setOrganisation_id(org.getOrganisation_id());
        	orgDTO.setName(org.getName());
        	
        	//Set Org-Users
        	orgDTO.setUser(new LinkedList<UserOrgDTO>());
        	
        	List<Users> userList = this.organisationUserDaoImpl.getUsersByOrganisationId(organisation_id);
        	for (int i=0;i<userList.size();i++) {
        		UserOrgDTO userOrgDTO = new UserOrgDTO();
        		Users us = userList.get(i);
        		userOrgDTO.setFirstname(us.getFirstname());
        		userOrgDTO.setLastname(us.getLastname());
        		userOrgDTO.setLogin(us.getLogin());
        		userOrgDTO.setUser_id(us.getUser_id());
        		orgDTO.getUser().add(userOrgDTO);
        	}
        	
        	XStream xStream = new XStream(new XppDriver());
			xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
			
			if (org != null && org.getOrgPatterns() != null && org.getOrgPatterns().length() != 0) {
				orgDTO.setOrgPatternsMap((Vector)xStream.fromXML(org.getOrgPatterns()));
			}
			
        	return orgDTO;
        }
        return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IOrganisationService#deleteOrganisation(java.lang.String, long)
	 */
	public Long deleteOrganisation(String SID, long organisation_id){
        Long users_id = this.sessionmanagement.checkSession(SID);
        long user_level = this.userDaoImpl.getUserLevelByID(users_id);
        return this.organisationDaoImpl.deleteOrganisation(user_level, organisation_id, users_id);
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IOrganisationService#saveOrUpdateOrganisation(java.lang.String, java.util.Map)
	 */
	public Long saveOrUpdateOrganisation(String SID, Map regObjectObj){
		try {
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)){
		        Map argObjectMap = (Map) regObjectObj;
		        long organisation_id = Long.valueOf(argObjectMap.get("organisation_id").toString()).longValue();
		        
//				Long maxUsers = null;
//				if (argObjectMap.get("maxUsers") != null) {
//					maxUsers = Long.valueOf(argObjectMap.get("maxUsers").toString()).longValue();
//				}
		        if (organisation_id==0){
		        	return this.organisationDaoImpl.addOrganisationWithMap(argObjectMap.get("orgname").toString(), 
		        			users_id, (Vector) argObjectMap.get("orgPatternMap"));	
		        } else {
		        	return this.organisationDaoImpl.updateOrganisation(organisation_id, argObjectMap.get("orgname").toString(), 
		        			users_id, (Vector) argObjectMap.get("orgPatternMap"));
		        }
	        }
		} catch (Exception err) {
			log.error("saveOrUpdateOrganisation",err);
		}
		return null;
        
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IOrganisationService#getUsersByOrganisation(java.lang.String, long, int, int, java.lang.String, boolean)
	 */
	public SearchResult getUsersByOrganisation(String SID, long organisation_id, int start, int max, String orderby, boolean asc){
		try {   
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        return this.organisationUserDaoImpl.getUsersByOrganisationId(user_level, organisation_id, start, max, orderby, asc);
		} catch (Exception err) {
			log.error("getUsersByOrganisation",err);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IOrganisationService#getModeratorsByOrganisationId(java.lang.String, long)
	 */
	public List<Users> getModeratorsByOrganisationId(String SID, long organisation_id) {
		try {   
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkAdminLevel(user_level)){
	            return this.organisationUserDaoImpl.getModeratorsByOrganisationId(organisation_id);
			}
		} catch (Exception err) {
			log.error("getModeratorsByOrganisationId",err);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IOrganisationService#addUserToOrganisation(java.lang.String, java.lang.Long, java.lang.Long, java.lang.String)
	 */
	public Long addUserToOrganisation(String SID, Long organisation_id, Long user_id, String comment) {
		try {
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)){
	            return this.organisationDaoImpl.addUserToOrganisation(user_id, organisation_id, users_id, comment, false);
			} else {
				return new Long(-276);
			}
	    } catch (Exception err) {
			log.error("getUsersByOrganisation",err);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IOrganisationService#deleteUserFromOrganisation(java.lang.String, java.lang.Long, java.lang.Long, java.lang.String)
	 */
	public Long deleteUserFromOrganisation(String SID, Long organisation_id, Long user_id, String comment) {
		try {
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        return this.organisationDaoImpl.deleteUserFromOrganisation(user_level, user_id, organisation_id);
		} catch (Exception err) {
			log.error("getUsersByOrganisation",err);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IOrganisationService#checkOrganizationStatus(java.lang.String, java.lang.Long, boolean, java.lang.Long, java.lang.Long)
	 */
    public Long checkOrganizationStatus(String SID, Long organization_id, boolean rememberMe, 
			Long language_id, Long organisation_id) {
    	try {
    		Long users_id = this.sessionmanagement.checkSession(SID);
            Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
    		if (AuthLevelmanagement.checkUserLevel(user_level)) {
    			
    			if (rememberMe) {
    				this.sessionmanagement.updateSessionWithPermanent(SID, language_id,organisation_id);
    			}
    			
    			//Organisation org = this.organisationDaoImpl.getOrganisationById(organization_id);
    			
    			//Maybe this can be re-used for Users which are pending
//    			if (org.getExpireDate() != null) {
//    				
//    				Date now = new Date();
//    				
//    				if (org.getExpireDate().getTime() < now.getTime()) {
//    					return new Long(-44);
//    				}
//    				
//    			}
//    			
//				if (org.getMaxWorkDays() != null && org.getUsedWorkDays() != null && org.getUsedWorkDays() >= org.getMaxWorkDays()){
//					return new Long(-45);
//				} else {
//					//check if today is already a new WorkDay
//					if (org.getLastWorkDay() == null || org.getUsedWorkDays() == null) {
//						org.setUsedWorkDays(new Long(1));
//						//int year, int month, int dayOfMonth, int hourOfDay,int minute
//						GregorianCalendar cal = new GregorianCalendar();
//						long dt = new GregorianCalendar(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),0,0).getTimeInMillis();
//						Date todayMidnight = new Date(dt);
//						org.setLastWorkDay(todayMidnight);
//						this.organisationDaoImpl.updateOrganisation(org);
//					} else {
//						Date now = new Date();
//						Long timeDifferenceInMilliSeconds = now.getTime() - org.getLastWorkDay().getTime();
//						//log.debug("timeDifferenceInMilliSeconds: "+timeDifferenceInMilliSeconds);
//						//log.debug("must########################: "+86400000);
//						if (timeDifferenceInMilliSeconds >= 86400000) {
//							//new Workdays
//							org.setUsedWorkDays(org.getUsedWorkDays()+1);
//							//int year, int month, int dayOfMonth, int hourOfDay,int minute
//							GregorianCalendar cal = new GregorianCalendar();
//							long dt = new GregorianCalendar(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),0,0).getTimeInMillis();
//							Date todayMidnight = new Date(dt);
//    						org.setLastWorkDay(todayMidnight);
//    						this.organisationDaoImpl.updateOrganisation(org);
//						}
//					}
//				}
//    				
//    				
    		
    		
    			//FIXME: Set Organization Id in RoomClient, Necessary??
	    		log.debug("set the Organization Id to the current RoomClient "+organization_id);
//				IConnection current = Red5.getConnectionLocal();
//	        	RoomClient currentClient = Application.getClientList().get(current.getClient().getId());
//	        	log.debug("currentClient: "+currentClient);
//	        	currentClient.setOrganizationId(organization_id);
//	        	log.debug("organization_id: "+organization_id);
//	        	Application.getClientList().put(current.getClient().getId(), currentClient);
	    		
	    		
	    		
	    		
	        	
	    		return new Long(2);
    		
    		}
    	} catch (Exception err) {
    		log.error("[checkOrganizationStatus]",err);
    	}
    	return null;
    }

}
