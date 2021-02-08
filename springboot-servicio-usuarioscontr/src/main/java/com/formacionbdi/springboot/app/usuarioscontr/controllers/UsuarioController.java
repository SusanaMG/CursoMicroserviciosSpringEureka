package com.formacionbdi.springboot.app.usuarioscontr.controllers;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.springboot.app.usuarios.commons.models.entity.Usuario;
import com.formacionbdi.springboot.app.usuarioscontr.models.service.IUsuarioService;

@RestController
public class UsuarioController {
	
	@Autowired
	private Environment env; 		// Para el balanceo de cargas con FEIGN
	
	@Value("${server.port}")		// Para el balanceo de cargas con RESTTEMPLATE
	private Integer port;
	
	
	@Autowired
	private IUsuarioService usuarioService;	
	
	//Métodos handler
	
	@GetMapping("/listar")		// Mapeo a un endpoint para la comunicación entre servicios
	public List<Usuario> listar(){
		return usuarioService.findAll().stream().map(usuario ->{
			return usuario;
		}).collect(Collectors.toList());
	}

	@GetMapping("/ver/{id}")
	public Usuario detalle(@PathVariable Long id) {
		Usuario usuario = usuarioService.findById(id);
		return usuario;
	}

	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public Usuario crear(@RequestBody Usuario usuario) {
		return usuarioService.save(usuario);
		
	}
	
	@PutMapping("/editar/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Usuario editar(@RequestBody Usuario usuario, @PathVariable Long id) {

		Usuario usuarioDb = usuarioService.findById(id);
		usuarioDb.setUsername(usuario.getUsername());	
//		usuarioDb.setPassword(usuario.getPassword());
//		usuarioDb.setNombre(usuario.getNombre());
//		usuarioDb.setApellido(usuario.getApellido());
//		usuarioDb.setEmail(usuario.getEmail());

		return usuarioService.save(usuarioDb);
	}

	@DeleteMapping("/eliminar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminar(@PathVariable Long id) {

		usuarioService.deleteById(id);
	}



}