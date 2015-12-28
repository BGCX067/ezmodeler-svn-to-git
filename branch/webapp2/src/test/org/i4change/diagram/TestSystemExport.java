package org.i4change.diagram;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.i4change.app.data.basic.Configurationmanagement;
import org.i4change.app.data.basic.ObjectIdentifierDaoImpl;
import org.i4change.app.data.diagram.AssigneeDaoImpl;
import org.i4change.app.data.diagram.DiagramDaoImpl;
import org.i4change.app.data.diagram.DiagramInstanceObjectDaoImpl;
import org.i4change.app.data.diagram.DiagramObjectDaoImpl;
import org.i4change.app.data.diagram.DiagramTypeDaoImpl;
import org.i4change.app.data.diagram.IssueAssigneeDaoImpl;
import org.i4change.app.data.diagram.RoleDaoImpl;
import org.i4change.app.data.domain.daos.OrganisationDaoImpl;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.data.user.daos.UserSidebarPropertyDaoImpl;
import org.i4change.app.hibernate.beans.adresses.Adresses_Emails;
import org.i4change.app.hibernate.beans.basic.Configuration;
import org.i4change.app.hibernate.beans.basic.ObjectIdentifier;
import org.i4change.app.hibernate.beans.diagram.DataCarrierDiagramObject;
import org.i4change.app.hibernate.beans.diagram.Diagram;
import org.i4change.app.hibernate.beans.diagram.DiagramInstanceObject;
import org.i4change.app.hibernate.beans.diagram.DiagramObject;
import org.i4change.app.hibernate.beans.diagram.IssueAssignee;
import org.i4change.app.hibernate.beans.diagram.Role;
import org.i4change.app.hibernate.beans.domain.Organisation;
import org.i4change.app.hibernate.beans.domain.Organisation_Users;
import org.i4change.app.hibernate.beans.user.UserSidebarProperty;
import org.i4change.app.hibernate.beans.user.Users;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class TestSystemExport extends TestCase {
	
	private static final Log log = LogFactory.getLog(TestSystemExport.class);	
	
	public void testGetDiagramList() {
		
		try {
			
			Document document = DocumentHelper.createDocument();
			document.setXMLEncoding("UTF-8");
			document.addComment(
					"###############################################\n" +
					"This File is auto-generated by the Backup Tool \n" +
					"###############################################");						
			
			Element root = document.addElement("root");
			
//			document = this.addConfigurationsToDocument(document, root);
//			
//			document = this.addOrganizationsToDocument(document, root);
//			
//			document = this.addUserToDocument(document, root);
//			
//			document = this.addDiagramObjectsToDocument(document, root);
//			
//			document = this.addDiagramsToDocument(document, root);
//			
//			document = this.addDiagramObjectInstancesToDocument(document, root);
//			
//			document = this.addIssueAssigneeToDocument(document, root);
//			
//			document = this.addRolesToDocument(document, root);
//			
//			document = this.addObjectIdentifiersToDocument(document, root);
//			
			document = this.addUserSidebarPropertyToDocument(document, root);
			
			this.serializetoXML(System.out, "UTF-8", document);
		
			//this.addDataByDocument(document);
			
		} catch (Exception err) {
			log.error("[testGetDiagramList]",err);
		}
		
	}
	
	/* ###############################
	 * Functions to generate Objects again
	 * 
	 */
	
	public void addDataByDocument(Document document) throws Exception {
		
        Element root = document.getRootElement();
        
        this.addOrganisationsByDocument(root);
        
        this.addUsersByDocument(root);
        
        this.addConfigurationByDocument(root);
        
        this.addDiagramWithoutParentByDocument(root);
        
        this.addDiagramObjectsByDocument(root);
        
        this.addDiagramParentByDocument(root);
        
        this.addDiagramInstanceObjectsByDocument(root);
        
        this.addRolesByDocument(root);
        
        this.addObjectIdentifiersByDocument(root);
        
	}
	
	
	private Long addUsersByDocument(Element root) throws Exception {
		
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
    		
        }
		
		return null;
	}
	
	private Long addOrganisationsByDocument(Element root) throws Exception {
		
		Element organizations = root.element("organizations");
        
        for (Iterator<Element> i = organizations.elementIterator( "organization" ); i.hasNext(); ) {
        	Element configuration = i.next();
        	
        	String elementText = configuration.getText();
    		
    		XStream xStream = new XStream(new XppDriver());
    		xStream.setMode(XStream.NO_REFERENCES);
    		
    		Organisation org = (Organisation) xStream.fromXML(elementText);
    		
    		log.debug("org: "+org.getOrganisation_id()+ " NAME: "+org.getName());
        }
		
		return null;
	}

	private Long addConfigurationByDocument(Element root) throws Exception {
		
		Element configurations = root.element("configurations");
        
        for (Iterator<Element> i = configurations.elementIterator( "configuration" ); i.hasNext(); ) {
        	Element configuration = i.next();
        	
        	String elementText = configuration.getText();
    		
    		XStream xStream = new XStream(new XppDriver());
    		xStream.setMode(XStream.NO_REFERENCES);
    		
    		Configuration conf = (Configuration) xStream.fromXML(elementText);
    		
    		log.debug("conf: "+conf.getConfiguration_id()+ " NAME: "+conf.getConf_key());
        }
		
		return null;
	}

	private Long addDiagramWithoutParentByDocument(Element root) throws Exception {
		
		Element diagrams = root.element("diagrams");
        
        for (Iterator<Element> i = diagrams.elementIterator( "diagram" ); i.hasNext(); ) {
        	Element diagram = i.next();
        	
        	String elementText = diagram.getText();
    		
    		XStream xStream = new XStream(new XppDriver());
    		xStream.setMode(XStream.NO_REFERENCES);
    		
    		Diagram dia = (Diagram) xStream.fromXML(elementText);
    		
    		dia.setParentDiagramObject(null);
    		
    		log.debug("dia: "+dia.getDiagramId()+ " NAME: "+dia.getName());
        }
		
		return null;
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
    		}
        }
		
		return null;
	}
		

	private Long addDiagramObjectsByDocument(Element root) throws Exception {
		
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
    		
        }
		
		return null;
	}
	
	private Long addDiagramInstanceObjectsByDocument(Element root) throws Exception {
		
		Element diagraminstanceobjects = root.element("diagraminstanceobjects");
        
        for (Iterator<Element> i = diagraminstanceobjects.elementIterator( "diagraminstanceobject" ); i.hasNext(); ) {
        	Element diagraminstanceobject = i.next();
        	
        	String elementText = diagraminstanceobject.getText();
    		
    		XStream xStream = new XStream(new XppDriver());
    		xStream.setMode(XStream.NO_REFERENCES);
    		
    		DiagramInstanceObject diaInstanceObj = (DiagramInstanceObject) xStream.fromXML(elementText);
    		
			log.debug("diaInstanceObj: "+diaInstanceObj.getDiagramInstanceObjectId()+ " NAME: "+diaInstanceObj.getDiagramObject().getName());
        }
		
		return null;
	}
	
	private Long addRolesByDocument(Element root) throws Exception {
		
		Element roles = root.element("roles");
        
        for (Iterator<Element> i = roles.elementIterator( "role" ); i.hasNext(); ) {
        	Element role = i.next();
        	
        	String elementText = role.getText();
    		
    		XStream xStream = new XStream(new XppDriver());
    		xStream.setMode(XStream.NO_REFERENCES);
    		
    		Role ro = (Role) xStream.fromXML(elementText);
    		
			log.debug("ro: "+ro.getRolesId()+ " NAME: "+ro.getAssignee().getLogin());
        }
		
		return null;
	}
	
	private Long addObjectIdentifiersByDocument(Element root) throws Exception {
		
		Element objectidentifiers = root.element("objectidentifiers");
        
        for (Iterator<Element> i = objectidentifiers.elementIterator( "objectidentifier" ); i.hasNext(); ) {
        	Element objectidentifier = i.next();
        	
        	String elementText = objectidentifier.getText();
    		
    		XStream xStream = new XStream(new XppDriver());
    		xStream.setMode(XStream.NO_REFERENCES);
    		
    		ObjectIdentifier objIdent = (ObjectIdentifier) xStream.fromXML(elementText);
    		
			log.debug("objIdent: "+objIdent.getObjectidentifier_id()+ " NAME: "+objIdent.getOrganization_id());
        }
		
		return null;
	}	
	
	
	/* ########################################
	 * Functions to generate XML
	 * 
	 */

	public Document addOrganizationsToDocument(Document document, Element root) throws Exception {
		
		Element users = root.addElement("organizations");

		List<Organisation> orgList = OrganisationDaoImpl.getInstance().getOrganisations();
		
		for (Iterator<Organisation> it = orgList.iterator();it.hasNext();) {
			Organisation org = it.next();

			Element organization = users.addElement("organization");

			XStream xStream = new XStream(new XppDriver());
			xStream.setMode(XStream.NO_REFERENCES);
			
			organization.setText(xStream.toXML(org));

		}

		return document;
	}	
	
	public Document addIssueAssigneeToDocument(Document document, Element root) throws Exception {
		
		Element issueassignees = root.addElement("issueassignees");

		List<IssueAssignee> issueAssingeeList = IssueAssigneeDaoImpl.getInstance().getIssueAssignees();
		
		for (Iterator<IssueAssignee> it = issueAssingeeList.iterator();it.hasNext();) {
			IssueAssignee issueAssingee = it.next();
			
			issueAssingee.getAssignee().setAdresses(null);
			issueAssingee.getAssignee().setOrganisation_users(null);
			
			issueAssingee.getDiagramObject().setDataCarrierDiagramObject(null);
			issueAssingee.getDiagramObject().setAssignee(null);
			issueAssingee.getDiagramObject().setInsertedby(null);

			Element issueassignee = issueassignees.addElement("issueassignee");

			XStream xStream = new XStream(new XppDriver());
			xStream.setMode(XStream.NO_REFERENCES);
			
			issueassignee.setText(xStream.toXML(issueAssingee));

		}

		return document;
	}	
	
	public Document addConfigurationsToDocument(Document document, Element root) throws Exception {
		
		Element users = root.addElement("configurations");

		List<Configuration> confList = Configurationmanagement.getInstance().getConfigurationsBackup();
		
		for (Iterator<Configuration> it = confList.iterator();it.hasNext();) {
			Configuration conf = it.next();

			Element configuration = users.addElement("configuration");
			
			//this.addFieldToElement(conf);

			XStream xStream = new XStream(new XppDriver());
			xStream.setMode(XStream.NO_REFERENCES);
			
			configuration.setText(xStream.toXML(conf));
			
		}

		return document;
	}
	

	public Document addUserToDocument(Document document, Element root) throws Exception {
		
		List<Users> uList = UserDaoImpl.getInstance().getAllBackupUsers();
		
		Element users = root.addElement("users");

		for (Iterator<Users> it = uList.iterator();it.hasNext();) {
			Users u = it.next();

			Set<Adresses_Emails> setEmails = u.getAdresses().getEmails();
			Set<Organisation_Users> orgUsers = u.getOrganisation_users();
			
			u.getAdresses().setEmails(null);
			u.setOrganisation_users(null);
			
			Element user = users.addElement("user");

			XStream xStream = new XStream(new XppDriver());
			xStream.setMode(XStream.NO_REFERENCES);
			
			user.setText(xStream.toXML(u));
			
			Element adressesemails = user.addElement("adressesemails");
			
			for (Iterator<Adresses_Emails> iterAddr = setEmails.iterator();iterAddr.hasNext();) {
				Adresses_Emails emails = iterAddr.next();
				
				Element adressesemail = adressesemails.addElement("adressesemail");
				
				XStream xStream_2 = new XStream(new XppDriver());
				xStream_2.setMode(XStream.NO_REFERENCES);
				
				adressesemail.setText(xStream_2.toXML(emails));
				
			}
			
			Element organisationusers = user.addElement("organisationusers");
			
			for (Iterator<Organisation_Users> iterAddr = orgUsers.iterator();iterAddr.hasNext();) {
				Organisation_Users orgUser = iterAddr.next();
				
				Element organisationuser = organisationusers.addElement("organisationuser");
				
				XStream xStream_3 = new XStream(new XppDriver());
				xStream_3.setMode(XStream.NO_REFERENCES);
				
				organisationuser.setText(xStream_3.toXML(orgUser));
				
			}
			
		}

		return document;
	}	

	public Document addDiagramsToDocument(Document document, Element root) throws Exception {
		
		List<Diagram> diagramList = DiagramDaoImpl.getInstance().getDiagrams();
		
		Element diagrams = root.addElement("diagrams");

		for (Iterator<Diagram> it = diagramList.iterator();it.hasNext();) {
			Diagram dia = it.next();
			
			if (dia.getInsertedby() != null) {
				dia.getInsertedby().setOrganisation_users(null);
				dia.getInsertedby().setAdresses(null);
			}
			
			if (dia.getUpdatedby() != null) {
				dia.getUpdatedby().setOrganisation_users(null);
				dia.getUpdatedby().setAdresses(null);
			}
			
			if (dia.getParentDiagramObject()!= null ){
				dia.getParentDiagramObject().setInsertedby(null);
				dia.getParentDiagramObject().setDataCarrierDiagramObject(null);
			}
			
			Element diagram = diagrams.addElement("diagram");

			XStream xStream = new XStream(new XppDriver());
			xStream.setMode(XStream.NO_REFERENCES);
			
			diagram.setText(xStream.toXML(dia));
			
		}

		return document;
	}

	public Document addDiagramObjectsToDocument(Document document, Element root) throws Exception {
		
		List<DiagramObject> diaObjectList = DiagramObjectDaoImpl.getInstance().getDiagramObjects();
		
		Element diagramobjects = root.addElement("diagramobjects");

		for (Iterator<DiagramObject> it = diaObjectList.iterator();it.hasNext();) {
			DiagramObject diaObj = it.next();

			Set<DataCarrierDiagramObject> dataCarrierDiagramObjectSet = diaObj.getDataCarrierDiagramObject();
			diaObj.setDataCarrierDiagramObject(null);
			
			diaObj.getInsertedby().setOrganisation_users(null);
			diaObj.getInsertedby().setAdresses(null);
			
			if (diaObj.getDiagram() != null && diaObj.getDiagram().getParentDiagramObject() != null) {
				diaObj.getDiagram().getParentDiagramObject().setDataCarrierDiagramObject(null);
			}
			
			Element diagramobject = diagramobjects.addElement("diagramobject");

			XStream xStream = new XStream(new XppDriver());
			xStream.setMode(XStream.NO_REFERENCES);
			
			diagramobject.setText(xStream.toXML(diaObj));
			
			Element datacarrierdiagramobjects = diagramobject.addElement("datacarrierdiagramobject");
			
			for (Iterator<DataCarrierDiagramObject> iterAddr = dataCarrierDiagramObjectSet.iterator();iterAddr.hasNext();) {
				DataCarrierDiagramObject dataCarrierObject = iterAddr.next();
				
				dataCarrierObject.setDataCarrierObjectdiagramObject(null);
				dataCarrierObject.setDiagramObject(null);
				
				Element datacarrierdiagramobject = datacarrierdiagramobjects.addElement("datacarrierdiagramobject");
				
				XStream xStream_3 = new XStream(new XppDriver());
				xStream_3.setMode(XStream.NO_REFERENCES);
				
				datacarrierdiagramobject.setText(xStream_3.toXML(dataCarrierObject));
				
			} 
			
		}

		return document;
	}

	public Document addDiagramObjectInstancesToDocument(Document document, Element root) throws Exception {
		
		List<DiagramInstanceObject> diaInstanceObjectList = DiagramInstanceObjectDaoImpl.getInstance().getDiagramInstanceObjects();
		
		Element diagraminstanceobjects = root.addElement("diagraminstanceobjects");

		for (Iterator<DiagramInstanceObject> it = diaInstanceObjectList.iterator();it.hasNext();) {
			DiagramInstanceObject diaInstanceObj = it.next();
			
			if (diaInstanceObj.getDiagramObject() != null) {
				diaInstanceObj.getDiagramObject().setDataCarrierDiagramObject(null);
				diaInstanceObj.getDiagramObject().setInsertedby(null);
				if (diaInstanceObj.getDiagramObject().getAssignee() != null) {
					diaInstanceObj.getDiagramObject().getAssignee().getAssignee().setAdresses(null);
					diaInstanceObj.getDiagramObject().getAssignee().getAssignee().setOrganisation_users(null);
				}
				//diaInstanceObj.getDiagramObject().setIssueassignee(null);
			}
			
			diaInstanceObj.setStartDiagramObject(null);
			diaInstanceObj.setEndDiagramObject(null);
			
			if (diaInstanceObj.getDiagram() != null) {
				diaInstanceObj.getDiagram().setParentDiagramObject(null);
				diaInstanceObj.getDiagram().setInsertedby(null);
				diaInstanceObj.getDiagram().setUpdatedby(null);
			}
			
			//diaInstanceObj.setDiagram(null);

			Element diagraminstanceobject = diagraminstanceobjects.addElement("diagraminstanceobject");

			XStream xStream = new XStream(new XppDriver());
			xStream.setMode(XStream.NO_REFERENCES);
			
			diagraminstanceobject.setText(xStream.toXML(diaInstanceObj));
			
		}

		return document;
	}
	
	public Document addRolesToDocument(Document document, Element root) throws Exception {
		
		List<Role> roleList = RoleDaoImpl.getInstance().getRoles();
		
		Element roles = root.addElement("roles");

		for (Iterator<Role> it = roleList.iterator();it.hasNext();) {
			Role ro = it.next();

			ro.getRoleObject().setDataCarrierDiagramObject(null);
			ro.getRoleObject().setInsertedby(null);
			
			ro.getAssignee().setAdresses(null);
			ro.getAssignee().setOrganisation_users(null);
			
			Element role = roles.addElement("role");

			XStream xStream = new XStream(new XppDriver());
			xStream.setMode(XStream.NO_REFERENCES);
			
			role.setText(xStream.toXML(ro));
			
		}

		return document;
	}

	public Document addObjectIdentifiersToDocument(Document document, Element root) throws Exception {
		
		List<ObjectIdentifier> objIdentList = ObjectIdentifierDaoImpl.getInstance().getCurrentObjectIdentifiers();
		
		Element objectidentifiers = root.addElement("objectidentifiers");
	
		for (Iterator<ObjectIdentifier> it = objIdentList.iterator();it.hasNext();) {
			ObjectIdentifier objIdent = it.next();
	
			Element objectidentifier = objectidentifiers.addElement("objectidentifier");
	
			XStream xStream = new XStream(new XppDriver());
			xStream.setMode(XStream.NO_REFERENCES);
			
			objectidentifier.setText(xStream.toXML(objIdent));
			
		}
	
		return document;
	}
	

	private Document addUserSidebarPropertyToDocument(Document document, Element root) throws Exception {
		
		List<UserSidebarProperty> sideBarList = UserSidebarPropertyDaoImpl.getInstance().getUserSidebarPropertyList();
		
		
		Element usersidebarproperties = root.addElement("usersidebarproperties");
	
		for (Iterator<UserSidebarProperty> it = sideBarList.iterator();it.hasNext();) {
			UserSidebarProperty sideBarItem = it.next();
			
			sideBarItem.getUsers().setAdresses(null);
			sideBarItem.getUsers().setOrganisation_users(null);
	
			Element usersidebarproperty = usersidebarproperties.addElement("usersidebarproperty");
	
			XStream xStream = new XStream(new XppDriver());
			xStream.setMode(XStream.NO_REFERENCES);
			
			usersidebarproperty.setText(xStream.toXML(sideBarItem));
			
		}
	
		return document;
	}	

	public void serializetoXML(OutputStream out, String aEncodingScheme, Document doc)
	throws Exception {
		OutputFormat outformat = OutputFormat.createPrettyPrint();
		outformat.setEncoding(aEncodingScheme);
		XMLWriter writer = new XMLWriter(out, outformat);
		writer.write(doc);
		writer.flush();
	}
	
}