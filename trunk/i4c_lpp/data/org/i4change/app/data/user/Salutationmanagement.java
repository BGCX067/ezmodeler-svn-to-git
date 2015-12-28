package org.i4change.app.data.user;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Criteria;
import org.i4change.app.hibernate.beans.user.Salutations;

import org.i4change.app.data.basic.Fieldmanagment;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * 
 * @author swagner
 *
 */
public class Salutationmanagement extends HibernateDaoSupport {

	private static final Log log = LogFactory
			.getLog(Salutationmanagement.class);

	//Spring loaded beans
	private Fieldmanagment fieldmanagment;

	public Fieldmanagment getFieldmanagment() {
		return fieldmanagment;
	}
	public void setFieldmanagment(Fieldmanagment fieldmanagment) {
		this.fieldmanagment = fieldmanagment;
	}
	
	/**
	 * Adds a new Salutation to the table Titles
	 * @param titelname
	 */
	public Long addUserSalutation(String titelname, long fieldvalues_id) {
		try {
			Salutations ti = new Salutations();
			ti.setName(titelname);
			ti.setDeleted("false");
			ti.setFieldvalues_id(fieldvalues_id);
			ti.setStarttime(new Date());
			Long salutations_id = (Long)getHibernateTemplate().save(ti);
			
			return salutations_id;
		} catch (HibernateException ex) {
			log.error("[addUserSalutation]" ,ex);
		} catch (Exception ex2) {
			log.error("[addUserSalutation]" ,ex2);
		}
		return null;
	}
	
	/**
	 * get a list of all availible Salutations
	 * @param user_level
	 * @return
	 */
	public List<Salutations> getUserSalutations(long language_id){
		try {
			Criteria crit = getSession().createCriteria(Salutations.class);
			List<Salutations> ll = crit.list();
			for (Iterator it4 = ll.iterator(); it4.hasNext();) {
				Salutations ti = (Salutations) it4.next();
				ti.setLabel(this.fieldmanagment.getFieldByLabelNumberAndLanguage(ti.getFieldvalues_id(),language_id));
			}
			
			
			return ll;
		} catch (HibernateException ex) {
			log.error("[addUserSalutation]" ,ex);
		} catch (Exception ex2) {
			log.error("[addUserSalutation]" ,ex2);
		}
		return null;
	}

}
