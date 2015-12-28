package org.i4change.app.dto.user;

public class UserOrgDTO {

	private Long user_id;
	private String firstname;
	private String lastname;
	private String login;
	private Long level_id;
	private OrgUserConnectionDTO orgUser;
	
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
	public OrgUserConnectionDTO getOrgUser() {
		return orgUser;
	}
	public void setOrgUser(OrgUserConnectionDTO orgUser) {
		this.orgUser = orgUser;
	}
	
}
