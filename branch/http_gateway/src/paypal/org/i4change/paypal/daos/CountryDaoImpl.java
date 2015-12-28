package org.i4change.paypal.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.paypal.beans.Country;

public class CountryDaoImpl extends DoJDBCConnection {

	private static final Log log = LogFactory.getLog(CountryDaoImpl.class);

	private static CountryDaoImpl instance = null;

	private CountryDaoImpl() {
	}

	public static synchronized CountryDaoImpl getInstance() {
		if (instance == null) {
			instance = new CountryDaoImpl();
		}
		return instance;
	}

	public List<Country> getCountryList() {
		PreparedStatement pstmt = null;
		Connection cn = null;
		ResultSet rs = null;
		LinkedList<Country> countryList = new LinkedList<Country>();
		
		try {
			
			
			log.debug("SELECT getCountryList: ");

			cn = this.getConnection();
			
			String selectSql = "SELECT name,paypal FROM country WHERE paypal != ''";
			
			log.debug("SELECT selectSql: "+selectSql);
			
	        pstmt = cn.prepareStatement(selectSql);
	        
	        
	        rs = pstmt.executeQuery();
	        // extract data from the ResultSet
	        while (rs.next()) {
	        	countryList.add(new Country(rs.getString(1),rs.getString(2)));
	        }
            
	        rs.close();
	        pstmt.close();
        	cn.close(); 
        	
            //ResultSetMetaData rsmd = rs.getMetaData();
            //int nmax = rsmd.getColumnCount(); 
		    
		} catch (Exception err) {
			log.error("[getCountryList]",err);
		}
		
		return countryList;
	}
}
