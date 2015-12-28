package org.i4change.app.hibernate.beans.user;

import java.util.Date;

import org.i4change.app.hibernate.beans.domain.Organisation;

/**
 * 
 * @hibernate.class table="discount"
 * lazy="false"
 *
 */
public class Discount {
	
	private long discountId;
	/*minimum number of users, number of Users you buy at one time*/
	private Integer numberOfUsers;
	private Double discount;
	private Organisation organisation;
	private Users user;
	private String deleted;
	private Date inserted;
	private Date updated;
	
	/**
     * 
     * @hibernate.id
     *  column="discount_id"
     *  generator-class="increment"
     */
	public long getDiscountId() {
		return discountId;
	}
	public void setDiscountId(long discountId) {
		this.discountId = discountId;
	}
	
	/**
     * @hibernate.property
     *  column="numberofusers"
     *  type="int"
     */	
	public Integer getNumberOfUsers() {
		return numberOfUsers;
	}
	public void setNumberOfUsers(Integer numberOfUsers) {
		this.numberOfUsers = numberOfUsers;
	}
	
	/**
     * @hibernate.property
     *  column="discount"
     *  precision="2"
     *  type="double"
     */
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	
	/**
	 * @hibernate.many-to-one
	 * column = "organisation_id"
	 * class = "org.i4change.app.hibernate.beans.domain.Organisation"
	 * insert="true"
	 * update="true"
	 * outer-join="true"
	 * lazy="false"
     */
	public Organisation getOrganisation() {
		return organisation;
	}
	public void setOrganisation(Organisation organisation) {
		this.organisation = organisation;
	}
	
	/**
     * @hibernate.property
     *  column="inserted"
     *  type="java.util.Date"
     */
	public Date getInserted() {
		return inserted;
	}
	public void setInserted(Date inserted) {
		this.inserted = inserted;
	}
	
	/**
     * @hibernate.property
     *  column="updated"
     *  type="java.util.Date"
     */	
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
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
	 * @hibernate.many-to-one
	 * column = "user_id"
	 * class = "org.i4change.app.hibernate.beans.user.Users"
	 * insert="true"
	 * update="true"
	 * outer-join="true"
	 * lazy="false"
     */	
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	
}
