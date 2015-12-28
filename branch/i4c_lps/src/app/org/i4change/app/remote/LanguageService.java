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
public class LanguageService {
	
	private static final Log log = LogFactory.getLog(LanguageService.class);
	
	/**
	 * get a List of all availible Languages
	 * @return
	 */
	public List getLanguages(){
		return Languagemanagement.getInstance().getLanguages();
	}
	
	/**
	 * get all fields of a given Language_id
	 * @param language_id
	 * @deprecated
	 * @return
	 */
	public List<Fieldlanguagesvalues> getLanguageById(Long language_id){
		return Fieldmanagment.getInstance().getAllFieldsByLanguage(language_id);
	}
	
	public Integer getDefaultLanguage() {
		return Integer.valueOf(Configurationmanagement.getInstance().
				getConfKey(3,"default_lang_id").getConf_value()).intValue();
	}
	
	
	/**
	 * get all fields of a given Language_id by params
	 * @param language_id
	 * @return
	 */
	public List<Fieldlanguagesvalues> getLanguageByIdAndMax(Long language_id, int start, int max){
		return Fieldmanagment.getInstance().getAllFieldsByLanguage(language_id,start,max);
	}
	
	public List<Fieldlanguagesvalues> getLanguageByIdAndMaxLabeled(Long language_id, int start, int max, boolean isLabeled){
		if (isLabeled) {
			return Fieldmanagment.getInstance().getAllFieldsByLanguage(language_id,start,max);
			//return Fieldmanagment.getInstance().getAllFieldsByLanguageLabeled(language_id,start,max);
		} else {
			return Fieldmanagment.getInstance().getAllFieldsByLanguage(language_id,start,max);
		}
	}
	
	public Fieldvalues getFieldvalueById(String SID, Long fieldvalues_id, Long language_id) {
		try {
			log.debug("getFieldvalueById: "+SID+" "+fieldvalues_id+" "+language_id);
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkAdminLevel(user_level)) {
	        	return Fieldmanagment.getInstance().getFieldvaluesByFieldid(fieldvalues_id, language_id);
	        }
		} catch (Exception err) {
			log.error("[getFieldvalueById] ",err);
		}
        return null;
	}
	
	public Long addLanguage(String SID, String langName) {
        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
        if (AuthLevelmanagement.getInstance().checkAdminLevel(user_level)) {
        	if (langName.length()==0) return new Long(-30);
        	return Languagemanagement.getInstance().addLanguage(langName);
        }
        return null;
	}
	
	public Long updateLanguage(String SID, Long language_id, String langName) {
        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
        if (AuthLevelmanagement.getInstance().checkAdminLevel(user_level)) {
        	if (langName.length()==0) return new Long(-30);
        	return Languagemanagement.getInstance().updateFieldLanguage(language_id, langName, "false");
        }
        return null;
	}
	
	public Long deleteLanguage(String SID, Long language_id) {
        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
        if (AuthLevelmanagement.getInstance().checkAdminLevel(user_level)) {
        	return Languagemanagement.getInstance().updateFieldLanguage(language_id, "", "true");
        }
        return null;
	}
	
	public Long deleteFieldlanguagesvaluesById(String SID, Long fieldlanguagesvalues_id) {
        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
        if (AuthLevelmanagement.getInstance().checkAdminLevel(user_level)){
        	return Fieldmanagment.getInstance().deleteFieldlanguagesvaluesById(fieldlanguagesvalues_id);
        }
        return null;
	}
	
	/**
	 * 
	 * @param SID
	 * @param start
	 * @param max
	 * @param orderby
	 * @param asc
	 * @param language_id
	 * @return
	 */
	public SearchResult getFieldsByLanguage(String SID, int start, int max, String orderby, boolean asc, 
			Long language_id, String search, String searchType){
        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
        if (AuthLevelmanagement.getInstance().checkAdminLevel(user_level)){
        	
        	log.debug("getFieldsByLanguage "+search+" "+searchType);
        	
        	if (search.length() == 0) {
        		return Fieldmanagment.getInstance().getFieldsByLanguage(start, max, orderby, asc, language_id);
        	} else {
        		return Fieldmanagment.getInstance().getFieldsByLanguageBySearch(start, max, orderby, asc, language_id, search, searchType);
        	}
        }
		return null;
	}
	
	/**
	 * 
	 * @param SID
	 * @param values
	 * @return
	 */
	public Long saveOrUpdateLabel(String SID, Map values)  {
		try {
			log.debug("saveOrUpdateLabel: "+values);
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
			Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
			String name = values.get("name").toString(); 
			Long fieldlanguagesvalues_id = Long.valueOf(values.get("fieldlanguagesvalues_id").toString()).longValue();
			Long language_id = Long.valueOf(values.get("language_id").toString()).longValue();
			Long fieldvalues_id = Long.valueOf(values.get("fieldvalues_id").toString()).longValue();
			Long label_number = Long.valueOf(values.get("label_number").toString()).longValue();
			String value = values.get("value").toString(); 
			if (AuthLevelmanagement.getInstance().checkAdminLevel(user_level)){
				if (fieldvalues_id>0 && fieldlanguagesvalues_id>0){
					log.error("UPDATE LABEL"+fieldvalues_id+", "+label_number+", "+
							name+", "+fieldlanguagesvalues_id+", "+value);
					return Fieldmanagment.getInstance().updateLabel(fieldvalues_id, label_number, 
							name, fieldlanguagesvalues_id, value);
				} else if (fieldvalues_id>0 && fieldlanguagesvalues_id==0) {
					log.error("INSERT NEW LABEL"+fieldvalues_id+", "+label_number+", "+
							name+", "+value+", "+language_id);
					return Fieldmanagment.getInstance().addAndUpdateLabel(fieldvalues_id, label_number, 
							name, value, language_id);
				} else {
					log.error("INSERT NEW FIELD AND LABEL"+fieldvalues_id+", "+label_number+", "+
							name+", "+value+", "+language_id);
					return Fieldmanagment.getInstance().addFieldAndLabel(name, label_number, value, language_id);
				}
			}
			return new Long(-26);	
		} catch (Exception e) {
			log.error("[saveOrUpdateLabel]",e);
		}
		return new Long(-1);	
	}

	/**
	 * @param language_id
	 * @param labels
	 * @param isLabeled
	 * @return
	 */
	public List<Fieldlanguagesvalues> getLanguageByIdAndVectorLabeled(
			Long language_id, Vector labels, Boolean isLabeled) {
			
		List<Fieldlanguagesvalues> fList = new LinkedList<Fieldlanguagesvalues>();
		
		for (Iterator iter = labels.iterator();iter.hasNext();) {
			Object keyItem = iter.next();
			log.debug("keyItem: "+keyItem);
			Long labelid = Long.valueOf(keyItem.toString()).longValue();
			fList.add(Fieldmanagment.getInstance().getFieldByLanguage(language_id,labelid));
		}
			
		return fList;
	}	

}
