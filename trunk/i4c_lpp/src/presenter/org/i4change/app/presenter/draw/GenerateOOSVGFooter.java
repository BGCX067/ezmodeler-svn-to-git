package org.i4change.app.presenter.draw;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.beans.ExportImportJob;
import org.i4change.app.data.diagram.DiagramDaoImpl;
import org.i4change.app.hibernate.beans.diagram.Diagram;
import org.i4change.app.hibernate.beans.export.PresentationTemplate;
import org.i4change.app.presenter.draw.helper.DrawGraphicStylesList;
import org.i4change.app.presenter.draw.helper.DrawTextStyleList;

public class GenerateOOSVGFooter extends BaseShapesOOSVG {
	
	private static final Log log = LogFactory.getLog(GenerateOOSVGFooter.class);

	private static GenerateOOSVGFooter instance = null;
	  
	//Spring managed beans
	private DiagramDaoImpl diagramDaoImpl;
	
	public static synchronized GenerateOOSVGFooter getInstance() {
		if (instance == null) {
			instance = new GenerateOOSVGFooter();
		}
		return instance;
	}
	
	public DiagramDaoImpl getDiagramDaoImpl() {
		return diagramDaoImpl;
	}
	public void setDiagramDaoImpl(DiagramDaoImpl diagramDaoImpl) {
		this.diagramDaoImpl = diagramDaoImpl;
	}


	public String generateFooter(DrawTextStyleList drawTextStyleList,
			ExportImportJob exportJob, DrawGraphicStylesList drawGraphicStylesList,
			PresentationTemplate pTemplate) {
		try {
			
			String obj = "";
			
			double x = 0;
			//double y = 20.194;
			double y = pTemplate.getFootery();
			double height = 0.806;
			double width = 18.5;
			
			String text = "";
			log.debug("Get Diagram By Id: "+exportJob.getDiagramId());
			if (exportJob.getDiagramId() != 0) {
				Diagram diagram = this.diagramDaoImpl.getDiagramById(exportJob.getDiagramId());
				log.debug("diagram: "+diagram);
				if (diagram.getInsertedby() != null) {
					text = diagram.getName()+" (r"+diagram.getDiagramrevision().getDiagramrevisionId()+" "+diagram.getInsertedby().getFirstname()+" "+diagram.getInsertedby().getLastname()+")";
				} else {
					text = diagram.getName()+" (r"+diagram.getDiagramrevision().getDiagramrevisionId()+")";
				}
			} else {
				text = exportJob.getDiagramName();
			}
			
			obj += this.drawText(x, y, width, height, text, drawTextStyleList.getDrawTextStyleFooter(), 
						drawTextStyleList.getDrawTextStyleFooter(), drawGraphicStylesList.getDrawGraphicStyleTextBox());
			
			log.debug("FOOTER "+obj);
			return obj;
		} catch (Exception err) {
			log.error("[generateFooter]",err);
		}
		return "";
	}
	

}
