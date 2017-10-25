/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.Filtros.BlueprintsFiltro;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.Filtros.BlueprintFiltroException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Service
public class BlueprintsServices {
    
    @Autowired
    BlueprintsPersistence bpp=null;
    
    @Autowired
    BlueprintsFiltro filtro=null;
    
    
    public void setBpp(BlueprintsPersistence bpp) {
        this.bpp = bpp;
    }
    
    public void setFiltro(BlueprintsFiltro fl) {
        this.filtro = fl;
    }

    public void addNewBlueprint(Blueprint bp) throws  BlueprintNotFoundException{
        
        bpp.saveBlueprint(bp);
    }
    
    public void deleteBlueprint(Blueprint bp) throws  BlueprintNotFoundException{
        
        bpp.deleteBlueprint(bp);
    }
    
    public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException, BlueprintFiltroException{
        return filtro.filtrarBlueprints(bpp.getAllBlueprints());
    }
    
    /**
     * 
     * @param author blueprint's author
     * @param name blueprint's name
     * @return the blueprint of the given name created by the given author
     * @throws BlueprintNotFoundException if there is no such blueprint
     * @throws edu.eci.arsw.blueprints.Filtros.BlueprintFiltroException
     */
    public Blueprint getBlueprint(String author,String name) throws BlueprintNotFoundException, BlueprintFiltroException{
        Blueprint a=bpp.getBlueprint(author, name);
        if(a!=null){
            filtro.filtrarBlueprint(a);
        }
        return a;
    }
    
    /**
     * 
     * @param author blueprint's author
     * @return all the blueprints of the given author
     * @throws BlueprintNotFoundException if the given author doesn't exist
     * @throws edu.eci.arsw.blueprints.Filtros.BlueprintFiltroException
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException, BlueprintFiltroException{
        Set<Blueprint> a=filtro.filtrarBlueprints(bpp.getBlueprintByAuthor(author));
        return a;
    }
    
}
