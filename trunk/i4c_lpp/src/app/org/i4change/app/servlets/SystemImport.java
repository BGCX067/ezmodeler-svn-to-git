package org.i4change.app.servlets;

import http.utils.multipartrequest.ServletMultipartRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.i4change.app.data.basic.Configurationmanagement;
import org.i4change.app.data.basic.ObjectIdentifierDaoImpl;
import org.i4change.app.data.basic.Sessionmanagement;
import org.i4change.app.data.diagram.AssigneeDaoImpl;
import org.i4change.app.data.diagram.DataCarrierDiagramObjectDaoImpl;
import org.i4change.app.data.diagram.DiagramDaoImpl;
import org.i4change.app.data.diagram.DiagramInstanceObjectDaoImpl;
import org.i4change.app.data.diagram.DiagramObjectDaoImpl;
import org.i4change.app.data.diagram.DiagramTypeDaoImpl;
import org.i4change.app.data.diagram.RoleDaoImpl;
import org.i4change.app.data.domain.daos.OrganisationDaoImpl;
import org.i4change.app.data.user.Addressmanagement;
import org.i4change.app.data.user.EmailDaoImpl;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.data.user.daos.UserSidebarPropertyDaoImpl;
import org.i4change.app.hibernate.beans.adresses.Adresses_Emails;
import org.i4change.app.hibernate.beans.basic.Configuration;
import org.i4change.app.hibernate.beans.basic.ObjectIdentifier;
import org.i4change.app.hibernate.beans.diagram.DataCarrierDiagramObject;
import org.i4change.app.hibernate.beans.diagram.Diagram;
import org.i4change.app.hibernate.beans.diagram.DiagramInstanceObject;
import org.i4change.app.hibernate.beans.diagram.DiagramObject;
import org.i4change.app.hibernate.beans.diagram.Role;
import org.i4change.app.hibernate.beans.domain.Organisation;
import org.i4change.app.hibernate.beans.domain.Organisation_Users;
import org.i4change.app.hibernate.beans.user.UserSidebarProperty;
import org.i4change.app.hibernate.beans.user.Users;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class SystemImport extends HttpServlet {

	private static final Log log = LogFactory.getLog(SystemImport.class);
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void service(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws ServletException,
			IOException {

		try {
			
			// Get Spring context
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
			
			UserDaoImpl userDaoImpl = (UserDaoImpl) context.getBean("i4change.UserDaoImpl");
			Sessionmanagement sessionmanagement = (Sessionmanagement) context.getBean("i4change.Sessionmanagement");
			
			String sid = httpServletRequest.getParameter("sid");
			if (sid == null) {
				sid = "default";
			}
			log.debug("sid: " + sid);

			String moduleName = httpServletRequest.getParameter("moduleName");
			if (moduleName == null) {
				moduleName = "moduleName";
			}
			System.out.println("moduleName: " + sid);
			Long users_id = sessionmanagement.checkSession(sid);
			Long user_level = userDaoImpl.getUserLevelByID(users_id);

			System.out.println("users_id: " + users_id);
			System.out.println("user_level: " + user_level);

			// if (user_level!=null && user_level > 0) {
			if (true) {


					ServletMultipartRequest upload = new ServletMultipartRequest(httpServletRequest, 104857600); // max100 mb
					InputStream is = upload.getFileContents("Filedata");
					
					SAXReader reader = new SAXReader();
			        Document document = reader.read(is);
			        
					this.addDataByDocument(document);
					

			} else {
				System.out.println("ERROR not authorized System Import "+ (new Date()));
			}	
	
		} catch (Exception er) {
			log.error("ERROR ", er);
			System.out.println("Error exporting: " + er);
			er.printStackTrace();
		}
	}

	/* ###############################
	 * Functions to generate Objects again
	 * 
	 */
	
public void addDataByDocument(Document document) throws Exception {
		
        Element root = document.getRootElement();
        
        log.debug("addDataByDocument: "+root);
        
        this.addOrganisationsByDocument(root);
        
        this.addUsersByDocument(root);
        
        this.addConfigurationByDocument(root);
        
        this.addDiagramWithoutParentByDocument(root);
        
        this.addDiagramObjectsByDocument(root);
        
        this.addDiagramParentByDocument(root);
        
        this.addDiagramInstanceObjectsByDocument(root);
        
        this.addRolesByDocument(root);
        
        this.addObjectIdentifiersByDocument(root);
        
        this.addUserSidebarPropertyByDocument(root);
        
	}
	

	private void addUsersByDocument(Element root) throws Exception {
		// Get Spring context
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		
		OrganisationDaoImpl organisationDaoImpl = (OrganisationDaoImpl) context.getBean("i4change.OrganisationDaoImpl");
		UserDaoImpl userDaoImpl = (UserDaoImpl) context.getBean("i4change.userDaoImpl");
		Addressmanagement addressmanagement = (Addressmanagement) context.getBean("i4change.Addressmanagement");
		EmailDaoImpl emailDaoImpl = (EmailDaoImpl) context.getBean("i4change.EmailDaoImpl");
		
		Element users = root.element("users");
        
        for (Iterator<Element> i = users.elementIterator( "user" ); i.hasNext(); ) {
        	Element user = i.next();
        	
        	String elementText = user.getText();
    		
    		XStream xStream = new XStream(new XppDriver());
    		xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
    		
    		Users us = (Users) xStream.fromXML(elementText);
    		
    		log.debug("us: "+us.getUser_id()+ " NAME: "+us.getLogin());
    		
    		//add Addresses-EMails to Adresses-Object
    		us.getAdresses().setEmails(new HashSet<Adresses_Emails>());
    		
    		Element adressesemails = user.element("adressesemails");
    		
    		for (Iterator<Element> iter_1 = adressesemails.elementIterator( "adressesemail" ); iter_1.hasNext(); ) {
            	Element adressesemail = iter_1.next();
            	
            	String elementText_1 = adressesemail.getText();
            	
            	XStream xStream_1 = new XStream(new XppDriver());
        		xStream_1.setMode(XStream.XPATH_RELATIVE_REFERENCES);
        		
        		Object obj = xStream_1.fromXML(elementText_1);
        		
        		Adresses_Emails addrEmails = (Adresses_Emails) xStream_1.fromXML(elementText_1);
        		
        		log.debug("addrEmails: "+addrEmails.getAdresses_emails_id()+ " NAME: "+addrEmails.getMail().getEmail());
        		
        		us.getAdresses().getEmails().add(addrEmails);
            	
    		}
    		
    		//add User_Organisation to User-Object
    		us.setOrganisation_users(new LinkedList<Organisation_Users>());
    		
    		Element organisationusers = user.element("organisationusers");
    		
    		for (Iterator<Element> iter_1 = organisationusers.elementIterator( "organisationuser" ); iter_1.hasNext(); ) {
            	Element organisationuser = iter_1.next();
            	
            	String elementText_2 = organisationuser.getText();
            	
            	XStream xStream_2 = new XStream(new XppDriver());
        		xStream_2.setMode(XStream.XPATH_RELATIVE_REFERENCES);
        		
        		Organisation_Users orgUsers = (Organisation_Users) xStream_2.fromXML(elementText_2);
        		
        		log.debug("orgUsers: "+orgUsers.getOrganisation_users_id()+ " NAME: "+orgUsers.getOrganisation().getName());
        		
        		us.getOrganisation_users().add(orgUsers);
            	
    		}
    		
    		if (userDaoImpl.getUserById(us.getUser_id()) == null) {
    			
    			log.debug("Address: "+us.getAdresses());
    			log.debug("Address: "+us.getAdresses().getAdresses_id());
    			
    			long addresses_id = addressmanagement.saveAddress(us.getAdresses().getStreet(), 
			    					us.getAdresses().getZip(), us.getAdresses().getTown(), 
			    					us.getAdresses().getStates().getCountry_id(), us.getAdresses().getAdditionalname(), 
			    					us.getAdresses().getComment(), us.getAdresses().getFax());
    			
    			
    			for (Adresses_Emails addEmails : us.getAdresses().getEmails()) {
    				
    				Long mail_id = emailDaoImpl.registerEmail(addEmails.getMail().getEmail(), addresses_id, "");
    				
    				
    			}
    			//UserDaoImpl.getInstance().addUser(level_id, availible, status, firstname, login, lastname, language_id, userpass, adress_id, age)
    			//UserDaoImpl.getInstance().addUser(us);
    			
    			us.setAdresses(addressmanagement.getAdressbyId(addresses_id));
    			
    			
    			
    			long user_id = userDaoImpl.addUser(us);
    			
    			for (Organisation_Users orgUsers : us.getOrganisation_users()) {
    				
    				organisationDaoImpl.addUserToOrganisationByImport(user_id, 
    							orgUsers.getOrganisation().getOrganisation_id());
    				
    			}
    			
    		} else {
    			log.debug("User with ID: "+us.getUser_id()+" does already exist");
    		}
    		
        }
        
	}
	
	private void addOrganisationsByDocument(Element root) throws Exception {
		// Get Spring context
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		
		OrganisationDaoImpl organisationDaoImpl = (OrganisationDaoImpl) context.getBean("i4change.OrganisationDaoImpl");
		
		Element organizations = root.element("organizations");
        
		log.debug("addOrganisationsByDocument"+organizations);
		
        for (Iterator<Element> i = organizations.elementIterator( "organisation" ); i.hasNext(); ) {
        	Element configuration = i.next();
        	
        	String elementText = configuration.getText();
    		
    		XStream xStream = new XStream(new XppDriver());
    		xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
    		
    		Organisation org = (Organisation) xStream.fromXML(elementText);
    		
    		log.debug("org: "+org.getOrganisation_id()+ " NAME: "+org.getName());
    		
    		if (organisationDaoImpl.getOrganisationById(org.getOrganisation_id()) == null) {
    			
    			organisationDaoImpl.addOrganisationByObject(org);
    			
    		} else {
    			log.debug("Organisation with this ID does already exist");
    		}
        }
        
	}

	private void addConfigurationByDocument(Element root) throws Exception {
		// Get Spring context
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		
		Configurationmanagement configurationmanagement = (Configurationmanagement) context.getBean("i4change.Configurationmanagement");
		
		Element configurations = root.element("configurations");
        
        for (Iterator<Element> i = configurations.elementIterator( "configuration" ); i.hasNext(); ) {
        	Element configuration = i.next();
        	
        	String elementText = configuration.getText();
    		
    		XStream xStream = new XStream(new XppDriver());
    		xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
    		
    		Configuration conf = (Configuration) xStream.fromXML(elementText);
    		
    		log.debug("conf: "+conf.getConfiguration_id()+ " NAME: "+conf.getConf_key());
    		
    		if (configurationmanagement.getConfByConfigurationById(conf.getConfiguration_id()) == null) {
    			
    			configurationmanagement.addConfig(conf);
    			
    		} else {
    			log.debug("Configuration with ID: "+conf.getConfiguration_id()+" does already exist");
    		}
    		
        }
		
	}

	private void addDiagramWithoutParentByDocument(Element root) throws Exception {
		
		// Get Spring context
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		
		DiagramDaoImpl diagramDaoImpl = (DiagramDaoImpl) context.getBean("i4change.DiagramDaoImpl");
		DiagramTypeDaoImpl diagramTypeDaoImpl = (DiagramTypeDaoImpl) context.getBean("i4change.DiagramTypeDaoImpl");
		
		Element diagrams = root.element("diagrams");
        
        for (Iterator<Element> i = diagrams.elementIterator( "diagram" ); i.hasNext(); ) {
        	Element diagram = i.next();
        	
        	String elementText = diagram.getText();
    		
    		XStream xStream = new XStream(new XppDriver());
    		xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
    		
    		Diagram dia = (Diagram) xStream.fromXML(elementText);
    		
    		dia.setParentDiagramObject(null);
    		
    		log.debug("dia: "+dia.getDiagramId()+ " NAME: "+dia.getName());
    		
    		if (diagramDaoImpl.getDiagramById(dia.getDiagramId()) == null) {
    			
    			if (diagramTypeDaoImpl.getDiagramTypeById(dia.getDiagramType().getDiagramTypeId()) == null) {
    				diagramTypeDaoImpl.addDiagramTypeByObject(dia.getDiagramType());
    			} else {
    				log.debug("DiagramType with ID: "+dia.getDiagramType().getDiagramTypeId()+" does already exist");
    			}
    			
    			if (diagramDaoImpl.getDiagramrevisionById(dia.getDiagramrevision().getDiagramrevisionId()) == null){
    				diagramDaoImpl.addDiagramrevisionByObject(dia.getDiagramrevision());
    			} else {
    				log.debug("Diagramrevision with ID: "+dia.getDiagramrevision().getDiagramrevisionId()+" does already exist");
    			}
    			
    			diagramDaoImpl.addDiagramByObject(dia);
    			
    		} else {
    			log.debug("Diagram with ID: "+dia.getDiagramId()+" does already exist");
    		}
    		
        }
		
	}
	
	private Long addDiagramParentByDocument(Element root) throws Exception {
		
		// Get Spring context
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		
		DiagramDaoImpl diagramDaoImpl = (DiagramDaoImpl) context.getBean("i4change.DiagramDaoImpl");
		
		Element diagrams = root.element("diagrams");
        
        for (Iterator<Element> i = diagrams.elementIterator( "diagram" ); i.hasNext(); ) {
        	Element diagram = i.next();
        	
        	String elementText = diagram.getText();
    		
    		XStream xStream = new XStream(new XppDriver());
    		xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
    		
    		Diagram dia = (Diagram) xStream.fromXML(elementText);
    		
    		if (dia.getParentDiagramObject()!=null) {
    			log.debug("Parent of dia: "+dia.getDiagramNo()+ " Parent NAME: "+dia.getParentDiagramObject().getName());
    			
    			diagramDaoImpl.updateDiagramParentbyId(dia.getParentDiagramObject().getDiagramObjectId(), dia.getDiagramId());
    		}
    		
        }
		
		return null;
	}
		

	private void addDiagramObjectsByDocument(Element root) throws Exception {
		
		// Get Spring context
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		
		AssigneeDaoImpl assigneeDaoImpl = (AssigneeDaoImpl) context.getBean("i4change.AssigneeDaoImpl");
		DiagramObjectDaoImpl diagramObjectDaoImpl = (DiagramObjectDaoImpl) context.getBean("i4change.DiagramObjectDaoImpl");
		DataCarrierDiagramObjectDaoImpl dataCarrierDiagramObjectDaoImpl = (DataCarrierDiagramObjectDaoImpl) context.getBean("i4change.DataCarrierDiagramObjectDaoImpl");
		
		Element diagramobjects = root.element("diagramobjects");
        
        for (Iterator<Element> i = diagramobjects.elementIterator( "diagramobject" ); i.hasNext(); ) {
        	Element diagramobject = i.next();
        	
        	String elementText = diagramobject.getText();
    		
    		XStream xStream = new XStream(new XppDriver());
    		xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
    		
    		DiagramObject diaObject = (DiagramObject) xStream.fromXML(elementText);
    		
    		log.debug("diaObject: "+diaObject.getDiagramObjectId()+ " NAME: "+diaObject.getName());
    		
    		//add DataCarrier Objects to diaObject
    		diaObject.setDataCarrierDiagramObject(new HashSet<DataCarrierDiagramObject>());
    		
    		Element datacarrierdiagramobjects = diagramobject.element("datacarrierdiagramobject");
    		
    		for (Iterator<Element> iter_1 = datacarrierdiagramobjects.elementIterator( "datacarrierdiagramobject" ); iter_1.hasNext(); ) {
            	Element datacarrierdiagramobject = iter_1.next();
            	
            	String elementText_1 = datacarrierdiagramobject.getText();
            	
            	XStream xStream_1 = new XStream(new XppDriver());
        		xStream_1.setMode(XStream.XPATH_RELATIVE_REFERENCES);
        		
        		DataCarrierDiagramObject dataCarrierObject = (DataCarrierDiagramObject) xStream_1.fromXML(elementText_1);
        		
        		log.debug("dataCarrierObject: "+dataCarrierObject.getDataCarrierDiagramObjectId()+ " NAME: "+
        				dataCarrierObject.getDiagramObjectId());
        		
        		diaObject.getDataCarrierDiagramObject().add(dataCarrierObject);
            	
    		}
    		
    		if (diagramObjectDaoImpl.getDiagramObjectById(diaObject.getDiagramObjectId()) == null) {
    			
    			if (diaObject.getAssignee() != null) {
    				if (assigneeDaoImpl.getAssginee(diaObject.getAssignee().getAssigneeId()) == null) {
    					
    					assigneeDaoImpl.addAssgineeByObject(diaObject.getAssignee());
    					
    				} else {
    					log.debug("(Assignee with ID: "+diaObject.getAssignee().getAssigneeId()+" does already exist");
    				}
    			}
    			
    			long diagramObjectId = diagramObjectDaoImpl.addDiagramObjectByObject(diaObject);
    			
    		} else {
    			log.debug("DiagramObject with ID: "+diaObject.getDiagramObjectId()+" does already exist");
    		}
    		
        }
        
        
        //The Data Carrier has to be inserted AFTER the IagramObject, cause it has its own item reference to a Diagram Object
        
        for (Iterator<Element> i = diagramobjects.elementIterator( "diagramobject" ); i.hasNext(); ) {
        	Element diagramobject = i.next();
        	
        	String elementText = diagramobject.getText();
    		
    		XStream xStream = new XStream(new XppDriver());
    		xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
    		
    		DiagramObject diaObject = (DiagramObject) xStream.fromXML(elementText);
    		
    		log.debug("diaObject: "+diaObject.getDiagramObjectId()+ " NAME: "+diaObject.getName());
    		
    		//add DataCarrier Objects to diaObject
    		diaObject.setDataCarrierDiagramObject(new HashSet<DataCarrierDiagramObject>());
    		
    		Element datacarrierdiagramobjects = diagramobject.element("datacarrierdiagramobject");
    		
    		for (Iterator<Element> iter_1 = datacarrierdiagramobjects.elementIterator( "datacarrierdiagramobject" ); iter_1.hasNext(); ) {
            	Element datacarrierdiagramobject = iter_1.next();
            	
            	String elementText_1 = datacarrierdiagramobject.getText();
            	
            	XStream xStream_1 = new XStream(new XppDriver());
        		xStream_1.setMode(XStream.XPATH_RELATIVE_REFERENCES);
        		
        		DataCarrierDiagramObject dataCarrierObject = (DataCarrierDiagramObject) xStream_1.fromXML(elementText_1);
        		
        		log.debug("dataCarrierObject: "+dataCarrierObject.getDataCarrierDiagramObjectId()+ " NAME: "+
        				dataCarrierObject.getDiagramObjectId());
        		
        		diaObject.getDataCarrierDiagramObject().add(dataCarrierObject);
            	
    		}
    		

    		//Add the Data Carriers
			for (DataCarrierDiagramObject dCarrier : diaObject.getDataCarrierDiagramObject()) {
				
				if (dataCarrierDiagramObjectDaoImpl.getDataCarrierDiagramObjectById(dCarrier.getDataCarrierObjectdiagramObjectId()) == null) {
					
					log.debug("dCarrier: "+dCarrier.getDataCarrierDiagramObjectId());
					
					log.debug("DiagramObjectId 1 DiagramObjectId 2: "+dCarrier.getDataCarrierObjectdiagramObjectId()+ " "+dCarrier.getDiagramObjectId());
					
					dataCarrierDiagramObjectDaoImpl.addOrUpdateDataCarrierDiagramObject(dCarrier.getDataCarrierObjectdiagramObjectId(), 
							dCarrier.getDiagramObjectId(), dCarrier.getInsertedBy());
					
				} else {
					log.debug("(DataCarrierDiagramObject with ID: "+dCarrier.getDataCarrierObjectdiagramObjectId()+" does already exist");
				}
				
			}

    		
        }
		
	}
	
	private void addDiagramInstanceObjectsByDocument(Element root) throws Exception {
		
		// Get Spring context
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		DiagramInstanceObjectDaoImpl diagramInstanceObjectDaoImpl = (DiagramInstanceObjectDaoImpl) context.getBean("i4change.DiagramInstanceObjectDaoImpl");
		
		Element diagraminstanceobjects = root.element("diagraminstanceobjects");
        
        for (Iterator<Element> i = diagraminstanceobjects.elementIterator( "diagraminstanceobject" ); i.hasNext(); ) {
        	Element diagraminstanceobject = i.next();
        	
        	String elementText = diagraminstanceobject.getText();
    		
    		XStream xStream = new XStream(new XppDriver());
    		xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
    		
    		DiagramInstanceObject diaInstanceObj = (DiagramInstanceObject) xStream.fromXML(elementText);
    		
			log.debug("diaInstanceObj: "+diaInstanceObj.getDiagramInstanceObjectId()+ " NAME: "+diaInstanceObj.getDiagramObject().getName());
        
			if (diagramInstanceObjectDaoImpl.getDiagramInstanceObjectById(diaInstanceObj.getDiagramInstanceObjectId()) == null) {
				
				diagramInstanceObjectDaoImpl.addDiagramInstanceObjectByObject(diaInstanceObj);
				
			} else {
				log.debug("DiagramInstanceObject with ID: "+diaInstanceObj.getDiagramInstanceObjectId()+" does already exist");
			}
        }
		
	}
	
	private void addRolesByDocument(Element root) throws Exception {
		
		// Get Spring context
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		RoleDaoImpl roleDaoImpl = (RoleDaoImpl) context.getBean("i4change.RoleDaoImpl");
		
		Element roles = root.element("roles");
        
        for (Iterator<Element> i = roles.elementIterator( "role" ); i.hasNext(); ) {
        	Element role = i.next();
        	
        	String elementText = role.getText();
    		
    		XStream xStream = new XStream(new XppDriver());
    		xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
    		
    		Role ro = (Role) xStream.fromXML(elementText);
    		
			log.debug("ro: "+ro.getRolesId()+ " NAME: "+ro.getAssignee().getLogin());
			
			if (roleDaoImpl.getRoleById(ro.getRolesId()) == null) {
				
				roleDaoImpl.addRoleByObject(ro);
				
			} else {
				log.debug("Role with ID: "+ro.getRolesId()+" does already exist");
			}
			
        }

	}
	
	private void addObjectIdentifiersByDocument(Element root) throws Exception {
		
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		ObjectIdentifierDaoImpl objectIdentifierDaoImpl = (ObjectIdentifierDaoImpl) context.getBean("i4change.ObjectIdentifierDaoImpl");
		
		Element objectidentifiers = root.element("objectidentifiers");
        
        for (Iterator<Element> i = objectidentifiers.elementIterator( "objectidentifier" ); i.hasNext(); ) {
        	Element objectidentifier = i.next();
        	
        	String elementText = objectidentifier.getText();
    		
    		XStream xStream = new XStream(new XppDriver());
    		xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
    		
    		ObjectIdentifier objIdent = (ObjectIdentifier) xStream.fromXML(elementText);
    		
			log.debug("objIdent: "+objIdent.getObjectidentifier_id()+ " NAME: "+objIdent.getOrganization_id());
			
			if (objectIdentifierDaoImpl.getCurrentObjectIdentifierById(objIdent.getObjectidentifier_id()) == null) {
				
				objectIdentifierDaoImpl.addNewObjectIdentifier(objIdent);
				
			} else {
				
				log.debug("ObjectIdentifierDaoImpl with ID: "+objIdent.getObjectidentifier_id()+" does already exist");
				
				//Update ObjectIdentifierDaoImpl to newer ID
				objectIdentifierDaoImpl.updateCurrentObjectIdentifier(objIdent);
				
			}
			
        }
		
	}	
	
	private void addUserSidebarPropertyByDocument(Element root) throws Exception {
		
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		UserSidebarPropertyDaoImpl userSidebarPropertyDaoImpl = (UserSidebarPropertyDaoImpl) context.getBean("i4change.UserSidebarPropertyDaoImpl");
		
		Element usersidebarproperties = root.element("usersidebarproperties");
        
        for (Iterator<Element> i = usersidebarproperties.elementIterator( "usersidebarproperty" ); i.hasNext(); ) {
        	Element usersidebarproperty = i.next();
        	
        	String elementText = usersidebarproperty.getText();
    		
    		XStream xStream = new XStream(new XppDriver());
    		xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
    		
    		UserSidebarProperty sideBarItem = (UserSidebarProperty) xStream.fromXML(elementText);
    		
			log.debug("sideBarItem: "+sideBarItem.getUserSidebarPropertyId()+ " NAME: "+sideBarItem.getDiagramNo());
			
			if (userSidebarPropertyDaoImpl.getUserSidebarPropertyById(sideBarItem.getUserSidebarPropertyId()) == null) {
				
				userSidebarPropertyDaoImpl.addUserSidebarPropertyByObject(sideBarItem);
				
			} else {
				
				log.debug("UserSidebarProperty with ID: "+sideBarItem.getUserSidebarPropertyId()+" does already exist");
				
			}
			
        }
		
	}	
	
}
