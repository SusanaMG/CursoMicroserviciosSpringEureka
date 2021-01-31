package com.formacionbdi.springboot.app.item.models.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.formacionbdi.springboot.app.item.clientes.ProductoClienteRest;
import com.formacionbdi.springboot.app.item.models.Item;
import com.formacionbdi.springboot.app.item.models.Producto;

@Service("serviceFeign")
//@Primary
public class ItemServiceFeign implements ItemService {

	@Autowired
	private ProductoClienteRest clienteFeign; 
	
	@Override
	public List<Item> findAll() {
		return clienteFeign.listar().stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
	}
	//Se transforma una lista de Productos en una lista de Items gracias a Java 8 al API streams

	@Override
	public Item findById(Long id, Integer cantidad) {
		return new Item(clienteFeign.detalle(id), cantidad);
	}

//	@Override
//	public Producto save(Producto producto) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Producto update(Producto producto, Long id) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void delete(Long id) {
//		// TODO Auto-generated method stub
//		
//	}

}

//Esta clase es una alternativa a RestTemplate
/*
 * Dos alternativas cuando hay dos clases que implementan la misma interfaz:
 * 1. Anotar la clase principal con @Primary para que sea la que se inyecte por
 *    defecto. 
 * 2. Pasarle a @Service el identificador de cada clase y, en la implementación,
 *    anotar con @Qualifier({identificador})
 */