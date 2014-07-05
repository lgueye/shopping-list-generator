package com.swissquote.battledev2014.shoppinglistgenerator.domain;

import java.io.Serializable;

import com.google.common.base.Objects;

public class CookingStep implements Serializable {

	private String description;

	public String getDescription() {
		return description;
	}

	public CookingStep(String description) {
		this.description = description;
	}

	public CookingStep() {

	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		CookingStep that = (CookingStep) o;

		if (description != null ? !description.equals(that.description) : that.description != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return description != null ? description.hashCode() : 0;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("description", description)
				.toString();
	}
}
