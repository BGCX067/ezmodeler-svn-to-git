package org.i4change.app.remote;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
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
import org.i4change.app.data.mail.MailItemServiceDaoImpl;
import org.i4change.app.data.user.daos.DiscountDaoImpl;
import org.i4change.app.data.user.daos.InvoiceDaoImpl;
import org.i4change.app.data.user.daos.TransactionPaypalDaoImpl;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.data.user.daos.UserPropertyDaoImpl;
import org.i4change.app.data.user.daos.UserSidebarPropertyDaoImpl;
import org.i4change.app.data.user.Usermanagement;
import org.i4change.app.data.user.Salutationmanagement;
import org.i4change.app.dt.payment.PaymentStatus;
import org.i4change.app.dto.user.AddressDTO;
import org.i4change.app.dto.user.CountryDTO;
import org.i4change.app.dto.user.DiscountDTO;
import org.i4change.app.dto.user.OrgUserConnectionDTO;
import org.i4change.app.dto.user.UserAuthDTO;
import org.i4change.app.dto.user.UserLicenseDTO;
import org.i4change.app.dto.user.UserProfileDTO;
import org.i4change.app.dto.user.UserSidebarPropertyDTO;
import org.i4change.app.dto.user.UserOrgDTO;
import org.i4change.app.hibernate.beans.basic.Configuration;
import org.i4change.app.hibernate.beans.domain.Organisation;
import org.i4change.app.hibernate.beans.domain.Organisation_Users;
import org.i4change.app.hibernate.beans.user.Discount;
import org.i4change.app.hibernate.beans.user.Invoice;
import org.i4change.app.hibernate.beans.user.Salutations;
import org.i4change.app.hibernate.beans.user.TransactionPaypal;
import org.i4change.app.hibernate.beans.user.UserSidebarProperty;
import org.i4change.app.hibernate.beans.user.Users;
import org.i4change.app.utils.math.CalendarPatterns;
import org.i4change.paypal.payment.ProcessPayment;

import com.paypal.soap.api.AckCodeType;
import com.paypal.soap.api.CreateRecurringPaymentsProfileResponseType;
import com.paypal.soap.api.DoDirectPaymentResponseType;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 
 * @author swagner
 *
 */
public class UserService implements IUserservice {
	
	private static final Log log = LogFactory.getLog(UserService.class);	
	
	//Spring Bean Injection
	private IApplication application;
	private Usermanagement usermanagement;
	private UserDaoImpl userDaoImpl;
	private UserPropertyDaoImpl userPropertyDaoImpl;
	private OrganisationUserDaoImpl organisationUserDaoImpl;
	private Sessionmanagement sessionmanagement;
	private Configurationmanagement configurationmanagement;
	private MailItemServiceDaoImpl mailItemServiceDaoImpl;
	private DiscountDaoImpl discountDaoImpl;
	private Salutationmanagement salutationmanagement;
	private UserSidebarPropertyDaoImpl userSidebarPropertyDaoImpl;
	private TransactionPaypalDaoImpl transactionPaypalDaoImpl;
	private InvoiceDaoImpl invoiceDaoImpl;
	private ProcessPayment processPayment;
	
