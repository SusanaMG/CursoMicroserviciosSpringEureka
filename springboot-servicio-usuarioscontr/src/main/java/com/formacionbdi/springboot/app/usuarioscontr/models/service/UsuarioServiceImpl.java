package com.formacionbdi.springboot.app.usuarioscontr.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacionbdi.springboot.app.usuarioscontr.models.dao.UsuarioDao;
import com.formacionbdi.springboot.app.usuarios.commons.models.entity.Usuario;

@Service
public class UsuarioServiceImpl implements IUsuarioService {
	
 	@Autowired
 	private UsuarioDao usuarioDao; 			//Inyectamos el UsuarioDao
 	
	@Override
	@Transactional(readOnly = true) 	//Para que se sincronice con la BBDD
	public List<Usuario> findAll() {
		return (List<Usuario>) usuarioDao.findAll() ;		
	}

	@Override
	@Transactional(readOnly = true) 	//Para que se sincronice con la BBDD
	public Usuario findById(Long id) {
		return usuarioDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Usuario save(Usuario usuario) {
		return usuarioDao.save(usuario);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		usuarioDao.deleteById(id);
	}

}
