package org.i4change.app.http.javarpc;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.dto.organization.OrganisationDetailedDTO;
import org.i4change.app.dto.user.OrganisationDTO;
import org.i4change.app.dto.user.OrganisationUserDTO;
import org.i4change.app.hibernate.beans.domain.Organisation;
import org.i4change.app.hibernate.beans.domain.Organisation_Users;
import org.i4change.app.hibernate.beans.user.Users;
import org.i4change.app.remote.IOrganisationService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author sebastianwagner
 *
 */
public class OrganisationServiceRPC extends BaseAdapterRPC {

	private static final Log log = LogFactory.getLog(OrganisationServiceRPC.class);
	
	public SearchResult getOrganisations(String SID, int start ,int max, String orderby, boolean asc){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IOrganisationService organisationService = (IOrganisationService) context.getBean("organisationservice.service");
		
			return organisationService.getOrganisations(SID, start, max, orderby, asc);
			
		} catch (Exception err) {
			log.error ("[getOrganisations]",err);
		}
		return null;
	}

	public List<OrganisationUserDTO> getOrganisationsByUser(String SID){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IOrganisationService organisationService = (IOrganisationService) context.getBean("organisationservice.service");
		
			return organisationService.getOrganisationsByUser(SID);
			
		} catch (Exception err) {
			log.error ("[getOrganisationsByUser]",err);
		}
		return null;
	}

	public List<Organisation> getAllOrganisations(String SID){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IOrganisationService organisationService = (IOrganisationService) context.getBean("organisationservice.service");
		
			return organisationService.getAllOrganisations(SID);
			
		} catch (Exception err) {
			log.error ("[getAllOrganisations]",err);
		}
		return null;
	}

	public Long addPendingOrganization(String SID, String orgName, Vector orgPatternsMap){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IOrganisationService organisationService = (IOrganisationService) context.getBean("organisationservice.service");
		
			return organisationService.addPendingOrganization(SID, orgName, orgPatternsMap);
			
		} catch (Exception err) {
			log.error ("[addPendingOrganization]",err);
		}
		return null;
	}	

	public OrganisationDTO getOrganisationById(String SID, long organisation_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IOrganisationService organisationService = (IOrganisationService) context.getBean("organisationservice.service");
		
			return organisationService.getOrganisationDTOById(SID, organisation_id);
			
		} catch (Exception err) {
			log.error ("[getOrganisationById]",err);
		}
		return null;
	}	
	
	public OrganisationDetailedDTO getOrganisationDetailedById(String SID, long organisation_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IOrganisationService organisationService = (IOrganisationService) context.getBean("organisationservice.service");
		
			return organisationService.getOrganisationDetailedById(SID, organisation_id);
			
		} catch (Exception err) {
			log.error ("[getOrganisationById]",err);
		}
		return null;
	}

	public Long deleteOrganisation(String SID, long organisation_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IOrganisationService organisationService = (IOrganisationService) context.getBean("organisationservice.service");
		
			return organisationService.deleteOrganisation(SID, organisation_id);
			
		} catch (Exception err) {
			log.error ("[deleteOrganisation]",err);
		}
		return null;
	}

	public Long saveOrUpdateOrganisation(String SID, Map regObjectObj){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IOrganisationService organisationService = (IOrganisationService) context.getBean("organisationservice.service");
		
			return organisationService.saveOrUpdateOrganisation(SID, regObjectObj);
			
		} catch (Exception err) {
			log.error ("[saveOrUpdateOrganisation]",err);
		}
		return null;
	}	

	public SearchResult getUsersByOrganisation(String SID, long organisation_id, int start, int max, 
			String orderby, boolean asc){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IOrganisationService organisationService = (IOrganisationService) context.getBean("organisationservice.service");
		
			return organisationService.getUsersByOrganisation(SID, organisation_id, start, max, orderby, asc);
			
		} catch (Exception err) {
			log.error ("[getUsersByOrganisation]",err);
		}
		return null;
	}		

	public List<Users> getModeratorsByOrganisationId(String SID, long organisation_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IOrganisationService organisationService = (IOrganisationService) context.getBean("organisationservice.service");
		
			return organisationService.getModeratorsByOrganisationId(SID, organisation_id);
			
		} catch (Exception err) {
			log.error ("[getModeratorsByOrganisationId]",err);
		}
		return null;
	}	

	public Long addUserToOrganisation(String SID, Long organisation_id, Long user_id, String comment){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IOrganisationService organisationService = (IOrganisationService) context.getBean("organisationservice.service");
		
			return organisationService.addUserToOrganisation(SID, organisation_id, user_id, comment);
			
		} catch (Exception err) {
			log.error ("[addUserToOrganisation]",err);
		}
		return null;
	}	

	public Long deleteUserFromOrganisation(String SID, Long organisation_id, Long user_id, String comment){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IOrganisationService organisationService = (IOrganisationService) context.getBean("organisationservice.service");
		
			return organisationService.deleteUserFromOrganisation(SID, organisation_id, user_id, comment);
			
		} catch (Exception err) {
			log.error ("[deleteUserFromOrganisation]",err);
		}
		return null;
	}	
	
	public Long checkOrganizationStatus(String SID, Long organization_id, boolean rememberMe, 
			Long language_id, Long organisation_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IOrganisationService organisationService = (IOrganisationService) context.getBean("organisationservice.service");
			
			return organisationService.checkOrganizationStatus(SID, organization_id, rememberMe, 
					language_id, organisation_id);
			
		} catch (Exception err) {
			log.error ("[checkOrganizationStatus]",err);
		}
		return null;
	}
	
	public Long checkOrganizationStatusStub(String SID, Long organization_id){
		try {
			
//			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
//			
//			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
//			OrganisationService organisationService = (OrganisationService) context.getBean("organisationservice.service");
			
			return new Long(1);
			
		} catch (Exception err) {
			log.error ("[checkOrganizationStatus]",err);
		}
		return null;
	}
	
}
