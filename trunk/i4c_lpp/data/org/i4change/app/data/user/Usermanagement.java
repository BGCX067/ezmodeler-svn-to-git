package org.i4change.app.data.user;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Date;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.EmailValidator;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import org.i4change.app.hibernate.beans.lang.Fieldlanguagesvalues;
import org.i4change.app.hibernate.beans.user.*;
import org.i4change.app.hibernate.beans.adresses.Adresses;
import org.i4change.app.hibernate.beans.adresses.Adresses_Emails;
import org.i4change.app.hibernate.beans.basic.Configuration;
import org.i4change.app.hibernate.beans.domain.Organisation;
import org.i4change.app.hibernate.beans.domain.Organisation_Users;
import org.i4change.app.templates.ResetPasswordTemplate;
import org.i4change.app.payment.GenerateInvoice;
import org.i4change.app.session.beans.RoomClient;
import org.i4change.app.data.basic.AuthLevelmanagement;
import org.i4change.app.data.basic.Configurationmanagement;
import org.i4change.app.data.basic.Fieldmanagment;
import org.i4change.app.data.mail.MailItemServiceDaoImpl;
import org.i4change.app.data.user.daos.DiscountDaoImpl;
import org.i4change.app.data.user.daos.TransactionPaypalDaoImpl;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.data.user.daos.UserPropertyDaoImpl;
import org.i4change.app.utils.mappings.CastMapToObject;
import org.i4change.app.utils.math.*;
import org.i4change.app.utils.mail.MailHandler;
import org.i4change.app.utils.crypt.*;

import org.i4change.app.data.basic.*;
import org.i4change.app.data.domain.daos.OrganisationDaoImpl;
import org.i4change.app.data.domain.daos.OrganisationUserDaoImpl;
import org.i4change.app.dto.user.AddressDTO;
import org.i4change.app.dto.user.CountryDTO;
import org.i4change.app.dto.user.UserAuthDTO;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 
 * @author swagner
 *
 */
public class Usermanagement extends HibernateDaoSupport {

	private static final Log log = LogFactory.getLog(Usermanagement.class);
	
	//Spring loaded Beans
	private UserDaoImpl userDaoImpl;
	private EmailDaoImpl emailDaoImpl;
	private OrganisationUserDaoImpl organisationUserDaoImpl;
	private Addressmanagement addressmanagement;
	private UserPropertyDaoImpl userPropertyDaoImpl;
	private ManageCryptStyle manageCryptStyle;
	private MailItemServiceDaoImpl mailItemServiceDaoImpl;
	private DiscountDaoImpl discountDaoImpl;
	private TransactionPaypalDaoImpl transactionPaypalDaoImpl;
	private GenerateInvoice generateInvoice;
	private Mailmanagement mailmanagement;
	private OrganisationDaoImpl organisationDaoImpl;
	private ResetPasswordTemplate resetPasswordTemplate;
	private CountryDaoImpl countryDaoImpl;
	private Configurationmanagement configurationmanagement;
	private MailHandler mailHandler;
	private Fieldmanagment fieldmanagment;
	private Sessionmanagement sessionmanagement;
	private ValidierungsUtil validierungsUtil;
	
	public Fieldmanagment getFieldmanagment() {
		return fieldmanagment;
	}
	public void setFieldmanagment(Fieldmanagment fieldmanagment) {
		this.fieldmanagment = fieldmanagment;
	}

	public Configurationmanagement getConfigurationmanagement() {
		return configurationmanagement;
	}
	public void setConfigurationmanagement(
			Configurationmanagement configurationmanagement) {
		this.configurationmanagement = configurationmanagement;
	}
	
	public CountryDaoImpl getCountryDaoImpl() {
		return countryDaoImpl;
	}
	public void setCountryDaoImpl(CountryDaoImpl countryDaoImpl) {
		this.countryDaoImpl = countryDaoImpl;
	}
	
	public ResetPasswordTemplate getResetPasswordTemplate() {
		return resetPasswordTemplate;
	}
	public void setResetPasswordTemplate(ResetPasswordTemplate resetPasswordTemplate) {
		this.resetPasswordTemplate = resetPasswordTemplate;
	}
	
