package com.swissquote.battledev2014.shoppinglistgenerator.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;
import com.swissquote.battledev2014.shoppinglistgenerator.domain.Menu;
import com.swissquote.battledev2014.shoppinglistgenerator.repository.MenuElasticsearchRepository;

@Service
@Validated
public class MenuService {

	@Autowired
	private MenuElasticsearchRepository elasticsearchRespository;

	@Transactional
	public String save(@NotNull @Valid final Menu menu) {
        String id = Hashing.sha1().hashString(RandomStringUtils.random(20), Charsets.UTF_8).toString();
        menu.setId(id);
        Menu persisted = elasticsearchRespository.save(menu);
        return persisted.getId();
	}

	@Transactional
	public void update(String id, @NotNull @Valid final Menu menu) {
		assert id.equals(menu.getId());
		assert getOne(id) != null;
        elasticsearchRespository.save(menu);
	}

	@Transactional
	public void delete(final String id) {
        elasticsearchRespository.delete(id);
	}

	@Transactional(readOnly = true)
	public List<Menu> findAll() {
		return Lists.newArrayList(elasticsearchRespository.findAll());
	}

	@Transactional(readOnly = true)
	public Menu getOne(String id) {
		return elasticsearchRespository.findOne(id);
	}

	@Transactional
	public void delete() {
		elasticsearchRespository.deleteAll();
	}
}
