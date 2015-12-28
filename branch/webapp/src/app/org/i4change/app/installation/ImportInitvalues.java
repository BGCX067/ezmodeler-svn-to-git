package org.i4change.app.installation;

import java.util.LinkedHashMap;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.io.SAXReader;
import org.dom4j.Element;
import org.dom4j.Document;
import org.i4change.app.data.basic.Configurationmanagement;
import org.i4change.app.data.basic.ErrorManagement;
import org.i4change.app.data.basic.Fieldmanagment;
import org.i4change.app.data.basic.Languagemanagement;
import org.i4change.app.data.user.Organisationmanagement;
import org.i4change.app.data.user.Salutationmanagement;
import org.i4change.app.data.user.Statemanagement;
import org.i4change.app.data.user.Usermanagement;
import org.i4change.app.data.basic.Navimanagement;

public class ImportInitvalues {

	private static final Log log = LogFactory.getLog(ImportInitvalues.class);

	public static final String languageFolderName = "languages/";

	private static final String nameOfLanguageFile = "languages.xml";

	private static final String nameOfCountriesFile = "countries.xml";

	private static final String nameOfErrorFile = "errorvalues.xml";

	private static ImportInitvalues instance;

	private ImportInitvalues() {
	}

	public static synchronized ImportInitvalues getInstance() {
		if (instance == null) {
			instance = new ImportInitvalues();
		}
		return instance;
	}

	public void loadMainMenu() {

		Usermanagement.getInstance().addUserLevel("User", 1);
		Usermanagement.getInstance().addUserLevel("Moderator", 2);
		Usermanagement.getInstance().addUserLevel("Admin", 3);
		
		Navimanagement.getInstance().addGlobalStructure("home", 1, 124, false,
				true, 1, "home", "false");
		Navimanagement.getInstance().addMainStructure("mainDashboard", 1, 290,
				true, true, 1, "mainDashboard", 1);

		Navimanagement.getInstance().addGlobalStructure("admin", 2, 6, false,
				true, 2, "admin", "false");
		Navimanagement.getInstance().addMainStructure("userAdmin", 13, 125,
				true, false, 2, "userAdmin", 2);
		Navimanagement.getInstance().addMainStructure("orgAdmin", 14, 127,
				true, false, 3, "orgAdmin", 2);
		Navimanagement.getInstance().addMainStructure("confAdmin", 16, 263,
				true, false, 3, "confAdmin", 2);
		Navimanagement.getInstance().addMainStructure("languagesEditor", 17,
				348, true, false, 3, "languagesEditor", 2);
		Navimanagement.getInstance().addMainStructure("backupContent", 18, 367,
				true, false, 3, "backupContent", 2);		

		ErrorManagement.getInstance().addErrortypes(new Long(1), new Long(322));
		ErrorManagement.getInstance().addErrortypes(new Long(2), new Long(323));

	}

	public void loadErrorMappingsFromXML(String filePath) throws Exception {

		SAXReader reader = new SAXReader();
		Document document = reader.read(filePath
				+ ImportInitvalues.nameOfErrorFile);

		Element root = document.getRootElement();

		for (Iterator it = root.elementIterator("row"); it.hasNext();) {

			Element row = (Element) it.next();

			Long errorvalues_id = null;
			Long fieldvalues_id = null;
			Long Errortypes_id = null;

			for (Iterator itSub = row.elementIterator("field"); itSub.hasNext();) {

				Element field = (Element) itSub.next();

				String name = field.attributeValue("name");
				String text = field.getText();
				//System.out.println("NAME | TEXT "+name+" | "+text);
				if (name.equals("errorvalues_id"))
					errorvalues_id = Long.valueOf(text).longValue();
				if (name.equals("fieldvalues_id"))
					fieldvalues_id = Long.valueOf(text).longValue();
				if (name.equals("Errortypes_id"))
					Errortypes_id = Long.valueOf(text).longValue();
			}

			ErrorManagement.getInstance().addErrorValues(errorvalues_id,
					Errortypes_id, fieldvalues_id);
		}
		log.error("ErrorMappings ADDED");
	}

	public void loadSalutations() {

		Salutationmanagement.getInstance().addUserSalutation("Mister", 261);
		Salutationmanagement.getInstance().addUserSalutation("Miss", 262);

	}

