package org.i4change.app.utils.crypt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.Configurationmanagement;

public class ManageCryptStyle {

	// Spring managed Beans
	private Configurationmanagement configurationmanagement;

	public Configurationmanagement getConfigurationmanagement() {
		return configurationmanagement;
	}

	public void setConfigurationmanagement(
			Configurationmanagement configurationmanagement) {
		this.configurationmanagement = configurationmanagement;
	}

	private static final Log log = LogFactory.getLog(ManageCryptStyle.class);

	private ManageCryptStyle() {
	}

	// private static ManageCryptStyle instance = null;
	//
	// public static synchronized ManageCryptStyle getInstance() {
	// if (instance == null) {
	// instance = new ManageCryptStyle();
	// }
	// return instance;
	// }

	public CryptStringAdapter getInstanceOfCrypt() {
		try {
			String configKeyCryptClassName = "org.i4change.app.utils.crypt.MD5Implementation";
			// String configKeyCryptClassName =
			// this.configurationmanagement.getConfKey(3,"crypt_ClassName").getConf_value();

			CryptStringAdapter t = (CryptStringAdapter) Class.forName(
					configKeyCryptClassName).newInstance();
			// t.getTime();
			return t;

		} catch (Exception err) {
			log.error("[getInstanceOfCrypt]", err);
		}
		return null;
	}

}
