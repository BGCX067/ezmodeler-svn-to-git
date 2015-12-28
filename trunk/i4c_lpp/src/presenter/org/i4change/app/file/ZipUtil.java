package org.i4change.app.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ZipUtil {

	private static final Log log = LogFactory.getLog(ZipUtil.class);

	public static synchronized File createODPFile(String outputRootPath, String zipFilepath) {
		try {
			
			ZipOutputStream zip = null;
			FileOutputStream fileWriter = null;

			fileWriter = new FileOutputStream(zipFilepath);
			zip = new ZipOutputStream(fileWriter);
			
			File outputRootFolder = new File(outputRootPath);
			
			for (String fileName : outputRootFolder.list()) {
				log.debug("fileName: "+outputRootPath + File.separatorChar + fileName);
				File fileORFolder = new File(outputRootPath + File.separatorChar + fileName);
				log.debug("fileORFolder: "+fileORFolder.exists());
				if (fileORFolder.isDirectory()){
					addFolderToZip("", fileORFolder.getAbsolutePath(), zip);
				} else {
					addFileToZip("", fileORFolder.getAbsolutePath(), zip);
				}
			}
			
			zip.flush();
			zip.close();

		} catch (Exception err) {
			log.error("[createODPFile]", err);
		}
		return null;
	}

	public static synchronized void zipFolder(String srcFolder, String destZipFile) throws Exception {
		
		ZipOutputStream zip = null;
		FileOutputStream fileWriter = null;

		fileWriter = new FileOutputStream(destZipFile);
		zip = new ZipOutputStream(fileWriter);

		addFolderToZip("", srcFolder, zip);
		zip.flush();
		zip.close();
		
	}

	private static void addFileToZip(String path, String srcFile, ZipOutputStream zip) throws Exception {

		log.debug("addFileToZip "+path+" "+srcFile+" "+zip);
		
		File folder = new File(srcFile);
		if (folder.isDirectory()) {
			addFolderToZip(path, srcFile, zip);
		} else {
			byte[] buf = new byte[1024];
			int len;
			FileInputStream in = new FileInputStream(srcFile);
			
			if (path.equals(File.separatorChar) || path.equals("")) {
				zip.putNextEntry(new ZipEntry(folder.getName()));
			} else {
				zip.putNextEntry(new ZipEntry(path + File.separatorChar
						+ folder.getName()));
			}
			while ((len = in.read(buf)) > 0) {
				zip.write(buf, 0, len);
			}
		}
		
	}

	private static void addFolderToZip(String path, String srcFolder, ZipOutputStream zip) throws Exception {
		
		log.debug("addFolderToZip "+path+" "+srcFolder+" "+zip);
		
		File folder = new File(srcFolder);

		for (String fileName : folder.list()) {
			if (path.equals("")) {
				addFileToZip(folder.getName(), srcFolder + File.separatorChar
						+ fileName, zip);
			} else {
				addFileToZip(path + File.separatorChar + folder.getName(),
						srcFolder + File.separatorChar + fileName, zip);
			}
		}
		
	}

}
