package com.swissquote.battledev2014.shoppinglistgenerator.service.mock;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.swissquote.battledev2014.shoppinglistgenerator.domain.Ingredient;
import com.swissquote.battledev2014.shoppinglistgenerator.domain.Recipe;
import com.swissquote.battledev2014.shoppinglistgenerator.service.NormalizerService;

@Service
public class NormalizerServiceImpl implements NormalizerService {

	private Properties qtyunit;

	@Value("${qtyunit.file.name}")
	private ClassPathResource qresource;

	private Properties synunit;

	@Value("${qtysyn.file.name}")
	private ClassPathResource sresource;

	@PostConstruct
	public void init() throws IOException {
		qtyunit = new Properties();
		qtyunit.load(qresource.getInputStream());

		synunit = new Properties();
		synunit.load(sresource.getInputStream());
	}

	public List<Recipe> normalize(List<Recipe> source) {

		for (Recipe recipe : source) {
			for (Ingredient ingredient : recipe.getIngredients()) {
				for (Map.Entry<Object, Object> entry : qtyunit.entrySet()) {
					ingredient.setQuantityUnit(ingredient
							.getQuantityUnit()
							.replaceAll("\\p{Z}", "")
							.replaceAll((String) entry.getValue(),
									(String) entry.getKey()));
				}

				for (Map.Entry<Object, Object> entry : synunit.entrySet()) {
					if (ingredient.getQuantityUnit().replaceAll("\\p{Z}", "")
							.matches((String) entry.getKey())) {
						ingredient.setQuantityUnit("g"); // :-/
						ingredient.setQuantity(Double.valueOf((String) entry
								.getValue()));
					}
				}
			}
		}
		return source;
	}
}
