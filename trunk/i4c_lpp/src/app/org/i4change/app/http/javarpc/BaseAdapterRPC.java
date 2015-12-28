package org.i4change.app.http.javarpc;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openlaszlo.data.helpers.ILaszloRPCAdapter;
import org.openlaszlo.data.helpers.LaszloRPCAdapter;
import org.openlaszlo.data.helpers.LaszloTypeMapping;

/**
 * @author sebastianwagner
 *
 */
public class BaseAdapterRPC extends LaszloRPCAdapter implements ILaszloRPCAdapter {
	
	private static final Log log = LogFactory.getLog(BaseAdapterRPC.class);

	public BaseAdapterRPC() {
		
		log.debug("#### getCustomMappings ####");
		
		//regular Java Types
		this.addCustomMappings(new LaszloTypeMapping("java.lang.Integer","parseInt"));
		this.addCustomMappings(new LaszloTypeMapping("java.lang.Long","parseLong"));
		this.addCustomMappings(new LaszloTypeMapping("java.util.Map","parseToMap"));
		this.addCustomMappings(new LaszloTypeMapping("java.util.LinkedHashMapMap","parseToMap"));
		
		this.addCustomMappings(new LaszloTypeMapping("java.lang.String","parseToString"));
		
	}
	

	/* (non-Javadoc)
	 * @see org.openlaszlo.data.OpenLaszloRPCAdapter#onCall(java.lang.String, java.lang.Class[], java.lang.Object[])
	 */
	public void onCall(String methodName, Class[] argClasses, Object[] argValues) {
		// TODO Auto-generated method stub
		super.onCall(methodName, argClasses, argValues);
	}
	
	
	public Integer parseInt(Object obj) {
		try {
			if (obj == null || obj.equals(null)) return null;
			return Integer.valueOf(""+obj).intValue();
		} catch (Exception err) {
			log.error("[parseInt]",err);
		}
		return null;
	}
	
	public Long parseLong(Object obj) {
		try {
			if (obj == null || obj.equals(null)) return null;
			//log.debug("parseLong obj: "+obj);
			return Long.valueOf(""+obj).longValue();
		} catch (Exception err) {
			log.error("[parseLong]",err);
		}
		return null;
	}
	 
	public Map parseToMap(Object obj) {
		try {
			
			if (Hashtable.class.isInstance(obj)) {
				Hashtable table = (Hashtable) obj;
				log.debug("Is Table");
				Map m = new HashMap();
				for (Iterator iter = table.keySet().iterator();iter.hasNext();) {
					Object key = iter.next();
					m.put(key, table.get(key));
				}
				
				return m;
			} else {
				log.debug("Is No Table: "+obj);
			}
			
		} catch (Exception err) {
			log.error("[parseToMap]",err);
		}
		return null;
	}	
	
	public String parseToString(Object obj) {
		try {
			return obj.toString();
		} catch (Exception err) {
			log.error("[parseToString]",err);
		}
		return null;
	}

}
