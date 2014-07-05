package com.swissquote.battledev2014.shoppinglistgenerator.domain;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.google.common.base.Objects;

@Document(indexName = "recipes")
public class Recipe implements Serializable {

    @Id
	private String id;
	private String title;
	private Integer cookingTime;
	private Integer preparationTime;
	private CookingLevel level;
	private PriceRange priceRange;
	private PreferedSeason season;
	private MealType type;
    private int mealsNumber;
	private String image;
	private RecipeCategory category;

	@Field(type = FieldType.Nested)
	private List<Ingredient> ingredients;
	@Field(type = FieldType.Nested)
	private List<CookingStep> steps;

	public Recipe() {
		// Default constructor needed for frameworks
	}

	public Recipe(String id, String title, Integer cookingTime,
				  Integer preparationTime, CookingLevel level,
				  PriceRange priceRange, PreferedSeason season,
				  MealType type, int mealsNumber,
				  List<Ingredient> ingredients, List<CookingStep> steps) {
		this.id = id;
		this.title = title;
		this.cookingTime = cookingTime;
		this.preparationTime = preparationTime;
		this.level = level;
		this.priceRange = priceRange;
		this.season = season;
		this.type = type;
		this.mealsNumber = mealsNumber;
		this.ingredients = ingredients;
		this.steps = steps;
	}

	public Recipe(String id, String title, Integer cookingTime, Integer preparationTime,
				  CookingLevel level, PriceRange priceRange, PreferedSeason season,
				  MealType type, int mealsNumber, String image,
				  RecipeCategory recipeCategory, List<Ingredient> ingredients, List<CookingStep> steps) {
		this.id = id;
		this.title = title;
		this.cookingTime = cookingTime;
		this.preparationTime = preparationTime;
		this.level = level;
		this.priceRange = priceRange;
		this.season = season;
		this.type = type;
		this.mealsNumber = mealsNumber;
		this.image = image;
		this.category = recipeCategory;
		this.ingredients = ingredients;
		this.steps = steps;
	}

	public Recipe(String id, String title, Integer cookingTime, Integer preparationTime,
			CookingLevel level, PriceRange priceRange, PreferedSeason season,
			MealType type, int mealsNumber, String image,
			List<Ingredient> ingredients, List<CookingStep> steps) {
		this.id = id;
		this.title = title;
		this.cookingTime = cookingTime;
		this.preparationTime = preparationTime;
		this.level = level;
		this.priceRange = priceRange;
		this.season = season;
		this.type = type;
		this.mealsNumber = mealsNumber;
		this.image = image;
		this.ingredients = ingredients;
		this.steps = steps;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getCookingTime() {
		return cookingTime;
	}

	public void setCookingTime(Integer cookingTime) {
		this.cookingTime = cookingTime;
	}

	public Integer getPreparationTime() {
		return preparationTime;
	}

	public void setPreparationTime(Integer preparationTime) {
		this.preparationTime = preparationTime;
	}

	public CookingLevel getLevel() {
		return level;
	}

	public void setLevel(CookingLevel level) {
		this.level = level;
	}

	public PriceRange getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(PriceRange priceRange) {
		this.priceRange = priceRange;
	}

	public PreferedSeason getSeason() {
		return season;
	}

	public void setSeason(PreferedSeason season) {
		this.season = season;
	}

    public int getMealsNumber() {
        return mealsNumber;
    }

    public void setMealsNumber(int mealsNumber) {
        this.mealsNumber = mealsNumber;
    }

	public MealType getType() {
		return type;
	}

	public void setType(MealType type) {
		this.type = type;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public List<CookingStep> getSteps() {
		return steps;
	}

	public void setSteps(List<CookingStep> steps) {
		this.steps = steps;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public RecipeCategory getCategory() {
		return category;
	}

	public void setCategory(RecipeCategory recipeCategory) {
		this.category = recipeCategory;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Recipe recipe = (Recipe) o;

		if (mealsNumber != recipe.mealsNumber) return false;
		if (category != recipe.category) return false;
		if (cookingTime != null ? !cookingTime.equals(recipe.cookingTime) : recipe.cookingTime != null) return false;
		if (id != null ? !id.equals(recipe.id) : recipe.id != null) return false;
		if (image != null ? !image.equals(recipe.image) : recipe.image != null) return false;
		if (ingredients != null ? !ingredients.equals(recipe.ingredients) : recipe.ingredients != null) return false;
		if (level != recipe.level) return false;
		if (preparationTime != null ? !preparationTime.equals(recipe.preparationTime) : recipe.preparationTime != null)
			return false;
		if (priceRange != recipe.priceRange) return false;
		if (season != recipe.season) return false;
		if (steps != null ? !steps.equals(recipe.steps) : recipe.steps != null) return false;
		if (title != null ? !title.equals(recipe.title) : recipe.title != null) return false;
		if (type != recipe.type) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (title != null ? title.hashCode() : 0);
		result = 31 * result + (cookingTime != null ? cookingTime.hashCode() : 0);
		result = 31 * result + (preparationTime != null ? preparationTime.hashCode() : 0);
		result = 31 * result + (level != null ? level.hashCode() : 0);
		result = 31 * result + (priceRange != null ? priceRange.hashCode() : 0);
		result = 31 * result + (season != null ? season.hashCode() : 0);
		result = 31 * result + (type != null ? type.hashCode() : 0);
		result = 31 * result + mealsNumber;
		result = 31 * result + (image != null ? image.hashCode() : 0);
		result = 31 * result + (category != null ? category.hashCode() : 0);
		result = 31 * result + (ingredients != null ? ingredients.hashCode() : 0);
		result = 31 * result + (steps != null ? steps.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("id", id)
				.add("title", title)
				.add("cookingTime", cookingTime)
				.add("preparationTime", preparationTime)
				.add("level", level)
				.add("priceRange", priceRange)
				.add("season", season)
				.add("type", type)
				.add("mealsNumber", mealsNumber)
				.add("image", image)
				.add("category", category)
				.add("ingredients", ingredients)
				.add("steps", steps)
				.toString();
	}
}
