package org.i4change.app.hibernate.beans.user;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.i4change.app.hibernate.beans.adresses.Adresses;
import org.i4change.app.hibernate.beans.domain.Organisation_Users;

/**
 * 
 * @hibernate.class table="users"
 * lazy="false"
 *
 */
public class Users {
	 
	private Long user_id;
	private Date age;
	private Integer availible;
	private String firstname;
	private Date lastlogin;
	private String lastname;
	private Long lasttrans;
	private Long level_id;
	private String login;
	private String password;
	private Date regdate;
	private Integer status;
	private Integer title_id;
	private Date starttime;
	private Date updatetime;
	private String pictureuri;
	private String deleted;
	private Long language_id;
	private Adresses adresses;
	private Adresses companyAddress;
	private String resethash;
	private String activatehash;
	private String uuid;
	
	private Userlevel userlevel;

    private Usergroups[] usergroups; 
    
    //do not show in the List of possible assignee's, this is usefull if you have multiple 
    //Admins/Moderators, but don' want to show them on the List
    private Boolean doNotShowInOrgSelection;
    
    //this attribute is set by the application-logic not Hibernate
    //private Organisation_Users orgUser;
    
    //Unfortunately java.util.Set does not work with javaRPC
    private List<Organisation_Users> organisation_users;
    
    private Boolean isPending;
    private Date expireDate;
    private Long maxWorkDays;
    private Long usedWorkDays;
    private Date lastWorkDay;
	//this attribute is used by the Mail Spool to check if the last Mail in 
	//case of a Pending Organization is send within the last 24 hours    
    private Date lastMailSend;
    
    //serialized XML
    //private String userProperties;
    //Not stored
    public List<UserProperty> userPropsAsObject;
    
    private Long maxPendingOrganizations;
    private Long createdOrganizations;
    
    //if this user gets a reminder mail of his pending  Organization
    private Boolean receivePendingReminder;
    private String userHash;
    
    private String xmlStringRegObjectObj;
    private Map regObjectObj;
    
    private Float pricePerUser;
    private Boolean useDefaultDiscounts;
    private List<Discount> discounts;
    
    private Long licenseUserPayed;
    private Long licenseUserUsed;
    private Boolean unlimitedLicenses;
	private Organisation_Users orgUser;

	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    /**
	 * @hibernate.many-to-one
	 * column = "adresses_id"
	 * class = "org.i4change.app.hibernate.beans.adresses.Adresses"
	 * insert="true"
	 * update="true"
	 * outer-join="true"
	 * lazy="false"
     */		
	public Adresses getAdresses() {
		return adresses;
	}
	public void setAdresses(Adresses adresses) {
		this.adresses = adresses;
	}

    
    /**
     * @hibernate.property
     *  column="age"
     *  type="java.util.Date"
     */  
	public Date getAge() {
		return age;
	}
	public void setAge(Date age) {
		if(age==null)
			age=new Date();
		this.age = age;
	}
    
    /**
     * @hibernate.property
     *  column="availible"
     *  type="int"
     */  
	public Integer getAvailible() {
		return availible;
	}
	public void setAvailible(Integer availible) {
		this.availible = availible;
	}

    /**
     * @hibernate.property
     *  column="firstname"
     *  type="string"
     */ 	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
    /**
     * @hibernate.property
     *  column="uuid"
     *  type="string"
     */ 
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
     * @hibernate.property
     *  column="lastlogin"
     *  type="java.util.Date"
     */ 
	public Date getLastlogin() {
		return lastlogin;
	}
	public void setLastlogin(Date lastlogin) {
		this.lastlogin = lastlogin;
	}

    /**
     * @hibernate.property
     *  column="lastname"
     *  type="string"
     */ 
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
    
    /**
     * @hibernate.property
     *  column="lasttrans"
     *  type="long"
     */ 
	public Long getLasttrans() {
		return lasttrans;
	}
	public void setLasttrans(Long lasttrans) {
		this.lasttrans = lasttrans;
	}
    
    /**
     * @hibernate.property
     *  column="level_id"
     *  type="long"
     */ 
	public Long getLevel_id() {
		return level_id;
	}
	public void setLevel_id(Long level_id) {
		this.level_id = level_id;
	}

