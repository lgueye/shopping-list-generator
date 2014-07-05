package com.swissquote.battledev2014.shoppinglistgenerator.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.swissquote.battledev2014.shoppinglistgenerator.domain.Menu;

public interface MenuElasticsearchRepository extends PagingAndSortingRepository<Menu, String> {
	//  Marker interface for repositories in spring-data-elasticsearch
}
