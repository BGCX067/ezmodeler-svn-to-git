package org.i4change.app.data.report.daos;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.i4change.app.hibernate.beans.report.ReportType;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class ReportTypesDaoImpl extends HibernateDaoSupport {
	
	private static final Log log = LogFactory.getLog(ReportTypesDaoImpl.class);

	public Long addReportType(String name){
		try {
			
			ReportType reportType = new ReportType();
			reportType.setName(name);
			reportType.setInserted(new Date());
			
			Long reportTypeId = (Long) getHibernateTemplate().save(reportType);
			
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

			Query query = getSession().createQuery(hql);
			query.setLong("reportTypeId", reportTypeId);
			ReportType reportType = (ReportType) query.uniqueResult();
			
			return reportType;
		} catch (HibernateException ex) {
			log.error("[getReportTypeById]",ex);
		} catch (Exception ex2) {
			log.error("[getReportTypeById]",ex2);
		}
		return null;
	}


}
