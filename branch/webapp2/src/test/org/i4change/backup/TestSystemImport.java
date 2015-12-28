package org.i4change.backup;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.i4change.app.data.basic.Configurationmanagement;
import org.i4change.app.data.basic.ObjectIdentifierDaoImpl;
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
import org.i4change.app.data.user.daos.AddressesEmailsDaoImpl;
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
import org.i4change.app.utils.crypt.ManageCryptStyle;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class TestSystemImport extends TestCase {
	
	//backup_15_09_2008_10_35_18.xml
	
	private static final Log log = LogFactory.getLog(TestSystemImport.class);
	
	public void testSystemImport() {
		try {
			
			File f = new File("docs/backup_16_09_2008_13_01_04.xml");
			
			if (f.exists()) {
				
				SAXReader reader = new SAXReader();
		        Document document = reader.read(f);
				
		        this.addDataByDocument(document);
				
			} else {
				log.debug("File does not exist");
			}
			
		} catch (Exception err) {
			log.error("[testSystemImport]",err);
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
		
		Element users = root.element("users");
        
        for (Iterator<Element> i = users.elementIterator( "user" ); i.hasNext(); ) {
        	Element user = i.next();
        	
        	String elementText = user.getText();
    		
    		XStream xStream = new XStream(new XppDriver());
    		xStream.setMode(XStream.NO_REFERENCES);
    		
    		Users us = (Users) xStream.fromXML(elementText);
    		
    		log.debug("us: "+us.getUser_id()+ " NAME: "+us.getLogin());
    		
    		//add Addresses-EMails to Adresses-Object
    		us.getAdresses().setEmails(new HashSet<Adresses_Emails>());
    		
    		Element adressesemails = user.element("adressesemails");
    		
    		for (Iterator<Element> iter_1 = adressesemails.elementIterator( "adressesemail" ); iter_1.hasNext(); ) {
            	Element adressesemail = iter_1.next();
            	
            	String elementText_1 = adressesemail.getText();
            	
            	XStream xStream_1 = new XStream(new XppDriver());
        		xStream_1.setMode(XStream.NO_REFERENCES);
        		
        		Object obj = xStream_1.fromXML(elementText_1);
        		
        		Adresses_Emails addrEmails = (Adresses_Emails) xStream_1.fromXML(elementText_1);
        		
        		log.debug("addrEmails: "+addrEmails.getAdresses_emails_id()+ " NAME: "+addrEmails.getMail().getEmail());
        		
        		us.getAdresses().getEmails().add(addrEmails);
            	
    		}
    		
    		//add User_Organisation to User-Object
    		us.setOrganisation_users(new HashSet<Organisation_Users>());
    		
    		Element organisationusers = user.element("organisationusers");
    		
    		for (Iterator<Element> iter_1 = organisationusers.elementIterator( "organisationuser" ); iter_1.hasNext(); ) {
            	Element organisationuser = iter_1.next();
            	
            	String elementText_2 = organisationuser.getText();
            	
            	XStream xStream_2 = new XStream(new XppDriver());
        		xStream_2.setMode(XStream.NO_REFERENCES);
        		
        		Organisation_Users orgUsers = (Organisation_Users) xStream_2.fromXML(elementText_2);
        		
        		log.debug("orgUsers: "+orgUsers.getOrganisation_users_id()+ " NAME: "+orgUsers.getOrganisation().getName());
        		
        		us.getOrganisation_users().add(orgUsers);
            	
    		}
    		
    		if (UserDaoImpl.getInstance().getUserById(us.getUser_id()) == null) {
    			
    			log.debug("Address: "+us.getAdresses());
    			log.debug("Address: "+us.getAdresses().getAdresses_id());
    			
    			long addresses_id = Addressmanagement.getInstance().saveAddress(us.getAdresses().getStreet(), 
			    					us.getAdresses().getZip(), us.getAdresses().getTown(), 
			    					us.getAdresses().getStates().getCountry_id(), us.getAdresses().getAdditionalname(), 
			    					us.getAdresses().getComment(), us.getAdresses().getFax());
    			
    			
    			for (Adresses_Emails addEmails : us.getAdresses().getEmails()) {
    				
    				Long mail_id = EmailDaoImpl.getInstance().registerEmail(addEmails.getMail().getEmail(), addresses_id, "");
    				
    				
    			}
    			//UserDaoImpl.getInstance().addUser(level_id, availible, status, firstname, login, lastname, language_id, userpass, adress_id, age)
    			//UserDaoImpl.getInstance().addUser(us);
    			
    			us.setAdresses(Addressmanagement.getInstance().getAdressbyId(addresses_id));
    			
    			
    			
    			long user_id = UserDaoImpl.getInstance().addUser(us);
    			
    			for (Organisation_Users orgUsers : us.getOrganisation_users()) {
    				
    				OrganisationDaoImpl.getInstance().addUserToOrganisationByImport(user_id, 
    							orgUsers.getOrganisation().getOrganisation_id());
    				
    			}
    			
    		} else {
    			log.debug("User with ID: "+us.getUser_id()+" does already exist");
    		}
    		
        }
        
	}
	
	private void addOrganisationsByDocument(Element root) throws Exception {
		
		Element organizations = root.element("organizations");
        
		log.debug("addOrganisationsByDocument"+organizations);
		
        for (Iterator<Element> i = organizations.elementIterator( "organisation" ); i.hasNext(); ) {
        	Element configuration = i.next();
        	
        	String elementText = configuration.getText();
    		
    		XStream xStream = new XStream(new XppDriver());
    		xStream.setMode(XStream.NO_REFERENCES);
    		
    		Organisation org = (Organisation) xStream.fromXML(elementText);
    		
    		log.debug("org: "+org.getOrganisation_id()+ " NAME: "+org.getName());
    		
    		if (OrganisationDaoImpl.getInstance().getOrganisationById(org.getOrganisation_id()) == null) {
    			
    			OrganisationDaoImpl.getInstance().addOrganisationByObject(org);
    			
    		} else {
    			log.debug("Organisation with this ID does already exist");
    		}
        }
        
	}

	private void addConfigurationByDocument(Element root) throws Exception {
		
		Element configurations = root.element("configurations");
        
        for (Iterator<Element> i = configurations.elementIterator( "configuration" ); i.hasNext(); ) {
        	Element configuration = i.next();
        	
        	String elementText = configuration.getText();
    		
    		XStream xStream = new XStream(new XppDriver());
    		xStream.setMode(XStream.NO_REFERENCES);
    		
    		Configuration conf = (Configuration) xStream.fromXML(elementText);
    		
    		log.debug("conf: "+conf.getConfiguration_id()+ " NAME: "+conf.getConf_key());
    		
    		if (Configurationmanagement.getInstance().getConfByConfigurationById(conf.getConfiguration_id()) == null) {
    			
    			Configurationmanagement.getInstance().addConfig(conf);
    			
    		} else {
    			log.debug("Configuration with ID: "+conf.getConfiguration_id()+" does already exist");
    		}
    		
        }
		
	}

	private void addDiagramWithoutParentByDocument(Element root) throws Exception {
		
		Element diagrams = root.element("diagrams");
        
        for (Iterator<Element> i = diagrams.elementIterator( "diagram" ); i.hasNext(); ) {
        	Element diagram = i.next();
        	
        	String elementText = diagram.getText();
    		
    		XStream xStream = new XStream(new XppDriver());
    		xStream.setMode(XStream.NO_REFERENCES);
    		
    		Diagram dia = (Diagram) xStream.fromXML(elementText);
    		
    		dia.setParentDiagramObject(null);
    		
    		log.debug("dia: "+dia.getDiagramId()+ " NAME: "+dia.getName());
    		
    		if (DiagramDaoImpl.getInstance().getDiagramById(dia.getDiagramId()) == null) {
    			
    			if (DiagramTypeDaoImpl.getInstance().getDiagramTypeById(dia.getDiagramType().getDiagramTypeId()) == null) {
    				DiagramTypeDaoImpl.getInstance().addDiagramTypeByObject(dia.getDiagramType());
    			} else {
    				log.debug("DiagramType with ID: "+dia.getDiagramType().getDiagramTypeId()+" does already exist");
    			}
    			
    			if (DiagramDaoImpl.getInstance().getDiagramrevisionById(dia.getDiagramrevision().getDiagramrevisionId()) == null){
    				DiagramDaoImpl.getInstance().addDiagramrevisionByObject(dia.getDiagramrevision());
    			} else {
    				log.debug("Diagramrevision with ID: "+dia.getDiagramrevision().getDiagramrevisionId()+" does already exist");
    			}
    			
    			DiagramDaoImpl.getInstance().addDiagramByObject(dia);
    			
    		} else {
    			log.debug("Diagram with ID: "+dia.getDiagramId()+" does already exist");
    		}
    		
        }
		
	}
	
	private Long addDiagramParentByDocument(Element root) throws Exception {
		
		Element diagrams = root.element("diagrams");
        
        for (Iterator<Element> i = diagrams.elementIterator( "diagram" ); i.hasNext(); ) {
        	Element diagram = i.next();
        	
        	String elementText = diagram.getText();
    		
    		XStream xStream = new XStream(new XppDriver());
    		xStream.setMode(XStream.NO_REFERENCES);
    		
    		Diagram dia = (Diagram) xStream.fromXML(elementText);
    		
    		if (dia.getParentDiagramObject()!=null) {
    			log.debug("Parent of dia: "+dia.getDiagramNo()+ " Parent NAME: "+dia.getParentDiagramObject().getName());
    			
    			DiagramDaoImpl.getInstance().updateDiagramParentbyId(dia.getParentDiagramObject().getDiagramObjectId(), dia.getDiagramId());
    		}
    		
        }
		
		return null;
	}
		

	private void addDiagramObjectsByDocument(Element root) throws Exception {
		
		Element diagramobjects = root.element("diagramobjects");
        
        for (Iterator<Element> i = diagramobjects.elementIterator( "diagramobject" ); i.hasNext(); ) {
        	Element diagramobject = i.next();
        	
        	String elementText = diagramobject.getText();
    		
    		XStream xStream = new XStream(new XppDriver());
    		xStream.setMode(XStream.NO_REFERENCES);
    		
    		DiagramObject diaObject = (DiagramObject) xStream.fromXML(elementText);
    		
    		log.debug("diaObject: "+diaObject.getDiagramObjectId()+ " NAME: "+diaObject.getName());
    		
    		//add DataCarrier Objects to diaObject
    		diaObject.setDataCarrierDiagramObject(new HashSet<DataCarrierDiagramObject>());
    		
    		Element datacarrierdiagramobjects = diagramobject.element("datacarrierdiagramobject");
    		
    		for (Iterator<Element> iter_1 = datacarrierdiagramobjects.elementIterator( "datacarrierdiagramobject" ); iter_1.hasNext(); ) {
            	Element datacarrierdiagramobject = iter_1.next();
            	
            	String elementText_1 = datacarrierdiagramobject.getText();
            	
            	XStream xStream_1 = new XStream(new XppDriver());
        		xStream_1.setMode(XStream.NO_REFERENCES);
        		
        		DataCarrierDiagramObject dataCarrierObject = (DataCarrierDiagramObject) xStream_1.fromXML(elementText_1);
        		
        		log.debug("dataCarrierObject: "+dataCarrierObject.getDataCarrierDiagramObjectId()+ " NAME: "+
        				dataCarrierObject.getDiagramObjectId());
        		
        		diaObject.getDataCarrierDiagramObject().add(dataCarrierObject);
            	
    		}
    		
    		if (DiagramObjectDaoImpl.getInstance().getDiagramObjectById(diaObject.getDiagramObjectId()) == null) {
    			
    			if (diaObject.getAssignee() != null) {
    				if (AssigneeDaoImpl.getInstance().getAssginee(diaObject.getAssignee().getAssigneeId()) == null) {
    					
    					AssigneeDaoImpl.getInstance().addAssgineeByObject(diaObject.getAssignee());
    					
    				} else {
    					log.debug("(Assignee with ID: "+diaObject.getAssignee().getAssigneeId()+" does already exist");
    				}
    			}
    			
    			long diagramObjectId = DiagramObjectDaoImpl.getInstance().addDiagramObjectByObject(diaObject);
    			
    		} else {
    			log.debug("DiagramObject with ID: "+diaObject.getDiagramObjectId()+" does already exist");
    		}
    		
        }
        
        
        //The Data Carrier has to be inserted AFTER the IagramObject, cause it has its own item reference to a Diagram Object
        
        for (Iterator<Element> i = diagramobjects.elementIterator( "diagramobject" ); i.hasNext(); ) {
        	Element diagramobject = i.next();
        	
        	String elementText = diagramobject.getText();
    		
    		XStream xStream = new XStream(new XppDriver());
    		xStream.setMode(XStream.NO_REFERENCES);
    		
    		DiagramObject diaObject = (DiagramObject) xStream.fromXML(elementText);
    		
    		log.debug("diaObject: "+diaObject.getDiagramObjectId()+ " NAME: "+diaObject.getName());
    		
    		//add DataCarrier Objects to diaObject
    		diaObject.setDataCarrierDiagramObject(new HashSet<DataCarrierDiagramObject>());
    		
    		Element datacarrierdiagramobjects = diagramobject.element("datacarrierdiagramobject");
    		
    		for (Iterator<Element> iter_1 = datacarrierdiagramobjects.elementIterator( "datacarrierdiagramobject" ); iter_1.hasNext(); ) {
            	Element datacarrierdiagramobject = iter_1.next();
            	
            	String elementText_1 = datacarrierdiagramobject.getText();
            	
            	XStream xStream_1 = new XStream(new XppDriver());
        		xStream_1.setMode(XStream.NO_REFERENCES);
        		
        		DataCarrierDiagramObject dataCarrierObject = (DataCarrierDiagramObject) xStream_1.fromXML(elementText_1);
        		
        		log.debug("dataCarrierObject: "+dataCarrierObject.getDataCarrierDiagramObjectId()+ " NAME: "+
        				dataCarrierObject.getDiagramObjectId());
        		
        		diaObject.getDataCarrierDiagramObject().add(dataCarrierObject);
            	
    		}
    		

    		//Add the Data Carriers
			for (DataCarrierDiagramObject dCarrier : diaObject.getDataCarrierDiagramObject()) {
				
				if (DataCarrierDiagramObjectDaoImpl.getInstance().getDataCarrierDiagramObjectById(dCarrier.getDataCarrierObjectdiagramObjectId()) == null) {
					
					log.debug("dCarrier: "+dCarrier.getDataCarrierDiagramObjectId());
					
					log.debug("DiagramObjectId 1 DiagramObjectId 2: "+dCarrier.getDataCarrierObjectdiagramObjectId()+ " "+dCarrier.getDiagramObjectId());
					
					DataCarrierDiagramObjectDaoImpl.getInstance().addDataCarrierDiagramObject(dCarrier.getDataCarrierObjectdiagramObjectId(), 
							dCarrier.getDiagramObjectId(), dCarrier.getInsertedBy());
					
				} else {
					log.debug("(DataCarrierDiagramObject with ID: "+dCarrier.getDataCarrierObjectdiagramObjectId()+" does already exist");
				}
				
			}

    		
        }
		
	}
	
	private void addDiagramInstanceObjectsByDocument(Element root) throws Exception {
		
		Element diagraminstanceobjects = root.element("diagraminstanceobjects");
        
        for (Iterator<Element> i = diagraminstanceobjects.elementIterator( "diagraminstanceobject" ); i.hasNext(); ) {
        	Element diagraminstanceobject = i.next();
        	
        	String elementText = diagraminstanceobject.getText();
    		
    		XStream xStream = new XStream(new XppDriver());
    		xStream.setMode(XStream.NO_REFERENCES);
    		
    		DiagramInstanceObject diaInstanceObj = (DiagramInstanceObject) xStream.fromXML(elementText);
    		
			log.debug("diaInstanceObj: "+diaInstanceObj.getDiagramInstanceObjectId()+ " NAME: "+diaInstanceObj.getDiagramObject().getName());
        
			if (DiagramInstanceObjectDaoImpl.getInstance().getDiagramInstanceObjectById(diaInstanceObj.getDiagramInstanceObjectId()) == null) {
				
				DiagramInstanceObjectDaoImpl.getInstance().addDiagramInstanceObjectByObject(diaInstanceObj);
				
			} else {
				log.debug("DiagramInstanceObject with ID: "+diaInstanceObj.getDiagramInstanceObjectId()+" does already exist");
			}
        }
		
	}
	
	private void addRolesByDocument(Element root) throws Exception {
		
		Element roles = root.element("roles");
        
        for (Iterator<Element> i = roles.elementIterator( "role" ); i.hasNext(); ) {
        	Element role = i.next();
        	
        	String elementText = role.getText();
    		
    		XStream xStream = new XStream(new XppDriver());
    		xStream.setMode(XStream.NO_REFERENCES);
    		
    		Role ro = (Role) xStream.fromXML(elementText);
    		
			log.debug("ro: "+ro.getRolesId()+ " NAME: "+ro.getAssignee().getLogin());
			
			if (RoleDaoImpl.getInstance().getRoleById(ro.getRolesId()) == null) {
				
				RoleDaoImpl.getInstance().addRoleByObject(ro);
				
			} else {
				log.debug("Role with ID: "+ro.getRolesId()+" does already exist");
			}
			
        }

	}
	
	private void addObjectIdentifiersByDocument(Element root) throws Exception {
		
		Element objectidentifiers = root.element("objectidentifiers");
        
        for (Iterator<Element> i = objectidentifiers.elementIterator( "objectidentifier" ); i.hasNext(); ) {
        	Element objectidentifier = i.next();
        	
        	String elementText = objectidentifier.getText();
    		
    		XStream xStream = new XStream(new XppDriver());
    		xStream.setMode(XStream.NO_REFERENCES);
    		
    		ObjectIdentifier objIdent = (ObjectIdentifier) xStream.fromXML(elementText);
    		
			log.debug("objIdent: "+objIdent.getObjectidentifier_id()+ " NAME: "+objIdent.getOrganization_id());
			
			if (ObjectIdentifierDaoImpl.getInstance().getCurrentObjectIdentifierById(objIdent.getObjectidentifier_id()) == null) {
				
				ObjectIdentifierDaoImpl.getInstance().addNewObjectIdentifier(objIdent);
				
			} else {
				
				log.debug("ObjectIdentifierDaoImpl with ID: "+objIdent.getObjectidentifier_id()+" does already exist");
				
				//Update ObjectIdentifierDaoImpl to newer ID
				ObjectIdentifierDaoImpl.getInstance().updateCurrentObjectIdentifier(objIdent);
				
			}
			
        }
		
	}	

	private void addUserSidebarPropertyByDocument(Element root) throws Exception {
		
		Element usersidebarproperties = root.element("usersidebarproperties");
        
        for (Iterator<Element> i = usersidebarproperties.elementIterator( "usersidebarproperty" ); i.hasNext(); ) {
        	Element usersidebarproperty = i.next();
        	
        	String elementText = usersidebarproperty.getText();
    		
    		XStream xStream = new XStream(new XppDriver());
    		xStream.setMode(XStream.NO_REFERENCES);
    		
    		UserSidebarProperty sideBarItem = (UserSidebarProperty) xStream.fromXML(elementText);
    		
			log.debug("sideBarItem: "+sideBarItem.getUserSidebarPropertyId()+ " NAME: "+sideBarItem.getDiagramNo());
			
			if (UserSidebarPropertyDaoImpl.getInstance().getUserSidebarPropertyById(sideBarItem.getUserSidebarPropertyId()) == null) {
				
				UserSidebarPropertyDaoImpl.getInstance().addUserSidebarPropertyByObject(sideBarItem);
				
			} else {
				
				log.debug("UserSidebarProperty with ID: "+sideBarItem.getUserSidebarPropertyId()+" does already exist");
				
			}
			
        }
		
	}	

}
