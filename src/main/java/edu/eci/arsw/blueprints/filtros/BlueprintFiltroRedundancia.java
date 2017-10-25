/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.Filtros;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 *
 * @author laura
 */
@Service
public class BlueprintFiltroRedundancia implements BlueprintsFiltro {

    @Override
    public Blueprint filtrarBlueprint(Blueprint pl) throws BlueprintFiltroException {
        List<Point> ps = pl.getPoints();
        List<Point> psnew = new ArrayList<>();
        if(ps.size()>=2){
            for (int i=1;i<=ps.size() ;i++){
                if(i==ps.size()){
                    if (!ps.get(0).toString().equals(ps.get(i-1).toString())) {
                        psnew.add(ps.get(i-1));
                    }
                }else if (!ps.get(i-1).toString().equals(ps.get(i).toString())) {
                    psnew.add(ps.get(i-1));
                }
            }
            pl.setPoints(psnew);
        }
        return pl;
    }

    @Override
    public Set<Blueprint> filtrarBlueprints(Set<Blueprint> pls) throws BlueprintFiltroException {
        for (Blueprint bp : pls) {
            List<Point> ps = bp.getPoints();
            List<Point> psnew = new ArrayList<>();
            if(ps.size()>=2){
                for (int i=1;i<=ps.size() ;i++){
                    if(i==ps.size()){
                        if (!ps.get(0).toString().equals(ps.get(i-1).toString())) {
                            psnew.add(ps.get(i-1));
                        }
                    }else if (!ps.get(i-1).toString().equals(ps.get(i).toString())) {
                        psnew.add(ps.get(i-1));
                    }
                }
                bp.setPoints(psnew);
            }
        }
        return pls;
    }

}
