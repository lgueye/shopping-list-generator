package com.swissquote.battledev2014.shoppinglistgenerator.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;
import com.swissquote.battledev2014.shoppinglistgenerator.domain.Recipe;
import com.swissquote.battledev2014.shoppinglistgenerator.repository.RecipeElasticsearchRepository;

@Service
@Validated
public class RecipeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RecipeService.class);

	@Autowired
	private RecipeElasticsearchRepository elasticsearchRespository;

	@Transactional
	public String save(@NotNull @Valid final Recipe recipe) {
		String id = Hashing.sha1().hashString(RandomStringUtils.random(20), Charsets.UTF_8).toString();
		recipe.setId(id);
		Recipe persisted = elasticsearchRespository.save(recipe);
		return persisted.getId();
	}

	@Transactional
	public void update(String id, @NotNull @Valid final Recipe recipe) {
		assert id.equals(recipe.getId());
		assert getOne(id) != null;
		elasticsearchRespository.save(recipe);
	}

	/**
	 * delete single document
	 */
	@Transactional
	public void delete(final String id) {
		elasticsearchRespository.delete(id);
	}

	/**
	 * find all
	 */
	@Transactional(readOnly = true)
	public List<Recipe> findAll() {
		return Lists.newArrayList(elasticsearchRespository.findAll());
	}

	/**
	 * find by RecipeCategory
	 */
	@Transactional(readOnly = true)
	public List<Recipe> findByCategory(String categoryId) {
		LOGGER.debug("findByCategory: " + categoryId);
		return Lists.newArrayList(elasticsearchRespository.findByCategory(categoryId.toLowerCase()));
	}

	@Transactional(readOnly = true)
	public Recipe getOne(String id) {
		return elasticsearchRespository.findOne(id);
	}

	/**
	 * clear index
	 */
	@Transactional
	public void delete() {
		elasticsearchRespository.deleteAll();
	}

	public List<Recipe> search(String query) {
		return elasticsearchRespository.search(query);
	}

	public void save(List<Recipe> recipes) {
		elasticsearchRespository.save(recipes);
	}
}
