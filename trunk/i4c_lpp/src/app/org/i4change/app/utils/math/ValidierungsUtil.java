package org.i4change.app.utils.math;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ValidierungsUtil {
	
	private static final Log log = LogFactory.getLog(ValidierungsUtil.class);
	
	private Map<String,String> validatorMap;
	private Map<String,Long> errorMessageMap;

	public Map<String,String> getValidatorMap() {
		return validatorMap;
	}
	public void setValidatorMap(Map<String,String> validatorMap) {
		this.validatorMap = validatorMap;
	}
	
	public Map<String, Long> getErrorMessageMap() {
		return errorMessageMap;
	}
	public void setErrorMessageMap(Map<String, Long> errorMessageMap) {
		this.errorMessageMap = errorMessageMap;
	}
	
	public Long validate (String inputKey, String input) {
		Long returnVal = 0L;
		String regExp = this.validatorMap.get(inputKey);
		
		//log.debug("log regExp "+regExp);
		
		//TODO: Fehlerbehandlung wenn für inputKey kein regExp
		if (!input.matches(regExp)) {
			returnVal = this.errorMessageMap.get(inputKey);
		}
		
		return returnVal;
	}
	
	public Long validate (Map<String,String> validationMap) {
		Long returnVal = 0L;
		
		for (Iterator<String> iter = validationMap.keySet().iterator();iter.hasNext();) {
			String inputKey = iter.next();
			String input = validationMap.get(inputKey);
			
			returnVal = this.validate(inputKey, input);
			
			if (returnVal < 0L) {
				break;
			}
			
		}
		
		return returnVal;
		
	}
	
}
