package org.i4change.paypal.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DoJDBCConnection {

	public Connection getConnection() throws Exception {
		Connection cn = null;
		Properties properties = new Properties();
		properties.load(this.getClass().getClassLoader()  
				.getResourceAsStream("i4change.properties"));
		
		String driver = null;
		String dbUrl = null ;
		
	    //*** "Name des Datenbanktreibers eingeben
		driver = properties.get("i4change.driver").toString();
	    //*** "Url der Databank eingeben *********
	    //*** Server : linux
	    //*** Service-Nummer : 3306
	    //*** Bezeichnung der Datenbank : test1
	    dbUrl = properties.get("i4change.dbUrl").toString();
	    
	    //*** Treiber laden ***********************************
        Class.forName( driver ).newInstance();
        //*** Verbindung aufnehmen:    ************************
        //*** Der User peter mit Kennwort mysql mšcht was wissen
        cn = DriverManager.getConnection( dbUrl, 
        		properties.get("i4change.user").toString(), 
        		properties.get("i4change.pass").toString() );
        
	    return cn;
	  }

}
