package com.formacionbdi.springboot.app.usuarios.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.RequestParam;

import com.formacionbdi.springboot.app.usuarios.models.entity.Usuario;

@RepositoryRestResource(path="usuarios")
public interface UsuarioDao extends PagingAndSortingRepository<Usuario, Long>{
	
	//////////////  CONSULTAS PERSONALIZADAS //////////////
	
	/*
	 * En este ejemplo son consultas de búsqueda. 
	 * localhost:8090/api/usuarios/usuarios/search/ + añadir el nombre del método
	 */
	
	/*
	 * FORMA 1: método personalizado con palabras clave
	 * A través del nombre del método (Query method name) se ejecuta la consulta JPQL select u from Usuario where u.username =?1
	 */
	
	//public Usuario findByUsername(String username);
	//POSTMAN: localhost:8090/api/usuarios/usuarios/search/findByUsername?username=admin
	
	//@RestResource(path="buscar-username")			
	//public Usuario findByUsername(@Param("nombre") String username);
	//POSTMAN: localhost:8090/api/usuarios/usuarios/search/buscar-username?nombre=admin 
	
	@RestResource(path="buscar-username")			//Anotación para personalizar los nombres
	public Usuario findByUsername(@Param("username") String username);
	//POSTMAN: localhost:8090/api/usuarios/usuarios/search/buscar-username?username=admin 
	//PROBANDO
	//public Usuario findByUsername(@RequestParam("username") String username);
	
	
	//Otro ejemplo
	//public Usuario findByUsernameAndEmail(String username, String email);

	
	/*
	 * FORMA 2: anotación @Query
	 */

	@Query("select u from Usuario u where u.username =?1") 
	public Usuario obtenerPorUsername(String username);
	//POSTMAN: localhost:8090/api/usuarios/usuarios/search/obtenerPorUsername?username=admin
	//También se puede usar la anotación @RestResource
	
	/*
	 * Explicación:
	 * @Query("select u from Usuario u where u.username =?1") 	
	 * u: alias para el objeto usuario
	 * Usuario: hace referencia a la clase Usuario de JPA Hibernate
	 * u.atributoDeLaClaseUsuario
	 */
	
	/*
	Otro ejemplo
	@Query("select u from Usuario u where u.username =?1 and u.email=?2")
	public Usuario obtenerPorUsernameYEmail(String username, String email);
	*/
	
	////////////// CONSULTA NATIVA SQL (orientada a tablas y campos) //////////////
	
	@Query(value = "AQUÍ_VA_LA_SELECT_CON_LA_CONSULTA_NATIVA", nativeQuery = true) 	
	public Usuario obtenerPorUsernameNativo(String username);
	
	
	//CRUD
	/*
	 * Se está usando el API Rest repository y se hace de forma automática gracias a la 
	 * anotación @RepositoryRestResource(path="usuarios")
	 * Ver cómo se ejecuta en el fichero del proyecto: NOTA_url_ejemplo_item2.txt
	 */

}
