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
import org.i4change.app.hibernate.ErrorValues;
import org.i4change.app.hibernate.Fieldlanguagesvalues;
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
        //long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);

	        if (errorid<0){
	//        	log.debug("errorid,language_id: "+errorid+"|"+language_id);
	        	ErrorValues eValues = ErrorManagement.getInstance().getErrorValuesById(errorid*(-1));
		        if (eValues!=null){
	//	        	log.debug(eValues.getFieldvalues());
	//	        	log.debug(eValues.getFieldvalues().getFieldvalues_id());
	//	        	log.debug(eValues.getErrortypes());
	//	        	log.debug(eValues.getErrortypes().getErrortypes_id());
	//	        	log.debug(eValues.getErrortypes().getFieldvalues());
	//	        	log.debug(eValues.getErrortypes().getFieldvalues().getFieldvalues_id());
		        	Fieldlanguagesvalues errorValue = Fieldmanagment.getInstance().getFieldByLabelNumberAndLanguage(eValues.getLabel_number(),language_id);
		        	Fieldlanguagesvalues typeValue = Fieldmanagment.getInstance().getFieldByLabelNumberAndLanguage(eValues.getErrortypes().getLabel_number(),language_id);
		        	if (errorValue!=null) {
		        		return new ErrorResult(errorid,errorValue.getValue(),typeValue.getValue());
		        	}
	        	}
	        } else {
	        	return new ErrorResult(errorid,"Error ... please check your input","Error");
	        }
		} catch (Exception err){
			log.error("[getErrorByCode]",err);
		}
        return null;
	}
	

}
