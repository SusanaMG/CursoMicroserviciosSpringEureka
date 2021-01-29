package com.formacionbdi.springboot.app.item2.models.service;

import java.util.List;

import com.formacionbdi.springboot.app.item2.models.Item;
import com.formacionbdi.springboot.app.item2.models.Producto;

public interface ItemService {
	
	public List<Item> findAll();
	
	public Item findById(Long id, Integer cantidad);
	
	public Producto save(Producto producto);
	
	public Producto update(Producto producto, Long id);
	
	public void delete(Long id);
	
}