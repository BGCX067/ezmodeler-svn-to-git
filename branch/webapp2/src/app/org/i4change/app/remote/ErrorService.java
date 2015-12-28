package org.i4change.app.remote;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


import org.i4change.app.data.basic.Fieldmanagment;
import org.i4change.app.data.basic.beans.ErrorResult;
import org.i4change.app.data.basic.ErrorManagement;
import org.i4change.app.hibernate.beans.basic.ErrorValues;
import org.i4change.app.hibernate.beans.lang.Fieldlanguagesvalues;
import org.i4change.app.hibernate.utils.HibernateUtil;

/**
 * 
 * @author swagner
 *
 */
public class ErrorService {
	
	private static final Log log = LogFactory.getLog(MainService.class);
	
	/**
	 * Gets an Error-Object by its id
	 * TODO: add error-code-handlers
	 * -20 duplicate FileName
	 * -21 FileName too short (length = 0)
	 * and make the persistent in the DataBase
	 * @param SID
	 * @param errorid
	 * @return
	 */
	public ErrorResult getErrorByCode(String SID, Long errorid, Long language_id){
		try {
        //Long users_id = Sessionmanagement.getInstance().checkSession(SID);
        //long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);

	        if (errorid<0){
	//        	log.debug("errorid,language_id: "+errorid+"|"+language_id);
	        	ErrorValues eValues = ErrorManagement.getInstance().getErrorValuesById(errorid*(-1));
	        	log.debug("eValues: "+eValues.getLabel_number()+" lang: "+language_id);
	        	
		        if (eValues!=null){
	//	        	log.debug(eValues.getFieldvalues());
	//	        	log.debug(eValues.getFieldvalues().getFieldvalues_id());
	//	        	log.debug(eValues.getErrorType());
	//	        	log.debug(eValues.getErrorType().getErrortype_id());
	//	        	log.debug(eValues.getErrorType().getFieldvalues());
	//	        	log.debug(eValues.getErrorType().getFieldvalues().getFieldvalues_id());
		        	Fieldlanguagesvalues errorValue = Fieldmanagment.getInstance().getFieldByLabelNumberAndLanguage(eValues.getLabel_number(),language_id);
		        	Fieldlanguagesvalues typeValue = Fieldmanagment.getInstance().getFieldByLabelNumberAndLanguage(eValues.getErrorType().getLabel_number(),language_id);
		        	
		        	log.debug("errorValue: "+errorValue);
		        	if (errorValue!=null) {
		        		return new ErrorResult(errorValue.getValue(),errorid,eValues.getErrorType().getLabel_number(),eValues.getLabel_number(),typeValue.getValue());
		        	}
	        	}
	        } else {
	        	return new ErrorResult("Error ... please check your input",errorid,-1L,-1L,"Error");
	        }
		} catch (Exception err){
			log.error("[getErrorByCode]",err);
		}
        return null;
	}
	

}
