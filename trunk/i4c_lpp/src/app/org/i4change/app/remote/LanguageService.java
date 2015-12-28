package org.i4change.app.remote;

import java.util.LinkedList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.AuthLevelmanagement;
import org.i4change.app.data.basic.Configurationmanagement;
import org.i4change.app.data.basic.Languagemanagement;
import org.i4change.app.data.basic.Sessionmanagement;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.data.basic.Fieldmanagment;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.hibernate.beans.lang.Fieldlanguagesvalues;
import org.i4change.app.hibernate.beans.lang.Fieldvalues;

/**
 * 
 * @author sebastianwagner
 *
 */
public class LanguageService implements ILanguageService {
	
	private static final Log log = LogFactory.getLog(LanguageService.class);
	
	//Spring loaded Beans
	private Configurationmanagement configurationmanagement;
	private Sessionmanagement sessionmanagement;
	private UserDaoImpl userDaoImpl;
	private Fieldmanagment fieldmanagment;
	private Languagemanagement languagemanagement;
	
	public Fieldmanagment getFieldmanagment() {
		return fieldmanagment;
	}
	public void setFieldmanagment(Fieldmanagment fieldmanagment) {
		this.fieldmanagment = fieldmanagment;
	}
	public Configurationmanagement getConfigurationmanagement() {
		return configurationmanagement;
	}
	public void setConfigurationmanagement(
			Configurationmanagement configurationmanagement) {
		this.configurationmanagement = configurationmanagement;
	}
	
	public Sessionmanagement getSessionmanagement() {
		return sessionmanagement;
	}
	public void setSessionmanagement(Sessionmanagement sessionmanagement) {
		this.sessionmanagement = sessionmanagement;
	}
	
