package com.swissquote.battledev2014.shoppinglistgenerator.domain;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.List;

public class ShoppingList implements Serializable {

	private String name;
	private List<Ingredient> ingredients;

	public ShoppingList() {
		// Default constructor needed for frameworks
	}

	public ShoppingList(String name, List<Ingredient> ingredients) {
		setIngredients(ingredients);
		setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ShoppingList that = (ShoppingList) o;

		if (ingredients != null ? !ingredients.equals(that.ingredients) : that.ingredients != null) return false;
		if (name != null ? !name.equals(that.name) : that.name != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (ingredients != null ? ingredients.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("name", name)
				.add("ingredients", ingredients)
				.toString();
	}
}
