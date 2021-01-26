package com.formacionbdi.springboot.app.item2.models.service;

import java.util.List;

import com.formacionbdi.springboot.app.item2.models.Item;

public interface ItemService {
	
	public List<Item> findAll();
	public Item findById(Long id, Integer cantidad);

}