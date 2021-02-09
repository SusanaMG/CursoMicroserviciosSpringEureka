package com.formacionbdi.springboot.app.oauth.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.formacionbdi.springboot.app.oauth.clients.UsuarioFeignClient;
import com.formacionbdi.springboot.app.usuarios.commons.models.entity.Usuario;

import feign.FeignException;

@Service
public class UsuarioService implements IUsuarioService, UserDetailsService {

	//Log para registrar mensajes en el terminal
	private Logger log = LoggerFactory.getLogger(UsuarioService.class);

	@Autowired
	private UsuarioFeignClient client;		//A través de client se captura el usuario
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			
		try {
			//Obtención del usuario
			Usuario usuario = client.findByUsername(username);
			
			//Obtención de los roles del usuario del tipo clase Usuario que es un List<Role> 
			//y casteo al tipo de rol del tipo Spring Security, tipo GrantedAuthority que es un List<GrantedAuthority>
			List<GrantedAuthority> authorities = usuario.getRoles()
					.stream()
					.map(role -> new SimpleGrantedAuthority(role.getNombre())) 			//hasta aquí es flujo
					.peek(authority -> log.info("Role: " + authority.getAuthority()))	//log: muestra el rol tipo authority del usuario autenticado
					.collect(Collectors.toList());										//aquí ya List<GrantedAuthority> 
		
			//log: username del usuario autenticado
			log.info("Usuario autenticado: " + username);
			
			//Return de un User del tipo de Spring Security (que está autenticado)
			return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, 
					true, true, authorities);
			
		} catch (FeignException e) {
			//Manejar el error si el usuario no existe
			log.error("Error en el login, no existe el usuario '"+ username +"' en el sistema");
			throw new UsernameNotFoundException("Error en el login, no existe el usuario '"+ username +"' en el sistema");
		}
	}

	@Override
	public Usuario findByUsername(String username) {
		return client.findByUsername(username);
	}

	/*
	 * Método para actualizar. Lo usamos para el número de intentos del Login
	 */
	@Override
	public Usuario update(Usuario usuario, Long id) {
		return client.update(usuario, id);
	}

}
/*
Sobre la propia clase: 
Esta clase de servicio se tiene que configurar en Spring Security para indicar que el proceso de autenticación (logging) 
se hace de esta forma con esta clase y esta implementación. Consumiendo un Api Rest mediante Feign.
*/

/*
Sobre la interfaces que se implementan:
UserDetailsService: es una interface propia de Spring Security
-- Método loadUserByUsername: se encarga de autenticar, de obtener al usuario por el username (independiente
-- de si se usa JPA, JDBC o ApiRest mediante Feign como este caso)

IUsuarioService: es una interface para obtener al usuario (objeto Usuario con todos los datos)
*/

/*
Para el casteo del tipo de rol de Spring Security, tipo GrantedAuthority:
No se crea un new GrantedAuthority porque es la interfaz, se tiene que crear del tipo de la clase contreta de 
Spring Security que es SimpleGrantedAuthority.

Las authorities que necesita el constructor de org.springframework.security.core.userdetails.User
son una lista de los roles que hemos creado. Pero la lista debe ser
del tipo GrantedAuthority, por lo que mapeamos nuestros roles para
pasarle al constructor de SimpleGrantedAuthority (GrantedAuthority es
la interfaz) el nombre de cada rol para que genere la lista.

*/
