package com.swissquote.battledev2014.shoppinglistgenerator.api;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import com.swissquote.battledev2014.shoppinglistgenerator.service.NormalizerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.swissquote.battledev2014.shoppinglistgenerator.domain.Recipe;
import com.swissquote.battledev2014.shoppinglistgenerator.service.RecipeProviderService;
import com.swissquote.battledev2014.shoppinglistgenerator.service.RecipeService;

@RestController
@RequestMapping("/api/recipes")
public class RecipesResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(RecipesResource.class);

	@Autowired
	private RecipeService recipeService;

	@Autowired
	private RecipeProviderService recipeProviderService;

	@Autowired
	private NormalizerService normalizerService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody @Valid Recipe recipe) {
		LOGGER.debug("Attempt to create recipe: {}", recipe);
		// Save recipe and get new id
		String id = this.recipeService.save(recipe);
		LOGGER.debug("Saved recipe with id: {}", id);

		// Build URI
		final URI location = ServletUriComponentsBuilder.fromCurrentServletMapping().path("/api/recipes/{id}").build().expand(id).toUri();
		LOGGER.debug("New resource can be found at : {}", location.toString());

		// Add uri location
		final HttpHeaders headers = new HttpHeaders();
		headers.setLocation(location);

		// Add header to response

		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<Recipe> findAll() {
		LOGGER.debug("Searching all recipes");
		return this.recipeService.findAll();
	}

	@RequestMapping(value = "/category/{catId}", method = RequestMethod.GET)
	public List<Recipe> findByCategory(@PathVariable("catId") String catId) {
		LOGGER.debug("Searching recipes by category: " + catId);
		return this.recipeService.findByCategory(catId);
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public List<Recipe> search(@RequestParam("q") String query) {
		LOGGER.debug("Searching recipes which title or ingredients.name contains {}", query);
		return this.recipeService.search(query);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Recipe get(@PathVariable("id") String id) {
		Recipe recipe = this.recipeService.getOne(id);
		if (recipe == null) {
			throw new ResourceNotFoundException("Recipe with id {" + id + "} was not found");
		}
		LOGGER.debug("Found recipe with id: {}", id);
		return recipe;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void update(@PathVariable("id") String id, @RequestBody @Valid Recipe recipe) {
		LOGGER.debug("Updated recipe with id: {}", id);
		recipeService.update(id, recipe);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		LOGGER.debug("Deleted recipe with id: {}", id);
		this.recipeService.delete(id);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Recipe not found")
	public void notFound() {
	}

	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete() {
		LOGGER.debug("Deleted all recipes");
		this.recipeService.delete();
	}

	@RequestMapping(value = "/reload", method = RequestMethod.GET)
	public void reload() throws IOException {
		long start = System.currentTimeMillis();
		recipeService.delete();
		List<Recipe> recipes = recipeProviderService.generate();
		recipeService.save(normalizerService.normalize(recipes));
		LOGGER.debug("Generated {} recipes in {} ms", recipes.size(), (System.currentTimeMillis() - start));
	}

	@RequestMapping(value = "/demo", method = RequestMethod.GET)
	public void demo() throws IOException {
		long start = System.currentTimeMillis();
		recipeService.delete();
		List<Recipe> recipes = recipeProviderService.demo();
		recipeService.save(normalizerService.normalize(recipes));
		LOGGER.debug("Generated {} recipes in {} ms", recipes.size(), (System.currentTimeMillis() - start));
	}
}
