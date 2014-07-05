package com.swissquote.battledev2014.shoppinglistgenerator.domain;

import com.google.common.base.Objects;

import java.io.Serializable;

public class Ingredient implements Serializable {

	private Double quantity;
	private String quantityUnit;
	private String name;

	public Ingredient() {
		// Default constructor needed for frameworks
	}

	public Ingredient(Double quantity, String quantityUnit, String name) {
		this.quantity = quantity;
		this.quantityUnit = quantityUnit;
		this.name = name;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public String getQuantityUnit() {
		return quantityUnit;
	}

	public void setQuantityUnit(String quantityUnit) {
		this.quantityUnit = quantityUnit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Ingredient that = (Ingredient) o;

		if (name != null ? !name.equals(that.name) : that.name != null) return false;
		if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
		if (quantityUnit != null ? !quantityUnit.equals(that.quantityUnit) : that.quantityUnit != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = quantity != null ? quantity.hashCode() : 0;
		result = 31 * result + (quantityUnit != null ? quantityUnit.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("quantity", quantity)
				.add("quantityUnit", quantityUnit)
				.add("name", name)
				.toString();
	}


}
