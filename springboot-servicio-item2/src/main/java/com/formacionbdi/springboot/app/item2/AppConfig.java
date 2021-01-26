package com.formacionbdi.springboot.app.item2;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.formacionbdi.springboot.app.item2.models.service.ItemServiceFeign;

@Configuration
//@LoadBalancerClient(name = "servicio-productos", configuration = ItemServiceFeign.class)
//@LoadBalancerClient(name = "servicio-productos")
public class AppConfig {
	
	//Es una clase de configuración

	@Bean("clienteRest")
	@LoadBalanced			
	public RestTemplate registrarRestTemplate() {
		return new RestTemplate();	
	}
}
