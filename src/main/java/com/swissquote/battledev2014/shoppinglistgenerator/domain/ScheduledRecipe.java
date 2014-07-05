package com.swissquote.battledev2014.shoppinglistgenerator.domain;

import com.google.common.base.Objects;

public class ScheduledRecipe {

	private Integer mealsNumber;
	private String recipeId;

	public ScheduledRecipe() {
		// Default constructor needed for frameworks
	}

	public ScheduledRecipe(Integer mealsNumber, String recipeId) {
		this.mealsNumber = mealsNumber;
		this.recipeId = recipeId;
	}

	public String getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(String recipeId) {
		this.recipeId = recipeId;
	}

	public Integer getMealsNumber() {
		return mealsNumber;
	}

	public void setMealsNumber(Integer mealsNumber) {
		this.mealsNumber = mealsNumber;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ScheduledRecipe that = (ScheduledRecipe) o;

		if (mealsNumber != null ? !mealsNumber.equals(that.mealsNumber) : that.mealsNumber != null) return false;
		if (recipeId != null ? !recipeId.equals(that.recipeId) : that.recipeId != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = mealsNumber != null ? mealsNumber.hashCode() : 0;
		result = 31 * result + (recipeId != null ? recipeId.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("mealsNumber", mealsNumber)
				.add("recipeId", recipeId)
				.toString();
	}
}
