package org.i4change.app.installation;

import java.util.LinkedHashMap;

public interface IImportInitvalues {

	public static final String languageFolderName = "languages/";

	public abstract void loadMainMenu();

	public abstract void loadErrorMappingsFromXML(String filePath)
			throws Exception;

	public abstract void loadSalutations();

	public abstract void loadConfiguration(String crypt_ClassName,
			String allowfrontendRegister, String smtpServer, String smtpPort,
			String referer, String mailauthname, String mailauthpass,
			String default_lang_id, String swf_path, String im_path,
			String url_feed, String url_feed2, String sendEmailAtRegister,
			String sendEmailWithVerficationCode);

	public abstract void loadDefaultRooms();

	public abstract void loadInitUserAndOrganisation(String username,
			String userpass, String email, String defaultOrganisationName);

	public abstract void loadInitLanguages(String filePath) throws Exception;

	public LinkedHashMap<Integer, String> getLanguageFiles(String filePath)
			throws Exception;

}