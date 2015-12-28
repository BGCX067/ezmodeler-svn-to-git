package org.i4change.app.data.basic;

import java.util.Iterator;
import java.util.List;
import java.util.Date;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.i4change.app.hibernate.beans.basic.Naviglobal;
import org.i4change.app.hibernate.beans.basic.Navimain;
import org.i4change.app.hibernate.beans.basic.Navisub;
//import org.i4change.app.hibernate.utils.HibernateUtil;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class Navimanagement extends HibernateDaoSupport {

	private static final Log log = LogFactory.getLog(Navimanagement.class);

	//Spring loaded beans
	private Fieldmanagment fieldmanagment;

	public Fieldmanagment getFieldmanagment() {
		return fieldmanagment;
	}
	public void setFieldmanagment(Fieldmanagment fieldmanagment) {
		this.fieldmanagment = fieldmanagment;
	}

	public List getMainMenu(Long user_level, Long USER_ID, Long language_id) {
		List ll = this.getMainMenu(user_level, USER_ID);
		for (Iterator it2 = ll.iterator(); it2.hasNext();) {
			Naviglobal navigl = (Naviglobal) it2.next();
			navigl.setLabel(this.fieldmanagment.getFieldByLabelNumberAndLanguage(navigl.getLabel_number(),language_id));
			Set s = navigl.getMainnavi();
			for (Iterator it3 = s.iterator(); it3.hasNext();) {
				Navimain navim = (Navimain) it3.next();
				navim.setLabel(this.fieldmanagment.getFieldByLabelNumberAndLanguage(navim.getLabel_number(),language_id));
				for (Iterator it4 = navim.getSubnavi().iterator(); it4.hasNext();) {
					Navisub navis = (Navisub) it4.next();
					navis.setLabel(this.fieldmanagment.getFieldByLabelNumberAndLanguage(navis.getLabel_number(),language_id));
				}

			}
		}
		return ll;
	}

	public List<Naviglobal> getMainMenu(long user_level, long USER_ID) {
		try {
			
//			Object idf = HibernateUtil.createSession();
//			Session session = HibernateUtil.getSession();
//			Transaction tx = session.beginTransaction();
			// Criteria crit = session.createCriteria();
			Query query = getSession().createQuery("select c from Naviglobal as c " +
					"where c.level_id <= :level_id AND " +
					"c.deleted='false' " +
					"order by c.naviorder");
			query.setLong("level_id", user_level);
			List<Naviglobal> navi = query.list();

//			tx.commit();
//			HibernateUtil.closeSession(idf);
			
			log.error("getMainMenu "+navi.size());
			
			return navi;
		} catch (HibernateException ex) {
			log.error(ex);
		} catch (Exception ex2) {
			log.error(ex2);
		}
		return null;
	}

	public Long addGlobalStructure(String action, int naviorder,
			long fieldvalues_id, boolean isleaf, boolean isopen, long level_id,
			String name) {
		try {
			Naviglobal ng = new Naviglobal();
			ng.setAction(action);
			ng.setComment("");
			ng.setIcon("");
			ng.setNaviorder(naviorder);
			ng.setLabel_number(fieldvalues_id);
			ng.setIsleaf(isleaf);
			ng.setIsopen(isopen);
			ng.setDeleted("false");
			ng.setLevel_id(level_id);
			ng.setName(name);
			ng.setStarttime(new Date());

//			Object idf = HibernateUtil.createSession();
//			Session session = HibernateUtil.getSession();
//			Transaction tx = session.beginTransaction();
			// Criteria crit = session.createCriteria();

			Long global_id = (Long) getHibernateTemplate().save(ng);

//			tx.commit();
//			HibernateUtil.closeSession(idf);

			return global_id;
		} catch (HibernateException ex) {
			log.error(ex);
		} catch (Exception ex2) {
			log.error(ex2);
		}
		return null;
	}

	public Long addMainStructure(String action, int naviorder,
			long fieldvalues_id, boolean isleaf, boolean isopen, long level_id,
			String name, long global_id) {
		try {
			Navimain ng = new Navimain();
			ng.setAction(action);
			ng.setComment("");
			ng.setIcon("");
			ng.setLabel_number(fieldvalues_id);
			ng.setIsleaf(isleaf);
			ng.setNaviorder(naviorder);
			ng.setIsopen(isopen);
			ng.setLevel_id(level_id);
			ng.setName(name);
			ng.setDeleted("false");
			ng.setGlobal_id(global_id);
			ng.setStarttime(new Date());

//			Object idf = HibernateUtil.createSession();
//			Session session = HibernateUtil.getSession();
//			Transaction tx = session.beginTransaction();
			// Criteria crit = session.createCriteria();

			Long main_id = (Long) getHibernateTemplate().save(ng);

//			tx.commit();
//			HibernateUtil.closeSession(idf);

			return main_id;
		} catch (HibernateException ex) {
			log.error(ex);
		} catch (Exception ex2) {
			log.error(ex2);
		}
		return null;
	}

	public void addSubStructure(String action, int naviorder,
			long fieldvalues_id, boolean isleaf, boolean isopen, long level_id,
			String name, long main_id) {
		try {
			Navisub ng = new Navisub();
			ng.setAction(action);
			ng.setComment("");
			ng.setIcon("");
			ng.setNaviorder(naviorder);
			ng.setLabel_number(fieldvalues_id);
			ng.setIsleaf(isleaf);
			ng.setIsopen(isopen);
			ng.setLevel_id(level_id);
			ng.setName(name);
			ng.setDeleted("false");
			ng.setMain_id(main_id);
			ng.setStarttime(new Date());

//			Object idf = HibernateUtil.createSession();
//			Session session = HibernateUtil.getSession();
//			Transaction tx = session.beginTransaction();
			// Criteria crit = session.createCriteria();

			Long sub_id = (Long) getHibernateTemplate().save(ng);

//			tx.commit();
//			HibernateUtil.closeSession(idf);

		} catch (HibernateException ex) {
			log.error(ex);
		} catch (Exception ex2) {
			log.error(ex2);
		}
	}
}