	public void loadConfiguration(String crypt_ClassName,
			String allowfrontendRegister, String smtpServer, String smtpPort,
			String referer, String mailauthname, String mailauthpass,
			String default_lang_id, String swf_path, String im_path,
			String url_feed, String url_feed2,
			String sendEmailAtRegister, String sendEmailWithVerficationCode) {

		Configurationmanagement
				.getInstance()
				.addConfByKey(
						3,
						"crypt_ClassName",
						crypt_ClassName,
						null,
						"This Class is used for Authentification-Crypting. Be carefull what you do here! If you change it while running previous Pass of users will not be workign anymore! for more Information see http://code.google.com/p/openmeetings/wiki/CustomCryptMechanism");
		//"1"
		Configurationmanagement.getInstance().addConfByKey(3,
				"allow_frontend_register", allowfrontendRegister, null, "");

		Configurationmanagement.getInstance().addConfByKey(3,
				"default_group_id", "1", null, "");

		//this domain_id is the Organisation of users who register through the frontend
		Configurationmanagement.getInstance().addConfByKey(3,
				"default_domain_id", "1", null, "");

		//"smtp.xmlcrm.org"
		Configurationmanagement.getInstance().addConfByKey(3, "smtp_server",
				smtpServer, null, "this is the smtp server to send messages");
		//25
		Configurationmanagement.getInstance().addConfByKey(3, "smtp_port",
				smtpPort, null, "this is the smtp server port normally 25");
		//"openmeetings@xmlcrm.org"
		Configurationmanagement.getInstance().addConfByKey(3,
				"system_email_addr", referer, null,
				"all send EMails by the system will have this address");
		//"openmeetings@xmlcrm.org"
		Configurationmanagement.getInstance().addConfByKey(3, "email_username",
				mailauthname, null, "System auth email username");
		//
		Configurationmanagement.getInstance().addConfByKey(3, "email_userpass",
				mailauthpass, null, "System auth email password");
		//"EN"
		Configurationmanagement.getInstance().addConfByKey(3, "default_lang_id",
				default_lang_id, null, "Default System Language ID see language.xml");

		Configurationmanagement.getInstance().addConfByKey(3, "swftools_path",
				swf_path, null, "Path To SWF-Tools");

		Configurationmanagement.getInstance().addConfByKey(3,
				"imagemagick_path", im_path, null, "Path to ImageMagick tools");

		Configurationmanagement.getInstance().addConfByKey(3, "rss_feed1",
				url_feed, null, "Feed URL");

		Configurationmanagement.getInstance().addConfByKey(3, "rss_feed2",
				url_feed2, null, "Feed URL 2");
		
		Configurationmanagement.getInstance().addConfByKey(3, "sendEmailAtRegister",
				sendEmailAtRegister, null, "User get a EMail with their Account data. Values: 0(No) or 1(Yes)");
		
		Configurationmanagement.getInstance().addConfByKey(3, "sendEmailWithVerficationCode",
				sendEmailWithVerficationCode, null, "User must activate their account by clicking on the " +
						"activation-link in the registering Email. Values: 0(No) or 1(Yes) " +
						"It makes no sense to make this(sendEmailWithVerficationCode) 1(Yes) while " +
						"sendEmailAtRegister is 0(No) cause you need" +
						"to send a EMail.");
	}

	public void loadDefaultRooms() {

	
	}

	public void loadInitUserAndOrganisation(String username, String userpass,
			String email, String defaultOrganisationName) {
		//Add user
		try {
			Long user_id = Usermanagement.getInstance().registerUserInit(
					new Long(3), 3, 1, 1, username, userpass, "lastname",
					"firstname", email, new java.util.Date(), "street", "no",
					"fax", "zip", 1, "town", 0, false, null);

			//Add default group
			Long organisation_id = Organisationmanagement.getInstance()
					.addOrganisation(defaultOrganisationName, user_id);

			//Add user to default group
			Organisationmanagement.getInstance().addUserToOrganisation(
					new Long(3), new Long(1), organisation_id, null, "");
		} catch (Exception e) {
			log.error("[loadInitUserAndOrganisation] ", e);
		}
	}

	/**
	 * import all language Names from the xml file
	 * @param filePath
	 * @throws Exception
	 */
	private void loadCountriesFiles(String filePath) throws Exception {

		SAXReader reader = new SAXReader();
		Document document = reader.read(filePath
				+ ImportInitvalues.nameOfCountriesFile);

		Element root = document.getRootElement();

		for (Iterator it = root.elementIterator("country"); it.hasNext();) {

			Element item = (Element) it.next();
			String country = item.attributeValue("name");

			Statemanagement.getInstance().addState(country);

		}
	}

