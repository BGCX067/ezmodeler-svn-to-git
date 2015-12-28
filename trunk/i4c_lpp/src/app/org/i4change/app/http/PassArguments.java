package org.i4change.app.http;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.http.beans.MessageBean;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class PassArguments  {

//	private static String secretCharStart = "*¤(*";
//	private static String secretCharTypeDevision = "*|¤|*";
//	private static String secretCharEnd = "*)¤*";
	
	private static final Log log = LogFactory.getLog(PassArguments.class);

	private static PassArguments instance = null;
	
	private PassArguments() {
	}

	public static synchronized PassArguments getInstance() {
		if (instance == null) {
			instance = new PassArguments();
		}
		return instance;
	}
	
	public Map processRequest(String args) {
		try {
		
			Map myObj = new HashMap();

			Document document = DocumentHelper.parseText(args);
			
			Element root = document.getRootElement();

	        // iterate through child elements of root
	        for ( Iterator i = root.elementIterator(); i.hasNext(); ) {
	            Element element = (Element) i.next();
	            
	            // do something
	            log.debug("Element : "+element.getName());
	            
	            log.debug("Element : "+element.getText());
        		log.debug("Element : "+element.elements().size());
        		
	        }

			//Document document = reader.r
			log.debug("myObj: "+myObj);
			
		} catch (Exception err) {
			log.error("[processRequest]",err);
		}
		return null;
	}
}
