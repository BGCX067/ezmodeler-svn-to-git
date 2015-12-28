package org.i4change.rpc;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.data.user.daos.UserPropertyDaoImpl;
import org.i4change.app.hibernate.beans.basic.Sessiondata;
import org.i4change.app.hibernate.beans.user.Users;
import org.openlaszlo.remote.json.LZReturnObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class TestJSON extends TestCase {
	
	
	private static final Log log = LogFactory.getLog(TestJSON.class);

	public TestJSON() {
		super();
	}

	public TestJSON(String name) {
		super(name);
	}
//	
//	public void testJSON()  {
//		try {
//			
//				
//				Users users = UserDaoImpl.getInstance().getUserById(1L);
//		        users.setUserPropsAsObject(UserPropertyDaoImpl.getInstance().getUserSidebarPropertyByUser(users.getUser_id()));
//		        
//		        if (users.getXmlStringRegObjectObj() != null && users.getXmlStringRegObjectObj().length() != 0) {
//		        	XStream xStream = new XStream(new XppDriver());
//					xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
//					
//					users.setRegObjectObj((Map) xStream.fromXML(users.getXmlStringRegObjectObj()));
//		        }
//		        
//		        users.setUserlevel(UserDaoImpl.getInstance().getUserLevel(users.getLevel_id()));	
//				
//				String buf = new LZReturnObject("javabean").createObjectProgram(users);
//				
//				log.debug("buf");
//				System.out.println("##buf#");
//				System.out.println(buf);
//		        System.out.println("###");
//		        
//		        
//		        String buf2 = LzAlternateJSONSerialization.getInstance().toJson(users);
//		        System.out.println("##buf2#");
//				System.out.println(buf2);
//		        System.out.println("###");
//			
//
//	        
//		
//		} catch (Exception err) {
//			log.error("[testAddPresentationToTable]",err);
//		}
//	}
	
	
	
	public void testJSON()  {
		try {
			
			for (int i=0;i<1000;i++) {
				
				Sessiondata sDate = new Sessiondata();
				sDate.setId(1L); 
				sDate.setLanguage_id(2L);
				
				System.out.println(sDate.getClass().getDeclaredFields().length);
				System.out.println(sDate.getClass().getFields().length);
				
				String buf = new LZReturnObject("javabean").createObjectProgram(sDate);
				
				log.debug("buf");
				System.out.println("##buf#");
				System.out.println(buf);
		        System.out.println("###");
		        
		        GsonBuilder gsonBuilder = new GsonBuilder();
		        gsonBuilder.serializeNulls();
		        gsonBuilder.setPrettyPrinting();
		        
		        Gson gson = gsonBuilder.create();
		        
		        String jsonOutput = gson.toJson(sDate);
		        System.out.println("##buf2#");
				System.out.println(jsonOutput);
		        System.out.println("###");

//		        String buf2 = LzAlternateJSONSerialization.getInstance().toJson(sDate);
//		        System.out.println("##buf2#");
//				System.out.println(buf2);
//		        System.out.println("###");
			}
			

	        
		
		} catch (Exception err) {
			log.error("[testAddPresentationToTable]",err);
		}
	}
	
//	public void testJSON2()  {
//	try {
//		Users us = UserDaoImpl.getInstance().getUserById(1L);
//		String buf = new LZReturnObject("javabean").createObjectProgram(us);
//		
//		log.debug("buf");
//		System.out.println("###");
//		System.out.println(buf);
//        System.out.println("###");
//        
//        String buf2 = LzAlternateJSONSerialization.getInstance().toJson(us);
//        System.out.println("###");
//		System.out.println(buf2);
//        System.out.println("###");
//		
//	
//	} catch (Exception err) {
//		log.error("[testAddPresentationToTable]",err);
//	}
//}
	
//	public void testJSON()  {
//		try {
//			Users us = UserDaoImpl.getInstance().getUserById(1L);
//			String buf = new LZReturnObject("javabean").createObjectProgram(us);
//			
//			log.debug("buf");
//			System.out.println("###");
//			System.out.println(buf);
//			
////			System.out.println("###");
////			XStream xstream = new XStream(new JsonHierarchicalStreamDriver());
////	        xstream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
////	        //xstream.alias("product", Product.class);
//
////	        System.out.println(xstream.toXML(us));
//	        System.out.println("###");
//			
//		
//		} catch (Exception err) {
//			log.error("[testAddPresentationToTable]",err);
//		}
//	}
	
//	public void testJSON2()  {
//		try {
//			Users us = UserDaoImpl.getInstance().getUserById(1L);
//			String buf = new LZReturnObject("javabean").createObjectProgram(us);
//			
//			XStream xstream = new XStream(new JettisonMappedXmlDriver());
//	        xstream.setMode(XStream.NO_REFERENCES);
//	        //xstream.alias("product", Product.class);
//
//	        System.out.println(xstream.toXML(us));
//	        
//		} catch (Exception err) {
//			log.error("[testAddPresentationToTable]",err);
//		}
//	}
//	
	
}
