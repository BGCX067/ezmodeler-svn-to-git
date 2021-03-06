package org.i4change.app.documents;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.remote.Application;

public class MakeExectuable {
	
	private static final Log log = LogFactory.getLog(MakeExectuable.class);

	public static void setExecutable(String fileName){
		try {
			
			log.debug("setExecutable: "+fileName);
			
			String[] cmd = new String[1];
			cmd[0] = "chmod +x "+fileName;
			
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec( "chmod +x "+fileName );
			
			InputStream stderr = proc.getErrorStream();
			InputStreamReader isr = new InputStreamReader(stderr);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			String error = "";
			while ((line = br.readLine()) != null){
				error += line;
				log.debug("line: "+line);
			}
			int exitVal = proc.waitFor();
			log.debug("exitVal: "+exitVal);
			
		} catch (Exception err) {
			log.error("[setExecutable]",err);
		}
	}

}
