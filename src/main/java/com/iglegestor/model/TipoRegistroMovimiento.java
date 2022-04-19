 package com.iglegestor.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class TipoRegistroMovimiento implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String descripcion;
	private String tipoContabilizacion;
	private String estado;
	
	private String ultimoUsuario;
	
	public TipoRegistroMovimiento() {
		super();
	}

	public TipoRegistroMovimiento(String descripcion, String tipoContabilizacion, String estado, String ultimoUsuario) {
		super();
		this.descripcion = descripcion;
		this.tipoContabilizacion = tipoContabilizacion;
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

	public String getTipoContabilizacion() {
		return tipoContabilizacion;
	}

	public void setTipoContabilizacion(String tipoContabilizacion) {
		this.tipoContabilizacion = tipoContabilizacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "TipoRegistroMovimiento [id=" + id + ", descripcion=" + descripcion + ", tipoContabilizacion="
				+ tipoContabilizacion + ", estado=" + estado + ", ultimoUsuario=" + ultimoUsuario + "]";
	}
	
}
