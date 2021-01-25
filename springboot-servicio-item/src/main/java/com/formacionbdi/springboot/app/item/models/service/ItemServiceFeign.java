package com.formacionbdi.springboot.app.item.models.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.formacionbdi.springboot.app.item.clientes.ProductoClienteRest;
import com.formacionbdi.springboot.app.item.models.Item;

@Service("serviceFeign")
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

}

//Esta clase es una alternativa a RestTemplate
/*
OPCIÓN A: @Primary: especifica que de los dos sevicios, éste es que el que va a implementar ItemService cuando no
se pone el nombre del componente
OPCIÓN B: @Service("serviceFeign") se le pone un nombre al servicio y desde el controlador se la hace referencia 
con @Qualifier("serviceFeign")
*/