	public UserDaoImpl getUserDaoImpl() {
		return userDaoImpl;
	}
	public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}
	
	public Languagemanagement getLanguagemanagement() {
		return languagemanagement;
	}
	public void setLanguagemanagement(Languagemanagement languagemanagement) {
		this.languagemanagement = languagemanagement;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.ILanguageService#getLanguages()
	 */
	public List getLanguages(){
		return this.languagemanagement.getLanguages();
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.ILanguageService#getLanguageById(java.lang.Long)
	 */
	public List<Fieldlanguagesvalues> getLanguageById(Long language_id){
		return this.fieldmanagment.getAllFieldsByLanguage(language_id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.i4change.app.remote.ILanguageService#getDefaultLanguage()
	 */
	public Integer getDefaultLanguage() {
		return Integer.valueOf(this.configurationmanagement.
				getConfKey(3,"default_lang_id").getConf_value()).intValue();
	}
	
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.ILanguageService#getLanguageByIdAndMax(java.lang.Long, int, int)
	 */
	public List<Map> getLanguageByIdAndMax(Long language_id, int start, int max){
		return this.fieldmanagment.getAllFieldsByLanguage(language_id,start,max);
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.ILanguageService#getLanguageByIdAndMaxLabeled(java.lang.Long, int, int, boolean)
	 */
	public List<Map> getLanguageByIdAndMaxLabeled(Long language_id, int start, int max, boolean isLabeled){
		if (isLabeled) {
			return this.fieldmanagment.getAllFieldsByLanguage(language_id,start,max);
			//return this.fieldmanagment.getAllFieldsByLanguageLabeled(language_id,start,max);
		} else {
			return this.fieldmanagment.getAllFieldsByLanguage(language_id,start,max);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.ILanguageService#getFieldvalueById(java.lang.String, java.lang.Long, java.lang.Long)
	 */
	public Fieldvalues getFieldvalueById(String SID, Long fieldvalues_id, Long language_id) {
		try {
			log.debug("getFieldvalueById: "+SID+" "+fieldvalues_id+" "+language_id);
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkAdminLevel(user_level)) {
	        	return this.fieldmanagment.getFieldvaluesByFieldid(fieldvalues_id, language_id);
	        }
		} catch (Exception err) {
			log.error("[getFieldvalueById] ",err);
		}
        return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.ILanguageService#addLanguage(java.lang.String, java.lang.String)
	 */
	public Long addLanguage(String SID, String langName) {
        Long users_id = this.sessionmanagement.checkSession(SID);
        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
        if (AuthLevelmanagement.checkAdminLevel(user_level)) {
        	if (langName.length()==0) return new Long(-30);
        	return this.languagemanagement.addLanguage(langName);
        }
        return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.ILanguageService#updateLanguage(java.lang.String, java.lang.Long, java.lang.String)
	 */
	public Long updateLanguage(String SID, Long language_id, String langName) {
        Long users_id = this.sessionmanagement.checkSession(SID);
        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
        if (AuthLevelmanagement.checkAdminLevel(user_level)) {
        	if (langName.length()==0) return new Long(-30);
        	return this.languagemanagement.updateFieldLanguage(language_id, langName, "false");
        }
        return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.ILanguageService#deleteLanguage(java.lang.String, java.lang.Long)
	 */
	public Long deleteLanguage(String SID, Long language_id) {
        Long users_id = this.sessionmanagement.checkSession(SID);
        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
        if (AuthLevelmanagement.checkAdminLevel(user_level)) {
        	return this.languagemanagement.updateFieldLanguage(language_id, "", "true");
        }
        return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.ILanguageService#deleteFieldlanguagesvaluesById(java.lang.String, java.lang.Long)
	 */
	public Long deleteFieldlanguagesvaluesById(String SID, Long fieldlanguagesvalues_id) {
        Long users_id = this.sessionmanagement.checkSession(SID);
        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
        if (AuthLevelmanagement.checkAdminLevel(user_level)){
        	return this.fieldmanagment.deleteFieldlanguagesvaluesById(fieldlanguagesvalues_id);
        }
        return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.ILanguageService#getFieldsByLanguage(java.lang.String, int, int, java.lang.String, boolean, java.lang.Long, java.lang.String, java.lang.String)
	 */
	public SearchResult getFieldsByLanguage(String SID, int start, int max, String orderby, boolean asc, 
			Long language_id, String search, String searchType){
        Long users_id = this.sessionmanagement.checkSession(SID);
        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
        if (AuthLevelmanagement.checkAdminLevel(user_level)){
        	
        	log.debug("getFieldsByLanguage "+search+" "+searchType);
        	
        	if (search.length() == 0) {
        		return this.fieldmanagment.getFieldsByLanguage(start, max, orderby, asc, language_id);
        	} else {
        		return this.fieldmanagment.getFieldsByLanguageBySearch(start, max, orderby, asc, language_id, search, searchType);
        	}
        }
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.ILanguageService#saveOrUpdateLabel(java.lang.String, java.util.Map)
	 */
	public Long saveOrUpdateLabel(String SID, Map values)  {
		try {
			log.debug("saveOrUpdateLabel: "+values);
			Long users_id = this.sessionmanagement.checkSession(SID);
			Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
			String name = values.get("name").toString(); 
			Long fieldlanguagesvalues_id = Long.valueOf(values.get("fieldlanguagesvalues_id").toString()).longValue();
			Long language_id = Long.valueOf(values.get("language_id").toString()).longValue();
			Long fieldvalues_id = Long.valueOf(values.get("fieldvalues_id").toString()).longValue();
			Long label_number = Long.valueOf(values.get("label_number").toString()).longValue();
			String value = values.get("value").toString(); 
			if (AuthLevelmanagement.checkAdminLevel(user_level)){
				if (fieldvalues_id>0 && fieldlanguagesvalues_id>0){
					log.error("UPDATE LABEL"+fieldvalues_id+", "+label_number+", "+
							name+", "+fieldlanguagesvalues_id+", "+value);
					return this.fieldmanagment.updateLabel(fieldvalues_id, label_number, 
							name, fieldlanguagesvalues_id, value);
				} else if (fieldvalues_id>0 && fieldlanguagesvalues_id==0) {
					log.error("INSERT NEW LABEL"+fieldvalues_id+", "+label_number+", "+
							name+", "+value+", "+language_id);
					return this.fieldmanagment.addAndUpdateLabel(fieldvalues_id, label_number, 
							name, value, language_id);
				} else {
					log.error("INSERT NEW FIELD AND LABEL"+fieldvalues_id+", "+label_number+", "+
							name+", "+value+", "+language_id);
					return this.fieldmanagment.addFieldAndLabel(name, label_number, value, language_id);
				}
			}
			return new Long(-26);	
		} catch (Exception e) {
			log.error("[saveOrUpdateLabel]",e);
		}
		return new Long(-1);	
	}

	/* (non-Javadoc)
	 * @see org.i4change.app.remote.ILanguageService#getLanguageByIdAndVectorLabeled(java.lang.Long, java.util.Vector, java.lang.Boolean)
	 */
	public List<Fieldlanguagesvalues> getLanguageByIdAndVectorLabeled(
			Long language_id, Vector labels, Boolean isLabeled) {
			
		List<Fieldlanguagesvalues> fList = new LinkedList<Fieldlanguagesvalues>();
		
		for (Iterator iter = labels.iterator();iter.hasNext();) {
			Object keyItem = iter.next();
			log.debug("keyItem: "+keyItem);
			Long labelid = Long.valueOf(keyItem.toString()).longValue();
			fList.add(this.fieldmanagment.getFieldByLanguage(language_id,labelid));
		}
			
		return fList;
	}	

}
