package com.formacionbdi.springboot.app.productos.models.service;

import java.util.List;

import com.formacionbdi.springboot.app.productos.models.entity.Producto;

public interface IProductoService {
	
	// Métodos que implementa la interfaz. 
	//El nomnbre del metodo no importa pero ponemos el mismo que está en el repositorio CrudRepository.class
	// referenciado en ProductoDao.java
	
	public List<Producto> findAll();
	
	public Producto findById(Long id);
	
	public Producto save(Producto producto);
	
	public void deleteById(Long id);
	
}
