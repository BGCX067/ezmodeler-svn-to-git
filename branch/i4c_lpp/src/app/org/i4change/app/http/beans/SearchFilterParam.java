package org.i4change.app.http.beans;

import java.util.Vector;

public class SearchFilterParam {

	public int start;
	public int max;
	public Vector objectTypeNames;
	public String orderBy;
	public boolean asc;
	public Vector search;
	public Vector properties;
	public Integer itemStatus;
	
	
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public Vector getObjectTypeNames() {
		return objectTypeNames;
	}
	public void setObjectTypeNames(Vector objectTypeNames) {
		this.objectTypeNames = objectTypeNames;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public boolean isAsc() {
		return asc;
	}
	public void setAsc(boolean asc) {
		this.asc = asc;
	}
	public Vector getSearch() {
		return search;
	}
	public void setSearch(Vector search) {
		this.search = search;
	}
	public Vector getProperties() {
		return properties;
	}
	public void setProperties(Vector properties) {
		this.properties = properties;
	}
	public Integer getItemStatus() {
		return itemStatus;
	}
	public void setItemStatus(Integer itemStatus) {
		this.itemStatus = itemStatus;
	}
	
	
}