	public UserDaoImpl getUserDaoImpl() {
		return userDaoImpl;
	}
	public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}

	public EmailDaoImpl getEmailDaoImpl() {
		return emailDaoImpl;
	}
	public void setEmailDaoImpl(EmailDaoImpl emailDaoImpl) {
		this.emailDaoImpl = emailDaoImpl;
	}


	public Addressmanagement getAddressmanagement() {
		return addressmanagement;
	}
	public void setAddressmanagement(Addressmanagement addressmanagement) {
		this.addressmanagement = addressmanagement;
	}


	public OrganisationUserDaoImpl getOrganisationUserDaoImpl() {
		return organisationUserDaoImpl;
	}
	public void setOrganisationUserDaoImpl(
			OrganisationUserDaoImpl organisationUserDaoImpl) {
		this.organisationUserDaoImpl = organisationUserDaoImpl;
	}

	public UserPropertyDaoImpl getUserPropertyDaoImpl() {
		return userPropertyDaoImpl;
	}
	public void setUserPropertyDaoImpl(UserPropertyDaoImpl userPropertyDaoImpl) {
		this.userPropertyDaoImpl = userPropertyDaoImpl;
	}
	
	public ManageCryptStyle getManageCryptStyle() {
		return manageCryptStyle;
	}
	public void setManageCryptStyle(ManageCryptStyle manageCryptStyle) {
		this.manageCryptStyle = manageCryptStyle;
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

	public TransactionPaypalDaoImpl getTransactionPaypalDaoImpl() {
		return transactionPaypalDaoImpl;
	}
	public void setTransactionPaypalDaoImpl(
			TransactionPaypalDaoImpl transactionPaypalDaoImpl) {
		this.transactionPaypalDaoImpl = transactionPaypalDaoImpl;
	}
	
	public GenerateInvoice getGenerateInvoice() {
		return generateInvoice;
	}
	public void setGenerateInvoice(GenerateInvoice generateInvoice) {
		this.generateInvoice = generateInvoice;
	}
	
	public Mailmanagement getMailmanagement() {
		return mailmanagement;
	}
	public void setMailmanagement(Mailmanagement mailmanagement) {
		this.mailmanagement = mailmanagement;
	}
	
	public OrganisationDaoImpl getOrganisationDaoImpl() {
		return organisationDaoImpl;
	}
	public void setOrganisationDaoImpl(OrganisationDaoImpl organisationDaoImpl) {
		this.organisationDaoImpl = organisationDaoImpl;
	}
	
	public List getUserByMod(Long user_level, long user_id){
		return null;
	}

	public MailHandler getMailHandler() {
		return mailHandler;
	}
	public void setMailHandler(MailHandler mailHandler) {
		this.mailHandler = mailHandler;
	}
	
	public Sessionmanagement getSessionmanagement() {
		return sessionmanagement;
	}
	public void setSessionmanagement(Sessionmanagement sessionmanagement) {
		this.sessionmanagement = sessionmanagement;
	}
	
	public ValidierungsUtil getValidierungsUtil() {
		return validierungsUtil;
	}
	public void setValidierungsUtil(ValidierungsUtil validierungsUtil) {
		this.validierungsUtil = validierungsUtil;
	}
	
	/**
	 * login logic
	 * @param SID
	 * @param Username
	 * @param Userpass
	 * @return
	 */
	public Object loginUser(String SID, String username, String userpass, RoomClient currentClient, Long userlang) {
		try {
			
			Criteria crit = getSession().createCriteria(Users.class);
			crit.add(Restrictions.eq("login", username));
			crit.add(Restrictions.eq("deleted", "false"));
			//crit.add(Restrictions.eq("status", 1));
			Users user = (Users) crit.uniqueResult();
			log.info("debug loginUser: " + username);

			log.info("debug user: " + user);
			
			if (user == null) {
				currentClient.setUser_id(null);
				log.debug("new Long(-10)");
				return new Long(-10);
			} else {
				if (this.manageCryptStyle.getInstanceOfCrypt().verifyPassword(userpass, user.getPassword())) {
					log.info("chsum OK: "+ user.getUser_id());
					
					if (user.getStatus() != 1) {
						currentClient.setUser_id(null);
						log.debug("new Long(-38)");
						return new Long(-38);
					} else {
						this.sessionmanagement.updateUser(SID, user.getUser_id());
						//user.setUserlevel(UserDaoImpl.getInstance().getUserLevel(user.getLevel_id()));	
						log.debug("this.organisationUserDaoImpl: "+this.organisationUserDaoImpl);
						
						//user.setOrganisation_users(this.organisationUserDaoImpl.getOrganisationsByUsersId(user.getUser_id()));
							
						//user.setUserPropsAsObject(this.userPropertyDaoImpl.getUserSidebarPropertyByUser(user.getUser_id()));
						
						user.setLanguage_id(userlang);
						updateLastLogin(user);
						currentClient.setUser_id(user.getUser_id());
						
						//Create Return DTO
						UserAuthDTO userAuth = new UserAuthDTO();
						userAuth.setUser_id(user.getUser_id());
						userAuth.setFirstname(user.getFirstname());
						userAuth.setLastname(user.getLastname());
						userAuth.setLevel_id(user.getLevel_id());
						userAuth.setLogin(user.getLogin());
						userAuth.setLanguage_id(userlang);
						userAuth.setUserPropsAsObject(this.userPropertyDaoImpl.getUserSidebarPropertyDTOByUser(user.getUser_id()));
						userAuth.setOrganisation_users(this.organisationUserDaoImpl.getOrganisationsDTOByUsersId(user.getUser_id()));
						userAuth.setUserlevel(this.userDaoImpl.getUserLevelDTO(user.getLevel_id()));
						
						userAuth.setMaxPendingOrganizations(user.getMaxPendingOrganizations());
						userAuth.setCreatedOrganizations(user.getCreatedOrganizations());
						
						String content = "A User has logged in:<br/> " +
								" "+user.getFirstname()+" "+user.getLastname()+" ["+user.getLogin()+"]<br/>";
						
						Configuration confEmailFromLogin = this.configurationmanagement.getConfKey(3L, "emailFromLogin");
						
						Configuration confEmailMonitor = this.configurationmanagement.getConfKey(3L, "emailMonitor");
						
						this.mailItemServiceDaoImpl.addMailItem(1L, 
								confEmailFromLogin.getConf_value(), //From
								confEmailMonitor.getConf_value(), //receipent
								"User " +user.getFirstname()+" "+user.getLastname()+" ["+user.getLogin()+"] has Logged in "+confEmailFromLogin.getConf_value(), 
								content, 
								null);
						
						return userAuth;
					}

				} else {
					currentClient.setUser_id(null);
					log.debug("new Long(-11)");
					return new Long(-11);
				}
			}
		
		} catch (HibernateException ex) {
			log.error("[loginUser]: ",ex);
		} catch (Exception ex2) {
			log.error("[loginUser]: ",ex2);
		}
		currentClient.setUser_id(null);
		log.debug("new Long(-1)");
		return new Long(-1);
	}

	public Long logout(String SID, long USER_ID) {
		this.sessionmanagement.updateUser(SID, 0);
		return new Long(-12);
	}

	private void updateLastLogin(Users us) {
		try {
			us.setLastlogin(new Date());
			getHibernateTemplate().update(us);
		} catch (HibernateException ex) {
			log.error(ex);
		} catch (Exception ex2) {
			log.error(ex2);
		}
	}

	

	public Long updateByAdministrationPanel(long user_level, Long user_id, Long level_id,
			String login, String password, String lastname, String firstname,
			Date age, String street, String additionalname, String zip, long states_id, String town,
			int availible, String telefon, String fax,
			String mobil, String email, String comment, int status, Vector organisations,
			int title_id, Boolean isPending, Date expireDate, Long maxWorkDays, 
			Boolean useDefaultDiscounts, Boolean unlimitedLicenses, 
			Long licenseUserPayed, Long licenseUserUsed, Float pricePerUser, Vector discounts) {

		if (AuthLevelmanagement.checkUserLevel(user_level) && user_id != 0) {
			try {
				Users us = this.userDaoImpl.getUserById(user_id);
				// Check for duplicates
				boolean checkName = true;
				if (!login.equals(us.getLogin())){
					checkName = this.userDaoImpl.checkUserLogin(login);
				}
				boolean checkEmail = true;
				Adresses_Emails mail = null;
				log.debug("mail 1 update User: "+us.getAdresses().getAdresses_id());
//				log.error("mail 2 update User: "+us.getAdresses().getEmails().size());
				Iterator it = us.getAdresses().getEmails().iterator();
//				log.error("mail 3 update User: "+it);
				if (it.hasNext()){
//					log.error("mail 4 update User: has next");
					mail = (Adresses_Emails) it.next();
//					log.error("mail 5 update User naxt"+mail);
				}				
				log.debug("updateUser mail: "+mail);
				log.debug("updateUser email: "+email);
				if (!email.equals(mail.getMail().getEmail())){
					checkEmail = this.emailDaoImpl.checkUserEMail(email);
				}
				if (checkName && checkEmail) {
					log.info("user_id " + user_id);
					
					us.setLastname(lastname);
					us.setFirstname(firstname);
					us.setAge(age);
					us.setLogin(login);
					us.setUpdatetime(new Date());
					us.setAvailible(availible);
					us.setStatus(status);
					us.setTitle_id(title_id);
					us.setIsPending(isPending);
					us.setExpireDate(expireDate);
					us.setMaxWorkDays(maxWorkDays);
					us.setUseDefaultDiscounts(useDefaultDiscounts);
					us.setUnlimitedLicenses(unlimitedLicenses);
					
					us.setLicenseUserPayed(licenseUserPayed);
					us.setLicenseUserUsed(licenseUserUsed);
					us.setPricePerUser(pricePerUser);
					
					log.debug("level_id ? "+level_id);
					
					if (level_id != 0)
						us.setLevel_id(new Long(level_id));
					if (password.length() != 0) {
						if (password.length()>=4){
							us.setPassword(this.manageCryptStyle.getInstanceOfCrypt().createPassPhrase(password));
						} else {
							return new Long(-7);
						}
					}					
					
					//Todo implement Phone
					this.addressmanagement.updateAdress(us.getAdresses().getAdresses_id(), street, zip, town, states_id, additionalname, comment, fax);
					this.emailDaoImpl.updateUserEmail(mail.getMail().getMail_id(),user_id, email);
					
					//add or delete organisations from this user
					log.debug("add or delete organisations from this user ");
					log.debug(organisations);
					
					if (organisations!=null){
						this.organisationDaoImpl.updateUserOrganisationsByUser(us, organisations);
					}
					
//					log.info("USER " + us.getLastname());

					getHibernateTemplate().update(us);
					
					this.discountDaoImpl.saveOrUpdateDiscountsListByUser(discounts, user_id);
					
					return us.getUser_id();
					
				} else {
					if (!checkName) {
						return new Long(-15);
					} else if (!checkEmail) {
						return new Long(-17);
					}
				}
			} catch (HibernateException ex) {
				log.error("[updateUser]",ex);
			} catch (Exception ex2) {
				log.error("[updateUser]",ex2);
			}
		} else {
			log.error("Error: Permission denied");
			return new Long(-1);
		}
		return new Long(-1);
	}
	
	
	public Long updateUserByOrgModerator(Long updatedby, Long user_id,
			String login, String password, String lastname, String firstname,
			Date age, String street, String additionalname, String zip, long states_id, String town,
			int availible, String telefon, String fax,
			String mobil, String email, String comment, int status, Boolean isModerator,
			int title_id, long organisation_id, Boolean sendMail) {

			try {
				
				Organisation orga = this.organisationDaoImpl.getOrganisationById(organisation_id);
				
				Users us = this.userDaoImpl.getUserById(user_id);
				// Check for duplicates
				boolean checkName = true;
				if (!login.equals(us.getLogin())){
					checkName = this.userDaoImpl.checkUserLogin(login);
				}
				boolean checkEmail = true;
				Adresses_Emails mail = null;
//				log.error("mail 1 update User: "+us.getAdresses().getAdresses_id());
//				log.error("mail 2 update User: "+us.getAdresses().getEmails().size());
				Iterator it = us.getAdresses().getEmails().iterator();
//				log.error("mail 3 update User: "+it);
				if (it.hasNext()){
//					log.error("mail 4 update User: has next");
					mail = (Adresses_Emails) it.next();
//					log.error("mail 5 update User naxt"+mail);
				}				
//				log.error("updateUser mail: "+mail);
//				log.error("updateUser email: "+email);
				if (!email.equals(mail.getMail().getEmail())){
					checkEmail = this.emailDaoImpl.checkUserEMail(email);
				}
				if (checkName && checkEmail) {
//					log.info("user_id " + user_id);
					
					us.setLastname(lastname);
					us.setFirstname(firstname);
					us.setAge(age);
					us.setLogin(login);
					us.setUpdatetime(new Date());
					us.setAvailible(availible);
					us.setStatus(status);
					us.setTitle_id(title_id);

					if (password.length() != 0) {
						if (password.length()>=4){
							us.setPassword(this.manageCryptStyle.getInstanceOfCrypt().createPassPhrase(password));
						} else {
							return new Long(-7);
						}
					}					
					
					//TODO: implement Phone
					this.addressmanagement.updateAdress(us.getAdresses().getAdresses_id(), street, zip, town, states_id, additionalname, comment, fax);
					this.emailDaoImpl.updateUserEmail(mail.getMail().getMail_id(),user_id, email);
					
					//there is no need to update the Org of that User, cause
					//if the User is not part of the Org then the Org.-Moderator would not see him
					//but there is need to update the Moderation Status
					Organisation_Users orgUser = this.organisationDaoImpl.getOrganisation_UserByUserAndOrganisation(user_id, organisation_id);
					orgUser.setIsModerator(isModerator);
					this.organisationDaoImpl.updateOrganisationUser(orgUser);
					
//					log.info("USER " + us.getLastname());

					getHibernateTemplate().update(us);
					
					if (sendMail) {
						Long default_lang_id = Long.valueOf(this.configurationmanagement.
				        		getConfKey(3,"default_lang_id").getConf_value()).longValue();
						
						this.mailmanagement.addMailToSpoolAboutNewUser(updatedby, user_id, orga.getName(), 
								login, password, default_lang_id);
					}
					
					
					
					return us.getUser_id();
					
				} else {
					if (!checkName) {
						return new Long(-15);
					} else if (!checkEmail) {
						return new Long(-17);
					}
				}
			} catch (HibernateException ex) {
				log.error("[updateUserByOrgModerator]",ex);
			} catch (Exception ex2) {
				log.error("[updateUserByOrgModerator]",ex2);
			}
			
		return new Long(-1);
	}



	/**
	 * Method to register a new User, User will automatically be added to the
	 * default user_level(1) new users will be automatically added to the
	 * Organisation with the id specified in the configuration value
	 * default_domain_id
	 * 
	 * @deprecated
	 * 
	 * @param user_level
	 * @param level_id
	 * @param availible
	 * @param status
	 * @param login
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
	 * @return
	 */
	public Long registerUserNew(String login, String Userpass, String lastname,
			String firstname, String email, Date age, String street,
			String additionalname, String fax, String zip, long states_id,
			String town, long language_id, String domain, Integer port, String webapp) {
		try {
			// Checks if FrontEndUsers can register
			if (this.configurationmanagement.getConfKey(3,"allow_frontend_register").getConf_value().equals("1")) {
				// TODO: add availible params sothat users have to verify their
				// login-data
				// TODO: add status from Configuration items
				Long user_id = this.registerUserInitNew(3, 1, 0, login, Userpass,lastname, firstname, 
							email, age, street, additionalname,fax, zip, states_id, town, language_id, 
							new Vector(), domain, port, webapp, "");
				// Get the default organisation_id of registered users
				if (user_id>0){
					long organisation_id = Long.valueOf(this.configurationmanagement.getConfKey(3,"default_domain_id").getConf_value()).longValue();
					this.organisationDaoImpl.addUserToOrganisation(user_id,organisation_id, user_id, "", false);
					
					
					//Auto Assign Users to Orgs
					List<Organisation> listOrgs = this.organisationDaoImpl.getOrganisations();
					
					for (Organisation org : listOrgs) {
					
						if (org.getOrgPatternsMap() != null) {
							
							for (Iterator iter = org.getOrgPatternsMap().iterator();iter.hasNext();) {
								String domainName = iter.next().toString();
								log.debug("Check domainName: "+domainName);
								if (email.indexOf(domainName) != -1) {
									//Found Domain, add User to Organization
									this.organisationDaoImpl.addUserToOrganisationByModerator(user_id, org.getOrganisation_id());
								}
								
							}
							
							
						}
						
					}
					
				}
				return user_id;
			}
		} catch (Exception e) {
			log.error("[registerUser]",e);
		}
		return null;
	}

	/**
	 * Method to register a new User, User will automatically be added to the
	 * default user_level(1) new users will be automatically added to the
	 * Organisation with the id specified in the configuration value
	 * default_domain_id
	 * 
	 * @param login
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
	 * @param domain
	 * @param port
	 * @param webapp
	 * @param regObjectObj
	 * @return
	 */
	public Long registerUserNewWithPersonalDetails(String login, String Userpass, String lastname,
			String firstname, String email, Date age, String street,
			String additionalname, String fax, String zip, long states_id,
			String town, long language_id, String domain, Integer port, String webapp,
			Map regObjectObj) {
		try {
			
			Map<String,String> validateData = new HashMap<String,String>();
			validateData.put("user.login", login);
			validateData.put("user.password", Userpass);
			
			Long returnVal = this.validierungsUtil.validate(validateData);
			
			if (returnVal < 0) {
				return returnVal;
			}
			
			// Checks if FrontEndUsers can register
			if (this.configurationmanagement.getConfKey(3,"allow_frontend_register").getConf_value().equals("1")) {
				
				XStream xStream = new XStream(new XppDriver());
    			xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
    			String xmlStringRegObjectObj = xStream.toXML(regObjectObj);	
				
				// TODO: add availible params sothat users have to verify their
				// login-data
				// TODO: add status from Configuration items
				Long user_id = this.registerUserInitNew(3, 1, 0, login, Userpass,lastname, firstname, 
							email, age, street, additionalname,fax, zip, states_id, town, language_id, 
							new java.util.Vector(), domain, port, webapp, xmlStringRegObjectObj);
				// Get the default organisation_id of registered users
				if (user_id>0){
					long organisation_id = Long.valueOf(this.configurationmanagement.getConfKey(3,"default_domain_id").getConf_value()).longValue();
					this.organisationDaoImpl.addUserToOrganisation(user_id,organisation_id, user_id, "", false);
					
					
					//Auto Assign Users to Orgs
					List<Organisation> listOrgs = this.organisationDaoImpl.getOrganisations();
					
					for (Organisation org : listOrgs) {
					
						if (org.getOrgPatternsMap() != null) {
							
							for (Iterator iter = org.getOrgPatternsMap().iterator();iter.hasNext();) {
								String domainName = iter.next().toString();
								log.debug("Check domainName: "+domainName);
								if (email.indexOf(domainName) != -1) {
									//Found Domain, add User to Organization
									this.organisationDaoImpl.addUserToOrganisationByModerator(user_id, org.getOrganisation_id());
								}
								
							}
							
							
						}
						
					}
					
				}
				
				if (user_id > 0) {
					
					String content = "A User has Signed up:<br/> " +
						" "+firstname+" "+lastname+" ["+login+"]<br/>";
	
					Configuration confEmailFromLogin = this.configurationmanagement.getConfKey(3L, "emailFromLogin");
					
					Configuration confEmailMonitor = this.configurationmanagement.getConfKey(3L, "emailMonitor");
					
					this.mailItemServiceDaoImpl.addMailItem(1L, 
								confEmailFromLogin.getConf_value(), //From
								confEmailMonitor.getConf_value(), //receipent
								"User " +firstname+" "+lastname+" ["+login+"] has Signed up at "+confEmailFromLogin.getConf_value(), 
								content, 
								null);
					
				}
				
				return user_id;
			}
		} catch (Exception e) {
			log.error("[registerUser]",e);
		}
		return null;
	}
	
	/**
	 * Adds a user including his adress-data,auth-date,mail-data
	 * 
	 * @param user_level
	 * @param level_id
	 * @param availible
	 * @param status
	 * @param login
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
	 * @return new users_id OR null if an exception, -1 if an error, -4 if mail
	 *         already taken, -5 if username already taken, -3 if login or pass
	 *         or mail is empty
	 */
	public Long registerUserInitNew(long user_level, long level_id, int availible,
			String login, String Userpass, String lastname,
			String firstname, String email, Date age, String street,
			String additionalname, String fax, String zip, long states_id,
			String town, long language_id, Vector organisations, 
			String domain, Integer port, String webapp, String xmlStringRegObjectObj) throws Exception {
		//TODO: make phone number persistent
		// User Level must be at least Admin
		// Moderators will get a temp update of there UserLevel to add Users to
		// their Group
		if (AuthLevelmanagement.checkModLevel(user_level)) {
			// Check for required data
			if (login.length()>=4 && Userpass.length()>=4) {
				// Check for duplicates
				boolean checkName = this.userDaoImpl.checkUserLogin(login);
				boolean checkEmail = this.emailDaoImpl.checkUserEMail(email);
				if (checkName && checkEmail) {
					
					String hash = this.manageCryptStyle.getInstanceOfCrypt().createPassPhrase(login + CalendarPatterns.getDateWithTimeByMiliSeconds(new Date()));
					
					String link = "http://"+domain+":"+port+"/"+webapp+"/activateUser?u="+hash;
					if (port.equals("80") || port == 80) {
						link = "http://"+domain+"/"+webapp+"/activateUser?u="+hash;
					} else if (port.equals("443") || port == 443) {
						link = "https://"+domain+"/"+webapp+"/activateUser?u="+hash;
					} else if (port.equals("8443") || port == 8443) {
						link = "https://"+domain+":"+port+"/"+webapp+"/activateUser?u="+hash;
					}
					
					String activation_link = "<a href='"+link+"'>"+link+"</a>";
							
					String sendetMail = this.emailDaoImpl.sendMail(login, Userpass, email,activation_link);
					if (!sendetMail.equals("success")) return new Long(-19);
					
					Long address_id = this.addressmanagement.saveAddress(street, zip, town, states_id, additionalname, "",fax);
					if (address_id==null) {
						return new Long(-22);
					}
					//add user with Status 0
					Long user_id = this.userDaoImpl.addUserNonActivated(level_id, availible, 0 ,firstname, 
							login, lastname, language_id, Userpass,address_id, age, hash, xmlStringRegObjectObj);
					if (user_id==null) {
						return new Long(-111);
					}
					Long adress_emails_id = this.emailDaoImpl.registerEmail(email, address_id,"");
					if (adress_emails_id==null) {
						return new Long(-112);
					}					
					this.organisationDaoImpl.addUserOrganisationsByHashMap(user_id, organisations);
					
					if (address_id > 0 && user_id > 0 && adress_emails_id > 0) {
						return user_id;
					} else {
						return new Long(-16);
					}
				} else {
					if (!checkName) {
						return new Long(-15);
					} else if (!checkEmail) {
						return new Long(-17);
					}
				}
			} else {
				return new Long(-13);
			}
		}
		return new Long(-1);
	}
	
	public Long addUserByAdministrationPanel(long user_level, long level_id, int availible,
			int status, String login, String Userpass, String lastname,
			String firstname, String email, Date age, String street,
			String additionalname, String fax, String zip, long states_id,
			String town, long language_id, Vector organisations, 
			Boolean isPending, Date expireDate, Long maxWorkDays, 
			Boolean useDefaultDiscounts, Boolean unlimitedLicenses, 
			Long licenseUserPayed, Long licenseUserUsed, Float pricePerUser, Vector discounts) throws Exception {
		//TODO: make phone number persistent
		// User Level must be at least Admin
		// Moderators will get a temp update of there UserLevel to add Users to
		// their Group
		if (AuthLevelmanagement.checkModLevel(user_level)) {
			// Check for required data
			if (login.length()>=4 && Userpass.length()>=4) {
				// Check for duplicates
				boolean checkName = this.userDaoImpl.checkUserLogin(login);
				boolean checkEmail = this.emailDaoImpl.checkUserEMail(email);
				if (checkName && checkEmail) {
					
					Long address_id = this.addressmanagement.saveAddress(street, zip, town, states_id, additionalname, "",fax);
					if (address_id==null) {
						return new Long(-22);
					}
					Long user_id = this.userDaoImpl.addUser(level_id, availible, status,firstname, login, 
								lastname, language_id, Userpass,address_id, age, 
								isPending, expireDate, maxWorkDays, 
								useDefaultDiscounts, unlimitedLicenses, 
								licenseUserPayed, licenseUserUsed, pricePerUser);
					
					this.discountDaoImpl.saveOrUpdateDiscountsListByUser(discounts, user_id);
					
					if (user_id==null) {
						return new Long(-111);
					}
					Long adress_emails_id = this.emailDaoImpl.registerEmail(email, address_id,"");
					if (adress_emails_id==null) {
						return new Long(-112);
					}					
					this.organisationDaoImpl.addUserOrganisationsByHashMap(user_id, organisations);
					
					if (address_id > 0 && user_id > 0 && adress_emails_id > 0) {
						return user_id;
					} else {
						return new Long(-16);
					}
				} else {
					if (!checkName) {
						return new Long(-15);
					} else if (!checkEmail) {
						return new Long(-17);
					}
				}
			} else {
				return new Long(-13);
			}
		}
		return new Long(-1);
	}
	
	public Long addUserOrgModerator(long insertedby, int availible,
			int status, String login, String Userpass, String lastname,
			String firstname, String email, Date age, String street,
			String additionalname, String fax, String zip, long states_id,
			String town, long language_id, long organisation_id, 
			Boolean isModerator, Boolean sendMail) throws Exception {
		
		try {
			//TODO: make phone number persistent
			// User Level must be at least Admin
			// Moderators will get a temp update of there UserLevel to add Users to
			// their Group
			
			
			Organisation orga = this.organisationDaoImpl.getOrganisationById(organisation_id);
			//check for MaxUsers - there is no maximum
			//			if (orga.getMaxUsers() != null) {
//				
//				Long currentUsers = this.userDaoImpl.selectMaxFromUsersByOrganization(organisation_id);
//				
//				if (currentUsers >= orga.getMaxUsers()) {
//					return new Long(-47);
//				}
//				
//			}
			
			// Check for required data
			if (login.length()>=4 && Userpass.length()>=4) {
				// Check for duplicates
				boolean checkName = this.userDaoImpl.checkUserLogin(login);
				boolean checkEmail = this.emailDaoImpl.checkUserEMail(email);
				if (checkName && checkEmail) {
					
					Long address_id = this.addressmanagement.saveAddress(street, zip, town, states_id, additionalname, "",fax);
					if (address_id==null) {
						return new Long(-22);
					}
					
					Integer expireDateMonthsMax = Integer.valueOf(this.configurationmanagement.getConfKey(3, "expireDateMonthsMax").getConf_value()).intValue();
		        	Long timeToExpire = new Long(expireDateMonthsMax) * 30 * 86400000;
		        	Date currentDate = new Date();
		        	Date expireDate = new Date(currentDate.getTime()+timeToExpire);
		        	
		        	Long maxWorkDays = Long.valueOf(this.configurationmanagement.getConfKey(3, "maxWorkDays").getConf_value()).longValue();
					
					Long user_id = this.userDaoImpl.addUser(new Long(1), availible, status,firstname, login, 
							lastname, language_id, Userpass,address_id, age, true, expireDate, maxWorkDays, true, false, 0L, 0L, null);
					if (user_id==null) {
						return new Long(-111);
					}
					Long adress_emails_id = this.emailDaoImpl.registerEmail(email, address_id,"");
					if (adress_emails_id==null) {
						return new Long(-112);
					}					
					this.organisationDaoImpl.addUserToOrganisation(user_id, organisation_id,
							insertedby, "", isModerator);
					
					//add that User also to the i4Change Playground
					long playGround_organisation_id = Long.valueOf(this.configurationmanagement.getConfKey(3,"default_domain_id").getConf_value()).longValue();
					this.organisationDaoImpl.addUserToOrganisation(user_id,playGround_organisation_id, insertedby, "", isModerator);
					
					if (address_id > 0 && user_id > 0 && adress_emails_id > 0) {
						
						if (sendMail) {
							Long default_lang_id = Long.valueOf(this.configurationmanagement.
					        		getConfKey(3,"default_lang_id").getConf_value()).longValue();
							
							this.mailmanagement.addMailToSpoolAboutNewUser(insertedby, user_id, orga.getName(), 
									login, Userpass, default_lang_id);
						}
						
						return user_id;
					} else {
						return new Long(-16);
					}
				} else {
					if (!checkName) {
						return new Long(-15);
					} else if (!checkEmail) {
						return new Long(-17);
					}
				}
			} else {
				return new Long(-13);
			}
		} catch (Exception err) {
			log.error("[addUserOrgModerator]",err);
		}
		return new Long(-1);
	}
	
	public Long importUserInstallation(long user_level, long level_id, int availible,
			int status, String login, String Userpass, String lastname,
			String firstname, String email, Date age, String street,
			String additionalname, String fax, String zip, long states_id,
			String town, long language_id, Boolean isPending, Date expireDate, Long maxWorkDay) throws Exception {
		//TODO: make phone number persistent
		// User Level must be at least Admin
		// Moderators will get a temp update of there UserLevel to add Users to
		// their Group
		if (AuthLevelmanagement.checkModLevel(user_level)) {
			// Check for required data
			if (login.length()>=4 && Userpass.length()>=4) {
				// Check for duplicates
				boolean checkName = this.userDaoImpl.checkUserLogin(login);
				boolean checkEmail = this.emailDaoImpl.checkUserEMail(email);
				if (checkName && checkEmail) {
											
					Long address_id = this.addressmanagement.saveAddress(street, zip, town, states_id, additionalname, "",fax);
					if (address_id==null) {
						return new Long(-22);
					}
					Long user_id = this.userDaoImpl.addUser(level_id, availible, status,firstname, login, lastname, 
							language_id, Userpass,address_id, age, isPending, expireDate, maxWorkDay,true,true,0L,0L,null);
					if (user_id==null) {
						return new Long(-111);
					}
					Long adress_emails_id = this.emailDaoImpl.registerEmail(email, address_id,"");
					if (adress_emails_id==null) {
						return new Long(-112);
					}					
					
					if (address_id > 0 && user_id > 0 && adress_emails_id > 0) {
						return user_id;
					} else {
						return new Long(-16);
					}
				} else {
					if (!checkName) {
						return new Long(-15);
					} else if (!checkEmail) {
						return new Long(-17);
					}
				}
			} else {
				return new Long(-13);
			}
		}
		return new Long(-1);
	}

	@SuppressWarnings("unchecked")
	public Long updateUserProfile(Map values) {
		try {
			
			Long user_id = Long.valueOf(values.get("user_id").toString()).longValue();
			if (user_id == null || user_id == 0) {
				return new Long(-1);
			}
			
			Users users = this.userDaoImpl.getUserById(user_id);
			if (users == null) {
				return new Long(-1);
			}
			
			String firstname = values.get("firstname").toString();
			String lastname = values.get("lastname").toString();
			Integer title_id = Integer.valueOf(values.get("title_id").toString()).intValue();
			
			String login = values.get("login").toString();
			if (login.length() < 4) {
				//Login too short
				return new Long(-13);
			}
			
			//check login for duplicates
			List<Users> usersWithSameLogin = this.userDaoImpl.getUsersByLogin(login);
			if (usersWithSameLogin.size() > 1) {
				return new Long(-15);
			} else if (usersWithSameLogin.size() == 1) {
				Users userWithSameLogin = usersWithSameLogin.get(0);
				if (!userWithSameLogin.getUser_id().equals(user_id)) {
					//Its another user with the same login, forbidden action
					return new Long(-15);
				}
			}
			
			log.debug(values.get("adresses").getClass().getName());
			log.debug(values.get("adresses"));
			//Personal Address
			Map adressesMap = (Map) values.get("adresses");
			log.debug("adressesMap: "+adressesMap);
			AddressDTO adressesDTO = (AddressDTO) CastMapToObject.getInstance().castByGivenObject(adressesMap, AddressDTO.class);
			
			Adresses adresses = this.addressmanagement.getAdressbyId(adressesDTO.getAdresses_id());
			
			adresses.setAdditionalname(adressesDTO.getAdditionalname());
			adresses.setComment(adressesDTO.getComment());
			adresses.setFax(adressesDTO.getFax());
			adresses.setMobile(adressesDTO.getMobile());
			adresses.setPhone(adressesDTO.getPhone());
			
			CountryDTO country = (CountryDTO) CastMapToObject.getInstance().castByGivenObject((Map) adressesMap.get("states"),CountryDTO.class);
			adresses.setStates(this.countryDaoImpl.getCountryById(country.getCountry_id()));
			
			adresses.setStreet(adressesDTO.getStreet());
			adresses.setTown(adressesDTO.getTown());
			adresses.setZip(adressesDTO.getZip());
			
			Adresses_Emails mail = adresses.getEmails().iterator().next();
			
			log.debug("adresses: "+adresses);
			
			String email = adressesDTO.getEmail();
			log.debug("email: "+email);
			
			//check EMail for duplicates
			List<Users> usersWithSameMail = this.emailDaoImpl.getForCheckUserEMail(email);
			if (usersWithSameMail.size() > 1) {
				return new Long(-17);
			} else if (usersWithSameMail.size() == 1) {
				Users userWithSameMail = usersWithSameMail.get(0);
				if (!userWithSameMail.getUser_id().equals(user_id)) {
					//Its another user with the same EMail, forbidden action
					return new Long(-11);
				}
			}
			
			
			//Company Address
			Long company_adress_id = null;
			
			Object cAdress = values.get("companyAddress");
			
			if (!String.class.isInstance(cAdress)) {
				Map companyAdressesMap = (Map) cAdress;
				AddressDTO companyAdressesDTO = null;
				
				if (companyAdressesMap != null) {
					companyAdressesDTO = (AddressDTO) CastMapToObject.getInstance().castByGivenObject(companyAdressesMap, AddressDTO.class);
				}
				
				
				if (companyAdressesDTO != null && 	companyAdressesDTO.getAdresses_id() != 0) {
					
					//Update Company Adress
					
					Adresses companyAddress = this.addressmanagement.getAdressbyId(companyAdressesDTO.getAdresses_id());
					company_adress_id = companyAddress.getAdresses_id();
					
					companyAddress.setAdditionalname(companyAdressesDTO.getAdditionalname());
					companyAddress.setComment(companyAdressesDTO.getComment());
					companyAddress.setFax(companyAdressesDTO.getFax());
					companyAddress.setMobile(companyAdressesDTO.getMobile());
					companyAddress.setPhone(companyAdressesDTO.getPhone());
					
					Map countryCompanyMap = (Map) companyAdressesMap.get("states");
					Long states_id = Long.valueOf(countryCompanyMap.get("country_id").toString()).longValue();
					companyAddress.setStates(this.countryDaoImpl.getCountryById(states_id));
					
					companyAddress.setStreet(companyAdressesDTO.getStreet());
					companyAddress.setTown(companyAdressesDTO.getTown());
					companyAddress.setZip(companyAdressesDTO.getZip());
					
					Adresses_Emails companyMail = adresses.getEmails().iterator().next();
					
					log.debug("companyAddress: "+companyAddress);
					
					String companyEmail = companyAdressesDTO.getEmail();
					companyMail.getMail().setEmail(companyEmail);
					log.debug("companyEmail: "+companyEmail);
					
					//Update EMail of Company Address
					this.emailDaoImpl.updateUserEmail(companyMail.getMail().getMail_id(),user_id, companyEmail);
					//Update Company Address
					this.addressmanagement.updateAdress(companyAddress);
				} else if (companyAdressesMap != null){
					
					//Add Company Address
					
					String street = companyAdressesDTO.getStreet();
					String zip = companyAdressesDTO.getZip();
					String town = companyAdressesDTO.getTown();
					Map countryCompanyMap = (Map) companyAdressesMap.get("states");
					Long states_id = Long.valueOf(countryCompanyMap.get("country_id").toString()).longValue();
					String additionalname = companyAdressesDTO.getAdditionalname();
					String comment = companyAdressesDTO.getComment();
					String phone = companyAdressesDTO.getPhone();
					String companyEmail = companyAdressesDTO.getEmail();
					String mobile = companyAdressesDTO.getMobile();
					
					log.debug("ABC DEF "+street+", "+zip+", "+
							town+", "+states_id+", "+additionalname+", "+comment+", '', "+phone+", "+mobile);
					
					company_adress_id = this.addressmanagement.saveAddressByCompanyProfile(street, zip, 
							town, states_id, additionalname, comment, "", phone, mobile);
					
					log.debug("ADDED NEW company_adress_id "+company_adress_id);
					log.debug("Save EMail NOW "+companyEmail);
					Long adress_emails_id = this.emailDaoImpl.registerEmail(companyEmail, company_adress_id,"");
					log.debug("Saved EMail adress_emails_id "+adress_emails_id);
					
				}
			} else {
				log.debug("Company Adress is String "+cAdress);
			}
			
			log.debug(values);
			log.debug(values.get("userpass"));
			
			//Password
			String userpass = values.get("userpass").toString();
			if (userpass == null || userpass.length() == 0) {
				userpass = users.getPassword();
			} else {
				if (userpass.length()<4) {
					//password too short
					return new Long(-13);
				}
			}
			
			Date dateOfBirth = null;
	        if (values.get("age") instanceof Date){
	        	dateOfBirth = (Date) values.get("age");
	        	log.error("updateUserProfile dateOfBirth: "+dateOfBirth);
	        }
	        
	        Object regObjectObj = values.get("regObjectObj");
	        
	        if (Map.class.isInstance(regObjectObj)) {
	        	//Is Map
	        	log.debug("regObjectObj Is MAP "+regObjectObj);
	        } else {
	        	//Is NULL
	        	log.debug("regObjectObj Is NULL ");
	        	regObjectObj = null;
	        }
	        
	        XStream xStream = new XStream(new XppDriver());
			xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
			String xmlStringRegObjectObj = xStream.toXML(regObjectObj);	
			
			//Update Email of Personal Address
			this.emailDaoImpl.updateUserEmail(mail.getMail().getMail_id(),user_id, email);
			//Update Personal Address
			this.addressmanagement.updateAdress(adresses);
			
			this.userDaoImpl.updateUserByProfile(
						user_id, firstname, 
						login, lastname, title_id, 
						userpass, 
						adresses.getAdresses_id(), dateOfBirth, 
						xmlStringRegObjectObj, company_adress_id);
			
			return user_id;
			
		} catch (Exception ex) {
			log.error("[updateUserProfile]",ex);
		}
		
		return new Long(-1);
	}
	
	
	
	/**
	 * Update User by Object
	 * @param user_level
	 * @param values
	 * @param users_id
	 * @return
	 */
	
	
	public Long saveOrUpdateUser(Long user_level, Map values, Long users_id){
		try {
			if (AuthLevelmanagement.checkAdminLevel(user_level)) {
				Long returnLong = null;
				Users user = (Users) CastMapToObject.getInstance().castByGivenObject(values, Users.class);
	
				if (user.getUser_id() != null && user.getUser_id()>0) {					
					
					returnLong = user.getUser_id();
					Users savedUser = this.userDaoImpl.getUserById(user.getUser_id());
					savedUser.setAge(user.getAge());
					savedUser.setFirstname(user.getFirstname());
					savedUser.setLastname(user.getLastname());
					savedUser.setTitle_id(user.getTitle_id());
					if (user.getPassword().length()>3){
						savedUser.setPassword(this.manageCryptStyle.getInstanceOfCrypt().createPassPhrase(user.getPassword()));
					}
					
					
					String email = values.get("email").toString();
					
					Adresses_Emails mail = null;
					Iterator it = savedUser.getAdresses().getEmails().iterator();
					if (it.hasNext()){
						mail = (Adresses_Emails) it.next();
					}	
					
					if (!email.equals(mail.getMail().getEmail())){
						boolean checkEmail = this.emailDaoImpl.checkUserEMail(email);
						if (checkEmail) {
							this.emailDaoImpl.updateUserEmail(mail.getMail().getMail_id(),savedUser.getUser_id(), email);
						} else {
							returnLong = new Long(-11);
						}
					}					
					
					this.addressmanagement.updateAdress(user.getAdresses());
					savedUser.setAdresses(this.addressmanagement.getAdressbyId(user.getAdresses().getAdresses_id()));

					getHibernateTemplate().update(savedUser);
					//session.flush();
					
					return returnLong;
				}
				
			} else {
				log.error("[saveOrUpdateUser] invalid auth "+users_id+ " "+new Date());
			}
		} catch (Exception ex) {
			log.error("[saveOrUpdateUser]",ex);
		}
		
		return null;
	}
	
	/**
	 * reset a username by a given mail oder login by sending a mail to the registered EMail-Address
	 * @param email
	 * @param username
	 * @param appLink
	 * @return
	 */
	public Long resetUser(String email, String username, String appLink) {
		try {
			//check if Mail given
			if (email.length()>0){
				Adresses_Emails addr_e = (Adresses_Emails) this.emailDaoImpl.getAdresses_EmailsByMail(email);
				//log.debug("addr_e "+addr_e);
				if (addr_e!=null) {
					//log.debug("getAdresses_id "+addr_e.getAdresses_id());
					Users us = this.userDaoImpl.getUserByAdressesId(addr_e.getAdresses_id());
					if (us!=null) {
						this.sendHashByUser(us, appLink);
						return new Long(-4);
					} else {
						return new Long(-9);
					}
				} else {
					return new Long(-9);
				}
			//check if username given
			} else if (username.length()>0){
				Users us = this.userDaoImpl.getUserByName(username);
				if (us!=null) {
					this.sendHashByUser(us, appLink);
					return new Long(-4);
				} else {
					return new Long(-3);
				}
			}
		} catch (Exception e) {
			log.error("[resetUser]",e);
			return new Long(-1);
		}
		return new Long(-2);
	}
	
	private void sendHashByUser(Users us, String appLink) throws Exception {
		String loginData = us.getLogin()+new Date();
		log.debug("User: "+us.getLogin());
		us.setResethash(this.manageCryptStyle.getInstanceOfCrypt().createPassPhrase(loginData));
		this.userDaoImpl.updateUser(us);
		String reset_link = appLink+"?lzproxied=solo&hash="+us.getResethash();
		
		Adresses_Emails addrE = (Adresses_Emails) us.getAdresses().getEmails().iterator().next();
		
		Long default_lang_id = Long.valueOf(this.configurationmanagement.
        		getConfKey(3,"default_lang_id").getConf_value()).longValue();
		
		String template = this.resetPasswordTemplate.getResetPasswordTemplate(reset_link, default_lang_id);
		
		Fieldlanguagesvalues labelid517 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(517), default_lang_id);
    	
		this.mailHandler.sendMail(addrE.getMail().getEmail(), labelid517.getValue(), template, null);
	}
	
	
	//Maybe re-use that code to send Reminder to Users
	public void sendMailsToPendingUser(){
		try {
			//List<Organisation> orgList = this.getPendingOrganisationsForSendingMail();
			List<Users> userList = this.userDaoImpl.getPendingUsersForSendingMail();
			
			Date now = new Date();
			
			Long default_lang_id = Long.valueOf(this.configurationmanagement.getConfKey(3,"default_lang_id").getConf_value()).longValue();
			
			LinkedList<LinkedHashMap<String,String>> sendedReminderOrgs = new LinkedList<LinkedHashMap<String,String>>();
			
			for (Iterator<Users> iter = userList.iterator();iter.hasNext();) {
				Users user = iter.next();
				
				GregorianCalendar cal = new GregorianCalendar();
				
				if (user.getLastMailSend() == null) {
					cal.setTime(user.getStarttime());
				} else {
					cal.setTime(user.getLastMailSend());
				}
				long dt = new GregorianCalendar(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),0,0).getTimeInMillis();
				
				Long timeDifferenceInMilliSeconds = now.getTime() - dt;
				
				log.debug("timeDifferenceInMilliSeconds: "+timeDifferenceInMilliSeconds);
				//daily send
				//if (timeDifferenceInMilliSeconds >= 86400000) {
				
				if (timeDifferenceInMilliSeconds >= 86400000) {
					
					log.debug("Found Org to send reminder"+user.getUser_id()+" Name: "+user.getLogin());
					System.out.println("Found Org to send reminder"+user.getUser_id()+" Name: "+user.getLogin());
					
					
					log.debug("Send Mail to Moderator: "+user.getUser_id()+" name: "+user.getLogin());
					
					Adresses_Emails mail = null;
					Iterator<Adresses_Emails> it = user.getAdresses().getEmails().iterator();
					if (it.hasNext()){
						mail = it.next();
					}
					
					String reminderUsers = user.getLogin() + " (" + user.getUser_id() + ") (" + mail.getMail().getEmail() + ") ("+user.getReceivePendingReminder()+")";
					

					LinkedHashMap<String,String> sendedReminderOrg = new LinkedHashMap<String,String>();
					sendedReminderOrg.put("orgName", user.getLogin());
					sendedReminderOrg.put("orgId", "");
					sendedReminderOrg.put("orgCreationDate", CalendarPatterns.getDateWithTimeByMiliSeconds(user.getStarttime()));
					sendedReminderOrg.put("reminderUsers", reminderUsers);
					
					sendedReminderOrgs.add(sendedReminderOrg);
					
					String receipent = mail.getMail().getEmail();
					
					log.debug("Send Mail to: "+receipent);
					
					Long langId = user.getLanguage_id();
					if (langId == null) {
						langId = default_lang_id;
					}
					
					if (user.getReceivePendingReminder() == null || user.getReceivePendingReminder()) {
						this.mailmanagement.addMailToSpoolAboutPendingOrganization(user.getUser_id(), receipent, 
								user.getLogin(), user.getStarttime(), langId, user.getUserHash());
					}
					
					
				}
				
				//Update Org to do not send twice
				user.setLastMailSend(new Date());
				this.userDaoImpl.updateUser(user);
				
			}
			
			
			//send Report to Administrators:
			List<Users> admins = this.userDaoImpl.getAdmins();
			
			for (Users admin : admins) {
				
				log.debug("Send Mail to Administrator: "+admin.getUser_id()+" name: "+admin.getLogin());
				
				Adresses_Emails mail = null;
				Iterator<Adresses_Emails> it = admin.getAdresses().getEmails().iterator();
				if (it.hasNext()){
					mail = it.next();
				}
				
				this.mailmanagement.addMailToSpoolAboutReportOfPendingOrganizations(admin.getUser_id(), 
						mail.getMail().getEmail(), sendedReminderOrgs, default_lang_id);
						
			}
			
		} catch (Exception ex2) {
			log.error("[sendMailsToPendingOrgs]" ,ex2);
		}
	}
	
	public void checkTransactionStatus() {
		try {
			
			List<TransactionPaypal> listOfReadyTransactions = this.transactionPaypalDaoImpl.getTransactionPayedByStatus("Completed");
			
			for (TransactionPaypal transactionPaypal : listOfReadyTransactions) {
				
				Users user = transactionPaypal.getUsers();
				
				if (transactionPaypal.getNumberOfLicenses() == null) {
					//do not do anything
				} else if (transactionPaypal.getNumberOfLicenses() == 1) {
					//Single User License
					
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
					
				} else {
					
					//Multi-User License
					if (user.getIsPending()) {
						
						Long numberOfLicensesToAdd = transactionPaypal.getNumberOfLicenses() - 1;
						
						Integer expireDateMonthsMax = 12;
			        	Long timeToExpire = new Long(expireDateMonthsMax) * 31 * 86400000;
			        	Date currentDate = new Date();
			        	Date expireDate = new Date(currentDate.getTime()+timeToExpire);
						
			        	user.setExpireDate(expireDate);
			        	user.setIsPending(false);

			        	if (user.getLicenseUserPayed() == null) {
							user.setLicenseUserPayed(numberOfLicensesToAdd);
						} else {
							user.setLicenseUserPayed(user.getLicenseUserPayed() + numberOfLicensesToAdd);
						}
			        	
			        	if (user.getLicenseUserUsed() == null) {
			        		user.setLicenseUserUsed(1L);
			        	} else {
			        		user.setLicenseUserUsed(user.getLicenseUserUsed()+1);
			        	}
			        	
						this.userDaoImpl.updateUser(user);
						
					} else {
						
						Long numberOfLicensesToAdd = transactionPaypal.getNumberOfLicenses();
						
						if (user.getLicenseUserPayed() == null) {
							user.setLicenseUserPayed(numberOfLicensesToAdd);
						} else {
							user.setLicenseUserPayed(user.getLicenseUserPayed() + numberOfLicensesToAdd);
						}
			        	
						this.userDaoImpl.updateUser(user);
							
						
					}
				}
				
				transactionPaypal.setIsControlled(true);
				transactionPaypal.setUpdated(new Date());
				
				log.debug("transactionPaypal transactionPaypal transactionPaypal");
				log.debug("transactionPaypal "+transactionPaypal.getUsers());
				if (transactionPaypal.getUsers() != null) {
					log.debug("transactionPaypal User_id: "+transactionPaypal.getUsers().getUser_id());
				}
				
				String fileFullPath_output = this.generateInvoice.generateInvoiceByTransaction(transactionPaypal);
				
				this.transactionPaypalDaoImpl.updateTransaction(transactionPaypal);
				
				if (fileFullPath_output != null) {
					this.mailmanagement.addMailToSpoolAboutPaymentReceived(user, fileFullPath_output);
				} else {
					log.error("Failed to send invoice EMAIL as File is empty TransActionPaypalId: "+transactionPaypal.getTransActionPaypalId());
				}
				
			}
			
		} catch (Exception err) {
			log.error("[checkTransactionStatus]",err);
		}
	}
	

}
