package org.i4change.app.remote;

import java.util.List;
import java.util.Map;

import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.dt.payment.PaymentStatus;
import org.i4change.app.dto.user.UserAuthDTO;
import org.i4change.app.dto.user.UserLicenseDTO;
import org.i4change.app.dto.user.UserOrgDTO;
import org.i4change.app.dto.user.UserProfileDTO;
import org.i4change.app.dto.user.UserSidebarPropertyDTO;
import org.i4change.app.hibernate.beans.user.Salutations;
import org.i4change.app.hibernate.beans.user.TransactionPaypal;
import org.i4change.app.hibernate.beans.user.Users;

public interface IUserservice {

	public abstract UserAuthDTO getUserSelfAutoLogin(String SID);

	public abstract String getUserPictureURI(String SID);

	public abstract UserLicenseDTO getUserLicense(String SID);

	public abstract UserProfileDTO getUserProfile(String SID);

	/**
	 * get your own user-object
	 * @param SID
	 * @param user_id
	 * @deprecated => use DTOs
	 * @return
	 */
	public abstract Users getUserSelf(String SID);

	public abstract Long resetUserPwd(String SID, String email, String login,
			String applink);

	public abstract Object getUserByHash(String SID, String hash);

	public abstract Object resetPassByHash(String SID, String hash, String pass);

	/**
	 * get user by id, admin + moderator only
	 * @param SID
	 * @param user_id
	 * @return
	 */
	public abstract UserProfileDTO getUserById(String SID, long user_id);

	/**
	 * refreshes the current SID
	 * @param SID
	 * @return
	 */
	public abstract String refreshSession(String SID);

	/**
	 * get all availible Salutations
	 * @param SID
	 * @return
	 */
	public abstract List<Salutations> getUserSalutations(String SID,
			long language_id);

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
	public abstract List<Users> searchUser(String SID, String searchcriteria,
			String searchstring, int max, int start, String orderby, boolean asc);

	public abstract SearchResult lookUpUser(String SID, String searchstring,
			int max, int start, String orderby, boolean asc);

	/**
	 * get a list of all users of an organisation
	 * @param SID
	 * @param organisation_id
	 * @param start
	 * @param max
	 * @param orderby
	 * @param asc
	 * @deprecated - no reference found in Client
	 * @return
	 */
	public abstract List<Users> getOrgUserList(String SID,
			long organisation_id, int start, int max, String orderby,
			boolean asc);

	/**
	 * @deprecated
	 * @param SID
	 * @return
	 */
	public abstract List getUserListByModForm(String SID);

	/**
	 * gets a hole user-list(admin-role only)
	 * @param SID
	 * @param start
	 * @param max
	 * @param orderby
	 * @return
	 */
	public abstract SearchResult getUserList(String SID, int start, int max,
			String orderby, boolean asc);

	public abstract SearchResult getUserListByOrganization(String SID,
			int start, int max, String orderby, boolean asc,
			long organization_id);

	/**
	 * returns a List of Users of this Organization
	 * @param SID
	 * @param organization_id
	 * @return
	 */
	public abstract List<UserOrgDTO> getUserByOrganization(String SID,
			Long organization_id);

	/**
	 * updates the user profile, every user can update his own profile
	 * @param SID
	 * @param argObject
	 * @return user_id or NULL or negativ value (error_id)
	 * @deprecated
	 */
	public abstract Long updateUserSelfSmall(String SID, Map values);

	public abstract Long updateUserProfile(String SID, Map values);

	/**
	 * 
	 * This function is triggered in the Administration Interface
	 * 
	 * @param SID
	 * @param regObjectObj
	 * @return
	 */
	public abstract Long saveOrUpdateUser(String SID, Map argObjectMap);

	/**
	 * 
	 * This function is triggered from the Moderation-User-Administration
	 * 
	 * @param SID
	 * @param regObjectObj
	 * @return
	 */
	public abstract Long saveOrUpdateUserOnly(String SID, Object regObjectObj);

	/**
	 * deletes a user
	 * @param SID
	 * @param user_idClient
	 * @return
	 */
	public abstract Long deleteUserAdmin(String SID, int user_idClient);

	public abstract Long deleteUserFromOrganization(String SID,
			long user_idClient, long organization_id);

	/**
	 * updates the individual Diagram Sidebar settings
	 * @param SID
	 * @param userSidebarPropertyId
	 * @param diagramNo
	 * @param propMap
	 * @return
	 */
	public abstract Long saveOrUpdateUserSidebarPropertyByDiagram(String SID,
			Long userSidebarPropertyId, Long diagramNo, Map propMap);

	/**
	 * get the individual Diagram Sidebar settings
	 * @param SID
	 * @param diagramNo
	 * @return
	 */
	public abstract UserSidebarPropertyDTO getUserSidebarPropertyByDiagram(
			String SID, Long diagramNo);

	/**
	 * 
	 * @param SID
	 * @return
	 */
	public abstract Long checkUserStatus(String SID);

	public abstract Long activateUserByMod(String SID, Long user_id);

	public abstract Long addTransaction(String SID, String amount,
			Long numberOfLicenses);

	public abstract SearchResult getTransactions(String SID, int start,
			int max, String orderby, boolean asc);

	public abstract TransactionPaypal getTransactionById(String SID,
			Long transactionId);

	public abstract PaymentStatus payLicense(String SID,
			Long i4change_transactionId, String firstName, String lastName,
			String address1, String address2, String city, String state,
			String zip, String countryCode, String creditCardType,
			String creditCardNumber, String expDateMonth, String expDateYear,
			String cvv2Number, String remoteAddr, String firstName2,
			String lastName2);

}