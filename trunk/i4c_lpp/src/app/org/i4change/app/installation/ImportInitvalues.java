package org.i4change.app.installation;

import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.i4change.app.data.basic.Configurationmanagement;
import org.i4change.app.data.basic.ErrorManagement;
import org.i4change.app.data.basic.Fieldmanagment;
import org.i4change.app.data.basic.Languagemanagement;
import org.i4change.app.data.basic.Navimanagement;
import org.i4change.app.data.diagram.DiagramTypeDaoImpl;
import org.i4change.app.data.diagram.ObjectTypeDaoImpl;
import org.i4change.app.data.diagram.PropertyValidationTypeDaoImpl;
import org.i4change.app.data.domain.daos.OrganisationDaoImpl;
import org.i4change.app.data.help.HelpTopicServiceDaoImpl;
import org.i4change.app.data.report.daos.ReportTypesDaoImpl;
import org.i4change.app.data.user.CountryDaoImpl;
import org.i4change.app.data.user.Salutationmanagement;
import org.i4change.app.data.user.Usermanagement;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.data.website.daos.WebItemDaoImpl;
import org.i4change.app.data.website.daos.WebItemTypeDaoImpl;

public class ImportInitvalues implements IImportInitvalues {

	private static final Log log = LogFactory.getLog(ImportInitvalues.class);

	private static final String nameOfLanguageFile = "languages.xml";

	private static final String nameOfCountriesFile = "countries.xml";

	private static final String nameOfErrorFile = "errorvalues.xml";

	// Spring loaded Beans
	private Fieldmanagment fieldmanagment;
	private UserDaoImpl userDaoImpl;
	private Navimanagement navimanagement;
	private HelpTopicServiceDaoImpl helpTopicServiceDaoImpl;
	private Languagemanagement languagemanagement;
	private Salutationmanagement salutationmanagement;
	private ErrorManagement errorManagement;
	private Usermanagement usermanagement;
	private Configurationmanagement configurationmanagement;
	private DiagramTypeDaoImpl diagramTypeDaoImpl;
	private WebItemTypeDaoImpl webItemTypeDaoImpl;
	private PropertyValidationTypeDaoImpl propertyValidationTypeDaoImpl;
	private ReportTypesDaoImpl reportTypesDaoImpl;
	private ObjectTypeDaoImpl objectTypeDaoImpl;
	private OrganisationDaoImpl organisationDaoImpl;
	private CountryDaoImpl countryDaoImpl;
	private WebItemDaoImpl webItemDaoImpl;

	// private static ImportInitvalues instance;
	//
	// private ImportInitvalues() {
	// }
	//
	// public static synchronized ImportInitvalues getInstance() {
	// if (instance == null) {
	// instance = new ImportInitvalues();
	// }
	// return instance;
	// }

	public Fieldmanagment getFieldmanagment() {
		return fieldmanagment;
	}

	public void setFieldmanagment(Fieldmanagment fieldmanagment) {
		this.fieldmanagment = fieldmanagment;
	}

	public UserDaoImpl getUserDaoImpl() {
		return userDaoImpl;
	}

