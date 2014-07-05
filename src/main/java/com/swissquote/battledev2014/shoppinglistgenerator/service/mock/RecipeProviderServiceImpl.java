package com.swissquote.battledev2014.shoppinglistgenerator.service.mock;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swissquote.battledev2014.shoppinglistgenerator.repository.RecipeElasticsearchRepository;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.swissquote.battledev2014.shoppinglistgenerator.domain.CookingLevel;
import com.swissquote.battledev2014.shoppinglistgenerator.domain.CookingStep;
import com.swissquote.battledev2014.shoppinglistgenerator.domain.Ingredient;
import com.swissquote.battledev2014.shoppinglistgenerator.domain.MealType;
import com.swissquote.battledev2014.shoppinglistgenerator.domain.PreferedSeason;
import com.swissquote.battledev2014.shoppinglistgenerator.domain.PriceRange;
import com.swissquote.battledev2014.shoppinglistgenerator.domain.Recipe;
import com.swissquote.battledev2014.shoppinglistgenerator.domain.RecipeCategory;
import com.swissquote.battledev2014.shoppinglistgenerator.service.RecipeProviderService;

@Service
public class RecipeProviderServiceImpl implements RecipeProviderService {

	List<String> dessertTechniques = Lists.newArrayList("Soufflé", "Tartelette", "Coulis", "Financier", "Quatre-quart", "Marbré", "Opéra",
			"Mousse", "Verrine");
	List<String> dessertIngredients = Lists.newArrayList("kiwi", "chocolat", "fruit de la passion", "pomme", "ananas", "cassonade",
			"clémentines", "rhubarbe", "nutella", "abricots", "groseilles", "orange", "mûres", "mirabelles");
	String dessertTemplate = "{0} {1}";

	List<String> mainPlateTechniques = Lists.newArrayList("Nems", "Gratin", "Fajitas", "Quiche", "Soufflé", "Tarte", "Salade", "Filet mignon",
			"Ramens", "Papillotes", "Parmentier", "Sauté", "Emincé", "Tourte", "Cake", "Blanquette", "Paupiette", "Colombo", "Filet");
	List<String> mainPlateMeats = Lists.newArrayList("Lieu noir", "sole tropicale", "noix de Saint-Jacques", "rognons de veau", "veau", "boeuf",
			"porc", "jambon", "agneau", "raviolle", "penne", "saucisses", "chèvre", "lapin", "poulet", "canard", "pintade", "chapon",
			"poitrine fumée");
	List<String> mainPlateVegetables = Lists.newArrayList("pruneaux", "miel", "abricots", "pommes de terres", "épinards", "haricots verts",
			"haricots verts", "riz", "raviolle", "courgettes", "choux-fleurs", "brocolis");

	List<String> mainPlateIngredients = new ArrayList() {
		{
			addAll(mainPlateMeats);
			addAll(mainPlateVegetables);
		}
	};
	String mainTemplate = "{0} {1}, {2}";


	List<String> starterTechniques = Lists.newArrayList("Salade", "Terrine", "Verrine", "Mousse");
	List<String> starterIngredients = Lists.newArrayList("avocat", "laitue", "tomates", "thon", "crevettes", "carottes", "champignons", "oeufs",
			"petits pois", "olives", "lardons", "parmesan", "pommes granny", "anchois", "chou-fleur", "celeri", "crabe");
	String starterTemplate = "{0} {1}";

	List<RecipeCategory> starterCategories = Lists.newArrayList(RecipeCategory.salad, RecipeCategory.starter);
	List<RecipeCategory> mealCategories = Lists.newArrayList(RecipeCategory.fish, RecipeCategory.meat);

	private ObjectMapper jsonMapper = new ObjectMapper();

	@Autowired
	private RecipeElasticsearchRepository recipeElasticsearchRepository;

	public List<Recipe> generate() throws IOException {
		int recipeCount = 1000;
		List<Recipe> recipes = new ArrayList<>();
		for (int i = 0; i < recipeCount; i++) {
			recipes.add(buildRecipe());
		}
		return recipes;
	}

