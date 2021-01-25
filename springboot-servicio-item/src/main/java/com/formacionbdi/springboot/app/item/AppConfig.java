package com.formacionbdi.springboot.app.item;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
	
	//Es una clase de configuración

	@Bean("clienteRest")
	public RestTemplate registrarRestTemplate() {
		return new RestTemplate();	
	}
}
