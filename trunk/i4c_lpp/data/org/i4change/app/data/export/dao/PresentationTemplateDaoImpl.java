package org.i4change.app.data.export.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.i4change.app.hibernate.beans.export.PresentationTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class PresentationTemplateDaoImpl extends HibernateDaoSupport {

	private static final Log log = LogFactory.getLog(PresentationTemplateDaoImpl.class);	
	
//	public void addPresentationTemplate(String templateKey, String templateName, 
//			String preview, boolean isPublic, Long organisationId, 
//			double imageWidth, double imageHeight, double imageX, double imageY, 
//			String head, String middle, String foot, String imagePre, 
//			int graphicIndent, int textIndent, int textSpanIndent) {
//		
//	}
//	
//	
//	public Long addPresentationTemplate(String templateKey, String templateName, 
//			String preview, Boolean isPublic, Long organisationId, 
//			double imageWidth, double imageHeight, double imageX, double imageY,
//			String head, String middle, String foot, String imagePre, 
//			int graphicIndent, int textIndent, int textSpanIndent) {
		
	public Long addPresentationTemplate(String templateKey, String templateName, 
			String preview, boolean isPublic, Long organisationId, 
			double imageWidth, double imageHeight, double imageX, double imageY, 
			String head, String middle, String foot, String imagePre, 
			int graphicIndent, int textIndent, int textSpanIndent, double footery) {		
		try {
			
			PresentationTemplate presentationTemplate = new PresentationTemplate();
			presentationTemplate.setDeleted("false");
			presentationTemplate.setInserted(new Date());
			presentationTemplate.setTemplateKey(templateKey);
			presentationTemplate.setTemplateName(templateName);
			presentationTemplate.setPreview(preview);
			presentationTemplate.setIsPublic(isPublic);
			presentationTemplate.setOrganisationId(organisationId);
			presentationTemplate.setImageWidth(imageWidth);
			presentationTemplate.setImageHeight(imageHeight);
			presentationTemplate.setImageX(imageX);
			presentationTemplate.setImageY(imageY);
			presentationTemplate.setHeadSlide(head);
			presentationTemplate.setMidSlide(middle);
			presentationTemplate.setFootSlide(foot);
			presentationTemplate.setImagePre(imagePre);
			presentationTemplate.setGraphicStyleIndent(graphicIndent);
			presentationTemplate.setTextStyleIndent(textIndent);
			presentationTemplate.setTextSpanStyleIndent(textSpanIndent);
			presentationTemplate.setFootery(footery);
			
			Long presentationTemplateId = (Long) getHibernateTemplate().save(presentationTemplate);

			return presentationTemplateId;
		} catch (HibernateException ex) {
			log.error("[addPresentationTemplate]",ex);
		} catch (Exception ex2) {
			log.error("[addPresentationTemplate]",ex2);
		}
		return null;
	}

	public PresentationTemplate getPresentationTemplateById(Long presentationTemplateId) {
		try {
			
			String hql = "SELECT c FROM PresentationTemplate c " +
						"WHERE c.presentationTemplateId=:presentationTemplateId";
	
			Query query = getSession().createQuery(hql);
			query.setLong("presentationTemplateId", presentationTemplateId);
		
			PresentationTemplate ptemplate = (PresentationTemplate) query.uniqueResult();
		
			return ptemplate;
		} catch (HibernateException ex) {
			log.error("[getPresentationTemplateById]",ex);
		} catch (Exception ex2) {
			log.error("[getPresentationTemplateById]",ex2);
		}
		return null;
	}
	
	public List<PresentationTemplate> getPresentationTemplates(Long organisationId) {
		try {
			
			String hql = "SELECT c FROM PresentationTemplate c " +
						"WHERE c.isPublic=:isPublic " +
						"OR c.organisationId!=:organisationId";
	
			Query query = getSession().createQuery(hql);
			query.setBoolean("isPublic", true);
			query.setLong("organisationId", organisationId);
		
			List<PresentationTemplate> ptemplates = query.list();
			
			return ptemplates;
		} catch (HibernateException ex) {
			log.error("[getPresentationTemplates]",ex);
		} catch (Exception ex2) {
			log.error("[getPresentationTemplates]",ex2);
		}
		return null;
	}

}
