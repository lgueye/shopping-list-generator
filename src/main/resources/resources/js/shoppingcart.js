var shoppingcart = function(menu){
	
	$.ajax(
			{type : "POST",
			url : "api/shoppinglist", 
			data : JSON.stringify(menu),
			success : successCallback,
			contentType : 'application/json',
			dataType : 'json'} 
	);
	
	
	function successCallback(data) {
		for(var i=0 ; i<data.ingredients.length ; i++){
			$("#shopping-cart-ul").append(
					$("<li>").append("<div class='shopping-icon'><img src='images/demo/html-icon.png'/></div><div class='shopping-quantity'>" + data.ingredients[i].quantity + " "+ data.ingredients[i].quantityUnit + "</div>").append(
							"<div class='shopping-content'><h4 class='shopping-name'>" + data.ingredients[i].name + "</h4></div>")
					);
		}
	}
	
	
}

