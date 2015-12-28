package org.i4change.app.hibernate.beans.help;

import java.util.Date;

/**
 * 
 * @hibernate.class table="helptopic"
 *
 */
public class HelpTopic {
	
	private long helptopicId;
	private Date inserted;
	private Long insertedby;
	private Date updated;
	private Long updatedby;
	private Long helpId;
	private String helpName;
	private Long topicLabelId;
	private Long labelId;
	private String deleted;
	private Integer priority;
	private Boolean isAgentHelp;
	
	/**
     * 
     * @hibernate.id
     *  column="helptopic_id"
     *  generator-class="increment"
     */ 
	public long getHelptopicId() {
		return helptopicId;
	}
	public void setHelptopicId(long helptopicId) {
		this.helptopicId = helptopicId;
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
     *  column="insertedby"
     *  type="long"
     */ 
	public Long getInsertedby() {
		return insertedby;
	}
	public void setInsertedby(Long insertedby) {
		this.insertedby = insertedby;
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
     *  column="updatedby"
     *  type="long"
     */ 
	public Long getUpdatedby() {
		return updatedby;
	}
	public void setUpdatedby(Long updatedby) {
		this.updatedby = updatedby;
	}
	
	/**
     * @hibernate.property
     *  column="helpId"
     *  type="long"
     */ 
	public Long getHelpId() {
		return helpId;
	}
	public void setHelpId(Long helpId) {
		this.helpId = helpId;
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
     *  column="labelid"
     *  type="long"
     */
	public Long getLabelId() {
		return labelId;
	}
	public void setLabelId(Long labelId) {
		this.labelId = labelId;
	}

	/**
     * @hibernate.property
     *  column="priority"
     *  type="int"
     */	
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	/**
     * @hibernate.property
     *  column="isagenthelp"
     *  type="boolean"
     */	
	public Boolean getIsAgentHelp() {
		return isAgentHelp;
	}
	public void setIsAgentHelp(Boolean isAgentHelp) {
		this.isAgentHelp = isAgentHelp;
	}
	
	/**
     * @hibernate.property
     *  column="topiclabelId"
     *  type="long"
     */
	public Long getTopicLabelId() {
		return topicLabelId;
	}
	public void setTopicLabelId(Long topicLabelId) {
		this.topicLabelId = topicLabelId;
	}
	
	/**
     * @hibernate.property
     *  column="helpname"
     *  type="string"
     */
	public String getHelpName() {
		return helpName;
	}
	public void setHelpName(String helpName) {
		this.helpName = helpName;
	}

}
