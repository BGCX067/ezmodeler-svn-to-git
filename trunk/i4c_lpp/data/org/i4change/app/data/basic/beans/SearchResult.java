package org.i4change.app.data.basic.beans;

import java.util.List;
import java.util.Vector;

public class SearchResult {
	
	private String objectName;
	private Long records;
	private List result;
	private String sqlQuery;
	private Vector<String> columnNames;
	
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public Long getRecords() {
		return records;
	}
	public void setRecords(Long records) {
		this.records = records;
	}
	public List getResult() {
		return result;
	}
	public void setResult(List result) {
		this.result = result;
	}
	public String getSqlQuery() {
		return sqlQuery;
	}
	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
	}
	public Vector<String> getColumnNames() {
		return columnNames;
	}
	public void setColumnNames(Vector<String> columnNames) {
		this.columnNames = columnNames;
	}
	
}
