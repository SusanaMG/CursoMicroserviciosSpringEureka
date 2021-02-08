package com.formacionbdi.springboot.app.usuarioscontr.models.dao;

import org.springframework.data.repository.CrudRepository;
import com.formacionbdi.springboot.app.usuarios.commons.models.entity.Usuario;

public interface UsuarioDao extends CrudRepository<Usuario, Long>{

}
