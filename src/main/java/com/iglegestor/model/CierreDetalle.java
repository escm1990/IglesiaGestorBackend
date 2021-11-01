package com.iglegestor.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class CierreDetalle implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String descripcion;
	private double monto;
	
	@JoinColumn(name = "cierre_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Cierre.class, fetch = FetchType.EAGER)
	@JsonIgnore
    private Cierre cierre;
	
	@Column(name = "cierre_id")
    private int cierre_id;

	public CierreDetalle() {
		super();
	}

	public CierreDetalle(String descripcion, double monto, int cierre_id) {
		super();
		this.descripcion = descripcion;
		this.monto = monto;
		this.cierre_id = cierre_id;
	}

	public int getCierre_id() {
		return cierre_id;
	}

	public void setCierre_id(int cierre_id) {
		this.cierre_id = cierre_id;
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

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public Cierre getCierre() {
		return cierre;
	}

	public void setCierre(Cierre cierre) {
		this.cierre = cierre;
	}

	@Override
	public String toString() {
		return "CierreDetalle [id=" + id + ", descripcion=" + descripcion + ", monto=" + monto + ", cierre_id="
				+ cierre_id + "]";
	}
	
	
	
}
