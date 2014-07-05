package com.swissquote.battledev2014.shoppinglistgenerator.domain;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.google.common.base.Objects;

@Document(indexName = "menus")
public class Menu implements Serializable {

	@Id
	private String id;
	private String name;
	@Field(type = FieldType.Nested)
	private List<ScheduledRecipe> schedules;

	public Menu() {
		// Default constructor needed for frameworks
	}

	public Menu(String id, String name, List<ScheduledRecipe> schedules) {
		this.id = id;
		this.name = name;
		this.schedules = schedules;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ScheduledRecipe> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<ScheduledRecipe> schedules) {
		this.schedules = schedules;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Menu menu = (Menu) o;

		if (id != null ? !id.equals(menu.id) : menu.id != null) return false;
		if (name != null ? !name.equals(menu.name) : menu.name != null) return false;
		if (schedules != null ? !schedules.equals(menu.schedules) : menu.schedules != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (schedules != null ? schedules.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("id", id)
				.add("name", name)
				.add("schedules", schedules)
				.toString();
	}
}
