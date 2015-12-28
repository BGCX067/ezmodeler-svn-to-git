package org.i4change.paypal.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.paypal.beans.Transaction;

public class TransactionDaoImpl extends DoJDBCConnection {

	private static final Log log = LogFactory.getLog(TransactionDaoImpl.class);

	private static TransactionDaoImpl instance = null;

	private TransactionDaoImpl() {
	}

	public static synchronized TransactionDaoImpl getInstance() {
		if (instance == null) {
			instance = new TransactionDaoImpl();
		}
		return instance;
	}
	
	public void addTransaction(String transActionId, String amount, Date transferDate, String status, Long userId) {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection cn = null;
		int numberOfRows = 0;
		
		try {
			
			String countSql = "select count(*) from `transaction`";
			
			String insertSql = "INSERT INTO transaction " +
					"(transaction_id,transactionpaypalid,amount,paiddate,status,inserted,user_id) " +
					"values(?, ?, ?, ?, ?, ?, ?)";
			
			cn = this.getConnection();
	        
	        pstmt = cn.prepareStatement(countSql);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	          numberOfRows = rs.getInt(1);
	          log.debug("numberOfRows= " + numberOfRows);
	        } else {
	          log.error("error: could not get the record counts");
	        }
	         
	        pstmt = cn.prepareStatement(insertSql);
	        
	        
	        pstmt.setInt(1, numberOfRows+1);
	        pstmt.setString(2, transActionId);
	        pstmt.setString(3, amount);
	        //pstmt.setTimestamp(parameterIndex, x)
	        pstmt.setTimestamp(4, new java.sql.Timestamp(transferDate.getTime()));
	        pstmt.setString(5, status);
	        pstmt.setTimestamp(6, new java.sql.Timestamp((new Date()).getTime()));
	        pstmt.setLong(7, userId);
	        
	        pstmt.execute();
            
	        pstmt.close();
        	cn.close(); 
        	
            //ResultSetMetaData rsmd = rs.getMetaData();
            //int nmax = rsmd.getColumnCount(); 
		    
		} catch (Exception err) {
			log.error("[addTransaction]",err);
		}
	}
	
	public void addAndUpdateTransaction(Long transaction_id, String transactionpaypalid, String status, 
			String amount, Date transferDate, String firstName, String lastName,
			String address1, String address2, String city, String state, String zip, String countryCode,
			String firstNameBilling, String lastNameBilling) {
		
		PreparedStatement pstmt = null;
		Connection cn = null;
		
		try {
			
			log.debug("addAndUpdateTransaction transActionId: "+transaction_id);

			cn = this.getConnection();
			
			String updateSql = "UPDATE transaction set " +
					"transactionpaypalid = ?, status = ?, amount = ?, paiddate = ?, inserted = ?," +
					"firstName = ?, lastName = ?, address_1 = ?, address_2 = ?, city = ?, " +
					"state = ?, zip = ?, countryCode = ?, firstNameBilling = ?, lastNameBilling = ? " +
					"where transaction_id = ?";
			
//			updateSql = "UPDATE transaction set " +
//						"status = ? ";
			
	        pstmt = cn.prepareStatement(updateSql);
	        
	        
	        pstmt.setString(1, transactionpaypalid);
	        pstmt.setString(2, status);
	        pstmt.setString(3, amount);
	        pstmt.setTimestamp(4, new java.sql.Timestamp(transferDate.getTime()));
	        pstmt.setTimestamp(5, new java.sql.Timestamp((new Date()).getTime()));
	        
	        pstmt.setString(6, firstName);
	        pstmt.setString(7, lastName);
	        pstmt.setString(8, address1);
	        pstmt.setString(9, address2);
	        pstmt.setString(10, city);
	        pstmt.setString(11, state);
	        pstmt.setString(12, zip);
	        pstmt.setString(13, countryCode);
	        pstmt.setString(14, firstNameBilling);
	        pstmt.setString(15, lastNameBilling);
	        
	        pstmt.setLong(16, transaction_id);
	        
	        pstmt.executeUpdate();
            
	        pstmt.close();
        	cn.close(); 
        	
            //ResultSetMetaData rsmd = rs.getMetaData();
            //int nmax = rsmd.getColumnCount(); 
		    
		} catch (Exception err) {
			log.error("[addTransaction]",err);
		}
	}
	
	public void updateTransaction(String transActionId, String status) {
		
		PreparedStatement pstmt = null;
		Connection cn = null;
		
		try {
			
			log.debug("UPDATE TRANSACTION transActionId: "+transActionId);

			cn = this.getConnection();
			
			String updateSql = "UPDATE transaction set " +
					"status = ? " +
					"where transactionpaypalid LIKE ?";
			
//			updateSql = "UPDATE transaction set " +
//						"status = ? ";
			
	        pstmt = cn.prepareStatement(updateSql);
	        
	        
	        pstmt.setString(1, status);
	        pstmt.setString(2, transActionId);
	        
	        pstmt.executeUpdate();
            
	        pstmt.close();
        	cn.close(); 
        	
            //ResultSetMetaData rsmd = rs.getMetaData();
            //int nmax = rsmd.getColumnCount(); 
		    
		} catch (Exception err) {
			log.error("[addTransaction]",err);
		}
	}
	
	public Transaction getTransactionTransactionById(Long transaction_id) {
		PreparedStatement pstmt = null;
		Connection cn = null;
		ResultSet rs = null;
		Transaction transaction = new Transaction();
		
		try {
			
			
			log.debug("SELECT TRANSACTION transActionId: "+transaction_id);

			cn = this.getConnection();
			
			String selectSql = "SELECT address_1,address_2,city,countryCode,state,firstName,lastName," +
					"amountstarttransaction,zip FROM transaction " +
					"where transaction_id = ?";
			
//			updateSql = "UPDATE transaction set " +
//						"status = ? ";
			
			log.debug("SELECT selectSql: "+selectSql);
			
	        pstmt = cn.prepareStatement(selectSql);
	        
	        
	        pstmt.setLong(1, transaction_id);
	        
	        rs = pstmt.executeQuery();
	        // extract data from the ResultSet
	        while (rs.next()) {
	        	transaction.setAddress_1(rs.getString(1));
	        	transaction.setAddress_2(rs.getString(2));
	        	transaction.setCity(rs.getString(3));
	        	transaction.setCountryCode(rs.getString(4));
	        	transaction.setState(rs.getString(5));
	        	transaction.setFirstName(rs.getString(6));
	        	transaction.setLastName(rs.getString(7));
	        	transaction.setAmountstarttransaction(rs.getString(8));
	        	transaction.setZip(rs.getString(9));
	        }
            
	        rs.close();
	        pstmt.close();
        	cn.close(); 
        	
            //ResultSetMetaData rsmd = rs.getMetaData();
            //int nmax = rsmd.getColumnCount(); 
		    
		} catch (Exception err) {
			log.error("[getTransactionAmountbyTransactionId]",err);
		}
		
		return transaction;
	}

}
