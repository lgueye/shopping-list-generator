package com.swissquote.battledev2014.shoppinglistgenerator.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import com.swissquote.battledev2014.shoppinglistgenerator.domain.RecipeCategory;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.swissquote.battledev2014.shoppinglistgenerator.ShoppinglistGeneratorApplication;
import com.swissquote.battledev2014.shoppinglistgenerator.domain.Recipe;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ShoppinglistGeneratorApplication.class)
@WebAppConfiguration
@IntegrationTest
public class RecipeTest {

	private ShoppingListGeneratorApi api = new ShoppingListGeneratorApi();

	@Test
	public void crudRecipeShouldSucceed() throws IOException {
		Recipe recipe = Fixtures.recipeFromFile("recipes/recipe-burger-italien-gorgonzola-figues.json");
		// Create
		URI uri = api.createRecipe(recipe);
		assertNotNull(uri);

		// Read
		Recipe persisted = api.loadRecipe(uri);
		assertNotNull(persisted);
		String id = persisted.getId();
		assertNotNull(id);
		persisted.setId(null);
		assertEquals(persisted, recipe);

		// Update
		persisted.setId(id);
		String expectedName = "test test test";
		persisted.setTitle(expectedName);
		api.updateRecipe(uri, persisted);
		Recipe updated = api.loadRecipe(uri);
		assertEquals(persisted, updated);

		// Delete
		api.deleteResource(uri);
		try {
			new RestTemplate().getForEntity(uri, Recipe.class);
		} catch (HttpClientErrorException e) {
			assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
		} catch (Exception e) {
			fail("expected {" + HttpClientErrorException.class + "}, got {" + e + "}");
		}
	}

	@Test
	public void searchRecipesShouldMatchIngredientsName() throws IOException {

		// Given
		api.deleteAllRecipes();
		Fixtures.createRecipe("recipes/recipe-eglefin-et-echalotes.json");
		Fixtures.createRecipe("recipes/recipe-bavarois-a-la-framboise.json");
		Fixtures.createRecipe("recipes/test-found-italy-in-ingredient.json");

		// When
		List<Recipe> results = api.searchRecipes("italy");

		// Then
		assertEquals(1, results.size());
	}

	@Test
	public void searchRecipesShouldMatchTitle() throws IOException {

		// Given
		api.deleteAllRecipes();
		Fixtures.createRecipe("recipes/recipe-eglefin-et-echalotes.json");
		Fixtures.createRecipe("recipes/recipe-bavarois-a-la-framboise.json");
		Fixtures.createRecipe("recipes/test-found-france-in-title.json");

		// When
		List<Recipe> results = api.searchRecipes("franc");

		// Then
		assertEquals(1, results.size());
	}

	@Test
	public void findAllRecipesShouldSucceed() throws IOException {

		// Given
		api.deleteAllRecipes();
		Fixtures.createRecipe("recipes/recipe-bavarois-a-la-framboise.json");
		Fixtures.createRecipe("recipes/recipe-tomates-farcies.json");

		// When
		List<Recipe> results = api.findAllRecipes();

		// Then
		assertEquals(2, results.size());
	}

	@Test
	public void findByRecipesByCategoryShouldSucceed() throws IOException {

		// Given
		api.deleteAllRecipes();
		Fixtures.createRecipe("recipes/test-category-meat-2.json");
		Fixtures.createRecipe("recipes/test-category-meat.json");
		Fixtures.createRecipe("recipes/test-category-salad.json");
		Fixtures.createRecipe("recipes/test-category-fish.json");

		// When
		List<Recipe> results;
		results = api.findRecipesByCategory(RecipeCategory.meat);

		// Then
		assertEquals(2, results.size());

		// When
		results = api.findRecipesByCategory(RecipeCategory.salad);

		// Then
		assertEquals(1, results.size());

		// When
		results = api.findRecipesByCategory(RecipeCategory.fish);

		// Then
		assertEquals(1, results.size());
	}

}
