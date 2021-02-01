package com.formacionbdi.springboot.app.item2.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.springboot.app.item2.models.Item;
import com.formacionbdi.springboot.app.item2.models.Producto;
import com.formacionbdi.springboot.app.item2.models.service.ItemService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RefreshScope
@RestController
public class ItemController {
	
	private Logger log = LoggerFactory.getLogger(ItemController.class);
	
	
	@Autowired
	private Environment env;
	
	
	@Autowired
	@Qualifier("serviceFeign")
	//@Qualifier("serviceRestTemplate")		//En el caso de usar RestTemplate
	private ItemService itemService;
	
	
	@Value("${configuracion.texto}")
	private String texto;
	
	
	// Métodos handler del controlador
	
	@GetMapping("/listar")
	public List<Item> listar(){
		return itemService.findAll();
	}
	
	//Método alternativo para cuando hay errores 
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
	
	
	@GetMapping("/obtener-config")
	public ResponseEntity<?> obtenerConfig(@Value("${server.port}") String puerto) {

		log.info(texto);

		// Dos formas de usar las propiedades de application.properties:
		// 1. Declarandola con @Value como variable.
		// 2. Pasándosela a la función como parámetro con @Value
		Map<String, String> json = new HashMap<>();
		json.put("texto", texto);
		json.put("puerto", puerto);

		if(env.getActiveProfiles().length>0 && env.getActiveProfiles()[0].equals("dev")) {
			json.put("autor.nombre", env.getProperty("configuracion.autor.nombre"));
			json.put("autor.email", env.getProperty("configuracion.autor.email"));
		}
		
		return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);
	}
	
	/*
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto crear(@RequestBody Producto producto) {
		return itemService.save(producto);
	}
	*/
	
}