package org.i4change.app.data.basic.daos;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.i4change.app.hibernate.beans.basic.DownloadHash;
import org.i4change.app.utils.crypt.ManageCryptStyle;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class DownloadHashDaoImpl extends HibernateDaoSupport {
	
	private static final Log log = LogFactory.getLog(DownloadHashDaoImpl.class);
	
	private ManageCryptStyle manageCryptStyle;

//	private static DownloadHashDaoImpl instance = null;
//	
//	public static synchronized DownloadHashDaoImpl getInstance() {
//		if (instance == null) {
//			instance = new DownloadHashDaoImpl();
//		}
//		return instance;
//	}
//	
	public ManageCryptStyle getManageCryptStyle() {
		return manageCryptStyle;
	}
	public void setManageCryptStyle(ManageCryptStyle manageCryptStyle) {
		this.manageCryptStyle = manageCryptStyle;
	}

	public String addDownloadHash(String fullFilePath){
		try {
			String myPass = "phrase"+(new Date()).getTime();
			String hash = this.manageCryptStyle.getInstanceOfCrypt().createPassPhrase(myPass);
			
			DownloadHash downloadHash = new DownloadHash();
			downloadHash.setAbsoluteFilePath(fullFilePath);
			downloadHash.setDownloaded(null);
			downloadHash.setInserted(new Date());
			downloadHash.setHash(hash);
			downloadHash.setUsed(false);

			Long downloadHashId = (Long) getHibernateTemplate().save(downloadHash);
			
			return hash;
			
		} catch (HibernateException ex) {
			log.error("[addDownloadHash]",ex);
		} catch (Exception ex2) {
			log.error("[addDownloadHash]",ex2);
		}
		return null;
	}
	
	public void updateDownloadHash(DownloadHash downloadHash){
		try {
			
			//get all users of this Organization
			getHibernateTemplate().update(downloadHash);
			
		} catch (HibernateException ex) {
			log.error("[updateDownloadHash] ",ex);
		} catch (Exception ex2) {
			log.error("[updateDownloadHash] ",ex2);
		}
	}

	public DownloadHash getDownloadHashByHash(String hash){
		try {
		
			String hql = "SELECT c FROM DownloadHash c " +
					"WHERE c.hash LIKE :hash AND c.used = :used";
			
			//get all users of this Organization
			Query query = getSession().createQuery(hql);
			query.setString("hash", hash);
			query.setBoolean("used", false);
			DownloadHash downloadHash = (DownloadHash) query.uniqueResult();
			
			return downloadHash;				
		} catch (HibernateException ex) {
			log.error("[getDownloadHashByHash] ",ex);
		} catch (Exception ex2) {
			log.error("[getDownloadHashByHash] ",ex2);
		}
		return null;
	}
	
}
