package com.swissquote.battledev2014.shoppinglistgenerator.service;

import java.util.List;

import com.swissquote.battledev2014.shoppinglistgenerator.domain.Recipe;

public interface NormalizerService {
	List<Recipe> normalize(List<Recipe> source);
}
