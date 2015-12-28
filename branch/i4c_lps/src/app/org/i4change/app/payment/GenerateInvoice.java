package org.i4change.app.payment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.Writer;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.Configurationmanagement;
import org.i4change.app.data.user.CountryDaoImpl;
import org.i4change.app.data.user.daos.InvoiceDaoImpl;
import org.i4change.app.data.user.daos.TransactionPaypalDaoImpl;
import org.i4change.app.documents.GenerateDocument;
import org.i4change.app.hibernate.beans.adresses.Country;
import org.i4change.app.hibernate.beans.basic.Configuration;
import org.i4change.app.hibernate.beans.user.Invoice;
import org.i4change.app.hibernate.beans.user.TransactionPaypal;
import org.i4change.app.remote.Application;
import org.i4change.app.utils.math.CalendarPatterns;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class GenerateInvoice {
	
	private static final Log log = LogFactory.getLog(GenerateInvoice.class);
	
	private static GenerateInvoice instance;

	private GenerateInvoice() {}

	public static synchronized GenerateInvoice getInstance() {
		if (instance == null) {
			instance = new GenerateInvoice();
		}
		return instance;
	}
	
	public String generateInvoiceByTransaction(Long transactionId) {
		try {
		
			Configuration configuration = Configurationmanagement.getInstance().getConfKey(3L, "invoiceNumber");
			Long invoiceNumber = Long.valueOf(configuration.getConf_value()).longValue();
			log.debug("pattern invoiceNumber "+invoiceNumber);
			configuration.setConf_value(""+(invoiceNumber+1));
			
			TransactionPaypal transactionPaypal = TransactionPaypalDaoImpl.getInstance().getTransactionPayedById(transactionId);
			
			Long userId = transactionPaypal.getUsers().getUser_id();
			transactionPaypal.setUsers(null);
			
			Invoice invoice = new Invoice();
			invoice.setInvoiceNumber(invoiceNumber);
			invoice.setInvoiceString(""+CalendarPatterns.getYear(new Date())+"-"+invoiceNumber);
			invoice.setTransactionPaypal(transactionPaypal);
			invoice.setInserted(new Date());
			invoice.setInsertedFormated(CalendarPatterns.getDateByMiliSeconds(new Date()));
			
			if (transactionPaypal.getNumberOfLicenses() == 1) {
				Configuration invoiceDescriptionSingleUser = Configurationmanagement.getInstance().getConfKey(3L, "invoiceDescriptionSingleUser");
				invoice.setDescription(invoiceDescriptionSingleUser.getConf_value());
			} else {
				Configuration invoiceDescriptionMultiUser = Configurationmanagement.getInstance().getConfKey(3L, "invoiceDescriptionMultiUser");
				invoice.setDescription(invoiceDescriptionMultiUser.getConf_value());
			}
			
			Country country = CountryDaoImpl.getInstance().getCountryByPaypalCode(transactionPaypal.getCountryCode());
			if (country != null) {
				transactionPaypal.setCountryAsName(country.getName());
			}
			
			XStream xStream = new XStream(new XppDriver());
			xStream.setMode(XStream.NO_REFERENCES);
			xStream.alias("invoice", Invoice.class);
			String transactionAsXmlString = xStream.toXML(invoice);
			
			String requestedFile = "Invoice_"+CalendarPatterns.getTimeForStreamId(new Date());
			
			String destinationFolder = Application.webAppPath + File.separatorChar 
										+ "upload" + File.separatorChar 
										+ "user_"+userId + File.separatorChar;
			
			File userDir = new File(destinationFolder);
			if (!userDir.exists()) {
				userDir.mkdir();
			}
			invoice.setMergedXmlData_FileName(requestedFile + ".xml");
			String fileFullPath_mergeXML = destinationFolder + requestedFile + ".xml";
			
			log.debug("##### WRITE XML TO: "+fileFullPath_mergeXML);
			
			Writer out = new BufferedWriter(new OutputStreamWriter(
	            new FileOutputStream(fileFullPath_mergeXML), "UTF8"));
	        out.write(transactionAsXmlString);
	        out.close();

			String fileFullPath_template = Application.webAppPath + File.separatorChar
										+ "WEB-INF" + File.separatorChar 
										+ "classes" + File.separatorChar 
										+ "invoice_template.odt";
			
			String fileFullPath_output = destinationFolder + requestedFile + ".pdf";
			
			invoice.setInvoiceFileName(requestedFile + ".pdf");
			
			GenerateDocument.getInstance().doConvertExec(Application.webAppPath + File.separatorChar, 
						fileFullPath_template, fileFullPath_output, fileFullPath_mergeXML);
			
			
			Configurationmanagement.getInstance().updateConfig(configuration);
			InvoiceDaoImpl.getInstance().addInvoice(invoice);
		
			return fileFullPath_output;
		} catch (Exception err) {
			log.error("[generateInvoiceByTransaction]",err);
		}
		return null;
	}

}