	public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}

	public Navimanagement getNavimanagement() {
		return navimanagement;
	}

	public void setNavimanagement(Navimanagement navimanagement) {
		this.navimanagement = navimanagement;
	}

	public HelpTopicServiceDaoImpl getHelpTopicServiceDaoImpl() {
		return helpTopicServiceDaoImpl;
	}

	public void setHelpTopicServiceDaoImpl(
			HelpTopicServiceDaoImpl helpTopicServiceDaoImpl) {
		this.helpTopicServiceDaoImpl = helpTopicServiceDaoImpl;
	}

	public Languagemanagement getLanguagemanagement() {
		return languagemanagement;
	}

	public void setLanguagemanagement(Languagemanagement languagemanagement) {
		this.languagemanagement = languagemanagement;
	}

	public Salutationmanagement getSalutationmanagement() {
		return salutationmanagement;
	}

	public void setSalutationmanagement(
			Salutationmanagement salutationmanagement) {
		this.salutationmanagement = salutationmanagement;
	}

	public ErrorManagement getErrorManagement() {
		return errorManagement;
	}

	public void setErrorManagement(ErrorManagement errorManagement) {
		this.errorManagement = errorManagement;
	}

	public Usermanagement getUsermanagement() {
		return usermanagement;
	}

	public void setUsermanagement(Usermanagement usermanagement) {
		this.usermanagement = usermanagement;
	}

	public Configurationmanagement getConfigurationmanagement() {
		return configurationmanagement;
	}

	public void setConfigurationmanagement(
			Configurationmanagement configurationmanagement) {
		this.configurationmanagement = configurationmanagement;
	}

	public DiagramTypeDaoImpl getDiagramTypeDaoImpl() {
		return diagramTypeDaoImpl;
	}

	public void setDiagramTypeDaoImpl(DiagramTypeDaoImpl diagramTypeDaoImpl) {
		this.diagramTypeDaoImpl = diagramTypeDaoImpl;
	}

	public WebItemTypeDaoImpl getWebItemTypeDaoImpl() {
		return webItemTypeDaoImpl;
	}

	public void setWebItemTypeDaoImpl(WebItemTypeDaoImpl webItemTypeDaoImpl) {
		this.webItemTypeDaoImpl = webItemTypeDaoImpl;
	}

	public PropertyValidationTypeDaoImpl getPropertyValidationTypeDaoImpl() {
		return propertyValidationTypeDaoImpl;
	}

	public void setPropertyValidationTypeDaoImpl(
			PropertyValidationTypeDaoImpl propertyValidationTypeDaoImpl) {
		this.propertyValidationTypeDaoImpl = propertyValidationTypeDaoImpl;
	}

	public ReportTypesDaoImpl getReportTypesDaoImpl() {
		return reportTypesDaoImpl;
	}

	public void setReportTypesDaoImpl(ReportTypesDaoImpl reportTypesDaoImpl) {
		this.reportTypesDaoImpl = reportTypesDaoImpl;
	}

	public ObjectTypeDaoImpl getObjectTypeDaoImpl() {
		return objectTypeDaoImpl;
	}

	public void setObjectTypeDaoImpl(ObjectTypeDaoImpl objectTypeDaoImpl) {
		this.objectTypeDaoImpl = objectTypeDaoImpl;
	}

	public OrganisationDaoImpl getOrganisationDaoImpl() {
		return organisationDaoImpl;
	}

	public void setOrganisationDaoImpl(OrganisationDaoImpl organisationDaoImpl) {
		this.organisationDaoImpl = organisationDaoImpl;
	}

	public CountryDaoImpl getCountryDaoImpl() {
		return countryDaoImpl;
	}

	public void setCountryDaoImpl(CountryDaoImpl countryDaoImpl) {
		this.countryDaoImpl = countryDaoImpl;
	}

	public WebItemDaoImpl getWebItemDaoImpl() {
		return webItemDaoImpl;
	}

	public void setWebItemDaoImpl(WebItemDaoImpl webItemDaoImpl) {
		this.webItemDaoImpl = webItemDaoImpl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.i4change.app.installation.IImportInitvalues#loadMainMenu()
	 */
	public void loadMainMenu() {

		this.userDaoImpl.addUserLevel("User", 1);
		this.userDaoImpl.addUserLevel("Moderator", 2);
		this.userDaoImpl.addUserLevel("Admin", 3);

		Long global_id = this.navimanagement.addGlobalStructure("home", 1, 124,
				false, true, 1, "home");
		this.navimanagement.addMainStructure("mainDashboard", 1, 290, true,
				true, 1, "mainDashboard", global_id);

		Long global_id_mod = this.navimanagement.addGlobalStructure("mod", 2,
				689, false, true, 2, "admin");
		this.navimanagement.addMainStructure("modRoles", 1, 690, true, false,
				2, "modRoles", global_id_mod);
		this.navimanagement.addMainStructure("modRoles", 2, 691, true, false,
				2, "modStatistics", global_id_mod);

		Long global_id_admin = this.navimanagement.addGlobalStructure("admin",
				3, 6, false, true, 3, "admin");
		this.navimanagement.addMainStructure("userAdmin", 13, 125, true, false,
				3, "userAdmin", global_id_admin);
		this.navimanagement.addMainStructure("orgAdmin", 14, 127, true, false,
				3, "orgAdmin", global_id_admin);
		this.navimanagement.addMainStructure("confAdmin", 16, 263, true, false,
				3, "confAdmin", global_id_admin);
		this.navimanagement.addMainStructure("languagesEditor", 17, 348, true,
				false, 3, "languagesEditor", global_id_admin);
		this.navimanagement.addMainStructure("backupContent", 18, 367, true,
				false, 3, "backupContent", global_id_admin);

		this.errorManagement.addErrorType(new Long(1), new Long(322));
		this.errorManagement.addErrorType(new Long(2), new Long(323));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.i4change.app.installation.IImportInitvalues#loadErrorMappingsFromXML
	 * (java.lang.String)
	 */
	public void loadErrorMappingsFromXML(String filePath) throws Exception {

		SAXReader reader = new SAXReader();
		Document document = reader.read(filePath
				+ ImportInitvalues.nameOfErrorFile);

		Element root = document.getRootElement();

		for (Iterator it = root.elementIterator("row"); it.hasNext();) {

			Element row = (Element) it.next();

			Long errorvalues_id = null;
			Long fieldvalues_id = null;
			Long errortype_id = null;

			for (Iterator itSub = row.elementIterator("field"); itSub.hasNext();) {

				Element field = (Element) itSub.next();

				String name = field.attributeValue("name");
				String text = field.getText();
				// System.out.println("NAME | TEXT "+name+" | "+text);
				if (name.equals("errorvalues_id"))
					errorvalues_id = Long.valueOf(text).longValue();
				if (name.equals("fieldvalues_id"))
					fieldvalues_id = Long.valueOf(text).longValue();
				if (name.equals("errortype_id"))
					errortype_id = Long.valueOf(text).longValue();
			}

			this.errorManagement.addErrorValues(errorvalues_id, errortype_id,
					fieldvalues_id);
		}
		log.error("ErrorMappings ADDED");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.i4change.app.installation.IImportInitvalues#loadSalutations()
	 */
	public void loadSalutations() {

		this.salutationmanagement.addUserSalutation("Mister", 261);
		this.salutationmanagement.addUserSalutation("Miss", 262);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.i4change.app.installation.IImportInitvalues#loadConfiguration(java
	 * .lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public void loadConfiguration(String crypt_ClassName,
			String allowfrontendRegister, String smtpServer, String smtpPort,
			String referer, String mailauthname, String mailauthpass,
			String default_lang_id, String swf_path, String im_path,
			String url_feed, String url_feed2, String sendEmailAtRegister,
			String sendEmailWithVerficationCode) {

		this.configurationmanagement
				.addConfByKey(
						3,
						"crypt_ClassName",
						crypt_ClassName,
						null,
						"This Class is used for Authentification-Crypting. Be carefull what you do here! If you change it while running previous Pass of users will not be workign anymore! for more Information see http://code.google.com/p/openmeetings/wiki/CustomCryptMechanism");
		// "1"
		this.configurationmanagement.addConfByKey(3, "allow_frontend_register",
				allowfrontendRegister, null, "");

		this.configurationmanagement.addConfByKey(3, "default_group_id", "1",
				null, "");

		// this domain_id is the Organisation of users who register through the
		// frontend
		this.configurationmanagement.addConfByKey(3, "default_domain_id", "1",
				null, "");

		// "smtp.xmlcrm.org"
		this.configurationmanagement.addConfByKey(3, "smtp_server", smtpServer,
				null, "this is the smtp server to send messages");
		// 25
		this.configurationmanagement.addConfByKey(3, "smtp_port", smtpPort,
				null, "this is the smtp server port normally 25");
		// "openmeetings@xmlcrm.org"
		this.configurationmanagement.addConfByKey(3, "system_email_addr",
				referer, null,
				"all send EMails by the system will have this address");
		// "openmeetings@xmlcrm.org"
		this.configurationmanagement.addConfByKey(3, "email_username",
				mailauthname, null, "System auth email username");
		//
		this.configurationmanagement.addConfByKey(3, "email_userpass",
				mailauthpass, null, "System auth email password");
		// "EN"
		this.configurationmanagement.addConfByKey(3, "default_lang_id",
				default_lang_id, null,
				"Default System Language ID see language.xml");

		this.configurationmanagement.addConfByKey(3, "swftools_path", swf_path,
				null, "Path To SWF-Tools");

		this.configurationmanagement.addConfByKey(3, "imagemagick_path",
				im_path, null, "Path to ImageMagick tools");

		this.configurationmanagement.addConfByKey(3, "rss_feed1", url_feed,
				null, "Feed URL");

		this.configurationmanagement.addConfByKey(3, "rss_feed2", url_feed2,
				null, "Feed URL 2");

		this.configurationmanagement
				.addConfByKey(3, "sendEmailAtRegister", sendEmailAtRegister,
						null,
						"User get a EMail with their Account data. Values: 0(No) or 1(Yes)");

		this.configurationmanagement
				.addConfByKey(
						3,
						"sendEmailWithVerficationCode",
						sendEmailWithVerficationCode,
						null,
						"User must activate their account by clicking on the "
								+ "activation-link in the registering Email. Values: 0(No) or 1(Yes) "
								+ "It makes no sense to make this(sendEmailWithVerficationCode) 1(Yes) while "
								+ "sendEmailAtRegister is 0(No) cause you need"
								+ "to send a EMail.");

		this.configurationmanagement
				.addConfByKey(3, "expireDateMonthsMax", "6", null,
						"Number of Months you can use your workdays for Pending Organizations");

		this.configurationmanagement.addConfByKey(3, "maxWorkDays", "30", null,
				"Number of workdays for Pending Organizations");

		this.configurationmanagement.addConfByKey(3, "maxUsers", "10", null,
				"Number of maxUsers for Pending Organizations");

		this.configurationmanagement
				.addConfByKey(
						3,
						"mailSpoolCheckTime",
						"10000",
						null,
						"Amount of Time in Milliseconds the Mail Spool will try to load the next Items from the Queue");

		this.configurationmanagement
				.addConfByKey(3, "mailSpoolMaxNumberOfItemsPerSpool", "5",
						null,
						"Number of mails which the Spool will check for Sending in one bunch");

		this.configurationmanagement.addConfByKey(3, "system_link_buy_license",
				"http://localhost/i4change/BuyLicense", null,
				"Link inside the Mails for Pending Organizations");

		this.configurationmanagement.addConfByKey(3,
				"system_link_unregister_from_list",
				"http://localhost:5080/i4change/Unregister", null,
				"Servlet to remove user from List of Pending Organizations");

		this.configurationmanagement.addConfByKey(3, "sessionClearCheckTime",
				"30000", null,
				"Time in MilliSeconds the Session cache will be cleared");

		this.configurationmanagement
				.addConfByKey(
						3,
						"reminderMailsPendingOrgs",
						"43200000",
						null,
						"Time in MilliSeconds the System will check for Pending Orgs to send a Reminder (86400000 == 1 Day)");

		this.configurationmanagement.addConfByKey(3,
				"defaultOrganizationPricing", "39.95", null,
				"default Price in USD for a Organization per Year + 1 account");

		this.configurationmanagement.addConfByKey(3, "defaultUserPricing",
				"39.95", null, "default Price in USD for a User per Year");

		this.configurationmanagement.addConfByKey(3, "discountNumberOfUsers1",
				"10", null, "default Pricing Barrier one");
		this.configurationmanagement.addConfByKey(3, "discountAmount1", "10",
				null, "default Discount Barrier one");

		this.configurationmanagement.addConfByKey(3, "discountNumberOfUsers2",
				"20", null, "default Pricing Barrier two");
		this.configurationmanagement.addConfByKey(3, "discountAmount2", "20",
				null, "default Discount Barrier two");

		this.configurationmanagement
				.addConfByKey(
						3,
						"maxNumberOfUser",
						"30",
						null,
						"default Discount Barrier three, values greater then that will receive a info message");

		this.configurationmanagement.addConfByKey(3, "baseUrlPaypal",
				"https://localhost:8443/paypal/", null,
				"default Port of Paypal application");

		this.configurationmanagement.addConfByKey(3, "invoiceNumber", "0",
				null, "Current Invoice Number");

		this.configurationmanagement.addConfByKey(3,
				"invoiceDescriptionSingleUser",
				"i4Change License 1 Year Single User", null,
				"invoiceDescriptionSingleUser");

		this.configurationmanagement.addConfByKey(3,
				"invoiceDescriptionMultiUser",
				"i4Change License 1 Year Multi User", null,
				"invoiceDescriptionMultiUser");

		this.configurationmanagement.addConfByKey(3, "emailFromLogin",
				"monitor@i4demo.com", null, "emailFromLogin Comment");

		this.configurationmanagement.addConfByKey(3, "emailMonitor",
				"monitor@i4ok.com", null, "emailMonitor Comment");

		// 86400000 == 1 Day

		this.diagramTypeDaoImpl.addDiagramType(new Long(593), "Process-Tree");// Process-Tree
		this.diagramTypeDaoImpl.addDiagramType(new Long(594), "Flow");// Flow
		this.diagramTypeDaoImpl.addDiagramType(new Long(595), "Organization");// Organization
		this.diagramTypeDaoImpl.addDiagramType(new Long(726), "MyRole");// MyRole

		// Types for Kaleidoscope
		this.webItemTypeDaoImpl.addWebItemType("Text", 1208L);
		this.webItemTypeDaoImpl.addWebItemType("Image", 1209L);
		this.webItemTypeDaoImpl.addWebItemType("Video", 1210L);
		this.webItemTypeDaoImpl.addWebItemType("UserDefinied", 1346L);

		// Add default Property Validation Types
		this.propertyValidationTypeDaoImpl.addDefaultPropertyValidationTypes();

		// Add default Report-Types
		this.reportTypesDaoImpl.addReportType("overview");

		this.objectTypeDaoImpl.addDefaultObjectTypes();

		this.addDefaultWebItems();

	}

	private void addDefaultWebItems() {
		try {

			this.webItemDaoImpl.addWebItem("ezModeler", "", "", 4L, 7, true,
					"", "feedbackPod", false, true, "bottomLeftSquare", false,
					1);

			this.webItemDaoImpl.addWebItem("Sign Up", "", "", 4L, 3, true, "",
					"registrationPod", false, true, "", false, 1);

			this.webItemDaoImpl
					.addWebItem(
							"Welcome to ezModeler",
							"",
							"<P ALIGN=\"LEFT\"><FONT FACE=\"Verdana\" SIZE=\"11\" COLOR=\"#000000\" LETTERSPACING=\"0\" KERNING=\"0\">Welcome to ezModeler,</FONT></P><P ALIGN=\"LEFT\"><FONT FACE=\"Verdana\" SIZE=\"11\" COLOR=\"#000000\" LETTERSPACING=\"0\" KERNING=\"0\"></FONT></P><P ALIGN=\"LEFT\"><FONT FACE=\"Verdana\" SIZE=\"11\" COLOR=\"#000000\" LETTERSPACING=\"0\" KERNING=\"0\">ezModeler is an enterprise ready application, but you can also use it at a very simple level. Please explorer the various options in the demo videos at our website:<FONT COLOR=\"#336699\"></FONT></FONT></P><P ALIGN=\"LEFT\"><FONT FACE=\"Verdana\" SIZE=\"11\" COLOR=\"#336699\" LETTERSPACING=\"0\" KERNING=\"0\"></FONT></P><P ALIGN=\"LEFT\"><FONT FACE=\"Verdana\" SIZE=\"11\" COLOR=\"#0000FF\" LETTERSPACING=\"0\" KERNING=\"0\"><A HREF=\"http://code.google.com/p/ezmodeler/wiki/DemoVideo\" TARGET=\"_new\"><U>http://code.google.com/p/ezmodeler/wiki/DemoVideo</U></A></FONT></P>",
							1L, 1, true, "", "", false, true, "", false, 1);

			this.webItemDaoImpl
					.addWebItem(
							"Support",
							"",
							"<P ALIGN=\"LEFT\"><FONT FACE=\"Verdana\" SIZE=\"11\" COLOR=\"#000000\" LETTERSPACING=\"0\" KERNING=\"0\">This is a Demo Service!</FONT></P><P ALIGN=\"LEFT\"><FONT FACE=\"Verdana\" SIZE=\"11\" COLOR=\"#000000\" LETTERSPACING=\"0\" KERNING=\"0\"></FONT></P><P ALIGN=\"LEFT\"><FONT FACE=\"Verdana\" SIZE=\"11\" COLOR=\"#000000\" LETTERSPACING=\"0\" KERNING=\"0\">You can freely use the service, there is no fee for using it! However the <B>export of diagrams to PPT is disabled</B>.</FONT></P><P ALIGN=\"LEFT\"><FONT FACE=\"Verdana\" SIZE=\"11\" COLOR=\"#000000\" LETTERSPACING=\"0\" KERNING=\"0\"></FONT></P><P ALIGN=\"LEFT\"><FONT FACE=\"Verdana\" SIZE=\"11\" COLOR=\"#000000\" LETTERSPACING=\"0\" KERNING=\"0\">You need to run ezModeler on your own server to have the full package.</FONT></P><P ALIGN=\"LEFT\"><FONT FACE=\"Verdana\" SIZE=\"11\" COLOR=\"#000000\" LETTERSPACING=\"0\" KERNING=\"0\"></FONT></P><P ALIGN=\"LEFT\"><FONT FACE=\"Verdana\" SIZE=\"11\" COLOR=\"#000000\" LETTERSPACING=\"0\" KERNING=\"0\">If you need assistance in using, maintenance or customization of ezModeler please contact our support to through our website:<FONT COLOR=\"#336699\"></FONT></FONT></P><P ALIGN=\"LEFT\"><FONT FACE=\"Verdana\" SIZE=\"11\" COLOR=\"#0000FF\" LETTERSPACING=\"0\" KERNING=\"0\"><A HREF=\"http://www.ezmodeler.com/team\" TARGET=\"_new\"><U></U></A></FONT></P><P ALIGN=\"LEFT\"><FONT FACE=\"Verdana\" SIZE=\"11\" COLOR=\"#0000FF\" LETTERSPACING=\"0\" KERNING=\"0\"><A HREF=\"http://www.ezmodeler.com/team\" TARGET=\"_new\"><U>http://www.ezmodeler.com/team</U></A></FONT></P>",
							1L, 9, true, "", "", false, true, "", false, 1);

		} catch (Exception e) {
			log.error("[addDefaultWebItems] ", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.i4change.app.installation.IImportInitvalues#loadDefaultRooms()
	 */
	public void loadDefaultRooms() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.i4change.app.installation.IImportInitvalues#loadInitUserAndOrganisation
	 * (java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void loadInitUserAndOrganisation(String username, String userpass,
			String email, String defaultOrganisationName) {
		// Add user
		try {
			Long user_id = this.usermanagement.importUserInstallation(new Long(
					3), 3, 1, 1, username, userpass, "Wagner", "Sebastian",
					email, new java.util.Date(), "Bleichstra§e", "92", "fax",
					"75173", 80, "Pforzheim", 1, false, null, null);

			// Add default group
			Long organisation_id = this.organisationDaoImpl.addOrganisation(
					defaultOrganisationName, user_id);

			// Add user to default group
			this.organisationDaoImpl.addUserToOrganisation(new Long(1),
					organisation_id, null, "", false);
		} catch (Exception e) {
			log.error("[loadInitUserAndOrganisation] ", e);
		}
	}

	/**
	 * import all language Names from the xml file
	 * 
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
			String paypal = item.attributeValue("paypal");

			this.countryDaoImpl.addCountry(country, paypal);

		}
	}

	/**
	 * load all availible languages File names and language name's from the
	 * config file
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public LinkedHashMap<Integer, String> getLanguageFiles(String filePath)
			throws Exception {

		LinkedHashMap<Integer, String> languages = new LinkedHashMap<Integer, String>();

		SAXReader reader = new SAXReader();
		Document document = reader.read(filePath
				+ ImportInitvalues.nameOfLanguageFile);

		Element root = document.getRootElement();

		for (Iterator<Element> it = root.elementIterator("lang"); it.hasNext();) {

			Element item = it.next();
			String country = item.getText();
			Integer id = Integer.valueOf(item.attribute("id").getValue())
					.intValue();

			// log.error("getLanguageFiles "+country);
			languages.put(id, country);

		}
		log.debug("Countries ADDED ");
		return languages;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.i4change.app.installation.IImportInitvalues#loadInitLanguages(java
	 * .lang.String)
	 */
	public void loadInitLanguages(String filePath) throws Exception {

		this.loadCountriesFiles(filePath);

		// String listLanguages[] = {"deutsch", "english", "french", "spanish"};

		LinkedHashMap<Integer, String> listlanguages = this
				.getLanguageFiles(filePath);

		// TODO empty tables before launch
		// Languagemanagement.getInstance().emptyFieldLanguage();

		boolean langFieldIdIsInited = false;

		/** Read all languages files */
		for (Iterator<Integer> itLang = listlanguages.keySet().iterator(); itLang
				.hasNext();) {
			Integer langId = itLang.next();
			String lang = listlanguages.get(langId);
			log.debug("loadInitLanguages lang: " + lang);

			Long languages_id = this.languagemanagement.addLanguage(lang);

			SAXReader reader = new SAXReader();
			Document document = reader.read(filePath + lang + ".xml");

			Element root = document.getRootElement();

			for (Iterator it = root.elementIterator("string"); it.hasNext();) {
				Element item = (Element) it.next();
				// log.error(item.getName());

				Long id = Long.valueOf(item.attributeValue("id")).longValue();
				String name = item.attributeValue("name");
				String value = "";

				for (Iterator t2 = item.elementIterator("value"); t2.hasNext();) {
					Element val = (Element) t2.next();
					value = val.getText();
				}

				// log.error("result: "+langFieldIdIsInited+" "+id+" "+name+" "+value);

				// if (this.checkForLangIds(id)){
				// Only do that for the first field-set
				if (!langFieldIdIsInited) {
					this.fieldmanagment.addFieldByLabelNumber(name, id);
				}

				this.fieldmanagment.addFieldValueByLabeldNumberAndLanguage(id,
						languages_id, value);
				// }
			}
			log.debug("Lang ADDED: " + lang);
			if (!langFieldIdIsInited)
				langFieldIdIsInited = true;
		}

		// Add help items

		SAXReader readerHelp = new SAXReader();
		Document documentHelp = readerHelp.read(filePath + "helpTopics.xml");
		Element roothelp = documentHelp.getRootElement();

		for (Iterator it = roothelp.elementIterator("item"); it.hasNext();) {
			Element item = (Element) it.next();

			Integer priority = Integer.valueOf(item.attributeValue("priority"))
					.intValue();
			Long labelId = Long.valueOf(item.attributeValue("labelid"))
					.longValue();
			Long topicLabelId = Long.valueOf(
					item.attributeValue("topiclabelid")).longValue();
			Long helpId = Long.valueOf(item.attributeValue("helpid"))
					.longValue();

			String helpName = item.attributeValue("helpname");
			String isAgentHelp = item.attributeValue("isAgentHelp");

			boolean isAgentHelpBool = false;
			if (isAgentHelp.equals("true")) {
				isAgentHelpBool = true;
			}

			this.helpTopicServiceDaoImpl.addHelpTopic(labelId, topicLabelId,
					helpId, helpName, isAgentHelpBool, priority);

		}
	}

	/**
	 * only import the initial Setup
	 * 
	 * @param id
	 * @return
	 */
	private boolean checkForLangIds(Long id) {
		try {

			int[] langIds = { 5, 6, 103, 105, 106, 107, 108, 109, 110, 111,
					112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123,
					124, 125, 127, 132, 133, 134, 135, 136, 137, 138, 139, 140,
					141, 142, 143, 144, 146, 147, 148, 149, 155, 156, 157, 158,
					159, 160, 161, 162, 164, 165, 166, 167, 168, 169, 170, 171,
					172, 173, 174, 175, 176, 177, 178, 180, 181, 182, 183, 184,
					185, 204, 261, 262, 263, 263, 264, 265, 266, 267, 268, 269,
					270, 271, 272, 273, 274, 275, 278, 279, 280, 281, 284, 288,
					290, 310, 311, 312, 313, 314, 315, 316, 317, 318, 319, 320,
					321, 322, 323, 324, 330, 331, 332, 333, 334, 335, 336, 337,
					338, 339, 340, 341, 342, 343, 344, 345, 346, 347, 348, 349,
					350, 351, 352, 353, 354, 355, 356, 357, 358, 360, 361, 362,
					363, 367, 368, 369, 370, 371, 374, 375, 376, 377, 379, 380,
					381, 382, 383, 384, 385, 386, 387, 388, 389, 430, 506, 507,
					508, 509, 510, 511, 513, 514, 515, 516, 519, 533, 534, 535,
					538 };

			for (int i = 0; i < langIds.length; i++) {
				if (langIds[i] == id) {
					return true;
				}
			}
		} catch (Exception err) {
			log.error("", err);
		}
		return false;
	}
}
