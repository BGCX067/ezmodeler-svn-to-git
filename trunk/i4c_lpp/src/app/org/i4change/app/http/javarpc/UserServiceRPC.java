package org.i4change.app.http.javarpc;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.dt.payment.PaymentStatus;
import org.i4change.app.dto.user.UserAuthDTO;
import org.i4change.app.dto.user.UserLicenseDTO;
import org.i4change.app.dto.user.UserProfileDTO;
import org.i4change.app.dto.user.UserSidebarPropertyDTO;
import org.i4change.app.dto.user.UserOrgDTO;
import org.i4change.app.hibernate.beans.user.Salutations;
import org.i4change.app.hibernate.beans.user.TransactionPaypal;
import org.i4change.app.hibernate.beans.user.UserSidebarProperty;
import org.i4change.app.hibernate.beans.user.Users;
import org.i4change.app.remote.IUserservice;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author sebastianwagner
 *
 */
public class UserServiceRPC extends BaseAdapterRPC {
	
	private static final Log log = LogFactory.getLog(UserServiceRPC.class);
	
	public UserAuthDTO getUserSelfAutoLogin(String SID) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IUserservice userservice = (IUserservice) context.getBean("userservice.service");
		
			return userservice.getUserSelfAutoLogin(SID);
			
		} catch (Exception err) {
			log.error ("[getUserSelfAutoLogin]",err);
		}
		return null;
	}
	
	public UserProfileDTO getUserProfile(String SID){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IUserservice userservice = (IUserservice) context.getBean("userservice.service");
		
			return userservice.getUserProfile(SID);
			
		} catch (Exception err) {
			log.error ("[getUserSelf]",err);
		}
		return null;
	}
	
	public UserLicenseDTO getUserLicense(String SID) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IUserservice userservice = (IUserservice) context.getBean("userservice.service");
		
			return userservice.getUserLicense(SID);
			
		} catch (Exception err) {
			log.error ("[getUserLicense]",err);
		}
		return null;
	}
	
//	public Users getUserSelf(String SID){
//		try {
//			
//			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
//			
//			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
//			UserService userservice = (UserService) context.getBean("userservice.service");
//		
//			return userservice.getUserSelf(SID);
//			
//		} catch (Exception err) {
//			log.error ("[getUserSelf]",err);
//		}
//		return null;
//	}
	
	public String getUserPictureURI(String SID){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IUserservice userservice = (IUserservice) context.getBean("userservice.service");
		
			return userservice.getUserPictureURI(SID);
			
		} catch (Exception err) {
			log.error ("[getUserPictureURI]",err);
		}
		return null;
	}
	
	public Long resetUserPwd(String SID, String email, String login, String applink){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IUserservice userservice = (IUserservice) context.getBean("userservice.service");
		
			return userservice.resetUserPwd(SID, email, login, applink);
			
		} catch (Exception err) {
			log.error ("[resetUserPwd]",err);
		}
		return null;
	}
	
	public Object getUserByHash(String SID, String hash){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IUserservice userservice = (IUserservice) context.getBean("userservice.service");
		
			return userservice.getUserByHash(SID, hash);
			
		} catch (Exception err) {
			log.error ("[getUserByHash]",err);
		}
		return null;
	}
	
	public Object resetPassByHash(String SID, String hash, String pass){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IUserservice userservice = (IUserservice) context.getBean("userservice.service");
		
			return userservice.resetPassByHash(SID, hash, pass);
			
		} catch (Exception err) {
			log.error ("[resetPassByHash]",err);
		}
		return null;
	}
	
	public UserProfileDTO getUserById(String SID, long user_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IUserservice userservice = (IUserservice) context.getBean("userservice.service");
		
			return userservice.getUserById(SID, user_id);
			
		} catch (Exception err) {
			log.error ("[getUserById]",err);
		}
		return null;
	}
	
	public String refreshSession(String SID){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IUserservice userservice = (IUserservice) context.getBean("userservice.service");
		
			return userservice.refreshSession(SID);
			
		} catch (Exception err) {
			log.error ("[refreshSession]",err);
		}
		return null;
	}
	
	public List<Salutations> getUserSalutations(String SID, int language_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IUserservice userservice = (IUserservice) context.getBean("userservice.service");
		
			return userservice.getUserSalutations(SID, language_id);
			
		} catch (Exception err) {
			log.error ("[getUserSalutations]",err);
		}
		return null;
	}
	
	public List<Users> searchUser(String SID, String searchcriteria ,String searchstring, int max, int start, String orderby, 
			boolean asc){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IUserservice userservice = (IUserservice) context.getBean("userservice.service");
		
			return userservice.searchUser(SID, searchcriteria, searchstring, max, start, orderby, asc);
			
		} catch (Exception err) {
			log.error ("[searchUser]",err);
		}
		return null;
	}
	
	public SearchResult lookUpUser(String SID, String searchstring, int max, int start, String orderby, 
			boolean asc){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IUserservice userservice = (IUserservice) context.getBean("userservice.service");
		
			return userservice.lookUpUser(SID, searchstring, max, start, orderby, asc);
			
		} catch (Exception err) {
			log.error ("[lookUpUser]",err);
		}
		return null;
	}
	
