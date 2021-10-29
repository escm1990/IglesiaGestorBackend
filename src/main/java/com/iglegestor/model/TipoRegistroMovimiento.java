package com.iglegestor.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class TipoRegistroMovimiento implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String descripcion;
	private String tipoContabilizacion;
	private String estado;
	
	@ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Iglesia iglesia;

	public TipoRegistroMovimiento() {
		super();
	}

	public TipoRegistroMovimiento(String descripcion, String tipoContabilizacion, String estado) {
		super();
		this.descripcion = descripcion;
		this.tipoContabilizacion = tipoContabilizacion;
		this.estado = estado;
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
				+ tipoContabilizacion + ", estado=" + estado + "]";
	}
	
}