    /**
     * @hibernate.property
     *  column="login"
     *  type="string"
     */ 
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}

    /**
     * @hibernate.property
     *  column="password"
     *  type="string"
     */ 	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    
    /**
     * @hibernate.property
     *  column="regdate"
     *  type="java.util.Date"
     */ 
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
    
    /**
     * @hibernate.property
     *  column="status"
     *  type="int"
     */ 
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
    
    /**
     * @hibernate.property
     *  column="title_id"
     *  type="int"
     */
	public Integer getTitle_id() {
		return title_id;
	}
	public void setTitle_id(Integer title_id) {
		this.title_id = title_id;
	}
	
    /**
     * 
     * @hibernate.id
     *  column="user_id"
     *  generator-class="increment"
     */ 	
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	
	public Usergroups[] getUsergroups() {
		return usergroups;
	}
	public void setUsergroups(Usergroups[] usergroups) {
		this.usergroups = usergroups;
	}
	
	public Userlevel getUserlevel() {
		return userlevel;
	}
	public void setUserlevel(Userlevel userlevel) {
		this.userlevel = userlevel;
	}    
    
    
    /**
     * @hibernate.property
     *  column="starttime"
     *  type="java.util.Date"
     */  	
	public Date getStarttime() {
		return starttime;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
    
    /**
     * @hibernate.property
     *  column="updatetime"
     *  type="java.util.Date"
     */  	
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
    /**
     * @hibernate.property
     *  column="deleted"
     *  type="string"
     */	
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

    /**
     * @hibernate.property
     *  column="pictureuri"
     *  type="string"
     */	
	public String getPictureuri() {
		return pictureuri;
	}
	public void setPictureuri(String pictureuri) {
		this.pictureuri = pictureuri;
	}

    /**
     * @hibernate.property
     *  column="language_id"
     *  type="long"
     */	
	public Long getLanguage_id() {
		return language_id;
	}
	public void setLanguage_id(Long language_id) {
		this.language_id = language_id;
	}
		

	public List<Organisation_Users> getOrganisation_users() {
		return organisation_users;
	}
	public void setOrganisation_users(List<Organisation_Users> organisation_users) {
		this.organisation_users = organisation_users;
	}

    /**
     * @hibernate.property
     *  column="resethash"
     *  type="string"
     */	
	public String getResethash() {
		return resethash;
	}
	public void setResethash(String resethash) {
		this.resethash = resethash;
	}
	
	/**
     * @hibernate.property
     *  column="activatehash"
     *  type="string"
     */	
	public String getActivatehash() {
		return activatehash;
	}
	public void setActivatehash(String activatehash) {
		this.activatehash = activatehash;
	}



//	public String getUserProperties() {
//		return userProperties;
//	}
//	public void setUserProperties(String userProperties) {
//		this.userProperties = userProperties;
//	}


	public List<UserProperty> getUserPropsAsObject() {
		return userPropsAsObject;
	}
	public void setUserPropsAsObject(List<UserProperty> userPropsAsObject) {
		this.userPropsAsObject = userPropsAsObject;
	}

	/**
     * @hibernate.property
     *  column="max_pending_organizations"
     *  type="long"
     */
	public Long getMaxPendingOrganizations() {
		return maxPendingOrganizations;
	}
	public void setMaxPendingOrganizations(Long maxPendingOrganizations) {
		this.maxPendingOrganizations = maxPendingOrganizations;
	}
	

    /**
     * @hibernate.property
     *  column="created_organizations"
     *  type="long"
     */
	public Long getCreatedOrganizations() {
		return createdOrganizations;
	}
	public void setCreatedOrganizations(Long createdOrganizations) {
		this.createdOrganizations = createdOrganizations;
	}

	/**
     * @hibernate.property
     *  column="receivependingreminder"
     *  type="boolean"
     */
	public Boolean getReceivePendingReminder() {
		return receivePendingReminder;
	}
	public void setReceivePendingReminder(Boolean receivePendingReminder) {
		this.receivePendingReminder = receivePendingReminder;
	}

	
	/**
     * @hibernate.property
     *  column="userhash"
     *  type="string"
     */
	public String getUserHash() {
		return userHash;
	}
	public void setUserHash(String userHash) {
		this.userHash = userHash;
	}

	/**
     * @hibernate.property
     *  column="donotshowinorgselection"
     *  type="boolean"
     */
	public Boolean getDoNotShowInOrgSelection() {
		return doNotShowInOrgSelection;
	}
	public void setDoNotShowInOrgSelection(Boolean doNotShowInOrgSelection) {
		this.doNotShowInOrgSelection = doNotShowInOrgSelection;
	}

//	public Organisation_Users getOrgUser() {
//		return orgUser;
//	}
//	public void setOrgUser(Organisation_Users orgUser) {
//		this.orgUser = orgUser;
//	}

	/**
     * @hibernate.property
     *  column="xmlstringregobjectobj"
     *  type="text"
     */
	public String getXmlStringRegObjectObj() {
		return xmlStringRegObjectObj;
	}
	public void setXmlStringRegObjectObj(String xmlStringRegObjectObj) {
		this.xmlStringRegObjectObj = xmlStringRegObjectObj;
	}

	public Map getRegObjectObj() {
		return regObjectObj;
	}
	public void setRegObjectObj(Map regObjectObj) {
		this.regObjectObj = regObjectObj;
	}

	/**
	 * @hibernate.many-to-one
	 * column = "company_adresses_id"
	 * class = "org.i4change.app.hibernate.beans.adresses.Adresses"
	 * insert="true"
	 * update="true"
	 * outer-join="true"
	 * lazy="false"
     */
	public Adresses getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(Adresses companyAddress) {
		this.companyAddress = companyAddress;
	}

	/**
     * @hibernate.property
     *  column="ispending"
     *  type="boolean"
     */
	public Boolean getIsPending() {
		return isPending;
	}
	public void setIsPending(Boolean isPending) {
		this.isPending = isPending;
	}

	/**
     * @hibernate.property
     *  column="expiredate"
     *  type="java.util.Date"
     */
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	/**
     * @hibernate.property
     *  column="maxworkdays"
     *  type="long"
     */
	public Long getMaxWorkDays() {
		return maxWorkDays;
	}
	public void setMaxWorkDays(Long maxWorkDays) {
		this.maxWorkDays = maxWorkDays;
	}

	/**
     * @hibernate.property
     *  column="usedworkdays"
     *  type="long"
     */
	public Long getUsedWorkDays() {
		return usedWorkDays;
	}
	public void setUsedWorkDays(Long usedWorkDays) {
		this.usedWorkDays = usedWorkDays;
	}

	/**
     * @hibernate.property
     *  column="lastworkday"
     *  type="java.util.Date"
     */
	public Date getLastWorkDay() {
		return lastWorkDay;
	}
	public void setLastWorkDay(Date lastWorkDay) {
		this.lastWorkDay = lastWorkDay;
	}

	/**
     * @hibernate.property
     *  column="lastmailsend"
     *  type="java.util.Date"
     */
	public Date getLastMailSend() {
		return lastMailSend;
	}
	public void setLastMailSend(Date lastMailSend) {
		this.lastMailSend = lastMailSend;
	}

	/**
     * @hibernate.property
     *  column="priceperuser"
     *  type="float"
     */
	public Float getPricePerUser() {
		return pricePerUser;
	}
	public void setPricePerUser(Float pricePerUser) {
		this.pricePerUser = pricePerUser;
	}

	public List<Discount> getDiscounts() {
		return discounts;
	}
	public void setDiscounts(List<Discount> discounts) {
		this.discounts = discounts;
	}

	/**
     * @hibernate.property
     *  column="licenseuserpayed"
     *  type="long"
     */
	public Long getLicenseUserPayed() {
		return licenseUserPayed;
	}
	public void setLicenseUserPayed(Long licenseUserPayed) {
		this.licenseUserPayed = licenseUserPayed;
	}

	/**
     * @hibernate.property
     *  column="licenseuserused"
     *  type="long"
     */
	public Long getLicenseUserUsed() {
		return licenseUserUsed;
	}
	public void setLicenseUserUsed(Long licenseUserUsed) {
		this.licenseUserUsed = licenseUserUsed;
	}

	/**
     * @hibernate.property
     *  column="usedefaultdiscounts"
     *  type="boolean"
     */
	public Boolean getUseDefaultDiscounts() {
		return useDefaultDiscounts;
	}
	public void setUseDefaultDiscounts(Boolean useDefaultDiscounts) {
		this.useDefaultDiscounts = useDefaultDiscounts;
	}

	/**
     * @hibernate.property
     *  column="unlimitedlicenses"
     *  type="boolean"
     */
	public Boolean getUnlimitedLicenses() {
		return unlimitedLicenses;
	}
	public void setUnlimitedLicenses(Boolean unlimitedLicenses) {
		this.unlimitedLicenses = unlimitedLicenses;
	}

	public Organisation_Users getOrgUser() {
		return orgUser;
	}
	public void setOrgUser(Organisation_Users orgUser) {
		this.orgUser = orgUser;
	}

	
}
