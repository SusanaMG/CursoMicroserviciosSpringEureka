package com.formacionbdi.springboot.app.usuarioscontr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
//Para reconocer el package de producto de commons. Para todos los packages que se necesiten. Separado por comas
@EntityScan({"com.formacionbdi.springboot.app.usuarios.commons.models.entity"}) 	
public class SpringbootServicioUsuarioscontrApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioUsuarioscontrApplication.class, args);
	}

}
