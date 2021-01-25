package com.formacionbdi.springboot.app.item.models.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.formacionbdi.springboot.app.item.models.Item;
import com.formacionbdi.springboot.app.item.models.Producto;

@Service("serviceRestTemplate")
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private RestTemplate clienteRest;

	@Override
	public List<Item> findAll() {
		
		List<Producto> productos = Arrays.asList(clienteRest.getForObject("http://servicio-productos/listar", Producto[].class));
		return productos.stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
	}
	//getForObject(url con el endpoint, clase de tipo de objeto que se quiere obtener). El método devuelve un array.
	//Nota: se castea a List con el método de la clase Arrays
	//Se transforma una lista de Productos en una lista de Items gracias a Java 8 al API streams

	@Override
	public Item findById(Long id, Integer cantidad) {
		
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		
		Producto producto = clienteRest.getForObject("http://servicio-productos/ver/{id}", Producto.class, pathVariables);
		return new Item(producto, cantidad);
	}
		//getForObject(url con el endpoint, clase de tipo de objeto que se quiere obtener, parámetro mapeado)

}

/*
 * Dos alternativas cuando hay dos clases que implementan la misma interfaz:
 * 1. Anotar la clase principal con @Primary para que sea la que se inyecte por
 *    defecto. 
 * 2. Pasarle a @Service el identificador de cada clase y, en la implementación,
 *    anotar con @Qualifier({identificador})
 */
