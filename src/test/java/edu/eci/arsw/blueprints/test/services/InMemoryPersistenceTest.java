package edu.eci.arsw.blueprints.test.services;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.Filtros.BlueprintFiltroException;
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
public class InMemoryPersistenceTest {
    
    @Test
    public void saveNewAndLoadTest() throws BlueprintFiltroException, BlueprintNotFoundException{
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();

        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0=new Blueprint("mack", "mypaint",pts0);
        
        ibpp.saveBlueprint(bp0);
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john1", "thepaint",pts);
        
        ibpp.saveBlueprint(bp);
        
        assertNotNull("Loading a previously stored blueprint returned null.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()));
        
        assertEquals("Loading a previously stored blueprint returned a different blueprint.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()), bp);
        
    }


    @Test
    public void saveExistingBpTest() {
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("milena1", "thepaint",pts);
        Point[] pts2=new Point[]{new Point(10, 10),new Point(20, 20)};
        Blueprint bp2=new Blueprint("milena", "thepaint",pts2);
        try {
            ibpp.saveBlueprint(bp);
            ibpp.saveBlueprint(bp2);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void getBlueprintsTest() {
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint10",pts);
        Blueprint bp1=new Blueprint("lala", "thepaint11",pts);
        
        try {
            ibpp.saveBlueprint(bp);
            ibpp.saveBlueprint(bp1);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            assertEquals("Blue print no encontrado por el nombre ", ibpp.getBlueprint("lala","thepaint11"), bp1);
        } catch (BlueprintNotFoundException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }
    }

    @Test
    public void getBlueprintsByAuthorTest() {
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Set<Blueprint> setBlueprints=new HashSet<>();
        Blueprint bp=new Blueprint("lolo", "thepaint",pts);
        Blueprint bp1=new Blueprint("lolo", "thepaint1",pts);
        Blueprint bp2=new Blueprint("lolo", "thepaint2",pts);
        Blueprint bp3=new Blueprint("andres", "thepaint0",pts);
        setBlueprints.add(bp);
        setBlueprints.add(bp1);
        setBlueprints.add(bp2);
        try {
            ibpp.saveBlueprint(bp);
            ibpp.saveBlueprint(bp1);
            ibpp.saveBlueprint(bp2);
            ibpp.saveBlueprint(bp3);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Set<Blueprint> s= ibpp.getBlueprintByAuthor("lolo");
            assertEquals("El conjunto de Blueprints no corresponde al conjunto del author",s.contains(bp3),false);
            assertEquals("El conjunto de Blueprints no contiene todos los Blueprints del autor",s,setBlueprints);
        } catch (BlueprintNotFoundException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }
                
        
    }
}
