package com.swissquote.battledev2014.shoppinglistgenerator.api;

import com.google.common.collect.Maps;
import com.swissquote.battledev2014.shoppinglistgenerator.domain.Menu;
import com.swissquote.battledev2014.shoppinglistgenerator.domain.Recipe;
import com.swissquote.battledev2014.shoppinglistgenerator.domain.RecipeCategory;
import com.swissquote.battledev2014.shoppinglistgenerator.domain.ShoppingList;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class ShoppingListGeneratorApi {

	private static final String RECIPES_RESOURCE_LOCATION = "http://localhost:8080/api/recipes";
	private static final String MENUS_RESOURCE_LOCATION = "http://localhost:8080/api/menus";
	private static final String SHOPPING_LISTS_RESOURCE_LOCATION = "http://localhost:8080/api/shoppinglist";


	public URI createMenu(Menu menu) {
		return new RestTemplate().postForLocation(MENUS_RESOURCE_LOCATION, menu);
	}

	public void deleteResource(URI uri) {
		new RestTemplate().delete(uri);
	}

	public void updateMenu(URI uri, Menu persisted) {
		new RestTemplate().put(uri, persisted);
	}

	public void updateRecipe(URI uri, Recipe persisted) {
		new RestTemplate().put(uri, persisted);
	}

	public void deleteAllRecipes() {
		new RestTemplate().delete(RECIPES_RESOURCE_LOCATION);
	}

	public ShoppingList createShoppingList(Menu menu) {
		return new RestTemplate().postForObject(SHOPPING_LISTS_RESOURCE_LOCATION, menu, ShoppingList.class);
	}

	public List<Recipe> searchRecipes(String keyword) {
		Map<String, String> params = Maps.newHashMap();
		params.put("q", keyword);
		ResponseEntity<List<Recipe>> response = new RestTemplate().exchange(RECIPES_RESOURCE_LOCATION + "/search?q={q}", HttpMethod.GET, null, new ParameterizedTypeReference<List<Recipe>>() {
		}, params);
		return response.getBody();
	}

	public URI createRecipe(Recipe recipe) throws IOException {
		return new RestTemplate().postForLocation(RECIPES_RESOURCE_LOCATION, recipe);
	}
	public Menu loadMenu(URI uri) {
		Menu persisted = new RestTemplate().getForObject(uri, Menu.class);
		assertNotNull(persisted);
		return persisted;
	}

	public Recipe loadRecipe(URI uri) {
		Recipe persisted = new RestTemplate().getForObject(uri, Recipe.class);
		assertNotNull(persisted);
		return persisted;
	}

	public Recipe loadRecipe(String recipeId) {
		return loadRecipe(URI.create(RECIPES_RESOURCE_LOCATION + "/" + recipeId));
	}

	public List<Recipe> findAllRecipes() {
		Map<String, ?> uriVariables = new HashMap<>();
		HttpEntity entity = new HttpEntity(null);
		ResponseEntity<List<Recipe>> response = new RestTemplate()
				.exchange(RECIPES_RESOURCE_LOCATION, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Recipe>>() {
				}, uriVariables);
		return response.getBody();
	}

	public List<Recipe> findRecipesByCategory(RecipeCategory category) {
		Map<String, ?> uriVariables = new HashMap<>();
		HttpEntity entity = new HttpEntity(null);
		ResponseEntity<List<Recipe>> response = new RestTemplate()
				.exchange(RECIPES_RESOURCE_LOCATION + "/category/" + category.toString(), HttpMethod.GET, entity, new ParameterizedTypeReference<List<Recipe>>() {
				}, uriVariables);
		return response.getBody();
	}
}
