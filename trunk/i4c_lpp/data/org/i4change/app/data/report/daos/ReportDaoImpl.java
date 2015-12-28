package org.i4change.app.data.report.daos;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.hibernate.beans.project.Project;
import org.i4change.app.hibernate.beans.report.Report;
import org.i4change.app.hibernate.beans.report.ReportType;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class ReportDaoImpl extends HibernateDaoSupport {
	
	private static final Log log = LogFactory.getLog(ReportDaoImpl.class);

	//Spring loaded Beans
	private UserDaoImpl userDaoImpl;
	
	public UserDaoImpl getUserDaoImpl() {
		return userDaoImpl;
	}
	public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}
	
	public List<Report> getReportsToConvertByType(Long reportTypeId){
		try {
			
			String hql = "select c from Report as c " +
						"where c.type.reportTypeId = :reportTypeId " +
						"AND c.startProcessing IS NOT NULL " +
						"AND c.endProcessing IS NULL";

			Query query = getSession().createQuery(hql);
			query.setLong("reportTypeId", reportTypeId);
			List<Report> reports = query.list();
			
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
			report.setOwner(this.userDaoImpl.getUserById(userId));
			report.setInsertedby(userId);
			report.setInserted(new Date());
			
			report.setStartProcessing(startDate);
			report.setEndProcessing(endDate);
			
			Long reportId = (Long) getHibernateTemplate().save(report);
			
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
			
			getHibernateTemplate().update(report);
			
		} catch (HibernateException ex) {
			log.error("[updateReport]",ex);
		} catch (Exception ex2) {
			log.error("[updateReport]",ex2);
		}
	}	

}
