/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import edu.eci.arsw.blueprints.Filtros.BlueprintFiltroException;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hcadavid
 */
@RestController
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {
    
    @Autowired
    BlueprintsServices blueService;
            
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> manejadorGetAllBlueprints() {
        try {
            return new ResponseEntity<>(blueService.getAllBlueprints(), HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException | BlueprintFiltroException  ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error"+ex,HttpStatus.NOT_FOUND);
        }
    }
    
    @RequestMapping(path = "/{autor}",method = RequestMethod.GET)
    public ResponseEntity<?> manejadorGetBlueprintByAutor(@PathVariable String autor){
        try {
            if(blueService.getBlueprintsByAuthor(autor).isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }else{
                return new ResponseEntity<>(blueService.getBlueprintsByAuthor(autor),HttpStatus.ACCEPTED);}
        } catch (BlueprintNotFoundException | BlueprintFiltroException  ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error"+ex,HttpStatus.NOT_FOUND);
        } 
    }
    @RequestMapping(path = "/{autor}/{name}",method = RequestMethod.GET)
    public ResponseEntity<?> manejadorGetBlueprint(@PathVariable String autor,@PathVariable String name){
        try {
            if(blueService.getBlueprint(autor, name)==null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }else{
                return new ResponseEntity<>(blueService.getBlueprint(autor, name),HttpStatus.ACCEPTED);}
        } catch (BlueprintNotFoundException | BlueprintFiltroException  ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error"+ex,HttpStatus.NOT_FOUND);
        } 
    }
    @RequestMapping(path="/planos",method = RequestMethod.POST)
    public ResponseEntity<?> manejadorNewBlueprint(@RequestBody Blueprint blue){
        try {
            blueService.addNewBlueprint(blue);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error"+ex,HttpStatus.FORBIDDEN);

        } 
    }
    
    @RequestMapping(path="/addpoints/{autor}/{name}",method = RequestMethod.PUT)
    public ResponseEntity<?> manejadorAddPoints(@RequestBody List<Point> p,@PathVariable String autor, @PathVariable String name){
        try {
            if(blueService.getBlueprint(autor, name)==null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }else{
                blueService.getBlueprint(autor, name).addPoints(p);
                //System.err.println(blueService.getBlueprint(autor, name).getPoints().toString());
                return new ResponseEntity<>(HttpStatus.ACCEPTED);}
        } catch (BlueprintNotFoundException|BlueprintFiltroException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error"+ex,HttpStatus.FORBIDDEN);

        } 
    }
    
    @RequestMapping(path="/deleteBlueprint/{autor}/{name}",method = RequestMethod.DELETE)
    public ResponseEntity<?> manejadorDeleteBlueprint(@PathVariable String autor, @PathVariable String name){
        try {
            if(blueService.getBlueprint(autor, name)==null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }else{
                blueService.deleteBlueprint(blueService.getBlueprint(autor, name));
                return new ResponseEntity<>(HttpStatus.ACCEPTED);}
        } catch (BlueprintNotFoundException|BlueprintFiltroException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error"+ex,HttpStatus.FORBIDDEN);

        } 
    }
    
    

}

