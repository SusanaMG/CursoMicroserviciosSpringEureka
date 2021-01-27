package com.formacionbdi.springboot. app.productos.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.springboot.app.productos.models.entity.Producto;
import com.formacionbdi.springboot.app.productos.models.service.IProductoService;

@RestController
public class ProductoController {
	
	@Autowired
	private Environment env; 		// Para el balanceo de cargas con FEIGN
	
	@Value("${server.port}")		// Para el balanceo de cargas con RESTTEMPLATE
	private Integer port;
	
	@Autowired
	private IProductoService productoService;
	
	//Métodos handler
	
	@GetMapping("/listar")		// Mapeo a un endpoint para la comunicación entre servicios
	public List<Producto> listar(){
		return productoService.findAll().stream().map(producto ->{
			//producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
			producto.setPort(port);
			return producto;
		}).collect(Collectors.toList());
	}
	
	@GetMapping("/ver/{id}")
	public Producto detalle(@PathVariable Long id) {
		Producto producto = productoService.findById(id);
		//producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		producto.setPort(port);
		
		//EXCEPCIÓN PARA COMPROBAR EL FUNCIONAMIENTO DE HYSTRIX: MANEJO DE ERRORES
		//Colocar throws Exception en la declaración del método
		/*
		 * boolean ok = false ; if(ok == false) { throw new
		 * Exception(" No se pudo cargar el producto"); }
		 */
		
		//REALIZACIÓN DE UNA PAUSA (THREAD.SLEEP) PARA COMPROBAR EL FUNCIONAMIENTO DE HYSTRIX
		
		/*try {
			Thread.sleep(2000L);			//Timeout de 2 segundos 
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		*/
		
		return producto;
	}

}