	public IApplication getApplication() {
		return application;
	}
	public void setApplication(IApplication application) {
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
	
	public UserPropertyDaoImpl getUserPropertyDaoImpl() {
		return userPropertyDaoImpl;
	}
	public void setUserPropertyDaoImpl(UserPropertyDaoImpl userPropertyDaoImpl) {
		this.userPropertyDaoImpl = userPropertyDaoImpl;
	}
	
	public OrganisationUserDaoImpl getOrganisationUserDaoImpl() {
		return organisationUserDaoImpl;
	}
	public void setOrganisationUserDaoImpl(
			OrganisationUserDaoImpl organisationUserDaoImpl) {
		this.organisationUserDaoImpl = organisationUserDaoImpl;
	}
	
	public Sessionmanagement getSessionmanagement() {
		return sessionmanagement;
	}
	public void setSessionmanagement(Sessionmanagement sessionmanagement) {
		this.sessionmanagement = sessionmanagement;
	}
	
	public Configurationmanagement getConfigurationmanagement() {
		return configurationmanagement;
	}
	public void setConfigurationmanagement(
			Configurationmanagement configurationmanagement) {
		this.configurationmanagement = configurationmanagement;
	}
	
	public MailItemServiceDaoImpl getMailItemServiceDaoImpl() {
		return mailItemServiceDaoImpl;
	}
	public void setMailItemServiceDaoImpl(
			MailItemServiceDaoImpl mailItemServiceDaoImpl) {
		this.mailItemServiceDaoImpl = mailItemServiceDaoImpl;
	}
	
	public DiscountDaoImpl getDiscountDaoImpl() {
		return discountDaoImpl;
	}
	public void setDiscountDaoImpl(DiscountDaoImpl discountDaoImpl) {
		this.discountDaoImpl = discountDaoImpl;
	}
	
	public Salutationmanagement getSalutationmanagement() {
		return salutationmanagement;
	}
	public void setSalutationmanagement(Salutationmanagement salutationmanagement) {
		this.salutationmanagement = salutationmanagement;
	}
	
	public UserSidebarPropertyDaoImpl getUserSidebarPropertyDaoImpl() {
		return userSidebarPropertyDaoImpl;
	}
	public void setUserSidebarPropertyDaoImpl(
			UserSidebarPropertyDaoImpl userSidebarPropertyDaoImpl) {
		this.userSidebarPropertyDaoImpl = userSidebarPropertyDaoImpl;
	}
	
	public TransactionPaypalDaoImpl getTransactionPaypalDaoImpl() {
		return transactionPaypalDaoImpl;
	}
	public void setTransactionPaypalDaoImpl(
			TransactionPaypalDaoImpl transactionPaypalDaoImpl) {
		this.transactionPaypalDaoImpl = transactionPaypalDaoImpl;
	}
	
	public InvoiceDaoImpl getInvoiceDaoImpl() {
		return invoiceDaoImpl;
	}
	public void setInvoiceDaoImpl(InvoiceDaoImpl invoiceDaoImpl) {
		this.invoiceDaoImpl = invoiceDaoImpl;
	}
	
	public ProcessPayment getProcessPayment() {
		return processPayment;
	}
	public void setProcessPayment(ProcessPayment processPayment) {
		this.processPayment = processPayment;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IUserservice#getUserSelfAutoLogin(java.lang.String)
	 */
	public UserAuthDTO getUserSelfAutoLogin(String SID){
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        
	        Users users = this.userDaoImpl.getUserById(users_id);
//	        users.setUserPropsAsObject(this.userPropertyDaoImpl.getUserSidebarPropertyByUser(users.getUser_id()));
//	        
//	        if (users.getXmlStringRegObjectObj() != null && users.getXmlStringRegObjectObj().length() != 0) {
//	        	XStream xStream = new XStream(new XppDriver());
//				xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
//				
//				users.setRegObjectObj((Map) xStream.fromXML(users.getXmlStringRegObjectObj()));
//	        }
//	        
//	        users.setUserlevel(this.userDaoImpl.getUserLevel(users.getLevel_id()));	
//	        
	      //Create Return DTO
			UserAuthDTO userAuth = new UserAuthDTO();
			userAuth.setUser_id(users.getUser_id());
			userAuth.setFirstname(users.getFirstname());
			userAuth.setLastname(users.getLastname());
			userAuth.setLevel_id(users.getLevel_id());
			userAuth.setLogin(users.getLogin());
			userAuth.setLanguage_id(users.getLanguage_id());
			userAuth.setUserPropsAsObject(this.userPropertyDaoImpl.getUserSidebarPropertyDTOByUser(users.getUser_id()));
			userAuth.setOrganisation_users(this.organisationUserDaoImpl.getOrganisationsDTOByUsersId(users.getUser_id()));
			userAuth.setUserlevel(this.userDaoImpl.getUserLevelDTO(users.getLevel_id()));
			
			userAuth.setMaxPendingOrganizations(users.getMaxPendingOrganizations());
			userAuth.setCreatedOrganizations(users.getCreatedOrganizations());
			
			
			String content = "A User has returned (auto-login) in:<br/> " +
					" "+users.getFirstname()+" "+users.getLastname()+" ["+users.getLogin()+"]<br/>";
	
			Configuration confEmailFromLogin = this.configurationmanagement.getConfKey(3L, "emailFromLogin");
			
			Configuration confEmailMonitor = this.configurationmanagement.getConfKey(3L, "emailMonitor");
			
			this.mailItemServiceDaoImpl.addMailItem(1L, 
						confEmailFromLogin.getConf_value(), //From
						confEmailMonitor.getConf_value(), //receipent
						"User " +users.getFirstname()+" "+users.getLastname()+" ["+users.getLogin()+"] has Logged in "+confEmailFromLogin.getConf_value(), 
						content, 
						null);
			
			return userAuth;
			
		} catch (Exception err) {
			log.error("getUserSelf ",err);
		}
        return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IUserservice#getUserPictureURI(java.lang.String)
	 */
	public String getUserPictureURI(String SID){
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        
	        String pictureUri = this.userDaoImpl.getUserPictureUriById(users_id);
	        
	        return pictureUri;
		} catch (Exception err) {
			log.error("getUserPictureURI ",err);
		}
        return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IUserservice#getUserLicense(java.lang.String)
	 */
	public UserLicenseDTO getUserLicense(String SID) {
		try {
			
			Long users_id = this.sessionmanagement.checkSession(SID);
	        
	        Users users = this.userDaoImpl.getUserById(users_id);
	        
	        UserLicenseDTO userLicenseDTO = new UserLicenseDTO();
	        
	        userLicenseDTO.setLicenseUserPayed(users.getLicenseUserPayed());
	        userLicenseDTO.setLicenseUserUsed(users.getLicenseUserUsed());
	        userLicenseDTO.setUnlimitedLicenses(users.getUnlimitedLicenses());
	        userLicenseDTO.setUser_id(users.getUser_id());
	        
	        return userLicenseDTO;
	        
		} catch (Exception err) {
			log.error("getUserProfile ",err);
		}
        return null; 
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IUserservice#getUserProfile(java.lang.String)
	 */
	public UserProfileDTO getUserProfile(String SID){
		try {
			
			Long users_id = this.sessionmanagement.checkSession(SID);
	        
	        Users users = this.userDaoImpl.getUserById(users_id);
	        
	        //Create DTO
	        UserProfileDTO userProfileDTO = this.wrapUserToUserProfileDTO(users);
	        
	        //Registration Data
	        if (users.getXmlStringRegObjectObj() != null && users.getXmlStringRegObjectObj().length() != 0) {
	        	XStream xStream = new XStream(new XppDriver());
				xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
				
				Object t = xStream.fromXML(users.getXmlStringRegObjectObj());
				
				if (Map.class.isInstance(t)) {
					userProfileDTO.setRegObjectObj((Map) t);
				} else {
					userProfileDTO.setRegObjectObj(null);
				}
	        }
	        
	        return userProfileDTO;
	        
		} catch (Exception err) {
			log.error("getUserProfile ",err);
		}
        return null;
	}
	
	private UserProfileDTO wrapUserToUserProfileDTO(Users users) {
		try {
			
			//Create DTO
	        UserProfileDTO userProfileDTO = new UserProfileDTO();
	        
	        //User Base
	        userProfileDTO.setUser_id(users.getUser_id());
			userProfileDTO.setFirstname(users.getFirstname());
			userProfileDTO.setLastname(users.getLastname());
			userProfileDTO.setLogin(users.getLogin());
			userProfileDTO.setPictureuri(users.getPictureuri());
			userProfileDTO.setAge(users.getAge());
			userProfileDTO.setTitle_id(users.getTitle_id().longValue());
			userProfileDTO.setUserPropsAsObject(this.userPropertyDaoImpl.getUserSidebarPropertyDTOByUser(users.getUser_id()));
			userProfileDTO.setOrganisation_users(this.organisationUserDaoImpl.getOrganisationsDTOByUsersId(users.getUser_id()));
			
	        //User Adress
	        userProfileDTO.setAdresses(new AddressDTO());
	        userProfileDTO.getAdresses().setAdresses_id(users.getAdresses().getAdresses_id());
	        userProfileDTO.getAdresses().setAdditionalname(users.getAdresses().getAdditionalname());
	        userProfileDTO.getAdresses().setComment(users.getAdresses().getComment());
	        userProfileDTO.getAdresses().setEmail(users.getAdresses().getEmails().iterator().next().getMail().getEmail());
	        userProfileDTO.getAdresses().setFax(users.getAdresses().getFax());
	        userProfileDTO.getAdresses().setMobile(users.getAdresses().getMobile());
	        userProfileDTO.getAdresses().setPhone(users.getAdresses().getPhone());
	        userProfileDTO.getAdresses().setStates(new CountryDTO());
	        userProfileDTO.getAdresses().getStates().setCountry_id(users.getAdresses().getStates().getCountry_id());
	        userProfileDTO.getAdresses().getStates().setName(users.getAdresses().getStates().getName());
	        userProfileDTO.getAdresses().getStates().setPaypal(users.getAdresses().getStates().getPaypal());
	        userProfileDTO.getAdresses().setStreet(users.getAdresses().getStreet());
	        userProfileDTO.getAdresses().setTown(users.getAdresses().getTown());
	        userProfileDTO.getAdresses().setZip(users.getAdresses().getZip());
	        
	        userProfileDTO.setOrganisation_users(this.organisationUserDaoImpl.getOrganisationsDTOByUsersId(users.getUser_id()));
	        userProfileDTO.setUserPropsAsObject(this.userPropertyDaoImpl.getUserSidebarPropertyDTOByUser(users.getUser_id()));
	        
	        //License Data
			userProfileDTO.setMaxPendingOrganizations(users.getMaxPendingOrganizations());
			userProfileDTO.setCreatedOrganizations(users.getCreatedOrganizations());
			
			userProfileDTO.setIsPending(users.getIsPending());
			userProfileDTO.setExpireDate(users.getExpireDate());
			userProfileDTO.setMaxPendingOrganizations(users.getMaxPendingOrganizations());
			userProfileDTO.setMaxWorkDays(users.getMaxWorkDays());
			userProfileDTO.setLastWorkDay(users.getLastWorkDay());
			
	        //Cost and Discount
	        userProfileDTO.setLicenseUserPayed(users.getLicenseUserPayed());
	        userProfileDTO.setLicenseUserUsed(users.getLicenseUserUsed());
	        userProfileDTO.setPricePerUser(users.getPricePerUser());
	        userProfileDTO.setUseDefaultDiscounts(users.getUseDefaultDiscounts());
	        userProfileDTO.setUnlimitedLicenses(users.getUnlimitedLicenses());
	        
	        List<Discount> discounts = this.discountDaoImpl.getDiscountsByUser(users.getUser_id());
	        
	        userProfileDTO.setDiscounts(new LinkedList<DiscountDTO>());
	        for (int i=0;i<discounts.size();i++) {
	        	Discount disc = discounts.get(i);
	        	DiscountDTO discDTO = new DiscountDTO();
	        	discDTO.setDiscount(disc.getDiscount());
	        	discDTO.setDiscountId(disc.getDiscountId());
	        	discDTO.setNumberOfUsers(disc.getNumberOfUsers());
	        	userProfileDTO.getDiscounts().add(discDTO);
	        }
	        
	        //Company Adress
	        if (users.getCompanyAddress() != null) {
		        userProfileDTO.setCompanyAddress(new AddressDTO());
		        userProfileDTO.getCompanyAddress().setAdresses_id(users.getCompanyAddress().getAdresses_id());
		        userProfileDTO.getCompanyAddress().setAdditionalname(users.getCompanyAddress().getAdditionalname());
		        userProfileDTO.getCompanyAddress().setComment(users.getCompanyAddress().getComment());
		        userProfileDTO.getCompanyAddress().setEmail(users.getCompanyAddress().getEmails().iterator().next().getMail().getEmail());
		        userProfileDTO.getCompanyAddress().setFax(users.getCompanyAddress().getFax());
		        userProfileDTO.getCompanyAddress().setMobile(users.getCompanyAddress().getMobile());
		        userProfileDTO.getCompanyAddress().setPhone(users.getCompanyAddress().getPhone());
		        userProfileDTO.getCompanyAddress().setStates(new CountryDTO());
		        userProfileDTO.getCompanyAddress().getStates().setCountry_id(users.getCompanyAddress().getStates().getCountry_id());
		        userProfileDTO.getCompanyAddress().getStates().setName(users.getCompanyAddress().getStates().getName());
		        userProfileDTO.getCompanyAddress().getStates().setPaypal(users.getCompanyAddress().getStates().getPaypal());
		        userProfileDTO.getCompanyAddress().setStreet(users.getCompanyAddress().getStreet());
		        userProfileDTO.getCompanyAddress().setTown(users.getCompanyAddress().getTown());
		        userProfileDTO.getCompanyAddress().setZip(users.getCompanyAddress().getZip());
	        }
	        
	        return userProfileDTO;
			
		} catch (Exception err) {
			log.error("wrapUserToUserProfileDTO ",err);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IUserservice#getUserSelf(java.lang.String)
	 */
	public Users getUserSelf(String SID){
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        
	        Users users = this.userDaoImpl.getUserById(users_id);
	        users.setUserPropsAsObject(this.userPropertyDaoImpl.getUserSidebarPropertyByUser(users.getUser_id()));
	        
	        if (users.getXmlStringRegObjectObj() != null && users.getXmlStringRegObjectObj().length() != 0) {
	        	XStream xStream = new XStream(new XppDriver());
				xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
				
				users.setRegObjectObj((Map) xStream.fromXML(users.getXmlStringRegObjectObj()));
	        }
	        
	        users.setUserlevel(this.userDaoImpl.getUserLevel(users.getLevel_id()));	
	        
	        return users;
		} catch (Exception err) {
			log.error("getUserSelf ",err);
		}
        return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IUserservice#resetUserPwd(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public Long resetUserPwd(String SID, String email, String login, String applink){
		this.sessionmanagement.checkSession(SID);
		return this.usermanagement.resetUser(email, login, applink);
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IUserservice#getUserByHash(java.lang.String, java.lang.String)
	 */
	public Object getUserByHash(String SID, String hash) {
		this.sessionmanagement.checkSession(SID);
		return this.userDaoImpl.getUserByHash(hash);
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IUserservice#resetPassByHash(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Object resetPassByHash(String SID, String hash, String pass) {
		this.sessionmanagement.checkSession(SID);
		return this.userDaoImpl.resetPassByHash(hash,pass);
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IUserservice#getUserById(java.lang.String, long)
	 */
	public UserProfileDTO getUserById(String SID, long user_id){
		try {
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        //TODO: Fix that only Org-Moderators and Admins are allowed to invoke this RPC-Call
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	Users users =  this.userDaoImpl.getUserById(user_id);
	        	
	        	UserProfileDTO userProfileDTO = this.wrapUserToUserProfileDTO(users);
	        	
	        	userProfileDTO.setLevel_id(users.getLevel_id());
	        	userProfileDTO.setStatus(users.getStatus());
	        	
	        	return userProfileDTO;
	        }
	        
		} catch (Exception err) {
			log.error("[getUserByIdAdmin]",err);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IUserservice#refreshSession(java.lang.String)
	 */
	public String refreshSession(String SID){
		try {
	        this.sessionmanagement.checkSession(SID);
	        return "ok";
		} catch (Exception err) {
			log.error("[refreshSession]",err);
		}
		return "error";
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IUserservice#getUserSalutations(java.lang.String, long)
	 */
	public List<Salutations> getUserSalutations(String SID, long language_id){
        return this.salutationmanagement.getUserSalutations(language_id);
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IUserservice#searchUser(java.lang.String, java.lang.String, java.lang.String, int, int, java.lang.String, boolean)
	 */
    public List<Users> searchUser(String SID, String searchcriteria ,String searchstring, int max, int start, String orderby, boolean asc){   	
        Long users_id = this.sessionmanagement.checkSession(SID);
        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);    	
    	return this.userDaoImpl.searchUser(user_level,searchcriteria,searchstring,max,start,orderby,asc);
    } 
    
    /* (non-Javadoc)
	 * @see org.i4change.app.remote.IUserservice#lookUpUser(java.lang.String, java.lang.String, int, int, java.lang.String, boolean)
	 */
    public SearchResult lookUpUser(String SID, String searchstring, int max, int start, String orderby, boolean asc){   	
        Long users_id = this.sessionmanagement.checkSession(SID);
        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);  
        if (AuthLevelmanagement.checkUserLevel(user_level)) {
        	SearchResult sResult = new SearchResult();
        	sResult.setObjectName(Users.class.getName());
        	sResult.setResult(this.userDaoImpl.lookUpUser(searchstring,max,start,orderby,asc));
        	sResult.setRecords(this.userDaoImpl.lookUpUserMax(searchstring, max, start, asc));
        	return sResult;
        }
        return null;
    }   
    
    /* (non-Javadoc)
	 * @see org.i4change.app.remote.IUserservice#getOrgUserList(java.lang.String, long, int, int, java.lang.String, boolean)
	 */
    public List<Users> getOrgUserList(String SID, long organisation_id, int start, int max, String orderby, boolean asc){
        Long users_id = this.sessionmanagement.checkSession(SID);
        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
        return this.organisationUserDaoImpl.getUsersByOrganisationId(organisation_id, start, max, orderby, asc);
    }
    
    /* (non-Javadoc)
	 * @see org.i4change.app.remote.IUserservice#getUserListByModForm(java.lang.String)
	 */
    public List getUserListByModForm(String SID){
        Long users_id = this.sessionmanagement.checkSession(SID);
        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
        return this.usermanagement.getUserByMod(user_level, users_id);
    }
    
    /**
     * gat a list of all organisations of an user
     * @param SID
     * @param client_user
     * @param start
     * @param max
     * @param orderby
     * @param asc
     * @deprecated
     * @return
     */
//    public List getOrganisationListByUser(String SID, long client_user, int start, int max, String orderby, boolean asc){
//        Long users_id = this.sessionmanagement.checkSession(SID);
//        long user_level = this.userDaoImpl.getUserLevelByID(users_id);
//        return this.organisationDaoImpl.getOrganisationsByUserId(user_level, client_user, start, max, orderby, asc);
//    }    
    
    /**
     * gets a list of all not assigned organisations of a user
     * @param SID
     * @param client_user
     * @param start
     * @param max
     * @param orderby
     * @param asc
     * @deprecated
     * @return
     */
//    public List getRestOrganisationListByUser(String SID, long client_user, int start, int max, String orderby, boolean asc){
//        Long users_id = this.sessionmanagement.checkSession(SID);
//        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
//        return this.organisationDaoImpl.getRestOrganisationsByUserId(user_level, client_user, start, max, orderby, asc);
//    }
    
    
    /* (non-Javadoc)
	 * @see org.i4change.app.remote.IUserservice#getUserList(java.lang.String, int, int, java.lang.String, boolean)
	 */
    public SearchResult getUserList(String SID, int start, int max, String orderby, boolean asc){
        Long users_id = this.sessionmanagement.checkSession(SID);
        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
        if (AuthLevelmanagement.checkAdminLevel(user_level)){
        	return this.userDaoImpl.getUsersList(start, max, orderby, asc);
        }
        return null;
    }
    
    
    /* (non-Javadoc)
	 * @see org.i4change.app.remote.IUserservice#getUserListByOrganization(java.lang.String, int, int, java.lang.String, boolean, long)
	 */
    public SearchResult getUserListByOrganization(String SID, int start, int max, String orderby, boolean asc, long organization_id){
        Long users_id = this.sessionmanagement.checkSession(SID);
        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
        //TODO: Fix that only Org.-Admins are allowed to process this RPC-Call
        if (AuthLevelmanagement.checkUserLevel(user_level)){
        	return this.userDaoImpl.getUsersListByOrganization(start, max, orderby, asc, organization_id);
        }
        return null;
    }
    
    
    /* (non-Javadoc)
	 * @see org.i4change.app.remote.IUserservice#getUserByOrganization(java.lang.String, java.lang.Long)
	 */
    public List<UserOrgDTO> getUserByOrganization(String SID, Long organization_id) {
    	try {
    		Long users_id = this.sessionmanagement.checkSession(SID);
            Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
    		if (AuthLevelmanagement.checkUserLevel(user_level)) {
    			List<Users> userList = this.organisationUserDaoImpl.getUsersByOrganisationId(organization_id);
    			
    			List<UserOrgDTO> userListDTO = new LinkedList<UserOrgDTO>();
    			
    			for (int i=0;i<userList.size();i++) {
    				Users us = userList.get(i);
    				UserOrgDTO usersOrgDTO = new UserOrgDTO();
    				usersOrgDTO.setFirstname(us.getFirstname());
    				usersOrgDTO.setLastname(us.getLastname());
    				usersOrgDTO.setOrgUser(new OrgUserConnectionDTO());
    				if (us.getOrgUser() != null) {
    					usersOrgDTO.getOrgUser().setIsModerator(us.getOrgUser().getIsModerator());
    				} else {
    					usersOrgDTO.getOrgUser().setIsModerator(false);
    				}
    				usersOrgDTO.setLogin(us.getLogin());
    				usersOrgDTO.setUser_id(us.getUser_id());
    				userListDTO.add(usersOrgDTO);
    			}
    			
    			return userListDTO;
    			
    		}
    	} catch (Exception err) {
    		log.error("[getUserByOrganization]",err);
    	}
    	return null;
    }
    
    /* (non-Javadoc)
	 * @see org.i4change.app.remote.IUserservice#updateUserSelfSmall(java.lang.String, java.util.Map)
	 */
    public Long updateUserSelfSmall(String SID, Map values ){
    	try {
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if(user_level!=null && user_level>=1){
	        	return this.usermanagement.saveOrUpdateUser(new Long(3),values, users_id);
	        } else {
	            return new Long(-2);
	        }
    	} catch (Exception err){
    		log.error("[updateUserSelfSmall] "+err);
    		return new Long(-1);
    	}
    }    
    
    /* (non-Javadoc)
	 * @see org.i4change.app.remote.IUserservice#updateUserProfile(java.lang.String, java.util.Map)
	 */
    public Long updateUserProfile(String SID, Map values) {
    	try {
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
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
    
    /* (non-Javadoc)
	 * @see org.i4change.app.remote.IUserservice#saveOrUpdateUser(java.lang.String, java.util.Map)
	 */
    public Long saveOrUpdateUser(String SID, Map argObjectMap){
    	try {
    		Long user_idClient = null;
    		if (argObjectMap.get("user_idClient")!=null){
    			user_idClient = Long.valueOf(argObjectMap.get("user_idClient").toString()).longValue();
    		}
    		//log.error("saveOrUpdateUser1: ");
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        //log.error("saveOrUpdateUser2: ");
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);	
	        
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
			log.debug(Long.valueOf(
							argObjectMap.get("licenseUserUsed").toString())
							.longValue());
			log.debug(pricePerUser);
			log.debug(discounts);
	       
    		if (user_idClient==null || user_idClient==0){
	        	return this.usermanagement.addUserByAdministrationPanel(user_level, 
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
		        return this.usermanagement.updateByAdministrationPanel(user_level,user_idClient, 
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
    
    /* (non-Javadoc)
	 * @see org.i4change.app.remote.IUserservice#saveOrUpdateUserOnly(java.lang.String, java.lang.Object)
	 */
    public Long saveOrUpdateUserOnly(String SID, Object regObjectObj){
    	try {
    		log.debug("saveOrUpdateUserOnly: "+regObjectObj.getClass().getName());
    		
    		Hashtable argObjectMap = (Hashtable) regObjectObj;
    		Long user_idClient = null;
    		if (argObjectMap.get("user_idClient")!=null){
    			user_idClient = Long.valueOf(argObjectMap.get("user_idClient").toString()).longValue();
    		}
    		//log.error("saveOrUpdateUser1: ");
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        //log.error("saveOrUpdateUser2: ");
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);	
	        
//	        log.error("saveOrUpdateUser1: "+argObjectMap.get("organisations"));
//	        log.error("saveOrUpdateUser2: "+argObjectMap.get("organisations").getClass());
//	        log.error("saveOrUpdateUser3: "+argObjectMap.get("user_idClient"));
	        //TODO: there is a BUG here: value send is Time as GMT but here it gets CEST which is wrong	  
	        //but maybe a OS-related-issue
	        //log.error("saveOrUpdateUser4: "+argObjectMap.get("userage"));
	        //log.error("saveOrUpdateUser5: "+argObjectMap.get("userage").getClass());
	        
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	Date age = null;
		        if (argObjectMap.get("userage") instanceof Date){
		        	age = (Date) argObjectMap.get("userage");
		        	log.error("saveOrUpdateUserOnly TimeZoneOffset: "+age.getTimezoneOffset());
		        }	

	    		if (user_idClient==null || user_idClient==0){
		        	return this.usermanagement.addUserOrgModerator(users_id, 
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
			        return this.usermanagement.updateUserByOrgModerator(users_id, user_idClient,
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
    
    
    
    /* (non-Javadoc)
	 * @see org.i4change.app.remote.IUserservice#deleteUserAdmin(java.lang.String, int)
	 */
    public Long deleteUserAdmin(String SID, int user_idClient){
    	Long users_id = this.sessionmanagement.checkSession(SID);
    	Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
    	if(AuthLevelmanagement.checkAdminLevel(user_level)){
    		if (users_id!=user_idClient){
    		return this.userDaoImpl.deleteUserID(user_idClient);
    		} else {
    			return new Long(-10);
    		}
    	} else {
    		return new Long(-11);
    	}
    } 
    
    /* (non-Javadoc)
	 * @see org.i4change.app.remote.IUserservice#deleteUserFromOrganization(java.lang.String, long, long)
	 */
    public Long deleteUserFromOrganization(String SID, long user_idClient, long organization_id){
    	Long users_id = this.sessionmanagement.checkSession(SID);
    	Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
    	if(AuthLevelmanagement.checkUserLevel(user_level)){
    		
    		//this.userDaoImpl.
    		Organisation_Users orgUser = this.organisationUserDaoImpl.checkUserInOrganisationId(organization_id, user_idClient);
    		if (orgUser!= null) {
    			orgUser.setDeleted("true");
    			orgUser.setUpdatetime(new Date());
    			this.organisationUserDaoImpl.updateUsersByOrganisationUser(orgUser);
    			
    			return new Long(-49);
    		} else {
    			return new Long(-1);
    		}
    		
    	} else {
    		return new Long(-1);
    	}
    } 
    
    /* (non-Javadoc)
	 * @see org.i4change.app.remote.IUserservice#saveOrUpdateUserSidebarPropertyByDiagram(java.lang.String, java.lang.Long, java.lang.Long, java.util.Map)
	 */
    public Long saveOrUpdateUserSidebarPropertyByDiagram(String SID, Long userSidebarPropertyId, Long diagramNo, Map propMap) {
    	try {
			Long user_id = this.sessionmanagement.checkSession(SID);
			Long user_level = this.userDaoImpl.getUserLevelByID(user_id);

			if (AuthLevelmanagement.checkUserLevel(user_level)) {
				
				if (userSidebarPropertyId == null || userSidebarPropertyId == 0) {
					return this.userSidebarPropertyDaoImpl.addUserSidebarProperty(diagramNo, user_id, propMap);
				} else {
					return this.userSidebarPropertyDaoImpl.updateUserSidebarProperty(userSidebarPropertyId, propMap);
				}
				
			}			
			
		} catch (Exception err) {
			log.error("saveOrUpdateUserSidebarPropertyByDiagram: ",err);
		}
		return null;
    }
    
    /* (non-Javadoc)
	 * @see org.i4change.app.remote.IUserservice#getUserSidebarPropertyByDiagram(java.lang.String, java.lang.Long)
	 */
    public UserSidebarPropertyDTO getUserSidebarPropertyByDiagram(String SID, Long diagramNo) {
    	try {
			Long user_id = this.sessionmanagement.checkSession(SID);
			Long user_level = this.userDaoImpl.getUserLevelByID(user_id);

			if (AuthLevelmanagement.checkUserLevel(user_level)) {
				
				UserSidebarProperty uSidebarProperty = this.userSidebarPropertyDaoImpl.getUserSidebarPropertyByDiagram(diagramNo, user_id);
				
				if (uSidebarProperty == null) {
					return null;
				} else {
					
					UserSidebarPropertyDTO uSidebarPropertyDTO = new UserSidebarPropertyDTO();
					
					uSidebarPropertyDTO.setCurrentMaxWidth(uSidebarProperty.getCurrentMaxWidth());
					uSidebarPropertyDTO.setCurrentOpenitem(uSidebarProperty.getCurrentOpenitem());
					uSidebarPropertyDTO.setCurrentZoom(uSidebarProperty.getCurrentZoom());
					uSidebarPropertyDTO.setDiagramHeight(uSidebarProperty.getDiagramHeight());
					uSidebarPropertyDTO.setDiagramNo(uSidebarPropertyDTO.getDiagramNo());
					uSidebarPropertyDTO.setDiagramWidth(uSidebarProperty.getDiagramWidth());
					uSidebarPropertyDTO.setDiagramX(uSidebarProperty.getDiagramX());
					uSidebarPropertyDTO.setDiagramY(uSidebarProperty.getDiagramY());
					uSidebarPropertyDTO.setIsOpen(uSidebarProperty.getIsOpen());
					uSidebarPropertyDTO.setUserSidebarPropertyId(uSidebarProperty.getUserSidebarPropertyId());
					uSidebarPropertyDTO.setSaveItemStatus(uSidebarProperty.getSaveItemStatus());
					uSidebarPropertyDTO.setShowPreNextDiagramsOnFlows(uSidebarProperty.getShowPreNextDiagramsOnFlows());
					
					return uSidebarPropertyDTO;
					
				}
				
			}			
			
		} catch (Exception err) {
			log.error("getUserSidebarPropertyByDiagram: ",err);
		}
		return null;
    }
    
    /* (non-Javadoc)
	 * @see org.i4change.app.remote.IUserservice#checkUserStatus(java.lang.String)
	 */
    public Long checkUserStatus(String SID) {
    	try {
    		Long users_id = this.sessionmanagement.checkSession(SID);
            Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
    		if (AuthLevelmanagement.checkUserLevel(user_level)) {
    			
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
    
    /* (non-Javadoc)
	 * @see org.i4change.app.remote.IUserservice#activateUserByMod(java.lang.String, java.lang.Long)
	 */
    public Long activateUserByMod(String SID, Long user_id) {
    	try {
    		
    		Long users_id = this.sessionmanagement.checkSession(SID);
            Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
    		if (AuthLevelmanagement.checkUserLevel(user_level)) {
    			
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
		        	
					this.userDaoImpl.updateUser(user);
				
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
			        	
						this.userDaoImpl.updateUser(user);
						
					} else {
						//If this user is still valid, add the 12 months to his account
						
						Integer expireDateMonthsMax = 12;
			        	Long timeToExpire = new Long(expireDateMonthsMax) * 31 * 86400000;
			        	Date expireDate = new Date(user.getExpireDate().getTime()+timeToExpire);
						
			        	user.setExpireDate(expireDate);
			        	
						this.userDaoImpl.updateUser(user);
						
					}
					
				}
    			
    			return new Long(1);
    		}
    		
    	} catch (Exception err) {
    		log.error("[activateUserByMod]",err);
    	}
    	return new Long(-1);
    }
    
    /* (non-Javadoc)
	 * @see org.i4change.app.remote.IUserservice#addTransaction(java.lang.String, java.lang.String, java.lang.Long)
	 */
    public Long addTransaction(String SID, String amount, Long numberOfLicenses) {
    	try {
    		Long users_id = this.sessionmanagement.checkSession(SID);
            Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
    		if (AuthLevelmanagement.checkUserLevel(user_level)) {
    			return this.transactionPaypalDaoImpl.addTransactionByUser(users_id, amount, numberOfLicenses);
    		}
    	} catch (Exception err) {
    		log.error("[addTransaction]",err);
    	}
    	return new Long(-1);
    }
    
    /* (non-Javadoc)
	 * @see org.i4change.app.remote.IUserservice#getTransactions(java.lang.String, int, int, java.lang.String, boolean)
	 */
    public SearchResult getTransactions(String SID, int start ,int max, String orderby, boolean asc){
         try {
        	Long users_id = this.sessionmanagement.checkSession(SID);
            Long user_level = this.userDaoImpl.getUserLevelByID(users_id); 
     		if (AuthLevelmanagement.checkUserLevel(user_level)) {
     			return this.invoiceDaoImpl.getTransactionsByUser(start, max, orderby, asc, users_id);
     		}
     	} catch (Exception err) {
     		log.error("[addTransaction]",err);
     	}
     	return null;
    }
    
    /* (non-Javadoc)
	 * @see org.i4change.app.remote.IUserservice#getTransactionById(java.lang.String, java.lang.Long)
	 */
    public TransactionPaypal getTransactionById(String SID, Long transactionId){
        try {
        	Long users_id = this.sessionmanagement.checkSession(SID);
            Long user_level = this.userDaoImpl.getUserLevelByID(users_id); 
    		if (AuthLevelmanagement.checkUserLevel(user_level)) {
    			return this.transactionPaypalDaoImpl.getTransactionTransactionById(transactionId);
    		}
    	} catch (Exception err) {
    		log.error("[getTransactionById]",err);
    	}
    	return null;
   }
	
   /* (non-Javadoc)
 * @see org.i4change.app.remote.IUserservice#payLicense(java.lang.String, java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
 */
public PaymentStatus payLicense(String SID, Long i4change_transactionId, String firstName, String lastName, String address1, 
		   String address2, String city, String state, String zip, String countryCode, 
		   String creditCardType, String creditCardNumber, String expDateMonth, 
		   String expDateYear, String cvv2Number, String remoteAddr, 
		   String firstName2, String lastName2) {
	   try {
		   
		   Long users_id = this.sessionmanagement.checkSession(SID);
           Long user_level = this.userDaoImpl.getUserLevelByID(users_id); 
   		   if (AuthLevelmanagement.checkUserLevel(user_level)) {
			
   			   log.debug("#### i4change_transactionId: "+i4change_transactionId);
			
   			   TransactionPaypal transaction = this.transactionPaypalDaoImpl.getTransactionTransactionById(i4change_transactionId);
			
//   			   DoDirectPaymentResponseType ppresponse = ProcessPayment.getInstance().
//   			   			processSubscriptionByParams(transaction.getAmountStartTransaction(), 
//								firstName, lastName, address1, address2, city, 
//								state, zip, countryCode, creditCardType, 
//								creditCardNumber, expDateMonth, expDateYear, 
//								cvv2Number, remoteAddr);
//		
   			   
   			CreateRecurringPaymentsProfileResponseType ppresponse = this.processPayment.
					  			processSubscriptionByParams(transaction.getAmountStartTransaction(), 
								firstName, lastName, address1, address2, city, 
								state, zip, countryCode, creditCardType, 
								creditCardNumber, expDateMonth, expDateYear, 
								cvv2Number, remoteAddr);
   			
   			
   			   PaymentStatus paymentStatus = new PaymentStatus();

   			   if (!ppresponse.getAck().equals(AckCodeType.Success) && 
					!ppresponse.getAck().equals(AckCodeType.SuccessWithWarning)) {
					//FIXME: ERROR
					log.debug("ERROROEROEROEROR"+ppresponse);
					
					paymentStatus.setStatus(false);
					paymentStatus.setPaypalResponse(ppresponse);
					
					return paymentStatus;
					
				} else {
					//Success in Payment
					//String transactionId = ppresponse.getTransactionID();
					//String amount = ppresponse.getAmount().get_value();
					Date transferDate = ppresponse.getTimestamp().getTime();
					log.debug("Success: ");
					log.debug("Success transferDate1: "+transferDate);
					DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
					log.debug("Success transferDate2: "+df.format(transferDate));
					
					//Add Transaction to List
					//TransactionDaoImpl.getInstance().addTransaction(transactionId, amount, transferDate, "NEW", userId);
					
					Invoice invoice = new Invoice();
					invoice.setTransactionPaypal(transaction);
					invoice.setInserted(new Date());
					Long invoiceId = this.invoiceDaoImpl.addInvoice(invoice);
					
					//Add Transaction to List of payed / in checking queue
					this.transactionPaypalDaoImpl.addAndUpdateTransaction(
							i4change_transactionId, null, 
							"NEW", null, transferDate, firstName, lastName, 
							address1, address2, city, state, 
							zip, countryCode, firstName2, lastName2, invoiceId);
					
					paymentStatus.setPaypalResponse(ppresponse);
					paymentStatus.setStatus(true);
					
					return paymentStatus;
				}
			
   		   }
	   } catch (Exception err) {
		   log.error("[payLicense]",err);
	   }
	   return null;
   }
   
   
   
}
