package org.i4change.app.data.report.daos;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.hibernate.beans.project.Project;
import org.i4change.app.hibernate.beans.report.Report;
import org.i4change.app.hibernate.beans.report.ReportType;
import org.i4change.app.hibernate.utils.HibernateUtil;

public class ReportDaoImpl {
	
	private static final Log log = LogFactory.getLog(ReportDaoImpl.class);

	private static ReportDaoImpl instance = null;

	private ReportDaoImpl() { 
	}

	public static synchronized ReportDaoImpl getInstance() {
		if (instance == null) {
			instance = new ReportDaoImpl();
		}
		return instance;
	}
	
	public List<Report> getReportsToConvertByType(Long reportTypeId){
		try {
			
			String hql = "select c from Report as c " +
						"where c.type.reportTypeId = :reportTypeId " +
						"AND c.startProcessing IS NOT NULL " +
						"AND c.endProcessing IS NULL";

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("reportTypeId", reportTypeId);
			List<Report> reports = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return reports;
		} catch (HibernateException ex) {
			log.error("[getReportsToConvertByType]",ex);
		} catch (Exception ex2) {
			log.error("[getReportsToConvertByType]",ex2);
		}
		return null;
	}

	
	public Long addReport(String name, Long userId, ReportType reportType, 
			Project project, Date startDate, Date endDate){
		try {
			
			Report report = new Report();
			report.setName(name);
			report.setDeleted("false");
			report.setType(reportType);
			report.setProject(project);
			report.setOwner(UserDaoImpl.getInstance().getUserById(userId));
			report.setInsertedby(userId);
			report.setInserted(new Date());
			
			report.setStartProcessing(startDate);
			report.setEndProcessing(endDate);
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Long reportId = (Long) session.save(report);
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return reportId;
		} catch (HibernateException ex) {
			log.error("[addReport]",ex);
		} catch (Exception ex2) {
			log.error("[addReport]",ex2);
		}
		return null;
	}
	
	
	public void updateReport(Report report){
		try {
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			session.update(report);
			tx.commit();
			HibernateUtil.closeSession(idf);
			
		} catch (HibernateException ex) {
			log.error("[updateReport]",ex);
		} catch (Exception ex2) {
			log.error("[updateReport]",ex2);
		}
	}	

}