	/**
	 * load all availible languages File names and language name's from the config file
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public LinkedHashMap<Integer,String> getLanguageFiles(String filePath) throws Exception {

		LinkedHashMap<Integer,String> languages = new LinkedHashMap<Integer,String>();

		SAXReader reader = new SAXReader();
		Document document = reader.read(filePath
				+ ImportInitvalues.nameOfLanguageFile);

		Element root = document.getRootElement();

		for (Iterator<Element> it = root.elementIterator("lang"); it.hasNext();) {

			Element item = it.next();
			String country = item.getText();
			Integer id = Integer.valueOf(item.attribute("id").getValue()).intValue();

			//log.error("getLanguageFiles "+country);
			languages.put(id,country);

		}
		log.debug("Countries ADDED ");
		return languages;

	}

	public void loadInitLanguages(String filePath) throws Exception {

		this.loadCountriesFiles(filePath);

		//String listLanguages[] = {"deutsch", "english", "french", "spanish"};

		LinkedHashMap<Integer,String> listlanguages = this.getLanguageFiles(filePath);

		// TODO empty tables before launch
		//Languagemanagement.getInstance().emptyFieldlanguage();
		
		
		boolean langFieldIdIsInited = false;

		/** Read all languages files */
		for (Iterator<Integer> itLang = listlanguages.keySet().iterator(); itLang
				.hasNext();) {
			Integer langId = itLang.next();
			String lang = listlanguages.get(langId);
			log.debug("loadInitLanguages lang: " + lang);

			Long languages_id = Languagemanagement.getInstance().addLanguage(lang);

			SAXReader reader = new SAXReader();
			Document document = reader.read(filePath + lang + ".xml");

			Element root = document.getRootElement();

			for (Iterator it = root.elementIterator("string"); it.hasNext();) {
				Element item = (Element) it.next();
				//log.error(item.getName());

				Long id = Long.valueOf(item.attributeValue("id")).longValue();
				String name = item.attributeValue("name");
				String value = "";

				for (Iterator t2 = item.elementIterator("value"); t2.hasNext();) {
					Element val = (Element) t2.next();
					value = val.getText();
				}

				//log.error("result: "+langFieldIdIsInited+" "+id+" "+name+" "+value);
				
				//if (this.checkForLangIds(id)){
					//Only do that for the first field-set
					if (!langFieldIdIsInited) {
						Fieldmanagment.getInstance().addFieldByLabelNumber(name,id);
					}
					
					Fieldmanagment.getInstance().addFieldValueByLabeldNumberAndLanguage(
							id, languages_id, value);
				//}
			}
			log.debug("Lang ADDED: " + lang);
			if (!langFieldIdIsInited)
				langFieldIdIsInited = true;
		}

	}

	/**
	 * only import the initial Setup
	 * @param id
	 * @return
	 */
	private boolean checkForLangIds(Long id){
		try {
			
			int[] langIds = {5, 6, 103, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 127, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 146, 147, 148, 149, 155, 156, 157, 158, 159, 160, 161, 162, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 178, 180, 181, 182, 183, 184, 185, 204, 261, 262, 263, 263, 264, 265, 266, 267, 268, 269, 270, 271, 272, 273, 274, 275, 278, 279, 280, 281, 284, 288, 290, 310, 311, 312, 313, 314, 315, 316, 317, 318, 319, 320, 321, 322, 323, 324, 330, 331, 332, 333, 334, 335, 336, 337, 338, 339, 340, 341, 342, 343, 344, 345, 346, 347, 348, 349, 350, 351, 352, 353, 354, 355, 356, 357, 358, 360, 361, 362, 363, 367, 368, 369, 370, 371, 374, 375, 376, 377, 379, 380, 381, 382, 383, 384, 385, 386, 387, 388, 389, 430, 506, 507, 508, 509, 510, 511, 513, 514, 515, 516, 519, 533, 534, 535, 538};
			
			for (int i=0;i<langIds.length;i++){
				if (langIds[i]==id){
					return true;
				}
			}
		} catch (Exception err) {
			log.error("",err);
		}
		return false;
	}
}
