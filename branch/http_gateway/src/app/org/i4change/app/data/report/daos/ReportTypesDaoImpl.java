package org.i4change.app.data.report.daos;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.i4change.app.hibernate.beans.report.ReportType;
import org.i4change.app.hibernate.utils.HibernateUtil;

public class ReportTypesDaoImpl {
	
	private static final Log log = LogFactory.getLog(ReportTypesDaoImpl.class);

	private static ReportTypesDaoImpl instance = null;

	private ReportTypesDaoImpl() {
	}

	public static synchronized ReportTypesDaoImpl getInstance() {
		if (instance == null) {
			instance = new ReportTypesDaoImpl();
		}
		return instance;
	}
	
	public Long addReportType(String name){
		try {
			
			ReportType reportType = new ReportType();
			reportType.setName(name);
			reportType.setInserted(new Date());
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Long reportTypeId = (Long) session.save(reportType);
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return reportTypeId;
		} catch (HibernateException ex) {
			log.error("[addReportType]",ex);
		} catch (Exception ex2) {
			log.error("[addReportType]",ex2);
		}
		return null;
	}
	
	public ReportType getReportTypeById(Long reportTypeId){
		try {
			
			String hql = "select c from ReportType as c " +
						"where c.reportTypeId = :reportTypeId ";

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("reportTypeId", reportTypeId);
			ReportType reportType = (ReportType) query.uniqueResult();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return reportType;
		} catch (HibernateException ex) {
			log.error("[getReportTypeById]",ex);
		} catch (Exception ex2) {
			log.error("[getReportTypeById]",ex2);
		}
		return null;
	}


}
