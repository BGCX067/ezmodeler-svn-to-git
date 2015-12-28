package org.i4change.app.hibernate.beans.diagram;

import java.util.Date;

import org.i4change.app.http.beans.SearchFilterParam;

/**
 * 
 * @hibernate.class table="searchquery"
 * lazy="false"
 *
 */
public class SearchQuery {
	
	public long searchQueryId;
	
	public String name;
	public String description;
	
	public String sql;
	public String paramsAsXML;
	
	public Date inserted;
	public Long insertedBy;
	public Date updated;
	public Long updatedBy;
	
	public String deleted;
	public Long organization_id;
	
	public SearchFilterParam sParam;
	
	/**
     * 
     * @hibernate.id
     *  column="searchquery_id"
     *  generator-class="increment"
     */
	public long getSearchQueryId() {
		return searchQueryId;
	}
	public void setSearchQueryId(long searchQueryId) {
		this.searchQueryId = searchQueryId;
	}
	
    /**
     * @hibernate.property
     *  column="sql_string"
     *  type="text"
     */		
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	
    /**
     * @hibernate.property
     *  column="params_as_xml"
     *  type="text"
     */	
	public String getParamsAsXML() {
		return paramsAsXML;
	}
	public void setParamsAsXML(String paramsAsXML) {
		this.paramsAsXML = paramsAsXML;
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
	public Long getInsertedBy() {
		return insertedBy;
	}
	public void setInsertedBy(Long insertedBy) {
		this.insertedBy = insertedBy;
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
	public Long getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
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
     *  column="organization_id"
     *  type="long"
     */	
	public Long getOrganization_id() {
		return organization_id;
	}
	public void setOrganization_id(Long organization_id) {
		this.organization_id = organization_id;
	}
	
	/**
     * @hibernate.property
     *  column="name"
     *  type="string"
     */		
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	/**
     * @hibernate.property
     *  column="description"
     *  type="string"
     */		
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public SearchFilterParam getSParam() {
		return sParam;
	}
	public void setSParam(SearchFilterParam param) {
		sParam = param;
	}
	
}
