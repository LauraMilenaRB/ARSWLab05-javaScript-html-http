/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.services;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.Filtros.BlueprintFiltroException;
import edu.eci.arsw.blueprints.Filtros.BlueprintFiltroSubmuestreo;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class BlueprintFiltroSubmuestreoTest {
    
    @Test
    public void filtrarBlueprints() throws BlueprintNotFoundException {
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        BlueprintFiltroSubmuestreo filtroSubmuestreo= new BlueprintFiltroSubmuestreo();
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10),new Point(0, 0),new Point(10, 10)};
        Set<Blueprint> setBlueprints=new HashSet<>();
        Blueprint bp=new Blueprint("lolo", "thepaint",pts);
        Blueprint bp1=new Blueprint("lolo", "thepaint1",pts);
        Blueprint bp2=new Blueprint("lolo", "thepaint2",pts);
        Blueprint bp3=new Blueprint("kk", "thepaint0",pts);
        setBlueprints.add(bp);
        setBlueprints.add(bp1);
        setBlueprints.add(bp2);
        try {
            ibpp.saveBlueprint(bp);
            ibpp.saveBlueprint(bp1);
            ibpp.saveBlueprint(bp2);
            ibpp.saveBlueprint(bp3);
            
            assertEquals("No se filtraron los puntos correctamente",filtroSubmuestreo.filtrarBlueprint(ibpp.getBlueprint("kk", "thepaint0")).getPoints().size(), pts.length-1);
        } catch (BlueprintFiltroException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }
    }
    
    @Test
    public void filtrarBlueprint() {
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Set<Blueprint> setBlueprints=new HashSet<>();
        Blueprint bp=new Blueprint("lolo", "thepaint",pts);
        Blueprint bp1=new Blueprint("lolo", "thepaint1",pts);
        Blueprint bp2=new Blueprint("lolo", "thepaint2",pts);
        Blueprint bp3=new Blueprint("kk", "thepaint0",pts);
        setBlueprints.add(bp);
        setBlueprints.add(bp1);
        setBlueprints.add(bp2);
        try {
            ibpp.saveBlueprint(bp);
            ibpp.saveBlueprint(bp1);
            ibpp.saveBlueprint(bp2);
            ibpp.saveBlueprint(bp3);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintFiltroSubmuestreoTest.class.getName()).log(Level.SEVERE, null, ex);
        }   
        
    }
}

