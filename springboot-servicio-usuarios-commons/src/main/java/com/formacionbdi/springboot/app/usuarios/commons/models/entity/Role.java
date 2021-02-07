package com.formacionbdi.springboot.app.usuarios.commons.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//import java.util.List;
//import javax.persistence.FetchType;
//import javax.persistence.ManyToMany;

@Entity
@Table(name = "roles")
public class Role implements Serializable {

	private static final long serialVersionUID = 8551353112143224098L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, length = 30)
	private String nombre;

	//Si se necesitase que fuera bidireccional la relación ManyToMany también se pondría este código en esta clase
	/*
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
	private List<Usuario> usuarios;
	*/
	
	// GETTERS AND SETTERS

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	//Si se necesitase que fuera bidireccional la relación ManyToMany también se pondría este código en esta clase
	/*
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	*/

}