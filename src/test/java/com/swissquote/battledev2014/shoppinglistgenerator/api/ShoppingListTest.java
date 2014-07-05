package com.swissquote.battledev2014.shoppinglistgenerator.api;

import com.google.common.collect.Lists;
import com.swissquote.battledev2014.shoppinglistgenerator.ShoppinglistGeneratorApplication;
import com.swissquote.battledev2014.shoppinglistgenerator.domain.Ingredient;
import com.swissquote.battledev2014.shoppinglistgenerator.domain.Menu;
import com.swissquote.battledev2014.shoppinglistgenerator.domain.Recipe;
import com.swissquote.battledev2014.shoppinglistgenerator.domain.ScheduledRecipe;
import com.swissquote.battledev2014.shoppinglistgenerator.domain.ShoppingList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.net.URI;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ShoppinglistGeneratorApplication.class)
@WebAppConfiguration
@IntegrationTest
public class ShoppingListTest {

	private ShoppingListGeneratorApi api = new ShoppingListGeneratorApi();

	@Test
	public void generateShoppingListShouldSucceed() throws IOException {
		String recipe0 = Fixtures.createRecipe("recipes/recipe-bavarois-a-la-framboise.json").toString();
		String recipe1 = Fixtures.createRecipe("recipes/recipe-brocolis-aux-lardons.json").toString();
		
        //Oeufs in common
        
        List<ScheduledRecipe> scheduledRecipes = Lists.newArrayList();
		scheduledRecipes.add(Fixtures.buildScheduledRecipe(6, recipe0));
		scheduledRecipes.add(Fixtures.buildScheduledRecipe(4, recipe1));
		Menu menu = new Menu(null, "louis-week26-menu", scheduledRecipes);
		// Create
		URI uri = api.createMenu(menu);
		assertNotNull(uri);

		// Read
		Menu persisted = api.loadMenu(uri);
		List<Ingredient> expectedIngredients = Lists.newArrayList();
		for (ScheduledRecipe scheduledRecipe : persisted.getSchedules()) {
			Recipe recipe = api.loadRecipe(scheduledRecipe.getRecipeId());
			expectedIngredients.addAll(recipe.getIngredients());
            
		}
		ShoppingList shoppingList = api.createShoppingList(persisted);
        //eggs have been merged
		assertEquals(expectedIngredients.size() -1, shoppingList.getIngredients().size());
		for (Ingredient in : shoppingList.getIngredients()) {
			if ("oeufs".equals(in.getName())) {
				assertEquals(6.0, in.getQuantity(), 0);
				break;
			}
		}
	}

    @Test
    public void generateShoppingListStrangeUnit() throws IOException {
        String recipe0 = Fixtures.createRecipe("recipes/recipe-eglefin-et-echalotes.json").toString();
        String recipe1 = Fixtures.createRecipe("/recipes/recipe-parmentier-de-courgettes.json").toString();

        List<ScheduledRecipe> scheduledRecipes = Lists.newArrayList();
        scheduledRecipes.add(Fixtures.buildScheduledRecipe(6, recipe0));
        scheduledRecipes.add(Fixtures.buildScheduledRecipe(4, recipe1));
        Menu menu = new Menu(null, "louis-week27-menu", scheduledRecipes);

        // Create
        URI uri = api.createMenu(menu);
        assertNotNull(uri);

        // Read
        Menu persisted = api.loadMenu(uri);
        List<Ingredient> expectedIngredients = Lists.newArrayList();
        for (ScheduledRecipe scheduledRecipe : persisted.getSchedules()) {
            Recipe recipe = api.loadRecipe(scheduledRecipe.getRecipeId());
            expectedIngredients.addAll(recipe.getIngredients());

        }
        ShoppingList shoppingList = api.createShoppingList(persisted);
        //persil and courgettes have been merged, with strange measure unit
        assertEquals(expectedIngredients.size() -2, shoppingList.getIngredients().size());

		for (Ingredient in : shoppingList.getIngredients()) {
			if ("persil".equals(in.getName())) {
				assertEquals(7.0, in.getQuantity(), 0);
				break;
			}
		}
    }

    @Test
    public void generateShoppingListStrangeUnitNormalizePeople() throws IOException {
        String recipe0 = Fixtures.createRecipe("recipes/recipe-eglefin-et-echalotes.json").toString();
        String recipe1 = Fixtures.createRecipe("/recipes/recipe-parmentier-de-courgettes.json").toString();

        List<ScheduledRecipe> scheduledRecipes = Lists.newArrayList();
        scheduledRecipes.add(Fixtures.buildScheduledRecipe(5, recipe0));
        scheduledRecipes.add(Fixtures.buildScheduledRecipe(4, recipe1));
        Menu menu = new Menu(null, "louis-week27-menu", scheduledRecipes);

        // Create
        URI uri = api.createMenu(menu);
        assertNotNull(uri);

        // Read
        Menu persisted = api.loadMenu(uri);
        List<Ingredient> expectedIngredients = Lists.newArrayList();
        for (ScheduledRecipe scheduledRecipe : persisted.getSchedules()) {
            Recipe recipe = api.loadRecipe(scheduledRecipe.getRecipeId());
            expectedIngredients.addAll(recipe.getIngredients());

        }
        ShoppingList shoppingList = api.createShoppingList(persisted);
        //persil and courgettes have been merged, with strange measure unit
        assertEquals(expectedIngredients.size() -2, shoppingList.getIngredients().size());

		for (Ingredient in : shoppingList.getIngredients()) {
			if ("persil".equals(in.getName())) {
				assertEquals(  Math.round((4.0 * (5.0 / 6.0)) + (3.0 * (4.0 / 4.0))), in.getQuantity(), 0);
				break;
			}

		}
    }

    @Test
    public void generateShoppingListUnitConversionNormalizePeople() throws IOException {
        String recipe0 = Fixtures.createRecipe("recipes/recipe-eglefin-et-echalotes.json").toString();
        String recipe1 = Fixtures.createRecipe("/recipes/recipe-parmentier-de-courgettes.json").toString();

        List<ScheduledRecipe> scheduledRecipes = Lists.newArrayList();
        scheduledRecipes.add(Fixtures.buildScheduledRecipe(5, recipe0));
        scheduledRecipes.add(Fixtures.buildScheduledRecipe(4, recipe1));
        Menu menu = new Menu(null, "louis-week27-menu", scheduledRecipes);

        // Create
        URI uri = api.createMenu(menu);
        assertNotNull(uri);

        // Read
        Menu persisted = api.loadMenu(uri);
        List<Ingredient> expectedIngredients = Lists.newArrayList();
        for (ScheduledRecipe scheduledRecipe : persisted.getSchedules()) {
            Recipe recipe = api.loadRecipe(scheduledRecipe.getRecipeId());
            expectedIngredients.addAll(recipe.getIngredients());

        }
        ShoppingList shoppingList = api.createShoppingList(persisted);
        //persil and courgettes have been merged, with strange measure unit
        assertEquals(expectedIngredients.size() -2, shoppingList.getIngredients().size());

		for (Ingredient in : shoppingList.getIngredients()) {
			if ("courgettes".equals(in.getName())) {
				long expected = Math.round((10.0 * (5.0 / 6.0)) + (1000.0 * (4.0 / 4.0)));
				assertEquals(expected, in.getQuantity(), 0);
				assertEquals("g", in.getQuantityUnit());
				break;
			}
		}
    }


}
