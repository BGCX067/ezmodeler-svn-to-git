package org.i4change.paypal.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FieldlanguagesvaluesDaoImpl extends DoJDBCConnection {

	private static final Log log = LogFactory.getLog(FieldlanguagesvaluesDaoImpl.class);

	private static FieldlanguagesvaluesDaoImpl instance = null;

	private FieldlanguagesvaluesDaoImpl() {
	}

	public static synchronized FieldlanguagesvaluesDaoImpl getInstance() {
		if (instance == null) {
			instance = new FieldlanguagesvaluesDaoImpl();
		}
		return instance;
	}

	public String getLabelStringByNumberAndLang(Long label_number, Long language_id) {
		PreparedStatement pstmt = null;
		Connection cn = null;
		ResultSet rs = null;
		String value = "";
		
		try {
			
			
			log.debug("SELECT value fieldlanguagesvalues: "+label_number+" "+language_id);

			cn = this.getConnection();
			
			String selectSql = "SELECT value FROM fieldlanguagesvalues " +
					"where label_number = ? AND language_id = ?";
			
	        pstmt = cn.prepareStatement(selectSql);
	        
	        
	        pstmt.setLong(1, label_number);
	        pstmt.setLong(2, language_id);
	        
	        rs = pstmt.executeQuery();
	        // extract data from the ResultSet
	        while (rs.next()) {
	        	value = rs.getString(1);
	        }
            
	        rs.close();
	        pstmt.close();
        	cn.close(); 
        	
            //ResultSetMetaData rsmd = rs.getMetaData();
            //int nmax = rsmd.getColumnCount(); 
		    
		} catch (Exception err) {
			log.error("[addTransaction]",err);
		}
		
		return value;
	}
	
}
