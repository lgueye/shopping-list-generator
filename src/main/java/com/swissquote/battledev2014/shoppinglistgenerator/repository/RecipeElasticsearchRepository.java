package com.swissquote.battledev2014.shoppinglistgenerator.repository;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.swissquote.battledev2014.shoppinglistgenerator.domain.Recipe;

public interface RecipeElasticsearchRepository extends PagingAndSortingRepository<Recipe, String> {

	public static final String FIND_BY_INGREDIENT_NAME_OR_TITLE_QUERY = "{\"bool\" : {\"should\" : [ {\"wildcard\" : { \"title\" : \"*?0*\" }}, {\"nested\" : {\"path\" : \"ingredients\",\"score_mode\" : \"avg\",\"query\" : {\"wildcard\" : { \"name\" : \"*?0*\" }}}}]}}";

	@Query(FIND_BY_INGREDIENT_NAME_OR_TITLE_QUERY)
	List<Recipe> search(String query);

	public static final String FIND_BY_CATEGORY_QUERY = "{\"term\" : {\"category\" : \"?0\" }}";

	@Query(FIND_BY_CATEGORY_QUERY)
	List<Recipe> findByCategory(String category);
}
