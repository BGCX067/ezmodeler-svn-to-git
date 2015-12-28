package org.i4change.app.remote;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.hibernate.beans.lang.Fieldlanguagesvalues;
import org.i4change.app.hibernate.beans.lang.Fieldvalues;

public interface ILanguageService {

	/**
	 * get a List of all availible Languages
	 * @return
	 */
	public abstract List getLanguages();

	/**
	 * get all fields of a given Language_id
	 * @param language_id
	 * @deprecated
	 * @return
	 */
	public abstract List<Fieldlanguagesvalues> getLanguageById(Long language_id);

	/**
	 * get all fields of a given Language_id by params
	 * @param language_id
	 * @return
	 */
	public abstract List<Map> getLanguageByIdAndMax(Long language_id,
			int start, int max);

	public abstract List<Map> getLanguageByIdAndMaxLabeled(Long language_id,
			int start, int max, boolean isLabeled);

	public abstract Fieldvalues getFieldvalueById(String SID,
			Long fieldvalues_id, Long language_id);

	public abstract Long addLanguage(String SID, String langName);

	public abstract Long updateLanguage(String SID, Long language_id,
			String langName);

	public abstract Long deleteLanguage(String SID, Long language_id);

	public abstract Long deleteFieldlanguagesvaluesById(String SID,
			Long fieldlanguagesvalues_id);

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
	public abstract SearchResult getFieldsByLanguage(String SID, int start,
			int max, String orderby, boolean asc, Long language_id,
			String search, String searchType);

	/**
	 * 
	 * @param SID
	 * @param values
	 * @return
	 */
	public abstract Long saveOrUpdateLabel(String SID, Map values);

	/**
	 * @param language_id
	 * @param labels
	 * @param isLabeled
	 * @return
	 */
	public abstract List<Fieldlanguagesvalues> getLanguageByIdAndVectorLabeled(
			Long language_id, Vector labels, Boolean isLabeled);
	
	public Integer getDefaultLanguage();

}