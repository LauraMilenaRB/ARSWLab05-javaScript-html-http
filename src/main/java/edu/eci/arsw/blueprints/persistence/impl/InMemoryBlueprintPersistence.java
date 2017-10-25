/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Service
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final Map<Tuple<String,String>,Blueprint> blueprints=new HashMap<>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts=new Point[]{new Point(140, 140),new Point(90, 90),new Point(90, 140)};
        Point[] pts1=new Point[]{new Point(10, 55),new Point(55, 55),new Point(55, 14)};
        Blueprint bp1=new Blueprint("author", "bpname",pts1);
        Blueprint bp2=new Blueprint("john", "thepaint",pts);
        Blueprint bp6=new Blueprint("juan", "thepaintjuan",pts);
        Blueprint bp7=new Blueprint("juan", "thepaintjuan1",pts1);
        blueprints.put(new Tuple<>(bp1.getAuthor(),bp1.getName()), bp1);
        blueprints.put(new Tuple<>(bp2.getAuthor(),bp2.getName()), bp2);
        blueprints.put(new Tuple<>(bp6.getAuthor(),bp6.getName()), bp6);
        blueprints.put(new Tuple<>(bp7.getAuthor(),bp7.getName()), bp7);
    }    
    
    @Override
    public void  saveBlueprint(Blueprint bp) throws BlueprintNotFoundException{
        synchronized(blueprints){
            if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
                
                throw new BlueprintNotFoundException("The given blueprint already exists: "+bp);
            }
            else{blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
            }
        }
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        return blueprints.get(new Tuple<>(author, bprintname));
    }

    @Override
    public Set<Blueprint> getBlueprintByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> Bpautor=new HashSet<>();
        for (Tuple key : blueprints.keySet()) {
            if(key.getElem1().equals(author)){
                Bpautor.add(blueprints.get(key));
            }
        }
        return Bpautor;
    }

    @Override
    public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException {
        Set<Blueprint> setBlueprints=new HashSet<>();
        for (Tuple key : blueprints.keySet()) {
            setBlueprints.add(blueprints.get(key));
        }
        return setBlueprints;
    }

    @Override
    public void deleteBlueprint(Blueprint bp) throws BlueprintNotFoundException{
        synchronized(blueprints){
            if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
                blueprints.remove(new Tuple<>(bp.getAuthor(),bp.getName()));
            }
            else{
                throw new BlueprintNotFoundException("The given blueprint no exists: "+bp);
            }
        }
    }

    
    
}
