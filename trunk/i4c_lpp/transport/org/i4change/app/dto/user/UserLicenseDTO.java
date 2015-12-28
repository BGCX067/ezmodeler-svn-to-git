package org.i4change.app.dto.user;

public class UserLicenseDTO {
	
	private Long user_id;
    private Long licenseUserPayed;
    private Long licenseUserUsed;
    private Boolean unlimitedLicenses;
    
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
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
    
}
