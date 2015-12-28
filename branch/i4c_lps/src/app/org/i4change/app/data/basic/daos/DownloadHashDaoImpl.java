package org.i4change.app.data.basic.daos;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.i4change.app.hibernate.beans.basic.DownloadHash;
import org.i4change.app.hibernate.utils.HibernateUtil;
import org.i4change.app.utils.crypt.ManageCryptStyle;

public class DownloadHashDaoImpl {
	
	private static final Log log = LogFactory.getLog(DownloadHashDaoImpl.class);

	private static DownloadHashDaoImpl instance = null;
	
	public static synchronized DownloadHashDaoImpl getInstance() {
		if (instance == null) {
			instance = new DownloadHashDaoImpl();
		}
		return instance;
	}
	
	public String addDownloadHash(String fullFilePath){
		try {
			String myPass = "phrase"+(new Date()).getTime();
			String hash = ManageCryptStyle.getInstance().getInstanceOfCrypt().createPassPhrase(myPass);
			
			DownloadHash downloadHash = new DownloadHash();
			downloadHash.setAbsoluteFilePath(fullFilePath);
			downloadHash.setDownloaded(null);
			downloadHash.setInserted(new Date());
			downloadHash.setHash(hash);
			downloadHash.setUsed(false);

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Long downloadHashId = (Long) session.save(downloadHash);
			tx.commit();
			HibernateUtil.closeSession(idf);
			
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
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			session.update(downloadHash);
			tx.commit();
			HibernateUtil.closeSession(idf);
			
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
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Query query = session.createQuery(hql);
			Transaction tx = session.beginTransaction();
			query.setString("hash", hash);
			query.setBoolean("used", false);
			DownloadHash downloadHash = (DownloadHash) query.uniqueResult();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return downloadHash;				
		} catch (HibernateException ex) {
			log.error("[getDownloadHashByHash] ",ex);
		} catch (Exception ex2) {
			log.error("[getDownloadHashByHash] ",ex2);
		}
		return null;
	}
	
}
