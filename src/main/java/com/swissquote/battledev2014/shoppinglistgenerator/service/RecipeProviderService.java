package com.swissquote.battledev2014.shoppinglistgenerator.service;

import java.io.IOException;
import java.util.List;

import com.swissquote.battledev2014.shoppinglistgenerator.domain.Recipe;

public interface RecipeProviderService {

	List<Recipe> generate() throws IOException;

	List<Recipe> demo() throws IOException;
}
