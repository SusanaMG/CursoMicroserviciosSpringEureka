package com.formacionbdi.springboot.app.usuarioscontr.models.service;

import java.util.List;

import com.formacionbdi.springboot.app.usuarios.commons.models.entity.Usuario;


public interface IUsuarioService { 

	// Métodos que implementa la interfaz. 
	//El nomnbre del metodo no importa pero ponemos el mismo que está en el repositorio CrudRepository.class
	// referenciado en ProductoDao.java

	public List<Usuario> findAll();

	public Usuario findById(Long id);

	public Usuario save(Usuario usuario);

	public void deleteById(Long id);
}


