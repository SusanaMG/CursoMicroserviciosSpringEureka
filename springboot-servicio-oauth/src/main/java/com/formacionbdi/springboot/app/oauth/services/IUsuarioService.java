package com.formacionbdi.springboot.app.oauth.services;


import com.formacionbdi.springboot.app.usuarios.commons.models.entity.Usuario;

public interface IUsuarioService {
	
	public Usuario findByUsername(String username);
	
	/*
	 * Método para actualizar. Lo usamos para el número de intentos del Login
	 */
	public Usuario update(Usuario usuario,  Long id);

}
