
apiclient=(function(){
    
    return {
        getBlueprintsByAuthor: function (authname, callback) {
            var getpromise = $.get("blueprints/"+authname+"/",callback);
            getpromise.then(
                    function () {
                        console.info("OK getBlueprintsByAuthor");
                        
                    },
                    function () {
                        alert("Autor no econtrado");
                    }
            );
            return getpromise;
        },

        getBlueprintsByNameAndAuthor: function (authname, bpname, callback) {
            var getpromise = $.get("blueprints/"+authname+"/"+bpname+"/",callback);
            getpromise.then(
                    function () {
                        console.info("OK getBlueprintsByNameAndAuthor");
                    },
                    function () {
                        alert("Plano no econtrado");
                    }
            );
            return getpromise;
        },
        updateBlueprintByName:function (authname,bpname,points){
            var putpromise =$.ajax({
                url: "blueprints/addpoints/"+authname+"/"+bpname+"/",
                type: 'PUT',
                data: JSON.stringify(points),
                contentType: "application/json"
            });
            putpromise.then(
                    function () {
                        console.info("OK updateBlueprintByName");
                    },
                    function () {
                        alert("El plano"+bpname+"no pudo ser actualizado");
                    }
            );
            return putpromise;
        },
        saveNewBlueprint:function (data){
            var postpromise =$.ajax({
                url: "blueprints/planos",
                type: 'POST',
                data: JSON.stringify(data),
                contentType: "application/json"
            });
            postpromise.then(
                    function () {
                        console.info("OK saveNewBlueprint");
                    },
                    function () {
                        alert("El nuevo Blueprint no pudo ser registrado");
                    }
            );
            return postpromise;
        },
        deleteBlueprint: function (authname, name) {
            var deletepromise = $.ajax({
                url: "blueprints/deleteBlueprint/"+authname+"/"+name,
                type: 'DELETE',
                contentType: "application/json"
            });
            deletepromise.then(
                    function () {
                        console.info("OK deleteBlueprint");
                    },
                    function () {
                        alert("No se pudo elminar el plano "+name);
                    }
            );
            return deletepromise;
        }

    };
})();
