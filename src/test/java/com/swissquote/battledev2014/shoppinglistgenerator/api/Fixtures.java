package com.swissquote.battledev2014.shoppinglistgenerator.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.swissquote.battledev2014.shoppinglistgenerator.domain.Menu;
import com.swissquote.battledev2014.shoppinglistgenerator.domain.Recipe;
import com.swissquote.battledev2014.shoppinglistgenerator.domain.ScheduledRecipe;
import com.swissquote.battledev2014.shoppinglistgenerator.domain.ShoppingList;
import org.joda.time.DateTime;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class Fixtures {

	private static ObjectMapper jsonMapper = new ObjectMapper();

	static {
		jsonMapper.setDateFormat(new ISO8601DateFormat());
	}

	public static URI createRecipe(String filePath) throws IOException {
		Recipe recipe = recipeFromFile(filePath);
		return new ShoppingListGeneratorApi().createRecipe(recipe);
	}
	public static Recipe recipeFromFile(String filePath) throws IOException {
		File recipeAsJson = new ClassPathResource(filePath).getFile();
		return jsonMapper.readValue(recipeAsJson, Recipe.class);
	}

	public static Menu buildMenu() throws IOException {
		String recipe0 = Fixtures.createRecipe("recipes/recipe-bavarois-a-la-framboise.json").toString();
		String recipe1 = Fixtures.createRecipe("recipes/recipe-brocolis-aux-lardons.json").toString();
		String recipe2 = Fixtures.createRecipe("recipes/recipe-burger-italien-gorgonzola-figues.json").toString();
		String recipe3 = Fixtures.createRecipe("recipes/recipe-cake-aux-courgettes.json").toString();
		String recipe4 = Fixtures.createRecipe("recipes/recipe-eglefin-et-echalotes.json").toString();
		String recipe5 = Fixtures.createRecipe("recipes/recipe-nems-de-poires-au-chocolat.json").toString();
		String recipe6 = Fixtures.createRecipe("recipes/recipe-noix-de-saint-jacques-tradition.json").toString();
		String recipe7 = Fixtures.createRecipe("recipes/recipe-omelette-fondante-aux-tomates-asperges-et-comte.json").toString();
		String recipe8 = Fixtures.createRecipe("recipes/recipe-parmentier-de-courgettes.json").toString();
		String recipe9 = Fixtures.createRecipe("recipes/recipe-quiche-aux-brocolis.json").toString();
		String recipe10 = Fixtures.createRecipe("recipes/recipe-tiramisu-fraises-et-fraises-tagada.json").toString();
		String recipe11 = Fixtures.createRecipe("recipes/recipe-tomates-farcies.json").toString();
		List<ScheduledRecipe> scheduledRecipes = Lists.newArrayList();
		scheduledRecipes.add(Fixtures.buildScheduledRecipe(2, recipe0));
		scheduledRecipes.add(Fixtures.buildScheduledRecipe(4, recipe1));
		scheduledRecipes.add(Fixtures.buildScheduledRecipe(6, recipe2));
		scheduledRecipes.add(Fixtures.buildScheduledRecipe(8, recipe3));
		scheduledRecipes.add(Fixtures.buildScheduledRecipe(10, recipe4));
		scheduledRecipes.add(Fixtures.buildScheduledRecipe(12, recipe5));
		scheduledRecipes.add(Fixtures.buildScheduledRecipe(2, recipe6));
		scheduledRecipes.add(Fixtures.buildScheduledRecipe(4, recipe7));
		scheduledRecipes.add(Fixtures.buildScheduledRecipe(6, recipe8));
		scheduledRecipes.add(Fixtures.buildScheduledRecipe(8, recipe9));
		scheduledRecipes.add(Fixtures.buildScheduledRecipe(10, recipe10));
		scheduledRecipes.add(Fixtures.buildScheduledRecipe(12, recipe11));
		return new Menu(null, "louis-week26-menu", scheduledRecipes);
	}

	public static ScheduledRecipe buildScheduledRecipe(int mealsNumber, String recipeId) {
		return new ScheduledRecipe(mealsNumber, recipeId.substring(recipeId.lastIndexOf('/') + 1, recipeId.length()));
	}
}