	public List<Recipe> demo() throws IOException {
		int recipeCount = 50;
		List<Recipe> recipes = new ArrayList<>();
		for (int i = 0; i < recipeCount; i++) {
			recipes.add(buildRecipe());
		}
		// Demonstrate 100g + 200g 'sucre en poudre'
		recipes.add(recipeFromFile("recipes/recipe-bavarois-a-la-framboise.json"));
		recipes.add(recipeFromFile("recipes/recipe-tiramisu-fraises-et-fraises-tagada.json"));

		// Demonstrate
		// unit addition
		// Demonstrate 1 kilogrammes + 200g 'steack hachés frais'
		// Demonstrate kilogrames => kg, grammes => g
		recipes.add(recipeFromFile("recipes/recipe-tomates-farcies.json"));
		recipes.add(recipeFromFile("recipes/recipe-burger-italien-gorgonzola-figues.json"));

		// Demonstrate feature: non measurable unit transformed into measurable things
		recipes.add(recipeFromFile("recipes/recipe-brocolis-aux-lardons.json"));
		recipes.add(recipeFromFile("recipes/recipe-cake-aux-courgettes.json"));
		recipes.add(recipeFromFile("recipes/recipe-eglefin-et-echalotes.json"));
		recipes.add(recipeFromFile("recipes/recipe-nems-de-poires-au-chocolat.json"));
		recipes.add(recipeFromFile("recipes/recipe-noix-de-saint-jacques-tradition.json"));
		recipes.add(recipeFromFile("recipes/recipe-omelette-fondante-aux-tomates-asperges-et-comte.json"));
		recipes.add(recipeFromFile("recipes/recipe-parmentier-de-courgettes.json"));
		recipes.add(recipeFromFile("recipes/recipe-quiche-aux-brocolis.json"));
		return recipes;
	}

	public Recipe recipeFromFile(String filePath) throws IOException {
		File recipeAsJson = new ClassPathResource(filePath).getFile();
		return jsonMapper.readValue(recipeAsJson, Recipe.class);
	}

	private Recipe buildRecipe() throws IOException {
		Recipe recipe = new Recipe();
		recipe.setLevel(randomCookingLevel());
		recipe.setCookingTime(randomCookingTime());
		recipe.setMealsNumber(randomMealsNumber());
		recipe.setPreparationTime(randomPreparationTime());
		recipe.setPriceRange(randomPriceRange());
		recipe.setSeason(randomSeason());
		MealType type = randomMealType();
		recipe.setType(type);
		recipe.setTitle(randomTitle(type));
		recipe.setIngredients(randomIngredients(type));
		recipe.setSteps(randomSteps());
		recipe.setImage(randomImage(type));
		recipe.setCategory(randomCategory(type));
		return recipe;
	}

	private RecipeCategory randomCategory(MealType type) {
		switch (type) {
			case dessert:return RecipeCategory.dessert;
			case starter:
				return starterCategories.get(RandomUtils.nextInt(starterCategories.size()));
			case main:
				return mealCategories.get(RandomUtils.nextInt(mealCategories.size()));
		}
		return null;
	}

	private String randomImage(MealType type) throws IOException {
		//File recipesDirectory = new File("resources/images/recipes");
		File recipesDirectory = new ClassPathResource("resources/images/recipes/" + type).getFile();
		Collection<File> files = FileUtils.listFiles(recipesDirectory, null, true);
		List<File> list = Lists.newArrayList(files);
		return "images/recipes/" + type+ "/" + list.get(RandomUtils.nextInt(list.size())).getName();
	}

	private Integer randomMealsNumber() {
		List<Integer> mealsNumbers = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		return mealsNumbers.get(RandomUtils.nextInt(mealsNumbers.size()));
	}

	private Integer randomCookingTime() {
		List<Integer> cookingTimes = Lists.newArrayList(10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85);
		return cookingTimes.get(RandomUtils.nextInt(cookingTimes.size()));
	}

	private Integer randomPreparationTime() {
		List<Integer> preparationTimes = Lists.newArrayList(10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85);
		return preparationTimes.get(RandomUtils.nextInt(preparationTimes.size()));
	}

	private CookingLevel randomCookingLevel() {
		List<CookingLevel> cookingLevels = Lists.newArrayList(CookingLevel.values());
		return cookingLevels.get(RandomUtils.nextInt(cookingLevels.size()));
	}

	private PriceRange randomPriceRange() {
		List<PriceRange> priceRangeLevels = Lists.newArrayList(PriceRange.values());
		return priceRangeLevels.get(RandomUtils.nextInt(priceRangeLevels.size()));
	}

