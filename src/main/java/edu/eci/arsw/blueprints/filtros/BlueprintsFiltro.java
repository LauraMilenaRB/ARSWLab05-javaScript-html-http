/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.Filtros;

import edu.eci.arsw.blueprints.model.Blueprint;
import java.util.List;
import java.util.Set;

/**
 *
 * @author laura
 */
public interface BlueprintsFiltro {
    
    public Blueprint filtrarBlueprint(Blueprint pl) throws BlueprintFiltroException; 
    
    public Set<Blueprint> filtrarBlueprints(Set<Blueprint> pl) throws BlueprintFiltroException;
}
