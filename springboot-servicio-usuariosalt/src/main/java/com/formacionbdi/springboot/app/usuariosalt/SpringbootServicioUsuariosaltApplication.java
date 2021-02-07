package com.formacionbdi.springboot.app.usuariosalt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan({"com.formacionbdi.springboot.app.usuarios.commons.models.entity"})
@SpringBootApplication
public class SpringbootServicioUsuariosaltApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioUsuariosaltApplication.class, args);
	}

}
