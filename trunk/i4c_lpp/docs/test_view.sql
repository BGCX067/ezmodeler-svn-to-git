CREATE VIEW orphans AS 
(
SELECT 
    c.diagramobject_id, c.name, c.objecttypename, c.inserted, c.updated 
FROM diagramobject c 
WHERE 
    c.diagramobject_id NOT IN 
        (
            SELECT c.diagramobject_id 
            FROM diagramobject c 
            INNER JOIN datacarrier_diagramobject dCarrier ON (c.diagramobject_id = dCarrier.datacarrier_object_diagramobject_id) 
            INNER JOIN diagraminstanceobject dioObject ON (dioObject.diagramobject_id = dCarrier.diagramobject_id) 
            INNER JOIN diagramsummary diaSummery ON (diaSummery.diagram_id = dioObject.diagram_id) 
            GROUP BY c.diagramobject_id  
        )  
AND c.diagramobject_id NOT IN 
        (  
            SELECT c.diagramobject_id 
            FROM diagramobject c 
            INNER JOIN diagraminstanceobject dioObject ON (dioObject.diagramobject_id = c.diagramobject_id) 
            INNER JOIN diagramsummary diaSummery ON (diaSummery.diagram_id = dioObject.diagram_id) 
            GROUP BY c.diagramobject_id 
        )
);