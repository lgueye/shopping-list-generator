var cat = null;
var findReceipesByCategory = function(category){
    cat = category;
    $.ajax(
        {type : "GET",
            url : "api/recipes/category/" + category,
            success : successCallback,
            contentType : 'application/json',
            dataType : 'json'}
    );


};

    function successCallback(data) {
        $("#search-input").attr("placeholder", cat);
        $("#recipes-list-ul li").each(function(item){
            if(!$(this).hasClass("todo-done")){
                $(this).remove();
            }
        });

        for(i=0 ; i < data.length ; i++){
            var skip = false;
            $("#recipes-list-ul li").each(function(item){
                if($(this).hasClass("todo-done") && $(this).attr("id") == data[i].id){
                    skip = true;
                    return;
                }
            });

            if(!skip){
                // steps
                var steps = "<div id='steps-" + data[i].id+ "' class='step-shopping'>";
                for (j=0 ; j<data[i].steps.length ; j++){
                    steps += data[i].steps[j].description + " <br/>";
                }
                steps += "</div>";

                $("#recipes-list-ul").append(
                    $("<li id='" + data[i].id + "'>").append("<div class='todo-icon'><img src='" + data[i].image + "' style='width:100%;height:100%;'/></div>").append(
                        "<div class='todo-content'><h4 class='todo-name'>" + data[i].title + "</h4></div><span style='color:white;'>" + data[i].cookingTime + "min | Season: " + data[i].season+ " | Level: " + data[i].level + " | Price: " + data[i].priceRange + "</span>" + steps + "<div class='counter'><span class='plus'>+</span><span class='minus'>-</span><span class='fui-user'></span><span class='fui-user'></span></div>")
                );

                $("#" +  data[i].id).on('click', function(){
                    if($("#steps-" +  $(this).attr("id")).hasClass("step-shopping")){
                        $("#steps-" +  $(this).attr("id")).removeClass("step-shopping")
                        $("#steps-" +  $(this).attr("id")).addClass("step-shopping-selected")
                    }else{
                        $("#steps-" +  $(this).attr("id")).addClass("step-shopping")
                        $("#steps-" +  $(this).attr("id")).removeClass("step-shopping-selected")
                    }
                });

                $("#" +  data[i].id).find(".plus").on('click', function(e){

                    $(this).parent().append("<span class='fui-user'></span>");
                    e.preventDefault();
                    e.stopPropagation();
                });

                $("#" +  data[i].id).find(".minus").on('click', function(e){
                    $(this).parent().find(".fui-user:last-child").remove();
                    e.preventDefault();
                    e.stopPropagation();
                });



            }

        }

        $("#container").css("height", null);
    }

var findByKeyword = function(keyword){

    $.ajax(
        {type : "GET",
            url : "api/recipes/search?q=" + keyword,
            success : successCallback,
            contentType : 'application/json',
            dataType : 'json'}
    );


};





var getShoppingCart = function(){
    var selectedMenus = [];
    $("#recipes-list-ul li").each(function(item){
        if($(this).hasClass("todo-done")){
            selectedMenus.push({
                "mealsNumber":$(this).find(".fui-user").length,
                "recipeId": $(this).attr("id")
            });
        }
    });

    var menu = { "id": 555,
        "name":"ouptutmenu",
        "schedules": selectedMenus
    };

    $('body').load("shoppingcart.html", function(){
        shoppingcart(menu);
    });

};

