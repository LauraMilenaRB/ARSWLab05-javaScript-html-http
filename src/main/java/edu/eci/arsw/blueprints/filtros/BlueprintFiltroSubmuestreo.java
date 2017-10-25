/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.Filtros;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import java.util.*;
import org.springframework.stereotype.Service;

/**
 *
 * @author laura
 */
//@Service
public class BlueprintFiltroSubmuestreo implements BlueprintsFiltro {

    @Override
    public Blueprint filtrarBlueprint(Blueprint pl) throws BlueprintFiltroException {
        List<Point> ps= pl.getPoints();
        List<Point> psnew=new ArrayList<>();
        for (int i=0;i<ps.size();i++) {
            if(i%2!=0 && i!=0){
                psnew.add(ps.get(i));
            }else if(i==0){
                psnew.add(ps.get(i));
            }
        }
        pl.setPoints(psnew);
        return pl;
    }

    @Override
    public Set<Blueprint> filtrarBlueprints(Set<Blueprint> pls) throws BlueprintFiltroException {
        for (Blueprint bp : pls) {
            List<Point> ps = bp.getPoints();
            List<Point> psnew = new ArrayList<>();
            for (int i = 0; i < ps.size(); i++) {
                if (i % 2 != 0 && i != 0) {
                    psnew.add(ps.get(i));
                } else if (i == 0) {
                    psnew.add(ps.get(i));
                }
            }
            bp.setPoints(psnew);
        }
        return pls;
    }

}
