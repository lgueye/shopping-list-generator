package com.swissquote.battledev2014.shoppinglistgenerator.api;

import com.swissquote.battledev2014.shoppinglistgenerator.domain.Menu;
import com.swissquote.battledev2014.shoppinglistgenerator.service.MenuService;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/menus")
public class MenusResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(MenusResource.class);

	@Autowired
	private MenuService service;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody @Valid Menu menu) {
		LOGGER.info("Attempt to create menu: {}", menu);
		// Save menu and get new id
		String id = this.service.save(menu);
		LOGGER.debug("Saved menu with id: {}", id);

		// Build URI
		final URI location = ServletUriComponentsBuilder.fromCurrentServletMapping().path("/api/menus/{id}").build().expand(id).toUri();
		LOGGER.debug("New resource can be found at : {}", location.toString());

		// Add uri location
		final HttpHeaders headers = new HttpHeaders();
		headers.setLocation(location);

		// Add header to response

		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<Menu> list() {
		LOGGER.info("Listing all menus");
		return this.service.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Menu get(@PathVariable("id") String id) {
		Menu menu = this.service.getOne(id);
		LOGGER.info("Found menu with id {}", id);
		if (menu == null) {
			throw new ResourceNotFoundException("Menu with id {" + id + "} was not found");
		}
		return menu;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable("id") String id, @RequestBody @Valid Menu menu) {
		LOGGER.info("Updated menu with id {}", id);
		service.update(id, menu);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") String id) {
		LOGGER.info("Deleted menu with id {}", id);
		this.service.delete(id);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete() {
		LOGGER.debug("Deleted all menus");
		this.service.delete();
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Menu not found")
	public void notFound() {
	}
}
