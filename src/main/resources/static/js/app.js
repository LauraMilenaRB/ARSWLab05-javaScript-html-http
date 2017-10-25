/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global apiclient,apimock,  */
var api=  apiclient;

app = (function () {
    var _author;
    var _name="";
    var _canvas;
    var _newPoints=[];
    var save=false;
    
    function getSuma(suma, num) {
        return suma + num.size;
    }
    function newObject(blueauthor) {
        return {"name": blueauthor.name, "size": blueauthor.points.length};
    }
    function addTable(val) {
        return "<tr><td>" + val.name + "</td>\n\
               <td >" + val.points.length + "</td>\n\
               <td><button type='button' onclick=\"app.getDraw(\'"+val.author+"\',\'"+val.name+"\')\">Open</button> </td></tr>";
    }
    function draw(points){
        var c = document.getElementById("myCanvas");
        var ctx=c.getContext("2d");
        var posx=points[0].x,posy=points[0].y;
        for (i=1;i<points.length;i++){
            ctx.moveTo(posx,posy);
            posx=points[i].x,posy=points[i].y;
            ctx.lineTo(posx,posy);
            ctx.stroke();
        }
    }
    function callbackDraw(blues) {
        var bluepoints = blues.points;
        if (bluepoints.length !== 0) {
            clearCanvas();
            draw(bluepoints);
            _newPoints.push(bluepoints[bluepoints.length - 1]);
        }
    };
    var callbackUpdateTable = function (blue) {
        var blue2 = blue.map(newObject);
        var markup = blue.map(addTable);
        $("table tbody tr").remove();
        $("table tbody").append(markup);
        $("#PuntosT").text("Total User points :" +blue2.reduce(getSuma, 0));
    };
    
    function clearCanvas(){
        _canvas.width=_canvas.width;_canvas.height=_canvas.height;
    }
    return {
        setAuthor: function (newauthor) {
            _author = newauthor;
        },
        getUpdateTable: function (author) {
            if(author!==""){
                clearCanvas();_newPoints=[];
                app.setAuthor(author);
                api.getBlueprintsByAuthor(author,callbackUpdateTable);
            }
            else{
                alert("Digite algun author");
            }
        },
        getDraw: function(autor,nameprints){
            _name=nameprints;_newPoints=[];_author=autor;save=false;
            api.getBlueprintsByNameAndAuthor(autor,nameprints,callbackDraw);
            $("#divcanvas").text("Current Blueprint: " + nameprints);
            
        },
        init: function () {
            _canvas = document.getElementById("myCanvas");
            console.info('initialized');
            $("#divcanvas input ").remove();
            $("#divcanvas").text("");_newPoints=[];
            save=false;
            if (window.PointerEvent) {
                _canvas.addEventListener("pointerdown", function (event) {
                    var x=Math.round($("#myCanvas").offset().left);
                    var y=$("#myCanvas").offset().top;
                    console.info('pointerdown' + event.pageX + ' , ' + event.pageY);
                    console.info('offset ' + x + ' , ' + y);
                    console.info('resta ' + event.pageX-x + ' , ' + event.pageY-y);
                    _newPoints.push({"x":event.pageX-x,"y":event.pageY-y});
                    console.info(_newPoints[0].y);
                    draw(_newPoints);
                });
            }else {
                _canvas.addEventListener("mousedown", function (event) {
                    alert('mousedown at ' + event.clientX + ',' + event.clientY);
                    var i=$("#myCanvas").offset();
                    _newPoints.push({"x":event.pageX-i.left,"y":event.pageY-i.top});
                    draw(_newPoints);
                });
            }
        } ,
        saveUpdate :function (){
            if(save){
                app.setAuthor(document.getElementById('author').value);
                var val=document.getElementById('newname').value;
                if(_newPoints.length!==0  && val!=="" && _author!==""){
                    var object={author:_author, "points":_newPoints, "name": val};
                    api.saveNewBlueprint(object)
                            .then(function (){api.getBlueprintsByAuthor(_author,callbackUpdateTable);});
                }
                else{
                    alert("Aun no ha registrado ningun punto o no ha elegido ningun autor.");}
            }else if(!save && _name!=="" && _author!==""){
                if(_newPoints.length!==0){
                    api.updateBlueprintByName(_author,_name,_newPoints)
                            .then(function (){api.getBlueprintsByAuthor(_author,callbackUpdateTable);});
                }
                else{
                    alert("Aun no ha registrado ningun punto o no ha elegido ninguno para actualizar.");}
            }else{
                alert("Aun no ha digitado el nombre del nuevo plano o no a seleccionado ninguno.");
            }
            $("#divcanvas input ").remove();
            $("#divcanvas").text("");
            save=false;clearCanvas();
            _newPoints=[];
        },
        newBlueprint:function (){
            _newPoints=[];
            save=true;
            clearCanvas();
            $("#divcanvas").text("");
            $("#divcanvas").append("New Blueprint name: "+"<input id='newname' type='text' value=\"\"''>");
            
        },
        deleteBlueprint: function (){
            api.deleteBlueprint(_author,_name)
                    .then(function (){api.getBlueprintsByAuthor(_author,callbackUpdateTable);});
            clearCanvas();
        }
    };
})();
$(document).ready(function(){
    $("#getBlueprintauthor").click(function (){
        app.getUpdateTable(document.getElementById('author').value);
    });
    $("#newBlueprint").click(function (){
        app.newBlueprint();
    });
    $("#saveUpdate").click(function (){
        app.saveUpdate();
    });
    $("#deleteBlueprint").click(function (){
        app.deleteBlueprint();
    });
});