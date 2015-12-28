package org.i4change.app.data.mail;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.i4change.app.data.basic.Configurationmanagement;
import org.i4change.app.hibernate.beans.mail.MailItem;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class MailItemServiceDaoImpl extends HibernateDaoSupport {
	
	//Spring loaded beans
	private Configurationmanagement configurationmanagement;
	
	private static final Log log = LogFactory.getLog(MailItemServiceDaoImpl.class);	
	
	public Configurationmanagement getConfigurationmanagement() {
		return configurationmanagement;
	}
	public void setConfigurationmanagement(
			Configurationmanagement configurationmanagement) {
		this.configurationmanagement = configurationmanagement;
	}

	public List<MailItem> getMailItems() {
		try {
			
			Integer mailSpoolMaxNumberOfItemsPerSpool = Integer.valueOf(this.configurationmanagement.
					getConfKey(3, "mailSpoolMaxNumberOfItemsPerSpool").getConf_value()).intValue();
			
			String hql = "SELECT c FROM MailItem as c " +
					"WHERE c.send!=:send";

			Query query = getSession().createQuery(hql);
			query.setMaxResults(mailSpoolMaxNumberOfItemsPerSpool);
			query.setBoolean("send", true);
			List<MailItem> mailItemList = query.list();

			//log.debug("select assignee " + mailItemList);

			return mailItemList;
			
		} catch (HibernateException ex) {
			log.error("[getMailItems]",ex);
		} catch (Exception ex2) {
			log.error("[getMailItems]",ex2);
		}
		return null;
	}
	
	public MailItem getMailItemById(Long mailItemId) {
		try {
			
			log.debug("getMailItemById: "+mailItemId);
			
			String hql = "SELECT c FROM MailItem as c " +
					"WHERE c.mailItemId=:mailItemId";

			Query query = getSession().createQuery(hql);
			query.setLong("mailItemId", mailItemId);
			MailItem mailItem = (MailItem) query.uniqueResult();

			//log.debug("select assignee " + mailItem);

			return mailItem;
			
		} catch (HibernateException ex) {
			log.error("[getMailItemById]",ex);
		} catch (Exception ex2) {
			log.error("[getMailItemById]",ex2);
		}
		return null;
	}

	public Long addMailItem(Long user_id, String sender, String receipent, String subject, String content, String filePath) {
		try {
			
			MailItem mailItem = new MailItem();
			mailItem.setInserted(new Date());
			mailItem.setInsertedby(user_id);
			mailItem.setSender(sender);
			mailItem.setReceipent(receipent);
			mailItem.setSubject(subject);
			mailItem.setContent(content);
			mailItem.setFilePath(filePath);
			mailItem.setSend(false);

			Long mailItemId = (Long) getHibernateTemplate().save(mailItem);

			//log.debug("addAssgineeObject: " + mailItemId);

			return mailItemId;
		} catch (HibernateException ex) {
			log.error("[addMailItem]",ex);
		} catch (Exception ex2) {
			log.error("[addMailItem]",ex2);
		}
		return null;
	}
	
	public void updateMailItem(Long mailItemId, String deliverStatus) {
		try {
			
			MailItem mailItem = this.getMailItemById(mailItemId);
			mailItem.setUpdated(new Date());
			mailItem.setSend(true);
			mailItem.setDeliverStatus(deliverStatus);

			getHibernateTemplate().update(mailItem);

		} catch (HibernateException ex) {
			log.error("[updateMailItem]",ex);
		} catch (Exception ex2) {
			log.error("[updateMailItem]",ex2);
		}
	}
	
}
