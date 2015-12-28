package org.i4change.app.xmlimport;

import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import org.i4change.app.data.basic.Fieldmanagment;
import org.i4change.app.data.basic.Languagemanagement;
import org.i4change.app.hibernate.beans.lang.FieldLanguage;
import org.i4change.app.hibernate.beans.lang.Fieldvalues;
import org.i4change.app.hibernate.beans.lang.Fieldlanguagesvalues;

public class LanguageImport {
	private static final Log log = LogFactory.getLog(LanguageImport.class);
	
	//Spring loaded beans
	private Languagemanagement languagemanagement;
	private Fieldmanagment fieldmanagment;

	public Fieldmanagment getFieldmanagment() {
		return fieldmanagment;
	}
	public void setFieldmanagment(Fieldmanagment fieldmanagment) {
		this.fieldmanagment = fieldmanagment;
	}
	
	public Languagemanagement getLanguagemanagement() {
		return languagemanagement;
	}
	public void setLanguagemanagement(Languagemanagement languagemanagement) {
		this.languagemanagement = languagemanagement;
	}

	public Long addLanguageByDocument(Long language_id, InputStream is) throws Exception {
		
		//return null if no language availible
		if (this.languagemanagement.getFieldLanguageById(language_id)==null) {
			return null;
		}
		
		
		SAXReader reader = new SAXReader();
        Document document = reader.read(is);
        
        Element root = document.getRootElement();
        
        for (Iterator i = root.elementIterator(); i.hasNext(); ) {
        	Element itemObject = (Element) i.next();
        	Long label_id = Long.valueOf(itemObject.attribute("id").getText()).longValue();
        	String fieldName = itemObject.attribute("name").getText();
        	String value = itemObject.element("value").getText();
        	log.error("CHECK "+language_id+","+label_id+","+fieldName+","+value);
        	this.addFieldValueById(language_id, label_id, fieldName, value);
        }
        
        return null;        
	}
	
	private void addFieldValueById(Long language_id, Long label_id, String fieldName, String value) throws Exception {
		
		Fieldvalues fv = this.fieldmanagment.getFieldvaluesByLabelNumber(label_id);
		
		if (fv != null) {
			fv.setUpdatetime(new Date());
			fv.setName(fieldName);
			this.fieldmanagment.updateField(fv);
			
			Fieldlanguagesvalues flv = this.fieldmanagment.getFieldByLabelNumberAndLanguage(label_id, language_id);
			
			if (flv != null) {
				
				flv.setValue(value);
				flv.setUpdatetime(new Date());
				
				this.fieldmanagment.updateFieldValueByFieldAndLanguage(flv);
				
			} else {
				
				this.fieldmanagment.addFieldValueByLabeldNumberAndLanguage(label_id, language_id, value);
				
			}
			
		} else {
			this.fieldmanagment.addFieldAndLabel(fieldName, label_id, value, language_id);
		}
		
	}
	
	
}
