package com.swissquote.battledev2014.shoppinglistgenerator.api;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.swissquote.battledev2014.shoppinglistgenerator.domain.Menu;
import com.swissquote.battledev2014.shoppinglistgenerator.domain.ShoppingList;
import com.swissquote.battledev2014.shoppinglistgenerator.service.ShoppingListService;

@RestController
@RequestMapping("/api/shoppinglist")
public class ShoppingListResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingListResource.class);

	@Autowired
	private ShoppingListService service;

	@RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE }, produces = {MediaType.APPLICATION_JSON_VALUE })
	public ShoppingList generate(@RequestBody @Valid Menu menu) {
		LOGGER.debug("Attempt to create shopping list from menu: {}", menu);
		// Save recipe and get new id
		ShoppingList shoppingList = service.generate(menu);
		LOGGER.debug("Generated shopping list : {}", shoppingList);
		return shoppingList;
	}

}
