package com.formacionbdi.springboot.app.item2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableCircuitBreaker
@EnableEurekaClient
@EnableFeignClients			//Habilita los clientes Feign e inyectarlos en otros componentes spring
@SpringBootApplication
public class SpringbootServicioServicioItem2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioServicioItem2Application.class, args);
	}

}

