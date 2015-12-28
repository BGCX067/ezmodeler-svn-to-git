package org.i4change.app.templates;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.Velocity;
import org.i4change.app.remote.Application;

/**
 * 
 * @author swagner
 *
 */

public class VelocityLoader {
	
	private static final Log log = LogFactory.getLog(VelocityLoader.class);
	
	/**
	 * Loads the Path from the Red5-Scope
	 *
	 */
	public VelocityLoader(){
		try {
			String current_dir = Application.webAppPath+File.separatorChar+"WEB-INF"+File.separatorChar+"velocity.properties";	
            Velocity.init(current_dir);
        } catch(Exception e) {
        	log.error("Problem initializing Velocity : " + e );
            System.out.println("Problem initializing Velocity : " + e );
        }
	}
	
	/**
	 * Loads the path by a given string, this is necessary cause if invoked
	 * by Servlet there is no Red5-Scope availible
	 * @param path
	 */
	public VelocityLoader(String path){
		try {
            Velocity.init(path+"WEB-INF/velocity.properties");
        } catch(Exception e) {
        	log.error("Problem initializing Velocity : " + e );
            System.out.println("Problem initializing Velocity : " + e );
        }
	}

}
