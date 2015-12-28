package org.i4change.diagram.diagramquery;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.Fieldmanagment;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.data.diagram.DiagramObjectDaoImpl;
import org.i4change.app.hibernate.beans.diagram.DiagramObject;
import org.i4change.app.hibernate.beans.lang.Fieldvalues;

public class TestGetLanguageFields extends TestCase {
	
	private static final Log log = LogFactory.getLog(TestGetLanguageFields.class);	
	
	public void testGetLanguageFields(){
		try {
			
			log.debug("TestGetLanguageFields: ");
			int start = 0;
			int max = 50;
			
			String orderby = "c.label_number";
			boolean asc = true;
			String search = "844";
			
			Long language_id = 1L;
	        
			Map<Integer,String> objectTypeNames = new HashMap<Integer,String>();
			objectTypeNames.put(0,"unitFixed");
			
			List<Fieldvalues> listResult = Fieldmanagment.getInstance().getFieldsValuesBySearch(start, max, 
						orderby, asc, search, language_id);
			for (Iterator<Fieldvalues> iter = listResult.iterator(); iter.hasNext();){
				Fieldvalues fv = iter.next();
				fv.setFieldlanguagesvalue(Fieldmanagment.getInstance().getFieldByLabelNumberAndLanguage(fv.getLabel_number(), language_id));
			}
	
			SearchResult sresult = new SearchResult();
			sresult.setObjectName(Fieldvalues.class.getName());
			sresult.setRecords(Fieldmanagment.getInstance().
						getFieldsValuesBySearchMaxResults(orderby, asc, search, language_id));
			sresult.setResult(listResult);
							
			log.debug("sresult ITEMS: "+sresult.getResult().size());
			log.debug("sresult RECORDS: "+sresult.getRecords());
		
			for (Fieldvalues fValue : listResult) {
				log.debug("diaObject: "+fValue.getName()+" "+fValue.getLabel_number()+" "+fValue.getFieldlanguagesvalue().getValue());
			}
			
		} catch (Exception err) {
			log.error("testgetDiagramObjectList: ",err);
		}
		
	}

}
