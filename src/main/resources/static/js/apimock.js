/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//@author hcadavid

apimock=(function(){
        
    var mockdata = [];
    
    mockdata["johnconnor"] = [{author: "johnconnor", "points": [{"x": 10, "y": 10}, {"x": 10, "y": 50},{"x": 50, "y": 50}], "name": "house"},
        {author: "johnconnor", "points": [{"x": 340, "y": 240}, {"x": 15, "y": 215}], "name": "gear"}];
    mockdata["maryweyland"] = [{author: "maryweyland", "points": [{"x": 140, "y": 140}, {"x": 115, "y": 115}], "name": "house2"},
        {author: "maryweyland", "points": [{"x": 140, "y": 140}, {"x": 115, "y": 115}], "name": "gear2"}];
    mockdata["john"] = [{author: "john", "name": "thepaint", "points": [{"x": 140, "y": 141}, {"x": 90, "y": 90}, {"x": 140, "y": 140}]}];
    mockdata["juan"] = [{author: "juan", "name": "thepaintjuan", "points": [{"x": 140, "y": 141}, {"x": 90, "y": 90}, {"x": 140, "y": 140}]},
        {author: "juan", "name": "thepaintjuan1", "points": [{"x": 140, "y": 141}, {"x": 90, "y": 90}, {"x": 140, "y": 140}]},
        {author: "juan", "name": "thepaintjuan2", "points": [{"x": 140, "y": 141}, {"x": 140, "y": 140}]}];
    
    
    return {
        getBlueprintsByAuthor: function (authname, callback) {
            callback(mockdata[authname]);
        },

        getBlueprintsByNameAndAuthor: function (authname, bpname, callback) {
            callback(mockdata[authname].find(function (e) {
                    return e.name === bpname;
                })
            );
        }

    };

})();

/*
 Example of use:
 var fun=function(list){
 console.info(list);
 }
 apimock.getBlueprintsByAuthor("johnconnor",fun);
 apimock.getBlueprintsByNameAndAuthor("johnconnor","house",fun);*/

