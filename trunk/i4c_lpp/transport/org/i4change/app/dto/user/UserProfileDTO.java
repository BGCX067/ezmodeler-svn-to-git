package org.i4change.app.dto.user;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class UserProfileDTO {
	
	private Long user_id;
	private String pictureuri;
	private String firstname;
	private String lastname;
	private Date age;
	private Long title_id;
	private String login; 
	private String userpass = "";
	
	private Long createdOrganizations;
	private Long maxPendingOrganizations;
	private Boolean isPending;
    private Date expireDate;
    private Long maxWorkDays;
    private Long usedWorkDays;
    private Date lastWorkDay;
	
	private AddressDTO adresses;
	private AddressDTO companyAddress;
	private Map regObjectObj;
	
    private Float pricePerUser;
    private Boolean useDefaultDiscounts;
	private List<DiscountDTO> discounts;
	
    private Long licenseUserPayed;
    private Long licenseUserUsed;
    private Boolean unlimitedLicenses;
    
    private List<OrganisationUserDTO> organisation_users;
	private List<UserPropertyDTO> userPropsAsObject;
	
	//Admin needs this Values
	private Long level_id;
	private Integer status;
	
	public AddressDTO getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(AddressDTO companyAddress) {
		this.companyAddress = companyAddress;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Long getCreatedOrganizations() {
		return createdOrganizations;
	}

	public void setCreatedOrganizations(Long createdOrganizations) {
		this.createdOrganizations = createdOrganizations;
	}

	public Long getMaxPendingOrganizations() {
		return maxPendingOrganizations;
	}

	public void setMaxPendingOrganizations(Long maxPendingOrganizations) {
		this.maxPendingOrganizations = maxPendingOrganizations;
	}

	public Map getRegObjectObj() {
		return regObjectObj;
	}

	public void setRegObjectObj(Map regObjectObj) {
		this.regObjectObj = regObjectObj;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getPictureuri() {
		return pictureuri;
	}

	public void setPictureuri(String pictureuri) {
		this.pictureuri = pictureuri;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Date getAge() {
		return age;
	}

	public void setAge(Date age) {
		this.age = age;
	}

	public Long getTitle_id() {
		return title_id;
	}

	public void setTitle_id(Long title_id) {
		this.title_id = title_id;
	}

	public AddressDTO getAdresses() {
		return adresses;
	}

	public void setAdresses(AddressDTO adresses) {
		this.adresses = adresses;
	}

	public Float getPricePerUser() {
		return pricePerUser;
	}

	public void setPricePerUser(Float pricePerUser) {
		this.pricePerUser = pricePerUser;
	}

	public Boolean getUseDefaultDiscounts() {
		return useDefaultDiscounts;
	}

	public void setUseDefaultDiscounts(Boolean useDefaultDiscounts) {
		this.useDefaultDiscounts = useDefaultDiscounts;
	}

	public List<DiscountDTO> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(List<DiscountDTO> discounts) {
		this.discounts = discounts;
	}

	public Long getLicenseUserPayed() {
		return licenseUserPayed;
	}

	public void setLicenseUserPayed(Long licenseUserPayed) {
		this.licenseUserPayed = licenseUserPayed;
	}

	public Long getLicenseUserUsed() {
		return licenseUserUsed;
	}

	public void setLicenseUserUsed(Long licenseUserUsed) {
		this.licenseUserUsed = licenseUserUsed;
	}

	public Boolean getUnlimitedLicenses() {
		return unlimitedLicenses;
	}

	public void setUnlimitedLicenses(Boolean unlimitedLicenses) {
		this.unlimitedLicenses = unlimitedLicenses;
	}

	public List<OrganisationUserDTO> getOrganisation_users() {
		return organisation_users;
	}

	public void setOrganisation_users(List<OrganisationUserDTO> organisation_users) {
		this.organisation_users = organisation_users;
	}

	public List<UserPropertyDTO> getUserPropsAsObject() {
		return userPropsAsObject;
	}

	public void setUserPropsAsObject(List<UserPropertyDTO> userPropsAsObject) {
		this.userPropsAsObject = userPropsAsObject;
	}
	public Boolean getIsPending() {
		return isPending;
	}

	public void setIsPending(Boolean isPending) {
		this.isPending = isPending;
	}

	public String getUserpass() {
		return userpass;
	}

	public void setUserpass(String userpass) {
		this.userpass = userpass;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public Long getMaxWorkDays() {
		return maxWorkDays;
	}

	public void setMaxWorkDays(Long maxWorkDays) {
		this.maxWorkDays = maxWorkDays;
	}

	public Long getUsedWorkDays() {
		return usedWorkDays;
	}

	public void setUsedWorkDays(Long usedWorkDays) {
		this.usedWorkDays = usedWorkDays;
	}

	public Date getLastWorkDay() {
		return lastWorkDay;
	}

	public void setLastWorkDay(Date lastWorkDay) {
		this.lastWorkDay = lastWorkDay;
	}

	public Long getLevel_id() {
		return level_id;
	}

	public void setLevel_id(Long level_id) {
		this.level_id = level_id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
