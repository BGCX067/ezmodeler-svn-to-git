package org.i4change.app.documents;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.remote.Application;
import org.i4change.app.utils.math.CalendarPatterns;

public class GenerateDocument {
	
	private static final Log log = LogFactory.getLog(GenerateDocument.class);
	 
	/**
	 * Generate PDF and thumbs (and swf)
	 * @param current_dir
	 * @param fileFullPath
	 * @param destinationFolder
	 * @param outputfile
	 * @return
	 */
	public HashMap<String,Object> doConvertExec(String current_dir , 
			String fileFullPath_template, 
			String fileFullPath_output, 
			String fileFullPath_mergeXML) {
		HashMap<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("process", "doConvertExec");				
		try {
			
			
			String[] cmd;
			String executable_fileName = "";
			
			//If no Windows Platform
			if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") == -1) {
				String runtimeFile = "jooconverter.sh";
				executable_fileName = Application.mergeFileFir+"PDFCONVERT_" 
						+ CalendarPatterns.getTimeForStreamId(new Date()) +"_"+ runtimeFile;
		
				cmd = new String[1];
				cmd[0] = executable_fileName;
			} else {
				String runtimeFile = "jooconverter.bat";
				executable_fileName = Application.mergeFileFir+"PDFCONVERT_" 
						+ CalendarPatterns.getTimeForStreamId(new Date()) +"_"+ runtimeFile;
				
				cmd = new String[4];
				cmd[0] = "cmd.exe";
				cmd[1] = "/C";
				cmd[2] = "start";
				cmd[3] = executable_fileName;
			}
			log.debug("executable_fileName: "+executable_fileName);
			
//			String runtimeFile = "jodconverter.bat";
//			String command = "cmd.exe /c start "+current_dir + "jod" + File.separatorChar
//				+ runtimeFile + " java " + fileFullPath + " "
//				+ destinationFolder + outputfile + ".pdf " + current_dir
//				+ "jod" + File.separatorChar;
//			
//			if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") == -1) {
//				runtimeFile = "jodconverter.sh";
//				command = current_dir + "jod" + File.separatorChar
//					+ runtimeFile + " java " + fileFullPath + " "
//					+ destinationFolder + outputfile + ".pdf " + current_dir
//					+ "jod" + File.separatorChar;				
//			}
			
			//Path to all JARs of JOD
			String jooClassPathFolder = current_dir + "joo" + File.separatorChar;
			
			//Create the Content of the Converter Script (.bat or .sh File)
			String fileContent = "java" +
					" " + "-cp" + " " +
					".:" + 
					"\"" + jooClassPathFolder + "commons-io-1.2.jar\":" +
					"\"" + jooClassPathFolder + "commons-logging-1.1.jar\":" +
					"\"" + jooClassPathFolder + "freemarker-2.3.8.jar\":" +
					"\"" + jooClassPathFolder + "jooconverter-2.1rc2.jar\":" +
					"\"" + jooClassPathFolder + "jooreports-2.0.0.jar\":" +
					"\"" + jooClassPathFolder + "openoffice-juh-2.0.3.jar\":" +
					"\"" + jooClassPathFolder + "openoffice-jurt-2.0.3.jar\":" +
					"\"" + jooClassPathFolder + "openoffice-ridl-2.0.3.jar\":" +
					"\"" + jooClassPathFolder + "openoffice-sandbox-2.0.3.jar\":" +
					"\"" + jooClassPathFolder + "openoffice-unoil-2.0.3.jar\":" +
					"\"" + jooClassPathFolder + "spring-beans-1.2.8.jar\":" +
					"\"" + jooClassPathFolder + "spring-context-1.2.8.jar\":" +
					"\"" + jooClassPathFolder + "spring-core-1.2.8.jar\":" +
					"\"" + jooClassPathFolder + "spring-web-1.2.8.jar\":" +
					"\"" + jooClassPathFolder + "spring-webmvc-1.2.8.jar\":" +
					"\"" + jooClassPathFolder + "vxom-1.1.jar\":" +
					"\"" + jooClassPathFolder + "xpp3-1.1.3_8.jar\":" +
					"\"" + jooClassPathFolder + "xstream-1.1.3.jar\":" +
					" " + "-jar" +
					" " + "\"" + jooClassPathFolder +"jooreports-2.0.0.jar\"" +
					" " + "\"" + fileFullPath_template + "\"" +
					" " + "\"" + fileFullPath_mergeXML + "\"" +
					" " + "\"" + fileFullPath_output + "\"" +
					Application.lineSeperator + "exit";
			
			
			//execute the Script
			FileOutputStream fos = new FileOutputStream(executable_fileName);
			fos.write(fileContent.getBytes());
			fos.close();
			
			//make new shell script executable
			//in JAVA6 this can be done directly through the api
			if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") == -1) {
				MakeExectuable.setExecutable(executable_fileName);
			}
			
			log.debug("command: "+cmd);
			
			Runtime rt = Runtime.getRuntime();
			returnMap.put("command",cmd.toString());
			Process proc = rt.exec(cmd);
			InputStream stderr = proc.getErrorStream();
			InputStreamReader isr = new InputStreamReader(stderr);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			String error = "";
			while ((line = br.readLine()) != null)
				error += line;
			returnMap.put("error", error);
			int exitVal = proc.waitFor();
			
			//354256 t
			//349968 a
			
			//298780 t
			//280024 a
			
			log.error(error);
			returnMap.put("exitValue", exitVal);
			return returnMap;
		} catch (Throwable t) {
			t.printStackTrace();
			returnMap.put("error", t.getMessage());
			returnMap.put("exitValue", -1);
			return returnMap;
		}
	}	
	
}
