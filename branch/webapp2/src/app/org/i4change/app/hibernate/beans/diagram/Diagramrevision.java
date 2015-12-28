package org.i4change.app.hibernate.beans.diagram;

// Generated 06.05.2008 11:46:39 by Hibernate Tools 3.2.1.GA

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @hibernate.class table="diagramrevision"
 * lazy="false"
 *
 */
public class Diagramrevision implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1143576931373461878L;
	private long diagramrevisionId;
	private Long insertedby;
	private Date inserted;
	private String comment;
	private Long updatedby;
	private Date updated;
	private String deleted;

	public Diagramrevision() {
	}

	public Diagramrevision(long diagramrevisionId) {
		this.diagramrevisionId = diagramrevisionId;
	}

	public Diagramrevision(long diagramrevisionId, Long insertedby,
			Date inserted, String comment, Long updatedby, Date updated,
			String deleted) {
		super();
		this.diagramrevisionId = diagramrevisionId;
		this.insertedby = insertedby;
		this.inserted = inserted;
		this.comment = comment;
		this.updatedby = updatedby;
		this.updated = updated;
		this.deleted = deleted;
	}

	/**
     * 
     * @hibernate.id
     *  column="diagramrevision_id"
     *  generator-class="increment"
     */ 
	public long getDiagramrevisionId() {
		return this.diagramrevisionId;
	}

	public void setDiagramrevisionId(long diagramrevisionId) {
		this.diagramrevisionId = diagramrevisionId;
	}
	
    /**
     * @hibernate.property
     *  column="insertedby"
     *  type="long"
     */
	public Long getInsertedby() {
		return this.insertedby;
	}

	public void setInsertedby(Long insertedby) {
		this.insertedby = insertedby;
	}
	
    /**
     * @hibernate.property
     *  column="inserted"
     *  type="java.util.Date"
     */
	public Date getInserted() {
		return this.inserted;
	}
	public void setInserted(Date inserted) {
		this.inserted = inserted;
	}

    /**
     * @hibernate.property
     *  column="comment"
     *  type="string"
     */
	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
    /**
     * @hibernate.property
     *  column="updatedby"
     *  type="long"
     */
	public Long getUpdatedby() {
		return this.updatedby;
	}
	public void setUpdatedby(Long updatedby) {
		this.updatedby = updatedby;
	}
	
    /**
     * @hibernate.property
     *  column="updated"
     *  type="java.util.Date"
     */
	public Date getUpdated() {
		return this.updated;
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
		return this.deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

}
