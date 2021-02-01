package com.formacionbdi.springboot.app.item2.models.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.formacionbdi.springboot.app.item2.models.Item;
import com.formacionbdi.springboot.app.item2.models.Producto;

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

	@Override
	public Producto save(Producto producto) {
		
		HttpEntity<Producto> body = new HttpEntity<Producto>(producto);
		
		ResponseEntity<Producto> response = clienteRest.exchange("http://servicio-productos/crear", HttpMethod.POST, body, Producto.class);
		Producto productoResponse = response.getBody();
		
		return productoResponse;
	}
		//exchange(url con el endpoint, tipo de la petición, request con el objeto, tipo de dato en el que se quiere recibir el objeto)


	@Override
	public Producto update(Producto producto, Long id) {
		
		//Mapeo del valor del id con HashMap
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		
		HttpEntity<Producto> body = new HttpEntity<Producto>(producto);
		ResponseEntity<Producto> response = clienteRest.exchange("http://servicio-productos/editar/{id}",
				HttpMethod.PUT, body, Producto.class, pathVariables);

		return response.getBody();
	}
	
	@Override
	public void delete(Long id) {
		
		//Mapeo del valor del id con HashMap
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		
		clienteRest.delete("http://servicio-productos/eliminar/{id}", pathVariables);
	}

}

/*
 * Dos alternativas cuando hay dos clases que implementan la misma interfaz:
 * 1. Anotar la clase principal con @Primary para que sea la que se inyecte por
 *    defecto. 
 * 2. Pasarle a @Service el identificador de cada clase y, en la implementación,
 *    anotar con @Qualifier({identificador})
 */
