package com.swissquote.battledev2014.shoppinglistgenerator.service;

import com.google.common.collect.Lists;
import com.swissquote.battledev2014.shoppinglistgenerator.domain.UnitEnum;
import com.swissquote.battledev2014.shoppinglistgenerator.domain.Ingredient;
import com.swissquote.battledev2014.shoppinglistgenerator.domain.Menu;
import com.swissquote.battledev2014.shoppinglistgenerator.domain.Recipe;
import com.swissquote.battledev2014.shoppinglistgenerator.domain.ScheduledRecipe;
import com.swissquote.battledev2014.shoppinglistgenerator.domain.ShoppingList;
import com.swissquote.battledev2014.shoppinglistgenerator.repository.RecipeElasticsearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShoppingListService {

	private Logger LOGGER = LoggerFactory.getLogger(ShoppingList.class);

	@Autowired
	private RecipeElasticsearchRepository recipeElasticsearchRepository;

	public ShoppingList generate(Menu menu) {
		LOGGER.debug("Generating list for menu {}", menu);
        List<Ingredient> ingredients = Lists.newArrayList();
        for (ScheduledRecipe scheduledRecipe : menu.getSchedules()) {
			String recipeId = scheduledRecipe.getRecipeId();
			Recipe recipe = recipeElasticsearchRepository.findOne(recipeId);
			ingredients.addAll(
                    normalizeQtyByPeople(recipe.getIngredients(), recipe.getMealsNumber(), scheduledRecipe.getMealsNumber()));

		}
		//Aggregate
		ingredients = aggregateIngredients(ingredients);
		ShoppingList shoppingList = new ShoppingList(menu.getName(), ingredients);
		LOGGER.debug("Generated list {}", shoppingList);
		return shoppingList;
	}

	private List<Ingredient> aggregateIngredients(List<Ingredient> ingredients) {
		Map<String, Ingredient> foundIngredients = new HashMap<>();
		for (Ingredient i : ingredients) {
			if (foundIngredients.containsKey(i.getName())) {
				Ingredient existingIngredient = foundIngredients.get(i.getName());
				foundIngredients.put(existingIngredient.getName(), incrementIngredient(existingIngredient, i));
			} else {
				i = normalize(i);
				foundIngredients.put(i.getName(), i);
			}
		}
		return new ArrayList<>(foundIngredients.values());
	}

	private Ingredient incrementIngredient(Ingredient previous, Ingredient toAdd) {

		//Get the unit for previous one ()
		previous.setQuantity(previous.getQuantity() + normalize(toAdd).getQuantity());
		return previous;
	}

	private Ingredient normalize(Ingredient toNormalize) {
		if (!normalizationNeeded(toNormalize.getQuantityUnit())) {
			return toNormalize;
		}
		toNormalize.setQuantity(toNormalize.getQuantity() * UnitEnum.valueOf(toNormalize.getQuantityUnit()).getRatioToBase());
		toNormalize.setQuantityUnit(convertToBaseUnit(toNormalize.getQuantityUnit()));
		return toNormalize;
	}

	private boolean normalizationNeeded(String unit) {
		boolean isNeeded = false;
		if (UnitEnum.g.name().equalsIgnoreCase(unit) || UnitEnum.cl.name().equalsIgnoreCase(unit)) {
			return isNeeded;
		}
		for (UnitEnum unitEnum : UnitEnum.values()) {
			if (unitEnum.name().equalsIgnoreCase(unit)) {
				isNeeded = true;
				break;
			}
		}
		return isNeeded;
	}

	private String convertToBaseUnit(String unit) {
		UnitEnum e = UnitEnum.valueOf(unit);
		switch (e) {
			case l:
			case ml:
			case dl:
				unit = UnitEnum.cl.name();
				break;
			case kg:
				unit = UnitEnum.g.name();
				break;
			default:
				break;
		}
		return unit;
	}

    /**
     * We request a quantity different from the original recipe
     *
     *
     * @param ingredients
     * @param recipeOriginalNumber
     * @param recipeRequestedNumber
     * @return normalizedLIstOfIngredients
     */
    private List<Ingredient> normalizeQtyByPeople(List<Ingredient> ingredients, int recipeOriginalNumber, int recipeRequestedNumber) {
        List<Ingredient> normalizedByPeopleIngList = new ArrayList<Ingredient>();
        for(Ingredient in : ingredients){
            if(recipeOriginalNumber!=recipeRequestedNumber && in.getQuantity()!=null){
				double quantity = in.getQuantity() * ((double) recipeRequestedNumber / (double) recipeOriginalNumber);
				in.setQuantity((double) Math.max(1, Math.round(quantity)));
            }
            normalizedByPeopleIngList.add(in);
        }
        return normalizedByPeopleIngList;
    }


}