//	public List<Users> getOrgUserList(String SID, long organisation_id, int start, int max, String orderby, 
//			boolean asc){
//		try {
//			
//			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
//			
//			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
//			UserService userservice = (UserService) context.getBean("userservice.service");
//		
//			return userservice.getOrgUserList(SID, organisation_id, start, max, orderby, asc);
//			
//		} catch (Exception err) {
//			log.error ("[getOrgUserList]",err);
//		}
//		return null;
//	}
	
//	public List getUserListByModForm(String SID){
//		try {
//			
//			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
//			
//			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
//			UserService userservice = (UserService) context.getBean("userservice.service");
//		
//			return userservice.getUserListByModForm(SID);
//			
//		} catch (Exception err) {
//			log.error ("[getUserListByModForm]",err);
//		}
//		return null;
//	}
	 
//	public List getOrganisationListByUser(String SID, long client_user, int start, int max, String orderby, 
//			boolean asc){
//		try {
//			
//			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
//			
//			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
//			UserService userservice = (UserService) context.getBean("userservice.service");
//		
//			return userservice.getOrganisationListByUser(SID, client_user, start, max, orderby, asc);
//			
//		} catch (Exception err) {
//			log.error ("[getOrganisationListByUser]",err);
//		}
//		return null;
//	}
	
//	public List getRestOrganisationListByUser(String SID, long client_user, int start, int max, String orderby, 
//			boolean asc){
//		try {
//			
//			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
//			
//			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
//			UserService userservice = (UserService) context.getBean("userservice.service");
//		
//			return userservice.getRestOrganisationListByUser(SID, client_user, start, max, orderby, asc);
//			
//		} catch (Exception err) {
//			log.error ("[getRestOrganisationListByUser]",err);
//		}
//		return null;
//	}
	
	public SearchResult getUserList(String SID, int start, int max, String orderby, boolean asc, 
			HttpServletRequest request){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IUserservice userservice = (IUserservice) context.getBean("userservice.service");
		
			return userservice.getUserList(SID, start, max, orderby, asc);
			
		} catch (Exception err) {
			log.error ("[getUserList]",err);
		}
		return null;
	}
	
	public SearchResult getUserListByOrganization(String SID, int start, int max, String orderby, boolean asc, 
			long organization_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IUserservice userservice = (IUserservice) context.getBean("userservice.service");
		
			return userservice.getUserListByOrganization(SID, start, max, orderby, asc, organization_id);
			
		} catch (Exception err) {
			log.error ("[getUserListByOrganization]",err);
		}
		return null;
	}
	
	public List<UserOrgDTO> getUserByOrganization(String SID, Long organization_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IUserservice userservice = (IUserservice) context.getBean("userservice.service");
		
			return userservice.getUserByOrganization(SID, organization_id);
			
		} catch (Exception err) {
			log.error ("[getUserByOrganization]",err);
		}
		return null;
	}
	
