package org.i4change.app.documents;

import java.util.Date;
import java.util.HashMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.Configurationmanagement;
import org.i4change.app.remote.Application;
import org.i4change.app.utils.math.CalendarPatterns;

public class GenerateThumbs {
	 
	private static final Log log = LogFactory.getLog(GenerateThumbs.class);
	
	//Spring loaded beans
	private Configurationmanagement configurationmanagement;

	public Configurationmanagement getConfigurationmanagement() {
		return configurationmanagement;
	}
	public void setConfigurationmanagement(
			Configurationmanagement configurationmanagement) {
		this.configurationmanagement = configurationmanagement;
	}
	
	public HashMap<String,Object> generateThumb(String pre, String current_dir, String filepath, Integer thumbSize) {
		HashMap<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("process", "generateThumb");		
		try {
			
			//Init variables
			String[] cmd;
			String executable_fileName = "";	
			String pathToIMagick = this.configurationmanagement.getConfKey(3,"imagemagick_path").getConf_value();
			if(!pathToIMagick.equals("") && !pathToIMagick.endsWith(File.separator)){
				pathToIMagick = pathToIMagick + File.separator;
			}
			File f = new File(filepath);
			String name = f.getName();
			String folder = f.getParentFile().getAbsolutePath()+File.separatorChar;
			
			//If no Windows Platform
			if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") == -1) {
				String runtimeFile = "thumbnail.sh";
				executable_fileName = Application.batchFileFir+"THUMB_" 
						+ CalendarPatterns.getTimeForStreamId(new Date()) +"_"+ runtimeFile;
		
				cmd = new String[1];
				cmd[0] = executable_fileName;
			} else {
				String runtimeFile = "thumbnail.bat";
				executable_fileName = Application.batchFileFir+"THUMB_" 
						+ CalendarPatterns.getTimeForStreamId(new Date()) +"_"+ runtimeFile;
				
				cmd = new String[4];
				cmd[0] = "cmd.exe";
				cmd[1] = "/C";
				cmd[2] = "start";
				cmd[3] = executable_fileName;
			}
			log.debug("executable_fileName: "+executable_fileName);
			
			//Create the Content of the Converter Script (.bat or .sh File)
			String fileContent = pathToIMagick + "convert -thumbnail " + thumbSize + "x" + thumbSize +
					" " + "\"" + filepath + ".jpg\"" +
					" " + "\"" + folder + pre+name+".jpg\"" +
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

			Runtime rt = Runtime.getRuntime();			
			returnMap.put("command", cmd.toString());
			Process proc = rt.exec(cmd);
			
			InputStream stderr = proc.getErrorStream();
			InputStreamReader isr = new InputStreamReader(stderr);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			String error = "";
			while ((line = br.readLine()) != null){
				error += line;
				log.debug("line: "+line);
			}
			returnMap.put("error", error);
			int exitVal = proc.waitFor();
			log.debug("exitVal: "+exitVal);
			returnMap.put("exitValue", exitVal);
			return returnMap;
		} catch (Throwable t) {
			t.printStackTrace();
			returnMap.put("error", t.getMessage());
			returnMap.put("exitValue", -1);
			return returnMap;
		}
	}
	
	public HashMap<String,Object> generateThumb(File inputFile, File outputFile, Integer thumbSize) {
		HashMap<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("process", "generateThumb");		
		try {
			
			//Init variables
			String[] cmd;
			String executable_fileName = "";	
			String pathToIMagick = this.configurationmanagement.getConfKey(3,"imagemagick_path").getConf_value();
			if(!pathToIMagick.equals("") && !pathToIMagick.endsWith(File.separator)){
				pathToIMagick = pathToIMagick + File.separator;
			}
			
			//If no Windows Platform
			if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") == -1) {
				String runtimeFile = "thumbnail.sh";
				executable_fileName = Application.batchFileFir+"THUMB_" 
						+ CalendarPatterns.getTimeForStreamId(new Date()) +"_"+ runtimeFile;
		
				cmd = new String[1];
				cmd[0] = executable_fileName;
			} else {
				String runtimeFile = "thumbnail.bat";
				executable_fileName = Application.batchFileFir+"THUMB_" 
						+ CalendarPatterns.getTimeForStreamId(new Date()) +"_"+ runtimeFile;
				
				cmd = new String[4];
				cmd[0] = "cmd.exe";
				cmd[1] = "/C";
				cmd[2] = "start";
				cmd[3] = executable_fileName;
			}
			log.debug("executable_fileName: "+executable_fileName);
			
			//Create the Content of the Converter Script (.bat or .sh File)
			String fileContent = pathToIMagick + "convert -thumbnail " + thumbSize + "x" + thumbSize +
					" " + "\"" + inputFile.getAbsolutePath() +"\"" +
					" " + "\"" + outputFile.getAbsolutePath() +"\"" +
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

			Runtime rt = Runtime.getRuntime();			
			returnMap.put("command", cmd.toString());
			Process proc = rt.exec(cmd);
			
			InputStream stderr = proc.getErrorStream();
			InputStreamReader isr = new InputStreamReader(stderr);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			String error = "";
			while ((line = br.readLine()) != null){
				error += line;
				log.debug("line: "+line);
			}
			returnMap.put("error", error);
			int exitVal = proc.waitFor();
			log.debug("exitVal: "+exitVal);
			returnMap.put("exitValue", exitVal);
			return returnMap;
		} catch (Throwable t) {
			t.printStackTrace();
			returnMap.put("error", t.getMessage());
			returnMap.put("exitValue", -1);
			return returnMap;
		}
	}
	
	public HashMap<String,Object> generateBatchThumb(String current_dir, String inputfile, 
			String outputpath, Integer thumbSize) {
		HashMap<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("process", "generateBatchThumb");			
		try {
			
			String[] cmd;
			String executable_fileName = "";
			String pathToIMagick = this.configurationmanagement.getConfKey(3,"imagemagick_path").getConf_value();
			if(!pathToIMagick.equals("") && !pathToIMagick.endsWith(File.separator)){
				pathToIMagick = pathToIMagick + File.separator;
			}

			//If no Windows Platform
			if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") == -1) {
				String runtimeFile = "thumbnail.sh";
				executable_fileName = Application.batchFileFir+"BATCHTHUMB_" 
						+ CalendarPatterns.getTimeForStreamId(new Date()) +"_"+ runtimeFile;
		
				cmd = new String[1];
				cmd[0] = executable_fileName;
			} else {
				String runtimeFile = "thumbnail.bat";
				executable_fileName = Application.batchFileFir+"BATCHTHUMB_" 
						+ CalendarPatterns.getTimeForStreamId(new Date()) +"_"+ runtimeFile;
				
				cmd = new String[4];
				cmd[0] = "cmd.exe";
				cmd[1] = "/C";
				cmd[2] = "start";
				cmd[3] = executable_fileName;
			}
			log.debug("executable_fileName: "+executable_fileName);
			
			
			//Create the Content of the Converter Script (.bat or .sh File)
			String fileContent = pathToIMagick + "convert -thumbnail " + thumbSize +
					" " + "\"" + inputfile +"\"" +
					" " + "\"" + outputpath+ "_thumb_page.jpg\"" +
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

			
			Runtime rt = Runtime.getRuntime();
			
			for (int i=0;i<cmd.length;i++){
				log.debug("cmd: "+cmd[i]);
			}			
			
			returnMap.put("command", cmd.toString());
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
