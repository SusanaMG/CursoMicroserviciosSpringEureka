package com.formacionbdi.springboot.app.oauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.formacionbdi.springboot.app.oauth.services.IUsuarioService;
import com.formacionbdi.springboot.app.usuarios.commons.models.entity.Usuario;

import feign.FeignException;


@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {
 
	private Logger log = LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);
	
	@Autowired
	private Environment env;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		
		UserDetails user = (UserDetails)authentication.getPrincipal();
 		String mensaje = "Success Login: " + user.getUsername();
 		System.out.println(mensaje);
 		log.info(mensaje);
 		
 		//Uso la condición de este if para forzar y salvar el error que me da al llamarse dos veces al publishAuthenticationSuccess
 		//if(! user.getUsername().equals("frontendapp")) {
  		//if(user.getUsername().equals("admin") || user.getUsername().equals("susana")) {
 		if(! user.getUsername().equals(env.getProperty("config.security.oauth.client.id"))) {
	 		//Reinicio de los intentos de Login en cada inicio de sesión
	 		Usuario usuario = usuarioService.findByUsername(authentication.getName()); 
	 		
	 		if(usuario.getIntentos() != null && usuario.getIntentos() > 0) {
	 			usuario.setIntentos(0);
				usuarioService.update(usuario, usuario.getId());
	 		}
 		}
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		
		String mensaje = "Error en el Login: " + exception.getMessage();
		log.error(mensaje);
 		System.out.println(mensaje);
 		
	 	
 		try {
 			Usuario usuario = usuarioService.findByUsername(authentication.getName()); 
	 		
	 		if(usuario.getIntentos() == null) {
	 			usuario.setIntentos(0);
	 		}
	 		
	 		log.info("Número de intentos actual es de: " + usuario.getIntentos());
	 		usuario.setIntentos(usuario.getIntentos() + 1);
	 		
	 		log.info("Número de intentos después es de: " + usuario.getIntentos());

			
			if(usuario.getIntentos() >= 3) {
				log.error("Usuario " + usuario.getNombre() + " deshabilitado");
				log.error(String.format("El usuario %s deshabilitado por máximos intentos", usuario.getUsername()));
				usuario.setEnabled(false);
			}
	 		
			usuarioService.update(usuario, usuario.getId());
 		
 		} catch (FeignException e) {
			log.error(String.format("El usuario %s no existe en el sistema", authentication.getName()));
		}
		
	}

}


/*
 * NOTA SOBRE EL ERROR QUE SE PRODUCE EN ESTA CLASE:
 * Al hacer la llamada desde el POSTMAN a "localhost:8090/api/security/oauth/token" (con el método POST) con las siguientes
 * credenciales:
 * ***Authorization: Basic Auth: Username=frontendapp - Password=12345
 * ***Body: username=admin - password=12345 - grant_type=password (o username=susana - password=12345 - grant_type=password)
 * 
 * Entra primero en esta clase al método publishAuthenticationSuccess porque coge "user.getUsername()" como "frontendapp", 
 * después continúa en la clase UsuarioService cogiendo el parámetro de entrada "username" como "admin", se autentifica 
 * correctamente y por último, vuelve a esta clase al método publishAuthenticationSuccess siendo ahora distinto y cogiendo 
 * "user.getUsername()" como "admin".
 * 
 * No encuentro el motivo por el que se produce esto y no entra primero a la clase UsuarioService y luego, al realizar la
 * autenticación positiva o negativa, entra en esta clase AuthenticationSuccessErrorHandler.
 * 
 * Al coger "user.getUsername()" como "frontendapp" daba problemas en la creación del Usuario usuario en el método de éxito
 * y por eso he implementado un if para solventarlo (pese a que no es lo correcto). * 
 */