//	public Long updateUserSelfSmall(String SID, Map values){
//		try {
//			
//			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
//			
//			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
//			UserService userservice = (UserService) context.getBean("userservice.service");
//		
//			return userservice.updateUserSelfSmall(SID, values);
//			
//		} catch (Exception err) {
//			log.error ("[updateUserSelfSmall]",err);
//		}
//		return null;
//	}
	
	public Long updateUserProfile(String SID, Map values){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IUserservice userservice = (IUserservice) context.getBean("userservice.service");
		
			return userservice.updateUserProfile(SID, values);
			
		} catch (Exception err) {
			log.error ("[updateUserProfile]",err);
		}
		return null;
	}
	
	public Long saveOrUpdateUser(String SID, Map regObjectObj){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IUserservice userservice = (IUserservice) context.getBean("userservice.service");
		
			return userservice.saveOrUpdateUser(SID, regObjectObj);
			
		} catch (Exception err) {
			log.error ("[saveOrUpdateUser]",err);
		}
		return null;
	}
	
	public Long saveOrUpdateUserOnly(String SID, Object regObjectObj){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IUserservice userservice = (IUserservice) context.getBean("userservice.service");
		
			return userservice.saveOrUpdateUserOnly(SID, regObjectObj);
			
		} catch (Exception err) {
			log.error ("[saveOrUpdateUserOnly]",err);
		}
		return null;
	}
	
	public Long deleteUserAdmin(String SID, int user_idClient){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IUserservice userservice = (IUserservice) context.getBean("userservice.service");
		
			return userservice.deleteUserAdmin(SID, user_idClient);
			
		} catch (Exception err) {
			log.error ("[deleteUserAdmin]",err);
		}
		return null;
	}
	
	public Long deleteUserFromOrganization(String SID, long user_idClient, long organization_id, 
			HttpServletRequest request){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IUserservice userservice = (IUserservice) context.getBean("userservice.service");
		
			return userservice.deleteUserFromOrganization(SID, user_idClient, organization_id);
			
		} catch (Exception err) {
			log.error ("[deleteUserFromOrganization]",err);
		}
		return null;
	}
	
	public Long saveOrUpdateUserSidebarPropertyByDiagram(String SID, Long userSidebarPropertyId, 
			Long diagramNo, Map propMap){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IUserservice userservice = (IUserservice) context.getBean("userservice.service");
		
			return userservice.saveOrUpdateUserSidebarPropertyByDiagram(SID, userSidebarPropertyId, diagramNo, propMap);
			
		} catch (Exception err) {
			log.error ("[deleteUserFromOrganization]",err);
		}
		return null;
	}
	
	public UserSidebarPropertyDTO getUserSidebarPropertyByDiagram(String SID, 
			Long diagramNo){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IUserservice userservice = (IUserservice) context.getBean("userservice.service");
		
			return userservice.getUserSidebarPropertyByDiagram(SID, diagramNo);
			
		} catch (Exception err) {
			log.error ("[getUserSidebarPropertyByDiagram]",err);
		}
		return null;
	}
	
	public Long checkUserStatus(String SID){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IUserservice userservice = (IUserservice) context.getBean("userservice.service");
		
			return userservice.checkUserStatus(SID);
			
		} catch (Exception err) {
			log.error ("[checkUserStatus]",err);
		}
		return null;
	}
	
	public Long activateUserByMod(String SID, Long user_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IUserservice userservice = (IUserservice) context.getBean("userservice.service");
		
			return userservice.activateUserByMod(SID, user_id);
			
		} catch (Exception err) {
			log.error ("[activateUserByMod]",err);
		}
		return null;
	}
	
	public Long addTransaction(String SID, String amount, Long numberOfLicenses){
		try {
			
			log.debug("addTransaction amount :: "+amount);
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IUserservice userservice = (IUserservice) context.getBean("userservice.service");
		
			return userservice.addTransaction(SID, amount, numberOfLicenses);
			
		} catch (Exception err) {
			log.error ("[addTransaction]",err);
		}
		return null;
	}
	
	public SearchResult getTransactions(String SID, int start ,int max, String orderby, boolean asc, 
			HttpServletRequest request){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IUserservice userservice = (IUserservice) context.getBean("userservice.service");
		
			return userservice.getTransactions(SID, start, max, orderby, asc);
			
		} catch (Exception err) {
			log.error ("[getTransactions]",err);
		}
		return null;
	}
	
	public TransactionPaypal getTransactionById(String SID, Long transactionId) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IUserservice userservice = (IUserservice) context.getBean("userservice.service");
		
			return userservice.getTransactionById(SID, transactionId);
			
		} catch (Exception err) {
			log.error ("[getTransactionById]",err);
		}
		return null;
	}
	
	/*
	 * 
	 * (class java.lang.String, int, class java.lang.String, class java.lang.String, class java.lang.String, 
	 *  class java.lang.String, class java.lang.String, class java.lang.String, class java.lang.String, class java.lang.String, 
	 *  class java.lang.String, class java.lang.String, class java.lang.String, 
	 *  class java.lang.String, class java.lang.String, class java.lang.String)
	 * 
	 * 
	 */
	
	public PaymentStatus payLicense(String SID, Long transactionId, String firstName, String lastName, String address1, 
			   String address2, String city, String state, String zip, String countryCode, 
			   String creditCardType, String creditCardNumber, String expDateMonth, 
			   String expDateYear, String cvv2Number, String firstName2, String lastName2) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IUserservice userservice = (IUserservice) context.getBean("userservice.service");
		
			String remoteAddr = this.servletRequest.getRemoteAddr();
			
			return userservice.payLicense(SID, transactionId, firstName, lastName, address1, 
					address2, city, state, zip, countryCode, creditCardType, creditCardNumber, 
					expDateMonth, expDateYear, cvv2Number, remoteAddr, firstName2, lastName2);
			
		} catch (Exception err) {
			log.error ("[getTransactionById]",err);
		}
		return null;
	}
	
}
