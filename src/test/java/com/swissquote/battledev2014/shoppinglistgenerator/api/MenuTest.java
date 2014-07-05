package com.swissquote.battledev2014.shoppinglistgenerator.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swissquote.battledev2014.shoppinglistgenerator.ShoppinglistGeneratorApplication;
import com.swissquote.battledev2014.shoppinglistgenerator.domain.Menu;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ShoppinglistGeneratorApplication.class)
@WebAppConfiguration
@IntegrationTest
public class MenuTest {

	private ShoppingListGeneratorApi api = new ShoppingListGeneratorApi();

	@Test
	public void crudMenuShouldSucceed() throws IOException {
		Menu menu = Fixtures.buildMenu();
		// Create
		URI uri = api.createMenu(menu);
		assertNotNull(uri);

		// Read
		Menu persisted = api.loadMenu(uri);

		assertNotNull(persisted);
		String id = persisted.getId();
		assertNotNull(id);
		persisted.setId(null);
		assertEquals(persisted, menu);

		// Update
		persisted.setId(id);
		String expectedName = "test test test";
		persisted.setName(expectedName);
		api.updateMenu(uri, persisted);
		Menu updated = api.loadMenu(uri);
		assertEquals(persisted, updated);

		// Delete
		api.deleteResource(uri);
		try {
			new RestTemplate().getForEntity(uri, Menu.class);
		}
		catch (HttpClientErrorException e) {
			assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
		}
		catch (Exception e) {
			fail("expected {" + HttpClientErrorException.class + "}, got {" + e + "}");
		}
	}
}
