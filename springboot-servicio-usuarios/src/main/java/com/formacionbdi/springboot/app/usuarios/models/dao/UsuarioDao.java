package com.formacionbdi.springboot.app.usuarios.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.formacionbdi.springboot.app.usuarios.models.entity.Usuario;

public interface UsuarioDao extends PagingAndSortingRepository<Usuario, Long>{
	
	//CONSULTAS PERSONALIZADAS
	
	//FORMA 1: método personalizado con palabras clave 
	//A través del nombre del método (Query method name) se ejecuta la consulta JPQL select u from Usuario where u.username =?1
	public Usuario findByUsername(String username);
	
	//Otro ejemplo
	//public Usuario findByUsernameAndEmail(String username, String email);

	
	//FORMA 2: anotación
	@Query("select u from Usuario u where u.username =?1") 	//Usuario: hace referencia a la clase de JPA Hibernate
	public Usuario obtenerPorUsername(String username);
	
	/*
	 * @Query("select u from Usuario u where u.username =?1") 	
	 * u: alias para el objeto usuario
	 * Usuario: hace referencia a la clase Usuario de JPA Hibernate
	 * u.atributoDeLaClaseUsuario
	 */
	
	//Otro ejemplo
	//@Query("select u from Usuario u where u.username =?1 and u.email=?2")
	//public Usuario obtenerPorUsernameYEmail(String username, String email);
	
	
	//CONSULTA NATIVA SQL (orientada a tablas y campos)
	@Query(value = "AQUÍ_VA_LA_SELECT_CON_LA_CONSULTA_NATIVA", nativeQuery = true) 	
	public Usuario obtenerPorUsernameNativo(String username);
	
}
