package org.i4change.paypal.payment;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.paypal.sdk.profiles.APIProfile;
import com.paypal.sdk.profiles.ProfileFactory;

public class APIProfileDaoImpl{

	private static final Log log = LogFactory.getLog(APIProfileDaoImpl.class);

	private static APIProfileDaoImpl instance = null;

	private APIProfileDaoImpl() {
	}

	public static synchronized APIProfileDaoImpl getInstance() { 
		if (instance == null) {
			instance = new APIProfileDaoImpl();
		}
		return instance;
	}
	
	public APIProfile getAPIProfile() {
		try {
			
			Properties properties = new Properties();
			properties.load(this.getClass().getClassLoader()  
					.getResourceAsStream("i4change.properties"));
			
			APIProfile profile = ProfileFactory.createSignatureAPIProfile();
			profile.setAPIUsername(properties.get("i4change.paypal.username").toString());
			profile.setAPIPassword(properties.get("i4change.paypal.password").toString());
			profile.setSignature(properties.get("i4change.paypal.signature").toString());
			profile.setEnvironment(properties.get("i4change.paypal.environment").toString());
		
			return profile;
		} catch (Exception err) {
			log.error("[getAPIProfile]",err);
		}
		return null;
	}

}
