package com.formacionbdi.springboot.app.item2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.springboot.app.item2.models.Item;
import com.formacionbdi.springboot.app.item2.models.Producto;
import com.formacionbdi.springboot.app.item2.models.service.ItemService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class ItemController {
	
	@Autowired
	@Qualifier("serviceFeign")
	//@Qualifier("serviceRestTemplate")		//En el caso de usar RestTemplate
	private ItemService itemService;
	
	// Métodos handler del controlador
	
	@GetMapping("/listar")
	public List<Item> listar(){
		return itemService.findAll();
	}
	
	//Método alternativo para cuando haa errores
	@HystrixCommand(fallbackMethod = "metodoAlternativo") 
	@GetMapping("/ver/{id}/cantidad/{cantidad}")
	public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad) {
		return itemService.findById(id, cantidad);
	}
	
	
	//Retorna un objeto Item alternativo en caso de error
	public Item metodoAlternativo(Long id, Integer cantidad) {
		Item item = new Item();
		Producto producto = new Producto();
		
		item.setCantidad(cantidad);
		producto.setId(id);
		producto.setNombre("Cámara Sony");
		producto.setPrecio(500.00);
		item.setProducto(producto);		
		return item;
	}
}
