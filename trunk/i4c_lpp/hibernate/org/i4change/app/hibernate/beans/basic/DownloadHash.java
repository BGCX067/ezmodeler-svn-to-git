package org.i4change.app.hibernate.beans.basic;

import java.util.Date;

/**
 * 
 * @hibernate.class table="downloadhash"
 *
 */
public class DownloadHash {
	
	private Long downloadHashId;
	private String hash;
	private Date inserted;
	private Date downloaded;
	private String absoluteFilePath;
	private Boolean used;
	private String ip;
	
	/**
     * 
     * @hibernate.id
     *  column="downloadhash_id"
     *  generator-class="increment"
     */ 
	public Long getDownloadHashId() {
		return downloadHashId;
	}
	public void setDownloadHashId(Long downloadHashId) {
		this.downloadHashId = downloadHashId;
	}
	
	/**
     * @hibernate.property
     *  column="hash"
     *  type="string"
     */ 
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
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
     *  column="downloaded"
     *  type="java.util.Date"
     */ 
	public Date getDownloaded() {
		return downloaded;
	}
	public void setDownloaded(Date downloaded) {
		this.downloaded = downloaded;
	}

	/**
     * @hibernate.property
     *  column="absolutefilepath"
     *  type="string"
     */ 
	public String getAbsoluteFilePath() {
		return absoluteFilePath;
	}
	public void setAbsoluteFilePath(String absoluteFilePath) {
		this.absoluteFilePath = absoluteFilePath;
	}

	/**
     * @hibernate.property
     *  column="used"
     *  type="boolean"
     */ 	
	public Boolean getUsed() {
		return used;
	}
	public void setUsed(Boolean used) {
		this.used = used;
	}
	
	/**
     * @hibernate.property
     *  column="ip"
     *  type="string"
     */ 
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
