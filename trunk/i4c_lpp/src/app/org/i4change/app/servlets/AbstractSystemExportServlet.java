package org.i4change.app.servlets;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import javax.servlet.http.HttpServlet;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class AbstractSystemExportServlet extends HttpServlet {
	
	private static final Log log = LogFactory.getLog(SystemExport.class);
	
	private LinkedList<String> getCompareTypesSimple() {
		LinkedList<String> typeList = new LinkedList<String>();
		
		//Primitives
		typeList.add("String");
		typeList.add("byte");
		typeList.add("int");
		typeList.add("boolean");
		typeList.add("float");
		typeList.add("long");
		typeList.add("double");
		
		
		//Object
		typeList.add("java.lang.String");
		typeList.add("java.lang.Number");
		typeList.add("java.lang.Byte");
		typeList.add("java.lang.Integer");
		typeList.add("java.lang.Boolean");
		typeList.add("java.lang.Float");
		typeList.add("java.lang.Long");
		typeList.add("java.lang.Double");
		typeList.add("java.util.Date");
		
		return typeList;
	}
	
	private boolean compareTypeNameToBasicTypes(String fieldTypeName) {
		try {
			
			for (Iterator it = this.getCompareTypesSimple().iterator();it.hasNext();) {
				if (fieldTypeName.equals(it.next())) return true;
			}
			
			return false;
		} catch (Exception ex) {
			log.error("[compareTypeNameToBasicTypes]",ex);
			return false;
		}
	}	
	
	public void addFieldToElement(Object instance) {
		try {
			
			Class targetClass = instance.getClass();
			
			//LinkedHashMap<String,LinkedHashMap<String,Object>> structuredMethodMap = this.parseClassToMethodList(targetClass);
			
			for ( Field anyField : targetClass.getDeclaredFields() )  { 
				String fieldName = anyField.getName(); 
				Class fieldType = anyField.getType();
				String fieldTypeName = anyField.getType().getName(); 

				if (this.compareTypeNameToBasicTypes(fieldTypeName)) {
					
					int mod = anyField.getModifiers();
						
					if (!Modifier.isFinal(mod)){
						
						//log.info("is private so get setter method "+fieldName);
						//LinkedHashMap<String,Object> methodSummery = structuredMethodMap.get(fieldName);
						
						Class fieldTypeClass = anyField.getType();
						String capitalizedFieldName = StringUtils.capitalize(fieldName);
						Method method = targetClass.getMethod("get" + capitalizedFieldName);
						
						//targetClass.getM
						
						if (method!=null) {
								
								Object returnVal = method.invoke(instance);
								
								log.debug("for " + fieldName + " in Class " + targetClass.getName() +" Return Vall: "+returnVal);
								
							} else {
								log.error("could not find a getter-method. Is there a setter-method for " + fieldName + " in Class " + targetClass.getName());
							}
						
						
					} else if (Modifier.isFinal(mod)) {
						log.error("Final attributes cannot be changed ");
					} else {
						log.error("Unhandled Modifier Type: " + mod);
					}
					
				} else {
					log.debug("This field cannot be exported Class: "+targetClass.getName()+ " Field: "+anyField.getName());
				}
			}
		} catch (Exception err) {
			log.error("[addFieldToElement]",err);
		}
	}

}
