package org.i4change.app.http.javarpc;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.hibernate.beans.lang.Fieldlanguagesvalues;
import org.i4change.app.hibernate.beans.lang.Fieldvalues;
import org.i4change.app.remote.ILanguageService;
import org.i4change.app.remote.LanguageService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 
 * @author sebastianwagner
 *
 */
public class LanguageServiceRPC extends BaseAdapterRPC {
	
	private static final Log log = LogFactory.getLog(LanguageServiceRPC.class);
	
	public List getLanguages(){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			ILanguageService languageService = (ILanguageService) context.getBean("languageservice.service");
			
			return languageService.getLanguages();
		} catch (Exception err) {
			log.error("[getLanguages]",err);
		}
		return null;
	}
	
	public Integer getDefaultLanguage() {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			ILanguageService languageService = (ILanguageService) context.getBean("languageservice.service");
			
			return languageService.getDefaultLanguage();
		} catch (Exception err) {
			log.error("[getDefaultLanguage]",err);
		}
		return null;
	}
	
	public List<Map> getLanguageByIdAndMax(Long language_id, Integer start, Integer max){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			ILanguageService languageService = (ILanguageService) context.getBean("languageservice.service");
			
			return languageService.getLanguageByIdAndMax(language_id, start, max);
		} catch (Exception err) {
			log.error("[getLanguageByIdAndMax]",err);
		}
		return null;
	}
	
	public List<Map> getLanguageByIdAndMaxLabeled(Long language_id, Integer start, 
			Integer max, Boolean isLabeled){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			ILanguageService languageService = (ILanguageService) context.getBean("languageservice.service");
			
			return languageService.getLanguageByIdAndMaxLabeled(language_id, start, max, isLabeled);
		} catch (Exception err) {
			log.error("[getLanguageByIdAndMaxLabeled]",err);
		}
		return null;
	}
	
	public List<Fieldlanguagesvalues> getLanguageByIdAndVectorLabeled(Long language_id, Vector labels, Boolean isLabeled){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			ILanguageService languageService = (ILanguageService) context.getBean("languageservice.service");
			
			return languageService.getLanguageByIdAndVectorLabeled(language_id, labels, isLabeled);
		} catch (Exception err) {
			log.error("[getLanguageByIdAndVectorLabeled]",err);
		}
		return null;
	}
	
	public Fieldvalues getFieldvalueById(String SID, Long fieldvalues_id, Long language_id) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			ILanguageService languageService = (ILanguageService) context.getBean("languageservice.service");
			
			return languageService.getFieldvalueById(SID, fieldvalues_id, language_id);
		} catch (Exception err) {
			log.error("[getFieldvalueById]",err);
		}
		return null;
	}
	
	public Long addLanguage(String SID, String langName) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			ILanguageService languageService = (ILanguageService) context.getBean("languageservice.service");
			
			return languageService.addLanguage(SID, langName);
		} catch (Exception err) {
			log.error("[addLanguage]",err);
		}
		return null;
	}
	
	public Long updateLanguage(String SID, Long language_id, String langName) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			ILanguageService languageService = (ILanguageService) context.getBean("languageservice.service");
			
			return languageService.updateLanguage(SID, language_id, langName);
		} catch (Exception err) {
			log.error("[updateLanguage]",err);
		}
		return null;
	}
	
	public Long deleteLanguage(String SID, Long language_id) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			ILanguageService languageService = (ILanguageService) context.getBean("languageservice.service");
			
			return languageService.deleteLanguage(SID, language_id);
		} catch (Exception err) {
			log.error("[deleteLanguage]",err);
		}
		return null;
	}
	
	public Long deleteFieldlanguagesvaluesById(String SID, Long fieldlanguagesvalues_id) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			ILanguageService languageService = (ILanguageService) context.getBean("languageservice.service");
			
			return languageService.deleteFieldlanguagesvaluesById(SID, fieldlanguagesvalues_id);
		} catch (Exception err) {
			log.error("[deleteFieldlanguagesvaluesById]",err);
		}
		return null;
	}
	
	public SearchResult getFieldsByLanguage(String SID, int start, int max, String orderby, boolean asc, 
			Long language_id, String search, String searchType){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			ILanguageService languageService = (ILanguageService) context.getBean("languageservice.service");
			
			return languageService.getFieldsByLanguage(SID, start, max, orderby, asc, language_id, search, searchType);
		} catch (Exception err) {
			log.error("[getFieldsByLanguage]",err);
		}
		return null;
	}
	
	public Long saveOrUpdateLabel(String SID, Map values)  {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			ILanguageService languageService = (ILanguageService) context.getBean("languageservice.service");
			
			return languageService.saveOrUpdateLabel(SID, values);
		} catch (Exception err) {
			log.error("[saveOrUpdateLabel]",err);
		}
		return null;
	}

}
