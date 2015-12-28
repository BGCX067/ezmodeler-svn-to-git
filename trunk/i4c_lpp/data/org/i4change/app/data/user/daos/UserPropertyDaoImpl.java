package org.i4change.app.data.user.daos;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.i4change.app.dto.user.UserPropertyDTO;
import org.i4change.app.hibernate.beans.user.UserProperty;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class UserPropertyDaoImpl extends HibernateDaoSupport {
	
	private static final Log log = LogFactory.getLog(UserPropertyDaoImpl.class);

	public UserProperty getUserSidebarPropertyByKey(String key, Long user_id) {
		try {
			
			String hql = "select c from UserProperty as c " +
							"where c.keyItem = :key AND c.user_id = :user_id";
	
			Query query = getSession().createQuery(hql);
			query.setString("key", key);
			query.setLong("user_id", user_id);
			UserProperty userProperty = (UserProperty) query.uniqueResult();
			
			return userProperty;
		} catch (HibernateException ex) {
			log.error("[getUserSidebarPropertyByKey]",ex);
		} catch (Exception ex2) {
			log.error("[getUserSidebarPropertyByKey]",ex2);
		}
		return null;
	}
	
	public List<UserProperty> getUserSidebarPropertyByUser(Long user_id) {
		try {
			
			String hql = "select c from UserProperty as c " +
							"where c.user_id = :user_id";

			Query query = getSession().createQuery(hql);
			query.setLong("user_id", user_id);
			List<UserProperty> userProperties = query.list();
			
			return userProperties;
		} catch (HibernateException ex) {
			log.error("[getUserSidebarPropertyByUser]",ex);
		} catch (Exception ex2) {
			log.error("[getUserSidebarPropertyByUser]",ex2);
		}
		return null;
	}
	
	public List<UserPropertyDTO> getUserSidebarPropertyDTOByUser(Long user_id) throws Exception {
			
		List<UserPropertyDTO> userPropsDTO = new LinkedList<UserPropertyDTO>();
		List<UserProperty> userProps = this.getUserSidebarPropertyByUser(user_id);
		
		XStream xStream = new XStream(new XppDriver());
		xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
		
		for (int i=0;i<userProps.size();i++){
			UserProperty userProp = userProps.get(i);
			UserPropertyDTO userPropDTO = new UserPropertyDTO();
			
			userPropDTO.setUserPropertyId(userProp.getUserPropertyId());
			userPropDTO.setKeyItem(userProp.getKeyItem());
			
			Object xmlString = xStream.fromXML(userProp.getValue());
			
			userPropDTO.setValue(xmlString);
			
			userPropsDTO.add(userPropDTO);
		}
		
		return userPropsDTO;
	}
	
	public void addOrUpdateUserProperty(String key, Long user_id, String value) {
		try {
			
			UserProperty userProperties = this.getUserSidebarPropertyByKey(key, user_id);
			if (userProperties == null || userProperties.getUserPropertyId() == 0) {
				userProperties = new UserProperty();
			}
			
			userProperties.setInserted(new Date());
			userProperties.setUser_id(user_id);
			userProperties.setValue(value);
			userProperties.setKeyItem(key);
			
			if (userProperties.getUserPropertyId() != 0) {
				getHibernateTemplate().update(userProperties);
			} else {
				getHibernateTemplate().save(userProperties);
			}
			
		} catch (HibernateException ex) {
			log.error("[addOrUpdateUserProperty]",ex);
		} catch (Exception ex2) {
			log.error("[addOrUpdateUserProperty]",ex2);
		}
	}	
	
}
