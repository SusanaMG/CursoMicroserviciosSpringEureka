package com.formacionbdi.springboot.app.zuul.oauth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;   
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@RefreshScope						//Para que se actualice si hay cambios
@Configuration
@EnableResourceServer				//Habilitar la configuración del servidor de recurso
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{
	 
	@Value("${config.security.oauth.jwt.key}")
	private String jwtKey;
	
	/*
	 * Método para configurar el token
	 */
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore());
	}

	/*
	 * Método para proteger rutas o endpoints. 
	 * Rutas específicas al principio y luego las genéricas (/**)
	 * Registro de CORS (para el acceso de aplicaciones cliente). Creamos los métodos corsConfigurationSource() 
	 * y corsFilter()
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		//Ruta pública. Cualquier usuario puede autenticarse
		.antMatchers("/api/security/oauth/**").permitAll() 
		//Ruta pública. Cualquier usuario puede listar
		.antMatchers(HttpMethod.GET, "/api/productos/listar", "/api/items/listar", "/api/items2/listar", 
				"/api/usuarios/usuarios", "/api/usuarioscontr/listar").permitAll()
		//Solo usuarios con rol "ADMIN" o "USER" pueden acceder a ver elementos
		.antMatchers(HttpMethod.GET, "/api/productos/ver/{id}", "/api/items/ver/{id}/cantidad/{cantidad}", 
				"/api/items2/ver/{id}/cantidad/{cantidad}", "/api/usuarios/usuarios/{id}", 
				"/api/usuarioscontr/ver/{id}").hasAnyRole("ADMIN", "USER")  //No usar ROLE_ADMIN / ROLE_USER aquí. Sí en BBDD
		//CRUD. Solo accesible para administradores.
		//Generalizando rutas del CRUD:
		.antMatchers("/api/productos/**", "/api/items/**", "/api/items2/**", "/api/usuarios/**", 
				"/api/usuarioscontr/**").hasRole("ADMIN")
		/*
		//Especificando rutas del CRUD:
		.antMatchers(HttpMethod.POST, "/api/productos/crear", "/api/items/crear", "/api/items2/crear", 
				"/api/usuarios/usuarios", "/api/usuarioscontr/ver/{id}").hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT, "/api/productos/editar/{id}", "/api/items/editar/{id}", "/api/items2/editar/{id}",
				"/api/usuarios/usuarios/{id}", "/api/usuarioscontr/editar/{id}").hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE, "/api/productos/eliminar/{id}", "/api/items/eliminar/{id}", "/api/items2/eliminar/{id}",
				"/api/usuarios/usuarios/{id}", "/api/usuarioscontr/eliminar/{id}").hasRole("ADMIN");
		*/
		
		//Cualquier otra ruta no configurada necesita autenticación
 		.anyRequest().authenticated()
		//Para registrar CORS 
		.and().cors().configurationSource(corsConfigurationSource());
	}
		
	/*
	 * Para configurar CORS. Ha de ser PUBLIC
	 */
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsconfig = new CorsConfiguration();
		//Genérico
		//corsconfig.addAllowedOrigin("*");
		corsconfig.setAllowedOrigins(Arrays.asList("*"));	//Lista de nombres de orígenes separados por comas
		//Permitir verbos HTTP. OAuth2 utiliza OPTIONS en los endpoints oauth, hay que incluirlo
		corsconfig.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
		//Que sea TRUE
		corsconfig.setAllowCredentials(true);
		//uAuthorization: importante cuando se envía el token y nos autenticamos con el client id y el secret key
		corsconfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
		
		//Pasar esta configuración a los endpoint
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsconfig);
		
		return source;
	}

	/*
	 * Método para registrar un filtro de CORS y que no solamente quede configurado en Spring Security
	 * CORS queda configurado a nivel global en un filtro de Spring en toda la aplicación
	 * Ha de ser PUBLIC
	 * 
	 */
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		FilterRegistrationBean<CorsFilter> bean = 
				new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);		//Darle prioridad alta
		return bean;
	}
	
	/*
	 * Misma configuración que el AuthorizationServerConfig.java del microservicio oauth
	 */
	@Bean
	public JwtTokenStore tokenStore() {

		return new JwtTokenStore(accessTokenConverter());
	}
	
	/*
	 * Misma configuración que el AuthorizationServerConfig.java del microservicio oauth
	 */
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {

		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		//Lo tenemos configurado en bootstarp.properties
		//tokenConverter.setSigningKey("algun_codigo_secreto_aeiou");  //El mismo código secreto o llave
		tokenConverter.setSigningKey(jwtKey);  //El mismo código secreto o llave

		return tokenConverter;
	}
	
}
 

/*
 @EnableResourceServer: habilitar la configuración del servidor de recurso
 
 Métodos con la misma estructura que el microservicio oauth (AuthorizationServerConfig.java = servidor de autorización). 
 Se copian los @Bean: tokenStore() y accessTokenConverter().
 En el accessTokenConverter() se tiene que tener el mismo código secreto o llave con la cual se firma el token: estamos 
 validando que el token sea el correcto. 
  
 */


