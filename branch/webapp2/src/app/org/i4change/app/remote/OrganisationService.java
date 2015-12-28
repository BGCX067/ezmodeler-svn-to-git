package org.i4change.app.remote;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import org.i4change.app.hibernate.beans.domain.Organisation;
import org.i4change.app.hibernate.beans.domain.Organisation_Users;
import org.i4change.app.hibernate.beans.user.Users;
import org.i4change.app.session.beans.RoomClient;
import org.i4change.app.utils.math.CalendarPatterns;
import org.red5.server.api.IConnection;
import org.red5.server.api.Red5;

/**
 * 
 * @author swagner
 *
 */
public class OrganisationService {
	
	private Application application;
	private OrganisationDaoImpl organisationDaoImpl;
	
	
	public Application getApplication() {
		return application;
	}
	public void setApplication(Application application) {
		this.application = application;
	}
	public OrganisationDaoImpl getOrganisationDaoImpl() {
		return organisationDaoImpl;
	}
	public void setOrganisationDaoImpl(OrganisationDaoImpl organisationDaoImpl) {
		this.organisationDaoImpl = organisationDaoImpl;
	}


	private static final Log log = LogFactory.getLog(OrganisationService.class);
	
	/**
	 * Loads a List of all available Organizations (Admin-role only)
	 * @param SID
	 * @return
	 */
	public SearchResult getOrganisations(String SID, int start ,int max, String orderby, boolean asc){
		try {
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkAdminLevel(user_level)){
	        	return OrganisationDaoImpl.getInstance().getOrganisations(start,max,orderby,asc);
	        }
		} catch (Exception e){
			log.error("getOrganisations",e);
		}
		return null;
	}
	
	public SearchResult getPendingOrganisations(String SID, int start ,int max, String orderby, boolean asc){
		try {
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkAdminLevel(user_level)){
	        	return this.organisationDaoImpl.getPendingOrganisations(start,max,orderby,asc);
	        }
		} catch (Exception e){
			log.error("getOrganisations",e);
		}
		return null;
	}
	
	public List<Organisation_Users> getOrganisationsByUser(String SID){
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)){
	        	return this.organisationDaoImpl.getOrganisationsByUserId(users_id);
	        }
		} catch (Exception err) {
			log.error("[getOrganisationsByUser]",err);
		}
		return null;
	}
	
	public List<Organisation> getAllOrganisations(String SID){
		try {
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkAdminLevel(user_level)){
	        	return this.organisationDaoImpl.getOrganisations();
	        }
		} catch (Exception e){
			log.error("getAllOrganisations",e);
		}
		return null;
	}	
	
	public Long addPendingOrganization(String SID, String orgName, Map orgPatternsMap){
		try {
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)){
	        	
	        	//check for duplicates
	        	if (this.organisationDaoImpl.checkOrgForUniqueName(orgName)){
	        		
	        		if (orgName.length() < 3) {
	        			return new Long(-42);
	        		}
	        		
	        		Users us = UserDaoImpl.getInstance().getUserById(users_id);
	        		
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
	
		        	Float defaultOrganizationPricing = Float.valueOf(Configurationmanagement.getInstance().getConfKey(3, "defaultOrganizationPricing").getConf_value()).floatValue();
		        	
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

		        	UserDaoImpl.getInstance().updateUser(us);
		        	
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
	
	/**
	 * get an organisation by a given id
	 * @param SID
	 * @param organisation_id
	 * @return
	 */
	public Organisation getOrganisationById(String SID, long organisation_id){
        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)){
        	return this.organisationDaoImpl.getOrganisationById(organisation_id);
        }
        return null;
	}
	
	/**
	 * deletes a organisation by a given id
	 * @param SID
	 * @param organisation_id
	 * @return
	 */
	public Long deleteOrganisation(String SID, long organisation_id){
        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
        long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
        return this.organisationDaoImpl.deleteOrganisation(user_level, organisation_id, users_id);
	}
	
	/**
	 * adds or updates an Organisation
	 * @param SID
	 * @param organisation_id
	 * @param orgname
	 * @return
	 */
	public Long saveOrUpdateOrganisation(String SID, Object regObjectObj){
		try {
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)){
		        LinkedHashMap argObjectMap = (LinkedHashMap) regObjectObj;
		        long organisation_id = Long.valueOf(argObjectMap.get("organisation_id").toString()).longValue();
		        
//				Long maxUsers = null;
//				if (argObjectMap.get("maxUsers") != null) {
//					maxUsers = Long.valueOf(argObjectMap.get("maxUsers").toString()).longValue();
//				}
		        if (organisation_id==0){
		        	return this.organisationDaoImpl.addOrganisationWithMap(argObjectMap.get("orgname").toString(), 
		        			users_id, (Map) argObjectMap.get("orgPatternMap"));	
		        } else {
		        	return this.organisationDaoImpl.updateOrganisation(organisation_id, argObjectMap.get("orgname").toString(), 
		        			users_id, (Map) argObjectMap.get("orgPatternMap"));
		        }
	        }
		} catch (Exception err) {
			log.error("saveOrUpdateOrganisation",err);
		}
		return null;
        
	}
	
	/**
	 * gets all users of a given organisation
	 * @param SID
	 * @param organisation_id
	 * @param start
	 * @param max
	 * @param orderby
	 * @param asc
	 * @return
	 */
	public SearchResult getUsersByOrganisation(String SID, long organisation_id, int start, int max, String orderby, boolean asc){
		try {   
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        return OrganisationUserDaoImpl.getInstance().getUsersByOrganisationId(user_level, organisation_id, start, max, orderby, asc);
		} catch (Exception err) {
			log.error("getUsersByOrganisation",err);
		}
		return null;
	}
	
	public List<Users> getModeratorsByOrganisationId(String SID, long organisation_id) {
		try {   
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkAdminLevel(user_level)){
	            return OrganisationUserDaoImpl.getInstance().getModeratorsByOrganisationId(organisation_id);
			}
		} catch (Exception err) {
			log.error("getModeratorsByOrganisationId",err);
		}
		return null;
	}
	
	public Long addUserToOrganisation(String SID, Long organisation_id, Long user_id, String comment) {
		try {
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)){
	            return this.organisationDaoImpl.addUserToOrganisation(user_id, organisation_id, users_id, comment, false);
			} else {
				return new Long(-276);
			}
	    } catch (Exception err) {
			log.error("getUsersByOrganisation",err);
		}
		return null;
	}
	
	public Long deleteUserFromOrganisation(String SID, Long organisation_id, Long user_id, String comment) {
		try {
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        return this.organisationDaoImpl.deleteUserFromOrganisation(user_level, user_id, organisation_id);
		} catch (Exception err) {
			log.error("getUsersByOrganisation",err);
		}
		return null;
	}

	/**
	 * @param SID
	 * @param organization_id
	 * @return
	 */
    public Long checkOrganizationStatus(String SID, Long organization_id) {
    	try {
    		Long users_id = Sessionmanagement.getInstance().checkSession(SID);
            Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
    		if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
    			
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
    		
    		
	    		log.debug("set the Organization Id to the current RoomClient "+organization_id);
				IConnection current = Red5.getConnectionLocal();
	        	RoomClient currentClient = Application.getClientList().get(current.getClient().getId());
	        	log.debug("currentClient: "+currentClient);
	        	currentClient.setOrganizationId(organization_id);
	        	log.debug("organization_id: "+organization_id);
	        	Application.getClientList().put(current.getClient().getId(), currentClient);
	        	
	    		return new Long(2);
    		
    		}
    	} catch (Exception err) {
    		log.error("[checkOrganizationStatus]",err);
    	}
    	return null;
    }

}
