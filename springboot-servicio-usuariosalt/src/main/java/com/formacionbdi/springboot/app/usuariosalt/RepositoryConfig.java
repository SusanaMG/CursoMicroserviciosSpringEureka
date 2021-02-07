package com.formacionbdi.springboot.app.usuariosalt;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

import com.formacionbdi.springboot.app.usuarios.commons.models.entity.Role;
import com.formacionbdi.springboot.app.usuarios.commons.models.entity.Usuario;

@Configuration							//Anotación para las clases de configuración
public class RepositoryConfig implements RepositoryRestConfigurer{

	//Sobreescribir un método: para que aparezcan los id en la respuesta que obtenemos al hacer el request
	//Tanto para el usuario como para los roles
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config.exposeIdsFor(Usuario.class, Role.class);

	}
	
}
