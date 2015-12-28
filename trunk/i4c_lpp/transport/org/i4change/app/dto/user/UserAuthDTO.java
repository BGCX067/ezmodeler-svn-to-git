package org.i4change.app.dto.user;

import java.util.List;

public class UserAuthDTO {
	
	private Long user_id;
	private String firstname;
	private String lastname;
	private String login;
	private Long level_id;
	private Long language_id;
	private UserlevelDTO userlevel;
	
	private Long createdOrganizations;
	private Long maxPendingOrganizations;
	
	private List<OrganisationUserDTO> organisation_users;
	private List<UserPropertyDTO> userPropsAsObject;
	
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
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
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public Long getLevel_id() {
		return level_id;
	}
	public void setLevel_id(Long level_id) {
		this.level_id = level_id;
	}
	public Long getLanguage_id() {
		return language_id;
	}
	public void setLanguage_id(Long language_id) {
		this.language_id = language_id;
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
	public UserlevelDTO getUserlevel() {
		return userlevel;
	}
	public void setUserlevel(UserlevelDTO userlevel) {
		this.userlevel = userlevel;
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
	
}
