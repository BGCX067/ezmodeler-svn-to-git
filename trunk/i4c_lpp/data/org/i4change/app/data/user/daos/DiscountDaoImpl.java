package org.i4change.app.data.user.daos;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.i4change.app.hibernate.beans.user.Discount;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

//import com.thoughtworks.xstream.XStream;
//import com.thoughtworks.xstream.io.xml.XppDriver;

public class DiscountDaoImpl extends HibernateDaoSupport {

	private static final Log log = LogFactory.getLog(DiscountDaoImpl.class);

	//Spring loaded Beans
	private UserDaoImpl userDaoImpl;
	
	public UserDaoImpl getUserDaoImpl() {
		return userDaoImpl;
	}
	public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}
	
	public Discount getDiscountById(Long discountId) {
		try {
			
			String hql = "select c from Discount as c " +
						"where c.discountId = :discountId";
	
			Query query = getSession().createQuery(hql);
			query.setLong("discountId", discountId);
			Discount discount = (Discount) query.uniqueResult();
			
			return discount;
			// TODO: Add Usergroups to user
			// users.setUsergroups(ResHandler.getGroupmanagement().getUserGroups(user_id));
		} catch (HibernateException ex) {
			log.error("[getDiscountById]",ex);
		} catch (Exception ex2) {
			log.error("[getDiscountById]",ex2);
		}
		return null;
	}
	

	public List<Discount> getDiscountsByUser(Long userId) {
		try {
			
			String hql = "select c from Discount as c " +
						"where c.user.user_id = :userId " +
						"AND c.deleted!=:deleted ";
	
			Query query = getSession().createQuery(hql);
			query.setLong("userId", userId);
			query.setString("deleted", "true");
			List<Discount> discounts = query.list();
			
			return discounts;
		} catch (HibernateException ex) {
			log.error("[getDiscountsByUser]",ex);
		} catch (Exception ex2) {
			log.error("[getDiscountsByUser]",ex2);
		}
		return null;
	}
	
	public Long addDiscountByUser(Long userId, Double discountGranted, Integer numberOfUsers){
		try {
			
			log.debug("addDiscountByUser "+userId+" "+discountGranted+" "+numberOfUsers);
			
			Discount discount = new Discount();
			discount.setUser(this.userDaoImpl.getUserById(userId));
			discount.setDiscount(discountGranted);
			discount.setNumberOfUsers(numberOfUsers);
			discount.setInserted(new Date());
			discount.setDeleted("false");
			
			Long discountId = (Long) getHibernateTemplate().save(discount);

			return discountId;
			// TODO: Add Usergroups to user
			// users.setUsergroups(ResHandler.getGroupmanagement().getUserGroups(user_id));
		} catch (HibernateException ex) {
			log.error("[addDiscountByUser]",ex);
		} catch (Exception ex2) {
			log.error("[addDiscountByUser]",ex2);
		}
		return null;
	}

	public void updateDiscountByUser(Long discountId, Long userId, Double discountGranted, Integer numberOfUsers){
		try {
			
			Discount discount = this.getDiscountById(discountId);
			discount.setUser(this.userDaoImpl.getUserById(userId));
			discount.setDiscount(discountGranted);
			discount.setNumberOfUsers(numberOfUsers);
			discount.setUpdated(new Date());
			
			this.updateDiscount(discount);
			
			// TODO: Add Usergroups to user
			// users.setUsergroups(ResHandler.getGroupmanagement().getUserGroups(user_id));
		} catch (Exception ex2) {
			log.error("[updateDiscountByUser]",ex2);
		}
	}
	public void deleteDiscount(Discount discount){
		try {
			
			discount.setDeleted("true");
			
			this.updateDiscount(discount);
			
			// TODO: Add Usergroups to user
			// users.setUsergroups(ResHandler.getGroupmanagement().getUserGroups(user_id));
		} catch (Exception ex2) {
			log.error("[deleteDiscount]",ex2);
		}
	}
	
	public void updateDiscount(Discount discount){
		try {
			
			getHibernateTemplate().update(discount);
			
			// TODO: Add Usergroups to user
			// users.setUsergroups(ResHandler.getGroupmanagement().getUserGroups(user_id));
		} catch (HibernateException ex) {
			log.error("[updateDiscount]",ex);
		} catch (Exception ex2) {
			log.error("[updateDiscount]",ex2);
		}
	}
	
	public void saveOrUpdateDiscountsListByUser(Vector discounts, Long userId) {
		try {
			
			log.debug("saveOrUpdateDiscountsListByUser"+userId);
			
			List<Discount> userDiscounts = this.getDiscountsByUser(userId);
			List<Discount> userDiscountsFound = new LinkedList<Discount>();
			
			for (Iterator iter = discounts.iterator();iter.hasNext();) {
				Object key = iter.next();
				log.debug(key);
				Map discountObject = (Map) key;
				log.debug(discountObject);
				Long discountsId = Long.valueOf(discountObject.get("discountId").toString()).longValue();
				if (discountsId == 0) {
					Double discountGranted = Double.valueOf(discountObject.get("discount").toString()).doubleValue();
					Integer numberOfUsers = Integer.valueOf(discountObject.get("numberOfUsers").toString()).intValue();
					this.addDiscountByUser(userId, discountGranted, numberOfUsers);
				}
				
				for (Discount discount : userDiscounts) {
					if (discount.getDiscountId() == discountsId) {
						userDiscountsFound.add(discount);
						break;
					}
				}
			}
			
			//Eliminate unused
			for (Discount discount : userDiscounts) {
				boolean found = false;
				for (Discount discountR : userDiscountsFound) {
					if (discountR.getDiscountId() == discount.getDiscountId()){
						found = true;
						break;
					}
				}
				if (!found) {
					this.deleteDiscount(discount);
				}
			}
			
		} catch (Exception ex2) {
			log.error("[saveOrUpdateDiscountsListByUser]",ex2);
		}
	}

}
