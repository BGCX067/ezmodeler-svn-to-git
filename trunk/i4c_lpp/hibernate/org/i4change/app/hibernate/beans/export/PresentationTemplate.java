package org.i4change.app.hibernate.beans.export;

import java.util.Date;

/**
 * 
 * @hibernate.class table="presentationtemplate"
 * lazy="false"
 *
 */
public class PresentationTemplate {

	private long presentationTemplateId;
	private String templateName;
	private String templateKey;
	private String preview;
	private String deleted;
	private Boolean isPublic;
	private Long organisationId;
	private Long insertedby;
	private Long updatedby;
	private Date inserted;
	private Date updated;
	private double imageWidth;
	private double imageHeight;
	private double imageX;
	private double imageY;
	
	private String headSlide;
	private String midSlide;
	private String footSlide;
	private String imagePre;
	
	private Integer graphicStyleIndent;
	private Integer textStyleIndent;
	private Integer textSpanStyleIndent;
	
	private double footery;
	
	public PresentationTemplate(){
		super();
	}
	
	/**
     * 
     * @hibernate.id
     *  column="presentationtemplate_id"
     *  generator-class="increment"
     */ 
	public long getPresentationTemplateId() {
		return presentationTemplateId;
	}
	public void setPresentationTemplateId(long presentationTemplateId) {
		this.presentationTemplateId = presentationTemplateId;
	}
	
	/**
     * @hibernate.property
     *  column="templateName"
     *  type="string"
     */
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	
	/**
     * @hibernate.property
     *  column="templateKey"
     *  type="string"
     */
	public String getTemplateKey() {
		return templateKey;
	}
	public void setTemplateKey(String templateKey) {
		this.templateKey = templateKey;
	}
	
	/**
     * @hibernate.property
     *  column="preview"
     *  type="string"
     */
	public String getPreview() {
		return preview;
	}
	public void setPreview(String preview) {
		this.preview = preview;
	}
	
	/**
     * @hibernate.property
     *  column="isPublic"
     *  type="boolean"
     */
	public Boolean getIsPublic() {
		return isPublic;
	}
	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}
	
	/**
     * @hibernate.property
     *  column="organisation_id"
     *  type="long"
     */
	public Long getOrganisationId() {
		return organisationId;
	}
	public void setOrganisationId(Long organisationId) {
		this.organisationId = organisationId;
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
     *  column="inserted"
     *  type="java.util.Date"
     */
	public void setInserted(Date inserted) {
		this.inserted = inserted;
	}
	public Date getUpdated() {
		return updated;
	}
	
	/**
     * @hibernate.property
     *  column="updated"
     *  type="java.util.Date"
     */
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	
	/**
     * @hibernate.property
     *  column="deleted"
     *  type="string"
     */
	public String getDeleted() {
		return deleted;
	}
	public Date getInserted() {
		return inserted;
	}

	/**
     * @hibernate.property
     *  column="imageWidth"
     *  type="double"
     */
	public double getImageWidth() {
		return imageWidth;
	}
	public void setImageWidth(double imageWidth) {
		this.imageWidth = imageWidth;
	}

	/**
     * @hibernate.property
     *  column="imageHeight"
     *  type="double"
     */
	public double getImageHeight() {
		return imageHeight;
	}
	public void setImageHeight(double imageHeight) {
		this.imageHeight = imageHeight;
	}
	
	/**
     * @hibernate.property
     *  column="imageX"
     *  type="double"
     */
	public double getImageX() {
		return imageX;
	}
	public void setImageX(double imageX) {
		this.imageX = imageX;
	}

	/**
     * @hibernate.property
     *  column="imageY"
     *  type="double"
     */
	public double getImageY() {
		return imageY;
	}
	public void setImageY(double imageY) {
		this.imageY = imageY;
	}

	/**
     * @hibernate.property
     *  column="headslide"
     *  type="text"
     */
	public String getHeadSlide() {
		return headSlide;
	}
	public void setHeadSlide(String headSlide) {
		this.headSlide = headSlide;
	}

	/**
     * @hibernate.property
     *  column="midslide"
     *  type="text"
     */
	public String getMidSlide() {
		return midSlide;
	}
	public void setMidSlide(String midSlide) {
		this.midSlide = midSlide;
	}

	/**
     * @hibernate.property
     *  column="footslide"
     *  type="text"
     */
	public String getFootSlide() {
		return footSlide;
	}
	public void setFootSlide(String footSlide) {
		this.footSlide = footSlide;
	}

	/**
     * @hibernate.property
     *  column="imagepre"
     *  type="text"
     */
	public String getImagePre() {
		return imagePre;
	}
	public void setImagePre(String imagePre) {
		this.imagePre = imagePre;
	}

	/**
     * @hibernate.property
     *  column="graphicstyleindent"
     *  type="int"
     */
	public Integer getGraphicStyleIndent() {
		return graphicStyleIndent;
	}

	public void setGraphicStyleIndent(Integer graphicStyleIndent) {
		this.graphicStyleIndent = graphicStyleIndent;
	}

	/**
     * @hibernate.property
     *  column="textstyleindent"
     *  type="int"
     */
	public Integer getTextStyleIndent() {
		return textStyleIndent;
	}

	public void setTextStyleIndent(Integer textStyleIndent) {
		this.textStyleIndent = textStyleIndent;
	}

	/**
     * @hibernate.property
     *  column="textspanstyleindent"
     *  type="int"
     */
	public Integer getTextSpanStyleIndent() {
		return textSpanStyleIndent;
	}

	public void setTextSpanStyleIndent(Integer textSpanStyleIndent) {
		this.textSpanStyleIndent = textSpanStyleIndent;
	}

	/**
     * @hibernate.property
     *  column="footery"
     *  type="double"
     */
	public double getFootery() {
		return footery;
	}
	public void setFootery(double footery) {
		this.footery = footery;
	}
	
}
