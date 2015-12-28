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

public class GeneratePDF {
	
	private static final Log log = LogFactory.getLog(GeneratePDF.class);
	 
	/**
	 * Generate PDF and thumbs (and swf)
	 * @param current_dir
	 * @param fileFullPath
	 * @param destinationFolder
	 * @param outputfile
	 * @return
	 */
	public HashMap<String,Object> doConvertExecToFormat(String current_dir , String fileFullPath, 
			String destinationFolder, String outputfile, String format) {
		HashMap<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("process", "doConvertExec");				
		try {
			
			
			String[] cmd;
			String executable_fileName = "";
			
			//If no Windows Platform
			if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") == -1) {
				String runtimeFile = "jodconverter.sh";
				executable_fileName = Application.batchFileFir+"PDFCONVERT_" 
						+ CalendarPatterns.getTimeForStreamId(new Date()) +"_"+ runtimeFile;
		
				cmd = new String[1];
				cmd[0] = executable_fileName;
			} else {
				String runtimeFile = "jodconverter.bat";
				executable_fileName = Application.batchFileFir+"PDFCONVERT_" 
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
			String jodClassPathFolder = current_dir + "jod" + File.separatorChar;
			
			//Create the Content of the Converter Script (.bat or .sh File)
			String fileContent = "java" +
					" " + "-cp" + " " +
					".:" + 
					"\"" + jodClassPathFolder + "commons-cli-1.0.jar\":" +
					"\"" + jodClassPathFolder + "commons-io-1.3.1.jar\":" +
					"\"" + jodClassPathFolder + "jodconverter-2.2.1.jar\":" +
					"\"" + jodClassPathFolder + "jodconverter-cli-2.2.1.jar\":" +
					"\"" + jodClassPathFolder + "juh-2.3.0.jar\":" +
					"\"" + jodClassPathFolder + "jurt-2.3.0.jar\":" +
					"\"" + jodClassPathFolder + "ridl-2.3.0.jar\":" +
					"\"" + jodClassPathFolder + "slf4j-api-1.4.3.jar\":" +
					"\"" + jodClassPathFolder + "slf4j-jdk14-1.4.3.jar\":" +
					"\"" + jodClassPathFolder + "unoil-2.3.0.jar\":" +
					"\"" + jodClassPathFolder + "xstream-1.2.2.jar\"" +
					" " + "-jar" +
					" " + "\"" + jodClassPathFolder +"jodconverter-cli-2.2.1.jar\"" +
					" " + "\"" + fileFullPath + "\"" +
					" " + "\"" + destinationFolder + outputfile + "."+format+"\"" +
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