	private PreferedSeason randomSeason() {
		List<PreferedSeason> seasons = Lists.newArrayList(PreferedSeason.values());
		return seasons.get(RandomUtils.nextInt(seasons.size()));
	}

	private MealType randomMealType() {
		List<MealType> mealTypes = Lists.newArrayList(MealType.values());
		return mealTypes.get(RandomUtils.nextInt(mealTypes.size()));
	}

	private String randomTitle(MealType mealType) {
		switch (mealType) {
			case starter:
				return randomStarterTitle();
			case main:
				return randomMainTitle();
			case dessert:
				return randomDessertTitle();
		}
		return null;
	}

	private String randomDessertTitle() {
		return MessageFormat.format(dessertTemplate, dessertTechniques.get(RandomUtils.nextInt(dessertTechniques.size())),
				dessertIngredients.get(RandomUtils.nextInt(dessertIngredients.size())));
	}

	private String randomMainTitle() {
		return MessageFormat.format(mainTemplate, mainPlateTechniques.get(RandomUtils.nextInt(mainPlateTechniques.size())),
				mainPlateMeats.get(RandomUtils.nextInt(mainPlateMeats.size())),
				mainPlateVegetables.get(RandomUtils.nextInt(mainPlateVegetables.size())));
	}

	private String randomStarterTitle() {
		return MessageFormat.format(starterTemplate, starterTechniques.get(RandomUtils.nextInt(starterTechniques.size())),
				starterIngredients.get(RandomUtils.nextInt(starterIngredients.size())));
	}

	private List<Ingredient> randomIngredients(MealType type) {
		List<Ingredient> randomIngredients = Lists.newArrayList();
		for (int i = 0; i < Math.max(3, RandomUtils.nextInt(8)); i++) {
			randomIngredients.add(randomIngredient(type));
		}
		return randomIngredients;
	}

	private Ingredient randomIngredient(MealType type) {
		Ingredient ingredient = new Ingredient();
		ingredient.setName(randomIngredientName(type));
		ingredient.setQuantity(Math.max(1, RandomUtils.nextDouble(new Random(100))));
		ingredient.setQuantityUnit(randomIngredientQuantityUnit());
		return ingredient;
	}

	private String randomIngredientQuantityUnit() {
		List<String> quantityUnits =
				Lists.newArrayList("cuillère à soupe", "cuillère à café", "cl", "l", "g", "kg", "grammes", "kilos", "gousse", "botte", "brin",
						"feuilles", "tranches", "sachet", "tranches", "pincée", "pointe", "dose", "quelques", "un peu", "larme", "bâtons");
		return quantityUnits.get(RandomUtils.nextInt(quantityUnits.size()));
	}

	private String randomIngredientName(MealType type) {
		return type == MealType.dessert ? dessertIngredients.get(RandomUtils.nextInt(dessertIngredients.size())) : type == MealType.main
				? mainPlateIngredients.get(RandomUtils.nextInt(mainPlateIngredients.size())) : dessertIngredients.get(RandomUtils
						.nextInt(dessertIngredients.size()));
	}

	private List<CookingStep> randomSteps() {
		List<String> steps =
				Lists.newArrayList("Duis tellus felis, consectetur id scelerisque a, ultrices sed nisl",
						"Vivamus iaculis lacinia eros ac aliquam", "Quisque nec lectus justo", "Aenean non sapien ac lectus sagittis posuere",
						"Sed auctor urna sed nibh feugiat rutrum sit amet suscipit sapien", "Donec imperdiet facilisis sem ut suscipit",
						"Vestibulum quis risus felis", "Duis sodales tortor nec risus lobortis, eget tincidunt arcu convallis",
						"Cras ut quam viverra, fermentum tortor non, tempor nisl",
						"Praesent risus lacus, aliquet ac risus vel, consequat iaculis diam",
						"Donec dictum, nisl vitae dapibus feugiat, elit lacus congue eros, et posuere massa dui et turpis",
						"Mauris malesuada tristique elementum. Etiam interdum eros leo, sit amet ultricies augue aliquam at",
						"Phasellus sagittis id nunc sed facilisis");
		List<CookingStep> randomSteps = Lists.newArrayList();
		for (int i = 0; i < RandomUtils.nextInt(8); i++) {
			randomSteps.add(new CookingStep(steps.get(RandomUtils.nextInt(steps.size()))));
		}
		return randomSteps;
	}

}
