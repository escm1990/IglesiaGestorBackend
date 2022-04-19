package com.iglegestor.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TipoEvento implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String descripcion;
	private String estado;
	
	private String ultimoUsuario;
	
	public TipoEvento() {
		super();
	}

	public TipoEvento(String descripcion, String estado, String ultimoUsuario) {
		super();
		this.descripcion = descripcion;
		this.estado = estado;
		this.ultimoUsuario = ultimoUsuario;
	}

	public String getUltimoUsuario() {
		return ultimoUsuario;
	}

	public void setUltimoUsuario(String ultimoUsuario) {
		this.ultimoUsuario = ultimoUsuario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "TipoEvento [id=" + id + ", descripcion=" + descripcion + ", estado=" + estado +  ", ultimoUsuario=" + ultimoUsuario + "]";
	}

}
