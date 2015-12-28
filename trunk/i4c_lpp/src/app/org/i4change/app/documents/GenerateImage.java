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
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.hibernate.beans.user.Users;
import org.i4change.app.remote.Application;
import org.i4change.app.utils.math.CalendarPatterns;

public class GenerateImage {

	private static final Log log = LogFactory.getLog(GenerateImage.class);

	//Spring loaded beans 
	private UserDaoImpl userDaoImpl;
	private GenerateThumbs generateThumbs;
	private Configurationmanagement configurationmanagement;

	public Configurationmanagement getConfigurationmanagement() {
		return configurationmanagement;
	}
	public void setConfigurationmanagement(
			Configurationmanagement configurationmanagement) {
		this.configurationmanagement = configurationmanagement;
	}
	
	public UserDaoImpl getUserDaoImpl() {
		return userDaoImpl;
	}
	public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}
	
	public GenerateThumbs getGenerateThumbs() {
		return generateThumbs;
	}
	public void setGenerateThumbs(GenerateThumbs generateThumbs) {
		this.generateThumbs = generateThumbs;
	}
	
	public HashMap<String,HashMap> convertImage(String current_dir, String fileName, String fileExt, 
						    String roomName, String fileNameShort, 
						    boolean fullProcessing) throws Exception {
		
		HashMap<String,HashMap> returnMap = new HashMap<String,HashMap>();

		String working_imgdir = current_dir + "upload" + File.separatorChar + roomName + File.separatorChar;
		String working_pptdir = current_dir + "uploadtemp" + File.separatorChar + roomName + File.separatorChar;

		String fileFullPath = working_pptdir + fileName+ fileExt;

		File f = new File(working_imgdir + fileName + fileExt);
		if (f.exists()) {
			int recursiveNumber = 0;
			String tempd = fileName + "_" + recursiveNumber;
			while (f.exists()) {
				recursiveNumber++;
				tempd = fileName + "_" + recursiveNumber;
				f = new File(working_imgdir + tempd + fileExt);
			}
			fileName = tempd;
		}

		String destinationFile = working_imgdir + fileName;
		
		log.debug("##### convertImage destinationFile: "+destinationFile);
		
		HashMap processJPG = this.convertSingleJpg(current_dir, fileFullPath, destinationFile);
		HashMap processThumb = this.generateThumbs.generateThumb("_thumb_", current_dir, destinationFile, 50);
		
		returnMap.put("processJPG", processJPG);
		returnMap.put("processThumb", processThumb);
		
		//Delete old one
		File fToDelete = new File(fileFullPath);
		fToDelete.delete();

		return returnMap;
	}

	public HashMap<String,HashMap> convertImageUserProfile(String current_dir, String fileName, String fileExt, Long users_id, 
			String fileNameShort, boolean fullProcessing) throws Exception {
		
		HashMap<String,HashMap> returnMap = new HashMap<String,HashMap>();

		String working_imgdir = current_dir + "upload" + File.separatorChar + "profiles" + File.separatorChar + "profile_"+users_id + File.separatorChar;
		String working_pptdir = current_dir + "uploadtemp" + File.separatorChar + "profiles" + File.separatorChar + "profile_"+users_id + File.separatorChar;

		String fileFullPath = working_pptdir + fileName+fileExt;

		File f = new File(working_imgdir + fileName + fileExt);
		if (f.exists()) {
			int recursiveNumber = 0;
			String tempd = fileName + "_" + recursiveNumber;
			while (f.exists()) {
				recursiveNumber++;
				tempd = fileName + "_" + recursiveNumber;
				f = new File(working_imgdir + tempd + fileExt);
			}
			fileName = tempd;
		}

		String destinationFile = working_imgdir + fileName;
		HashMap<String,Object> processJPG = this.convertSingleJpg(current_dir, fileFullPath, destinationFile);
		
		HashMap<String,Object> processThumb1 = this.generateThumbs.generateThumb("_chat_", current_dir, destinationFile, 40);
		HashMap<String,Object> processThumb2 = this.generateThumbs.generateThumb("_profile_", current_dir, destinationFile, 100);
		HashMap<String,Object> processThumb3 = this.generateThumbs.generateThumb("_big_", current_dir, destinationFile, 240);		
		
		returnMap.put("processJPG", processJPG);
		returnMap.put("processThumb1", processThumb1);
		returnMap.put("processThumb2", processThumb2);
		returnMap.put("processThumb3", processThumb3);
		
		//Delete old one
		File fToDelete = new File(fileFullPath);
		//fToDelete.delete();
		
		File fileNameToStore = new File(destinationFile+".jpg");
		String pictureuri = fileNameToStore.getName();
		Users us = this.userDaoImpl.getUserById(users_id);
		us.setUpdatetime(new java.util.Date());
		us.setPictureuri(pictureuri);
		this.userDaoImpl.updateUser(us);	
		
		//TODO: Update User Picture
		//Application.getInstance().updateUserSessionObject(users_id,pictureuri);

		return returnMap;
	}
	
	/**
	 * -density 150 -resize 800\> 
	 * 
	 */

	private HashMap<String,Object> convertSingleJpg(String current_dir, String inputFile, String outputfile) {
		HashMap<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("process", "convertSingleJpg");
		try {

			//Init variables
			String[] cmd;
			String executable_fileName = "";	
			String pathToIMagick = this.configurationmanagement.getConfKey(3,"imagemagick_path").getConf_value();
			if(!pathToIMagick.equals("") && !pathToIMagick.endsWith(File.separator)){
				pathToIMagick = pathToIMagick + File.separator;
			}
			
			log.debug("##### convertSingleJpg pngconverter: ");
		
			
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
			
			Runtime rt = Runtime.getRuntime();
			
			log.debug("cmd: "+cmd);

			//Create the Content of the Converter Script (.bat or .sh File)
			String fileContent = pathToIMagick + "convert " +
					" " + "\"" + inputFile + "\"" +
					" " + "\"" + outputfile+".jpg\"" +
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

			log.debug("ERROR: "+error);
			
			return returnMap;
		} catch (Throwable t) {
			t.printStackTrace();
			returnMap.put("error",t.getMessage());
			returnMap.put("exitValue", -1);
			return returnMap;
		}
	}

	public HashMap<String,Object> convertSingleImageFromSvg(String inputFile, String outputfile, int width, int height) {
		HashMap<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("process", "convertSingleJpg");
		try {

			//Init variables
			String[] cmd;
			String executable_fileName = "";	
			String pathToIMagick = this.configurationmanagement.getConfKey(3,"imagemagick_path").getConf_value();
			if(!pathToIMagick.equals("") && !pathToIMagick.endsWith(File.separator)){
				pathToIMagick = pathToIMagick + File.separator;
			}
			
			log.debug("##### convertSingleJpg pngconverter: ");
		
			
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
			
			Runtime rt = Runtime.getRuntime();
			
			log.debug("cmd: "+cmd);

			//Create the Content of the Converter Script (.bat or .sh File) 
			String fileContent = pathToIMagick + "convert " +
					//"-density 196 -resample 72 -unsharp 0x.5 "+ 
					"-transparent white -quality 100 -unsharp 0x.5 " +
					"-size "+width+"x"+height+" " +
					" " + "\"" + inputFile + "\"" +
					" " + "\"" + outputfile+"\"" +
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

			log.debug("ERROR: "+error);
			
			return returnMap;
		} catch (Throwable t) {
			t.printStackTrace();
			returnMap.put("error",t.getMessage());
			returnMap.put("exitValue", -1);
			return returnMap;
		}
	}
	
	public HashMap<String,Object> resizeSingleImage(String inputFile, String outputfile, int width, int height) {
		HashMap<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("process", "convertSingleJpg");
		try {

			//Init variables
			String[] cmd;
			String executable_fileName = "";	
			String pathToIMagick = this.configurationmanagement.getConfKey(3,"imagemagick_path").getConf_value();
			if(!pathToIMagick.equals("") && !pathToIMagick.endsWith(File.separator)){
				pathToIMagick = pathToIMagick + File.separator;
			}
			
			log.debug("##### convertSingleJpg pngconverter: ");
		
			
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
			
			Runtime rt = Runtime.getRuntime();
			
			log.debug("cmd: "+cmd);

			//Create the Content of the Converter Script (.bat or .sh File) 
			String fileContent = pathToIMagick + "convert " +
					//"-density 196 -resample 72 -unsharp 0x.5 "+ 
					" " + "\"" + inputFile + "\" " +
					"-resize "+width+"x"+height+" -quality 100 -unsharp 0x.5 " +
					" " + "\"" + outputfile+"\"" +
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

			log.debug("ERROR: "+error);
			
			return returnMap;
		} catch (Throwable t) {
			t.printStackTrace();
			returnMap.put("error",t.getMessage());
			returnMap.put("exitValue", -1);
			return returnMap;
		}
	}
	
}
